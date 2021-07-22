package org.opoo.apps.dv.office.converter.jive;

import java.io.File;

import org.opoo.apps.dv.ConversionException;
import org.opoo.apps.dv.office.OfficeConversionArtifact;
import org.opoo.apps.dv.office.OfficeConversionMetaData;
import org.opoo.apps.dv.office.converter.GenericOfficeConverter.ConversionCommand;
import org.opoo.apps.dv.office.event.OfficeConversionEvent;
import org.opoo.apps.dv.office.event.OfficeConversionEvent.ConversionEventPayload;
import org.opoo.apps.dv.office.model.OfficeArtifact;
import org.opoo.apps.dv.util.Utils;
import org.opoo.apps.event.v2.EventDispatcher;

import com.jivesoftware.office.command.ConversionCommandExecutor;
import com.jivesoftware.office.command.ConversionCommandExecutor.CommandExecutionResult;
import com.jivesoftware.office.manager.ConversionCommandType;
import com.jivesoftware.office.manager.ConversionManager;

public class JiveConversionUtils {
	
	
	/**
	 * 调用ConversionCommandExecutor实现文件转换。
	 * @param cm
	 * @param inputFile
	 * @param page
	 * @param command
	 * @param commandExecutor
	 * @param dispatcher
	 * @throws Exception
	 */
	static void convert(OfficeConversionMetaData cm, File inputFile, int page, ConversionCommand command,
			ConversionCommandExecutor commandExecutor, EventDispatcher dispatcher) throws Exception {
		final String jobID = Utils.createJobID();
		//conversionCommandExecutor.execute(jobID, input, page, outExt);
		CommandExecutionResult result = commandExecutor.execute(jobID, inputFile, page, command.getOutExt());
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
	
	/**
	 * 调用ConversionManager实现文件转换。
	 * @param cm
	 * @param inputFile
	 * @param page
	 * @param command
	 * @param commandType
	 * @param conversionManager
	 * @param dispatcher
	 * @throws Exception
	 */
	static void convert(OfficeConversionMetaData cm, File inputFile, int page, ConversionCommand command,
			ConversionCommandType commandType, ConversionManager conversionManager, EventDispatcher dispatcher) throws Exception {
		final String jobID = Utils.createJobID();
		// executeCommand(jobID, commandType, input, page, outExt);
		CommandExecutionResult result = conversionManager.executeCommand(jobID,	commandType, inputFile, page, command.getOutExt());
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
}
