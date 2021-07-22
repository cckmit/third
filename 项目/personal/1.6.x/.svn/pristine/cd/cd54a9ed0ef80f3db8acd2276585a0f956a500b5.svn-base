package org.opoo.storage;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;
 
public interface Storage {
	
	boolean containsKey(String key);

	boolean delete(String key);

	ByteBuffer getBuffer(String key);

	Iterable<String> getKeys();

	String getNamespace();

	Map<String, String> getStatistics();

	InputStream getStream(String key);

	boolean put(String key, byte[] data);

	boolean put(String key, InputStream data);
}
