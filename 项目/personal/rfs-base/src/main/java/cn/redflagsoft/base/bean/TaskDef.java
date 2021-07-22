/*
 * $Id: TaskDef.java 6401 2014-05-08 07:19:11Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

import java.util.Set;

/**
 * Task����ӿڡ�
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface TaskDef extends BizSummaryTemplateProvider, BizDef{
	
	final static int DUTY_REF_IGNORE = 0;
	
	/**
	 * ��������Ϣ��Դ�ڶ��󡣲���ʹ�á��ο�  {@link BizDef#DUTYER_TYPE_FROM_OBJECT}
	 * @see BizDef#DUTYER_TYPE_FROM_OBJECT
	 */
	@Deprecated
	final static int DUTY_REF_FROM_OBJECT = 1;
	
	/**
	 * �����˱�����ڸ����ˡ�
	 */
	final static int DUTY_REF_CLERK_EQUALS_DUTYER = 11;
	
	
	int INVALID_TYPE = -1;
	
	int getTaskType();
	
	String getName();
	
	int getVisibility();
	
	Integer getParentTaskType();
	
	String getTypeAlias();
	
//	TaskDef getParent();
	
//	List<TaskDef> getSubTaskDefs();
	
	Set<Integer> getSubTaskTypes();
	
	Set<Integer> getWorkTypes();
	
	int getDutyRef();
	
	String getLifeStageFieldName();
	
	String getBizSummaryTemplate();      //    ҵ��״��ģ��

	/**
	 * �����ʶ������λ�洢��Ϣ��
	 * 
	 * <ol>���ҵ���
	 *  <li>��һλ ��ʾ����ʱ��ʱ�Ƿ������һ�졣 1������0��������
	 *  <li>�ڶ�λ ����������
	 * </ol>
	 * @return
	 * @see http://192.168.18.6/sf/sfmain/do/go/wiki1213
	 */
	int getCalculateFlag();
	
	int getHasGuide();
}
