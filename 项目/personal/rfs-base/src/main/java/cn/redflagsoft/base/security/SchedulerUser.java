/*
 * $Id: SchedulerUser.java 6335 2013-12-11 06:30:56Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security;


/**
 * ��������
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class SchedulerUser extends InternalUser{
	private static final long serialVersionUID = 2977400421947701154L;
	private static final SchedulerUser instance = new SchedulerUser();
	
	public static final String USERNAME = "$scheduler";
	public static final long USER_ID = 13L;
	/**
	 * @param username
	 * @param userId
	 */
	public SchedulerUser() {
		super(USERNAME, USER_ID, "��ʱ����");
	}
	
	public static SchedulerUser getInstance(){
		return instance;
	}
}
