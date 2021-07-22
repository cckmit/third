package org.opoo.apps.file.handler;


import org.opoo.apps.Model;

public interface FileHandlerChain {
	
	/**
	 * 
	 * @param model
	 * @param inputFile
	 * @return
	 * @throws Exception
	 */
	Model handle(Model model, FileInfo fileInfo) throws Exception;
	
}
