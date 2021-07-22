package org.opoo.util.task;

import java.util.Date;
import java.util.List;


public interface Pollable {

    /**
     * Returns <tt>true</tt> if the task is running, <tt>false</tt> otherwise.
     *
     * @return <tt>true</tt> if the task is running, <tt>false</tt> otherwise.
     */
    boolean isRunning();

    /**
     * Returns the minimum value of task progress (default is 0). If this is in
     * {@link #isIndeterminate() indeterminate} mode then this will return -1.
     *
     * @return the minimum value of task progress.
     */
    int getTaskMinimum();

    /**
     * Returns the maximum value of task progress (default is 100). If this is in
     * {@link #isIndeterminate() indeterminate} mode then this will return -1.
     *
     * @return the maximum value of task progress.
     */
    int getTaskMaximum();

    /**
     * Returns the current value of the task's progress. This will be x where
     * min <= x <= max. If this is in  {@link #isIndeterminate() indeterminate} mode then this will return -1.
     *
     * @return the current value of the task's progress.
     */
    int getTaskValue();

    /**
     * Returns the date the task was started.
     *
     * @return the date the task was started.
     */
    Date getStartDate();

    /**
     * Returns the date the task ended.
     *
     * @return the date the task ended.
     */
    Date getEndDate();

    /**
     * Returns the percent complete of the task as a double value between 0.0 and 100.0. If this is an
     * {@link #isIndeterminate() indeterminate} mode then this will return -1.
     *
     * @return the percent complete of the task.
     */
    double getPercentComplete();

    /**
     * Returns <tt>true</tt> if we are unable to determine the progress being made, <tt>false</tt> othewise.
     * This is useful to use when progress can't be determined but some indication of activity needs to be displayed.
     *
     * @return <tt>true</tt> if we are unable to determine the progress being made, <tt>false</tt> othewise.
     */
    boolean isIndeterminate();

    /**
     * A simple call back method which will be called when the task has completed.
     *
     * @return <tt>true</tt> if the task is finished, <tt>false</tt> otherwise.
     */
    boolean isFinished();

    /**
     * Returns true if the runnable completed successfully.
     *
     * @return true if the runnable completed successfully.
     */
    boolean isSuccess();

    /**
     * Returns a list of failures.
     *
     * @return a list of failures.
     */
    List<Exception> getFailures();
}
