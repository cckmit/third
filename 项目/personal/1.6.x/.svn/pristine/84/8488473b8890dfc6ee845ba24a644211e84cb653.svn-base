package org.opoo.apps.file.openoffice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artofsolving.jodconverter.DocumentFormatRegistry;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.sun.star.lang.XComponent;

public class RefreshableStreamOpenOfficeDocumentConverter extends StreamOpenOfficeDocumentConverter implements XComponentRefresherAware{
	private static final Log log = LogFactory.getLog(RefreshableStreamOpenOfficeDocumentConverter.class);
	private XComponentRefresher componentRefresher;
	
	public RefreshableStreamOpenOfficeDocumentConverter(
			OpenOfficeConnection connection,
			DocumentFormatRegistry formatRegistry) {
		super(connection, formatRegistry);
	}

	public RefreshableStreamOpenOfficeDocumentConverter(
			OpenOfficeConnection connection) {
		super(connection);
	}
	public RefreshableStreamOpenOfficeDocumentConverter(OpenOfficeConnection connection, XComponentRefresher componentRefresher){
		this(connection);
		setComponentRefresher(componentRefresher);
	}
	
	public RefreshableStreamOpenOfficeDocumentConverter(OpenOfficeConnection connection, DocumentFormatRegistry formatRegistry, XComponentRefresher componentRefresher) {
		this(connection, formatRegistry);
		setComponentRefresher(componentRefresher);
	}

	
	@Override
	protected void refreshDocument(XComponent document) {
		super.refreshDocument(document);
		if(componentRefresher != null)
			componentRefresher.refresh(document);
	}

	/**
	 * @return the componentRefresher
	 */
	public XComponentRefresher getComponentRefresher() {
		return componentRefresher;
	}

	/**
	 * @param componentRefresher the componentRefresher to set
	 */
	public void setComponentRefresher(XComponentRefresher componentRefresher) {
		this.componentRefresher = componentRefresher;
		if(componentRefresher != null){
			removeReadOnly();
		}
	}
	
	private void removeReadOnly(){
		getDefaultLoadProperties().remove("ReadOnly");
		//System.out.println(getDefaultLoadProperties());
		log.info("去除ReadOnly属性, 当前属性为：" + getDefaultLoadProperties());
	}
}
