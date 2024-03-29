/*
 * $Id: ArchivingStatus.java 6369 2014-04-14 10:43:28Z thh $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Restrictions;

/**
 * 对象归档状态。
 * 见《业务设计（项目，项目状态，1-0-0）》。
 * <pre>
------------
归档状态是一个模拟档案管理的概念，表现个体的业务对象所处的档案状态，以便可以有效地控制用户对业务对象的权限。
注意：管理状态是与业务无关的。

状态
代码
含义和说明
---------
公开
0～99
已公开的档案。默认为0。一般查询为小于100。


归档
100
已经归档，无特别权限，不能访问。未公开的对象，不参与一般的统计。

私有
个人私档
110
个人档发布以后，变为公开档。

私有
小组私档
112
只能小组内部访问。

私有
部门私档
114
只能部门内部访问。

私有
单位私档
116
只能单位内部访问。

保密
118
只能特定单位内部访问。

其他
120～126
备用。

作废
127
作废的业务对象，作逻辑删除，不可参与任何查询。
 */
public enum ArchivingStatus {
	PUBLIC			((byte)0, "公开", "0-99，已公开的档案。默认为0。一般查询为小于100。"),
	ARCHIVED		((byte)100, "归档", "已经归档，无特别权限，不能访问。未公开的对象，不参与一般的统计。"),
	FOR_OWNER		((byte)110, "个人私档", "个人档发布以后，变为公开档。"),
	FOR_GROUP		((byte)112, "小组私档", "只能小组内部访问。"),
	FOR_DEPARTMENT	((byte)114, "部门私档", "只能部门内部访问。"),
	FOR_ORG			((byte)116, "单位私档", "只能单位内部访问。"),
	PROTECTED		((byte)118, "保密档", "只能特定单位内部访问。"),
	OTHER			((byte)120, "其它", "120-126，备用"),
	DELETED			((byte)127, "作废档", "作废的业务对象，作逻辑删除，不可参与任何查询。")
	;
	
	/**
	 * 0
	 */
	public static final byte STATUS_公开档 = ArchivingStatus.PUBLIC.getByteStatus();
	/**
	 * 100
	 */
	public static final byte STATUS_归档 = ArchivingStatus.ARCHIVED.getByteStatus();
	/**
	 * 110
	 */
	public static final byte STATUS_个人档 = ArchivingStatus.FOR_OWNER.getByteStatus();
	/**
	 * 112
	 */
	public static final byte STATUS_小组档 = ArchivingStatus.FOR_GROUP.getByteStatus();
	/**
	 * 114
	 */
	public static final byte STATUS_部门档 = ArchivingStatus.FOR_DEPARTMENT.getByteStatus();
	/**
	 * 116
	 */
	public static final byte STATUS_单位档 = ArchivingStatus.FOR_ORG.getByteStatus();
	/**
	 * 118
	 */
	public static final byte STATUS_保护档 = ArchivingStatus.PROTECTED.getByteStatus();
	/**
	 * 127
	 */
	public static final byte STATUS_作废档 = ArchivingStatus.DELETED.getByteStatus();
	
	
	
	/**
	 * 业务对象归档状态有效性查询条件。 statis<100
	 */
	public static final Criterion VALID_STATUS_CRITERION = Restrictions.lt("status", STATUS_归档);
	
	/**
	 * 业务对象有效性查询条件。 statis=116
	 */
	public static final Criterion VALID_STATUS_FOR_ORG = Restrictions.lt("status", STATUS_单位档);
	
	//class definition
	private final byte status;
	private final String description;
	private final String statusName;
	private ArchivingStatus(byte status, String statusName, String description){
		this.status = status;
		this.description = description;
		this.statusName = statusName;
	}
	private ArchivingStatus(byte status, String statusName){
		this(status, statusName, null);
	}
	/**
	 * @return the status
	 */
	public byte getByteStatus() {
		return status;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}
}
