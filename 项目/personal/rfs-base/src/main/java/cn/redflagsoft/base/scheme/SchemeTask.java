/*
 * $Id: SchemeTask.java 4471 2011-07-07 10:02:03Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface SchemeTask extends Runnable {
	int DEFAULT = 0;
	int MATTERS_HANDLER_ACCEPT = 11;
	int MATTERS_HANDLER_READY = 12;
	int MATTERS_HANDLER_AVOID = 13;
	int MATTERS_HANDLER_STOP = 14;
	int MATTERS_HANDLER_CANCEL = 15;
	int MATTERS_HANDLER_FINISH = 16;
	int MATTERS_HANDLER_FINISH_NO_TAG = 17;
	
	/**
	 * 类型。
	 * @return
	 */
	int getType();
	
	boolean hasRun();
	
	//int getRunCount();
}
