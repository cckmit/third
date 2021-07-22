package cn.redflagsoft.base.service.impl;

import java.util.Date;
import java.util.List;

import org.opoo.apps.annotation.Queryable;
import org.opoo.apps.util.DateUtils;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Issue;
import cn.redflagsoft.base.bean.IssueDetails;
import cn.redflagsoft.base.bean.Progress;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.dao.ObjectsDao;
import cn.redflagsoft.base.dao.ProgressDao;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.IssueService;
import cn.redflagsoft.base.service.TaskService;

public class IssueServiceImpl extends AbstractRFSObjectService<Issue> implements
		IssueService {
	private ObjectsDao objectsDao;
	private TaskService taskService;

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public ProgressDao getProgressDao() {
		return progressDao;
	}

	public void setProgressDao(ProgressDao progressDao) {
		this.progressDao = progressDao;
	}

	private ProgressDao progressDao;

	public ObjectsDao getObjectsDao() {
		return objectsDao;
	}

	public void setObjectsDao(ObjectsDao objectsDao) {
		this.objectsDao = objectsDao;
	}

	public Issue createIssue(RFSObject object, Issue issue) {

		Clerk clerk = UserClerkHolder.getClerk();

		issue.setOrgId(clerk.getEntityID());
		issue.setOrgName(clerk.getEntityName());

		issue.setReporterId(clerk.getId());
		issue.setReporterName(clerk.getName());

		issue.setRefObjectId(object.getId());
		issue.setRefObjectName(object.getName());
		issue.setRefObjectType(object.getObjectType());

		if (issue.getReportTime() == null) {
			issue.setReportTime(new Date());
		}
		//去掉时分秒毫秒
		issue.setReportTime(DateUtils.toStartOfDay(issue.getReportTime()));
		return super.saveObject(issue);
	}

	@Queryable(argNames = { "id" })
	public IssueDetails getIssueDetails(Long id) {
		Issue issue = getObjectDao().get(id);

		if (issue != null) {
			Task task = new Task();
			Progress progress = new Progress();
			Long activeTaskSN = issue.getActiveTaskSN();

			if (activeTaskSN != null) {
				task = taskService.getTask(activeTaskSN);
			}

			// 组装对应的查询条件
			ResultFilter filter = ResultFilter.createPageableResultFilter(0, 1); // 0：从0开始
																					// 取1条记录
			SimpleExpression eq = Restrictions.eq("refObjectId", issue.getId());
			filter.setCriterion(eq);
			filter.setOrder(Order.desc("reportTime")); // 制定排序方式

			List<Progress> listProgress = progressDao.find(filter);
			if (listProgress != null && !listProgress.isEmpty()) {
				progress = listProgress.get(0);
			}
			return new IssueDetails(issue, task, progress, null);
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	public List<Issue> findIssuees(ResultFilter filter){
		return getObjectDao().find(filter);
	}
}
