package org.opoo.apps.log;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.Layout;
import org.apache.log4j.RollingFileAppender;
import org.opoo.apps.AppsHome;


/**
 * 滚动的日志 Appender。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated since 1.5, using Log4JConfigurator
 */
public class Log4JRollingFileAppender extends RollingFileAppender {
	//private static final Logger log = Logger.getLogger(Log4JRollingFileAppender.class.getName());
	public Log4JRollingFileAppender() {
		super();
		//System.out.println("construct Log4JRollingFileAppender: ");
	}

	public Log4JRollingFileAppender(Layout layout, String filename,
			boolean append) throws IOException {
		super(layout, filename, append);
		//System.out.println("construct Log4JRollingFileAppender(Layout layout, String filename, boolean append): " + filename);
	}

	public Log4JRollingFileAppender(Layout layout, String filename)	throws IOException {
		super(layout, filename);
		//System.out.println("construct Log4JRollingFileAppender(Layout layout, String filename): " + filename);
	}

	/* (non-Javadoc)
	 * @see org.apache.log4j.RollingFileAppender#setFile(java.lang.String, boolean, boolean, int)
	 */
	@Override
	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize) throws IOException {
		fileName = new File(AppsHome.getAppsLogs(), fileName).getAbsolutePath();
		super.setFile(fileName, append, bufferedIO, bufferSize);
	}

	/* (non-Javadoc)
	 * @see org.apache.log4j.FileAppender#setFile(java.lang.String)
	 */
	@Override
	public void setFile(String file) {
		//System.out.println("LOG4J.setFile: " + file);
		super.setFile(file);
	}

	/* (non-Javadoc)
	 * @see org.apache.log4j.WriterAppender#setWriter(java.io.Writer)
	 */
	@Override
	public synchronized void setWriter(Writer writer) {
		//System.out.println("LOG4J.setWriter: " + writer);
		super.setWriter(writer);
	}

//	public static void main(String[] args){
//		File file = new File(Globals.getWorkHome() + File.separator + "logs", "ts.logs");
//		String fileName = file.getAbsolutePath();
//		//System.out.println(fileName);
//	}
}
