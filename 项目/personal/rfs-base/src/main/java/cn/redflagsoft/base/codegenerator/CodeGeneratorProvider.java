/*
 * $Id: CodeGeneratorProvider.java 5240 2011-12-21 09:27:55Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.codegenerator;

import cn.redflagsoft.base.bean.RFSEntityObject;

/**
 * @author Alex Lin
 *
 */
public interface CodeGeneratorProvider {
//	/**
//	 * 
//	 * @param clazz
//	 * @param bizCategory
//	 * @param bizType
//	 * @param clerkID
//	 * @param orgID
//	 * @return
//	 */
//	String generateCode(Class<?> clazz, Byte bizCategory, Integer bizType, Long clerkID, Long orgID);
//	/**
//	 * 
//	 * @param bizCategory
//	 * @param bizType
//	 * @param clerkID
//	 * @param orgID
//	 * @return
//	 */
//	//String generateCode(Byte bizCategory, Short bizType, Long clerkID, Long orgID);
//	
//	/**
//	 * 产生对应id的code，系统将自动取得当前登录用户的用户ID，和部门ID。
//	 * 
//	 * @param clazz 指某类对象。
//	 * @param bizCategory
//	 * @param bizType
//	 * @return
//	 */
//	String generateCode(Class<?> clazz, Byte bizCategory, Integer bizType);
//	
//	/**
//	 * 产生对应category的code，系统将自动取得当前登录用户的用户ID，和部门ID。
//	 * 
//	 * @param bizCategory
//	 * @param bizType
//	 * @return
//	 */
//	//String generateCode(Byte bizCategory, Short bizType);
	
	
	String generateCode(RFSEntityObject object);
}
