package org.opoo.apps.license.multicast;

import java.util.Properties;

import org.opoo.apps.AppsGlobals;

public abstract class MulticastConfigUtils {

	public static String getValue(Properties props, String key){
		if(props != null){
			String p = props.getProperty(key);
			if(p != null){
				return p;
			}
		}
		return AppsGlobals.getSetupProperty(key);
	}
	
	public static String getValue(Properties props, String key, String defaultValue){
		if(props != null){
			String p = props.getProperty(key);
			if(p != null){
				return p;
			}
		}
		return AppsGlobals.getSetupProperty(key, defaultValue);
	}
	
	public static int getValue(Properties props, String key, int defaultValue){
		if(props != null){
			String p = props.getProperty(key);
			if(p != null){
				return Integer.parseInt(p);
			}
		}
		return AppsGlobals.getSetupProperty(key, defaultValue);
	}
}
