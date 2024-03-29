/*
 * $Id: DatumCategoryServiceImpl.java 5132 2011-11-25 07:39:17Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.service.AttachmentManager;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Datum;
import cn.redflagsoft.base.bean.DatumAttachment;
import cn.redflagsoft.base.bean.DatumCategory;
import cn.redflagsoft.base.bean.DatumCategoryWithMatterDatum;
import cn.redflagsoft.base.bean.MatterDatum;
import cn.redflagsoft.base.bean.RFSEntityDescriptor;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.RFSObjectDescriptor;
import cn.redflagsoft.base.dao.DatumCategoryDao;
import cn.redflagsoft.base.dao.MatterDatumDao;
import cn.redflagsoft.base.service.DatumCategoryService;
import cn.redflagsoft.base.service.DatumService;
import cn.redflagsoft.base.vo.DatumCategoryVO;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class DatumCategoryServiceImpl implements DatumCategoryService {
	private static final Log log = LogFactory.getLog(DatumCategoryServiceImpl.class);
	private DatumCategoryDao datumCategoryDao;
	private MatterDatumDao matterDatumDao;
//	private DatumDao datumDao;
	private DatumService datumService;
	private AttachmentManager attachmentManager;
	
//	public void setDatumDao(DatumDao datumDao) {
//		this.datumDao = datumDao;
//	}

	public void setDatumCategoryDao(DatumCategoryDao datumCategoryDao) {
		this.datumCategoryDao = datumCategoryDao;
	}

	/**
	 * @param attachmentManager the attachmentManager to set
	 */
	public void setAttachmentManager(AttachmentManager attachmentManager) {
		this.attachmentManager = attachmentManager;
	}

	public void setDatumService(DatumService datumService) {
		this.datumService = datumService;
	}

	public void setMatterDatumDao(MatterDatumDao matterDatumDao) {
		this.matterDatumDao = matterDatumDao;
	}
	
	public List<DatumCategoryVO> findDatumCategory(int taskType, int workType, int processType, Long matterID,
			Long objectID, int objectType, byte bizAction){
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		Logic logic = Restrictions.logic(Restrictions.eq("b.taskType", taskType))
				.and(Restrictions.eq("b.workType", workType))
				.and(Restrictions.eq("b.processType", processType))
				.and(Restrictions.eq("b.matterID", matterID))
				.and(Restrictions.eq("b.bizAction", bizAction));
		filter.setCriterion(logic);
		filter.setOrder(Order.asc("b.displayOrder"));
		//String qs = "select a, b from DatumCategory a, MatterDatum b where b.datumType=a.id and b.taskType=?"
		//		+ " and b.workType=? and b.processType=? and b.matterID=? order by b.displayOrder";
		
		List<DatumCategoryWithMatterDatum> list = matterDatumDao.findDatumCategoryWithMatterDatum(filter);
		List<DatumCategory> ldc = new ArrayList<DatumCategory>(list);
		return populateDatumCategoryVOList(ldc, objectID, objectType, workType);
	}
	
	/**
	 * matterDatumType 表示隐藏还是显示
	 */
	public List<DatumCategoryVO> findDatumCategory(int taskType, int workType, int processType, Long matterID,
			int matterDatumType, Long objectID, int objectType, byte bizAction) {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		Logic logic = Restrictions.logic(Restrictions.eq("b.taskType", taskType))
				.and(Restrictions.eq("b.workType", workType))
				.and(Restrictions.eq("b.processType", processType))
				.and(Restrictions.eq("b.matterID", matterID))
				.and(Restrictions.eq("b.bizAction", bizAction))
				.and(Restrictions.eq("b.type", matterDatumType));
		filter.setCriterion(logic);
		filter.setOrder(Order.asc("b.displayOrder"));
		//String qs = "select a, b from DatumCategory a, MatterDatum b where b.datumType=a.id and b.taskType=?"
		//		+ " and b.workType=? and b.processType=? and b.matterID=? and b.type=? order by b.displayOrder";
		List<DatumCategoryWithMatterDatum> list = matterDatumDao.findDatumCategoryWithMatterDatum(filter);
		List<DatumCategory> ldc = new ArrayList<DatumCategory>(list);
		return populateDatumCategoryVOList(ldc, objectID, objectType, workType);
	}
	
	public List<DatumCategoryVO> findDatumCategory(int taskType, int workType, int processType, Long matterID,
			Long objectID, int objectType) {
		return findDatumCategory(taskType, workType, processType, matterID, objectID, objectType, MatterDatum.BIZ_ACTION_DEFAULT);
	}
	public List<DatumCategoryVO> findDatumCategoryBAK(int taskType, int workType, int processType, Long matterID,
			Long objectID, int objectType) {
		List<DatumCategory> ldc = matterDatumDao.findDatumCategory(taskType, workType, processType, matterID);
//		return populateDatumCategoryVOList(ldc, objectID);
		return populateDatumCategoryVOList(ldc, objectID, objectType, workType);
	}
	
	/**
	 * matterDatumType 表示隐藏还是显示
	 */
	public List<DatumCategoryVO> findDatumCategory(int taskType, int workType, int processType, Long matterID,
			int matterDatumType, Long objectID, int objectType){
		return findDatumCategory(taskType, workType, processType, matterID, matterDatumType, objectID, objectType, MatterDatum.BIZ_ACTION_DEFAULT); 
	}
	
	public List<DatumCategoryVO> findDatumCategoryBAK(int taskType, int workType, int processType, Long matterID,
			int matterDatumType, Long objectID, int objectType) {
		List<DatumCategory> ldc = matterDatumDao.findDatumCategory(taskType, workType, processType, matterID, matterDatumType);
		//return populateDatumCategoryVOList(ldc, objectID);
		return populateDatumCategoryVOList(ldc, objectID, objectType, workType);
	}
	
	private List<DatumCategoryVO> populateDatumCategoryVOList(List<DatumCategory> ldc, Long objectId, int objectType, int workType){
		if(ldc == null || ldc.isEmpty()){
			return Collections.emptyList();
		}
		List<Datum> datumList = new ArrayList<Datum>();
		if(objectId != null){
			RFSEntityObject entityObject = null;
			if(objectType == 0){
				entityObject = new RFSObjectDescriptor(objectType, objectId, null);
			}else{
				entityObject = new RFSEntityDescriptor(objectType, objectId);
			}
			datumList = datumService.findWorkDatum(entityObject, workType);
		}
		
		List<DatumCategoryVO> result = new ArrayList<DatumCategoryVO>();
		for(DatumCategory dc:ldc){
			result.add(buildDatumCategoryVO(dc, datumList));
		}
		return result;
	}
	
	/**
	 * @param dc
	 * @param datumList
	 * @return
	 * @since 2.0.2
	 */
	private DatumCategoryVO buildDatumCategoryVO(DatumCategory dc, List<Datum> datumList) {
		DatumAttachment da = null;
		if(datumList.size() > 0){
			Datum datum = null;
			for(Datum d: datumList){
				if(dc.getId().equals(d.getCategoryID()) && StringUtils.isNotBlank(d.getContent())){
					datum = d;
					break;
				}
			}
			//找到了datum
			if(datum != null && datum.getContent() != null){
				try {
					long fileId = Long.parseLong(datum.getContent());
					Attachment attachment = attachmentManager.getAttachment(fileId);
					da = new DatumAttachment(datum, attachment);
				} catch (NumberFormatException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		
		return new DatumCategoryVO(dc, da);
	}

//	private List<DatumCategoryVO> populateDatumCategoryVOList(List<DatumCategory> ldc, Long objectID) {
////		List<DatumCategory> ldc = matterDatumDao.findDatumCategory(taskType, workType, processType, matterID, matterDatumType);
//		List<DatumCategoryVO> ldcVo = new ArrayList<DatumCategoryVO>();
//		for (DatumCategory d : ldc) {
//			if (objectID != null) {
//				// //时间倒序排列
////				List<DatumAttachment> lda = datumDao.findDatumAttachmentByTypeAndCategoryID(objectID, (short) 0, d.getId());
////				if (lda.size() > 0) {
////					//有可能增加多条记录，取第一条即可
////					/*
////					for (DatumAttachment da : lda) {
////						DatumCategoryVO dcVo = new DatumCategoryVO(d, da);
////						ldcVo.add(dcVo);
////					}*/
////					DatumCategoryVO dcVo = new DatumCategoryVO(d, lda.get(0));
////					ldcVo.add(dcVo);
////					if(log.isDebugEnabled() && lda.size() > 1){
////						log.debug("对象‘" + objectID + "’有多条类型为‘" + d.getName() + "’的资料，只取最新的一条。");
////					}
////				} else {
////					DatumCategoryVO dcVo = new DatumCategoryVO(d, null);
////					ldcVo.add(dcVo);
////				}
//				//同一类型资料多次上传会根据配置覆盖以前，因此每种资料只能取得一个或0个当前资料
//				DatumAttachment da = datumService.getCurrentDatumAttachment(objectID, d.getId());
//				if(da != null){
//					DatumCategoryVO dcVo = new DatumCategoryVO(d, da);
//					ldcVo.add(dcVo);
//					log.debug("Find current datum for object: " + objectID);
//				}else{
//					DatumCategoryVO dcVo = new DatumCategoryVO(d, null);
//					ldcVo.add(dcVo);
//				}
//			} else {
//				DatumCategoryVO dcVo = new DatumCategoryVO(d, null);
//				ldcVo.add(dcVo);
//			}
//		}
//		return ldcVo;
//	}

	public int deleteDatumCategory(DatumCategory datumCategory) {
		return datumCategoryDao.delete(datumCategory);
	}

	public DatumCategory getDatumCategory(Long id) {
		return datumCategoryDao.get(id);
	}

	public DatumCategory updateDatumCategory(DatumCategory datumCategory) {
		return datumCategoryDao.update(datumCategory);
	}
}
