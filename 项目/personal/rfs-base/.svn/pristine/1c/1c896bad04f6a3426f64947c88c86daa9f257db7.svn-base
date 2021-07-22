package cn.redflagsoft.base.bean;

import java.util.Date;

import org.opoo.apps.util.SerializableUtils;

import junit.framework.TestCase;

public class IssueTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testJsonSerialize(){
		Issue i = new Issue();
		
		i.setBizStatus((byte) 2);
		i.setCreationTime(new Date());
		
		i.setOrgName("Admini Org");
		
		String s = SerializableUtils.toJSON(i);
		System.out.println(s);
	}
}
