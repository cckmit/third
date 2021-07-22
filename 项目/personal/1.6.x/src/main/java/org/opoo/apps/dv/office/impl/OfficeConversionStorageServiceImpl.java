package org.opoo.apps.dv.office.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.FastDateFormat;
import org.opoo.apps.dv.impl.AbstractConversionStorageService;
import org.opoo.apps.dv.office.OfficeConversionArtifact;
import org.opoo.apps.dv.office.OfficeConversionArtifactType;
import org.opoo.apps.dv.office.OfficeConversionErrorStep;
import org.opoo.apps.dv.office.OfficeConversionMetaData;
import org.opoo.apps.dv.office.OfficeConversionStorageService;
import org.opoo.apps.dv.office.dao.OfficeConversionDao;
import org.opoo.apps.dv.office.model.OfficeArtifact;
import org.opoo.cache.Cache;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.factory.annotation.Required;

import com.google.common.collect.Lists;

public class OfficeConversionStorageServiceImpl
		extends
		AbstractConversionStorageService<OfficeConversionMetaData, OfficeConversionArtifact, OfficeConversionArtifactType, OfficeConversionDao>
		implements OfficeConversionStorageService {
	
	private Cache<Long, List<OfficeConversionErrorStep>> conversionErrorSteps;
	
	@Required
	public void setConversionErrorStepsCache(Cache<Long, List<OfficeConversionErrorStep>> conversionErrorSteps) {
		this.conversionErrorSteps = conversionErrorSteps;
	}
	
	@Override
	protected OfficeConversionArtifact buildArtifact(OfficeConversionMetaData cm, OfficeConversionArtifactType type, int partNumber) {
		return new OfficeArtifact(cm.getId(), type, partNumber) ;
	}

	public OfficeConversionErrorStep saveErrorStep(
			OfficeConversionErrorStep conversionErrorStep) {
		 // TODO: fix: for some reason the old DV schema only allows one error per step
//        if (getErrorSteps(getMetaData(conversionErrorStep.getConversionMetaDataID())).size() > 0) {
//            return null;
//        }
        this.conversionErrorSteps.remove(conversionErrorStep.getConversionMetaDataID());
        return conversionDao.saveErrorStep(conversionErrorStep);
	}


	public void deleteSteps(OfficeConversionMetaData cm) {
		conversionDao.removeErrorStepsByMetaDataID(cm.getId());
        conversionErrorSteps.remove(cm.getId());
	}

	public List<OfficeConversionErrorStep> getErrorSteps(
			OfficeConversionMetaData cm) {
		if (cm == null) {
			return Lists.newLinkedList();
		}

		List<OfficeConversionErrorStep> steps = conversionErrorSteps.get(cm.getId());

		if (steps == null) {
			steps = conversionDao.findErrorStepsByMetaDataID(cm.getId());
			// cache even empty lists for negative cache
			conversionErrorSteps.put(cm.getId(), steps);
		}

		return steps;
	}

	public List<Long> getErrorConversionMetadataIDs(ResultFilter filter) {
		return conversionDao.findErrorMetadataIDs(filter);
	}
	
	@Override
	protected String buildStorageKey(OfficeConversionArtifact ca) {
		OfficeConversionMetaData data = getMetaData(ca.getConversionMetadataID());
		Date date = data.getCreationDate();
		ArtifactTypeInfo info = ArtifactTypeInfo.getTypeInfo(ca.getType());
		
		StringBuffer sb = new StringBuffer();
		sb.append("file:///").append(ca.getInstanceID()).append('/');
		if(date != null){
			sb.append(format.format(date));
		}else{
			sb.append("NODATE");
		}
		sb.append('/').append(ca.getConversionMetadataID()).append('/')
			.append(ca.getType().getName().toLowerCase()).append(ca.getPage())
			.append(".").append(info.getExt());
		return sb.toString();
	}
	static final FastDateFormat format = FastDateFormat.getInstance("yyyy/MM/dd");

	public static enum ArtifactTypeInfo {
		File(OfficeConversionArtifactType.File, "application/octet-stream", "file"),
        Pdf(OfficeConversionArtifactType.Pdf, "application/pdf", "pdf"),
        Text(OfficeConversionArtifactType.Text, "text/plain", "txt"),
        Thumbnail(OfficeConversionArtifactType.Thumbnail, "image/png", "png"),
        Preview(OfficeConversionArtifactType.Preview, "application/x-shockwave-flash", "swf");
        
        private static Map<OfficeConversionArtifactType, ArtifactTypeInfo> INFOS;
        static{
        	Map<OfficeConversionArtifactType, ArtifactTypeInfo> map = new HashMap<OfficeConversionArtifactType, ArtifactTypeInfo>();
        	for(ArtifactTypeInfo info: ArtifactTypeInfo.values()){
        		map.put(info.getType(), info);
        	}
        	INFOS = Collections.synchronizedMap(map);
        }
	
        private final OfficeConversionArtifactType type;
        private final String contentType;
        private final String ext;
        private ArtifactTypeInfo(OfficeConversionArtifactType type, String contentType, String ext){
        	this.type = type;
        	this.contentType = contentType;
        	this.ext = ext;
        }
		public OfficeConversionArtifactType getType() {
			return type;
		}
		public String getContentType() {
			return contentType;
		}
		public String getExt() {
			return ext;
		}
        
		public static ArtifactTypeInfo getTypeInfo(OfficeConversionArtifactType type){
			return INFOS.get(type);
		}
	}
}
