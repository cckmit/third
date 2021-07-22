package org.opoo.apps.conversion;

import java.io.Serializable;



public interface ConversionRevision extends Serializable{
	
	/**
	 * 
	 * 
    long getConvertableObjectID();

    String getFilename();

    long getLength();

    int getNumberOfPages();

    void setNumberOfPages(int numberOfPages);

    int getRevisionNumber();

    String getMetadata();

    void setMetadata(String metadata);

    ConvertibleType getConvertableType();

    long getConversionMetaDataID();

    boolean isValid();

    long getCreationDate();

    long getModificationDate();
	 * @return
	 */
	
	long getRevisionId();
	
    long getObjectId();
    
    int getObjectType();
    
    /**
     * 实际的ContentType，可能和原始文件的contentType是不同的。
     * 这个是根据文件的内容判断出来的，而原始文件一般是根据文件名
     * 判断出来的。
     * @return
     */
    String getContentType();
    
    int getRevisionNumber();

    String getFilename();

    long getLength();

    int getNumberOfPages();

    void setNumberOfPages(int numberOfPages);

    String getMetadata();

    void setMetadata(String metadata);

    boolean isValid();

    long getCreationDate();

    long getModificationDate();
    
}
