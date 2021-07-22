package org.opoo.apps.web.resource;

import org.springframework.core.io.Resource;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface ResourceManager {

//	InputStream loadModuleResource(String path) throws IOException;
//
//	boolean contains(String path);
//
//	File get(String path);
//	
//	
//	//Resource getResouce();
	
	
	/**
	 * ��ȡ��̬��Դ
	 * 
	 * @param path
	 * @return
	 * 
	 */
	Resource getPublicResource(String path);
	
	/**
	 * ��ȡģ����Դ
	 * 
	 * @param path
	 * @return
	 */
	Resource getModuleResource(String path);
}
