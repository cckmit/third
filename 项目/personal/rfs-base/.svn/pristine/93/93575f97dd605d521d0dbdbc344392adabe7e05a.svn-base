package cn.redflagsoft.base.scheduling;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.scheduling.quartz.SingleNodeQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.redflagsoft.base.service.TaskService;

/**
 * 定义计算task的timeUsed等。
 *
 */
public class TaskQuartzJob extends SingleNodeQuartzJobBean {
	public static final String TASK_JOB_ENBALED = "TaskQuartzJob.enabled";
	private static final Log log = LogFactory.getLog(TaskQuartzJob.class);
	private TaskService taskService;

	/**
	 * @return the taskService
	 */
	public TaskService getTaskService() {
		return taskService;
	}

	/**
	 * @param taskService the taskService to set
	 */
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	protected void executeInSeniorClusterMember(JobExecutionContext arg0) throws JobExecutionException {
		if(Application.isInitialized() && Application.isContextInitialized() && AppsGlobals.isSetup()){
			if(!AppsGlobals.getProperty(TASK_JOB_ENBALED, true)){
				log.debug("TaskQuartzJob 未启用，配置运行时属性 " + TASK_JOB_ENBALED + "=true 启用该Job。");
				return;
			}
			log.debug("执行Job：" + this);
//			int count = taskService.calculateAllRunningTasksTimeUsed(null);
			int count = taskService.calculateAllRunningTasksTimeUsedInThreads(null);
			log.debug("执行完毕：" + this + ", 更新task数量：" + count);
		}
	}
}
