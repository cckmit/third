/*
 * $Id: LifeStage.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.vo.statistics;

import java.io.Serializable;

/**
 * @author Alex Lin
 *
 */
public class LifeStage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7014523055285244759L;
	public LifeStage(int stage, String name) {
		super();
		this.stage = stage;
		this.name = name;
	}
	public LifeStage(){
		
	}
	
	private int stage;
	private String name;
	/**
	 * @return the stage
	 */
	public int getStage() {
		return stage;
	}
	/**
	 * @param stage the stage to set
	 */
	public void setStage(int stage) {
		this.stage = stage;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String toString(){
		return super.toString() + "(name=" + name + ", stage=" + stage + ")";
	}
}
