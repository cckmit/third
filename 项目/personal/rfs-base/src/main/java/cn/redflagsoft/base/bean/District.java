/*
 * $Id: District.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;
import java.util.List;

import org.springframework.core.Ordered;

/**
 * ��������ҵ�����
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface District extends Ordered, Serializable{
	
	String getCode();
	
	String getName();
	
	String getRemark();
	
	int getDisplayOrder();
	
	District getParent();
	
	byte getStatus();
	
	int getType();
	
	List<District> getSubdistricts();
}
