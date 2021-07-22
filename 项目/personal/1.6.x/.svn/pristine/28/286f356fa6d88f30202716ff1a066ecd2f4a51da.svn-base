package org.opoo.apps.dv.office.converter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.opoo.apps.dv.ConversionArtifactType;
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
import org.opoo.apps.dv.office.conversion.HttpClientExecutor;
import org.opoo.apps.dv.office.conversion.HttpClientExecutor.DataProvider;
import org.opoo.apps.dv.office.conversion.HttpClientExecutor.ExecutorCommand;
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
public class GenericOfficeConverter extends OfficeConverter implements Converter, ConverterPrototype{
	//作为每个转换器的标识，一个类型一个转换器实例
	private OfficeConversionArtifactType conversionArtifactType;
	private ConversionCommand command;
    
	private HttpClientExecutor httpClientExecutor;
	private EventDispatcher dispatcher;
	private OfficeConversionStorageService conversionStorageService;
	private ServiceHostObjectFactory serviceHostObjectFactory;
	private OfficeConversionConfig conversionConfig;
    
    private static final Logger log = Logger.getLogger(GenericOfficeConverter.class);

	public void setConversionArtifactType(ConversionArtifactType type) {
		this.conversionArtifactType = (OfficeConversionArtifactType) type;
		this.command = ConversionCommand.getConversionCommand(conversionArtifactType);
	}
	  /**
     * 如果输入是Artifact而不是文件时，使用这个方法进行转换，此时传递给服务端的文件名即cm的文件名。
     * @param cm
     * @param inputCA
     * @param partNumbers
     */
    public void convert(OfficeConversionMetaData cm, OfficeConversionArtifact inputCA, List<Integer> pages){
    	Assert.notNull(inputCA);
    	DataProvider conversionDataProvider = new ConversionArtifactDataProvider(inputCA, conversionStorageService, cm.getFilename());
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
		DataProvider conversionDataProvider = new HttpClientExecutor.FileDataProvider(inputFile, cm.getFilename()/*null*/);
		convert(cm, conversionDataProvider, pages);
	}
	@Override
	public void convert(OfficeConversionMetaData cm, InputArtifact input, List<Integer> pages){
		DataProvider conversionDataProvider = new InputArtifactDataProvider(cm, input);
		convert(cm, conversionDataProvider, pages);
	}
	
	protected void convert(OfficeConversionMetaData cm, DataProvider conversionDataProvider, List<Integer> pages){
		//初始化type时就初始化command
		//ConversionCommand command = getConversionCommand(conversionArtifactType);
		try {
			convert(command, cm, conversionDataProvider, pages);
		}
	    catch (Exception e) {
	    	log.error(String.format("GenericConversionExecutor Executor failed to complete successfully for file %s",
	                    cm.getFilename()), e);
	    	dispatcher.dispatchEvent(new OfficeConversionEvent(OfficeConversionEvent.Type.ERROR,
	            		new ConversionEventPayload(cm, (OfficeConversionArtifact)null, 
	            				e.getMessage(), command.getConversionStep())));
	    }
	}
	private void convert(ConversionCommand command2, OfficeConversionMetaData cm, DataProvider conversionDataProvider, List<Integer> pages) throws Exception {
		// cache the first file only
        boolean inputFileCached = false;
        String service = makeService();
        
        for (Integer page: pages) {
        	File target = null;
            try  {
            	
            	String jobID = createJobID();
                target = Utils.createTempFile(String.format("ca-%s-%s-%d-%s", cm.getFilename(),
                        conversionArtifactType.name(), page, command.getOutExt(),page), "." + command.getOutExt());
                int executeTimeout = (int) conversionConfig.getConversionStepTimeout(command.getOperation());
                
                httpClientExecutor.execute(service, jobID, command.getExecutorCommand(), conversionDataProvider, target, page,
                		inputFileCached, conversionConfig.getAlwaysPassInputFile(), executeTimeout);


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
	
	
	private String makeService() throws Exception{
		ServiceHost host = (ServiceHost) serviceHostObjectFactory.makeObject();
		return String.format("%s://%s:%d%s", getServiceScheme(), host.getHost(), getServicePort(), getServiceContext());
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
	
	public void setHttpClientExecutor(HttpClientExecutor httpClientExecutor) {
		this.httpClientExecutor = httpClientExecutor;
	}

	/**
     * Provide the data for HTTP POST's FilePart using the conversion storage provider
     */
    private static class ConversionArtifactDataProvider implements DataProvider {

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
	
    private class InputArtifactDataProvider implements DataProvider{
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
    
	public static enum ConversionCommand {
        OFFICETOPDF(ExecutorCommand.OFFICETOPDF, OfficeConversionArtifactType.Pdf, OfficeConversionEvent.Type.PDF_GENERATED, OfficeConversionStep.PdfGeneration),
        TEXTEXTRACT(ExecutorCommand.TEXTEXTRACT, OfficeConversionArtifactType.Text, OfficeConversionEvent.Type.TEXT_INDEX_GENERATED, OfficeConversionStep.TextIndexing),
        IMAGER(ExecutorCommand.IMAGER, OfficeConversionArtifactType.Thumbnail, OfficeConversionEvent.Type.THUMBMATIL_PAGE_GENERATED, OfficeConversionStep.ThumbnailGeneration),
        PDF2SWF(ExecutorCommand.PDF2SWF, OfficeConversionArtifactType.Preview, OfficeConversionEvent.Type.PREVIEW_PAGE_GENERATED, OfficeConversionStep.PreviewGeneration);
       
        private static Map<OfficeConversionArtifactType, ConversionCommand> COMMANDS;

        static
        {
            Map<OfficeConversionArtifactType, ConversionCommand> localCommands = new HashMap<OfficeConversionArtifactType, ConversionCommand>();

            for (ConversionCommand command : ConversionCommand.values()) {
                localCommands.put(command.getConversionArtifactType(), command);
            }

            COMMANDS = Collections.synchronizedMap(localCommands);
        }

        private ExecutorCommand executorCommand;
        private OfficeConversionArtifactType conversionArtifactType;
        private OfficeConversionEvent.Type eventType;
        private OfficeConversionStep conversionStep;

        private ConversionCommand (ExecutorCommand command, OfficeConversionArtifactType conversionArtifactType, 
        		OfficeConversionEvent.Type eventType,  OfficeConversionStep step) {
            this.executorCommand = command;
            this.conversionArtifactType = conversionArtifactType;
            this.eventType = eventType;
            this.conversionStep = step;
        }

        public OfficeConversionArtifactType getConversionArtifactType() {
            return conversionArtifactType;
        }

        public ExecutorCommand getExecutorCommand() {
            return executorCommand;
        }
        
        public String getOperation(){
        	return executorCommand.getOperation();
        }

        public String getOutExt() {
            return executorCommand.getOutExt();
        }

        public String getOutContentType() {
            return executorCommand.getContentType();
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
}
