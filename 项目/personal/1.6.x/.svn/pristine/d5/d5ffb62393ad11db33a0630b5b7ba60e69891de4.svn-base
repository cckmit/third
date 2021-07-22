package org.opoo.apps.dv.office.converter.jive;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.opoo.apps.dv.ConversionArtifactType;
import org.opoo.apps.dv.converter.AbstractConverterFactory;
import org.opoo.apps.dv.converter.Converter;
import org.opoo.apps.dv.office.OfficeConversionArtifactType;
import org.opoo.apps.dv.office.converter.GenericOfficeConverter.ConversionCommand;
import org.opoo.apps.event.v2.EventDispatcher;

import com.jivesoftware.office.command.ConversionCommandExecutor;
/**
 * 
 * @author lcj
 *
 */
public class JiveCommandExecutorConverterFactory extends AbstractConverterFactory{
	private Map<String, ConversionCommandExecutor> executorsMap;
	private EventDispatcher dispatcher;

	public JiveCommandExecutorConverterFactory() {
	}

	public void setExecutorsMap(ConcurrentMap<String, ConversionCommandExecutor> executorsMap) {
		this.executorsMap = executorsMap;
	}

	public void setEventDispatcher(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	protected Converter createConverter(ConversionArtifactType type) {
		ConversionCommand command = ConversionCommand.getConversionCommand((OfficeConversionArtifactType)type);
	   	if(command == null){
	   		throw new IllegalStateException(String.format("Unable to load ConversionCommand for artifact type:%s", type));
        }
	   	ConversionCommandExecutor commandExecutor = (ConversionCommandExecutor)executorsMap.get(type.getName());
        if(commandExecutor == null){
            throw new IllegalStateException(String.format("Unable to load executor for artifact type:%s", type));
		}
	   	return new JiveCommandExecutorConverter(dispatcher, command, commandExecutor);
	}
}
