package org.opoo.apps.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.opoo.apps.json.JSONSerializer;
import org.opoo.apps.json.JacksonJSONSerializerFactory;
import org.opoo.apps.json.jackson.JSONObjectMapper;
import org.opoo.apps.xml.XMLSerializer;
import org.opoo.apps.xml.XStreamXMLSerializerFactory;

public abstract class SerializableUtils {
//	private static ObjectMapper objectMapper = new JSONObjectMapper();
	static XMLSerializer xmlSerializer = new XStreamXMLSerializerFactory().getXMLSerializer();
	static JSONSerializer jsonSerializer;// = new JacksonJSONSerializerFactory().getJSONSerializer();
	//new DefaultJSONSerializer();
	
	static{
		setObjectMapper(new JSONObjectMapper());
	}
	
	public static void setObjectMapper(ObjectMapper objectMapper){
		JacksonJSONSerializerFactory factory = new JacksonJSONSerializerFactory();
		factory.setObjectMapper(objectMapper);
		jsonSerializer = factory.getJSONSerializer();
	}
	
//	private static XStream xstream;
//	
//	
//	protected static XStream getJSONXStream() {
//        XStream xs = new XStream(new JettisonMappedXmlDriver());
//        xs.setMode(XStream.NO_REFERENCES);
//        return xs;
//    }
//	
//	protected static XStream getXStream(){
//		if(xstream == null){
//			xstream = new XStream();
//		}
//		return xstream;
//	}
	
	public static String toXML(Object bean){
		return xmlSerializer.toXML(bean);
	}
	
	public static String toJSON(Object bean){
		return jsonSerializer.toJSON(bean);
	}

	public static void setXMLSerializer(XMLSerializer xmlSerializer) {
		SerializableUtils.xmlSerializer = xmlSerializer;
	}

	public static void setJSONSerializer(JSONSerializer jsonSerializer) {
		SerializableUtils.jsonSerializer = jsonSerializer;
	}
}
