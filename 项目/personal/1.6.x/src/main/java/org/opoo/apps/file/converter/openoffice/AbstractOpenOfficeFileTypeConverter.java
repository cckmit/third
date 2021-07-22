package org.opoo.apps.file.converter.openoffice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opoo.apps.file.converter.AbstractFileTypeConverter;
import org.opoo.apps.file.converter.FileTypesPair;
import org.opoo.apps.file.openoffice.SocketXConnectionProvider;
import org.opoo.apps.file.openoffice.XConnectionProvider;

public abstract class AbstractOpenOfficeFileTypeConverter extends AbstractFileTypeConverter {
	public static final List<FileTypesPair> ALL_SUPPORTED_TYPES;
	static{
		List<FileTypesPair> list = new ArrayList<FileTypesPair>();
		list.add(new FileTypesPair(
				new String[]{"odt", "sxw", "rtf", "doc", "wpd", "txt"/*, "html"*/, "docx"},
				new String[]{"pdf", "odt", "sxw", "rtf", "doc", "txt", "html", "wiki"}
		));
		list.add(new FileTypesPair(
				new String[]{"ods", "sxc", "xls", "csv", "tsv", "xlsx"},
				new String[]{"pdf", "ods", "sxc", "xls", "csv", "tsv", "html"}
		));
		list.add(new FileTypesPair(
				new String[]{"odp", "sxi", "ppt", "pptx"},
				new String[]{"pdf", "swf", "odp", "sxi", "ppt", "html"}
		));
		list.add(new FileTypesPair(
				new String[]{"odg"},
				new String[]{"svg", "swf"}
		));
		ALL_SUPPORTED_TYPES = Collections.unmodifiableList(list);
	}
	
	
	private XConnectionProvider connectionProvider = new SocketXConnectionProvider();


	/**
	 * @return the connectionProvider
	 */
	public XConnectionProvider getConnectionProvider() {
		return connectionProvider;
	}


	/**
	 * @param connectionProvider the connectionProvider to set
	 */
	public void setConnectionProvider(XConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}
	
	

}
