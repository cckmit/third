package org.opoo.apps.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.database.impl.MetaDataImpl;
import org.springframework.jdbc.datasource.DataSourceUtils;


/**
 * 数据源管理器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class DataSourceManager {

	private static final Log log = LogFactory.getLog(DataSourceManager.class);
	private static DataSourceProvider dataSourceProvider;
	private static MetaData metaData;
	private static final Object providerLock = new Object();
	
	private DataSourceManager(){
	}

	/**
	 * @return the dataSourceProvider
	 */
	public static DataSourceProvider getDataSourceProvider() {
		if(dataSourceProvider == null){
			//临时使用内嵌的数据库
			if(EmbeddedDataSourceProvider.isDatabaseEmbedded()){
				log.debug("database.emebedded : true");
				setDataSourceProvider(new EmbeddedDataSourceProvider());
			}else{
				String className = AppsGlobals.getSetupProperty(DataSourceProvider.DATA_SOURCE_PROVIDER_CLASS);
				synchronized(providerLock){
					if(dataSourceProvider == null){
						if (className != null) {
							try {
								Class<?> conClass = Class.forName(className);
								setDataSourceProvider((DataSourceProvider) conClass.newInstance());
							} catch (Exception e) {
								log.error("Warning: failed to create the data source provider specified by dataSourceProvider.className. Using the default pool.",
												e);
								setDataSourceProvider(new DefaultDataSourceProvider());
							}
						} else {
							setDataSourceProvider(new DefaultDataSourceProvider());
						}
					}
				}
			}
		}
		
		return dataSourceProvider;
	}

	/**
	 * @param dataSourceProvider the dataSourceProvider to set
	 */
	public static void setDataSourceProvider(DataSourceProvider dataSourceProvider) {
		DataSourceManager.dataSourceProvider = dataSourceProvider;
		if(dataSourceProvider != null){
			String className = dataSourceProvider.getClass().getName();
			
			//同步配置，如果指定临时调整，不变更配置
			if(!EmbeddedDataSourceProvider.isDatabaseEmbedded()){
				AppsGlobals.setSetupProperty(DataSourceProvider.DATA_SOURCE_PROVIDER_CLASS, className);
			}
		}
		metaData = null;
	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static DataSource getDataSource() throws Exception{
		return getDataSourceProvider().getDataSource();
	}
	
	public static DataSource getRuntimeDataSource(){
		try {
			return getDataSource();
		} catch (Exception e) {
			throw new CannotGetDataSourceException("创建数据源出错", e);
		}
	}
	
	
	public static MetaData getMetaData() {
		if (metaData == null) {
			DataSource dataSource = getRuntimeDataSource();
			Connection conn = null;
			try {
				//System.out.println("获取数据库Meta：" + dataSource);
				log.debug("获取数据库 Meta ...");
				conn = DataSourceUtils.getConnection(dataSource);//dataSource.getConnection();
				DatabaseMetaData databaseMetaData = conn.getMetaData();
				metaData = new MetaDataImpl(databaseMetaData);
			} catch (Exception e) {
				log.error(e);
			} finally {
				DataSourceUtils.releaseConnection(conn, dataSource);
//				if (conn != null) {
//					try {
//						conn.close();
//					} catch (SQLException e) {
//					}
//				}
			}
		}
		return metaData;
	}
}