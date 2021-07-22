package org.opoo.apps.cache;

import java.io.Serializable;


/**
 * 集群任务。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface ClusterTask extends Runnable, Serializable// , PriorityTask
{
	Object getResult();

	void execute();
}
