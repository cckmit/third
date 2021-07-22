package cn.redflagsoft.base.scheme.schemes.task;

import java.util.List;
import org.opoo.util.Assert;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.service.TaskService;
/**
 * 
 * 业务操作
 * 
 * @author 
 *
 */
public class TaskScheme extends AbstractScheme {
	private List<Long> ids;
	private Task task;
	private TaskService taskService;
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	
	public TaskService getTaskService() {
		return taskService;
	}
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
	/*
	 * 删除业务
	 */
	public Object doDeleteTasks(){
		Assert.notNull(ids, "被删除的组ID集合不能为空。");
		int n = 0;
		for(long sn: ids) {
			try{
			System.out.println("业务sn："+sn);
			taskService.taskDelete(sn);//task.sn
			n+=1;
			}catch (Exception e) {
				System.out.println(e.getMessage());
				return "业务删除失败";
			}
			
		}
		return "业务删除成功，共删除了 " + n + " 条业务。";
	}
	
}
