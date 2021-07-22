/*
 * $Id: PostWork.java 3996 2010-10-18 06:56:46Z lcj $
 * PostWork.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

/**
 * @author mwx
 *
 */
public class PostWork extends VersionableBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5273969477202582517L;
	private Long sn;
	private Long postID;
	private Long workID;
	
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
	 * 编号
	 * 
	 * 唯一
	 * @param sn the sn to set
	 */
	public void setSn(Long sn) {
		this.sn = sn;
	}
	/**
	 * @return the postID
	 */
	public Long getPostID() {
		return postID;
	}
	/**
	 * 岗位
	 * @param postID the postID to set
	 */
	public void setPostID(Long postID) {
		this.postID = postID;
	}
	/**
	 * @return the workID
	 */
	public Long getWorkID() {
		return workID;
	}
	/**
	 * 工作
	 * @param workID the workID to set
	 */
	public void setWorkID(Long workID) {
		this.workID = workID;
	}
}
