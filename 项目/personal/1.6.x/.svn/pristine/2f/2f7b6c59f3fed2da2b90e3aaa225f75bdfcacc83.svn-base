package org.opoo.apps.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.FactoryBean;


/**
 * 数据源工厂。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class DataSourceFactoryBean implements FactoryBean {
	
//	public static final String DATA_SOURCE_PROVIDER_CLASS = DataSourceProvider.DATA_SOURCE_PROVIDER_CLASS;
	
//	private static final Object providerLock = new Object();
	//private static final Log log = LogFactory.getLog(DataSourceFactoryBean.class);
	
//	private DataSourceProvider dataSourceProvider;
	
	/*************************************************
	public Object getObject2() throws Exception {
	    String className = AppsGlobals.getSetupProperty(DATA_SOURCE_PROVIDER_CLASS);
		String newProviderClassName = null;
		DataSource dataSource;
		synchronized (providerLock) {
			if (dataSourceProvider == null) {
				if (className != null) {
					try {
						Class<?> conClass = Class.forName(className);
						setDataSourceProvider((DataSourceProvider) conClass.newInstance());
						newProviderClassName = className;
					} catch (Exception e) {
						log.error("Warning: failed to create the connection provider specified by dataSourceProvider.className. Using the default pool.",
										e);
						setDataSourceProvider(new DefaultDataSourceProvider());
						newProviderClassName = DefaultDataSourceProvider.class.getName();
					}
				} else {
					setDataSourceProvider(new DefaultDataSourceProvider());
					newProviderClassName = DefaultDataSourceProvider.class.getName();
				}
			}
			dataSource = dataSourceProvider.getDataSource();
		}
		if (newProviderClassName != null) {
			AppsGlobals.setSetupProperty(DATA_SOURCE_PROVIDER_CLASS, newProviderClassName);
		}
//        if(tokenReplaceDatabaseNames)
//            dataSource = new TokenReplaceDataSourceWrapper(dataSource, tokenReplaceMappings);
//        if(profilingEnabled)
//            dataSource = new ProfiledDataSourceWrapper(dataSource);
//        if(transactionTrackingEnabled)
//            dataSource = new TransactionalTrackingDataSourceWrapper(dataSource);
        return dataSource;
	}
	
	*****************************************/

	public Class<DataSource> getObjectType() {
		return DataSource.class;
	}

	public boolean isSingleton() {
		return true;
	}

	/**
	 * @return the dataSourceProvider
	 */
	public DataSourceProvider getDataSourceProvider() {
		return DataSourceManager.getDataSourceProvider();
	}



	public Object getObject() throws Exception {
		return DataSourceManager.getDataSourceProvider().getDataSource();
	}

}
