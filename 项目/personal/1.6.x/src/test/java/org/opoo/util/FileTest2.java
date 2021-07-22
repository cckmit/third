package org.opoo.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.ShutdownHookProcessDestroyer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.jivesoftware.office.command.BashCommandExecutorResultHandler;

public class FileTest2 {

	@Test
	public void test() {
		File file = new File("\\\\t400\\test");
		System.out.println(file.exists());
		System.out.println(file);
		
		file = new File("//t400/test");
		System.out.println(file.exists());
		System.out.println(file);
	}
	
	static String error;
	
	@Test
	public void test3(){
		BashCommandWorker worker = new BashCommandWorker("dirc", 120000, "net use", Maps.newHashMap(), null, 0);
		worker.run();
		System.out.println("Error===> " + worker.getError());
	}
	
	
//	@Test
	public void test2(){
		HashMap<Object,Object> map = Maps.newHashMap();
		map.put("ip", "127.0.0.1");
//		String line = "ping ${ip} ";
//		CommandLine commandLine = CommandLine.parse(line, map);
//		DefaultExecutor executor = new DefaultExecutor();
//		executor.setExitValue(1);
//		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
//		executor.setWatchdog(watchdog);
//		int exitValue = executor.execute(commandLine);
//		System.out.println(exitValue);
		
//		final Log logger = LogFactory.getLog(getClass());
		final Logger logger = Logger.getLogger("org.opoo");
		
		String command = "net use";
		int timelimit = 2500000;
		int successExitValue = 0;
		try {
			
			 CommandLine cmdLine = CommandLine.parse(command, map);
			 BashCommandExecutorResultHandler resultHandler = new BashCommandExecutorResultHandler();
			 ExecuteWatchdog watchdog = new ExecuteWatchdog(timelimit);
			 DefaultExecutor executor = new DefaultExecutor();
			 executor.setExitValue(successExitValue);
			 LogOutputStream outputStream = new LogOutputStream() {
			     protected void processLine(String line, int level)
			     {
			         logger.info(String.format("Executor: %s", new Object[] { line
			         }));
			         System.out.println(line);
			     }
			 };
			 
			 LogOutputStream errorStream = new LogOutputStream() {
			     protected void processLine(String line, int level)
			     {
			         error = error != null ? (new StringBuilder()).append(error).append(" ").append(line).toString() : line;
			         logger.error(String.format("Executor logged an error: %s", new Object[] {line}));
			         System.err.println(line);
			     }
			 };
			 
			 executor.setStreamHandler(new PumpStreamHandler(outputStream, errorStream));
			 executor.setProcessDestroyer(new ShutdownHookProcessDestroyer());
			 executor.setWatchdog(watchdog);
			 executor.execute(cmdLine, resultHandler);
			 resultHandler.waitFor(timelimit);
		}catch(InterruptedException ie)
        {
            error = String.format("Time allocated for the bash command %s worker expired %d", new Object[] {
                command, Long.valueOf(timelimit)
            });
        }
        catch(Exception e)
        {
            error = String.format("Running the bash command %s ended with error %s", new Object[] {
                command, e.getMessage()
            });
            e.printStackTrace();
        }
        if(error != null)
            logger.error(error);
	}

}
