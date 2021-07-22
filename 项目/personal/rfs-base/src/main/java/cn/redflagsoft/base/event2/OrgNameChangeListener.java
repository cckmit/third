/*
 * $Id: OrgNameChangeListener.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.event2;

import cn.redflagsoft.base.bean.Org;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface OrgNameChangeListener {
	
	void orgNameChange(Org org, String oldName, String newName);
}
