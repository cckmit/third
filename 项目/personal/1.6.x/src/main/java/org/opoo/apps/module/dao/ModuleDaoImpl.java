package org.opoo.apps.module.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.opoo.apps.database.DataSourceManager;
import org.opoo.apps.module.ModuleMetaData.Scope;


/**
 * 模块数据访问实现类。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ModuleDaoImpl implements ModuleDao {
	private ModuleDao localModuleDao;
	private ModuleDao clusterModuleDao;
	
	public ModuleBean create(ModuleBean module) {
		return getModuleDao(module).create(module);
	}

	public ModuleBean create(ModuleBean module, int contentLength, InputStream inputstream) {
		return getModuleDao(module).create(module, contentLength, inputstream);
	}

	public void delete(ModuleBean module) {
		getModuleDao(module).delete(module);
	}

	
	public void delete(String s) {
		ModuleBean module = getByName(s);
		if(module != null){
			getModuleDao(module).delete(s);
		}
	}

	public ModuleBean getByName(String s) {
		ModuleBean module = getLocalModuleDao().getByName(s);
		if(module == null){
			module = getClusterModuleDao().getByName(s);
		}
		return module;
	}

	public List<ModuleBean> getModuleBeans() {
		List<ModuleBean> list1 = getLocalModuleDao().getModuleBeans();
		List<ModuleBean> list2 = getClusterModuleDao().getModuleBeans();
		ArrayList<ModuleBean> list3 = new ArrayList<ModuleBean>();
		list3.addAll(list2);
		list3.addAll(list1);
		return list3;
	}

	public InputStream getModuleData(ModuleBean module) {
		return getModuleDao(module).getModuleData(module);
	}

	public void setModuleData(ModuleBean module, int contentLength, InputStream inputstream) {
		getModuleDao(module).setModuleData(module, contentLength, inputstream);
	}

	/**
	 * @return the localModuleDao
	 */
	public ModuleDao getLocalModuleDao() {
		if(localModuleDao == null){
			localModuleDao = new LocalModuleDaoImpl();
		}
		return localModuleDao;
	}

	/**
	 * @param localModuleDao the localModuleDao to set
	 */
	public void setLocalModuleDao(ModuleDao localModuleDao) {
		this.localModuleDao = localModuleDao;
	}

	/**
	 * @return the clusterModuleDao
	 */
	public ModuleDao getClusterModuleDao() {
		if (clusterModuleDao == null) {
			ClusterModuleDaoImpl dao = new ClusterModuleDaoImpl();
			dao.setDataSource(DataSourceManager.getRuntimeDataSource());
			//使用默认值
			//dao.setLobHandler(DataSourceManager.getMetaData().getLobHandler());
			clusterModuleDao = dao;
		}
		return clusterModuleDao;
	}

	/**
	 * @param clusterModuleDao the clusterModuleDao to set
	 */
	public void setClusterModuleDao(ModuleDao clusterModuleDao) {
		this.clusterModuleDao = clusterModuleDao;
	}
	
	
	protected ModuleDao getModuleDao(ModuleBean module){
		if(module.getScope() == Scope.cluster){
			return getClusterModuleDao();
		}else if(module.getScope() == Scope.local){
			return getLocalModuleDao();
		}else{
			throw new IllegalArgumentException("找不到DAO: [scope=" + module.getScope() + "]");
		}
	}
}
