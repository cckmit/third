/*
 * $Id: BizDef.java 6414 2014-07-08 03:52:05Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

import cn.redflagsoft.base.ObjectTypes;

/**
 * ҵ���塣
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface BizDef {
	//public static final short GLOSSARY_CATEGORY_BIZ_DEF_DUTYER_TYPE = Glossary.CATEGORY_BIZ_DEF_DUTYER_TYPE;

	/**
	 * �ޡ�
	 */
	public static final int DUTYER_TYPE_UNKNOWN = 0;
	/**
	 * 1 ��ҵ�����������˶�����л�ȡ���Ե�ǰ�û�Ϊ�����ˣ���ҵ�����������˶������ҿ������ܺͷֹ��쵼
	 */
	public static final int DUTYER_TYPE_CONFIG_CURRENT_USER = 1;
	/**
	 * 2 ��ҵ�����������˶�����л�ȡ������������ȡ��һ������
	 */
	public static final int DUTYER_TYPE_CONFIG_FIRST_MATCH = 2;
	/**
	 * 3 �ӵ�ǰҵ�������漰�Ķ�����ȡ3��������
	 */
	public static final int DUTYER_TYPE_FROM_OBJECT = 3;
	
	/**
	 * 4 ��ҵ�����������˶�����л�ȡ�����ݶ�������ε�λ���в�ѯ�����ص�һ����������
	 * �ļ�¼��
	 */
	public static final int DUTYER_TYPE_CONFIG_BY_OBJECT_ORG_ID = 4;
	
	/**
	 * ��ȡ��ǰҵ�����е����������͡�
	 * @return
	 */
	int getDutyerType();
	
	/**
	 * ҵ�����ID������taskType��WorkType
	 * @return
	 */
	Integer getId();
	
	/**
	 * ҵ�����ͣ���־��ҵ��ʱtask��work����job
	 * @see ObjectTypes
	 * @return
	 */
	int getObjectType();
}
