/*
 * $Id: CautionSurveyWorkScheme.java 5971 2012-08-03 05:57:13Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
 * ��ʾ����Լ̸
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
	 * ����Լ̸����
	 * @return
	 */
	public Object doSurvey(){
		Assert.notNull(getCautionSurvey(), "ҵ�������Ϊ�գ�");
		
		Caution caution = (Caution)getObject();
		Assert.notNull(caution, "ҵ����������Ϊ�գ�");
		
		CautionSurvey cautions = cautionSurveyService.saveOrUpdateCautionSurvey(caution, cautionSurvey);
		
		addRelatedItem(cautions);
		finishMatters();
		
		return "ҵ����ɹ���";
	}
	
	/**
	 * ���ݾ�ʾID��ѯ����Լ̸������
	 * 
	 * <p>������������¼��������ݣ�
	 * <ol>
	 * <li> ��ǰ��ʾ������ص�CautionSurvey����
	 * <li> ����Լ̸��Ӧ��Task��״̬��Ϣ
	 * <li> ����Լ̸�漰 ����������Work��Ϣ����Ҫ��status����Ϣ
	 * <li> ����Լ̸��Ӧ������ļ����Ϣ
	 * 
	 * </ol>
	 * @return ����Լ̸�ı�����
	 * @see FormDataLoader#buildFormData(Object, long, short, short)
	 */
	public Object viewGetFormData(){
		Assert.notNull(getObjectId(), "��ҵ�����ID����Ϊ�գ�");
		Assert.notNull(getTaskType(), "TaskType����Ϊ�գ�");
		
		CautionSurvey cautions = cautionSurveyService.getCautionSurveyByCautionId(getObjectId());
		
		return base_formDataLoader.buildFormData(cautions,getObjectId(),Caution.OBJECT_TYPE,getTaskType());
	}
	
	public Map<String, Object> buildParameters(MatterAffair m,
			WorkScheme w, RFSObject rfsObj, RFSObject rfsObj2) {
		if("doAvoid".equals(this.getMethod()) || "avoid".equals(this.getMethod())){
			Map<String,Object> map = new HashMap<String, Object>();
			//��װ����
			
			map.put("objectId", rfsObj.getId() + "");
			
			return map;
		}else{
			throw new SchemeException("�޷���������WorkScheme����Ĳ���.");
		}
		
	}
	
}
