package org.opoo.apps.lifecycle.spring;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.event.v2.core.PropertyEvent;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyOverrideConfigurer;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;

/**
 * A property override configurer that uses AppsProperties as it source.  Allows explicit mapping of Apps (system)
 * properties to Spring bean properties via 'appsPropertyMappings', or implicit mappings where the apps property
 * <p/>
 * Also updates bean properties when correspsonding apps properties are changed.
 * <p/>
 * See http://blog.arendsen.net/index.php/2005/03/12/configuration-management-with-spring/ See
 * http://static.springframework.org/spring/docs/2.0.x/api/org/springframework/beans/factory/config/PropertyOverrideConfigurer.html
 * See http://static.springframework.org/spring/docs/2.0.x/reference/beans.html
 */
public class AppsPropertyOverrideConfigurer extends PropertyOverrideConfigurer
        implements BeanFactoryAware, org.opoo.apps.event.v2.EventListener<PropertyEvent>
{

    private static final Logger log = LogManager.getLogger(AppsPropertyOverrideConfigurer.class);
    private static final String DEFAULT_PROPERTY_PREFIX = "spring";

    private BiMap<String, String> appsPropertyMappings = HashBiMap.create();
    private String appsPropertyPrefix = DEFAULT_PROPERTY_PREFIX;
    private ConfigurableListableBeanFactory configurableBeanFactory;
    private boolean dynamic = true;

    @Required
    public final void setAppsPropertyMappings(Map<String, String> appsPropertyMappings) {
        this.appsPropertyMappings = HashBiMap.create(appsPropertyMappings);
    }

    /**
     * The AppsPropertyPrefix controls the prefix used to identify which apps properties are used to implicitly map to
     * Spring bean properties.  For example, if this is set to "spring", a Apps Property such as "spring.fooTask.period"
     * would be used to set the "period" property of the "fooTask" Spring bean, provided the Apps property has a value
     * and the Spring bean has such a property.
     *
     * @param appsPropertyPrefix The string prefix used to find implicit Apps properties.
     */
    public final void setAppsPropertyPrefix(String appsPropertyPrefix) {
        if (StringUtils.isBlank(appsPropertyPrefix)) {
            throw new IllegalArgumentException("appsPropertyPrefix cannot be blank");
        }
        this.appsPropertyPrefix = appsPropertyPrefix;
    }

    public final void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            this.configurableBeanFactory = (ConfigurableListableBeanFactory) beanFactory;
        }
        else {
            throw new IllegalStateException("BeanFactory is not configural. AppsPropertyOverrideConfiurer is not supported");
        }
    }

    public final void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public void init() {
        validateBeanMappings(appsPropertyMappings.keySet());
        Properties props = new Properties();

        //find convention matching apps properties
        List<String> appsPropertyNames = AppsGlobals.getPropertyNames();
        for (String appsPropertyName : appsPropertyNames) {
            resolveConventionProperty(props, appsPropertyName);
        }

        //add explicit mappings
        for (String appsPropertyName : appsPropertyMappings.keySet()) {
            resolveProperty(props, appsPropertyName);
        }
        this.setProperties(props);
    }

    private void validateBeanMappings(Set<String> beanPropertyMappings) {
        for (String beanPropertyMapping : beanPropertyMappings) {
            resolveReference(beanPropertyMapping);
        }
    }

    private void resolveConventionProperty(Properties props, String appsPropertyName) {
        if (appsPropertyName == null || !appsPropertyName
                .startsWith(appsPropertyPrefix + DEFAULT_BEAN_NAME_SEPARATOR))
        {
            return;
        }

        String value = AppsGlobals.getProperty(appsPropertyName);
        String beanAndProp = StringUtils
                .removeStart(appsPropertyName, appsPropertyPrefix + DEFAULT_BEAN_NAME_SEPARATOR);
        String beanName = beanAndProp.substring(0, beanAndProp.indexOf("."));
        //check that bean exists before setting property
        if (value != null && configurableBeanFactory.containsBean(beanName)) {
            props.put(beanAndProp, value);
        }
    }

    private void resolveProperty(Properties props, String appsPropertyName) {
        try {
            props.put(appsPropertyName, AppsGlobals.getProperty(appsPropertyMappings.get(appsPropertyName)));
        }
        catch (Exception e) {
            log.debug(String.format("Couldn't load AppsProperty, %s: %s", appsPropertyName, e.getMessage()));
        }
    }

    public void handle(PropertyEvent e) {
        if (e.getName() == null || !dynamic) {
            return;
        }

        String beanNameAndPropertyName = null;
        if (appsPropertyMappings.containsValue(e.getName())) {
            beanNameAndPropertyName = appsPropertyMappings.inverse().get(e.getName());
        }
        else if (e.getName().startsWith(appsPropertyPrefix)) {
            beanNameAndPropertyName = StringUtils
                    .removeStart(e.getName(), appsPropertyPrefix + DEFAULT_BEAN_NAME_SEPARATOR);
        }

        if (StringUtils.isEmpty(beanNameAndPropertyName)) {
            return;
        }
        BeanPropertyReference reference = resolveReference(beanNameAndPropertyName);
        if (e.getType().equals(PropertyEvent.Type.REMOVED) || e.getValue() == null) {
            configureOriginalValue(reference);
        }
        else {
            setBeanProperty(e.getValue(), reference);
        }
    }

    private List<String> splitBeanNameAr(String beanNameAndPropertyName){
    	String[] arr = beanNameAndPropertyName.split("\\.");
    	List<String> list = Lists.newArrayList();
    	for(String a: arr){
    		list.add(a.trim());
    	}
    	return list;
    }
    
    private BeanPropertyReference resolveReference(String beanNameAndPropertyName) {
        List<String> beanNameAr = splitBeanNameAr(beanNameAndPropertyName);
        if (beanNameAr.size() != 2) {
            throw new IllegalArgumentException("Can't map Apps property \"" + beanNameAndPropertyName
                    + "\" to Spring bean name and property name");
        }
        BeanPropertyReference reference = new BeanPropertyReference(beanNameAr.get(0), beanNameAr.get(1));

        //make sure there is a bean with this name and property
        if (!configurableBeanFactory.containsBeanDefinition(reference.beanName)) {
            throw new IllegalArgumentException("No bean found for name \"" + reference.beanName + "\"");
        }

        if (!dynamic) {
            return reference;
        }

        BeanDefinition bd = configurableBeanFactory.getBeanDefinition(reference.beanName);
        //if (bd.isPrototype()) {
        if(isPrototype(bd)){
            throw new IllegalArgumentException("Bean \"" + reference.beanName + "\" does not support dynamic property "
                    + "override because it is Prototype scoped. Either disable dynamic updates, make the bean "
                    + "singleton scoped or add the property to the bean's definition.");
        }
        PropertyValue value = bd.getPropertyValues().getPropertyValue(reference.propName);
        if (value == null) {
            // not here, look for it in the parent chain
            String parentName = bd.getParentName();
            while (parentName != null && value == null) {
                BeanDefinition pbd = configurableBeanFactory.getBeanDefinition(parentName);
                value = pbd.getPropertyValues().getPropertyValue(reference.propName);
                parentName = bd.getParentName();
            }
        }
        if (value == null) {
            throw new IllegalArgumentException(
                    "Bean \"" + reference.beanName + "\" is missing property value \"" + reference.propName
                            + "\". It does not support dynamic updates. Either disable dynamic updates or "
                            + "add the property to the bean's definition.");
        }
        return reference;
    }
    
    private boolean isPrototype(BeanDefinition bd){
    	//Spring 3.0
    	//return bd.isPrototype();
    	return "prototype".equalsIgnoreCase(bd.getScope());
    }
    

    private void configureOriginalValue(BeanPropertyReference reference) {
        BeanDefinition bd = configurableBeanFactory.getBeanDefinition(reference.beanName);
        PropertyValue value = bd.getPropertyValues().getPropertyValue(reference.propName);
        if (value == null) {
            log.warn("Default value could not be determine for removed property: " + reference);
            return;
        }

        Object bean = configurableBeanFactory.getBean(reference.beanName);

        try {
            BeanWrapper beanWrapper = new BeanWrapperImpl(bean);
            beanWrapper.setPropertyValue(value);
            log.info("Set value \"" + value.getValue() + "\" on \"" + reference + "\"");
        }
        catch (Exception ex) {
            log.error("Couldn't set value \"" + value.getValue() + "\" on \"" + reference + "\"", ex);
        }
    }

    private void setBeanProperty(Object newValue, BeanPropertyReference reference) {
        Object bean = configurableBeanFactory.getBean(reference.beanName);

        try {
            BeanWrapper beanWrapper = new BeanWrapperImpl(bean);
            beanWrapper.setPropertyValue(reference.propName, newValue);
            log.info("Set value \"" + newValue + "\" on \"" + reference + "\"");
        }
        catch (Exception ex) {
            log.error("Couldn't set value \"" + newValue + "\" on \"" + reference + "\"", ex);
        }
    }

    private class BeanPropertyReference {
        private String beanName;
        private String propName;

        private BeanPropertyReference(String beanName, String propName) {
            this.beanName = beanName;
            this.propName = propName;
        }

        @Override
        public String toString() {
            return beanName + DEFAULT_BEAN_NAME_SEPARATOR + propName;
        }
    }
}
