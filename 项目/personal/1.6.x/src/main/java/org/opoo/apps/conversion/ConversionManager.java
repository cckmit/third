package org.opoo.apps.conversion;

import java.io.InputStream;
import java.util.List;

import org.opoo.ndao.support.ResultFilter;

public interface ConversionManager {

	/**
	 * 在系统支持转换的情况下判断指定的对象是否可以被转换。
	 * 
	 * <pre>
	 * 被转换的对象通常分为下面几类。
	 * 1. MS Office 2003/2007, pdf --> pdf --> Preview(swf), Thumbnails(png), text
	 * 2. 视频文件 mp4,avi,mpeg,flv --> flv --> Thumbnails
	 * 3. xxx - XXX
	 * </pre>
	 * @param object
	 * @return
	 * @see #isConversionEnabled()
	 */
	boolean isConvertable(Object object);
	
	/**
	 * 
	 * @return
	 */
	boolean isConversionEnabled();
	
	/**
	 * 调用前object中的contentType应该是修正过的。
	 * 
	 * @param object
	 * @return
	 */
	boolean convert(ConvertableObject object);
	
	ConversionRevision getConversionRevision(long revisionId);
	
	ConversionRevision getConversionRevision(ConvertableObject object);
	
	ConversionRevision getConversionRevision(int objectType, long objectId, int revisionNumber);
	
	ConversionArtifact getConversionArtifact(ConversionRevision rev, ConversionArtifactType type, int page);

	InputStream getConversionArtifactData(ConversionArtifact ca) throws Exception;
	
	ConversionStatus getConversionStatus(ConversionRevision rev);
	
	List<ConversionStatus> getConversionStatuses();
	
	ConvertableObject getConvertableObject(ConversionRevision rev);
	
	List<ConversionStatus> getErrorStatuses();
	
	boolean isAllowedToConvert(ConvertableObject co);

    boolean isOffice2007Document(ConvertableObject co);
    
    boolean isModifiableOnDownload(ConvertableObject co);

    void deleteAll(ConversionRevision rev);

    int getErrorConversionRevisionCount();

    int getConversionRevisionCount();

    List<ConversionStatus> getConversionErrorStatuses(ResultFilter filter);
}
