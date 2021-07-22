package org.opoo.apps.conversion.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.opoo.apps.conversion.ConversionArtifact;
import org.opoo.apps.conversion.ConversionArtifactType;
import org.opoo.apps.conversion.ConversionErrorStep;
import org.opoo.apps.conversion.ConversionRevision;
import org.opoo.apps.conversion.ConversionStorageService;
import org.opoo.apps.conversion.dao.ConversionDao;
import org.opoo.apps.conversion.model.ConversionArtifactImpl;
import org.opoo.apps.conversion.model.ConversionErrorStepImpl;
import org.opoo.apps.conversion.model.ConversionRevisionImpl;
import org.opoo.apps.id.IdGenerator;
import org.opoo.apps.id.IdGeneratorProvider;
import org.opoo.apps.transaction.TransactionUtils;
import org.opoo.cache.Cache;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.storage.Storage;
import org.springframework.beans.factory.annotation.Required;

import com.google.common.collect.Lists;

public class ConversionStorageServiceImpl implements ConversionStorageService {
	private static final Logger log = LogManager.getLogger(ConversionStorageServiceImpl.class);
	
	private ConversionDao conversionDao;
	private Cache<Long,ConversionRevision> conversionRevisionCache;
	private Cache<String,Long> conversionRevisionIdCache;
	private Cache<String,ConversionArtifact> conversionArtifactCache;
	private Cache<Long, List<ConversionErrorStep>> conversionErrorStepsCache;
	private IdGenerator<Long> revisionIdGenerator;
	private IdGenerator<Long> stepIdGenerator;
	private Storage storage;
	
	@Required
	public void setConversionErrorStepsCache(Cache<Long, List<ConversionErrorStep>> conversionErrorStepsCache) {
		this.conversionErrorStepsCache = conversionErrorStepsCache;
	}
	@Required
	public void setConversionDao(ConversionDao conversionDao) {
		this.conversionDao = conversionDao;
	}
	@Required
	public void setConversionArtifactCache(Cache<String, ConversionArtifact> conversionArtifactCache) {
		this.conversionArtifactCache = conversionArtifactCache;
	}
	@Required
	public void setConversionRevisionIdCache(
			Cache<String, Long> conversionRevisionIdCache) {
		this.conversionRevisionIdCache = conversionRevisionIdCache;
	}
	@Required
	public void setIdGeneratorProvider(IdGeneratorProvider<Long> provider){
		revisionIdGenerator = provider.getIdGenerator(ConversionRevision.class.getSimpleName());
		stepIdGenerator = provider.getIdGenerator(ConversionErrorStep.class.getSimpleName());
	}
	@Required
	public void setStorage(Storage storage){
		this.storage = storage;
	}
	@Required
	public void setConversionRevisionCache(
			Cache<Long, ConversionRevision> conversionRevisionCache) {
		this.conversionRevisionCache = conversionRevisionCache;
	}
	
	public void putRevisionIntoCache(ConversionRevision revision){
		conversionRevisionCache.put(revision.getRevisionId(), revision);
		String key = buildRevisionIdCacheKey(revision.getObjectType(), revision.getObjectId(), revision.getRevisionNumber());
		conversionRevisionIdCache.put(key, revision.getRevisionId());
	}
	public void removeRevisionFromCache(long revisionId){
		ConversionRevision revision = conversionRevisionCache.remove(revisionId);
		if(revision != null){
			conversionRevisionIdCache.remove(buildRevisionIdCacheKey(revision.getObjectType(), revision.getObjectId(), revision.getRevisionNumber()));
		}
	}
	
	public ConversionRevision getRevisionFromCache(long revisionId){
		return conversionRevisionCache.get(revisionId);
	}
	public ConversionRevision getRevisionFromCache(int objectType, long objectId, int revisionNumber){
		String key = buildRevisionIdCacheKey(objectType, objectId, revisionNumber);
		Long revId = conversionRevisionIdCache.get(key);
		if(revId != null){
			return getRevisionFromCache(revId.longValue());
		}
		return null;
	}
	private String buildRevisionIdCacheKey(int objectType, long objectId, int revisionNumber){
		return String.format("%s.%s.%s", objectType, objectId, revisionNumber);
	}
	
	public ConversionRevision getRevision(long revisionId) {
		ConversionRevision revision = getRevisionFromCache(revisionId);//conversionRevisionCache.get(revisionId);
        if (revision != null) {
            return revision;
        }
        revision = conversionDao.getRevision(revisionId);
        if (revision != null) {
        	//conversionRevisionCache.put(revisionId, revision);
        	putRevisionIntoCache(revision);
        }
        return revision; 
	}

	public ConversionRevision saveRevision(int objectType, long objectId,
			String filename, String realMimeType, long contentLength,
			int revisionNumber, int numberOfPages, String metadata) {
		ConversionRevisionImpl rev = new ConversionRevisionImpl();
		rev.setRevisionId(revisionIdGenerator.getNext());

		rev.setContentType(realMimeType);
		rev.setObjectId(objectId);
		rev.setObjectType(objectType);
		rev.setCreationDate(System.currentTimeMillis());
		rev.setFilename(filename);
		rev.setLength(contentLength);
		rev.setModificationDate(0);
		rev.setNumberOfPages(numberOfPages);
		rev.setRevisionNumber(revisionNumber);
		rev.setMetadata(metadata);
		
		ConversionRevision revision = conversionDao.saveRevision(rev);
		//conversionRevisionCache.put(rev.getRevisionId(), revision);
		putRevisionIntoCache(revision);
		return revision;
	}

	
	public ConversionRevision getRevision(int objectType, long objectId, int revisionNumber) {
		ConversionRevision revision = getRevisionFromCache(objectType, objectId, revisionNumber);
		if(revision == null){
			revision = conversionDao.getRevision(objectType, objectId, revisionNumber);
			if(revision != null){
				putRevisionIntoCache(revision);
			}
		}
		return revision;
		//return conversionDao.getRevision(objectType, objectId, revisionNumber);
	}

	
	
	public void updateRevision(ConversionRevision rev) {
		ConversionRevisionImpl revImpl = null;
		if(rev instanceof ConversionRevisionImpl){
			revImpl = (ConversionRevisionImpl) rev;
			if(revImpl.isModified()){
				revImpl = conversionDao.updateRevision(revImpl);
			}
		}else{
			revImpl = new ConversionRevisionImpl(rev);
			revImpl = conversionDao.updateRevision(revImpl);
		}
		if(revImpl != null){
			//conversionRevisionCache.put(revImpl.getRevisionId(), revImpl);
			putRevisionIntoCache(revImpl);
		}
	}

	
	public void deleteErrorSteps(ConversionRevision revision) {
		conversionDao.removeErrorStepsByRevisionId(revision.getRevisionId());
		conversionErrorStepsCache.remove(revision.getRevisionId());
	}
	
	public ConversionErrorStep saveErrorStep(long revisionId, ConversionArtifactType type,	String message) {
		// TODO: fix: for some reason the old DV schema only allows one error per step
		ConversionRevision revision = getRevision(revisionId);
        if (getErrorSteps(revision).size() > 0) {
            return null;
        }

        this.conversionErrorStepsCache.remove(revisionId);
        
        ConversionErrorStepImpl step = new ConversionErrorStepImpl();
        step.setId(stepIdGenerator.getNext());
        step.setRevisionId(revisionId);
        step.setStep(type.getName());
        step.setMessage(message);
        step.setModificationDate(System.currentTimeMillis());
        return conversionDao.saveErrorStep(step);           
	}
	
    public List<ConversionErrorStep> getErrorSteps(ConversionRevision rev) {
        List<ConversionErrorStep> steps = conversionErrorStepsCache.get(rev.getRevisionId());
        if (steps == null) {
        	List<ConversionErrorStepImpl> list = conversionDao.findErrorStepsByRevisionId(rev.getRevisionId());
        	if(list == null){
        		list = Lists.newArrayList();
        	}
            steps = new ArrayList<ConversionErrorStep>(list);
            
            // cache even empty lists for negative cache
            conversionErrorStepsCache.put(rev.getRevisionId(), steps);
        }
        return steps;
    }
	
	public ConversionArtifact getArtifact(ConversionRevision revision, ConversionArtifactType type, int page) {
		if(revision == null){
			return null;
		}
		String key = new ConversionArtifactImpl(revision.getRevisionId(), type, page).getStorageKey();
		
		ConversionArtifact artifact = conversionArtifactCache.get(key);
		if(artifact == null){
			artifact = conversionDao.getArtifact(revision.getRevisionId(), type, page);
			if(artifact != null){
				conversionArtifactCache.put(key, artifact);
			}
		}
		return artifact;
	}

	
	public ConversionArtifact saveArtifact(long revisionId, ConversionArtifactType type, int page, String filename,
			String contentType, long length, File file) throws Exception{
		
		ConversionArtifactImpl a = new ConversionArtifactImpl();
		a.setRevisionId(revisionId);
		a.setType(type);
		a.setPage(page);
		a.setFilename(filename);
		a.setContentType(contentType);
		a.setLength(length);
		
		boolean put = false;

//        ConversionArtifact originalCA = new ConversionArtifactImpl(revisionId, type, page);
        final String caKey = a.getStorageKey();
        
        if(!storage.containsKey(caKey)){
        	// keep the ca and its binary body in sync
            TransactionUtils.onTransactionRollback(new Runnable() {
                public void run() {
                    if (storage.containsKey(caKey)) {
                    	storage.delete(caKey);
                    }
                }
            });
        	
        	InputStream is = null;
        	try{
        		is = FileUtils.openInputStream(file);
        		put = storage.put(caKey, is);
        	}finally{
        		IOUtils.closeQuietly(is);
        	}
		} else {
			log.info("A duplicate artifact generated and descarted "
					+ a.getStorageKey());
		}

//        if (!storageProvider.containsKey(caKey)) {
//            // keep the ca and its binary body in sync
//            TransactionUtil.onTransactionRollback(new Runnable() {
//                public void run() {
//
//                    if (storageProvider.containsKey(caKey)) {
//                        storageProvider.delete(caKey);
//                    }
//                }
//            });
//            
//            put = storageProvider.put(caKey, newFileInputStream(file));
//        }
//        else {
//            log.info("A duplicate artifact generated and descarted " + ca.getStorageKey());
//        }

        if (put) {
            a = conversionDao.saveArtifact(a);
            conversionArtifactCache.put(caKey, a);
        }
        else {
            return null;
        }

        return a;
	}

	
	public InputStream getArtifactStream(ConversionArtifact a) {
		String key = a.getStorageKey();
		if(storage.containsKey(key)){
			return storage.getStream(key);
		}
//		 final String caKey = ca.getStorageKey();
//	        if (storageProvider.containsKey(caKey)) {
//	            return storageProvider.getStream(ca.getStorageKey());
//	        }
//	        return null;
		
		return null;
	}

	
	public int getArtifactCount(ConversionRevision revision,ConversionArtifactType type) {
		return conversionDao.getArtifactCount(revision.getRevisionId(), type);
	}
	
	public void deleteAll(ConversionRevision rev) {
		long revisionId = rev.getRevisionId();
		
		//delete error steps for revision
		conversionDao.removeErrorStepsByRevisionId(revisionId);
        conversionErrorStepsCache.remove(revisionId);

        //find conversion artifacts for revision
        final List<ConversionArtifact> cas = Lists.newLinkedList();
        List<ConversionArtifactImpl> list = conversionDao.findArtifactsByRevisionId(revisionId);
        for(ConversionArtifactImpl ca: list){
        	cas.add(ca);
        	conversionArtifactCache.remove(ca.getStorageKey());
        }
        
        //delete all artifactts of revision
        conversionDao.removeArtifactsByRevisionId(revisionId);
        
        conversionDao.removeRevision(revisionId);
        //conversionRevisionCache.remove(revisionId);
        removeRevisionFromCache(revisionId);

        // keep the ca and its binary body in sync
        TransactionUtils.onTransactionCommit(new Runnable() {
            public void run() {
            	// cleanup the bin store bodies last as well as cache
            	for (ConversionArtifact ca : cas) {
                   String caKey = ca.getStorageKey();
                   if (storage.containsKey(caKey)) {
                	   log.debug(String.format("Deleting '%s' from storage %s.", caKey, storage));
                	   storage.delete(caKey);
                   }
               }
            }
        });
	}

	public int getRevisionCount() {
		return conversionDao.getRevisionCount();
	}
	public int getErrorRevisionCount() {
		return conversionDao.getErrorRevisionCount();
	}
	public List<Long> getErrorRevisionIds(ResultFilter filter) {
		return conversionDao.findErrorRevisionIds(filter);
	}
}
