/*
 * $Id: SupervisionUser.java 6335 2013-12-11 06:30:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security;

/**
 * ����ϵͳ
 */
public class SupervisionUser extends InternalUser {
	private static final long serialVersionUID = 137271234891038340L;
	private static final SupervisionUser instance = new SupervisionUser();
	
	public static final String USERNAME = "$supervision";
	public static final long USER_ID = 14L;
	
	public SupervisionUser() {
		super(USERNAME, USER_ID, "���Ӷ���");
	}

	public static SupervisionUser getInstance(){
		return instance;
	}
}
