package org.opoo.apps.license;

import java.net.URL;

import org.springframework.core.io.Resource;

/**
 * 实例信息加载器。
 * 
 * @author lcj
 *
 */
public class AppsInstanceLoader {
	
	private static AppsInstance instance;
	
	/**
	 * 初始化Loader。
	 * 
	 * @param resource 实例信息所在的资源的Resource。
	 */
	public static synchronized void initialize(Resource resource){
		AppsInstanceLoader.setAppsInstance(new DefaultInstance(resource));
	}
	
	/**
	 * 初始化Loader。
	 * 
	 * @param url 实例信息所在资源的URL。
	 */
	public static synchronized void initialize(URL url){
		AppsInstanceLoader.setAppsInstance(new DefaultInstance(url));
	}
	
	/**
	 * 设置当前实例。
	 * 
	 * @param instance
	 */
	public static synchronized void setAppsInstance(AppsInstance instance){
		AppsInstanceLoader.instance = instance;
	}
	
	/**
	 * 获取当前实例的实例对象。
	 * 
	 * @return 实例对象
	 */
	public static AppsInstance getAppsInstance(){
		if(AppsInstanceLoader.instance == null){
			throw new AppsLicenseException("Instance information not loaded, please call " +
					"AppsInstanceLoader.initialize() or AppsInstanceLoader.setAppsInstance() first.");
		}
		return AppsInstanceLoader.instance; 
	}
	
	/**
	 * 获取当前实例的指定属性值。
	 * 
	 * @param prop 属性名称
	 * @return 属性值
	 * @see AppsInstance#getProperty(String)
	 */
	public static String getProperty(String prop){
		return getAppsInstance().getProperty(prop);
	}
}
