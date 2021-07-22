/*
 * $Id: SchemeInfo.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
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
public class SchemeInfo {
	private String name;
	private String method;

	public SchemeInfo(String name, String method) {
		this.name = name;
		this.method = method;
	}

	public String getName() {
		return name;
	}

	public String getMethod() {
		return method;
	}

	public String toString() {
		if(method != null){
			return name + "!" + method;
		}
		return name;
	}
}
