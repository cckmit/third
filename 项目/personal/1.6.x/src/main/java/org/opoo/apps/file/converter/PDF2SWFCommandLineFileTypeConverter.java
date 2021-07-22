package org.opoo.apps.file.converter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PDF2SWFCommandLineFileTypeConverter extends CommandLineFileTypeConverter {
	public static final List<FileTypesPair> SUPPORTED_TYPES;
	static{
		List<FileTypesPair> list = new ArrayList<FileTypesPair>();
		list.add(new FileTypesPair("pdf", "swf"));
		SUPPORTED_TYPES = Collections.unmodifiableList(list);
	}
	
	public PDF2SWFCommandLineFileTypeConverter(){
		super();
		//setSupportedFileTypes(SUPPORTED_TYPES);
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.file.converter.AbstractFileTypeConverter#getSupportedFileTypes()
	 */
	@Override
	public List<FileTypesPair> getSupportedFileTypes() {
		return SUPPORTED_TYPES;
	}
	
	public static void main(String[] args){
		System.out.println(new File("d:\\js.swf").length());
		PDF2SWFCommandLineFileTypeConverter c = new PDF2SWFCommandLineFileTypeConverter();
		c.setCommand("cmd /C D:/Downloads/swftools-2009-03-15-1014/p2s");
		c.setWorkDir("D:/Downloads/swftools-2009-03-15-1014");
		c.convert(new File("d:\\js.pdf"), new File("d:\\js.swf"));
		
	}
}
