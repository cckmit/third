package org.opoo.apps.dvi.office;

import org.opoo.apps.dv.ConvertableObject;
import org.opoo.apps.dvi.ConversionManager;


public interface OfficeConversionManager extends ConversionManager {
    /*
    boolean generateConversionArtifact(OfficeConversionMetaData cm,
    		OfficeConversionArtifactType type, int page);
    */    
	boolean isOfficeIntegrationLicensed();

	boolean isOffice2007Document(ConvertableObject co);

	boolean isSameDocumentType(String newFileName, String oldFileName);
}
