/*
 * Copyright (C) 2006-2009 Alex Lin. All rights reserved.
 *
 * Alex PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms. See http://www.opoo.org/apps/license.txt for details.
 */
package org.opoo.apps.license;

import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.license.loader.CheckCallback;
import org.opoo.apps.license.loader.FileLicenseLoader;
import org.opoo.apps.license.loader.HaspLicenseLoader;
import org.opoo.apps.license.loader.LicenseLoader;
import org.opoo.apps.license.loader.MinaLicenseLoader;
import org.opoo.apps.license.loader.WsLicenseLoaderV2RestSSL;
import org.opoo.apps.lifecycle.Application;

import com.jivesoftware.license.License;
import com.jivesoftware.license.LicenseException;
import com.jivesoftware.license.LicenseManager;
import com.jivesoftware.license.LicenseProvider;


/**
 * 应用级 License 管理器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class AppsLicenseManager extends LicenseManager {

	public static enum AppsLicenseProperty{
		expirationDate, grantedIP, reportURL, devMode, allowedUserCount, numClusterMembers, validateURL
	}
	
	private static final Log log = AppsLicenseLog.LOG;//LogFactory.getLog(AppsLicenseManager.class.getName());
    private static AppsLicenseManager instance;
    private boolean initialized;
    private AtomicBoolean initializing = new AtomicBoolean(false);
    private AtomicBoolean checking = new AtomicBoolean(false);
    private boolean failed;
    private Collection<Exception> exceptions = new ArrayList<Exception>();
    //private Collection<LicenseLoader> licenseLoaders = new ArrayList<LicenseLoader>();
    private Executor executor;
    private boolean newExecutor = false;
    
    private AtomicLong lastLoaded = new AtomicLong(-1);
    private LicenseLoader currentLoader;
    
    private static final int ONE_HOUR_MILLIS = 60 * 60 * 1000;

	private int checkCount = 0;

//    private static final int DEFAULT_NETWORK_CHECK_INTERVAL = 3 * 60 * 1000;
//    private static final int DEFAULT_NETWORK_LOAD_INTERVAL = 15 * 60 * 1000;
//    private static final int DEFAULT_FILE_CHECK_INTERVAL = 1 * 60 * 1000;
//    private static final int DEFAULT_FILE_LOAD_INTERVAL = 1 * 60 * 60 * 1000;
//    private static final int DEFAULT_HASP_CHECK_INTERVAL = 1 * 60 * 1000;
//    private static final int DEFAULT_HASP_LOAD_INTERVAL = 3 * 60 * 1000;
//    
//    private int networkCheckInterval = DEFAULT_NETWORK_CHECK_INTERVAL;
//    private int networkLoadInterval = DEFAULT_NETWORK_LOAD_INTERVAL;
//    private int fileCheckInterval = DEFAULT_FILE_CHECK_INTERVAL;
//    private int fileLoadInterval = DEFAULT_FILE_LOAD_INTERVAL;
//    private int haspCheckInterval = DEFAULT_HASP_CHECK_INTERVAL;
//    private int haspLoadInterval = DEFAULT_HASP_LOAD_INTERVAL;
    
    
    private AppsLicenseManager(){
//    	Collection<LicenseLoader> licenseLoaders = new ArrayList<LicenseLoader>();
//    	if(isFileLicenseEnabled()){
//    		licenseLoaders.add(new FileLicenseLoader());
//    	}
//    	if(isHaspLicenseEnabled()){
//    		licenseLoaders.add(new HaspLicenseLoader());
//    	}else{
//    		licenseLoaders.add(new NetworkLicenseLoader());
//    	}
    	
//    	fileCheckInterval = AppsGlobals.getSetupProperty("license.file.checkInterval", DEFAULT_FILE_CHECK_INTERVAL);
//    	fileLoadInterval = AppsGlobals.getSetupProperty("license.file.loadInterval", DEFAULT_FILE_LOAD_INTERVAL);
    }
    
	public static synchronized AppsLicenseManager getInstance() {
		if (instance == null) {
			instance = new AppsLicenseManager();
			instance.init();
		}
		return instance;
	}
	
	public static boolean isHaspLicenseEnabled(){
		return (AppsGlobals.getSetupProperty("license.hasp.enabled", false)
		|| Boolean.getBoolean("license.hasp.enabled")) 
		&& HaspLicenseLoader.isHaspDriverInstalled();
	}
	
	public static boolean isFileLicenseEnabled(){
		return AppsGlobals.getSetupProperty("license.file.enabled", false)
		|| Boolean.getBoolean("license.file.enabled");
	}
	
	public static boolean isNetworkLicenseEnabled(){
		return AppsGlobals.getSetupProperty("license.network.enabled", true)
		|| Boolean.getBoolean("license.network.enabled");
	}
	
	public static boolean isMinaLicenseEnabled(){
		return AppsGlobals.getSetupProperty("license.mina.enabled", false)
		|| Boolean.getBoolean("license.mina.enabled");
	}
    
    private static synchronized LicenseAndLoader loadAvailableLicense(/*Collection<LicenseLoader> licenseLoaders, */LicenseProvider provider){
    	Collection<LicenseLoader> licenseLoaders = new ArrayList<LicenseLoader>();
    	if(isFileLicenseEnabled()){
    		licenseLoaders.add(new FileLicenseLoader());
    	}
    	if(isHaspLicenseEnabled()){
    		licenseLoaders.add(new HaspLicenseLoader());
    	}
    	if(isMinaLicenseEnabled()){
    		licenseLoaders.add(new MinaLicenseLoader(provider, null));
    	}
    	if(isNetworkLicenseEnabled()){
    		//licenseLoaders.add(new WsLicenseLoaderV2());
    		//licenseLoaders.add(new WsLicenseLoaderV2Rest());
    		licenseLoaders.add(new WsLicenseLoaderV2RestSSL());
    	}
//    	licenseLoaders.add(new MinaLicenseLoader(provider, null));
    	
    	for(LicenseLoader loader : licenseLoaders){
    		License loadedLicense = loader.loadLicense(provider);
    		if(loadedLicense != null){
    			return new LicenseAndLoader(loadedLicense, loader);
    		}
    	}
    	return null;
    }
    
    private void init(){
        if(initializing.get()){
        	log.warn("Manager is intializing in another thread, please wait.");
        	return;
        }
        try{
        	initializing.set(true);
        	init2();
        }finally{
        	initializing.set(false);
        }
    }
    
    private void init2(){
        if(initialized){
            log.warn("Already initialized, call destroy()");
        	return;
        };

        failed = false;
        exceptions.clear();
        
        if(currentLoader != null){
        	currentLoader.stopListen();
        }
        LicenseProvider licenseProvider = new DefaultLicenseProvider();
        LicenseAndLoader l = loadAvailableLicense(/*licenseLoaders,*/ licenseProvider);
        
        if(l != null){
        	setLicense(l.license);
        	setProvider(licenseProvider);
        	currentLoader = l.licenseLoader;
        	currentLoader.startListen(licenseProvider);
        	//lastLoading = System.currentTimeMillis();
        	lastLoaded.set(System.currentTimeMillis());
        	
        	log.debug("License loaded.");
        	
        	displayLicense(l.license);
        }else{
        	String msg = "These is no available license in application.";
        	log.warn(msg);
        	failed = true;
        	exceptions.add(new LicenseException(msg));
        	setLicense(null);
        	currentLoader = null;
        	//log.debug("License load failed.");
        }
        
		initialized = true;
    }

    @Deprecated
	public void initialize(Reader reader) throws LicenseException, IOException {
		super.initialize(new DefaultLicenseProvider(), reader);
	}
	
    @Override
	public void initialize(LicenseProvider licenseProvider, Reader reader) throws LicenseException, IOException {
		throw new UnsupportedOperationException();
	}

	public void destroy() {
		if(currentLoader != null){
			currentLoader.stopListen();
		}
		initialized = false;
		// instance = null;
	}
    
	public boolean isFailed() {
		return failed;
	}
    
	@Override
	public License getLicense(){
		if(!initialized){
			callInit();
		}
		License license = super.getLicense();
		
		if(license != null){
			callCheck(license);
		}else{
			destroy();
		}
		return license;
	}
	
	private void callInit(){
		getExecutor().execute(new Runnable() {
			public void run() {
				init();
			}
		});
	}
	private void callCheck(final License license){
		getExecutor().execute(new Runnable() {
			public void run() {
				//System.out.println("call check");
				check(license);
			}
		});
	}
	private void check(final License license){
		if(checking.get()){
			log.debug("License is checking in another thread.");
			return;
		}
		try{
			checking.set(true);
			//System.out.println("call check2");
			check2(license);
		}finally{
			checking.set(false);
		}
	}
	
	private void check2(final License license){
		if(lastLoaded.get() > 0 && System.currentTimeMillis() - lastLoaded.get() > ONE_HOUR_MILLIS){
			destroy();
			//System.out.println("call check load");
			return;
		}

		if(currentLoader != null){
			//System.out.println("call loader.check");
			currentLoader.check(getProvider(), license, new CheckCallback(){
				public void checkFailed() {
					destroy();					
				}

				public void checkSuccess() {
					if(log.isDebugEnabled()){
						log.debug("callback check ok #" + (checkCount++));
					}
				}
			});
		}
	}
	
	
	private Executor getExecutor(){
		if(executor == null) {
			if(AppsGlobals.isSetup() && Application.isContextInitialized()){
				executor = Application.getContext().get("scheduledExecutor", Executor.class);
				log.debug("Using scheduledExecutor from Spring Context.");
			}else{
				log.debug("Create new cached thread pool executor.");
				executor = Executors.newCachedThreadPool();
				newExecutor = true;
			}
		}
		return executor;
	}
	
	public void shutdown(){
		destroy();
		if(newExecutor/* && executor instanceof ExecutorService*/){
			((ExecutorService) executor).shutdown();
		}
	}
	
	public final AppsLicense getAppsLicense(){
		License license = getLicense();
		if(license == null){
			return null;
		}
		if(license instanceof AppsLicense){
			return (AppsLicense) license;
		}
		return new AppsLicense(license/*getLicense()*/);
	}
	
    public final boolean isModuleAvailable(String module){
        if(module == null)
            return false;
        boolean isInstalled = isModuleLicensed(module);
        if(!isInstalled)
            return false;
        else
        	return AppsGlobals.getProperty("apps.module." + module + ".enabled", true);
    }
    
    private static void displayLicense(License lic){
    	AppsLicense license = new AppsLicense(lic);
    	String sp = System.getProperty("line.separator");
    	License.Version version = license.getVersion();
    	License.Client client = license.getClient();

    	StringBuffer sb = new StringBuffer();
    	sb.append("#").append(license.getID())
    			.append(" for ").append(license.getName())
    			.append("(")
    			.append(version.getVersionString())
    			.append(") to ")
    			.append(client.getName());
    	if(client.getCompany() != null){
    		sb.append(", ").append(client.getCompany());
    	}
    	sb.append(sp);
    	
    	
    	if(license.getExpirationDate() != null){
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    		sb.append("From ")
    			.append(format.format(license.getCreationDate()))
    			.append(" to ")
    			.append(format.format(license.getExpirationDate()))
    			.append(sp);
    	}else{
    		sb.append("No time limits").append(sp);
    	}
    	if(license.getGrantedIP() != null){
    		sb.append("IP granted to ").append(license.getGrantedIP()).append(sp);
    	}else{
    		sb.append("No ip limits").append(sp);
    	}
    	
    	//System.out.println(sp);
    	//System.out.println(sb.toString());
    	//System.out.flush();
    	log.info(sp + sb.toString());
    }
    
    private static class LicenseAndLoader{
    	public License license;
    	public LicenseLoader licenseLoader;
		public LicenseAndLoader(License license, LicenseLoader loader) {
			this.license = license;
			this.licenseLoader = loader;
		}
    }
    
    
    public static void main(String[] args) throws Exception{
//    	AppsGlobals.getBeansHolder().put("product", new Product(){
//			private static final long serialVersionUID = 1L;
//
//			public Collection<Module> getModules() {
//				List<License.Module> modules = new ArrayList<License.Module>();
//				modules.add(new License.Module("core"));
//				modules.add(new License.Module("base"));
//				return Collections.unmodifiableCollection(modules);
//			}
//
//			public String getName() {
//				return "touchstone";
//			}
//
//			public Version getVersion() {
//				return new License.Version(1, 0, 0);
//			}
//    	});
    	AppsLicense appsLicense = AppsLicenseManager.getInstance().getAppsLicense();
    	System.out.println(appsLicense);
    	AppsLicenseManager.getInstance().getAppsLicense();
    	AppsLicenseManager.getInstance().getAppsLicense();
    	AppsLicenseManager.getInstance().getAppsLicense();
    	AppsLicenseManager.getInstance().getAppsLicense();
    	
    	System.out.println("斤斤。。。。。。。。。。。");
    	
    	AppsLicenseManager.getInstance().shutdown();
    	
//    	AppsLicenseManager.getInstance().requestValidation();
//    	SecureRandom random = new SecureRandom();
//    	
//    	while(true){
//    		AppsLicenseManager.getInstance().requestValidation();
//    		Thread.sleep(random.nextInt(10000));
//    	}
    }



//	public static License createDefaultLicense() {
//		
//		
//		// TODO Auto-generated method stub
//		return null;
////		 DefaultLicenseProvider provider = new DefaultLicenseProvider();
////	        License defaultLicense = provider.getDefaultLicense();
////	        defaultLicense.setClient(new com.jivesoftware.license.License.Client("Evaluation User", ""));
////	        defaultLicense.setType(com.jivesoftware.license.License.Type.EVALUATION);
////	        defaultLicense.getProperties().put("defaultLicense", "true");
////	        return defaultLicense;
////	    }
//	}
	
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public static Log getLogger(){
//		return LOG;
//	}
	
    
//    public void requestValidation() throws SchedulerException{
//    	taskExecutor.execute(new Runnable() {
//			public void run() {
//				if(System.currentTimeMillis() - lastLoading > 1000 * 60){
//					check();
//				}else{
//					System.out.println("skip: " + new Date());
//				}
//			}
//		});
//    }
//    
//    private void check(){
//    	System.out.println("Checkinternal");
//    	displayLicense(getLicense());
//    }
}
