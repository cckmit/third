/*
 * $Id: CautionDecideServiceImpl.java 5971 2012-08-03 05:57:13Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.CautionDecide;
import cn.redflagsoft.base.dao.CautionDecideDao;
import cn.redflagsoft.base.service.CautionDecideService;


public class CautionDecideServiceImpl implements CautionDecideService {
	
	private CautionDecideDao cautionDecideDao;
	
	
	public CautionDecideDao getCautionDecideDao() {
		return cautionDecideDao;
	}

	public void setCautionDecideDao(CautionDecideDao cautionDecideDao) {
		this.cautionDecideDao = cautionDecideDao;
	}

	public CautionDecide getCautionDecideByCautionId(Long cautionId) {
		ResultFilter filter = new ResultFilter();
		
		filter.setCriterion(Restrictions.eq("cautionId", cautionId));
		filter.setOrder(Order.desc("decideTime"));
		
		List<CautionDecide> list = cautionDecideDao.find(filter);
		if(list.size()>0){
			return list.get(0);
		} else{
			return null;
		}
	}

	public int deleteCautionDecide(CautionDecide cautionDecide) {
		return cautionDecideDao.delete(cautionDecide);
	}

	public List<CautionDecide> findCautionDecides(ResultFilter filter) {
		return cautionDecideDao.find(filter);
	}

	public CautionDecide saveOrUpdateCautionDecide(Caution caution,CautionDecide cautionDecide) {
		boolean b = false;
		CautionDecide cautiond = getCautionDecideByCautionId(caution.getId());
		if(cautiond == null){
			cautiond = new CautionDecide();
			
			cautiond.setCautionId(caution.getId());
			cautiond.setCautionName(caution.getName());
			cautiond.setCautionCode(caution.getCode());
			b= true;
		}
		cautiond.setDecideFileNo(cautionDecide.getDecideFileNo());
		cautiond.setDecideFileId(cautionDecide.getDecideFileId());
		cautiond.setDecideOrgName(cautionDecide.getDecideOrgName());
		cautiond.setDecideOrgId(cautionDecide.getDecideOrgId());
		cautiond.setDecideHandleMode(cautionDecide.getDecideHandleMode());
		cautiond.setDecideTime(cautionDecide.getDecideTime());
		cautiond.setRemark(cautionDecide.getRemark());
		
		
		return b ? cautionDecideDao.save(cautiond):cautionDecideDao.update(cautiond);
	}

}
