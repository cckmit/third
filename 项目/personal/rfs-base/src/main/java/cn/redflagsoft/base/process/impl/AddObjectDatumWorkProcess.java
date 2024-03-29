/*
 * $Id: AddObjectDatumWorkProcess.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.process.impl;

import java.util.Map;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Datum;
import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.WorkProcessException;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.DatumService;

/**
 * 
 * 向指定的业务对象增加资料，并记录上传者的部门信息。
 * 输入参数是资料对象。
 * 
 * @author Alex Lin
 *
 */
@ProcessType(AddObjectDatumWorkProcess.TYPE)
public class AddObjectDatumWorkProcess extends AbstractWorkProcess {
	public static final int TYPE = 7017;
	private DatumService datumService;
	private Datum datum;

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.process.WorkProcess#execute(java.util.Map, boolean)
	 */
	public Object execute(Map parameters, boolean needLog)throws WorkProcessException {
		Clerk clerk = UserClerkHolder.getClerk();
		datum.setPromulgator(clerk.getEntityID());
		datum.setPromulgatorAbbr(clerk.getEntityName());
		return datumService.saveDatum(datum);
	}

	/**
	 * @return the datum
	 */
	public Datum getDatum() {
		return datum;
	}

	/**
	 * @param datum the datum to set
	 */
	public void setDatum(Datum datum) {
		this.datum = datum;
	}

	/**
	 * @return the datumService
	 */
	public DatumService getDatumService() {
		return datumService;
	}

	/**
	 * @param datumService the datumService to set
	 */
	public void setDatumService(DatumService datumService) {
		this.datumService = datumService;
	}
}
