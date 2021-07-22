package cn.redflagsoft.base.scheme.schemes.commons;

import cn.redflagsoft.base.bean.commons.ObjectShelve;

/**
 * 
 * @author ZF
 *
 */
public class ObjectShelveWorkScheme extends AbstractObjectAdminWorkScheme {
	private ObjectShelve shelve;

	public ObjectShelve getShelve() {
		return shelve;
	}

	public void setShelve(ObjectShelve shelve) {
		this.shelve = shelve;
	}
	
	public Object doScheme(){
		return shelve(shelve);
	}
	
	/**
	 * 搁置。
	 * 将该方法单独定义，便于在继承中重写，因为do方法不能重写。
	 * @param shelve
	 * @return
	 */
	protected Object shelve(ObjectShelve shelve){
		ObjectShelve shelve2 = getObjectAdminService().shelveObject(getObject(), shelve);
		addRelatedItem(shelve2);
		
		getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
		return "搁置成功！";
	}
}
