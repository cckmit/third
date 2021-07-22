package org.opoo.apps.module.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.opoo.apps.module.ModuleMetaData.Scope;
import org.opoo.apps.test.SpringTests;

public class ModuleDaoImplTest extends SpringTests {
	static{
		System.setProperty("apps.home", "C:\\apps");
	}
	

	public void testCreateModuleBeanIntInputStream() throws FileNotFoundException {
		ModuleDao moduleDao= new ModuleDaoImpl();
		System.out.println(moduleDao + " ------------------");
		ModuleBean bean = new ModuleBean();
		bean.setScope(Scope.local);
		bean.setName("sample");
		File file = new File("D:\\workspace\\apps-modules\\target\\sample.modar");
		int len = (int) file.length();
		moduleDao.create(bean, len, new FileInputStream(file));
		
		super.setComplete();
	}

	public void etestGetModuleBeans() {
		//fail("Not yet implemented");
		ModuleDao moduleDao= new ModuleDaoImpl();
		List<ModuleBean> beans = moduleDao.getModuleBeans();
		for(ModuleBean bean: beans){
			System.out.println(bean);
		}
	}

}
