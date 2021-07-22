package org.opoo.apps.database;

import java.io.File;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsHome;

/**
 * 嵌入式数据源提供者。
 * 
 * 提供默认的 HSQLDB 数据库引擎，作为开发和测试使用。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class EmbeddedDataSourceProvider extends AbstractDataSourceProvider implements DataSourceProvider {
	private static final Log log = LogFactory.getLog(EmbeddedDataSourceProvider.class);
	
	/**
	 * 是否临时允许使用内嵌数据库。
	 * @return
	 */
	public static boolean isDatabaseEmbedded(){
		//System.out.println(System.getProperties());
		return Boolean.getBoolean("database.embedded");
	}
	
	@Override
	protected DataSource buildDataSource() throws Exception {
		Properties props = new Properties();
		String url = "jdbc:hsqldb:" + AppsHome.getAppsHome() + File.separator + "database" + File.separator + "apps";
		props.setProperty(DefaultDataSourceProvider.PROP_DRIVERCLASSNAME, "org.hsqldb.jdbcDriver");
		props.setProperty(DefaultDataSourceProvider.PROP_URL, url);
		props.setProperty(DefaultDataSourceProvider.PROP_MAXACTIVE, "50");
		props.setProperty(DefaultDataSourceProvider.PROP_MAXIDLE, "10");
		props.setProperty(DefaultDataSourceProvider.PROP_MAXWAIT, "12000");
		props.setProperty(DefaultDataSourceProvider.PROP_USERNAME, "sa");
		props.setProperty(DefaultDataSourceProvider.PROP_PASSWORD, "");
		try{
			return BasicDataSourceFactory.createDataSource(props);
		}finally{
			log.debug("database url: " + url);
			log.debug("Create datasource by BasicDataSourceFactory");
		}
	}
	
	public static void main(String[] args) throws Exception{
//		EmbeddedDataSourceProvider p = new EmbeddedDataSourceProvider();
//		DataSource ds = p.getDataSource();
//		System.out.println(ds);
//		Connection connection = ds.getConnection();
//		
//		connection.close();
//		
//		DataSourceManager.setDataSourceProvider(new EmbeddedDataSourceProvider());
		System.setProperty("database.embedded", "true");
		DataSource source = DataSourceManager.getRuntimeDataSource();
		System.out.println(DataSourceManager.getDataSourceProvider());
		System.out.println(source);
		MetaData data = DataSourceManager.getMetaData();
		System.out.println(data.getDatabaseType());
	}
}
