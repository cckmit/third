package org.opoo.apps.aspectj;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SampleMain sm = new SampleMain();
		
		int i = 0;
		System.out.println((i++) + " : -----------------------------------");
		sm.testAspect();
		
		System.out.println((i++) + " : -----------------------------------");
		sm.testAspect2("����");
		
		System.out.println((i++) + " : -----------------------------------");
		sm.testNoAspect("��һ������");
		
		System.out.println((i++) + " : -----------------------------------");
	}

}
