package org.opoo.apps.database;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.util.Assert;


/**
 * JNDI数据源提供者。
 * 
 * 使用应用服务器提供的数据源，使用JNDI方式访问。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class JndiDataSourceProvider extends AbstractDataSourceProvider implements DataSourceProvider {


	/**
	 * 
	 */
	public static final String jndiPropertyKeys[] = { 
			Context.APPLET, 
			Context.AUTHORITATIVE, 
			Context.BATCHSIZE,
			Context.DNS_URL, 
			Context.INITIAL_CONTEXT_FACTORY, 
			Context.LANGUAGE, 
			Context.OBJECT_FACTORIES,
			Context.PROVIDER_URL, 
			Context.REFERRAL, 
			Context.SECURITY_AUTHENTICATION, 
			Context.SECURITY_CREDENTIALS,
			Context.SECURITY_PRINCIPAL, 
			Context.SECURITY_PROTOCOL,
			Context.STATE_FACTORIES, 
			Context.URL_PKG_PREFIXES };
	public static final String JNDI_NAME_KEY = "datasource.jndiProvider.name";
	
	private static final List<String> jndiPropertyKeysList = Arrays.asList(jndiPropertyKeys);
	
	private static final Log log = LogFactory.getLog(JndiDataSourceProvider.class);
	
	private String dataSourceName;
	
	public JndiDataSourceProvider() {
		super();
		this.dataSourceName = AppsGlobals.getSetupProperty(JNDI_NAME_KEY);
	}
	public JndiDataSourceProvider(String jndiName){
		super();
		this.dataSourceName = jndiName;
	}

	/**
	 * 
	 * @param name
	 * @param value
	 */
	public void setProperty(String name, String value) {
		if ("name".equals(name)) {
			Assert.notNull(value, "JNDI 名称不能为空。");
			dataSourceName = value;
			AppsGlobals.setSetupProperty(JNDI_NAME_KEY, value);
		}else{
			if(jndiPropertyKeysList.contains(name)){
				if(StringUtils.isBlank(value)){
					AppsGlobals.deleteSetupProperty(name);
				}else{
					AppsGlobals.setSetupProperty(name, value);
				}
			}
		}
		
		//
		super.clearInstance();
	}

	public String getProperty(String name) {
		if ("name".equals(name))
			return dataSourceName;
		else
			return AppsGlobals.getSetupProperty(name);
	}

	@Override
	protected DataSource buildDataSource() throws Exception {
		if (StringUtils.isBlank(dataSourceName)) {
			throw new NamingException("No name specified for DataSource. JNDI lookup will fail");
		}
		try {
			Properties contextProperties = new Properties();
			for (String k : jndiPropertyKeys) {
				String v = AppsGlobals.getSetupProperty(k);
				if (v != null) {
					contextProperties.setProperty(k, v);
				}
			}

			Context context;
			if (contextProperties.size() > 0) {
				context = new InitialContext(contextProperties);
			} else {
				context = new InitialContext();
			}
			return (DataSource) context.lookup(dataSourceName);
		} catch (Exception e) {
			String msg = "Could not lookup DataSource at '" + dataSourceName + "'";
			log.error(msg);
			throw new Exception(msg, e);
		}
	} 
}
