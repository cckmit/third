package org.opoo.apps.module.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsHome;
import org.opoo.apps.module.ModuleMetaData.Scope;
import org.opoo.util.OpooProperties;
import org.opoo.util.XMLOpooProperties;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

/**
 * 本地模块的Dao访问类。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class LocalModuleDaoImpl implements ModuleDao {
	private static final Log log = LogFactory.getLog(LocalModuleDaoImpl.class);
	private OpooProperties<String, String> props;
	
	public LocalModuleDaoImpl() {
		super();
		init();
	}
	private void init(){
		File file = new File(AppsHome.getAppsHome(), "module-files");
		if(!file.exists()){
			file.mkdirs();
		}
		file = new File(file, "modules.xml");
		if(!file.exists()){
			try {
				createModulesXmlFile(file);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			props = new XMLOpooProperties(file.getAbsolutePath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private static void createModulesXmlFile(File file) throws IOException {
		FileWriter jhWriter = new FileWriter(file);
		jhWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		jhWriter.write("<modules>\n");
		jhWriter.write("</modules>\n");
		jhWriter.flush();
		jhWriter.close();
	}

	public ModuleBean create(ModuleBean module) {
		Assert.notNull(module.getName());
		Date now = new Date();
		module.setCreationTime(now);
		module.setModificationTime(now);
		props.put(module.getName() + ".creationTime", String.valueOf(now.getTime()));
		props.put(module.getName() + ".modificationTime", String.valueOf(now.getTime()));
		System.out.println("创建1");
		return module;
	}

	public ModuleBean create(ModuleBean module, int contentLength, InputStream inputstream) {
		module = create(module);
		setModuleData(module, contentLength, inputstream);
		System.out.println("创建2");
		return module;
	}

	public void delete(ModuleBean module) {
		Assert.notNull(module.getName());
		delete(module.getName());
	}

	public void delete(String name) {
		String file = props.get(name + ".file");
		
		props.remove(name);
		//删除文件
		
		if(file != null){
			File f = new File(file);
			if(f.exists()){
				f.delete();
			}
		}
		System.out.println("删除");
	}

	public ModuleBean getByName(String name) {
		boolean hasValue = false;
		
		ModuleBean m = new ModuleBean(name);
		m.setScope(Scope.local);
		String ct = props.get(name + ".creationTime");
		String mt = props.get(name + ".modificationTime");
		if(ct != null){
			m.setCreationTime(new Date(Long.parseLong(ct)));
			hasValue = true;
		}
		if(mt != null){
			m.setModificationTime(new Date(Long.parseLong(mt)));
			hasValue = true;
		}
		if(hasValue) return m;
		return null;
	}

	public List<ModuleBean> getModuleBeans() {
		Collection<String> names = props.getChildrenNames("");
		log.debug("查询模块：" + names);
		
		List<ModuleBean> beans = new ArrayList<ModuleBean>();
		for(String name: names){
			ModuleBean bean = getByName(name);
			System.out.println("--->>>" + bean);
			if(bean != null && bean.getName() != null){
				beans.add(bean);
			}
		}
		
		return beans;
	}

	public InputStream getModuleData(ModuleBean module) {
		String file = props.get(module.getName() + ".file");
		if(file != null){
			File f = new File(file);
			if(f.exists()){
				try {
					return new FileInputStream(f);
				} catch (FileNotFoundException e) {
					log.error(e);
				}
			}
		}
		return null;
	}

	public void setModuleData(ModuleBean module, int contentLength, InputStream inputstream) {
		try {
			File file = new File(AppsHome.getAppsHome(), "module-files");
			if(!file.exists()){
				file.mkdirs();
			}
			file = new File(file, module.getName() + ".module");
			FileOutputStream fos = new FileOutputStream(file);
			FileCopyUtils.copy(inputstream, fos);
			props.put(module.getName() + ".fileLength", String.valueOf(contentLength));
			props.put(module.getName() + ".file", file.getAbsolutePath());
			props.put(module.getName() + ".modificationTime", String.valueOf(System.currentTimeMillis()));
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
	}
	

	
	public static void main(String[] args) throws IOException{
		LocalModuleDaoImpl dao = new LocalModuleDaoImpl();
//		ModuleBean m = new ModuleBean();
//		m.setCreationTime(new Date());
//		m.setModificationTime(new Date());
//		m.setName("null");
//		//dao.create(m);
//		
//		//dao.delete(m);
//		System.out.println(m);
//		
//		
//		File file = new File(AppsHome.getAppsHome(), "modules.xml");
//		XMLOpooProperties props = new XMLOpooProperties(file.getAbsolutePath());
//		String s = props.getProperty("null");
//		System.out.println(s != null);
//		
//		ModuleBean mm = dao.getByName("null2");
//		System.out.println(mm + "" + dao.getModuleBeans());
		
		
//		FileInputStream fis = new FileInputStream("C:\\apps\\modules\\m.zip_installed");
//		ModuleBean bean = new ModuleBean("um");
//		bean = dao.create(bean);
//		dao.setModuleData(bean, fis.available(), fis);
		
		
		Collection<ModuleBean> c = dao.getModuleBeans();
		for(ModuleBean bean: c){
			System.out.println(bean);
		}
	}
}
