package org.opoo.apps.dv.office.dao;

import java.util.List;

import org.opoo.apps.dv.dao.ConversionDao;
import org.opoo.apps.dv.office.OfficeConversionArtifact;
import org.opoo.apps.dv.office.OfficeConversionArtifactType;
import org.opoo.apps.dv.office.OfficeConversionErrorStep;
import org.opoo.apps.dv.office.OfficeConversionMetaData;
import org.opoo.ndao.support.ResultFilter;

public interface OfficeConversionDao
		extends
		ConversionDao<OfficeConversionMetaData, OfficeConversionArtifact, OfficeConversionArtifactType> {

	OfficeConversionErrorStep saveErrorStep(OfficeConversionErrorStep conversionErrorStep);

	List<OfficeConversionErrorStep> findErrorStepsByMetaDataID(long cmID);

	void removeErrorStepsByMetaDataID(long id);

	List<Long> findErrorMetadataIDs(ResultFilter filter);

}
