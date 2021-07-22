package org.opoo.apps.conversion.v2;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.opoo.apps.conversion.ConversionArtifact;
import org.opoo.apps.conversion.ConversionArtifactType;
import org.opoo.apps.conversion.ConversionManager;
import org.opoo.apps.conversion.ConversionRevision;
import org.opoo.apps.conversion.ConversionStatus;
import org.opoo.apps.conversion.ConversionStorageService;
import org.opoo.apps.conversion.ConvertableObject;
import org.opoo.apps.conversion.model.ConversionArtifactImpl;
import org.opoo.apps.conversion.util.ConversionUtils;
import org.opoo.apps.conversion.util.FileFormats;
import org.opoo.apps.conversion.v2.ConversionEvent.Context;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.cache.Cache;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.factory.annotation.Required;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class ConversionManagerImplV2 implements ConversionManager{
	private static final Logger log = Logger.getLogger(ConversionManagerImplV2.class);
	
	private ConversionStorageService conversionStorageService;
	private List<ConversionProvider> providers = Lists.newArrayList();
	Cache<Long, ProcessingInfo> processCache;
	
	@Required
	public void setConversionStorageService(
			ConversionStorageService conversionStorageService) {
		this.conversionStorageService = conversionStorageService;
	}
	@Required
	public void setConversionProviders(List<ConversionProvider> providers) {
		this.providers = providers;
	}

	@Required
	public void setProcessCache(Cache<Long, ProcessingInfo> processCache) {
		this.processCache = processCache;
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

	private List<String> getDisabledExtensions() {
		// TODO Auto-generated method stub
		return Lists.newArrayList();
	}

	public boolean isConversionEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	
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
        File file = null;
        long revisionId = -1;
        ConversionRevision revision = null;
        try{
        	revision = conversionStorageService.getRevision(object.getObjectType(), object.getObjectId(), object.getRevisionNumber());
        	if(revision == null){
//        		int objectType, long objectId, 
//    			String filename, String realMimeType, long contentLength, 
//    			int revisionNumber,
//    			int numberOfPages, String metadata
        		log.debug("create revision " + object.getObjectId());
        		revision = conversionStorageService.saveRevision(object.getObjectType(), object.getObjectId(),
        				object.getFilename(), object.getContentType(), object.getContentLength(),
        				object.getRevisionNumber(),
        				0, null);
        	}
        	
        	revisionId = revision.getRevisionId();
        	ProcessingInfo info = processCache.get(revisionId);
        	if(info != null){
        		log.info(String.format("Conversion for %s already running.", revision.getFilename()));
                return true;
        	}
        	
        	// create a new info and figure out exactly what needs to be generated
            // and what's generated so far so no need to go to DB anymore
        	info = currentProvider.createProcessingInfo();//new ProcessingInfo(currentProvider.getName());
        	processCache.put(revisionId, info);

            // file with the required artifact data
            //caFile = FileUtils.createTempFile(cm);

            // delete the steps as errors since starting over
            //conversionStorageService.deleteSteps(revision);
             //return currentProvider.convert(revision, object.getStreamData());
        	
        	conversionStorageService.deleteErrorSteps(revision);
        	
        	file = ConversionUtils.createTempFile(revision);
        	ConversionUtils.writeToFile(file, object.getStreamData());
        	
        	log.debug("创建原始文件：" + file);
        	
//        	currentProvider.startConvert(info, revision, file);
        	currentProvider.handleFileGenerated(info, revision, BaseConversionArtifactType.File, 0, file);
        	return true;
        }catch(Exception e){
        	log.error(String.format("Error starting converting %s", object), e);            
            // kill it
            processCache.remove(revisionId);
            return false;
        }finally{
        	FileUtils.deleteQuietly(file);
        }
	}
	
	/**
	 * 某种文件处理完成时调用的处理函数。
	 * @param event
	 */
	protected void handleFileGenerated(ConversionEvent event){
		Context context = event.getSource();
		ConversionRevision rev = context.getRevision();
		ConversionArtifactType type = context.getType();
		int page = context.getPage();
		File file = context.getGeneratedFile();
		try{
			ProcessingInfo info = processCache.get(rev.getRevisionId());
			if(!ConversionUtils.isValid(file)){
				String message = String.format("The conversion failed to generate a %s file file %s for page %d.", type, rev.getFilename(),page);
				log.warn(message);
				
				if(info != null){
					//记录某部件已经转换完成
					info.recordArtifactGenerated(rev.getRevisionId(), type, page);
					//检查是否所有的都已经完成
					getConversionProvider(info).checkProcessingCompletion(info, rev, type, page);
				}
				
				//处理错误
				event.getSource().setMessage(message);
				handleError(event);
				return ;
			}
			
			//Save
			ConversionArtifact ca = null;
			if(context.isNeedToSave()){
				ca = conversionStorageService.saveArtifact(rev.getRevisionId(), type, page, null, type.getContentType(), file.length(), file);
			}
			
//			if(rev.getNumberOfPages() <= 0){
//				rev.setNumberOfPages(FileFormats.getNumberOfPages(pdf));
//				conversionStorageService.updateRevision(rev);
//			}
			
			if (info == null) {
                String message = String.format("The conversion stopped for file %s possible because the allocated time expired. ", rev.getFilename());
                log.warn(message);
                return;
            }
			
			if(context.isNeedToSave() && ca != null){
				info.recordArtifactGenerated(ca);
			}else{
				info.recordArtifactGenerated(rev.getRevisionId(), type, page);
			}

			
			//ConversionProvider conversionProvider = getConversionProvider(info.getProviderName());
			ConversionProvider conversionProvider = getConversionProvider(info);
			conversionProvider.handleFileGenerated(info, rev, type, page, file);
		}catch(Exception ex){
			 log.error("Error processing PDF generated", ex);
			 event.getSource().setMessage(ex.getMessage());
	         handleError(event);
		}finally{
			//原始文件没用了
			FileUtils.deleteQuietly(file);
		}
	}
	
	/**
	 * 在info中查找provider。
	 * @param info
	 * @return
	 */
	protected ConversionProvider getConversionProvider(ProcessingInfo info){
		if(info.conversionProvider == null){
			String providerName = info.getProviderName();
			for(ConversionProvider p: providers){
				if(p.getName().equals(providerName)){
					info.conversionProvider = p;
					break;
				}
			}
			if(info.conversionProvider == null){
				throw new RuntimeException("Cannot find exactly ConversionProvider '" + providerName + "'.");
			}
		}
		
		return info.conversionProvider;
	}
	
	protected void handleCompletion(ConversionEvent event){
		Context source = event.getSource();
		ConversionRevision revision = source.getRevision();
		ProcessingInfo info = processCache.get(revision.getRevisionId());
		if(info != null){
			processCache.remove(revision.getRevisionId());
			log.info("All conversion artifacts for " + revision.getFilename() + " generated in "
					+ (System.currentTimeMillis() - info.getStartedProcessingMillis()) + " millis.");
		}
	}
	
	
	private void handleError(ConversionEvent event) {
		System.err.println(event.getSource().getMessage());
		Context context = event.getSource();
		ConversionRevision revision = context.getRevision();
		conversionStorageService.saveErrorStep(revision.getRevisionId(), context.getType(), context.getMessage());
	}

	public ConversionRevision getConversionRevision(long revisionId) {
		return conversionStorageService.getRevision(revisionId);
	}

	public ConversionRevision getConversionRevision(ConvertableObject object) {
		return conversionStorageService.getRevision(object.getObjectType(), object.getObjectId(), object.getRevisionNumber());
	}

	public ConversionRevision getConversionRevision(int objectType,	long objectId, int revisionNumber) {
		return conversionStorageService.getRevision(objectType, objectId, revisionNumber);
	}

	public ConversionArtifact getConversionArtifact(ConversionRevision rev,	ConversionArtifactType type, int page) {
		return conversionStorageService.getArtifact(rev, type, page);
	}

	public InputStream getConversionArtifactData(ConversionArtifact ca)	throws Exception {
		return conversionStorageService.getArtifactStream(ca);
	}

	public ConversionStatus getConversionStatus(ConversionRevision rev) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ConversionStatus> getConversionStatuses() {
		// TODO Auto-generated method stub
		return null;
	}

	public ConvertableObject getConvertableObject(ConversionRevision rev) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ConversionStatus> getErrorStatuses() {
		// TODO Auto-generated method stub
		return null;
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

	public void deleteAll(ConversionRevision rev) {
		conversionStorageService.deleteAll(rev);
	}

	public int getErrorConversionRevisionCount() {
		return conversionStorageService.getErrorRevisionCount();
	}

	public int getConversionRevisionCount() {
		return conversionStorageService.getRevisionCount();
	}

	public List<ConversionStatus> getConversionErrorStatuses(ResultFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * 转换处理过程信息。有可能存在于集群缓存中。 如果在集群缓存中，必须处理序列化问题，例如provider不能序列化。
	 * 
	 */
	public static class ProcessingInfo implements Serializable {
		private static final long serialVersionUID = -9165122031764981889L;
		//dont't serialize this field
		private transient ConversionProvider conversionProvider;
		
		private String providerName;

		private long lastProcessedMillis;
		private long startedProcessingMillis;
		private final Set<ConversionArtifact> scheduledArtifacts = Sets.newLinkedHashSet();
		private final Set<ConversionArtifact> generatedArtifacts = Sets.newLinkedHashSet();
		private int errorCount = 0;

		public ProcessingInfo(ConversionProvider provider) {
			this.conversionProvider = provider;
			this.providerName = provider.getName();
			startedProcessingMillis = lastProcessedMillis = System.currentTimeMillis();
		}

//		public ConversionProvider getProvider(
//				ConversionManagerImplV2 conversionManagerImpl) {
//			if (provider == null) {
//				for (ConversionProvider p : conversionManagerImpl.providers) {
//					if (providerName.equals(p.getName())) {
//						provider = p;
//						break;
//					}
//				}
//			}
//			return provider;
//		}

		public String getProviderName() {
			return providerName;
		}


		private synchronized void addScheduledArtifact(ConversionArtifact ca) {
			scheduledArtifacts.add(ca);
		}
		
		public void addScheduledArtifact(long revisionId, ConversionArtifactType type, int page){
			addScheduledArtifact(new ConversionArtifactImpl(revisionId, type, page));
		}

		public synchronized int getTotalScheduledArtifacts() {
			return scheduledArtifacts.size();
		}

		public synchronized void recordArtifactGenerated(ConversionArtifact ca) {
			lastProcessedMillis = System.currentTimeMillis();
			scheduledArtifacts.remove(ca);
			generatedArtifacts.add(ca);
		}
		public void recordArtifactGenerated(long revisionId, ConversionArtifactType type, int page){
			recordArtifactGenerated(new ConversionArtifactImpl(revisionId, type, page));
		}

		public synchronized void recordError() {
			errorCount++;
		}

		public synchronized int getErrorCount() {
			return errorCount;
		}

		public synchronized Set<ConversionArtifact> getGeneratedArtifacts(ConversionArtifactType type) {
			return getActifactsByType(generatedArtifacts, type);
		}

		public synchronized Set<ConversionArtifact> getScheduledArtifacts() {
			return Sets.newLinkedHashSet(scheduledArtifacts);
		}

		public synchronized Set<ConversionArtifact> getScheduledArtifacts(ConversionArtifactType type) {
			return getActifactsByType(scheduledArtifacts, type);
		}

		public synchronized boolean isGenerated(ConversionArtifact ca) {
			return generatedArtifacts.contains(ca);
		}

		public synchronized long getLastProcessedMillis() {
			return lastProcessedMillis;
		}

		public long getStartedProcessingMillis() {
			return startedProcessingMillis;
		}

		protected Set<ConversionArtifact> getActifactsByType(Set<ConversionArtifact> artifacts, ConversionArtifactType type) {
			Set<ConversionArtifact> cas = Sets.newLinkedHashSet();
			for (ConversionArtifact ca : artifacts) {
				if (ca.getType() == type) {
					cas.add(ca);
				}
			}
			return cas;
		}
	}
	
	public static class ConversionEventListener implements EventListener<ConversionEvent>{
		
		private ConversionManagerImplV2 conversionManagerImpl;
		
		@Required
		public void setConversionManagerImpl(ConversionManagerImplV2 conversionManagerImpl) {
			this.conversionManagerImpl = conversionManagerImpl;
		}

		public void handle(ConversionEvent event) {
			switch(event.getType()){
			case Completion:
				conversionManagerImpl.handleCompletion(event);
				break;
			case Error:
				conversionManagerImpl.handleError(event);
				break;
			case FileGenerated:
				conversionManagerImpl.handleFileGenerated(event);
				break;
			}
		}
	}
}
