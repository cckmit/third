package cn.redflagsoft.base.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.opoo.apps.annotation.Queryable;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.EventMsg;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.Peoples;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskLog;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.TaskMatter;
import cn.redflagsoft.base.dao.OrgDao;
import cn.redflagsoft.base.dao.PeoplesDao;
import cn.redflagsoft.base.dao.RiskLogDao;
import cn.redflagsoft.base.dao.TaskDao;
import cn.redflagsoft.base.dao.TaskMatterDao;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.EntityObjectLoader;
import cn.redflagsoft.base.service.EventMsgService;
import cn.redflagsoft.base.service.GlossaryService;
import cn.redflagsoft.base.service.ObjectInfoService;
import cn.redflagsoft.base.service.RiskLogService;
import cn.redflagsoft.base.service.RiskService;
import cn.redflagsoft.base.util.DateUtil;
import cn.redflagsoft.base.vo.BizVO;
import cn.redflagsoft.base.vo.MonitorVO;
import cn.redflagsoft.base.vo.ObjectInfoVO;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class RiskLogServiceImpl implements RiskLogService {

	private RiskLogDao riskLogDao;
	private TaskDao taskDao;
	private TaskMatterDao taskMatterDao;
	// private ObjectService<RFSObject> objectService;
	private ObjectInfoService objectInfoService;
	private PeoplesDao peoplesDao;
	private EventMsgService eventMsgService;
	private RiskService riskService;
	private OrgDao orgDao;
	private GlossaryService glossaryService;
	private ClerkService clerkService;
	private EntityObjectLoader entityObjectLoader;

	public GlossaryService getGlossaryService() {
		return glossaryService;
	}

	public void setGlossaryService(GlossaryService glossaryService) {
		this.glossaryService = glossaryService;
	}

	public ClerkService getClerkService() {
		return clerkService;
	}

	public void setClerkService(ClerkService clerkService) {
		this.clerkService = clerkService;
	}

	public RiskService getRiskService() {
		return riskService;
	}

	public void setRiskService(RiskService riskService) {
		this.riskService = riskService;
	}

	public OrgDao getOrgDao() {
		return orgDao;
	}

	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	public EventMsgService getEventMsgService() {
		return eventMsgService;
	}

	public void setEventMsgService(EventMsgService eventMsgService) {
		this.eventMsgService = eventMsgService;
	}

	public PeoplesDao getPeoplesDao() {
		return peoplesDao;
	}

	public void setPeoplesDao(PeoplesDao peoplesDao) {
		this.peoplesDao = peoplesDao;
	}

	public ObjectInfoService getObjectInfoService() {
		return objectInfoService;
	}

	public void setObjectInfoService(ObjectInfoService objectInfoService) {
		this.objectInfoService = objectInfoService;
	}

	public TaskMatterDao getTaskMatterDao() {
		return taskMatterDao;
	}

	public void setTaskMatterDao(TaskMatterDao taskMatterDao) {
		this.taskMatterDao = taskMatterDao;
	}

	public TaskDao getTaskDao() {
		return taskDao;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public RiskLogDao getRiskLogDao() {
		return riskLogDao;
	}

	public void setRiskLogDao(RiskLogDao riskLogDao) {
		this.riskLogDao = riskLogDao;
	}
	
	public RiskLog saveLogByRisk(Risk risk, Task t, int type) {
		RiskLog rl = new RiskLog();
		// rl.setId(this.getIdGenerator().getNext());
		rl.setCategory(risk.getCategory());
		rl.setThingCode(risk.getThingCode());
		rl.setThingName(risk.getThingName());
		rl.setConclusion(risk.getConclusion());
		rl.setCreator(risk.getCreator());
//		rl.setDutyerID(risk.getDutyerID());
//		rl.setDutyerName(risk.getDutyerName());
//		rl.setDutyerType(risk.getDutyerType());
		rl.setGrade(risk.getGrade());
		rl.setJuralLimit(risk.getJuralLimit());
		rl.setModificationTime(risk.getModificationTime());
		rl.setModifier(risk.getModifier());
		rl.setName(risk.getName());
		rl.setObjectAttr(risk.getObjectAttr());
		rl.setObjectID(risk.getObjectID());
		rl.setObjectType(risk.getObjectType());
		rl.setPause(risk.getPause());
		rl.setRuleID(risk.getRuleID());
		rl.setRefID(risk.getRefID());
		rl.setRefObjectId(risk.getRefObjectId());
		rl.setRefObjectType(risk.getRefObjectType());
		rl.setRefTaskTypeName(risk.getRefTaskTypeName());
		rl.setRefType(risk.getRefType());
		rl.setRemark(risk.getRemark());
		rl.setScaleMark(risk.getScaleMark());
		rl.setScaleValue(risk.getScaleValue());
		rl.setScaleValue1(risk.getScaleValue1());
		rl.setScaleValue2(risk.getScaleValue2());
		rl.setScaleValue3(risk.getScaleValue3());
		rl.setScaleValue4(risk.getScaleValue4());
		rl.setScaleValue5(risk.getScaleValue5());
		rl.setScaleValue6(risk.getScaleValue6());
		rl.setStatus(risk.getStatus());
		rl.setSystemID(risk.getSystemID());
		rl.setType(risk.getType());
		rl.setValue(risk.getValue());
		rl.setValueFormat(risk.getValueFormat());
		rl.setValueSign(risk.getValueSign());
		rl.setValueSource(risk.getValueSource());
		rl.setValueType(risk.getValueType());
		rl.setValueUnit(risk.getValueUnit());
		rl.setCreationTime(new Date());
		rl.setBusiType(type);
		rl.setInitValue(risk.getInitValue());
		
		////2012-02-24
		rl.setSuperviseOrgAbbr(risk.getSuperviseOrgAbbr());
		rl.setSuperviseOrgId(risk.getSuperviseOrgId());
		rl.setDutyerDeptId(risk.getDutyerDeptId());
		rl.setDutyerDeptName(risk.getDutyerDeptName());
		rl.setDutyerID(risk.getDutyerID());
		rl.setDutyerLeaderId(risk.getDutyerLeaderId());
		rl.setDutyerLeaderMobNo(risk.getDutyerLeaderMobNo());
		rl.setDutyerLeaderName(risk.getDutyerLeaderName());
		rl.setDutyerManagerId(risk.getDutyerManagerId());
		rl.setDutyerManagerMobNo(risk.getDutyerManagerMobNo());
		rl.setDutyerManagerName(risk.getDutyerManagerName());
		rl.setDutyerMobNo(risk.getDutyerMobNo());
		rl.setDutyerName(risk.getDutyerName());
		rl.setDutyerOrgId(risk.getDutyerOrgId());
		rl.setDutyerOrgName(risk.getDutyerOrgName());
		rl.setDutyerType(risk.getDutyerType());
		
		//ref object 2012-02-24
		rl.setProjectID(risk.getRefObjectId());
		rl.setProjectName(risk.getRefObjectName());
		rl.setProjectSn(risk.getRefObjectSn());
		
		// 2012-05-04
		rl.setObjectCode(risk.getObjectCode());
		
		if (t == null) {
			if (risk.getObjectType() == Risk.OBJECT_TYPE_TASK) {
				t = taskDao.get(risk.getObjectID());
			}

		}
		if (t != null) {
			rl.setTaskSN(t.getSn());
			rl.setTaskName(t.getName());
			rl.setP3ID(t.getClerkID());
			rl.setBusiStartTime(t.getBusiStartTime());
			rl.setBusiEndTime(t.getBusiEndTime());
			
			//2012-02-24
			/*
			List<TaskMatter> tms = taskMatterDao.findTaskMatterByTaskSn(t.getSn());
			if (tms != null && tms.size() > 0) {
				TaskMatter tm = tms.get(0);
				rl.setItemID(tm.getMatterCode());
				rl.setItemName(tm.getMatterName());
			}
			*/
		}
		
		rl.setItemID(risk.getItemID());
		rl.setItemName(risk.getItemName());
		
		if (rl.getValue() != null) {
			rl.setTimeUsed(rl.getValue().intValue());
		}
		if (rl.getScaleValue() != null) {
			if (rl.getValue() != null) {
				rl.setTimeRemain((rl.getScaleValue().subtract(rl.getValue()))
						.intValue());
			} else {
				rl.setTimeRemain((rl.getScaleValue()).intValue());
			}
		}

		return riskLogDao.save(rl);
	}

	//RISK中增加字段后，字段抄送规则不同, 2012-02-24
	@Deprecated
	public RiskLog saveLogByRisk_BAK(Risk risk, Task t, int type) {
		RiskLog rl = new RiskLog();
		// rl.setId(this.getIdGenerator().getNext());
		rl.setCategory(risk.getCategory());
		rl.setThingCode(risk.getThingCode());
		rl.setThingName(risk.getThingName());
		rl.setConclusion(risk.getConclusion());
		rl.setCreator(risk.getCreator());
		rl.setDutyerID(risk.getDutyerID());
		rl.setDutyerName(risk.getDutyerName());
		rl.setDutyerType(risk.getDutyerType());
		rl.setGrade(risk.getGrade());
		rl.setJuralLimit(risk.getJuralLimit());
		rl.setModificationTime(risk.getModificationTime());
		rl.setModifier(risk.getModifier());
		rl.setName(risk.getName());
		rl.setObjectAttr(risk.getObjectAttr());
		rl.setObjectID(risk.getObjectID());
		rl.setObjectType(risk.getObjectType());
		rl.setPause(risk.getPause());
		rl.setRuleID(risk.getRuleID());
		rl.setRefID(risk.getRefID());
		rl.setRefObjectId(risk.getRefObjectId());
		rl.setRefObjectType(risk.getRefObjectType());
		rl.setRefTaskTypeName(risk.getRefTaskTypeName());
		rl.setRefType(risk.getRefType());
		rl.setRemark(risk.getRemark());
		rl.setScaleMark(risk.getScaleMark());
		rl.setScaleValue(risk.getScaleValue());
		rl.setScaleValue1(risk.getScaleValue1());
		rl.setScaleValue2(risk.getScaleValue2());
		rl.setScaleValue3(risk.getScaleValue3());
		rl.setScaleValue4(risk.getScaleValue4());
		rl.setScaleValue5(risk.getScaleValue5());
		rl.setScaleValue6(risk.getScaleValue6());
		rl.setStatus(risk.getStatus());
		rl.setSystemID(risk.getSystemID());
		rl.setType(risk.getType());
		rl.setValue(risk.getValue());
		rl.setValueFormat(risk.getValueFormat());
		rl.setValueSign(risk.getValueSign());
		rl.setValueSource(risk.getValueSource());
		rl.setValueType(risk.getValueType());
		rl.setValueUnit(risk.getValueUnit());
		rl.setCreationTime(new Date());
		rl.setBusiType(type);
		rl.setInitValue(risk.getInitValue());
		if (t == null) {
			if (risk.getObjectType() == Risk.OBJECT_TYPE_TASK) {
				t = taskDao.get(risk.getObjectID());
			}

		}
		if (t != null) {
			rl.setTaskSN(t.getSn());
			rl.setTaskName(t.getName());
			rl.setP3ID(t.getClerkID());
			rl.setBusiStartTime(t.getBusiStartTime());
			rl.setBusiEndTime(t.getBusiEndTime());
			List<TaskMatter> tms = taskMatterDao.findTaskMatterByTaskSn(t.getSn());
			if (tms != null) {
				if (tms.size() > 0) {
					TaskMatter tm = tms.get(0);
					rl.setItemID(tm.getMatterCode());
					rl.setItemName(tm.getMatterName());
				}
			}
		}
		
		//如果
		if (rl.getRefObjectId() != null) {
			// 有objectService就从objectService取，否则从objectInfoService取
			if (entityObjectLoader != null) {
				// if(objectService!=null){
				// System.out.println("rl.getRefObjectId()="+rl.getRefObjectId());
				// RFSObject o=objectService.getObject(rl.getRefObjectId());
				// System.out.println("o="+o.toJSONString());
				RFSObject o = entityObjectLoader.getEntityObject(rl.getRefObjectType(), rl.getRefObjectId());

				if (o != null) {
					rl.setProjectID(o.getId());
					rl.setProjectName(o.getName());

					try {
						String sn = (String) PropertyUtils.getProperty(o, "sn");
						rl.setProjectSn(sn);
					} catch (Exception e) {
						// e.printStackTrace();
						System.err.println(e.getMessage());
					}
					// rl.setDutyClerkID(ov.getDutyClerkID());
				}
			} else {/*
					 * ObjectInfoVO
					 * ov=objectInfoService.getObjectInfo(rl.getRefObjectId());
					 * //
					 * System.out.println(">>>>>>>>>ObjectInfoVO="+ov.getObjectID
					 * ()+"  "+ov.getObjectName()+"  "+ov.getObjectSN());
					 * if(ov!=null){ rl.setProjectID(ov.getObjectID());
					 * rl.setProjectSn(ov.getObjectSN());
					 * rl.setProjectName(ov.getObjectName());
					 * //rl.setDutyClerkID(ov.getDutyClerkID()); }
					 */
			}
		}

		if (rl.getValue() != null) {
			rl.setTimeUsed(rl.getValue().intValue());
		}
		if (rl.getScaleValue() != null) {
			if (rl.getValue() != null) {
				rl.setTimeRemain((rl.getScaleValue().subtract(rl.getValue()))
						.intValue());
			} else {
				rl.setTimeRemain((rl.getScaleValue()).intValue());
			}
		}

		return riskLogDao.save(rl);
	}

	public void riskRemindCopySend(Date start, Date end) {
		//Map<String,String> tmpTemplateValue = new HashMap<String,String>();
		List<Long> spl = peoplesDao.findDistinctAllSndPeople();
		if (spl != null) {
			for (Long sp : spl) {
				List<Peoples> pl = peoplesDao.fingBySndPeople(sp);
				List<List<Long>> gradeDutyClerkID = new ArrayList<List<Long>>();
				List<Long> gradeDutyClerkID101 = new ArrayList<Long>();
				List<Long> gradeDutyClerkID102 = new ArrayList<Long>();
				List<Long> gradeDutyClerkID103 = new ArrayList<Long>();
				List<Long> gradeDutyClerkID104 = new ArrayList<Long>();
				List<Long> gradeDutyClerkID105 = new ArrayList<Long>();
				List<Long> gradeDutyClerkID106 = new ArrayList<Long>();
				for (Peoples p : pl) {
					if (p.getType() == Peoples.TYPE_RISK_WHITE) {
						gradeDutyClerkID101.add(p.getFstPeople());
					} else if (p.getType() == Peoples.TYPE_RISK_BLUE) {
						gradeDutyClerkID102.add(p.getFstPeople());
					} else if (p.getType() == Peoples.TYPE_RISK_YELLOW) {
						gradeDutyClerkID103.add(p.getFstPeople());
					} else if (p.getType() == Peoples.TYPE_RISK_ORANGE) {
						gradeDutyClerkID104.add(p.getFstPeople());
					} else if (p.getType() == Peoples.TYPE_RISK_RED) {
						gradeDutyClerkID105.add(p.getFstPeople());
					} else if (p.getType() == Peoples.TYPE_RISK_BLACK) {
						gradeDutyClerkID106.add(p.getFstPeople());
					}
				}
				gradeDutyClerkID.add(gradeDutyClerkID101);
				gradeDutyClerkID.add(gradeDutyClerkID102);
				gradeDutyClerkID.add(gradeDutyClerkID103);
				gradeDutyClerkID.add(gradeDutyClerkID104);
				gradeDutyClerkID.add(gradeDutyClerkID105);
				gradeDutyClerkID.add(gradeDutyClerkID106);
				byte i = 1;
				for (List<Long> dcIDList : gradeDutyClerkID) {
					if (dcIDList != null && dcIDList.size() > 0) {
						// System.out.println("dcIDList"+dcIDList.toString());
						List<RiskLog> lrl = riskLogDao.findGradeChanged(start,
								end, i, dcIDList);
						HashMap<Integer, HashMap<Long,Long>> tmap = new HashMap<Integer, HashMap<Long,Long>>();
						for (RiskLog r : lrl) {
							if (tmap.get(r.getRefType()) != null) {
								if (tmap.get(r.getRefType()).get(
										r.getRefObjectId()) != null) {
									tmap.get(r.getRefType())
											.put(r.getRefObjectId(),
													((Long) (tmap.get(r
															.getRefType()).get(r
															.getRefObjectId())) + 1L));
								} else {
									tmap.get(r.getRefType()).put(
											r.getRefObjectId(), 1L);
								}
							} else {
								HashMap<Long,Long> pmap = new HashMap<Long,Long>();
								pmap.put(r.getRefObjectId(), 1L);
								tmap.put(r.getRefType(), pmap);
							}
							// System.out.println(r.getProjectName());
							// System.out.println(r.getTaskName());
							// System.out.println(r.getGrade());
						}

						Object[] os2 = tmap.keySet().toArray();
						for (int j = 0; j < os2.length; j++) {
							// System.out.println("task_type:"+os2[j]);
							// System.out.println("project_count"+tmap.get(os2[j]));
							Map<String,Object> tmpTemplateValue = Maps.newHashMap();
							tmpTemplateValue.put("${Project_Num}",
									String.valueOf(tmap.get(os2[j]).size()));
							List<EventMsg> le = eventMsgService
									.findEventMsgCfg(1001, 0L,	(Integer) os2[j], 0, 203L);
							for (EventMsg e : le) {
								if (e.getDutyerType() == (100 + i)) {
									eventMsgService.createBaseSmsg(e, Lists.newArrayList(sp), e.getTitle(),	e.getTemplet(),	tmpTemplateValue);
								}
							}
						}
					}

					i++;
				}
			}
		}
	}

	@Queryable(argNames = { "id" })
	public MonitorVO getMonitorVOByRiskLogID(Long id) {
		MonitorVO vo = null;
		RiskLog rl = riskLogDao.get(id);
		if (rl != null) {
			Task task = taskDao.get(rl.getObjectID());
			if (task != null) {
				vo = new MonitorVO();
				vo.setCategory(rl.getCategory());
				vo.setThingCode(rl.getThingCode());
				vo.setThingName(rl.getThingName());
				vo.setConclusion(rl.getConclusion());
				vo.setJuralLimit(rl.getJuralLimit());
				vo.setGrade(rl.getGrade());
				vo.setTaskCode(task.getCode());
				vo.setTimeLimit(task.getTimeLimit());
				vo.setGradeName(riskService.getGradeExplain(rl.getGrade()));
				vo.setTaskName(task.getName());
				vo.setDutyEntity(task.getDutyerID());
				if (task.getDutyerID() != null) {
					Org o = orgDao.get(task.getDutyerID());
					if (o != null) {
						vo.setDutyEntityAbbr(o.getAbbr());
					}
				}
				// 区分日、天计算
				if (RiskRule.VALUE_UNIT_WORKDAY == rl.getValueUnit()) {
					vo.setLimitTime(DateUtil.workdaysLater(task.getBeginTime(),
							task.getTimeLimit()));
				} else if (RiskRule.VALUE_UNIT_DAY == rl.getValueUnit()) {//
					//vo.setLimitTime(DateUtil.getDays(task.getBeginTime(), task.getTimeLimit(), Calendar.DATE));
					vo.setLimitTime(DateUtil.daysLater(task.getBeginTime(), task.getTimeLimit()));
				}

				BigDecimal remainTime = rl.getScaleValue();
				if (remainTime != null) {
					if (rl.getValue() != null) {
						vo.setRemainTime(remainTime.subtract(rl.getValue()));
					}
				}

				vo.setScaleValue(rl.getScaleValue());
				vo.setValue(rl.getValue());
				vo.setModificationTime(rl.getModificationTime());
				vo.setStatusName(task.getStatusName());
				vo.setStatus(task.getStatus());
				vo.setBeginTime(task.getBeginTime());
				vo.setEndTime(task.getEndTime());
				vo.setScaleMark(rl.getScaleMark());
				vo.setScaleValue1(rl.getScaleValue1());
				vo.setScaleValue2(rl.getScaleValue2());
				vo.setScaleValue3(rl.getScaleValue3());
				vo.setScaleValue4(rl.getScaleValue4());
				vo.setScaleValue5(rl.getScaleValue5());
				vo.setScaleValue6(rl.getScaleValue6());
				vo.setRemark(rl.getRemark());

				return vo;
			}
		}

		return null;
	}

	@Queryable(argNames = { "id" })
	public BizVO getTaskBusinessByRiskLogID(Long id) {
		BizVO vo = null;
		RiskLog rl = riskLogDao.get(id);
		if (rl != null) {
			vo = new BizVO();
			if (rl.getRefObjectType() == 1001) {
				ObjectInfoVO ov = objectInfoService.getObjectInfo(rl
						.getRefObjectId());
				if (ov != null) {
					vo.setObjectSn(ov.getObjectSN());
					vo.setObjectName(ov.getObjectName());
				}
			}

			Task task = taskDao.get(rl.getObjectID());
			if (task != null) {
				vo.setOrder(1L);
				vo.setCode(task.getCode());
				vo.setType(task.getType());
				vo.setStatus(task.getStatus());
				vo.setTypeName(glossaryService.getByCategoryAndCode(
						BizVO.GLOSSARY_TASK_CODE, task.getType()));
				vo.setBeginTime(task.getBeginTime());
				vo.setEndTime(task.getEndTime());
				vo.setTimeLimit(task.getTimeLimit());
				vo.setSn(task.getSn());
				short timeUsed = (short) (task.getTimeUsed()
						- task.getTimeHang() - task.getTimeDelay());
				vo.setTimeUsed(timeUsed);
				vo.setRemarks(task.getRemark());
				vo.setClerkID(task.getClerkID());
				vo.setDutyer(task.getDutyerID());
				if (task.getClerkID() != null) {
					Clerk clerk = clerkService.getClerk(task.getClerkID());
					if (clerk != null) {
						vo.setClerkName(clerk.getName());
					}
				}
				if (task.getDutyerID() != null) {
					Org org = orgDao.get(task.getDutyerID());
					if (org != null) {
						vo.setDutyName(org.getAbbr());
					}
				}
			}
		}

		return vo;
	}

	/**
	 * @return the entityObjectLoader
	 */
	public EntityObjectLoader getEntityObjectLoader() {
		return entityObjectLoader;
	}

	/**
	 * @param entityObjectLoader
	 *            the entityObjectLoader to set
	 */
	public void setEntityObjectLoader(EntityObjectLoader entityObjectLoader) {
		this.entityObjectLoader = entityObjectLoader;
	}
}
