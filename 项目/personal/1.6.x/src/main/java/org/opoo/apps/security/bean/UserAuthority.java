package org.opoo.apps.security.bean;

import org.opoo.ndao.Domain;

/**
 * 用户权限。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class UserAuthority implements Domain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1650153761357597145L;
	private String username;
	private String authority;
	private Long id;
	
	public UserAuthority(String username, String authority) {
		super();
		this.username = username;
		this.authority = authority;
	}
	public UserAuthority() {
		super();
	}

	
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}
	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
