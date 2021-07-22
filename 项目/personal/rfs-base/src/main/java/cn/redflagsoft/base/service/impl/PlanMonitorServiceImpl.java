/*
 * $Id: PlanMonitorServiceImpl.java 4891 2011-10-11 03:40:19Z lcj $
 * WorkServiceImpl.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.redflagsoft.base.bean.ObjectRiskGrade;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.dao.DepartmentDao;
import cn.redflagsoft.base.dao.GlossaryDao;
import cn.redflagsoft.base.dao.ObjectDao;
import cn.redflagsoft.base.dao.OrgDao;
import cn.redflagsoft.base.dao.PeopleDao;
import cn.redflagsoft.base.service.PlanMonitorService;
import cn.redflagsoft.base.vo.statistics.GradeGroup;
import cn.redflagsoft.base.vo.statistics.LifeStage;
import cn.redflagsoft.base.vo.statistics.PlanMonitor;
import cn.redflagsoft.base.vo.statistics.PlanMonitorResult;

/**
 * 
 * @author ymq
 *
 */
public class PlanMonitorServiceImpl implements PlanMonitorService {
	public static final Log log = LogFactory.getLog(PlanMonitorServiceImpl.class);
	private ObjectDao<RFSObject> objectDao;
	private GlossaryDao glossaryDao;
	//private RiskDao riskDao;
	private OrgDao orgDao;
	private DepartmentDao departmentDao;
	private PeopleDao peopleDao;

	public void setObjectDao(ObjectDao<RFSObject> objectDao) {
		this.objectDao = objectDao;
	}

	public void setGlossaryDao(GlossaryDao glossaryDao) {
		this.glossaryDao = glossaryDao;
	}

//	public void setRiskDao(RiskDao riskDao) {
//		this.riskDao = riskDao;
//	}

	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public void setPeopleDao(PeopleDao peopleDao) {
		this.peopleDao = peopleDao;
	}

	public PlanMonitorResult getPlanMonitorResult(Integer objectType, Byte riskValueType) {
		//生成 PlanMonitorResult
		PlanMonitorResult pmr = new PlanMonitorResult();
		//XXX 2011-08-21
		pmr.setLifeStages(findLifeStages(objectType.shortValue()));
		//System.out.println(pmr.getLifeStages());
		
		//获取 objectID, grade 列表
		//List array = riskDao.findRiskByType(objectType);
		List<ObjectRiskGrade> objectList = objectDao.findObjectGroupByRiskGrade(objectType, riskValueType);
		List<PlanMonitor> planMonitors = processPlanMonitors(pmr.getLifeStages(), objectList);
		
		//pmr.setPlanMonitors(this.doPlanMonitorList(pmr.getLifeStages(), array));
		
		pmr.setPlanMonitors(planMonitors);
		
		pmr.calculatePlanMonitors();
		return pmr;
	}
	


	/**
	 * 生成 List<LifeStage> 列表
	 * 
	 * @param category
	 * @return List<LifeStage>
	 */
	private List<LifeStage> findLifeStages(short category) {
		List<LifeStage> elements = new ArrayList<LifeStage>();
		Map<Integer, String> glossaryMap = glossaryDao.findByCategory(category);
		if(null == glossaryMap || glossaryMap.isEmpty()) {
			log.warn("查询 Glossary 没有找到 category 为:" + category + " 的相关记当,返回 List<LifeStage> 空集合");
		} else {
			//System.out.println("------------------------------------------------");
			//System.out.println(glossaryMap);
			for(Integer key : glossaryMap.keySet()) {
				elements.add(new LifeStage(key, glossaryMap.get(key)));
			}
			//System.out.println(elements);
		}
		return elements;
	}
	
	/**
	 * @param lifeStages 
	 * @param objectList 
	 * @return
	 */
	private List<PlanMonitor> processPlanMonitors(List<LifeStage> lifeStages, List<ObjectRiskGrade> objectList) {
		List<PlanMonitor> pmList = new ArrayList<PlanMonitor>();
		if(lifeStages == null || lifeStages.isEmpty()) {
			log.warn("List<LifeStage>为空或长度为0操作忽略!");
			return pmList;
		} 
		if(objectList == null || objectList.isEmpty()) {
			log.warn("Risk List Field [ObjectId, Grade] 为空或长度为0操作忽略!");
			return pmList;
		}
		
		Map<String, List<ObjectRiskGrade>> map = listGroupByManager(objectList);
		
		for(Map.Entry<String, List<ObjectRiskGrade>> en: map.entrySet()){
			String key = en.getKey();
			List<ObjectRiskGrade> tmp = en.getValue();
			PlanMonitor pm = new PlanMonitor();
			
			RFSObject managerKey = parseManagerKey(key);
			Object manager = doManager(managerKey.getManagerType(), managerKey.getManager());
			
			pm.setManager(manager);
			pm.setManagerType(managerKey.getManagerType());
			
			for(LifeStage ls: lifeStages){
				pm.addGradeGroup(ls.getStage(), new GradeGroup());
				log.debug("创建stage的GradeGroup：" + ls.getStage());
			}
			
			for(RFSObject obj: tmp){
				log.debug("查找相应stage的GradeGroup：" + obj.getLifeStage());
				GradeGroup gradeGroup = pm.getGradeGroup(obj.getLifeStage());
				switch(obj.getRiskGrade()) {
					case Risk.GRADE_NORMAL: gradeGroup.getNormal().plusCount(); break;
					case Risk.GRADE_BLUE: gradeGroup.getBlue().plusCount(); break;
					case Risk.GRADE_YELLOW: gradeGroup.getYellow().plusCount(); break;
					case Risk.GRADE_RED: gradeGroup.getRed().plusCount(); break;
				}
			}
			
			pm.calculateStatistic();
			pmList.add(pm);
		}
		
			
		return pmList;
	}
	
	
	private String buildManagerKey(ObjectRiskGrade obj){
		return obj.getManager() + ";" + obj.getManagerType();
	}
	
	private ObjectRiskGrade parseManagerKey(String key){
		String[] strArr = key.split(";");
		if(strArr.length != 2){
			throw new IllegalArgumentException("存储manager的key不正确。");
		}
		ObjectRiskGrade obj = new ObjectRiskGrade();
		obj.setManager(Long.valueOf(strArr[0]));
		obj.setManagerType(Byte.parseByte(strArr[1]));
		return obj;
	}
	
	
	private Map<String, List<ObjectRiskGrade>> listGroupByManager(List<ObjectRiskGrade> list){
		Map<String, List<ObjectRiskGrade>> map = new HashMap<String, List<ObjectRiskGrade>>(); 
		List<ObjectRiskGrade> tmp = null;
		for(ObjectRiskGrade obj: list){
			if(obj.getManager() == null || obj.getManagerType() == 0 || obj.getManager().longValue() == 0){
				log.warn("RFSObject 的manager或者managerType无效，忽略这条记录：manager="
						+ obj.getManager() + ", managerType=" + obj.getManagerType());
				continue;
			}	
			
			
			String key = buildManagerKey(obj);
			tmp = map.get(key);
			if(tmp == null){
				tmp = new ArrayList<ObjectRiskGrade>();
				map.put(key, tmp);
			}
			
			tmp.add(obj);
		}
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
//	private List<PlanMonitor> doPlanMonitorList(List<LifeStage> lifeStageList, List array) {
//		List<PlanMonitor> pmList = new ArrayList<PlanMonitor>();
//		if(lifeStageList == null || lifeStageList.isEmpty()) {
//			log.warn("List<LifeStage>为空或长度为0操作忽略!");
//		} else if(array == null || array.isEmpty()) {
//			log.warn("Risk List Field [ObjectId, Grade] 为空或长度为0操作忽略!");
//		} else {
//			
//			Map<String, List<Object[]>>  map = doMap(array);
//			//System.out.println(map);
//			PlanMonitor pm = null;
//			Object[] tmp = null;
//			for(Map.Entry<String, List<Object[]>> enn: map.entrySet()){
//				pm = new PlanMonitor();
//				List<Object[]> objList = enn.getValue();
//				String key = enn.getKey();
//				String[] keys = key.split(";");
//				Long managerId = Long.valueOf(keys[0]);
//				byte managerType = Byte.valueOf(keys[1]);
//				Object manager = doManager(managerType, managerId);
//				
//				pm.setManager(manager);
//				pm.setManagerType(managerType);
//				
//				for(LifeStage stage: lifeStageList){
//					pm.addGradeGroup(stage.getStage(), new GradeGroup());
//					log.debug("创建stage的GradeGroup：" + stage.getStage());
//				}
//				
//				for(Object[] arr: objList){
//					Byte grade = (Byte) arr[0];
//					short stage = (Short) arr[1];
//					log.debug("查找相应stage的GradeGroup：" + stage);
//					switch(grade) {
//						case Risk.GRADE_NORMAL: pm.getGradeGroup(stage).getNormal().plusCount(); break;
//						case Risk.GRADE_BLUE: pm.getGradeGroup(stage).getBlue().plusCount(); break;
//						case Risk.GRADE_YELLOW: pm.getGradeGroup(stage).getYellow().plusCount(); break;
//						case Risk.GRADE_RED: pm.getGradeGroup(stage).getRed().plusCount(); break;
//					}
//				}
//				
//				pm.calculateStatistic();
//				pmList.add(pm);
//			}
//			
//			
//			
//			
//			
//			
//			
//			
//			
//			/*
//			
//			//生成 Map,键 Manager 值 [grade, lifestage, managerType]
//			Map<Long, Object[]> domainMap = this.doDomainMap(array);
//			
//			
//			//定义 Map 值 Object[]数组下标索引
//			int GRADE_INDEX = 0;
//			int LIFESTAGE_INDEX = 1;
//			int MANAGERTYPE_INDEX = 2;
//			//转换为 List<PlanMonitor>
//			PlanMonitor pm = null;
//			Object[] tmp = null;
//			//遍历 Map
//			for(Long manager : domainMap.keySet()) {
//				pm = new PlanMonitor();
//				tmp = domainMap.get(manager);
//				for(LifeStage node : lifeStageList) {
//					//设置ManagerType
//					pm.setManagerType((Byte)tmp[MANAGERTYPE_INDEX]);
//					//生成 Manager 对象
//					pm.setManager(this.doManager((Byte)tmp[MANAGERTYPE_INDEX], manager));
//					//GradeGroup加入队列
//					if(pm.getGradeGroup(node.getStage()) == null) {
//						//添加 GradeGroup
//						pm.addGradeGroup(node.getStage(), new GradeGroup());
//						//生成 Risk Count
//						switch((Byte)tmp[GRADE_INDEX]) {
//						case Risk.GRADE_NORMAL: pm.getGradeGroup(node.getStage()).getNormal().plusCount(); break;
//						case Risk.GRADE_WARNING: pm.getGradeGroup(node.getStage()).getWarning().plusCount(); break;
//						case Risk.GRADE_YELLOW: pm.getGradeGroup(node.getStage()).getYellow().plusCount(); break;
//						case Risk.GRADE_RED: pm.getGradeGroup(node.getStage()).getRed().plusCount(); break;
//						}
//					}
//				}
//				//计算合计
//				pm.calculateStatistic();
//				//PlanMonitor加入队列
//				pmList.add(pm);
//			}
//			
//			*/
//		}
//		return pmList;
//	}
	
//	private Map<String, List<Object[]>> doMap(List<Object[]> list){
//		Object[] array = null;
//		RFSObject rfs = null;
//		int OBJECTID_INDEX = 0;
//		int GRADE_INDEX = 1;
//		Map<String, List<Object[]>> map = new HashMap<String, List<Object[]>>();
//		for (int i = 0; i < list.size(); i++) {
//			// list 中存放的是二维数组
//			array = (Object[]) list.get(i);
//			// 获取Risk 的 Object 实例
//			rfs = objectDao.get((Long) array[OBJECTID_INDEX]);
//			if(null == rfs) {
//				log.warn("RFSObject id:" + array[OBJECTID_INDEX] + "对象没有找到当前操作忽略!");
//				continue;
//			}
//			if(rfs.getManager() == null || rfs.getManagerType() == 0 || rfs.getManager().longValue() == 0){
//				log.warn("RFSObject 的manager或者managerType无效，忽略这条记录：manager=" +
//						rfs.getManager() + ", managerType=" + rfs.getManagerType());
//				continue;
//			}
//			
//			String key = rfs.getManager() + ";" + rfs.getManagerType();
//			List<Object[]> tmp = map.get(key);
//			if(tmp == null){
//				tmp = new ArrayList<Object[]>();
//				map.put(key, tmp);
//			}
//			tmp.add(new Object[] { array[GRADE_INDEX],	rfs.getLifeStage(), rfs.getManagerType(), rfs.getManager()});
//		}
//		return map;
//	}
	
//	/**
//	 * 组装域Map实例
//	 * 
//	 * @param list
//	 * @return Map
//	 */
//	private Map<Long, Object[]> doDomainMap(List list) {
//		Object[] array = null;
//		RFSObject rfs = null;
//		int OBJECTID_INDEX = 0;
//		int GRADE_INDEX = 1;
//		Map<Long, Object[]> map = new HashMap<Long, Object[]>();
//		for (int i = 0; i < list.size(); i++) {
//			// list 中存放的是二维数组
//			array = (Object[]) list.get(i);
//			// 获取Risk 的 Object 实例
//			rfs = objectDao.get((Long) array[OBJECTID_INDEX]);
//			if(null == rfs) {
//				log.warn("RFSObject id:" + array[OBJECTID_INDEX] + "对象没有找到当前操作忽略!");
//				continue;
//			}
//			// Map 过滤 Risk 实例
//			if (null == map.get(rfs.getManager())) {
//				map.put(rfs.getManager(), new Object[] { array[GRADE_INDEX],
//						rfs.getLifeStage(), rfs.getManagerType() });
//			}
//		}
//		return map;
//	}
	
	
	
	/**
	 * 组装Manager, Manger对象为其中任意种对象 [Org, Department, People]
	 * 
	 * @param objs
	 * @return Object
	 */
	private Object doManager(byte managerType, Long manger) {
		//根据 ManagerType 生成 Manager 对象
		switch(managerType) {
		//单位
		case RFSObject.MANAGER_TYPE_ORG: return orgDao.get(manger);
		//部门
		case RFSObject.MANAGER_TYPE_DEPARTMENT: return departmentDao.get(manger);
		//个人
		case RFSObject.MANAGER_TYPE_PEOPLE: return peopleDao.get(manger);
		default:
			log.warn("ManagerType:" + managerType + "未找到操作忽略!");
			return null;
		}
	}	
	
	
//	private class Manager implements java.io.Serializable{
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = -5727487903099548592L;
//		public Long manager;
//		public byte managerType;
//		public Manager(){};
//		public Manager(Long id, byte type){
//			manager = id;
//			managerType = type;
//		}
//		
//		public boolean equals(Object o){
//			if(o == this) return true;
//			if(o instanceof Manager){
//				Manager oo = (Manager) o;
//				return this.manager.equals(oo.manager) && this.managerType == oo.managerType;
//			}
//			return false;
//		}
//		
//	}
	
	
	

	public PlanMonitorResult getObjectRiskSummaryByManager(Integer objectType, Integer riskType, Short objectStatus, Short year,int key,Long entityID) {

		//生成 PlanMonitorResult
		PlanMonitorResult pmr = new PlanMonitorResult();
		//XXX 2011-08-21
		pmr.setLifeStages(findLifeStages(objectType.shortValue()));
		

		List<RFSObject> objectList = objectDao.findObjectGroupByRiskGrade(objectType, riskType, objectStatus, year,key,entityID);

		
		List<PlanMonitor> planMonitors = processPlanMonitorsAll(pmr.getLifeStages(), objectList);
		
		pmr.setPlanMonitors(planMonitors);
		
		pmr.calculatePlanMonitors();
		return pmr;
	}
	
	/**
	 * @param lifeStages 
	 * @param objectList 
	 * @return
	 */
	private List<PlanMonitor> processPlanMonitorsAll(List<LifeStage> lifeStages, List<RFSObject> objectList) {
		List<PlanMonitor> pmList = new ArrayList<PlanMonitor>();
		if(lifeStages == null || lifeStages.isEmpty()) {
			log.warn("List<LifeStage>为空或长度为0操作忽略!");
			return pmList;
		} 
		if(objectList == null || objectList.isEmpty()) {
			log.warn("Risk List Field [ObjectId, Grade] 为空或长度为0操作忽略!");
			return pmList;
		}
		
		Map<String, List<RFSObject>> map = listGroupByManagerAll(objectList);
		
		for(Map.Entry<String, List<RFSObject>> en: map.entrySet()){
			String key = en.getKey();
			List<RFSObject> tmp = en.getValue();
			PlanMonitor pm = new PlanMonitor();
			
			RFSObject managerKey = parseManagerKey(key);
			Object manager = doManager(managerKey.getManagerType(), managerKey.getManager());
			
			pm.setManager(manager);
			pm.setManagerType(managerKey.getManagerType());
			
			for(LifeStage ls: lifeStages){
				pm.addGradeGroup(ls.getStage(), new GradeGroup());
				log.debug("创建stage的GradeGroup：阶段：" + ls.getStage());
			}
			
			for(RFSObject obj: tmp){
				log.debug("查找相应stage的GradeGroup：阶段：" + obj.getLifeStage() + ", getRiskGrade=" + obj.getRiskGrade());
				GradeGroup gradeGroup = pm.getGradeGroup(obj.getLifeStage());
				switch(obj.getRiskGrade()) {
					case Risk.GRADE_NORMAL: gradeGroup.getNormal().plusCount(); break;
					case Risk.GRADE_BLUE: gradeGroup.getBlue().plusCount(); break;
					case Risk.GRADE_YELLOW: gradeGroup.getYellow().plusCount(); break;
					case Risk.GRADE_RED: gradeGroup.getRed().plusCount(); break;
					case Risk.GRADE_WHITE: gradeGroup.getWhite().plusCount(); break;
					case Risk.GRADE_ORANGE: gradeGroup.getOrange().plusCount(); break;
					case Risk.GRADE_BLACK: gradeGroup.getBlack().plusCount(); break;
				}
			}
			//System.out.println(pm);
			pm.calculateStatistic();
			pmList.add(pm);
		}
		
			
		return pmList;
	}
	
	private Map<String, List<RFSObject>> listGroupByManagerAll(List<RFSObject> list){
		Map<String, List<RFSObject>> map = new LinkedHashMap<String, List<RFSObject>>(); 
		List<RFSObject> tmp = null;
		for(RFSObject obj: list){
			if(obj.getManager() == null || obj.getManagerType() == 0 || obj.getManager().longValue() == 0){
				log.warn("RFSObject 的manager或者managerType无效，忽略这条记录：manager="
						+ obj.getManager() + ", managerType=" + obj.getManagerType());
				continue;
			}	
			
			
			String key = buildManagerKey(obj);
			tmp = map.get(key);
			if(tmp == null){
				tmp = new ArrayList<RFSObject>();
				map.put(key, tmp);
			}
			
			tmp.add(obj);
		}
		return map;
	}
	
	private String buildManagerKey(RFSObject obj){
		return obj.getManager() + ";" + obj.getManagerType();
	}	
}