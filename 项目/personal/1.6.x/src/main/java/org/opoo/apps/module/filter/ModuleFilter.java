package org.opoo.apps.module.filter;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsConstants;
import org.opoo.apps.AppsContext;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.module.Module;
import org.opoo.apps.module.ModuleListener;
import org.opoo.apps.module.ModuleManager;
import org.opoo.apps.util.ChainingClassLoader;


/**
 * 模块过滤器。
 * 
 * 1. 适当应用类加载器。
 * 2. 保护模块配置信息，避免被WEB外部访问。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ModuleFilter implements Filter {
	private class ModuleManagerObserver implements ModuleListener {
		public void moduleCreated(String name, Module<?> module) {
			loader = null;
		}

		public void moduleDestroyed(String name, Module<?> module) {
			loader = null;
		}
	}

	public static final String MODULE_INF_PATH = "/" + AppsConstants.MODULE_INF + "/";
	private static final Log log = LogFactory.getLog(ModuleFilter.class);
	private volatile ClassLoader loader;
	private volatile ModuleManagerObserver observer;

	public ModuleFilter() {
	}

	public void init(FilterConfig filterconfig) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String uri = request.getRequestURI();
		
		if(uri.contains(MODULE_INF_PATH)){
			((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_NOT_FOUND);
			if(log.isDebugEnabled()){
				log.debug("不能访问受保护的模块资源：" + uri);
			}
			return;
		}
//		log.warn("替换ClassLoader： " + uri);
//		log.debug("" + Application.isInitialized());
//		log.debug("" + AppsGlobals.isSetup());
//		log.debug("" + !uri.contains(".jsp"));
//		log.debug("" + uri.contains(".jspa"));
		if (Application.isInitialized() && AppsGlobals.isSetup()
				&& (!uri.contains(".jsp") || uri.contains(".jspa"))) {
			Thread thread = Thread.currentThread();
			if (loader == null) {
				AppsContext ctx = Application.getContext();
				ModuleManager mgr = ctx.getModuleManager();
				if (observer == null) {
					observer = new ModuleManagerObserver();
					mgr.addModuleListener(observer);
				}
				ClassLoader parent = thread.getContextClassLoader();
				if (parent == null)
					parent = ModuleFilter.class.getClassLoader();
				Collection<ClassLoader> loaders = mgr.getClassLoaders();
				loader = new ChainingClassLoader(parent, loaders);
			}
			if(log.isDebugEnabled()){
				log.debug("Set chain class loader for '" + uri + "'.");
			}
			thread.setContextClassLoader(loader);
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy() {
	}
}
