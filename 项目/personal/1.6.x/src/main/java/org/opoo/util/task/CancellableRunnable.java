package org.opoo.util.task;

/**
 * Cancellable task.
 */
public abstract class CancellableRunnable implements Runnable {
    private boolean doTask = true;

    public void run() {
        if (doTask) {
            doTask();
        }
    }

    public abstract void doTask();

    public boolean isDoTask() {
        return doTask;
    }

    public void setDoTask(boolean doTask) {
        this.doTask = doTask;
    }
}
