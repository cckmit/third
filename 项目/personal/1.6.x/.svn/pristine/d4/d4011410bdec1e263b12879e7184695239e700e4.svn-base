package org.opoo.apps.license;

import java.io.Serializable;

import org.opoo.apps.security.UserManager;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;


@Configurable(value="sampleObject",preConstruction=true)
public class DomainAopSampleObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7340324078734396498L;
	private transient UserManager userManager;
	public DomainAopSampleObject() {
		super();
		System.out.println(userManager);
	}
	/**
	 * @return the userManager
	 */
	public UserManager getUserManager() {
		return userManager;
	}
	/**
	 * @param userManager the userManager to set
	 */
	@Transactional
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
}
