package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.TaskMatter;

public interface TaskMatterService {
	TaskMatter getTaskMatterByTaskSN(Long taskSN);
}
