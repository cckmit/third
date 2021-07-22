package org.opoo.apps.log;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Layout;
import org.opoo.apps.AppsHome;

/**
 * 日滚动的日志 Appender。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated since 1.5, using Log4JConfigurator
 */
public class Log4JDailyRollingFileAppender extends DailyRollingFileAppender {
	//private static final Logger log = Logger.getLogger(Log4JDailyRollingFileAppender.class.getName());
	
	public Log4JDailyRollingFileAppender() {
		super();
	}

	public Log4JDailyRollingFileAppender(Layout layout, String filename,
			String datePattern) throws IOException {
		super(layout, filename, datePattern);
	}

	/* (non-Javadoc)
	 * @see org.apache.log4j.FileAppender#setFile(java.lang.String, boolean, boolean, int)
	 */
	@Override
	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize) throws IOException {
		fileName = new File(AppsHome.getAppsLogs(), fileName).getAbsolutePath();
		super.setFile(fileName, append, bufferedIO, bufferSize);
	}

}
