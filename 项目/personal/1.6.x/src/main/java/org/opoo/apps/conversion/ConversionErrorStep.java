package org.opoo.apps.conversion;

import java.io.Serializable;

public interface ConversionErrorStep extends Serializable{
	
	long getRevisionId();
	
	String getStep();
	
	long getModificationDate();
	
	String getMessage();
}
