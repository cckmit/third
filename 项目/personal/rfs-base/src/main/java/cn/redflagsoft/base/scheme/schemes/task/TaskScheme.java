package cn.redflagsoft.base.scheme.schemes.task;

import java.util.List;
import org.opoo.util.Assert;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.service.TaskService;
/**
 * 
 * ҵ�����
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
	 * ɾ��ҵ��
	 */
	public Object doDeleteTasks(){
		Assert.notNull(ids, "��ɾ������ID���ϲ���Ϊ�ա�");
		int n = 0;
		for(long sn: ids) {
			try{
			System.out.println("ҵ��sn��"+sn);
			taskService.taskDelete(sn);//task.sn
			n+=1;
			}catch (Exception e) {
				System.out.println(e.getMessage());
				return "ҵ��ɾ��ʧ��";
			}
			
		}
		return "ҵ��ɾ���ɹ�����ɾ���� " + n + " ��ҵ��";
	}
	
}
