/*
 * $Id: LogoutService.java 6054 2012-10-09 06:45:08Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security;

import org.springframework.security.ui.logout.LogoutHandler;



/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface LogoutService extends LogoutHandler{
	void logout();
}
