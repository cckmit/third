package org.opoo.apps.license;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.opoo.util.XMLOpooProperties2;
import org.springframework.core.io.Resource;

import com.jivesoftware.license.License;
import com.jivesoftware.license.License.Module;
import com.jivesoftware.license.License.Version;

public class DefaultInstance implements AppsInstance {
	public static final String DEFAULT_NAME = "opoo-apps";
	
	private static final long serialVersionUID = -1155531141316600485L;
	private XMLOpooProperties2 props;
	
	private String name = DEFAULT_NAME;
	private License.Version version;
	private List<License.Module> modules;
	
	private final URL url;
	
	public DefaultInstance(URL url){
		final Logger log = LogManager.getLogger(DefaultInstance.class);
        InputStream in = null;
        try {
        	this.url = url;
            in = url.openStream();
            initialize(in);
        }catch (Throwable e) {
            log.debug("Error loading apps instance from " + url, e);
            throw new AppsLicenseException(e);
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (Exception e) { /* ignored */ }
        }
	}
	public DefaultInstance(Resource resource) {
		final Logger log = LogManager.getLogger(DefaultInstance.class);
        InputStream in = null;
        try {
        	this.url = resource.getURL();
            in = resource.getInputStream();
            initialize(in);
        }
        catch (Throwable e) {
            log.debug("Error loading apps instance from resource: " + resource, e);
            throw new AppsLicenseException(e);
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (Exception e) { /* ignored */ }
        }
	}
	
	private void initialize(InputStream in) throws Exception{
		if (in != null) {
        	props = new XMLOpooProperties2(in);
        }else{
        	throw new AppsLicenseException(url + " is missing.");
        }
        
        ///////////////////////////////////////////
        String name = props.getProperty("product.name");
		if(StringUtils.isNotBlank(name)){
			this.name = name;
		}
		
		String version = props.getProperty("product.version");
		if(StringUtils.isNotBlank(version)){
			version = version.replace('-', ' ');
			this.version = License.Version.parseVersion(version);
		}
		
		String modules = props.getProperty("product.modules");
		if(StringUtils.isNotBlank(modules)){
			this.modules = new ArrayList<License.Module>();
			StringTokenizer st = new StringTokenizer(modules, ", ");
			while(st.hasMoreTokens()){
				this.modules.add(new License.Module(st.nextToken()));
			}
		}else{
			throw new AppsLicenseException("No licensed modules.");
		}
	}
	
	

	public String getName() {
		return name;
	}

	public Version getVersion() {
		return version;
	}
	
	public String getType(){
		return getProperty("product.type");
	}
	
	public String getInstanceId(){
		return getProperty("product.instanceId");
	}

	public Collection<Module> getModules() {
		return modules;
	}

	public String getProperty(String prop) {
		return props != null ? props.getProperty(prop) : null;
	}

	public Map<String, String> getProperties() {
		return props == null ? new HashMap<String,String>(): props;
	}
	
	public URL getUrl() {
		return url;
	}
}
