/*
 * $Id: OrgDeletedListener.java 6299 2013-08-13 01:56:24Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.event2;

import cn.redflagsoft.base.bean.Org;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface OrgDeletedListener {
	void orgDeleted(Org org);
}
