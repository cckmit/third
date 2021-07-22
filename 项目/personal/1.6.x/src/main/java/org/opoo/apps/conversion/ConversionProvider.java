package org.opoo.apps.conversion;

import java.io.InputStream;



public interface ConversionProvider {
	
	boolean isConvertable(Object object);
	
	boolean convert(ConversionRevision revision, InputStream originalFile) throws Exception;
	
	ConversionArtifactType[] getSupportedTypes();
}
