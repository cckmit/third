package org.opoo.apps.module;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;
import java.util.zip.ZipFile;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.opoo.apps.AppsConstants;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;


/**
 * 模块操作工具类。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ModuleUtils {

	private static final Log log = LogFactory.getLog(ModuleUtils.class);
	public static final String MODULE_DIR_SYS_PROP = "moduleDirs";

	private ModuleUtils() {
	}

	public static File outputJarFile(String moduleName, InputStream in, File basedir) throws IOException {
		File file = null;
		OutputStream out = null;
		try {
			file = new File(basedir, moduleName + ".jar");
			file.createNewFile();
			out = new BufferedOutputStream(new FileOutputStream(file));
			byte buffer[] = new byte[32768];
			int len;
			while ((len = in.read(buffer)) != -1){
				out.write(buffer, 0, len);
			}
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return file;
	}

	public static File extractModule(File jarFile, File basedir) throws IOException {
		String moduleName = jarFile.getName().substring(0, jarFile.getName().length() - 4).toLowerCase();
		if(moduleName.indexOf('.') > 0){
			moduleName = moduleName.replace('.', '_');
		}
		
		File dir = new File(basedir, moduleName);
		if (!dir.exists()) {
//			System.out.println(dir);
			unzipModule(moduleName, jarFile, dir);
			try {
				Document document = getModuleConfiguration(dir);
				if (document != null) {
					Node node = document.selectSingleNode("/module/name");
					if (node != null) {
						String moduleName2 = node.getText();
						//System.out.println(moduleName2);
						if (!moduleName2.equals(moduleName)) {
							moduleName = moduleName2;
							File todir = new File(basedir, moduleName2);
							if (!dir.equals(todir)) {
								if (todir.exists())
									FileUtils.deleteDirectory(todir);
								FileUtils.copyDirectory(dir, todir);
								FileUtils.deleteDirectory(dir);
								
								log.info(String.format("Move module '%s' to '%s'.", dir.getName(), todir.getName()));
								
								dir = todir;
							}
						}
					}else{
						log.warn("Using directory name as module name.");
					}
				}
			} catch (DocumentException e) {
				log.error(e);
			}
		}
		return dir;
	}

	public static Document getModuleConfiguration(File moduleDir) throws DocumentException {
		File moduleConfig = new File(moduleDir, AppsConstants.MODULE_INF + "/module.xml");
		if (moduleConfig.exists()) {
			SAXReader saxReader = new SAXReader();
			saxReader.setEncoding("GBK");
			//TODO 将来应对带namespace的xml进行解析
//			Map<String,String> map = new HashMap<String,String>();
//	        map.put("apps","http://www.opoo.org/schema/module");
//			saxReader.getDocumentFactory().setXPathNamespaceURIs(map);
			return saxReader.read(moduleConfig);
		} else {
			return null;
		}
	}

	public static void unzipModule(String moduleName, File jarFile, File dir) {
		ZipFile zipFile;
		try {
			zipFile = new JarFile(jarFile);
			if (zipFile.getEntry(AppsConstants.MODULE_INF + "/module.xml") == null) {
				return;
			}

			dir.mkdir();
			dir.setLastModified(jarFile.lastModified());

			log.debug((new StringBuilder()).append("Extracting module: ").append(moduleName).toString());

			Enumeration<?> e = zipFile.entries();
			do {
				if (!e.hasMoreElements()) {
					break;
				}

				JarEntry entry = (JarEntry) e.nextElement();
				File entryFile = new File(dir, entry.getName());
				String lowerCaseName = entry.getName().toLowerCase();
				if (!lowerCaseName.endsWith("manifest.mf") && !entry.isDirectory()
						&& !lowerCaseName.contains("meta-inf/")) {
					entryFile.getParentFile().mkdirs();
					FileOutputStream out = new FileOutputStream(entryFile);
					InputStream zin = zipFile.getInputStream(entry);
					byte b[] = new byte[512];
					int len;
					while ((len = zin.read(b)) != -1)
						out.write(b, 0, len);
					out.flush();
					out.close();
					zin.close();
				}
			} while (true);
			zipFile.close();
			unpackArchives(new File(dir, "lib"));
		} catch (Exception e) {
			log.error(e);
		}
	}

	private static void unpackArchives(File libDir) {
		File packedFiles[] = libDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".pack");
			}
		});
		
		if (packedFiles == null){
			return;
		}
		
		for(File packedFile: packedFiles){
			try {
				String jarName = packedFile.getName().substring(0, packedFile.getName().length() - ".pack".length());
				File jarFile = new File(libDir, jarName);
				if (jarFile.exists())
					jarFile.delete();
				InputStream in = new BufferedInputStream(new FileInputStream(packedFile));
				JarOutputStream out = new JarOutputStream(new BufferedOutputStream(new FileOutputStream(new File(
						libDir, jarName))));
				java.util.jar.Pack200.Unpacker unpacker = Pack200.newUnpacker();
				unpacker.unpack(in, out);
				in.close();
				out.close();
				packedFile.delete();
			} catch (Exception e) {
				log.error(e);
			}
		}

	}

	public static boolean resultsContainsClass(List<?> results, final Class<?> class1) {
		return CollectionUtils.exists(results, new Predicate() {
			public boolean evaluate(Object arg0) {
				return arg0 != null && arg0.getClass().isAssignableFrom(class1);
			}
		});
	}

	public static List<String> getDevModulePaths() {
		List<String> paths = new ArrayList<String>();
		String moduleDirs = System.getProperty(MODULE_DIR_SYS_PROP);
		//if (Boolean.parseBoolean(System.getProperty("apps.devMode", "false")) && moduleDirs != null) {
		if(AppsGlobals.isDevMode() && moduleDirs != null){
			String split[] = moduleDirs.split(",");
			for(String string: split){
				string = string.trim();
				if (!"".equals(string))
					paths.add(string);
			}

		}
		return paths;
	}

//	public static class ModuleDocument implements Document{
//		
//	}
	
	public static void main(String[] args) throws IOException, DocumentException{
		AppsHome.initialize();
//		Document doc = ModuleUtils.getModuleConfiguration(new File("C:\\apps\\modules\\apps-modules-1.0-snapshot"));
//		System.out.println(doc.selectSingleNode("/module"));
//		Node node = doc.selectSingleNode("/module");
//		node = node.selectSingleNode("/module/name");
//		System.out.println(node);
//		//org.dom4j.io.
//		
//		Document d = (Document) doc.clone();
//		System.out.println(">>" + d);
//		Namespace n = new Namespace("apps", "http://www.opoo.org/schema/module");
//		d.getRootElement().remove(n);
//		System.out.println(d.selectSingleNode("/module/name"));
//		
//		System.out.println(doc.selectSingleNode("/apps:module/apps:name"));
//		
//		Element root = doc.getRootElement();
//		Namespace ns = root.getNamespace();
//		if(ns != null){
//			System.out.println(ns.getURI() + " : [" + ns.getPrefix() + "]");
//			System.out.println(root.remove(ns));
//			
//			
//			System.out.println(root.selectSingleNode("/apps:name"));
//			StringWriter sw = new StringWriter();
//			XMLWriter w = new XMLWriter(sw);
//			w.write(root);
//			System.out.println(sw.toString());
//		}
//		System.out.println(root.getPath());
		
		
		//File f = ModuleUtils.extractModule(new File("C:\\apps\\modules\\apps-modules-1.0-snapshot.jar"), new File("C:\\apps\\modules"));
		//System.out.println(f);
		//Document xml = ModuleUtils.getModuleConfiguration(new File("C:\\apps\\modules\\um"));
		//System.out.println(xml);
//		String moduleName = "opoo-apps-module-sample-1.0-snapshot";
//		if(moduleName.indexOf('.') > 0){
//			moduleName = moduleName.replace('.', '_');
//		}
//		System.out.println(moduleName);
		
		File file = new File("E:\\java-works\\data-exchange\\target\\data-exchange.jar");
		ModuleUtils.unzipModule("data-exchange", file, new File("c:\\apps\\aaaa"));
	}
}
