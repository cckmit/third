package org.opoo.apps.dv.office;

import org.opoo.apps.dv.ConversionManager;
import org.opoo.apps.dv.ConvertableObject;


public interface OfficeConversionManager
		extends
		ConversionManager<OfficeConversionMetaData, OfficeConversionArtifact, OfficeConversionArtifactType, OfficeConversionStatus, OfficeConversionStep> {
    /*
    boolean generateConversionArtifact(OfficeConversionMetaData cm,
    		OfficeConversionArtifactType type, int page);
    */    
	boolean isOfficeIntegrationLicensed();

	boolean isOffice2007Document(ConvertableObject co);

	boolean isSameDocumentType(String newFileName, String oldFileName);
}
