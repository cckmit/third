/*
 * $Id: AceManager.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security;

import java.util.Set;

public interface AceManager {
	/**
	 * 
	 * @return
	 */
	Set<Ace> getAcl();
	
	/**
	 * 
	 * @param aceId
	 * @return
	 */
	Ace getAce(String aceId);
}
