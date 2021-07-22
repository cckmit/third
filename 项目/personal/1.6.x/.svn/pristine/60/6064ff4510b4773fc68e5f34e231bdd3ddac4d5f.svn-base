package org.opoo.apps.dv.office.conversion.jive.modules.docconverter;

import org.opoo.apps.dv.util.Utils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jivesoftware.office.manager.ConversionManager;

public class JiveModulesDocconverter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Utils.setDevMode(true);
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dv/spring-dv-jive-modules-docconverter.xml");
		
		ConversionManager conversionManager = (ConversionManager) context.getBean("conversionManagerImpl");
		System.out.println(conversionManager);
		
		
		context.destroy();
	}

}
