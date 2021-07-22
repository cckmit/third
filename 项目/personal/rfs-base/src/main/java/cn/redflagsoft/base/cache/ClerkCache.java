/*
 * $Id: ClerkCache.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.cache;

import cn.redflagsoft.base.bean.Clerk;

/**
 * @author Alex Lin
 *
 */
public interface ClerkCache {
	
	/**
	 * 从缓存中获取clerk对象。
	 * @param id
	 * @return
	 */
	Clerk getClerkFromCache(Long id);
	
	/**
	 * 将clerk对象存进缓存中。
	 * @param clerk
	 */
	void putClerkIntoCache(Clerk clerk);
	
	/**
	 * 从缓存中移除指定的clerk。
	 * 
	 * @param id
	 */
	void removeFromCache(Long id);
	
	/**
	 * 
	 * @param clerk
	 */
	void removeFromCache(Clerk clerk);
	
	/**
	 * 清空缓存。
	 */
	void clear();
}
