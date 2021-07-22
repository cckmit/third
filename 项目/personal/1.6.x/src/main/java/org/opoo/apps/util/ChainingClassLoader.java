package org.opoo.apps.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.opoo.cache.Cache;
import org.opoo.cache.MapCache;

public class ChainingClassLoader extends URLClassLoader {

	private static final Logger log = Logger.getLogger(ChainingClassLoader.class.getName());
	private final ClassLoader parent;
	private final Collection<ClassLoader> loaders;
	private static final URL NULL_URL_ARRAY[] = new URL[0];
	private static Cache<String, Object> cacheLookupCache = new MapCache<String, Object>();

	// new TimedExpirationMap("classLoaderLookupCache", 30000L, 60000L);

	public ChainingClassLoader(ClassLoader parent, Collection<ClassLoader> loaders) {
		super(NULL_URL_ARRAY, parent);
		this.parent = parent;
		this.loaders = loaders;
	}

	public Class<?> loadClass(String string) throws ClassNotFoundException {
		Object cls = cacheLookupCache.get(string);
		if (cls != null) {
			if (cls instanceof ClassNotFoundException) {
				throw (ClassNotFoundException) cls;
			} else {
				return (Class<?>) cls;
			}
		}

		ClassNotFoundException ex = null;
		Class<?> clazz = null;
		try {
			clazz = parent.loadClass(string);
		} catch (ClassNotFoundException e) {
			ex = e;
		}
		if (clazz == null) {
			for (ClassLoader l : loaders) {
				try {
					clazz = l.loadClass(string);
					break;
				} catch (ClassNotFoundException e) {
				}
			}
		}

		if (clazz == null) {
			cacheLookupCache.put(string, ex);
			throw ex;
		} else {
			cacheLookupCache.put(string, clazz);
			return clazz;
		}
	}

	public URL getResource(String string) {
		URL url = parent.getResource(string);
		if (url == null) {
			for (ClassLoader l : loaders) {
				url = l.getResource(string);
				if (url != null) {
					break;
				}
			}
		}
		return url;
	}

	public Enumeration<URL> getResources(String string) throws IOException {
		Enumeration<URL> enumeration = parent.getResources(string);
		if (enumeration == null || !enumeration.hasMoreElements()) {
			for (ClassLoader l : loaders) {
				enumeration = l.getResources(string);
				if (enumeration != null && enumeration.hasMoreElements()) {
					break;
				}
			}
		}
		return enumeration;
	}

	public InputStream getResourceAsStream(String string) {
		InputStream stream = parent.getResourceAsStream(string);
		if (stream == null) {
			for (ClassLoader l : loaders) {
				stream = l.getResourceAsStream(string);
				if (stream != null) {
					break;
				}
			}
		}
		return stream;
	}

	public URL[] getURLs() {
		if (parent instanceof URLClassLoader)
			try {
				URL resource[] = ((URLClassLoader) parent).getURLs();
				if (resource != null) {
					return resource;
				}
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage(), e);
			}
		return super.getURLs();
	}

	public URL findResource(String string) {
		if (parent instanceof URLClassLoader)
			try {
				URL resource = ((URLClassLoader) parent).findResource(string);
				if (resource != null)
					return resource;
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage(), e);
			}
		return super.findResource(string);
	}

	public Enumeration<URL> findResources(String string) throws IOException {
		if (parent instanceof URLClassLoader)
			try {
				Enumeration<URL> resource = ((URLClassLoader) parent).findResources(string);
				if (resource != null)
					return resource;
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage(), e);
			}
		return super.findResources(string);
	}

	public static void clearCache() {
		cacheLookupCache.clear();
	}

	public ClassLoader getWrappedClassLoader() {
		return parent;
	}

}
