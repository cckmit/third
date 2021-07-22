package org.opoo.apps.file;


import java.net.URI;

public abstract class AbstractFileStorageFactory implements FileStorageFactory {

	/* (non-Javadoc)
	 * @see org.opoo.apps.file.FileStorageFactory#createFileStorage(java.lang.String, java.net.URI)
	 */
	public final FileStorage createFileStorage(String storeName, URI uri)  throws FileStorageException{
		AbstractFileStorage afs = createAbstractFileStorage(storeName, uri);
		//做一些额外处理。
		//...
		return afs;
	}
	
	protected abstract AbstractFileStorage createAbstractFileStorage(String storeName, URI uri) throws FileStorageException;
}
