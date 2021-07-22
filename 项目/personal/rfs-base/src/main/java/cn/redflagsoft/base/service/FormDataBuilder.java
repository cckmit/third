/*
 * $Id: FormDataBuilder.java 5971 2012-08-03 05:57:13Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import java.util.Map;

import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.Work;

/**
 * 表单数据构建器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface FormDataBuilder {
	
	
	/**
	 * 构建表单数据。
	 * @return
	 */
	Map<String, Object> toMap();
	
	/**
	 * 增加对象的所有属性到表单数据。
	 * @param object 对象
	 * @return 构建器对象本身
	 */
	FormDataBuilder append(Object object);
	
	/**
	 * 增加对象的部分属性到表单数据，指定排除的属性。
	 * @param object 对象
	 * @param excludeFields 排除的属性列表
	 * @return 构建器对象本身
	 */
	FormDataBuilder appendExcludes(Object object, String... excludeFields);
	
	/**
	 * 增加对象的部分属性到表单数据，指定包含的属性。
	 * @param object 对象
	 * @param includeFields 包含的属性
	 * @return 构建器对象本身
	 */
	FormDataBuilder appendIncludes(Object object, String... includeFields);
	
	/**
	 * 增加Task信息到表单数据。属性格式为 task_&lt;XXX&gt;_&lt;taskType&gt;。
	 * @param task 任务对象
	 * @return 构建器对象本身
	 */
	FormDataBuilder appendTask(Task task);
	
//	/**
//	 * 根据Task的sn查询对象并增加task信息到表单数据。
//	 * @param taskSN 任务的SN
//	 * @return 构建器对象本身
//	 * @see #appendTask(Task)
//	 */
//	FormDataBuilder appendTask(Long taskSN);
//	
//	/**
//	 * 根据对象ID和对象关系类型，在objects查找task的sn，并将查找到的
//	 * task对象信息增加到表单数据中。
//	 * 
//	 * @param objectId 在objects中的第一个对象的ID
//	 * @param objectsTypeOfTask 在objects中的关系类型
//	 * @return 构建器对象本身
//	 * @see #appendTask(Long)
//	 * @see #appendTask(Task)
//	 */
//	FormDataBuilder appendTask(long objectId, short objectsTypeOfTask);
	
//	/**
//	 * 查找对象相关的指定类型的task的最新一个task以及task相关的所有work，
//	 * 并将这些task和work数据增加到表单数据中。
//	 * 
//	 * @param objectId
//	 * @param objectType
//	 * @param taskType
//	 * @return
//	 */

//	FormDataBuilder appendObjectLastTaskAndWorks(long objectId, int objectType, int taskType);

	
//	/**
//	 * 设置task对象的信息到表单数据，一个表单数据只能设置一个
//	 * task对象的信息，后设置的会覆盖之前设置的数据。
//	 * 属性格式为 task_&lt;XXX&gt;。
//	 * @param task 任务对象
//	 * @return 构建器对象本身
//	 */
//	FormDataBuilder setTask(Task task);
//	
//	/**
//	 * 根据task的SN查找task对象，并设置task对象的信息到表单数据。
//	 * @param taskSN 任务的SN
//	 * @return 构建器对象本身
//	 * @see #setTask(Task)
//	 */
//	FormDataBuilder setTask(Long taskSN);
//	
//	/**
//	 * 根据对象ID和对象关系类型，在objects查找task的sn，并将查找到的
//	 * task对象信息设置到表单数据中。
//	 * @param objectId 在objects中的第一个对象的ID
//	 * @param objectsTypeOfTask 在objects中的关系类型
//	 * @return 构建器对象本身
//	 * @see #setTask(Long)
//	 * @see #setTask(Task)
//	 */
//	FormDataBuilder setTask(long objectId, short objectsTypeOfTask);
//	
//	
//	/**
//	 * 根据业务对象的ID和类型以及所办的业务类型（taskType）查询该业务对应的
//	 * task信息（单个）和work信息（多个），并组装到表单数据中。
//	 * 
//	 * <p>通常要查询指定业务对象指定事项的Task、Work信息时，调用这个方法。
//	 * 
//	 * @param objectId 业务对象ID
//	 * @param objectType 业务对象类型
//	 * @param taskType task的类型
//	 * @return 构建器对象本身
//	 */


//	FormDataBuilder setTaskAndWorks(long objectId, int objectType, int taskType);

	
	/**
	 * 增加Work的信息到表单数据。
	 * 增加的属性为 work_&lt;XXX&gt;_&lt;workType&gt;
	 * 
	 * @param work work对象
	 * @return 构建器对象本身
	 */
	FormDataBuilder appendWork(Work work);
//	/**
//	 * 根据Work的SN查询Work对象，并增加其信息到表单数据中。
//	 * @param workSN Work的SN
//	 * @return 构建器对象本身
//	 * @see #appendWork(Work)
//	 */
//	FormDataBuilder appendWork(Long workSN);
//	
//	/**
//	 * 根据对象ID和对象关系类型，在objects查找work的sn，并将查找到的
//	 * work对象信息设置到表单数据中。
//	 * 
//	 * @param objectId 在objects中的第一个对象的ID
//	 * @param objectsTypeOfWork  在objects中的关系类型
//	 * @return 构建器对象本身
//	 * @see #appendWork(Work)
//	 * @see #appendWork(Long)
//	 */
//	FormDataBuilder appendWork(long objectId, short objectsTypeOfWork);
	
	/**
	 * 查询指定对象，指定类型的task，work层次信息，包含下级节点。
	 * @param object
	 * @param taskType
	 */
	FormDataBuilder appendTask(RFSEntityObject object, int taskType);
}
