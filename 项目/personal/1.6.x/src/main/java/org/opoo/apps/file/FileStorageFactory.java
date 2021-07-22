package org.opoo.apps.file;

import java.net.URI;

public interface FileStorageFactory {
	/**
	 * 
	 * @param storeName
	 * @param uri
	 * @return
	 */
	FileStorage createFileStorage(String storeName, URI uri) throws FileStorageException;
	/**
	 * 该工厂是否支持store的URI。
	 * @param uri
	 * @return
	 */
	boolean supports(URI uri);
}
