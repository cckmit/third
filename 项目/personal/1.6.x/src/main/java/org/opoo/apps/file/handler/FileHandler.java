package org.opoo.apps.file.handler;

import org.opoo.apps.Model;

public interface FileHandler {

	/**
	 * 
	 * @param model
	 * @param inputFile �ýӿڣ���ֹ�޸�
	 * @param chain
	 * @return
	 * @throws Exception
	 */
	Model handle(Model model, FileInfo fileInfo, FileHandlerChain chain) throws Exception;
	
}
