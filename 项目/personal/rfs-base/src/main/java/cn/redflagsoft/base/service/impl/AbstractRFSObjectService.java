/*
 * $Id: AbstractRFSObjectService.java 5951 2012-08-02 06:22:41Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
 * ҵ������������ࡣ
 * 
 * ���ദ�����ʱ����ͬʱ�����������������׶ζ�����Ҫ��ͬ�����ӡ�ɾ����
 *
 * @author Alex Lin(alex@opoo.org)
 *
 * @param <T> �����ҵ��������͡�
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
			throw new IllegalStateException("������ getObjectClass() ��ָ�������Ķ������͡�");
		}
		
		//log.debug("�����������ͣ�" + objectClass.getName());
		
		isLifeStageable = LifeStageable.class.isAssignableFrom(objectClass);
		
		if(isLifeStageable){
			//Assert.notNull(lifeStageDao, "��ǰ��������� lifeStageDao ����Ϊ��");
			Assert.notNull(lifeStageUpdater, "��ǰ����Ĳ����������LifeStage��Ϣ����ǰ����������� lifeStageUpdater ���ԡ�");
		}
		
		eventClass = getObjectEventClass();
		
		//log.info("����LifeStage����" + isLifeStageable);
		//log.info("�¼����ͣ�" + eventClass);

		String msg = String.format("ҵ�����: %s, ����LifeStage����: %s, �����¼�����: %s", objectClass.getName(), isLifeStageable, eventClass);
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
	 * �����Ҫʱ���������븲�����������������ȷ���¼����͡�
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
				log.error("�޷������¼�ʵ����" + eventClass, e);
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
				log.error("�޷������¼�ʵ����" + eventClass, e);
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
//	 * ����ҵ����󴴽������׶ζ���
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
	 * ���ݹ̶�������ѯTaskableObject���ϡ�
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
	 * ����ҵ�����ļ��ϲ�ѯ����װ<code>TaskableObject</code> �ļ��ϡ�
	 * 
	 * <p>��ѯ������ص�task����״̬���ˣ�ֻ��ѯ�ڰ����ͣ��Task��
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
			logic.and(Restrictions.logic(Restrictions.eq("status", Task.STATUS_�ڰ�)).or(Restrictions.eq("status", Task.STATUS_��ͣ)));
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
	 * ��ѯTaskableObject���ϡ�
	 * 
	 * <p>���ܲ���Ϊ ResultFilter��һ����Ҫָ��ԭʼ���� taskType��
	 * ��ѯ������ص�task������״̬���ˡ�
	 * 
	 * @param filter ����������
	 * @return
	 */
	public List<TaskableObject> findTaskableObjectByFilter(ResultFilter filter){
		Assert.notNull(filter);
		Map<String, ?> map = filter.getRawParameters();
		Integer taskType = MapUtils.getInteger(map, "taskType");
		//Assert.notNull(taskType, "��ѯʱ����ָ������taskType���ǲ�ѯ������");
		if(taskType == null){
			log.warn("��ѯʱδָ������taskType���ǲ�ѯ������");
		}
		
		List<T> list = getObjectDao().find(filter);
		//taskType ����Ϊ  null
		return populateTaskableObjectList(list, taskType);
	}
	

	/**
	 * ����ҵ�����ļ��ϲ�ѯ����װ<code>TaskableObject</code> �ļ��ϡ�
	 * 
	 * <p>��ѯ������ص�task������״̬���ˡ�
	 * 
	 * @param list ҵ�����ļ���
	 * @param taskType �ö���ǰ��ѯʱ��Ҫ��ȡ��task��type������Ϊnull
	 * @return TaskableObject����
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
			//�����ID����
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
			//task״̬���ж�
			//logic.and(Restrictions.logic(Restrictions.eq("status", Task.STATUS_�ڰ�)).or(Restrictions.eq("status", Task.STATUS_��ͣ)));
			filter.setCriterion(logic);
			//���µļ�¼�ں��棬��װ Map ��ʱ��Ϳ���ֵ�������µļ�¼
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
				//taskType == nullʱ��mapΪ�գ�taskΪnull
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
	 * ��ҳ��ѯ TaskableObject ���ϡ�
	 * 
	 * <p>���ܲ���Ϊ ResultFilter��һ����Ҫָ��ԭʼ���� taskType��
	 * ��ѯ������ص�task������״̬���ˡ�
	 * 
	 * @param filter
	 * @return
	 */
	public PageableList<TaskableObject> findPageableTaskableObjectByFilter(ResultFilter filter){
		Assert.notNull(filter);
		Map<String, ?> map = filter.getRawParameters();
		Integer taskType = MapUtils.getInteger(map, "taskType");
		//Assert.notNull(taskType, "��ѯʱ����ָ������taskType���ǲ�ѯ������");
		if(taskType == null){
			log.warn("��ѯʱδָ������taskType���ǲ�ѯ������");
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
	 * ����  TaskableObject ʵ����
	 * @param object
	 * @param task ����Ϊnull
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
