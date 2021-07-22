package org.opoo.apps.dv.office.converter.jive;

import java.util.List;

import org.apache.log4j.Logger;
import org.opoo.apps.dv.ConversionArtifactType;
import org.opoo.apps.dv.converter.ConverterPrototype;
import org.opoo.apps.dv.converter.InputArtifact;
import org.opoo.apps.dv.converter.SpringConverterFactory;
import org.opoo.apps.dv.office.OfficeConversionArtifact;
import org.opoo.apps.dv.office.OfficeConversionArtifactType;
import org.opoo.apps.dv.office.OfficeConversionMetaData;
import org.opoo.apps.dv.office.converter.GenericOfficeConverter.ConversionCommand;
import org.opoo.apps.dv.office.converter.OfficeConverter;
import org.opoo.apps.dv.office.event.OfficeConversionEvent;
import org.opoo.apps.dv.office.event.OfficeConversionEvent.ConversionEventPayload;
import org.opoo.apps.event.v2.EventDispatcher;

import com.jivesoftware.office.manager.ConversionCommandType;
import com.jivesoftware.office.manager.ConversionManager;


/**
 * 使用 JiveSoftware的后台程序 docconveter 实现的转换器，可结合工厂类 {@link SpringConverterFactory}使用。
 * @author lcj
 *
 */
public class JiveConversionManagerConverter extends OfficeConverter implements ConverterPrototype {
	private static final Logger log = Logger.getLogger(JiveConversionManagerConverter.class);

	private EventDispatcher dispatcher;
	private ConversionManager conversionManager;
	private OfficeConversionArtifactType conversionArtifactType;

	
	private ConversionCommand command;
	private ConversionCommandType commandType;

	public void setEventDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void setConversionManager(ConversionManager conversionManager) {
		this.conversionManager = conversionManager;
	}

	public void setConversionArtifactType(ConversionArtifactType type) {
		this.conversionArtifactType = (OfficeConversionArtifactType)type;
		command = ConversionCommand.getConversionCommand(conversionArtifactType);
		commandType = ConversionCommandType.getByName(command.name());
		if(commandType == null){
			throw new IllegalStateException(String.format("Command %s is not valid", command));
		}
	}

	public void convert(OfficeConversionMetaData cm, InputArtifact inputFile, List<Integer> partNumbers) {
		for(int page: partNumbers){
			try {
				//convert(cm, inputFile, page, command, commandType, conversionManager);
				JiveConversionUtils.convert(cm, inputFile.getFile(), page, command, commandType, conversionManager, dispatcher);
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
			ConversionCommandType commandType, ConversionManager conversionManager) throws Exception {
		final String jobID = createJobID();
		// executeCommand(jobID, commandType, input, page, outExt);
		CommandExecutionResult result = conversionManager.executeCommand(jobID,	commandType, inputFile.getFile(), page, command.getOutExt());
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
