package org.opoo.apps;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.servlet.ServletContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.opoo.apps.license.AppsLicenseManager;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.lifecycle.ApplicationState;
import org.opoo.apps.lifecycle.spring.AppsContextLoader;
import org.opoo.apps.log.Log4JConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Main entry point for running instances of the Apps system.
 */
public class Apps {
	 //initialized by the startup sequence
    static Logger log;

    private CountDownLatch runLatch;
    private CountDownLatch startupLatch = new CountDownLatch(1);
    private final List<String> subsystems = new ArrayList<String>(2);

    private ConfigurableApplicationContext context;
//    private BrokerService broker;
    private AppsContextLoader contextLoader;

    private static final Apps instance = new Apps();

    //Known Subsystems
    public static final String SPRING_SERVICE = "spring";
    public static final String MESSAGING_SERVICE = "messaging";
    public static final String WEB_SERVICE = "web";

    public static void main(String[] args) {
        Thread.currentThread().setName("AppsMain");

        final Apps cs = Apps.getInstance();

        try {
            cs.start(new AppsContextLoader());
        }
        catch(Exception ex) {
            final Logger log = LogManager.getLogger(Apps.class);
            log.error("Failed to start Apps.", ex);
            System.exit(1);
        }

        CountDownLatch runLatch = cs.getRunLatch();
        try {
            runLatch.await();
            cs.stop();
        }
        catch(InterruptedException ie) {
            log.warn("Interrupted. Commencing explicit shutdown.", ie);
        }
        catch(Exception ex) {
            log.warn("Unhandled exception in main runtime.", ex);
        }

        log.warn("System shutdown complete.");
    }

    private Apps() {
        //instances of this class should not be created
    }

    /**
     * Singleton accessor method.
     * @return
     */
    public static Apps getInstance() {
        return instance;
    }

    /**
     * Starts a stand-alone instance of Apps including all required subsystems.
     *
     * @return true of the startup was successful
     */
    public boolean start(AppsContextLoader contextLoader) {
        return start(contextLoader, null);
    }

    /**
     * Starts an instance of Apps including all required subsystems in the
     * context of a web application.
     * @param contextLoader
     * @param context
     * @return
     */
    public boolean start(AppsContextLoader contextLoader, ServletContext context) {
        final long timer = System.currentTimeMillis();
        this.contextLoader = contextLoader;

        //initialize logging
//        Log4JConfigurator.configureAppsLogsPath();
        
        //note that the following sequence is quite brittle, changing it will likely break
        //several things including setup - we need to init apps home, then logging, then
        //spring - both the webapp and stand-alone main() use this same sequence

        //initialize apps home
        AppsHome.initialize();

        //initialize logging
        Log4JConfigurator.configureAppsLogsPath();

        //get the logger after initialization
        log = LogManager.getLogger(Apps.class);

        log.debug("Loading spring subsystem.");
        try {
            final long springTimer = System.currentTimeMillis();
            Application.setContextLoader(contextLoader);
            if(context == null ) {
            	Application.initialize();
            } else {
            	Application.initialize(context);
            }

            this.context = (ConfigurableApplicationContext)Application.getContext();
            registerSubsystem(SPRING_SERVICE);
            log.info("Spring subsystem initialization in " + (System.currentTimeMillis() - springTimer) + "ms.");

        }
        catch(Exception ex) {
            log.warn("Unhandled exception durning spring initialization. The system is likely unstable.", ex);
            throw new RuntimeException(ex);
        }

        //Relying on spring to instantiate the broker service.
        //Relying on transition to running state to start the broker
        //Relying on spring to stop the broker service
//        log.debug("Loading messaging subsystem.");
//        this.broker = JmsBrokerUtils.getBrokerService();
//        if (this.broker != null) {
//            registerSubsystem(MESSAGING_SERVICE);
//        }


        boolean result = doPostInit(this.contextLoader);
        runLatch = new CountDownLatch(this.subsystems.size());
        log.info("Apps started in " + (System.currentTimeMillis() - timer) + "ms.");
        return result;
    }
    private boolean doPostInit(AppsContextLoader loader) {
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
    
//    private boolean doPostInit(AppsContextLoader loader) {
//        final Logger log = LogManager.getLogger(Apps.class);
//        try {
//            if(loader.doSetup()) {
//                Application.setApplicationState(ApplicationState.INITIALIZED,
//                        ApplicationState.SETUP_STARTED);
//            }
//            else {
//                //check to see if we need to upgrade
//                try {
//                    UpgradeManager um = JiveApplication.getContext().getUpgradeManager();
//                    if (null == um) {
//                        log.warn("Unable to retrieve upgrade manager from Jive Application. The application is likely " +
//                                "in an unhealthy state.");
//                    }
//                    else {
//                        um.initialize();
//                        if (um.isUpgradeNeeded()) {
//                            JiveApplication.setApplicationState(ApplicationState.INITIALIZED,
//                                    ApplicationState.UPGRADE_NEEDED);
//                        }
//                        else {
//                            JiveApplication.setApplicationState(ApplicationState.INITIALIZED,
//                                    ApplicationState.RUNNING);
//                        }
//                    }
//                }
//                catch(Exception exc) {
//                    log.fatal("Unable to retrieve upgrade manager from Jive Application. " +
//                        "Application will remain in an uninitialized state.", exc);
//                }
//            }
//
//        }
//        catch(Exception ex) {
//            log.fatal("Unhandled exception during Apps post initialization.", ex);
//            return false;
//        }
//
//        return true;
//    }
    
    /**
     * Stop all Apps services and their dependent subsystems.
     */
    public void stop(){
    	stop(null);
    }
    /**
     * 
     * @param servletContext
     */
    public void stop(ServletContext servletContext) {
        if(runLatch == null) return;

        if(servletContext != null){
        	Application.destroy(servletContext);
        }else{
        	Application.destroy();
        }
        /*
        if (context != null && context.isActive()) {
            try {
                context.close();
            }
            catch (Exception e) {
                log.warn("Unhandled exception during spring context shutdown.", e);
            }
        }
        */

//        if (broker != null && broker.isStarted()) {
//            try {
//                broker.stop();
//            }
//            catch (Exception e) {
//                log.warn("Unhandled exception during messaging shutdown.", e);
//            }
//        }

        for(int i = 0; i < subsystems.size(); i++) {
            runLatch.countDown();
        }
    }

    /**
     * Registers a subsystem with the main runtime.
     * @param name
     * @return true if successful, false if the subsystem already exists
     */
    public boolean registerSubsystem(String name) {
        if(subsystems.contains(name)) {
            return false;
        }
        else {
            subsystems.add(name);
            return true;
        }
    }

    /**
     * Release a subsystem from the global registry.
     * @return true if successful, false if the subsystem does not exist
     */
    public boolean releaseSubsystem(String name) {
        if(subsystems.contains(name)) {
            subsystems.remove(name);
            if(runLatch != null) {
                runLatch.countDown();
            }
            return true;
        }
        return false;
    }

    /**
     * Exposes the startup latch for the main entry point. This latch
     * will release after all known subsystems have successfully
     * started up.
     * @return
     */
    public CountDownLatch getStartupLatch() {
        return startupLatch;
    }

    /**
     * Returns a list of all known subsystem names.
     * @return
     */
    public List<String> getSubsystems() {
        return subsystems;
    }

    /**
     * Returns a thread-safe latch that will be released when the application
     * exits the normal running state.
     * @return
     */
    public CountDownLatch getRunLatch() {
        return runLatch;
    }

    /**
     * Return the master context for the Apps instance.
     * @return
     */
    public ApplicationContext getContext() {
        return context;
    }
}
