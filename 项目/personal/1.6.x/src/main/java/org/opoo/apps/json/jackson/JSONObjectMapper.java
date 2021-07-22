package org.opoo.apps.json.jackson;

import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializerProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;


public class JSONObjectMapper extends ObjectMapper {
	/**
	 * 使用Google Code Jsonplugin 的 日期默认格式，为了和之前的程序
	 * 保持兼容。
	 */
	static final String RFC3339_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	static final SimpleDateFormat RFC3339_FORMAT_INSTANCE = new SimpleDateFormat(RFC3339_FORMAT);

	public JSONObjectMapper() {
		super();
		init();
	}

	public JSONObjectMapper(JsonFactory jf, SerializerProvider sp,
			DeserializerProvider dp, SerializationConfig sconfig,
			DeserializationConfig dconfig) {
		super(jf, sp, dp, sconfig, dconfig);
		init();
	}

	public JSONObjectMapper(JsonFactory jf, SerializerProvider sp,
			DeserializerProvider dp) {
		super(jf, sp, dp);
		init();
	}

	public JSONObjectMapper(JsonFactory jf) {
		super(jf);
		init();
	}
	
	/**
	 * 将ObjectMapper设置成和Google plugin兼容的处理方式。
	 */
	private void init() {
		AnnotationIntrospector pair = AnnotationIntrospector.pair(
				new JSONAnnotationIntrospector(),
				new JacksonAnnotationIntrospector());
		setAnnotationIntrospector(pair);
		setDateFormat(RFC3339_FORMAT_INSTANCE);
	}
}
