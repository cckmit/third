package org.opoo.apps.web.struts2.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.servlet.ServletContext;

import org.opoo.apps.AppsConstants;
import org.opoo.apps.AppsContext;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.module.Module;
import org.opoo.apps.module.ModuleManager;
import org.opoo.apps.module.ModuleManagerImpl;
import org.opoo.apps.module.ModuleMetaData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsConstants;

import com.opensymphony.xwork2.inject.Inject;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;

public class AppsFreemarkerManager extends org.apache.struts2.views.freemarker.FreemarkerManager {
	private static final Log log = LogFactory.getLog(AppsFreemarkerManager.class);

	@Inject(StrutsConstants.STRUTS_I18N_ENCODING)
    public void setEncoding(String encoding) {
		log.debug("设置编码：" + encoding);
        super.setEncoding(encoding);
    }
	@Override
	protected TemplateLoader getTemplateLoader(ServletContext servletContext) {
		TemplateLoader templateLoader = super.getTemplateLoader(servletContext);
		
		AppsContext ctx = Application.getContext();
		ModuleManager mgr = ctx.getModuleManager();
		Collection<Module<?>> modules = mgr.getModules();
		List<TemplateLoader> list = new ArrayList<TemplateLoader>();
		for(Module<?> module: modules){
			ModuleMetaData metaData = mgr.getMetaData(module);
			//System.out.println("--------------------: " + metaData.getModuleDirectory());
			try {
				File file = new File(metaData.getModuleInfDirectory(), "classes");
				if(file.exists()){
					list.add(new FileTemplateLoader(file ));
					if(log.isDebugEnabled()){
						log.debug("为模块'" + metaData.getName() + "'创建 模板目录: " 
							+ file.getAbsolutePath());
					}
				}
				file = new File(metaData.getModuleInfDirectory(), "lib/" + metaData.getName() + ".jar");
				//log.debug("file.exists: " + file);
				if(file.exists()){
					list.add(new JarTemplateLoader(file));
					if(log.isDebugEnabled()){
						log.debug("为Jar文件'" + file + "'创建模板 JarTemplateLoader");
					}
				}
				
				file = new File(metaData.getModuleInfDirectory(), "lib/" + metaData.getName() + "-" + metaData.getVersion() + ".jar");
				if(file.exists()){
					list.add(new JarTemplateLoader(file));
					if(log.isDebugEnabled()){
						log.debug("为Jar文件'" + file + "'创建模板 JarTemplateLoader");
					}
				}
			} catch (IOException e) {
				log.error("创建模板失败", e);
			}
		}
		
		if(mgr instanceof ModuleManagerImpl){
			Set<String> devModules = ((ModuleManagerImpl) mgr).getDevModules();
			for(String devModule: devModules){
				try {
					File module = new File(devModule);
					File moduleInfDir = new File(module, AppsConstants.MODULE_INF);
					File file = new File(moduleInfDir, "classes");
					if(file.exists()){
						list.add(new FileTemplateLoader(file ));
						if(log.isDebugEnabled()){
							log.debug("为模块'" + module.getName() + "'创建 模板目录: " 
								+ file.getAbsolutePath());
						}
					}
					file = new File(moduleInfDir, "lib/" + module.getName() + ".jar");
					//log.debug("file.exists: " + file);
					if(file.exists()){
						list.add(new JarTemplateLoader(file));
						if(log.isDebugEnabled()){
							log.debug("为Jar文件'" + file + "'创建模板 JarTemplateLoader");
						}
					}
				} catch (IOException e) {
					log.error("创建模板失败", e);
				}
			}
		}
		
		if(list.size() > 0){
			list.add(templateLoader);
			return new MultiTemplateLoader(list.toArray(new TemplateLoader[list.size()]));
			
		}
		//System.out.println("========================================" + templateLoader);
		return templateLoader;
	}
	
	/**
	 * 从 Jar 文件中加载指定路径的模板。
	 *
	 */
	private static class JarTemplateLoader implements TemplateLoader{
		private JarFile jarFile;
		public JarTemplateLoader(File jarFile) throws IOException{
			this.jarFile = new JarFile(jarFile);
		}
		
		public void closeTemplateSource(Object templateSource) throws IOException {
			
		}

		public Object findTemplateSource(String name) throws IOException {
			if(name.startsWith("/")){
				name = name.substring(1);
			}
			if(log.isDebugEnabled()){
				log.debug("findTemplateSource: " + name);
			}
			return jarFile.getJarEntry(name);
		}

		public long getLastModified(Object templateSource) {
			ZipEntry ze = (ZipEntry) templateSource;
			return ze.getTime();
		}

		public Reader getReader(Object templateSource, String encoding) throws IOException {
			ZipEntry ze = (ZipEntry) templateSource;
			InputStream inputStream = jarFile.getInputStream(ze);
			//System.out.println(inputStream);
			return new InputStreamReader(inputStream, encoding);
		}
	}
}
