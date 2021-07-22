package cn.redflagsoft.base.test;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import org.junit.Test;
import org.opoo.util.XMLOpooProperties2;
import org.springframework.util.ResourceUtils;

public class XmlLoaderTest{
	
	@Test
	public void testLoad() throws IOException, Exception{
		URL url = ResourceUtils.getURL("classpath:base_build.xml");
		System.out.println(url);
		XMLOpooProperties2 props = new XMLOpooProperties2(url.openStream());
		Collection<String> names = props.getPropertyNames();
		System.out.println(names);
		System.out.println(props.get("specification.vendor"));
	}
}
