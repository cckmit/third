package org.opoo.apps.util;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

@Deprecated
public class InitPropLoader
{
	private static final Logger log = Logger.getLogger(InitPropLoader.class.getName());
	
    public InitPropLoader()
    {
    }

    public String getAppsHome()
    {
        String appsHome = null;
        InputStream in = null;
        try
        {
            in = getClass().getResourceAsStream("/apps_init.xml");
            if(in != null)
            {
            	
                Document doc = (new SAXReader()).read(in);
                appsHome = doc.getRootElement().getText();
                System.out.println(appsHome);
            }
        }
        catch(Throwable e)
        {
            log.log(Level.SEVERE, "Error loading apps_init.xml to find appsHome", e);
        }
        finally
        {
            try
            {
                if(in != null)
                    in.close();
            }
            catch(Exception e) { }
        }
        if(appsHome != null)
            for(appsHome = appsHome.trim(); appsHome.endsWith("/") || appsHome.endsWith("\\"); appsHome = appsHome.substring(0, appsHome.length() - 1));
        if("".equals(appsHome))
            appsHome = null;
        return appsHome;
    }
}

