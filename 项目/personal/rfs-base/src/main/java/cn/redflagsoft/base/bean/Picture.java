/*
 * $Id: Picture.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

import cn.redflagsoft.base.util.CodeMapUtils;

import com.googlecode.jsonplugin.annotations.JSON;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class Picture extends VersionableBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6310463011466226670L;
	
	public static final byte STATUS_有效 = 1;
	public static final byte STATUS_无效 = 0;
	
	
	private Long fileId;
	private Long objectId;
	private String objectName;
	private String title;
	private Short category;
	private String categoryName;
	private String scene;
	private String keywords;
	private Long publisherId;
	private String publisherName;
	private Date publishTime;
	private String fileFormat;
	private int width;
	private int height;
	private int abbrWidth;
	private int abbrHeight;
	private long fileSize;
	private String string1;
	private String string2;
	private String string3;
	private String description;
	
	
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Short getCategory() {
		return category;
	}
	public void setCategory(Short category) {
		this.category = category;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public void setCategoryIdLabel(String label){
		setCategoryName(label);
	}
	
	public String getScene() {
		return scene;
	}
	public void setScene(String scene) {
		this.scene = scene;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Long getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	
	@JSON(format="yyyy-MM-dd")
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getAbbrWidth() {
		return abbrWidth;
	}
	public void setAbbrWidth(int abbrWidth) {
		this.abbrWidth = abbrWidth;
	}
	public int getAbbrHeight() {
		return abbrHeight;
	}
	public void setAbbrHeight(int abbrHeight) {
		this.abbrHeight = abbrHeight;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getString1() {
		return string1;
	}
	public void setString1(String string1) {
		this.string1 = string1;
	}
	public String getString2() {
		return string2;
	}
	public void setString2(String string2) {
		this.string2 = string2;
	}
	public String getString3() {
		return string3;
	}
	public void setString3(String string3) {
		this.string3 = string3;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getStatusName(){
		return CodeMapUtils.getCodeName(Picture.class, "STATUS", getStatus());
	}
	
}
