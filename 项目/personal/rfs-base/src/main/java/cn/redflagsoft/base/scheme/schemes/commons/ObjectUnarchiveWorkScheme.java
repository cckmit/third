package cn.redflagsoft.base.scheme.schemes.commons;

import cn.redflagsoft.base.bean.commons.ObjectUnarchive;

/**
 * 
 * @author ZF
 *
 */
public class ObjectUnarchiveWorkScheme extends AbstractObjectAdminWorkScheme {
	private ObjectUnarchive unarchive;

	public ObjectUnarchive getUnarchive() {
		return unarchive;
	}

	public void setUnarchive(ObjectUnarchive unarchive) {
		this.unarchive = unarchive;
	}
	
	public Object doScheme(){
		return unarchive(unarchive);
	}
	
	/**
	 * 退档。
	 * 将该方法单独定义，便于在继承中重写，因为do方法不能重写。
	 * @param unarchive
	 * @return
	 */
	protected Object unarchive(ObjectUnarchive unarchive){
		//System.out.println("*******************************" + unarchive);
		ObjectUnarchive unarchive2 = getObjectAdminService().unarchiveObject(getObject(), unarchive);
		addRelatedItem(unarchive2);
		
		getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
		return "退档成功！";
	}
}
