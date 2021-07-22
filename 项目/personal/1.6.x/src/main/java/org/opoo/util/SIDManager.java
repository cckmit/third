package org.opoo.util;

public interface SIDManager {
	
	String generateSID();

	boolean isValidSID(String sid);
}
