package org.opoo.apps.license;

import com.jivesoftware.license.LicenseException;



/**
 * 应用程序 License 持有者。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class AppsLicenseHolder {
	
//	private static final Log log = LogFactory.getLog("License");
	
	private static AppsLicenseManager licenseManager = AppsLicenseManager.getInstance();
	private static AppsLicense license = licenseManager.getAppsLicense();
	private static long ONE_HOUR = 1L * 1000 * 60 * 60 * 1;
	private static long lastGet = 0L;
	
	private static ThreadLocal<AppsLicense> threadLicenseHolder = new ThreadLocal<AppsLicense>();
	
	public static synchronized AppsLicense getAppsLicense() throws LicenseException{
		if(licenseManager == null){
			licenseManager = AppsLicenseManager.getInstance();
		}
		return licenseManager.getAppsLicense();
	}
	/**
	 * 获取应用程序的 License。
	 * 注意，这个 License 会每隔 1 个小时重新初始化一次。
	 * 
	 * @return
	 * @throws LicenseException
	 */
	public static synchronized AppsLicense getAppsLicense_bak() throws LicenseException{
		if(licenseManager == null){
			licenseManager = AppsLicenseManager.getInstance();
		}
		
		if(lastGet == 0L){
			lastGet = System.currentTimeMillis();
		}
		
		//上次获取时间大于 1 个小时
		if(System.currentTimeMillis() - lastGet > ONE_HOUR){
			license = null;
		}
		
		if(license != null){
			if(license.getDevMode() != null){
				System.err.printf("*** 这是一个开发版本，授权给 %s，请勿用于产品 ***\n", license.getClient().getName());
			}
			return license;
		}
		
		//尝试次数
		int n = 10;
		int index = 0;
		while(license == null && index < n){
			licenseManager.destroy();
			//licenseManager.init();
			license = licenseManager.getAppsLicense();
			index++;
		}
		
		if(license == null){
			throw new AppsLicenseException("无法读取到有效的 License。");
		}
		
		if (license.getDevMode() != null) {
			System.err.printf("*** 这是一个开发版本，授权给 %s，请勿用于产品 ***\n", license.getClient().getName());
		}
		
		AppsLicenseLog.LOG.info("重新读取了 License 信息。");
	
		lastGet = System.currentTimeMillis();
		return license;
	}
	
	
	/**
	 * 获取当前线程的 License 对象。
	 * 
	 * @return
	 */
	public static AppsLicense getThreadAppsLicense(){
		return threadLicenseHolder.get();
	}
	
	
	/**
	 * 设置当前线程 License 对象。
	 * 
	 * @param license
	 */
	public static void setThreadAppsLicense(AppsLicense license){
		threadLicenseHolder.set(license);
	}
	
	
	
	/**
	 * 获取当前线程中的 License 对象，如果没有，则通过 License 管理器加载 License。
	 * 
	 * @return
	 * @throws LicenseException 加载及验证 License 出现异常
	 */
	public static AppsLicense getRequiredThreadAppsLicense() throws LicenseException{
		AppsLicense license = AppsLicenseHolder.getThreadAppsLicense();
		if(license == null){
			license = AppsLicenseHolder.getAppsLicense();
			AppsLicenseHolder.setThreadAppsLicense(license);
		}
		return license;
	}
}
