/*
 * $Id: Service.java 6017 2012-09-18 10:09:16Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.services.api;

/**
 *	����
 */
public interface Service {

//	void addListener(ServiceListener listener);
//	
//	void removeListemer(ServiceListener listener);

//	List<ServiceOperation> getCustomOperations();
	
	ServiceInfo getInfo();
	
	void start() throws ServiceException;
	
	void stop() throws ServiceException;
}
