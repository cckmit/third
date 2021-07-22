package cn.redflagsoft.base.service.impl;

import cn.redflagsoft.base.bean.TaskData;
import cn.redflagsoft.base.dao.TaskDataDao;
import cn.redflagsoft.base.service.TaskDataService;

public class TaskDataServiceImpl implements TaskDataService{
	private TaskDataDao taskDataDao;

	public void setTaskDataDao(TaskDataDao taskDataDao) {
		this.taskDataDao = taskDataDao;
	}

	public int deleteTaskData(TaskData taskData) {
		return taskDataDao.delete(taskData);
	}

	public TaskData getTaskData(Long sn) {
		return taskDataDao.get(sn);
	}

	public TaskData updateTaskData(TaskData taskData) {
		return taskDataDao.update(taskData);
	}
}
