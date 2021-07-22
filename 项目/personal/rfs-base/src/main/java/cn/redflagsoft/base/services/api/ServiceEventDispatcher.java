/*
 * $Id: ServiceEventDispatcher.java 6017 2012-09-18 10:09:16Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.services.api;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface ServiceEventDispatcher {
	
	void addListener(ServiceListener listener);
	
	void removeListemer(ServiceListener listener);
}
