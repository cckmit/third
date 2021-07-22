/*
 * $Id: NullClerkCache.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.cache.impl;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.cache.ClerkCache;

/**
 * @author Alex Lin
 *
 */
public class NullClerkCache implements ClerkCache {

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.cache.ClerkCache#clear()
	 */
	public void clear() {
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.cache.ClerkCache#getClerkFromCache(java.lang.Long)
	 */
	public Clerk getClerkFromCache(Long id) {
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.cache.ClerkCache#putClerkIntoCache(cn.redflagsoft.base.bean.Clerk)
	 */
	public void putClerkIntoCache(Clerk clerk) {
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.cache.ClerkCache#removeFromCache(java.lang.Long)
	 */
	public void removeFromCache(Long id) {
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.cache.ClerkCache#removeFromCache(cn.redflagsoft.base.bean.Clerk)
	 */
	public void removeFromCache(Clerk clerk) {
		
	}

}
