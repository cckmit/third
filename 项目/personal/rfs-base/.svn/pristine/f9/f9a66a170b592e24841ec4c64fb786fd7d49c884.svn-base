package cn.redflagsoft.base.schemes;

import java.util.HashMap;
import java.util.Map;

import cn.redflagsoft.base.test.BaseStrutsTests;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;

public class ProgressWorkSchemeTest extends BaseStrutsTests {

	public void testSaveProgress() throws Exception {
		Map <String,String> map = new HashMap<String, String>();
		map.put("target", "progress");
		map.put("m","submitProgress");
		map.put("objectId","1");
		map.put("progress.objectId","1");
		map.put("progress.objectName","");
		map.put("progress.title","");
		map.put("progress.description","");
		map.put("progress.objectType","1003");
		
		Map<String, Map<String, String>> extraContext = new HashMap<String, Map<String, String>>();
		extraContext.put(ActionContext.PARAMETERS, map);
		ActionProxy proxy = actionProxyFactory.createActionProxy("/xml", "scheme", extraContext, false, false);
		
		proxy.execute();
	}

}
