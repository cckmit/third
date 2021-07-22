package org.opoo.apps.license;

import java.lang.annotation.Annotation;

import org.opoo.apps.test.SpringTests;

public class MyTest extends SpringTests {

	
	public void test0(){
		Annotation[] annotations = DomainAopSampleObject.class.getAnnotations();
		for(Annotation ann: annotations){
			System.out.println(ann);
		}
		
//		annotations = DbPoll.class.getAnnotations();
//		for(Annotation ann: annotations){
//			System.out.println(ann);
//		}
		
		DomainAopSampleObject object = new DomainAopSampleObject();
		System.out.println(object.getUserManager());
		
	}
}
