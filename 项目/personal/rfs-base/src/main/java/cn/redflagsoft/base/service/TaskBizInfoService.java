/*
 * $Id: TaskBizInfoService.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;


import cn.redflagsoft.base.NonUniqueResultException;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.TaskBizInfo;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface TaskBizInfoService {

	/**
	 * 
	 * @param task
	 * @param taskBizInfo
	 * @return
	 */
	TaskBizInfo createTaskBizInfo(Task task, TaskBizInfo taskBizInfo);
	
	
	/**
	 * 查询指定 id 的 TaskBizInfo 信息
	 * @param id
	 * @return
	 */
	TaskBizInfo getTaskBizInfo(Long id);
	
	
	/**
	 * 更新 TaskBizInfo 对象。
	 * @param taskBizInfo
	 * @return
	 */
	TaskBizInfo update(TaskBizInfo taskBizInfo);

	
	/**
	 * 根据 TaskSN 查找指定的 TaskBizInfo 对象。
	 * 
	 * 通常一个 task 对象至多对应一个 TaskBizInfo 对象。
	 * @param taskSN
	 * @return 返回 指定的 TaskBizInfo 对象或者 null。
	 * @throws 同一个TaskSN有多个TaskBizInfo对象，则抛出异常。
	 */
	TaskBizInfo getTaskBizInfoByTaskSN(Long taskSN) throws NonUniqueResultException;
	
	
	
	/**
	 * 保存审批受理登记信息。
	 * 
	 * <p>首先根据task信息查询 TaskBizInfo对象，如果不存在则创建。然后更新 TaskBizInfo 对象
	 * 的受理部分属性的内容。
	 * @param task Task 对象不能为空，其 sn 和 name 属性也不能为空。
	 * @param taskBizInfo 用于传递受理信息的参数对象，不等同于持久对象 TaskBizInfo，只是借用了数据类型。
	 * @return 保存后持久对象 TaskBizInfo。
	 */
	TaskBizInfo saveAcptBizInfo(Task task, TaskBizInfo taskBizInfo);
	
	
	
	/**
	 * 保存审批决定登记信息。
	 * 
	 * <p>首先根据task信息查询 TaskBizInfo对象，如果不存在则创建。然后更新 TaskBizInfo 对象
	 * 的审批决定登记部分属性的内容。
	 * 
	 * 
	 * @param task 对象不能为空，其 sn 和 name 属性也不能为空。
	 * @param taskBizInfo 用于传递审批决定登记信息的参数对象，不等同于持久对象 TaskBizInfo，只是借用了数据类型。
	 * @return 保存后持久对象 TaskBizInfo。
	 */
	TaskBizInfo saveDaBizInfo(Task task, TaskBizInfo taskBizInfo);
	
	
	/**
	 * 保存审批决定发文信息。
	 * 
	 * <p>首先根据task信息查询 TaskBizInfo对象，如果不存在则创建。然后更新 TaskBizInfo 对象
	 * 的审批决定发文部分属性的内容。
	 * 
	 * 
	 * @param task 对象不能为空，其 sn 和 name 属性也不能为空。
	 * @param taskBizInfo 用于传递审批决定发文信息的参数对象，不等同于持久对象 TaskBizInfo，只是借用了数据类型。
	 * @return 保存后持久对象 TaskBizInfo。
	 */
	TaskBizInfo saveDrBizInfo(Task task, TaskBizInfo taskBizInfo);
	
	
	/**
	 * 保存报审受理备案信息。
	 * 
	 * <p>首先根据task信息查询 TaskBizInfo对象，如果不存在则创建。然后更新 TaskBizInfo 对象
	 * 的报审受理备案部分属性的内容。
	 * 
	 * 
	 * @param task 对象不能为空，其 sn 和 name 属性也不能为空。
	 * @param taskBizInfo 用于传递报审受理备案信息的参数对象，不等同于持久对象 TaskBizInfo，只是借用了数据类型。
	 * @return 保存后持久对象 TaskBizInfo。
	 */
	TaskBizInfo saveRcdBizInfo(Task task, TaskBizInfo taskBizInfo);
	
	
	/**
	 * 保存工程资料存档信息。
	 * 
	 * <p>首先根据task信息查询 TaskBizInfo对象，如果不存在则创建。然后更新 TaskBizInfo 对象
	 * 的工程资料存档部分属性的内容。
	 * 
	 * 
	 * @param task 对象不能为空，其 sn 和 name 属性也不能为空。
	 * @param taskBizInfo 用于传递工程资料存档信息的参数对象，不等同于持久对象 TaskBizInfo，只是借用了数据类型。
	 * @return 保存后持久对象 TaskBizInfo。
	 */
	TaskBizInfo saveAcvBizInfo(Task task, TaskBizInfo taskBizInfo);
}
