/*
 * $Id: WorkSchemeEventListener.java 5211 2011-12-14 07:02:04Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.listener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.event.v2.EventListener;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.TaskDefinition;
import cn.redflagsoft.base.bean.Work;
import cn.redflagsoft.base.bean.WorkDefinition;
import cn.redflagsoft.base.dao.TaskDefinitionDao;
import cn.redflagsoft.base.dao.WorkDefinitionDao;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.scheme.WorkScheme;
import cn.redflagsoft.base.scheme.event.WorkSchemeEvent;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.TaskService;
import cn.redflagsoft.base.service.WorkService;
import cn.redflagsoft.base.vo.DatumVOList;
import cn.redflagsoft.base.vo.MatterVO;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class WorkSchemeEventListener implements EventListener<WorkSchemeEvent> {
	private Log log = LogFactory.getLog(WorkSchemeEventListener.class);
	
	private TaskDefinitionDao taskDefinitionDao;
	private WorkDefinitionDao workDefinitionDao;
	private WorkService workService;
	private TaskService taskService;
	
	
	/**
	 * @param workService the workService to set
	 */
	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}


	/**
	 * @param taskService the taskService to set
	 */
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}



	/**
	 * @param taskDefinitionDao the taskDefinitionDao to set
	 */
	public void setTaskDefinitionDao(TaskDefinitionDao taskDefinitionDao) {
		this.taskDefinitionDao = taskDefinitionDao;
	}



	/**
	 * @param workDefinitionDao the workDefinitionDao to set
	 */
	public void setWorkDefinitionDao(WorkDefinitionDao workDefinitionDao) {
		this.workDefinitionDao = workDefinitionDao;
	}


	/* (non-Javadoc)
	 * @see org.opoo.apps.event.v2.EventListener#handle(org.opoo.apps.event.v2.Event)
	 */
	public void handle(WorkSchemeEvent event) {
		if(event.getType() == WorkSchemeEvent.Type.AFTER_EXECUTE){
			handleAfterExecute(event.getSource());
		}
	}
	
	
	/**
	 * ��Ҫ��������Task��Work��ģ��
	 */
	private void handleAfterExecute(WorkScheme ws){
		log = LogFactory.getLog(ws.getClass());
		boolean isDebugEnabled = log.isDebugEnabled();
		
		Work work = ws.getWork();
		Task task = ws.getTask();
		DatumVOList datumVOList = ws.getDatumVOList();
		MatterVO matterVO = ws.getMatterVO();
		RFSObject object = ws.getObject();
		Map<?,?> map = ws.getParameters();
		Clerk clerk = UserClerkHolder.getClerk();
		if(ws instanceof AbstractWorkScheme){
			clerk = ((AbstractWorkScheme) ws).getClerk();
		}
		
		Map<Object,Object> param = new HashMap<Object,Object>();
		if(map != null){
			param.putAll(map);
			Set<?> set = map.entrySet();
			Iterator<?> iterator = set.iterator();
			while(iterator.hasNext()){
				Map.Entry<?,?> en = (Entry<?,?>) iterator.next();
				Object key = en.getKey();
				Object value = en.getValue();
				if(value != null){
					if(value.getClass().isArray()){
						int length = Array.getLength(value);
						if(length > 0){
							param.put(key, Array.get(value, 0));
						}
					}
				}
			}
		}
		
		Map<String,Object> context = new HashMap<String,Object>();
		context.put("work", work);
		context.put("task", task);
		context.put("datum", datumVOList);
		context.put("matter", matterVO);
		context.put("object", object);
		context.put("param", param);
		context.put("clerk", clerk);
		context.put("date", new SimpleDateFormat("yyyy��MM��dd��").format(new Date()));
		context.put("dateTime", new SimpleDateFormat("yyyy��MM��dd��hhʱmm��ss��").format(new Date()));
		
		if(work != null){
			WorkDefinition wd = workDefinitionDao.get(work.getType());
			if(wd == null){
				log.warn("AfterExecute - û��ΪWork����WorkDefinition��" + work);
			}else{
				String template = wd.getSummaryTemplate();
				if(StringUtils.isNotBlank(template)){
					try {
						String summary = org.opoo.apps.util.StringUtils.processExpression(template, context);
						if(StringUtils.isNotBlank(summary)){
							
							if(isDebugEnabled){
								String msg = String.format("ΪWork(sn=%s)����ժҪ��Ϣ'%s'.", work.getSn(), summary);
								log.debug(msg);
							}
							
							work.setSummary(summary);
							workService.updateWork(work);
						}
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}else{
					log.debug("AfterExecute - ����ժҪģ��Ϊ�գ�������ժҪ��Ϣ��" + work);
				}
			}
		}else{
			log.warn("AfterExecute - Work is null, may be error.");
		}
		
		
		if(task != null){
			TaskDefinition td = taskDefinitionDao.get(task.getType());
			if(td == null){
				log.warn("AfterExecute - û��ΪTask����TaskDefinition��" + task);
			}else{
				String template = td.getSummaryTemplate();
				if(StringUtils.isNotBlank(template)){
					try {
						String summary = org.opoo.apps.util.StringUtils.processExpression(template, context);
						if(StringUtils.isNotBlank(summary)){
							
							if(isDebugEnabled){
								String msg = String.format("ΪTask(sn=%s)����ժҪ��Ϣ'%s'.", task.getSn(), summary);
								log.debug(msg);
							}
							
							task.setSummary(summary);
							taskService.updateTask(task);
						}
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}else{
					log.debug("AfterExecute - ����ժҪģ��Ϊ�գ�������ժҪ��Ϣ��" + task);
				}
			}
		}else{
			log.warn("AfterExecute - Task is null, may be error.");
		}
	}

	
	public static void main(String[] args) throws Exception{
//		System.out.println("Beep!" + (char)7);
//		
//		char   chrBell='\007';   
//		System.out.println(chrBell);
		
		java.awt.Toolkit.getDefaultToolkit().beep();
		
//		Map<String,Object> context = new HashMap<String,Object>();
//		Project obj = new Project();
//		obj.setId(12973219398L);
//		obj.setName("Ŷ�˾����˵�");
//		
//		Clerk clerk = new Clerk();
//		clerk.setId(91273912L);
//		clerk.setName("aaaaa");
//		context.put("work", new Work());
//		context.put("task", new Task());
//		context.put("datum", new DatumVOList(new ArrayList()));
//		context.put("matter", new MatterVO());
//		context.put("object", obj);
//		context.put("param", new HashMap());
//		context.put("clerk", clerk);
//		
////		String s = org.opoo.apps.util.StringUtils.processExpression("ssssss ${object.id} sssssssss ${clerk.name},,${object.name}", context);
////		System.out.println(s);
//		
//		String expr = "ssssss ${object.id} sssssssss ${clerk.name},,${object.name}";
//		StringWriter out = new StringWriter();
//		VelocityContext context2 = new VelocityContext(context);
//		Velocity.evaluate(context2, out, "DEBUG", expr);
//		System.out.println(out.toString());
//		
//		
//		String string = org.opoo.apps.util.StringUtils.processExpression(expr, context);
//		System.out.println(string);
//		
//		
//		
//		System.out.println(obj.getId());
//		System.out.println(obj.getKey());
//		System.out.println(clerk.getId());
//		System.out.println(clerk.getKey());
//		
//		BeanInfo info = java.beans.Introspector.getBeanInfo(Clerk.class);
//		PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
//		for(PropertyDescriptor desc: descriptors){
//			System.out.println(desc.getName() + " : " + desc.getPropertyType());
//			
//			findPropertyType(desc.getReadMethod(), desc.getWriteMethod());
//		}
//		
//		//freemarker.ext.beans.BeansWrapper.getDefaultInstance().wrap(object)
	}
	
//	private static Class findPropertyType(Method readMethod, Method writeMethod) throws IntrospectionException {
//		Class propertyType = null;
//		try {
//			if (readMethod != null) {
//				Class[] params = readMethod.getParameterTypes();
//				if (params.length != 0) {
//					throw new IntrospectionException("bad read method arg count: " + readMethod);
//				}
//				propertyType = readMethod.getReturnType();
//				if (propertyType == Void.TYPE) {
//					throw new IntrospectionException("read method " + readMethod.getName() + " returns void");
//				}
//				System.out.println(readMethod + "------->" + propertyType);
//			}
//			if (writeMethod != null) {
//				Class params[] = writeMethod.getParameterTypes();
//				if (params.length != 1) {
//					throw new IntrospectionException("bad write method arg count: " + writeMethod);
//				}
//				if (propertyType != null && propertyType != params[0]) {
//					throw new IntrospectionException("type mismatch between read and write methods:" + writeMethod);
//				}
//				propertyType = params[0];
//			}
//		} catch (IntrospectionException ex) {
//			throw ex;
//		}
//		return propertyType;
//	}
}
