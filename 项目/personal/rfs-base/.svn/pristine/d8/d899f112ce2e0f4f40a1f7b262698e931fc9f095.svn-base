package cn.redflagsoft.base.scheduling;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job {
	private static Log log = LogFactory.getLog(HelloJob.class);
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// Say Hello to the World and display the date/time
        log.info("Hello World! - " + new Date());
	}

}
