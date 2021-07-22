package org.opoo.apps.web.struts2.config;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.ExceptionMappingConfig;
import com.opensymphony.xwork2.config.entities.InterceptorMapping;
import com.opensymphony.xwork2.config.entities.ResultConfig;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class UnoverridableActionConfig extends ActionConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1403201868858158360L;
	private String caller;
	
	
	public UnoverridableActionConfig() {
		super();
	}

	@SuppressWarnings("unchecked")
	public UnoverridableActionConfig(String methodName, Class clazz, Map<String, Object> parameters,
			Map<String, ResultConfig> results, List<InterceptorMapping> interceptors) {
		super(methodName, clazz, parameters, results, interceptors);
	}

	public UnoverridableActionConfig(String methodName, String className, Map<String, Object> parameters,
			Map<String, ResultConfig> results, List<InterceptorMapping> interceptors) {
		super(methodName, className, parameters, results, interceptors);
	}

	@SuppressWarnings("unchecked")
	public UnoverridableActionConfig(String methodName, Class clazz, Map<String, Object> parameters,
			Map<String, ResultConfig> results, List<InterceptorMapping> interceptors,
			List<ExceptionMappingConfig> exceptionMappings) {
		super(methodName, clazz, parameters, results, interceptors, exceptionMappings);
	}

	public UnoverridableActionConfig(String methodName, String className, Map<String, Object> parameters,
			Map<String, ResultConfig> results, List<InterceptorMapping> interceptors,
			List<ExceptionMappingConfig> exceptionMappings) {
		super(methodName, className, parameters, results, interceptors, exceptionMappings);
	}

	public UnoverridableActionConfig(String methodName, String className, String packageName,
			Map<String, Object> parameters, Map<String, ResultConfig> results, List<InterceptorMapping> interceptors) {
		super(methodName, className, packageName, parameters, results, interceptors);
	}

	public UnoverridableActionConfig(String methodName, String className, String packageName,
			Map<String, Object> parameters, Map<String, ResultConfig> results, List<InterceptorMapping> interceptors,
			List<ExceptionMappingConfig> exceptionMappings) {
		super(methodName, className, packageName, parameters, results, interceptors, exceptionMappings);
	}

	/**
	 * @return the caller
	 */
	public String getCaller() {
		return caller;
	}

	/**
	 * @param caller the caller to set
	 */
	public void setCaller(String caller) {
		this.caller = caller;
	}

}
