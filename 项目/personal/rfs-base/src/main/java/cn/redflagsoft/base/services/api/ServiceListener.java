/*
 * $Id: ServiceListener.java 6027 2012-09-20 07:36:57Z lcj $
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
public interface ServiceListener {

	/**
	 * Invoked when a service is starting.
	 * @param event
	 */
	void serviceStarting(ServiceEvent event);
	
	/**
	 * Invoked when a service has started.
	 * @param event
	 */
	void serviceStarted(ServiceEvent event);
	
	/**
	 * Invoked when a service is stopping.
	 * @param event
	 */
	void serviceStopping(ServiceEvent event);
	
	/**
	 * Invoked when a service has stopped.
	 * @param event
	 */
	void serviceStopped(ServiceEvent event);
}
