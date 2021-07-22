package org.opoo.apps.file;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsHome;
import org.opoo.apps.file.converter.FileTypeConverterManager;
import org.opoo.apps.file.converter.FileTypeConvertibleAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class FileStorageManagerImpl implements FileStorageManager, InitializingBean, FileTypeConvertibleAware{
	private static final Log log = LogFactory.getLog(FileStorageManagerImpl.class);
	public static final String STORES_KEY = "attachments.stores";
	public static final String ACTIVE_STORE_NAME_KEY = "attachments.activeStore";
	public static final String DEFAULT_STORE_NAME = "store0";
	public static final boolean IS_WINDOWS = File.separator.equals("\\");
	
	private List<FileStorageFactory> fileStorageFactories = new ArrayList<FileStorageFactory>();
	
	private Map<String, FileStorage> fileStorages = new HashMap<String, FileStorage>();
	private String activeStoreName;
	
	//convertible
	private FileTypeConverterManager fileTypeConverterManager;
	private String protectedFormat;
	private String readableFormat;
	
	public FileStorage getActiveFileStorage(){
		//检查配置是否发生了变更。
		checkActiveStore();
		return getFileStorage(activeStoreName);
	}
	

	public FileStorage getFileStorage(String storeName) {
		FileStorage fileStorage = fileStorages.get(storeName);
		if(fileStorage == null){
			String storeValue = AppsGlobals.getSetupProperty(STORES_KEY + "." + storeName);
			Assert.hasText(storeValue, "setup配置错误，不存在：" + STORES_KEY + "." + storeName);
			log.debug("文件存储配置：" + storeName + " -> " + storeValue);
			URI uri = null;
			try {
				uri = new URI(storeValue);
			} catch (URISyntaxException e) {
				throw new FileStorageException(e);
			}
			String scheme = uri.getScheme();
			Assert.hasText(scheme, "setup配置错误，没有指定文件存储协议: " + storeValue);
			FileStorageFactory factory = findSupportedFactory(uri);
			Assert.notNull(factory, "文件存储协议找不到对应的文件存储工厂：" + scheme + ", " + uri.getPath());
			//fileStorage = factory.createFileStorage(storeName, uri);
			fileStorage = createFileStorage(factory, storeName, uri);
			fileStorages.put(storeName, fileStorage);
		}
		return fileStorage;
	}
	
	
	/**
	 * 查找支持的文件存储工厂。
	 * @param uri
	 * @return
	 */
	protected FileStorageFactory findSupportedFactory(URI uri){
		for(FileStorageFactory factory: fileStorageFactories){
			if(factory.supports(uri)){
				return factory;
			}
		}
		return null;
	}
	
	/**
	 * 创建FileStorage,如果FileStorage支持文件类型转换，则设置相应的值。
	 * 
	 * @param factory
	 * @param storeName
	 * @param uri
	 * @return
	 */
	protected FileStorage createFileStorage(FileStorageFactory factory, String storeName, URI uri){
		FileStorage fileStorage = factory.createFileStorage(storeName, uri);
		if(fileStorage instanceof FileTypeConvertibleAware){
			FileTypeConvertibleAware aware = (FileTypeConvertibleAware) fileStorage;
			aware.setFileTypeConverterManager(fileTypeConverterManager);
			aware.setProtectedFormat(protectedFormat);
			aware.setReadableFormat(readableFormat);
			log.debug(fileStorage + " is a FileTypeConvertibleAware");
		}
		return fileStorage;
	}
	

	



	public void afterPropertiesSet() throws Exception {
		Assert.notEmpty(fileStorageFactories);
		prepareActiveStore();
	}
	
	/**
	 * 
	 */
	private void prepareActiveStore(){
		activeStoreName = AppsGlobals.getSetupProperty(ACTIVE_STORE_NAME_KEY);
		
		//如果活动的store名称不存在，则创建默认的名称store0。
		if(activeStoreName == null){
			activeStoreName = DEFAULT_STORE_NAME;
			AppsGlobals.setSetupProperty(ACTIVE_STORE_NAME_KEY, activeStoreName);
		}
		
		String activeStoreValue = AppsGlobals.getSetupProperty(STORES_KEY + "." + activeStoreName);
		//如果活动的store的配置不存在，则创建默认值。
		//默认的store是本地文件系统存储的，位于workHome的attachments目录下。
		if(StringUtils.isBlank(activeStoreValue)){
			activeStoreValue = getWorkHomeURLString() + "/attachments";
			AppsGlobals.setSetupProperty(STORES_KEY + "." + activeStoreName, activeStoreValue);
		}
		
		log.debug("当前活动的文件存储器：" + activeStoreName);
	}
	
	
	/**
	 * 要求workHome只能存在于本地文件系统中。
	 * @return
	 */
	private String getWorkHomeURLString(){
		String workHome = AppsHome.getAppsHomePath();
		workHome = FilenameUtils.separatorsToUnix(workHome);
		if(workHome.endsWith("/")){
			workHome = workHome.substring(0, workHome.length() - 1);
		}
		if(workHome.charAt(0) == '/'){
			return FileSystemFileStorageFactory.SUPPORTED_SCHEME + "://" + workHome;
		}else{
			return FileSystemFileStorageFactory.SUPPORTED_SCHEME + ":///" + workHome;
		}
	}
	
	
	/**
	 * 
	 */
	private void checkActiveStore(){
		String newActiveStoreName = AppsGlobals.getSetupProperty(ACTIVE_STORE_NAME_KEY);
		//当没有准备activeStoreName或activeStoreName发生了变化时
		if(activeStoreName == null || !activeStoreName.equals(newActiveStoreName)){
			prepareActiveStore();
		}
	}
	
	


	/**
	 * @return the fileStorageFactories
	 */
	public List<FileStorageFactory> getFileStorageFactories() {
		return fileStorageFactories;
	}


	/**
	 * @param fileStorageFactories the fileStorageFactories to set
	 */
	public void setFileStorageFactories(List<FileStorageFactory> fileStorageFactories) {
		this.fileStorageFactories = fileStorageFactories;
	}
	
	/**
	 * 
	 */
	public void setFileTypeConverterManager(
			FileTypeConverterManager fileTypeConverterManager) {
		this.fileTypeConverterManager = fileTypeConverterManager;
	}


	public void setProtectedFormat(String protectedFormat) {
		this.protectedFormat = protectedFormat;
	}


	public void setReadableFormat(String readableFormat) {
		this.readableFormat = readableFormat;
	}
	
	
	
	public static void main(String[] args) throws MalformedURLException, URISyntaxException{
		
		URI url = new URI("ftp://user:passwd@localhost/abcd/asdas.l");
		url = new URI("db:localhost:d:/sdas/abcd/attachments");
		URL uurl = new URL("file:///D:/localhost:datasource@localhost:jdbc:sss");
		System.out.println(url.getScheme() + "," + url.getHost() + "," + url.getRawPath() + "," + url.getPath()
				 + "," + url.getUserInfo() + "," + url.getRawUserInfo()	+","+ url.getSchemeSpecificPart()
		
		);
		System.out.println(uurl.getProtocol() + "," + url.getHost() + "," + url.getRawPath() + "," + url.getPath()
				 + "," + url.getUserInfo() + "," + url.getRawUserInfo()	+","+ url.getSchemeSpecificPart()
		
		);
		//url.getQuery();
		
		String abcd = "/downloads/asdasd/filename";
		String s = FilenameUtils.getBaseName(abcd);
		System.out.println(s);
		s = FilenameUtils.getExtension(abcd);
		System.out.println(s);
		s = FilenameUtils.getFullPath(abcd);
		System.out.println(s);
		s = FilenameUtils.getFullPathNoEndSeparator(abcd);
		System.out.println(s);
		s = FilenameUtils.getName(abcd);
		System.out.println(s);
		s = FilenameUtils.getPath(abcd);
		System.out.println(s);
		s = FilenameUtils.getPathNoEndSeparator(abcd);
		System.out.println(s);
		s = FilenameUtils.getPrefix(abcd);
		System.out.println(s);
		System.out.println( FilenameUtils.getPrefixLength(abcd));
		
		s = FilenameUtils.removeExtension(abcd);
		System.out.println(s);
		
	}
	
}
