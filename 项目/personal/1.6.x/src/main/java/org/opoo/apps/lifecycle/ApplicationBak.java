package org.opoo.apps.lifecycle;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsContext;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.license.AppsInstanceLoader;
import org.opoo.apps.lifecycle.event.ApplicationStateChangeEvent;
import org.opoo.apps.module.ModuleManagerImpl;
import org.opoo.apps.security.SecurityUtils;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserHolder;
import org.opoo.apps.web.context.ConfigurationProvider;
import org.opoo.apps.web.context.ConfigurationProviderManager;
import org.opoo.apps.web.context.SpringContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.context.ContextLoader;

/**
 * 应用程序主程序。
 * 
 * 定义了启动、停止Spring容器的方法和对应用程序状态的操作。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 * @deprecated since 1.6.0, just backup.
 */
public abstract class ApplicationBak {

	private static ApplicationState state = ApplicationState.INITIALIZING;
	private static SpringContext context;
	private static EventDispatcher dispatcher;
	private static ContextLoader contextLoader;
	private static ServletContext servletContext;
	private static final Log log = LogFactory.getLog(ApplicationBak.class);

	public static boolean isInitialized() {
		return state == ApplicationState.RUNNING || state == ApplicationState.INITIALIZED;
	}

	public static boolean isContextInitialized() {
		//System.out.println(context + ". " + (context != null ? context.isActive() : false));
		return context != null && context.isActive();
	}

	public static synchronized void start() {
		if (state == ApplicationState.INITIALIZING) {
			Thread currentThread = Thread.currentThread();
			ClassLoader oldLoader = currentThread.getContextClassLoader();
			try {
				log.info("Opoo Apps Spring application life cycle initializing");
				if (AppsGlobals.isSetup()) {
					ModuleManagerImpl.getInstance().preInit();
				}
				
				ConfigurationProvider pro = ConfigurationProviderManager.getConfigurationProvider();//new ConfigurationProviderImpl();
				String[] configLocations = pro.getConfigLocations();
				if (log.isInfoEnabled()) {
					log.info("Using " + configLocations.length + " context files.");
					for (String file : configLocations) {
						log.info("Including '" + file + "' in context files.");
					}
				}
				context = new SpringContext();
				context.setConfigLocations(configLocations);
				context.refresh();
			} catch (Throwable e) {
				setApplicationState(ApplicationState.INITIALIZING, ApplicationState.ERROR);
				log.error(e.getMessage(), e);
				throw new RuntimeException(e);
			} finally {
				if (oldLoader != null)
					currentThread.setContextClassLoader(oldLoader);
			}
		} else {
			log.warn("Application.start(): Application is already initialized.");
		}
	}

	public static synchronized void start(ContextLoader contextLoader, ServletContext servletContext) {
		ApplicationBak.contextLoader = contextLoader;
		ApplicationBak.servletContext = servletContext;
		if (contextLoader == null) {
			String msg = "Invalid runtime state, the context loader has not been established.";
			log.fatal(msg);
			throw new IllegalStateException(msg);
		}
		if (state == ApplicationState.INITIALIZING) {
			Thread currentThread = Thread.currentThread();
			ClassLoader oldLoader = currentThread.getContextClassLoader();
			try {
				log.info("Application Spring application life cycle initializing");
				if (AppsGlobals.isSetup()) {
					ModuleManagerImpl.getInstance().preInit();
				}
				contextLoader.initWebApplicationContext(servletContext);
			} catch (Throwable e) {
				setApplicationState(ApplicationState.INITIALIZING, ApplicationState.ERROR);
				log.error(e.getMessage(), e);
				throw new RuntimeException(e);
			} finally {
				if (oldLoader != null)
					currentThread.setContextClassLoader(oldLoader);
			}
		} else {
			log.warn("Application.start(): Application is already initialized.");
		}
	}

	public static synchronized void destroy() {
		try {
			if (contextLoader != null && servletContext != null) {
				contextLoader.closeWebApplicationContext(servletContext);
			}

			if (context != null && context.isActive()) {
				try {
					context.close();
				} catch (Exception e) {
					log.warn("Unhandled exception during spring context shutdown.", e);
				}
			}

			setApplicationState(state, ApplicationState.SHUTDOWN);
			if (context != null) {
				context.destroy();
			}
			dispatcher = null;
		} catch (Throwable e) {
			setApplicationState(state, ApplicationState.ERROR);
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

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

	public static AppsContext getContext() {
		return context;
	}

	private static String getInitErrorMessage() {
		return (new StringBuilder()).append("System is not initialized, unable to return ").append(
				AppsContext.class.getName()).append(" object. Call ").append(ApplicationBak.class.getName()).append(
				".initialize() once before calling this method.").toString();
	}

	public static ApplicationState getApplicationState() {
		return state;
	}

	public static synchronized ApplicationState setApplicationState(ApplicationState previousState,
			ApplicationState newState) {
		if (state != previousState) {
			return state;
		}

		if (dispatcher != null) {
			dispatcher.dispatchEvent(new ApplicationStateChangeEvent(previousState, newState));
		}
		state = newState;
		return state;
	}

	public static synchronized void finishContextRefresh(SpringContext springContext) {
		context = springContext;
		ApplicationBak.dispatcher = context.get("eventDispatcher", EventDispatcher.class);
		setApplicationState(ApplicationState.INITIALIZING, ApplicationState.INITIALIZED);
	}

	/**
	 * @param dispatcher the dispatcher to set
	 */
	public static void setEventDispatcher(EventDispatcher dispatcher) {
		ApplicationBak.dispatcher = dispatcher;
	}
	
	
	
	public static void main(String[] args){
		AppsInstanceLoader.initialize(new ClassPathResource("apps_build.xml"));
		ConfigurationProvider pro = ConfigurationProviderManager.getConfigurationProvider();//new ConfigurationProviderImpl();
		String[] configLocations = pro.getConfigLocations();
		if (log.isInfoEnabled()) {
			log.info("Using " + configLocations.length + " context files.");
			for (String file : configLocations) {
				log.info("Including '" + file + "' in context files.");
			}
		}
	}
}
