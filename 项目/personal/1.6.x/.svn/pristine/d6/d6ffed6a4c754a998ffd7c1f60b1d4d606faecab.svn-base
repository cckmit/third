package org.opoo.util.task;

/**
 */
public class TaskWrapper implements Comparable<TaskWrapper> {
    private Runnable task;
    private int priority;

    public TaskWrapper(int priority, Runnable task) {
        this.priority = priority;
        this.task = task;
    }

    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int compareTo(TaskWrapper o) {
//        if(o instanceof TaskWrapper){
            TaskWrapper other = (TaskWrapper) o;
            if(other.priority == this.priority){
                return 0;
            }
            return this.priority > other.priority ? 1:-1;
//        }
//        return 1;
    }
}
