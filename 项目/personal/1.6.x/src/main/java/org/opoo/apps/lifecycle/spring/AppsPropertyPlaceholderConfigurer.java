package org.opoo.apps.lifecycle.spring;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 可以使用Globals中的属性最后属性替换的属性配置器。
 * 这个类自身也可以进行属性替换。
 *
 * PropertyPlaceholderConfigurer only post-processes beans within the same context, it is not inheritable.
 * 所有的 post processor都只在同一个 context 中有效。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class AppsPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private static final Log log = LogFactory.getLog(AppsPropertyPlaceholderConfigurer.class);
	private static Pattern pattern = Pattern.compile("\\S*\\$\\{(\\w+)\\}\\S*");
	private static final String PROP_INCLUDE_SETUP_PROPS = "apps.placeholder.includeSetupProperties";
	private static final String PROP_INCLUDE_RUNTIME_PROPS = "apps.placeholder.includeRuntimeProperties";

	private boolean includeSetupProperties = getSystemBooleanProperty(PROP_INCLUDE_SETUP_PROPS, false);
	private boolean includeRuntimeProperties = getSystemBooleanProperty(PROP_INCLUDE_RUNTIME_PROPS, true);
	private boolean enableInternalReplace = false;

	public boolean isIncludeSetupProperties() {
		return includeSetupProperties;
	}

	public void setIncludeSetupProperties(boolean includeSetupProperties) {
		this.includeSetupProperties = includeSetupProperties;
	}

	public boolean isEnableInternalReplace() {
		return enableInternalReplace;
	}

	public void setEnableInternalReplace(boolean enableInternalReplace) {
		this.enableInternalReplace = enableInternalReplace;
	}
	
	public boolean isIncludeRuntimeProperties() {
		return includeRuntimeProperties;
	}

	public void setIncludeRuntimeProperties(boolean includeRuntimeProperties) {
		this.includeRuntimeProperties = includeRuntimeProperties;
	}

	@Override
	protected void loadProperties(Properties props) throws IOException {
		super.loadProperties(props);
		if(enableInternalReplace){
			processInternalPlaceholder(props);
		}
//		if(includeSetupProperties){
//			loadSetupProperties(props);
//		}
	}
	
	
	private void processInternalPlaceholder(Properties p, String key){
		String v = p.getProperty(key);
		if(v != null){
			Matcher m = pattern.matcher(v);
			if(m.matches() && m.groupCount() > 0){
				String k = m.group(1);
				//log.debug(":::" + k);
				processInternalPlaceholder(p, k);
				v = v.replaceAll("\\$\\{(\\w+)\\}", p.getProperty(k));
				p.setProperty(key, v);
			}
		}
	}

	private void processInternalPlaceholder(Properties p) {
		if (p.isEmpty()) {
			Set<Object> set = new HashSet<Object>(p.keySet());
			for (Object key : set) {
				processInternalPlaceholder(p, (String)key);
			}
		}
	}
	
//	private void loadSetupProperties(Properties props){
//		List<String> list = AppsGlobals.getSetupPropertyNames();
//		//System.out.println(list);
//		for(String k: list){
//			String v = AppsGlobals.getSetupProperty(k);
//			if(v == null) v = "";
//			props.put(k, v);
//		}
//		log.debug("Add globals setup properties to config: " + list.size());
//	}
	
	@Override
	protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
		String propVal = null;
		if (systemPropertiesMode == SYSTEM_PROPERTIES_MODE_OVERRIDE) {
			propVal = resolveSystemProperty(placeholder);
		}
		if(propVal == null && includeRuntimeProperties){
			propVal = AppsGlobals.getProperty(placeholder);
			debugLog("runtime", placeholder, propVal);
		}
		if(propVal == null && includeSetupProperties){
			propVal = AppsGlobals.getSetupProperty(placeholder);
			debugLog("setup", placeholder, propVal);
		}
		if (propVal == null) {
			propVal = resolvePlaceholder(placeholder, props);
		}
		if (propVal == null && systemPropertiesMode == SYSTEM_PROPERTIES_MODE_FALLBACK) {
			propVal = resolveSystemProperty(placeholder);
		}
		return propVal;
	}
	
//	/* (non-Javadoc)
//	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#resolvePlaceholder(java.lang.String, java.util.Properties)
//	 */
//	@Override
//	protected String resolvePlaceholder(String placeholder, Properties props) {
//		String propVal = super.resolvePlaceholder(placeholder, props);
//		if(propVal == null && includeRuntimeProperties){
//			propVal = AppsGlobals.getProperty(placeholder);
//			debugLog("runtime", placeholder, propVal);
//		}
//		if(propVal == null && includeSetupProperties){
//			propVal = AppsGlobals.getSetupProperty(placeholder);
//			debugLog("setup", placeholder, propVal);
//		}
//		return propVal;
//	}
	
	private static void debugLog(String propType, String placeholder, String propVal){
		if(log.isDebugEnabled()){
			String lowerCase = placeholder.toLowerCase();
			if(lowerCase.endsWith("password") || lowerCase.endsWith("passwd")){
				log.debug("Resolving " + propType + " property placeholder: " + placeholder + " -> [PROTECTED]");
			}else{
				log.debug("Resolving " + propType + " property placeholder: " + placeholder + " -> " + propVal);
			}
		}
	}
	
	private static boolean getSystemBooleanProperty(String propName, boolean defaultValue){
		String value = System.getProperty(propName);
		if(value != null){
			return value.equalsIgnoreCase("true");
		}
		return defaultValue;
	}
}
