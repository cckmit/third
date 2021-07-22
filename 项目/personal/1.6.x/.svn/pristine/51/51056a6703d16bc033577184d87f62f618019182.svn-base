package org.opoo.apps.lifecycle.spring;

import java.io.IOException;
import java.util.Collections;

import org.opoo.apps.AppsContext;
import org.opoo.apps.event.spring.SpringEvent;
import org.opoo.apps.license.AppsLicenseManager;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.lifecycle.ApplicationState;
import org.opoo.apps.module.ModuleManager;
import org.opoo.apps.security.PresenceManager;
import org.opoo.apps.security.UserManager;
import org.opoo.apps.service.AttachmentManager;
import org.opoo.apps.service.QueryService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.XmlWebApplicationContext;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.6.0
 */
public class SpringAppsContext extends XmlWebApplicationContext implements AppsContext {
	
	
	@SuppressWarnings("unchecked")
	public <T> T get(String name, Class<T> cls) {
		try {
			return (T) super.getBean(name, cls);
		} catch (BeansException e) {
			logger.debug(e);
			return null;
		}
	}



	/* (non-Javadoc)
	 * @see org.springframework.context.support.AbstractApplicationContext#getResources(java.lang.String)
	 */
	@Override
	public Resource[] getResources(String locationPattern) throws IOException {
		//System.out.println(locationPattern);
		return super.getResources(locationPattern);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.context.support.AbstractApplicationContext#finishRefresh()
	 */
	@Override
	protected void finishRefresh() {
		super.finishRefresh();
		Application.finishContextRefresh(this);
		publishEvent(new FinishRefreshEvent(this));
		if(logger.isDebugEnabled()){
			logger.debug("Ë¢ÐÂÍê±Ï");
		}
	}
	
	
	public static class FinishRefreshEvent extends SpringEvent{
		private static final long serialVersionUID = -1837386935138323192L;

		public FinishRefreshEvent(Object source) {
			super(source);
		}
	}

	
	
	public static void main(String[] args){
		Collections.emptyList().add(new Object());
		
		//ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
		//SpringJiveContextImpl context = new SpringJiveContextImpl();
//		SpringAppsContext context = new SpringAppsContext();
//		context.setConfigLocation("classpath*:applicationContext-apps-hibernate.xml;classpath*:applicationContext-apps-hibernate.xml");
		//context.setConfigLocations(new String[]{"classpath*:applicationContext-apps-initialize.xml"});
		
//		System.out.println(context.getConfigLocations().length);
		//context.refresh();
		
//		LicenseManager lm = context.get("licenseManager", LicenseManager.class);
//		System.out.println(lm);
	}



	private AppsLicenseManager licenseManager;
	public AppsLicenseManager getLicenseManager() {
		if(!isRunningState()){
			return getLicenseManagerImpl();
		}
		if(licenseManager == null){
			licenseManager = getLicenseManagerImpl();
		}
		return licenseManager;
	}
	
	private AppsLicenseManager getLicenseManagerImpl(){
		return get("licenseManager", AppsLicenseManager.class);
	}

	
	protected boolean isRunningState(){
		return Application.getApplicationState() == ApplicationState.RUNNING;
	}


	private ModuleManager moduleManager;
	/* (non-Javadoc)
	 * @see org.opoo.apps.AppsContext#getModuleManager()
	 */
	public ModuleManager getModuleManager() {
		if(!isRunningState()){
			return getModuleManagerImpl();
		}
		if(moduleManager == null){
			moduleManager = getModuleManagerImpl();
		}
		return moduleManager;
	}
	private ModuleManager getModuleManagerImpl() {
		return get("moduleManager", ModuleManager.class);
	}

	
	private UserManager userManger;
	public UserManager getUserManager(){
		if(!isRunningState()){
			return getUserManagerImpl();
		}
		if(userManger == null){
			userManger = getUserManagerImpl();
		}
		return userManger;
	}
	private UserManager getUserManagerImpl() {
		return get("userManager", UserManager.class);
	}


	private PresenceManager presenceManager;
	public PresenceManager getPresenceManager() {
		if(!isRunningState()){
			return getPresenceManagerImpl();
		}
		if(presenceManager == null){
			presenceManager = getPresenceManagerImpl();
		}
		return presenceManager;
	}

	private PresenceManager getPresenceManagerImpl() {
		return get("presenceManager", PresenceManager.class);
	}


	private AttachmentManager attachmentManager;
	public AttachmentManager getAttachmentManager() {
		if(isRunningState()){
			return getAttachmentManagerImpl();
		}
		if(attachmentManager == null){
			attachmentManager = getAttachmentManagerImpl();
		}
		return attachmentManager;
	}

	private AttachmentManager getAttachmentManagerImpl(){
		return get("attachmentManager", AttachmentManager.class);
	}

	private QueryService queryService;
	public QueryService getQueryService() {
		if(isRunningState()){
			return getQueryServiceImpl();
		}
		if(queryService == null){
			queryService = getQueryServiceImpl();
		}
		return queryService;
	}
	
	private QueryService getQueryServiceImpl(){
		return get("queryService", QueryService.class);
	}



	public ApplicationContext getApplicationContext() {
		return this;
	}
}
