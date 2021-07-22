package org.opoo.apps.dvi.office;

import org.opoo.apps.dvi.ConversionArtifact;

public interface OfficeConversionArtifact extends ConversionArtifact {
	/**
	 * Ò³Âë
	 * @return Ò³Âë
	 * @see #getPartNumber()
	 */
	int getPage();
}
