package cn.redflagsoft.base.service.impl;

import static cn.redflagsoft.base.service.impl.RiskHelper.copyDutyerFromObjectOrgClerk;
import static cn.redflagsoft.base.service.impl.RiskHelper.copyDutyerFromRFSObject;
import static cn.redflagsoft.base.service.impl.RiskHelper.copyDutyerFromRiskRule;
import static cn.redflagsoft.base.service.impl.RiskHelper.copyDutyerFromTask;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.ndao.ObjectNotFoundException;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.redflagsoft.base.NotFoundException;
import cn.redflagsoft.base.aop.annotation.BizLog;
import cn.redflagsoft.base.bean.BizSummaryProvider;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.DutyersInfo;
import cn.redflagsoft.base.bean.EventMsg;
import cn.redflagsoft.base.bean.ExDataRisk;
import cn.redflagsoft.base.bean.Matter;
import cn.redflagsoft.base.bean.ObjectOrgClerk;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskEntry;
import cn.redflagsoft.base.bean.RiskLog;
import cn.redflagsoft.base.bean.RiskMonitorable;
import cn.redflagsoft.base.bean.RiskObject;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.RiskTree;
import cn.redflagsoft.base.bean.RiskValue;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bizlog.BizLogContext;
import cn.redflagsoft.base.codegenerator.CodeGeneratorProvider;
import cn.redflagsoft.base.dao.EntityGroupDao;
import cn.redflagsoft.base.dao.ExDataRiskDao;
import cn.redflagsoft.base.dao.GlossaryDao;
import cn.redflagsoft.base.dao.MatterDao;
import cn.redflagsoft.base.dao.RiskDao;
import cn.redflagsoft.base.dao.RiskRuleDao;
import cn.redflagsoft.base.dao.TaskDao;
import cn.redflagsoft.base.event.RiskEvent;
import cn.redflagsoft.base.event.RiskLogEvent;
import cn.redflagsoft.base.security.SupervisorUser;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.EntityObjectFactory;
import cn.redflagsoft.base.service.EntityObjectLoader;
import cn.redflagsoft.base.service.EventMsgService;
import cn.redflagsoft.base.service.ObjectFinder;
import cn.redflagsoft.base.service.ObjectHandler;
import cn.redflagsoft.base.service.ObjectOrgClerkService;
import cn.redflagsoft.base.service.RiskCalculator;
import cn.redflagsoft.base.service.RiskChecker;
import cn.redflagsoft.base.service.RiskGradeChangeHandler;
import cn.redflagsoft.base.service.RiskHandler;
import cn.redflagsoft.base.service.RiskLogService;
import cn.redflagsoft.base.service.RiskService;
import cn.redflagsoft.base.util.BatchHelper;
import cn.redflagsoft.base.util.BigDecimalUtils;
import cn.redflagsoft.base.util.DateUtil;
import cn.redflagsoft.base.util.EqualsUtils;

import com.google.common.collect.Maps;

/**
 * 其中关于RiskMonitable的操作，都只处理Risk信息，可能改变了RiskMonitable对象，但并不持久化
 * 该被监控对象，使用者需要手动调用更新等方法来持久化这种修改。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class RiskServiceImpl implements RiskService, ApplicationContextAware, 
	EntityObjectFactory<RiskObject>,ObjectFinder<Risk>, RiskChecker {
	
	/**
	 * 运行时属性：
	 * “calculateAllRunningRisks.optimized=true”。
	 * 
	 * <pre>
	 * 说明：在计算所有运行状态的Risk时，如果使用优化算法，如果上次运行时间和本次运行时间是同一天，
	 * 则不再运行计算。不使用优化时，每次都计算。
	 * </pre>
	 */
	public static final String CALCULATE_ALL_RUNNING_RISKS_OPTIMIZED_KEY = "calculateAllRunningRisks.optimized";
	
	public static final String CALCULATE_ALL_RUNNING_RISKS_THREADS_KEY = "calculateAllRunningRisks.nThreads";
	

	public static final Log log = LogFactory.getLog(RiskServiceImpl.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	//private static final byte RISK_CATEGORY = 0;
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
	private RiskDao riskDao;
	private RiskRuleDao riskRuleDao;
//	private ObjectService<RFSObject> objectService;
	private GlossaryDao glossaryDao;
	//private RiskGradeChangeHandler riskGradeChangeHandler;
	private EntityGroupDao entityGroupDao;
	private MatterDao matterDao;
	private Collection<RiskGradeChangeHandler> riskGradeChangeHandlers;
	private ApplicationContext applicationContext;
    private RiskLogService riskLogService;
    private ExDataRiskDao exDataRiskDao;
    private EventMsgService eventMsgService;
    private EntityObjectLoader entityObjectLoader;
    private TaskDao taskDao;
    private CodeGeneratorProvider codeGeneratorProvider;
    private RiskCalculator riskCalculator;
    private ClerkService clerkService;
    private ObjectOrgClerkService objectOrgClerkService;
    //private BizLogService bizLogService;
    
    private Date lastCalculateTime;
    
    private Collection<RiskHandler> riskHandlers;
    
//	/**
//	 * @param bizLogService the bizLogService to set
//	 */
//	public void setBizLogService(BizLogService bizLogService) {
//		this.bizLogService = bizLogService;
//	}
	/**
	 * @param objectOrgClerkService the objectOrgClerkService to set
	 */
	public void setObjectOrgClerkService(ObjectOrgClerkService objectOrgClerkService) {
		this.objectOrgClerkService = objectOrgClerkService;
	}
	/**
	 * @return the clerkService
	 */
	public ClerkService getClerkService() {
		return clerkService;
	}
	/**
	 * @param clerkService the clerkService to set
	 */
	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}
	/**
	 * @param riskCalculator the riskCalculator to set
	 */
	public void setRiskCalculator(RiskCalculator riskCalculator) {
		this.riskCalculator = riskCalculator;
	}
	/**
	 * @return the codeGeneratorProvider
	 */
	public CodeGeneratorProvider getCodeGeneratorProvider() {
		return codeGeneratorProvider;
	}
	/**
	 * @param codeGeneratorProvider the codeGeneratorProvider to set
	 */
	public void setCodeGeneratorProvider(CodeGeneratorProvider codeGeneratorProvider) {
		this.codeGeneratorProvider = codeGeneratorProvider;
	}
	/**
	 * @param taskDao the taskDao to set
	 */
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public EventMsgService getEventMsgService() {
		return eventMsgService;
	}

	public void setEventMsgService(EventMsgService eventMsgService) {
		this.eventMsgService = eventMsgService;
	}

	public ExDataRiskDao getExDataRiskDao() {
		return exDataRiskDao;
	}

	public void setExDataRiskDao(ExDataRiskDao exDataRiskDao) {
		this.exDataRiskDao = exDataRiskDao;
	}

	public RiskLogService getRiskLogService() {
		return riskLogService;
	}

	public void setRiskLogService(RiskLogService riskLogService) {
		this.riskLogService = riskLogService;
	}

	public void setEntityGroupDao(EntityGroupDao entityGroupDao) {
		this.entityGroupDao = entityGroupDao;
	}

	public void setRiskDao(RiskDao riskDao) {
		this.riskDao = riskDao;
	}

	public List<Object[]> findRiskByType(int objectType) {
		return riskDao.findRiskByType(objectType);
	}

	public RiskRuleDao getRiskRuleDao() {
		return riskRuleDao;
	}

	public void setRiskRuleDao(RiskRuleDao riskRuleDao) {
		this.riskRuleDao = riskRuleDao;
	}

//	public ObjectService<RFSObject> getObjectService() {
//		return objectService;
//	}
//
//	public void setObjectService(ObjectService<RFSObject> objectService) {
//		this.objectService = objectService;
//	}

	public RiskDao getRiskDao() {
		return riskDao;
	}

	public GlossaryDao getGlossaryDao() {
		return glossaryDao;
	}

	public void setGlossaryDao(GlossaryDao glossaryDao) {
		this.glossaryDao = glossaryDao;
	}

//	public RiskGradeChangeHandler getRiskGradeChangeHandler() {
//		return riskGradeChangeHandler;
//	}
//
//	public void setRiskGradeChangeHandler(
//			RiskGradeChangeHandler riskGradeChangeHandler) {
//		this.riskGradeChangeHandler = riskGradeChangeHandler;
//	}

	public void setMatterDao(MatterDao matterDao) {
		this.matterDao = matterDao;
	}

	public List<RiskEntry> createRisksForRiskMonitorable(RiskMonitorable riskMonitorable, Long dutyerId) {
		List<RiskRule> riskRules = riskRuleDao.findRiskRules(riskMonitorable.getObjectType());
		List<RiskEntry> riskEntrys = new ArrayList<RiskEntry>();
		Risk risk;
		RFSObject object = null;
		for(RiskRule riskRule : riskRules) {
			if(riskMonitorable instanceof RFSObject) {
				object = (RFSObject)riskMonitorable;
			}
			risk = transferRisk(riskMonitorable, dutyerId, riskRule, object, null);
			riskEntrys.add(new RiskEntry(risk.getId(), risk.getObjectAttr(), risk.getValueSource()));
		}
		riskMonitorable.setRiskEntries(riskEntrys);
		return riskEntrys;
	}
	
	//FIXME:标记，Task中调用
	public List<RiskEntry> createRisk(RiskMonitorable riskMonitorable, int refType, Long dutyerId, RFSObject object) {
		//根据被监控对象的类型和相关类型查询配置的风险规则
		List<RiskRule> riskRules = riskRuleDao.findRiskRules(riskMonitorable.getObjectType(), refType);
		if(IS_DEBUG_ENABLED){
			log.debug("objectType, type = " + riskMonitorable.getObjectType() + ", " + refType + ":::" + riskRules);
		}
		
		List<RiskEntry> riskEntrys = new ArrayList<RiskEntry>();
		for(RiskRule riskRule : riskRules) {
			Risk risk = transferRisk(riskMonitorable, dutyerId, riskRule, object, null);
			riskEntrys.add(new RiskEntry(risk.getId(), risk.getObjectAttr(), risk.getValueSource()));
		}
		riskMonitorable.setRiskEntries(riskEntrys);
		return riskEntrys;
	}
	
	public List<RiskEntry> createRisk(RiskMonitorable riskMonitorable, String objectAttr, int refType, Long dutyerId) {
		List<RiskRule> riskRules = riskRuleDao.findRiskRules(riskMonitorable.getObjectType(), objectAttr, refType);
		List<RiskEntry> riskEntrys = new ArrayList<RiskEntry>();
		Risk risk;
		RFSObject object = null;
		for(RiskRule riskRule : riskRules) {
			if(riskMonitorable instanceof RFSObject) {
				object = (RFSObject)riskMonitorable;
			}
			risk = transferRisk(riskMonitorable, dutyerId, riskRule, object, null);
			RiskEntry entry = new RiskEntry(risk.getId(), risk.getObjectAttr(), risk.getValueSource());
			riskEntrys.add(entry);	
			riskMonitorable.addRiskEntry(entry);
		}
		return riskEntrys;
	}
	
	/**
	 * WorkScheme 手动创建风险方法
	 */
	public List<RiskEntry> createRisk(RiskMonitorable riskMonitorable, String objectAttr, Long dutyerId, BigDecimal scaleValue) {
		List<RiskRule> riskRules = riskRuleDao.findRiskRules(riskMonitorable.getObjectType(), objectAttr);
		List<RiskEntry> riskEntrys = new ArrayList<RiskEntry>();
		Risk risk;
		RFSObject object = null;
		for(RiskRule riskRule : riskRules) {
			if(riskMonitorable instanceof RFSObject) {
				object = (RFSObject)riskMonitorable;
			}
			risk = transferRisk(riskMonitorable, dutyerId, riskRule, object, scaleValue);
			riskEntrys.add(new RiskEntry(risk.getId(), risk.getObjectAttr(), risk.getValueSource()));
		}
		return riskEntrys;
	}
	
//	/**
//	 * 创建监察对象。此方法可以手动调用，为指定的规则创建监察对象。
//	 * 
//	 * @param riskMonitorable 被监控对象，不能为空
//	 * @param dutyerId 责任主体ID，可以为空
//	 * @param riskRule 监察规则，不能为空
//	 * @param object 相关的主对象，可以为空
//	 * @param scaleValue 标度值，可以为空
//	 * @return 风险监控项集合，一般返回给被监控对象进行保存
//	 */
//	public List<RiskEntry> createRisk(RiskMonitorable riskMonitorable, Long dutyerId, RiskRule riskRule, RFSObject object, BigDecimal scaleValue){
//		Risk risk = transferRisk(riskMonitorable, dutyerId, riskRule, object, scaleValue);
//		List<RiskEntry> riskEntrys = new ArrayList<RiskEntry>();
//		riskEntrys.add(new RiskEntry(risk.getId(), risk.getObjectAttr(), risk.getValueSource()));
//		return riskEntrys;
//	}
	
	/**
	 * 
	 *  创建监察对象。此方法可以手动调用，为指定的规则创建监察对象。
	 * 
	 * @param riskMonitorable 被监控对象，不能为空
	 * @param dutyerId 责任主体ID，可以为空
	 * @param riskRule 监察规则，不能为空
	 * @param object 相关的主对象，可以为空
	 * @param scaleValue 标度值，可以为空
	 * @param code 编码，可以为空
	 * @param beginTime 开始时间，可以为空
	 * @param remark 备注，可以为空
	 * @return 风险监控项,一般返回给被监控对象进行保存
	 */
	public RiskEntry createRisk(RiskMonitorable riskMonitorable, Long dutyerId, RiskRule riskRule, 
			RFSObject object, BigDecimal scaleValue, String code, Date beginTime, String remark){
		Risk risk = transferRisk(riskMonitorable, dutyerId, riskRule, object, scaleValue, code, beginTime, remark);
		return new RiskEntry(risk.getId(), risk.getObjectAttr(), risk.getValueSource());
	}

	/**
	 * 根据被监控对象和规则创建监察信息。
	 * 
	 * @param riskMonitorable 被监控对象，不能为空
	 * @param dutyerId 责任主体ID，可以为空
	 * @param riskRule 监察规则，不能为空
	 * @param object 相关的主对象，可以为空
	 * @param scaleValue 标度值，可以为空
	 * @return 监察对象
	 */
	private Risk transferRisk(RiskMonitorable riskMonitorable, Long dutyerId, RiskRule riskRule, 
			RFSObject object, BigDecimal scaleValue){
		return transferRisk(riskMonitorable, dutyerId, riskRule, object, scaleValue, null, null, null);
	}
	/**
	 * 根据被监控对象和规则创建监察信息。
	 * 
	 *  @param riskMonitorable 被监控对象，不能为空
	 * @param dutyerId 责任主体ID，可以为空
	 * @param riskRule 监察规则，不能为空
	 * @param object 相关的主对象，可以为空
	 * @param scaleValue 标度值，可以为空
	 * @param code 编码，可以为空
	 * @param beginTime 开始时间，可以为空
	 * @param remark 备注，可以为空
	 * @return 监察对象
	 */
	//@BizLog(taskType=9999, workType=8888)
	private Risk transferRisk(RiskMonitorable riskMonitorable, Long dutyerId, RiskRule riskRule, 
			RFSObject object, BigDecimal scaleValue, String code, Date beginTime, String remark) {
		Assert.notNull(riskMonitorable);
		Assert.notNull(riskRule);

		//if(StringUtils.isBlank(code)){
		//	code = codeGeneratorProvider.generateCode(Risk.class, RISK_CATEGORY, riskRule.getType());
		//}
		
		Risk risk = new Risk();
		//risk.setCode(code != null ? code : riskRule.getCode());
		risk.setObjectID(riskMonitorable.getId());
		risk.setObjectType(riskRule.getObjectType());
		risk.setObjectAttr(riskRule.getObjectAttr());
		risk.setObjectAttrName(riskRule.getObjectAttrName());
		risk.setSuperviseOrgId(riskRule.getSuperviseOrgId());
		risk.setSuperviseOrgAbbr(riskRule.getSuperviseOrgAbbr());
		risk.setCategory(riskRule.getCategory());
		risk.setName(riskRule.getName());
		risk.setType(riskRule.getType());
		risk.setRuleID(riskRule.getId());
		risk.setRuleCode(riskRule.getCode());
		risk.setRuleName(riskRule.getName());
		risk.setRuleType(riskRule.getRuleType());
		risk.setDutyerID(dutyerId);
		risk.setDutyerType(riskRule.getDutyerType());
		//监察规则
		risk.setRuleSummary(riskRule.getRuleSummary());
		short dutyerType = riskRule.getDutyerType();
		//责任人信息抄自有关地方，按dutyer_type的定义，0来自监察规则；2来自object；3来自ref_object。
		if(dutyerType == RiskRule.DUTYER_TYPE_FROM_RISK_RULE){
			copyDutyerFromRiskRule(risk, riskRule);
		}else if(dutyerType == RiskRule.DUTYER_TYPE_FROM_MONITORABLE_OBJECT){
			if(riskMonitorable instanceof Task){
				copyDutyerFromTask(risk, (Task)riskMonitorable);
			}else if(riskMonitorable instanceof RFSObject){
				copyDutyerFromRFSObject(risk, (RFSObject)riskMonitorable);
			}else if(riskMonitorable instanceof DutyersInfo){
				RiskHelper.copyDutyerFromDutyersInfo(risk, (DutyersInfo) riskMonitorable);
			}
		}else if(dutyerType == RiskRule.DUTYER_TYPE_FROM_REF_OBJECT){
			copyDutyerFromRFSObject(risk, object);
		}else if(dutyerType == RiskRule.DUTYER_TYPE_FROM_MONITORABLE_OBJECT_ORG_CLERK){
			//注意，riskrule的dutyerID即对象单位人员关联关系表中的关系类型
			int objectOrgClerkType = riskRule.getDutyerId().intValue();
			
			int objectType = riskMonitorable.getObjectType();
			long objectId = riskMonitorable.getId();
			ObjectOrgClerk lastObjectOrgClerk = objectOrgClerkService.getLastObjectOrgClerk(objectOrgClerkType, objectType, objectId);
			copyDutyerFromObjectOrgClerk(risk, lastObjectOrgClerk);
		}else if(dutyerType == RiskRule.DUTYER_TYPE_FROM_REF_OBJECT_ORG_CLERK){
			//注意，riskrule的dutyerID即对象单位人员关联关系表中的关系类型
			int objectOrgClerkType = riskRule.getDutyerId().intValue();
			
			int objectType = object.getObjectType();
			long objectId = object.getId();
			ObjectOrgClerk lastObjectOrgClerk = objectOrgClerkService.getLastObjectOrgClerk(objectOrgClerkType, objectType, objectId);
			copyDutyerFromObjectOrgClerk(risk, lastObjectOrgClerk);
		}else{
			//log.warn("...");
			throw new IllegalStateException("无法处理Risk的责任人信息，RiskRule(id=" 
					+ riskRule.getId() + ")定义的的责任人类型未知：" + dutyerType);
		}
		
		//设置责任人的电话
		if(risk.getDutyerLeaderId() != null){
			Clerk clerk = clerkService.getClerk(risk.getDutyerLeaderId());
			if(clerk != null){
				risk.setDutyerLeaderMobNo(clerk.getMobNo());
			}
		}
		if(risk.getDutyerManagerId() != null){
			Clerk clerk = clerkService.getClerk(risk.getDutyerManagerId());
			if(clerk != null){
				risk.setDutyerManagerMobNo(clerk.getMobNo());
			}
		}
		if(risk.getDutyerID() != null){
			Clerk clerk = clerkService.getClerk(risk.getDutyerID());
			if(clerk != null){
				risk.setDutyerMobNo(clerk.getMobNo());
			}
		}
		
		if(riskRule.getInitValue()!=null){
			risk.setInitValue(riskRule.getInitValue());
			risk.setValue(riskRule.getInitValue());
		}
		
		risk.setValueSign(riskRule.getValueSign());
		risk.setValueType(riskRule.getValueType());
		risk.setValueUnit(riskRule.getValueUnit());
		risk.setValueFormat(riskRule.getValueFormat());
		risk.setValueSource(riskRule.getValueSource());
		risk.setScaleValue(scaleValue == null ? riskRule.getScaleValue() : scaleValue);
		risk.setScaleValue1(riskRule.getScaleValue1());
		risk.setScaleValue2(riskRule.getScaleValue2());
		risk.setScaleValue3(riskRule.getScaleValue3());
		risk.setScaleValue4(riskRule.getScaleValue4());
		risk.setScaleValue5(riskRule.getScaleValue5());
		risk.setScaleValue6(riskRule.getScaleValue6());
		risk.setScaleMark(riskRule.getScaleMark());
		//refType用于查询RiskRule对象，并复制到Risk中
		risk.setRefType(riskRule.getRefType());
		//risk.setStatus(riskRule.getStatus());
		
//		Date date = new Date();
//		risk.setCreationTime(date);
//		risk.setModificationTime(date);
		
		risk.setJuralLimit(riskRule.getJuralLimit());
//		risk.setConclusionTpl1(riskRule.getConclusionTpl1());
//        risk.setConclusionTpl2(riskRule.getConclusionTpl2());
//        risk.setConclusionTpl3(riskRule.getConclusionTpl3());
//        risk.setConclusionTpl4(riskRule.getConclusionTpl4());
//        risk.setConclusionTpl5(riskRule.getConclusionTpl5());
//        risk.setConclusionTpl6(riskRule.getConclusionTpl6());
		
		
		if(riskMonitorable instanceof Task){
			Task task = (Task)riskMonitorable;
			risk.setObjectCode(task.getCode());
			risk.setObjectName(task.getName());
			risk.setRefTaskTypeName(task.getName());
			risk.setBeginTime(task.getBeginTime());
		}else if(riskMonitorable instanceof RFSObject){
			RFSObject o = (RFSObject)riskMonitorable;
			risk.setObjectName(o.getName());
//			risk.setRefTaskTypeName(o.getActiveTaskSN()对应的task的name);
			if(o.getActiveTaskSN() != null && o.getActiveTaskSN().longValue() != 0){
				Task task = taskDao.get(o.getActiveTaskSN());
				if(task != null){
					risk.setRefTaskTypeName(task.getName());
				}
			}
			
			try {
				risk.setObjectCode((String) PropertyUtils.getProperty(o, "code"));
			} catch (Exception e) {
				log.debug(e.getMessage());
				log.warn("无法获取被监察对象的Code，使用其ID作为Code");
				risk.setObjectCode(riskMonitorable.getId() + "");
			} 
		}
		
		//设置ref Object
		if(object != null) {
			risk.setRefObjectName(object.getName());
			risk.setRefObjectType(object.getObjectType());
			risk.setRefObjectId(object.getId());
		    risk.setThingCode(object.getId() + "-" + riskRule.getId());
		    risk.setThingName(object.getName()+ riskRule.getName());
		    
		    try {
				risk.setRefObjectSn((String) PropertyUtils.getProperty(object, "sn"));
			} catch (Exception e) {
				log.debug(e.getMessage());
			} 
		} else {
			log.warn("transferRisk 时 RFSObject = null，setRefObjectType 与 setRefObjectId 时失败 ... ");
		}
		
		//since 2.1.x-20110922
		//risk.setObjectAttrName(riskRule.getObjectAttrName());
		risk.setMessageConfig1(riskRule.getMessageConfig1());
		risk.setMessageConfig2(riskRule.getMessageConfig2());
		risk.setMessageConfig3(riskRule.getMessageConfig3());
		risk.setMessageConfig4(riskRule.getMessageConfig4());
		risk.setMessageConfig5(riskRule.getMessageConfig5());
		risk.setMessageConfig6(riskRule.getMessageConfig6());
		
//		risk.setMessageTemplate1(riskRule.getMessageTemplate1());
//		risk.setMessageTemplate2(riskRule.getMessageTemplate2());
//		risk.setMessageTemplate3(riskRule.getMessageTemplate3());
//		risk.setMessageTemplate4(riskRule.getMessageTemplate4());
//		risk.setMessageTemplate5(riskRule.getMessageTemplate5());
//		risk.setMessageTemplate6(riskRule.getMessageTemplate6());
		risk.setConclusion("正常");
		//指定的beginTime优先级高，可以覆盖前面从task中传入的值
		if(beginTime != null){
			risk.setBeginTime(beginTime);
		}
		if(risk.getBeginTime() == null){
			risk.setBeginTime(new Date());
		}
		risk.setRemark(remark);
		risk.setStatus(Risk.STATUS_ON_USE);
		risk.setSystemID(riskRule.getSystemId());
		
		//2012-02-24
		risk.setItemID(riskRule.getItemID());
		risk.setItemName(riskRule.getItemName());
		
		if(StringUtils.isBlank(code)){
			RiskObject riskObject = new RiskObject(risk);
			code = codeGeneratorProvider.generateCode(riskObject);
		}
		risk.setCode(code != null ? code : riskRule.getCode());
		
		
		//保存之前
		beforeSaveRisk(risk, riskMonitorable, riskRule, object, scaleValue);
		//保存
		risk = riskDao.save(risk);
		//保存之后
		afterSaveRisk(risk, riskMonitorable, riskRule, object, scaleValue);
	
		saveCreateRiskBizLog(new RiskObject(risk));
		return risk;
	}
	
	
	/**
	 * 
	 * @param riskMonitorable
	 * @param risk
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void handlerAttr(RiskMonitorable riskMonitorable, Risk risk) {
		String objectAttr = risk.getObjectAttr();
		try {
			if(objectAttr.toUpperCase().equals("TIMELIMIT")) {
				PropertyUtils.setProperty(riskMonitorable, "timeUnit", risk.getValueUnit());
				PropertyUtils.setProperty(riskMonitorable, "timeLimit", risk.getScaleValue3());
			} else if(objectAttr.toUpperCase().equals("HANGLIMIT")) {
				PropertyUtils.setProperty(riskMonitorable, "timeUnit", risk.getValueUnit());
				PropertyUtils.setProperty(riskMonitorable, "hangLimit", risk.getScaleValue3());
			} else if(objectAttr.toUpperCase().equals("DELAYLIMIT")) {
				PropertyUtils.setProperty(riskMonitorable, "timeUnit", risk.getValueUnit());
				PropertyUtils.setProperty(riskMonitorable, "delayLimit", risk.getScaleValue3());
			} else if(objectAttr.toUpperCase().equals("HANGTIMES")) {
				PropertyUtils.setProperty(riskMonitorable, "hangTimes", risk.getScaleValue3());
			} else if(objectAttr.toUpperCase().equals("DELAYTIMES")) {
				PropertyUtils.setProperty(riskMonitorable, "delayTimes", risk.getScaleValue3());
			}
		} catch(Exception e) {
			log.error("PropertyUtils 获取接口引用实例属性错误 ..." + e.getMessage());
			e.printStackTrace();
		}
	}

	public void hangRisk(Long riskId, Date hangTime) {
		Risk risk = riskDao.get(riskId);
		hangRisk(risk, hangTime);
	}
	public void hangRisk(Risk risk, Date hangTime){
		if(risk != null) {
			if(hangTime == null){
				hangTime = new Date();
			}
			
			//重新计算Risk并保存修改
			risk.setValueCalculateTime(hangTime);
			calculateAndUpdateRiskValue(risk);
			
//			risk.checkGrade(null);
			risk.setPause(Risk.PAUSE_YES);
			risk.setHangTime(hangTime/* != null ? hangTime : new Date()*/);
			risk.setStatus(Risk.STATUS_UN_USE);
			
			risk = riskDao.update(risk);
			sendRiskLogEvent(risk, null, RiskLogEvent.HANG);
			//
			saveHangRiskBizLog(new RiskObject(risk));
		}else {
			log.warn("hangRisk 时没有找到 Risk 实例");
		}
	}

	public void wakeRisk(Long riskId, Date wakeTime) {
		Risk risk = riskDao.get(riskId);
		wakeRisk(risk, wakeTime);
	}
	public void wakeRisk(Risk risk, Date wakeTime){
		if(risk != null) {
			Date time = wakeTime != null ? wakeTime : new Date();
		    //XXX:wakeRisk()时重设MTime是否合适？
			risk.setModificationTime(time);
			risk.setLastRunTime(time);
			risk.setValueChangedTime(time);
			risk.setPause(Risk.PAUSE_NO);
			risk.setWakeTime(time);
			risk.setStatus(Risk.STATUS_ON_USE);
			riskDao.update(risk);
			sendRiskLogEvent(risk, null, RiskLogEvent.WAKE);
			
			saveWakeRiskBizLog(new RiskObject(risk));
		}else {
			log.warn("hangRisk 时没有找到 Risk 实例");
		}
	}

	public void terminateRisk(Long riskId, Date endTime) {
		Risk risk = riskDao.get(riskId);
		terminateRisk(risk, endTime);
	}
	public void terminateRisk(Risk risk, Date endTime){
		terminateRisk(risk, null, endTime);
	}
	public void terminateRisk(Risk risk, Task task, Date endTime){
		if(risk != null) {
			risk.setStatus(/*(byte)2*/ Risk.STATUS_FINISH);
			risk.setEndTime(endTime != null ? endTime : new Date());
			riskDao.update(risk);
			sendRiskLogEvent(risk, task, RiskLogEvent.TERMINATE);
			
			saveTerminateRiskBizLog(new RiskObject(risk));
		}else {
			log.warn("terminateRisk 时没有找到 Risk 实例");
		}
	}

	/*
	public void checkRisk(Long riskId, BigDecimal value) {
        if(log.isInfoEnabled()){
            log.info("[RiskServiceImpl.checkRisk()] riskId:"+riskId);
        }
        
		Risk risk = riskDao.get(riskId);
		if(risk != null) {
			calculateRiskGrade(risk, value, true);
//			risk.checkGrade(value);
//			riskDao.update(risk);
//			risk.setValue(value);
//			risk.checkGrade();
		}else {
			log.warn("checkRisk 时没有找到 Risk 实例 riskId = " + riskId);
		}	
	}*/

	public void updateRiskValue(Long riskId, BigDecimal value) {
        if(log.isInfoEnabled()){
            log.info("[RiskServiceImpl.updateRiskValue()] riskId:"+ riskId);
        }
//		checkRisk(riskId, value);
		Risk risk = riskDao.get(riskId);
		updateRiskValue(risk, value, null);
	}

	/**
	 * 更新ScaleValue值（目前主要用于百分比监察的值更新）
	 * @param riskId
	 * @param value
	 */
   public void updateRiskScaleValue(Long riskId, BigDecimal scaleValue) {
        if(log.isInfoEnabled()){
            log.info("[RiskServiceImpl.updateRiskScaleValue()] riskId:"+riskId);
        }

        Risk risk = riskDao.get(riskId);
        updateRiskScaleValue(risk, scaleValue);
        
//        if(risk != null) {
//            if((risk.getScaleMark()&64)!=0){//百分比
//                risk.setScaleValue(scaleValue);
//                risk.checkGrade(null);
//                riskDao.save(risk);
//            }else{
//                //scaleValue实际只对百分比计算有意义，本处只做兼容性处理。
//                risk.setScaleValue(scaleValue);
//                risk.checkGrade(null);
//                riskDao.save(risk);
//            }
//        }else {
//            log.warn("updateRiskScaleValue()时没有找到 Risk 实例 riskId = " + riskId);
//        }   
    }
	   
   /**
    * 更新ScaleValue值（目前主要用于百分比监察的值更新）
    * @param risk
    * @param value
    */
  public void updateRiskScaleValue(Risk risk, BigDecimal scaleValue) {
       if(log.isInfoEnabled()){
           log.info("[RiskServiceImpl.updateRiskScaleValue()] risk:"+risk);
       }

       if(risk != null) {
//           if((risk.getScaleMark()&64)!=0){//百分比
//               risk.setScaleValue(scaleValue);
//               risk.checkGrade(null);
//               riskDao.save(risk);
//           }else{
//               //scaleValue实际只对百分比计算有意义，本处只做兼容性处理。
//               risk.setScaleValue(scaleValue);
//               risk.checkGrade(null);
//               riskDao.save(risk);
//           }
    	   BigDecimal oldScaleValue = risk.getScaleValue();
    	   risk.setScaleValue(scaleValue);
    	   boolean updated = calculateAndUpdateRiskGrade(risk);
			if(!updated){
				riskDao.update(risk);
			}
			saveScaleValueChangedBizLog(new RiskObject(risk), scaleValue, oldScaleValue);
       }else {
           log.warn("updateRiskScaleValue()时没有找到 Risk 实例 risk = " + risk);
       }   
   }

  
	public void updateRiskDutyer(Long riskId, Long dutyerId) {
		Risk risk = riskDao.get(riskId);
		if(risk != null) {
			risk.setDutyerID(dutyerId);
			riskDao.update(risk);
			sendRiskLogEvent(risk,null,RiskLogEvent.DUTYER_CHANGE);
		}else {
			log.warn("updateRiskDutyer 时没有找到 Risk 实例 riskId = " + riskId);
		}
	}

	public void updateRiskScale(Long riskId, BigDecimal scaleValue, byte scaleId) {
		if(riskId != null) {
			Risk risk = riskDao.get(riskId);
			boolean isCheck = false;
			if(risk != null) {
//			    log.debug("riskId="+riskId+"    scaleValue="+scaleValue+"    scaleId="+scaleId);
				switch (scaleId) {
				case 0:
					if(risk.getScaleValue() != scaleValue) {
						risk.setScaleValue(scaleValue);						
						isCheck = true;
					}
					break;
				case 1:
					if(risk.getScaleValue1() != scaleValue) {
						risk.setScaleValue1(scaleValue);
						isCheck = true;
					}
					break;					
				case 2:
					if(risk.getScaleValue2() != scaleValue) {
						risk.setScaleValue2(scaleValue);
						isCheck = true;
					}
					break;
				case 3:
					if(risk.getScaleValue3() != scaleValue) {
						risk.setScaleValue3(scaleValue);
						isCheck = true;
					}
					break;
				case 4:
					if(risk.getScaleValue4() != scaleValue) {
						risk.setScaleValue4(scaleValue);
						isCheck = true;	
					}
					break;
				case 5:
					if(risk.getScaleValue5() != scaleValue) {
						risk.setScaleValue5(scaleValue);
						isCheck = true;
					}
					break;
				case 6:
					if(risk.getScaleValue6() != scaleValue) {
						risk.setScaleValue6(scaleValue);
						isCheck = true;
					}
					break;					
				}

//				log.debug("isCheck="+isCheck);
				
				if(isCheck) {
//					risk.checkGrade(null);
//					riskDao.update(risk);
					
					//重新算risk并保存
					boolean updated = calculateAndUpdateRiskGrade(risk);
					if(!updated){
						riskDao.update(risk);
					}
					
//					log.debug("risk.grade="+risk.getGrade()+"("+risk.getGradeExplain()+")");
					sendRiskLogEvent(risk,null,RiskLogEvent.SCALE_CHANGE);
				}
			} else {
				log.warn("updateRiskScale 时获取 RiskID 为" + riskId + "为空....");
			}
		} else {
			log.warn("updateRiskScale 时 RiskID 为空....");
		}
	}
	
	
	/**
	 * 定时执行任务。
	 * @deprecated
	 */
	public void watch_backup() {
        if(log.isInfoEnabled()){
            log.debug("[RiskServiceImpl.watch()]...");
        }
		//String hql = "from Risk where status=? and pause=? and valueSource=?";
		//return getHibernateTemplate().find(hql, new Object[]{(byte)1, (byte)0, (byte)1});
		List<Risk> risks = riskDao.findTimeRisks();		
		if(risks != null) {
			for (Risk risk : risks) {
				checkRiskGrade(risk);
			}
		} else {
			log.warn("watch 时没有找到 Risk 实例....");
		}
	}
	/**
	 * 定时执行任务。
	 * @deprecated
	 */
	public void watch(){
		calculateAllRunningRisks();
	}
	
	
	/**
	 * 计算单个Risk的最终方法之一。
	 * @deprecated
	 */
	public void checkRiskGrade(Risk risk){
		calculateAndUpdateRiskValue(risk, null);
	}
	
	
	
//	/**
//	 * 计算监察级别并根据需要更新监察对象。
//	 * 
//	 * @param risk Risk对象
//	 * @param newValue 用于计算grade的value值，不可以为空。可使用 {@link #calculateNewValue(Risk, Date)}先计算这个值。
//	 * @param forceCalculateGrade 是否强制计算risk的grade，如果修改了risk的scalevalue等不影响value值但影响grade的属性，
//	 * 			应该选择强制计算。如果值取false，则只有在value发生了变化时，才计算grade的值。
//	 * @return 计算并更新后的Risk对象
//	 */
//	protected Risk checkRiskGradeAndUpdate2(Risk risk, BigDecimal newValue, boolean forceCalculateGrade){
//		if(IS_DEBUG_ENABLED){
//			log.debug("checkRiskGradeAndUpdate2 risk grade: " + risk.getId());
//		}
//		Assert.notNull(risk, "Risk对象不能为空");
//		Assert.notNull(newValue, "用于计算的value不能为空");
//		
//		boolean valueChanged = false;
//		boolean gradeChanged  = false;
//		
//		BigDecimal oldValue = risk.getValue();
//		valueChanged = !EqualsUtils.equals(newValue, oldValue);
//		if(valueChanged){
//			risk.setValue(newValue);
//			
//			//设置被监控对象的被监控属性的值
//			if(risk.getObjectAttr() != null){
//				riskDao.updateObjectAttr(risk.getObjectID(), risk.getObjectType(), risk.getObjectAttr(), risk.getValue());
//			}
//			sendRiskLogEvent(risk, null, RiskLogEvent.RISK_VALUE_CHANGE);
//		}
//		
//		
//		//如果值有变化或者需要强制计算grade
//		if(valueChanged || forceCalculateGrade){
//			byte newGrade = riskCalculator.calculateGrade(risk);
//			byte oldGrade = risk.getGrade();
//			gradeChanged = (newGrade != oldGrade);
//			if(gradeChanged){
//				if(IS_DEBUG_ENABLED){
//					log.debug("[RiskServiceImpl.checkRiskGrade()] 风险等级有变更，执行处理：" + risk);
//				}
//				risk.setGrade(newGrade);
//				String conclusion = calculateConclusion(risk);
//				risk.setConclusion(conclusion);
//				
//				riskGradeChangeHandle(risk, oldGrade);
//				sendRiskLogEvent(risk,null,RiskLogEvent.RISK_GRADE_CHANGE);
//				dealEventMsg(risk);
//			}
//		}
//		
//		if(valueChanged || gradeChanged){
//			risk = riskDao.update(risk);
//		}
//		return risk;
//	}
	
	
	
	
	
	/**
	 * 计算单个Risk的最终方法，其它代码应该都调用这个方法。
	 * @param risk
	 * @param checkTime
	 * @deprecated using {@link #checkRiskGradeAndUpdate2(Risk, BigDecimal, Date, boolean))}
	 */
	protected Risk checkRiskGradeAndUpdate(Risk risk, BigDecimal newValue, Date checkTime){
		if(IS_DEBUG_ENABLED){
			log.debug("Checking risk grade: " + risk.getId());
		}
		
		byte oldGrade = risk.checkGrade(newValue, checkTime);
		boolean gradeChanged = oldGrade != risk.getGrade();
		
		if(risk.isValueChanged() || gradeChanged){
			if(gradeChanged){
				if(IS_DEBUG_ENABLED){
					log.debug("[RiskServiceImpl.checkRiskGrade()] 风险等级有变更，执行处理：" + risk);
				}
				calculateConclusion(risk, oldGrade);
				riskGradeChangeHandle(risk, oldGrade);
				
				sendRiskLogEvent(risk,null,RiskLogEvent.RISK_GRADE_CHANGE);
				//riskGradeChangeHandler.gradeChange(risk, oldGrade);
				
				dealEventMsg(risk);
				
//				short bizAction = 0;
//				switch(risk.getGrade()){
//					case Risk.GRADE_WHITE	:bizAction=101; break;
//					case Risk.GRADE_BLUE 	:bizAction=102; break;
//					case Risk.GRADE_YELLOW 	:bizAction=103; break;
//					case Risk.GRADE_ORANGE 	:bizAction=104; break;
//					case Risk.GRADE_RED 	:bizAction=105; break;
//					case Risk.GRADE_BLACK 	:bizAction=106; break;
//				}
////				System.out.println("===================");
////				System.out.println("risk.id="+risk.getId());
////				System.out.println("risk.reftype="+risk.getType());
////				System.out.println("risk.refObjectid="+risk.getRefObjectId());
////				System.out.println("===================");
//				eventMsgService.dealEventMsg(risk.getRefObjectType(),risk.getRefObjectId(), risk.getRefType(),risk.getObjectID(), 0, bizAction);
			}else{
				if(IS_DEBUG_ENABLED){
					log.debug("风险等级无变更：" + risk);
				}
				//FIXME: 似乎以前的版本会重复执行这个步骤
				//			}
				sendRiskLogEvent(risk, null, RiskLogEvent.RISK_VALUE_CHANGE);
			}
		}else{
			log.debug("未改变：risk.isValueChanged(): " + risk.isValueChanged() + ", gradeChage: " + gradeChanged);
		}
		
		if(IS_DEBUG_ENABLED){
			log.debug("Start update risk ");
		}
		risk = riskDao.update(risk);
		
		if(IS_DEBUG_ENABLED){
			log.debug("End update risk ");
		}
		
		return risk;
	}
	
	private void dealEventMsg(Risk risk){
		short bizAction = 0;
		switch(risk.getGrade()){
			case Risk.GRADE_WHITE	:bizAction=101; break;
			case Risk.GRADE_BLUE 	:bizAction=102; break;
			case Risk.GRADE_YELLOW 	:bizAction=103; break;
			case Risk.GRADE_ORANGE 	:bizAction=104; break;
			case Risk.GRADE_RED 	:bizAction=105; break;
			case Risk.GRADE_BLACK 	:bizAction=106; break;
		}
//		System.out.println("===================");
//		System.out.println("risk.id="+risk.getId());
//		System.out.println("risk.reftype="+risk.getType());
//		System.out.println("risk.refObjectid="+risk.getRefObjectId());
//		System.out.println("===================");
		eventMsgService.dealEventMsg(risk.getRefObjectType(),risk.getRefObjectId(), risk.getRefType(),risk.getObjectID(), 0, bizAction);
	}
	
	private String calculateConclusion(Risk risk) {
		byte grade = risk.getGrade();
		if(grade == 0){
			return "正常";
		}else if(grade >=1 && grade <= 6){
			RiskRule rule = riskRuleDao.get(risk.getRuleID());
			if(rule == null){
				throw new ObjectNotFoundException(String.format("Risk 对应的 Rule 找不到(risk.id=%s, risk.ruleID=%s)",
						risk.getId(), risk.getRuleID()));
			}
			String[] templates = {"", 
					rule.getConclusionTpl1(), rule.getConclusionTpl2(),
					rule.getConclusionTpl3(), rule.getConclusionTpl4(),
					rule.getConclusionTpl5(), rule.getConclusionTpl6()};
			return templates[grade];
		}else{
			return "";
		}
	}
	
	/**
	 * 这部分功能从原来的RiskGradeChangeHandlerImpl移过来，应该是一段针对Risk的处理，
	 * 不应在handler中处理。
	 * @see RiskGradeChangeHandlerImpl
	 * @since 2.1.0-20110926
	 * @param risk
	 * @param oldGrade
	 */
	private void calculateConclusion(Risk risk, byte oldGrade) {
		//根据grade设置监察结论（目前先不考虑变量情况）
		//RiskRuleDao有缓存
		RiskRule rule;// = riskRuleDao.get(risk.getRuleID());
        switch (risk.getGrade()) {
        case Risk.GRADE_NORMAL:
            risk.setConclusion("正常");
            break;
        case Risk.GRADE_WHITE:
        	rule = riskRuleDao.get(risk.getRuleID());
            risk.setConclusion(rule.getConclusionTpl1());
            break;
        case Risk.GRADE_BLUE:
        	rule = riskRuleDao.get(risk.getRuleID());
            risk.setConclusion(rule.getConclusionTpl2());
            break;
        case Risk.GRADE_YELLOW:
        	rule = riskRuleDao.get(risk.getRuleID());
            risk.setConclusion(rule.getConclusionTpl3());
            break;
        case Risk.GRADE_ORANGE:
        	rule = riskRuleDao.get(risk.getRuleID());
            risk.setConclusion(rule.getConclusionTpl4());
            break;
        case Risk.GRADE_RED:
        	rule = riskRuleDao.get(risk.getRuleID());
            risk.setConclusion(rule.getConclusionTpl5());
            break;
        case Risk.GRADE_BLACK:
        	rule = riskRuleDao.get(risk.getRuleID());
            risk.setConclusion(rule.getConclusionTpl6());
            break;
        default:
            risk.setConclusion("");
            break;
        }
	}

	//public void updateObjectAttr(Long objectId, int objectType, String objectAttr) {
	//	riskDao.updateObjectAttr(objectId, objectType, objectAttr, null);
	//}

	public Risk getRiskById(Long id) {
		return riskDao.get(id);
	}

	public BigDecimal getRiskScale(Long id, byte scaleId) {
		Risk risk = getRiskById(id);
		switch(scaleId) {
		
		case 1: return risk.getScaleValue1();
		case 2: return risk.getScaleValue2();
		case 3: return risk.getScaleValue3();
		case 4: return risk.getScaleValue4();
		case 5: return risk.getScaleValue5();
		case 6: return risk.getScaleValue6();
		default:
			return risk.getScaleValue();
		}
	}

	public void deleteRisk(RiskMonitorable riskMonitorable, String objectAttr) {
		RiskEntry riskEntry = riskMonitorable.getRiskEntryByObjectAttr(objectAttr);
		Risk r=riskDao.get(riskEntry.getRiskID());
		if(r!=null){
			sendRiskLogEvent(r,(Task) riskMonitorable,RiskLogEvent.TERMINATE);
		}else {
			log.warn("deleteRisk时r为null!");
		}
		riskDao.remove(riskEntry.getRiskID());
		riskMonitorable.removeRiskEntry(riskEntry);
	}

	public void deleteRisk(RiskMonitorable riskMonitorable) {
		List<RiskEntry> entries = riskMonitorable.getRiskEntries();
		if(entries == null) {
			log.warn("deleteRisk 时 RiskEntry 列表为空，操作终止 ... ");
			return;
		}
		for(RiskEntry re : entries) {
			Risk r = riskDao.get(re.getRiskID());
			if(r != null){
				if(riskMonitorable instanceof Task){
					sendRiskLogEvent(r, (Task)riskMonitorable, RiskLogEvent.TERMINATE);
				}
				riskDao.delete(r);
			}else {
				log.warn("deleteRisk时r为null!");
			}
//			riskDao.remove(re.getRiskID());
		}
		riskMonitorable.removeAllRiskEntries();
	}

	public List<RiskTree> findRiskTree(Long dutyerId) {
		List<Risk> risks = riskDao.findRiskByDutyerId(dutyerId);
		List<RiskTree> result = new ArrayList<RiskTree>();
		if(risks != null && !risks.isEmpty()) {
			//上次
			int preRefType = 0;
			Long preObjectId = null;
			//当前
			short curObjectType;
			Long curObjectId = null;
			int curRefType = 0;
			//计数器,组索引,List长度
			int count = 0;
			int index = 0;			
			int length = risks.size();
			//名称
			String name = null;
			String term = null;
			//临时变量
			Risk risk = null;
			RFSObject object = null;
			List<RiskTree> trees = new ArrayList<RiskTree>();
			for(int i = 0; i < length; i++) {
				risk = risks.get(i);
				//初始化
				if(i == 0){
					index = i;
					preRefType = risk.getRefType();
					object = entityObjectLoader.getEntityObject(risk.getRefObjectType(), risk.getRefObjectId());//objectService.getObject(risk.getRefObjectId());
					if(preRefType != 0) {
						//XXX 2011-08-21
						term = glossaryDao.getByCategoryAndCode((short) risk.getObjectType(), preRefType);
					} else {
						term = null;
					}
				}
				//保存当前
				//XXX 2011-08-21
				curObjectType = (short) risk.getObjectType();
				curObjectId = risk.getObjectID();
				curRefType = risk.getRefType();
				if(curObjectType == Task.OBJECT_TYPE) {//处理业务分组
					//累加计数器
					count++;
					//比较(本次与上一次)
					if(preRefType != curRefType) {
						//添加组
						name = object != null ? object.getName() + "(" + (count - 1) + ")" : "(" + (count - 1) + ")";
						setNameAndTerm(index, name, term, trees);
						//重新初始化
						index += (count - 1);
						count = 1;
						preRefType = curRefType;
						//object = objectService.getObject(risk.getRefObjectId());
						object = entityObjectLoader.getEntityObject(risk.getRefObjectType(), risk.getRefObjectId());
						if(preRefType != 0) {
							term = glossaryDao.getByCategoryAndCode(curObjectType, preRefType);
						} else {
							term = null;
						}
					}
					//添加组成员
					trees.add(convertToRiskTree(risk));
				}
				else if(curObjectType != Task.OBJECT_TYPE) {//处理项目分组			
					if(preRefType != curRefType) {
						//添加组
						name = object != null ? object.getName() + "(" + count + ")" : "(" + count + ")";
						setNameAndTerm(index, name, term, trees);
						preRefType = curRefType;
					}
					//初始化
					if(preObjectId == null) {
						index = 0;
						count = 0;
						preObjectId = curObjectId;
						//object = objectService.getObject(risk.getRefObjectId());
						object = entityObjectLoader.getEntityObject(risk.getRefObjectType(), risk.getRefObjectId());
						if(curRefType != 0) {
							term = glossaryDao.getByCategoryAndCode(curObjectType, curRefType);
						} else {
							term = null;
						}
					}
					//累加计数器
					count++;
					//比较(本次与上一次)
					if(!preObjectId.equals(curObjectId)) {				
						//添加组
						name = object != null ? object.getName() + "(" + (count - 1) + ")" : "(" + (count - 1) + ")";
						setNameAndTerm(index, name, term, result);
						//重新初始化
						index += (count - 1);
						count = 1;
						preObjectId = curObjectId;
						//object = objectService.getObject(risk.getRefObjectId());
						object = entityObjectLoader.getEntityObject(risk.getRefObjectType(), risk.getRefObjectId());
						if(curRefType != 0) {
							term = glossaryDao.getByCategoryAndCode(curObjectType, curRefType);
						} else {
							term = null;
						}
					}
					result.add(convertToRiskTree(risk));
				}
				if(i == length - 1) {//处理最后一列
					if(curObjectType == Task.OBJECT_TYPE) {
						index = index != 0 ? trees.size() - 1 : 0;
						count = index != 0 ? 1 : count;
						name = object != null ? object.getName() + "(" + count + ")" : "(" + count + ")";
						setNameAndTerm(index, name, term, trees);
					} else {
						name = object != null ? object.getName() + "(" + count + ")" : "(" + count + ")";
						setNameAndTerm(index, name, term, result);						
					}
				}
			}
			merge(result, trees);
		} else {
			log.warn("findRiskTree 时没有找到 dutyerId = " + dutyerId + " 的 Risk 风险 ,返回 List = empty ... ");
		}
		return result;
	}
	
	private RiskTree convertToRiskTree(Risk risk) {
		RiskTree tree = new RiskTree();
		tree.setId(risk.getId());
		tree.setObjectID(risk.getObjectID());
		tree.setType(risk.getType());
		tree.setObjectType(risk.getObjectType());
		tree.setObjectAttr(risk.getObjectAttr());
		tree.setDutyerID(risk.getDutyerID());
		tree.setDutyerType(risk.getDutyerType());
		tree.setValueSign(risk.getValueSign());
		tree.setValueType(risk.getValueType());
		tree.setValueUnit(risk.getValueUnit());
		tree.setValue(risk.getValue());
		tree.setGrade(risk.getGrade());
		tree.setValueFormat(risk.getValueFormat());
		tree.setValueSource(risk.getValueSource());
		tree.setScaleValue(risk.getScaleValue());
		tree.setScaleValue1(risk.getScaleValue1());
		tree.setScaleValue2(risk.getScaleValue2());
		tree.setScaleValue3(risk.getScaleValue3());
		tree.setScaleValue4(risk.getScaleValue4());
		tree.setScaleValue5(risk.getScaleValue5());
		tree.setScaleValue6(risk.getScaleValue6());
		tree.setScaleMark(risk.getScaleMark());
		tree.setRefType(risk.getRefType());
		tree.setStatus(risk.getStatus());
		tree.setCreationTime(risk.getCreationTime());
		tree.setModificationTime(risk.getModificationTime());	
		tree.setRefObjectType(risk.getRefObjectType());
		tree.setRefObjectId(risk.getRefObjectId());
		tree.setName(risk.getName());
		tree.setGradeExplain(getGradeExplain(tree.getGrade()));
		return tree;
	}
	
	private List<RiskTree> merge(List<RiskTree> src, List<RiskTree> item) {
		if(item != null && src != null){
			for(RiskTree rt : item) {
				src.add(rt);
			}
		}
		return src;
	}
	
	public String getGradeExplain(byte grade) {
		switch(grade){
		case 0: return "正常";
		case 1: return "提醒";
		case 2: return "预警";
		case 3: return "黄牌";
		case 4: return "橙牌";
		case 5: return "红牌";
		case 6: return "黒牌";
		default:
			log.warn("getGradeExplain 时没有找到 grade = " + grade + " 的选择,返回空字符串 ... ");
			return "";
		}
	}
	
	private void setNameAndTerm(int off, String name, String term, List<RiskTree> src) {
		if(src != null) {
			for(RiskTree  rt : src.subList(off, src.size())) {
				rt.setLabel(name);
				if(term != null){
					rt.setName(term.trim() + " - " + rt.getName());
				}
			}
		}
	}
	
	public Map<String, List<Map<Object,Object>>> findRiskByObjectId(Long objectId, int objectType) {
		if (objectId != null) {
			List<Risk> risks = riskDao.findRiskByObjectId(objectId, objectType);
			Map<String, List<Map<Object,Object>>> result = new HashMap<String, List<Map<Object,Object>>>();
			if (risks != null && !risks.isEmpty()) {
				List<Map<Object,Object>> headers = new ArrayList<Map<Object,Object>>();
				List<Map<Object,Object>> rows = new ArrayList<Map<Object,Object>>();
				Map<Object,Object> tmp = new HashMap<Object,Object>();
				tmp.put("label", "建设单位");
				tmp.put("dataFieldName", "entityName");
				headers.add(tmp);
				tmp = new HashMap<Object,Object>();
				boolean append;
				Risk risk;
				for (int i = 0; i < risks.size(); i++) {
					risk = risks.get(i);
					append = true;
					for (Map<Object,Object> header : headers) {
						if (header.containsValue(risk.getName())) {
							append = false;
							break;
						}
					}
					if (append) {
						tmp.put("label", risk.getName());
						tmp.put("dataFieldName", "name" + i);
						headers.add(tmp);
						tmp = new HashMap<Object,Object>();
					}
				}
				for (Risk element : risks) {
					append = true;
					for (Map<Object,Object> row : rows) {
						if (row.containsValue(element.getDutyerName())) {
							append = false;
							break;
						}
					}
					if (append) {
						tmp.put("dutyerID", element.getDutyerID());
						tmp.put("entityName", element.getDutyerName());
						for (int j = 1; j < headers.size(); j++) {
							tmp.put(headers.get(j).get("dataFieldName"), element.getGrade());
						}
						rows.add(tmp);
						tmp = new HashMap<Object,Object>();
					}					
				}
				result.put("headers", headers);
				result.put("rows", rows);
			} else {
				log.warn("findRiskByTaskTypesAndObjectId 时结果集为空,返回List<Map> = empty ");
			}
			return result;
		} else {
			log.warn("findRiskByObjectId 时 objectId 为空返回List<Map> = null,操作忽略 ... ");
			return null;
		}
	}
	
	/*@SuppressWarnings("unchecked")
	public Map<String, List<Map>> findRiskByObjectId(Long objectId) {
		if (objectId != null) {
			List<Map> maps = riskDao.findRiskByTaskTypesAndObjectId(objectId);
			List<Map> headers = new ArrayList<Map>();
			List<Map> rows = new ArrayList<Map>();
			Map<String, List<Map>> result = new HashMap<String, List<Map>>();
			if (maps != null && maps.size() > 0) {
				//fill headers
				Map tmp = new HashMap();
				tmp.put("label", "建设单位");
				tmp.put("dataFieldName", "entity");
				headers.add(tmp);
				tmp = new HashMap();
				boolean append;
				for (int i = 0; i < maps.size(); i++) {
					append = true;
					for (Map header : headers) {
						if (header.containsValue(maps.get(i).get("name"))) {
							append = false;
							break;
						}
					}
					if (append) {
						tmp.put("label", maps.get(i).get("name"));
						tmp.put("dataFieldName", "name" + i);
						headers.add(tmp);
						tmp = new HashMap();
					}
				}
				//fill rows
				for (Map map : maps) {
					append = true;
					for(Map row : rows) {
						if (row.containsValue(map.get("abbr"))) {
							append = false;
							break;
						}
					}
					if(append) {
						tmp.put("dutyerID", map.get("dutyerID"));
						tmp.put("entity", map.get("abbr"));
						for (int j = 1; j < headers.size(); j++) {
							tmp.put(headers.get(j).get("dataFieldName"), map.get("grade"));
						}
						rows.add(tmp);
						tmp = new HashMap();
					}
				}
				//merger result
				result.put("headers", headers);
				result.put("rows", rows);
			} else {
				log.warn("findRiskByTaskTypesAndObjectId 时结果集为空,返回List<Map> = empty ");
			}
			return result;
		} else {
			log.warn("findRiskByObjectId 时 objectId 为空返回List<Map> = null,操作忽略 ... ");
			return null;
		}
	}*/
	
	
	public List<Risk> findRiskByObjectIdAndType(Long objectId,int objectType) {
		List<Risk> risks=riskDao.findRiskByObjectId(objectId, objectType);		
		return risks;
	}

	public List<Risk> findTaskRiskByObjectIdAndTaskType(Long objectId,
			int objectType, Integer... taskTypes) {
		List<Risk> risks=riskDao.findTaskRiskByObjectIdAndTaskType(objectId, objectType, taskTypes);
		return risks;
	}
	
	public Risk querySingleRisk(Long objectId, int refObjectType) {
		return riskDao.get(Restrictions.logic(
				Restrictions.eq("objectType", refObjectType)).and(
				Restrictions.eq("objectID", objectId)));
	}

	public List<Map<String,Object>> findCollectByGroupID(Long groupId) {
		List<Map<String,Object>> resultMaps =new ArrayList<Map<String,Object>>();
		List<Object[]> os=riskDao.findMatterCollect();
		List<Org> orgs=entityGroupDao.findOrgsInGroup(groupId);	
		List<Matter> matters=matterDao.find();
		Map<String,Object> totalMap=new HashMap<String,Object>();
		totalMap.put("matterID",0L);
		totalMap.put("matterName","总计");
		for(Org org:orgs){						
			totalMap.put("org_"+org.getId(), 0L);										
		}		
		for(Matter m:matters){
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("matterID",m.getId());
			resultMap.put("matterName",m.getName());
			for(Org org:orgs){
				Long v=getMatterCollect(m.getId(),org.getId(),os);				
				resultMap.put("org_"+org.getId(), v);	
				totalMap.put("org_"+org.getId(),((Long)totalMap.get("org_"+org.getId()))+v);
			}
			resultMaps.add(resultMap);						
		}
		resultMaps.add(totalMap);
		
	/*
		Long lastMatterID = 0L;
		Map totalMap=new HashMap();
		totalMap.put("matterID",0L);
		totalMap.put("matterName","总计");
		for(Org org:orgs){						
			totalMap.put("org_"+org.getId(), 0L);										
		}
		Map resultMap=null;
		for(Object[] o:os){		
			Long matterID=(Long)o[0];
			if(matterID!=null){
				if(matterID.compareTo(lastMatterID)!=0){
					resultMap=new HashMap();
					resultMap.put("matterID",matterID);
					resultMap.put("matterName",o[1].toString());		
					for(Org org:orgs){						
							resultMap.put("org_"+org.getId(), 0L);										
					}	
					resultMaps.add(resultMap);
				}
					if((o[2]!=null)&&(resultMap.get("org_"+o[2].toString())!=null)){
						resultMap.put("org_"+o[2].toString(), (Long)o[3]);	
						totalMap.put("org_"+o[2].toString(),(Long)totalMap.get("org_"+o[2].toString())+(Long)o[3]);	
					}
				lastMatterID=matterID;
			}			
		}
		resultMaps.add(totalMap);*/
		System.out.println(resultMaps.toString());
		return resultMaps;
	}
	
	public Long getMatterCollect(Long matterID,Long dutyerID,List<Object[]> os){
		Long result=0L;
		for(Object[] o:os){
			if(matterID.compareTo((Long)o[0])==0){
				if(dutyerID.compareTo((Long)o[2])==0){
					result=(Long)o[3];
				}
			}
		}		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@SuppressWarnings("unchecked")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		try {
			riskGradeChangeHandlers = applicationContext.getBeansOfType(RiskGradeChangeHandler.class).values();
			log.info("获取风险变更处理器: " + riskGradeChangeHandlers);
		} catch (Exception e) {
			log.warn("获取风险变更处理器出错，风险发生变化时将忽略处理。", e);
		}
		try {
			riskHandlers = applicationContext.getBeansOfType(RiskHandler.class).values();
			log.info("获取RiskHandler: " + riskGradeChangeHandlers);
		} catch (Exception e) {
			log.warn("获取RiskHandler出错，Risk创建时无法插入逻辑判断。", e);
		}
	}
	
	/**
	 * RISK风险级别变更时执行的方法。
	 * 调用注入的RiskGradeChangeHandler来执行具体的处理，并发出级别变更的事件。
	 * @param risk
	 * @param oldGrade
	 * @see RiskGradeChangeHandler
	 */
	private void riskGradeChangeHandle(Risk risk, byte oldGrade){
		if(riskGradeChangeHandlers != null){
			for(RiskGradeChangeHandler rgch: riskGradeChangeHandlers){
				rgch.gradeChange(risk, oldGrade);
			}
		}
		
		//发出事件
		Risk old = new Risk();
		old.setGrade(oldGrade);
		ApplicationEvent event = new RiskEvent(risk, RiskEvent.CHANGE, old);
		applicationContext.publishEvent(event);		
	}
	
	public void sendRiskLogEvent(Risk risk, Task task, int type){
		RiskLog riskLog = riskLogService.saveLogByRisk(risk,task,type);
		/*
		if(type==RiskLogEvent.RISK_GRADE_CHANGE){
			if(riskLog.getGrade()==2||riskLog.getGrade()==3||riskLog.getGrade()==4||riskLog.getGrade()==5||riskLog.getGrade()==6){
				SignCard sc=new SignCard();
				sc.setGrade(riskLog.getGrade());
				sc.setLabel(riskLog.getProjectName()+"项目-"+riskLog.getTaskName()+"-"+this.getGradeExplain(riskLog.getGrade()));
				sc.setType(riskLog.getRuleID().shortValue());
				sc.setRvDutyerType(riskLog.getDutyerType());
				sc.setRvDutyerID(riskLog.getDutyerID());
				sc.setRvDesc(riskLog.getConclusion());
				sc.setJuralLimit(riskLog.getJuralLimit());
				sc.setRefRiskLogID(riskLog.getId());
				sc.setRefObjectType(riskLog.getRefObjectType());
				sc.setRefObjectId(riskLog.getRefObjectId());
				sc.setRvOccurTime(riskLog.getCreationTime());
				sc.setCreateType(SignCard.CREATE_TYPE_AUTO);
				sc.setCode(riskLog.getId().toString());
				signCardDao.save(sc);
				
			}
		}
		*/
		
		//如果是监察，才生成待交换的数据。
		if(risk.getRuleType() == RiskRule.RULE_TYPE_监察){
			ExDataRisk ex = new ExDataRisk();
			ex.setDataID(riskLog.getId());
			ex.setStatus(ExDataRisk.STATUS_WAIT);	
			ex.setCreationTime(new Date());
			exDataRiskDao.save(ex);
		
			//
			ApplicationEvent event = new RiskLogEvent(riskLog, type, ex, risk);
			applicationContext.publishEvent(event);		
		}
	}
	
	private void beforeSaveRisk(Risk risk, RiskMonitorable riskMonitorable,
			RiskRule riskRule, RFSObject object, BigDecimal scaleValue) {
		if(riskHandlers != null){
			for(RiskHandler h: riskHandlers){
				h.beforeSaveRisk(risk, riskMonitorable, riskRule, object, scaleValue);
			}
		}
	}
	
	private void afterSaveRisk(Risk risk, RiskMonitorable riskMonitorable,
			RiskRule riskRule, RFSObject object, BigDecimal scaleValue) {
		if (riskHandlers != null) {
			for (RiskHandler h : riskHandlers) {
				h.afterSaveRisk(risk, riskMonitorable, riskRule, object, scaleValue);
			}
		}
	}
	
	
	public List<Object[]> getValueAndScaleValueCountGroupByDutyerID(){
		return riskDao.getValueAndScaleValueCountGroupByDutyerID();
	}
	public void deleteRiskByTaskSn(final Long taskSN) {
		 riskDao.deleteRiskByTaskSn(taskSN);
//		Task task = taskDao.get(taskSN);
//		if(task != null){
//			deleteRisk(task);
//		}
	}
	
	public List<Risk> findRiskByObjectAndObjectType(Long objectID,Integer objectType){
		return riskDao.findRiskByObjectAndObjectType(objectID, objectType);
	}

//	public void update(Risk risk) {
//		riskDao.update(risk);
//	}
	
	public List<Risk> findAllRisks(){
		return riskDao.find();
	}

	public void gradeChangeRemind(int days) {
		List<Risk> lr = riskDao.find();
		int countWhite = 0;
		int countBlue = 0;
		int countYellow = 0;
		int countOrange = 0;
		int countRed = 0;
		int countBlack = 0;
		for (Risk r : lr) {
			if (r.getPause() == Risk.PAUSE_NO) {
				int tmp = DateUtil.getTimeUsed(new Date(),
						/*DateUtil.getDays(new Date(), days, Calendar.DATE)*/
						DateUtil.daysLater(new Date(), days),
						r.getValueUnit());
				BigDecimal willUsed = r.getValue() != null ? r.getValue().add(
						BigDecimal.valueOf(tmp)) : BigDecimal.ZERO
						.add(BigDecimal.valueOf(tmp));
				if ((r.getScaleMark() & 32) != 0
						&& (willUsed.compareTo(r.getScaleValue6()) >= 0)) {
					countBlack++;
				} else if ((r.getScaleMark() & 16) != 0
						&& (willUsed.compareTo(r.getScaleValue5()) >= 0)) {
					countRed++;
				} else if ((r.getScaleMark() & 8) != 0
						&& (willUsed.compareTo(r.getScaleValue4()) >= 0)) {
					countOrange++;
				} else if ((r.getScaleMark() & 4) != 0
						&& (willUsed.compareTo(r.getScaleValue3()) >= 0)) {
					countYellow++;
				} else if ((r.getScaleMark() & 2) != 0
						&& (willUsed.compareTo(r.getScaleValue2()) >= 0)) {
					countBlue++;
				} else if ((r.getScaleMark() & 1) != 0
						&& (willUsed.compareTo(r.getScaleValue1()) >= 0)) {
					countWhite++;
				}
			}
		}
		// System.out.println("countWhite:"+countWhite);
		// System.out.println("countOrange:"+countOrange);
		// System.out.println("countBlack:"+countBlack);
		// System.out.println("countBlue:"+countBlue);
		// System.out.println("countYellow:"+countYellow);
		// System.out.println("countRed:"+countRed);
		Map<String,Object> tmp = new HashMap<String,Object>();
		tmp.put("${advanceDays}", String.valueOf(days));
		tmp.put("${countWhite}", String.valueOf(countWhite));
		tmp.put("${countBlue}", String.valueOf(countBlue));
		tmp.put("${countYellow}", String.valueOf(countYellow));
		tmp.put("${countOrange}", String.valueOf(countOrange));
		tmp.put("${countRed}", String.valueOf(countRed));
		tmp.put("${countBlack}", String.valueOf(countBlack));

		for (int i = 0; i < 7; i++) {
			long eventType = Long.valueOf((days > 0 ? "2" : "1")
					+ sdf2.format(new Date()) + "0" + i);
			// System.out.println("i:"+i);
			// System.out.println("eventType:"+eventType);
			if ((i == 0 && (countBlue > 0 || countYellow > 0 || countRed > 0))
					|| (i == 1 && countWhite > 0) || (i == 2 && countBlue > 0)
					|| (i == 3 && countYellow > 0)
					|| (i == 4 && countOrange > 0) || (i == 5 && countRed > 0)
					|| (i == 6 && countBlack > 0)) {
				// System.out.println("in====eventType:"+eventType);
				List<EventMsg> eventMsgs = eventMsgService.findEventMsgCfg(1001, 0L, eventType);
				if (eventMsgs != null && eventMsgs.size() > 0) {
					for (EventMsg em : eventMsgs) {
						if (em.getIsCreateMsg() == EventMsg.IS_TRUE) {
							eventMsgService.createSmsgByEventMsg(em, 0L, null,	tmp);
						}
					}
				}
			}
		}
	}

	/**
	 * @return the entityObjectLoader
	 */
	public EntityObjectLoader getEntityObjectLoader() {
		return entityObjectLoader;
	}

	/**
	 * @param entityObjectLoader the entityObjectLoader to set
	 */
	public void setEntityObjectLoader(EntityObjectLoader entityObjectLoader) {
		this.entityObjectLoader = entityObjectLoader;
	}

	
	
	public int getObjectType() {
		return RiskObject.OBJECT_TYPE;
	}

	public RiskObject loadObject(long id) throws NotFoundException {
		Risk risk = getRiskById(id);
		if(risk != null){
			return new RiskObject(risk);
		}
		return null;
	}

	public List<RiskObject> loadObjects(List<Long> objectIds) throws NotFoundException {
		ResultFilter f = new ResultFilter();
		f.setCriterion(Restrictions.in("id", objectIds));
		List<Risk> list = riskDao.find(f);
		if(list.size() > 0){
			List<RiskObject> result = new ArrayList<RiskObject>();
			for(Risk risk: list){
				result.add(new RiskObject(risk));
			}
			return result;
		}
		return Collections.emptyList();
	}
	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.EntityObjectFactory#loadObjects(org.opoo.ndao.support.ResultFilter)
	 */
	public List<RiskObject> loadObjects(ResultFilter filter) {
		List<Risk> list = riskDao.find(filter);
		if(list.size() > 0){
			List<RiskObject> result = new ArrayList<RiskObject>();
			for(Risk risk: list){
				result.add(new RiskObject(risk));
			}
			return result;
		}
		return Collections.emptyList();
	}
	/**
	 * 延期
	 */
	public void delayRisk(Long riskID, short delayTime) {
		Risk risk = riskDao.get(riskID);
		delayRisk(risk, delayTime);
	}
	
	public void delayRisk(Risk risk, short delayTime){
		if(risk != null && delayTime != 0){
			BigDecimal delta = new BigDecimal(delayTime);
			risk.setScaleValue(BigDecimalUtils.add(risk.getScaleValue(), delta));
			risk.setScaleValue1(BigDecimalUtils.add(risk.getScaleValue1(), delta));
			risk.setScaleValue2(BigDecimalUtils.add(risk.getScaleValue2(), delta));
			risk.setScaleValue3(BigDecimalUtils.add(risk.getScaleValue3(), delta));
			risk.setScaleValue4(BigDecimalUtils.add(risk.getScaleValue4(), delta));
			risk.setScaleValue5(BigDecimalUtils.add(risk.getScaleValue5(), delta));
			risk.setScaleValue6(BigDecimalUtils.add(risk.getScaleValue6(), delta));
			
			//延期时添加延期时间到监察规则
			String ruleSummary = risk.getRuleSummary();
			if(ruleSummary == null){
				ruleSummary = "";
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
			String string = format.format(new Date());
			ruleSummary += string + "延期" + delayTime + risk.getValueUnitName() + "。";
			risk.setRuleSummary(ruleSummary);
			
			//重新计算
//			risk.checkGrade(null);
//			riskDao.update(risk);
			
			//修该了scaleValue，value没变，则强制计算grade
			boolean updated = calculateAndUpdateRiskGrade(risk);
			if(!updated){
				risk = riskDao.update(risk);
			}
			sendRiskLogEvent(risk, null, RiskLogEvent.SCALE_CHANGE);
			//
			saveDelayRiskBizLog(new RiskObject(risk), delayTime);
		}
	}
	
	public List<Risk> findRisks(ResultFilter filter){
		return riskDao.find(filter);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectCountQuery#getObjectCount(org.opoo.ndao.support.ResultFilter)
	 */
	public int getObjectCount(ResultFilter rf) {
		return riskDao.getCount(rf);
	}
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.ObjectFinder#findObjects(org.opoo.ndao.support.ResultFilter)
	 */
	public List<Risk> findObjects(ResultFilter rf) {
		return riskDao.find(rf);
	}
	
	
//	public void removeRisk(Long id){
//		Risk risk = riskDao.get(id);
//		if(risk != null){
//			Long objectID = risk.getObjectID();
//			int type = risk.getObjectType();
//			if(objectID != null){
//				RFSEntityObject object = entityObjectLoader.getEntityObject(type, objectID);
//				if(object instanceof RiskMonitorable){
//					RiskMonitorable rm = (RiskMonitorable) object;
//					rm.setRiskEntries(null);s
//					//
//					update(rm);
//				}
//			}
//			riskDao.delete(risk);
//		}
//	}
	
	
	/**
	 * @deprecated
	 */
	public boolean calculateAndUpdateRiskValue(Risk risk, Date calculateTime){
		BigDecimal newValue = riskCalculator.calculateValue(risk, calculateTime);
		return updateRiskValue(risk, newValue, calculateTime);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public boolean calculateAndUpdateRiskValue(Risk risk){
		RiskValue riskValue = riskCalculator.getRiskValue(risk);
		boolean updateRiskValue = updateRiskValue(risk, riskValue);
		
		//在updateRiskValue内部未保存对象，则这里保存
		if(!updateRiskValue){
			if(risk.getValueSource() != RiskRule.VALUE_SOURCE_TIMER_ADD){
				//直接设置
				risk.setLastRunTime(new Date());
				riskDao.update(risk);
				updateRiskValue = true;
			}
		}
		//bizlog
		saveCalculateAndUpdateRiskValueBizLog(new RiskObject(risk), riskValue);
		return updateRiskValue;
	}
	
	
	public boolean updateRiskValue(Risk risk, RiskValue value){
		if(value instanceof BizSummaryProvider){
			String bizSummary = ((BizSummaryProvider)value).getBizSummary();
			if(log.isDebugEnabled()){
				log.debug("设置Risk的bizSummary: " + bizSummary);
			}
			risk.setBizSummary(bizSummary);
		}
		
		return updateRiskValue(risk, value.getValue(), value.getValueGeneratedTime());
	}
	
	public boolean updateRiskValue(Risk risk, BigDecimal newValue, Date valueChangedTime){
		BigDecimal oldValue = risk.getValue();
		boolean valueChanged = !EqualsUtils.equals(newValue, oldValue);
		if(valueChanged){
			if(IS_DEBUG_ENABLED){
				log.debug("Risk的value变更：" + risk);
			}
			if(valueChangedTime == null){
				valueChangedTime = new Date();
			}
			
			risk.setValue(newValue);
			//指针，不能随便修改
			//risk.setLastRunTime(valueChangedTime);
			risk.setValueChangedTime(valueChangedTime);
			//如果值不是自动增长的则设置
			if(risk.getValueSource() != RiskRule.VALUE_SOURCE_TIMER_ADD){
				//直接设置
				risk.setLastRunTime(new Date());
			}
			
			//设置被监控对象的被监控属性的值
			if(risk.getObjectAttr() != null && risk.getValueSource() == RiskRule.VALUE_SOURCE_TIMER_ADD){
				riskDao.updateObjectAttr(risk.getObjectID(), risk.getObjectType(), risk.getObjectAttr(), risk.getValue());
			}
			
			//计算risk的remark
			RiskRule rule = riskRuleDao.get(risk.getRuleID());
			if(rule == null){
				throw new ObjectNotFoundException(String.format("Risk 对应的 Rule 找不到(risk.id=%s, risk.ruleID=%s)", 
						risk.getId(), risk.getRuleID()));
			}
			if(StringUtils.isNotBlank(rule.getRemarkTemplate())){
				risk.setRemark(parseRiskRemark(rule.getRemarkTemplate(), risk, rule));
			}
			
			//设置监察人 2012 04 25
			Clerk clerk = UserClerkHolder.getNullableClerk();
			if(clerk == null){
				clerk = getClerkService().getClerk(SupervisorUser.USER_ID);
			}
			if(clerk != null){
				risk.setSuperviseClerkId(clerk.getId());
				risk.setSuperviseClerkName(clerk.getName());
			}else{
				log.warn("找不到监察人");
			}
			
			sendRiskLogEvent(risk, null, RiskLogEvent.RISK_VALUE_CHANGE);
			
			//计算risk的grade值
			boolean gradeChanged = calculateAndUpdateRiskGrade(risk);
			
			//如果grade级别没有保存，则在这里保存
			if(!gradeChanged){
				riskDao.update(risk);
			}
			
			saveValueChangedBizLog(new RiskObject(risk), newValue, oldValue);
		}
		return valueChanged;
	}
	
	public static String parseRiskRemark(String remarkTemplate, Risk risk, RiskRule rule){
		Map<String,Object> map = Maps.newHashMap();
		map.put("risk", risk);
		map.put("rule", rule);
		map.put("date", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
		map.put("dateTime", new SimpleDateFormat("yyyy年MM月dd日hh时mm分ss秒").format(new Date()));
		try {
			return  org.opoo.apps.util.StringUtils.processExpression(remarkTemplate, map);
		} catch (Exception e) {
			log.error("Risk备注模板解析错误", e);
		}
		return null;
	}
	
	public boolean calculateAndUpdateRiskGrade(Risk risk){
		byte grade = riskCalculator.calculateGrade(risk);
		return updateRiskGrade(risk, grade);
	}
	
	public boolean updateRiskGrade(Risk risk, byte newGrade){
		byte oldGrade = risk.getGrade();
		boolean gradeChanged = (newGrade != oldGrade);
		if(gradeChanged){
			if(IS_DEBUG_ENABLED){
				log.debug("Risk的grade变更：" + risk);
			}
			risk.setGrade(newGrade);
			risk.setGradeChangedTime(new Date());
			
			String conclusion = calculateConclusion(risk);
			risk.setConclusion(conclusion);
			
			risk = riskDao.update(risk);
			
			riskGradeChangeHandle(risk, oldGrade);
			sendRiskLogEvent(risk,null,RiskLogEvent.RISK_GRADE_CHANGE);
			dealEventMsg(risk);
			
			//risk = riskDao.update(risk);
			saveGrageChangedBizLog(new RiskObject(risk), newGrade, oldGrade);
		}
		return gradeChanged;
	}
	
	public int calculateAllRunningRisksInThreads() {
		final Date time = new Date();
		boolean optimized = AppsGlobals.getProperty(CALCULATE_ALL_RUNNING_RISKS_OPTIMIZED_KEY, true);
		if(optimized && DateUtil.isSameDay(time, lastCalculateTime)){
			log.info("同一天已经运行过，不再执行calculateAllRunningRisks()，上次执行时间：" + lastCalculateTime);
			return 0;
		}
		
		Logic logic = Restrictions.logic(Restrictions.eq("status", Risk.STATUS_ON_USE))
				.and(Restrictions.eq("pause", Risk.PAUSE_NO))
				;//.and(Restrictions.eq("valueSource", RiskRule.VALUE_SOURCE_TIMER_ADD));
		ResultFilter filter = new ResultFilter(logic, null);
		List<Long> ids = riskDao.findIds(filter);
		
		if(log.isDebugEnabled()){
			log.debug("本次共需计算risk：" + ids.size());
		}
		if(ids.isEmpty()){
			log.info("无需计算。");
			return 0;
		}
		
		int result = calculateInThreads(ids, time);
		
		lastCalculateTime = time;
		if(IS_DEBUG_ENABLED){
            log.debug("[RiskServiceImpl.calculateAllRunningRisks()]处理数据 " + result + " 条，用时 " + (System.currentTimeMillis() - time.getTime()) + " 毫秒。");
        }
		
		return result;
	}
	
	private int calculateInThreads(List<Long> ids, final Date time){
		class Result{
			Risk risk; 
			boolean updated;
			Result(Risk risk, boolean updated){this.risk = risk; this.updated = updated;}
		};
		
		int nThreads = AppsGlobals.getProperty(CALCULATE_ALL_RUNNING_RISKS_THREADS_KEY, 10);
		ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
		ExecutorCompletionService<Result> service = new ExecutorCompletionService<Result>(executorService);
		
		for(final Long id: ids){
			service.submit(new Callable<Result>() {
				public Result call() throws Exception {
					Risk risk = riskDao.get(id);
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					try {
						SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(SupervisorUser.USERNAME, "***"));
						boolean b = calculateAndUpdateRiskValue(risk);
						return new Result(risk, b);
					} catch (Exception e) {
						//System.out.println("设置和计算Risk值时出错: " + e);
						log.error("设置和计算Risk值时出错", e);
						return null;
					}finally{
						//调用完成时，将当前用户信息恢复
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			});
		}
		
		int result = 0;
		try{
			for(int i = 0, n = ids.size() ; i < n ; i++){
				Result r = service.take().get();
				if(log.isDebugEnabled()){
					log.debug("获取到 risk(id=" + r.risk.getId() + ")的计算结果: 是否变更 => " + r.updated);
				}
				if(r != null && r.updated){
					result++;
				}
			}
		}catch(Throwable e){
			log.error("calculateInThreads 获取计算结果时出错", e);
		}
		
		executorService.shutdown();
		executorService = null;//GC
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.RiskChecker#calculateAllTimeAddedRunningRisks()
	 */
	public int calculateAllRunningRisks() {
//		String hql = "from Risk where status=? and pause=? and valueSource=?";
//		getHibernateTemplate().find(hql, 
//				new Object[]{Risk.STATUS_ON_USE, 
//				Risk.PAUSE_NO, 
//				RiskRule.VALUE_SOURCE_TIMER_ADD});
		
		final Date time = new Date();
		boolean optimized = AppsGlobals.getProperty(CALCULATE_ALL_RUNNING_RISKS_OPTIMIZED_KEY, true);
		if(optimized && DateUtil.isSameDay(time, lastCalculateTime)){
			log.info("同一天已经运行过，不再执行calculateAllRunningRisks()，上次执行时间：" + lastCalculateTime);
			return 0;
		}

		
		Logic logic = Restrictions.logic(Restrictions.eq("status", Risk.STATUS_ON_USE))
			.and(Restrictions.eq("pause", Risk.PAUSE_NO))
			;//.and(Restrictions.eq("valueSource", RiskRule.VALUE_SOURCE_TIMER_ADD));
		
		if(IS_DEBUG_ENABLED){
			log.debug("calculateAllTimeAddedRunningRisks 查询条件：" + logic);
		}
		
		//每次处理10条
		ResultFilter filter = new ResultFilter(logic, null, 0, 20);
		
		ObjectHandler<Long, Risk> idHandler = new ObjectHandler<Long, Risk>(){
			public Risk handle(Long id) {
				Risk risk = riskDao.get(id);
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				try {
					SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(SupervisorUser.USERNAME, "***"));
					boolean b = calculateAndUpdateRiskValue(risk);
					return b ? risk : null;
				} catch (Exception e) {
					//System.out.println("设置和计算Risk值时出错: " + e);
					log.error("设置和计算Risk值时出错", e);
					return null;
				}finally{
					//调用完成时，将当前用户信息恢复
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		};
		
		int object = BatchHelper.batchHandleObject(new IdFinder(), idHandler, filter, false);
		//int object = BatchHelper.batchHandleObjectByID(finder, handler, filter, false);
		lastCalculateTime = time;
		if(IS_DEBUG_ENABLED){
            log.debug("[RiskServiceImpl.calculateAllRunningRisks()]处理数据 " + object + "条。");
        }
		
		return object;
	}
	
	public Date getLastCalculateAllRunningRisksTime() {
		return lastCalculateTime;
	}
	
	public void resetLastCalculateAllRunningRisksTime(){
		this.lastCalculateTime = null;
	}
	
//	/* (non-Javadoc)
//	 * @see cn.redflagsoft.base.service.RiskChecker#recalculateAllRisksGrade(boolean)
//	 */
//	public int recalculateAllRisksGrade(boolean force) {
//		//每次处理10条
//		ResultFilter filter = new ResultFilter(null, null, 0, 10);
//		
//		ObjectHandler<Long, Risk> idHandler = new ObjectHandler<Long, Risk>(){
//			public Risk handle(Long id) {
//				Risk risk = riskDao.get(id);
//				boolean b = calculateAndUpdateRiskGrade(risk);
//				return b ? risk : null;
//			}
//		};
//		
//		int object = BatchHelper.batchHandleObject(new IdFinder(), idHandler, filter, false);
//		//int object = BatchHelper.batchHandleObjectByID(finder, handler, filter, false);
//		if(IS_DEBUG_ENABLED){
//            log.debug("[RiskServiceImpl.recalculateAllRisksGrade()]处理数据 " + object + "条。");
//        }
//		
//		return object;
//	}
	
	
	private class IdFinder implements ObjectFinder<Long>{
		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectCountQuery#getObjectCount(org.opoo.ndao.support.ResultFilter)
		 */
		public int getObjectCount(ResultFilter rf) {
			return riskDao.getCount(rf);
		}

		/* (non-Javadoc)
		 * @see cn.redflagsoft.base.service.ObjectFinder#findObjects(org.opoo.ndao.support.ResultFilter)
		 */
		public List<Long> findObjects(ResultFilter rf) {
			return riskDao.findIds(rf);
		}
	}
	
	public void updateRiskDutyersInfo(Risk risk, Clerk dutyer, Clerk dutyerLeader1, Clerk dutyerLeader2){
		if(risk != null){
			boolean updated = false;
			StringBuffer logMessage = new StringBuffer();
			
			if(dutyer != null){
				logMessage.append(risk.getDutyerName()  + " -> " + dutyer.getName() + ", ");
				updated = true;
				
				risk.setDutyerOrgId(dutyer.getEntityID());
				risk.setDutyerOrgName(dutyer.getEntityName());
				risk.setDutyerID(dutyer.getId());
				risk.setDutyerName(dutyer.getName());
				risk.setDutyerMobNo(dutyer.getMobNo());
			}
			
			if(dutyerLeader1 != null){
				logMessage.append(risk.getDutyerManagerName()  + " -> " + dutyerLeader1.getName() + ", ");
				updated = true;
				
				risk.setDutyerManagerId(dutyerLeader1.getId());
				risk.setDutyerManagerName(dutyerLeader1.getName());
				risk.setDutyerManagerMobNo(dutyerLeader1.getMobNo());
			}

			if(dutyerLeader2 != null){
				logMessage.append(risk.getDutyerLeaderName()  + " -> " + dutyerLeader2.getName() + ", ");
				updated = true;
				
				risk.setDutyerLeaderId(dutyerLeader2.getId());
				risk.setDutyerLeaderName(dutyerLeader2.getName());
				risk.setDutyerLeaderMobNo(dutyerLeader2.getMobNo());
			}
			
			if(updated){
				risk = riskDao.update(risk);
				saveUpdateRiskDutyClerkBizLog(new RiskObject(risk), logMessage.toString());
			}else{
				log.warn("责任人未变更，输入为空。");
			}
		}
	}
	
	
	
	public void updateRiskDutyersInfo(Long riskID, Clerk dutyer, Clerk dutyerLeader1, Clerk dutyerLeader2) {
		updateRiskDutyersInfo(getRiskById(riskID), dutyer, dutyerLeader1, dutyerLeader2);
	} 
	
	
	////////////////////////////////////////////////////////
	// 监察操作日志业务类型定义 2012-05-16
	/*
	 监察登记，112-101	监察登记，112-101-11
	监察暂停，112-102	监察暂停，112-102-99
	监察重启，112-103	监察重启，112-103-99
	监察结束，112-104	监察结束，112-104-99
	监察作废，112-105	监察作废，112-105-99
	监察计算，112-106	监察计算，112-106-99
	监察延期，112-107	监察延期，112-107-99
	监察结果级别变更，112-108		监察结果级别变更，112-108-99
	监察结果值变更，112-109		监察结果值变更，112-109-99
	监察规定值变更，112-111		监察规定值变更，112-111-99
	监察责任人变更，112-112		监察责任人变更，112-112-99
	 */
	
	/**
	 * 保存risk登记时的业务日志。
	 * @param risk
	 */
	@BizLog(objectIndex=0, taskType=112101, workType=11210111, userId=SupervisorUser.USER_ID)
	private void saveCreateRiskBizLog(RiskObject risk){
		if(log.isDebugEnabled()){
			log.debug("记录监察登记日志：" + risk.getRisk());
		}
	}
	@BizLog(objectIndex=0, taskType=112102, workType=11210299, userId=SupervisorUser.USER_ID)
	private void saveHangRiskBizLog(RiskObject risk){
		if(log.isDebugEnabled()){
			log.debug("记录监察暂停日志：" + risk.getRisk());
		}
	}
	@BizLog(objectIndex=0, taskType=112103, workType=11210399, userId=SupervisorUser.USER_ID)
	private void saveWakeRiskBizLog(RiskObject risk){
		if(log.isDebugEnabled()){
			log.debug("记录监察重启日志：" + risk.getRisk());
		}
	}
	@BizLog(objectIndex=0, taskType=112104, workType=11210499, userId=SupervisorUser.USER_ID)
	private void saveTerminateRiskBizLog(RiskObject risk){
		if(log.isDebugEnabled()){
			log.debug("记录监察结束日志：" + risk.getRisk());
		}
	}
	@BizLog(objectIndex=0, taskType=112105, workType=11210599, userId=SupervisorUser.USER_ID)
	protected void saveInvalidRiskBizLog(RiskObject risk){
		if(log.isDebugEnabled()){
			log.debug("记录监察作废日志：" + risk.getRisk());
		}
	}
	
	@BizLog(objectIndex=0, taskType=112106, workType=11210699, userId=SupervisorUser.USER_ID)
	private void saveCalculateAndUpdateRiskValueBizLog(RiskObject risk, RiskValue riskValue){
		if(log.isDebugEnabled()){
			log.debug("记录监察计算日志：" + risk.getRisk());
		}
		try{
			String ds = "[]";
			if(riskValue.getValueGeneratedTime() != null){
				ds = AppsGlobals.formatDateTime(riskValue.getValueGeneratedTime());
			}else{
				ds = AppsGlobals.formatDateTime(new Date());
			}
			BizLogContext.getTask().setNote("当前值：" + riskValue.getValue() + ", 于 " + ds + " 产生");
		}catch(Exception e){
			log.error("设置监察计算备注时出错", e);
		}
	}
	@BizLog(objectIndex=0, taskType=112107, workType=11210799, userId=SupervisorUser.USER_ID)
	private void saveDelayRiskBizLog(RiskObject risk, short time){
		if(log.isDebugEnabled()){
			log.debug("记录监察延期日志：" + risk.getRisk());
		}
		try{
			BizLogContext.getTask().setNote("本次延期 " + time + risk.getRisk().getValueUnitName());
		}catch(Exception e){
			log.error("设置监察延期备注时出错", e);
		}
	}
	
	@BizLog(objectIndex=0, taskType=112108, workType=11210899, userId=SupervisorUser.USER_ID)
	private void saveGrageChangedBizLog(RiskObject risk, byte newGrade, byte oldGrade){
		if(log.isDebugEnabled()){
			log.debug("记录监察结果级别变更日志：" + risk.getRisk());
		}
		try{
			BizLogContext.getTask().setNote("监察等级变更：" 
					+ Risk.getGradeExplain(oldGrade)
					+ " -> " + Risk.getGradeExplain(newGrade));
		}catch(Exception e){
			log.error("设置监察结果级别变更备注时出错", e);
		}
	}
	@BizLog(objectIndex=0, taskType=112109, workType=11210999, userId=SupervisorUser.USER_ID)
	private void saveValueChangedBizLog(RiskObject risk, BigDecimal newValue, BigDecimal oldValue){
		if(log.isDebugEnabled()){
			log.debug("记录监察结果值变更日志：" + risk.getRisk());
		}
		try{
			BizLogContext.getTask().setNote("结果值变更：" + oldValue + " -> " + newValue);
		}catch(Exception e){
			log.error("设置监察结果值变更备注时出错", e);
		}
	}
	@BizLog(objectIndex=0, taskType=112111, workType=11211199, userId=SupervisorUser.USER_ID)
	private void saveScaleValueChangedBizLog(RiskObject risk, BigDecimal newScaleValue, BigDecimal oldScaleValue){
		if(log.isDebugEnabled()){
			log.debug("记录监察规定值变更日志：" + risk.getRisk());
		}
		try{
			BizLogContext.getTask().setNote("规定值变更：" + oldScaleValue + " -> " + newScaleValue);
		}catch(Exception e){
			log.error("设置监察规定值变更备注时出错", e);
		}
	}
	@BizLog(objectIndex=0, taskType=112112, workType=11211299, userId=SupervisorUser.USER_ID)
	private void saveUpdateRiskDutyClerkBizLog(RiskObject risk, String logMessage) {
		if(log.isDebugEnabled()){
			log.debug("记录监察责任人变更日志：" + risk.getRisk());
		}
		try{
			BizLogContext.getTask().setNote("三级责任人变更：" + logMessage);
		}catch(Exception e){
			log.error("设置监察责任人变更备注时出错", e);
		}
	}
	public void riskInvalid(RFSObject rfsObject) {
		riskDao.riskInvalid(rfsObject);
	}
}
