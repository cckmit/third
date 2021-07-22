/**
 * Copyright (C) 2006-2009 Alex Lin. All rights reserved.
 *
 * Alex PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms. See http://www.opoo.org/apps/license.txt for details.
 */
package org.opoo.apps.license;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.opoo.util.Assert;

import com.jivesoftware.license.License;


/**
 * 应用级 License.
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class AppsLicense extends License {
	private static final long serialVersionUID = -70259821264677110L;

	private License license;

	public AppsLicense(License license) {
		this.license = license;
		Assert.notNull(license, "原始 License 对象不能为空。");
	}

	@Override
	public void setClient(String name, String company) {
		license.setClient(name, company);
	}

	@Override
	public void setModule(String moduleName) {
		license.setModule(moduleName);
	}

	@Override
	public void setModules(String[] modules) {
		license.setModules(modules);
	}

	@Override
	public void setVersion(String version) {
		license.setVersion(version);
	}

	public long getID() {
		return license.getID();
	}

	public void setID(long l) {
		license.setID(l);
	}

	public String getName() {
		return license.getName();
	}

	public void setName(String module) {
		license.setName(module);
	}

	public Date getCreationDate() {
		return license.getCreationDate();
	}

	public com.jivesoftware.license.License.Version getVersion() {
		return license.getVersion();
	}

	public void setVersion(com.jivesoftware.license.License.Version version) {
		license.setVersion(version);
	}

	public void setCreationDate(Date date) {
		license.setCreationDate(date);
	}

	public com.jivesoftware.license.License.Client getClient() {
		return license.getClient();
	}

	public void setClient(com.jivesoftware.license.License.Client client) {
		license.setClient(client);
	}

	public List<License.Module> getModules() {
		return license.getModules();
	}

	public void setModules(List<License.Module> modules) {
		license.setModules(modules);
	}

	public Map<String, String> getProperties() {
		return license.getProperties();
	}

	public void setProperties(Map<String, String> map) {
		license.setProperties(map);
	}

	public String getSignature() {
		return license.getSignature();
	}

	public void setSignature(String module) {
		license.setSignature(module);
	}

	public com.jivesoftware.license.License.Type getType() {
		return license.getType();
	}

	public void setType(com.jivesoftware.license.License.Type type) {
		license.setType(type);
	}

	public byte[] getFingerprint() {
		return license.getFingerprint();
	}

	public boolean equals(Object o) {
		return license.equals(o);
	}

	public int hashCode() {
		return license.hashCode();
	}

	public String toString() {
		return license.toString();
	}

	public String toXML() {
		return license.toXML();
	}

	public String getEdition() {
		return license.getEdition();
	}

	public void setEdition(String module) {
		license.setEdition(module);
	}

	public Date getExpirationDate() {
		String s = getProperties().get(AppsLicenseManager.AppsLicenseProperty.expirationDate.name());
		return parseDate(s);
	}
	
	public String getGrantedIP(){
		return getProperties().get(AppsLicenseManager.AppsLicenseProperty.grantedIP.name());
	}
	
	public String getReportURL(){
		return getProperties().get(AppsLicenseManager.AppsLicenseProperty.reportURL.name());
	}
	
	public String getDevMode(){
		return getProperties().get(AppsLicenseManager.AppsLicenseProperty.devMode.name());
	}

	public int getAllowedUserCount() {
		String userCount = getProperties().get(AppsLicenseManager.AppsLicenseProperty.allowedUserCount.name());
		if (userCount != null) {
			return Integer.parseInt(userCount);
		}
		return -1;
	}

	public int getNumClusterMembers() {
		int allowedMembers = 1;
		String s = getProperties().get(AppsLicenseManager.AppsLicenseProperty.numClusterMembers.name());
		if (s != null) {
			int numClusterMembers = Integer.parseInt(s);
			if (numClusterMembers < allowedMembers)
				allowedMembers = numClusterMembers;
			if (allowedMembers == 1)
				allowedMembers = numClusterMembers;
		}
		return allowedMembers;
	}

	public int getNumberOfClusterMembers() {
		String s = getProperties().get(AppsLicenseManager.AppsLicenseProperty.numClusterMembers.name());
		if (s != null){
			return Integer.parseInt(s);
		}else{
			return -1;
		}
	}
	
	public boolean isInvalidLicense(){
		String isInvalid = getProperties().get("invalid");
        return isInvalid != null && "true".equals( isInvalid );
	}
	
	public static enum Edition{
		client, developer, standalone
	}
}
