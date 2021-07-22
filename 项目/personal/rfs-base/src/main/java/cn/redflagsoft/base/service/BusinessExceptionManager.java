/*
 * $Id: BusinessExceptionManager.java 6299 2013-08-13 01:56:24Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
	 * 抛出业务异常。
	 * 根据 msgId 在 EventMsg 中查询异常消息，如果查不到，则取 defaultMessage。
	 * @param msgId
	 * @param defaultMessage
	 * @throws BusinessException
	 */
	void throwBusinessException(long msgId, String defaultMessage) throws BusinessException;

	/**
	 * 抛出业务异常。
	 * 根据 msgId 在 EventMsg 中查询异常消息，如果查不到，则取 defaultMessage。
	 * @param msgId
	 * @param defaultMessage
	 * @param e
	 * @throws BusinessException
	 */
	void throwBusinessException(long msgId, String defaultMessage, Throwable e) throws BusinessException;
	
	/**
	 * 抛出业务异常。
	 * 根据 msgId 在 EventMsg 中查询异常消息，如果查不到，则取 e.message。
	 * @param msgId
	 * @param e
	 * @throws BusinessException
	 */
	void throwBusinessException(long msgId, Throwable e) throws BusinessException;
	
	/**
	 * 抛出业务异常。
	 * 根据 msgId 在 EventMsg 中查询异常消息的模板，如果查不到，则取 defaultMessage。
	 * 消息模板和 args 再由 MessageFormat.format() 解析生成 消息内容。
	 * @param msgId
	 * @param defaultMessage
	 * @param e
	 * @param args
	 * @throws BusinessException
	 * @see {@link MessageFormat#format(String, Object...)}
	 */
	void throwBusinessException(long msgId, String defaultMessage, Throwable e, Object... args) throws BusinessException;
	
	/**
	 * 抛出业务异常。
	 * 根据 msgId 在 EventMsg 中查询异常消息的模板，如果查不到，则取 defaultMessage。
	 * 消息模板和 context 再由 FreeMarker 解析生成 消息内容。
	 * @param msgId
	 * @param defaultMessage
	 * @param e
	 * @param context
	 * @throws BusinessException
	 */
	void throwBusinessException(long msgId, String defaultMessage, Throwable e, Map<String,Object> context) throws BusinessException;
}
