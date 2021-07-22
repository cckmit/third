package org.opoo.apps.dv.provider;

import java.io.File;

import org.opoo.apps.dv.ConvertableObject;

public class TestConvertibleObject implements ConvertableObject{
	// public static final String NAME = "testConvertibleObject";
	public static final int OBJECT_TYPE = -1988;// StringUtils.hash(NAME).hashCode();

	private String contentType;
	private File file;
	private String filename;
	private Long id;

	public TestConvertibleObject(String filename, String contentType, File file) {
		this.id = Math.round(Math.random() * -1000);
		this.filename = filename;
		this.contentType = contentType;
		this.file = file;
	}

	public String getContentType() {
		return contentType;
	}

	public File getFile() {
		return file;
	}

	public String getFilename() {
		return filename;
	}

	public int getObjectType() {
		return OBJECT_TYPE;
	}

	public Long getId() {
		return id;
	}
}
