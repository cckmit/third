package org.opoo.apps.dao.hibernate3;

import org.opoo.apps.bean.core.Property;
import org.opoo.apps.dao.PropertyDao;



public class PropertyHibernateDao extends AbstractAppsHibernateDao<Property,String> implements PropertyDao {


	@Override
	/**
	 * 删除时将删除子节点。
	 */
	public int remove(String name) {
		int n = getQuerySupport().executeUpdate("delete from Property where name like ?", name);
		n += getQuerySupport().executeUpdate("delete from Property where name like ?", name + ".%");
		return n;
	}

	protected String getIdProperty() {
		return "name";
	}

	public int deleteAll() {
		return getQuerySupport().executeUpdate("delete from Property");
	}
}
