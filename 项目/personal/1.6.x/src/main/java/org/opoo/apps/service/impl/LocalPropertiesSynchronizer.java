package org.opoo.apps.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opoo.apps.bean.core.Property;
import org.opoo.apps.dao.PropertyDao;
import org.opoo.util.OpooPropertiesSynchronizer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated
 */
public class LocalPropertiesSynchronizer implements	OpooPropertiesSynchronizer, InitializingBean {
	private PropertyDao propertyDao;
	public PropertyDao getPropertyDao() {
		return propertyDao;
	}

	public void setPropertyDao(PropertyDao propertyDao) {
		this.propertyDao = propertyDao;
	}

	public void clear() {
		propertyDao.deleteAll();
	}

	public Map<String, String> loadAll() {
		List<Property> list = propertyDao.find();
		Map<String, String> map = new HashMap<String, String>();
		for(Property p: list){
			map.put(p.getName(), p.getValue());
		}
		return map;
	}

	public void remove(String arg0) {
		propertyDao.remove(arg0);
	}

	public void save(String name, String value) {
		propertyDao.save(new Property(name, value));
	}

	public void update(String name, String value) {
		propertyDao.update(new Property(name, value) );
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(propertyDao);
		//LocalProperties.setPropertiesSynchronizer(this);
	}
}
