/*
 * $Id: Organization.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;
import java.util.List;

import org.springframework.core.Ordered;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface Organization extends Serializable, Ordered {
	
	long getId();
	
	String getCode();
	
	String getName();
	
	String getAbbr();
	
	String getEntityCode();
	
	String getLicense();
	
	String getAuthorizer();
	
	String getHolder();
	
	String getManager();
	
	String getTelNo();
	
	String getFaxNo();
	
	String getLegalAddr();
	
	String getWorkAddr();
	
	Organization getParent();
	
	District getDistrict();

	List<Organization> getSuborgs();
}
