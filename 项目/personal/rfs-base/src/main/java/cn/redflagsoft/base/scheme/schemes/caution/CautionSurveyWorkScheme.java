/*
 * $Id: CautionSurveyWorkScheme.java 5971 2012-08-03 05:57:13Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme.schemes.caution;
import java.util.HashMap;
import java.util.Map;

import org.opoo.util.Assert;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.CautionSurvey;
import cn.redflagsoft.base.bean.MatterAffair;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.scheme.SchemeParametersBuilder;
import cn.redflagsoft.base.scheme.WorkScheme;
import cn.redflagsoft.base.scheme.schemes.caution.AbstractCautionWorkScheme;
import cn.redflagsoft.base.service.CautionSurveyService;
import cn.redflagsoft.base.service.FormDataLoader;


/**
 * 警示调查约谈
 * @author Administrator
 *
 */
public class CautionSurveyWorkScheme extends AbstractCautionWorkScheme  implements SchemeParametersBuilder{
	private FormDataLoader base_formDataLoader;
	private CautionSurveyService cautionSurveyService;
	
	private CautionSurvey cautionSurvey;
	
	public FormDataLoader getBase_formDataLoader() {
		return base_formDataLoader;
	}

	public void setBase_formDataLoader(FormDataLoader baseFormDataLoader) {
		base_formDataLoader = baseFormDataLoader;
	}

	public CautionSurveyService getCautionSurveyService() {
		return cautionSurveyService;
	}

	public void setCautionSurveyService(CautionSurveyService cautionSurveyService) {
		this.cautionSurveyService = cautionSurveyService;
	}

	public CautionSurvey getCautionSurvey() {
		return cautionSurvey;
	}

	public void setCautionSurvey(CautionSurvey cautionSurvey) {
		this.cautionSurvey = cautionSurvey;
	}
	
	/**
	 * 调查约谈办理
	 * @return
	 */
	public Object doSurvey(){
		Assert.notNull(getCautionSurvey(), "业务对象不能为空！");
		
		Caution caution = (Caution)getObject();
		Assert.notNull(caution, "业务主对象不能为空！");
		
		CautionSurvey cautions = cautionSurveyService.saveOrUpdateCautionSurvey(caution, cautionSurvey);
		
		addRelatedItem(cautions);
		finishMatters();
		
		return "业务处理成功！";
	}
	
	/**
	 * 根据警示ID查询调查约谈表单对象。
	 * 
	 * <p>表单对象包含以下几部分内容：
	 * <ol>
	 * <li> 当前警示对象相关的CautionSurvey对象
	 * <li> 调查约谈对应的Task的状态信息
	 * <li> 调查约谈涉及 结束操作的Work信息，主要是status的信息
	 * <li> 调查约谈对应的事项的监管信息
	 * 
	 * </ol>
	 * @return 调查约谈的表单对象
	 * @see FormDataLoader#buildFormData(Object, long, short, short)
	 */
	public Object viewGetFormData(){
		Assert.notNull(getObjectId(), "主业务对象ID不能为空！");
		Assert.notNull(getTaskType(), "TaskType不能为空！");
		
		CautionSurvey cautions = cautionSurveyService.getCautionSurveyByCautionId(getObjectId());
		
		return base_formDataLoader.buildFormData(cautions,getObjectId(),Caution.OBJECT_TYPE,getTaskType());
	}
	
	public Map<String, Object> buildParameters(MatterAffair m,
			WorkScheme w, RFSObject rfsObj, RFSObject rfsObj2) {
		if("doAvoid".equals(this.getMethod()) || "avoid".equals(this.getMethod())){
			Map<String,Object> map = new HashMap<String, Object>();
			//组装参数
			
			map.put("objectId", rfsObj.getId() + "");
			
			return map;
		}else{
			throw new SchemeException("无法构建调用WorkScheme所需的参数.");
		}
		
	}
	
}
