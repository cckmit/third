/*
 * $Id: NonUniqueResultException.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class NonUniqueResultException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8579072315297035188L;

	/**
	 * 
	 */
	public NonUniqueResultException() {
	}

	/**
	 * @param message
	 */
	public NonUniqueResultException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NonUniqueResultException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NonUniqueResultException(String message, Throwable cause) {
		super(message, cause);
	}
}
