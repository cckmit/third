/*
 * $Id: ServicesManager.java 6015 2012-09-18 09:59:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.services;

import java.util.List;

import cn.redflagsoft.base.NotFoundException;
import cn.redflagsoft.base.services.api.Service;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface ServicesManager {
	
	List<Service> getServices();
	
	Service getService(String name) throws NotFoundException;
}
