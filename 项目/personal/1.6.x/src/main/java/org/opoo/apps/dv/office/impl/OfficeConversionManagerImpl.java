package org.opoo.apps.dv.office.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.AppsObject;
import org.opoo.apps.dv.ConvertableObject;
import org.opoo.apps.dv.converter.Converter;
import org.opoo.apps.dv.converter.ConverterFactory;
import org.opoo.apps.dv.converter.InputArtifact;
import org.opoo.apps.dv.impl.AbstractConversionManager;
import org.opoo.apps.dv.office.OfficeConversionArtifact;
import org.opoo.apps.dv.office.OfficeConversionArtifactType;
import org.opoo.apps.dv.office.OfficeConversionConfig;
import org.opoo.apps.dv.office.OfficeConversionErrorStep;
import org.opoo.apps.dv.office.OfficeConversionManager;
import org.opoo.apps.dv.office.OfficeConversionMetaData;
import org.opoo.apps.dv.office.OfficeConversionStatus;
import org.opoo.apps.dv.office.OfficeConversionStep;
import org.opoo.apps.dv.office.OfficeConversionStorageService;
import org.opoo.apps.dv.office.event.OfficeConversionEvent;
import org.opoo.apps.dv.office.event.OfficeConversionEvent.ConversionEventPayload;
import org.opoo.apps.dv.office.model.OfficeArtifact;
import org.opoo.apps.dv.office.model.OfficeErrorStep;
import org.opoo.apps.dv.office.model.OfficeMetaData;
import org.opoo.apps.dv.provider.ConvertibleObjectProvider;
import org.opoo.apps.dv.provider.ConvertibleObjectProvider.SizedInputStream;
import org.opoo.apps.dv.provider.FileProvider;
import org.opoo.apps.dv.util.FileFormats;
import org.opoo.apps.dv.util.Utils;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.apps.license.AppsLicenseManager;
import org.opoo.apps.service.MimeTypeManager;
import org.opoo.apps.util.FileUtils;
import org.opoo.cache.Cache;
import org.opoo.cache.CacheWrapper;
import org.opoo.ndao.support.ResultFilter;
import org.springframework.beans.factory.annotation.Required;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class OfficeConversionManagerImpl
	extends AbstractConversionManager<OfficeConversionMetaData, 
		OfficeConversionArtifact, OfficeConversionArtifactType, 
		OfficeConversionStatus, OfficeConversionStep, 
		OfficeConversionStorageService>
	implements OfficeConversionManager{
	private static final Logger log = Logger.getLogger(OfficeConversionManagerImpl.class);
	
	private ProcessCacheWrapper/*Cache<Long, ConversionProcessInfo>*/ processCache;
	private ConverterFactory converterFactory;
	private AppsLicenseManager licenseManager;
	private Executor executor;
	private MimeTypeManager mimeTypeManager;
	private OfficeConversionConfig conversionConfig;

	public void setConversionConfig(OfficeConversionConfig config){
		this.conversionConfig = config;
	}
	
	public void setMimeTypeManager(MimeTypeManager mimeTypeManager) {
		this.mimeTypeManager = mimeTypeManager;
	}

	public void setProcessCache(Cache<Long, ConversionProcessInfo> processCache) {
//		if(processCache instanceof ProcessCacheWrapper){
//			this.processCache = (ProcessCacheWrapper) processCache;
//		}else{
		this.processCache = new ProcessCacheWrapper(processCache);
//		}
	}

	public void setConverterFactory(ConverterFactory converterFactory) {
		this.converterFactory = converterFactory;
	}

	public void setLicenseManager(AppsLicenseManager licenseManager) {
		this.licenseManager = licenseManager;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}
	
	public boolean isConversionEnabled() {
		return AppsGlobals.getProperty("officeintegration.enabled", true);
	}

	public boolean isConvertable(AppsObject appsObject) {
		if (!isConversionEnabled()) {
			return false;
		}

		if (appsObject instanceof ConvertableObject) {

			ConvertableObject co = (ConvertableObject) appsObject;

			if (coProviderFactory.hasRegisteredProvider(co)) {

				ConvertibleObjectProvider coProvider = coProviderFactory.get(co);

				if (!coProvider.isAllowedToConvert()) {
					return false;
				}

				String fileName = coProvider.getFilename();
				String suggestedContentType = coProvider.getContentType();
				if (StringUtils.isEmpty(fileName)) {
					return false;
				}

				if (isDisabled(co)) {
					return false;
				}
				InputStream inputStream = null;
				try {
					inputStream = coProvider.getStream();
					if (inputStream == null) {
						return false;
					}
					String realMimeType = mimeTypeManager.getFileMimeType(fileName, suggestedContentType, inputStream);

					return FileFormats.DocumentType.isOffice2003MimeType(realMimeType)
							|| FileFormats.DocumentType.isOffice2007MimeType(realMimeType)
							|| FileFormats.DocumentType.isPDFMimeType(realMimeType);
				} catch (IOException io) {
					String msg = "Error determining Mime Type. ";
					if (log.isDebugEnabled()) {
						log.debug(msg, io);
					} else {
						log.error(msg + io.getMessage());
					}
					return false;
				} finally {
					IOUtils.closeQuietly(inputStream);
				}
			}

		}

		return false;
	}
	    
	    
	public boolean isDisabled(ConvertableObject co) {
		if (co == null) {
			return false;
		}

		// might be one of the disabled extentions
		if (coProviderFactory.hasRegisteredProvider(co)) {
			ConvertibleObjectProvider coProvider = coProviderFactory.get(co);
			String fileName = coProvider.getFilename();

			String fileExt = FileFormats.DocumentType.getExtension(fileName);
			for (String ext : conversionConfig.getDisabledExtensions()) {
				if (ext.equalsIgnoreCase(fileExt)) {
					return true;
				}
			}
		}
		return false;
	}

	protected static void writeToFile(File file, InputStream is)
			throws Exception {
		OutputStream fos = null;
		try {
			fos = FileUtils.openOutputStream(file);
			IOUtils.copy(is, fos);
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(fos);
		}
	}

	/**
	 * 如果原始对象支持提供文件，则取对象提供的文件，否则创建临时文件，并将对象的stream数据复制到 文件中。 注意清理临时文件。
	 * 
	 * @param provider
	 * @param info 
	 * @return
	 * @throws Exception
	 */
	protected File getConvertibleObjectFile(
			ConvertibleObjectProvider provider, ConversionProcessInfo info) throws Exception {
		if (provider instanceof FileProvider) {
			//原文件，正式文件不能被删除
			return ((FileProvider) provider).getFile();
		}
		//原文件流生成的临时文件，转换完成时删除
		File file = Utils.createTempFile("DV-", provider.getFilename());
		writeToFile(file, provider.getStream());
		info.tempFiles.add(file);
		return file;
	}
	    
    public boolean convert(final ConvertableObject binary) {
        if (binary == null) {
            log.error("Can't request conversion for a null binary");
            return false;
        }

        if (!isConvertable(binary)) {
            log.error(String.format("Binary %s is can't be converted ", binary));
            return false;
        }

        if (!isConversionEnabled()) {
            return false;
        }
        // file with the required artifact data
        //File caFile = null;
        long cmID = -1;
        OfficeConversionMetaData cm = null;
        try {
            
            if (!coProviderFactory.hasRegisteredProvider(binary)) {
                log.error(String.format("Convertabel object %s does not have a provider registered with ConvertibleObjectProviderFactory so it can't be converted.",
                        /*cm.getFilename()*/binary));
                return false;
            }

            ConvertibleObjectProvider provider = coProviderFactory.get(binary);

            cm = conversionStorageService.getMetaData(binary.getObjectType(), binary.getId(), provider.getRevisionNumber());
    
            // brand-new conversion
            if (cm == null) {
            	OfficeMetaData impl = new OfficeMetaData(binary.getObjectType(), binary.getId(),
            			provider.getFilename(), provider.getContentLength(), 0, provider.getRevisionNumber(), null);
            	cm = conversionStorageService.saveMetaData(impl);
            }

            ConversionProcessInfo info = processCache.get(cm.getId());

            // distibuted lock for the next 4 lines on the cmID to make it more robust???
            if (info != null) {
                log.info(String.format("Conversion for %s already running.", cm.getFilename()));
                return true;
            }

            // create a new info and figure out exactly what needs to be generated
            // and what's generated so far so no need to go to DB anymore
            info = new ConversionProcessInfo();
            cmID = cm.getId();
            processCache.put(cmID, info);

            // file with the required artifact data
            //caFile = File.createTempFile("dv-", ".ca");//FileUtils.createTempFile(cm);

            // delete the steps as errors since starting over
            conversionStorageService.deleteSteps(cm);

            //  check PDF existance
            OfficeConversionArtifact pdf = conversionStorageService.getArtifact(cm, OfficeConversionArtifactType.Pdf, 0);

            //如果存在 PDF 的 artifact，则原始文件一定不是 PDF 格式的。
            if (pdf != null) {
                info.recordConversionArtifactGenerated(pdf);
                
                File pdfFile = Utils.createTempFile("DV-" + cm.getFilename(), ".pdf");
                writeToFile(pdfFile, conversionStorageService.getArtifactBody(pdf));
                info.tempFiles.add(pdfFile);
                
                checkPDFGeneration(info, cm, pdfFile);
            }
            else {

                // need to generate the PDF or this document is PDF
                boolean isPDF = FileFormats.DocumentType.isPDFFileType(provider.getFilename());
                //writeToFile(caFile, provider.getStream());
                //注意定期清理可能产生的临时文件
                File originFile = getConvertibleObjectFile(provider, info);
                
                // must be a doc
                if (!isPDF) {
                	//request generate PDF file by origin file
                    requestPDFGeneration(info, cm, originFile);
                } else {
                	if(log.isDebugEnabled()){
                		log.info("原文件是PDF文件: " + originFile);
                	}
                	//origin file is PDF file
                	checkPDFGeneration(info, cm, originFile);
                }
            }
        }
        catch (Exception e) {
            log.error(String.format("Error starting converting %s", binary), e);            
            if (cm != null) {
                this.handleError(cm , null, e.getMessage());
            }
            // kill it
            this.processCache.remove(cmID);
            return false;
        }
        finally {
            //FileUtils.deleteQuietly(caFile);
        }

        return true;
    }
	 
    /**
     * 当PDF已经存在时（无论是PDF已经经过转换的来或者源文件就是PDF），检查
     * 后续的转换工作是否完成，即检查Preview和Thumbnail是否都已经生成了。
     * 
     * @param info
     * @param cm
     * @param pdfFile
     * @throws Exception
     */
    private void checkPDFGeneration(ConversionProcessInfo info,	OfficeConversionMetaData cm, File pdfFile) throws Exception{
    	// check if there is a pdf but no number of pages
        if (cm.getNumberOfPages() <= 0) {
            try {
                cm.setNumberOfPages(FileFormats.getNumberOfPages(pdfFile));
            }
            finally {
            	//TODO
                //FileUtils.deleteQuietly(pdfFile);
            }
            cm = (OfficeConversionMetaData) conversionStorageService.updateMetaData(cm);
        }
        
        // check previews
        if (cm.getNumberOfPages() > 0) {
            boolean previewsMissing = false;
            //需要产生NumberOfPages + 1 个  SWF 文件，第 0 个 SWF 是 PDF 文件所有页转换的，每页一帧的动画，其后第 n 个
            //SWF 是原 PDF 文件第 N页转换的，每个SWF只有一帧。
            int previewsCount = conversionStorageService.getArtifactCount(cm, OfficeConversionArtifactType.Preview);
            if (previewsCount < cm.getNumberOfPages() + 1) {
                previewsMissing = true;
                for (int page = 0; page <= cm.getNumberOfPages(); page++) {
                	OfficeConversionArtifact preview = conversionStorageService.getArtifact(cm, OfficeConversionArtifactType.Preview, page);
                    if (preview != null) {
                        info.recordConversionArtifactGenerated(preview);
                    }else {
                        info.addConversionArtifactToGenerate(
                                new OfficeArtifact(cm.getId(), OfficeConversionArtifactType.Preview, page));
                    }
                }
            }
            
         // check thumnbails
            boolean thumbnailsMissing = false;
            int thumbnailsCount = conversionStorageService.getArtifactCount(cm, OfficeConversionArtifactType.Thumbnail);
            if(thumbnailsCount < cm.getNumberOfPages()){
            	thumbnailsMissing = true;
            	for (int page = 1; page <= cm.getNumberOfPages(); page++) {
            		OfficeConversionArtifact thumbnail = conversionStorageService.getArtifact(cm, OfficeConversionArtifactType.Thumbnail, page);
                    if (thumbnail != null) {
                        info.recordConversionArtifactGenerated(thumbnail);
                    }else {
                        info.addConversionArtifactToGenerate(
                                new OfficeArtifact(cm.getId(), OfficeConversionArtifactType.Thumbnail, page));
                    }
                }
            }
            
            if(previewsMissing || thumbnailsMissing){
            	requestFinalArtifactGeneration(info, cm, pdfFile);
            }else{
            	this.processCache.remove(cm.getId());
            }
        }
    }
	 
	 	
	private void requestPDFGeneration(ConversionProcessInfo info, OfficeConversionMetaData cm, File caFile) {
		Converter converter = converterFactory.getConverter(OfficeConversionArtifactType.Pdf);
		// generate PDF
        OfficeConversionArtifact pdfCA = new OfficeArtifact(cm.getId(), OfficeConversionArtifactType.Pdf, 0);
        info.addConversionArtifactToGenerate(pdfCA);
        
        InputArtifact input = new InputArtifact(caFile, OfficeConversionArtifactType.File);
        executeConversionTask(converter, cm, input, Collections.singletonList(0));
        /*
        Runnable task = new Runnable(){
			public void run() {
				converter.convert(cm, pdfCA, Collections.singletonList(0), caFile);
			}
        };

        //excute PDF generation as high priority
        executor.execute(task);
        */
	}
	
	/**
	 * 同步或者异步执行文件转换工作，文件转换器转换完成时必须发出事件。
	 * @param cm
	 * @param a
	 * @param indexes
	 * @param caFile
	 * @param converter
	 */
	private void executeConversionTask(final Converter converter, final OfficeConversionMetaData cm, 
			final InputArtifact inputFile, final List<Integer> indexes){
		Runnable task = new Runnable(){
			public void run() {
				converter.convert(cm, inputFile, indexes);
			}
        };

        //excute PDF generation as high priority
        executor.execute(task);
	}
	
	
	protected void handlePdfGenerated(OfficeConversionMetaData cm, OfficeConversionArtifact ca, File pdfContent) {
        try {
            ConversionProcessInfo info = processCache.get(cm.getId());
            if (pdfContent == null || !pdfContent.exists() || pdfContent.length() == 0) {
                String message = String.format("The conversion failed to generate a PDF file file %s for page %d. ",
                                cm.getFilename(), ca.getPage());
                log.warn(message);

                if (info != null) {
                    info.recordConversionArtifactGenerated(ca);
                }

                handleError(cm, OfficeConversionStep.PdfGeneration, message);
                return;
            }
                                            
            // see if needs to update the page numbers and save
            if (cm.getNumberOfPages() <= 0) {
                cm.setNumberOfPages(FileFormats.getNumberOfPages(pdfContent));
                conversionStorageService.updateMetaData(cm);
            }

            conversionStorageService.saveArtifact(ca, pdfContent);

            if (info == null) {
                String message = String
                        .format("The conversion stopped for file %s possible because the allocated time expired. ",
                                cm.getFilename());
                log.warn(message);
                return;
            }

            info.recordConversionArtifactGenerated(ca);
            info.tempFiles.add(pdfContent);

            // request previews and thumbnail
            for (int i = 0; i <= cm.getNumberOfPages(); i++) {
                OfficeArtifact c = new OfficeArtifact(cm.getId(), OfficeConversionArtifactType.Preview, i);
                info.addConversionArtifactToGenerate(c);
                
                if(i > 0){
                	c = new OfficeArtifact(cm.getId(), OfficeConversionArtifactType.Thumbnail, i);
                	info.addConversionArtifactToGenerate(c);
                }
            }
            requestFinalArtifactGeneration(info, cm, pdfContent);

            // remove the doc file from the storage since now has the PDF
            //conversionStorageService.deleteOriginalFile(cm);
        }
        catch (Exception ex) {
            log.error("Error processing PDF generated", ex);
            handleError(cm, OfficeConversionStep.PdfGeneration, ex.getMessage());
        }
        finally {
            //FileUtils.deleteQuietly(pdfContent);
        }
    }
	

	private void requestFinalArtifactGeneration(ConversionProcessInfo info,	OfficeConversionMetaData cm, File pdfFile) {
		 for (OfficeConversionArtifact ca : info.getScheduledConversionArtifacts()) {
			 Converter converter = converterFactory.getConverter(ca.getType());
			 //execute
			 InputArtifact pdf = new InputArtifact(pdfFile, OfficeConversionArtifactType.Pdf);
			 executeConversionTask(converter, cm, pdf, Collections.singletonList(ca.getPage()));
         }
	}
	
	protected void handleFinalArtifactGenerated(OfficeConversionMetaData cm, OfficeConversionArtifact ca, File dataFile, OfficeConversionStep step) {
		try {
            ConversionProcessInfo info = processCache.get(cm.getId());

            if (dataFile == null || !dataFile.exists() || dataFile.length() == 0) {
                String message = String
                        .format("The conversion failed to generate an artifact for file %s for page %d. ", cm.getFilename(), ca.getPage());
                log.warn(message);

                if (info != null) {
                    info.recordConversionArtifactGenerated(ca);
                    if (info.getScheduledConversionArtifacts().size() == 0) {
                        processCache.remove(cm.getId());
                    }
                }

                // no need for an error here since the viewer won't show up if less then needed pages
                return;
            }

            conversionStorageService.saveArtifact(ca, dataFile);

            if (info == null) {
                String message = String
                        .format("The conversion stopped for file %s possible because the allocated time expired. ",
                                cm.getFilename());
                log.warn(message);
                return;
            }

            info.recordConversionArtifactGenerated(ca);

            if (info.getScheduledConversionArtifacts().size() == 0) {
                log.info("All requested conversion artifacts for " + cm.getFilename() + " generated in "
                        + (System.currentTimeMillis() - info.getStartedProcessingMillis()) + " millis");
                // remove the process cache entry
                processCache.remove(cm.getId());
            }

        }
        catch (Exception ex) {
            log.error(String.format("Error processing artifact %s generated for %s", ca.getStorageKey(),
                    cm.getFilename()), ex);
            handleError(cm, step, ex.getMessage());
        }finally {
        	//log.debug("删除临时文件：" + dataFile);
        	FileUtils.deleteQuietly(dataFile);
        }
	}
	
	protected void handleError(OfficeConversionMetaData cm, OfficeConversionStep step, String message) {
        try {
            if (step == null) {
                if (cm.getNumberOfPages() < 0) {
                    step = OfficeConversionStep.PdfGeneration;
                }
                else {
                    step = OfficeConversionStep.PreviewGeneration;
                }

            }
        	
            log.error("Error converting " + cm.getFilename() + " " + cm);

            // refresh cm
            OfficeConversionMetaData lastCM = (OfficeConversionMetaData) this.getConversionMetaData(cm.getId());

            // see if the message has the body
            if (StringUtils.isEmpty(message)) {
                message = "Unexpected error - possibly the document is corrupted.";
            }

            // could not generate the pdf
            if (lastCM == null) {
                conversionStorageService.saveErrorStep(new OfficeErrorStep(cm.getId(), new Date(), message, OfficeConversionStep.Uploaded));
            }
            else if (lastCM.getNumberOfPages() <= 0) {

                conversionStorageService.saveErrorStep(new OfficeErrorStep(cm.getId(), new Date(), message, step));

                // stop processing if can't get PDF
                processCache.remove(cm.getId());
            }
            else {

                // for final artifacts
                ConversionProcessInfo info = processCache.get(cm.getId());
                if (info != null) {
                    info.recordError();

                    // it's only an error if all final artifacts failed such as for password protecetd PDFs                    
                    if (info.getScheduledConversionArtifacts().size() <= info.getErrorCount()) {
                        conversionStorageService.saveErrorStep(new OfficeErrorStep(cm.getId(), new Date(), message, step));
                        // stop processing if nothing else is scheduled
                        processCache.remove(cm.getId());
                    }
                }
            }
        }
        catch (Exception ex) {
            log.error("Error processing the document's conversion error " + cm.getFilename(), ex);
        }
    }
	
	
	
	
	
	
	
	
	
	
	











	/**
	 * Cached information about things that were scheduled
	 */
	public static class ConversionProcessInfo implements Serializable {
		private static final long serialVersionUID = 7883905533441275406L;
		//用来记录原始文件所产生的临时文件
//		private File originFileTempFile;
		private Set<File> tempFiles = Sets.newHashSet();
		
		private long lastProcessedMillis;
		private long startedProcessingMillis;
		
		private final Set<OfficeConversionArtifact> scheduledArtifacts = Sets.newLinkedHashSet();
		private final Set<OfficeConversionArtifact> generatedArtifacts = Sets.newLinkedHashSet();
		private int errorCount = 0;

		public ConversionProcessInfo() {
			startedProcessingMillis = lastProcessedMillis = System.currentTimeMillis();
		}

		public synchronized void addConversionArtifactToGenerate(OfficeConversionArtifact ca) {
			scheduledArtifacts.add(ca);
		}

		public synchronized int getTotalScheduledArtifacts() {
			return scheduledArtifacts.size();
		}

		public synchronized void recordConversionArtifactGenerated(OfficeConversionArtifact ca) {
			lastProcessedMillis = System.currentTimeMillis();
			scheduledArtifacts.remove(ca);
			generatedArtifacts.add(ca);
		}

		public synchronized void recordError() {
			errorCount++;
		}

		public synchronized int getErrorCount() {
			return errorCount;
		}

		public synchronized Set<OfficeConversionArtifact> getGeneratedConversionArtifacts(
				OfficeConversionArtifactType type) {
			return getActifactsByType(generatedArtifacts, type);
		}

		public synchronized Set<OfficeConversionArtifact> getScheduledConversionArtifacts() {
			return Sets.newLinkedHashSet(scheduledArtifacts);
		}

		public synchronized Set<OfficeConversionArtifact> getScheduledConversionArtifacts(
				OfficeConversionArtifactType type) {
			return getActifactsByType(scheduledArtifacts, type);
		}

		public synchronized boolean isGenerated(OfficeConversionArtifact ca) {
			return generatedArtifacts.contains(ca);
		}

		public synchronized long getLastProcessedMillis() {
			return lastProcessedMillis;
		}

		public long getStartedProcessingMillis() {
			return startedProcessingMillis;
		}

		protected Set<OfficeConversionArtifact> getActifactsByType(
				Set<OfficeConversionArtifact> artifacts, OfficeConversionArtifactType type) {
			Set<OfficeConversionArtifact> cas = Sets.newLinkedHashSet();

			for (OfficeConversionArtifact ca : artifacts) {
				if (ca.getType() == type) {
					cas.add(ca);
				}
			}
			return cas;
		}
	}






	public boolean generateConversionArtifact(OfficeConversionMetaData cm, OfficeConversionArtifactType type, int page) {
		try {
            // rerequest the artifact if not in flight
			OfficeConversionArtifact ca = new OfficeArtifact(cm.getId(), type, page);
            if (cm != null && cm.isValid() && (ca.getType() == OfficeConversionArtifactType.Thumbnail
                    || ca.getType() == OfficeConversionArtifactType.Preview))
            {
                ConversionProcessInfo info = new ConversionProcessInfo();
                OfficeConversionArtifact pdfCA = this.getConversionArtifact(cm, OfficeConversionArtifactType.Pdf, 0);
                info.recordConversionArtifactGenerated(pdfCA);
                
                //查找PDF文件
                InputStream inputStream = this.getConversionArtifactBody(pdfCA);
                File pdfFile = Utils.createTempFile("DV-" + cm.getFilename(), ".pdf");
                writeToFile(pdfFile, inputStream);

                processCache.put(cm.getId(), info);
                info.addConversionArtifactToGenerate(ca);              
                requestFinalArtifactGeneration(info, cm, pdfFile);
            }
            return true;
        }
        catch (Exception e) {
            log.error(String.format("Error requesting an artifact for file %s of type %s and page %d", cm.getFilename(),
                    type.name(), page), e);

        }
        return false;
	}

	public SizedInputStream getModifiedStream(ConvertableObject co)
			throws IOException {
		if (isConversionEnabled() && isConvertable(co)) {
			if (coProviderFactory.hasRegisteredProvider(co)) {

//				ConvertibleObjectProvider convertibleObjectProvider = coProviderFactory.get(co);
//				long size = convertibleObjectProvider.getContentLength();
//				String fileName = convertibleObjectProvider.getFilename();
				ConvertibleObjectProvider.SizedInputStream modifiedStream = coProviderFactory.get(co).getModifiedStream();

				// // check if the new file smaller or large by more than the
				// configured threshhold, then return the original
				// long difference = Math.abs(size -
				// modifiedStream.getLength());
				// if ( difference >
				// conversionServiceConfig.getDownloadFileSizeModificationCorruptionThreshhold())
				// {
				// log.error(String.format(
				// "The file %s was modified by more than allowed corruption difference %d by openxml4j so the original will be used and Apps for Office metadata won't be inserted",
				// fileName, difference));
				// return null;
				// }

				return modifiedStream;

			}
		}

		return null;
	}


	public boolean isModifiableOnDownload(ConvertableObject co) {
		if (!coProviderFactory.hasRegisteredProvider(co)) {
			return false;
		}

		String fileName = coProviderFactory.get(co).getFilename();

		if (StringUtils.isEmpty(fileName)) {
			return false;
		}

		return FileFormats.DocumentType.isOffice2007(fileName)
				|| FileFormats.DocumentType.isOffice2003(fileName);
	}

	public boolean isOfficeIntegrationLicensed() {
		return licenseManager.isModuleLicensed("officeintegration");
	}

	public boolean isOffice2007Document(ConvertableObject co) {
		if (!coProviderFactory.hasRegisteredProvider(co)) {
            return false;
        }

        String fileName = coProviderFactory.get(co).getFilename();

        if (StringUtils.isEmpty(fileName)) {
            return false;
        }

        return FileFormats.DocumentType.isOffice2007(fileName);
	}

	public boolean isSameDocumentType(String newFileName, String oldFileName) {
		FileFormats.DocumentType newType = FileFormats.DocumentType.parse(newFileName);
        FileFormats.DocumentType oldType = FileFormats.DocumentType.parse(oldFileName);
        return newType == oldType;
	}

	public OfficeConversionStatus getConversionStatus(OfficeConversionMetaData cm, OfficeConversionStep... steps) {
        // if know nothing about - which should never happen but still need to protect
        if (cm == null) {
            return new OfficeConversionStatus(false, 0, 0, 0, 0, false, null, null, null, -1L);
        }
        // figure out the number of artifacts in-flight and in the storage
        ConversionProcessInfo processInfo = processCache.get(cm.getId());

        // normal case
        if (processInfo != null) {
            boolean isPDfGenerated = processInfo.getGeneratedConversionArtifacts(OfficeConversionArtifactType.Pdf).size() > 0;
            boolean isPDFScheduled = processInfo.getScheduledConversionArtifacts(OfficeConversionArtifactType.Pdf).size() > 0;

            int previewsGenerated = processInfo.getGeneratedConversionArtifacts(OfficeConversionArtifactType.Preview).size();

            int previewsScheduled = processInfo.getScheduledConversionArtifacts(OfficeConversionArtifactType.Preview).size();
            
            int thumbnailsGenerated = processInfo.getGeneratedConversionArtifacts(OfficeConversionArtifactType.Thumbnail).size();

            int thumbnailsScheduled = processInfo.getScheduledConversionArtifacts(OfficeConversionArtifactType.Thumbnail).size();

            // converting means still waiting for PDF or scheduled previews to be generated 
            return new OfficeConversionStatus(isPDfGenerated, previewsGenerated, previewsGenerated + previewsScheduled,
            		thumbnailsGenerated, thumbnailsGenerated + thumbnailsScheduled,
                    isPDFScheduled || previewsScheduled > 0 || thumbnailsScheduled > 0, null,
                    new Date(processInfo.getStartedProcessingMillis()), new Date(processInfo.getLastProcessedMillis()),
                    cm.getId());
        }
        
        //stepsFilter 为null时，不过滤
        //Set<String> stepsFilter = (steps == null || steps.length == 0) ? null : Sets.newHashSet(steps);
        Set<OfficeConversionStep> stepsFilter = steps == null || steps.length == 0 ? null : Sets.newHashSet(steps);
        
        // look for errors
        List<OfficeConversionErrorStep> errorSteps = conversionStorageService.getErrorSteps(cm);
        String error = null;
        for (OfficeConversionErrorStep step : errorSteps) {
        	// if (!StringUtils.isEmpty(step.getMessage()) && stepsFilter.contains(step.getStep())) {
            if (!StringUtils.isEmpty(step.getMessage()) 
            		&&(stepsFilter == null || stepsFilter.contains(step.getStep()))) {
                error = step.getMessage();
                break;
            }
        }

        boolean isPDfGenerated = conversionStorageService.getArtifact(cm, OfficeConversionArtifactType.Pdf, 0) != null;
        int countPreviews = 0;
        int countThumbnails = 0;
        int totalPages = cm.getNumberOfPages();
             
        if ( totalPages > 0) {
            totalPages = cm.getNumberOfPages();
            countPreviews = conversionStorageService.getArtifactCount(cm, OfficeConversionArtifactType.Preview);
            countThumbnails = conversionStorageService.getArtifactCount(cm, OfficeConversionArtifactType.Thumbnail);
        }
        
        return new OfficeConversionStatus(isPDfGenerated, countPreviews, countPreviews, countThumbnails, countThumbnails, false, error, 
        		cm.getCreationDate(), cm.getCreationDate(), cm.getId());
	}

	public List<OfficeConversionStatus> getConversionStatuses() {
		return Lists.transform( Lists.newArrayList(getInFlightConversions()), new Function<Long,OfficeConversionStatus>() {
            public OfficeConversionStatus apply(Long conversionMetaDataId) {
                return getConversionStatus(getConversionMetaData(conversionMetaDataId));
            }
        });
	}
	
	private Set<Long> getInFlightConversions() {
		return processCache.keySet();
	}

	public List<OfficeConversionStatus> getErrorStatuses(ResultFilter filter) {
		return Lists.transform( conversionStorageService.getErrorConversionMetadataIDs(filter), new Function<Long,OfficeConversionStatus>() {
            public OfficeConversionStatus apply(Long conversionMetaDataId) {
                return getConversionStatus( getConversionMetaData(conversionMetaDataId));
            }
        });
	}
	
	protected void checkProgress(OfficeConversionMetaData cm) {
		// record process timeout warning
		ConversionProcessInfo info = processCache.get(cm.getId());
		if (info != null) {
			long takes = System.currentTimeMillis()	- info.getLastProcessedMillis();
			if (takes > conversionConfig.getConversionProgressTimeout()) {
				log.warn("The document conversion for is making slow progress "	+ cm.getFilename() + " " + takes + " millis.");
			}
		}
	}
	
	private static class ProcessCacheWrapper extends CacheWrapper<Long, ConversionProcessInfo>{
		public ProcessCacheWrapper(Cache<Long, ConversionProcessInfo> cache) {
			super(cache);
		}

		@Override
		public ConversionProcessInfo remove(Object key) {
			ConversionProcessInfo info = super.remove(key);
			if(info != null && !info.tempFiles.isEmpty()){
				for(File file : info.tempFiles){
					if(log.isDebugEnabled()){
						log.debug("删除过程中产生的临时文件: " + file);
					}
					FileUtils.deleteQuietly(file);
				}
				info.tempFiles.clear();
			}
			return info;
		}
	}
	
	
	public static class ConversionEventListener implements EventListener<OfficeConversionEvent>{
		private OfficeConversionManagerImpl conversionManager;

        public void handle(OfficeConversionEvent e) {
        	ConversionEventPayload payload = e.getSource();
        	OfficeConversionArtifact ca = payload.getConversionArtifact();
        	OfficeConversionMetaData cm = payload.getConversionMetaData();
        	File file = payload.getGeneratedArtifactData();
            conversionManager.checkProgress(e.getSource().getConversionMetaData());

            switch (e.getType()) {
                case PDF_GENERATED:
                    conversionManager.handlePdfGenerated(cm, ca, file);
                    break;
                case THUMBMATIL_PAGE_GENERATED:
                    conversionManager.handleFinalArtifactGenerated(cm, ca, file, OfficeConversionStep.ThumbnailGeneration);
                    break;
                case PREVIEW_PAGE_GENERATED:
                    conversionManager.handleFinalArtifactGenerated(cm, ca, file, OfficeConversionStep.PreviewGeneration);
                    break;
                 case TEXT_INDEX_GENERATED:
                    //conversionManager.handleTextExtractionResult(cm, ca, file);
                    log.warn("Not implement.");
                	break;
                case ERROR:
                	
                	 // hack for nwo to see if ream CM
                    if (payload.getConversionStep() == OfficeConversionStep.TextIndexing) {
                        conversionManager.handleTextExtractionResult(e);
                    } else {
                        conversionManager.handleError(cm, payload.getConversionStep(), payload.getMessage());
                    }
                    break;
            }
        }

        @Required
        public void setConversionManager(OfficeConversionManagerImpl conversionManager) {
            this.conversionManager = conversionManager;
        }
	}






	protected void handleTextExtractionResult(OfficeConversionEvent e) {
		// TODO Auto-generated method stub
	}
}
