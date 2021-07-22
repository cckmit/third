package cn.redflagsoft.base.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.opoo.apps.AppsHome;
import org.opoo.apps.bean.core.Attachment;
import org.opoo.apps.file.converter.FileTypeConverter;
import org.opoo.apps.file.converter.FileTypeConverterManager;
import org.opoo.apps.file.converter.openoffice.OpenOfficeFileTypeConverter;
import org.opoo.apps.file.openoffice.SocketXConnectionProvider;
import org.opoo.apps.file.util.FileUtils;
import org.opoo.apps.service.AttachmentManager;

import cn.redflagsoft.base.test.BaseTests;

public class FileTypeConverterTest extends BaseTests{
	
	protected FileTypeConverterManager fileTypeConverterManager;
	protected AttachmentManager attachmentManager;

	public static void main(String[] args) {
		OpenOfficeFileTypeConverter converter = new OpenOfficeFileTypeConverter();
		converter.setConnectionProvider(new SocketXConnectionProvider("192.168.18.19", 8100));
		converter.convert(new File("/work/test.doc"), new File("/work/test.txt"));
	}
	
	public void test0() throws IOException{
		long fileId = 0L;
		
		
		//if word xp/2003
		FileTypeConverter converter = fileTypeConverterManager.findFileTypeConverter("doc", "txt");
		//if word 2007+
//		FileTypeConverter converter = fileTypeConverterManager.findFileTypeConverter("docx", "txt");
		if(converter == null){
			//throw new Exception
			return;
		}
		
		//»ñÈ¡¸½¼þ
		Attachment attachment = attachmentManager.getAttachment(fileId);
		
		File inputFile = null;
		File outputFile = null;
		InputStream in = null;
		OutputStream out = null;
		try{
			File temp = AppsHome.getTemp();
			
			in = attachment.readInputStream();
			inputFile = File.createTempFile("inputfile", ".doc", temp);
			out = new FileOutputStream(inputFile);
			IOUtils.copy(in, out);
			
			outputFile = File.createTempFile("outputfile", ".txt", temp);
			
			converter.convert(inputFile, outputFile);
			
			String content = FileUtils.readFileToString(outputFile, "GBK");
		}finally{
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
		
	}
}
