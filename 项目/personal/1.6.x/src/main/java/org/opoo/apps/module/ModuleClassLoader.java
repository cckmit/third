package org.opoo.apps.module;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsConstants;
import org.opoo.apps.lifecycle.Application;

/**
 * 模块的类加载器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ModuleClassLoader {
	private static final Log log = LogFactory.getLog(ModuleClassLoader.class);
	private URLClassLoader classLoader;
	private final List<URL> list = new ArrayList<URL>();

	public ModuleClassLoader() throws SecurityException {
	}

	public void addDirectory(File directory) {
		try {
			directory = new File(directory, AppsConstants.MODULE_INF);
			File classesDir = new File(directory, "classes");
			if (classesDir.exists()) {
				list.add(classesDir.toURI().toURL());
			}
			File databaseDir = new File(directory, "database");
			if (databaseDir.exists()) {
				list.add(databaseDir.toURI().toURL());
			}
			File i18nDir = new File(directory, "i18n");
			if (i18nDir.exists()) {
				list.add(i18nDir.toURI().toURL());
			}
			File libDir = new File(directory, "lib");

			File jars[] = libDir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(".jar") || name.endsWith(".zip");
				}
			});

			if (jars != null) {
				File arr$[] = jars;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++) {
					File jar = arr$[i$];
					if (jar != null && jar.isFile())
						list.add(jar.toURI().toURL());
				}

			}
		} catch (MalformedURLException mue) {
			log.error(mue);
		}
	}

	public Collection<URL> getURLS() {
		return list;
	}

	public void addURL(URL url) {
		list.add(url);
	}

	public void initialize() {
		Iterator<URL> urls = list.iterator();
		URL urlArray[] = new URL[list.size()];
		for (int i = 0; urls.hasNext(); i++) {
			urlArray[i] = urls.next();
		}

		if (classLoader != null) {
			classLoader = new URLClassLoader(urlArray, classLoader);
		} else {
			classLoader = new URLClassLoader(urlArray, findParentClassLoader());
		}
	}

	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return classLoader.loadClass(name);
	}

	public void destroy() {
		classLoader = null;
	}

	private ClassLoader findParentClassLoader() {
		ClassLoader parent = Application.class.getClassLoader();
		if (parent == null)
			parent = getClass().getClassLoader();
		if (parent == null)
			parent = ClassLoader.getSystemClassLoader();
		return parent;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ModuleClassLoader");
		sb.append("{classLoader=").append(classLoader);
		sb.append(", list=").append(list);
		sb.append('}');
		return sb.toString();
	}

}
