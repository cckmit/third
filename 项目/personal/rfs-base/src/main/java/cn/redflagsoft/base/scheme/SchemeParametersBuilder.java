/*
 * $Id: SchemeParametersBuilder.java 5209 2011-12-14 03:15:57Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.scheme;

import java.util.Map;

import cn.redflagsoft.base.bean.MatterAffair;
import cn.redflagsoft.base.bean.RFSObject;

/**
 * WorkScheme的参数构建器。
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public interface SchemeParametersBuilder {

	/**
	 * 构建调用WorkScheme的参数集合，通常在MatterAffair自动驱动调用WorkScheme时，
	 * 先调用这个方法来构建要调用的WorkScheme的参数，然后再调用WorkScheme。
	 * 
	 * @param ma 要调用WorkScheme的MatterAffair配置
	 * @param refWorkSchme 该MatterAffair处理涉及的原来的WorkScheme
	 * @param refObject 该MatterAffair处理涉及的原来的主对象
	 * @param refAffairObject 该MatterAffair处理涉及的原来的AffairObject
	 * @return 构建好的map集合
	 */
	Map<String,Object> buildParameters(MatterAffair ma, WorkScheme refWorkSchme, RFSObject refObject, RFSObject refAffairObject);
}
