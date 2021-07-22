package org.opoo.apps.conversion.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.opoo.apps.AppsHome;
import org.opoo.apps.conversion.ConversionRevision;

public abstract class ConversionUtils {

	public static boolean isValid(File file){
		return file != null && file.exists() && file.length() > 0;
	}
	
	public static void writeToFile(File file, InputStream is) throws IOException {
		OutputStream fos = null;
		try {
			fos = FileUtils.openOutputStream(file);
			IOUtils.copy(is, fos);
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(fos);
		}
	}
	
	public static File createTempFile(ConversionRevision revision) throws IOException {
		//return File.createTempFile("DOC-" + revision.getRevisionId(), ".tmp", AppsHome.getTemp());
		return File.createTempFile("DOC-" + revision.getRevisionId(), ".tmp");
	}
	
	//cv/testdocs/test.doc
	public static File getTestFile(String filename) throws IOException{
		InputStream inputStream = ConversionUtils.class.getResourceAsStream("/cv/testdocs/" + filename);
		try {
			String extension = FilenameUtils.getExtension(filename);
			File file = File.createTempFile("test-", "." + extension);
			writeToFile(file, inputStream);
			return file;
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}
}
