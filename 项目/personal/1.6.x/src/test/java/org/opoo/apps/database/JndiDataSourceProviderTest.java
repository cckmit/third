package org.opoo.apps.database;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import junit.framework.TestCase;

public class JndiDataSourceProviderTest extends TestCase {

	public void etest0(){
		JndiDataSourceProvider p = new JndiDataSourceProvider();
		System.out.println(p);
		p.setProperty("name", "jdbc/tsoracle");
		p.setProperty(Context.SECURITY_PRINCIPAL, null);
		
		
		
	}
	
	public void test1() throws NamingException{
		Context context = new InitialContext();
		NamingEnumeration<Binding> bindings = context.listBindings("java:comp/env");
		System.out.println(bindings);
	}
}
