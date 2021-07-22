package org.opoo.apps.conversion.model;

import org.opoo.apps.conversion.ConversionErrorStep;

public class ConversionErrorStepImpl implements ConversionErrorStep {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6023667786367571790L;
	private long id = -1;//PK
	private long revisionId;
	private String step;
	private long modificationDate;
	private String message;
	
	public ConversionErrorStepImpl(long revisionId, String step,
			long modificationDate, String message) {
		super();
		this.revisionId = revisionId;
		this.step = step;
		this.modificationDate = modificationDate;
		this.message = message;
	}
	
	public ConversionErrorStepImpl() {
		super();
	}
	
	public ConversionErrorStepImpl(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRevisionId() {
		return revisionId;
	}
	public void setRevisionId(long revisionId) {
		this.revisionId = revisionId;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public long getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(long modificationDate) {
		this.modificationDate = modificationDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toString(){
		return "ConversionErrorStepImpl{revisionId="+ revisionId 
			+ ", step='" + step
			+ "', modificationDate=" + modificationDate 
			+ ", message='" + message + "'}";
	}
}
