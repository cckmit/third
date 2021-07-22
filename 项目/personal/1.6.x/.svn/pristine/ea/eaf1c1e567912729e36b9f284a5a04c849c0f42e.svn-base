package org.opoo.apps.web.struts2.action.admin.setup;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.DialectFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.database.DataSourceManager;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.opensymphony.xwork2.ActionSupport;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class HibernateSetupAction extends SetupActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5190084774792473563L;

	public static final String HIBERNATE_DIALECT = "hibernate.dialect";
	//public static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	
	private static final Log log = LogFactory.getLog(HibernateSetupAction.class);
	private String dialect;
	private String hibernateProperties;
	
	
	public String doDefault(){
		loadHibernateProperties();
		if(dialect == null){
			dialect = determinDialect();
		}
		return ActionSupport.INPUT;
	}
	
	public void validateExecute(){
		if(StringUtils.isBlank(dialect)){
			addActionError("请输入 Hibernate 方言。");
			return;
		}
		try{
			DialectFactory.buildDialect(dialect);
		}catch(Exception e){
			addActionError("输入 Hibernate 方言不正确：" + e.getMessage());
			log.error("输入 Hibernate 方言不正确", e);
			return;
		}
		if(StringUtils.isBlank(hibernateProperties)){
			addActionError("请输入 Hibernate 属性。");
		}
	}
	
	public String execute(){
		try{
			PropertiesEditor pe = new PropertiesEditor();
			pe.setAsText(hibernateProperties);
			Properties pp = (Properties) pe.getValue();
			if(pp.isEmpty()){
				addActionError("Hibernate 属性不能为空。");
				return ActionSupport.ERROR;
			}
			
			pp.setProperty(HIBERNATE_DIALECT, dialect);
			
			saveHibernateProperties(pp);
		}catch(Exception e){
			addActionError("Hibernate 属性格式不正确。");
			log.error("Hibernate 属性格式不正确", e);
			return ActionSupport.ERROR;
		}
		
		return "next";
	}
	
//	@Override
//	public void prepare() throws Exception{
//		loadHibernateProperties();
//		if(dialect == null){
//			dialect = determinDialect();
//		}
//	}
	
	private void loadHibernateProperties(){
		List<String> names = AppsGlobals.getSetupPropertyNames();
		Properties props = new Properties();
		for(String name: names){
			if(name.startsWith("hibernate.")){
				String value = AppsGlobals.getSetupProperty(name);
				if(HIBERNATE_DIALECT.equals(name)){
					dialect = value;
				}else{
					props.put(name, value);
				}
			}
		} 
		if(props.isEmpty()){
			/**
			    hibernate.cache.provider_class=com.tangosol.coherence.hibernate.CoherenceCacheProvider
				hibernate.cache.use_minimal_puts=true
				hibernate.show_sql=false
				hibernate.jdbc.batch_size=25
				hibernate.cache.use_query_cache=true
				hibernate.jdbc.fetch_size=50
			 */
			props.setProperty("hibernate.jdbc.batch_size", "25");
			props.setProperty("hibernate.jdbc.fetch_size", "50");
			props.setProperty("hibernate.show_sql", "false");
			//props.setProperty("hibernate.cache.provider_class", "com.tangosol.coherence.hibernate.CoherenceCacheProvider");
			//props.setProperty("hibernate.cache.use_minimal_puts", "true");
			//props.setProperty("hibernate.cache.use_query_cache", "true");
			//很严重，在SessionFactory结束时会删除表
			//props.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		}
		StringBuffer sb = new StringBuffer();
		Set<Entry<Object,Object>> set = props.entrySet();
		for(Entry<Object,Object> en: set){
			sb.append((String)en.getKey()).append("=").append((String)en.getValue()).append("\n");
		}
		hibernateProperties = sb.toString();
	}
	
	private void saveHibernateProperties(Properties properties){
		Set<Entry<Object,Object>> set = properties.entrySet();
		for(Entry<Object,Object> en: set){
			if(en.getValue() == null){
				AppsGlobals.deleteSetupProperty((String)en.getKey());
			}else{
				AppsGlobals.setSetupProperty((String)en.getKey(), (String)en.getValue());
			}
		}
	}
	
	private String determinDialect(){
		try{
			DataSource ds = DataSourceManager.getDataSource();
			
			Connection conn = DataSourceUtils.getConnection(ds);
			String databaseName;
			int databaseMajorVersion;
			try {
				DatabaseMetaData meta = conn.getMetaData();
				databaseName = meta.getDatabaseProductName();
				databaseMajorVersion = getDatabaseMajorVersion(meta);
				log.info("RDBMS: " + databaseName + ", version: " + meta.getDatabaseProductVersion() );
				log.info("JDBC driver: " + meta.getDriverName() + ", version: " + meta.getDriverVersion() );
				
				Dialect dialect = DialectFactory.determineDialect(databaseName, databaseMajorVersion);
				if(dialect != null){
					return dialect.getClass().getName();
				}
			}finally{
				DataSourceUtils.releaseConnection(conn, ds);
			}
		}catch(Exception e){
			log.error("could not determin hibernate dialect.", e);
		}
		return null;
	}
	
	private int getDatabaseMajorVersion(DatabaseMetaData meta) {
		try {
			Method gdbmvMethod = DatabaseMetaData.class.getMethod("getDatabaseMajorVersion");
			return ( (Integer) gdbmvMethod.invoke(meta) ).intValue();
		}
		catch (NoSuchMethodException nsme) {
			return 0;
		}
		catch (Throwable t) {
			log.debug("could not get database version from JDBC metadata");
			return 0;
		}
	}

	/**
	 * @return the dialect
	 */
	public String getDialect() {
		return dialect;
	}

	/**
	 * @param dialect the dialect to set
	 */
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	/**
	 * @return the hibernateProperties
	 */
	public String getHibernateProperties() {
		return hibernateProperties;
	}

	/**
	 * @param hibernateProperties the hibernateProperties to set
	 */
	public void setHibernateProperties(String hibernateProperties) {
		this.hibernateProperties = hibernateProperties;
	}
}
