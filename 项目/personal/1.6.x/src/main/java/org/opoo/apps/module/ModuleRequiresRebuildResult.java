package org.opoo.apps.module;


/**
 * ģ����Ҫ�ؽ���
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