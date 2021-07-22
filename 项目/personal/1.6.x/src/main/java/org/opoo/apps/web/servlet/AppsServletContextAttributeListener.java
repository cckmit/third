package org.opoo.apps.web.servlet;


import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

public class AppsServletContextAttributeListener implements ServletContextAttributeListener {

	public void attributeAdded(ServletContextAttributeEvent scab) {
		System.out.println("**attributeAdded: " + scab.getName() + " --> " + scab.getValue());
	
	}

	public void attributeRemoved(ServletContextAttributeEvent scab) {
		System.out.println("**attributeRemoved: " + scab.getName() + " --> " + scab.getValue());
	}

	public void attributeReplaced(ServletContextAttributeEvent scab) {
		System.out.println("**attributeReplaced: " + scab.getName() + " --> " + scab.getValue());
	}

}
