package org.opoo.apps.lifecycle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.helpers.LogLog;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;
import org.opoo.apps.license.AppsLicenseManager;
import org.opoo.apps.log.Log4JConfigurator;
import org.springframework.web.context.ContextLoader;


/**
 * 系统启动引导程序。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @deprecated since 1.6.0, just backup.
 */
public class Bootstrap {
	private static Bootstrap instance = new Bootstrap();
	private static final Log log = LogFactory.getLog(Bootstrap.class.getName());
	private final List<String> subsystems = new ArrayList<String>(2);

	private CountDownLatch runLatch;
	private CountDownLatch startupLatch;

	public static void main(String args[]) {
		
		//System.setProperty("apps.spring.configLocation", "classpath:spring-application.xml");
		System.setProperty(LogFactory.DIAGNOSTICS_DEST_PROPERTY, "STDERR");
		LogLog.setInternalDebugging(true);
		LogLog.setQuietMode(false);
		
		
		
		Thread.currentThread().setName("AppsMain");
		Bootstrap bs = getInstance();
		try {
			bs.start();
			
			//System.out.println(Application.getContext().get("umModule", Object.class));
		} catch (Exception ex) {
			log.error("Failed to start Apps.", ex);
			System.exit(1);
		}
		CountDownLatch runLatch = bs.getRunLatch();
		try {
			runLatch.await();
			bs.stop();
		} catch (InterruptedException ie) {
			log.warn("Interrupted. Commencing explicit shutdown.", ie);
		} catch (Exception ex) {
			log.warn("Unhandled exception in main runtime.", ex);
		}
		log.warn("System shutdown complete.");
	}

	private Bootstrap() {
		startupLatch = new CountDownLatch(1);
	}

	public static synchronized Bootstrap getInstance() {
		return instance;
	}

	public boolean start() {
		return start(null, null);
	}

	public boolean start(ContextLoader contextLoader, ServletContext context) {
		long timer = System.currentTimeMillis();

		//System.setProperty("apps.logs", AppsHome.getAppsLogsPath());
		
		Log4JConfigurator.configureAppsLogsPath();
		AppsHome.initialize();

		log.info("Loading spring subsystem.");
		try {
			long springTimer = System.currentTimeMillis();
			if (context != null && contextLoader != null) {
				ApplicationBak.start(contextLoader, context);
			} else {
				ApplicationBak.start();
			}
			registerSubsystem("spring");
			log.info((new StringBuilder()).append("Spring subsystem initialization in ").append(
					System.currentTimeMillis() - springTimer).append("ms.").toString());
		} catch (Exception ex) {
			log.error("Unhandled exception durning spring initialization. The system is likely unstable.", ex);
			throw new RuntimeException(ex);
		}
		// log.debug("Loading messaging subsystem.");
		// broker = JmsBrokerUtils.getBrokerService();
		// if(broker != null)
		// registerSubsystem("messaging");
		boolean result = doPostInit(contextLoader);
		runLatch = new CountDownLatch(subsystems.size());
		log.info((new StringBuilder()).append("Apps started in ").append(System.currentTimeMillis() - timer).append(
				"ms.").toString());
		return result;
	}

	private boolean doPostInit(ContextLoader loader) {
		try {
			if(!AppsGlobals.isSetup() || AppsLicenseManager.getInstance().isFailed()){
				Application.setApplicationState(ApplicationState.INITIALIZED, ApplicationState.SETUP_STARTED);
			}else{
				Application.setApplicationState(ApplicationState.INITIALIZED, ApplicationState.RUNNING);
			}
		} catch (Exception ex) {
			log.error("Unhandled exception during Apps post initialization.", ex);
			return false;
		}
		return true;
	}

	public void stop() {
		if (runLatch == null) {
			return;
		}

		ApplicationBak.destroy();

		// ConfigurableApplicationContext context =
		// (ConfigurableApplicationContext) Application.getContext();
		// if (context != null && context.isActive()) {
		// try {
		// context.close();
		// } catch (Exception e) {
		// log.warn("Unhandled exception during spring context shutdown.", e);
		// }
		// }
		// if(broker != null && broker.isStarted())
		// try
		// {
		// broker.stop();
		// }
		// catch(Exception e)
		// {
		// log.warn("Unhandled exception during messaging shutdown.", e);
		// }
		for (int i = 0; i < subsystems.size(); i++)
			runLatch.countDown();
	}

	public boolean registerSubsystem(String name) {
		if (subsystems.contains(name)) {
			return false;
		} else {
			subsystems.add(name);
			return true;
		}
	}

	public boolean releaseSubsystem(String name) {
		if (subsystems.contains(name)) {
			subsystems.remove(name);
			if (runLatch != null)
				runLatch.countDown();
			return true;
		} else {
			return false;
		}
	}

	public CountDownLatch getStartupLatch() {
		return startupLatch;
	}

	public List<String> getSubsystems() {
		return subsystems;
	}

	public CountDownLatch getRunLatch() {
		return runLatch;
	}
}
