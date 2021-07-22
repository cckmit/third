/*
 * $Id: CautionSurveyServiceImpl.java 5971 2012-08-03 05:57:13Z lcj $
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
import cn.redflagsoft.base.bean.CautionSurvey;
import cn.redflagsoft.base.dao.CautionSurveyDao;
import cn.redflagsoft.base.service.CautionSurveyService;


public class CautionSurveyServiceImpl implements CautionSurveyService {
	private CautionSurveyDao cautionSurveyDao;
	
	public CautionSurveyDao getCautionSurveyDao() {
		return cautionSurveyDao;
	}

	public void setCautionSurveyDao(CautionSurveyDao cautionSurveyDao) {
		this.cautionSurveyDao = cautionSurveyDao;
	}

	public CautionSurvey getCautionSurveyByCautionId(Long cautionId) {

		ResultFilter filter = new ResultFilter();
		filter.setCriterion(Restrictions.eq("cautionId", cautionId));
		filter.setOrder(Order.desc("surveyTime"));
		
		List<CautionSurvey> list = cautionSurveyDao.find(filter);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public int deleteCautionSurvey(CautionSurvey cautionSurvey) {
		
		return cautionSurveyDao.delete(cautionSurvey);
	}

	public List<CautionSurvey> findCautionSurveys(ResultFilter filter) {
		return cautionSurveyDao.find(filter);
	}

	public CautionSurvey saveOrUpdateCautionSurvey(Caution caution,
			CautionSurvey cautionSurvey) {
		boolean b = false;
		CautionSurvey cautions = getCautionSurveyByCautionId(caution.getId());
		if(cautions == null){
			cautions = new CautionSurvey();
			cautions.setCautionId(caution.getId());
			cautions.setCautionName(caution.getName());
			cautions.setCautionCode(caution.getCode());
			
			b = true;
		}
		cautions.setSurveyFileId(cautionSurvey.getSurveyFileId());
		cautions.setSurveyFileNo(cautionSurvey.getSurveyFileNo());
		cautions.setSurveyOrgId(cautionSurvey.getSurveyOrgId());
		cautions.setSurveyOrgName(cautionSurvey.getSurveyOrgName());
		cautions.setSurveyTime(cautionSurvey.getSurveyTime());
		cautions.setRemark(cautionSurvey.getRemark());
		
		
		return b? cautionSurveyDao.save(cautions):cautionSurveyDao.update(cautions);
	}
}
