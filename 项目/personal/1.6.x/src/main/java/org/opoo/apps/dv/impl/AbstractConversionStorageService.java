package org.opoo.apps.dv.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.opoo.apps.dv.ConversionArtifact;
import org.opoo.apps.dv.ConversionArtifactType;
import org.opoo.apps.dv.ConversionMetaData;
import org.opoo.apps.dv.ConversionStorageService;
import org.opoo.apps.dv.dao.ConversionDao;
import org.opoo.apps.transaction.TransactionUtils;
import org.opoo.apps.util.FileUtils;
import org.opoo.cache.Cache;
import org.opoo.storage.Storage;
import org.springframework.beans.factory.annotation.Required;

public abstract class AbstractConversionStorageService<M extends ConversionMetaData, A extends ConversionArtifact<T>, T extends ConversionArtifactType, D extends ConversionDao<M,A,T>>
		implements ConversionStorageService<M, A, T> {
	protected final Logger log = Logger.getLogger(getClass());
	
	protected Cache<Long, M> conversionMetaDataCache;
	protected Cache<String, Long> conversionMetaDataIDCache;
	protected Cache<String, A> conversionArtifactCache;
	protected Cache<ConversionArtifactCacheKey, Integer> conversionArtifactCountCache;
	protected Storage storage;
	protected D conversionDao;
	
	@Required
	public void setConversionMetaDataCache(Cache<Long, M> conversionMetaDataCache) {
		this.conversionMetaDataCache = conversionMetaDataCache;
	}

	@Required
	public void setConversionMetaDataIDCache(
			Cache<String, Long> conversionMetaDataIDCache) {
		this.conversionMetaDataIDCache = conversionMetaDataIDCache;
	}

	@Required
	public void setConversionArtifactCache(Cache<String, A> conversionArtifactCache) {
		this.conversionArtifactCache = conversionArtifactCache;
	}

	@Required
	public void setConversionArtifactCountCache(
			Cache<ConversionArtifactCacheKey, Integer> conversionArtifactCountCache) {
		this.conversionArtifactCountCache = conversionArtifactCountCache;
	}

	@Required
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	
	@Required
	public void setConversionDao(D conversionDao) {
		this.conversionDao = conversionDao;
	}

	
	public final M getMetaData(long id) {
		M m = conversionMetaDataCache.get(id);
		if(m == null){
			m = conversionDao.getMetaData(id);
			if(m != null){
				putIntoCache(m);
			}
		}
		return m;
	}

	protected void putIntoCache(M m) {
		conversionMetaDataCache.put(m.getId(), m);
		String key = buildMetaDataIDCacheKey(m.getConvertableObjectType(), m.getConvertableObjectId(), m.getRevisionNumber());
		conversionMetaDataIDCache.put(key, m.getId());
	}
	
	protected String buildMetaDataIDCacheKey(int type, long id, int version){
		return String.format("t%s_i%s_v%s", type, id, version);
	}
	
	public final M getMetaData(int convertableObjectType, long convertableObjectId, int version) {
		long id = getMetaDataID(convertableObjectType, convertableObjectId, version);
		if(id != -1L){
			return getMetaData(id);
		}
		return null;
	}
	
	/**
	 * 查询对象对应的元数据ID。
	 * @param objectType
	 * @param objectId
	 * @param version
	 * @return
	 */
	public final long getMetaDataID(int objectType, long objectId, int version){
		String key = buildMetaDataIDCacheKey(objectType, objectId, version);
		Long id = conversionMetaDataIDCache.get(key);
		if(id == null){
			id = conversionDao.getMetaDataID(objectType, objectId, version);
			if(id != null){
				conversionMetaDataIDCache.put(key, id);
			}
		}
		return id != null ? id.longValue() : -1L;
	}

	public final int getMetaDataCount() {
		return conversionDao.getMetaDataCount();
	}

	public final int getMetaDataErrorCount(String filter) {
		return conversionDao.getMetaDataErrorCount(filter);
	}
	
	protected abstract A buildArtifact(M cm, T type, int partNumber);
	
	protected ConversionArtifactCacheKey buildConversionMetaDataIDCacheKey(long cmID, T type){
		return new ConversionArtifactCacheKey(cmID, type);
	}
	
	public final A getArtifact(M cm, T type, int partNumber) {
		if(cm == null){
			return null;
		}
		String key = buildArtifact(cm, type, partNumber).getStorageKey();
		A ca = conversionArtifactCache.get(key);
		if(ca == null){
			ca = conversionDao.getArtifact(cm.getId(), type, partNumber);
			if(ca != null){
				conversionArtifactCache.put(key, ca);
			}
		}
		return ca;
	}

	public final InputStream getArtifactBody(A artifact) throws IOException {
		/*
		final String storageKey = artifact.getStorageKey();
		if(storage.containsKey(storageKey)){
			return storage.getStream(storageKey);
		}
		return null;
		*/
		return getArtifactFile(artifact);
	}
	
	protected InputStream getArtifactFile(A artifact) throws IOException {
		final String storageKey = buildStorageKey(artifact);//artifact.getStorageKey();
		if(storage.containsKey(storageKey)){
			return storage.getStream(storageKey);
		}
		return null;
	}
	
	protected String buildStorageKey(A artifact){
		return artifact.getStorageKey();
	}

	public final int getArtifactCount(M cm, T type) {
		ConversionArtifactCacheKey key = buildConversionMetaDataIDCacheKey(cm.getId(), type);
		Integer count = conversionArtifactCountCache.get(key);
		if(count == null){
			count = conversionDao.getArtifactCount(cm.getId(), type);
			conversionArtifactCountCache.put(key, count);
		}
		return count;
	}

	
	public final void deleteAll(M cm) {
		final List<A> list = conversionDao.findArtifacts(cm.getId());
		conversionDao.removeAllByMetaDataID(cm.getId());
		
		String idCacheKey = buildMetaDataIDCacheKey(cm.getConvertableObjectType(), cm.getConvertableObjectId(), cm.getRevisionNumber());
		conversionMetaDataIDCache.remove(idCacheKey);
		conversionMetaDataCache.remove(cm.getId());
		conversionArtifactCache.clear();
		conversionArtifactCountCache.clear();
		
		TransactionUtils.onTransactionCommit(new Runnable() {
            public void run() {
                // cleanup the bin store bodies last as well as cache
               for (A ca : list) {
                   String caKey = buildStorageKey(ca);// ca.getStorageKey();
                   if (storage.containsKey(caKey)) {
                	   storage.delete(caKey);
                   }
               }
            }
        });
	}
	
	public M saveMetaData(M meta) {
		M data = conversionDao.saveMetaData(meta);
		if(data != null){
			putIntoCache(data);
		}
		return data;
	}

	public M updateMetaData(M meta) {
		M data = conversionDao.updateMetaData(meta);
		if(data != null){
			putIntoCache(data);
		}
		return data;
	}

	public final A saveArtifact(A ca, File file) throws Exception {
		boolean put = saveArtifactFile(ca, file);
		 if(put){
			 A artifact = conversionDao.saveArtifact(ca);
			 conversionArtifactCache.put(ca.getStorageKey(), ca);
			 conversionArtifactCountCache.remove(buildConversionMetaDataIDCacheKey(ca.getConversionMetadataID(), ca.getType()));
			 return artifact;
		 }else{
			 return null;
		 }
	}
	
	protected boolean saveArtifactFile(A ca, File file) throws IOException{
		boolean put = false;
		final String storageKey = buildStorageKey(ca);//ca.getStorageKey();
		if (!storage.containsKey(storageKey)) {
			// keep the ca and its binary body in sync
			TransactionUtils.onTransactionRollback(new Runnable() {
				public void run() {

					if (storage.containsKey(storageKey)) {
						storage.delete(storageKey);
					}
				}
			});

			put = storage.put(storageKey, newFileInputStream(file));
		} else {
			log.info("A duplicate artifact generated and descarted " + storageKey/* ca.getStorageKey()*/);
		}
		return put;
	}
	
	
	InputStream newFileInputStream(File file) throws FileNotFoundException {
        return FileUtils.newFileInputStream(file);
    }
	

	/**
     * Caching key to avoid the count query
     */
    public static class ConversionArtifactCacheKey implements Serializable {
		private static final long serialVersionUID = -8242343087204991617L;
		private Long conversionMetadataID;
        private ConversionArtifactType type;

        // required for caching
        public ConversionArtifactCacheKey() {

        }

        public ConversionArtifactCacheKey(Long conversionMetadataID, ConversionArtifactType type) {
            this.conversionMetadataID = conversionMetadataID;
            this.type = type;
        }

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			ConversionArtifactCacheKey that = (ConversionArtifactCacheKey) o;

			if (conversionMetadataID != null ? !conversionMetadataID
					.equals(that.conversionMetadataID)
					: that.conversionMetadataID != null) {
				return false;
			}
			if (type != that.type) {
				return false;
			}

			return true;
		}

        @Override
        public int hashCode() {
            int result = conversionMetadataID != null ? conversionMetadataID.hashCode() : 0;
            result = 31 * result + (type != null ? type.hashCode() : 0);
            return result;
        }
    }
}
