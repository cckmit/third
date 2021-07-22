package org.opoo.apps.web.struts2.action.admin.setup;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.opoo.apps.AppsGlobals;

import junit.framework.TestCase;

public class HibernateSetupActionTest extends TestCase {

	public void testDeterminDialection() throws Exception{
		HibernateSetupAction action = new HibernateSetupAction();
		action.prepare();
		String dialect = action.getDialect();
		System.out.println(dialect);
		
		Map<String,String> map = AppsGlobals.getSetupChildrenProperties("datasource");
		System.out.println(map);
		Collection<String> names = AppsGlobals.getSetupChildrenNames("datasource");
		System.out.println(names);
		List<String> list = AppsGlobals.getSetupPropertyNames();
		System.out.println(list);
	}
}
