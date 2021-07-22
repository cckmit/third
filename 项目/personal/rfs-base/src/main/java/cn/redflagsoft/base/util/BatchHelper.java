/*
 * $Id: BatchHelper.java 6174 2013-01-10 05:55:58Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.util.Assert;

import cn.redflagsoft.base.service.ObjectByIDFinder;
import cn.redflagsoft.base.service.ObjectFinder;
import cn.redflagsoft.base.service.ObjectHandler;

/**
 * 批处理工具。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public abstract class BatchHelper {
	private static final Log log = LogFactory.getLog(BatchHelper.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	/**
	 * 批次处理对象。
	 * 每批次都直接查询出对象集合，然后循环处理每个对象。
	 * 
	 * @param finder 查询器
	 * @param handler 处理器
	 * @param filter 查询条件
	 * @param handlerChangeFinder 结果处理器是否会影响查询器的查询集合
	 * @return 处理条数
	 */
	public static <T,R> int batchHandleObject(ObjectFinder<T> finder, ObjectHandler<T,R> handler, 
			ResultFilter filter, boolean handlerChangeFinder){
		Assert.notNull(filter, "必须指定查询条件");
		Assert.isTrue(filter.getMaxResults() > 0, "每批次处理数量必须大于0，在filter.maxResults中指定");

		int pageSize = filter.getMaxResults();
		int objectCount = finder.getObjectCount(filter);
		
		if(objectCount == 0){
			log.debug("待处理对象数为0，结束处理。");
			return 0;
		}
		
		if(IS_DEBUG_ENABLED){
			log.debug("共有待处理对象" + objectCount + "个，开始处理...");
		}
		
		int index = 0;
		int resultCount = 0;
		while(true){
			ResultFilter filter2 = ResultFilter.createResultFilter(filter);
			filter2.setFirstResult(handlerChangeFinder ? 0 : index);//如果操作结果影响了查询，则每次都从第0条开始查询
			List<T> objects = finder.findObjects(filter2);
			
			if(!objects.isEmpty()){
				if(IS_DEBUG_ENABLED){
					log.debug("开始处理第" + (index + 1) + '-' + (index + objects.size()) + "条...");
				}
				for(T object:objects){
					index++;
					
					if(IS_DEBUG_ENABLED){
						log.debug("handle object #" + index + ": " + object);
					}
					R r = handler.handle(object);
					if(r != null){
						resultCount++;
					}
				}
			}
			
			//如果没有查询到任何记录或者记录数小于分页，表示已经处理完所有数据
			if(objects.size() < pageSize){
				break;
			}
			//如果结果不影响查询，而且正好达到最大条数（例如分页大写10，总共100条，目前index为100）
			if(!handlerChangeFinder && index >= objectCount){
				break;
			}
		}
		
		/*
		for(int i = 0 ; i < objectCount ; i += filter.getMaxResults()){
			ResultFilter filter2 = ResultFilter.createResultFilter(filter);
			filter2.setFirstResult(i);
			List<T> objects = finder.findObjects(filter2);
			if(IS_DEBUG_ENABLED){
				log.debug("开始处理第" + (i + 1) + '-' + (i + objects.size()) + "条...");
			}
			for(T object:objects){
				if(IS_DEBUG_ENABLED){
					index++;
					log.debug("handle object #" + index + ": " + object);
				}
				R r = handler.handle(object);
				if(r != null){
					resultCount++;
				}
			}
		}*/
		
		if(IS_DEBUG_ENABLED){
			log.debug("共有待处理对象" + objectCount + "个，实际处理对象" + resultCount + "个，循环" + index + "次");
		}
		return resultCount;
	}
	
	/**
	 * 批次处理对象。
	 * 每批次都只查询出对象ID集合，然后循环查询每个对象，再进行处理，适合使用了缓存
	 * 的查询服务，例如RFSObject体系的ObjectService。
	 * 
	 * @param finder 查询器
	 * @param handler 处理器
	 * @param filter 查询条件
	 * @param handlerChangeFinder 结果处理器是否会影响查询器的查询集合
	 * @return 处理条数
	 * @deprecated
	 */
	public static <T,K,R> int batchHandleObjectByID(ObjectByIDFinder<T, K> finder, ObjectHandler<T,R> handler, 
			ResultFilter filter, boolean handlerChangeFinder){
		Assert.notNull(filter, "必须指定查询条件");
		Assert.isTrue(filter.getMaxResults() > 0, "每批次处理数量必须大于0，在filter.maxResults中指定");
		
		int pageSize = filter.getMaxResults();
		int objectCount = finder.getObjectCount(filter);
		
		if(objectCount == 0){
			log.debug("待处理对象数为0，结束处理。");
			return 0;
		}
		
		
		if(IS_DEBUG_ENABLED){
			log.debug("共有待处理对象" + objectCount + "个，开始处理...");
		}
		
		int index = 0;
		int resultCount = 0;
		while(true){
			ResultFilter filter2 = ResultFilter.createResultFilter(filter);
			filter2.setFirstResult(handlerChangeFinder ? 0 : index);
			List<K> ids = finder.findObjectIds(filter2);
			
			if(!ids.isEmpty()){
				if(IS_DEBUG_ENABLED){
					log.debug("开始处理第" + (index + 1) + '-' + (index + ids.size()) + "条...");
				}
				for(K id: ids){
					index++;

					if(IS_DEBUG_ENABLED){
						log.debug("handle object #" + index + ": " + id);
					}
					T object = finder.getObject(id);
					R r = handler.handle(object);
					if(r != null){
						resultCount++;
					}
				}
			}
			
			//如果没有查询到任何记录或者记录数小于分页，表示已经处理完所有数据
			if(ids.size() < pageSize){
				break;
			}
			//如果结果不影响查询，而且正好达到最大条数（例如分页大写10，总共100条，目前index为100）
			if(!handlerChangeFinder && index >= objectCount){
				break;
			}
		}
		
		
		/*
		for(int i = 0 ; i < objectCount ; i += filter.getMaxResults()){
			ResultFilter filter2 = ResultFilter.createResultFilter(filter);
			filter2.setFirstResult(i);
			List<K> ids = finder.findObjectIds(filter2);
			
			if(IS_DEBUG_ENABLED){
				log.debug("开始处理第" + (i + 1) + '-' + (i + ids.size()) + "条...");
			}
			
			for(K id: ids){
				if(IS_DEBUG_ENABLED){
					index++;
					log.debug("handle object #" + index + ": " + id);
				}
				T object = finder.getObject(id);
				R r = handler.handle(object);
				if(r != null){
					resultCount++;
				}
			}
		}
		*/
		if(IS_DEBUG_ENABLED){
			log.debug("共有待处理对象" + objectCount + "个，实际处理对象" + resultCount + "个，循环" + index + "次");
		}
		return resultCount;
	}
}
