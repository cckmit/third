package org.opoo.apps.dvi.office.converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.PartSource;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.dv.connector.DummyHttpClientFactory;
import org.opoo.apps.dv.connector.HttpClientFactory;
import org.opoo.apps.dv.connector.ServiceHost;
import org.opoo.apps.dv.connector.ServiceHostObjectFactory;
import org.opoo.apps.dvi.ConversionException;
import org.opoo.apps.dvi.ConversionMetaData;
import org.opoo.apps.dvi.converter.Converter;
import org.opoo.apps.dvi.office.OfficeConversionArtifact;
import org.opoo.apps.dvi.office.OfficeConversionArtifactType;
import org.opoo.apps.dvi.office.OfficeConversionConfig;
import org.opoo.apps.dvi.office.OfficeConversionMetaData;
import org.opoo.apps.dvi.office.OfficeConversionStep;
import org.opoo.apps.dvi.office.OfficeConversionStorageService;
import org.opoo.apps.dvi.office.event.OfficeConversionEvent;
import org.opoo.apps.dvi.office.event.OfficeConversionEvent.ConversionEventPayload;
import org.opoo.apps.dvi.office.model.OfficeArtifact;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.util.FileUtils;
import org.opoo.util.Assert;

public class GenericOfficeConverter implements Converter{
    private EventDispatcher dispatcher;
    private HttpClientFactory httpClientFactory = new DummyHttpClientFactory();
    private ServiceHostObjectFactory serviceHostObjectFactory;
    private OfficeConversionConfig conversionConfig;
	private OfficeConversionStorageService conversionStorageService;
	//作为每个转换器的标识，一个类型一个转换器实例
	private OfficeConversionArtifactType conversionArtifactType;

    private static final Logger log = Logger.getLogger(GenericOfficeConverter.class);
    
    protected File createTempFile(String prefix, String suffix) throws IOException {
    	return FileUtils.createTempFile(prefix);
    }

    protected String getInstanceID(){
		return AppsGlobals.getProperty(AppsGlobals.APPS_INSTANCE_ID);
	}
    
    public void convert(OfficeConversionMetaData cm, OfficeConversionArtifact inputCA, List<Integer> partNumbers){
    	Assert.notNull(inputCA);
    	ConversionDataProvider conversionDataProvider 
    		= new ConversionArtifactDataProvider(inputCA, conversionStorageService, cm.getFilename());
		convert(cm, conversionDataProvider, partNumbers);
    }
    
	public void convert(ConversionMetaData cm, File inputFile, List<Integer> partNumbers) {
		if(inputFile == null || !inputFile.exists() || !inputFile.canRead() || inputFile.length() == 0){
			log.error("输入文件无效：" + inputFile);
			dispatcher.dispatchEvent(new OfficeConversionEvent(OfficeConversionEvent.Type.ERROR,
            		new ConversionEventPayload((OfficeConversionMetaData)cm, (OfficeConversionArtifact)null, "输入文件无效", null)));
			return;
		}
		ConversionDataProvider conversionDataProvider = new FileDataProvider(inputFile, null);
		convert((OfficeConversionMetaData)cm, conversionDataProvider, partNumbers);
	}
	
	protected void convert(OfficeConversionMetaData cm, ConversionDataProvider conversionDataProvider, List<Integer> pages){
		ConversionCommand command = getConversionCommand(conversionArtifactType);
		try {
			convert(command, (OfficeConversionMetaData) cm,conversionDataProvider, pages);
		}
	    catch (Exception e) {
	    	log.error(String.format("GenericConversionExecutor Executor failed to complete successfully for file %s",
	                    cm.getFilename()), e);
//		            dispatcher.fire(new ConversionExecutorEvent(ConversionExecutorEvent.Type.ERROR,
//		                    new ConversionEventPayload(conversionMetaData, conversionArtifact, e.getMessage(), command.getConversionStep())));

	    	dispatcher.dispatchEvent(new OfficeConversionEvent(OfficeConversionEvent.Type.ERROR,
	            		new ConversionEventPayload((OfficeConversionMetaData)cm, (OfficeConversionArtifact)null, e.getMessage(), command.getConversionStep())));
	    }
	}
	    
	private void convert(ConversionCommand command,
			OfficeConversionMetaData cm, ConversionDataProvider conversionDataProvider, List<Integer> pages) throws Exception {
		ServiceHost host = (ServiceHost) serviceHostObjectFactory.makeObject();
		
		// cache the first file only
        boolean inputFileCached = false;

        HttpClient client = httpClientFactory.getNewClient();
        
        for (Integer page: pages) {
            try  {
                File target = createTempFile(String.format("ca-%s-%s-%d.%s", cm.getFilename(),
                        conversionArtifactType.name(), page, command.getOutExt(),page), command.getOutExt());

                doExecute(client, conversionDataProvider, target, page, command, inputFileCached, cm, host);

//                String filename = cm.getFilename() + ".p" + page + "." + command.getOutExt();
                OfficeConversionArtifact conversionResult = new OfficeArtifact(getInstanceID(),
	                    cm.getId(), command.getConversionArtifactType(), target.length(), null, command.getOutContentType(), page);


                ConversionEventPayload payload = new ConversionEventPayload(cm, conversionResult, target);
                dispatcher.dispatchEvent(new OfficeConversionEvent(command.getEventType(), payload));
                inputFileCached = true;
                
            } catch (Exception e) {
                log.error(String.format("Generic Executor of command %s on file %s and page %d failed to complete successfully: %s",
                        command.getOperation(), cm.getFilename(), page, e.getMessage()));
//	                dispatcher.fire(new ConversionExecutorEvent(ConversionExecutorEvent.Type.ERROR,
//	                    new ConversionEventPayload(conversionMetaData, conversionArtifact, e.getMessage(), command.getConversionStep())));
                
                dispatcher.dispatchEvent(new OfficeConversionEvent(OfficeConversionEvent.Type.ERROR,
	            		new ConversionEventPayload((OfficeConversionMetaData)cm, (OfficeConversionArtifact)null, e.getMessage(), command.getConversionStep())));
            }
        }
	}


	protected void doExecute(final HttpClient client, final ConversionDataProvider conversionDataProvider, File targetFile,
        Integer page,  ConversionCommand command, boolean inputFileCached, OfficeConversionMetaData cm, ServiceHost host)
        throws ConversionException {

        try {

            if (command == null) {
                throw new RuntimeException(String.format("Can't find command to generate a conversion artifact '%s'", conversionArtifactType));
            }

            final String jobID = createJobID();             

            client.getParams().setParameter("http.socket.timeout", (int) conversionConfig.getConversionStepTimeout(command.getOperation()));
            
            /* service definition:
            @PathParam("command") String command, @QueryParam("jobID") String jobID, @QueryParam("page") int page,
                 @QueryParam("inExt") String inExt, @QueryParam("outExt") String outExt, MultipartBody body
             */

            String inExt =  conversionDataProvider.getInputFilename() != null && conversionDataProvider.getInputFilename().contains(".") ?
                    conversionDataProvider.getInputFilename().substring(conversionDataProvider.getInputFilename().lastIndexOf('.')) : "";
           
            
            String cmd = command.getOperation();
            if(OfficeConversionArtifactType.Preview == conversionArtifactType && page == 0){
           		System.out.println("----------------------PDF2SWFPBM————————————————————————");
            	cmd = "PDF2SWFPBM";
            }
                    
            PostMethod post = new PostMethod(String.format("%s://%s:%d%s/execute/%s?inExt=%s&outExt=%s&page=%d&jobID=%s&inputfile=%s", getServiceScheme(),
                    host.getHost(), getServicePort(), getServiceContext(), cmd/*command.getOperation()*/, inExt, command.getOutExt(), page, jobID, conversionDataProvider.getFileName()));


            // only pass the file to the first page unless configured to always which can be a requirement for load balancing
            Part[] parts = !inputFileCached || conversionConfig.getAlwaysPassInputFile() ? new Part[] {
                    new FilePart("file", conversionDataProvider)} : new Part[0];

            post.addRequestHeader("Content-type", "multipart/form-data");

            post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));

            long timer = System.currentTimeMillis();
            int statusCode = client.executeMethod(post);

            if (statusCode == HttpStatus.SC_OK) {

                long bytes = extractResults(post, targetFile);
                    log.info(String.format("Streamed %s bytes to target file %s for document %s and page %d from conversion service in %sms.",
                            bytes, targetFile.getName(), conversionDataProvider.getInputFilename(), page, (System.currentTimeMillis() - timer)));
                
            }
            else {

                log.info(command.getOperation() + " conversion returned error status: " + statusCode);
               
                ConversionResponse commandResponse = null;
                try {
                	 ObjectMapper mapper = new ObjectMapper();
                	 commandResponse = mapper.readValue(post.getResponseBodyAsStream(), ConversionResponse.class);
                    log.error(String.format(
                            "For file %s conversion failed:errorCode=%s;message=%s;",
                            cm.getFilename(), commandResponse.getReturnCode(), commandResponse.getMessage()));


                }
                catch(Exception e) {
                    log.warn(String.format("Error parsing reponse from the server for file %s and command  %s: %s",
                    		cm.getFilename(),  command.getOperation(), post.getResponseBodyAsString()) );
                }

                String response = null;
                if (commandResponse == null) {
                    response = post.getResponseBodyAsString(3500);
                }
                else {
                    response = String.format("Error code: %s  Message: %s ", GenericConversionError.getConversionError(commandResponse.getReturnCode()),
                            commandResponse.getMessage());
                }

                throw new RuntimeException(String.format("Converting file %s for step %s and page %d encountered an error: %s ",
                        conversionDataProvider.getInputFilename(), command.getOperation(), page, response ));

            }
        }
        catch (ConnectException ce) {
            throw new ConversionException("Couldn't connect to conversion service at " + host.getHost() + ":" + getServicePort() + " for operation " +
                    command.getOperation() + ".", ce);
        }
        catch (IOException ioe) {
            throw new ConversionException("IO exception performing operation " + command.getOperation() + ".", ioe);
        }
        catch (Exception e) {                
            throw new ConversionException(command.getOperation() + " failed with unhandled exception: " + e.getMessage(), e);
        }
        finally {

        }
    }      




    private long extractResults(PostMethod post, File targetFile) throws IOException {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(targetFile);
            return IOUtils.copyLarge(post.getResponseBodyAsStream(), outputStream);
        }
        finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    protected ConversionCommand getConversionCommand(OfficeConversionArtifactType conversionArtifactType) {
        return ConversionCommand.getConversionCommand(conversionArtifactType);
    }



     /**
     * Create a new JOB ID to represent this line of work. Multiple calls to this method will never return the same
     * value.
     *
     * @return
     */
    protected String createJobID() {
        return UUID.randomUUID().toString();
    }


    protected String getServiceScheme() {
        return conversionConfig.getConversionServerScheme();
    }


    protected int getServicePort() {
        return conversionConfig.getConversionServerPort();
    }

    protected String getServiceContext() {
        return conversionConfig.getConversionServerContext();
    }

    public void setDispatcher(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void setHttpClientFactory(HttpClientFactory httpClientFactory) {
        this.httpClientFactory = httpClientFactory;
    }
    
    public void setConversionConfig(OfficeConversionConfig conversionConfig) {
		this.conversionConfig = conversionConfig;
	}

	public void setConversionArtifactType(OfficeConversionArtifactType conversionArtifactType) {
        this.conversionArtifactType = conversionArtifactType;
    }

    public void setConversionStorageService(OfficeConversionStorageService conversionStorageService) {
		this.conversionStorageService = conversionStorageService;
	}

	public void setServiceHostObjectFactory(
			ServiceHostObjectFactory serviceHostObjectFactory) {
		this.serviceHostObjectFactory = serviceHostObjectFactory;
	}

	enum ConversionCommand {

        OFFICETOPDF("office2pdf", OfficeConversionArtifactType.Pdf, "application/pdf", "pdf", OfficeConversionEvent.Type.PDF_GENERATED, OfficeConversionStep.PdfGeneration),
        TEXTEXTRACT("TEXTEXTRACT", OfficeConversionArtifactType.Text, "text/plain", "txt", OfficeConversionEvent.Type.TEXT_INDEX_GENERATED, OfficeConversionStep.TextIndexing),
        IMAGER("imager", OfficeConversionArtifactType.Thumbnail, "image/png", "png", OfficeConversionEvent.Type.THUMBMATIL_PAGE_GENERATED, OfficeConversionStep.ThumbnailGeneration),
        PDF2SWF("pdf2swf", OfficeConversionArtifactType.Preview, "application/x-shockwave-flash", "swf", OfficeConversionEvent.Type.PREVIEW_PAGE_GENERATED, OfficeConversionStep.PreviewGeneration);
       
        private static Map<OfficeConversionArtifactType, ConversionCommand> COMMANDS;

        static
        {
            Map<OfficeConversionArtifactType, ConversionCommand> localCommands = new HashMap<OfficeConversionArtifactType, ConversionCommand>();

            for (ConversionCommand command : ConversionCommand.values()) {
                localCommands.put(command.getConversionArtifactType(), command);
            }

            COMMANDS = Collections.synchronizedMap(localCommands);
        }

        private String operation;
        private OfficeConversionArtifactType conversionArtifactType;
        private String outContentType;
        private String outExt;
        private OfficeConversionEvent.Type eventType;
        private OfficeConversionStep conversionStep;

        private ConversionCommand (String command, OfficeConversionArtifactType conversionArtifactType,
                String outContentType, String outExt, OfficeConversionEvent.Type eventType,
                OfficeConversionStep step) {
            this.operation = command;
            this.conversionArtifactType = conversionArtifactType;
            this.outContentType = outContentType;
            this.outExt = outExt;
            this.eventType = eventType;
            this.outContentType = outContentType;
            this.conversionStep = step;
        }

        public OfficeConversionArtifactType getConversionArtifactType() {
            return conversionArtifactType;
        }

        public String getOperation() {
            return operation;
        }

        public String getOutExt() {
            return outExt;
        }

        public String getOutContentType() {
            return outContentType;
        }

        public OfficeConversionEvent.Type getEventType() {
            return eventType;
        }

        public OfficeConversionStep getConversionStep() {
            return conversionStep;
        }

        public static ConversionCommand getConversionCommand(OfficeConversionArtifactType conversionArtifactType) {
            return COMMANDS.get(conversionArtifactType);
        }
    }

    /**
     * The interface to hide the data provider for Document conversion CA
     * or Text extraction
     */
    private interface ConversionDataProvider extends PartSource {
        String getInputFilename();
    }

    /**
     * Provide the data for HTTP POST's FilePart using the conversion storage provider
     */
    private static class ConversionArtifactDataProvider implements ConversionDataProvider {

        private final OfficeConversionArtifact conversionArtifact;
        private final OfficeConversionStorageService storageService;
        private final String filename;

        ConversionArtifactDataProvider(OfficeConversionArtifact ca, OfficeConversionStorageService storageService, String filename) {
            this.conversionArtifact = ca;
            this.storageService = storageService;
            this.filename = filename;
        }

        public long getLength() {
            return conversionArtifact.getLength();
        }

        public String getFileName() {
            return conversionArtifact.getStorageKey();
        }

        public InputStream createInputStream() throws IOException {
            return storageService.getArtifactBody(conversionArtifact);
        }

        public String getInputFilename() {
            return filename;
        }

    }

    /**
     * Provide the data for HTTP POST's FilePart using a temp file for text extraction
     */
    private class FileDataProvider implements ConversionDataProvider {


        private final File file;
        private final String contentTypeHint;

        FileDataProvider(File file, String contentTypeHint)   {
            this.file = file;
            this.contentTypeHint = contentTypeHint;
        }


        public long getLength() {
            return file.length();
        }

        public String getFileName() {
            return String.format("%s-%s", getInstanceID(), new String(Base64.encodeBase64(file.getName().getBytes())));
        }

        public InputStream createInputStream() throws IOException {
            return FileUtils.newFileInputStream(file);
        }

        public String getInputFilename() {
            return contentTypeHint;
        }
    }

	
	public static class ConversionResponse {
		private Integer returnCode = 1000;
		private String message = "";

		public Integer getReturnCode() {
			return returnCode;
		}

		public void setReturnCode(Integer returnCode) {
			this.returnCode = returnCode;
		}

		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}
}
