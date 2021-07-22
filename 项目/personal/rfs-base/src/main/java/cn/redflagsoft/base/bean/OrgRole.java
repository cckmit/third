package cn.redflagsoft.base.bean;

import java.util.Date;


/**
 * 
 * 
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OrgRole extends VersionableBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3685120987540323562L;

	private Long orgId;
	private Long roleId;
	/**
	 * 保存，暂时无用。
	 */
	private Date deleteTime;
	private Long deleter;
	
	
	
	public OrgRole() {
		super();
	}
	
	
	public OrgRole(Long orgId, Long roleId) {
		super();
		this.orgId = orgId;
		this.roleId = roleId;
	}


	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return the deleteTime
	 */
	public Date getDeleteTime() {
		return deleteTime;
	}
	/**
	 * @param deleteTime the deleteTime to set
	 */
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}


	public Long getDeleter() {
		return deleter;
	}


	public void setDeleter(Long deleter) {
		this.deleter = deleter;
	}
	
	
}
