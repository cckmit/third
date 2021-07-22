/**
 * Copyright (C) 2006-2009 Alex Lin. All rights reserved.
 *
 * Alex PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms. See http://www.opoo.org/apps/license.txt for details.
 */
package org.opoo.apps.license;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.opoo.apps.AppsGlobals;
import org.opoo.apps.license.validator.CheckSignatureValidator;
import org.opoo.apps.license.validator.ExpirationDateValidator;
import org.opoo.apps.license.validator.GrantedIPValidator;
import org.opoo.apps.license.validator.NameValidator;
import org.springframework.util.ResourceUtils;

import com.jivesoftware.license.License;
import com.jivesoftware.license.validator.Validator;
import com.jivesoftware.license.validator.VersionValidator;

public class DefaultLicenseProvider implements AppsLicenseProvider {
//
//	public Collection<Module> getInstalledModules() {
//		List<License.Module> modules = new ArrayList<License.Module>();
//        modules.add(new License.Module("core"));
//        return Collections.unmodifiableCollection(modules);
//	}
//
//	public String getName() {
//		return "";
//	}
//
//
//
//	public Version getVersion() {
//		return new License.Version(1, 0, 0);
//	}	
	
	private boolean devMode = AppsGlobals.isDevMode();
	
	public static final String DEFAULT_NAME = "opoo-apps";
	
	private String name = DEFAULT_NAME;
	private License.Version version;
	private List<License.Module> modules;
	
	public DefaultLicenseProvider(){
		//product = AppsGlobals.getBeansHolder().get("product", Product.class);
		if(!devMode){
			//product = Application.getContext().get("product", Product.class);
			//Map map = ((ApplicationContext)Application.getContext()).getBeansOfType(Product.class);
			//if(map != null && map)
			
			//System.err.println("找不到产品");
		}else{
			
		}
		
		//Assert.notNull(product);
		//开发模式不抛出异常
		//if(product == null){
		//	throw new LicenseException("找不到产品信息。");
		//}
		
		/* 2011-4-22
		try{
			BuildPropLoader propLoader = new BuildPropLoader();
			String name = propLoader.getProperty("product.name");
			if(StringUtils.isNotBlank(name)){
				this.name = name;
			}
			
			String version = propLoader.getProperty("product.version");
			if(StringUtils.isNotBlank(version)){
				version = version.replace('-', ' ');
				this.version = License.Version.parseVersion(version);
			}
			
			String modules = propLoader.getProperty("product.modules");
			if(StringUtils.isNotBlank(modules)){
				this.modules = new ArrayList<License.Module>();
				StringTokenizer st = new StringTokenizer(modules, ", ");
				while(st.hasMoreTokens()){
					this.modules.add(new License.Module(st.nextToken()));
				}
			}else{
				throw new Exception("No licensed modules.");
			}
		}catch(Exception e){
			//System.err.println(e.getMessage());
			//AppsLicenseLog.LOG.error("Load product build info error", e);
			throw new AppsLicenseException(e.getMessage());
		}
		*/
		
		this.name =  AppsInstanceLoader.getAppsInstance().getName();
		this.version = AppsInstanceLoader.getAppsInstance().getVersion();
		this.modules = new ArrayList<License.Module>(AppsInstanceLoader.getAppsInstance().getModules());
		
		if(version == null){
			version = new License.Version(2, 0, 0);
		}
		
		if(this.modules == null){
			this.modules = new ArrayList<License.Module>();
		}
		
		if(this.modules.isEmpty()){
			this.modules.add(new License.Module("base"));
			this.modules.add(new License.Module("core"));
		}
	}
	
	
	public Collection<Validator> getValidators() {
		List<Validator> validators = new ArrayList<Validator>();
        validators.add(new CheckSignatureValidator());
        validators.add(new NameValidator(getName()));
        validators.add(new ExpirationDateValidator());
        validators.add(new GrantedIPValidator());
        validators.add(new VersionValidator(getVersion()));
        return Collections.unmodifiableCollection(validators);
	}
	
	
	public Collection<License.Module> getInstalledModules() {
		//if(devMode){
//			List<License.Module> modules = new ArrayList<License.Module>();
//			modules.add(new License.Module("core"));
//			modules.add(new License.Module("base"));
			
			return Collections.unmodifiableCollection(modules);
		//}
		
		//return product != null ? product.getModules() : Collections.EMPTY_LIST;
	}

	public String getName() {
//		if(devMode){
//			return "opoo-apps";
//		}
		return name;
	}

	public License.Version getVersion() {
//		if(devMode){
//			return new Version(2, 0, 0);
//		}
		return version;
	}
	
	
	
	public static void main(String[] args) throws IOException{
//		String licenseFile = "trial-internal.license";
//        InputStream stream = DefaultLicenseProvider.class.getClassLoader().getResourceAsStream(licenseFile);
//        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
//        StringBuilder licenseBody = new StringBuilder();
//        try {
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                licenseBody.append(inputLine);
//            }
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        
//        System.out.println(licenseBody.toString());
//        byte[] decode = Base64.decodeBase64(licenseBody.toString().getBytes());
//        System.out.println(new String(decode));
		
		
		
		
//		BuildPropLoader propLoader = new BuildPropLoader();
//		System.out.println(propLoader.getProperties().getPropertyNames());
//		System.out.println(propLoader.getProperty("product.version"));
		
		
		AppsInstanceLoader.initialize(ResourceUtils.getURL("classpath:apps_build.xml"));
		DefaultLicenseProvider provider = new DefaultLicenseProvider();
		Collection<License.Module> installedModules = provider.getInstalledModules();
		String name2 = provider.getName();
		License.Version version2 = provider.getVersion();
		
		System.out.println(installedModules);
		System.out.println(name2);
		System.out.println(version2);
	}
	
}
