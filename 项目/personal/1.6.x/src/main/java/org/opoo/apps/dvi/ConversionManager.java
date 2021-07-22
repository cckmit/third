package org.opoo.apps.dvi;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.opoo.apps.AppsObject;
import org.opoo.apps.dv.ConvertableObject;
import org.opoo.ndao.support.ResultFilter;

public interface ConversionManager {
	
	boolean convert(ConvertableObject binary);
	
	boolean isConversionEnabled();
	
	boolean isConvertable(AppsObject o);

	boolean isDisabled(ConvertableObject co);

	boolean isAllowedToConvert(ConvertableObject co);

	boolean isModifiableOnDownload(ConvertableObject co);

	ConversionMetaData getConversionMetaData(ConvertableObject co);

	ConversionMetaData getConversionMetaData(long id);

	ConversionMetaData getConversionMetaData(int objectType, long objectId, int version);
	
	int getConversionMetaDataCount();

	int getConversionMetaDataErrorCount(String filter);
	
	ConvertableObject getConvertableObject(ConversionMetaData cm);
	
	ConversionArtifact getConversionArtifact(ConversionMetaData cm, ConversionArtifactType type, int partNumber);
	
	InputStream getConversionArtifactBody(ConversionArtifact ca) throws IOException;
	
	boolean generateConversionArtifact(ConversionMetaData cm, ConversionArtifactType type, int partNumber);
	
	//ConvertibleObjectProvider.SizedInputStream getModifiedStream(ConvertableObject co) throws IOException;
	
	void deleteAll(ConversionMetaData cm);
	
	ConversionStatus getConversionStatus(ConversionMetaData cm, ConversionStep... steps);
	
	List<ConversionStatus> getConversionStatuses();
	
	List<ConversionStatus> getErrorStatuses(ResultFilter filter);
}
