package org.opoo.apps.security.dao.hibernate3;

import org.opoo.apps.dao.hibernate3.AbstractAppsHibernateDao;
import org.opoo.apps.id.IdGeneratorProvider;
import org.opoo.ndao.Domain;

public abstract class AbstractSecurityHibernateDao<T extends Domain<K>, K extends java.io.Serializable> extends AbstractAppsHibernateDao<T, K> {
	private IdGeneratorProvider<Long> securityIdGeneratorProvider;

	/**
	 * @return the securityIdGeneratorProvider
	 */
	public IdGeneratorProvider<Long> getSecurityIdGeneratorProvider() {
		return securityIdGeneratorProvider;
	}

	/**
	 * @param securityIdGeneratorProvider the securityIdGeneratorProvider to set
	 */
	public void setSecurityIdGeneratorProvider(IdGeneratorProvider<Long> securityIdGeneratorProvider) {
		this.securityIdGeneratorProvider = securityIdGeneratorProvider;
	}
}
