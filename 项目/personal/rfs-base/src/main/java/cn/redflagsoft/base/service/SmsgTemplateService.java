/*
 * $Id: SmsgTemplateService.java 6086 2012-10-30 01:11:41Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.util.Map;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface SmsgTemplateService {

	/**
	 * @param title
	 * @param params
	 * @return
	 */
	String processTemplate(String template, Map<String, Object> params);

	/**
	 * 
	 * @param taskSN
	 * @return
	 */
	Map<String, Object> getTaskContext(Long taskSN);
}
