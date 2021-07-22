package org.opoo.apps.scheduling.quartz;

import org.opoo.apps.AppsGlobals;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


/**
 * Apps SchedulerFactoryBean
 * @author Alex Lin(alex@opoo.org)
 */
public class AppsSchedulerFactoryBean extends SchedulerFactoryBean {
	@Override
	public void afterPropertiesSet() throws Exception {
		//开发时可以设置成 false，避免 定时任务干扰正常的测试
		boolean autoStartup = AppsGlobals.getSetupProperty("scheduler.autoStartup", true);
		super.setAutoStartup(autoStartup);
		super.afterPropertiesSet();
	}

}
