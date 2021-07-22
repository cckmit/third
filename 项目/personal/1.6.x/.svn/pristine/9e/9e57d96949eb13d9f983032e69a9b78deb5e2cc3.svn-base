package org.opoo.apps.dvi.office.model;

import java.util.Date;

import org.opoo.apps.dvi.office.OfficeConversionErrorStep;
import org.opoo.apps.dvi.office.OfficeConversionStep;

public class OfficeErrorStep implements OfficeConversionErrorStep{
	private static final long serialVersionUID = -1730168251940272057L;
	private OfficeConversionStep step;
    private Date modificationDate;
    private String message;
    private long conversionMetaDataID;
    
	public OfficeErrorStep() {
		super();
	}
	public OfficeErrorStep(long conversionMetaDataID, Date date, String message, OfficeConversionStep step) {
		this.conversionMetaDataID = conversionMetaDataID;
		this.message = message;
		this.modificationDate = date;
		this.step = step;
	}
	public OfficeConversionStep getStep() {
		return step;
	}
	public Date getModificationDate() {
		return modificationDate;
	}
	public String getMessage() {
		return message;
	}
	public long getConversionMetaDataID() {
		return conversionMetaDataID;
	}
	
	 
    //用做实体类，需要ID属性和set方法。
    private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setStep(OfficeConversionStep step) {
		this.step = step;
	}
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setConversionMetaDataID(long conversionMetaDataID) {
		this.conversionMetaDataID = conversionMetaDataID;
	}
}
