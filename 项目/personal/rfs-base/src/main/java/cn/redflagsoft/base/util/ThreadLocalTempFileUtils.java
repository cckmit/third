/*
 * $Id: ThreadLocalTempFileUtils.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ThreadLocalTempFileUtils {
	private static final Log log = LogFactory.getLog(ThreadLocalTempFileUtils.class);
	
	private static ThreadLocal<List<File>> tempFiles = new ThreadLocal<List<File>>();
	
	/**
	 * 向当前线程的临时文件中添加一个临时文件。
	 * 
	 * @param tempFile
	 * @return
	 */
	public static List<File> addTempFile(File tempFile){
		List<File> list = tempFiles.get();
		if(list == null){
			list = new ArrayList<File>();
		}
		if(!list.contains(tempFile)){
			list.add(tempFile);
		}
		tempFiles.set(list);
		return list;
	}
	
	/**
	 * 删除当前线程的所有临时文件。
	 */
	public static void deleteTempFiles(){
		List<File> list = tempFiles.get();
		if(list != null){
			if(log.isDebugEnabled()){
				log.debug("当前线程共有临时文件：" + list.size());
			}
			for(File file: list){
				deleteTempFile(file);
			}
			list.clear();
		}
	}
	
	private static void deleteTempFile(File file){
		if(file != null && file.isFile()){
			try{
				file.delete();
				log.debug("删除临时文件：" + file.getAbsolutePath());
			}catch(Exception e){
				if(log.isDebugEnabled()){
					log.debug("删除临时文件出错：", e);
				}
			}
		}
		file = null;
	}
}
