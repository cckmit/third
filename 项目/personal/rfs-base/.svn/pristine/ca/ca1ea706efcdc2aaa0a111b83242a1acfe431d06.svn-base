package cn.redflagsoft.base.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.redflagsoft.base.bean.ArchivingStatus;
import cn.redflagsoft.base.bean.InfoConfig;
import cn.redflagsoft.base.bean.InfoDetail;
import cn.redflagsoft.base.bean.InfoStat;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.dao.InfoConfigDao;
import cn.redflagsoft.base.dao.InfoDetailDao;
import cn.redflagsoft.base.dao.InfoStatDao;
import cn.redflagsoft.base.service.EntityObjectLoader;
import cn.redflagsoft.base.service.InfoStatHandler;
import cn.redflagsoft.base.service.InfoStatService;
import cn.redflagsoft.base.service.TaskService;

import com.google.common.collect.Lists;

public class InfoStatServiceImpl implements InfoStatService {
	private static final Log log = LogFactory.getLog(InfoStatServiceImpl.class);

//	@SuppressWarnings("rawtypes")
//	private ObjectService objectService;
//	private ObjectFinder<RFSObject> objectService;
//	private int objectType;
//	private String entityName;
	private TaskService taskService;
	private InfoDetailDao infoDetailDao;
	private InfoStatDao infoStatDao;
	private InfoConfigDao infoConfigDao;
	private EntityObjectLoader entityObjectLoader;
	private List<InfoStatHandler> handlers;
	
	
	
	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public List<InfoStatHandler> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<InfoStatHandler> handlers) {
		this.handlers = handlers;
	}

	public InfoConfigDao getInfoConfigDao() {
		return infoConfigDao;
	}

	public void setInfoConfigDao(InfoConfigDao infoConfigDao) {
		this.infoConfigDao = infoConfigDao;
	}

	public InfoDetailDao getInfoDetailDao() {
		return infoDetailDao;
	}

	public void setInfoDetailDao(InfoDetailDao infoDetailDao) {
		this.infoDetailDao = infoDetailDao;
	}

	public InfoStatDao getInfoStatDao() {
		return infoStatDao;
	}

	public void setInfoStatDao(InfoStatDao infoStatDao) {
		this.infoStatDao = infoStatDao;
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

	public void stat(int objectType) {
		removeInfoStat(objectType);
        ResultFilter filter = new ResultFilter(ArchivingStatus.VALID_STATUS_CRITERION, null);
        List<RFSObject> objects = entityObjectLoader.findEntityObjects(objectType, filter);
//        List<RFSObject> objects = <objectService>.findObjects();//注意，objectService应该注入具体对象服务类，比如projectService或者contractService
        for(RFSObject o: objects){
            statAndSave(o);
        }
	}
	
	public void statForDevDebug(int objectType, int limit) {
		removeInfoStat(objectType);
        ResultFilter filter = new ResultFilter(ArchivingStatus.VALID_STATUS_CRITERION, null, 0, limit);
        List<RFSObject> objects = entityObjectLoader.findEntityObjects(objectType, filter);
//        List<RFSObject> objects = <objectService>.findObjects();//注意，objectService应该注入具体对象服务类，比如projectService或者contractService
        for(RFSObject o: objects){
            statAndSave(o);
        }
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void removeInfoStat(int objectType){
		clearInfoDetails(objectType);//删除上次统计结果明细
        clearInfoStats(objectType);//删除上次统计结果
	}
	
	/**
	 * @param objectType2
	 */
	private void clearInfoStats(int objectType) {
		infoStatDao.removeByObjectType(objectType);
	}

	/**
	 * @param objectType2
	 */
	private void clearInfoDetails(int objectType) {
		infoDetailDao.removeByObjectType(objectType);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public List<InfoDetail> stat(RFSObject o) {
		List<InfoDetail> result = Lists.newArrayList();
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		SimpleExpression eq2 = Restrictions.eq("objectType", o.getObjectType());
		SimpleExpression lt = Restrictions.le("tag", o.getTag());
		SimpleExpression eq = Restrictions.eq("status", InfoConfig.STATUS_VALID);
		Logic logic = Restrictions.logic(eq2).and(lt).and(eq);
		filter.setCriterion(logic);
		filter.setOrder(Order.asc("displayOrder"));
		// ("from InfoConfig a where a.objectType=? and a.tag <= ? and a.status=?",o.objectType, o.tag, STATUS_VALID);
		List<InfoConfig> configList = infoConfigDao.find(filter);
		if(log.isDebugEnabled()){
			log.debug(String.format("查询到对象（%s）的规则共 %s 条。", o, configList.size()));
		}
		//针对每个信息项进行处理
		for (InfoConfig config : configList) {
			InfoDetail d = stat(o, config);
			if(d != null){
				result.add(d);
			}
		}
		return result;
	}

	/**
	 * 统计指定对象的指定信息项
	 * @param o
	 * @param config
	 * @return
	 */
	private InfoDetail stat(RFSObject o, InfoConfig config) {
		if(config.getRefTaskType() > 0){
			if(isExistsRefTaskStatus12(o, config)){
				log.debug("相关的Task免办，当前统计项被忽略：" + config);
				return null;
			}
		}
		
		InfoStatHandler handler = getHandler(o, config);
		byte status = handler.getStatStatus(o, config);
		DutyEntity dutyEntity = getDutyEntity(o, config);
		InfoDetail detail = new InfoDetail();
		detail.setObjectType(o.getObjectType());
		detail.setObjectId(o.getId());
		detail.setInfoConfigId(config.getId());
		detail.setStatus(status);
		detail.setDutyEntityId(dutyEntity.getId());
		detail.setDutyEntityName(dutyEntity.getName());
		return detail;
	}
	
	/**
	 * 根据一定的策略获取信息项的责任单位ID和名称，例如config中dutyEntityId!=0时取config中的值，等于0时取项目的责任单位。
	 * 
	 * @param o
	 * @param config
	 * @return
	 */
	protected DutyEntity getDutyEntity(RFSObject o, InfoConfig config) {
		if(config.getDutyEntityId() == null || config.getDutyEntityId().longValue() == 0L){
			return new DutyEntity(o.getDutyEntityID(), o.getDutyEntityName());
		}else{
			return new DutyEntity(config.getDutyEntityId(), config.getDutyEntityName());
		}
	}

	private InfoStatHandler getHandler(RFSObject o, InfoConfig config) {
		for (InfoStatHandler handler : handlers) {
			if (handler.supports(o, config)) {
				return handler;
			}
		}
		throw new RuntimeException("InfoStatHandler not found");
	}

	// 统计某个对象，并保存结果
	void statAndSave(RFSObject o) {
		List<InfoDetail> details = stat(o);
		for (InfoDetail d : details) {
			saveInfoDetail(d);
			addInfoDetailToStat(d);
		}
	}

	/**
	 * @param d
	 * @return
	 */
	public InfoDetail saveInfoDetail(InfoDetail d) {
		if (d.getStatus() != InfoDetail.STATUS_NONE){
			return infoDetailDao.save(d);
		}
		return null;
	}

	public InfoStat addInfoDetailToStat(InfoDetail d) {
		// 无需办理的不统计
		if (d.getStatus() != InfoDetail.STATUS_NONE) {
			// ("from InfoStat a where a.infoConfigId=? and a.dutyEntityId=?", d.infoConfigId, d.dutyEntityId);
			SimpleExpression eq = Restrictions.eq("infoConfigId", d.getInfoConfigId());
			Logic logic = Restrictions.logic(eq).and(Restrictions.eq("dutyEntityId", d.getDutyEntityId()));
			
			InfoStat s = infoStatDao.get(logic);

			boolean isNew = false;
			
			if (s == null) {
				isNew = true;
				s = new InfoStat();
				s.setInfoConfigId(d.getInfoConfigId());
				s.setObjectType(d.getObjectType());
				s.setCompleteInfoCount(0);
				s.setIncompleteInfoCount(0);
				s.setDutyEntityId(d.getDutyEntityId());
				s.setDutyEntityName(d.getDutyEntityName());
			}

			if (d.getStatus() == InfoDetail.STATUS_COMPLETE) {
				s.setCompleteInfoCount(s.getCompleteInfoCount() + 1);
			} else if (d.getStatus() == InfoDetail.STATUS_INCOMPLETE) {
				s.setIncompleteInfoCount(s.getIncompleteInfoCount() + 1);
			}
			return isNew ? saveInfoStat(s) : updateInfoStat(s);
		}
		return null;
	}

	private InfoStat saveInfoStat(InfoStat s) {
		return infoStatDao.save(s);
	}

	private InfoStat updateInfoStat(InfoStat s) {
		return infoStatDao.update(s);
	}
	
	/**
	 * 是否存在相关task，且相关的task的状态是免办的？
	 * @param o
	 * @param config
	 * @return
	 */
	private boolean isExistsRefTaskStatus12(RFSObject o, InfoConfig config){
		ResultFilter filter1 = ResultFilter.createEmptyResultFilter();	
		SimpleExpression e = Restrictions.eq("refObjectId", o.getId());
		SimpleExpression e2 = Restrictions.eq("refObjectType", o.getObjectType());
		SimpleExpression e3 = Restrictions.eq("type", config.getRefTaskType());
		Logic and = Restrictions.logic(e).and(e2).and(e3).and(Restrictions.eq("status", Task.STATUS_免办));
		
		//根据task判断是否是免办，如果是免办状态则不统计。
		filter1.setCriterion(and);
		int taskCount = taskService.getTaskCount(filter1);
		return taskCount > 0;
	}
	
	/**
	 * 
	 *
	 */
	public static class DutyEntity implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 5252994374078435874L;
		
		private Long id;
		private String name;
		/**
		 * @param id
		 * @param name
		 */
		public DutyEntity(Long id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		/**
		 * 
		 */
		public DutyEntity() {
			super();
		}
		/**
		 * @return the id
		 */
		public Long getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(Long id) {
			this.id = id;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
	}
}
