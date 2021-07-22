package org.opoo.util;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

public class ClassPathUtilsTest {

	@Test
	public void testCopyJatPath() throws Exception {
		String path = "avatars";
		//fail("Not yet implemented");
		URL url = getClass().getClassLoader().getResource(path);
		System.out.println(url);
		if(ResourceUtils.isJarURL(url)){
			System.out.println("is jar url: " + url);
			URL jarFileURL = ResourceUtils.extractJarFileURL(url);
			System.out.println(jarFileURL);
			ClassPathUtils.copyJatPath(jarFileURL, path, new File("e:/aaaaa"), true);
			//URL url2 = new URL(url, "abcd/asdsa/ss.jpg");
			//System.out.println(url2.openStream());
		}
		
	}

	
	
	
	public void testAA() throws Exception{
    	//ClassPathUtils.copyPath(ClassPathUtils.class.getClassLoader(), "license", 
    	//		new File("c:\\ddd"));
    	//ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:spring-boot.xml");
    	//System.out.println(ac);
    	
    	
    	
    	
//	    	
//	    	PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//	    	Resource[] res = resolver.getResources("classpath*:resources/**/*.*");
//	    	for (Resource resource : res) {
//				
//				boolean b = ResourceUtils.isJarURL(resource.getURL());
//				System.out.println(resource.getURL() + " " + b);
//				if(b){
//					String s = resource.getURL().toString();
//					s = s.substring(s.indexOf("!/") + 2);
//					//System.out.println(s + " --> " + resource.isReadable());
//					
//					//System.out.println(new File(resource.getURL().getFile()));
//					//FileUtils.copyURLToFile(resource.getURL(), new File("D:/abcc", s));
//				}
//				//FileUtils.copyURLToFile(resource.getURL(), destination)
//			}
//	    	if(true)return;
    
    	Enumeration<URL> resources = ClassPathUtils.class.getClassLoader().getResources("resources");
    	while (resources.hasMoreElements()) {
			URL url = resources.nextElement();
			boolean isJarURL = ResourceUtils.isJarURL(url);
			System.out.println(url + " isJarURL: " + isJarURL);
			
			
			URLConnection con = url.openConnection();
			if (con instanceof JarURLConnection) {
				// Should usually be the case for traditional JAR files.
				JarURLConnection jarCon = (JarURLConnection) con;
				jarCon.setUseCaches(false);
				JarFile jarFile = jarCon.getJarFile();
				String jarFileUrl = jarCon.getJarFileURL().toExternalForm();
				JarEntry jarEntry = jarCon.getJarEntry();
				String rootEntryPath = (jarEntry != null ? jarEntry.getName() : "");
				
				
				
				System.out.println(jarFile);
				System.out.println(jarFileUrl);
				System.out.println(rootEntryPath);
				
				UrlResource res = new UrlResource(url);
				System.out.println("根资源：" + res.getURL());
				Resource r2 = res.createRelative("/themes/custom/theme.xml");
				System.out.println("相对资源："  + r2.getURL() + " ");
			}
			
			//if(true) continue;
			
			
			if(isJarURL){
				
				
				
				System.out.println(url + " isJarURL: " + isJarURL);
				URL nn = new URL(url, "resourcess/themes/custom/theme.xml");
				System.out.println(" " + nn);
				
				
				URL jarFile = ResourceUtils.extractJarFileURL(url);
				nn = new URL(jarFile, "resources/themes/custom/theme.xml");
				System.out.println(nn);
				
				nn = new URL(new URL(jarFile + "!/"), "resources/themes/custom/theme.xml");
				System.out.println(nn);
				
				File f = new File(jarFile.toURI());
				System.out.println("Jar File: " + jarFile);
				JarFile file = new JarFile(f);
				
				
				String target = "E:/aaa";
				Enumeration<JarEntry> entries = file.entries();
				while (entries.hasMoreElements()) {
					JarEntry jarEntry = (JarEntry) entries.nextElement();
					if(jarEntry.getName().startsWith("resources/")){
						String relativePath = jarEntry.getName().substring("resources/".length());
						if(relativePath != null && relativePath.length() > 0){
							File tmp = new File(target, relativePath);
							if(jarEntry.isDirectory()){
								tmp.mkdirs();
								System.out.println("创建目录: " + tmp);
							}else{
								File parent = tmp.getParentFile();
								if(!parent.exists()){
									parent.mkdirs();
								}
								InputStream is = file.getInputStream(jarEntry);
								FileCopyUtils.copy(is, new FileOutputStream(tmp));
								System.out.println("复制文件: " + tmp);
							}
						}
					}
					
					if(!jarEntry.isDirectory() && jarEntry.getName().startsWith("resources/")){
						//URL u = new URL(url, jarEntry.getName());
						//System.out.println("Copy '" + u + "' to 'D:/abcc/" + jarEntry.getName()  + "'");
						
						
						//URLConnection connection = u.openConnection();
						//FileUtils.copyURLToFile(u, new File("D:/abcc/" + jarEntry.getName()));
						//System.out.println(connection);
						//u.openStream();
						
						
						
//							InputStream is = file.getInputStream(jarEntry);
//							FileCopyUtils.copy(is, new FileOutputStream("D:/abccc/" + jarEntry.getName()));
						
					}
				}
				file.close();
			}
		}
	}
}
