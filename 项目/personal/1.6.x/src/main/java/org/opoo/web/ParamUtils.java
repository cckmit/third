/*
 * $Id: ParamUtils.java 13 2010-11-26 05:04:02Z alex $
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
package org.opoo.web;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public abstract class ParamUtils
{

    public static String getParameter(HttpServletRequest request, String name)
    {
        return getParameter(request, name, false);
    }

    public static String getParameter(HttpServletRequest request, String name, String defaultValue)
    {
        return getParameter(request, name, defaultValue, false);
    }

    public static String getParameter(HttpServletRequest request, String name, boolean emptyStringsOK)
    {
        return getParameter(request, name, null, emptyStringsOK);
    }

    public static String getParameter(HttpServletRequest request, String name, String defaultValue, boolean emptyStringsOK)
    {
        String temp = request.getParameter(name);
        if(temp != null)
        {
            if(temp.equals("") && !emptyStringsOK)
                return defaultValue;
            else
                return temp;
        } else
        {
            return defaultValue;
        }
    }

	public static String[] getParameters(HttpServletRequest request, String name)
    {
        if(name == null)
            return new String[0];
        String paramValues[] = request.getParameterValues(name);
        if(paramValues == null || paramValues.length == 0)
            return new String[0];
        List<String> values = new ArrayList<String>(paramValues.length);
        for(int i = 0; i < paramValues.length; i++)
            if(paramValues[i] != null && !"".equals(paramValues[i]))
                values.add(paramValues[i]);

        return (String[])(String[])values.toArray(new String[0]);
    }

    public static boolean getBooleanParameter(HttpServletRequest request, String name)
    {
        return getBooleanParameter(request, name, false);
    }

    public static boolean getBooleanParameter(HttpServletRequest request, String name, boolean defaultVal)
    {
        String temp = request.getParameter(name);
        if("true".equals(temp) || "on".equals(temp))
            return true;
        if("false".equals(temp) || "off".equals(temp))
            return false;
        else
            return defaultVal;
    }

    public static int getIntParameter(HttpServletRequest request, String name, int defaultNum)
    {
        String temp = request.getParameter(name);
        if(temp != null && !temp.equals(""))
        {
            int num = defaultNum;
            try
            {
                num = Integer.parseInt(temp.trim());
            }
            catch(Exception ignored) { }
            return num;
        } else
        {
            return defaultNum;
        }
    }

    public static int[] getIntParameters(HttpServletRequest request, String name, int defaultNum)
    {
        String paramValues[] = request.getParameterValues(name);
        if(paramValues == null || paramValues.length == 0)
            return new int[0];
        int values[] = new int[paramValues.length];
        for(int i = 0; i < paramValues.length; i++)
            try
            {
                values[i] = Integer.parseInt(paramValues[i].trim());
            }
            catch(Exception e)
            {
                values[i] = defaultNum;
            }

        return values;
    }

    public static double getDoubleParameter(HttpServletRequest request, String name, double defaultNum)
    {
        String temp = request.getParameter(name);
        if(temp != null && !temp.equals(""))
        {
            double num = defaultNum;
            try
            {
                num = Double.parseDouble(temp.trim());
            }
            catch(Exception ignored) { }
            return num;
        } else
        {
            return defaultNum;
        }
    }

    public static long getLongParameter(HttpServletRequest request, String name, long defaultNum)
    {
        String temp = request.getParameter(name);
        if(temp != null && !temp.equals(""))
        {
            long num = defaultNum;
            try
            {
                num = Long.parseLong(temp.trim());
            }
            catch(Exception ignored) { }
            return num;
        } else
        {
            return defaultNum;
        }
    }

    public static long[] getLongParameters(HttpServletRequest request, String name, long defaultNum)
    {
        String paramValues[] = request.getParameterValues(name);
        if(paramValues == null || paramValues.length == 0)
            return new long[0];
        long values[] = new long[paramValues.length];
        for(int i = 0; i < paramValues.length; i++)
            try
            {
                values[i] = Long.parseLong(paramValues[i].trim());
            }
            catch(Exception e)
            {
                values[i] = defaultNum;
            }

        return values;
    }

    public static String getAttribute(HttpServletRequest request, String name)
    {
        return getAttribute(request, name, false);
    }

    public static String getAttribute(HttpServletRequest request, String name, boolean emptyStringsOK)
    {
        String temp = (String)request.getAttribute(name);
        if(temp != null)
        {
            if(temp.equals("") && !emptyStringsOK)
                return null;
            else
                return temp;
        } else
        {
            return null;
        }
    }

    public static boolean getBooleanAttribute(HttpServletRequest request, String name)
    {
        String temp = (String)request.getAttribute(name);
        return temp != null && temp.equals("true");
    }

    public static int getIntAttribute(HttpServletRequest request, String name, int defaultNum)
    {
        String temp = (String)request.getAttribute(name);
        if(temp != null && !temp.equals(""))
        {
            int num = defaultNum;
            try
            {
                num = Integer.parseInt(temp.trim());
            }
            catch(Exception ignored) { }
            return num;
        } else
        {
            return defaultNum;
        }
    }

    public static long getLongAttribute(HttpServletRequest request, String name, long defaultNum)
    {
        String temp = (String)request.getAttribute(name);
        if(temp != null && !temp.equals(""))
        {
            long num = defaultNum;
            try
            {
                num = Long.parseLong(temp.trim());
            }
            catch(Exception ignored) { }
            return num;
        } else
        {
            return defaultNum;
        }
    }
}
