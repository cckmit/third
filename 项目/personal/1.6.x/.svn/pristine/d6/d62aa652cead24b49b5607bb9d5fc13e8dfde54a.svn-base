package org.opoo.apps.conversion.impl;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.opoo.apps.conversion.ConversionArtifact;
import org.opoo.apps.conversion.ConversionArtifactType;
import org.opoo.apps.conversion.ConversionManager;
import org.opoo.apps.conversion.ConversionProvider;
import org.opoo.apps.conversion.ConversionRevision;
import org.opoo.apps.conversion.ConversionStatus;
import org.opoo.apps.conversion.ConversionStorageService;
import org.opoo.apps.conversion.ConvertableObject;
import org.opoo.apps.conversion.event.ConversionEvent;
import org.opoo.apps.conversion.util.FileFormats;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.cache.Cache;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.factory.annotation.Required;

import com.google.common.collect.Lists;


public class ConversionManagerImplBak implements ConversionManager {
	private static final Logger log = Logger.getLogger(ConversionManagerImplBak.class);
	
	private List<ConversionProvider> providers = Lists.newArrayList();
	private ConversionStorageService conversionStorageService;

	private Cache<Long, ConversionLock> lockCache;
	
	//@Required
	public void setProviders(List<ConversionProvider> providers) {
		this.providers = providers;
	}

	@Required
	public void setConversionStorageService(
			ConversionStorageService conversionStorageService) {
		this.conversionStorageService = conversionStorageService;
	}
	@Required
	public void setLockCache(Cache<Long, ConversionLock> lockCache) {
		this.lockCache = lockCache;
	}

	public boolean isConvertable(Object object) {
		if (!isConversionEnabled()) {
            return false;
        }

        if (object instanceof ConvertableObject) {
            ConvertableObject co = (ConvertableObject) object;
            String fileName = co.getFilename();
//            String suggestedContentType = co.getContentType();
            if(StringUtils.isBlank(fileName)){
            	return false;
            } 
            
            // might be one of the disabled extentions
            String fileExt = FileFormats.DocumentType.getExtension(fileName);
            for (String ext : getDisabledExtensions()) {
                if (ext.equalsIgnoreCase(fileExt)) {
                    return false;
                }
            }
            
            for(ConversionProvider p: providers){
            	if(p.isConvertable(object)){
            		return true;
            	}
            }
        }
        return false;
	}
	

	public List<String> getDisabledExtensions() {
		return Lists.newArrayList();
	}

	public boolean isConversionEnabled(){
		return true;
	}

	
	/**
	 * 调用前应该先调用isConvertable
	 */
	
	public boolean convert(ConvertableObject object) {
        if (object == null) {
            log.error("Can't request conversion for a null binary");
            return false;
        }

        if (!isConvertable(object)) {
            log.error(String.format("Object %s is can't be converted ", object));
            return false;
        }

        if (!isConversionEnabled()) {
            return false;
        }
        
        ConversionProvider currentProvider = null;
        for(ConversionProvider provider: providers){
        	if(provider.isConvertable(object)){
        		currentProvider = provider;
        		break;
        	}
        }
        
        if(currentProvider == null){
        	log.error(String.format("object %s is can't be converted, no matched conversion provider.", object));
            return false;
        }
        
        // file with the required artifact data
        long revisionId = -1;
        ConversionRevision revision = null;
        try{
        	revision = conversionStorageService.getRevision(object.getObjectType(), object.getObjectId(), object.getRevisionNumber());
        	if(revision == null){
        		revision = conversionStorageService.saveRevision(object.getObjectType(), object.getObjectId(),
        				object.getFilename(), object.getContentType(), object.getContentLength(),
        				object.getRevisionNumber(),
        				0, null);
        	}
        	
        	revisionId = revision.getRevisionId();
        	ConversionLock lock = lockCache.get(revisionId);
        	if(lock != null){
        		log.info(String.format("Conversion for %s already running.", revision.getFilename()));
                return true;
        	}
        	
        	// create a new info and figure out exactly what needs to be generated
            // and what's generated so far so no need to go to DB anymore
        	lock = new ConversionLock();
        	lockCache.put(revisionId, lock);

            // file with the required artifact data
            //caFile = FileUtils.createTempFile(cm);

            // delete the steps as errors since starting over
            //conversionStorageService.deleteSteps(revision);
             return currentProvider.convert(revision, object.getStreamData());
        }catch(Exception e){
        	log.error(String.format("Error starting converting %s", object), e);            
            // kill it
            this.lockCache.remove(revisionId);
            return false;
        }finally{
        	//FileUtils.deleteQuietly(caFile);
        }
	}

	
	public ConversionRevision getConversionRevision(long revisionId) {
		return conversionStorageService.getRevision(revisionId);
	}

	
	public ConversionRevision getConversionRevision(ConvertableObject object) {
		return getConversionRevision(object.getObjectType(), object.getObjectId(), object.getRevisionNumber());
	}

	
	protected void handleError(ConversionRevision payload) {
		// TODO Auto-generated method stub
		
	}
	protected void handleCompleteEvent(ConversionRevision payload){
		//conversionManagerImpl.lockCache.remove(e.getPayload().getRevisionId());
	}
	
	public static class ConversionLock implements Serializable{
		private static final long serialVersionUID = -9165122031764981889L;
		private long start = System.currentTimeMillis();

		public long getStart() {
			return start;
		}
	}
	
	public static class ConversionEventListener implements EventListener<ConversionEvent>{
		private ConversionManagerImplBak conversionManagerImpl;
		
		public void setConversionManagerImpl(ConversionManagerImplBak conversionManagerImpl) {
			this.conversionManagerImpl = conversionManagerImpl;
		}
		
		public void handle(ConversionEvent e) {
			if(e.getType() == ConversionEvent.Type.Complete){
				conversionManagerImpl.handleCompleteEvent(e.getSource());
			}else if(e.getType() == ConversionEvent.Type.Error){
				conversionManagerImpl.handleError(e.getSource());
			}
		}
	}

	public InputStream getConversionArtifactData(ConversionArtifact ca)	throws Exception {
		return conversionStorageService.getArtifactStream(ca);
	}


	public boolean isAllowedToConvert(ConvertableObject co) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isOffice2007Document(ConvertableObject co) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isModifiableOnDownload(ConvertableObject co) {
		// TODO Auto-generated method stub
		return false;
	}

	public ConversionRevision getConversionRevision(int objectType,	long objectId, int revisionNumber) {
		return conversionStorageService.getRevision(objectType, objectId, revisionNumber);
	}
	public ConvertableObject getConvertableObject(ConversionRevision rev) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ConversionArtifact getConversionArtifact(ConversionRevision rev,	ConversionArtifactType type, int page) {
		return conversionStorageService.getArtifact(rev, type, page);
	}

	public void deleteAll(ConversionRevision rev) {
		conversionStorageService.deleteAll(rev);
	}

	public int getErrorConversionRevisionCount() {
		return conversionStorageService.getErrorRevisionCount();
	}

	public int getConversionRevisionCount() {
		return conversionStorageService.getRevisionCount();
	}

	public ConversionStatus getConversionStatus(ConversionRevision rev) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ConversionStatus> getConversionStatuses() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ConversionStatus> getErrorStatuses() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ConversionStatus> getConversionErrorStatuses(ResultFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
}
