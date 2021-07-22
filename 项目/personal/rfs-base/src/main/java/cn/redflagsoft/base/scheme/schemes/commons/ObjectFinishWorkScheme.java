package cn.redflagsoft.base.scheme.schemes.commons;

import cn.redflagsoft.base.bean.commons.ObjectFinish;

/**
 * 
 * @author ZF
 *
 */
public class ObjectFinishWorkScheme extends AbstractObjectAdminWorkScheme {
	private ObjectFinish finish;

	public ObjectFinish getFinish() {
		return finish;
	}

	public void setFinish(ObjectFinish finish) {
		this.finish = finish;
	}
	
	public Object doScheme(){
		return finish(finish);
	}
	
	/**
	 * 结束。
	 * 将该方法单独定义，便于在继承中重写，因为do方法不能重写。
	 * @param finish
	 * @return
	 */
	protected Object finish(ObjectFinish finish){
		ObjectFinish finish2 = getObjectAdminService().finishObject(getObject(), finish);
		addRelatedItem(finish2);
		
		getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
		return "结束成功！";
	}
}
