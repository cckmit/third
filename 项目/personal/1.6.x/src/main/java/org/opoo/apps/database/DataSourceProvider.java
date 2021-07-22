package org.opoo.apps.database;

import javax.sql.DataSource;

/**
 * 数据源提供者。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface DataSourceProvider {
	/**
	 * 数据源提供者类名在配置中的属性名称。
	 */
	String DATA_SOURCE_PROVIDER_CLASS = "dataSourceProvider.className";
	
	/**
	 * 获取数据源。
	 * @return
	 * @throws Exception 
	 */
	DataSource getDataSource() throws Exception;
	
	
	/**
	 * 销毁数据源
	 */
	void destroy();
}
