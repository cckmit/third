package org.opoo.apps.module;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * 模块管理器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface ModuleManager {

	/**
	 * 获取所有模块的Spring配置路径。
	 * 
	 * @return
	 */
	List<String> getConfigLocations();

	
	/**
	 * 安装模块。
	 * 
	 * @param file
	 * @return
	 * @throws ModuleException
	 */
	ModuleResultList installModule(File file) throws ModuleException;

	/**
	 * 获取所有模块。
	 * 
	 * @return
	 */
	Collection<Module<?>> getModules();

	/**
	 * 获取所有模块的类加载器。
	 * 
	 * @return
	 */
	Collection<ClassLoader> getClassLoaders();

	/**
	 * 获取指定模块。
	 * 
	 * @param name 模块名称
	 * @return
	 */
	Module<?> getModule(String name);

	/**
	 * 获取模块头信息。
	 * 
	 * @param name
	 * @return
	 */
	ModuleMetaData getMetaData(String name);

	/**
	 * 获取所有模块的资源。
	 * 
	 * @param locale
	 * @return
	 */
	List<ResourceBundle> getModuleResourceBundles(Locale locale);

	/**
	 * 获取指定模块的资源。
	 * 
	 * @param module
	 * @param locale
	 * @return
	 */
	ResourceBundle getModuleResourceBundle(Module<?> module, Locale locale);

	/**
	 * 卸载模块。
	 * 
	 * @param module
	 * @return
	 */
	ModuleResultList uninstallModule(Module<?> module);

	/**
	 * 获取模块头信息。
	 * 
	 * @param module
	 * @return
	 */
	ModuleMetaData getMetaData(Module<?> module);

	/**
	 * 获取模块类加载器。
	 * 
	 * @param module
	 * @return
	 */
	ModuleClassLoader getModuleClassloader(Module<?> module);

	/**
	 * 指定该管理器是不是已经初始化。
	 * 
	 * @return
	 */
	boolean isInitialized();

	/**
	 * 指定名称的模块是否是损坏的。
	 * 
	 * @param name
	 * @return
	 */
	boolean isModuleBroken(String name);

	/**
	 * 获取所有损坏的模块。
	 * 
	 * @return
	 */
	Map<String, String> getBrokenModules();

	/**
	 * 删除损坏的模块。
	 * 
	 * @param name
	 */
	void deleteBrokenModule(String name);
	
	/**
	 * 添加模块监听器。
	 * 
	 * @param listener
	 */
	void addModuleListener(ModuleListener listener);
	
	/**
	 * 删除指定模块监听器。
	 * 
	 * @param listener
	 */
	void removeModuleListener(ModuleListener listener);
}
