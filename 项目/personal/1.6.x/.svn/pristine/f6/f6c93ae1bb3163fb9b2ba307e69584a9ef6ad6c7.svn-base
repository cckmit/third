package org.opoo.apps.dvi.dao;

import java.util.List;

import org.opoo.apps.dvi.ConversionArtifact;
import org.opoo.apps.dvi.ConversionArtifactType;
import org.opoo.apps.dvi.ConversionMetaData;

public interface ConversionDao {

	ConversionMetaData getMetaData(long id);

	Long getMetaDataID(int objectType, long objectId, int version);

	int getMetaDataCount();

	int getMetaDataErrorCount(String filter);
	
	ConversionArtifact getArtifact(long cmID, ConversionArtifactType type, int partNumber);
	
	int getArtifactCount(long cmID, ConversionArtifactType type);
	
	List<ConversionArtifact> findArtifacts(long cmID);
	
	void removeAllByMetaDataID(long cmID);
	
	/**
	 * @param meta
	 * @return
	 */
	ConversionMetaData saveMetaData(ConversionMetaData meta);
	
	ConversionMetaData updateMetaData(ConversionMetaData meta);
	
	ConversionArtifact saveArtifact(ConversionArtifact ca);
}
