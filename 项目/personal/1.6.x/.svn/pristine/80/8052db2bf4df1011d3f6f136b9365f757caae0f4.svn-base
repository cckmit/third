package org.opoo.apps.dv;


import java.io.Serializable;
import java.util.Date;

/**
 * The class helps to keep track of the document conversion information that does not belong
 * in the Document or Document Body object
 */
public interface ConversionMetaData extends Serializable {
	
	long getId();

    int getConvertableObjectType();
    
    long getConvertableObjectId();

    String getFilename();

    /**
     * 被转换的原始文件的字节大小。
     * @return 文件长度，字节数
     */
    long getLength();

    //int getNumberOfPages();

    //void setNumberOfPages(int numberOfPages);

    int getRevisionNumber();

    String getMetadata();

    void setMetadata(String metadata);

    boolean isValid();

    Date getCreationDate();

    Date getModificationDate();

    /**
     * 文件页数或者分段数。
     * 通常文档文件有页数；
     * 而视频文件可能会被分段存储，拆分成几个部分，
     * 这里记录分段数。
     * 
     * @return 文档文件的页数、视频文件的分段数量
     */
    int getNumberOfParts();
    
    //void setNumberOfParts(int numberOfParts);
}
