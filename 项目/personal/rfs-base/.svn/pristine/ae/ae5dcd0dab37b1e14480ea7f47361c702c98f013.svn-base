/*
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.vo;

import org.apache.commons.beanutils.PropertyUtils;

import cn.redflagsoft.base.bean.DatumAttachment;
import cn.redflagsoft.base.bean.DatumCategory;
import cn.redflagsoft.base.bean.DatumCategoryWithMatterDatum;


/**
 * @author 
 *
 */
public class DatumCategoryVO extends DatumCategory {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4761542065628622365L;

	//id
	private String content;
	
	private String fileType;
	
	private String readableFormat;
	
	private String protectedFormat;
	
	private String fileName;
	
	private String fileNoField;
	
	private String fileIdField;
	
	private int matterDatumType;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getReadableFormat() {
		return readableFormat;
	}

	public void setReadableFormat(String readableFormat) {
		this.readableFormat = readableFormat;
	}

	public String getProtectedFormat() {
		return protectedFormat;
	}

	public void setProtectedFormat(String protectedFormat) {
		this.protectedFormat = protectedFormat;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileId() {
		return getContent();
	}
	

	public String getFileNoField() {
		return fileNoField;
	}

	public void setFileNoField(String fileNoField) {
		this.fileNoField = fileNoField;
	}

	public String getFileIdField() {
		return fileIdField;
	}

	public void setFileIdField(String fileIdField) {
		this.fileIdField = fileIdField;
	}

	/**
	 * @return the matterDatumType
	 */
	public int getMatterDatumType() {
		return matterDatumType;
	}

	/**
	 * @param matterDatumType the matterDatumType to set
	 */
	public void setMatterDatumType(int matterDatumType) {
		this.matterDatumType = matterDatumType;
	}

	public DatumCategoryVO(DatumCategory dc, DatumAttachment da) {
		try {
			PropertyUtils.copyProperties(this, dc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(dc instanceof DatumCategoryWithMatterDatum){
			DatumCategoryWithMatterDatum dcc = (DatumCategoryWithMatterDatum) dc;
			this.fileIdField = dcc.getFileIdField();
			this.fileNoField = dcc.getFileNoField();
			this.matterDatumType = dcc.getMatterDatumType();
		}
		if (da != null) {
			this.content = da.getContent();
			this.fileName = da.getFileName();
			this.fileType = da.getFileType();
			this.protectedFormat = da.getProtectedFormat();
			this.readableFormat = da.getReadableFormat();
		}
	}
}
