package org.opoo.apps.database;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;



/**
 * 默认的数据源提供者。
 * 
 * 使用 DBCP 的数据库连接池。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class DefaultDataSourceProvider extends AbstractDataSourceProvider implements DataSourceProvider {
    
	private static final Log log = LogFactory.getLog(DefaultDataSourceProvider.class);

    public final static String PROP_DEFAULTAUTOCOMMIT = "defaultAutoCommit";
    public final static String PROP_DEFAULTREADONLY = "defaultReadOnly";
    public final static String PROP_DEFAULTTRANSACTIONISOLATION = "defaultTransactionIsolation";
    public final static String PROP_DEFAULTCATALOG = "defaultCatalog";
    public final static String PROP_DRIVERCLASSNAME = "driverClassName";
    public final static String PROP_MAXACTIVE = "maxActive";
    public final static String PROP_MAXIDLE = "maxIdle";
    public final static String PROP_MINIDLE = "minIdle";
    public final static String PROP_INITIALSIZE = "initialSize";
    public final static String PROP_MAXWAIT = "maxWait";
    public final static String PROP_TESTONBORROW = "testOnBorrow";
    public final static String PROP_TESTONRETURN = "testOnReturn";
    public final static String PROP_TIMEBETWEENEVICTIONRUNSMILLIS = "timeBetweenEvictionRunsMillis";
    public final static String PROP_NUMTESTSPEREVICTIONRUN = "numTestsPerEvictionRun";
    public final static String PROP_MINEVICTABLEIDLETIMEMILLIS = "minEvictableIdleTimeMillis";
    public final static String PROP_TESTWHILEIDLE = "testWhileIdle";
    public final static String PROP_PASSWORD = "password";
    public final static String PROP_URL = "url";
    public final static String PROP_USERNAME = "username";
    public final static String PROP_VALIDATIONQUERY = "validationQuery";
    public final static String PROP_ACCESSTOUNDERLYINGCONNECTIONALLOWED = "accessToUnderlyingConnectionAllowed";
    public final static String PROP_REMOVEABANDONED = "removeAbandoned";
    public final static String PROP_REMOVEABANDONEDTIMEOUT = "removeAbandonedTimeout";
    public final static String PROP_LOGABANDONED = "logAbandoned";
    public final static String PROP_POOLPREPAREDSTATEMENTS = "poolPreparedStatements";
    public final static String PROP_MAXOPENPREPAREDSTATEMENTS = "maxOpenPreparedStatements";
    public final static String PROP_CONNECTIONPROPERTIES = "connectionProperties";
    /**
     * 
     */
    private final static String[] ALL_PROPERTIES = {
        PROP_DEFAULTAUTOCOMMIT,
        PROP_DEFAULTREADONLY,
        PROP_DEFAULTTRANSACTIONISOLATION,
        PROP_DEFAULTCATALOG,
        PROP_DRIVERCLASSNAME,
        PROP_MAXACTIVE,
        PROP_MAXIDLE,
        PROP_MINIDLE,
        PROP_INITIALSIZE,
        PROP_MAXWAIT,
        PROP_TESTONBORROW,
        PROP_TESTONRETURN,
        PROP_TIMEBETWEENEVICTIONRUNSMILLIS,
        PROP_NUMTESTSPEREVICTIONRUN,
        PROP_MINEVICTABLEIDLETIMEMILLIS,
        PROP_TESTWHILEIDLE,
        PROP_PASSWORD,
        PROP_URL,
        PROP_USERNAME,
        PROP_VALIDATIONQUERY,
        PROP_ACCESSTOUNDERLYINGCONNECTIONALLOWED,
        PROP_REMOVEABANDONED,
        PROP_REMOVEABANDONEDTIMEOUT,
        PROP_LOGABANDONED,
        PROP_POOLPREPAREDSTATEMENTS,
        PROP_MAXOPENPREPAREDSTATEMENTS,
        PROP_CONNECTIONPROPERTIES
    };
    
    public static final String DATA_SOURCE_PROPS_PREFIX = "datasource.defaultProvider.";
    
    private Properties dataSourceProperties;
    
    /**
     * 从配置文件读取属性，创建对象实例。
     */
    public DefaultDataSourceProvider(){
    	Properties properties = loadPropertiesFromSetupProperties();
    	//System.out.println(properties);
    	
    	if(properties.get(PROP_MINIDLE) != null){
    		properties.setProperty(PROP_MINIDLE, "20");
    	}
    	if(properties.get(PROP_MAXACTIVE) != null){
    		properties.setProperty(PROP_MAXACTIVE, "50");
    	}
    	if(properties.getProperty(PROP_MAXWAIT) != null){
    		properties.setProperty(PROP_MAXWAIT, "12000");
    	}
       	this.dataSourceProperties = properties;
    }
    /**
     * 根据参数创建对象实例。
     * @param properties 创建数据源的参数
     */
	public DefaultDataSourceProvider(Properties properties){
    	if(properties.get(PROP_MINIDLE) != null){
    		properties.setProperty(PROP_MINIDLE, "20");
    	}
    	if(properties.get(PROP_MAXACTIVE) != null){
    		properties.setProperty(PROP_MAXACTIVE, "50");
    	}
    	if(properties.getProperty(PROP_MAXWAIT) != null){
    		properties.setProperty(PROP_MAXWAIT, "12000");
    	}
       	this.dataSourceProperties = properties;
    }
	
    private static String buildKey(String key){
    	return DATA_SOURCE_PROPS_PREFIX + key;
    }
    
    public static Properties loadPropertiesFromSetupProperties() {
    	Properties props = new Properties();
    	for(String key: ALL_PROPERTIES){
    		String value = AppsGlobals.getSetupProperty(buildKey(key));
    		if(StringUtils.isNotBlank(value)){
    			props.put(key, value);
    		}
    	}
		return props;
	}
    
    private static void savePropertiesAsSetupProperties(Properties props){
    	for(String key: ALL_PROPERTIES){
    		String value = props.getProperty(key);
    		if(StringUtils.isNotBlank(value)){
    			AppsGlobals.setSetupProperty(buildKey(key), value);
    		}else{
    			AppsGlobals.deleteSetupProperty(buildKey(key));
    		}
    	}
    }
	
	@Override
	protected DataSource buildDataSource() throws Exception{
		try{
			return BasicDataSourceFactory.createDataSource(dataSourceProperties);
		}finally{
			log.debug("Create datasource by BasicDataSourceFactory.");
		}
	}

	/**
	 * @return the dataSourceProperties
	 */
	public Properties getDataSourceProperties() {
		return dataSourceProperties;
	}

	/**
	 * @param dataSourceProperties
	 *            the dataSourceProperties to set
	 */
	public void setDataSourceProperties(Properties dataSourceProperties) {
		this.dataSourceProperties = dataSourceProperties;
		savePropertiesAsSetupProperties(dataSourceProperties);
		//设置属性后清空缓存对象
		super.clearInstance();
	}
}
