package org.opoo.apps.dv.office;

import org.opoo.apps.dv.ConversionArtifact;

public interface OfficeConversionArtifact extends ConversionArtifact<OfficeConversionArtifactType> {
	/**
	 * ҳ��
	 * @return ҳ��
	 * @see #getPartNumber()
	 */
	int getPage();
}
