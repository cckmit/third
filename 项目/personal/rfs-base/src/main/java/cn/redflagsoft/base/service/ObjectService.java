/*
 * ObjectService.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service;

import java.math.BigDecimal;
import java.util.List;

import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.RiskMonitorable;
import cn.redflagsoft.base.vo.statistics.PlanMonitorResult;

/**
 * @author Alex Lin
 *
 */
public interface ObjectService<T extends RFSObject> extends RiskMonitorableService,ObjectFinder<T>/*, ObjectByIDFinder<T, Long>*/ {
	/**
	 * �½�һ�����󣬲������ɶ�����صķ��ռ����Ϣ��
	 * 
	 * @param object
	 * @return
	 */
	T saveObject(T object);
	
	/**
	 * ����һ����������
	 * @param object
	 * @return
	 */
	T updateObject(T object);
	
	/**
	 * ���»�������
	 * @param oldObject
	 * @param newObject
	 * @return
	 */
	T updateObject(T oldObject, T newObject);
	
	/**
	 * ɾ����������
	 * @param object
	 */
	void deleteObject(T object);
	
	/**
	 * ɾ������
	 * @param id
	 */
	void removeObject(Long id);
	
	/**
	 * ��ȡ����
	 * @param id
	 * @return
	 */
	T getObject(Long id);
	
	/**
	 * ��ѯ�����б�
	 * @param rf
	 * @return
	 */
	List<T> findObjects(ResultFilter rf);
	
	/**
	 * ԭʼ�Ĳ�ѯ�����б������κ�������
	 * @param rf
	 * @return
	 */
	List<T> findObjectsOrigin(ResultFilter rf);
	
	/**
	 * ��ѯ��ҳ�Ķ����б�
	 * 
	 * @param rf
	 * @return
	 */
	PageableList<T> findPageableObjects(ResultFilter rf);
	
	/**
	 * ��ȡ����������
	 * @param rf
	 * @return
	 */
	int getObjectCount(ResultFilter rf);
	//List<T> findObjectByGrade(Integer objectType, Short lifeStage, Byte grade, Byte managerType, Long manager);	
	

	@Deprecated
	PlanMonitorResult getObjectRiskSummaryByManager(Integer objectType, Integer riskType, Short objectStatus, Short year,int key,Long entityID);

	@Deprecated
	void updateObjectsDutyClerkIdAndNameByClerkID(List<Long> ids,Long clerkID,String clerkName);
	
	/**
	 * ��ѯID���ϡ�
	 * 
	 * @param rf
	 * @return
	 */
	List<Long> findObjectIds(ResultFilter rf);
	
	
	
	public static class IdFinder implements ObjectFinder<Long>{
		private ObjectService<?> objectService;
		
		/**
		 * @param objectService
		 */
		public IdFinder(ObjectService<?> objectService) {
			super();
			this.objectService = objectService;
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectCountQuery#getObjectCount(org.opoo.ndao.support.ResultFilter)
		 */
		public int getObjectCount(ResultFilter rf) {
			return objectService.getObjectCount(rf);
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectFinder#findObjects(org.opoo.ndao.support.ResultFilter)
		 */
		public List<Long> findObjects(ResultFilter rf) {
			return objectService.findObjectIds(rf);
		}
	}
	
	public static class InvalidObjectService<T extends RFSObject> implements ObjectService<T> {

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.RiskMonitorableService#updateRiskDutyer(cn.redflagsoft.base.bean.RiskMonitorable, java.lang.String, java.lang.Long)
		 */
		public void updateRiskDutyer(RiskMonitorable rm, String objectAttr,
				Long dutyerID) {
			throw new InvalidObjectServiceOperationException();
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.RiskMonitorableService#setRiskScale(cn.redflagsoft.base.bean.RiskMonitorable, java.lang.String, java.math.BigDecimal, byte)
		 */
		public void setRiskScale(RiskMonitorable rm, String objectAttr, BigDecimal scaleValue, byte scaleId) {
			throw new InvalidObjectServiceOperationException();
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.RiskMonitorableService#getRiskValueUnit(cn.redflagsoft.base.bean.RiskMonitorable, java.lang.String)
		 */
		public Byte getRiskValueUnit(RiskMonitorable rm, String objectAttr) {
			throw new InvalidObjectServiceOperationException();
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.RiskMonitorableService#getRiskScale(cn.redflagsoft.base.bean.RiskMonitorable, java.lang.String, byte)
		 */
		public BigDecimal getRiskScale(RiskMonitorable rm, String objectAttr,
				byte scaleId) {
			throw new InvalidObjectServiceOperationException();
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectService#saveObject(cn.redflagsoft.base.bean.RFSObject)
		 */
		public T saveObject(T object) {
			throw new InvalidObjectServiceOperationException();
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectService#updateObject(cn.redflagsoft.base.bean.RFSObject)
		 */
		public T updateObject(T object) {
			throw new InvalidObjectServiceOperationException();
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectService#updateObject(cn.redflagsoft.base.bean.RFSObject, cn.redflagsoft.base.bean.RFSObject)
		 */
		public T updateObject(T oldObject, T newObject) {
			throw new InvalidObjectServiceOperationException();
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectService#deleteObject(cn.redflagsoft.base.bean.RFSObject)
		 */
		public void deleteObject(T object) {
			throw new InvalidObjectServiceOperationException();
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectService#removeObject(java.lang.Long)
		 */
		public void removeObject(Long id) {
			throw new InvalidObjectServiceOperationException();
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectService#getObject(java.lang.Long)
		 */
		public T getObject(Long id) {
			throw new InvalidObjectServiceOperationException();
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectService#findObjects(org.opoo.ndao.support.ResultFilter)
		 */
		public List<T> findObjects(ResultFilter rf) {
			throw new InvalidObjectServiceOperationException();
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectService#findPageableObjects(org.opoo.ndao.support.ResultFilter)
		 */
		public PageableList<T> findPageableObjects(ResultFilter rf) {
			throw new InvalidObjectServiceOperationException();
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectService#getObjectCount(org.opoo.ndao.support.ResultFilter)
		 */
		public int getObjectCount(ResultFilter rf) {
			throw new InvalidObjectServiceOperationException();
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectService#getObjectRiskSummaryByManager(java.lang.Short, java.lang.Short, java.lang.Short, java.lang.Short, int, java.lang.Long)
		 */
		public PlanMonitorResult getObjectRiskSummaryByManager(Integer objectType,
				Integer riskType, Short objectStatus, Short year, int key,
				Long entityID) {
			throw new InvalidObjectServiceOperationException();
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectService#updateObjectsDutyClerkIdAndNameByClerkID(java.util.List, java.lang.Long, java.lang.String)
		 */
		public void updateObjectsDutyClerkIdAndNameByClerkID(List<Long> ids,
				Long clerkID, String clerkName) {
			throw new InvalidObjectServiceOperationException();
		}

		public List<Long> findObjectIds(ResultFilter rf) {
			throw new InvalidObjectServiceOperationException();
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectService#findObjectsOrigin(org.opoo.ndao.support.ResultFilter)
		 */
		public List<T> findObjectsOrigin(ResultFilter rf) {
			throw new InvalidObjectServiceOperationException();
		}
	}
	
	
	public static class InvalidObjectServiceOperationException extends
			UnsupportedOperationException {
		private static final long serialVersionUID = 1632345022062883814L;

		public InvalidObjectServiceOperationException() {
		}

		/**
		 * @param message
		 */
		public InvalidObjectServiceOperationException(String message) {
			super(message);
		}

		/**
		 * @param cause
		 */
		public InvalidObjectServiceOperationException(Throwable cause) {
			super(cause);
		}

		/**
		 * @param message
		 * @param cause
		 */
		public InvalidObjectServiceOperationException(String message,
				Throwable cause) {
			super(message, cause);
		}
	}
}
