/*
 * $Id: OrderedOrderFinder.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.order;

import org.springframework.core.Ordered;

import cn.redflagsoft.base.service.OrderFinder;

/**
 * ʵ����Ordered�ӿڵĶ��������Ų����ࡣ
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OrderedOrderFinder implements OrderFinder<Ordered> {
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.OrderFinder#getOrder(java.lang.Object)
	 */
	public int getOrder(Ordered t) {
		return t.getOrder();
	}
}
