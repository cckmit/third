/*
 * $Id: PageContextUtils.java 13 2010-11-26 05:04:02Z alex $
 *
 * Copyright 2006-2008 Alex Lin. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opoo.web.tags;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author Alex Lin(alex@opoo.org)
 * @version 1.0
 */
public abstract class PageContextUtils {
    /**
     * Locate and return the specified bean, from an optionally specified
     * scope, in the specified page context.  If no such bean is found,
     * return <code>null</code> instead.  If an exception is thrown, it will
     * have already been saved via a call to <code>saveException()</code>.
     *
     * @param pageContext Page context to be searched
     * @param name Name of the bean to be retrieved
     * @param scope Scope to be searched (page, request, session, application)
     *  or <code>null</code> to use <code>findAttribute()</code> instead
     * @return JavaBean in the specified page context
     * @exception JspException if an invalid scope name
     *  is requested
     */
    public static Object lookup(PageContext pageContext, String name,
                                String scope) throws JspException {

        Object bean = null;
        if (scope == null)
            bean = pageContext.findAttribute(name);
        else if (scope.equalsIgnoreCase("page"))
            bean = pageContext.getAttribute(name, PageContext.PAGE_SCOPE);
        else if (scope.equalsIgnoreCase("request"))
            bean = pageContext.getAttribute(name, PageContext.REQUEST_SCOPE);
        else if (scope.equalsIgnoreCase("session"))
            bean = pageContext.getAttribute(name, PageContext.SESSION_SCOPE);
        else if (scope.equalsIgnoreCase("application"))
            bean = pageContext.getAttribute(name, PageContext.APPLICATION_SCOPE);
        else {
            JspException e = new JspException("lookup.scope error");
            throw e;
        }
        return (bean);
    }


    /**
     * Locate and return the specified property of the specified bean, from
     * an optionally specified scope, in the specified page context.  If an
     * exception is thrown, it will have already been saved via a call to
     * <code>saveException()</code>.
     *
     * @param pageContext Page context to be searched
     * @param name Name of the bean to be retrieved
     * @param property Name of the property to be retrieved, or
     *  <code>null</code> to retrieve the bean itself
     * @param scope Scope to be searched (page, request, session, application)
     *  or <code>null</code> to use <code>findAttribute()</code> instead
     * @return property of specified JavaBean
     *
     * @exception JspException if an invalid scope name
     *  is requested
     * @exception JspException if the specified bean is not found
     * @exception JspException if accessing this property causes an
     *  IllegalAccessException, IllegalArgumentException,
     *  InvocationTargetException, or NoSuchMethodException
     */
    public static Object lookup(
            PageContext pageContext,
            String name,
            String property,
            String scope) throws JspException {

        // Look up the requested bean, and return if requested
        Object bean = lookup(pageContext, name, scope);
        if (bean == null) {
            JspException e = null;
            if (scope == null) {
                e = new JspException("lookup.bean.any error");
            } else {
                e = new JspException("lookup.bean error");
            }
            //saveException(pageContext, e);
            throw e;
        }
        if (property == null)
            return (bean);

        // Locate and return the specified property
        try {
            return (PropertyUtils.getProperty(bean, property));
        } catch (IllegalAccessException e) {
            //saveException(pageContext, e);
            throw new JspException("lookup.access error");
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if (t == null){
                t = e;
            }
            //saveException(pageContext, t);
            throw new JspException("lookup.target");
        } catch (NoSuchMethodException e) {
            //saveException(pageContext, e);
            throw new JspException("lookup.method");
        }
    }

    public static void write(PageContext pc, String s) throws JspException {
        try {
            pc.getOut().print(s);
        } catch (IOException e) {
            throw new JspException(e);
        }
    }
}
