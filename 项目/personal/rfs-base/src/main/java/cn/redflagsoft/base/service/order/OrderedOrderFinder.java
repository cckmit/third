/*
 * $Id: OrderedOrderFinder.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.order;

import org.springframework.core.Ordered;

import cn.redflagsoft.base.service.OrderFinder;

/**
 * 实现了Ordered接口的对象的排序号查找类。
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
