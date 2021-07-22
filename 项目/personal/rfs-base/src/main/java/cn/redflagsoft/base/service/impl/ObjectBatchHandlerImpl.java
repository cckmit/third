/*
 * $Id: ObjectBatchHandlerImpl.java 5189 2011-12-12 04:52:47Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
 * ��������������
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
	//private boolean handleObjectByID = false;//��ѯ����ʱ�Ƿ��Ȳ�ѯ������ID���ϣ�Ȼ��ѭ����ѯÿ�������ٽ��д����ʺ�ʹ���˻���
	private boolean handlerChangeFinder = false;//����������Ƿ���ѯ���Ĳ�ѯ����

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
		//����������
		ResultFilter filter = ResultFilter.createPageableResultFilter(0, batchSize);
		if(StringUtils.isNotBlank(filterString)){
			filter.setCriterion(Restrictions.sql(filterString));
		}
		//���Խ�Handler�����������ѯ��
		if(handler instanceof CriterionProvider){
			Criterion criterion = ((CriterionProvider)handler).getCriterion();
			if(criterion != null){
				filter = ResultFilterUtils.append(filter, criterion);
			}
		}
		
		if(IS_DEBUG_ENABLED){
			StringBuffer msg = new StringBuffer("����Ĳ�ѯ����Ϊ��").append(filter.getCriterion());
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
		
		//����
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
		//	Assert.isInstanceOf(ObjectByIDFinder.class, finder, "finder������ObjectByIDFinder����");
		//}else{
		//	Assert.isInstanceOf(ObjectFinder.class, finder, "finder������ObjectFinder����");
		//}
	}
}
