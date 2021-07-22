package org.opoo.apps.license;

import java.util.Collection;

import com.jivesoftware.license.License.Module;
import com.jivesoftware.license.License.Version;

@Deprecated
public class DummyProduct implements Product {

	/**
	 * 
	 */
	private static final long serialVersionUID = 814019849106948333L;
	
	private Collection<Module> modules;
	private String name;
	private Version version;
	/**
	 * @return the modules
	 */
	public Collection<Module> getModules() {
		return modules;
	}
	/**
	 * @param modules the modules to set
	 */
	public void setModules(Collection<Module> modules) {
		this.modules = modules;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the version
	 */
	public Version getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Version version) {
		this.version = version;
	}
	
//	public Collection<Module> getModules() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public String getName() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public Version getVersion() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
