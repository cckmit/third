package org.opoo.apps.file.converter;

import java.io.File;
import java.util.List;

import org.springframework.util.StopWatch;

import junit.framework.TestCase;

public class JODConverterTest extends TestCase {

	public void etestConverter() throws Exception{
		StopWatch stop = new StopWatch();
		stop.start("new");
		JODConverter c = new JODConverter();
		//solaris 10 starsuite
		c.setHost("localhost");
		
		stop.stop();
		stop.start("support");
		boolean supports = c.supports("doc", "swf");
		stop.stop();
		System.out.println(supports);
		//System.out.println(stop.prettyPrint());
		
		stop.start("support");
		supports = c.supports("xls", "pdf");
		stop.stop();
		System.out.println(supports);
		System.out.println(stop.prettyPrint());
		
		
		//c.convert(new FileInputStream("/d:/VMware.Service.txt"), "txt", new FileOutputStream("d:/VMware.Serviceee.doc"), "doc");
		c.convert(new File("C:\\Documents and Settings\\Administrator\\桌面\\附件\\B超室统计月报表.xls"), new File("C:\\Documents and Settings\\Administrator\\桌面\\附件\\a.jpg"));
	}
	
	
}
