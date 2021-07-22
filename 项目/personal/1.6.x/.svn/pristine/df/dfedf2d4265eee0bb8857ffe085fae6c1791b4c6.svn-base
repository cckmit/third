package org.opoo.apps.xml;

import java.io.Reader;
import java.io.Writer;

/**
 * java和XML转化类。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface XMLSerializer {
	/**
	 * 
	 * @param bean
	 * @return
	 */
    String toXML(Object bean);
	Object fromXML(Class<?> clazz, String xml);

    void marshal(Object bean, Writer writer);
	Object unmarshal(Class<?> clazz, Reader reader);
}
