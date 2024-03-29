/*
 * $Id: GlossaryCacheImpl.java 6305 2013-08-20 07:48:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.cache.impl;

import org.opoo.cache.Cache;

import cn.redflagsoft.base.bean.Glossary;
import cn.redflagsoft.base.cache.GlossaryCache;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class GlossaryCacheImpl implements GlossaryCache {
	private Cache<String,String> cache;

	/**
	 * @param cache
	 */
	public GlossaryCacheImpl(Cache<String, String> cache) {
		super();
		this.cache = cache;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.cache.GlossaryCache#getGlossaryTerm(short, int)
	 */
	public String getGlossaryTerm(short category, int code) {
		String key = buildResultCacheKey(category, code);
		String value = cache.get(key);
		return value;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.cache.GlossaryCache#putGlossaryIntoCache(cn.redflagsoft.base.bean.Glossary)
	 */
	public void putGlossaryIntoCache(Glossary g) {
		cache.put(buildResultCacheKey(g.getCategory(), g.getCode()), g.getTerm());
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.cache.GlossaryCache#isCategoryLoaded(short)
	 */
	public boolean isCategoryLoaded(short category) {
		return cache != null && cache.get(buildCategoryLoadCacheKey(category)) != null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.cache.GlossaryCache#setCategoryLoaded(short)
	 */
	public void setCategoryLoaded(short category) {
		if(cache != null){
			cache.put(buildCategoryLoadCacheKey(category), "1");
		}
	}
	
	private String buildResultCacheKey(short category, int code){
		return category + "|" + code;
	}
	
	private String buildCategoryLoadCacheKey(short category){
		return category + ".loaded";
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.cache.GlossaryCache#clear()
	 */
	public void clear() {
		if(cache != null){
			cache.clear();
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.cache.GlossaryCache#clear(short)
	 */
	public void clear(short category) {
		/*
		cache.remove(buildCategoryLoadCacheKey(category));
		List<String> keys = new ArrayList<String>();
		String prefix = category + "|";
		//cache.keySet maybe not implements
		Set<String> keySet = cache.keySet();
		for(String key: keySet){
			if(key.startsWith(prefix)){
				keys.add(key);
			}
		}
		
		for(String key: keys){
			cache.remove(key);
		}*/
		
		//FIXME
		//cache 的 keySet 方法有可能是没有实现的，所以采取以下这种效率不太好
		//但是比较安全的做法
		clear();
	}
	
	public Cache<String,String> getWrappedCache(){
		return cache;
	}
}
