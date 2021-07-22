package org.opoo.apps.conversion.doc;

import org.opoo.apps.conversion.ConversionArtifactType;


public enum DocConversionArtifactType implements ConversionArtifactType {
	//File("application/octet-stream", ""),
    Preview("application/x-shockwave-flash", "swf"),
    Thumbnail("image/png", "png"),
    Text("text/plain", "txt"),
    Pdf("application/pdf", "pdf");

	private final String contentType;
	private final String extension;
	private DocConversionArtifactType(String contentType, String extension){
		this.contentType = contentType;
		this.extension = extension;
	}
	
	
	public String getName() {
		return name();
	}

	
	public String getContentType() {
		return contentType;
	}

	
	public String getExtension() {
		return extension;
	}
}
