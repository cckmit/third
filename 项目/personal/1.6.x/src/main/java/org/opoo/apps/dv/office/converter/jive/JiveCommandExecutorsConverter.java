package org.opoo.apps.dv.office.converter.jive;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

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

import com.jivesoftware.office.command.ConversionCommandExecutor;


/**
 * 使用 JiveSoftware的后台程序 docconveter 的 ConversionCommandExecutor 实现的转换器。
 * 本身也相当于 ConversionCommandExecutor 工厂，结合 {@link SpringConverterFactory} 使用。
 * @author lcj
 *
 */
public class JiveCommandExecutorsConverter extends OfficeConverter implements ConverterPrototype{
	private static final Logger log = Logger.getLogger(JiveCommandExecutorsConverter.class);
	private Map<String, ConversionCommandExecutor> executorsMap;
	private EventDispatcher dispatcher;
	private ConversionCommandExecutor commandExecutor;
	private ConversionCommand command;
	
	public void setExecutorsMap(ConcurrentMap<String, ConversionCommandExecutor> executorsMap) {
		this.executorsMap = executorsMap;
	}

	public void setEventDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	public void setConversionArtifactType(ConversionArtifactType type) {
		ConversionCommand command = ConversionCommand.getConversionCommand((OfficeConversionArtifactType)type);
	   	if(command == null){
	   		throw new IllegalStateException(String.format("Unable to load ConversionCommand for artifact type:%s", type));
        }
	   	ConversionCommandExecutor commandExecutor = (ConversionCommandExecutor)executorsMap.get(type.getName());
        if(commandExecutor == null){
            throw new IllegalStateException(String.format("Unable to load executor for artifact type:%s", type));
		}
	   	this.command = command;
	   	this.commandExecutor = commandExecutor;
	}
	
	public void convert(OfficeConversionMetaData cm, InputArtifact inputFile, List<Integer> partNumbers) {
		for(int page: partNumbers){
			try {
				//convert(cm, inputFile, page, command, commandExecutor);
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
}
