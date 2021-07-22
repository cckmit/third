package org.opoo.util;

import java.io.File;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.ShutdownHookProcessDestroyer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jivesoftware.office.command.BashCommandExecutorResultHandler;

public class BashCommandWorker implements Runnable{
	private final String name;
	private final long timelimit;
	private final String command;
	private final Map substitutionMap;
	private final File workingDirectory;
	private final int successExitValue;
	private String error;

	public BashCommandWorker(String name, long timelimit, String command,
			Map substitutionMap, File workingDirectory, int successExitValue) {
		super();
		this.name = name;
		this.timelimit = timelimit;
		this.command = command;
		this.substitutionMap = substitutionMap;
		this.workingDirectory = workingDirectory;
		this.successExitValue = successExitValue;
	}

	public void run() {
		final Log logger = LogFactory.getLog("BashCommandWorker_" + name);
		try {
			CommandLine cmdLine = CommandLine.parse(command, substitutionMap);
			BashCommandExecutorResultHandler resultHandler = new BashCommandExecutorResultHandler();
			ExecuteWatchdog watchdog = new ExecuteWatchdog(timelimit);
			DefaultExecutor executor = new DefaultExecutor();
			if (workingDirectory != null) {
				executor.setWorkingDirectory(workingDirectory);
			}
			executor.setExitValue(successExitValue);
			LogOutputStream outputStream = new LogOutputStream() {
				protected void processLine(String line, int level) {
					String string = String.format("Executor %s: %s", name, line);
					logger.info(string);
					System.out.println(string);
				}
			};

			LogOutputStream errorStream = new LogOutputStream() {
				protected void processLine(String line, int level) {
					error = error != null ? (new StringBuilder()).append(error).append(" ").append(line).toString() : line;
					String string = String.format("Executor %s logged an error: %s", name, line);
					logger.error(string);
					System.err.println(string);
				}
			};

			executor.setStreamHandler(new PumpStreamHandler(outputStream, errorStream));
			executor.setProcessDestroyer(new ShutdownHookProcessDestroyer());
			executor.setWatchdog(watchdog);
			executor.execute(cmdLine, resultHandler);
			resultHandler.waitFor(timelimit);
			
			
			if (!resultHandler.hasResult()) {
				error = String
						.format("Running the bash command %s return could not finish in time allocated %d",
								command, timelimit);
			} else if (error == null) {
				if (resultHandler.getException() != null) {
					error = resultHandler.getException().getMessage();
				} else if (resultHandler.getExitValue() != successExitValue) {
					error = String
							.format("Running the bash command %s return a non success error code %d",
									command, resultHandler.getExitValue());
				} else {
					// if(!outputFile.exists() || outputFile.length() == 0L)
					// error =
					// String.format("Running the bash command %s jobID %s return a non success error code %d",
					// new Object[] {
					// command, jobID,
					// Integer.valueOf(resultHandler.getExitValue())
					// });
				}
			}
		} catch (InterruptedException ie) {
			error = String.format(
					"Time allocated for the bash command %s worker expired %d",
					command, timelimit);
		} catch (Exception e) {
			error = String.format(
					"Running the bash command %s ended with error %s", command,
					e.getMessage());
			e.printStackTrace();
		}

		if (error != null) {
			logger.error(error);
		}
	}

	public String getError() {
		return error;
	}
}
