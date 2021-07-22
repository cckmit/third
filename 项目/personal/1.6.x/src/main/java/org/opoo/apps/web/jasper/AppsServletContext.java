package org.opoo.apps.web.jasper;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.web.resource.ResourceManager;
import org.opoo.web.servlet.ServletContextWrapper;
import org.springframework.core.io.Resource;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class AppsServletContext extends ServletContextWrapper {
	private static Log log = LogFactory.getLog(AppsServletContext.class);
	
	//private String moduleServletPath = "/m/";//AppsConstants.MODULE_SERVLET_PATH;
	
	public AppsServletContext(ServletContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see org.opoo.web.servlet.ServletContextWrapper#getContext(java.lang.String)
	 */
	@Override
	public ServletContext getContext(String uripath) {
		return getRequiredAppsServletContext(super.getContext(uripath));
	}
	
	protected boolean isAppsResource(String path){
		return path.startsWith("/modules/") || path.startsWith("/resources/");
	}
	
	protected Resource findResource(String path){
		ResourceManager resourceManager = Application.getContext().get("resourceManager", ResourceManager.class);
		if(path.startsWith("/modules/")){
			String pathInfo = path.substring(8);
			return resourceManager.getModuleResource(pathInfo);
		}else if(path.startsWith("/resources/")){
			String pathInfo = path.substring(10);
			resourceManager.getPublicResource(pathInfo);
		}
		
		return null;
	}
	/* (non-Javadoc)
	 * @see org.opoo.web.servlet.ServletContextWrapper#getRealPath(java.lang.String)
	 */
	@Override
	public String getRealPath(String path) {
		if(isAppsResource(path)){
			Resource resource = findResource(path);
			if(resource != null && resource.exists()){
				try {
					String rp = resource.getFile().getAbsolutePath();
					
					if(log.isDebugEnabled()){
						log.debug("getRealPath for " + path + " is " + rp);
					}
					return rp;
				} catch (IOException e) {
					log.error("Cannot find resource for path " + path, e);
					return null;
				}
			}else{
				return null;
			}
		}
		
		
//		return null;
//		
//		File file = getPossibleModuleJspFile(path);
//		if(file != null){
//			if(log.isDebugEnabled()){
//				log.debug("Ä£¿éÖÐµÄJSP - getRealPath: " + file);
//			}
//			return file.getAbsolutePath();
//		}
		return super.getRealPath(path);
	}

	/* (non-Javadoc)
	 * @see org.opoo.web.servlet.ServletContextWrapper#getResource(java.lang.String)
	 */
	@Override
	public URL getResource(String path) throws MalformedURLException {
		if(isAppsResource(path)){
			Resource resource = findResource(path);
			if(resource != null && resource.exists()){
				try {
					URL url = resource.getURL();
					
					if(log.isDebugEnabled()){
						log.debug("getResource for " + path + " is " + url);
					}
					return url;
				} catch (IOException e) {
					log.error("Cannot find resource for path " + path, e);
					return null;
				}
			}else{
				return null;
			}
		}
		
	
		return super.getResource(path);
	}

	/* (non-Javadoc)
	 * @see org.opoo.web.servlet.ServletContextWrapper#getResourceAsStream(java.lang.String)
	 */
	@Override
	public InputStream getResourceAsStream(String path) {
		if(isAppsResource(path)){
			Resource resource = findResource(path);
			if(resource != null && resource.exists()){
				try {
					InputStream is = resource.getInputStream();
					
					if(log.isDebugEnabled()){
						log.debug("getResourceAsStream for " + path + " is " + is);
					}
					return is;
				} catch (IOException e) {
					log.error("Cannot find resource for path " + path, e);
					return null;
				}
			}else{
				return null;
			}
		}
		
		return super.getResourceAsStream(path);
	}
	
	

	public static AppsServletContext getRequiredAppsServletContext(ServletContext context){
		if(context instanceof AppsServletContext){
			return (AppsServletContext) context;
		}else{
			return new AppsServletContext(context);
		}
	}
}
