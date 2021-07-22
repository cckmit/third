package org.opoo.apps.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.opoo.apps.AppsConstants;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.license.AppsLicenseHolder;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.lifecycle.ApplicationState;


/**
 * 应用程序状态过滤器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ApplicationStateFilter extends AbstractFilter implements Filter {

//	private static final Log log = LogFactory.getLog(ApplicationStateFilter.class);
	private static final String MAINTENANCE_PAGE = "MAINTENANCE_PAGE";
	private static final String UPGRADE_PAGE = "UPGRADE_PAGE";
	private static final String POST_UPGRADE_PAGE = "POST_UPGRADE_PAGE";
	private static final String POST_UPGRADE_PAGE_DEFAULT = "/post-upgrade/index.jspa";
	private static final String MAINTENANCE_PAGE_DEFAULT = "skin.default.maintenance.page";
	private static final String UPGRADE_PAGE_DEFAULT = "/upgrade/index.jspa";
	private static final String SETUP_PAGE = "SETUP_PAGE";
	private static final String SETUP_PAGE_DEFAULT = "/admin/setup/index.jsp";
	private static final String RESTART_PAGE = "RESTART_PAGE";
	private static final String RESTART_PAGE_DEFAULT = "/state-force-restart.jspa";
	private static final String RESTART_ADMIN_PAGE = "RESTART_ADMIN_PAGE";
	private static final String RESTART_ADMIN_PAGE_DEFAULT = "/state-admin-force-restart.jspa";
//	private static final String RICH_CLIENT_CHECKER_CLASS = "RICH_CLIENT_CHECKER_CLASS";
	private String maintenancePage;
	private String upgradePage;
	private String setupPage;
	private String errorFtl;
	private String postUpgradePage;
	private String restartAdminPage;
	private String restartUserPage;
	
	public ApplicationStateFilter() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		//naintenance page
		try {
			maintenancePage = filterConfig.getInitParameter(MAINTENANCE_PAGE);
			if (null == maintenancePage) {
				maintenancePage = AppsGlobals.getProperty(MAINTENANCE_PAGE_DEFAULT);
				if (null == maintenancePage)
					log.info("No default maintenance page configured.");
			}
		} catch (Exception ex) {
			log.warn("Error establishing default maintenance page.", ex);
		}
		
		//setup page
		try {
			setupPage = filterConfig.getInitParameter(SETUP_PAGE);
			if (null == setupPage)
				setupPage = SETUP_PAGE_DEFAULT;
		} catch (Exception ex) {
			log.warn("Error establishing setup page.", ex);
		}
		
		//
		upgradePage = filterConfig.getInitParameter(UPGRADE_PAGE);
		if (null == upgradePage){
			upgradePage = UPGRADE_PAGE_DEFAULT;
		}
		
		//
		postUpgradePage = filterConfig.getInitParameter(POST_UPGRADE_PAGE);
		if (postUpgradePage == null){
			postUpgradePage = POST_UPGRADE_PAGE_DEFAULT;
		}

		//
		restartUserPage = filterConfig.getInitParameter(RESTART_PAGE);
		if (restartUserPage == null){
			restartUserPage = RESTART_PAGE_DEFAULT;
		}
		
		restartAdminPage = filterConfig.getInitParameter(RESTART_ADMIN_PAGE);
		if (restartAdminPage == null){
			restartAdminPage = RESTART_ADMIN_PAGE_DEFAULT;
		}
		
		
		/////////////////////////////////////////////////////////////////////////////////////////
//		String className = filterConfig.getInitParameter(RICH_CLIENT_CHECKER_CLASS);
//		if(className != null){
//			richClientChecker = (RichClientChecker) ClassUtils.newInstance(className);
//		}
//		if(richClientChecker == null){
//			//richClientChecker = new RichClientCheckerAdaptor();
//			log.info("不使用富客户端检查器");
//		}else{
//			log.info("使用富客户端检查器：" + className);
//			if(richClientChecker instanceof HeaderTokenRichClientChecker){
//				HeaderTokenRichClientChecker cc = (HeaderTokenRichClientChecker) richClientChecker;
//				String name = filterConfig.getInitParameter("HeaderTokenRichClientChecker.headerName");
//				String value = filterConfig.getInitParameter("HeaderTokenRichClientChecker.headerValue");
//				if(name != null){
//					cc.setHeaderName(name);
//					log.debug("set HeaderTokenRichClientChecker.headerName = " + name);
//				}
//				if(value != null){
//					cc.setHeaderValue(value);
//					log.debug("set HeaderTokenRichClientChecker.headerValue = " + value);
//				}
//			}
//		}
		
		
		log.info(String.format("Application state filter configured to:\n" +
				"Setup Page: '%s'\n" +
				"Maintenance Page: '%s'\n" +
				"Upgrade Page: '%s'\n" +
				"Error Page: '%s'\n", setupPage, maintenancePage, upgradePage, errorFtl));
	}

	public void destroy() {
		log.info("Application state filter going offline.");
	}
	
	
	


	/* (non-Javadoc)
	 * @see org.opoo.apps.web.filter.AbstractFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected final void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		doFilterInternal2(request, response, filterChain);
		
		AppsLicenseHolder.setThreadAppsLicense(null);
	}
	
	protected void doFilterInternal2(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException{ 
		
//	}
//
//	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//			throws IOException, ServletException {
//		
//		JspFactory factory = JspFactory.getDefaultFactory();
//		if(factory != null && !(factory instanceof AppsJspFactory)){
//			factory = new AppsJspFactory(factory);
//			JspFactory.setDefaultFactory(factory);
//		}
		
		
		ApplicationState state = Application.getApplicationState();
//		HttpServletRequest request = (HttpServletRequest) servletRequest;
//		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String uriAfterContextPath = StringUtils.removeStart(requestURI, contextPath);
		//判断是否富客户端
		boolean isRichClient = isRichClient(request, response);
		
		if(isRichClient && IS_DEBUG_ENABLED){
			log.debug("富客户端: " + requestURI);
		}
		
		if (state == ApplicationState.RUNNING){
			if (requestURI.contains("/admin/setup") || uriAfterContextPath.startsWith("/upgrade")) {
				redirect(request, response, "/index.jsp");
				return;
			} else {
				filterChain.doFilter(request, response);
				return;
			}
		}
		
		if (state == ApplicationState.MAINTENANCE_STARTED){
			if (requestURI.contains("/admin/") || requestURI.contains(maintenancePage) 
					|| requestURI.contains("/images/")
					|| requestURI.contains("favicon.ico") 
					/*|| requestURI.contains("/login")*/) {
				filterChain.doFilter(request, response);
				return;
			} else {
				
				if(isRichClient){
					//response.setHeader("location", maintenancePage);
					//response.sendError(AppsConstants.HttpServletResponse.SC_MAINTENANCE);
					sendToRichClient(request, response, AppsConstants.HttpServletResponse.SC_MAINTENANCE, maintenancePage);
					return;
				}
				
				//forward 比 redirect合适
				//redirect(request, response, maintenancePage);
				forward(request, response, maintenancePage);
				return;
			}
		}
		if (state == ApplicationState.RESTART_REQUIRED) {
			if (requestURI.contains(restartUserPage) 
					|| requestURI.contains(restartAdminPage)
					|| requestURI.contains("/admin/style/")
//					|| requestURI.contains("/admin/images/") 
					|| requestURI.contains("/images")
					|| requestURI.contains("favicon.ico")){
				
				filterChain.doFilter(request, response);
				
			}else if (requestURI.contains("/upgrade/") || requestURI.contains("/admin/")){
				
				if(isRichClient){
//					response.setHeader("location", restartAdminPage);
//					response.sendError(AppsConstants.HttpServletResponse.SC_RESTART_REQUIRED);
					sendToRichClient(request, response, AppsConstants.HttpServletResponse.SC_RESTART_REQUIRED, restartAdminPage);
					return;
				}
			
				
				redirect(request, response, restartAdminPage);
			}else{
				if(isRichClient){
//					response.setHeader("location", restartUserPage);
//					response.sendError(AppsConstants.HttpServletResponse.SC_RESTART_REQUIRED);
					sendToRichClient(request, response, AppsConstants.HttpServletResponse.SC_RESTART_REQUIRED, restartUserPage);
					return;
				}
				
				
				redirect(request, response, restartUserPage);
			}
			return;
		}
		if (state == ApplicationState.UPGRADE_NEEDED || state == ApplicationState.UPGRADE_COMPLETE
				|| state == ApplicationState.UPGRADE_STARTED)
			if (requestURI.contains("/upgrade/") || requestURI.contains("/admin/style/")
					|| requestURI.contains("/admin/images/") || requestURI.contains("/images/")
					|| requestURI.contains("/favicon.ico")) {
				filterChain.doFilter(request, response);
				return;
			} else {
				redirect(request, response, upgradePage);
				return;
			}
		if (state == ApplicationState.POST_UPGRADE_NEEDED || state == ApplicationState.POST_UPGRADE_STARTED)
			if (requestURI.contains("/post-upgrade/")) {
				filterChain.doFilter(request, response);
				return;
			} else {
				redirect(request, response, postUpgradePage);
				return;
			}
		if (state == ApplicationState.SETUP_STARTED)
			if (requestURI.contains("/admin/") && !requestURI.contains("login") || requestURI.contains("/scripts/")
					|| requestURI.contains("/resources/") || requestURI.contains("/dwr/")) {
				filterChain.doFilter(request, response);
				return;
			} else {
				
				if(isRichClient){
//					response.setHeader("location", setupPage);
//					response.sendError(AppsConstants.HttpServletResponse.SC_NOT_SETUP);
					sendToRichClient(request, response, AppsConstants.HttpServletResponse.SC_NOT_SETUP, setupPage);
					return;
				}
				
				redirect(request, response, setupPage);
				return;
			}
		if (state == ApplicationState.SHUTDOWN) {
			response.getOutputStream().print("Apps application has shutdown.");
			return;
		} else {
			log.warn((new StringBuilder()).append("Application state '").append(state)
					.append("' has no routing rules.").toString());
			filterChain.doFilter(request, response);
			return;
		}
	}
	
	private void forward(HttpServletRequest request, HttpServletResponse response, String resource) 
		throws ServletException, IOException{
		request.getRequestDispatcher(resource).forward(request, response);
	}

	private void redirect(HttpServletRequest request, HttpServletResponse response, String resource)
			throws IOException, ServletException {
		if (null == resource)
			throw new NullPointerException("Dispatch resource is null.");
		if (resource.startsWith("http://") || resource.startsWith("https://")) {
			response.sendRedirect(resource);
			return;
		}
		if (resource.contains(request.getContextPath()))
			response.sendRedirect(resource);
		else
			response.sendRedirect((new StringBuilder()).append(request.getContextPath()).append(resource).toString());
	}
}