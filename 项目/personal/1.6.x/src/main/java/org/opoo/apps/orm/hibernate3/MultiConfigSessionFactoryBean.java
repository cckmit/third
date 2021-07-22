package org.opoo.apps.orm.hibernate3;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Mappings;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.event.EventListeners;
import org.opoo.apps.AppsGlobals;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.orm.hibernate3.TypeDefinitionBean;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class MultiConfigSessionFactoryBean extends LocalSessionFactoryBean 
	implements ApplicationContextAware, BeanNameAware {
	
	private static final Log log = LogFactory.getLog(MultiConfigSessionFactoryBean.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	
	private String beanName;
	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();;
	private Collection<ConfigurationProvider> configurationProviders;


	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.orm.hibernate3.LocalSessionFactoryBean#postProcessConfiguration(org.hibernate.cfg.Configuration)
	 */
	@Override
	protected void postProcessConfiguration(Configuration config) throws HibernateException {
		super.postProcessConfiguration(config);
		if(configurationProviders != null){
			try{
				for(ConfigurationProvider provider: configurationProviders){
					processConfiguration(config, provider);
				}
			}catch(Exception e){
				throw new HibernateException(e);
			}
		}
	}
	
	protected void processConfiguration(Configuration config, ConfigurationProvider provider) throws Exception{
		Properties entityCacheStrategies = provider.getEntityCacheStrategies();
		Properties collectionCacheStrategies = provider.getCollectionCacheStrategies();
		Map<String,Object> eventListeners = provider.getEventListeners();
		Map<String, SQLFunction> functions = provider.getSQLFunctions();
		
		if (entityCacheStrategies != null) {
			// Register cache strategies for mapped entities.
			for (Enumeration<?> classNames = entityCacheStrategies.propertyNames(); classNames.hasMoreElements();) {
				String className = (String) classNames.nextElement();
				String[] strategyAndRegion =
						StringUtils.commaDelimitedListToStringArray(entityCacheStrategies.getProperty(className));
				if (strategyAndRegion.length > 1) {
					config.setCacheConcurrencyStrategy(className, strategyAndRegion[0], strategyAndRegion[1]);
				}
				else if (strategyAndRegion.length > 0) {
					config.setCacheConcurrencyStrategy(className, strategyAndRegion[0]);
				}
			}
		}

		if (collectionCacheStrategies != null) {
			// Register cache strategies for mapped collections.
			for (Enumeration<?> collRoles = collectionCacheStrategies.propertyNames(); collRoles.hasMoreElements();) {
				String collRole = (String) collRoles.nextElement();
				String[] strategyAndRegion =
						StringUtils.commaDelimitedListToStringArray(collectionCacheStrategies.getProperty(collRole));
				if (strategyAndRegion.length > 1) {
					config.setCollectionCacheConcurrencyStrategy(collRole, strategyAndRegion[0], strategyAndRegion[1]);
				}
				else if (strategyAndRegion.length > 0) {
					config.setCollectionCacheConcurrencyStrategy(collRole, strategyAndRegion[0]);
				}
			}
		}

		if (eventListeners != null) {
			// Register specified Hibernate event listeners.
			for (Iterator<Map.Entry<String,Object>> it = eventListeners.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String,Object> entry = (Map.Entry<String,Object>) it.next();
				Assert.isTrue(entry.getKey() instanceof String, "Event listener key needs to be of type String");
				String listenerType = (String) entry.getKey();
				Object listenerObject = entry.getValue();
				if (listenerObject instanceof Collection) {
					Collection<?> listeners = (Collection<?>) listenerObject;
					EventListeners listenerRegistry = config.getEventListeners();
					Object[] listenerArray =
							(Object[]) Array.newInstance(listenerRegistry.getListenerClassFor(listenerType), listeners.size());
					listenerArray = listeners.toArray(listenerArray);
					config.setListeners(listenerType, listenerArray);
				}
				else {
					config.setListener(listenerType, listenerObject);
				}
			}
		}
		
		
		if(functions != null){
			for(Map.Entry<String, SQLFunction> entry: functions.entrySet()){
				config.addSqlFunction(entry.getKey(), entry.getValue());
			}
		}
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.springframework.orm.hibernate3.LocalSessionFactoryBean#postProcessMappings(org.hibernate.cfg.Configuration)
	 */
	@Override
	protected void postProcessMappings(Configuration config) throws HibernateException {
		super.postProcessMappings(config);
		if(configurationProviders != null){
			try{
				for(ConfigurationProvider provider: configurationProviders){
					processMappings(config, provider);
				}
			}catch(Exception e){
				throw new HibernateException(e);
			}
		}
	}
	
	protected void processMappings(Configuration config, ConfigurationProvider provider) throws Exception{
		TypeDefinitionBean[] typeDefinitions = provider.getTypeDefinitions();
		FilterDefinition[] filterDefinitions = provider.getFilterDefinitions();
		String[] mappingResources = provider.getMappingResources();
		Resource[] mappingLocations = provider.getMappingLocations();
		Resource[] cacheableMappingLocations = provider.getCacheableMappingLocations();
		Resource[] mappingJarLocations = provider.getMappingJarLocations();
		Resource[] mappingDirectoryLocations = provider.getMappingDirectoryLocations();
		
		
		
		
		if (typeDefinitions != null) {
			// Register specified Hibernate type definitions.
			Mappings mappings = config.createMappings();
			for (int i = 0; i < typeDefinitions.length; i++) {
				TypeDefinitionBean typeDef = typeDefinitions[i];
				mappings.addTypeDef(typeDef.getTypeName(), typeDef.getTypeClass(), typeDef.getParameters());
			}
		}

		if (filterDefinitions != null) {
			// Register specified Hibernate FilterDefinitions.
			for (int i = 0; i < filterDefinitions.length; i++) {
				config.addFilterDefinition(filterDefinitions[i]);
			}
		}
		
//
//		if (this.configLocations != null) {
//			for (int i = 0; i < this.configLocations.length; i++) {
//				// Load Hibernate configuration from given location.
//				config.configure(this.configLocations[i].getURL());
//			}
//		}
//
//		if (this.hibernateProperties != null) {
//			// Add given Hibernate properties to Configuration.
//			config.addProperties(this.hibernateProperties);
//		}
//
//		if (dataSource != null) {
//			Class providerClass = LocalDataSourceConnectionProvider.class;
//			if (isUseTransactionAwareDataSource() || dataSource instanceof TransactionAwareDataSourceProxy) {
//				providerClass = TransactionAwareDataSourceConnectionProvider.class;
//			}
//			else if (config.getProperty(Environment.TRANSACTION_MANAGER_STRATEGY) != null) {
//				providerClass = LocalJtaDataSourceConnectionProvider.class;
//			}
//			// Set Spring-provided DataSource as Hibernate ConnectionProvider.
//			config.setProperty(Environment.CONNECTION_PROVIDER, providerClass.getName());
//		}
//
//		if (this.cacheProvider != null) {
//			// Expose Spring-provided Hibernate CacheProvider.
//			config.setProperty(Environment.CACHE_PROVIDER, LocalCacheProviderProxy.class.getName());
//		}

		if (mappingResources != null) {
			// Register given Hibernate mapping definitions, contained in resource files.
			for (int i = 0; i < mappingResources.length; i++) {
				Resource resource = new ClassPathResource(mappingResources[i].trim(), beanClassLoader);
				config.addInputStream(resource.getInputStream());
			}
		}

		if (mappingLocations != null) {
			// Register given Hibernate mapping definitions, contained in resource files.
			for (int i = 0; i < mappingLocations.length; i++) {
				config.addInputStream(mappingLocations[i].getInputStream());
			}
		}

		if (cacheableMappingLocations != null) {
			// Register given cacheable Hibernate mapping definitions, read from the file system.
			for (int i = 0; i < cacheableMappingLocations.length; i++) {
				config.addCacheableFile(cacheableMappingLocations[i].getFile());
			}
		}

		if (mappingJarLocations != null) {
			// Register given Hibernate mapping definitions, contained in jar files.
			for (int i = 0; i < mappingJarLocations.length; i++) {
				Resource resource = mappingJarLocations[i];
				config.addJar(resource.getFile());
			}
		}

		if (mappingDirectoryLocations != null) {
			// Register all Hibernate mapping definitions in the given directories.
			for (int i = 0; i < mappingDirectoryLocations.length; i++) {
				File file = mappingDirectoryLocations[i].getFile();
				if (!file.isDirectory()) {
					throw new IllegalArgumentException(
							"Mapping directory location [" + mappingDirectoryLocations[i] +
							"] does not denote a directory");
				}
				config.addDirectory(file);
			}
		}
	}
	




	@SuppressWarnings("unchecked")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, ConfigurationProviderImpl> beans = applicationContext.getBeansOfType(ConfigurationProviderImpl.class);
		configurationProviders = new ArrayList<ConfigurationProvider>(beans.size());
		for(ConfigurationProviderImpl bean: beans.values()){
			if(beanName.equals(bean.getSessionFactoryBeanName())){
				configurationProviders.add(bean);
				log.info("Find ConfigurationProvider: " + bean);
			}
		}
	}
	public void setBeanName(String name) {
		this.beanName = name;
	}

	/**
	 * @return the configurationProviders
	 */
	public Collection<ConfigurationProvider> getConfigurationProviders() {
		return configurationProviders;
	}

	/**
	 * @param configurationProviders the configurationProviders to set
	 */
	public void setConfigurationProviders(
			Collection<ConfigurationProvider> configurationProviders) {
		this.configurationProviders = configurationProviders;
	}

	/* (non-Javadoc)
	 * @see org.springframework.orm.hibernate3.AbstractSessionFactoryBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Properties properties = getHibernateProperties();
		if(properties == null || properties.isEmpty()){
			List<String> names = AppsGlobals.getSetupPropertyNames();
			if(names != null && !names.isEmpty()){
				Properties props = new Properties();
				for(String name: names){
					if(name.startsWith("hibernate.")){
						String value = AppsGlobals.getSetupProperty(name);
						props.put(name, value);
						if(IS_DEBUG_ENABLED){
							log.debug(name + " = " + value);
						}
					}
				}
				
				if(!props.isEmpty()){
					log.info("Found hibernate properties from global properties.");
					setHibernateProperties(props);
				}
			}
		}
		super.afterPropertiesSet();
	}
	
	
}
