/*
 * $Id: CaptchaInvalidException.java 4983 2011-10-28 04:14:15Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security.webapp;

import org.springframework.security.AuthenticationException;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class CaptchaInvalidException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6392491652916886934L;

	/**
	 * @param msg
	 */
	public CaptchaInvalidException(String msg) {
		super(msg);
	}

	/**
	 * @param msg
	 * @param t
	 */
	public CaptchaInvalidException(String msg, Throwable t) {
		super(msg, t);
	}

	/**
	 * @param msg
	 * @param extraInformation
	 */
	public CaptchaInvalidException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}

}
