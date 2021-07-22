/*
 * $Id: ObjectEntityService.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.vo.BatchUpdateResult;

/**
 * ҵ������뵥λ֮��Ĺ�ϵ��
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface ObjectEntityService {
	
	/**
	 * Ϊָ��ID�ĵ�λ�������ö���
	 * �������ID����Ϊ�գ������ָ����λָ�����͵Ĺ�����ϵ��
	 * 
	 * @param entityId ��λID
	 * @param objectIds ����ID����
	 * @param type ��ϵ����
	 * @param objectType ��������
	 * @return  �������ý��
	 */
	BatchUpdateResult setObjectsToEntity(Long entityId, List<Long> objectIds, int type, int objectType);
	
	/**
	 * ���ݵ�λID���Ҷ���ID���ϡ�
	 * 
	 * @param entityId ��λID
	 * @param type ��ϵ����
	 * @param objectType ��������
	 * @return ����ID����
	 */
	List<Long> findObjectIdsByEntityId(Long entityId, int type, int objectType);
}
