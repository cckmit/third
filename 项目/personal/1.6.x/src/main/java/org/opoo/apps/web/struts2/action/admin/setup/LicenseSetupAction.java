package org.opoo.apps.web.struts2.action.admin.setup;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.opoo.apps.license.AppsLicense;
import org.opoo.apps.license.AppsLicenseManager;

import com.jivesoftware.license.License;
import com.opensymphony.xwork2.ActionSupport;

public class LicenseSetupAction extends SetupActionSupport implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1913799894903624211L;
	private Map<String, Object> session;
    protected String licenseString;
    protected boolean evaluation;
    protected boolean changeLicense;
    protected License license;
	
	
	public String input() {
		getSession().put("apps.setup.sidebar.0", "in_progress");
		getSession().put("apps.setup.sidebar.1", "incomplete");
		getSession().put("apps.setup.sidebar.2", "incomplete");
		getSession().put("apps.setup.sidebar.3", "incomplete");
		getSession().put("apps.setup.sidebar.4", "incomplete");
		
		
		AppsLicense l = AppsLicenseManager.getInstance().getAppsLicense();
		if(l != null){ 
			if(l.isInvalidLicense()){
				addActionError("现有的授权许可无效。");
			}else{
				license = l;
			}
		}
		return ActionSupport.INPUT;
	}
	
	public String execute(){
		return ActionSupport.SUCCESS;
    }
	

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
		
	}

	/**
	 * @return the session
	 */
	@SuppressWarnings("unchecked")
	public Map getSession() {
		return session;
	}

	/**
	 * @return the evaluation
	 */
	public boolean isEvaluation() {
		return evaluation;
	}

	/**
	 * @param evaluation the evaluation to set
	 */
	public void setEvaluation(boolean evaluation) {
		this.evaluation = evaluation;
	}

	/**
	 * @return the license
	 */
	public License getLicense() {
		return license;
	}

	/**
	 * @param license the license to set
	 */
	public void setLicense(License license) {
		this.license = license;
	}
	/**
	 * @return the licenseString
	 */
	public String getLicenseString() {
		return licenseString;
	}
	/**
	 * @param licenseString the licenseString to set
	 */
	public void setLicenseString(String licenseString) {
		this.licenseString = licenseString;
	}
	/**
	 * @return the changeLicense
	 */
	public boolean isChangeLicense() {
		return changeLicense;
	}
	/**
	 * @param changeLicense the changeLicense to set
	 */
	public void setChangeLicense(boolean changeLicense) {
		this.changeLicense = changeLicense;
	}
}
