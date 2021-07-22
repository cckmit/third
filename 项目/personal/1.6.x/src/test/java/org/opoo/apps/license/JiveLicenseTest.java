/**
 * 
 */
package org.opoo.apps.license;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;

import com.jivesoftware.license.License;
import com.jivesoftware.license.LicenseException;
import com.jivesoftware.license.io.LicenseReader;


/**
 * @author lcj
 *
 */
public class JiveLicenseTest {
	
	@Test
	public void testReadTrialLicense(){
		License defaultLicense = null;
		
		// calculate which trial license to use from build property
        String licenseFile = "";
        String edition = "external";
        if ( "external".equals( edition ) ) {
            licenseFile = "trial-public.license";
        } else if ( "internal".equals( edition ) ) {
            licenseFile = "trial-employee.license";
        } else {
            throw new RuntimeException("Unknown edition from build property: " + edition );
        }
		
		InputStream stream = JiveLicenseTest.class.getClassLoader().getResourceAsStream(licenseFile);
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        StringBuilder licenseBody = new StringBuilder();
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                licenseBody.append(inputLine);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // construct a license object from the license body
        //LicenseReader reader = new LicenseReader();
        StringReader licenseReader = new StringReader( licenseBody.toString() );
        try {
            defaultLicense = read(licenseReader);
        }
        catch (IOException e) {
            throw new RuntimeException("Could not read license from " + licenseFile, e );
        }

        Assert.assertNotNull(licenseFile);
        
        System.out.println(defaultLicense);
	}
	
	

    public License read(Reader reader) throws LicenseException, IOException
    {
        String xml = decodeToXml(reader);
        return License.fromXML(xml);
    }

    private String decodeToXml(Reader in) throws IOException
    {
        StringBuffer text = new StringBuffer();
        char buf[] = new char[1024];
        int len;
        while((len = in.read(buf)) >= 0) 
        {
            int j = 0;
            while(j < len) 
            {
                char ch = buf[j];
                if(Character.isLetter(ch) || Character.isDigit(ch) || ch == '+' || ch == '/' || ch == '=')
                    text.append(ch);
                j++;
            }
        }
        in.close();
        String xml = new String(Base64.decodeBase64(text.toString().getBytes("utf-8")));
        System.out.println(xml);
        return xml;
    }
}
