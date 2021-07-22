package org.opoo.apps.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.opoo.apps.json.jackson.JSONObjectMapper;

public class JacksonJSONSerializerFactory implements JSONSerializerFactory {
	private ObjectMapper objectMapper;
	
	public JacksonJSONSerializerFactory(ObjectMapper objectMapper) {
		super();
		this.objectMapper = objectMapper;
	}

	public JacksonJSONSerializerFactory() {
		super();
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public JSONSerializer getJSONSerializer() {
		if(objectMapper == null){
			objectMapper = new JSONObjectMapper();
		}
		return new JacksonJSONSerializer(objectMapper);
	}
}
