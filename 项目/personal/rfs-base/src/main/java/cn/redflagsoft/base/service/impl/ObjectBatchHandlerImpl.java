/*
 * $Id: ObjectBatchHandlerImpl.java 5189 2011-12-12 04:52:47Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.ndao.support.ResultFilterUtils;
import org.springframework.beans.factory.InitializingBean;

import cn.redflagsoft.base.service.ObjectBatchHandler;
import cn.redflagsoft.base.service.ObjectFinder;
import cn.redflagsoft.base.service.ObjectHandler;
import cn.redflagsoft.base.support.CriterionProvider;
import cn.redflagsoft.base.util.BatchHelper;

/**
 * 对象批处理器。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ObjectBatchHandlerImpl<T,R> implements ObjectBatchHandler, InitializingBean {
	private static final Log log = LogFactory.getLog(ObjectBatchHandlerImpl.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	private ObjectFinder<T> finder;
	private ObjectHandler<T,R> handler;
	private int batchSize = 10;
	private String filterString;
	//private boolean handleObjectByID = false;//查询对象时是否先查询出对象ID集合，然后循环查询每个对象，再进行处理，适合使用了缓存
	private boolean handlerChangeFinder = false;//结果处理器是否会查询器的查询集合

	/**
	 * @return the finder
	 */
	public ObjectFinder<T> getFinder() {
		return finder;
	}

	/**
	 * @param finder the finder to set
	 */
	public void setFinder(ObjectFinder<T> finder) {
		this.finder = finder;
	}

	/**
	 * @return the handler
	 */
	public ObjectHandler<T, R> getHandler() {
		return handler;
	}

	/**
	 * @param handler the handler to set
	 */
	public void setHandler(ObjectHandler<T, R> handler) {
		this.handler = handler;
	}

	/**
	 * @return the batchSize
	 */
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * @param batchSize the batchSize to set
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/**
	 * @return the filterString
	 */
	public String getFilterString() {
		return filterString;
	}

	/**
	 * @param filterString the filterString to set
	 */
	public void setFilterString(String filterString) {
		this.filterString = filterString;
	}

//
//	/**
//	 * @return the handleObjectByID
//	 */
//	public boolean isHandleObjectByID() {
//		return handleObjectByID;
//	}
//
//	/**
//	 * @param handleObjectByID the handleObjectByID to set
//	 */
//	public void setHandleObjectByID(boolean handleObjectByID) {
//		this.handleObjectByID = handleObjectByID;
//	}
	
	/**
	 * @return the handlerChangeFinder
	 */
	public boolean isHandlerChangeFinder() {
		return handlerChangeFinder;
	}

	/**
	 * @param handlerChangeFinder the handlerChangeFinder to set
	 */
	public void setHandlerChangeFinder(boolean handlerChangeFinder) {
		this.handlerChangeFinder = handlerChangeFinder;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectBatchHandler#handleObjects()
	 */
	@SuppressWarnings("unchecked")
	public int handleObjects() {
		//创建过滤器
		ResultFilter filter = ResultFilter.createPageableResultFilter(0, batchSize);
		if(StringUtils.isNotBlank(filterString)){
			filter.setCriterion(Restrictions.sql(filterString));
		}
		//可以将Handler的条件纳入查询。
		if(handler instanceof CriterionProvider){
			Criterion criterion = ((CriterionProvider)handler).getCriterion();
			if(criterion != null){
				filter = ResultFilterUtils.append(filter, criterion);
			}
		}
		
		if(IS_DEBUG_ENABLED){
			StringBuffer msg = new StringBuffer("对象的查询条件为：").append(filter.getCriterion());
			if(filter.getCriterion() != null){
				Object[] values = filter.getCriterion().getValues();
				if(values != null && values.length > 0){
					msg.append("(");
					for(int i = 0 ; i < values.length ; i++){
						if(i > 0){
							msg.append(", ");
						}
						msg.append(values[i]);
					}
					msg.append(")");
				}
			}
			log.debug(msg.toString());
		}
		
		//处理
		//if(handleObjectByID){
		//	return BatchHelper.batchHandleObjectByID((ObjectByIDFinder<T,K>)finder, handler, filter, handlerChangeFinder);
		//}else{
			return BatchHelper.batchHandleObject(finder, handler, filter, handlerChangeFinder);
		//}
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		//if(handleObjectByID){
		//	Assert.isInstanceOf(ObjectByIDFinder.class, finder, "finder必须是ObjectByIDFinder类型");
		//}else{
		//	Assert.isInstanceOf(ObjectFinder.class, finder, "finder必须是ObjectFinder类型");
		//}
	}
}
