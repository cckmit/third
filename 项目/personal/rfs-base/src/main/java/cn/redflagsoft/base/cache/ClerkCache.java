/*
 * $Id: ClerkCache.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.cache;

import cn.redflagsoft.base.bean.Clerk;

/**
 * @author Alex Lin
 *
 */
public interface ClerkCache {
	
	/**
	 * �ӻ����л�ȡclerk����
	 * @param id
	 * @return
	 */
	Clerk getClerkFromCache(Long id);
	
	/**
	 * ��clerk�����������С�
	 * @param clerk
	 */
	void putClerkIntoCache(Clerk clerk);
	
	/**
	 * �ӻ������Ƴ�ָ����clerk��
	 * 
	 * @param id
	 */
	void removeFromCache(Long id);
	
	/**
	 * 
	 * @param clerk
	 */
	void removeFromCache(Clerk clerk);
	
	/**
	 * ��ջ��档
	 */
	void clear();
}
