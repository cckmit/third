package cn.redflagsoft.base.service;

import java.util.Map;


/**
 * @deprecated
 */
public interface MsgTemplateService {

	String getTaskMsgTemplateContent(String template, Long taskSN);
	
	String getRiskMsgTemplateContent(String template,Long riskID);
	
	public String changeTemplate(String template, Map<String,String> msgTemplate);
}