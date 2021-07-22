/*
 * $Id: Codeable.java 5240 2011-12-21 09:27:55Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.codegenerator;

import cn.redflagsoft.base.bean.RFSEntityObject;

/**
 * 包含编号的实体的接口。
 * 
 * @author Alex Lin
 *
 */
public interface Codeable  extends RFSEntityObject{
	/**
	 * 获取编号。
	 * @return
	 */
	String getCode();
	/**
	 * 设置编号。
	 * @param code
	 */
	void setCode(String code);
	
	//Byte getCodeBizCategory();
	//Integer getCodeBizType();
}
