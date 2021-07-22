package org.opoo.apps.dv.office.conversion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;

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
import org.opoo.apps.dv.connector.DummyHttpClientFactory;
import org.opoo.apps.dv.connector.HttpClientFactory;
import org.opoo.apps.dv.util.Utils;
import org.opoo.apps.util.FileUtils;

public class HttpClientExecutor {
	private static final Logger log = Logger.getLogger(HttpClientExecutor.class);
	
	private HttpClientFactory httpClientFactory = new DummyHttpClientFactory();
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public HttpClientExecutor(HttpClientFactory httpClientFactory) {
		super();
		this.httpClientFactory = httpClientFactory;
	}

//	protected long getConversionTimeout(String operation){
//		return 120000L;
//	}
//	
//	public void execute(Command command, File input, File target, int page) throws ExecutorException{
//		String jobID = Utils.createJobID();
//		FileDataProvider provider = new FileDataProvider(input, input.getName());
//		execute(jobID, command, provider, target, page, true, false, 120000L);
//	}
	
//	public void setHttpClientFactory(HttpClientFactory httpClientFactory) {
//		this.httpClientFactory = httpClientFactory;
//	}

//	public void setServiceAddresses(List<String> serviceAddresses) {
//		this.serviceAddressFactory = new ServiceAddressFactory();
//		this.serviceAddressFactory.setServiceAddresses(serviceAddresses);
//	}

//	public void setObjectMapper(ObjectMapper objectMapper) {
//		this.objectMapper = objectMapper;
//	}

	
	
	public void execute(String service, String jobID,
			ExecutorCommand command,
			final DataProvider conversionDataProvider, File targetFile,
			int page, boolean inputFileCached, boolean alwaysPassInputFile,
			int executeTimeout) throws ConversionException {
		//(String jobID, ConversionCommandType type, File input, int page, String outExt)
		
//		String service = serviceAddressFactory.makeObject();
		HttpClient client = httpClientFactory.getNewClient();
        try {
            if (command == null) {
                throw new RuntimeException(String.format("Can't find command to generate a conversion artifact '%s'", targetFile));
            }

            //final String jobID = createJobID();             

            client.getParams().setParameter("http.socket.timeout", executeTimeout);
            
            /* service definition:
            @PathParam("command") String command, @QueryParam("jobID") String jobID, @QueryParam("page") int page,
                 @QueryParam("inExt") String inExt, @QueryParam("outExt") String outExt, MultipartBody body
             */

            String inExt =  conversionDataProvider.getInputFilename() != null && conversionDataProvider.getInputFilename().contains(".") ?
                    conversionDataProvider.getInputFilename().substring(conversionDataProvider.getInputFilename().lastIndexOf('.')) : "";
           
            PostMethod post = new PostMethod(String.format("%s/execute/%s?inExt=%s&outExt=%s&page=%d&jobID=%s&inputfile=%s", 
            		service, command.getOperation(), inExt, command.getOutExt(), page, jobID, conversionDataProvider.getFileName()));


            // only pass the file to the first page unless configured to always which can be a requirement for load balancing
            Part[] parts = !inputFileCached || alwaysPassInputFile ? new Part[] {new FilePart("file", conversionDataProvider)} : new Part[0];

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
        }
        catch (ConnectException ce) {
            throw new ConversionException("Couldn't connect to conversion service at " + service + " for operation " +
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
	 
	
//	private static class ServiceAddressFactory {
//		private List<String> serviceAddresses;
//
//		public String makeObject() {
//			return randomItem(serviceAddresses);
//		}
//
//		public void setServiceAddresses(List<String> serviceAddresses) {
//			this.serviceAddresses = serviceAddresses;
//		}
//
//		private String randomItem(List<String> items) {
//			int idx = (int) ((Math.random() * 100 * items.size()) / 100);
//			if (idx >= items.size()) {
//				idx = items.size() - 1;
//			}
//			return items.get(idx);
//		}
//	}
	
	public static enum ExecutorCommand {
        OFFICETOPDF("office2pdf", "application/pdf", "pdf"),
        TEXTEXTRACT("TEXTEXTRACT", "text/plain", "txt"),
        IMAGER("imager", "image/png", "png"),
        PDF2SWF("pdf2swf", "application/x-shockwave-flash", "swf");
        
        private final String operation;
        private final String contentType;
        private final String outExt;
		private ExecutorCommand(String operation, String contentType, String outExt) {
			this.operation = operation;
			this.contentType = contentType;
			this.outExt = outExt;
		}
		
		public String getOperation() {
			return operation;
		}
		public String getContentType() {
			return contentType;
		}
		public String getOutExt() {
			return outExt;
		}
	}
    


	/**
     * The interface to hide the data provider for Document conversion CA
     * or Text extraction
     */
    public interface DataProvider extends PartSource {
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
    public static class FileDataProvider implements DataProvider {
        private final File file;
        private final String contentTypeHint;

        public FileDataProvider(File file, String contentTypeHint)   {
            this.file = file;
            this.contentTypeHint = contentTypeHint;
        }

        public long getLength() {
            return file.length();
        }

        public String getFileName() {
//            return String.format("%s-%s", JiveGlobals.getJiveProperty(JiveGlobals.JIVE_INSTANCE_ID),
//                    new String(Base64.encodeBase64(file.getName().getBytes())));
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
	
	public static class ConversionException extends Exception{
		private static final long serialVersionUID = -2937702379015161879L;

		public ConversionException() {
			super();
		}

		public ConversionException(String message, Throwable cause) {
			super(message, cause);
		}

		public ConversionException(String message) {
			super(message);
		}

		public ConversionException(Throwable cause) {
			super(cause);
		}
	}
}
