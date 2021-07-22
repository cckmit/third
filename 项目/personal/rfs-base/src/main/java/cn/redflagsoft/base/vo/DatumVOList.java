/*
 * $Id: DatumVOList.java 5383 2012-03-05 09:13:22Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Alex Lin
 *
 */
public class DatumVOList extends ArrayList<DatumVO> implements DatumInfoProvider{
	private static final long serialVersionUID = 395286886734930232L;
	private Long datumDispatchOrgId;
	private String datumDispatchOrgName;
	private Date datumDispatchTime;
	private int operation;
	
	//private List<DatumVO> datumVOList;
	public DatumVOList(List<DatumVO> list){
		//datumVOList = list;
		super(list);
	}
	

//	@Override
//	public DatumVO get(int index) {
//		return datumVOList.get(index);
//	}
//
//
//	@Override
//	public int size() {
//		return datumVOList.size();
//	}

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
	 * @return the datumDispatchOrgId
	 */
	public Long getDatumDispatchOrgId() {
		return datumDispatchOrgId;
	}

	/**
	 * @param datumDispatchOrgId the datumDispatchOrgId to set
	 */
	public void setDatumDispatchOrgId(Long datumDispatchOrgId) {
		this.datumDispatchOrgId = datumDispatchOrgId;
	}

	/**
	 * @return the datumDispatchOrgName
	 */
	public String getDatumDispatchOrgName() {
		return datumDispatchOrgName;
	}

	/**
	 * @param datumDispatchOrgName the datumDispatchOrgName to set
	 */
	public void setDatumDispatchOrgName(String datumDispatchOrgName) {
		this.datumDispatchOrgName = datumDispatchOrgName;
	}

	/**
	 * @return the datumDispatchTime
	 */
	public Date getDatumDispatchTime() {
		return datumDispatchTime;
	}

	/**
	 * @param datumDispatchTime the datumDispatchTime to set
	 */
	public void setDatumDispatchTime(Date datumDispatchTime) {
		this.datumDispatchTime = datumDispatchTime;
	}
}
