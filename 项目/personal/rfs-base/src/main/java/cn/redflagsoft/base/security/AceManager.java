/*
 * $Id: AceManager.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import java.util.Set;

public interface AceManager {
	/**
	 * 
	 * @return
	 */
	Set<Ace> getAcl();
	
	/**
	 * 
	 * @param aceId
	 * @return
	 */
	Ace getAce(String aceId);
}
