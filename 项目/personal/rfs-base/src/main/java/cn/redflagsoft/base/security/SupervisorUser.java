/*
 * $Id: SupervisorUser.java 6335 2013-12-11 06:30:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security;


/**
 * ���Ӽ�졣
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class SupervisorUser extends InternalUser {
	private static final long serialVersionUID = 1504477165271308278L;
	private static final SupervisorUser instance = new SupervisorUser();
	
	public static final String USERNAME = "$supervisor";
	public static final long USER_ID = 12L;
	

	/**
	 * 
	 */
	public SupervisorUser() {
		super(USERNAME, USER_ID, "���Ӽ��");
	}

	public static SupervisorUser getInstance(){
		return instance;
	}

	public static void main(String[] args){
		System.out.println(new SupervisorUser());
	}
}
