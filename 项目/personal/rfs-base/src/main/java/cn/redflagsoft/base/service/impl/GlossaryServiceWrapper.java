/*
 * $Id: GlossaryServiceWrapper.java 6305 2013-08-20 07:48:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.Glossary;
import cn.redflagsoft.base.cache.GlossaryCache;
import cn.redflagsoft.base.service.GlossaryService;

/**
 * 带缓存的Glossary服务类。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class GlossaryServiceWrapper implements GlossaryService {

	private static final Log log = LogFactory.getLog(GlossaryServiceWrapper.class);
	//同一个线程只输出一次DEBUG信息
	private static final ThreadLocal<Boolean> debuged = new ThreadLocal<Boolean>();
	
	private final GlossaryService service;
	private final GlossaryCache cache;
	
	public GlossaryServiceWrapper(GlossaryService service, GlossaryCache cache) {
		super();
		this.service = service;
		this.cache = cache;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.GlossaryService#findByCategory(short)
	 */
	public Map<Integer, String> findByCategory(short category) {
		return service.findByCategory(category);
		
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.GlossaryService#findByCategory2(short)
	 */
	public List<Glossary> findByCategory2(short category) {
		return service.findByCategory2(category);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.GlossaryService#getByCategoryAndCode(short, short)
	 */
	public String getByCategoryAndCode(short category, int code) {
		if(cache == null){
			return service.getByCategoryAndCode(category, code);
		}else{
			String value = cache.getGlossaryTerm(category, code);
			if(value != null){
				return value;
			}else{
				//已经加载过，但是还是没有数据，可能的确没有数据，不必查询后台
				if(cache.isCategoryLoaded(category)){
					if(log.isDebugEnabled()){
						if(!Boolean.TRUE.equals(debuged.get())){
							debuged.set(Boolean.TRUE);
							
							String name = getCategoryName(category);
							if(name == null){
								name = "";
							}
							log.debug(String.format("该类型 '%s(%s)' 的 Glossary 已经加载到缓存中，但仍未查询出编码为 %s 的值，" +
								"可能因为：1.数据库中无相关记录；2.缓存数据比较旧，清空缓存试试。", name, category, code));
						}
					}
					return value;
				}else{
					//缓存，并返回值
					List<Glossary> list = findByCategory2(category);
					for (Glossary g : list) {
						cache.putGlossaryIntoCache(g);
					}
					cache.setCategoryLoaded(category);
					
					return cache.getGlossaryTerm(category, code);
				}
			}
		}
	}
	
	/**
	 * 获取Category对象的名称。
	 * Category的映射关系存储在Glossary的category为0的部分。
	 * 
	 * @param category 
	 * @return 名称
	 */
	public String getCategoryName(short category){
		short rootCategory = 0;
		if(!cache.isCategoryLoaded(rootCategory)){
			List<Glossary> list = findByCategory2(rootCategory);
			for (Glossary g : list) {
				cache.putGlossaryIntoCache(g);
			}
			cache.setCategoryLoaded(rootCategory);
		}
		
		return cache.getGlossaryTerm(rootCategory, category);
	}
}
