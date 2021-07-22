package cn.redflagsoft.base.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.opoo.apps.Mappable;

import cn.redflagsoft.base.util.BeanUtils;

public class ObjectDetails implements Serializable, Mappable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4003288909251321793L;

	private RFSObject object;
	private Task task;
	private Issue issue;
	private Progress progress;

	public ObjectDetails() {
		super();
	}

	public ObjectDetails(RFSObject object, Task task, Progress progress,
			Issue issue) {
		super();
		this.object = object;
		this.task = task;
		this.progress = progress;
		this.issue = issue;
	}

	/**
	 * @return the progress
	 */
	public Progress getProgress() {
		return progress;
	}

	/**
	 * @param progress
	 *            the progress to set
	 */
	public void setProgress(Progress progress) {
		this.progress = progress;
	}

	/**
	 * @return the object
	 */
	public RFSObject getObject() {
		return object;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(RFSObject object) {
		this.object = object;
	}

	/**
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * @param task
	 *            the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * @return the issue
	 */
	public Issue getIssue() {
		return issue;
	}

	/**
	 * @param issue
	 *            the issue to set
	 */
	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	@SuppressWarnings("unchecked")
	public Map toMap() {
		Map<String,Object> map = new HashMap<String,Object>();
		if (object != null) {
			Map<String, ?> tmp = BeanUtils.getPropertiesMap(object);
			map.putAll(tmp);
		}

		if (task != null) {
			Map<String, ?> tmp = BeanUtils.getPropertiesMap(task);
			for (Map.Entry<?, ?> en : tmp.entrySet()) {
				map.put("task_" + en.getKey(), en.getValue());
			}
		}

		if (issue != null) {
			Map<String, ?> tmp = BeanUtils.getPropertiesMap(issue);
			for (Map.Entry<?, ?> en : tmp.entrySet()) {
				map.put("issue_" + en.getKey(), en.getValue());
			}
		}

		if (progress != null) {
			Map<String, ?> tmp = BeanUtils.getPropertiesMap(progress);
			for (Map.Entry<?, ?> en : tmp.entrySet()) {
				map.put("progress_" + en.getKey(), en.getValue());
			}
		}
		return map;
	}

	public static void main(String[] args) {
		ObjectDetails od = new ObjectDetails();
		od.setIssue(new Issue());
		od.setTask(new Task());
		od.setProgress(new Progress());
		od.setObject(new RFSObject());
		System.out.println(od.toMap());
	}
}
