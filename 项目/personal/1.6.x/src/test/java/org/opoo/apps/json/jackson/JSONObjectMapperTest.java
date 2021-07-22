package org.opoo.apps.json.jackson;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.bean.core.Property;
import org.opoo.apps.util.SerializableUtils;

public class JSONObjectMapperTest {

	static ObjectMapper mapper = new JSONObjectMapper();
	
	@Test
	public void testSer0() throws IOException{
		Property p = new Property();
		p.setId("222");
		p.setValue("333");
		
		StringWriter w = new StringWriter();
		mapper.writeValue(w, p);
	
		System.out.println(w);
	
		Attachment a = new Attachment();
		a.setId(1000L);
		a.setContentType("application/json");
		a.setFileName("sssssssss");
		mapper.writeValue(w, a);
		System.out.println(w);
	}
	
	@Test
	public void testSer1(){
		String json = SerializableUtils.toJSON(new Attachment());
		System.out.println(json);
	}
	
}
