package org.opoo.storage.eos;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

import org.opoo.storage.Storage;
import com.jivesoftware.eos.StorageProvider;


/**
 * Using Jive EOS (External Opaque Storage) as Storage.
 * <p>
 * <b>NOTICE: Jive EOS is a part of Jive Software, and Jive Software is commercial software.</b>
 */
public class ExternalOpaqueStorage implements Storage {
	private final StorageProvider storageProvider;
	
	public ExternalOpaqueStorage(StorageProvider storageProvider){
		this.storageProvider = storageProvider;
	}
	
	public boolean containsKey(String key) {
		return storageProvider.containsKey(key);
	}

	public boolean delete(String key) {
		return storageProvider.delete(key);
	}

	public ByteBuffer getBuffer(String key) {
		return storageProvider.getBuffer(key);
	}

	public Iterable<String> getKeys() {
		return storageProvider.getKeys();
	}

	public String getNamespace() {
		return storageProvider.getNamespace();
	}

	public Map<String, String> getStatistics() {
		return storageProvider.getStatistics();
	}

	public InputStream getStream(String key) {
		return storageProvider.getStream(key);
	}

	public boolean put(String key, byte[] data) {
		return storageProvider.put(key, data);
	}

	public boolean put(String key, InputStream data) {
		return storageProvider.put(key, data);
	}
}
