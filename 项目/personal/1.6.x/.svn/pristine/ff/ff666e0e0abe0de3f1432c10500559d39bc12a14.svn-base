package org.opoo.apps.file.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class FileUtils extends org.opoo.apps.util.FileUtils{
	/**
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String streamToString(final InputStream is) throws IOException{
		StringBuffer result = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while ((line = in.readLine()) != null) {
			result.append(line).append("\n");
		}
		return result.toString();
	}
}
