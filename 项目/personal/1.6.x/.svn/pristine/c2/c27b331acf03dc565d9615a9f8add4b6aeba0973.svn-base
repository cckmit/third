package org.opoo.apps.cache;

import java.io.Serializable;

import com.tangosol.net.PriorityTask;


/**
 * 集群任务。
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public abstract class AbstractClusterTask implements ClusterTask, Runnable, Serializable, PriorityTask {
	private static final long serialVersionUID = 3828200874175855057L;

	public AbstractClusterTask() {
	}

	public Object getResult() {
		return null;
	}

	public void run() {
		execute();
	}

	public int getSchedulingPriority() {
		return 0;
	}

	public long getExecutionTimeoutMillis() {
		return 0L;
	}

	public long getRequestTimeoutMillis() {
		return 0L;
	}

	public void runCanceled(boolean flag) {
	}
}