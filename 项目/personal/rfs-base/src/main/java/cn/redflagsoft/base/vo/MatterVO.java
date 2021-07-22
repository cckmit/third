/*
 * MatterVO.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.vo;


/**
 * @author Alex Lin
 * 
 */
public class MatterVO{
	private int operation;
	private Long[] matterIds;
	
	public MatterVO(){}
	
	public MatterVO(Long[] matterIds){
		this.matterIds = matterIds;
	}
	
	/**
	 * @return the operation
	 */
	public int getOperation() {
		return operation;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(int operation) {
		this.operation = operation;
	}
	/**
	 * @return the matterIds
	 */
	public Long[] getMatterIds() {
		return matterIds;
	}
	/**
	 * @param matterIds the matterIds to set
	 */
	public void setMatterIds(Long[] matterIds) {
		this.matterIds = matterIds;
	}

	
}
