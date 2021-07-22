package org.opoo.apps.xml;


import java.io.Reader;
import java.io.Writer;

import org.opoo.apps.util.SerializableUtils;


/**
 * XML学序列化类。
 * <p>即将删除，留下仅仅为了兼容一些编译错误。
 * 
 * @author lcj
 * @deprecated using {@link XMLSerializerFactory} and {@link SerializableUtils} for replacement.
 */
public abstract class JavaXMLFactory implements XMLSerializerFactory{
//	public static final String JAVA_XML_FACTORY = "javaXMLFactory.className";
//	private static final Log log = LogFactory.getLog(JavaXMLFactory.class);
	protected static String factoryClass = "org.opoo.apps.xml.CastorJavaXMLFactory";

	public XMLSerializer getJavaXML(){
		return getXMLSerializer();
	}

	public static void setFactoryClass(String className) {
		JavaXMLFactory.factoryClass = className;
	}

	public static XMLSerializerFactory getInstance() {
		return SingletonHolder.instance;
	}

	public static void main(String[] args) {
		System.out.println(JavaXMLFactory.getInstance());
	}

	private static class SingletonHolder {
		private static final XMLSerializerFactory instance = new JavaXMLFactory(){
			public XMLSerializer getXMLSerializer() {
				return new XMLSerializer(){
					public String toXML(Object bean) {
						return SerializableUtils.toXML(bean);
					}

					public Object fromXML(Class<?> clazz, String xml) {
						throw new UnsupportedOperationException();
					}

					public void marshal(Object bean, Writer writer) {
						throw new UnsupportedOperationException();
					}

					public Object unmarshal(Class<?> clazz, Reader reader) {
						throw new UnsupportedOperationException();
					}};
			}
		};
//		static {
//			String factoryClass = AppsGlobals.getSetupProperty(JAVA_XML_FACTORY);
//			if (StringUtils.isNotBlank(factoryClass)) {
//				JavaXMLFactory.factoryClass = factoryClass;
//			}
//			log.debug(JavaXMLFactory.factoryClass);
//			instance = (XMLSerializerFactory) ClassUtils.newInstance(JavaXMLFactory.factoryClass);
//		}
	}
}
