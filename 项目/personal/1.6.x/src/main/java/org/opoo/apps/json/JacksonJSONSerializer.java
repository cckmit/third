package org.opoo.apps.json;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class JacksonJSONSerializer implements JSONSerializer{
	private static final Log log = LogFactory.getLog(JacksonJSONSerializer.class);
	private ObjectMapper objectMapper;
	
	public JacksonJSONSerializer(ObjectMapper objectMapper) {
		super();
		this.objectMapper = objectMapper;
	}
	
	public String toJSON(Object object) {
		try {
			ObjectMapper mapper = objectMapper;
			StringWriter writer = new StringWriter();
			//JsonEncoding enc = JsonEncoding.UTF8;
			JsonGenerator jg = mapper.getJsonFactory().createJsonGenerator(writer/* , enc */);
			//jg.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
			//jg.enable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);

	        // Want indentation?
	        if (mapper.getSerializationConfig().isEnabled(SerializationConfig.Feature.INDENT_OUTPUT)) {
	            jg.useDefaultPrettyPrinter();
	        }
	        
		    mapper.writeValue(jg, object);
			return writer.toString();
			//return objectMapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			log.error("JSON����ʱ����", e);
			throw new RuntimeException("JSON����ʱ����", e);
		} catch (JsonMappingException e) {
			log.error("JSONӳ��ʱ����", e);
			throw new RuntimeException("JSONӳ��ʱ����", e);
		} catch (IOException e) {
			log.error("JSON IO����", e);
			throw new RuntimeException("JSON IO����", e);
		} catch(RuntimeException e){
			log.error("����JSON�ַ���ʱ��������", e);
			throw e;
		}
	}

	public Object fromJSON(String json) {
		try {
			return objectMapper.readTree(json);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
