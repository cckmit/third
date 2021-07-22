package org.opoo.util.task;


/**
 * A simple extension of the {@link java.lang.Runnable} class which adds the ability for a runnable
 * to be monitored much like a progress meter. In fact, the design of the methods is borrowed from
 * the JProgressBar class.
 */
public interface PollableRunnable extends Runnable, Pollable {

    /**
     * A method to cancel execution of the task. Note, some implementations might not support this. In
     * that case an {@link java.lang.UnsupportedOperationException} should be thrown.
     */
    void cancel();
}
