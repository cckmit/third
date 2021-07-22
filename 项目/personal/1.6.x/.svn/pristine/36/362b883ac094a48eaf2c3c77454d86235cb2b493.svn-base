package org.opoo.apps.web.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.springframework.core.io.Resource;
import org.springframework.util.Assert;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ResourceWrapper implements Resource {
	private final Resource resource;
	public ResourceWrapper(Resource resource){
		Assert.notNull(resource);
		this.resource = resource;
	}
	public Resource createRelative(String relativePath) throws IOException {
		return resource.createRelative(relativePath);
	}

	public boolean exists() {
		return resource.exists();
	}

	public String getDescription() {
		return resource.getDescription();
	}

	public File getFile() throws IOException {
		return resource.getFile();
	}

	public String getFilename() {
		return resource.getFilename();
	}

	public URI getURI() throws IOException {
		return resource.getURI();
	}

	public URL getURL() throws IOException {
		return resource.getURL();
	}

	public boolean isOpen() {
		return resource.isOpen();
	}

	public boolean isReadable() {
		return resource.isReadable();
	}

	public long lastModified() throws IOException {
		return resource.lastModified();
	}

	public InputStream getInputStream() throws IOException {
		return resource.getInputStream();
	}
}
