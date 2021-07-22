package org.opoo.apps.dv.office.model;

import java.util.Date;

import org.opoo.apps.dv.office.OfficeConversionMetaData;

public class OfficeMetaData implements OfficeConversionMetaData {
	private static final long serialVersionUID = -3560025464934158389L;
	private long id;
	private int convertableObjectType;
	private long convertableObjectId;
	private String filename;
	private long length;
	private int numberOfPages;
	private int revisionNumber;
	private String metadata = null;
	private Date creationDate;
	private Date modificationDate;
    
	public OfficeMetaData() {
		super();
	}
	
	/**
	 * 构建Metadata实例，当前id没有有效值。
	 * @param covnertableObjectId
	 * @param convertableObjectType
	 * @param filename
	 * @param length
	 * @param numberOfPages
	 * @param revisionNumber
	 * @param metadata
	 */
	public OfficeMetaData(int convertableObjectType, long covnertableObjectId, String filename, long length,
			int numberOfPages, int revisionNumber, String metadata){
		this(-1L, convertableObjectType, covnertableObjectId, filename, length, numberOfPages, revisionNumber, metadata, new Date(), new Date());
	}
	public OfficeMetaData(long id, int convertableObjectType, long covnertableObjectId,
			String filename, long length,
			int numberOfPages, int revisionNumber, String metadata,
			Date creationDate, Date modificationDate) {
		super();
		this.id = id;
		this.convertableObjectId = covnertableObjectId;
		this.convertableObjectType = convertableObjectType;
		this.filename = filename;
		this.length = length;
		this.numberOfPages = numberOfPages;
		this.revisionNumber = revisionNumber;
		this.metadata = metadata;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
	}

	public long getId() {
		return id;
	}

	public long getConvertableObjectId() {
		return convertableObjectId;
	}

	public int getConvertableObjectType() {
		return convertableObjectType;
	}

	public String getFilename() {
		return filename;
	}

	public long getLength() {
		return length;
	}

	public int getRevisionNumber() {
		return revisionNumber;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public boolean isValid() {
		 return this.numberOfPages > 0;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public int getNumberOfParts() {
		return getNumberOfPages();
	}
	public void setNumberOfParts(int numberOfParts) {
		setNumberOfPages(numberOfParts);
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        OfficeMetaData that = (OfficeMetaData) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "OfficeMetaData{" + "id=" + id + ", covnertableObjectId="
                + convertableObjectId + ", numberOfPages=" + numberOfPages + ", revisionNumber="
                + revisionNumber + ", metadata='" + metadata + '\'' + ", convertableObjectType=" + convertableObjectType + '}';
    }

    
    
    private String contentType;
    //用做实体类，需要set方法。
    public void setId(long id) {
		this.id = id;
	}

	public void setConvertableObjectType(int convertableObjectType) {
		this.convertableObjectType = convertableObjectType;
	}

	public void setConvertableObjectId(long convertableObjectId) {
		this.convertableObjectId = convertableObjectId;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
