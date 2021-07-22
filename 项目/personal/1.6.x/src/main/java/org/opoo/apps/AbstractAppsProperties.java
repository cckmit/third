package org.opoo.apps;

import static org.apache.commons.lang.math.NumberUtils.toInt;

public abstract class AbstractAppsProperties implements	AppsProperties<String, String> {

	public boolean getBooleanProperty(String propertyKey) {
        return Boolean.valueOf(get(propertyKey));
    }

    public boolean getProperty(String propertyKey, boolean defaultValue) {
        String value = get(propertyKey);
        if (value != null) {
            return Boolean.valueOf(value);
        }
        else {
            return defaultValue;
        }
    }

    public int getProperty(String propertyKey, int defaultValue) {
        return toInt(get(propertyKey), defaultValue);
    }
    
    public int getIntProperty(String propertyKey){
    	String value = get(propertyKey);
//        if (value != null) {
//        	throw new NullPointerException();
//        }
        return toInt(value);
    }
    
    public String getProperty(String propertyKey) {
		return get(propertyKey);
	}

	public String getProperty(String propertyKey, String defaultValue) {
		String value = get(propertyKey);
		if(value != null){
			return value;
		}
		return defaultValue;
	}
}
