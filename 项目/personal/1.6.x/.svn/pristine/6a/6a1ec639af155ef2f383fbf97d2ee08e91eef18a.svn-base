package org.opoo.apps.web.struts2.action.admin.setup;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.database.DataSourceProvider;
import org.opoo.apps.database.JndiDataSourceProvider;

import com.opensymphony.xwork2.ActionSupport;


public class JndiDataSourceSetupAction extends DataSourceSetupAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1534679829592124662L;
	private static final Log log = LogFactory.getLog(JndiDataSourceSetupAction.class);
	protected String jndiName;
	protected String jndiNameMode;
	protected NamingEnumeration<Binding> bindings;

	public NamingEnumeration<Binding> getBindings() {
		return bindings;
	}

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public String getJndiNameMode() {
		return jndiNameMode;
	}

	public void setJndiNameMode(String jndiNameMode) {
		this.jndiNameMode = jndiNameMode;
	}

	public void prepare() throws Exception {
		jndiName = AppsGlobals.getSetupProperty(JndiDataSourceProvider.JNDI_NAME_KEY);
	}

	public String doDefault() {
		getSession().put("apps.setup.sidebar.0", "done");
		getSession().put("apps.setup.sidebar.1", "done");
		getSession().put("apps.setup.sidebar.2", "in_progress");
		getSession().put("apps.setup.sidebar.3", "incomplete");
		getSession().put("apps.setup.sidebar.4", "incomplete");
		getSession().put("apps.setup.sidebar.5", "incomplete");
		getSession().put("apps.setup.sidebar.6", "incomplete");
		try {
			Context context = new InitialContext();
			bindings = context.listBindings("java:comp/env");///jdbc
			if(!bindings.hasMoreElements()){
				bindings = null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ActionSupport.INPUT;
	}

	public void validate() {
		if (jndiNameMode == null && jndiName == null)
			addActionError("无效的 JNDI 数据源名称。");
		else if ("custom".equals(jndiNameMode) && jndiName == null)
			addActionError("无效的 JNDI 数据源名称。");
	}

	public String execute() {
		String lookupName;
		if ((jndiNameMode == null || "custom".equals(jndiNameMode)) && jndiName != null)
			lookupName = jndiName;
		else
			lookupName = jndiNameMode;

		AppsGlobals.setSetupProperty(DataSourceProvider.DATA_SOURCE_PROVIDER_CLASS, 
				JndiDataSourceProvider.class.getName());
		AppsGlobals.setSetupProperty(JndiDataSourceProvider.JNDI_NAME_KEY, lookupName);
		JndiDataSourceProvider dsp = new JndiDataSourceProvider();
		dsp.setProperty("name", lookupName);
		testConnection(dsp);

		if (hasActionErrors()) {
			return ActionSupport.ERROR;
		}

		getSession().put("apps.setup.sidebar.0", "done");
		getSession().put("apps.setup.sidebar.1", "done");
		getSession().put("apps.setup.sidebar.2", "done");
		getSession().put("apps.setup.sidebar.3", "in_progress");
		getSession().put("apps.setup.sidebar.4", "incomplete");
		getSession().put("apps.setup.sidebar.5", "incomplete");
		getSession().put("apps.setup.sidebar.6", "incomplete");
		return "next";
	}
}
