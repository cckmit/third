package org.opoo.apps.conversion.doc;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.opoo.apps.conversion.ConversionArtifact;
import org.opoo.apps.conversion.ConversionArtifactType;
import org.opoo.apps.conversion.ConversionProvider;
import org.opoo.apps.conversion.ConversionRevision;
import org.opoo.apps.conversion.ConversionStorageService;
import org.opoo.apps.conversion.ConvertableObject;
import org.opoo.apps.conversion.doc.DocConversionExecutorEvent.Payload;
import org.opoo.apps.conversion.event.ConversionEvent;
import org.opoo.apps.conversion.model.ConversionArtifactImpl;
import org.opoo.apps.conversion.util.ConversionUtils;
import org.opoo.apps.conversion.util.FileFormats;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.cache.Cache;
import org.springframework.beans.factory.annotation.Required;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class DocConversionProvider implements ConversionProvider {
	public static class ArtifactKey implements Serializable{
		private static final long serialVersionUID = -6448386845096022915L;
		private final long revisionId;
		private final ConversionArtifactType type;
		private final int page;
		public ArtifactKey(long revisionId, ConversionArtifactType type, int page) {
			super();
			this.revisionId = revisionId;
			this.type = type;
			this.page = page;
		}
		public ArtifactKey(ConversionArtifact a){
			this.revisionId = a.getRevisionId();
			this.type = a.getType();
			this.page = a.getPage();
		}
		public long getRevisionId() {
			return revisionId;
		}
		public ConversionArtifactType getType() {
			return type;
		}
		public int getPage() {
			return page;
		}
		
		
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }

	        ArtifactKey that = (ArtifactKey) o;

	        if (page != that.page) {
	            return false;
	        }
	        if (revisionId != that.revisionId) {
	            return false;
	        }
//	        if (instanceID != null ? !instanceID.equals(that.instanceID) : that.instanceID != null) {
//	            return false;
//	        }
	        if (type != that.type) {
	            return false;
	        }

	        return true;
	    }

	    
	    public int hashCode() {
	        int result = type != null ? type.hashCode() : 0;
	        //result = 31 * result + (instanceID != null ? instanceID.hashCode() : 0);
	        result = 31 * result + (int) (revisionId ^ (revisionId >>> 32));
	        result = 31 * result + page;
	        return result;
	    }

	    
	    public String toString() {
//	        return "ConversionArtifact{" + "type=" + type + ", instanceID='" + instanceID + '\'' + ", revisionID="
//	                + revisionId + ", page=" + page + ", filename='" + filename + '\'' + ", contentType='" + contentType
//	                + '\'' + ", length=" + length + '}';
	    	return "ConversionArtifact.Key{type=" + type + ", revisionId=" + revisionId + ", page=" + page + "}";
	    }
	    
	    public String toStorageKey() {
			//return String.format("i%s_r%d_t%s_p%d", "", revisionId, type.getName(), page);
	    	return new ConversionArtifactImpl(revisionId, type, page).getStorageKey();
//	    	return ConversionArtifactImpl.buildStorageKey(revisionId, type.getName(), page);
		}
	}
	public static class ConversionProcessInfo implements Serializable {
		private static final long serialVersionUID = -3538577977432156810L;
		private long lastProcessedMillis;
        private long startedProcessingMillis;
        private final Set<ArtifactKey> scheduledArtifacts = Sets.newLinkedHashSet();
        private final Set<ArtifactKey> generatedArtifacts = Sets.newLinkedHashSet();
        private int errorCount = 0;

        public ConversionProcessInfo() {
            startedProcessingMillis = lastProcessedMillis = System.currentTimeMillis();
        }
        
        public synchronized void addScheduledArtifact(long revisionId, ConversionArtifactType type, int page) {
        	scheduledArtifacts.add(new ArtifactKey(revisionId, type, page));
        }

        public synchronized int getTotalScheduledArtifacts() {
            return scheduledArtifacts.size();
        }
        private ArtifactKey buildCachedArtifact(ConversionArtifact ca){
        		return new ArtifactKey(ca);
        }
        public synchronized void recordArtifactGenerated(ConversionArtifact ca) {
        	ArtifactKey key = buildCachedArtifact(ca);
        	recordArtifactGenerated(key);
        }
        public synchronized void recordArtifactGenerated(long revisionId, ConversionArtifactType type, int page) {
        	ArtifactKey key = new ArtifactKey(revisionId, type, page);
        	recordArtifactGenerated(key);
        }
        private synchronized void recordArtifactGenerated(ArtifactKey key){
        	lastProcessedMillis = System.currentTimeMillis();
            scheduledArtifacts.remove(key);
            generatedArtifacts.add(key);
        }

        public synchronized void recordError() {
            errorCount++;
        }

        public synchronized int getErrorCount() {
            return errorCount;    
        }

        public synchronized Set<ArtifactKey> getGeneratedArtifacts(ConversionArtifactType type) {
            return getActifactsByType(generatedArtifacts, type);                      
        }

        public synchronized Set<ArtifactKey> getScheduledArtifacts() {
            return Sets.newLinkedHashSet(scheduledArtifacts);
        }

        public synchronized Set<ArtifactKey> getScheduledArtifacts(ConversionArtifactType type) {
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

        protected Set<ArtifactKey> getActifactsByType(Set<ArtifactKey> artifacts, ConversionArtifactType type) {
            Set<ArtifactKey> cas = Sets.newLinkedHashSet();

            for (ArtifactKey ca : artifacts) {
                if (ca.getType() == type) {
                    cas.add(ca);
                }
            }
            return cas;
        }
    }
	
	private static final Logger log = Logger.getLogger(DocConversionProvider.class);
	private EventDispatcher dispatcher;
	private Cache<Long, ConversionProcessInfo> processCache;
	private ConversionStorageService conversionStorageService;
	private DocConversionExecutorFactory conversionExecutorFactory;
	private Executor executorService;

	@Required
	public void setDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	@Required
	public void setProcessCache(Cache<Long, ConversionProcessInfo> processCache) {
		this.processCache = processCache;
	}
	@Required
	public void setConversionStorageService(
			ConversionStorageService conversionStorageService) {
		this.conversionStorageService = conversionStorageService;
	}
	@Required
	public void setConversionExecutorFactory(
			DocConversionExecutorFactory conversionExecutorFactory) {
		this.conversionExecutorFactory = conversionExecutorFactory;
	}
	@Required
	public void setExecutorService(Executor executorService) {
		this.executorService = executorService;
	}

	
	public boolean convert(ConversionRevision revision, InputStream originalFile) throws Exception {
        if (originalFile == null) {
            log.error("Can't request conversion for a null binary");
            return false;
        }

        if (revision == null) {
        	log.error("Can't request conversion for a null revision");
            return false;
        }
        
//        if(!isConvertable(co, revision.getConvertableContentType())){
//        	return false;
//        }
        if(!isConvertableContentType(revision.getContentType())){
        	
        }

        if (!isConversionEnabled()) {
            return false;
        }
		
        
        ConversionProcessInfo info = processCache.get(revision.getRevisionId());

        // distibuted lock for the next 4 lines on the cmID to make it more robust???
        if (info != null) {
            log.info(String.format("Conversion for %s already running.", revision.getFilename()));
            return true;
        }

        // create a new info and figure out exactly what needs to be generated
        // and what's generated so far so no need to go to DB anymore
        info = new ConversionProcessInfo();
        long revisionId = revision.getRevisionId();
        processCache.put(revisionId, info);
        
        conversionStorageService.deleteErrorSteps(revision);
        
        //File file = File.createTempFile("DOC-" + revisionId, ".tmp");
        try{
        	boolean isPDF = FileFormats.DocumentType.isPDFFileType(revision.getFilename());
        	File file = ConversionUtils.createTempFile(revision);
        	ConversionUtils.writeToFile(file, originalFile);
        	
        	//如果不是pdf
        	if(!isPDF){
//        		conversionStorageService.createNewArtifact(revisionId, type, length, filename, contentType, page, file)
        		//ConversionContext context = new ConversionContext(revision, file, null);
        		//info.addScheduledArtifact(new DocumentArtifact(revisionId, DocumentConversionArtifactType.Pdf, 0));
        		//callExecutor(DocumentConversionArtifactType.Pdf, context);
        		requestPDFGeneration(info, revision, file);
        	}else{
        		
        		//ConversionResultEvent event = new ConversionResultEvent(ConversionResultEvent.Type.PdfGenerated, result);
        		//dispatcher.fire(event);
        		if(revision.getNumberOfPages() <= 0){
        			revision.setNumberOfPages(FileFormats.getNumberOfPages(file));
    				conversionStorageService.updateRevision(revision);
    			}
        		
        		//ConversionContext context = new ConversionContext(revision, null, file);
        		requestFinalGeneration(info, revision, file);
        	}
        }catch(Exception e){
        	throw e;
        }finally{
//        	FileUtils.deleteQuietly(file);
        }
		return true;
	}
	
	
	private void requestPDFGeneration(ConversionProcessInfo info, ConversionRevision rev, File file) {
		info.addScheduledArtifact(rev.getRevisionId(), DocConversionArtifactType.Pdf, 0);
		DocConversionContext context = new DocConversionContext(rev, file, null);
		Runnable task = buildExecutorTask(context, DocConversionArtifactType.Pdf);
		executorService.execute(task);
	}
	
	protected void handlePdfGenerated(DocConversionExecutorEvent event){
		Payload payload = event.getSource();
		ConversionRevision rev = payload.getRevision();
		File pdf = payload.getGeneratedFile();
		try{
			ConversionProcessInfo info = processCache.get(rev.getRevisionId());
			if(!ConversionUtils.isValid(pdf)){
				String message = String.format("The conversion failed to generate a PDF file file %s for page %d. ", rev.getFilename(), payload.getPage());
				log.warn(message);
				
				if(info != null){
					info.recordArtifactGenerated(rev.getRevisionId(), payload.getType(), 0);
				}
				
				handleError(payload, DocConversionArtifactType.Pdf, message);
				return ;
			}
			
			ConversionArtifact pdfCA = conversionStorageService.saveArtifact(rev.getRevisionId(), DocConversionArtifactType.Pdf, 0, null,
					DocConversionArtifactType.Pdf.getContentType(), pdf.length(), pdf);//conversionStorageService.createArtifact(artifactKey,	(String)null, pdf.length(), pdf);
			
			if(rev.getNumberOfPages() <= 0){
				rev.setNumberOfPages(FileFormats.getNumberOfPages(pdf));
				conversionStorageService.updateRevision(rev);
			}
			
			if (info == null) {
                String message = String.format("The conversion stopped for file %s possible because the allocated time expired. ", rev.getFilename());
                log.warn(message);
                return;
            }
			
			info.recordArtifactGenerated(pdfCA);
			
			requestFinalGeneration(info, rev, pdf);
		}catch(Exception ex){
			 log.error("Error processing PDF generated", ex);
	         handleError(payload, payload.getType(), ex.getMessage());
		}finally{
			//原始文件没用了
			FileUtils.deleteQuietly(payload.getFile());
		}
	}
	
	private void requestFinalGeneration(ConversionProcessInfo info,	ConversionRevision rev, File pdf) {
		List<Runnable> tasks = Lists.newArrayList();
		long revisionId = rev.getRevisionId();
		DocConversionContext context = new DocConversionContext(rev, null, pdf);
		
		int count = conversionStorageService.getArtifactCount(rev, DocConversionArtifactType.Preview);
		if(count < rev.getNumberOfPages()){
			for(int i = 1 ; i <= rev.getNumberOfPages() ; i++){
				ConversionArtifact artifact = conversionStorageService.getArtifact(rev, DocConversionArtifactType.Preview, i);
				if (artifact != null) {
					info.recordArtifactGenerated(artifact);
				} else {
					info.addScheduledArtifact(revisionId, DocConversionArtifactType.Preview, i);
					Runnable task = buildExecutorTask(context, DocConversionArtifactType.Preview, i);
					tasks.add(task);
				}
			}
		}
		
		int count2 = conversionStorageService.getArtifactCount(rev, DocConversionArtifactType.Thumbnail);
		if(count2 < rev.getNumberOfPages()){
			for(int i = 1 ; i <= rev.getNumberOfPages() ; i++){
				ConversionArtifact artifact = conversionStorageService.getArtifact(rev, DocConversionArtifactType.Thumbnail, i);
				if (artifact != null) {
					info.recordArtifactGenerated(artifact);
				} else {
					info.addScheduledArtifact(revisionId, DocConversionArtifactType.Thumbnail, i);
					Runnable task = buildExecutorTask(context, DocConversionArtifactType.Thumbnail, i);
					tasks.add(task);
				}
			}
		}
		
		if(tasks.size() == 0){
			stopProcessing(context.getRevision());
			return;
		}
		
		//执行
		for(Runnable task : tasks){
			executorService.execute(task);
		}
	}
	
	protected Runnable buildExecutorTask(final DocConversionContext context, ConversionArtifactType type, final int... page){
		final DocConversionExecutor executor = conversionExecutorFactory.getConversionExecutor(type);
		return new Runnable(){
			
			public void run() {
				executor.execute(context, page);
			}
		};
	}
	
	private void stopProcessing(ConversionRevision revison){
		processCache.remove(revison.getRevisionId());
		
		//发出完成事件
		dispatcher.dispatchEvent(new ConversionEvent(ConversionEvent.Type.Complete, revison));
	}
	
	
	private void handleError(DocConversionContext context, ConversionArtifactType type, String message) {
        try {
        	ConversionRevision rev = context.getRevision();
            log.error("Error converting " + rev.getFilename() + " " + rev);

            // refresh ConversionRevision
            ConversionRevision lastCM = conversionStorageService.getRevision(rev.getRevisionId());

            // could not generate the pdf
            if (lastCM == null) {
                conversionStorageService.saveErrorStep(rev.getRevisionId(), DocConversionArtifactType.Pdf, message);
            }
            else if (lastCM.getNumberOfPages() <= 0) {

                conversionStorageService.saveErrorStep(rev.getRevisionId(), type, message);

                // stop processing if can't get PDF
                stopProcessing(context.getRevision());
            }
            else {

                // for final artifacts
                ConversionProcessInfo info = processCache.get(rev.getRevisionId());
                if (info != null) {
                    info.recordError();

                    // it's only an error if all final artifacts failed such as for password protecetd PDFs                    
                    if (info.getScheduledArtifacts().size() <= info.getErrorCount()) {
                        conversionStorageService.saveErrorStep(rev.getRevisionId(), type, message);
                        // stop processing if nothing else is scheduled
                        //processCache.remove(cm.getConversionMetaDataID());
                        stopProcessing(context.getRevision());
                    }

                }
                
            }
        }
        catch (Exception ex) {
            log.error("Error processing the document's conversion error " + context.getRevision().getFilename(), ex);
        }
		
	}

	protected void handleFinalGenerated(DocConversionExecutorEvent event){
		Payload payload = event.getSource();
		ConversionRevision rev = payload.getRevision();
		File dataFile = payload.getGeneratedFile();
		try{
			ConversionProcessInfo info = processCache.get(rev.getRevisionId());

            if (!ConversionUtils.isValid(dataFile)) {
                String message = String
                        .format("The conversion failed to generate an artifact for file %s for page %d. ",
                                rev.getFilename(), payload.getPage());
                log.warn(message);


                if (info != null) {
                    info.recordArtifactGenerated(rev.getRevisionId(), payload.getType(), payload.getPage());
                    if (info.getScheduledArtifacts().size() == 0) {
                        //processCache.remove(rev.getRevisionId());
                        //FileUtils.deleteQuietly(payload.getPdf());
                    	stopProcessing(payload.getRevision());
                    }
                }

                // no need for an error here since the viewer won't show up if less then needed pages
                return;
            }

			ConversionArtifact ca = conversionStorageService.saveArtifact(rev.getRevisionId(), payload.getType(),
					payload.getPage(), null, payload.getType().getContentType(), dataFile.length(), dataFile);

            if (info == null) {
                String message = String
                        .format("The conversion stopped for file %s possible because the allocated time expired. ",
                                rev.getFilename());
                log.warn(message);
                return;
            }
            
            info.recordArtifactGenerated(ca);
            if (info.getScheduledArtifacts().size() == 0) {

                log.info("All requested conversion artifacts for " + rev.getFilename() + " generated in "
                        + (System.currentTimeMillis() - info.getStartedProcessingMillis()) + " millis");
                // remove the process cache entry
                //processCache.remove(rev.getRevisionId());
                //FileUtils.deleteQuietly(payload.getPdf());
                stopProcessing(payload.getRevision());
            }
			
			//requestFinalGeneration(info, rev, pdf);
		}catch(Exception ex){
			log.error(String.format("Error processing artifact %s(page %s) generated for %s", payload.getType(), payload.getPage(),
                    rev.getFilename()), ex);

            handleError(payload, payload.getType(), ex.getMessage());
		}finally{
			 FileUtils.deleteQuietly(dataFile);
		}
	}
	

	private boolean isConversionEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	

	
	public boolean isConvertable(Object object) {
		if (!isConversionEnabled()) {
            return false;
        }
        if (object instanceof ConvertableObject) {
            ConvertableObject co = (ConvertableObject) object;
            String fileName = co.getFilename();
            String contentType = co.getContentType();
            if(StringUtils.isNotBlank(fileName) && StringUtils.isNotBlank(contentType)){
            	return isConvertableContentType(contentType);
            }
        }
		return false;
	}
	
	public boolean isConvertableContentType(String contentType){
		return FileFormats.DocumentType.isOffice2003MimeType(contentType)
                || FileFormats.DocumentType.isOffice2007MimeType(contentType) 
                || FileFormats.DocumentType.isPDFMimeType(contentType);
	}
	
	
	public ConversionArtifactType[] getSupportedTypes() {
		return DocConversionArtifactType.values();
	}
}
