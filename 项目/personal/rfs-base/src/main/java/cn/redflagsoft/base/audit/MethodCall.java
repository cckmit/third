package cn.redflagsoft.base.audit;


import java.lang.reflect.Method;

import cn.redflagsoft.base.security.UserClerk;

/**
 * Encapsulates the details of a given method call for the audit system.
 */
public interface MethodCall {

    /**
     * Returns the user on whose behalf the method is called.
     *
     * @return the user on whose behalf the method is called.
     */
    public UserClerk getCaller();

    /**
     * Returns the object on which the method is called or null if this is a static method call.
     *
     * @return the object on which the method is called or null if this is a static method call.
     */
    public Object getTargetObject();

    /**
     * Returns the name of the method being called.
     *
     * @return the name of the method being called.
     */
    public Method getMethod();

    /**
     * Returns the class of the object being invoked, or the declaring class of the method for static method calls.
     * 
     * @return the class of the object being invoked, or the declaring class of the method for static method calls.
     */
    public Class<?> getTargetClass();

    /**
     * Returns the parameters (if any) that were passed into the method.
     * 
     * @return the parameters (if any) that were passed into the method.
     */
    public Object[] getParameterValues();
    
    /**
     * 
     * @return
     */
    public Object getResult();
}
