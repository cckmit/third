package org.opoo.apps.dv.provider;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.opoo.apps.dv.ConvertableObject;
import org.opoo.util.AutoCloseInputStream;


public class TestConvertibleObjectProvider implements
		ConvertibleObjectProviderPrototype, ConvertibleObjectProvider, FileProvider {
	TestConvertibleObject object;

    public String getFilename() {
        return object.getFilename();
    }

    public String getContentType() {
        return object.getContentType();
    }

    public InputStream getStream() throws IOException {
        return new AutoCloseInputStream(new BufferedInputStream(new FileInputStream(object.getFile())));
    }

    public int getRevisionNumber() {
        return 1;
    }

    public long getContentLength() {
        return object.getFile().length();
    }

    public ConvertableObject getVersion(int version) {
        return object;
    }

    public boolean isAllowedToConvert() {
        return true; 
    }

    public SizedInputStream getModifiedStream() {
        return null;
    }

    public void setContext(ConvertableObject co) {
        this.object = (TestConvertibleObject)co;
    }

	public File getFile() {
		return object.getFile();
	}
}
