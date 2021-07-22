package org.opoo.apps.dv.office;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jivesoftware.eos.fs.FileStorageProvider;

public class TestFileStorageProvider extends FileStorageProvider {
    private File rootDirectory;

    private static final Logger log = Logger.getLogger(TestFileStorageProvider.class);

    public TestFileStorageProvider() {
		super("test", buildStorageProviderInitProps());
		this.rootDirectory = new File((String) buildStorageProviderInitProps().get("rootDirectory"), namespace);
        log.debug(String.format("Root directory set to '%s'.", this.rootDirectory.getAbsolutePath()));
	}

	static Properties buildStorageProviderInitProps(){
		String tmp = System.getProperty("java.io.tmpdir");
		File dir = new File(tmp, "dv-storage");
		Properties properties = new Properties();
		properties.setProperty("rootDirectory", dir.getAbsolutePath());
		return properties;
	}

	@Override
	protected File getFileHandle(String key) {
		if(key.startsWith("file:///")){
			File file = new File(this.rootDirectory, key.substring(8));
			System.out.println("FILE ==> " + file);
			File parent = file.getParentFile();
			if(!parent.exists()){
				parent.mkdirs();
			}
			return file;
		}
		return super.getFileHandle(key);
	}

	@Override
	protected File getKeyHandle(String key) {
		if(key.startsWith("file:///")){
			File file = new File(this.rootDirectory, key.substring(8) + ".key");
			System.out.println("KEY ==> " + file);
			File parent = file.getParentFile();
			if(!parent.exists()){
				parent.mkdirs();
			}
			return file;
		}
		return super.getKeyHandle(key);
	}
}
