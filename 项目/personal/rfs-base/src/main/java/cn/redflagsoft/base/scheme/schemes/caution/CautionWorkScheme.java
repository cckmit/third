/*
 * $Id: CautionWorkScheme.java 5971 2012-08-03 05:57:13Z lcj $
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

import org.opoo.apps.AppsGlobals;
import org.opoo.util.Assert;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.MatterAffair;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.commons.ObjectFinish;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.scheme.SchemeParametersBuilder;
import cn.redflagsoft.base.scheme.WorkScheme;
import cn.redflagsoft.base.scheme.schemes.caution.AbstractCautionWorkScheme;
import cn.redflagsoft.base.service.CautionService;
import cn.redflagsoft.base.service.ObjectAdminService;
import cn.redflagsoft.base.service.RiskService;
import cn.redflagsoft.base.service.SmsgService;
import cn.redflagsoft.base.util.MapUtils;
import cn.redflagsoft.base.service.CallSmsgOrCallCautionNotifyService;
import cn.redflagsoft.base.service.FormDataLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class CautionWorkScheme extends AbstractCautionWorkScheme  implements SchemeParametersBuilder{
	private static final Log log = LogFactory.getLog(CautionWorkScheme.class);
	/**
	 * 可以免办后续步骤的 grade 值集合。
	 */
	public static final String AVOID_CAUTION_STEPS_GRADES = ",1,";
	public static final String AVOID_CAUTION_STEPS_GRADES_PROP = "avoid.caution.steps.grades";
	
	private Caution caution;
	private ObjectFinish cautionFinish;
	
	private FormDataLoader base_formDataLoader;
	private ObjectAdminService objectAdminService;
	private RiskService riskService;
	private SmsgService smsgService;
	private CallSmsgOrCallCautionNotifyService callSmsgOrCallCautionNotifyService;
	private Long riskId;
	private Long cautionId;
	
	public CallSmsgOrCallCautionNotifyService getCallSmsgOrCallCautionNotifyService() {
		return callSmsgOrCallCautionNotifyService;
	}
	public void setCallSmsgOrCallCautionNotifyService(
			CallSmsgOrCallCautionNotifyService callSmsgOrCallCautionNotifyService) {
		this.callSmsgOrCallCautionNotifyService = callSmsgOrCallCautionNotifyService;
	}
	public SmsgService getSmsgService() {
		return smsgService;
	}
	public void setSmsgService(SmsgService smsgService) {
		this.smsgService = smsgService;
	}
	public Long getCautionId() {
		return cautionId;
	}
	public void setCautionId(Long cautionId) {
		this.cautionId = cautionId;
	}
	public Long getRiskId() {
		return riskId;
	}
	public void setRiskId(Long riskId) {
		this.riskId = riskId;
	}
	
	public FormDataLoader getBase_formDataLoader() {
		return base_formDataLoader;
	}
	public void setBase_formDataLoader(FormDataLoader baseFormDataLoader) {
		base_formDataLoader = baseFormDataLoader;
	}
	/**
	 * @return the riskService
	 */
	public RiskService getRiskService() {
		return riskService;
	}
	/**
	 * @param riskService the riskService to set
	 */
	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}
	/**
	 * @return the caution
	 */
	public Caution getCaution() {
		return caution;
	}
	/**
	 * @param caution the caution to set
	 */
	public void setCaution(Caution caution) {
		this.caution = caution;
	}

	public ObjectFinish getCautionFinish() {
		return cautionFinish;
	}
	public void setCautionFinish(ObjectFinish cautionFinish) {
		this.cautionFinish = cautionFinish;
	}
	
	public ObjectAdminService getObjectAdminService() {
		return objectAdminService;
	}
	public void setObjectAdminService(ObjectAdminService objectAdminService) {
		this.objectAdminService = objectAdminService;
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.scheme.AbstractWorkScheme#createObject()
	 */
	@Override
	protected RFSObject createObject() {
		
		Assert.notNull(getRiskId(),"RiskID不能为空！");
		CautionService cautionService = (CautionService) getObjectService();
		
		//如果指定了clerkID则查询，没有则取当前用户
		Clerk superviseClerk = getClerk();
		Risk risk = riskService.getRiskById(getRiskId());
		Assert.notNull(risk, "监察对象不能为空，无法登记警示。");
		
		//如果是在前台登记，则有caution对象，否则没有caution对象
		if(caution != null){
			return cautionService.createCaution(risk, superviseClerk, caution.getName(), caution.getCode(), caution.getGrade(), caution.getObjAttrValue(), caution.getConclusion(), caution.getHappenTime(),caution.getCautionSummary(), caution.getRemark());
		}else{
			//@see RiskGradeChangeHandler4Caution#gradeChange(Risk, byte)
			return cautionService.createCaution(risk, superviseClerk);
		}
	}
	

	/**
	 * 通过配置运行时属性 <code>avoid.caution.steps.grades</code> 指定可以免办后续步骤的
	 * grade 值集合，值前后都是用","连接，例如“提醒”时直接发送短信免办后续步骤，则配置该属性
	 * 为“,1,”，如果要在“提醒”和“预警”时都免办后续步骤并直接发短信，则配置该属性为“,1,2,”。
	 */
	public Object doScheme(){
		finishMatters();
		Caution c = (Caution) getObject();
		
		//if(c.getGrade() == Risk.GRADE_WHITE){
		if(canAvoid(c.getGrade())){
			try {
				callSmsgOrCallCautionNotifyService.callSmsg(c);
				callSmsgOrCallCautionNotifyService.callCautionNotify(c, null);
			} catch (Exception e) {
				log.error("调用callSmsgOrCallCautionNotifyService出错");
				throw new SchemeException(e);
			}
			
			if(c.getGrade() > 0){
				finishMatter(c.getGrade());
			}
		}
		
		//注意，finishMatters()默认执行的tag为0的，所以把小于等于0点必须去掉
//		if(c.getGrade() > 0){
//			finishMatter(c.getGrade());
//		}
		return "警示登记成功！";
	}
	
	private boolean canAvoid(byte grade){
		String value = AppsGlobals.getProperty(AVOID_CAUTION_STEPS_GRADES_PROP, AVOID_CAUTION_STEPS_GRADES);
		return value.contains("," + grade + ",");
	}
	
	public Object viewGetFormData(){
		
		Assert.notNull( getCautionId(),"getCautionId() is required");
		Assert.notNull(getTaskType(), "TaskType is required");
		Caution caution = (Caution) getObjectService().getObject(cautionId);
		return base_formDataLoader.buildFormData(caution, getCautionId(), Caution.OBJECT_TYPE, getTaskType());
	}
	
	public Object doFinish(){
		Assert.notNull(getCautionFinish(), "前台业务对象不能为空！");
		
		Caution oldCaution = (Caution)getObject();
		Assert.notNull(oldCaution, "业务主对象不能为空！");
											  //operateDescLabel
		String operateDesc = MapUtils.getString(getParameters(), "cautionFinish.operateDescLabel");
		if(operateDesc != null){
			cautionFinish.setOperateDesc(operateDesc);
		}
		
		ObjectFinish finishCaution = getObjectAdminService().finishObject(oldCaution, cautionFinish);
		Assert.notNull(finishCaution);
		
		addRelatedItem(finishCaution);
		finishMatters();
		
		return "业务办理成功！";
	}
	
	public Object viewGetFinishFormData(){
		Assert.notNull(getObjectId(), "ObjectID is required");
		Assert.notNull(getTaskType(), "TaskType is required");
		
		Caution cau = (Caution)getObjectService().getObject(getObjectId());
		ObjectFinish finish = null;
		if(cau.getFinishId() != null){
			finish = getObjectAdminService().getObjectAdmin(cau.getFinishId());
			
		}
		
		return base_formDataLoader.buildFormData(finish, getObjectId(), Caution.OBJECT_TYPE, getTaskType());
		
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
