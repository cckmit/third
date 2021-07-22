package org.opoo.apps.web.struts2.action.admin.setup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.struts2.interceptor.SessionAware;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;
import org.opoo.apps.license.AppsLicense;
import org.opoo.apps.license.AppsLicenseManager;
import org.opoo.apps.license.DefaultLicenseProvider;

import com.jivesoftware.license.License;
import com.jivesoftware.license.LicenseException;
import com.jivesoftware.license.LicenseManager;
import com.jivesoftware.license.io.LicenseReader;
import com.opensymphony.xwork2.ActionSupport;

public class LicenseSetupAction_BAK extends SetupActionSupport implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1913799894903624211L;
	private Map session;
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
		File f = AppsHome.getLicenseFile();
		if (f != null && f.exists())
			try {
				LicenseReader licenseReader = new LicenseReader();
				License l = licenseReader.read(new BufferedReader(new FileReader(f)));
				l = new AppsLicense(l);
				Collection<LicenseException> exceptions = AppsLicenseManager.validate(new DefaultLicenseProvider(), l);
				for (LicenseException e : exceptions) {
					if (e.getMessage().contains("License signature is invalid."))
						addActionError("旧的 License 有问题。");
					else
						addActionError(e.getMessage());
				}

				if (!hasActionErrors()) {
					license = l;
					evaluation = false;
				}
			} catch (Exception e) {
				addActionError("AppsHome 中的 License 文件是无效的。");
			}
		return ActionSupport.INPUT;
	}
	  public String execute()
	    {
		  AppsLicenseManager licenseManager = AppsLicenseManager.getInstance();
	        File licenseFile = AppsHome.getLicenseFile();
	        if(evaluation)
	        {
	            if(licenseFile.exists())
	                licenseFile.delete();
	            licenseManager.destroy();
	            AppsGlobals.setSetupProperty("license.evaluation", "true");
	            try
	            {
	            	AppsLicenseManager.getInstance().initialize((Reader)null);
	            }
	            catch(IOException e)
	            {
	                LogFactory.getLog(LicenseSetupAction_BAK.class).error(e.getMessage(), e);
	            }
	        } else
	        {
	            if(!licenseFile.exists()){
	                addActionError(getText("找不到 license 文件。"));
	            }
	            AppsGlobals.deleteSetupProperty("license.evaluation");
	            licenseManager.destroy();
	            try
	            {
	            	AppsLicenseManager.getInstance().initialize(new BufferedReader(new FileReader(licenseFile)));
	            }
	            catch(IOException e)
	            {
	                addActionError("安装的 License 无效。");
	            }
	        }
	        if(hasActionErrors())
	            return ActionSupport.INPUT;
	        else
	            return ActionSupport.SUCCESS;
	    }

	
	
	  public String doSet()
	    {
	        File licenseFile = AppsHome.getLicenseFile();
	        if(!evaluation)
	        {
	            try
	            {
	                licenseFile.createNewFile();
	                Reader reader = new StringReader(licenseString);
	                Writer writer = new BufferedWriter(new FileWriter(licenseFile));
	                try
	                {
	                    char buffer[] = new char[32768];
	                    int len;
	                    while((len = reader.read(buffer)) != -1) 
	                        writer.write(buffer, 0, len);
	                    writer.flush();
	                }
	                catch(IOException e)
	                {
	                    LogManager.getLogger(LicenseSetupAction_BAK.class).error(e.getMessage(), e);
	                    addActionError("License 文件无法写入。");
	                }
	                finally
	                {
	                    writer.close();
	                }
	            }
	            catch(IOException e)
	            {
	                addActionError("安装的 License 无效。");
	            }
	            try
	            {
	                LicenseReader reader = new LicenseReader();
	                license = reader.read(new BufferedReader(new FileReader(licenseFile)));
	                license = new AppsLicense(license);
	            }
	            catch(IOException e)
	            {
	                addActionError("License 文件无法读取。");
	            }
	        } else
	        {
	            if(licenseFile.exists())
	                licenseFile.delete();
	            license = null;// AppsLicenseManager.createDefaultLicense();
	        }
	        return ActionSupport.INPUT;
	    }
	
	  public void validateSet() {
		if (licenseString == null || "".equals(licenseString)) {
			addActionError("请输入 License 内容。");
			return;
		}
		try {
			LicenseReader reader = new LicenseReader();
			License l = reader.read(new StringReader(licenseString));
			Collection<LicenseException> licenseExceptions = LicenseManager.validate(new DefaultLicenseProvider(), l);
			for (LicenseException e : licenseExceptions) {
				if (e.getMessage().contains("License signature is invalid."))
					addActionError("License 无效，请使用 1.5.0 以后版本的 License 文件。");
				else
					addActionError(e.getMessage());
			}
		} catch (Exception e) {
			LogFactory.getLog(LicenseSetupAction_BAK.class).error(e.getMessage(), e);
			addActionError("输入的 License 文本无效，您可以查看系统运行日志获得详细信息。");
		}
	}

	
	
	

	public void setSession(Map session) {
		this.session = session;
		
	}

	/**
	 * @return the session
	 */
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
