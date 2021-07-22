package org.opoo.apps.conversion;

import java.io.Serializable;


public interface ConversionArtifact extends Serializable{
	
	long getRevisionId();
	
	String getInstanceId();
	
	ConversionArtifactType getType();
	
	int getPage();
	
	String getFilename();

	String getContentType();
	
	long getLength();
	
	String getStorageKey();
}
