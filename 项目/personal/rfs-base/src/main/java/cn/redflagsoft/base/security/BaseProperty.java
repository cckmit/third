/*
 * $Id: BaseProperty.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security;


/**
 * 
 * �������ԣ�ϵͳ�ڲ�ʹ�õġ�
 * @author Alex Lin
 *
 */
public enum BaseProperty {
	pageSize;
	
	
//	private static final StringManager strings = StringManager.getManager(BaseProperty.class);
//
//	public String getLabel() {
//		String label = strings.getString(name());
//		if(label == null){
//			label = name();
//		}
//		return label;
//	}
	
	public String getName(){
		return name();
	}
}
