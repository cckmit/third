package org.opoo.apps.web.servlet;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.opoo.apps.AppsContext;
import org.opoo.apps.license.AppsLicenseManager;
import org.opoo.apps.lifecycle.Application;

import com.jivesoftware.license.License;


/**
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ServletUtils
{

    private ServletUtils()
    {
    }

    public static String getServletPath(HttpServletRequest request)
    {
        String thisPath = request.getServletPath();
        if(thisPath == null)
        {
            String requestURI = request.getRequestURI();
            if(request.getPathInfo() != null)
                thisPath = requestURI.substring(0, requestURI.indexOf(request.getPathInfo()));
            else
                thisPath = requestURI;
        } else
        if(thisPath.equals("") && request.getPathInfo() != null)
            thisPath = request.getPathInfo();
        return thisPath;
    }

    public static boolean isModified(HttpServletRequest request, byte bytes[], long modified)
    {
        return isModified(request, bytes.length, modified);
    }

    public static boolean isModified(HttpServletRequest request, long fileSize, long modified)
    {
        if(request.getHeader("If-Modified-Since") != null && request.getHeader("If-None-Match") != null)
        {
            String byteTag = getEtag(fileSize);
            String eTag = request.getHeader("If-None-Match");
            Calendar ifModifiedSince = Calendar.getInstance();
            ifModifiedSince.setTimeInMillis(request.getDateHeader("If-Modified-Since"));
            Calendar publishDate = Calendar.getInstance();
            publishDate.setTime(new Date(modified));
            publishDate.set(Calendar.MILLISECOND, 0);
            int diff = ifModifiedSince.getTime().compareTo(publishDate.getTime());
            return diff != 0 || !eTag.equalsIgnoreCase(byteTag);
        } else
        {
            return true;
        }
    }

    public static String getEtag(byte bytes[])
    {
        return getEtag(bytes.length);
    }

    public static String getEtag(long size)
    {
        AppsContext ctx = Application.getContext();
        if(ctx != null && !AppsLicenseManager.getInstance().isFailed())
        {
            License license = ctx.getLicenseManager().getLicense();
            return (new StringBuilder()).append("\"").append(license.getVersion().getVersionString()).append("-").append(String.valueOf(size)).append("\"").toString();
        } else
        {
            return (new StringBuilder()).append("\"setup-").append(String.valueOf(size)).append("\"").toString();
        }
    }
}
