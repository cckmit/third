package org.opoo.apps.xml;

import java.io.Reader;
import java.io.Writer;

import com.thoughtworks.xstream.XStream;


public class XStreamXMLSerializer implements XMLSerializer {
	private XStream xs = new XStream();
	
	public String toXML(Object bean) {
		return xs.toXML(bean);
	}

	public Object fromXML(Class<?> clazz, String xml) {
		return xs.fromXML(xml);
	}

	public void marshal(Object bean, Writer writer) {
		xs.toXML(bean, writer);
	}

	public Object unmarshal(Class<?> clazz, Reader reader) {
		return xs.fromXML(reader);
	}
}
