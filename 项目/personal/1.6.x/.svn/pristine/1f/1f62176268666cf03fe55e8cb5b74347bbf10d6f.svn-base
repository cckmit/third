package org.opoo.apps.file.handler;

import org.opoo.apps.Model;

public interface FileHandlerChainManager {
	
	/**
	 * 
	 * @param config
	 * @return
	 */
	FileHandlerChain getFileHandlerChain(String config);
	
	/**
	 * 
	 * @param chain
	 * @param file
	 * @param fileName
	 * @param contentType
	 * @param fileSize
	 * @return
	 */
	Model performChain(FileHandlerChain chain, FileInfo fileInfo);

	
	/**
	 * 
	 * @param chainConfig
	 * @param file
	 * @param fileName
	 * @param contentType
	 * @param fileSize
	 * @return
	 */
	Model performChain(String chainConfig, FileInfo fileInfo);

}
