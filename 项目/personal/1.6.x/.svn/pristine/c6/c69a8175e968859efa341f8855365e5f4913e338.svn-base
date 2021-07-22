package org.opoo.util;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ResourceUtils;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ClassPathUtils
{
	
	private static final Log log = LogFactory.getLog(ClassPathUtils.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();

    private ClassPathUtils()
    {
    }

    /**
     * 
     * @param loader
     * @param classPathSource
     * @param destination
     * @throws Exception
     */
    public static void copyPath(ClassLoader loader, String classPathSource, File destination)  throws Exception
    {
        copyPath(loader, classPathSource, destination, true);
    }

    /**
     * 
     * @param loader
     * @param classPathSource
     * @param destination
     * @param overwrite
     * @throws Exception
     */
    public static void copyPath(ClassLoader loader, String classPathSource, File destination, boolean overwrite) throws Exception
    {
    	if(classPathSource == null || destination == null){
    		return;
    	}
    	
    	URL url = loader.getResource(classPathSource);
    	if(IS_DEBUG_ENABLED){
			log.debug("Copy path from '" + url + "' to '" + destination + "'");
		}
    	
    	boolean b = ResourceUtils.isJarURL(url);
    	if(b){
    		URL jarFileURL = ResourceUtils.extractJarFileURL(url);
    		copyJatPath(jarFileURL, classPathSource, destination, overwrite);
    	}else{
    		copyFilePath(url, destination, overwrite);
    	}
    }
    
    /**
     * 
     * @param jarFileURL
     * @param sourcePath
     * @param destination
     * @param overwrite
     * @throws Exception
     */
    protected static void copyJatPath(URL jarFileURL, String sourcePath, File destination, boolean overwrite)throws Exception{
    	//URL jarFileURL = ResourceUtils.extractJarFileURL(url);
    	if(!sourcePath.endsWith("/")){
    		sourcePath += "/";
    	}
    	String root = jarFileURL.toString() + "!/";
    	if(!root.startsWith("jar:")){
    		root = "jar:" + root;
    	}
    	//System.out.println("RRRRRRRRRRRRR" + root);
    	
    	JarFile jarFile = new JarFile(new File(jarFileURL.toURI()));
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry jarEntry = entries.nextElement();
			String name = jarEntry.getName();
			//System.out.println(name + "." + sourcePath + "." + name.startsWith(sourcePath));
			if(name.startsWith(sourcePath)){
				String relativePath = name.substring(sourcePath.length());
//				System.out.println("rp: " + relativePath);
				if(relativePath != null && relativePath.length() > 0){
					File tmp = new File(destination, relativePath);
					//不存在或者允许覆盖
					if(overwrite || !tmp.exists()){
						if(jarEntry.isDirectory()){
							tmp.mkdirs();
							//System.out.println("创建目录: " + tmp);
							if(IS_DEBUG_ENABLED){
								log.debug("创建目录：" + tmp);
							}
						}else{
							File parent = tmp.getParentFile();
							if(!parent.exists()){
								parent.mkdirs();
							}
							//1.FileCopyUtils.copy
							//InputStream is = jarFile.getInputStream(jarEntry);
							//FileCopyUtils.copy(is, new FileOutputStream(tmp));
							
							//2. url copy
							URL u = new URL(root + name);
							//System.out.println(u);
							FileUtils.copyURLToFile(u, tmp);
							if(IS_DEBUG_ENABLED){
								log.debug("复制文件 '" + u + "' 到 '" + tmp + "'。");
							}
						}
					}
				}
			}
		}
		
		try{
			jarFile.close();
		}catch(Exception ie){
		}
    }
    
    /**
     * 
     * @param sourceDirectoryURL
     * @param destination
     * @param overwrite
     * @throws Exception
     */
    protected static void copyFilePath(URL sourceDirectoryURL, File destination, boolean overwrite)
			throws Exception {
		URI classPathNode = sourceDirectoryURL.toURI();
		File f = new File(classPathNode);
		if (!destination.exists()){
			destination.mkdirs();
		}
		
		File nodes[] = f.listFiles();
		if (nodes == null || nodes.length == 0){
			return;
		}
		for (File node : nodes) {
			if (node.isDirectory()) {
				FileUtils.copyDirectory(node, new File(destination, node.getName()));
			} else {
				FileUtils.copyFile(node, new File(destination, node.getName()));
			}
		}
	}
    
    
//    public static void copyPath2(ClassLoader loader, String classPathSource, File destination, boolean overwrite)
//        throws Exception
//    {
//        if(classPathSource == null || destination == null)
//            return;
//        //URL url = loader.getResource(classPathSource);
//        URI classPathNode = new URI(loader.getResource(classPathSource).toString());
//        File f = new File(classPathNode);
//        if(!destination.exists())
//            destination.mkdirs();
//        File nodes[] = f.listFiles();
//        if(nodes == null || nodes.length == 0)
//            return;
//        File arr$[] = nodes;
//        int len$ = arr$.length;
//        for(int i$ = 0; i$ < len$; i$++)
//        {
//            File node = arr$[i$];
//            if(node.isDirectory())
//                FileUtils.copyDirectory(node, new File(destination, node.getName()));
//            else
//                FileUtils.copyFile(node, new File(destination, node.getName()));
//        }
//    }
    
    
}