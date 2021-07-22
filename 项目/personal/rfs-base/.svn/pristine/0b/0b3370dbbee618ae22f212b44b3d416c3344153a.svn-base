package cn.redflagsoft.base.service.impl;

import org.opoo.ndao.criterion.Restrictions;

import cn.redflagsoft.base.bean.TaskMatter;
import cn.redflagsoft.base.dao.TaskMatterDao;
import cn.redflagsoft.base.service.TaskMatterService;

public class TaskMatterServiceImpl implements TaskMatterService {
	private TaskMatterDao taskMatterDao;

	public TaskMatterDao getTaskMatterDao() {
		return taskMatterDao;
	}

	public void setTaskMatterDao(TaskMatterDao taskMatterDao) {
		this.taskMatterDao = taskMatterDao;
	}

	public TaskMatter getTaskMatterByTaskSN(Long taskSN) {
		return taskMatterDao.get(Restrictions.eq("taskSN", taskSN));
	}
}
