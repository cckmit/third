package org.opoo.apps.file.converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.PartSource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.opoo.apps.dv.connector.HttpClientFactory;
import org.opoo.apps.dv.connector.ServiceHost;
import org.opoo.apps.dv.connector.ServiceHostObjectFactory;
import org.opoo.apps.dv.office.OfficeConversionConfig;
import org.opoo.apps.dv.util.Utils;
import org.opoo.apps.json.jackson.JSONObjectMapper;
import org.opoo.apps.util.FileUtils;
import org.springframework.util.Assert;

public class GenericFileTypeConverter extends AbstractFileTypeConverter {
	private static final Log log = LogFactory.getLog(GenericFileTypeConverter.class);
	
	private OfficeConversionConfig conversionConfig;
	private ServiceHostObjectFactory serviceHostObjectFactory;
	private HttpClientFactory httpClientFactory;
	private ObjectMapper objectMapper = new JSONObjectMapper();
	
	
	private ConversionType conversionType;
	private ConversionCommand command;
	
	public void setConversionConfig(OfficeConversionConfig conversionConfig) {
		this.conversionConfig = conversionConfig;
	}

	public void setServiceHostObjectFactory(
			ServiceHostObjectFactory serviceHostObjectFactory) {
		this.serviceHostObjectFactory = serviceHostObjectFactory;
	}

	public void setHttpClientFactory(HttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	public void setConversionType(ConversionType conversionType){
		this.conversionType = conversionType;
		
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		//Assert.notNull(conversionType, "conversionType is required.");
		if(conversionType != null){
			this.command = ConversionCommand.getConversionCommand(conversionType);
		}
	}
	
	public void convert(File input, File output) throws FileTypeConvertException {
		Assert.notNull(command, "command is required.");
		try {
			convert(command, input, output);
		} catch (Exception e) {
			throw new FileTypeConvertException(String.format("GenericFileTypeConverter Executor failed to complete successfully for file %s to %s", input, output), e);
		}
	}

	public void convert(ConversionCommand command, File input, File output) throws Exception {
		FileDataProvider provider = new FileDataProvider(input, input.getName());
		convert(command, provider, output);
	}
	
	private void convert(ConversionCommand command, ConversionDataProvider conversionDataProvider, File output) throws Exception {
		ServiceHost host = (ServiceHost) serviceHostObjectFactory.makeObject();

		// cache the first file only
        boolean inputFileCached = false;

        HttpClient client = httpClientFactory.getNewClient();
        
        	File target = output;
            try  {
//                target = Utils.createTempFile(String.format("ca-%s-%s-%d-%s", cm.getFilename(),
//                        conversionArtifactType.name(), page, command.getOutExt(), page), "." + command.getOutExt());

                doExecute(client, conversionDataProvider, target, command, inputFileCached, host);

                inputFileCached = true;
                
            } catch (Exception e) {
            	//出错时尝试删除临时文件
            	FileUtils.deleteQuietly(target);
            	
                log.error(String.format("Generic Executor of command %s on file %s failed to complete successfully: %s",
                        command.getOperation(), conversionDataProvider.getFileName(), e.getMessage()), e);
            }
	}


	protected void doExecute(final HttpClient client, final ConversionDataProvider conversionDataProvider, File targetFile,
        ConversionCommand command, boolean inputFileCached, ServiceHost host) throws RuntimeException {
        try {
            if (command == null) {
                throw new RuntimeException(String.format("Can't find command to generate a conversion artifact '%s'",conversionType));
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
            
            int page = 0;
            
            PostMethod post = new PostMethod(String.format("%s://%s:%d%s/execute/%s?inExt=%s&outExt=%s&page=%d&jobID=%s&inputfile=%s", 
            		getServiceScheme(), host.getHost(), getServicePort(), getServiceContext(), cmd/*command.getOperation()*/, inExt, 
                    command.getOutExt(), page, jobID, conversionDataProvider.getInputFilename()));


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
                
            } else {

                log.info(command.getOperation() + " conversion returned error status: " + statusCode);
               
                ConversionResponse commandResponse = null;
                try {
                	 commandResponse = objectMapper.readValue(post.getResponseBodyAsStream(), ConversionResponse.class);
                    log.error(String.format("For file %s conversion failed:errorCode=%s;message=%s;",
                    		conversionDataProvider.getInputFilename(), commandResponse.getReturnCode(), commandResponse.getMessage()));
                }
                catch(Exception e) {
                    log.warn(String.format("Error parsing reponse from the server for file %s and command  %s: %s",
                    		conversionDataProvider.getInputFilename(),  command.getOperation(), post.getResponseBodyAsString()) );
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
        } catch (ConnectException ce) {
            throw new RuntimeException("Couldn't connect to conversion service at " + host.getHost() + ":" + getServicePort() + " for operation " +
                    command.getOperation() + ".", ce);
        }
        catch (IOException ioe) {
            throw new RuntimeException("IO exception performing operation " + command.getOperation() + ".", ioe);
        }
        catch (Exception e) {                
            throw new RuntimeException(command.getOperation() + " failed with unhandled exception: " + e.getMessage(), e);
        } finally {

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
    
    /**
     * Create a new JOB ID to represent this line of work. Multiple calls to this method will never return the same
     * value.
     *
     * @return
     */
    protected String createJobID() {
        return UUID.randomUUID().toString();
    	//return Utils.createJobID();
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
	
	
	 
	
	public enum ConversionType{
		Pdf, Text, Thumbnail, Preview;
	}
	
	public static enum ConversionCommand {
        OFFICETOPDF("office2pdf", ConversionType.Pdf, "application/pdf", "pdf"),
        TEXTEXTRACT("TEXTEXTRACT", ConversionType.Text, "text/plain", "txt"),
        IMAGER("imager", ConversionType.Thumbnail, "image/png", "png"),
        PDF2SWF("pdf2swf", ConversionType.Preview, "application/x-shockwave-flash", "swf");
       
        private static Map<ConversionType, ConversionCommand> COMMANDS;

        static
        {
            Map<ConversionType, ConversionCommand> localCommands = new HashMap<ConversionType, ConversionCommand>();

            for (ConversionCommand command : ConversionCommand.values()) {
                localCommands.put(command.getConversionType(), command);
            }

            COMMANDS = Collections.synchronizedMap(localCommands);
        }

        private String operation;
        private ConversionType conversionArtifactType;
        private String outContentType;
        private String outExt;

        private ConversionCommand (String command, ConversionType conversionArtifactType,
                String outContentType, String outExt) {
            this.operation = command;
            this.conversionArtifactType = conversionArtifactType;
            this.outContentType = outContentType;
            this.outExt = outExt;
            this.outContentType = outContentType;
        }

        public ConversionType getConversionType() {
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

        public static ConversionCommand getConversionCommand(ConversionType conversionArtifactType) {
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
			return contentTypeHint;
            //return String.format("%s-%s", Utils.getInstanceID(), new String(Base64.encodeBase64(file.getName().getBytes())));
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
