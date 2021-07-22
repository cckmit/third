package org.opoo.apps.file.openoffice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artofsolving.jodconverter.DocumentFormatRegistry;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.sun.star.lang.XComponent;



public class RefreshableOpenOfficeDocumentConverter extends OpenOfficeDocumentConverter implements XComponentRefresherAware{
	private static final Log log = LogFactory.getLog(RefreshableOpenOfficeDocumentConverter.class);
	private XComponentRefresher componentRefresher;

	public RefreshableOpenOfficeDocumentConverter(OpenOfficeConnection connection, DocumentFormatRegistry formatRegistry) {
		super(connection, formatRegistry);
	}

	public RefreshableOpenOfficeDocumentConverter(OpenOfficeConnection connection) {
		super(connection);
	}
	
	public RefreshableOpenOfficeDocumentConverter(OpenOfficeConnection connection, XComponentRefresher componentRefresher){
		this(connection);
		setComponentRefresher(componentRefresher);
	}
	
	public RefreshableOpenOfficeDocumentConverter(OpenOfficeConnection connection,	DocumentFormatRegistry formatRegistry, XComponentRefresher componentRefresher) {
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
		log.info("去除ReadOnly属性, 当前属性为：" + getDefaultLoadProperties());
		//System.out.println(getDefaultLoadProperties());
	}
}
