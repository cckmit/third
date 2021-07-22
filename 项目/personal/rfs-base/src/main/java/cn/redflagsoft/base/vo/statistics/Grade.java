/*
 * $Id: Grade.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.vo.statistics;

/**
 * @author Alex Lin
 *
 */
public class Grade implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3649496791305033701L;

	public Grade(){
		
	}
	public Grade(int count, int status) {
		super();
		this.count = count;
		this.status = status;
	}
	//private Long id = 0L;
	private int count = 0;
	private int status = 0;

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	public void plusCount(){
		this.count++;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String toString(){
		return super.toString() + "(count=" + count + ")";
	}
}
