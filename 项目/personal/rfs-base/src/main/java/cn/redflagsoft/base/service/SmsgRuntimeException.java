/*
 * $Id: SmsgRuntimeException.java 5277 2011-12-26 09:07:11Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service;

/**
 * ��Ϣ��ص�����ʱ�쳣��
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class SmsgRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5866713126203736460L;

	/**
	 * 
	 */
	public SmsgRuntimeException() {
	}

	/**
	 * @param message
	 */
	public SmsgRuntimeException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SmsgRuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SmsgRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
