package org.opoo.apps.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.bean.core.Property;
import org.opoo.apps.cache.AbstractClusterTask;
import org.opoo.apps.cache.CacheFactory;
import org.opoo.apps.dao.PropertyDao;
import org.opoo.apps.database.DataSourceManager;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventDispatcherAware;
import org.opoo.apps.event.v2.core.PropertyEvent;
import org.opoo.apps.service.Properties;
import org.opoo.util.AbstractOpooProperties;


/**
 * 系统属性类。
 * 
 * 实现了DatabaseProperties一样的功能。
 * 数据保存在数据库中，使用Hibernate的dao进行访问，支持集群。
 * 改类在系统环境启动之后（至少Hibernate启动之后）才能正确运行，所以在一定程度上不能替代
 * DatabaseProperties的功能。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class PropertiesImpl extends AbstractOpooProperties implements Properties, EventDispatcherAware, CacheFactory.ClusteringListener {
	private static final Log log = LogFactory.getLog(PropertiesImpl.class);
	private Map<String,String> properties = new ConcurrentHashMap<String, String>();
	private PropertyDao propertyDao;
	private EventDispatcher dispatcher;
	
	public PropertiesImpl(PropertyDao propertyDao){
		this.propertyDao = propertyDao;
		initialize();
		CacheFactory.addClusteringListener(this);
	}
	
	public void initialize(){
		List<Property> list = this.propertyDao.find();
		properties.clear();
		for(Property p: list){
			properties.put(p.getName(), p.getValue());
		}
	}
	
	public Collection<String> getChildrenNames(String parentKey) {
		Collection<String> results = new HashSet<String>();
		String parentKeyDot = parentKey + ".";
		for (String key : keySet()) {
			if (!key.startsWith(parentKeyDot) || key.equals(parentKey)) {
				continue;
			}
			int dotIndex = key.indexOf(".", parentKey.length() + 1);
			if (dotIndex < 1) {
				if (!results.contains(key)) {
					results.add(key);
				}
			} else {
				String name = parentKey
						+ key.substring(parentKey.length(), dotIndex);
				results.add(name);
			}
		}
		return results;
	}

	public Collection<String> getProperties(String parentKey) {
		Collection<String> keys = getChildrenNames(parentKey);
		Collection<String> values = new ArrayList<String>();
		for (String key : keys) {
			String value = getProperty(key);
			if (value != null) {
				values.add(value);
			}
		}
		return values;
	}

	public Collection<String> getPropertyNames() {
		return new HashSet<String>(properties.keySet());
	}

	public void clear() {
		throw new UnsupportedOperationException();
		//properties.clear();
		//propertyDao.deleteAll();
	}

	public boolean containsKey(Object key) {
		return properties.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return properties.containsValue(value);
	}

	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return Collections.unmodifiableSet(properties.entrySet());
	}

	public String get(Object key) {
		return properties.get(key);
	}

	public boolean isEmpty() {
		return properties.isEmpty();
	}

	public Set<String> keySet() {
		return Collections.unmodifiableSet(properties.keySet());
	}

	public String put(String key, String value) {
		String s = null;
		if (key == null || value == null)
			throw new NullPointerException("Key or value cannot be null. Key=" + key + ", value=" + value);
		if ("".equals(key)) {
			log.warn((new StringBuilder()).append("Can not save a blank key: '").append(key).append("'.").toString());
		} else {
			if (value.equals("") && DataSourceManager.getMetaData().getDatabaseType().isOracle()) {
				value = " ";
			}
			if (key.endsWith(".")){
				key = key.substring(0, key.length() - 1);
			}
			key = key.trim();
			String oldValue = properties.put(key, value);
			if (oldValue != null) {
				if (!oldValue.equals(value)) {
					//updateProperty(key, value);
					propertyDao.update(new Property(key, value));
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("PARAM_MODIFIED_OLD_VALUE", oldValue);
					fireEvent(new PropertyEvent(PropertyEvent.Type.MODIFIED, key, value, params));
				}
			} else {
				//insertProperty(key, value);
				propertyDao.save(new Property(key, value));
				fireEvent(key, value, PropertyEvent.Type.ADDED);
			}
			CacheFactory.doClusterTask(new PropertyClusterPutTask(key, value));
			s = oldValue;
		}

		return s;
	}

	private void fireEvent(String key, String value, PropertyEvent.Type type) {
		fireEvent(new PropertyEvent(type, key, value, null));
	}

	private void fireEvent(PropertyEvent event) {
		if (dispatcher != null){
			dispatcher.dispatchEvent(event);
		}else{
			log.error("Unable to fire apps property event - missing dispatcher");
		}
	}
	
	public void putAll(Map<? extends String, ? extends String> m) {
		for(Map.Entry<? extends String, ? extends String> entry: m.entrySet()){
			put(entry.getKey(), entry.getValue());
		}
	}

	public String remove(Object key) {
		String value = properties.remove(key);
		if (value != null) {
			List<String> removedValues = new ArrayList<String>();
			Collection<String> propNames = getPropertyNames();
			String keyDot = (new StringBuilder()).append(key).append(".").toString();
			for (String name : propNames) {
				if (name.startsWith(keyDot)) {
					removedValues.add(properties.remove(name));
				}
			}

			//deleteProperty((String) key);
			propertyDao.remove((String) key);

			Map<String,Object> params = new HashMap<String,Object>();
			params.put("PARAM_REMOVED_VALUES", removedValues);

			fireEvent(new PropertyEvent(PropertyEvent.Type.REMOVED, (String) key, value, params));
			CacheFactory.doClusterTask(new PropertyClusterRemoveTask((String) key));
		}
		return value;
	}

	public int size() {
		return properties.size();
	}

	public Collection<String> values() {
		return Collections.unmodifiableCollection(properties.values());
	}
	
	
	public String toString(){
		return super.toString() + ":" + properties.toString();
	}

	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.dispatcher = eventDispatcher;
	}

	
	protected void clusterRemove(String key) {
		properties.remove(key);
		if(log.isDebugEnabled()){
			log.debug("集群删除属性：" + key);
		}
	}

	protected void clusterPut(String key, String value) {
		properties.put(key, value);
		if(log.isDebugEnabled()){
			log.debug("集群设置属性: " + key + " = " + value);
		}
	}
	
	class PropertyClusterRemoveTask extends AbstractClusterTask {
		private static final long serialVersionUID = -1903827921499972150L;
		private String key;
		protected PropertyClusterRemoveTask(String key) {
			this.key = key;
		}

		public void execute() {
			clusterRemove(key);
		}
	}
	
	class PropertyClusterPutTask extends AbstractClusterTask {
		private static final long serialVersionUID = -6716241834110683337L;
		private String key;
		private String value;

		protected PropertyClusterPutTask(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public void execute() {
			clusterPut(key, value);
		}
	}


	////////////////////////////////////////////////
	// CacheFactory.ClusteringListener
	///////////////////////////////////////////////
	/**
	 * 
	 */
	public void clusteringStarted() {
		log.debug("收到集群事件，重新初始化属性。");
		initialize();
	}
	/**
	 * 
	 */
	public void clusteringStopped() {
		
	}
}

