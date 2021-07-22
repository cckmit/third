package org.opoo.util.task;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.opoo.apps.lifecycle.Application;

/**
 * A class to manage the execution of tasks in the Jive system. A TaskEngine
 * object accepts Runnable objects and queues them for execution by
 * worker threads. Optionally, a priority may be assigned to each task. Tasks with a
 * higher priority are taken from the queue first.<p>
 */
public class TaskEngine {

    private static final Logger log = Logger.getLogger(TaskEngine.class.getName());

    public static final int HIGH_PRIORITY = 2;
    public static final int MEDIUM_PRIORITY = 1;
    public static final int LOW_PRIORITY = 0;

    /**
     * A queue of tasks to be executed.
     */
    private PriorityQueue<TaskWrapper> taskQueue = null;


    /**
     * A thread group for all workers.
     */
    private ThreadGroup threadGroup;

    /**
     * An array of worker threads.
     */
    private TaskEngineWorker[] workers = null;

    /**
     * A Timer to perform periodic tasks.
     */
    private Timer taskTimer = null;

    private final Object lock = new Object();

    private long newWorkerTimestamp = System.currentTimeMillis();
    private long busyTimestamp = System.currentTimeMillis();

    private boolean mockMode = false;

    public void setMockMode(boolean mockMode) {
        this.mockMode = mockMode;
    }

    /**
     * Flag to tell workers the app is completely initialize and tasks can execute
     */
    private  boolean started = false;

    public void init() {}


    /**
     * Used to queue up scheduled tasks that are created during initialization of spring beans that aren't using
     * definitions in spring-taskContext.xml.
     */
    private static class ScheduledTaskWrapper
    {
        private ScheduledTask task;
        private Date date;
        private long delay;
        private long period;

        private ScheduledTaskWrapper(ScheduledTask task, Date date, long delay, long period) {
            this.task = task;
            this.date = date;
            this.delay = delay;
            this.period = period;
        }
    }

    /**
     * If delayStartup is true, we'll use this list to gather up all the background tasks that are created by other singleton beans
     * (i.e., not defined in spring-taskContext.xml).
     */
    private List<ScheduledTaskWrapper> startupScheduledTaskList;

    public TaskEngine() {
        taskQueue = new PriorityQueue<TaskWrapper>();
        threadGroup = new ThreadGroup("Task Engine Workers");
        startupScheduledTaskList = new ArrayList<ScheduledTaskWrapper>();
    }

    /**
     * Starts the processing of periodic tasks. Called by TaskEngineBootstrap once app is in running state.
     */
    public void doInit() {
        // create TimerTask/worker threads
        initBackgroundResources();

        // kick off any tasks added by other singletons
        for ( ScheduledTaskWrapper w : startupScheduledTaskList) {
            if ( w.date != null ) {
                taskTimer.schedule(w.task, w.date);
            }
            else {
                taskTimer.scheduleAtFixedRate(w.task, w.delay, w.period);
            }
        }

        synchronized (lock) {
            started = true;
            lock.notifyAll();
        }
    }


    /**
     * Sets up background threads/timers. If delayStartup is true this is done after the app is running - otherwise done
     * when bean is first constructed.
     */
    private void initBackgroundResources() {
        // Initialize the task timer and make it a deamon.
        taskTimer = new java.util.Timer(true);

        // Use 5 worker threads by default.
        workers = new TaskEngineWorker[5];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new TaskEngineWorker("Task Engine Worker " + i);
            workers[i].setDaemon(true);
            workers[i].start();
        }
    }


    /***
     *  Used to clean-up the threads, so they may be re-initialized
     */
    private void stopAllWorkers() {
        // De-start the thread pool
        synchronized(lock) {
            started = false;

            if (workers != null) {
                // Stop all the workers
                for (TaskEngine.TaskEngineWorker worker : workers) {
                    worker.stopWorker();
                }

                // Give them a chance to finish
                lock.notifyAll();
                workers = null;
            }
        }
    }

    /**
     * Returns the current size of the task queue, which is the number of
     * work items waiting to be processed.
     *
     * @return the current number of items on the queue.
     */
    public int size() {
        synchronized(lock) {
            return taskQueue.size();
        }
    }

    /**
     * Returns the number of worker threads.
     *
     * @return the number of worker threads.
     */
    public int getNumWorkers() {
        return workers != null ? workers.length : 0;
    }

    /**
     * Adds a task to the task queue. The task will be executed immediately
     * provided there is a free worker thread to execute it. Otherwise, it
     * will execute as soon as a worker thread becomes available.<p>
     *
     * @param task the task to execute
     */
    public void addTask(Runnable task) {
        addTask(MEDIUM_PRIORITY, task);
    }

    /**
     * Adds a task to the task queue. The task will be executed immediately
     * provided there is a free worker thread to execute it. Otherwise, it
     * will execute as soon as a worker thread becomes available.<p>
     *
     * @param priority the priority of the task in the queue.
     * @param task the task to execute
     */
    public void addTask(int priority, Runnable task) {
        if (mockMode) {
            executeMockModeTask(task);
            return;
        }

        synchronized(lock) {
            if ( Application.isInitialized() && workers != null) {
            // Now, grow or shrink the worker pool as necessary.
            if (taskQueue.size() > Math.ceil(workers.length/2)) {
                // Update the busy timestamp.
                busyTimestamp = System.currentTimeMillis();
                // Attempt to add another worker to handle the load.
                addWorker();
            }
            else if (workers.length > 3) {
                // Attempt to remove a worker.
                removeWorker();
            }

            TaskWrapper wrapper = new TaskWrapper(priority, task);
            taskQueue.offer(wrapper);
            // Notify a worker thread of the enqueue.
            lock.notify();
        }
            else {
                TaskWrapper wrapper = new TaskWrapper(priority, task);
                taskQueue.offer(wrapper);
    }
        }
    }

    /**
     * Schedules a task to be run once after a specified delay.
     *
     * @param task task to be scheduled.
     * @param date the date in milliseconds at which the task is to be executed.
     * @return a TimerTask object which can be used to track execution of the
     *      task.
     */
    public TimerTask scheduleTask(Runnable task, Date date) {
        return scheduleTask(MEDIUM_PRIORITY, task, date);
    }

    /**
     * Schedules a task to be run once after a specified delay.
     *
     * @param priority the priority of the task in the queue.
     * @param task task to be scheduled.
     * @param date the date in milliseconds at which the task is to be executed.
     * @return a TimerTask object which can be used to track execution of the
     *      task.
     */
    public TimerTask scheduleTask(int priority, Runnable task, Date date) {
        ScheduledTask timerTask = new ScheduledTask(this, priority, task, true);
        if ( taskTimer == null ) {
            startupScheduledTaskList.add(new ScheduledTaskWrapper(timerTask, date, 0, 0));
        }
        else {
            taskTimer.schedule(timerTask, date);
        }
        return timerTask;
    }

    /**
     * Schedules a task to periodically run. This is useful for tasks such as
     * updating search indexes, deleting old data at periodic intervals, etc.
     *
     * @param task task to be scheduled.
     * @param delay delay in milliseconds before task is to be executed.
     * @param period time in milliseconds between successive task executions.
     * @return a TimerTask object which can be used to track executions of the
     *      task and to cancel subsequent executions.
     */
    public TimerTask scheduleTask(Runnable task, long delay, long period) {
        return scheduleTask(MEDIUM_PRIORITY, task, delay, period, false);
    }

    /**
     * Schedules a task to periodically run. This is useful for tasks such as
     * updating search indexes, deleting old data at periodic intervals, etc.
     *
     * @param priority the priority of the task in the queue.
     * @param task task to be scheduled.
     * @param delay delay in milliseconds before task is to be executed.
     * @param period time in milliseconds between successive task executions.
     * @param allowMultiple if true, long running and frequent tasks will result in multiple concurrent calls to
     * the supplied Runnable.
     * @return a TimerTask object which can be used to track executions of the
     *      task and to cancel subsequent executions.
     */
    public TimerTask scheduleTask(int priority, Runnable task, long delay, long period, boolean allowMultiple) {
        ScheduledTask timerTask = new ScheduledTask(this, priority, task, allowMultiple);
        if ( taskTimer == null ) {
            startupScheduledTaskList.add(new ScheduledTaskWrapper(timerTask, null, delay, period));
        }
        else {
            taskTimer.scheduleAtFixedRate(timerTask, delay, period);
        }

        return timerTask;
    }

    /**
     * Shuts down the task engine service. This method is for internal use only.
     */
    public void destroy() {
        if ( taskTimer != null ) {
        taskTimer.cancel();
        }
        stopAllWorkers();
    }

    /**
     * Adds a new worker to handle load. New workers are added at most once every two seconds
     * and only up to thirty workers.
     */
    private void addWorker() {
        // Only add a new worker if it's been at least 2 seconds since the last time.
        if (workers.length < 30 && System.currentTimeMillis() > newWorkerTimestamp + 2000) {
            int newSize = workers.length + 1;
            int lastIndex = newSize-1;
            TaskEngineWorker[] newWorkers = new TaskEngineWorker[newSize];
            System.arraycopy(workers, 0, newWorkers, 0, workers.length);
            newWorkers[lastIndex] = new TaskEngineWorker("Task Engine Worker " + lastIndex);
            newWorkers[lastIndex].setDaemon(true);
            newWorkers[lastIndex].start();
            // Finally, switch in new data structure.
            workers = newWorkers;
            newWorkerTimestamp = System.currentTimeMillis();
        }
    }

    /**
     * Removes a worker if load is lower than the necessary number of workers. Workers are removed
     * at once every five seconds, down to a minimum of three workers.
     */
    private void removeWorker() {
        // Only remove a worker if at least 5 seconds have passed since we were last busy.
        if (workers.length > 3 && System.currentTimeMillis() > busyTimestamp + 1000 * 5) {
            // First, stop the last worker.
            workers[workers.length-1].stopWorker();
            // Create a new worker array one elment smaller than the current one.
            int newSize = workers.length-1;
            TaskEngineWorker[] newWorkers = new TaskEngineWorker[newSize];
            // Copy in elements up to newSize.
            System.arraycopy(workers, 0, newWorkers, 0, newSize);
            workers = newWorkers;
            // Update the busy timestamp so that another worker won't be removed for a bit.
            busyTimestamp = System.currentTimeMillis();
        }
    }

    public void addLowPriorityTask(Runnable task) {
        addTask(LOW_PRIORITY, task);
    }

    public void addMediumPriorityTask(Runnable task) {
        addTask(MEDIUM_PRIORITY, task);
    }

    public void addHighPriorityTask(Runnable task) {
        addTask(HIGH_PRIORITY, task);
    }

    /**
     * A worker thread class which executes <code>Task</code> objects.
     */
    private class TaskEngineWorker extends Thread {

        private boolean done = false;

        TaskEngineWorker(String name) {
            super(threadGroup, name);
        }

        /**
         * Stops the worker.
         */
        public void stopWorker() {
            done = true;
        }

        /**
         * Return the next task in the queue. If no task is available, this method
         * will block until a task is added to the queue or the worker is "done"
         *
         * @return a <code>Task</code> object
         */
        private TaskWrapper nextTask() {
            TaskWrapper wrapper = null;
            synchronized(lock) {
                // Block until we have another object in the queue to execute.
                while (!done && (taskQueue.isEmpty() || !started)) {
                    try {
                        lock.wait();
                    }
                    catch (InterruptedException ie) {
                        //propogate notify in case of interruption/notification race
                        lock.notify();
                    }
                }

                //only drain queue if app is initialized
                if (!taskQueue.isEmpty() && Application.isContextInitialized()){
                    wrapper = taskQueue.poll();
                }
            }

            return wrapper;
        }

        /**
         * Get tasks from the task engine. The call to get another task will
         * block until there is an available task to execute.
         */
        public void run() {
            while (!done) {
                int currentThreadPriority = this.getPriority();
                int newThreadPriority = currentThreadPriority;

                try {
                    TaskWrapper wrapper = nextTask();
                    if (wrapper != null) {
                        int desiredTaskPriority = wrapper.getPriority();
                        newThreadPriority = (desiredTaskPriority == HIGH_PRIORITY ? 9 :
                                (desiredTaskPriority == MEDIUM_PRIORITY ? 5 : 2));

                        // run the task at a thread priority appropriate to it's priority
                        if (newThreadPriority != currentThreadPriority) {
                            try {
                                log.finest("Running task engine worker (" + wrapper.getTask().getClass() +
                                        ") at thread priority " + newThreadPriority);
                                this.setPriority(newThreadPriority);
                            }
                            catch (Exception e) {
                                log.log(Level.SEVERE, e.getMessage(),  e);
                            }
                        }

                        log.finest("Executing task (" + wrapper.getTask().getClass() + ")");

//                        SecurityContext sc = SecurityContextHolder.getContext();
//                        if(sc instanceof JiveSecurityContext) {
//                            JiveSecurityContext jsc = (JiveSecurityContext)SecurityContextHolder.getContext();
//                            jsc.setAuthentication(new SystemAuthentication(), true);
//                        }
//                        else {
//                            sc.setAuthentication(new SystemAuthentication());
//                        }
                        wrapper.getTask().run();
                        log.finest("Completed execution task (" + wrapper.getTask().getClass() + ")");

                        if (newThreadPriority != currentThreadPriority) {
                            try {
                                log.finest("Restoring task engine worker thread to thread priority - " +
                                        currentThreadPriority);
                                this.setPriority(currentThreadPriority);
                            }
                            catch (Exception e) {
                                log.log(Level.SEVERE, e.getMessage(), e);
                            }
                        }
                    }
                }
                catch (Exception e) {
                    log.log(Level.SEVERE, e.getMessage(), e);
                    if (newThreadPriority != currentThreadPriority) {
                        try {
                            log.finest("Restoring task engine worker thread to thread priority - " +
                                    currentThreadPriority);
                            this.setPriority(currentThreadPriority);
                        }
                        catch (Exception e2) {
                            log.log(Level.SEVERE, e2.getMessage(), e);
                        }
                    }
                }
            }
        }
    }

    /**
     * A subclass of TimerClass that passes along a Runnable to the task engine
     * when the scheduled task is run.
     */
    private static class ScheduledTask extends TimerTask {

        private TaskEngine engine;
        private int priority;
        private Runnable task;

        ScheduledTask(TaskEngine engine, int priority, Runnable task, boolean allowConcurrentTasks) {
            this.engine = engine;
            this.priority = priority;
            if (allowConcurrentTasks) {
                this.task = task;
            }
            else {
                this.task = new GuardedRunnable(task);
            }
        }

        public void run() {

            if (task instanceof GuardedRunnable) {
                if (shouldCancelTask(((GuardedRunnable)task).getRunnable())) {
                    cancel();
                    return;
                }
            }
            else {
                if (shouldCancelTask(task)){
                    cancel();
                    return;
                }
            }

            // Put the task into the queue to be run as soon as possible by a
            // worker.
            engine.addTask(priority, task);
        }

        private boolean shouldCancelTask(Runnable scheduledTask) {
            if (scheduledTask instanceof CancellableTask) {
               CancellableTask cancellable = (CancellableTask) scheduledTask;
               if (cancellable.shouldCancel()) {
                   return true;
               }
            }
            return false;
        }
    }

    /**
     * Decorator for a Runnable which no ops any calls to run if it is currently running.
     */
    private static class GuardedRunnable implements Runnable {
        private Runnable runnable;
        private volatile boolean running;

        GuardedRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        public void run() {
            if (!running) {
                try {
                    running = true;
                    runnable.run();
                }
                finally {
                    running = false;
                }
            }
        }

        public String toString() {
            return runnable.toString();
        }

        public Runnable getRunnable() {
            return runnable;
        }
    }

    private void executeMockModeTask(final Runnable task) {
        // In mockMode, set the security context to SystemAuthentication as this reflects the behavior
        // of taskEngine.run().  SudoExecutor switches back to the calling context.
        if (Application.isInitialized()) {
            // execute the thread as the System authentication
            Callable<Object> c = new Callable<Object>() {
                public Object call() throws Exception {
                    task.run();
                    return null;
                }
            };

//            SudoExecutor se = new SudoExecutor(Application.getEffectiveContext().getAuthenticationProvider(),
//                    new SystemAuthentication());
//            try {
//                log.log(Level.INFO, "Executing task using SystemAuthentication");
//                se.executeCallable(c);
//            }
//            catch (Exception e) {
//                log.log(Level.SEVERE, "Cannot execute mockMode task as system", e);
//            }
            
            //Ìæ»»ÎªÒÔÏÂ
			try {
			    log.log(Level.INFO, "Executing task using SystemAuthentication");
			    c.call();
			}
			catch (Exception e) {
			    log.log(Level.SEVERE, "Cannot execute mockMode task as system", e);
			}
        }
        else {
            log.log(Level.INFO, "JiveApplication is not initialized, executing task using current Authentication");
            task.run();
        }
    }
}