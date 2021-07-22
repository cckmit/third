package org.opoo.apps.license.loader;

import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.opoo.apps.license.AppsLicenseLog;
import org.opoo.apps.license.DefaultLicenseProvider;
import org.opoo.apps.license.AppsLicenseManager.AppsLicenseProperty;
import org.opoo.apps.license.hasp.HaspConfig;
import org.opoo.apps.license.hasp.HaspRO;

import Aladdin.Hasp;
import Aladdin.HaspStatus;

import com.jivesoftware.license.License;
import com.jivesoftware.license.LicenseException;
import com.jivesoftware.license.LicenseProvider;
import com.jivesoftware.license.License.Client;
import com.jivesoftware.license.validator.VersionValidator;


/**
 * Hasp License Loader。
 * 
 * <p>在远程桌面下运行时，需要定义 Feature 的 Remote Desktop 为 true。
 * 默认的Feature 0无法设置远程属性。
 * 因此必须使用其他特性值。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class HaspLicenseLoader extends AbstractLicenseLoader implements LicenseLoader {
	
	private static final Log log = AppsLicenseLog.LOG;
	private SecureRandom random = new SecureRandom();
	
	public License loadLicense(LicenseProvider provider) {
		Hasp hasp = new Hasp(HaspConfig.HASP_LICENSE_FID/*Hasp.HASP_DEFAULT_FID*/);
		String info = hasp.getInfo(HaspConfig.LOCAL_SCOPE, 
				HaspConfig.KEY_INFO_FORMAT, HaspConfig.VENDOR_CODE);
		int status = hasp.getLastError();
		if(status != HaspStatus.HASP_STATUS_OK){
			log.debug("Can not get key info, error #" + status);
			return null;
		}

		long id = -1;
		String type = "";
		try {
			log.debug(info);
			
			Document document = DocumentHelper.parseText(info);
			Element node = (Element) document.selectSingleNode("/root/hasp");
			id = Long.parseLong(node.attributeValue("id"));
			type = node.attributeValue("type");
		} catch (Exception e) {
			log.debug("parse key info error", e);
			return null;
		}
		
		//判断是不是ALS许可
		hasp = new Hasp(HaspConfig.APPS_LICENSE_SERVER_FID);
		hasp.loginScope(HaspConfig.LOCAL_SCOPE, HaspConfig.VENDOR_CODE);
		status = hasp.getLastError();
		boolean isALS = (status == HaspStatus.HASP_STATUS_OK);
		hasp.logout();
		
		//登录指定的FID
		hasp = new Hasp(HaspConfig.HASP_LICENSE_FID);
		hasp.loginScope(HaspConfig.LOCAL_SCOPE, HaspConfig.VENDOR_CODE);
		status = hasp.getLastError();
		if(status != HaspStatus.HASP_STATUS_OK){
			log.debug("key can not login, error #" + status);
//			hasp.logout();
			return null;
		}
		
		//读取RO数据
		byte[] rodata = new byte[112];
		hasp.read(Hasp.HASP_FILEID_RO, 0, rodata);
		status = hasp.getLastError();
		if(status != HaspStatus.HASP_STATUS_OK){
			log.debug("can not read data from RO, error #" + status);
			hasp.logout();
			return null;
		}
		
		//解析RO数据
		HaspRO hm = new HaspRO();
		try {
			hm.fromBytes(rodata);
		} catch (Exception e) {
			log.debug("parse RO data failed", e);
			return null;
		}
		
		License.Version version = hm.getVersion();
		
		License license = new License();
		license.setID(id);
		license.setClient(new Client("End User/Developer", "(RedFlagSoft.CN Licensed Company)"));
		license.setType(License.Type.COMMERCIAL);
		license.setCreationDate(new Date());
		license.setVersion(new License.Version(version.getMajor(), version.getMinor(), version.getRevision(), "HL"));
		license.setName(provider.getName() + (isALS ? "(ALS Enabled)" : ""));
		license.setEdition("standard(local)");
        List<License.Module> modules = new ArrayList<License.Module>();
        modules.add(new License.Module("core"));
        modules.add(new License.Module("base"));
        if(isALS){
        	modules.add(new License.Module("als"));
        }
        license.setModules(modules);
        String date = License.formatDate(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000));
        license.getProperties().put(AppsLicenseProperty.expirationDate.name(), date);
        license.getProperties().put(AppsLicenseProperty.reportURL.name(), "http://report.redflagsoft.cn/report.jspa");
        //TODO 读取Hasp 生成
        //defaultLicense.getProperties().put(AppsLicenseProperty.devMode.name(), "true");
        license.getProperties().put(AppsLicenseProperty.numClusterMembers.name(), String.valueOf(hm.getNumClusterMembers()));
        //license.getProperties().put(AppsLicenseProperty.allowedUserCount, "100");
        if(hm.getAllowedUserCount() > 0){
        	license.getProperties().put(AppsLicenseProperty.allowedUserCount.name(), String.valueOf(hm.getAllowedUserCount()));
        }
        
        license.getProperties().put("hasp", "true");
        license.getProperties().put("hasp-type", type);
        license.getProperties().put("hasp-id", String.valueOf(id));
        
//        if(isALS){
//	     	   允许客户端数量
//        	byte b = readFromRO(hasp, 8);
//        	if(b == Byte.MIN_VALUE){
//        		log.debug("key can not read client seats, error #" + status);
//    			hasp.logout();
//    			return null;
//        	}
//        	if(b > 0){
//        		license.getProperties().put("client-seats", b + "");
//        	}
//        }
  
        try{
        	String ip = InetAddress.getLocalHost().getHostAddress();
        	license.getProperties().put(AppsLicenseProperty.grantedIP.name(), ip);
        }catch(Exception e){
        	//ignore
        }
        
        //防止1个授权多个应用，写随机数
        byte[] sid = new byte[1];
        random.nextBytes(sid);
        license.getProperties().put("sid", "" + sid[0]);
        
        log.debug("SID: " + sid[0]);
        hasp.write(Hasp.HASP_FILEID_RW, 0, sid);
        status = hasp.getLastError();
        if(status != HaspStatus.HASP_STATUS_OK){
			log.debug("Cannot write key, error #" + status);
			hasp.logout();
			return null;
		}
        
        hasp.logout();
        status = hasp.getLastError();
        if(status != HaspStatus.HASP_STATUS_OK){
			log.debug("Cannot logout key, error #" + status);
			return null;
		}
        
        
        //validate
        try {
			VersionValidator validator = new VersionValidator(provider.getVersion());
			validator.validate(license);
		} catch (LicenseException e) {
			log.debug("version error", e);
			return null;
		}
        
        //sign
        return license;
	}
	
	
	protected void checkInternal(LicenseProvider provider, License license, CheckCallback callback) {
		//System.out.println(this + " Checking license.");
		boolean result = true;
		Hasp hasp = new Hasp(HaspConfig.HASP_LICENSE_FID/*Hasp.HASP_DEFAULT_FID*/);
		hasp.loginScope(HaspConfig.LOCAL_SCOPE, HaspConfig.VENDOR_CODE);
		int status = hasp.getLastError();
		
		result = status == HaspStatus.HASP_STATUS_OK;
		if(!result){
			callback.checkFailed();
			return;
		}
		
		String sid = license.getProperties().get("sid");
		if(sid != null){
			byte bsid = Byte.parseByte(sid);
			byte[] data = new byte[1];
			hasp.read(Hasp.HASP_FILEID_RW, 0, data);
			status = hasp.getLastError();
			result = status == HaspStatus.HASP_STATUS_OK && data[0] == bsid;
			if(!result){
				/*log.debug("check sid error. #" + status);
				callback.checkFailed();
				hasp.logout();
				return;*/
				//暂不处理
				log.warn("Your Hsap HL Key using for more than one application.");
			}
		}
		
		hasp.logout();
		status = hasp.getLastError();
		if(status != HaspStatus.HASP_STATUS_OK){
			log.debug("logout error #" + status);
		}
		
		if(result){
			callback.checkSuccess();
		}else{
			callback.checkFailed();
		}
	}
	

	@Override
	protected int getCheckInterval() {
		return 1 * 20 * 1000;
	}
	
	public static boolean isHaspDriverInstalled(){
		try {
			new Hasp(HaspConfig.HASP_LICENSE_FID/*Hasp.HASP_DEFAULT_FID*/);
			return true;
		} catch (Error e) {
			log.error(e.getMessage());
			return false;
		}
	}

	public static void main(String[] args){
		HaspLicenseLoader loader = new HaspLicenseLoader();
		License license = loader.loadLicense(new DefaultLicenseProvider());
		System.out.println(license.toXML());
		
		loader.checkInternal(new DefaultLicenseProvider(), license, new CheckCallback(){
			public void checkFailed() {
				System.out.println("failed.");
			}
			public void checkSuccess() {
				System.out.println("OK.");
			}
		});
		

		
//		Hasp hasp = new Hasp(HaspConfig.HASP_LICENSE_FID);
//		hasp.loginScope(HaspConfig.LOCAL_SCOPE, HaspConfig.VENDOR_CODE);
//		
//		byte[] rodata = new byte[112];
//		hasp.read(Hasp.HASP_FILEID_RO, 0, rodata);
//		int status = hasp.getLastError();
//		if(status != HaspStatus.HASP_STATUS_OK){
//			log.debug("can not read data from RO, error #" + status);
//			hasp.logout();
//			return;
//		}
//		
//		//解析RO数据
//		HaspRO hm = new HaspRO();
//		try {
//			hm.fromBytes(rodata);
//		} catch (Exception e) {
//			log.debug("parse RO data failed", e);
//		}
//		
//		System.out.println(hm.getVersion());
//		System.out.println(hm.getAllowedUserCount());
//		System.out.println(hm.getNumClusterMembers());
	}
}
