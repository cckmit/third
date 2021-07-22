
import org.opoo.apps.aspectj.SampleAnn;

import java.lang.reflect.Method;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.aspectj.lang.reflect.MethodSignature;


public aspect SampleOne{

	pointcut publicmethod() : execution(* *(..)) && @annotation(SampleAnn);
	
	//pointcut publicmethod(): call(@SampleAnn * *(..));

	before() : publicmethod() {
		MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
		Method method = methodSignature.getMethod();
		System.out.println(method);
		
	}

}