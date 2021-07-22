package org.opoo.apps.module;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.opoo.apps.AppsConstants;
import org.opoo.apps.module.dao.ModuleBean;


/**
 * 模块头实现类。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ModuleMetaDataImpl implements ModuleMetaData {
	private static final Log log = LogFactory.getLog(ModuleMetaDataImpl.class);
	
    protected String name;
    protected String author;
    protected Module<?> module;
    protected ModuleClassLoader loader;
    protected Document config;
    protected final File moduleDirectory;
    protected String version;
    protected String minServerVersion;
    protected String maxServerVersion;
    protected String databaseKey;
    protected String description;
    protected int databaseVersion;
    protected boolean readmeExists;
    protected boolean changelogExists;
    protected boolean largeLogoExists;
    protected boolean smallLogoExists;
    protected boolean installed;
    protected boolean uninstalled;
    private ModuleBean moduleBean;
    private final File moduleInfDirectory;
    private Scope scope = Scope.cluster;
    
    public ModuleMetaDataImpl(Module<?> module, ModuleClassLoader loader, Document config, File moduleDirectory)
    {
        databaseVersion = -1;
        this.module = module;
        this.loader = loader;
        this.config = config;
        this.moduleDirectory = moduleDirectory;
        this.moduleInfDirectory = new File(this.moduleDirectory, AppsConstants.MODULE_INF);
        
        name = getElementValue("/module/name");
        author = getElementValue("/module/author");
        version = getElementValue("/module/version");
        minServerVersion = getElementValue("/module/minServerVersion");
        maxServerVersion = getElementValue("/module/maxServerVersion");
        databaseKey = getElementValue("/module/databaseKey");
        description = getElementValue("/module/description");
        String versionString = getElementValue("/module/databaseVersion");
        if(versionString != null){
            try
            {
                databaseVersion = Integer.parseInt(versionString.trim());
            }
            catch(NumberFormatException nfe)
            {
                log.error(nfe);
            }
        }
        
        String scopeString = getElementValue("/module/scope");
        if(scopeString != null){
        	scope = Scope.valueOf(scopeString);
        }

        readmeExists = (new File(moduleDirectory, "readme.html")).exists();
        changelogExists = (new File(moduleDirectory, "changelog.html")).exists();
        largeLogoExists = (new File(moduleDirectory, "logo_large.png")).exists();
        smallLogoExists = (new File(moduleDirectory, "logo_small.png")).exists();
    }

    protected String getElementValue(String xpath) {
		try {
			Element element = (Element) config.selectSingleNode(xpath);
			if (element != null) {
				return element.getTextTrim();
			}
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
    
	public String getAuthor() {
		return author;
	}

	public ModuleClassLoader getClassLoader() {
		return loader;
	}

	public Document getConfig() {
		return config;
	}

	public String getDatabaseKey() {
		return databaseKey;
	}

	public int getDatabaseVersion() {
		return databaseVersion;
	}

	public String getDescription() {
		return description;
	}

	public String getMinServerVersion() {
		return minServerVersion;
	}

	public Module<?> getModule() {
		return module;
	}

	public File getModuleDirectory() {
		return moduleDirectory;
	}
	
	public File getModuleInfDirectory(){
		return moduleInfDirectory;
	}
	
	public File getModuleConfiguration(){
		return new File(moduleInfDirectory, "module.xml");
	}

	public Map<String, String> getModuleProperties() {
		return null;
	}

	public ResourceBundle getModuleResourceBundle(Locale locale) {
		return null;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public boolean isChangelogExists() {
		return changelogExists;
	}

	public boolean isInstalled() {
		return installed;
	}
	
	public void setInstalled(boolean installed){
		this.installed = installed;
	}

	public boolean isLargeLogoExists() {
		return largeLogoExists;
	}

	public boolean isReadmeExists() {
		return readmeExists;
	}

	public boolean isSmallLogoExists() {
		return smallLogoExists;
	}

	public boolean isUninstalled() {
		return uninstalled;
	}

	public String getMaxServerVersion() {
		return maxServerVersion;
	}
	
    public void setUninstalled(boolean uninstalled)
    {
        this.uninstalled = uninstalled;
    }

	/**
	 * @return the moduleBean
	 */
	public ModuleBean getModuleBean() {
		return moduleBean;
	}

	/**
	 * @param moduleBean the moduleBean to set
	 */
	public void setModuleBean(ModuleBean moduleBean) {
		this.moduleBean = moduleBean;
	}
	
	
	public Scope getScope(){
		return scope;
	}
}
