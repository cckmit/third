package cn.redflagsoft.base.audit;


import java.lang.reflect.Method;

import cn.redflagsoft.base.security.UserClerk;

/**
 * Default implementation of MethodCall which must be constructed manually
 * by code invoking the audit api.
 */
public class SimpleMethodCall implements MethodCall {
    private UserClerk caller;
    private Method method;
    private Object target;
    private Object[] params;
    private Object result;

    public SimpleMethodCall(){}

    public SimpleMethodCall(UserClerk caller, Object target, Method method, Object[] params) {
        this.caller = caller;
        this.target = target;
        this.method = method;
        this.params = params;
    }
    
    public SimpleMethodCall(UserClerk caller, Object target, Method method, Object[] params, Object result) {
        this.caller = caller;
        this.target = target;
        this.method = method;
        this.params = params;
        this.result = result;
    }

    public void setCaller(UserClerk caller) {
        this.caller = caller;
    }

    public UserClerk getCaller() {
        return caller;
    }

    public void setTargetObject(Object target) {
        this.target = target;
    }

    public Object getTargetObject() {
        return target;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getTargetClass() {
        if (target != null) {
            return target.getClass();
        }
        else {
            return method.getDeclaringClass();
        }
    }

    public void setParameterValues(Object... params) {
        this.params = params;
    }

    public Object[] getParameterValues() {
        return params;
    }

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
