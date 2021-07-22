package org.opoo.apps.orm.hibernate3;

import java.util.Map;
import java.util.Properties;

import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.FilterDefinition;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate3.TypeDefinitionBean;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ConfigurationProviderImpl implements ConfigurationProvider {
	private String sessionFactoryBeanName;
	
	//private Resource[] configLocations;

	private String[] mappingResources;

	private Resource[] mappingLocations;

	private Resource[] cacheableMappingLocations;

	private Resource[] mappingJarLocations;

	private Resource[] mappingDirectoryLocations;

	//private Properties hibernateProperties;

	//private TransactionManager jtaTransactionManager;

	//private CacheProvider cacheProvider;

	//private LobHandler lobHandler;

	//private Interceptor entityInterceptor;

	//private NamingStrategy namingStrategy;

	private TypeDefinitionBean[] typeDefinitions;

	private FilterDefinition[] filterDefinitions;

	private Properties entityCacheStrategies;

	private Properties collectionCacheStrategies;

	private Map<String, Object> eventListeners;
	
	//private boolean schemaUpdate = false;

	//private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

	//private Configuration configuration;
	
	private Map<String, SQLFunction> sqlFunctions;
	
	public ConfigurationProviderImpl() {
		super();
	}
	
	
	/**
	 * @return the mappingResources
	 */
	public String[] getMappingResources() {
		return mappingResources;
	}

	/**
	 * @param mappingResources the mappingResources to set
	 */
	public void setMappingResources(String[] mappingResources) {
		this.mappingResources = mappingResources;
	}

	/**
	 * @return the mappingLocations
	 */
	public Resource[] getMappingLocations() {
		return mappingLocations;
	}

	/**
	 * @param mappingLocations the mappingLocations to set
	 */
	public void setMappingLocations(Resource[] mappingLocations) {
		this.mappingLocations = mappingLocations;
	}

	/**
	 * @return the cacheableMappingLocations
	 */
	public Resource[] getCacheableMappingLocations() {
		return cacheableMappingLocations;
	}

	/**
	 * @param cacheableMappingLocations the cacheableMappingLocations to set
	 */
	public void setCacheableMappingLocations(Resource[] cacheableMappingLocations) {
		this.cacheableMappingLocations = cacheableMappingLocations;
	}

	/**
	 * @return the mappingJarLocations
	 */
	public Resource[] getMappingJarLocations() {
		return mappingJarLocations;
	}

	/**
	 * @param mappingJarLocations the mappingJarLocations to set
	 */
	public void setMappingJarLocations(Resource[] mappingJarLocations) {
		this.mappingJarLocations = mappingJarLocations;
	}

	/**
	 * @return the mappingDirectoryLocations
	 */
	public Resource[] getMappingDirectoryLocations() {
		return mappingDirectoryLocations;
	}

	/**
	 * @param mappingDirectoryLocations the mappingDirectoryLocations to set
	 */
	public void setMappingDirectoryLocations(Resource[] mappingDirectoryLocations) {
		this.mappingDirectoryLocations = mappingDirectoryLocations;
	}

	/**
	 * @return the typeDefinitions
	 */
	public TypeDefinitionBean[] getTypeDefinitions() {
		return typeDefinitions;
	}

	/**
	 * @param typeDefinitions the typeDefinitions to set
	 */
	public void setTypeDefinitions(TypeDefinitionBean[] typeDefinitions) {
		this.typeDefinitions = typeDefinitions;
	}

	/**
	 * @return the filterDefinitions
	 */
	public FilterDefinition[] getFilterDefinitions() {
		return filterDefinitions;
	}

	/**
	 * @param filterDefinitions the filterDefinitions to set
	 */
	public void setFilterDefinitions(FilterDefinition[] filterDefinitions) {
		this.filterDefinitions = filterDefinitions;
	}

	/**
	 * @return the entityCacheStrategies
	 */
	public Properties getEntityCacheStrategies() {
		return entityCacheStrategies;
	}

	/**
	 * @param entityCacheStrategies the entityCacheStrategies to set
	 */
	public void setEntityCacheStrategies(Properties entityCacheStrategies) {
		this.entityCacheStrategies = entityCacheStrategies;
	}

	/**
	 * @return the collectionCacheStrategies
	 */
	public Properties getCollectionCacheStrategies() {
		return collectionCacheStrategies;
	}

	/**
	 * @param collectionCacheStrategies the collectionCacheStrategies to set
	 */
	public void setCollectionCacheStrategies(Properties collectionCacheStrategies) {
		this.collectionCacheStrategies = collectionCacheStrategies;
	}

	/**
	 * @return the eventListeners
	 */
	public Map<String, Object> getEventListeners() {
		return eventListeners;
	}

	/**
	 * @param eventListeners the eventListeners to set
	 */
	public void setEventListeners(Map<String, Object> eventListeners) {
		this.eventListeners = eventListeners;
	}


	/**
	 * @return the sessionFactoryBeanName
	 */
	public String getSessionFactoryBeanName() {
		return sessionFactoryBeanName;
	}


	/**
	 * @param sessionFactoryBeanName the sessionFactoryBeanName to set
	 */
	public void setSessionFactoryBeanName(String sessionFactoryBeanName) {
		this.sessionFactoryBeanName = sessionFactoryBeanName;
	}


	public Map<String, SQLFunction> getSQLFunctions() {
		return sqlFunctions;
	}
	
	public void setSQLFunctions(Map<String, SQLFunction> functions){
		this.sqlFunctions = functions;
	}
}
