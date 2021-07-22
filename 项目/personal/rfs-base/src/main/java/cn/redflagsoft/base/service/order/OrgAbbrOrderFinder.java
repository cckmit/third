/*
 * $Id: OrgAbbrOrderFinder.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.order;

import cn.redflagsoft.base.bean.Org;

/**
 * ���ݵ�λ�ļ��������ָ����λ������š�
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OrgAbbrOrderFinder extends AbstractOrgOrderFinder<String> {

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrderFinder#getOrder(java.lang.Object)
	 */
	public int getOrder(String t) {
		if(t == null){
			return 0;
		}
		for (Org org : getCachedOrgList()) {
			if(org.getAbbr() != null && org.getAbbr().equals(t)){
				return org.getOrder();
			}
		}
		return 0;
	}

}
