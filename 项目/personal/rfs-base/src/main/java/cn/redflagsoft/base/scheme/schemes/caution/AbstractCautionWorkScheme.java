package cn.redflagsoft.base.scheme.schemes.caution;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.scheme.schemes.BaseWorkScheme;
import cn.redflagsoft.base.service.CautionService;
import cn.redflagsoft.base.service.ObjectService;

public abstract class AbstractCautionWorkScheme extends BaseWorkScheme {

	/**
	 * 参数Caution.
	 */
	private Caution caution;

	
	@SuppressWarnings("unchecked")
	@Override
	public void setObjectService(ObjectService objectService) {
		if (objectService instanceof CautionService) {
			// ok
		} else {
			throw new IllegalArgumentException(
					"objectService 必须配置成  AbstractCautionService 的实现类。");
		}
		super.setObjectService(objectService);
	}

	
	public final CautionService getCautionService() {
		ObjectService<?> service = getObjectService();
		if (service instanceof CautionService) {
			return (CautionService) service;
		}
		throw new IllegalArgumentException(
				"配置的 objectService 不是 CautionService 类型。");
	}

	
	
	public Caution getCaution() {
		return caution;
	}

	public void setCaution(Caution caution) {
		this.caution = caution;
	}

	@Override
	public Object doScheme() {
		return "业务处理成功！";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
	}
}
