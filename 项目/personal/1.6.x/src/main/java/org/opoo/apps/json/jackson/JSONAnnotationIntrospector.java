package org.opoo.apps.json.jackson;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.introspect.Annotated;
import org.codehaus.jackson.map.introspect.AnnotatedConstructor;
import org.codehaus.jackson.map.introspect.AnnotatedField;
import org.codehaus.jackson.map.introspect.AnnotatedMember;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;
import org.codehaus.jackson.map.introspect.NopAnnotationIntrospector;

import com.googlecode.jsonplugin.annotations.JSON;


public class JSONAnnotationIntrospector extends NopAnnotationIntrospector {
	private static final Log log = LogFactory.getLog(JSONAnnotationIntrospector.class);

	protected boolean _isIgnorable(Annotated a) {
		//System.out.println("_isIgnorable: " + a.getName() + " " + a.getRawType()  + " " + Date.class.isAssignableFrom(a.getRawType()));
		AnnotatedElement annotated = a.getAnnotated();
		if(annotated != null){
			JSON json = annotated.getAnnotation(JSON.class);
			//System.out.println("JSON: " + json);
			if(json != null){
				String name = a.getName();
				if(name.startsWith("get") && !json.serialize()){
					if(log.isDebugEnabled()){
						log.debug("标记了JSON(serialize=false)，忽略该属性：" + annotated);
					}
					return true;
				}
				if(name.startsWith("set") && !json.deserialize()){
					if(log.isDebugEnabled()){
						log.debug("标记了JSON(deserialize=false)，忽略该属性：" + annotated);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean hasIgnoreMarker(AnnotatedMember member) {
		return _isIgnorable(member);
	}

	@Override
	public boolean isIgnorableConstructor(AnnotatedConstructor c) {
		return _isIgnorable(c);
	}

	@Override
	public boolean isIgnorableMethod(AnnotatedMethod m) {
		return _isIgnorable(m);
	}

	@Override
	public boolean isIgnorableField(AnnotatedField f) {
		return _isIgnorable(f);
	}

	@Override
	public Object findSerializer(Annotated am) {
		//System.out.println("findSerializer: " + am.getName() + " " + am.getRawType()  + " " + Date.class.isAssignableFrom(am.getRawType()));
		AnnotatedElement annotated = am.getAnnotated();
		//处理自定义的日志序列化格式
		if(annotated != null && Date.class.isAssignableFrom(am.getRawType())){
			final JSON json = annotated.getAnnotation(JSON.class);
			if(json != null){
				if(json.serialize() && json.format() != null && !"".equals(json.format().trim())){
					return new DateSerializer(json.format());
				}
			}
		}
		return super.findSerializer(am);
	}

	@Override
	public Object findDeserializer(Annotated am) {
		//System.out.println("findDeserializer: " + am.getName() + " " + am.getRawType()  + " " + Date.class.isAssignableFrom(am.getRawType()));
		AnnotatedElement annotated = am.getAnnotated();
		//处理自定义的日志序列化格式
		if(annotated != null){
			final JSON json = annotated.getAnnotation(JSON.class);
			if(json != null && json.format() != null && !"".equals(json.format().trim())){
				return new DateDeserializer(json.format());
			}
		}
		return super.findDeserializer(am);
	}

	private static class DateSerializer extends JsonSerializer<Date>{
		private final SimpleDateFormat formatter;
		DateSerializer(String format) {
			super();
			formatter = new SimpleDateFormat(format);
		}
		@Override
		public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) 
				throws IOException, JsonProcessingException {
	        String formattedDate = formatter.format(value);   
	        jgen.writeString(formattedDate);  
	        if(log.isDebugEnabled()){
				log.debug("format date to string: " + value);
			}
		}
	}
	
	private static class DateDeserializer extends JsonDeserializer<Date>{
		private final SimpleDateFormat formatter;
		DateDeserializer(String format) {
			super();
			formatter = new SimpleDateFormat(format);
		}
		@Override
		public Date deserialize(JsonParser jp, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			if(log.isDebugEnabled()){
				log.debug("parse date from string: " + jp.getText());
			}
			try {
				return formatter.parse(jp.getText());
			} catch (ParseException e) {
				throw new IOException(e);
			}
		}
	}
}
