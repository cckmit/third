/*
 * $Id$
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.config;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class RFSPropertyNamespaceHandlerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
		System.setProperty("test.bean.rfsp", "AAAAAAAAAAAAAAAAA");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-custom-handler.xml");
		TestBean bean = (TestBean) context.getBean("testBean");
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
	}
	
	//@Test
	public void test3() throws SchedulerException{
		//SchedulerFactoryBean bean = new SchedulerFactoryBean();
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler(); 
		
		 JobDetail jobDetail = new JobDetail("myJob", // job name  
				 Scheduler.DEFAULT_GROUP, // job group (you can also specify 'null' to  
                 // use the default group)  
                 TestJob.class); // the java class to execute  

//		jobDetail.getJobDataMap().put("jobSays", "Hello World!");  
//		jobDetail.getJobDataMap().put("myFloatValue", 3.141f);  
//		jobDetail.getJobDataMap().put("myStateData", new ArrayList());  
		
		Trigger trigger = TriggerUtils.makeDailyTrigger(0, 1);  
		trigger.setStartTime(new Date());  
		trigger.setName("myTrigger"); 
		//trigger.setGroup("")
		
		//Add the given JobDetail to the Scheduler,   
		//and associate the given Trigger with it.  
		scheduler.scheduleJob(jobDetail, trigger);    
		//scheduler.start();
		
		List<?> jobs = scheduler.getCurrentlyExecutingJobs();
		System.out.println(jobs);
		
		Trigger trigger2 = scheduler.getTrigger("myTrigger", Scheduler.DEFAULT_GROUP);
		System.out.println(trigger2.getJobName());
		System.out.println(trigger2.getFinalFireTime());
		System.out.println(trigger2.getNextFireTime());
		System.out.println(trigger2.getStartTime());
		trigger2.getEndTime();
		
		
		String[] triggerGroupNames = scheduler.getTriggerGroupNames();
		for(String triggerGroupName: triggerGroupNames){
			String[] jobNames = scheduler.getJobNames(triggerGroupName);
			for(String jobName: jobNames){
				System.out.println(triggerGroupName + "->" + jobName);
			}
			String[] triggerNames = scheduler.getTriggerNames(triggerGroupName);
			for(String triggerName: triggerNames){
				System.out.println(triggerGroupName + "->" + triggerName);
				Trigger tr = scheduler.getTrigger(triggerName, triggerGroupName);
				System.out.println(tr.getClass());
				if(tr instanceof CronTrigger){
					CronTrigger ct = (CronTrigger) tr;
					System.out.println(ct.getName() + ":" + ct.getCronExpression());
					System.out.println(ct.getExpressionSummary());
//					ct.setCronExpression(cronExpression)
//					scheduler.rescheduleJob(triggerName, groupName, newTrigger);
				}
				System.out.println(tr.getDescription());
				System.out.println(tr.getFinalFireTime());
				System.out.println(tr.getNextFireTime());
				System.out.println(tr.getStartTime());
				System.out.println(tr.getFullJobName());
				int state = scheduler.getTriggerState(triggerName, triggerGroupName);
				System.out.println(state);
				tr.getPreviousFireTime();
				tr.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
				tr.setStartTime(new Date());
			}
		}
	}
	
	class Task{
		public String triggerName;
		public String triggerGroupName;
		public String jobName;
	}

}
