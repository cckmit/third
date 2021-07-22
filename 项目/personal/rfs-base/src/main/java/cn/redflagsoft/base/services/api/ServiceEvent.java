/*
 * $Id: ServiceEvent.java 6027 2012-09-20 07:36:57Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.services.api;

import java.util.EventObject;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ServiceEvent extends EventObject {

	private static final long serialVersionUID = -6065562503268029734L;
	
	public static int SERVICE_STARTED = 2; 
	public static int SERVICE_STARTING = 1; 
	public static int SERVICE_STOPPED = 4;
	public static int SERVICE_STOPPING = 3; 

	
	private final int eventType;
	/**
	 * @param source
	 */
	public ServiceEvent(Service source, int type) {
		super(source);
		this.eventType = type;
	}

	public Service getService(){
		return (Service) getSource();
	}

	/**
	 * @return the eventType
	 */
	public int getEventType() {
		return eventType;
	}
}
