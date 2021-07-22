package org.opoo.apps.orm.hibernate3;

import java.util.Map;
import java.util.Properties;

import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.FilterDefinition;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate3.TypeDefinitionBean;

/**
 * Hibernate≈‰÷√Ã·π©’ﬂ°£
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface ConfigurationProvider {

	public String[] getMappingResources();

	public Resource[] getMappingLocations();

	public Resource[] getCacheableMappingLocations();

	public Resource[] getMappingJarLocations() ;

	public Resource[] getMappingDirectoryLocations();

	public TypeDefinitionBean[] getTypeDefinitions();

	public FilterDefinition[] getFilterDefinitions() ;

	public Properties getEntityCacheStrategies();

	public Properties getCollectionCacheStrategies();
	
	public Map<String, Object> getEventListeners() ;
	
	public Map<String, SQLFunction> getSQLFunctions();
}
