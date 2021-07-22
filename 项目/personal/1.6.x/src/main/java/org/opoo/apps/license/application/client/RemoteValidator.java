package org.opoo.apps.license.application.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.opoo.apps.license.AppsLicense;
import org.opoo.apps.license.AppsLicenseManager.AppsLicenseProperty;
import org.opoo.apps.license.io.AppsLicenseWriter;
import org.opoo.apps.license.validator.Validator;

import com.jivesoftware.license.License;
import com.jivesoftware.license.LicenseException;
import com.jivesoftware.license.LicenseSigner;
import com.jivesoftware.license.io.LicenseReader;

@Deprecated
public class RemoteValidator implements Validator {
	//private static final Log log = LogFactory.getLog(RemoteValidator.class);
	
	private LicenseWSClient client = new LicenseWSClient();
	
	public void validate(License license) throws LicenseException {
		AppsLicense lic = new AppsLicense(license);
		if(lic.getDevMode() != null){
			boolean validate = client.validate(license);
			if(!validate){
				throw new LicenseException("License 校验失败。");
			}
		}
	}
	
	

	
	public static void main2(String[] args) throws Exception{
		License defaultLicense = new License();
        defaultLicense.setID(1981230L);
        
        //defaultLicense.setClient(new License.Client("服务器5.10.15", "深圳市红旗信息技术有限公司"));
        defaultLicense.setClient(new License.Client("孙铭传", "深圳市红旗信息技术有限公司"));
        //defaultLicense.setClient(new License.Client("发改局服务器", "深圳市南山区发改局"));
        
        defaultLicense.setType(License.Type.COMMERCIAL);
        defaultLicense.setCreationDate(new Date());
        //defaultLicense.setVersion(new License.Version(1, 5, 0, "GA"));
        //defaultLicense.setName("touchstone");
        
        defaultLicense.setVersion(new License.Version(1, 0, 0, "DEV"));
        defaultLicense.setName("milestone");
        defaultLicense.setEdition("standard");
        List<License.Module> modules = new ArrayList<License.Module>();
        modules.add(new License.Module("core"));
        modules.add(new License.Module("base"));
        defaultLicense.setModules(modules);
        defaultLicense.getProperties().put(AppsLicenseProperty.expirationDate.name(), "07/15/2010");
        
        //defaultLicense.getProperties().put(AppsLicenseProperty.grantedIP.name(), "10.200.60.42,10.200.60.142,10.200.60.187");
        defaultLicense.getProperties().put(AppsLicenseProperty.grantedIP.name(), "192.168.18.108");
        //defaultLicense.getProperties().put(AppsLicenseProperty.grantedIP.name(), "192.168.18.5,192.168.18.15,192.168.18.10");
        
        defaultLicense.getProperties().put(AppsLicenseProperty.reportURL.name(), "http://report.redflagsoft.cn/report.jspa");
        defaultLicense.getProperties().put(AppsLicenseProperty.devMode.name(), "true");
        defaultLicense.getProperties().put(AppsLicenseProperty.numClusterMembers.name(), "1");
        
        defaultLicense.getProperties().put(AppsLicenseProperty.validateURL.name(), "http://localhost:8080/apps/services/license");
        AppsLicense al = new AppsLicense(defaultLicense);
        
        String privKey = "c:/rfs/rfs.private.key";
        LicenseSigner ls = new LicenseSigner(new FileReader(new File(privKey)));
        //AppsLicenseWriter w = new AppsLicenseWriter(al, ls);
        System.out.println(AppsLicenseWriter.encode(al, -1, ls));
        System.out.println(al.getSignature());
        
        System.out.println(al.toXML());
        
        RemoteValidator validator = new RemoteValidator();
        validator.validate(al);
	}
	
	
	public static License readLicense() throws Exception{
		File file = new File("");
		Reader reader = new BufferedReader(new FileReader(file));
		//String s = decodeToXml(reader);
		//System.out.println(s);
		
		
		LicenseReader licenseReader = new LicenseReader();
		License l = licenseReader.read(reader);
		System.out.println(l);
		System.out.println(new String(l.getFingerprint()));
//		Validator v = new CheckSignatureValidator();
//		v.validate(l);
		return l;
	}
	
	
	public static void main(String[] args) throws Exception{
		File file = new File("d:/core.license");
		Reader reader = new BufferedReader(new FileReader(file));
		
		
		LicenseReader licenseReader = new LicenseReader();
		License l = licenseReader.read(reader);
		System.out.println(l.getProperties());
		
		RemoteValidator validator = new RemoteValidator();
        validator.validate(l);
	}
}
