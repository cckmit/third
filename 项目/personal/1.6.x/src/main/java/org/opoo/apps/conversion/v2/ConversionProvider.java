package org.opoo.apps.conversion.v2;

import java.io.File;

import org.opoo.apps.conversion.ConversionArtifactType;
import org.opoo.apps.conversion.ConversionRevision;
import org.opoo.apps.conversion.v2.ConversionManagerImplV2.ProcessingInfo;

public interface ConversionProvider {
	
	String getName();
	
	boolean isConvertable(Object object);

	ProcessingInfo createProcessingInfo();

//	void startConvert(ProcessingInfo info, ConversionRevision revision, File file);

	void handleFileGenerated(ProcessingInfo info, ConversionRevision rev, ConversionArtifactType type, int page, File file) throws Exception;
	
	void checkProcessingCompletion(ProcessingInfo info, ConversionRevision rev, ConversionArtifactType type, int page);
}
