/*
 * $Id: SelectDataSource.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * 选择数据源配置。
 * 
 * <p>
 * 该配置根据{@link #cat}的值来进行分类，各个分类描述如下：
 * <ol>
 * <li>{@link #CAT_单位分组} (1): 查询单位分组中的单位，单位分组的ID由<code>source</code>指定。
 * <li>{@link #CAT_人员分组} (2): 查询人员分组中的人员，人员分组的ID由<code>source</code>指定。
 * <li>{@link #CAT_术语表} (3): 查询{@link Glossary}中的值，Glossary的<code>catagory</code>
 * 		由<code>source</code>指定。<b>除了能解析Glossary外，还能应用于静态字段组成的CodeMap定义。</b>
 * <li>{@link #CAT_枚举} (4): 解析<code>source</code>字符串形成集合，通常source的格式为
 * 		“<code><b>1:有效;2:无效;3:不知道</b></code>”。
 * <li>{@link #CAT_单位分组中的人员} (5): 查询单位分组中的单位，单位分组的ID由<code>source</code>指定。
 * <li>{@link #CAT_指定单位的人员} (6): 查询指定单位的人员，source大于0时，source的值就是指定的单位ID，
 * 		source为0时，取当前登录用户的单位ID为制定单位的ID，source小于0时，取查询参数orgId为指定单位
 * 		的ID。
 * <li>{@link #CAT_指定单位指定人员分组中的人员} (7): 查询指定的单位和指定人员分组中的人员。其中数据源配
 * 		置中source的值由两部分组成，clerkGroup的ID和org的ID。用“|”分隔：
 * 		<code>clerkGroupId|orgId</code>，其中orgId可以省略，省略时表示orgId为“-1”，此 时source形
 * 		式为<code>clerkGroupId</code>。orgId的规则是：orgId大于0时，orgId的值就是指定的单位ID，
 * 		source为0时，取当前登录用户的单位ID为制定单位的ID，orgId小于0时，取查询参数orgId为指定单位
 * 		的ID。
 * </ol>
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since rfs-base 1.5.6
 * @since 2011-02-27
 */
public class SelectDataSource extends VersionableBean {
	
	public static short CAT_单位分组 = 1;
	public static short CAT_人员分组 = 2;
	public static short CAT_术语表 = 3;
	public static short CAT_枚举 = 4;
	public static short CAT_单位分组中的人员 = 5;
	public static short CAT_指定单位的人员 = 6;
	public static short CAT_指定单位指定人员分组中的人员 = 7;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4362763309012260353L;

	// private Long id;
	private String selectId;
	private short cat;
	private String source;
	private String description;
	private byte editStatus;

	// private int type; //类型
	// private byte status; //状态
	// private String remark; //备注
	// private Long creator; //创建者
	// private Date creationTime; //创建时间
	// private Long modifier; //最后修改者
	// private Date modificationTime; //最后修改时间

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

	public short getCat() {
		return cat;
	}

	public void setCat(short cat) {
		this.cat = cat;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte getEditStatus() {
		return editStatus;
	}

	public void setEditStatus(byte editStatus) {
		this.editStatus = editStatus;
	}

	public String toString(){
		return new ToStringBuilder(this)
			.append("id", getId())
			.append("selectId", selectId)
			.append("category", cat)
			.append("source", source)
			.append("description", description)
			.append("editStatus", editStatus)
			.toString();
	}
	
	public static void main(String[] args){
		SelectDataSource ds = new SelectDataSource();
		System.out.println(ds);
	}
}
