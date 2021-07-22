/*
 * $Id: DatumAccessLogServiceImpl.java 5395 2012-03-06 01:18:05Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.DatumAccessLog;
import cn.redflagsoft.base.dao.DatumAccessLogDao;
import cn.redflagsoft.base.service.DatumAccessLogService;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class DatumAccessLogServiceImpl implements DatumAccessLogService {
	
	private DatumAccessLogDao datumAccessLogDao;

	/**
	 * @return the datumAccessLogDao
	 */
	public DatumAccessLogDao getDatumAccessLogDao() {
		return datumAccessLogDao;
	}

	/**
	 * @param datumAccessLogDao the datumAccessLogDao to set
	 */
	public void setDatumAccessLogDao(DatumAccessLogDao datumAccessLogDao) {
		this.datumAccessLogDao = datumAccessLogDao;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DatumAccessLogService#saveAccessLog(cn.redflagsoft.base.bean.DatumAccessLog)
	 */
	public DatumAccessLog saveAccessLog(DatumAccessLog datumAccessLog) {
		Assert.notNull(datumAccessLog);
		Assert.notNull(datumAccessLog.getDatumId());
		return datumAccessLogDao.save(datumAccessLog);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.DatumAccessLogService#findAccessLogs(java.lang.Long)
	 */
	public List<DatumAccessLog> findAccessLogs(Long datumId) {
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(Restrictions.eq("datumId", datumId));
		filter.setOrder(Order.desc("accessTime"));
		return datumAccessLogDao.find(filter);
	}

}
