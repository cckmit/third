package org.opoo.apps.module;

/**
 * ÐèÒªÖØÆô¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class RequireRestartResult implements ConfiguratorResult {
	public static RequireRestartResult getRequireRestartResult() {
		return instance;
	}

	private RequireRestartResult() {
	}

	public String toString() {
		return "RequireRestartResult";
	}

	private static final RequireRestartResult instance = new RequireRestartResult();
}
