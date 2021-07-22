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
     * ʵ�ʵ�ContentType�����ܺ�ԭʼ�ļ���contentType�ǲ�ͬ�ġ�
     * ����Ǹ����ļ��������жϳ����ģ���ԭʼ�ļ�һ���Ǹ����ļ���
     * �жϳ����ġ�
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
