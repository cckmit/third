package cn.redflagsoft.base.bean;

import org.apache.commons.beanutils.PropertyUtils;
import org.opoo.ndao.DataAccessException;

public class DatumCategoryWithMatterDatum extends DatumCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8178021068052581702L;
	private MatterDatum matterDatum;
	private String fileNoField;
	private String fileIdField;
	private int matterDatumType;

	public DatumCategoryWithMatterDatum(DatumCategory datumCategory, MatterDatum matterDatum){
		try {
			PropertyUtils.copyProperties(this, datumCategory);
		} catch (Exception e) {
			throw new DataAccessException(e);
		} 
		this.matterDatum = matterDatum;
		if(matterDatum != null){
			this.fileIdField = matterDatum.getFileIdField();
			this.fileNoField = matterDatum.getFileNoField();
			this.matterDatumType = matterDatum.getType();
		}
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
	
	protected MatterDatum getMatterDatum(){
		return matterDatum;
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
}
