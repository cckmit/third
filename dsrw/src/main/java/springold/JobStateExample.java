package springold;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


public class JobStateExample
{
    public void run()
            throws Exception
    {
        System.out.println("------- ��ʼ�� -------------------");

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        System.out.println("------- ��ʼ����� --------");

        System.out.println("------- ��Scheduler����Job ----------------");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��");

        Date startTime = DateBuilder.nextGivenSecondDate(null, 10);

        JobDetail job1 = JobBuilder.newJob(SimpleJobQuartz.class).withIdentity("job1", "group1").build();

        SimpleTrigger trigger1 = (SimpleTrigger)TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(4))
                .build();

        job1.getJobDataMap().put("color", "Green");
        job1.getJobDataMap().put("count", 1);
        job1.getJobDataMap().put("total", 1);
        job1.getJobDataMap().put("sex", "boy");

        Date scheduleTime1 = sched.scheduleJob(job1, trigger1);
        System.out.println(job1.getKey() + "  ����:  " + dateFormat.format(scheduleTime1) + " ���У��ظ� " + trigger1.getRepeatCount() + " ��,ÿ " + trigger1.getRepeatInterval() / 1000L + " ��ִ��һ��");

        JobDetail job2 = JobBuilder.newJob(SimpleJobQuartz.class).withIdentity("job2", "group1").build();
        SimpleTrigger trigger2 = (SimpleTrigger)TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(4))
                .build();

        job2.getJobDataMap().put("color", "Red");
        job2.getJobDataMap().put("count", 1);
        job2.getJobDataMap().put("total", 1);
        job2.getJobDataMap().put("sex", "girl");

        Date scheduleTime2 = sched.scheduleJob(job2, trigger2);
        System.out.println(job2.getKey().toString() + "  ����:  " + dateFormat.format(scheduleTime2) + " ���У��ظ� " + trigger2.getRepeatCount() + " ��,ÿ " + trigger2.getRepeatInterval() / 1000L + " ��ִ��һ��");

        System.out.println("------- ��ʼScheduler ----------------");

        sched.start();

        System.out.println("------- Scheduler����job���� -----------------");

        System.out.println("------- �ȴ�60��... -------------");
        try
        {
            Thread.sleep(60000L);
        }
        catch (Exception e)
        {
        }

        System.out.println("------- �ر�Scheduler ---------------------");

        sched.shutdown(true);

        System.out.println("------- �ر���� -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        System.out.println("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public static void main(String[] args)
            throws Exception
    {
        JobStateExample example = new JobStateExample();
        example.run();
    }
}