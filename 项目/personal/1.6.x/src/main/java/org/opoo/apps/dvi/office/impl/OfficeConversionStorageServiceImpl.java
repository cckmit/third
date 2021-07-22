package org.opoo.apps.dvi.office.impl;

import java.util.List;

import org.opoo.apps.dvi.ConversionArtifactType;
import org.opoo.apps.dvi.ConversionMetaData;
import org.opoo.apps.dvi.impl.AbstractConversionStorageService;
import org.opoo.apps.dvi.office.OfficeConversionArtifact;
import org.opoo.apps.dvi.office.OfficeConversionArtifactType;
import org.opoo.apps.dvi.office.OfficeConversionErrorStep;
import org.opoo.apps.dvi.office.OfficeConversionMetaData;
import org.opoo.apps.dvi.office.OfficeConversionStorageService;
import org.opoo.apps.dvi.office.dao.OfficeConversionDao;
import org.opoo.apps.dvi.office.model.OfficeArtifact;
import org.opoo.cache.Cache;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.factory.annotation.Required;

import com.google.common.collect.Lists;

public class OfficeConversionStorageServiceImpl extends
		AbstractConversionStorageService implements
		OfficeConversionStorageService {
	
	private Cache<Long, List<OfficeConversionErrorStep>> conversionErrorSteps;
	
	private OfficeConversionDao getOfficeConversionDao(){
		return (OfficeConversionDao) conversionDao;
	}
	
	@Required
	public void setConversionErrorStepsCache(Cache<Long, List<OfficeConversionErrorStep>> conversionErrorSteps) {
		this.conversionErrorSteps = conversionErrorSteps;
	}
	
	@Override
	protected OfficeConversionArtifact buildArtifact(ConversionMetaData cm, ConversionArtifactType type, int partNumber) {
		return new OfficeArtifact(cm.getId(), (OfficeConversionArtifactType) type, partNumber) ;
	}

	public OfficeConversionErrorStep saveErrorStep(
			OfficeConversionErrorStep conversionErrorStep) {
		 // TODO: fix: for some reason the old DV schema only allows one error per step
//        if (getErrorSteps(getMetaData(conversionErrorStep.getConversionMetaDataID())).size() > 0) {
//            return null;
//        }
        this.conversionErrorSteps.remove(conversionErrorStep.getConversionMetaDataID());
        return getOfficeConversionDao().saveErrorStep(conversionErrorStep);
	}


	public void deleteSteps(OfficeConversionMetaData cm) {
		getOfficeConversionDao().removeErrorStepsByMetaDataID(cm.getId());
        conversionErrorSteps.remove(cm.getId());
	}

	public List<OfficeConversionErrorStep> getErrorSteps(
			OfficeConversionMetaData cm) {
		if (cm == null) {
			return Lists.newLinkedList();
		}

		List<OfficeConversionErrorStep> steps = conversionErrorSteps.get(cm.getId());

		if (steps == null) {
			steps = getOfficeConversionDao().findErrorStepsByMetaDataID(cm.getId());
			// cache even empty lists for negative cache
			conversionErrorSteps.put(cm.getId(), steps);
		}

		return steps;
	}

	public List<Long> getErrorConversionMetadataIDs(ResultFilter filter) {
		return getOfficeConversionDao().findErrorMetadataIDs(filter);
	}

}
