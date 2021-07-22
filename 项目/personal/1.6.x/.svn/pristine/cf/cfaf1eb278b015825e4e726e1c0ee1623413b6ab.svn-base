package org.opoo.util.task;

import java.io.Serializable;
import java.util.*;

/**
 * An abstract implementation of the {@link PollableRunnable} interface. This preconfigures a 0->100
 * determinate progress meter and has a simple implementation of the {@link #run()} method. <p>
 *
 * Developers need only to override the {@link #getTaskValue()} method and the {@link #doRun()} method.<p>
 */
public abstract class AbstractPollableRunnable implements PollableRunnable, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4731279162101101403L;
	private boolean running = false;
    private Date startDate;
    private Date endDate;

    public boolean isRunning() {
        return running;
    }

    public int getTaskMinimum() {
        return 0;
    }

    public int getTaskMaximum() {
        return 100;
    }

    public abstract int getTaskValue();

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getPercentComplete() {
        if (isIndeterminate()) {
            return -1.0;
        }
        else {
            int val = getTaskValue();
            int min = getTaskMinimum();
            int max = getTaskMaximum();

            if (val < min) {
                val = min;
            }
            if (val > max) {
                val = max;
            }
            int delta = max - min;
            if (delta == 0) {
                return 0;
            }
            double percent = 100.0*((double)(val-min)/(double)delta);
            return percent;
        }
    }

    public boolean isIndeterminate() {
        return false;
    }

    public boolean isFinished() {
        return (!running && getEndDate() != null);
    }

    public boolean isSuccess() {
        return true;
    }

    public List<Exception> getFailures() {
        return Collections.emptyList();
    }

    /**
     * Not implemented in this implementation.
     */
    public void cancel() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    /**
     * A simple implementation of the {@link java.lang.Runnable#run()} method which sets this
     * task's running status to <tt>true</tt> then calls the abstract method {@link #doRun()}. When
     * <tt>doRun()</tt> returns it will set the running status to <tt>false</tt> then call the
     * {@link #isFinished()} callback. <p>
     *
     * Note, this method is final so it cannot be subclassed - it will call doRun() which
     * is abstract and must be implemented by subclasses.
     */
    public final void run() {
        running = true;
        startDate = new Date();
        try {
            doRun();
        }
        finally {
            running = false;
            endDate = new Date();
        }
    }

    /**
     * Executes the logic of the task. Errors should be handled by the implementing class as this
     * method does not throw any exceptions.
     */
    public abstract void doRun();
}
