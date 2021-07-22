package cn.redflagsoft.base.service.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.cache.Cache;
import org.opoo.cache.NullCache;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import cn.redflagsoft.base.aop.interceptor.ParametersAware;
import cn.redflagsoft.base.bean.EventMsg;
import cn.redflagsoft.base.bean.Peoples;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.dao.EventMsgDao;
import cn.redflagsoft.base.dao.PeoplesDao;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeException;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.security.SchedulerUser;
import cn.redflagsoft.base.service.EventMsgService;
import cn.redflagsoft.base.service.HolidayService;
import cn.redflagsoft.base.service.ObjectService;
import cn.redflagsoft.base.service.SmsgTemplateService;
import cn.redflagsoft.base.service.TaskService;
import cn.redflagsoft.base.util.DateUtil;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class EventMsgServiceImpl implements EventMsgService {
	public static final String PROP_EVENTMSG_EXCEPTION_IF_FAILED = "eventmsg.exception.if.failed";
	public static final String CREATE_SMSG_WORKSCHEME_NAME = "createSmsg";
	
	public static final Log log = LogFactory.getLog(EventMsgServiceImpl.class);
	

	private EventMsgDao eventMsgDao;
	private HolidayService holidayService;

	private ObjectService<?> objectService;
	private TaskService taskService;
	private PeoplesDao peoplesDao;
	private SchemeManager schemeManager;
	private SmsgTemplateService msgTemplateService;
	private Cache<Long, EventMsg> eventMsgCache = new NullCache<Long, EventMsg>();
	private Cache<String,List<Long>> eventMsgListCache = new NullCache<String,List<Long>>();
	

	public SmsgTemplateService getMsgTemplateService() {
		return msgTemplateService;
	}

	public void setMsgTemplateService(SmsgTemplateService msgTemplateService) {
		this.msgTemplateService = msgTemplateService;
	}

	public SchemeManager getSchemeManager() {
		return schemeManager;
	}

	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public ObjectService<?> getObjectService() {
		return objectService;
	}

	public void setObjectService(ObjectService<?> objectService) {
		this.objectService = objectService;
	}

	public HolidayService getHolidayService() {
		return holidayService;
	}

	public void setHolidayService(HolidayService holidayService) {
		this.holidayService = holidayService;
	}

	public EventMsgDao getEventMsgDao() {
		return eventMsgDao;
	}

	public void setEventMsgDao(EventMsgDao eventMsgDao) {
		this.eventMsgDao = eventMsgDao;
	}


	/**
	 * @return the eventMsgCache
	 */
	public Cache<Long, EventMsg> getEventMsgCache() {
		return eventMsgCache;
	}

	/**
	 * @param eventMsgCache the eventMsgCache to set
	 */
	public void setEventMsgCache(Cache<Long, EventMsg> eventMsgCache) {
		this.eventMsgCache = eventMsgCache;
	}

	/**
	 * @return the eventMsgListCache
	 */
	public Cache<String, List<Long>> getEventMsgListCache() {
		return eventMsgListCache;
	}

	/**
	 * @param eventMsgListCache the eventMsgListCache to set
	 */
	public void setEventMsgListCache(Cache<String, List<Long>> eventMsgListCache) {
		this.eventMsgListCache = eventMsgListCache;
	}
	
	/**
	 * @return the peoplesDao
	 */
	public PeoplesDao getPeoplesDao() {
		return peoplesDao;
	}

	/**
	 * @param peoplesDao the peoplesDao to set
	 */
	public void setPeoplesDao(PeoplesDao peoplesDao) {
		this.peoplesDao = peoplesDao;
	}

	public List<EventMsg> findEventMsgCfg(int objectType, Long objectID, int jobType, int taskType,
			int workType, int processType, Long eventType) {
		
		String cacheKey = buildCacheKey(objectType, objectID, jobType, taskType, workType, processType, eventType);
		List<EventMsg> list = getEventMsgsFromCache(cacheKey);
		if(list == null){
			list = eventMsgDao.findEventMsgCfg(objectType, objectID, jobType, taskType, workType, processType, eventType);
			if(list != null){
				putEventMsgsIntoCache(cacheKey, list);
			}
		}else{
			log.debug("Load EventMsgs from cache: " + cacheKey);
		}
		
		return list;
		
		//return eventMsgDao.findEventMsgCfg(objectType, objectID, jobType, taskType, workType, processType, eventType);
	}

	public List<EventMsg> findEventMsgCfg(int objectType, Long objectID, int taskType, int workType,
			Long eventType) {
		return findEventMsgCfg(objectType, objectID, (int) 0, taskType, workType, (int) 0, eventType);
	}

	public List<EventMsg> findEventMsgCfg(int objectType, Long objectID, Long eventType) {
		return findEventMsgCfg(objectType, objectID, (int) 0, (int) 0, (int) 0, (int) 0, eventType);
	}

	//call by RiskServiceImpl
	public List<Smsg> dealEventMsg(int objectType, Long objectID, int taskType, Long taskSN, int workType, short bizAction) {
		return dealEventMsg(objectType, objectID, taskType, taskSN, workType, bizAction, null);
	}

	public List<Smsg> dealEventMsg(int objectType, Long objectID, int taskType, Long taskSN, int workType,
			short bizAction, Map<String, Object> context) {

		List<EventMsg> eventMsgs = findEventMsgCfg(objectType, objectID, taskType, workType, (long) bizAction);
		if(eventMsgs == null || eventMsgs.isEmpty()){
			return Collections.emptyList();
		}

		List<Smsg> results = Lists.newArrayList();
		if (log.isDebugEnabled()) {
			String s = String.format(
					"查找Event-Msg-Config(objectType=%s, objectID=%s, taskType=%s, workType=%s, eventType=%s): %s 条",
					objectType, objectID, taskType, workType, bizAction, eventMsgs.size());
			log.debug(s);
		}

			for (EventMsg em : eventMsgs) {
				if (log.isDebugEnabled()) {
					log.debug("EventMsg: " + em.getTitle() + " - IsCreateMsg: " + em.getIsCreateMsg());
				}

				if (em.getIsCreateSms() == EventMsg.IS_TRUE) {
					List<Smsg> msgs = createSmsgByEventMsg(em, objectID, taskSN, context);
					results.addAll(msgs);
				}
			}
		return results;
	}



	public List<Smsg> createSmsgByEventMsg(EventMsg em, Long objectID, Long taskSN, Map<String,Object> context) {
		//String receivers = "0:" + em.getDutyerID();
		List<Long> receiverIds = Lists.newArrayList(em.getDutyerID());
		if ((em.getDutyerType() == EventMsg.DUTYER_TYPE_PROJECT_DIRECTOR)
				|| (em.getDutyerType() >= 101 && em.getDutyerType() <= 106)) {
			//必须在下面重新赋值
			receiverIds = Collections.emptyList();
			
			RFSObject o = objectService.getObject(objectID);
			if(o != null && o.getDutyClerkID() != null){
				if (em.getDutyerType() == EventMsg.DUTYER_TYPE_PROJECT_DIRECTOR) {
					receiverIds = Lists.newArrayList(o.getDutyClerkID());
				} else if (em.getDutyerType() >= 101 && em.getDutyerType() <= 106) {
					List<Peoples> lps = peoplesDao.findPeoplesByTypeAndFstPeople((byte) em.getDutyerType(), o.getDutyClerkID());
					receiverIds = Lists.newArrayList();
					for (Peoples ps : lps) {
						receiverIds.add(ps.getSndPeople());
					}
				}
			}
		} else if (em.getDutyerType() == EventMsg.DUTYER_TYPE_TASK_CLERK) {
			//必须在下面重新赋值
			receiverIds = Collections.emptyList();
			
			Task t = taskService.getTask(taskSN);
			if (t != null) {
				if (t.getClerkID() != null) {
					receiverIds = Lists.newArrayList(t.getClerkID());
				}
			}
		}
		
		//
		if(receiverIds.isEmpty()){
			log.warn("createSmsgByEventMsg() 接收人列表为空，无法生成消息。");
			return null;
		}
		
		String title = "";
		String content = "";
		if (em.getTitleType() == 1 || em.getTitleType() == 201 || em.getTitleType() == 301 || em.getTitleType() == 501) {
			Map<String, Object> newContext = Maps.newHashMap();
			if(context != null){
				newContext.putAll(context);
			}
			Map<String, Object> taskContext = msgTemplateService.getTaskContext(taskSN);
			newContext.putAll(taskContext);
			
			String titleTemplate = em.getTitle();
			title = msgTemplateService.processTemplate(titleTemplate, newContext);
			
			String contentTemplate = em.getTemplet();
			content = msgTemplateService.processTemplate(contentTemplate, newContext);
		} else if (em.getTitleType() == 999) {
			title = msgTemplateService.processTemplate(em.getTitle(), context);
			content = msgTemplateService.processTemplate(em.getTemplet(), context);
		}
		
		return createBaseSmsg(em, receiverIds, title, content, null);
	}
	
	/**
	 * 如果单独调用该方法，则模板处理代码有效，如果从{@link #createSmsgByEventMsg(EventMsg, Long, Long, Map)}调用，则模板很可能已经处理
	 * 过。
	 */
	public List<Smsg> createBaseSmsg(EventMsg em, List<Long> receiverIds, String title, String content, Map<String,Object> context) {
		title = msgTemplateService.processTemplate(title, context);
		content = msgTemplateService.processTemplate(content, context);
		return createBaseSmsg(em, receiverIds, title, content);
	}
	
	public List<Smsg> createBaseSmsg(EventMsg em, List<Long> receiverIds, String title, String content){
		List<Smsg> list = Lists.newArrayList();
		if (EventMsg.IS_TRUE == em.getIsCreateSms()) {
			list.add(createBaseSmsg(em, receiverIds, title, content, Smsg.KIND_手机短信));
		}
		if (EventMsg.IS_TRUE == em.getIsCreateEmail()) {
			list.add(createBaseSmsg(em, receiverIds, title, content, Smsg.KIND_电子邮件));
		}
		if (EventMsg.IS_TRUE == em.getIsSendInternal()) {
			list.add(createBaseSmsg(em, receiverIds, title, content, Smsg.KIND_内部消息));
		}
		return list;
	}
	
	public Smsg createBaseSmsg(EventMsg em, List<Long> receiverIds, String title, String content, byte msgKind){
		Map<String,String> params = new HashMap<String,String>();
		params.put("smsg.kind", msgKind+"");
		params.put("smsg.title", title);
		params.put("smsg.content", content);
		params.put("smsg.bizStatus", Smsg.BIZ_STATUS_已拟定+"");
		params.put("smsg.refObjectId", 0+"");
		params.put("smsg.refObjectCode", 0+"");
		params.put("smsg.refObjectName", 0+"");
		params.put("smsg.frAddr", "无");
		
		for(int i = 0 ; i < receiverIds.size() ; i++){
			params.put("receiverIds[" + i + "]", receiverIds.get(i) + "");
		}
		if (EventMsg.IS_TRUE == em.getNeedConfirm()) {
			params.put("autosend", "0");
		}else{
			params.put("autosend", "1");
		}
		
		boolean isHoliday = holidayService.isHoliday(new Date());
		
		Date tempPublishTime = null;
		Date tempExpirationTime = null;
	

		switch (em.getPublishType()) {
		case 1:
			tempPublishTime = new Date();
			break;
		case 11:
			tempPublishTime = em.getPublishTime();
			break;
		case 101:
			tempPublishTime = isHoliday ? DateUtil.workdaysLater(new Date(), 0) : new Date();
			break;
		case 200:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 0, 0, 0)
					: getDateWithSetTime(new Date(), 0, 0, 0);
			break;
		case 201:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 1, 0, 0)
					: getDateWithSetTime(new Date(), 1, 0, 0);
			break;
		case 202:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 2, 0, 0)
					: getDateWithSetTime(new Date(), 2, 0, 0);
			break;
		case 203:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 3, 0, 0)
					: getDateWithSetTime(new Date(), 3, 0, 0);
			break;
		case 204:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 4, 0, 0)
					: getDateWithSetTime(new Date(), 4, 0, 0);
			break;
		case 205:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 5, 0, 0)
					: getDateWithSetTime(new Date(), 5, 0, 0);
			break;
		case 206:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 6, 0, 0)
					: getDateWithSetTime(new Date(), 6, 0, 0);
			break;
		case 207:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 7, 0, 0)
					: getDateWithSetTime(new Date(), 7, 0, 0);
			break;
		case 208:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 8, 0, 0)
					: getDateWithSetTime(new Date(), 8, 0, 0);
			break;
		case 209:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 9, 0, 0)
					: getDateWithSetTime(new Date(), 9, 0, 0);
			break;
		case 210:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 10, 0, 0)
					: getDateWithSetTime(new Date(), 10, 0, 0);
			break;
		case 211:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 11, 0, 0)
					: getDateWithSetTime(new Date(), 11, 0, 0);
			break;
		case 212:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 12, 0, 0)
					: getDateWithSetTime(new Date(), 12, 0, 0);
			break;
		case 213:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 13, 0, 0)
					: getDateWithSetTime(new Date(), 13, 0, 0);
			break;
		case 214:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 14, 0, 0)
					: getDateWithSetTime(new Date(), 14, 0, 0);
			break;
		case 215:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 15, 0, 0)
					: getDateWithSetTime(new Date(), 15, 0, 0);
			break;
		case 216:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 16, 0, 0)
					: getDateWithSetTime(new Date(), 16, 0, 0);
			break;
		case 217:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 17, 0, 0)
					: getDateWithSetTime(new Date(), 17, 0, 0);
			break;
		case 218:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 18, 0, 0)
					: getDateWithSetTime(new Date(), 18, 0, 0);
			break;
		case 219:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 19, 0, 0)
					: getDateWithSetTime(new Date(), 19, 0, 0);
			break;
		case 220:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 20, 0, 0)
					: getDateWithSetTime(new Date(), 20, 0, 0);
			break;
		case 221:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 21, 0, 0)
					: getDateWithSetTime(new Date(), 21, 0, 0);
			break;
		case 222:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 22, 0, 0)
					: getDateWithSetTime(new Date(), 22, 0, 0);
			break;
		case 223:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 23, 0, 0)
					: getDateWithSetTime(new Date(), 23, 0, 0);
			break;
		case 224:
			tempPublishTime = isHoliday ? getDateWithSetTime(DateUtil.workdaysLater(new Date(), 0), 24, 0, 0)
					: getDateWithSetTime(new Date(), 24, 0, 0);
			break;

		case 300:
			tempPublishTime = getDateWithSetTime(new Date(), 0, 0, 0);
			break;
		case 301:
			tempPublishTime = getDateWithSetTime(new Date(), 1, 0, 0);
			break;
		case 302:
			tempPublishTime = getDateWithSetTime(new Date(), 2, 0, 0);
			break;
		case 303:
			tempPublishTime = getDateWithSetTime(new Date(), 3, 0, 0);
			break;
		case 304:
			tempPublishTime = getDateWithSetTime(new Date(), 4, 0, 0);
			break;
		case 305:
			tempPublishTime = getDateWithSetTime(new Date(), 5, 0, 0);
			break;
		case 306:
			tempPublishTime = getDateWithSetTime(new Date(), 6, 0, 0);
			break;
		case 307:
			tempPublishTime = getDateWithSetTime(new Date(), 7, 0, 0);
			break;
		case 308:
			tempPublishTime = getDateWithSetTime(new Date(), 8, 0, 0);
			break;
		case 309:
			tempPublishTime = getDateWithSetTime(new Date(), 9, 0, 0);
			break;
		case 310:
			tempPublishTime = getDateWithSetTime(new Date(), 10, 0, 0);
			break;
		case 311:
			tempPublishTime = getDateWithSetTime(new Date(), 11, 0, 0);
			break;
		case 312:
			tempPublishTime = getDateWithSetTime(new Date(), 12, 0, 0);
			break;
		case 313:
			tempPublishTime = getDateWithSetTime(new Date(), 13, 0, 0);
			break;
		case 314:
			tempPublishTime = getDateWithSetTime(new Date(), 14, 0, 0);
			break;
		case 315:
			tempPublishTime = getDateWithSetTime(new Date(), 15, 0, 0);
			break;
		case 316:
			tempPublishTime = getDateWithSetTime(new Date(), 16, 0, 0);
			break;
		case 317:
			tempPublishTime = getDateWithSetTime(new Date(), 17, 0, 0);
			break;
		case 318:
			tempPublishTime = getDateWithSetTime(new Date(), 18, 0, 0);
			break;
		case 319:
			tempPublishTime = getDateWithSetTime(new Date(), 19, 0, 0);
			break;
		case 320:
			tempPublishTime = getDateWithSetTime(new Date(), 20, 0, 0);
			break;
		case 321:
			tempPublishTime = getDateWithSetTime(new Date(), 21, 0, 0);
			break;
		case 322:
			tempPublishTime = getDateWithSetTime(new Date(), 22, 0, 0);
			break;
		case 323:
			tempPublishTime = getDateWithSetTime(new Date(), 23, 0, 0);
			break;
		case 324:
			tempPublishTime = getDateWithSetTime(new Date(), 24, 0, 0);
			break;
		}
		//params.put("smsg.publishTime", tempPublishTime);
		switch (em.getExpiresType()) {
		case 1:
			tempExpirationTime = em.getExpirationTime();
			break;
		case 1005:
			tempExpirationTime = DateUtil.minutesLater(tempPublishTime, 5);
			break;
		case 1010:
			tempExpirationTime = DateUtil.minutesLater(tempPublishTime, 10);
			break;
		case 1015:
			tempExpirationTime = DateUtil.minutesLater(tempPublishTime, 15);
			break;
		case 1030:
			tempExpirationTime = DateUtil.minutesLater(tempPublishTime, 30);
			break;
		case 1045:
			tempExpirationTime = DateUtil.minutesLater(tempPublishTime, 45);
			break;
		case 1101:
			tempExpirationTime = DateUtil.hoursLater(tempPublishTime, 1);
			break;
		case 1102:
			tempExpirationTime = DateUtil.hoursLater(tempPublishTime, 2);
			break;
		case 1105:
			tempExpirationTime = DateUtil.hoursLater(tempPublishTime, 5);
			break;
		case 1112:
			tempExpirationTime = DateUtil.hoursLater(tempPublishTime, 12);
			break;
		case 1124:
			tempExpirationTime = DateUtil.hoursLater(tempPublishTime, 24);
			break;
		case 1201:
			tempExpirationTime = DateUtil.daysLater(tempPublishTime, 1);
			break;
		case 1202:
			tempExpirationTime = DateUtil.daysLater(tempPublishTime, 2);
			break;
		case 1203:
			tempExpirationTime = DateUtil.daysLater(tempPublishTime, 3);
			break;
		case 1205:
			tempExpirationTime = DateUtil.daysLater(tempPublishTime, 5);
			break;
		case 1210:
			tempExpirationTime = DateUtil.daysLater(tempPublishTime, 10);
			break;
		case 1301:
			tempExpirationTime = DateUtil.workdaysLater(tempPublishTime, 1);
			break;
		case 1302:
			tempExpirationTime = DateUtil.workdaysLater(tempPublishTime, 2);
			break;
		case 1303:
			tempExpirationTime = DateUtil.workdaysLater(tempPublishTime, 3);
			break;
		case 1305:
			tempExpirationTime = DateUtil.workdaysLater(tempPublishTime, 5);
			break;
		case 1310:
			tempExpirationTime = DateUtil.workdaysLater(tempPublishTime, 10);
			break;
		case 1401:
			tempExpirationTime = DateUtil.monthsAfter(tempPublishTime, 1);
			break;
		case 1402:
			tempExpirationTime = DateUtil.monthsAfter(tempPublishTime, 2);
			break;
		case 1403:
			tempExpirationTime = DateUtil.monthsAfter(tempPublishTime, 3);
			break;
		case 1501:
			tempExpirationTime = DateUtil.yearsLater(tempPublishTime, 1);
			break;
		case 1502:
			tempExpirationTime = DateUtil.yearsLater(tempPublishTime, 2);
			break;
		case 1503:
			tempExpirationTime = DateUtil.yearsLater(tempPublishTime, 3);
			break;
		}
		//params.put("smsg.expirationTime", tempExpirationTime);
		
		if(tempPublishTime != null){
			params.put("smsg.publishTime", AppsGlobals.formatShortDateTime(tempPublishTime));
		}
		if(tempExpirationTime != null){
			params.put("smsg.expirationTime", AppsGlobals.formatShortDateTime(tempExpirationTime));
		}
		
		return createBaseSmsgWorkScheme(params);
	}
	
	private Date getDateWithSetTime(Date time, int hour, int minute, int second) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		return c.getTime();
	}
	private Smsg createBaseSmsgWorkScheme(Map<String, String> params){
		//from 设置成工作流
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try{
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(SchedulerUser.USERNAME, "***"));

			Scheme scheme = schemeManager.getScheme(CREATE_SMSG_WORKSCHEME_NAME);
			((ParametersAware) scheme).setParameters(params);
			
			SchemeInvoker.invoke(scheme, null);
			RFSObject object = ((AbstractWorkScheme)scheme).getObject();
			return (Smsg)object;
		}catch(Exception e){
			if(isEventMsgExceptionIfFailed()){
				throw new SchemeException("EventMsg创建Smsg失败", e);
			}else{
				log.error("EventMsg创建Smsg失败", e);
				return null;
			}
		}finally{
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}
	
	/**
	 * EventMsg处理失败时是否抛出异常？，默认true。
	 * @return
	 */
	private boolean isEventMsgExceptionIfFailed(){
		return AppsGlobals.getProperty(PROP_EVENTMSG_EXCEPTION_IF_FAILED, true);
	}
	
	
	private String buildCacheKey(int objectType, Long objectID, int jobType, int taskType,
			int workType, int processType, Long eventType){
		return String.format("_ot%s_oi%s_jt%s_tt%s_wt%s_pt%s_et%s", objectType, objectID, jobType, taskType, workType, processType, eventType);
	}
	
	private List<EventMsg> getEventMsgsFromCache(String key){
		List<Long> ids = eventMsgListCache.get(key);
		if(ids == null){
			return null;
		}
		List<EventMsg> list = Lists.newArrayList();
		for(Long id: ids){
			list.add(getEventMsg(id));
		}
		return list;
	}
	
	private void putEventMsgsIntoCache(String key, List<EventMsg> list){
		List<Long> ids = Lists.newArrayList();
		for(EventMsg em:list){
			ids.add(em.getId());
			eventMsgCache.put(em.getId(), em);
		}
		eventMsgListCache.put(key, ids);
	}
	
	public EventMsg getEventMsg(Long id){
		EventMsg eventMsg = eventMsgCache.get(id);
		if(eventMsg == null){
			eventMsg = eventMsgDao.get(id);
			if(eventMsg != null){
				eventMsgCache.put(id, eventMsg);
			}
		}
		return eventMsg;
	}
}
