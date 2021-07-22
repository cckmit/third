package cn.redflagsoft.base.scheme.schemes;

import java.util.HashMap;
import java.util.Map;

import cn.redflagsoft.base.aop.interceptor.ParametersAware;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.test.BaseTests;

public class CheckedItemsPrintSchemeTest extends BaseTests {
	protected SchemeManager schemeManager; 
	
	///processType=5011&id={XXX}
	public void testDoScheme() throws Exception {
		Scheme scheme = schemeManager.getScheme("checkedItemsPrintScheme");
		System.out.println(scheme);
		Map<String,String> params = new HashMap<String,String>();
		params.put("targetProcessType", "5011");
		params.put("ids[0]", "121212");
		params.put("ids[1]", "121213");
		params.put("ids[2]", "121214");
		params.put("targetObjectIdProperty", "id");
		((ParametersAware) scheme).setParameters(params);
		
		
		//测试必须在子项目里，5011的process在基础工程不存在
		//Object object = SchemeInvoker.invoke(scheme, "scheme");
		//System.out.println(object);
		
		//fail("Not yet implemented");
	}
}
