package org.opoo.apps.file.handler;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

public class SimpleFileInfo implements FileInfo, Serializable {
	private static final long serialVersionUID = -8915561803872255169L;
	private final File file;
	private final String fileName;
	private final String contentType;
	private final long fileSize;
	private final Map<String,Object> parameters;
	
	public SimpleFileInfo(File file, String fileName, String contentType, long fileSize, Map<String,Object> parameters) {
		super();
		this.file = file;
		this.fileName = fileName;
		this.contentType = contentType;
		this.fileSize = fileSize;
		this.parameters = parameters;
	}


	
	public String getContentType() {
		return contentType;
	}

	public File getFile() {
		return file;
	}

	public String getFileName() {
		return fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public Map<String,Object> getParameters() {
		return parameters;
	}

}
