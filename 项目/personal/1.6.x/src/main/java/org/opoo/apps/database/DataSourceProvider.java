package org.opoo.apps.database;

import javax.sql.DataSource;

/**
 * ����Դ�ṩ�ߡ�
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface DataSourceProvider {
	/**
	 * ����Դ�ṩ�������������е��������ơ�
	 */
	String DATA_SOURCE_PROVIDER_CLASS = "dataSourceProvider.className";
	
	/**
	 * ��ȡ����Դ��
	 * @return
	 * @throws Exception 
	 */
	DataSource getDataSource() throws Exception;
	
	
	/**
	 * ��������Դ
	 */
	void destroy();
}
