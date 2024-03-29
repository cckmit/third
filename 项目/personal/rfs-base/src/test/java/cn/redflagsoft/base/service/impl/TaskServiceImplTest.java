/*
 * $Id: TaskServiceImplTest.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;

import cn.redflagsoft.base.bean.RFSItemable;
import cn.redflagsoft.base.bean.RFSObjectable;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.test.BaseTests;
import edu.emory.mathcs.backport.java.util.concurrent.ExecutorService;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class TaskServiceImplTest extends BaseTests {
	protected TaskServiceImpl taskService;
	

	/**
	 * Test method for {@link cn.redflagsoft.base.service.impl.TaskServiceImpl#findTasks(cn.redflagsoft.base.bean.RFSObjectable, java.lang.Short, java.lang.Integer, java.lang.Byte)}.
	 */
	public void etestFindTasksRFSObjectableShortIntegerByte() {
		RFSObjectable objectable = new RFSObjectable() {
			public Long getId() {
				return 1000L;
			}

			public int getObjectType() {
				return 1002;
			}

			public String getName() {
				return null;
			}
		};
		List<Task> tasks = taskService.findTasks(objectable,  1110, null, null);
		System.out.println(tasks);
	}

	/**
	 * Test method for {@link cn.redflagsoft.base.service.impl.TaskServiceImpl#findTasks(cn.redflagsoft.base.bean.RFSItemable, java.lang.Short, java.lang.Integer, java.lang.Byte)}.
	 */
	public void etestFindTasksRFSItemableShortIntegerByte() {
		RFSItemable itemable = new RFSItemable(){
			public Long getId() {
				return 1000L;
			}
			public int getObjectType() {
				return 9999;
			}};
		List<Task> tasks = taskService.findTasks(itemable, 1110, null, null);
		System.out.println(tasks);
	}
	
	public void testCreateTask(){
		super.setComplete();
		//taskService.createTask(0L, (short)9999, new Long[]{9999L}, 9999L, (short)1, 0L);
		
		ExecutorService service = Executors.newFixedThreadPool(10);
		for(int i = 0 ; i< 1000 ; i++){
			Runnable runnable = new Runnable() {
				public void run() {
					Task task = taskService.createTask(0L, (short)9999, new Long[]{9999L}, 9999L, (short)1, 0L);
					System.out.println(Thread.currentThread() + " " + task);
				}
			};
			service.execute(runnable);
		}
		
		try {
			Thread.sleep(500000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
