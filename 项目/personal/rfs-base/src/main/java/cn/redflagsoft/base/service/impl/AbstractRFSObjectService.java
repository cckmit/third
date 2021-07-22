/*
 * $Id: AbstractRFSObjectService.java 5951 2012-08-02 06:22:41Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventDispatcherAware;
import org.opoo.apps.lifecycle.Application;
import org.opoo.ndao.DataAccessException;
import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.util.Assert;

import cn.redflagsoft.base.NotFoundException;
import cn.redflagsoft.base.bean.LifeStageable;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.RFSTaskableObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.TaskableObject;
import cn.redflagsoft.base.dao.TaskDao;
import cn.redflagsoft.base.event2.RFSObjectEvent;
import cn.redflagsoft.base.service.EntityObjectFactory;
import cn.redflagsoft.base.service.LifeStageUpdater;
import cn.redflagsoft.base.service.RFSObjectService;
import cn.redflagsoft.base.util.MapUtils;
import cn.redflagsoft.base.util.ObjectTypeUtils;


/**
 * 业务对象服务抽象类。
 * 
 * 该类处理对象时，可同时处理对象关联的生命阶段对象，主要是同步增加、删除。
 *
 * @author Alex Lin(alex@opoo.org)
 *
 * @param <T> 具体的业务对象类型。
 */
public abstract class AbstractRFSObjectService<T extends RFSObject> extends ObjectServiceImpl<T> 
	implements RFSObjectService<T>, EventDispatcherAware, EntityObjectFactory<T>{
	private final Log log = LogFactory.getLog(getClass());
	
	//private LifeStageDao lifeStageDao;
	private Class<T> objectClass;
	private Class<? extends RFSObjectEvent<T>> eventClass;
	private int objectType = 0;
	
	private boolean isLifeStageable = false;

	private EventDispatcher eventDispatcher;
	
	private LifeStageUpdater<T> lifeStageUpdater;
	
	
	
//	/**
//	 * @return the lifeStageDao
//	 */
//	public LifeStageDao getLifeStageDao() {
//		return lifeStageDao;
//	}
//	
//
//	/**
//	 * @param lifeStageDao the lifeStageDao to set
//	 */
//	public void setLifeStageDao(LifeStageDao lifeStageDao) {
//		this.lifeStageDao = lifeStageDao;
//	}

	public LifeStageUpdater<T> getLifeStageUpdater() {
		return lifeStageUpdater;
	}




	public void setLifeStageUpdater(LifeStageUpdater<T> lifeStageUpdater) {
		this.lifeStageUpdater = lifeStageUpdater;
	}



	/**
	 * @return the eventDispatcher
	 */
	public EventDispatcher getEventDispatcher() {
		return eventDispatcher;
	}




	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.impl.ObjectServiceImpl#afterPropertiesSet()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		//Class<? extends RFSObject> objectClass = null;

		Type genType = getClass().getGenericSuperclass();
		if (genType != null && genType instanceof ParameterizedType) {
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
			if (params.length == 1) {
				objectClass = params[0] instanceof Class ? (Class<T>) params[0] : null;
			}
		}
		
		if(objectClass == null){
			objectClass = getObjectClass();
		}
		
		if(objectClass == null){
			throw new IllegalStateException("必须在 getObjectClass() 中指定操作的对象类型。");
		}
		
		//log.debug("操作对象类型：" + objectClass.getName());
		
		isLifeStageable = LifeStageable.class.isAssignableFrom(objectClass);
		
		if(isLifeStageable){
			//Assert.notNull(lifeStageDao, "当前服务的属性 lifeStageDao 不能为空");
			Assert.notNull(lifeStageUpdater, "当前服务的操作对象带有LifeStage信息，当前服务必须配置 lifeStageUpdater 属性。");
		}
		
		eventClass = getObjectEventClass();
		
		//log.info("附带LifeStage对象：" + isLifeStageable);
		//log.info("事件类型：" + eventClass);

		String msg = String.format("业务对象: %s, 附带LifeStage对象: %s, 驱动事件类型: %s", objectClass.getName(), isLifeStageable, eventClass);
		log.debug(msg);
		
		Integer type = ObjectTypeUtils.getObjectType(objectClass);
		if(type != null){
			log.debug("Find objectType '" + type + "' for class '" + objectClass.getName() + "'.");
			objectType = type;
		}
	}
	
	
	
	
	/**
	 * 
	 * @return
	 */
	protected Class<T> getObjectClass(){
		return null;
	}
	
	/**
	 * 如果需要时间驱动，请覆盖这个方法，返回正确的事件类型。
	 * @return
	 */
	protected Class<? extends RFSObjectEvent<T>> getObjectEventClass(){
		return null;
	}
	
	protected RFSObjectEvent<T> buildEvent(RFSObjectEvent.Type type, T object) {
		if(eventClass != null){
			try {
				Constructor<? extends RFSObjectEvent<T>> constructor 
						= eventClass.getConstructor(type.getClass(), objectClass);
				return constructor.newInstance(type, object);
			} catch (Exception e) {
				log.error("无法创建事件实例：" + eventClass, e);
			}
		}
		return null;
	}
	
	protected RFSObjectEvent<T> buildUpdateEvent(T old, T newObj) {
		if(eventClass != null){
			try {
				Constructor<? extends RFSObjectEvent<T>> constructor 
						= eventClass.getConstructor(RFSObjectEvent.Type.class, objectClass, objectClass);
				return constructor.newInstance(RFSObjectEvent.Type.UPDATED, old, newObj);
			} catch (Exception e) {
				log.error("无法创建事件实例：" + eventClass, e);
			}
		}
		return null;
	}
	

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.impl.ObjectServiceImpl#deleteObject(cn.redflagsoft.base.bean.RFSObject)
	 */
	@Override
	public void deleteObject(T object) {
		if(isLifeStageable){
			lifeStageUpdater.delete(object);
			//lifeStageDao.remove(object.getId());
		}
		
//		if(eventDispatcher != null){
//			RFSObjectEvent<T> event = buildEvent(RFSObjectEvent.Type.DELETE, object);
//			if(event != null){
//				eventDispatcher.dispatchEvent(event);
//			}
//		}
		super.deleteObject(object);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.impl.ObjectServiceImpl#removeObject(java.lang.Long)
	 */
	@Override
	public void removeObject(Long id) {
		if(isLifeStageable){
			//lifeStageDao.remove(id);
			try {
				T rfsObject = objectClass.newInstance();
				rfsObject.setId(id);
				lifeStageUpdater.delete(rfsObject);
				
//				if(eventDispatcher != null){
//					RFSObjectEvent<T> event = buildEvent(RFSObjectEvent.Type.DELETE, rfsObject);
//					if(event != null){
//						eventDispatcher.dispatchEvent(event);
//					}
//				}
			} catch (Exception e) {
				throw new DataAccessException(e);
			} 
		}
		super.removeObject(id);
	}


	@Override
	public T updateObject(T object) {
		T t = super.updateObject(object);
		if(isLifeStageable){
			lifeStageUpdater.update(t);
		}
		
//		if(eventDispatcher != null){
//			RFSObjectEvent<T> event = buildEvent(RFSObjectEvent.Type.UPDATED, object);
//			if(event != null){
//				eventDispatcher.dispatchEvent(event);
//			}
//		}
		return t;
	}




	@Override
	@Deprecated
	public void updateObjectsDutyClerkIdAndNameByClerkID(List<Long> ids, Long clerkID, String clerkName) {
		super.updateObjectsDutyClerkIdAndNameByClerkID(ids, clerkID, clerkName);
		if(isLifeStageable){
			for(Long id: ids){
				T t = getObject(id);
				lifeStageUpdater.update(t);
			}
		}
	}




	public T updateObject(T oldObject, T newObject){
		T obj = super.updateObject(oldObject, newObject);
		if(isLifeStageable){
			lifeStageUpdater.update(obj);
		}
		
//		if(eventDispatcher != null){
//			RFSObjectEvent<T> event = buildUpdateEvent(oldObject, newObject);
//			if(event != null){
//				eventDispatcher.dispatchEvent(event);
//			}
//		}
		return obj;
	}
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.impl.ObjectServiceImpl#saveObject(cn.redflagsoft.base.bean.RFSObject)
	 */
	@Override
	public T saveObject(T object) {
		T t =  super.saveObject(object);
		if(isLifeStageable){
			//LifeStage ls = buildLifeStage(t);
			//lifeStageDao.save(ls);
			lifeStageUpdater.create(t);
		}
		
//		if(eventDispatcher != null){
//			RFSObjectEvent<T> event = buildEvent(RFSObjectEvent.Type.CREATED, object);
//			if(event != null){
//				eventDispatcher.dispatchEvent(event);
//			}
//		}
		
		return t;
	}
	
//	/**
//	 * 根据业务对象创建生命阶段对象。
//	 * 
//	 * @param lefeStage
//	 * @param object
//	 * @return
//	 */
//	protected LifeStage buildLifeStage(T object){
////		LifeStage ls = null;
////		if(isLifeStageable){
////			ls = ((LifeStageable) object).toLifeStage();
////		}else{
////			ls = new LifeStage(object);
////		}
////		return ls;
//		
//		return ((LifeStageable) object).toLifeStage();
//	}
//	
	


	/* (non-Javadoc)
	 * @see org.opoo.apps.event.v2.EventDispatcherAware#setEventDispatcher(org.opoo.apps.event.v2.EventDispatcher)
	 */
	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
	}
	
	

	
	/**
	 * 根据固定条件查询TaskableObject集合。
	 * 
	 */
	public List<TaskableObject> findTaskableObject(int taskType, String name, String sn) {
		ResultFilter filter = null;
		if(name != null || sn != null){
			filter = ResultFilter.createEmptyResultFilter();
			filter.setCriterion(buildQueryTaskableObjectsCriterion(name, sn));
//			Criterion c = null;
//			if(name != null){
//				c = Restrictions.like("name", "%" + name + "%");
//			}
//			
//			if(sn != null){
//				Criterion newc = Restrictions.like("sn", "%" + sn + "%");
//				if(c == null){
//					c = newc;
//				}else{
//					c = Restrictions.logic(c).and(newc);
//				}
//			}
//			
//			filter.setCriterion(c);
		}
		
		List<T> list = findObjects(filter);
		return processTaskableObjectList(list, taskType);
	}
	
	

	
	/**
	 * 根据业务对象的集合查询并组装<code>TaskableObject</code> 的集合。
	 * 
	 * <p>查询对象相关的task进行状态过滤，只查询在办和暂停的Task。
	 * 
	 * @param list
	 * @param taskType
	 * @return
	 */
	protected List<TaskableObject> processTaskableObjectList(final List<T> list, final int taskType){
		List<TaskableObject> result = null;
		
		if(list != null && !list.isEmpty()){
			List<Long> objectIds = new AbstractList<Long>(){
				@Override
				public Long get(int index) {
					return list.get(index).getId();
				}
				@Override
				public int size() {
					return list.size();
				}
			};
			
			
			ResultFilter filter = ResultFilter.createEmptyResultFilter();
			Logic logic = Restrictions.logic(Restrictions.in("refObjectId", objectIds));
			logic.and(Restrictions.eq("type", taskType));
			logic.and(Restrictions.logic(Restrictions.eq("status", Task.STATUS_在办)).or(Restrictions.eq("status", Task.STATUS_暂停)));
			filter.setCriterion(logic);
			
			TaskDao taskDao = Application.getContext().get("taskDao", TaskDao.class);
			List<Task> tasks = taskDao.find(filter);
			final Map<Long, Task> taskMap = new HashMap<Long,Task>();
			for(Task task: tasks){
				taskMap.put(task.getRefObjectId(), task);
			}
			
			/*List<TaskableObject> */result = new AbstractList<TaskableObject>(){
				@Override
				public TaskableObject get(int index) {
					T t = list.get(index);
					Task task = taskMap.get(t.getId());
					return buildTaskableObject(t, task);
				}
	
				@Override
				public int size() {
					return list.size();
				}
			};
		
		}else{
			result = new ArrayList<TaskableObject>();
		}
		
		if(list instanceof PageableList<?>){
			PageableList<T> pl = (PageableList<T>) list;
			return new PageableList<TaskableObject>(result, pl.getStartIndex(), pl.getPageSize(), pl.getItemCount());
		}else{
			return result;
		}
	}
	
	
	/**
	 * 查询TaskableObject集合。
	 * 
	 * <p>接受参数为 ResultFilter，一般需要指定原始参数 taskType。
	 * 查询对象相关的task不进行状态过滤。
	 * 
	 * @param filter 过滤条件。
	 * @return
	 */
	public List<TaskableObject> findTaskableObjectByFilter(ResultFilter filter){
		Assert.notNull(filter);
		Map<String, ?> map = filter.getRawParameters();
		Integer taskType = MapUtils.getInteger(map, "taskType");
		//Assert.notNull(taskType, "查询时必须指定参数taskType（非查询条件）");
		if(taskType == null){
			log.warn("查询时未指定参数taskType（非查询条件）");
		}
		
		List<T> list = getObjectDao().find(filter);
		//taskType 可能为  null
		return populateTaskableObjectList(list, taskType);
	}
	

	/**
	 * 根据业务对象的集合查询并组装<code>TaskableObject</code> 的集合。
	 * 
	 * <p>查询对象相关的task不进行状态过滤。
	 * 
	 * @param list 业务对象的集合
	 * @param taskType 该对象当前查询时需要读取的task的type，可以为null
	 * @return TaskableObject集合
	 */
	protected List<TaskableObject> populateTaskableObjectList(final List<T> list, final Integer taskType) {
		if(list == null){
			return null;
		}

		if(list.isEmpty()){
			if(list instanceof PageableList<?>){
				PageableList<T> pl = (PageableList<T>) list;
				return new PageableList<TaskableObject>(new ArrayList<TaskableObject>(), pl.getStartIndex(), pl.getPageSize(), pl.getItemCount());
			}else{
				return Collections.emptyList();
			}
		}
		
		//<objectId,Task>
		final Map<Long,Task> taskMap = new HashMap<Long,Task>();
		if(taskType != null){
			//对象的ID集合
			List<Long> objectIds = new AbstractList<Long>(){
				@Override
				public Long get(int index) {
					return list.get(index).getId();
				}
				@Override
				public int size() {
					return list.size();
				}
			};
			
			ResultFilter filter = ResultFilter.createEmptyResultFilter();
			Logic logic = Restrictions.logic(Restrictions.in("refObjectId", objectIds));
			logic.and(Restrictions.eq("type", taskType));
			//task状态不判断
			//logic.and(Restrictions.logic(Restrictions.eq("status", Task.STATUS_在办)).or(Restrictions.eq("status", Task.STATUS_暂停)));
			filter.setCriterion(logic);
			//最新的记录在后面，组装 Map 的时候就可以值保留最新的记录
			filter.setOrder(Order.asc("creationTime"));
			
			TaskDao taskDao = Application.getContext().get("taskDao", TaskDao.class);
			List<Task> tasks = taskDao.find(filter);
			for(Task task: tasks){
				taskMap.put(task.getRefObjectId(), task);
			}
		}
		
		//Build TaskableObject collection
		List<TaskableObject> result = new AbstractList<TaskableObject>(){
			@Override
			public TaskableObject get(int index) {
				T t = list.get(index);
				//taskType == null时，map为空，task为null
				Task task = taskMap.get(t.getId());
				return buildTaskableObject(t, task);
			}
			@Override
			public int size() {
				return list.size();
			}
		};
		
		if(list instanceof PageableList<?>){
			PageableList<T> pl = (PageableList<T>) list;
			return new PageableList<TaskableObject>(result, pl.getStartIndex(), pl.getPageSize(), pl.getItemCount());
		}else{
			return result;
		}
	}




	//@Queryable(argNames={"start", "limit", "taskType", "name", "sn"})
	public PageableList<TaskableObject> findPageableTaskableObject(int start, int limit, int taskType, String name,
			String sn) {
		ResultFilter filter = ResultFilter.createPageableResultFilter(start, limit);
		if(name != null || sn != null){
			filter.setCriterion(buildQueryTaskableObjectsCriterion(name, sn));
//			
//			Criterion c = null;
//			if(name != null){
//				c = Restrictions.like("name", "%" + name + "%");
//			}
//			
//			if(sn != null){
//				Criterion newc = Restrictions.like("name", "%" + sn + "%");
//				if(c == null){
//					c = newc;
//				}else{
//					c = Restrictions.logic(c).and(newc);
//				}
//			}
//			
//			filter.setCriterion(c);
		}
		
		PageableList<T> list = findPageableObjects(filter);
		
		return (PageableList<TaskableObject>) processTaskableObjectList(list, taskType);
	}
	
	/**
	 * 分页查询 TaskableObject 集合。
	 * 
	 * <p>接受参数为 ResultFilter，一般需要指定原始参数 taskType。
	 * 查询对象相关的task不进行状态过滤。
	 * 
	 * @param filter
	 * @return
	 */
	public PageableList<TaskableObject> findPageableTaskableObjectByFilter(ResultFilter filter){
		Assert.notNull(filter);
		Map<String, ?> map = filter.getRawParameters();
		Integer taskType = MapUtils.getInteger(map, "taskType");
		//Assert.notNull(taskType, "查询时必须指定参数taskType（非查询条件）");
		if(taskType == null){
			log.warn("查询时未指定参数taskType（非查询条件）");
		}
		
		PageableList<T> list = getObjectDao().findPageableList(filter);
		return (PageableList<TaskableObject>) populateTaskableObjectList(list, taskType);
	}
	
	
	protected Criterion buildQueryTaskableObjectsCriterion(String name, String sn){
		Criterion c = null;
		if(name != null){
			c = Restrictions.like("name", "%" + name + "%");
		}
		
		if(sn != null){
			Criterion newc = Restrictions.like("sn", "%" + sn + "%");
			if(c == null){
				c = newc;
			}else{
				c = Restrictions.logic(c).and(newc);
			}
		}
		return c;
	}
	
	
	/**
	 * 创建  TaskableObject 实例。
	 * @param object
	 * @param task 可以为null
	 * @return
	 */
	protected TaskableObject buildTaskableObject(T object, Task task){
		return new RFSTaskableObject(object, task);
	}

	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.EntityObjectFactory#getObjectType()
	 */
	public int getObjectType() {
		return objectType;
	}


	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.EntityObjectFactory#loadObject(long)
	 */
	public T loadObject(long id) throws NotFoundException {
		return getObject(id);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.EntityObjectFactory#loadObjects(java.util.List)
	 */
	public List<T> loadObjects(List<Long> objectIds) throws NotFoundException {
		ResultFilter f = new ResultFilter();
		f.setCriterion(Restrictions.in("id", objectIds));
		return findObjects(f);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.EntityObjectFactory#loadObjects(org.opoo.ndao.support.ResultFilter)
	 */
	public List<T> loadObjects(ResultFilter filter) {
		return findObjects(filter);
	}




	public static void main(String[] args) throws Exception{
//		System.out.println(java.util.List.class.isAssignableFrom(java.util.ArrayList.class));
//		System.out.println(java.util.ArrayList.class.isAssignableFrom(java.util.List.class));
//		System.out.println(java.util.List.class.isAssignableFrom(java.util.List.class));
//		
//		Method method = AbstractRFSObjectService.class.getMethod("findTaskableObject", String.class, short.class);
//		System.out.println(method);
//		Queryable queryable = method.getAnnotation(Queryable.class);
//		System.out.println(queryable);
		
		Task task = new Task();
		task.setSn(2000L);
		
		final List<Task> list = new ArrayList<Task>();
		list.add(task);
		
		List<Long> nlist = new AbstractList<Long>(){

			@Override
			public Long get(int index) {
				return list.get(index).getSn();
			}

			@Override
			public int size() {
				return list.size();
			}};
			
			
		System.out.println(nlist.toArray(new Long[0])[0]);
	}
}
