package org.opoo.apps.web.struts2.action.admin.setup;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.database.DataSourceProvider;
import org.opoo.apps.database.EmbeddedDataSourceProvider;
import org.opoo.apps.database.JndiDataSourceProvider;
import org.opoo.apps.database.spring.DatabaseProperties;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.opensymphony.xwork2.ActionSupport;



public class DataSourceSetupAction extends SetupActionSupport
{
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -5264067952360114377L;

	private static final Log log = LogFactory.getLog(DataSourceSetupAction.class);

    public static final String CONNECTION_FAILED = "connection.failed";
    public static final String NO_APPS_TABLES = "no.apps.tables";
    public static final String EMBEDDED_MODE = "embedded";
    public static final String THIRD_PARTY_MODE = "thirdparty";
    public static final String JNDI_MODE = "jndi";
    protected String mode = THIRD_PARTY_MODE;
    protected DataSourceTransactionManager transactionManager;


    public String getMode()
    {
        return mode;
    }

    public void setMode(String mode)
    {
        this.mode = mode;
    }
    
    public void prepare() throws Exception {
		String prop = AppsGlobals.getSetupProperty(DataSourceProvider.DATA_SOURCE_PROVIDER_CLASS);
		System.out.println("CLASS: " + prop);
		if (EmbeddedDataSourceProvider.class.getName().equals(prop))
			mode = EMBEDDED_MODE;
		else if (JndiDataSourceProvider.class.getName().equals(prop))
			mode = JNDI_MODE;
		
		log.debug("Now mode is " + mode);
	}


    public String doDefault()
    {
        getSession().put("apps.setup.sidebar.0", "done");
        getSession().put("apps.setup.sidebar.1", "in_progress");
        getSession().put("apps.setup.sidebar.2", "incomplete");
        getSession().put("apps.setup.sidebar.3", "incomplete");
        getSession().put("apps.setup.sidebar.4", "incomplete");
        return ActionSupport.INPUT;
    }

    public String doContinue()
    {
        DatabaseProperties.getInstance().init();
        getSession().put("apps.setup.sidebar.0", "done");
        getSession().put("apps.setup.sidebar.1", "in_progress");
        getSession().put("apps.setup.sidebar.2", "incomplete");
        getSession().put("apps.setup.sidebar.3", "incomplete");
        getSession().put("apps.setup.sidebar.4", "incomplete");
        return "next";
    }

    public String execute()
    {
    	  if(EMBEDDED_MODE.equals(mode))
          {
              AppsGlobals.setSetupProperty(DataSourceProvider.DATA_SOURCE_PROVIDER_CLASS, EmbeddedDataSourceProvider.class.getName());
              EmbeddedDataSourceProvider dsp = new EmbeddedDataSourceProvider();
              testConnection(dsp);
              
              if(hasActionErrors()){
            	  return ActionSupport.ERROR;
              }else{
            	  return "embedded";
              }
          }
          if(THIRD_PARTY_MODE.equals(mode))
              return "thirdparty";
          if(JNDI_MODE.equals(mode))
              return "jndi";
          else
              return ActionSupport.ERROR;
    }


    protected boolean testConnection(DataSourceProvider dsp){
    	boolean success = true;
    	if(log.isDebugEnabled()){
    		log.debug("Testing database connection for datasource: " + dsp);
    	}
    	Connection conn = null;
    	DataSource ds = null;
    	try{
    		
    		ds = dsp.getDataSource();
    		conn = DataSourceUtils.getConnection(ds);
    		if(conn == null){
    			success = false;
                addActionError("无法获取数据库连接。");
                if(log.isDebugEnabled()){
                    log.debug("Unable to retrieve database connection");
                }
    		}
    	
    		//测试所需要的表是否存在
    		try{
    			conn.prepareStatement("select count(*) from sec_users").executeQuery();
    		}catch(Exception e){
    			log.error(e.getMessage(), e);
    			throw new Exception("数据库中不存在系统所需的表。");
    		}
    	
    	}catch(Exception e){
    		success = false;
    		String msg = "无法正确连接数据库";
    		addActionError(msg + ": " + e.getMessage());
    		log.error(msg, e);
    	}finally{
    		DataSourceUtils.releaseConnection(conn, ds);
    	}
    	return success;
    }
}

