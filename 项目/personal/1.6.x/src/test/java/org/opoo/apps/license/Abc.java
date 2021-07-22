package org.opoo.apps.license;

import java.lang.annotation.Annotation;

//import com.jivesoftware.community.audit.aop.Audit;

public class Abc {
	public static void main(String[] args){
		DomainAopSampleObject a = new DomainAopSampleObject();
		System.out.println(a);
		System.out.println(a.getUserManager());
		
		Annotation[] annotations = DomainAopSampleObject.class.getAnnotations();
		for(Annotation ann: annotations){
			System.out.println(ann);
		}
		
//		annotations = AuditAspect.class.getAnnotations();
//		for(Annotation ann: annotations){
//			System.out.println(ann);
//		}
	}
}
