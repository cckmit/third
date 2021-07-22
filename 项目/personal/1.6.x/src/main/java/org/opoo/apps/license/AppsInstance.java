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
 * ��Ʒʵ����
 * 
 * @author lcj
 *
 */
public interface AppsInstance extends Serializable {
	/**
	 * ��Ʒ���ơ�
	 * @return
	 */
	String getName();

	/**
	 * ��Ʒ�汾��
	 * @return
	 */
	Version getVersion();

	/**
	 * ��Ʒģ�顣
	 * @return
	 */
	Collection<Module> getModules();
	
	/**
	 * ��Ʒ���͡�
	 * @return
	 */
	String getType();
	/**
	 * ��Ʒ��ʵ��ID��
	 * @return
	 */
	String getInstanceId();
	
	/**
	 * ��ȡ��Ʒʵ��ָ�����Ե�ֵ��
	 * @param prop
	 * @return
	 */
	String getProperty(String prop);
	
	/**
	 * ��ȡ��Ʒʵ�����������Լ��ϡ�
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
