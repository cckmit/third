package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.TaskData;

public interface TaskDataService {
	TaskData getTaskData(Long sn);
	
	int deleteTaskData(TaskData taskData);
	
	TaskData updateTaskData(TaskData taskData);
}
