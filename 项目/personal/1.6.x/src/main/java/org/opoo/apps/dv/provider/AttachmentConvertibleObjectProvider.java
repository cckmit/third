package org.opoo.apps.dv.provider;

import java.io.IOException;
import java.io.InputStream;

import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.dv.ConvertableObject;

public class AttachmentConvertibleObjectProvider implements	ConvertibleObjectProviderPrototype {
	Attachment attachment;
	
	public String getFilename() {
		return attachment.getFileName();
	}

	public String getContentType() {
		return attachment.getContentType();
	}

	public InputStream getStream() throws IOException {
		return attachment.readInputStream();
	}

	public int getRevisionNumber() {
		return 1;
	}

	public long getContentLength() {
		return attachment.getFileSize();
	}

	public ConvertableObject getVersion(int version) {
		//return attachment;
		//TODO Attachment还没有实现接口
		return null;
	}

	public boolean isAllowedToConvert() {
		return true;
	}

	public SizedInputStream getModifiedStream() throws IOException {
		return null;
	}

	public void setContext(ConvertableObject co) {
		this.attachment = (Attachment) co;
	}
}
