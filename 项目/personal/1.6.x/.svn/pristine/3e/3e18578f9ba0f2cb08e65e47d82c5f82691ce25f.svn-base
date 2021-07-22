package org.opoo.apps.ws.jaxrs;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.opoo.apps.json.jackson.JSONObjectMapper;

public class AppsJacksonJsonProvider extends JacksonJsonProvider {
//	/**
//	 * 使用Google Code Jsonplugin 的 日期默认格式，为了和之前的程序
//	 * 保持兼容。
//	 */
//	static final String RFC3339_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	public AppsJacksonJsonProvider() {
		super(new JSONObjectMapper());
	}
	
//	private static ObjectMapper buildObjectMapper(){
//		 AnnotationIntrospector pair = AnnotationIntrospector.pair(new JSONAnnotationIntrospector(), 
//				 new JacksonAnnotationIntrospector());
//		 ObjectMapper mapper = new ObjectMapper();
//		 mapper.setAnnotationIntrospector(pair);
//		 mapper.setDateFormat(new SimpleDateFormat(RFC3339_FORMAT));
//		 return mapper;
//	}
}
