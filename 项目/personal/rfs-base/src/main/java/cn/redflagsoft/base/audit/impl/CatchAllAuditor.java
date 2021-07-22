package cn.redflagsoft.base.audit.impl;


import java.lang.reflect.Method;

import cn.redflagsoft.base.audit.AbstractAuditor;
import cn.redflagsoft.base.audit.MethodCall;
import cn.redflagsoft.base.audit.MutableAuditMessage;
import cn.redflagsoft.base.audit.NotAuditableException;

/**
 * Auditor which does contains lowest common denominator logic to handle any {@link MethodCall} passed in.
 */
public class CatchAllAuditor extends AbstractAuditor {

    public boolean methodIsAuditable(Method method) {
        return true;
    }

    public String buildDetails(MethodCall call, MutableAuditMessage message) {
        StringBuilder builder = new StringBuilder();
        String paramString = buildParamString(call.getParameterValues());

        builder.append(call.getMethod()).append(" called");
        if (paramString.length() > 0) {
            builder.append(" with params ").append(paramString);
        }
        return builder.toString();
    }

    public String buildDescription(MethodCall call, MutableAuditMessage message) {
        StringBuilder builder = new StringBuilder();
        builder.append(call.getMethod().getName());

        builder.append(" called on ");
        if (call.getTargetObject() != null) {
            builder.append(call.getTargetObject().getClass().getSimpleName());
        }
        else {
            builder.append(call.getMethod().getDeclaringClass().getSimpleName());
        }

        return builder.toString();
    }

    private String buildParamString(Object[] params) {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<params.length; i++) {
            builder.append((params[i] != null ? params[i].toString() : "null"));
            if (i < params.length) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }

    public void setAuditedClass(Class<?> audited) throws NotAuditableException {
        /* no op */
    }
}
