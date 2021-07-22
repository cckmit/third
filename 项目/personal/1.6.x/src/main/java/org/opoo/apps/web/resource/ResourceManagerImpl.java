package org.opoo.apps.web.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsHome;
import org.opoo.apps.module.Module;
import org.opoo.apps.module.ModuleManager;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ResourceManagerImpl implements ResourceManager {
//	private final Map<String,Boolean> fileLocationMap = new ConcurrentHashMap<String,Boolean>();
//	private static final boolean ARE_PATH_FILE_SEPARATOR_EQUAL = File.separator.equals("/");
	private static final Log log = LogFactory.getLog(ResourceManagerImpl.class);
	
	private final Map<String,Resource> staticResources = new ConcurrentHashMap<String,Resource>();
	private final Map<String,Resource> moduleResources = new ConcurrentHashMap<String,Resource>();
	
	
	private static final Resource NOT_EXISTS_RESOURCE = new NotExistsResource();
	
	private ModuleManager moduleManager;
	/*
	public boolean contains(String path) {
        Boolean containsFile = fileLocationMap.get(path);
        if(Boolean.FALSE.equals(containsFile))
            return false;
        else
            return contains(new File(getResourceDirectory(), path));
	}
	
    public boolean contains(File file)
    {
        if(file.exists() && file.canRead())
        {
            try {
				fileLocationMap.put(getPath(file), Boolean.valueOf(true));
				return true;
			} catch (IOException e) {
				log.warn(String.format("Unable to build path from file %s", file));
			}
        }
        try
        {
            fileLocationMap.put(getPath(file), Boolean.FALSE);
            return false;
        }
        catch(IOException io)
        {
            log.warn(String.format("Unable to build path from file %s", file));
        }
        return false;
    }

    private String getPath(File file) throws IOException
	{
	    String path = file.getCanonicalPath();
	    path = path.substring(path.indexOf((new StringBuilder()).append(File.separator).append("resources").toString()) + 10);
	    if(!ARE_PATH_FILE_SEPARATOR_EQUAL)
	        path = StringUtils.replace(path, File.separator, "/");
	    return path;
	}

    
    public File getResourceDirectory()
    {
        return AppsHome.getResources();
    }
    
	public File get(String path) {
		return new File(getResourceDirectory(), path);
	}

	public InputStream loadModuleResource(String path) throws IOException {
		   String tokens[] = path.split("/");
	        String moduleName = tokens[0];
	        int index = 0;
	        if("".equals(moduleName))
	        {
	            index = 1;
	            moduleName = tokens[1];
	        }
	        ModuleManager moduleManager = Application.getContext().getModuleManager();
	        if(null == moduleManager)
	            throw new RuntimeException("Module manager has not been configured in this system.");
	        Module<?> module = moduleManager.getModule(moduleName);
	        if(module == null)
	            throw new FileNotFoundException((new StringBuilder()).append("Module ").append(moduleName).append(" has not been loaded, could not find path: ").append(path).toString());
	        File directory = moduleManager.getMetaData(module).getModuleDirectory();
	        StringBuilder builder = new StringBuilder("/");
	        for(int i = index + 1; i < tokens.length; i++)
	        {
	            builder.append(tokens[i]);
	            if(!tokens[i].contains("."))
	                builder.append("/");
	        }

	        File fullPath = new File(directory, builder.toString());
	        if(!fullPath.exists() || !fullPath.canRead())
	            throw new IOException((new StringBuilder()).append("Module[").append(moduleName).append("] resource cannot be read or does not exist at path: ").append(fullPath).toString());
	        else
	            return new FileInputStream(fullPath);
	}
	*/
	
	
	/**
	 * 
	 */
	public Resource getModuleResource(String path) {
		Resource resource = moduleResources.get(path);
		if(resource == null){
			String tokens[] = path.split("/");
			String moduleName = tokens[0];
			int index = 0;
			if ("".equals(moduleName)) {
				index = 1;
				moduleName = tokens[1];
			}
			
			if(tokens.length <= index + 1){
				//throw new IllegalArgumentException("Not a valid resource: " + path);
				return NOT_EXISTS_RESOURCE;
			}
			
			if (null == moduleManager){
				throw new RuntimeException("Module manager has not been configured in this system.");
			}
			
			Module<?> module = moduleManager.getModule(moduleName);
			if (module == null){
				String msg = (new StringBuilder()).append("Module ").append(moduleName).append(
						" has not been loaded, could not find path: ").append(path).toString();
				log.warn(msg);
				//throw new RuntimeException(msg);
				return NOT_EXISTS_RESOURCE;
			}
			
			File directory = moduleManager.getMetaData(module).getModuleDirectory();
			StringBuilder builder = new StringBuilder("/");
			for (int i = index + 1; i < tokens.length; i++) {
				builder.append(tokens[i]);
				// filename contains '.'
				if (!tokens[i].contains("."))
					builder.append("/");
			}
			//
			File fullPath = new File(directory, builder.toString());
			
			resource = new FileSystemResource(fullPath);
			moduleResources.put(path, resource);
			
			if(log.isDebugEnabled()){
				log.debug("Find module resource for " + path);
			}
		}
		return resource;
	}

	public Resource getPublicResource(String path) {
		Resource resource = staticResources.get(path);
		if(resource == null){
//			String fname = path;
//			if(!ARE_PATH_FILE_SEPARATOR_EQUAL){
//				fname = fname.replaceAll("/", File.separator);
//			}
			File file = new File(AppsHome.getResources(), path);
			resource = new FileSystemResource(file);
			staticResources.put(path, resource);
			
			if(log.isDebugEnabled()){
				log.debug("Find public resource for " + path);
			}
		}
		return resource;
	}
	
	


	/**
	 * @return the moduleManager
	 */
	public ModuleManager getModuleManager() {
		return moduleManager;
	}

	/**
	 * @param moduleManager the moduleManager to set
	 */
	public void setModuleManager(ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}
	
	
	protected static class NotExistsResource implements Resource{
		private String path;
		public NotExistsResource(String path){
			this.path = path;
		}
		public NotExistsResource(){
		}
		
		public Resource createRelative(String relativePath) throws IOException {
			return null;
		}

		public boolean exists() {
			return false;
		}

		public String getDescription() {
			return "Not exits";
		}

		public File getFile() throws IOException {
			return null;
		}

		public String getFilename() {
			return path;
		}

		public URI getURI() throws IOException {
			return null;
		}

		public URL getURL() throws IOException {
			return null;
		}

		public boolean isOpen() {
			return false;
		}

		public boolean isReadable() {
			return false;
		}

		public long lastModified() throws IOException {
			return 0;
		}

		public InputStream getInputStream() throws IOException {
			return null;
		}
		
		public String toString(){
			return super.toString() + ":" + path;
		}
	}
	
	
	public static void main(String[] args) throws IOException{
		File file = new File("c:\\apps\\modules", "/um/a.jsp");
		System.out.println(file);
		System.out.println(file.exists());
		
		ResourceManagerImpl rm = new ResourceManagerImpl();
		Resource r = rm.getPublicResource("/myd.txt");
		System.out.println(r.exists());
		System.out.println(r.getFilename());
		System.out.println(r.getFile());
		System.out.println(r.getDescription());
		System.out.println(r.getURL());
	}
}
