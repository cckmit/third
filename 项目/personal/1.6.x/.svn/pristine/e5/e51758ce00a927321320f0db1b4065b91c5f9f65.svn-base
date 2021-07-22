package org.opoo.apps.web;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.util.ClassUtils;


/**
 * 富客户端检查器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface RichClientChecker {
	/**
	 * 检查请求方是否是富客户端。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	boolean isRichClient(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	
	/**
	 * 给富客户端响应特殊的信息。
	 * 
	 * @param request
	 * @param response
	 * @param status
	 * @param location
	 * @throws IOException 
	 */
	void sendToClient(HttpServletRequest request, HttpServletResponse response, int status, String location) throws IOException;




	/**
	 * RichClientChecker 持有者。
	 *
	 */
	public static class Holder{
		private static final Log log = LogFactory.getLog(Holder.class);
		private static final String KEY = "org.opoo.apps.web.RichClientChecker.KEY";
		
		
		public static final String RICH_CLIENT_CHECKER_CLASS = "richClientChecker";
		
		/**
		 * 
		 * @param servletContext
		 * @param params
		 * @return
		 */
		public static RichClientChecker getRichClientChecker(ServletContext servletContext){
			RichClientChecker richClientChecker = (RichClientChecker) servletContext.getAttribute(KEY);
			
			if(richClientChecker == null){
				String className = servletContext.getInitParameter(RICH_CLIENT_CHECKER_CLASS);
				if(className != null){
					richClientChecker = (RichClientChecker) ClassUtils.newInstance(className);
				}
				if(richClientChecker == null){
					//richClientChecker = new RichClientCheckerAdaptor();
					log.info("不使用富客户端检查器");
				}else{
					log.info("使用富客户端检查器：" + className);
					if(richClientChecker instanceof HeaderTokenRichClientChecker){
						HeaderTokenRichClientChecker cc = (HeaderTokenRichClientChecker) richClientChecker;
						String name = servletContext.getInitParameter("HeaderTokenRichClientChecker.headerName");
						String value = servletContext.getInitParameter("HeaderTokenRichClientChecker.headerValue");
						if(name != null){
							cc.setHeaderName(name);
							log.debug("set HeaderTokenRichClientChecker.headerName = " + name);
						}
						if(value != null){
							cc.setHeaderValue(value);
							log.debug("set HeaderTokenRichClientChecker.headerValue = " + value);
						}
					}
				}
				
				
				/**
				 * 
				 */
				if(richClientChecker != null){
					log.debug("Save richClientChecker to servletContext: " + richClientChecker);
					servletContext.setAttribute(KEY, richClientChecker);
				}
			}
			return richClientChecker;
		}
	}
}
