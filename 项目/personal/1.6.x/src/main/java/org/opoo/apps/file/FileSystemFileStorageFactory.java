package org.opoo.apps.file;

import java.net.URI;

import org.apache.commons.io.FilenameUtils;
/**
 * 
 * 
 * @author Alex Lin
 *
 */
public class FileSystemFileStorageFactory extends AbstractFileStorageFactory implements FileStorageFactory {
	public static final String SUPPORTED_SCHEME = "file";

	
	
	/**
	 * @param uri URI -  file:///c:/work.home/attachments
	 * @return FileStorage
	 */
	public AbstractFileStorage createAbstractFileStorage(String storeName, URI uri) throws FileStorageException {
		String path = uri.getPath();
		return new FileSystemFileStorage(storeName, path);
	}
	
	
	public boolean supports(URI uri){
		String scheme = uri.getScheme();
		return SUPPORTED_SCHEME.equalsIgnoreCase(scheme);
	}
	
	
	public static void main(String[] args) throws Exception{
		//new java.net.URL("").
		String u = "file:///c:/asdasd/root/work.home/attachments";
		URI uri = new URI(u);
		//uri.getScheme();
		String path = uri.getPath();
		System.out.println(path);
		System.out.println(FilenameUtils.getFullPathNoEndSeparator(path));
		System.out.println(FilenameUtils.getPathNoEndSeparator(path));
		//System.out.println(FileUtils.getExtensionName(filename)
	}



}
