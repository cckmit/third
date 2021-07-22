package org.opoo.apps.web.struts2.action.admin.setup;

import org.opoo.apps.AppsGlobals;

import com.opensymphony.xwork2.ActionSupport;

public class FileConvertersSetupAction extends SetupActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8345423791171598000L;

	public static final String FILE_CONVERTERS_ENABLED = "attachments.fileConverters.enabled";
	
	private boolean enabled;
	
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#doDefault()
	 */
	@Override
	public String doDefault() throws Exception {
		enabled = Boolean.parseBoolean(AppsGlobals.getSetupProperty(FILE_CONVERTERS_ENABLED, "false"));
		return ActionSupport.INPUT;
	}
	
	public String execute(){
		if(enabled){
			AppsGlobals.setSetupProperty(FILE_CONVERTERS_ENABLED, "true");
			return "openoffice";
		}else{
			AppsGlobals.setSetupProperty(FILE_CONVERTERS_ENABLED, "false");
			return "next";
		}
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
