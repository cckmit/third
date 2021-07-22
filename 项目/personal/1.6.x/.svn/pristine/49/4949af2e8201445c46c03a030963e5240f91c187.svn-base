package org.opoo.storage.eos;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.Maps;
import com.jivesoftware.eos.fs.FileStorageProvider;

public class MyFileStorageProvider extends FileStorageProvider {

	public MyFileStorageProvider(String namespace, Properties properties) {
		//new File((String)properties.get("rootDirectory"), namespace);
		super(namespace, properties);
	}

	protected File getDirectory(String key){
		return super.getDirectory(key);
	}
	
	protected File getDirectory2(String key) {
		String string = getPath(key);
		File dir = new File("d:\\temp\\eos", string);
		if(!dir.exists()){
			dir.mkdirs();
		}
		//File dir = super.getDirectory(key);
		System.out.println("getDirectory(" + key + "): " + dir);
		return dir;
	}
	
	private SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
	private Map<String, String> path = Maps.newHashMap();
	private String getPath(String key){
		String p = path.get(key);
		if(p == null){
			p = f.format(new Date());
			path.put(key, p);
		}
		return p;
	}

	@Override
	protected File getFileHandle(String key) {
		File dir = super.getFileHandle(key);
		System.out.println("getFileHandle(" + key + "): " + dir);
		return dir;
	}

	@Override
	protected String getHash(String arg0) {
		String hash = super.getHash(arg0);
		System.out.println("getHash(" + arg0 + "): " + hash);
		return hash;
	}

	@Override
	protected File getKeyHandle(String key) {
		File dir = super.getKeyHandle(key);
		System.out.println("getKeyHandle(" + key + "): " + dir);
		return dir;
	}
}
