package org.opoo.apps.module.dao;

import java.io.InputStream;
import java.util.List;


/**
 * 模块数据访问接口。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface ModuleDao {

	/**
	 * 创建模块。
	 * 
	 * @param module 模块Bean
	 * @return 已经创建完成的模块Bean
	 */
	ModuleBean create(ModuleBean module);
	/**
	 * 删除指定的模块。
	 * 
	 * @param module 要删除的模块的模块Bean
	 */
	void delete(ModuleBean module);

	/**
	 * 删除指定名称的模块。
	 * @param name 模块名称
	 */
	void delete(String name);

	/**
	 * 通过模块名称查找模块。
	 * @param s 模块名称
	 * @return 模块bean
	 */
	ModuleBean getByName(String s);

	/**
	 * 获取全部有效模块。
	 * 
	 * @return 模块bean集合。
	 */
	List<ModuleBean> getModuleBeans();

	/**
	 * 为指定的模块设置模块包数据流。
	 * 
	 * @param module 模块Bean
	 * @param contentLength 模块包数据流字节长度
	 * @param inputstream 模块包数据流
	 */
	void setModuleData(ModuleBean module, int contentLength, InputStream inputstream);

	/**
	 * 获取指定模块的模块包数据流。
	 * 
	 * @param module 模块bean
	 * @return 模块包数据流
	 */
	InputStream getModuleData(ModuleBean module);

	/**
	 * 创建模块。
	 * 
	 * @param module 要创建的模块Bean
	 * @param contentLength 模块包的数据流字节长度
	 * @param inputstream 模块包数据流
	 * @return 返回已创建的模块Bean
	 */
	ModuleBean create(ModuleBean module, int contentLength, InputStream inputstream);
}
