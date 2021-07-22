package org.opoo.apps.lifecycle;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.opoo.apps.AppsContext;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.lifecycle.event.ApplicationStateChangeEvent;
import org.opoo.apps.lifecycle.spring.AppsContextLoader;
import org.opoo.apps.lifecycle.spring.SpringAppsContext;
import org.opoo.apps.module.ModuleManagerImpl;
import org.opoo.apps.security.GroupManager;
import org.opoo.apps.security.SecurityUtils;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.opoo.apps.security.UserManager;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;


/**
 * This class is one main entry point for the system. Almost all uses of this class will be concerned solely with the
 * {@link #getEffectiveContext()} method which is used to get an instance of the {@link AppsContext} interface. This
 * will return a context which will apply the permissions for the current authentication as received from {@link
 * SecurityContext#getAuthentication()}.
 * <p/>
 * Fine-grained pluggability of the user and group system is also available. See the {@link UserManager} and {@link
 * GroupManager} classes for more information.
 *
 * @javadoc api
 * @see SecurityContextHolder
 * @see SpringAppsContext
 */
public class Application {

	private static final Logger log = LogManager.getLogger(Application.class);

	private static ApplicationState state = ApplicationState.INITIALIZING;
	private static SpringAppsContext context;
	private static EventDispatcher dispatcher;
	private static AppsContextLoader contextLoader;

	// not instantiable
	private Application() {
		// no-op
	}

	public void setDispatcher(EventDispatcher d) {
		// this doesn't really work for a static class, but needed to implement
		// EventDispatcherAware
	}

	/**
	 * Specifically set the context loader to use, overwriting the apps/spring
	 * loader.
	 * 
	 * @param loader
	 */
	public static void setContextLoader(AppsContextLoader loader) {
		contextLoader = loader;
	}

	/**
	 * Returns <tt>true</tt> if the application is initialized, <tt>false</tt>
	 * otherwise.
	 * 
	 * @return true if the application is initialized, false otherwise.
	 */
	public static boolean isInitialized() {
		return (state == ApplicationState.RUNNING) || (state == ApplicationState.INITIALIZED);
	}

	/**
	 * Returns <tt>true</tt> if the Spring Context is initialized,
	 * <tt>false</tt> otherwise.
	 * 
	 * @return true if the application is initialized, false otherwise.
	 */
	public static boolean isContextInitialized() {
		return context != null && context.isActive();
	}

//	/**
//	 * Checks to see if the database is available.
//	 * 
//	 * @return true if a connection to the database is available, false
//	 *         otherwise.
//	 */
//	public static boolean isDatabaseAvailable() {
//		return StaticConnectionManager.testConnection();
//	}

	/**
	 * Initializes the application. Normal users of the API will not need to
	 * call this as this is a method called internally.
	 */
	public static void initialize() {
		initialize(null);
	}

	/**
	 * Initializes the web application. Normal users of the API will not need to
	 * call this as this is a method called internally. To get an instance of
	 * the application, call {@link #getEffectiveContext()}.
	 */
	public static synchronized void initialize(ServletContext servletContext) {
		if (contextLoader == null) {
			final String msg = "Invalid runtime state, the context loader has not been established.";
			log.fatal(msg);
			throw new IllegalStateException(msg);
		}

		if (state == ApplicationState.INITIALIZING) {
			Thread currentThread = Thread.currentThread();
			ClassLoader oldLoader = currentThread.getContextClassLoader();

			try {
				log.info("Opoo Apps Spring application life cycle initializing");
				if (AppsGlobals.isSetup()) {
					ModuleManagerImpl.getInstance().preInit();
				}

				if (servletContext != null) {
					// init Spring context - overridden finishRefresh method
					// will assign new context to
					// our internal context reference before spring app events
					// are fired
					contextLoader.initWebApplicationContext(servletContext);
				} else {
					context = new SpringAppsContext();
					List<String> contextFiles = contextLoader.getContextFiles();
					context.setConfigLocations(contextFiles.toArray(new String[contextFiles.size()]));
					context.refresh();
				}
			} catch (Throwable e) {
				Application.setApplicationState(ApplicationState.INITIALIZING, ApplicationState.ERROR);
				log.error(e.getMessage(), e);
				throw new RuntimeException(e);
			} finally {
				if (oldLoader != null) {
					currentThread.setContextClassLoader(oldLoader);
				}
			}
		} else {
			log.warn("NewApplication.initialize(): NewApplication is already initialized.");
		}
	}

	/**
	 * Destroys the application. Normal users of the API will not need to call
	 * this as this is a method called internally.
	 */
	public static synchronized void destroy() {
		try {
			Application.setApplicationState(state, ApplicationState.SHUTDOWN);
			if (context != null) {
				context.destroy();
			}
			dispatcher = null;
		} catch (Throwable e) {
			Application.setApplicationState(state, ApplicationState.ERROR);
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Destroys the web application. Normal users of the API will not need to
	 * call this as this is a method called internally.
	 */
	public static synchronized void destroy(ServletContext servletContext) {
		try {
			if (contextLoader != null) {
				contextLoader.closeWebApplicationContext(servletContext);
			}
			Application.setApplicationState(state, ApplicationState.SHUTDOWN);
		} catch (Throwable e) {
			Application.setApplicationState(state, ApplicationState.ERROR);
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns a apps context with the appropriate permissions of the effective
	 * user (which is not necessarily the same as the authenticated user. If the
	 * effective user is the system, an unproxied context will be returned.
	 * <p/>
	 * This method will not function until the application has been properly
	 * initialized or if a security context does not exist for the current call
	 * stack.
	 * 
	 * @return
	 */
	public static AppsContext getEffectiveContext() {
		if (null != context) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (null == auth) {
				log.error("Authentication has not been established.");
				log.error(getInitErrorMessage());
				throw new SecurityException("Authentication not established for current execution context.");
			}
			if (SecurityUtils.isGranted(auth, "ROLE_ADMIN")) {
				return context;
			}
			User user = UserHolder.getUser();
			if (SecurityUtils.isGranted(user, "ROLE_ADMIN")) {
				return context;
			}
			return context.get("appsContext", AppsContext.class);
		} else {
			log.error(getInitErrorMessage());
			return null;
		}
	}



	/**
	 * Returns the raw apps context (spring context).
	 * 
	 * @return
	 */
	public static AppsContext getContext() {
		return context;
	}

	/**
	 * Returns a nice common error message.
	 */
	private static String getInitErrorMessage() {
		return "System is not initialized, unable to return "
				+ AppsContext.class.getName() + " object. Call "
				+ Application.class.getName()
				+ ".initialize() once before calling this method.";
	}

	/**
	 * For testing purposes only. Used to override the appscontext returned.
	 * </p> Make sure to run {@link #clearTestContext} after you are done.
	 * 
	 * @param context
	 *            The test apps context
	 */
	protected static void setTestContext(SpringAppsContext context) {
		Application.context = context;
		Application.setApplicationState(ApplicationState.INITIALIZING,
				ApplicationState.INITIALIZED);
	}

	/**
	 * For testing purposes only. Used to reset the appscontext after using
	 * setTestContext.
	 */
	protected static void clearTestContext() {
		Application.context = new SpringAppsContext();
	}

	// ******************************************************************************
	// Application state management.
	// ******************************************************************************
	public static ApplicationState getApplicationState() {
		return state;
	}

	/**
	 * Transition the application from the previous to a new state. The intent
	 * of the previous state parameter is to prevent invalid transitions -
	 * client code can request a transition but that the application will only
	 * honor certain to-from conditions.
	 * 
	 * @param newState
	 *            the state being transitioned to.
	 * @param previousState
	 *            the state which is being transitioned from
	 * @return State of the application after the operation.
	 */
	public static synchronized ApplicationState setApplicationState(ApplicationState previousState, ApplicationState newState) {
		if (state != previousState) {
			return state;
		}

		if (dispatcher != null) {
			dispatcher.dispatchEvent(new ApplicationStateChangeEvent(previousState, newState));
		}
		state = newState;
		return state;
	}

	/**
	 * This is called by the SpringContext when the context is done
	 * initializing but before any Spring contextRefresh events are fired, so
	 * calls done on that event don't wind up with a null
	 * Application.getContext() reference.
	 * 
	 * @param springContext
	 */
	public static synchronized void finishContextRefresh(SpringAppsContext springContext) {
		context = springContext;
		Application.dispatcher = context.get("eventDispatcher", EventDispatcher.class);
		Application.setApplicationState(ApplicationState.INITIALIZING, ApplicationState.INITIALIZED);
	}
}