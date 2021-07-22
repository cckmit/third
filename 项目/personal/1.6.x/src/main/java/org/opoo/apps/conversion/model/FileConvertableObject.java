package org.opoo.apps.conversion.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.opoo.apps.conversion.ConvertableObject;

public class FileConvertableObject implements ConvertableObject {
	private final File file;
	private final int objectType;
	private final long objectId;
	private final int revisionNumber;
	private String contentType;
	private String filename;
	public FileConvertableObject(File file, String contentType, int objectType, long objectId, int revisionNumber){
		this.file = file;
		this.contentType = contentType;
		this.objectId = objectId;
		this.objectType = objectType;
		this.revisionNumber = revisionNumber;
		this.filename = file.getName();
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

	
	public String getContentType() {
		return contentType;
	}

	
	public InputStream getStreamData() throws IOException {
		return new BufferedInputStream(new FileInputStream(file));
	}

	
	public long getContentLength() {
		return file.length();
	}

	
	public boolean isAllowedToConvert() {
		return file.length() > 0;
	}

	
	public int getRevisionNumber() {
		return revisionNumber;
	}

	
	public int getObjectType() {
		return objectType;
	}

	
	public long getObjectId() {
		return objectId;
	}
}
