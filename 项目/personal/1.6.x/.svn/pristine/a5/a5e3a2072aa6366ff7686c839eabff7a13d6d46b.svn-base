package org.opoo.apps;

import org.opoo.apps.license.AppsLicenseManager;
import org.opoo.apps.module.ModuleManager;
import org.opoo.apps.security.PresenceManager;
import org.opoo.apps.security.UserManager;
import org.opoo.apps.service.AttachmentManager;
import org.opoo.apps.service.QueryService;
import org.springframework.context.ApplicationContext;


/**
 * 系统上下文环境。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface AppsContext {

	/**
	 * 根据对象名称、标识查找一个指定类型对象。
	 * 
	 * @param <T>
	 * @param name
	 * @param cls
	 * @return
	 */
	<T> T get(String name, Class<T> cls);


	/**
	 * 获取Spring的 ApplicationContext
	 * @return
	 */
	ApplicationContext getApplicationContext();
	
	/**
	 * 根据名称获取对象。
	 * 
	 * @param name
	 * @return 如果不存在返回null
	 */
	Object getBean(String name);
	
	/**
	 * 获取 License 管理器。
	 * @return
	 */
	AppsLicenseManager getLicenseManager();
	
	/**
	 * 获取模块管理器。
	 * @return
	 */
	ModuleManager getModuleManager();
	
	/**
	 * 获取用户管理器。
	 * @return
	 */
	UserManager getUserManager();
	
	/**
	 * 获取在线用户管理器。
	 * @return
	 */
	PresenceManager getPresenceManager();
	
	/**
	 * 获取附件管理器。
	 * @return
	 */
	AttachmentManager getAttachmentManager();
	
	
	
	/**
	 * 获取通用查询服务。
	 * 
	 * @return
	 */
	QueryService getQueryService();
}
