package org.opoo.apps.dvi.office;

import java.util.List;

import org.opoo.apps.dvi.ConversionStorageService;
import org.opoo.ndao.support.ResultFilter;

public interface OfficeConversionStorageService extends ConversionStorageService {

//	OfficeConversionMetaData saveMetaData(OfficeConversionMetaData meta);
//
//	OfficeConversionMetaData updateMetaData(OfficeConversionMetaData meta);
//
//	OfficeConversionArtifact saveArtifact(OfficeConversionArtifact ca, File file) throws Exception;
	
	OfficeConversionErrorStep saveErrorStep(OfficeConversionErrorStep conversionErrorStep);
	
	void deleteSteps(OfficeConversionMetaData cm);
	
	List<OfficeConversionErrorStep> getErrorSteps(OfficeConversionMetaData cm);
	
	List<Long> getErrorConversionMetadataIDs(ResultFilter filter);
}
