package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.test.BaseTests;

/**
 * 基础对象加载器测试。
 * 
 * 部分对象应该无法加载，例如大部分子项目中才出现的主对象。
 * @author lcj
 *
 */
public class EntityObjectLoaderTest extends BaseTests {

	protected EntityObjectLoader entityObjectLoader;
	
	public void test0(){
		assertNotNull(entityObjectLoader);
		System.out.println(entityObjectLoader);
		
		RFSEntityObject object = entityObjectLoader.getEntityObject(1002, 1000L);
		System.out.println(object);
	}
}
