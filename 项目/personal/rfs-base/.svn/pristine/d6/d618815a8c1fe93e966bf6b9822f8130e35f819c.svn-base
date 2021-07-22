package cn.redflagsoft.base.scheme.schemes.commons;

import cn.redflagsoft.base.bean.commons.ObjectPublish;

/**
 * 
 * @author ZF
 *
 */
public class ObjectPublishWorkScheme extends AbstractObjectAdminWorkScheme {
	private ObjectPublish publish;

	public ObjectPublish getPublish() {
		return publish;
	}

	public void setPublish(ObjectPublish publish) {
		this.publish = publish;
	}
	
	public Object doScheme(){
		return publish(publish);
	}
	
	/**
	 * 发布。
	 * 将该方法单独定义，便于在继承中重写，因为do方法不能重写。
	 * @param publish
	 * @return
	 */
	protected Object publish(ObjectPublish publish){
		ObjectPublish publish2 = getObjectAdminService().publishObject(getObject(), publish);
		addRelatedItem(publish2);
		
		getMattersHandler().finishMatters(getTask(), getWork(), getObject(), getMatterVO().getMatterIds());
		return "发布成功！";
	}
	
}
