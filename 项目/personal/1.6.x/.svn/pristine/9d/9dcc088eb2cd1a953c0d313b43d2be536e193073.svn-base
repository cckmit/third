package org.opoo.apps.dv.office.converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.opoo.apps.dv.ConversionArtifactType;
import org.opoo.apps.dv.ConversionException;
import org.opoo.apps.dv.connector.DummyHttpClientFactory;
import org.opoo.apps.dv.connector.HttpClientFactory;
import org.opoo.apps.dv.connector.ServiceHost;
import org.opoo.apps.dv.connector.ServiceHostObjectFactory;
import org.opoo.apps.dv.converter.Converter;
import org.opoo.apps.dv.converter.ConverterPrototype;
import org.opoo.apps.dv.converter.InputArtifact;
import org.opoo.apps.dv.converter.SpringConverterFactory;
import org.opoo.apps.dv.office.OfficeConversionArtifact;
import org.opoo.apps.dv.office.OfficeConversionArtifactType;
import org.opoo.apps.dv.office.OfficeConversionConfig;
import org.opoo.apps.dv.office.OfficeConversionMetaData;
import org.opoo.apps.dv.office.OfficeConversionStep;
import org.opoo.apps.dv.office.OfficeConversionStorageService;
import org.opoo.apps.dv.office.event.OfficeConversionEvent;
import org.opoo.apps.dv.office.event.OfficeConversionEvent.ConversionEventPayload;
import org.opoo.apps.dv.office.model.OfficeArtifact;
import org.opoo.apps.dv.util.Utils;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.util.FileUtils;
import org.opoo.util.Assert;

/**
 * 使用 HttpClient 调用远程 Web 服务来进行文件转换。
 * 该功能是转换服务器的客户端实例，该转换器支持的转换类型直接受
 * 转换服务器支持的类型限制。
 * 
 * <p>由于该功能只是一个Web客户端，所以在当前应用中可以不安装或集成
 * 文件类型转换相关的代码，减少依赖。
 * 
 * <p>可结合工厂类 {@link SpringConverterFactory}使用。
 * @author lcj
 */
public class GenericOfficeConverter_BAK extends OfficeConverter implements Converter, ConverterPrototype{
    private EventDispatcher dispatcher;
    private HttpClientFactory httpClientFactory = new DummyHttpClientFactory();
    private ServiceHostObjectFactory serviceHostObjectFactory;
    private OfficeConversionConfig conversionConfig;
	private OfficeConversionStorageService conversionStorageService;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	//作为每个转换器的标识，一个类型一个转换器实例
	private OfficeConversionArtifactType conversionArtifactType;
	private ConversionCommand command;
	
    private static final Logger log = Logger.getLogger(GenericOfficeConverter_BAK.class);
    
    /**
     * 该类的实例创建后，将调用这个方法设置该转换器可以转换的类型。
     * @param conversionArtifactType 可转换的工件类型
     */
	public void setConversionArtifactType(ConversionArtifactType conversionArtifactType) {
        this.conversionArtifactType = (OfficeConversionArtifactType)conversionArtifactType;
        this.command = getConversionCommand(this.conversionArtifactType);
    }
    
    /**
     * 如果输入是Artifact而不是文件时，使用这个方法进行转换，此时传递给服务端的文件名即cm的文件名。
     * @param cm
     * @param inputCA
     * @param partNumbers
     */
    public void convert(OfficeConversionMetaData cm, OfficeConversionArtifact inputCA, List<Integer> pages){
    	Assert.notNull(inputCA);
    	ConversionDataProvider conversionDataProvider 
    		= new ConversionArtifactDataProvider(inputCA, conversionStorageService, cm.getFilename());
		convert(cm, conversionDataProvider, pages);
    }
    
    /**
     * 如果要充分利用服务端的文件缓存机制（不用每次都接收客户端的数据流）则需要保证同一个输入文件时，输入文件的文件名称不能变。
     */
	public void convert(OfficeConversionMetaData cm, File inputFile, List<Integer> pages) {
		/*
		if(inputFile == null || !inputFile.exists() || !inputFile.canRead() || inputFile.length() == 0){
			log.error("输入文件无效：" + inputFile);
			dispatcher.dispatchEvent(new OfficeConversionEvent(OfficeConversionEvent.Type.ERROR,
            		new ConversionEventPayload(cm, (OfficeConversionArtifact)null, "输入文件无效", null)));
			return;
		}*/
		ConversionDataProvider conversionDataProvider = new FileDataProvider(inputFile, cm.getFilename()/*null*/);
		convert(cm, conversionDataProvider, pages);
	}
	
	public void convert(OfficeConversionMetaData cm, InputArtifact input, List<Integer> pages){
		ConversionDataProvider conversionDataProvider = new InputArtifactDataProvider(cm, input);
		convert(cm, conversionDataProvider, pages);
	}
	
	protected void convert(OfficeConversionMetaData cm, ConversionDataProvider conversionDataProvider, List<Integer> pages){
		//初始化type时就初始化command
		//ConversionCommand command = getConversionCommand(conversionArtifactType);
		try {
			convert(command, cm,conversionDataProvider, pages);
		}
	    catch (Exception e) {
	    	log.error(String.format("GenericConversionExecutor Executor failed to complete successfully for file %s",
	                    cm.getFilename()), e);

	    	dispatcher.dispatchEvent(new OfficeConversionEvent(OfficeConversionEvent.Type.ERROR,
	            		new ConversionEventPayload(cm, (OfficeConversionArtifact)null, 
	            				e.getMessage(), command.getConversionStep())));
	    }
	}
	    
	private void convert(ConversionCommand command,
			OfficeConversionMetaData cm, ConversionDataProvider conversionDataProvider, List<Integer> pages) throws Exception {
		ServiceHost host = (ServiceHost) serviceHostObjectFactory.makeObject();
		
		// cache the first file only
        boolean inputFileCached = false;

        HttpClient client = httpClientFactory.getNewClient();
        
        for (Integer page: pages) {
        	File target = null;
            try  {
                target = Utils.createTempFile(String.format("ca-%s-%s-%d-%s", cm.getFilename(),
                        conversionArtifactType.name(), page, command.getOutExt(),page), "." + command.getOutExt());

                doExecute(client, conversionDataProvider, target, page, command, inputFileCached, cm, host);

//                String filename = cm.getFilename() + ".p" + page + "." + command.getOutExt();
                OfficeConversionArtifact conversionResult = new OfficeArtifact(Utils.getInstanceID(),
	                    cm.getId(), command.getConversionArtifactType(), target.length(), null, command.getOutContentType(), page);


                ConversionEventPayload payload = new ConversionEventPayload(cm, conversionResult, target);
                dispatcher.dispatchEvent(new OfficeConversionEvent(command.getEventType(), payload));
                inputFileCached = true;
                
            } catch (Exception e) {
            	//出错时尝试删除临时文件
            	FileUtils.deleteQuietly(target);
            	
                log.error(String.format("Generic Executor of command %s on file %s and page %d failed to complete successfully: %s",
                        command.getOperation(), cm.getFilename(), page, e.getMessage()), e);

                dispatcher.dispatchEvent(new OfficeConversionEvent(OfficeConversionEvent.Type.ERROR,
	            		new ConversionEventPayload(cm, (OfficeConversionArtifact)null, 
	            				e.getMessage(), command.getConversionStep())));
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
//            if(OfficeConversionArtifactType.Preview == conversionArtifactType && page == 0){
//           		System.out.println("----------------------PDF2SWFPBM————————————————————————");
//            	cmd = "PDF2SWFPBM";
//            }
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
                	 commandResponse = objectMapper.readValue(post.getResponseBodyAsStream(), ConversionResponse.class);
                    log.error(String.format("For file %s conversion failed:errorCode=%s;message=%s;",
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
                    response = String.format("Error code: %s  Message: %s ", ConversionError.getConversionError(commandResponse.getReturnCode()),
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
        //return UUID.randomUUID().toString();
    	return Utils.createJobID();
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

    public void setEventDispatcher(EventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void setHttpClientFactory(HttpClientFactory httpClientFactory) {
        this.httpClientFactory = httpClientFactory;
    }
    
    public void setConversionConfig(OfficeConversionConfig conversionConfig) {
		this.conversionConfig = conversionConfig;
	}


    public void setConversionStorageService(OfficeConversionStorageService conversionStorageService) {
		this.conversionStorageService = conversionStorageService;
	}

	public void setServiceHostObjectFactory(
			ServiceHostObjectFactory serviceHostObjectFactory) {
		this.serviceHostObjectFactory = serviceHostObjectFactory;
	}
	
	public void setObjectMapper(ObjectMapper mapper){
		this.objectMapper = mapper;
	}

	public static enum ConversionCommand {
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
        /**
         * 服务端会根据这个名字判断是否需要从缓存中读取数据，而不是每次都从客户端读取数据流。
         * 所以这个名字在一个转换中保持唯一性很重要。
         */
        String getFileName();
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
    
    private class InputArtifactDataProvider implements ConversionDataProvider{
    	private final OfficeConversionMetaData cm;
    	private final InputArtifact input;
		
    	InputArtifactDataProvider(OfficeConversionMetaData cm, InputArtifact input) {
			super();
			this.cm = cm;
			this.input = input;
		}
		public InputStream createInputStream() throws IOException {
			return FileUtils.newFileInputStream(input.getFile());
		}
		public long getLength() {
			return input.getFile().length();
		}
		public String getFileName() {
			//return String.format("i%s_r%d_t%s_p%d", instanceID, conversionMetadataID, type.getName(), page);
			String s = String.format("%s-%s-%s.%s", Utils.getInstanceID(), cm.getId(), input.getPartNumber(), input.getType().getName());
			//System.out.println("FileName: " + s);
			return s;
		}
		
		public String getInputFilename() {
			return cm.getFilename();
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
            return String.format("%s-%s", Utils.getInstanceID(), new String(Base64.encodeBase64(file.getName().getBytes())));
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
	
	public static enum ConversionError {
	    GeneralError(1000, "Generic Conversion Error");

	    private int errorCode;
	    private String description;

	    private ConversionError(int errorCode, String description) {
	        this.errorCode = errorCode;
	        this.description = description;
	    }

	    public int getErrorCode() {
	        return errorCode;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public static ConversionError getConversionError(int errorCode) {

	        for (ConversionError code: ConversionError.values()) {
	            if (code.getErrorCode() == errorCode) {
	                return code;
	            }
	        }

	        return null;

	    }
	    
	    @Override
	    public String toString() {
	        return "ConversionError{" + "errorCode=" + errorCode + ", description='" + description + '\'' + '}';
	    }
	}
}
