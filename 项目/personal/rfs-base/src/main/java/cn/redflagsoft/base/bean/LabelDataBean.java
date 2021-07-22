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
 * ������ʾ��ǰ̨������������������ݡ�
 * �������ɴ˽ṹ��ʵ���࣬��Ҫʵ������ӿڡ�
 * 
 * 
 * @author Alex
 *
 */
public interface LabelDataBean extends Serializable, LabeledData, Labelable {
	/**
	 * ��ǩ��
	 * 
	 * @return String
	 */
	String getLabel();
	/**
	 * ���ݡ�
	 * @return Serializable
	 */
	Serializable getData();
}
