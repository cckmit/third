package org.opoo.apps.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


public abstract class AnnotationUtils extends
		org.springframework.core.annotation.AnnotationUtils {

	/**
	 * 在当前类及当前类的接口中查找相应的Annotation.
	 * 
	 * @param <A>
	 * @param method
	 * @param annotationType
	 * @return
	 */
	public static <A extends Annotation> A getPossibleAnnotation(final Method method, final Class<A> annotationType) {
		A annotation = AnnotationUtils.findAnnotation(method, annotationType);
		if(annotation != null){
			return annotation;
		}
		
		Class<?> cl = method.getDeclaringClass();
		return getAnnotationFromInterfaces(cl, method, annotationType);
	}
	
	
	
	/**
	 * 从当前类的所有接口中查找相应的Annotation。
	 * 
	 * @param <A>
	 * @param cl
	 * @param method
	 * @param annotationType
	 * @return
	 */
	public static <A extends Annotation> A getAnnotationFromInterfaces(Class<?> cl, final Method method, final Class<A> annotationType) {
		A annotation = null;
		for (Class<?> ifc : cl.getInterfaces()){
			try {
				Method equivalentMethod = ifc.getDeclaredMethod(method.getName(), method.getParameterTypes());
				annotation = equivalentMethod.getAnnotation(annotationType);
				if(annotation != null){
					return annotation;
				}
			}
			catch (NoSuchMethodException ex) {
				// We're done...
			}
			
			annotation = getAnnotationFromInterfaces(ifc, method, annotationType);
			if(annotation != null){
				return annotation;
			}
		}
		
		return null;
	}
}
