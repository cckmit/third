/*
 * $Id: CodeGenerator.java 5240 2011-12-21 09:27:55Z lcj $
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
public interface CodeGenerator {

	/**
	 * 未指定实体生成编号。
	 * @param obbject
	 * @return
	 */
	String generateCode(RFSEntityObject object);
}
