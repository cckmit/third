/*
 * $Id: MapBean.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * �� Map ��װ�����ݽṹ��
 * �����ݽṹ��¼������ѯ�����
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class MapBean extends LinkedHashMap<String,Object> implements Serializable{
	private static final long serialVersionUID = 2190970985166203521L;
	public MapBean(Map<? extends String, ? extends Object> m) {
		super(m);
	}
}
