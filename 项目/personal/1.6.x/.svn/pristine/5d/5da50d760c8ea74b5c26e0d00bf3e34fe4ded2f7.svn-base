package org.opoo.storage.eos;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.util.StopWatch;

import com.jivesoftware.eos.fs.FileStorageProvider;

public class MyFileStorageProviderTest {

	@Test
	public void test() throws IOException {
		Properties p = new Properties();
		p.setProperty("rootDirectory", "d:\\temp");
		MyFileStorageProvider sp = new MyFileStorageProvider("eos", p);
		boolean containsKey = sp.containsKey("ÎÄ×ÖÃèÊö");
		System.out.println(containsKey);
		
		System.out.println(sp.getStream("salkdlsjÎÄ×ÖÃèÊö"));
		
		//sp.put("ÎÄ×ÖÃèÊö", FileUtils.openInputStream(new File("d:\\aaaa.txt")));
		Iterable<String> keys = sp.getKeys();
		for(String key :keys){
			System.out.println(key);
		}
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		
		
		Properties p2 = new Properties();
		p2.setProperty("rootDirectory", "d:\\temp\\dv-storage");//D:\temp\dv-storage
		FileStorageProvider provider = new FileStorageProvider("test", p2);
		Iterable<String> keys2 = provider.getKeys();
		for(String key: keys2){
			System.out.println(key);
		}
		InputStream stream = sp.getStream("/2012/2/323/23.pdf");
		System.out.println(stream);
		
		StopWatch w = new StopWatch();
		w.prettyPrint();
	}

}
