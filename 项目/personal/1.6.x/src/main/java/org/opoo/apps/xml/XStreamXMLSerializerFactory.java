package org.opoo.apps.xml;

public class XStreamXMLSerializerFactory implements XMLSerializerFactory {
	private XStreamXMLSerializer s = new XStreamXMLSerializer();
	
	public XMLSerializer getXMLSerializer() {
		return s;
	}

}
