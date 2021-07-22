package org.opoo.apps.dv.dao;

import java.util.List;

import org.opoo.apps.dv.ConversionArtifact;
import org.opoo.apps.dv.ConversionArtifactType;
import org.opoo.apps.dv.ConversionMetaData;

public interface ConversionDao<M extends ConversionMetaData, A extends ConversionArtifact<T>, T extends ConversionArtifactType> {

	M getMetaData(long id);

	Long getMetaDataID(int objectType, long objectId, int version);

	int getMetaDataCount();

	int getMetaDataErrorCount(String filter);
	
	A getArtifact(long cmID, T type, int partNumber);
	
	int getArtifactCount(long cmID, T type);
	
	List<A> findArtifacts(long cmID);
	
	void removeAllByMetaDataID(long cmID);
	
	/**
	 * @param meta
	 * @return
	 */
	M saveMetaData(M meta);
	
	M updateMetaData(M meta);
	
	A saveArtifact(A ca);
}
