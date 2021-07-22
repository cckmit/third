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
	 * �˵���
	 * ���÷����������壬�����ڼ̳�����д����Ϊdo����������д��
	 * @param unarchive
	 * @return
	 */
	protected Object unarchive(ObjectUnarchive unarchive){
		//System.out.println("*******************************" + unarchive);
		ObjectUnarchive unarchive2 = getObjectAdminService().unarchiveObject(getObject(), unarchive);
		addRelatedItem(unarchive2);
		
		getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
		return "�˵��ɹ���";
	}
}
