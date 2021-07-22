package cn.redflagsoft.base.security;

import org.springframework.security.annotation.Secured;

public class AnnotationObject {

	@Secured("ROLE_USER")
	public void doSomething(){
		System.out.println("doSomething is process...");
	}
	
	public static void main(String[] args){
		AnnotationObject ao = new AnnotationObject();
		ao.doSomething();
	}
}
