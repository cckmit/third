package org.opoo.apps.dv;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.opoo.apps.AppsObject;
import org.opoo.ndao.support.ResultFilter;

public interface ConversionManager<M extends ConversionMetaData, A extends ConversionArtifact<T>, T extends ConversionArtifactType, S extends ConversionStatus, P extends ConversionStep> {
	
	boolean convert(ConvertableObject binary);
	
	boolean isConversionEnabled();
	
	boolean isConvertable(AppsObject o);

	boolean isDisabled(ConvertableObject co);

	boolean isAllowedToConvert(ConvertableObject co);

	boolean isModifiableOnDownload(ConvertableObject co);

	M getConversionMetaData(ConvertableObject co);

	M getConversionMetaData(long id);

	M getConversionMetaData(int objectType, long objectId, int version);
	
	int getConversionMetaDataCount();

	int getConversionMetaDataErrorCount(String filter);
	
	ConvertableObject getConvertableObject(M cm);
	
	A getConversionArtifact(M cm, T type, int partNumber);
	
	InputStream getConversionArtifactBody(A ca) throws IOException;
	
	boolean generateConversionArtifact(M cm, T type, int partNumber);
	
	//ConvertibleObjectProvider.SizedInputStream getModifiedStream(ConvertableObject co) throws IOException;
	
	void deleteAll(M cm);
	
	S getConversionStatus(M cm, P... steps);
	
	List<S> getConversionStatuses();
	
	List<S> getErrorStatuses(ResultFilter filter);
}
