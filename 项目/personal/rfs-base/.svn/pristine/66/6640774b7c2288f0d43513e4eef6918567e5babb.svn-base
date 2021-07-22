package cn.redflagsoft.base.service.impl;

import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.HistoryRisk;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.dao.ClerkDao;
import cn.redflagsoft.base.dao.HistoryRiskDao;
import cn.redflagsoft.base.dao.OrgDao;
import cn.redflagsoft.base.dao.TaskDao;
import cn.redflagsoft.base.dao.TaskMatterDao;
import cn.redflagsoft.base.service.RiskGradeChangeHandler;
import cn.redflagsoft.base.service.RiskMessageService;
import cn.redflagsoft.base.service.RiskRuleService;


/**
 * 默认的风险变更处理器。
 *
 */
public class RiskGradeChangeHandlerImpl implements RiskGradeChangeHandler {
	private static final Log log = LogFactory.getLog(RiskGradeChangeHandlerImpl.class);
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	private ClerkDao clerkDao;
	private TaskMatterDao taskMatterDao;
	private OrgDao orgDao;
	private HistoryRiskDao historyRiskDao;
	private TaskDao taskDao;
	private RiskMessageService riskMessageService;
	private RiskRuleService riskRuleService;
	

    public ClerkDao getClerkDao() {
    	return clerkDao;
    }

    public void setClerkDao(ClerkDao clerkDao) {
    	this.clerkDao = clerkDao;
    }

    public TaskMatterDao getTaskMatterDao() {
    	return taskMatterDao;
    }

    public void setTaskMatterDao(TaskMatterDao taskMatterDao) {
    	this.taskMatterDao = taskMatterDao;
    }

    public OrgDao getOrgDao() {
    	return orgDao;
    }

    public void setOrgDao(OrgDao orgDao) {
    	this.orgDao = orgDao;
    }

    public HistoryRiskDao getHistoryRiskDao() {
    	return historyRiskDao;
    }

    public void setHistoryRiskDao(HistoryRiskDao historyRiskDao) {
    	this.historyRiskDao = historyRiskDao;
    }

    public TaskDao getTaskDao() {
    	return taskDao;
    }

    public void setTaskDao(TaskDao taskDao) {
    	this.taskDao = taskDao;
    }

    public RiskMessageService getRiskMessageService() {
    	return riskMessageService;
    }

    public void setRiskMessageService(RiskMessageService riskMessageService) {
    	this.riskMessageService = riskMessageService;
    }


	/**
	 * @return the riskRuleService
	 */
	public RiskRuleService getRiskRuleService() {
		return riskRuleService;
	}

	/**
	 * @param riskRuleService the riskRuleService to set
	 */
	public void setRiskRuleService(RiskRuleService riskRuleService) {
		this.riskRuleService = riskRuleService;
	}

	/**
     * grade 改变时处理
     * 
     * 暂时处理未办结状态下风险
     */
    public void gradeChange(Risk risk, byte oldGrade) {
    	if (RiskRule.RULE_TYPE_监察 != risk.getRuleType()) {
			log.debug("不是监察，不处理");
			return;
		}
    	
        //Task task=null;
 /*
  ***********************似乎没有用到以下代码 ****************
  * Task为空时调用task.getTimeLimit()出错，因此注释掉该段代码
  * 2010-08-09 by lcj
  *     
    	Date juralTime=null;
        if(risk!=null && risk.getObjectType()==102){//object为task            
            task = taskDao.get(risk.getObjectID());            
            juralTime=DateUtil.getDays(task.getBeginTime(), task.getTimeLimit(), Calendar.DATE);
        }else{            
            juralTime=DateUtil.getDays(risk.getCreationTime(), risk.getScaleValue().intValue(), Calendar.DATE);            
        }
        
        Date currTime=new Date();
        String stringTime = sdf.format(juralTime);
        //要区分工作日和自然日进行计算
        int time=DateUtil.getTimeUsed(currTime,juralTime,risk.getValueUnit());
 ***********************************************/       
        /*//旧的写法1
        String dayName="";
        if(4==risk.getValueUnit()){//日
            dayName="个工作日";
        }else if(5==risk.getValueUnit()){//天
            dayName="天";
        }else{
            dayName="天";
        }
    
        if (risk.getGrade() == Risk.GRADE_BLUE)
            risk.setConclusion("当前业务还未办理完成，离规定的办理时限（" + stringTime + "）还剩" + Math.abs(time) + dayName+"。系统发出预警。");
        else if (risk.getGrade() == Risk.GRADE_YELLOW)
            risk.setConclusion("当前业务还未办理完成，比规定的办理时限（" + stringTime + "）超期了" + Math.abs(time) + dayName+"。系统发出黄牌警告。");       
        else if (risk.getGrade() == Risk.GRADE_RED)
            risk.setConclusion("当前业务还未办理完成，比规定的办理时限（" + stringTime + "）超期了" + Math.abs(time) + dayName+"。系统发出红牌警告。");       
         */
        
        /*//旧的写法2
        if (risk.getGrade() == Risk.GRADE_BLUE)
            risk.setConclusion("当前业务尚未办理完成，办理时间已超出规定的办理时限。系统发出预警。");
        else if (risk.getGrade() == Risk.GRADE_YELLOW)
            risk.setConclusion("当前业务尚未办理完成，办理时间已超出规定的办理时限。系统发出黄牌警告。");       
        else if (risk.getGrade() == Risk.GRADE_RED)
            risk.setConclusion("当前业务尚未办理完成，办理时间已超出规定的办理时限。系统发出红牌警告。");       
       */

        //since 2.1.0-20110926,移到RiskServiceImpl中处理
        //根据grade设置监察结论（目前先不考虑变量情况）
        /*switch (risk.getGrade()) {
        case Risk.GRADE_NORMAL:
            risk.setConclusion("");
            break;
        case Risk.GRADE_WHITE:
            risk.setConclusion(risk.getConclusionTpl1());
            break;
        case Risk.GRADE_BLUE:
            risk.setConclusion(risk.getConclusionTpl2());
            break;
        case Risk.GRADE_YELLOW:
            risk.setConclusion(risk.getConclusionTpl3());
            break;
        case Risk.GRADE_ORANGE:
            risk.setConclusion(risk.getConclusionTpl4());
            break;
        case Risk.GRADE_RED:
            risk.setConclusion(risk.getConclusionTpl5());
            break;
        case Risk.GRADE_BLACK:
            risk.setConclusion(risk.getConclusionTpl6());
            break;
        default:
            risk.setConclusion("");
            break;
        }*/
        RiskRule rule = getRiskRuleService().getRiskRule(risk.getRuleID());
        
        //if(task != null){
            //saveHistoryRisk(risk, rule, task);  
        //}else{
            saveHistoryRisk(risk, rule);  
        //}
    }

    /**
     * 保存历史
     * @param risk
     */
    void saveHistoryRisk(final Risk risk, final RiskRule rule,  final Task task) {
    	new Thread() {
    		public void run() {
    			HistoryRisk historyRisk = new HistoryRisk();
    			historyRisk.setCreationTime(risk.getCreationTime());
    			historyRisk.setCreator(risk.getCreator());
    			historyRisk.setTaskCode(task.getCode());
    			historyRisk.setDutyerID(risk.getDutyerID());
    			historyRisk.setDutyerName(risk.getDutyerName());
    			historyRisk.setDutyerType(risk.getDutyerType());
    			if (task.getEntityID() != null) {
    				historyRisk.setEntityName(orgDao.get(task.getEntityID()).getName());
    			}
    			historyRisk.setGrade(risk.getGrade());
    			
                historyRisk.setCategory(risk.getCategory());
                historyRisk.setThingCode(risk.getThingCode());
                historyRisk.setThingName(risk.getThingName());
    			
    			historyRisk.setJuralLimit(risk.getJuralLimit());
    			historyRisk.setConclusionTpl1(rule.getConclusionTpl1());
    			historyRisk.setConclusionTpl2(rule.getConclusionTpl2());
    			historyRisk.setConclusionTpl3(rule.getConclusionTpl3());
    			historyRisk.setConclusionTpl4(rule.getConclusionTpl4());
    			historyRisk.setConclusionTpl5(rule.getConclusionTpl5());
    			historyRisk.setConclusionTpl6(rule.getConclusionTpl6());
    			historyRisk.setConclusion(risk.getConclusion());
    			
    			historyRisk.setModificationTime(risk.getModificationTime());
    			historyRisk.setModifier(risk.getModifier());
    			historyRisk.setName(risk.getName());
    			historyRisk.setObjectAttr(risk.getObjectAttr());
    			historyRisk.setObjectID(risk.getObjectID());
    			historyRisk.setObjectType(risk.getObjectType());
    			historyRisk.setPause(risk.getPause());
    			historyRisk.setRefID(risk.getRefID());
    			historyRisk.setRefObjectId(risk.getRefObjectId());
    			historyRisk.setRefObjectType(risk.getRefObjectType());
    			historyRisk.setRefTaskTypeName(risk.getRefTaskTypeName());
    			historyRisk.setRefType(risk.getRefType());
    			historyRisk.setRemark(risk.getRemark());
    			historyRisk.setRuleID(risk.getRuleID());
    			historyRisk.setScaleMark(risk.getScaleMark());
    			historyRisk.setScaleValue(risk.getScaleValue());
    			historyRisk.setScaleValue1(risk.getScaleValue1());
    			historyRisk.setScaleValue2(risk.getScaleValue2());
    			historyRisk.setScaleValue3(risk.getScaleValue3());
    			historyRisk.setScaleValue4(risk.getScaleValue4());
    			historyRisk.setScaleValue5(risk.getScaleValue5());
    			historyRisk.setScaleValue6(risk.getScaleValue6());
    			historyRisk.setStatus(risk.getStatus());
    			historyRisk.setSystemID(risk.getSystemID());
    			historyRisk.setTaskBeginTime(task.getBeginTime());
    			historyRisk.setTaskEndTime(task.getEndTime());
    			historyRisk.setTaskName(task.getName());
    			historyRisk.setTaskSn(task.getId());
    			historyRisk.setTaskStatus(task.getStatus());
    			historyRisk.setTaskStatusName(task.getStatusName());
    			historyRisk.setType(risk.getType());
    			historyRisk.setValue(risk.getValue());
    			historyRisk.setValueFormat(risk.getValueFormat());
    			historyRisk.setValueSign(risk.getValueSign());
    			historyRisk.setValueSource(risk.getValueSource());
    			historyRisk.setValueType(risk.getValueType());
    			historyRisk.setValueUnit(risk.getValueUnit());
    			historyRiskDao.save(historyRisk);				
    		}
    	}.start();
    }
    
    /**
     * 保存历史
     * @param risk
     */
    void saveHistoryRisk(final Risk risk, final RiskRule rule) {
        new Thread() {
            public void run() {
                HistoryRisk historyRisk = new HistoryRisk();
                historyRisk.setCreationTime(risk.getCreationTime());
                historyRisk.setCreator(risk.getCreator());
                historyRisk.setTaskCode("");
                historyRisk.setDutyerID(risk.getDutyerID());
                historyRisk.setDutyerName(risk.getDutyerName());
                historyRisk.setDutyerType(risk.getDutyerType());
                historyRisk.setEntityName("");
                historyRisk.setGrade(risk.getGrade());
                
                historyRisk.setCategory(risk.getCategory());
                historyRisk.setThingCode(risk.getThingCode());
                historyRisk.setThingName(risk.getThingName());
                
                historyRisk.setJuralLimit(risk.getJuralLimit());
                historyRisk.setConclusionTpl1(rule.getConclusionTpl1());
                historyRisk.setConclusionTpl2(rule.getConclusionTpl2());
                historyRisk.setConclusionTpl3(rule.getConclusionTpl3());
                historyRisk.setConclusionTpl4(rule.getConclusionTpl4());
                historyRisk.setConclusionTpl5(rule.getConclusionTpl5());
                historyRisk.setConclusionTpl6(rule.getConclusionTpl6());
                historyRisk.setConclusion(risk.getConclusion());
                
                historyRisk.setModificationTime(risk.getModificationTime());
                historyRisk.setModifier(risk.getModifier());
                historyRisk.setName(risk.getName());
                historyRisk.setObjectAttr(risk.getObjectAttr());
                historyRisk.setObjectID(risk.getObjectID());
                historyRisk.setObjectType(risk.getObjectType());
                historyRisk.setPause(risk.getPause());
                historyRisk.setRefID(risk.getRefID());
                historyRisk.setRefObjectId(risk.getRefObjectId());
                historyRisk.setRefObjectType(risk.getRefObjectType());
                historyRisk.setRefTaskTypeName(risk.getRefTaskTypeName());
                historyRisk.setRefType(risk.getRefType());
                historyRisk.setRemark(risk.getRemark());
                historyRisk.setRuleID(risk.getRuleID());
                historyRisk.setScaleMark(risk.getScaleMark());
                historyRisk.setScaleValue(risk.getScaleValue());
                historyRisk.setScaleValue1(risk.getScaleValue1());
                historyRisk.setScaleValue2(risk.getScaleValue2());
                historyRisk.setScaleValue3(risk.getScaleValue3());
                historyRisk.setScaleValue4(risk.getScaleValue4());
                historyRisk.setScaleValue5(risk.getScaleValue5());
                historyRisk.setScaleValue6(risk.getScaleValue6());
                historyRisk.setStatus(risk.getStatus());
                historyRisk.setSystemID(risk.getSystemID());
                historyRisk.setTaskBeginTime(null);
                historyRisk.setTaskEndTime(null);
                historyRisk.setTaskName("");
                historyRisk.setTaskSn(0L);
                historyRisk.setTaskStatus((byte) 0);
                historyRisk.setTaskStatusName("");
                historyRisk.setType(risk.getType());
                historyRisk.setValue(risk.getValue());
                historyRisk.setValueFormat(risk.getValueFormat());
                historyRisk.setValueSign(risk.getValueSign());
                historyRisk.setValueSource(risk.getValueSource());
                historyRisk.setValueType(risk.getValueType());
                historyRisk.setValueUnit(risk.getValueUnit());
                historyRiskDao.save(historyRisk);               
            }
        }.start();
    }
}
