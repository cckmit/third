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
   private static SimpleDateFormat sdf2=new SimpleDateFormat("MM月dd号HH点mm分");
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
		   msgTemplate.put("${Task_Type_Name}",t.getName());  //业务类别
		   List<RFSObject> los=objectTaskDao.findObjectsByTaskSN(taskSN);
		   if(los!=null&&los.size()>0){
			   for(int i=0;i<los.size();i++)
			   {
				   ObjectInfoVO oiv=objectInfoService.getObjectInfo(los.get(i).getKey());
				   if(oiv!=null){
					   msgTemplate.put("${Project_Sn}",oiv.getObjectSN());//项目编号
					   msgTemplate.put("${Project_Abbr}",oiv.getObjectName());//项目名称
				   }
				   
			   }
		   }
		   List<RiskEntry> lr=t.getRiskEntries();
		   if(lr!=null&&lr.size()>0){
			   for(int i=0;i<lr.size();i++){
				  Risk r=riskService.getRiskById(lr.get(i).getRiskID());
				  if(r!=null){
					  msgTemplate.put("${Risk_Days_Value}",r.getValue()!=null?r.getValue().toString():"0");//已用天数
					  msgTemplate.put("${Risk_Date_Start}",sdf.format(r.getCreationTime()));//开始时间
					  msgTemplate.put("${Risk_Days_Remain}",r.getScaleValue().subtract(r.getValue()!=null?r.getValue():BigDecimal.ZERO).toString());//剩余天数
					  msgTemplate.put("${Risk_Day_ValueUnit}",r.getValueUnit()==RiskRule.VALUE_UNIT_DAY?"天": r.getValueUnit()==RiskRule.VALUE_UNIT_WORKDAY?"工作日":"");//单位
					  msgTemplate.put("${Risk_Days_ScaleValue}",r.getScaleValue().toString());//规定时限
					  BigDecimal redScale=r.getScaleValue5();
						 if(redScale!=null){
							 msgTemplate.put("${Risk_Days_Red_Debug}",redScale.subtract(r.getScaleValue()).toString());//红牌纠错时限
						 }
					  if(r.getDutyerID()!=null){
						   Org org=getOrgService().getOrgByDutyEntity(r.getDutyerID());
						   if(org!=null){
							   msgTemplate.put("${Risk_Dutyer_Name}",org.getAbbr());//责任单位
						   }
					   }	  
			     }
			   }
			   
		   }
		   if(t.getClerkID()!=null){
			 Clerk c=getClerkService().getClerk(t.getClerkID()); 
			 if(c!=null){
				 msgTemplate.put("${Task_Clerk_Name}", c.getName());//task的办理人
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
			 msgTemplate.put("${Risk_Days_Value}",r.getValue()!=null?r.getValue().toString():"0");//已用天数
			 msgTemplate.put("${Risk_Date_Start}",sdf.format(r.getCreationTime()));//开始时间
			 msgTemplate.put("${Risk_Days_Remain}",r.getScaleValue().subtract(r.getValue()!=null?r.getValue():BigDecimal.ZERO).toString());//剩余天数
			 msgTemplate.put("${Risk_Day_ValueUnit}",r.getValueUnit()==RiskRule.VALUE_UNIT_DAY?"天": r.getValueUnit()==RiskRule.VALUE_UNIT_WORKDAY?"工作日":"");//单位
			 msgTemplate.put("${Risk_Days_ScaleValue}",r.getScaleValue().toString());//规定时限
			 BigDecimal redScale=r.getScaleValue5();
			 if(redScale!=null){
				 msgTemplate.put("${Risk_Days_Red_Debug}",redScale.subtract(r.getScaleValue()).toString());//红牌纠错时限
			 }
			if(r.getObjectType()==Risk.OBJECT_TYPE_TASK&&r.getRefObjectType()==Risk.OBJECT_TYPE_PROJECT)
			{			
				ObjectInfoVO oiv=objectInfoService.getObjectInfo(r.getRefObjectId());
				if(oiv!=null){
					msgTemplate.put("${Project_Sn}",oiv.getObjectSN());//项目编号
					msgTemplate.put("${Project_Abbr}",oiv.getObjectName());//项目名称
				}
				Task t=getTaskService().getTask(r.getObjectID());	
				if(t!=null){
					msgTemplate.put("${Task_Type_Name}",t.getName()); //业务类别
					if(t.getClerkID()!=null){
					   Clerk c=getClerkService().getClerk(t.getClerkID()); 
					   if(c!=null){
						   msgTemplate.put("${Task_Clerk_Name}", c.getName());//task的办理人
					   }
					 }
				}
				
			}
			 if(r.getDutyerID()!=null){
				   Org org=getOrgService().getOrgByDutyEntity(r.getDutyerID());
				   if(org!=null){
					   msgTemplate.put("${Risk_Dutyer_Name}",org.getAbbr());//责任单位		
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
		template=template.replace("${Now}",sdf.format(new Date()));//当前日期
		template=template.replace("${Now_Format1}",sdf2.format(new Date()));//xx月xx号xx点xx分
		return template;
	}
	


	public void afterPropertiesSet() throws Exception {
	    //FIXME 此处有硬代码
		//静态变量可写在这个方法，每次启动应用自动调用一次。
		staticMap.put("${System_Abbr}", "南山区政府投资建设项目全过程跟踪管理系统");	//系统简称	
	}
}
