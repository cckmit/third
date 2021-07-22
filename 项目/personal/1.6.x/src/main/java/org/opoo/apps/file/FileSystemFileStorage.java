package org.opoo.apps.file;

import static org.apache.commons.io.FilenameUtils.separatorsToSystem;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.file.converter.FileTypeConverter;
import org.springframework.util.FileCopyUtils;


public class FileSystemFileStorage extends AbstractFileStorage implements FileStorage {
	private static final Log log = LogFactory.getLog(FileSystemFileStorage.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	private static final SimpleDateFormat LOCATION_FORMAT = new SimpleDateFormat("/yyyy/MM/dd/");
	
	private String path;
	
	/**
	 * 
	 * @param storeName
	 * @param path 必须以非文件夹分隔符结束。
	 */
	public FileSystemFileStorage(String storeName, String path){
		super(storeName);
		this.path = path;
		log.info("创建：" + storeName + " -> " + path + ", 路径是否存在? " + new File(path).exists());
	}
	/**
	 * 根据location创建文件的觉得路径，不包含文件后缀。
	 * @param location
	 * @return
	 */
	protected String buildBaseName(String location){
		return separatorsToSystem(path + location);
	}
	
	/**
	 * 
	 * @param details
	 * @return
	 */
	protected File buildFile(FileDetails details){
		return buildFile(details, null);
	}
	
	/**
	 * 
	 * @param details
	 * @param format
	 * @return
	 */
	protected File buildFile(Details details, String format){
		if(format == null){
			format = details.getOriginalFormat();
		}
		String baseName = buildBaseName(details.getLocation());
		return new File(baseName + "." + format);
	}


	@Override
	protected FileDetails save(InputStream is, FileDetails details, Long id) throws FileStorageException {
		String location = LOCATION_FORMAT.format(new Date()) + id;
		details.setLocation(location);
		
		File file = buildFile(details);
		try {
			File parent = file.getParentFile();
			if(!parent.exists()){
				parent.mkdirs();
				//if(!parent.mkdirs()){
				//	throw new FileStorageException("创建目录出错：" + parent.getPath());
				//}
			}			
			
			FileCopyUtils.copy(is, new BufferedOutputStream(new FileOutputStream(file)));
			
			long fileSize = file.length();
			details.setFileSize(fileSize);
			
			if(IS_DEBUG_ENABLED){
				log.debug("保存文件: " + file.getAbsolutePath() + ", 大小：" + fileSize);
			}
			///这里进行格式转换等等操作
			//转化完成要更新相应的属性

			//如果存储时由调用者指定了格式，并且格式是 NONE，则说明不转换
			if(details.getProtectedFormat() != null && Details.PROTECTED_FORMAT_NONE.equals(details.getProtectedFormat())){
				details.setProtectedFormat(null);
				if(log.isInfoEnabled()){
					log.info("指定受保护格式为 none，不转换文件的格式：" + buildFile(details, null));
				}
			}
			else {
				//如果存储时由调用者指定了格式，并且格式不是 NONE，则使用指定的格式，否则选取配置的格式
				String pf = details.getProtectedFormat();
				if(pf == null){
					pf = getProtectedFormat();
					log.debug("受保护的文件格式使用系统配置的参数：" + pf);
				}else{
					log.debug("受保护的文件格式使用特别指定的格式：" + pf);
				}

//				String pf = (details.getProtectedFormat() != null) ? details.getProtectedFormat() : getProtectedFormat();
				if (pf != null) {
					log.debug("未指定受保护格式或者指定的受保护格式不是 none，");

					//如果格式一致
					if (details.getOriginalFormat().equalsIgnoreCase(pf)) {
						details.setProtectedFormat(pf);
					} else {
						if (getFileTypeConverterManager() != null) {
							File output = buildFile(details, pf);
							FileTypeConverter converter = getFileTypeConverterManager().findFileTypeConverter(details.getOriginalFormat(), pf);
							if (converter != null) {
								converter.convert(file, details.getOriginalFormat(), output, pf);
								details.setProtectedFormat(pf);
							} else {
								log.warn("找不到适当的文件格式转换器：" + details.getOriginalFormat() + " - >" + pf);
							}

//						boolean result = getFileTypeConverterManager().convert(file, details.getOriginalFormat(), output, pf);
//						if(result){
//							details.setProtectedFormat(pf);
//						}else{
//							log.error("文件格式转换失败: " + details.getOriginalFormat() + " -> " 
//									+ details.getProtectedFormat());
//							//throw new FileStorageException("文件格式转换失败");
//						}
						}
					}
				}else{
					log.debug("系统未配置受保护文件格式，跳过文件转换。");
				}
			}

			//如果存储时由调用者指定了格式，并且格式是 NONE，则说明不转换
			if(details.getReadableFormat() != null && Details.READABLE_FORMAT_NONE.equals(details.getReadableFormat())){
				details.setReadableFormat(null);
			}
			else {
				//如果存储时由调用者指定了格式，并且格式不是 NONE，则使用指定的格式，否则选取配置的格式
				//String rf = (details.getReadableFormat() != null) ?  details.getReadableFormat() : getReadableFormat();

				String rf = details.getReadableFormat();
				if(rf == null){
					rf = getReadableFormat();
					log.debug("只读文件格式使用系统配置的参数：" + rf);
				}else{
					log.debug("只读文件格式使用特别指定的格式：" + rf);
				}

				if (rf != null) {
					if (rf.equalsIgnoreCase(details.getOriginalFormat())) {
						details.setReadableFormat(rf);
					} else if (rf.equalsIgnoreCase(details.getProtectedFormat())) {
						details.setReadableFormat(rf);
					} else {
						if (getFileTypeConverterManager() != null) {
							File output = buildFile(details, rf);
							File input = file;
							String inputFormat = details.getOriginalFormat();
							FileTypeConverter converter = getFileTypeConverterManager().findFileTypeConverter(details.getOriginalFormat(), rf);
							if (converter == null && details.getProtectedFormat() != null) {
								converter = getFileTypeConverterManager().findFileTypeConverter(details.getProtectedFormat(), rf);
								input = buildFile(details, details.getProtectedFormat());
								inputFormat = details.getProtectedFormat();
							}

							if (converter != null) {
								converter.convert(input, inputFormat, output, rf);
								details.setReadableFormat(rf);
							} else {
								log.warn("找不到适当的文件格式转换器：" + details.getOriginalFormat()
										+ ", " + details.getProtectedFormat() + " - >" + rf);
							}

//						boolean result = getFileTypeConverterManager().convert(file, details.getOriginalFormat(), output, rf);
//						if(!result && details.getProtectedFormat() != null){
//							File input = buildFile(details, details.getProtectedFormat());
//							result = getFileTypeConverterManager().convert(input, details.getProtectedFormat(), output, rf);
//						}
//						if(result){
//							details.setReadableFormat(rf);
//						}else{
//							log.error("文件格式转换失败: " + details.getOriginalFormat() + " or " 
//									+ details.getProtectedFormat() + " -> " + details.getReadableFormat());
//							//throw new FileStorageException("文件格式转换失败");
//						}
						}
					}
				}else{
					log.debug("系统未配置只读文件格式，跳过文件转换。");
				}
			}
			
			
//			if(log.isWarnEnabled() && getFileTypeConverterManager() == null){
//				log.warn("没有配置文件类型转换管理器，不对文件类型进行转换。");
//			}	
			
			return details;
		} catch (Exception e) {
			throw new FileStorageException(e);
		}
		

	}
	
	


	/**
	 * 删除相关的所有文件。
	 * 文件可能被存储了三种格式，需要都删除。
	 */
	public void delete(Details details) {
		if(details == null){
			log.warn("被删除附件为空，不执行任何操作");
			return ;
		}
		String baseName = buildBaseName(details.getLocation());
		File file = new File(baseName + "." + details.getOriginalFormat());
		if(file.exists()){
			boolean b = file.delete();
			if(!b){
				throw new FileStorageException("删除文件失败：" + file);
			}
			if(IS_DEBUG_ENABLED){
				log.debug("删除文件：" + file.getAbsolutePath());
			}
		}
		
		if(StringUtils.isNotBlank(details.getProtectedFormat())){
			file = new File(baseName + "." + details.getProtectedFormat());
			if(file.exists()){
				file.delete();
				if(IS_DEBUG_ENABLED){
					log.debug("删除受保护文件：" + file.getAbsolutePath());
				}
			}
		}
		
		if(StringUtils.isNotBlank(details.getReadableFormat())){
			file = new File(baseName + "." + details.getReadableFormat());
			if(file.exists()){
				file.delete();
				if(IS_DEBUG_ENABLED){
					log.debug("删除阅读格式的文件：" + file.getAbsolutePath());
				}
			}
		}
	}



	/**
	 * 
	 */
	public InputStream fetch(Details details, String format) throws FileStorageException {
		File file = buildFile(details, format);
		try {
			if(IS_DEBUG_ENABLED){
				log.debug("访问文件：" + file.getAbsolutePath());
			}
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new FileStorageException(e);
		}
	}
}
