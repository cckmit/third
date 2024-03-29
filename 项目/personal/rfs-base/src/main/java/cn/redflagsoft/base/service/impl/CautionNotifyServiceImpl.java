/*
 * $Id: CautionNotifyServiceImpl.java 5971 2012-08-03 05:57:13Z lcj $
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
import cn.redflagsoft.base.bean.CautionNotify;
import cn.redflagsoft.base.dao.CautionNotifyDao;
import cn.redflagsoft.base.service.CautionNotifyService;


public class CautionNotifyServiceImpl implements CautionNotifyService {

	private CautionNotifyDao cautionNotifyDao;
	
	
	public CautionNotifyDao getCautionNotifyDao() {
		return cautionNotifyDao;
	}

	public void setCautionNotifyDao(CautionNotifyDao cautionNotifyDao) {
		this.cautionNotifyDao = cautionNotifyDao;
	}

	public CautionNotify getCautionNotifyByCautionId(Long cautionId,int type) {
		ResultFilter filter = new ResultFilter();
		
		filter.setCriterion(Restrictions.logic(Restrictions.eq("type", type)).and(Restrictions.eq("cautionId", cautionId)));
		
		filter.setOrder(Order.desc("notifyTime"));
		
		List<CautionNotify> list = cautionNotifyDao.find(filter);
		if(list.size()>0){
			return list.get(0);
		} else{
			return null;
		}
	}

	public int deleteCautionNotify(CautionNotify cautionNotify) {
		return cautionNotifyDao.delete(cautionNotify);
	}

	public List<CautionNotify> findCautionNotifys(ResultFilter filter) {
		return cautionNotifyDao.find(filter);
	}

	public CautionNotify saveOrUpdateCautionNotify(Caution caution, CautionNotify cautionNotify) {
		
		CautionNotify cautionNotify2 = null;//getCautionNotifyByCautionId(caution.getId());
		if(cautionNotify2 == null){
			cautionNotify2 = new CautionNotify();
			cautionNotify2.setCautionId(caution.getId());
			cautionNotify2.setCautionName(caution.getName());
			cautionNotify2.setCautionCode(caution.getCode());
		}
		
		cautionNotify2.setNotifyFileNo(cautionNotify.getNotifyFileNo());
		cautionNotify2.setNotifyFileId(cautionNotify.getNotifyFileId());
		cautionNotify2.setNotifyTargetName(cautionNotify.getNotifyTargetName());
		cautionNotify2.setNotifyTargetId(cautionNotify.getNotifyTargetId());
		
		cautionNotify2.setNotifyContent(cautionNotify.getNotifyContent());
		cautionNotify2.setNotifyClerkName(cautionNotify.getNotifyClerkName());
		cautionNotify2.setNotifyClerkId(cautionNotify.getNotifyClerkId());
		cautionNotify2.setNotifyTime(cautionNotify.getNotifyTime());
		cautionNotify2.setType(cautionNotify.getType());
	
		cautionNotify2.setRemark(cautionNotify.getRemark());
		
		return cautionNotifyDao.save(cautionNotify2);
	}
}
