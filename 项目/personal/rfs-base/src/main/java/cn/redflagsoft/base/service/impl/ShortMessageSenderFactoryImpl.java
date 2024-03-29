/*
 * $Id: ShortMessageSenderFactoryImpl.java 6195 2013-04-02 10:04:31Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.redflagsoft.base.bean.OrderedComparator;
import cn.redflagsoft.base.service.ShortMessageSender;
import cn.redflagsoft.base.service.ShortMessageSenderFactory;

import com.google.common.collect.Lists;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ShortMessageSenderFactoryImpl implements ShortMessageSenderFactory, ApplicationContextAware {
	
	private List<ShortMessageSender> senders;

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ShortMessageSenderFactory#getAvailableSender()
	 */
	public ShortMessageSender getAvailableSender() {
		if(senders == null || senders.isEmpty()){
			return null;
		}
		
		for(ShortMessageSender sender: senders){
			if(sender.isRunning()){
				return sender;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		@SuppressWarnings("unchecked")
		Map<String, ShortMessageSender> map = applicationContext.getBeansOfType(ShortMessageSender.class);
		Collection<ShortMessageSender> values = map.values();
		ArrayList<ShortMessageSender> list = Lists.newArrayList(values);
		Collections.sort(list, OrderedComparator.getInstance());
		senders = list;
	}
}
