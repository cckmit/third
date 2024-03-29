/*
 * $Id: LifeStageServiceImpl.java 5056 2011-11-11 01:59:28Z zf $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.LifeStage;
import cn.redflagsoft.base.dao.LifeStageDao;
import cn.redflagsoft.base.service.LifeStageService;
import cn.redflagsoft.base.util.LifeStageSummaryHelper;
import cn.redflagsoft.base.util.MapUtils;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class LifeStageServiceImpl implements LifeStageService {
	private LifeStageDao lifeStageDao;

	public LifeStageDao getLifeStageDao() {
		return lifeStageDao;
	}
	public void setLifeStageDao(LifeStageDao lifeStageDao) {
		this.lifeStageDao = lifeStageDao;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.LifeStageService#getLifeStage(long)
	 */
	public LifeStage getLifeStage(long id) {
		return lifeStageDao.get(id);
	}
	
	public static void main(String[] args){
		StringBuffer sb = new StringBuffer("select ");
		for(int i = 0 ; i < 100; i++){
			if(i > 0){
				sb.append(", ");
			}
			sb.append("sum(status" + i + ")");
			
			System.out.println("update hh_life_stage set status" + i + "=0 where status" + i + "<>9;");
			System.out.println("update hh_life_stage set status" + i + "=1 where status" + i + "=9;");
		}
		sb.append(" from hh_life_stage;");
		System.out.println(sb.toString());
		
		List<LifeStage> lss = new ArrayList<LifeStage>();
		LifeStageSummaryHelper.makeSummaryList(lss, (byte)1);
		
		System.out.println(null == null);
	}
	
	public List<Map<String, Object>> findSummaryLifeStage(ResultFilter resultFilter) {
		String[] groupFieldNames = null;
		byte compareStatusValue = 1;

		Map<String, ?> parameters = resultFilter.getRawParameters();
		if(parameters != null){
			String compareStatusValueString = MapUtils.getString(parameters,"compareStatusValue","1");
			String groupFieldNamesString = MapUtils.getString(parameters,"groupFieldNames");
			if(groupFieldNamesString != null){
				groupFieldNames = groupFieldNamesString.split(",");
			}
			compareStatusValue = Byte.parseByte(compareStatusValueString);
		}
		
		List<LifeStage> list = lifeStageDao.find(resultFilter);
		return LifeStageSummaryHelper.makeSummaryList(list, compareStatusValue, groupFieldNames);
	}
	
	public List<LifeStage> findLifeStage(ResultFilter filter) {
		return lifeStageDao.find(filter);
	}
	
	public LifeStage updateLifeStage(LifeStage lifeStage){
		return lifeStageDao.update(lifeStage);
	}
}


