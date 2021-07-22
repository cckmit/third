/*
 * $Id: BusinessExceptionManager.java 6299 2013-08-13 01:56:24Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

import java.text.MessageFormat;
import java.util.Map;

import cn.redflagsoft.base.BusinessException;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface BusinessExceptionManager {

	/**
	 * �׳�ҵ���쳣��
	 * ���� msgId �� EventMsg �в�ѯ�쳣��Ϣ������鲻������ȡ defaultMessage��
	 * @param msgId
	 * @param defaultMessage
	 * @throws BusinessException
	 */
	void throwBusinessException(long msgId, String defaultMessage) throws BusinessException;

	/**
	 * �׳�ҵ���쳣��
	 * ���� msgId �� EventMsg �в�ѯ�쳣��Ϣ������鲻������ȡ defaultMessage��
	 * @param msgId
	 * @param defaultMessage
	 * @param e
	 * @throws BusinessException
	 */
	void throwBusinessException(long msgId, String defaultMessage, Throwable e) throws BusinessException;
	
	/**
	 * �׳�ҵ���쳣��
	 * ���� msgId �� EventMsg �в�ѯ�쳣��Ϣ������鲻������ȡ e.message��
	 * @param msgId
	 * @param e
	 * @throws BusinessException
	 */
	void throwBusinessException(long msgId, Throwable e) throws BusinessException;
	
	/**
	 * �׳�ҵ���쳣��
	 * ���� msgId �� EventMsg �в�ѯ�쳣��Ϣ��ģ�壬����鲻������ȡ defaultMessage��
	 * ��Ϣģ��� args ���� MessageFormat.format() �������� ��Ϣ���ݡ�
	 * @param msgId
	 * @param defaultMessage
	 * @param e
	 * @param args
	 * @throws BusinessException
	 * @see {@link MessageFormat#format(String, Object...)}
	 */
	void throwBusinessException(long msgId, String defaultMessage, Throwable e, Object... args) throws BusinessException;
	
	/**
	 * �׳�ҵ���쳣��
	 * ���� msgId �� EventMsg �в�ѯ�쳣��Ϣ��ģ�壬����鲻������ȡ defaultMessage��
	 * ��Ϣģ��� context ���� FreeMarker �������� ��Ϣ���ݡ�
	 * @param msgId
	 * @param defaultMessage
	 * @param e
	 * @param context
	 * @throws BusinessException
	 */
	void throwBusinessException(long msgId, String defaultMessage, Throwable e, Map<String,Object> context) throws BusinessException;
}
