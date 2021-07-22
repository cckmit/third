package org.opoo.apps.conversion.model;

import org.opoo.apps.conversion.ConversionArtifact;
import org.opoo.apps.conversion.ConversionArtifactType;


public class ConversionArtifactImpl implements ConversionArtifact {
	private static final long serialVersionUID = -1063020136319907767L;
	private long revisionId;
	private String instanceId;
	private ConversionArtifactType type;
	private String stringType;
	private int page;
	private String filename;
	private String contentType;
	private long length;
	
	public ConversionArtifactImpl(){
	}

	public ConversionArtifactImpl(long revisionId, ConversionArtifactType type,	int page) {
		this.revisionId = revisionId;
		this.page = page;
		this.type = type;
	}
	
	public long getRevisionId() {
		return revisionId;
	}
	
	public String getInstanceId() {
		return instanceId;
	}

	
	public ConversionArtifactType getType() {
		return type;
	}

	
	public int getPage() {
		return page;
	}

	
	public String getFilename() {
		return filename;
	}

	
	public String getContentType() {
		return contentType;
	}

	
	public long getLength() {
		return length;
	}

	public String getStringType() {
		return stringType;
	}

	public void setStringType(String stringType) {
		this.stringType = stringType;
	}

	public void setRevisionId(long revisionId) {
		this.revisionId = revisionId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public void setType(ConversionArtifactType type) {
		this.type = type;
		if(type != null){
			this.stringType = type.getName();
		}
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setLength(long length) {
		this.length = length;
	}

	
	
    /**
     * This is the key we will use to retrieve this object from the StorageService.  It should be unique in a
     * system.
     *
     * @return a key which can be used to retrieve this object from the storage service.
     */
	
    public String getStorageKey() {
        return String.format("i%s_r%d_t%s_p%d", instanceId, revisionId, type != null ? type.getName(): stringType, page);
    	//return buildStorageKey(revisionId, type != null ? type.getName() : stringType, page);
    }
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ConversionArtifact that = (ConversionArtifact) o;

		if (page != that.getPage()) {
			return false;
		}
		if (revisionId != that.getRevisionId()) {
			return false;
		}
		if (instanceId != null ? !instanceId.equals(that.getInstanceId())
				: that.getInstanceId() != null) {
			return false;
		}
		if (type != that.getType()) {
			return false;
		}

		return true;
	}

	
	public int hashCode() {
		int result = type != null ? type.hashCode() : 0;
		result = 31 * result + (instanceId != null ? instanceId.hashCode() : 0);
		result = 31 * result
				+ (int) (revisionId ^ (revisionId >>> 32));
		result = 31 * result + page;
		return result;
	}

	
	public String toString() {
		return "ConversionArtifactImpl{" + "type=" + (type != null ? type: stringType) + ", instanceID='"
				+ instanceId + '\'' + ", revisionID=" + revisionId + ", page="
				+ page + ", filename='" + filename + '\'' + ", contentType='"
				+ contentType + '\'' + ", length=" + length + '}';
	}
}
