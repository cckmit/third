/*
 * $Id: WorkDefinitionService.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.WorkDefinition;

/**
 * Work�������ӿڡ�
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface WorkDefinitionService extends WorkDefProvider {
	/**
	 * 
	 * @param workType
	 * @return
	 */
	WorkDefinition getWorkDefinition(int workType);

	WorkDefinition updateWorkDefinition(WorkDefinition workDefinition);

	void remove(int workType);
}
