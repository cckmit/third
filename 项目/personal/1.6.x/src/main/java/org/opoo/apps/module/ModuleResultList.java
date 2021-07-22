package org.opoo.apps.module;

import java.util.ArrayList;
import java.util.List;


/**
 * 模块配置结果集合。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ModuleResultList extends ArrayList<ConfiguratorResult> {
	private static final long serialVersionUID = 2626283940777628076L;
	private ModuleMetaData metaData;

	public ModuleResultList(ModuleMetaData metaData, ConfiguratorResult... result){
		this.metaData = metaData;
		//add(result);
		for(ConfiguratorResult r: result){
			add(r);
		}
	}
	
	public ModuleResultList(ModuleMetaData metaData, List<ConfiguratorResult> results) {
		this.metaData = metaData;
		addAll(results);
	}

	public ModuleMetaData getModuleMetaData() {
		return metaData;
	}
}
