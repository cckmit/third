/*
 * $Id: VersionableBean.java 6335 2013-12-11 06:30:56Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.util.Date;

import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.opoo.ndao.domain.Versionable;

import cn.redflagsoft.base.security.AuthUtils;
import cn.redflagsoft.base.security.InternalUser;
import cn.redflagsoft.base.service.ClerkService;


/**
 * ��ʶ�˴����ߣ��޸��ߵ�ʵ���ࡣ
 * 
 * @author Alex Lin
 *
 */
public abstract class VersionableBean extends BaseBean implements VersionLogable, Versionable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3844665144002142247L;

	private Date creationTime;
	
	private Date modificationTime;
	
	
	/**
	 * �����ߡ�
	 */
	private Long creator;
	/**
	 * �޸��ߡ�
	 */
	private Long modifier;
	/**
	 * �����ߵı�ʶ��
	 * @return Long
	 */
	public Long getCreator() {
		return creator;
	}
	
	/**
	 * 
	 * @param creator
	 */
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	/**
	 * �޸��ߵı�ʶ��
	 * @return Long
	 */
	public Long getModifier() {
		return modifier;
	}
	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the modificationTime
	 */
	public Date getModificationTime() {
		return modificationTime;
	}

	/**
	 * @param modificationTime the modificationTime to set
	 */
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}	
	
	public String getCreatorName(){
		if(getCreator() != null && isVersionUpdatorNameParsed()){
			return getClerkName(getCreator());
		}
		return null;
	}
	
	public String getModifierName(){
		if(getModifier() != null && isVersionUpdatorNameParsed()){
			return getClerkName(getModifier());
		}
		return null;
	}
	
	/**
	 * @param modifier2
	 * @return
	 */
	private String getClerkName(Long clerkId) {
		if(clerkId == null){
			return null;
		}
		if(clerkId.longValue() <= 0L){
			return null;
		}
		InternalUser user = AuthUtils.getInternalUser(clerkId);
		if(user != null){
			return user.getName();
		}
		try {
			ClerkService service = Application.getContext().get("clerkService", ClerkService.class);
			if(service == null){
				return null;
			}
			Clerk clerk = service.getClerk(clerkId);
			if(clerk == null){
				return null;
			}
			return clerk.getName();
		} catch (Throwable e) {
			LogFactory.getLog(getClass()).debug(e.getMessage(), e);
		}
		return null;
	}

	private boolean isVersionUpdatorNameParsed(){
		try{
			return AppsGlobals.getProperty("VersionUpdatorNameParsed", true);
		}catch(Exception e){
			return false;
		}
	}
}
