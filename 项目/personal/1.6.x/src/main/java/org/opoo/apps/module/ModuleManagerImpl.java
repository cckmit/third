package org.opoo.apps.module;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.opoo.apps.AppsConstants;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventDispatcherAware;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.apps.license.AppsLicenseManager;
import org.opoo.apps.lifecycle.Application;
import org.opoo.apps.lifecycle.ApplicationState;
import org.opoo.apps.lifecycle.event.ApplicationStateChangeEvent;
import org.opoo.apps.module.configurators.ModuleCacheConfigurator;
import org.opoo.apps.module.dao.ModuleBean;
import org.opoo.apps.module.dao.ModuleDao;
import org.opoo.apps.module.dao.ModuleDaoImpl;
import org.opoo.apps.module.event.ModuleEvent;
import org.opoo.apps.module.event.ModuleStateEvent;
import org.opoo.apps.util.ChainingClassLoader;
import org.opoo.util.IOUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jivesoftware.license.License;

/**
 * 模块管理器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ModuleManagerImpl implements ModuleManager, DisposableBean, EventDispatcherAware, EventListener<ApplicationStateChangeEvent>{
	private static final Log log = LogFactory.getLog(ModuleManagerImpl.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	

	private static ModuleManagerImpl instance = new ModuleManagerImpl();
	private ModuleDao moduleDao; //= new LocalModuleDaoImpl();
	private AtomicBoolean initialized = new AtomicBoolean(false);

	private File modulesDirectory;
	private Map<String, Module<?>> modules;
	private Map<String, String> brokenModules;
	private Map<Object, ModuleMetaData> moduleMeta;
	private Map<Module<?>, File> moduleDirs;
	private Map<String, ModuleClassLoader> classloaders;
	private Collection<ModuleConfigurator> configurators;
	private Set<ModuleListener> moduleListeners;

	//private Executor executor;
	private AppsLicenseManager licenseManager;
	private EventDispatcher eventDispatcher;
	private Set<String> devModules;

	private Map<Locale, List<ResourceBundle>> bundleCache;
	

	private ModuleManagerImpl() {
		devModules = new HashSet<String>();
		modulesDirectory = AppsHome.getModules();
		modules = new ConcurrentHashMap<String, Module<?>>();
		brokenModules = new ConcurrentHashMap<String, String>();
		moduleMeta = new ConcurrentHashMap<Object, ModuleMetaData>();
		moduleDirs = new ConcurrentHashMap<Module<?>, File>();
		classloaders = new ConcurrentHashMap<String, ModuleClassLoader>();
		configurators = new ArrayList<ModuleConfigurator>();
		bundleCache = new ConcurrentHashMap<Locale, List<ResourceBundle>>();
		//executor = Executors.newCachedThreadPool();
		//licenseManager = AppsLicenseManager.getInstance();
		moduleListeners = new HashSet<ModuleListener>();
		moduleDao = new ModuleDaoImpl();
		
		log.info("Construct module manager " + ModuleManagerImpl.class.getName());
	}

	public static final synchronized ModuleManagerImpl getInstance() {
		return instance;
	}

	public List<String> getConfigLocations() {
		List<String> result = new ArrayList<String>();
		List<ModuleBean> beans = getModuleBeans();
		for (ModuleBean bean : beans) {
			File currentDir = new File(modulesDirectory, bean.getName());
			if (currentDir.exists()) {
				File springFile = new File(currentDir, AppsConstants.MODULE_INF + "/spring.xml");
				if (springFile.exists()) {
					try {
						log.info(String.format("Adding spring.xml for module %s to the main spring context.",
								currentDir.getName()));
						result.add(springFile.toURI().toURL().toString());
					} catch (MalformedURLException e) {
						log.error(e.getMessage(), e);
					}
				} else {
					if (IS_DEBUG_ENABLED)
						log.debug(String.format("Module %s is restartable but does not have a spring config",
								currentDir.getName()));
				}
			} else {
				log
						.warn(String
								.format(
										"Could not find the local directory for module %s. An additional restart will be needed forthe module to load correctly",
										currentDir.getName()));
			}
		}

		//if (Boolean.parseBoolean(System.getProperty("apps.devMode", "false"))) {
		if(AppsGlobals.isDevMode()){
			if (IS_DEBUG_ENABLED) {
				log.debug("Adding dev modules spring configurations.");
			}
			List<String> paths = ModuleUtils.getDevModulePaths();
			for (String path : paths) {
				File dir = new File(path);
				if (dir.exists()) {
					File springConf = new File(dir, AppsConstants.MODULE_INF + "/spring.xml");
					if (springConf.exists()) {
						try {
							result.add(springConf.toURI().toURL().toString());
						} catch (MalformedURLException e) {
							log.error(e.getMessage(), e);
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 安装待安装模块包，创建丢失的模块，清理已经卸载的模块，初始化类加载器。
	 * 这个方法应该只执行一次，但并不做严格限制。
	 * 
	 * @throws ModuleException
	 */
	public void preInit() throws ModuleException {
		try {
			if (AppsGlobals.isSetup()) {
				File deployDir = AppsHome.getModules();
				if (deployDir.exists()) {
					File files[] = deployDir.listFiles(new FilenameFilter() {
						public boolean accept(File dir, String name) {
							return name.endsWith(".jar");// ||
							// name.endsWith(".zip");
						}
					});

//					File arr$[] = files;
//					int len$ = arr$.length;
//					for (int i$ = 0; i$ < len$; i$++) {
//						File f = arr$[i$];
					for(File f: files){
						try {
							//File file = f;
							installModule(f);
							try {
								log.debug("Deleting module jar " + f.getName());
								FileUtils.forceDelete(f);
								// log.debug("Rename module jar " +
								// f.getName());
								// file.renameTo(new File(deployDir, f.getName()
								// + "_installed"));
							} catch (Exception e) {
								log.error(e.getMessage(), e);
							}
							continue;
						} catch (Exception e) {
							log.warn((new StringBuilder()).append("Renaming broken module ").append(f.getName())
									.append(" to ").append(f.getName()).append("_broken.").toString());
						}
						if (!f.renameTo(new File(deployDir, (new StringBuilder()).append(f.getName()).append("_broken")
								.toString())))
							log.error((new StringBuilder()).append("Unable to rename module ").append(f.getName())
									.toString());
					}
				}
				moduleDirs.clear();
				modules.clear();
				moduleMeta.clear();

				// try
				// {
				// FileUtils.deleteDirectory(moduleDirectory);
				// }
				// catch(IOException e)
				// {
				// throw new ModuleException(e);
				// }
				// moduleDirectory.mkdirs();
				List<ModuleBean> beans = getModuleBeans();

				//创建不存在（丢失）的模块目录，可能是在集群中别的节点安装的模块
				createMissingModules(beans);
				//清除已经卸载的模块
				clearUninstalledModules(beans);

				createModuleCacheDirectories(beans);
				Thread currentThread = Thread.currentThread();
				currentThread.setContextClassLoader(createModuleChainingClassloader(beans));
				
				////
				//专门针对cache的配置
				ModuleCacheConfigurator configurator = new ModuleCacheConfigurator();
				for(ModuleBean bean: beans){
					File moduleDir = new File(modulesDirectory, bean.getName());
					configurator.configure(moduleDir, bean.getName());
				}
				
				List<String> paths = ModuleUtils.getDevModulePaths();
				for(String path: paths){
					File dir = new File(path);
					if(dir.exists()){
						configurator.configure(dir, dir.getName());
					}
				}
			}
		} catch (Throwable throwable) {
			throw new ModuleException(throwable);
		}
	}

	/**
	 * 清除已卸载（在dao中不存在）的模块部署目录。
	 * 
	 * @param beans
	 */
	private void clearUninstalledModules(List<ModuleBean> beans) {
		Map<String, ModuleBean> map = asMap(beans);
		File[] files = modulesDirectory.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});

		for (File file : files) {
			String moduleName = file.getName();
			if (map.get(moduleName) == null) {
				try {
					FileUtils.deleteDirectory(file);
					log.info("删除不存在模块的目录：" + file.getAbsolutePath());
				} catch (IOException e) {
					log.error("删除不存在模块的目录出错: " + e.getMessage());
				}
			}
		}
	}

	private Map<String, ModuleBean> asMap(List<ModuleBean> beans) {
		Map<String, ModuleBean> map = new HashMap<String, ModuleBean>();
		for (ModuleBean bean : beans) {
			map.put(bean.getName(), bean);
		}
		return map;
	}

	/**
	 * 创建不存在（丢失）的模块目录，可能是在集群中别的节点安装的模块。
	 * @param beans
	 */
	private void createMissingModules(List<ModuleBean> beans) {
		for (ModuleBean bean : beans) {
			File moduleDir = new File(modulesDirectory, bean.getName());
			if(moduleDir.exists()){
				if(moduleDir.lastModified() < bean.getModificationTime().getTime()){
					log.info("库中的模块已经更新，删除旧的模块部署缓存: " + bean.getName());
					try {
						FileUtils.deleteDirectory(moduleDir);
					} catch (IOException e) {
						log.error("删除旧的模块部署缓存出错", e);
					}
				}
			}
			if (!moduleDir.exists()) {
				try {
					InputStream data = moduleDao.getModuleData(bean);
					File jarFile = ModuleUtils.outputJarFile(bean.getName(), data, modulesDirectory);
					ModuleUtils.extractModule(jarFile, modulesDirectory);
					jarFile.delete();
					moduleDir.setLastModified(bean.getModificationTime().getTime());
					log.info("部署丢失的模块：" + bean.getName());
				} catch (IOException e) {
					log.error(e.getMessage(), e);
					throw new RuntimeException(e);
				}
			}
		}
	}

	protected ClassLoader createModuleChainingClassloader(List<ModuleBean> beans) {
		ClassLoader parent = Thread.currentThread().getContextClassLoader();
		if (parent == null) {
			parent = Application.class.getClassLoader();
		}
		if (AppsGlobals.isSetup()) {
			List<ClassLoader> moduleClassLoaders = new ArrayList<ClassLoader>();
			//1
			for (ModuleBean bean : beans) {
				File f = new File(modulesDirectory, bean.getName());
				ModuleClassLoader mcl = getModuleClassLoader(bean.getName(), f);
				moduleClassLoaders.add(mcl.getClassLoader());
			}
//			//2
//			File[] dirs = modulesDirectory.listFiles(new FileFilter(){
//				public boolean accept(File pathname) {
//					return pathname.isDirectory();
//				}
//			});
//			for(File f: dirs){
//				ModuleClassLoader mcl = getModuleClassLoader(f.getName(), f);
//				moduleClassLoaders.add(mcl.getClassLoader());
//			}
			
			

			//if (Boolean.parseBoolean(System.getProperty("apps.devMode", "false"))) {
			if(AppsGlobals.isDevMode()){
				List<String> paths = ModuleUtils.getDevModulePaths();
				for (String path : paths) {
					File dir = new File(path);
					if (dir.exists()) {
						ModuleClassLoader mcl = getModuleClassLoader(dir.getName(), dir);
						moduleClassLoaders.add(mcl.getClassLoader());
						if(log.isDebugEnabled()){
							log.debug("Add class loader for dev module: " + dir.getName());
						}
					}
				}

			}
			return new ChainingClassLoader(parent, moduleClassLoaders);
		} else {
			return parent;
		}
	}

	private ModuleClassLoader getModuleClassLoader(String name, File f) {
		ModuleClassLoader cl = (ModuleClassLoader) classloaders.get(name);
		if (cl == null) {
			cl = new ModuleClassLoader();
			cl.addDirectory(f);
			cl.initialize();
			classloaders.put(name, cl);
		}
		return cl;
	}

	private void createModuleCacheDirectories(List<ModuleBean> beans) {
		// TODO Auto-generated method stub

	}

	public ModuleResultList installModule(File file) throws ModuleException {
		InputStream in = null;
		try {
			if(IS_DEBUG_ENABLED){
				log.debug("Install module file " + file);
			}
			File moduleDir = ModuleUtils.extractModule(file, modulesDirectory);
			String moduleName = moduleDir.getName();

			if (IS_DEBUG_ENABLED) {
				log.debug("Extracted to dir " + moduleDir);
			}

			if (moduleName == null || moduleName.contains("installtask")) {
				String msg = "Could not determine module name";
				brokenModules.put(file.getName(), msg);
				log.error(msg);
				throw new ModuleException(msg);
			}

			ModuleBean bean = new ModuleBean(moduleName);

			Document moduleXML = ModuleUtils.getModuleConfiguration(moduleDir);
			if (moduleXML == null) {
				String msg = String.format("Module %s does not contain a module.xml", moduleName);
				brokenModules.put(moduleName, msg);
				log.error(msg);
				throw new ModuleException(msg);
			}

			// Module<?> module = getModule(moduleName);
			// if (module == null) {
			// module = new DummyModule(moduleName);
			// ModuleMetaDataImpl metaData = new ModuleMetaDataImpl(module,
			// null, moduleXML, moduleDir);
			// metaData.setInstalled(true);
			// registerModule(module, moduleDir, metaData);
			// }
			
			//内存中是否存在
			ModuleMetaDataImpl meta = null;
			Module<?> module = getModule(moduleName);
			if(module == null){
				module = new DummyModule(moduleName);
				meta = new ModuleMetaDataImpl(module, null, moduleXML, moduleDir);
				meta.setInstalled(true);
				registerModule(module, moduleDir, meta);
			}else{
				meta = (ModuleMetaDataImpl) getMetaData(module);
			}
				

			// 如果有，先卸载，再重新解压
			if (moduleDao.getByName(moduleName) != null) {
				log.info(String.format("Module %s is already installed, uninstalling then reinstalling.", moduleName));
				uninstallModule(moduleName);
				moduleDir = ModuleUtils.extractModule(file, modulesDirectory);
			}
			

			// 存库
			bean.setScope(meta.getScope());
			in = new BufferedInputStream(new FileInputStream(file));
			//int 最大 2G，作为模块一般不可能有大于2g的包
			int contentLength = (int) file.length();
			//bean.setLocal(meta.isLocalModule());
			moduleDao.create(bean, contentLength, in);
			if(IS_DEBUG_ENABLED){
				log.debug("Save module bean " + bean.getName());
			}
			

//			// 重新创建内存对象
//			moduleXML = ModuleUtils.getModuleConfiguration(moduleDir);
//			Module<DummyModule> module = new DummyModule(moduleName);
//			ModuleMetaDataImpl meta = new ModuleMetaDataImpl(module, null, moduleXML, moduleDir);
//			meta.setInstalled(true);
//			registerModule(module, moduleDir, meta);

			// 发送事件
			if (eventDispatcher != null) {
				ModuleStateEvent event = new ModuleStateEvent(ModuleStateEvent.State.INSTALLED, meta);
				eventDispatcher.dispatchEvent(event);
			}

			return new ModuleResultList(meta, RequireRestartResult.getRequireRestartResult());
		} catch (ModuleException ex) {
			brokenModules.put(file.getName(), "Module error: " + ex.getMessage());
			log.warn(ex);
			throw ex;
		} catch (Exception ex) {
			brokenModules.put(file.getName(), "Unknown error: " + ex.getMessage());
			log.warn(ex);
			throw new ModuleException(ex);
		}finally{
			IOUtils.close(in);
		}
	}

	public ModuleResultList uninstallModule(String moduleName) {
		if(IS_DEBUG_ENABLED){
			log.debug("Uninstall module " + moduleName);
		}
		
		moduleDao.delete(moduleName);
		if(IS_DEBUG_ENABLED){
			log.debug("Delete module bean " + moduleName);
		}

		ModuleMetaDataImpl metaData = (ModuleMetaDataImpl) getMetaData(moduleName);
		if (metaData != null) {
			metaData.setUninstalled(true);
		}

		if (eventDispatcher != null) {
			ModuleStateEvent event = new ModuleStateEvent(ModuleStateEvent.State.UNINSTALLED, metaData);
			eventDispatcher.dispatchEvent(event);
		}

		return new ModuleResultList(metaData, RequireRestartResult.getRequireRestartResult());

	}

	protected void registerModule(Module<?> module, File moduleDir, ModuleMetaData meta) {
		modules.put(moduleDir.getName(), module);
		moduleDirs.put(module, moduleDir);
		moduleMeta.put(moduleDir.getName(), meta);
		moduleMeta.put(module, meta);
	}

	public Module<?> getModule(String name) {
		return modules.get(name);
	}

	protected List<ModuleBean> getModuleBeans() {
		return moduleDao.getModuleBeans();
	}

	public ModuleMetaData getMetaData(String name) {
		return moduleMeta.get(name);
	}

	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
		if(log.isDebugEnabled()){
			log.debug("set eventDispatcher: "  + eventDispatcher);
		}
	}
	
	/**
	 * 
	 * @param moduleFileName
	 * @return
	 */
	public boolean isModuleFilePresent(String moduleFileName){
		return (new File((new StringBuilder()).append(modulesDirectory).append(File.separator).append(moduleFileName).toString())).exists();
	}


	public void init() {
		log.info("Initializing module manager.");
		if (initialized.get()) {
			return;
		}

		try {
			final List<ModuleBean> beans = moduleDao.getModuleBeans();
			
//			executor.execute(new Runnable() {
//				public void run() {
	//					while(!Application.isContextInitialized()){
	//						try {
	//							Thread.sleep(1000);
	//						} catch (InterruptedException e) {
	//							// TODO Auto-generated catch block
	//							e.printStackTrace();
	//						}
	//					};
					if (Application.isContextInitialized()) {
						for (ModuleBean bean : beans) {
							File moduleDir = new File(modulesDirectory, bean.getName());
							try {
								loadModule(moduleDir, bean);
							} catch (ModuleException e) {
								log.error(e.getMessage(), e);
							}
						}

						loadDevModules();
						
						ModuleManagerImpl.this.initialized.set(true);
					}else{
						log.warn("Application is not initialized, skip loading modules.");
					}
//				}
//			});
			//initialized.set(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			initialized.set(false);
		}
	}

	protected void loadDevModules() {
		List<String> paths = ModuleUtils.getDevModulePaths();
		for (String path : paths) {
			try {
				File moduleDir = new File(path);
				File dir = null;
				if (moduleDir.isAbsolute()) {
					dir = moduleDir;
				} else {
					String workingDir = System.getProperty("user.dir");
					dir = new File(workingDir, path);
				}
				if (!getDevModules().contains(dir.toString())) {
					ModuleBean bean = new ModuleBean(dir.getName());
					// bean.setName(dir.getName());
					loadModule(dir, bean);
					getDevModules().add(dir.toString());
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public Set<String> getDevModules() {
		return devModules;
	}

	protected List<ConfiguratorResult> loadModule(File moduleDir, ModuleBean bean) throws ModuleException {
		List<ConfiguratorResult> results = new ArrayList<ConfiguratorResult>();
		if (!AppsGlobals.isSetup()) {
			return Collections.emptyList();
		}

		log.debug((new StringBuilder()).append("Loading module ").append(moduleDir.getName()).toString());

		Document moduleXML = null;
		try {
			moduleXML = ModuleUtils.getModuleConfiguration(moduleDir);
		} catch (DocumentException e) {
			throw new ModuleException(e);
		}
		if (moduleXML == null) {
			String msg = (new StringBuilder()).append("Module ").append(moduleDir.getName()).append(
					" could not be loaded: no module.xml file found").toString();
			log.error(msg);
			brokenModules.put(moduleDir.getName(), "No module.xml found.");
			throw new ModuleException(msg);
		}

		Node moduleNameNode = moduleXML.selectSingleNode("/module/name");
		String moduleName = moduleNameNode.getText();
		isValidVersion(moduleName, moduleXML, moduleDir);
		ModuleClassLoader moduleLoader = getModuleClassLoader(moduleName, moduleDir);
		//System.out.println(moduleLoader);
		if (moduleLoader == null) {
			return Collections.emptyList();
		}
		moduleLoader.initialize();

		Module<?> module = null;
		Node classNode = moduleXML.selectSingleNode("/module/class");
		if (classNode != null) {
			String className = classNode.getText();
			try {
				module = (Module<?>) moduleLoader.loadClass(className).newInstance();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new ModuleException(e);
			}
		} else {
			module = new DummyModule(moduleName);
		}

		ModuleMetaDataImpl metaData = new ModuleMetaDataImpl(module, moduleLoader, moduleXML, moduleDir);
		metaData.setModuleBean(bean);

		registerModule(module, moduleDir, metaData);

		ConfigurationContext context = new ConfigurationContext(metaData);
		Thread currentThread = Thread.currentThread();
		ClassLoader oldLoader = currentThread.getContextClassLoader();
		try {
			currentThread.setContextClassLoader(moduleLoader.getClassLoader());
			for (ModuleConfigurator configurator : configurators) {
				configurator.configure(context);
			}
		} catch (Exception e) {
			brokenModules.put(moduleDir.getName(), "Failed to configure class loader.");
			throw new ModuleException(e);
		} finally {
			if (oldLoader != null)
				currentThread.setContextClassLoader(oldLoader);
		}

		try {
			module.init();
			fireModuleCreatedEvent(moduleDir.getName(), module);
		} catch (IncompatibleClassChangeError e) {
			log.error((new StringBuilder()).append("Unable to initialize module, module ").append(moduleName).append(
					" binds to an old class version needs to be required.").toString());
			results.add(ModuleRequiresRebuildResult.getModuleRequiresRebuildResult());
			brokenModules.put(moduleDir.getName(), "Failed to initialize.");
		}

		results.addAll(context.getResults());
		ChainingClassLoader.clearCache();
		return results;
	}

	private void fireModuleCreatedEvent(String name, Module<?> module) {
		if (eventDispatcher != null) {
			ModuleEvent event = new ModuleEvent(ModuleEvent.Type.CREATED, module);
			eventDispatcher.dispatchEvent(event);
		}
		Collection<ModuleListener> list = new ArrayList<ModuleListener>(moduleListeners);
		for(ModuleListener l: list){
			l.moduleCreated(name, module);
		}
		
		if(log.isDebugEnabled()){
			log.debug("dispatch module create event for module: " + name);
		}
	}

    private void fireModuleDestroyedEvent(String name, Module<?> module) {
		if (eventDispatcher != null) {
			ModuleEvent event = new ModuleEvent(ModuleEvent.Type.DESTROYED, module);
			eventDispatcher.dispatchEvent(event);
		}
    	Collection<ModuleListener> list = new ArrayList<ModuleListener>(moduleListeners);
		for(ModuleListener l: list){
			l.moduleDestroyed(name, module);
		}
		
		if(log.isDebugEnabled()){
			log.debug("dispatch module destroy event for module: " + name);
		}
    }
	protected void isValidVersion(String moduleName, Document moduleXML, File moduleDir) throws ModuleException {
		License.Version version = licenseManager.getLicense().getVersion();
		Element maxServerVersionElement = (Element) moduleXML.selectSingleNode("/module/maxServerVersion");
		if (maxServerVersionElement != null) {
			String s = maxServerVersionElement.getText();
			if (s != null) {
				s = s.trim();
				License.Version maxServerVersion = License.Version.parseVersion(s);
				if (version.compareTo(maxServerVersion) > 0) {
					String msg = String.format("Module %s requires a version less than or equal to %s", moduleName, s);
					log.error(msg);
					throw new ModuleException(msg);
				}
			}
		}
		Element minServerVersionElement = (Element) moduleXML.selectSingleNode("/module/minServerVersion");
		if (minServerVersionElement != null) {
			String s = minServerVersionElement.getText();
			if (s != null) {
				s = s.trim();
				License.Version minServerVersion = License.Version.parseVersion(s);
				if (version.compareTo(minServerVersion) < 0) {
					String msg = String.format("Module %s requires a version of at least %s", moduleName, s);
					log.error(msg);
					throw new ModuleException(msg);
				}
			} else {
				log.warn(String.format("Empty minServerVersion element has no value in module %s", moduleName));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opoo.apps.module.ModuleManager#deleteBrokenModule(java.lang.String)
	 */
	public void deleteBrokenModule(String name) {
		try {
			moduleDao.delete(name);
		} catch (Exception e) {
		}
		try {
			File f = new File(modulesDirectory, (new StringBuilder()).append(name).append("_broken").toString());
			FileUtils.forceDelete(f);
		} catch (Exception e) {
		}
		brokenModules.remove(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opoo.apps.module.ModuleManager#getBrokenModule()
	 */
	public Map<String, String> getBrokenModules() {
		return brokenModules;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opoo.apps.module.ModuleManager#getClassLoaders()
	 */
	public Collection<ClassLoader> getClassLoaders() {
		List<ClassLoader> list = new ArrayList<ClassLoader>();
		for (ModuleClassLoader mcl : classloaders.values()) {
			list.add(mcl.getClassLoader());
		}
		return Collections.unmodifiableCollection(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opoo.apps.module.ModuleManager#getModuleClassloader(org.opoo.apps
	 * .module.Module)
	 */
	public ModuleClassLoader getModuleClassloader(Module<?> module) {
		ModuleMetaData md = getMetaData(module);
		if (md != null) {
			return classloaders.get(md.getName());
		} else {
			log.warn((new StringBuilder()).append("No module meta data object was found for ").append(module).append(
					" perhaps, the module has been uninstalled.").toString());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opoo.apps.module.ModuleManager#getModuleMetaData(org.opoo.apps.module
	 * .Module)
	 */
	public ModuleMetaData getMetaData(Module<?> module) {
		return moduleMeta.get(module);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opoo.apps.module.ModuleManager#getModuleResourceBundle(org.opoo.apps
	 * .module.Module, java.util.Locale)
	 */
	public ResourceBundle getModuleResourceBundle(Module<?> module, Locale locale) {
		ModuleClassLoader loader = getModuleClassloader(module);
		ResourceBundle bundle = null;
		try {
			bundle = ResourceBundle.getBundle("module_i18n", locale, loader.getClassLoader());
		} catch (MissingResourceException e) {
			bundle = null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return bundle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opoo.apps.module.ModuleManager#getModuleResourceBundles(java.util
	 * .Locale)
	 */
	public List<ResourceBundle> getModuleResourceBundles(Locale locale) {
		List<ResourceBundle> resources = bundleCache.get(locale);
		if (resources == null) {
			resources = new LinkedList<ResourceBundle>();
			for (Module<?> module : modules.values()) {
				try {
					ResourceBundle b = getModuleResourceBundle(module, locale);
					if (b != null)
						((LinkedList<ResourceBundle>) resources).addFirst(b);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}

			bundleCache.put(locale, resources);
		}
		return resources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opoo.apps.module.ModuleManager#getModules()
	 */
	public Collection<Module<?>> getModules() {
		return Collections.unmodifiableCollection(modules.values());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opoo.apps.module.ModuleManager#isInitialized()
	 */
	public boolean isInitialized() {
		return initialized.get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opoo.apps.module.ModuleManager#isModuleBroken(java.lang.String)
	 */
	public boolean isModuleBroken(String name) {
		return brokenModules.containsKey(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opoo.apps.module.ModuleManager#uninstallModule(org.opoo.apps.module
	 * .Module)
	 */
	public ModuleResultList uninstallModule(Module<?> module) {
		ModuleMetaData meta = moduleMeta.get(module);
		return uninstallModule(meta.getName());
	}

	
	@Transactional(propagation=Propagation.REQUIRED)
	public void destroy() {
		for (Module<?> module : modules.values()) {
			ModuleMetaData meta = getMetaData(module);
			ConfigurationContext ctx = new ConfigurationContext(meta);
			for (ModuleConfigurator config : configurators) {
				config.destroy(ctx);
			}
			try {
				module.destroy();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		modules.clear();
		brokenModules.clear();
		moduleDirs.clear();
		classloaders.clear();
		initialized.set(false);
	}

	 public ModuleResultList unloadModule(String moduleName) {
		ModuleResultList moduleresultlist = null;
		bundleCache.clear();
		log.debug((new StringBuilder()).append("Unloading module ").append(moduleName).toString());
		Module<?> module = modules.get(moduleName);
		if (module == null) {
			List<ConfiguratorResult> results = Collections.emptyList();
			moduleresultlist = new ModuleResultList((ModuleMetaData) null, results);
		} else {
			ModuleMetaData meta = moduleMeta.get(moduleName);
			ModuleClassLoader classLoader = classloaders.get(module);
			if (classLoader != null)
				classLoader.destroy();
			modules.remove(moduleName);
			moduleMeta.remove(moduleName);
			moduleMeta.remove(module);
			moduleDirs.remove(module);
			classloaders.remove(moduleName);
			ConfigurationContext context = new ConfigurationContext(meta);
			for (ModuleConfigurator config : configurators) {
				config.destroy(context);
			}

			module.destroy();
			ChainingClassLoader.clearCache();
			fireModuleDestroyedEvent(moduleName, module);
			if (eventDispatcher != null) {
				ModuleStateEvent event = new ModuleStateEvent(ModuleStateEvent.State.UNLOADED, meta);
				eventDispatcher.dispatchEvent(event);
			}
			List<ConfiguratorResult> results = context.getResults();
			moduleresultlist = new ModuleResultList(meta, results);
		}
		return moduleresultlist;
	}
	


	/**
	 * @param moduleDao the moduleDao to set
	 */
	public void setModuleDao(ModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}


	/**
	 * @param configurators the configurators to set
	 */
	public void setConfigurators(Collection<ModuleConfigurator> configurators) {
		this.configurators = configurators;
	}



//	/**
//	 * @param executorService the executorService to set
//	 */
//	public void setExecutor(Executor executor) {
//		this.executor = executor;
//		if(log.isDebugEnabled()){
//			log.debug("set executor: "  + executor);
//		}
//	}



	/**
	 * @param licenseManager the licenseManager to set
	 */
	public void setLicenseManager(AppsLicenseManager licenseManager) {
		this.licenseManager = licenseManager;
		if(log.isDebugEnabled()){
			log.debug("set licenseManager: "  + licenseManager);
		}
	}
	


	public void addModuleListener(ModuleListener listener) {
		this.moduleListeners.add(listener);
	}

	public void removeModuleListener(ModuleListener listener) {
		this.moduleListeners.remove(listener);
	}
	public static void main(String[] args) throws ModuleException {
//		System.setProperty(LogFactory.DIAGNOSTICS_DEST_PROPERTY, "STDERR");
//		
//		LogLog.setInternalDebugging(true);
//		LogLog.setQuietMode(false);
//		
//		System.setProperty("apps.logs", AppsHome.getAppsLogsPath());
//		PropertyConfigurator.configure("E:\\java-works\\opoo-apps-1.5.0\\src\\main\\resources\\log4j.properties");
		ModuleManagerImpl impl = ModuleManagerImpl.getInstance();
		System.out.println(ModuleManagerImpl.log);
		
		impl.preInit();
//		// ModuleManagerImpl.getInstance().installModule(new File("d:/m.zip"));
		impl.init();
//		Collection<Module<?>> ms = impl.getModules();
//		System.out.println(impl.getConfigLocations());
//		
//		ModuleBean bean = new ModuleBean("um");
//		impl.loadModule(new File("C:\\apps\\modules\\um"), bean);
	}

	
	
	
	/**
	 * 
	 */
	public void handle(ApplicationStateChangeEvent event) {
		if(event.getPreviousState() == ApplicationState.INITIALIZING
				&& event.getNewState() == ApplicationState.INITIALIZED){
			init();
		}
	}

	/**
	 * @return the moduleDao
	 */
	public ModuleDao getModuleDao() {
		if(moduleDao == null){
			moduleDao = new ModuleDaoImpl();
		}
		return moduleDao;
	}
	
}
