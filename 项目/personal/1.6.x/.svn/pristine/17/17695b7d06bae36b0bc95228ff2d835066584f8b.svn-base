package org.opoo.apps.dvi.office.dao;

import java.util.List;

import org.opoo.apps.dvi.dao.ConversionDao;
import org.opoo.apps.dvi.office.OfficeConversionErrorStep;
import org.opoo.ndao.support.ResultFilter;

public interface OfficeConversionDao extends ConversionDao {

	OfficeConversionErrorStep saveErrorStep(OfficeConversionErrorStep conversionErrorStep);

	List<OfficeConversionErrorStep> findErrorStepsByMetaDataID(long cmID);

	void removeErrorStepsByMetaDataID(long id);

	List<Long> findErrorMetadataIDs(ResultFilter filter);

}
