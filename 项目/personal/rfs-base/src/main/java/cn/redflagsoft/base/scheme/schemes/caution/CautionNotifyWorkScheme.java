/*
 * $Id: CautionNotifyWorkScheme.java 5971 2012-08-03 05:57:13Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.caution;

import java.util.Map;

import org.opoo.util.Assert;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.CautionNotify;
import cn.redflagsoft.base.scheme.schemes.caution.AbstractCautionWorkScheme;
import cn.redflagsoft.base.service.CautionNotifyService;
import cn.redflagsoft.base.service.FormDataLoader;


/**
 * 警示告知
 * @author Administrator
 *
 */
public class CautionNotifyWorkScheme extends AbstractCautionWorkScheme {
	  
	private CautionNotifyService cautionNotifyService;
	private CautionNotify cautionNotify;
	
	private FormDataLoader base_formDataLoader;
	
	public CautionNotifyService getCautionNotifyService() {
		return cautionNotifyService;
	}


	public void setCautionNotifyService(CautionNotifyService cautionNotifyService) {
		this.cautionNotifyService = cautionNotifyService;
	}
	
	public CautionNotify getCautionNotify() {
		return cautionNotify;
	}


	public void setCautionNotify(CautionNotify cautionNotify) {
		this.cautionNotify = cautionNotify;
	}
	

	public FormDataLoader getBase_formDataLoader() {
		return base_formDataLoader;
	}


	public void setBase_formDataLoader(FormDataLoader baseFormDataLoader) {
		base_formDataLoader = baseFormDataLoader;
	}


	/**
	 * 警示短信告知
	 * @return
	 */
	public Object doSmsNotify(){
		Assert.notNull(getCautionNotify(), "警示告知对象不能为空");
		Caution caution = (Caution)getObject();
		Assert.notNull(caution, "警示对象不能为空");
		
		getCautionNotify().setType(CautionNotify.TYPE_SMS_NOTIFY);
		CautionNotify CautionNotify2 = getCautionNotifyService().saveOrUpdateCautionNotify(caution, getCautionNotify());
		
		Assert.notNull(CautionNotify2);
		addRelatedItem(CautionNotify2);
		finishMatters();
		
		return "业务处理成功！";
	}
	
	
	/**
	 * 警示文书告知
	 * @return
	 */
	public Object doBookNotify(){
		Assert.notNull(getCautionNotify(), "警示告知对象不能为空");
		Caution caution = (Caution)getObject();
		Assert.notNull(caution, "警示对象不能为空");
		
		getCautionNotify().setType(CautionNotify.TYPE_BOOK_NOYIFY);
		CautionNotify CautionNotify2 = getCautionNotifyService().saveOrUpdateCautionNotify(caution, getCautionNotify());
		Assert.notNull(CautionNotify2);
		addRelatedItem(CautionNotify2);
		finishMatters();
		
		return "业务处理成功！";
	}
	
	
	
	public Object viewGetFormData(){
		Assert.notNull(getObjectId(), "ObjectId is required");
		Assert.notNull(getTaskType(), "taskType is required");

		Map<String, Object> map = base_formDataLoader.buildFormData(null, getObjectId(), Caution.OBJECT_TYPE, getTaskType());
		
		CautionNotify smsNotify = getCautionNotifyService().getCautionNotifyByCautionId(getObjectId(),CautionNotify.TYPE_SMS_NOTIFY);
		
		if(smsNotify != null){
			map.put("smsNotify.notifyFileNo", smsNotify.getNotifyFileNo());
			map.put("smsNotify.notifyFileId", smsNotify.getNotifyFileId());
			map.put("smsNotify.notifyTargetName", smsNotify.getNotifyTargetName());
			map.put("smsNotify.notifyContent", smsNotify.getNotifyContent());
			map.put("smsNotify.notifyClerkName", smsNotify.getNotifyClerkName());
			map.put("smsNotify.notifyTime", smsNotify.getNotifyTime());
			map.put("smsNotify.remark", smsNotify.getRemark());
		
		}
		CautionNotify bookNotify = getCautionNotifyService().getCautionNotifyByCautionId(getObjectId(),CautionNotify.TYPE_BOOK_NOYIFY);
		
		if(bookNotify!= null){
			map.put("bookNotify.notifyFileNo", bookNotify.getNotifyFileNo());
			map.put("bookNotify.notifyFileId", bookNotify.getNotifyFileId());
			map.put("bookNotify.notifyTargetName", bookNotify.getNotifyTargetName());
			map.put("bookNotify.notifyContent", bookNotify.getNotifyContent());
			map.put("bookNotify.notifyClerkName", bookNotify.getNotifyClerkName());
			map.put("bookNotify.notifyTime", bookNotify.getNotifyTime());
			map.put("bookNotify.remark", bookNotify.getRemark());
		}
		
		return map;
	}
}
