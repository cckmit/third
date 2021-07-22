package org.opoo.apps.dv.office;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

public class TestUtils {
	public static File getTestFile(String filename){
		InputStream inputStream = TestUtils.class.getResourceAsStream("/dv/testdocs/" + filename);
		OutputStream fos = null;
		try {
			String extension = FilenameUtils.getExtension(filename);
			File file = File.createTempFile("test-", "." + extension);
			fos = FileUtils.openOutputStream(file);
			IOUtils.copy(inputStream, fos);
			return file;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(fos);
			IOUtils.closeQuietly(inputStream);
		}
	}
	
//	public static File getStorageRootDir(){
//		String tmp = System.getProperty("java.io.tmpdir");
//		return new File(tmp, "dv-storage");
//	}
//	
//	public static void main(String[] args){
//		System.out.println(getStorageRootDir());
//	}
}
