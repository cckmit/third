package org.opoo.apps.dv.office.converter.jive;

import java.util.List;

import org.apache.log4j.Logger;
import org.opoo.apps.dv.converter.InputArtifact;
import org.opoo.apps.dv.office.OfficeConversionArtifact;
import org.opoo.apps.dv.office.OfficeConversionMetaData;
import org.opoo.apps.dv.office.converter.GenericOfficeConverter.ConversionCommand;
import org.opoo.apps.dv.office.converter.OfficeConverter;
import org.opoo.apps.dv.office.event.OfficeConversionEvent;
import org.opoo.apps.dv.office.event.OfficeConversionEvent.ConversionEventPayload;
import org.opoo.apps.event.v2.EventDispatcher;

import com.jivesoftware.office.command.ConversionCommandExecutor;


/**
 * 使用 JiveSoftware的后台程序 docconveter 的 ConversionCommandExecutor 实现的转换器。
 * 可以将每个转换器和一个 ConversionCommandExecutor 绑定。
 * @author lcj
 *
 */
public class JiveCommandExecutorConverter extends OfficeConverter {
	private static final Logger log = Logger.getLogger(JiveCommandExecutorConverter.class);
	
	private EventDispatcher dispatcher;
	private ConversionCommandExecutor commandExecutor;
	private ConversionCommand command;
	
	/**
	 * 将每个转换器和每个CommandExecutor绑定。
	 * @param dispatcher
	 * @param command
	 * @param commandExecutor
	 */
	public JiveCommandExecutorConverter(EventDispatcher dispatcher, ConversionCommand command, ConversionCommandExecutor commandExecutor){
		this.dispatcher = dispatcher;
		this.command = command;
		this.commandExecutor = commandExecutor;
	}
	
	public void convert(OfficeConversionMetaData cm, InputArtifact inputFile, List<Integer> partNumbers) {
		for(int page: partNumbers){
			try {
				/*convert(cm, inputFile, page, command, commandExecutor);*/
				JiveConversionUtils.convert(cm, inputFile.getFile(), page, command, commandExecutor, dispatcher);
			} catch (Exception e) {
				log.error(String.format("Generic Executor of command %s on file %s and page %d failed to complete successfully: %s",
                        command.getOperation(), cm.getFilename(), page, e.getMessage()));

                dispatcher.dispatchEvent(new OfficeConversionEvent(OfficeConversionEvent.Type.ERROR,
	            		new ConversionEventPayload((OfficeConversionMetaData)cm, (OfficeConversionArtifact)null, 
	            				e.getMessage(), command.getConversionStep())));
			}
		}
	}
	/*
	protected void convert(OfficeConversionMetaData cm,	InputArtifact inputFile, int page, ConversionCommand command,
			ConversionCommandExecutor commandExecutor) throws Exception {
		final String jobID = createJobID();
		//conversionCommandExecutor.execute(jobID, input, page, outExt);
		CommandExecutionResult result = commandExecutor.execute(jobID, inputFile.getFile(), page, command.getOutExt());
		if(result.isSuccessful()){
			File target = result.getResult();
			OfficeConversionArtifact conversionResult = new OfficeArtifact(Utils.getInstanceID(),
                    cm.getId(), command.getConversionArtifactType(), target.length(), null, command.getOutContentType(), page);

			ConversionEventPayload payload = new ConversionEventPayload(cm, conversionResult, target);
			dispatcher.dispatchEvent(new OfficeConversionEvent(command.getEventType(), payload));
		}else{
			String error = String.format("Error executing command %s for jobID %s and page %d: %s", command, jobID, page, result.getError());
			//log.error(error);
			throw new ConversionException(error);
		}
	}

	protected String createJobID() {
		//return UUID.randomUUID().toString();
		return Utils.createJobID();
	}
	*/
}
