package org.opoo.apps.dvi.office;

import org.opoo.apps.dvi.ConversionArtifactType;

public enum OfficeConversionArtifactType implements ConversionArtifactType {
	Pdf, Preview, Thumbnail, Text;

	public String getName() {
		return name();
	}
}
