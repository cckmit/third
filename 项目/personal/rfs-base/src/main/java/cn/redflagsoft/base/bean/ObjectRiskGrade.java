/*
 * $Id: ObjectRiskGrade.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;


/**
 * @author Alex Lin
 *
 */
public class ObjectRiskGrade extends RFSObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2943547566997519519L;
	
	public ObjectRiskGrade(){
		super();
	}

	public ObjectRiskGrade(Long id, short lifeStage, Long manager, byte managerType, byte grade){
		super();
		this.setId(id);
		this.setLifeStage(lifeStage);
		this.setManager(manager);
		this.setManagerType(managerType);
		this.setRiskGrade(grade);
	}
	
	public ObjectRiskGrade(RFSObject obj){
		super();
		this.setId(obj.getId());
		this.setLifeStage(obj.getLifeStage());
		this.setManager(obj.getManager());
		this.setManagerType(obj.getManagerType());
		this.setRiskGrade(obj.getRiskGrade());
	}

}
