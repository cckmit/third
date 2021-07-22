package org.opoo.apps.xml;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class CastorJavaXML implements XMLSerializer {
	public CastorJavaXML() {
	}

	public Object fromXML(Class<?> clazz, String xml) {
		StringReader reader = new StringReader(xml);
		return unmarshal(clazz, reader);
	}

	/**
	 * convert xml to java object
	 * 
	 * @param clazz
	 *            - Class
	 * @param reader
	 *            - Reader
	 * @return Object
	 * 
	 */
	public Object unmarshal(Class<?> clazz, Reader reader) {
		try {
			return Unmarshaller.unmarshal(clazz, reader);
		} catch (Exception ex) {
			throw new XMLException(ex);
		}
	}

	/**
	 * @param object
	 */
	public void marshal(Object object, Writer writer) {
		try {
			Marshaller.marshal(object, writer);
		} catch (Exception ex) {
			throw new XMLException(ex);
		}
	}

	/**
	 * 
	 */
	public String toXML(Object bean) {
		if (bean instanceof XMLSerializable) {
			return ((XMLSerializable) bean).toXMLString();
		}

		StringWriter writer = new StringWriter();
		marshal(bean, writer);
		return writer.toString();
	}

}
