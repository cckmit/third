/*
 * $Id: GlossaryCache.java 6305 2013-08-20 07:48:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.cache;

import cn.redflagsoft.base.bean.Glossary;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface GlossaryCache {

	String getGlossaryTerm(short category, int code);
	
	void putGlossaryIntoCache(Glossary g);
	
	boolean isCategoryLoaded(short category);
	
	void setCategoryLoaded(short category);
	
	void clear();
	
	void clear(short category);
}
