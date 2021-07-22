/*
 * $Id: SchemeException.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.scheme;

/**
 * WorkScheme�쳣��
 * 
 * @author Alex Lin
 *
 */
public class SchemeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5140170644156323172L;

	/**
	 * 
	 */
	public SchemeException() {
	}

	/**
	 * @param message
	 */
	public SchemeException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public SchemeException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SchemeException(String message, Throwable cause) {
		super(message, cause);
	}

}
