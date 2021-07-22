/*
 * DefaultLabelDataBean.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;

import org.opoo.apps.LabeledBean;

/**
 * @author Alex Lin
 *
 */
public class DefaultLabelDataBean extends LabeledBean implements LabelDataBean, Serializable {

	private static final long serialVersionUID = -4179059880656479122L;

	public DefaultLabelDataBean(String label, Serializable data){
		super(label, data);
	}
}
