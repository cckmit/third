/**
 * 
 */
package org.opoo.apps.license;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import com.jivesoftware.license.License.Module;
import com.jivesoftware.license.License.Version;

/**
 * 产品实例。
 * 
 * @author lcj
 *
 */
public interface AppsInstance extends Serializable {
	/**
	 * 产品名称。
	 * @return
	 */
	String getName();

	/**
	 * 产品版本。
	 * @return
	 */
	Version getVersion();

	/**
	 * 产品模块。
	 * @return
	 */
	Collection<Module> getModules();
	
	/**
	 * 产品类型。
	 * @return
	 */
	String getType();
	/**
	 * 产品的实例ID。
	 * @return
	 */
	String getInstanceId();
	
	/**
	 * 获取产品实例指定属性的值。
	 * @param prop
	 * @return
	 */
	String getProperty(String prop);
	
	/**
	 * 获取产品实例的所有属性集合。
	 * @return
	 */
	Map<String,String> getProperties();
	
//	String getSpecificationTitle();
//	
//	String getSpecificationVendor();
//	
//	String getSpecificationVersion();
//	
//	specification>
//	<title>${project.name}</title>
//	<vendor>${project.organization.name}</vendor>
//	<version>${project.version}</version>
//</specification>
//<implementation>
//	<title>${project.name}</title>
//	<vendor>${project.organization.name}</vendor>
//	<version>${buildNumber}</version>
//</implementation>
}
