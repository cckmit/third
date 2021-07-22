/*
 * $Id: NoSuchSchemeException.java 4658 2011-09-05 02:03:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.scheme;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class NoSuchSchemeException extends SchemeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2770860360495270011L;

	/**
	 * @param message
	 */
	public NoSuchSchemeException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NoSuchSchemeException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoSuchSchemeException(String message, Throwable cause) {
		super(message, cause);
	}

}
