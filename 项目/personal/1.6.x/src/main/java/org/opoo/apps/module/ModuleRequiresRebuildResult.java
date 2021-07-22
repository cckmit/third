package org.opoo.apps.module;


/**
 * 模块需要重建。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ModuleRequiresRebuildResult implements ConfiguratorResult {

	private static final ModuleRequiresRebuildResult instance = new ModuleRequiresRebuildResult();

	private ModuleRequiresRebuildResult() {
	}

	public static ModuleRequiresRebuildResult getModuleRequiresRebuildResult() {
		return instance;
	}
}