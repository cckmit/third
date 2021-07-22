package org.opoo.apps.conversion.model;

import org.opoo.apps.conversion.ConversionRevision;

public class ConversionRevisionImpl implements ConversionRevision {
	private static final long serialVersionUID = 2704281383335794844L;
	private long revisionId;
	private long objectId;
	private int objectType;
	private String filename;
	private long length;
	private int numberOfPages;
	private int revisionNumber;
	private String metadata = null;
	private String contentType;
	private long creationDate;
	private long modificationDate;

	private boolean modified = false;
	
	
	

	public ConversionRevisionImpl() {
		super();
	}


	public ConversionRevisionImpl(long revisionId) {
		super();
		this.revisionId = revisionId;
	}
	
	public ConversionRevisionImpl(ConversionRevision rev) {
		super();
		this.revisionId = rev.getRevisionId();
		this.contentType = rev.getContentType();
		this.creationDate = rev.getCreationDate();
		this.filename = rev.getFilename();
		this.length = rev.getLength();
		this.metadata = rev.getMetadata();
		this.modificationDate = rev.getModificationDate();
		this.numberOfPages = rev.getNumberOfPages();
		this.objectId = rev.getObjectId();
		this.objectType = rev.getObjectType();
		this.revisionNumber = rev.getRevisionNumber();
	}


	public boolean isModified() {
		return modified;
	}

	
	public long getRevisionId() {
		return revisionId;
	}

	
	public long getObjectId() {
		return objectId;
	}

	
	public int getObjectType() {
		return objectType;
	}

	
	public String getContentType() {
		return contentType;
	}

	
	public int getRevisionNumber() {
		return revisionNumber;
	}

	
	public String getFilename() {
		return filename;
	}

	
	public long getLength() {
		return length;
	}

	
	public int getNumberOfPages() {
		return numberOfPages;
	}

	
	public void setNumberOfPages(int numberOfPages) {
		if (numberOfPages != this.numberOfPages) {
			modified = true;
			this.numberOfPages = numberOfPages;
		}
	}

	
	public String getMetadata() {
		return metadata;
	}

	
	public void setMetadata(String metadata) {
		if ((metadata != null && !metadata.equals(this.metadata))
				|| (metadata == null && this.metadata != null)) {
			modified = true;
			this.metadata = metadata;
		}
	}

	
	public boolean isValid() {
		return this.length > 0;
	}

	
	public long getCreationDate() {
		return creationDate;
	}

	
	public long getModificationDate() {
		return modificationDate;
	}


	public void setObjectId(long convertableObjectId) {
		this.objectId = convertableObjectId;
	}


	public void setRevisionId(long revisionId) {
		this.revisionId = revisionId;
	}

	public void setObjectType(int convertableObjectType) {
		this.objectType = convertableObjectType;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public void setLength(long length) {
		this.length = length;
	}


	public void setRevisionNumber(int revisionNumber) {
		this.revisionNumber = revisionNumber;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}


	public void setModificationDate(long modificationDate) {
		this.modificationDate = modificationDate;
	}


//	public void setModified(boolean modified) {
//		this.modified = modified;
//	}


	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ConversionRevisionImpl that = (ConversionRevisionImpl) o;
		return revisionId == that.revisionId;
	}

	
	public int hashCode() {
		return (int) (revisionId ^ (revisionId >>> 32));
	}

	
	public String toString() {
		return "ConversionRevisionImpl{" + "revisionId=" + revisionId
				+ ", objectId=" + objectId 
				+ ", objectType=" + objectType
				+ ", revisionNumber=" + revisionNumber
				+ ", filename='" + filename
				+ "', contentType='" + contentType
				+ "', numberOfPages=" + numberOfPages 
				+ ", metadata='" + metadata + "'}";
	}
}
