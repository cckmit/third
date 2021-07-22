package org.opoo.apps.file;

import java.io.InputStream;
/**
 * FTP存储的文件系统。
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class FtpFileStorage extends AbstractFileStorage implements FileStorage {

	public FtpFileStorage(String storeName) {
		super(storeName);
		// TODO Auto-generated constructor stub
	}


	public void delete(Details details) {
		// TODO Auto-generated method stub
		
	}

	public InputStream fetch(Details details, String format)
			throws FileStorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected FileDetails save(InputStream is, FileDetails details, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
