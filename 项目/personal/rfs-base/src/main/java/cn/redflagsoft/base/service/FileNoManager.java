/*
 * $Id: FileNoManager.java 5508 2012-04-11 08:02:27Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.FileNo;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface FileNoManager {

	//FileNo saveFileNoDef(FileNo def);
	
	/**
	 * 获取指定ID的文号定义。
	 * @param uid
	 * @return
	 */
	FileNo getFileNo(String uid);
	
	/**
	 * 生成指定ID的文号，但不修改文号定义的当前值。
	 * 通常在加载表单数据时调用这个方法，生成一个文号给前台显示。
	 * @param uid
	 * @return
	 */
	String generateFileNo(String uid);

	/**
	 * 保存当前ID最后一次生成的文号。
	 * 通常是提交时调用这个方法。
	 * 如果文号在自动增长的，这个方法可以将自增长量+1。
	 * @param uid
	 * @param lastFileNo
	 * @return
	 */
	FileNo updateFileNo(String uid, String lastFileNo);

	/**
	 * 重新设置文号的自增长量为指定值。
	 * 例如：如果文号按年自增长，则在每年年初调用这么方法，将值设置为0.
	 * @param uid
	 * @param value
	 * @return
	 */
	FileNo resetFileNoAutoIncrementValue(String uid, int value);
}
