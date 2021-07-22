package org.opoo.apps.conversion.v2;

import org.opoo.apps.conversion.ConversionArtifactType;

public enum BaseConversionArtifactType implements ConversionArtifactType {
	File;

	public String getName() {
		return name();
	}

	public String getContentType() {
		return "oct/stream";
	}

	public String getExtension() {
		return "file";
	}

}
