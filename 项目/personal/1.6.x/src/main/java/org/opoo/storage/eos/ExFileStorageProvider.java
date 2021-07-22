package org.opoo.storage.eos;

import java.io.File;
import java.util.Properties;

import com.jivesoftware.eos.fs.FileStorageProvider;

/**
 * key=CA:ISID-2012-08-12-CMID-TYPE-N.EXT
 * @author lcj
 *
 */
public class ExFileStorageProvider extends FileStorageProvider {
	File effectiveRoot;
	public ExFileStorageProvider(String ns, Properties props) {
		super(ns, props);
		effectiveRoot = new File(props.getProperty("rootDirectory"), namespace);
	}
	@Override
	protected File getDirectory(String key) {
		return super.getDirectory(key);
	}
	@Override
	protected File getFileHandle(String key) {
		return super.getFileHandle(key);
	}
	@Override
	protected String getHash(String key) {
		return super.getHash(key);
	}
	@Override
	protected File getKeyHandle(String key) {
		return super.getKeyHandle(key);
	}
}
