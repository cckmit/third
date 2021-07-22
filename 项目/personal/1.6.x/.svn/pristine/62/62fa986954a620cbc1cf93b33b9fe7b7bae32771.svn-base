package org.opoo.apps.web.struts2.action.admin.setup;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.database.DataSourceProvider;
import org.opoo.apps.database.DefaultDataSourceProvider;
import org.opoo.apps.database.spring.DatabaseProperties;
import org.springframework.util.ClassUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Validateable;

public class ThirdPartyDataSourceSetupAction extends DataSourceSetupAction implements Validateable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3505173630409149211L;
	private static final Log log = LogFactory.getLog(ThirdPartyDataSourceSetupAction.class);
    private String username;
    private String password;
    private int maxActive = 25;
    private int maxIdle = 5;
    private long maxWait = 12000;
    private String driverClassName;
    private String url;
    private boolean defaultAutoCommit = true;
	private boolean success = false;

    public void prepare()
    {
    	Properties props = DefaultDataSourceProvider.loadPropertiesFromSetupProperties();
    	username = props.getProperty(DefaultDataSourceProvider.PROP_USERNAME);
    	password = props.getProperty(DefaultDataSourceProvider.PROP_PASSWORD);
    	driverClassName = props.getProperty(DefaultDataSourceProvider.PROP_DRIVERCLASSNAME);
        url = props.getProperty(DefaultDataSourceProvider.PROP_URL);
        String sMaxActive = props.getProperty(DefaultDataSourceProvider.PROP_MAXACTIVE);
        String sMaxIdle = props.getProperty(DefaultDataSourceProvider.PROP_MAXIDLE);
        String sMaxWait = props.getProperty(DefaultDataSourceProvider.PROP_MAXWAIT);
        String sDefaultAutoCommit = props.getProperty(DefaultDataSourceProvider.PROP_DEFAULTAUTOCOMMIT);
        try {
        	if(sMaxActive != null){
        		maxActive = Integer.parseInt(sMaxActive);
        	}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		try {
			if (sMaxIdle != null) {
				maxIdle = Integer.parseInt(sMaxIdle);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		try {
			if (sMaxWait != null) {
				maxWait = Long.parseLong(sMaxWait);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}	
		try {
			if (sDefaultAutoCommit != null) {
				defaultAutoCommit = Boolean.parseBoolean(sDefaultAutoCommit);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}	
    }

    public String doDefault()
    {
        getSession().put("apps.setup.sidebar.0", "done");
        getSession().put("apps.setup.sidebar.1", "done");
        getSession().put("apps.setup.sidebar.2", "in_progress");
        getSession().put("apps.setup.sidebar.3", "incomplete");
        getSession().put("apps.setup.sidebar.4", "incomplete");
        getSession().put("apps.setup.sidebar.5", "incomplete");
        getSession().put("apps.setup.sidebar.6", "incomplete");
        return ActionSupport.INPUT;
    }

    public void validateExecute() {
    	if(StringUtils.isBlank(driverClassName)){
    		addActionError("必须指定 JDBC 驱动类。");
    	}
    	if(StringUtils.isBlank(url)){
    		addActionError("必须指定 JDBC URL。");
    	}
    	if(hasActionErrors()){
    		return;
    	}
		try {
			ClassUtils.forName(driverClassName);
		} catch (Exception cnfe) {
			addActionError("无法加载驱动程序：" + driverClassName);
		}
	}

    public String execute() {
		AppsGlobals.setSetupProperty(DataSourceProvider.DATA_SOURCE_PROVIDER_CLASS, DefaultDataSourceProvider.class.getName());
		Properties props = buildProperties();
		DefaultDataSourceProvider dsp = new DefaultDataSourceProvider();
		dsp.setDataSourceProperties(props);
		testConnection(dsp);

		if (hasActionErrors()) {
			return ActionSupport.ERROR;
		}
		DatabaseProperties.getInstance().init();
		getSession().put("apps.setup.sidebar.0", "done");
		getSession().put("apps.setup.sidebar.1", "done");
		getSession().put("apps.setup.sidebar.2", "done");
		getSession().put("apps.setup.sidebar.3", "in_progress");
		getSession().put("apps.setup.sidebar.4", "incomplete");
		getSession().put("apps.setup.sidebar.5", "incomplete");
		getSession().put("apps.setup.sidebar.6", "incomplete");
		return "next";
	}

    private Properties buildProperties()
    {
       	Properties props = new Properties();
    	props.setProperty(DefaultDataSourceProvider.PROP_USERNAME, username);
    	props.setProperty(DefaultDataSourceProvider.PROP_PASSWORD, password);
    	props.setProperty(DefaultDataSourceProvider.PROP_DRIVERCLASSNAME, driverClassName);
        props.setProperty(DefaultDataSourceProvider.PROP_URL, url);
        props.setProperty(DefaultDataSourceProvider.PROP_MAXACTIVE, String.valueOf(maxActive));
        props.setProperty(DefaultDataSourceProvider.PROP_MAXIDLE, String.valueOf(maxIdle));
        props.setProperty(DefaultDataSourceProvider.PROP_MAXWAIT, String.valueOf(maxWait));
        props.setProperty(DefaultDataSourceProvider.PROP_DEFAULTAUTOCOMMIT, String.valueOf(defaultAutoCommit));
        return props;
    }
    
    public void validateTest(){
    	validateExecute();
    }
    
    public String doTest(){
    	Properties props = buildProperties();
    	DefaultDataSourceProvider dsp = new DefaultDataSourceProvider(props);
    	testConnection(dsp);
    	if(!hasActionErrors()){
    		addActionMessage("数据源测试通过");
    		success = true;
    	}
		return ActionSupport.INPUT;
    }


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}



	/**
	 * @return the maxIdle
	 */
	public int getMaxIdle() {
		return maxIdle;
	}

	/**
	 * @param maxIdle the maxIdle to set
	 */
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	/**
	 * @return the maxWait
	 */
	public long getMaxWait() {
		return maxWait;
	}

	/**
	 * @param maxWait the maxWait to set
	 */
	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}

	/**
	 * @return the driverClassName
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * @param driverClassName the driverClassName to set
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the defaultAutoCommit
	 */
	public boolean isDefaultAutoCommit() {
		return defaultAutoCommit;
	}

	/**
	 * @param defaultAutoCommit the defaultAutoCommit to set
	 */
	public void setDefaultAutoCommit(boolean defaultAutoCommit) {
		this.defaultAutoCommit = defaultAutoCommit;
	}

	/**
	 * @return the maxActive
	 */
	public int getMaxActive() {
		return maxActive;
	}

	/**
	 * @param maxActive the maxActive to set
	 */
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
}
