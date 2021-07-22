/*
 * $Id: LabelDataBean.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;

import org.opoo.apps.Labelable;
import org.springframework.security.vote.LabeledData;

/**
 * 用来表示向前台传递下拉框等所需数据。
 * 可以生成此结构的实体类，需要实现这个接口。
 * 
 * 
 * @author Alex
 *
 */
public interface LabelDataBean extends Serializable, LabeledData, Labelable {
	/**
	 * 标签。
	 * 
	 * @return String
	 */
	String getLabel();
	/**
	 * 数据。
	 * @return Serializable
	 */
	Serializable getData();
}
