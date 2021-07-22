package org.opoo.apps.module;

import java.io.File;
import java.util.List;

import org.opoo.apps.AppsHome;
import org.opoo.apps.module.dao.ModuleBean;

import junit.framework.TestCase;

public class ModuleManagerImplTest extends TestCase {

	public void testInstallModule() throws ModuleException{
		AppsHome.initialize();
		
		
		File file = new File("D:\\workspace\\apps-modules\\target\\apps-modules-1.0-SNAPSHOT.jar");
		file = new File("D:\\workspace\\apps-modules\\target\\sample.modar");
		ModuleManagerImpl.getInstance().getModuleDao();
		ModuleManagerImpl.getInstance().installModule(file);
		
		List<ModuleBean> beans = ModuleManagerImpl.getInstance().getModuleBeans();
		for(ModuleBean bean: beans){
			System.out.println(bean);
		}
	}
}
