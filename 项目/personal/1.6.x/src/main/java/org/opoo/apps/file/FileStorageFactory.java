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
	 * �ù����Ƿ�֧��store��URI��
	 * @param uri
	 * @return
	 */
	boolean supports(URI uri);
}
