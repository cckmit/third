/*
 * $Id: RolePost.java 3996 2010-10-18 06:56:46Z lcj $
 * RolePost.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author mwx
 *
 */
public class RolePost extends VersionableBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5184019557493502408L;
	private Long sn;
	private Long roleID;
	private Long postID;
	
	public Long getId(){
		return getSn();
	}
	
	public void setId(Long id){
		setId(id);
	}
	/**
	 * @return the sn
	 */
	public Long getSn() {
		return sn;
	}
	/**
	 * ±àºÅ
	 * 
	 * Î¨Ò»
	 * @param sn the sn to set
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}
	/**
	 * @return the roleID
	 */
	public Long getRoleID() {
		return roleID;
	}
	/**
	 * ½ÇÉ«
	 * @param roleID the roleID to set
	 */
	public void setRoleID(Long roleID) {
		this.roleID = roleID;
	}
	/**
	 * @return the postID
	 */
	public Long getPostID() {
		return postID;
	}
	/**
	 * ¸ÚÎ»
	 * @param postID the postID to set
	 */
	public void setPostID(Long postID) {
		this.postID = postID;
	}
	
}
