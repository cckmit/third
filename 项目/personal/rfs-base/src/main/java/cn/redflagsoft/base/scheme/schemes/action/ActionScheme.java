package cn.redflagsoft.base.scheme.schemes.action;

import java.util.List;
import org.opoo.util.Assert;
import cn.redflagsoft.base.bean.Action;
import cn.redflagsoft.base.menu.ActionManager;
import cn.redflagsoft.base.scheme.AbstractScheme;
/**
 * 操作。应用，服务。
 * 菜单对应的服务。
 * 
 * @author 
 *
 */
public class ActionScheme extends AbstractScheme {
	private Long id;
	private List<Long> ids;
	private Action action;
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}

	private ActionManager actionManager;
	
	public ActionManager getActionManager() {
		return actionManager;
	}
	public void setActionManager(ActionManager actionManager) {
		this.actionManager = actionManager;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
	/*
	 * 修改服务
	 */
	public Object doUpdateAction() {
		Action action2 = actionManager.getAction(action.getId());
		
		action2.setName(action.getName());
		action2.setIcon(action.getIcon());
		action2.setLocation(action.getLocation());
		action2.setStatus(action.getStatus());
		action2.setType(action.getType());
		action2.setRemark(action.getRemark());
		action2.setUid(action.getUid());
		
		actionManager.updateAction(action2);
		return "服务修改成功。";
	}
	
	/*
	 * 删除服务
	 */
	public Object doDeleteActions(){
		Assert.notNull(ids, "被删除的组ID集合不能为空。");
		int n = actionManager.removeActions(ids);//action.id
		return "服务删除成功，共删除了 " + n + " 个分组。";
	}
	
	/*
	 * 创建服务
	 */
	public Object doCreateAction(){
		actionManager.saveAction(action);
		return "服务创建成功。";
	}
}
