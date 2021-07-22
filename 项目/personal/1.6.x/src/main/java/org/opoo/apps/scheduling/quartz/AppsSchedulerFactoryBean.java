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
		//����ʱ�������ó� false������ ��ʱ������������Ĳ���
		boolean autoStartup = AppsGlobals.getSetupProperty("scheduler.autoStartup", true);
		super.setAutoStartup(autoStartup);
		super.afterPropertiesSet();
	}

}
