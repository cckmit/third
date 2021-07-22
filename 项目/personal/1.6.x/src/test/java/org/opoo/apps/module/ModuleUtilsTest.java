package org.opoo.apps.module;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;

import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;

import junit.framework.TestCase;

public class ModuleUtilsTest extends TestCase {
	public void etest0() throws ZipException, IOException{
		File file = new File("C:\\Documents and Settings\\Administrator\\×ÀÃæ\\org.eclipse.equinox.jsp.examples-proj.zip");
		ZipFile zip = new ZipFile(file);
		System.out.println(zip);
		ZipEntry en = zip.getEntry("org.eclipse.equinox.jsp.examples/build.properties");
		System.out.println(en.getName());
	}
	
	public void test1() throws IOException{
		String str = "E:\\work.home\\apps\\modules";
		File file = new File(str);
		if(file.exists()){
			System.out.println("----------------");
			long lastModified = file.lastModified();
			Date date = new Date(lastModified);
			System.out.println(date);
			
			//FileUtils.deleteDirectory(file);
		}
		System.out.println(file.exists());
		
		RandomWordGenerator generator = new RandomWordGenerator("abcdefghijklmnopqrstuvwxyz");
		System.out.println(generator.getWord(10));
	}
}
