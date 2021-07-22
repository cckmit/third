package cn.redflagsoft.base.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskEntry;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.dao.ObjectTaskDao;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.MsgTemplateService;
import cn.redflagsoft.base.service.ObjectInfoService;
import cn.redflagsoft.base.service.OrgService;
import cn.redflagsoft.base.service.RiskService;
import cn.redflagsoft.base.service.TaskService;
import cn.redflagsoft.base.vo.ObjectInfoVO;

/**
 * @deprecated
 */
public class MsgTemplateServiceImpl implements MsgTemplateService,InitializingBean {
   private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
   private static SimpleDateFormat sdf2=new SimpleDateFormat("MM��dd��HH��mm��");
   private TaskService taskService;
   private RiskService riskService;
   private ClerkService clerkService;
   private OrgService orgService;	
   private ObjectInfoService objectInfoService;
   private ObjectTaskDao objectTaskDao;
   private static Map<String,String> staticMap=new HashMap<String,String>();
   
   public TaskService getTaskService() {
	return taskService;
   }

   public void setTaskService(TaskService taskService) {
	this.taskService = taskService;
   }
   

	public RiskService getRiskService() {
		return riskService;
	}

	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}

	public ClerkService getClerkService() {
		return clerkService;
	}

	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}

	public OrgService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	public ObjectInfoService getObjectInfoService() {
		return objectInfoService;
	}

	public void setObjectInfoService(ObjectInfoService objectInfoService) {
		this.objectInfoService = objectInfoService;
	}

	
	public ObjectTaskDao getObjectTaskDao() {
		return objectTaskDao;
	}

	public void setObjectTaskDao(ObjectTaskDao objectTaskDao) {
		this.objectTaskDao = objectTaskDao;
	}

	//@Queryable(argNames={"template","taskSN"})
	public String getTaskMsgTemplateContent(String template,Long taskSN){
	   Map<String,String> msgTemplate=new HashMap<String,String>();
	   Task t=getTaskService().getTask(taskSN);
	   if(t!=null){
		   msgTemplate.put("${Task_Type_Name}",t.getName());  //ҵ�����
		   List<RFSObject> los=objectTaskDao.findObjectsByTaskSN(taskSN);
		   if(los!=null&&los.size()>0){
			   for(int i=0;i<los.size();i++)
			   {
				   ObjectInfoVO oiv=objectInfoService.getObjectInfo(los.get(i).getKey());
				   if(oiv!=null){
					   msgTemplate.put("${Project_Sn}",oiv.getObjectSN());//��Ŀ���
					   msgTemplate.put("${Project_Abbr}",oiv.getObjectName());//��Ŀ����
				   }
				   
			   }
		   }
		   List<RiskEntry> lr=t.getRiskEntries();
		   if(lr!=null&&lr.size()>0){
			   for(int i=0;i<lr.size();i++){
				  Risk r=riskService.getRiskById(lr.get(i).getRiskID());
				  if(r!=null){
					  msgTemplate.put("${Risk_Days_Value}",r.getValue()!=null?r.getValue().toString():"0");//��������
					  msgTemplate.put("${Risk_Date_Start}",sdf.format(r.getCreationTime()));//��ʼʱ��
					  msgTemplate.put("${Risk_Days_Remain}",r.getScaleValue().subtract(r.getValue()!=null?r.getValue():BigDecimal.ZERO).toString());//ʣ������
					  msgTemplate.put("${Risk_Day_ValueUnit}",r.getValueUnit()==RiskRule.VALUE_UNIT_DAY?"��": r.getValueUnit()==RiskRule.VALUE_UNIT_WORKDAY?"������":"");//��λ
					  msgTemplate.put("${Risk_Days_ScaleValue}",r.getScaleValue().toString());//�涨ʱ��
					  BigDecimal redScale=r.getScaleValue5();
						 if(redScale!=null){
							 msgTemplate.put("${Risk_Days_Red_Debug}",redScale.subtract(r.getScaleValue()).toString());//���ƾ���ʱ��
						 }
					  if(r.getDutyerID()!=null){
						   Org org=getOrgService().getOrgByDutyEntity(r.getDutyerID());
						   if(org!=null){
							   msgTemplate.put("${Risk_Dutyer_Name}",org.getAbbr());//���ε�λ
						   }
					   }	  
			     }
			   }
			   
		   }
		   if(t.getClerkID()!=null){
			 Clerk c=getClerkService().getClerk(t.getClerkID()); 
			 if(c!=null){
				 msgTemplate.put("${Task_Clerk_Name}", c.getName());//task�İ�����
			 }
		   }
		  
	   }
	   return changeTemplate(template,msgTemplate);
   }
	
	//@Queryable(argNames={"template","riskID"})
	public String getRiskMsgTemplateContent(String template,Long riskID){
		Map<String,String> msgTemplate=new HashMap<String,String>();
		Risk r=getRiskService().getRiskById(riskID);
		if(r!=null){
			 msgTemplate.put("${Risk_Days_Value}",r.getValue()!=null?r.getValue().toString():"0");//��������
			 msgTemplate.put("${Risk_Date_Start}",sdf.format(r.getCreationTime()));//��ʼʱ��
			 msgTemplate.put("${Risk_Days_Remain}",r.getScaleValue().subtract(r.getValue()!=null?r.getValue():BigDecimal.ZERO).toString());//ʣ������
			 msgTemplate.put("${Risk_Day_ValueUnit}",r.getValueUnit()==RiskRule.VALUE_UNIT_DAY?"��": r.getValueUnit()==RiskRule.VALUE_UNIT_WORKDAY?"������":"");//��λ
			 msgTemplate.put("${Risk_Days_ScaleValue}",r.getScaleValue().toString());//�涨ʱ��
			 BigDecimal redScale=r.getScaleValue5();
			 if(redScale!=null){
				 msgTemplate.put("${Risk_Days_Red_Debug}",redScale.subtract(r.getScaleValue()).toString());//���ƾ���ʱ��
			 }
			if(r.getObjectType()==Risk.OBJECT_TYPE_TASK&&r.getRefObjectType()==Risk.OBJECT_TYPE_PROJECT)
			{			
				ObjectInfoVO oiv=objectInfoService.getObjectInfo(r.getRefObjectId());
				if(oiv!=null){
					msgTemplate.put("${Project_Sn}",oiv.getObjectSN());//��Ŀ���
					msgTemplate.put("${Project_Abbr}",oiv.getObjectName());//��Ŀ����
				}
				Task t=getTaskService().getTask(r.getObjectID());	
				if(t!=null){
					msgTemplate.put("${Task_Type_Name}",t.getName()); //ҵ�����
					if(t.getClerkID()!=null){
					   Clerk c=getClerkService().getClerk(t.getClerkID()); 
					   if(c!=null){
						   msgTemplate.put("${Task_Clerk_Name}", c.getName());//task�İ�����
					   }
					 }
				}
				
			}
			 if(r.getDutyerID()!=null){
				   Org org=getOrgService().getOrgByDutyEntity(r.getDutyerID());
				   if(org!=null){
					   msgTemplate.put("${Risk_Dutyer_Name}",org.getAbbr());//���ε�λ		
				   }
			 }
		}
		return changeTemplate(template,msgTemplate);
	}
	
	public String changeTemplate(String template,Map<String,String> msgTemplate){
		Object[] keys1=staticMap.keySet().toArray();
		for(int i=0;i<keys1.length;i++){
			//System.out.println("==========keys1["+i+"]=========="+keys1[i]);
			template=template.replace((String)keys1[i],(String) staticMap.get(keys1[i]));
		}
		if(msgTemplate!=null){
			Object[] keys2=msgTemplate.keySet().toArray();
			for(int i=0;i<keys2.length;i++){
				//System.out.println("==========keys2["+i+"]=========="+keys2[i]);
				template=template.replace((String)keys2[i],(String) msgTemplate.get(keys2[i]));
			}
		}
		template=template.replace("${Now}",sdf.format(new Date()));//��ǰ����
		template=template.replace("${Now_Format1}",sdf2.format(new Date()));//xx��xx��xx��xx��
		return template;
	}
	


	public void afterPropertiesSet() throws Exception {
	    //FIXME �˴���Ӳ����
		//��̬������д�����������ÿ������Ӧ���Զ�����һ�Ρ�
		staticMap.put("${System_Abbr}", "��ɽ������Ͷ�ʽ�����Ŀȫ���̸��ٹ���ϵͳ");	//ϵͳ���	
	}
}
