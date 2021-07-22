package cn.redflagsoft.base.aop.aspect;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import cn.redflagsoft.base.scheme.schemes.menu.v2.MenuScheme;

public class AspectJTest extends TestCase {

	
	public void etestAspectJ(){
		boolean compileTimeWoven = AspectJ.isCompileTimeWoven();
		System.out.println(compileTimeWoven);
		
		MenuScheme scheme = new MenuScheme();
		Map<String,String> params = new HashMap<String, String>();
		params.put("param0", "0");
		params.put("__rc", "java");
		scheme.setParameters(params);
		scheme.doScheme();
	}
	
	public void testFindMethodAnnotations(){
		Method[] methods = AspectJ.class.getDeclaredMethods();
		for(Method m: methods){
			System.out.println(m);
			Annotation[] annotations = m.getAnnotations();
			for (Annotation annotation : annotations) {
				System.out.println(annotation);
			}
			System.out.println("--------------------");
		}
	}
}
