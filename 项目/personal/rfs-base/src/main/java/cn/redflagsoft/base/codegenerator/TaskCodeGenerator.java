/*
 * $Id: TaskCodeGenerator.java 5240 2011-12-21 09:27:55Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.codegenerator;

import cn.redflagsoft.base.bean.RFSEntityObject;

/**
 * Task的编号生成器，所有task归为一类。
 * 
 * @author mwx
 * @author lcj
 * @deprecated using {@link DateBasedCodeGenerator}
 */
public class TaskCodeGenerator extends DateBasedCodeGenerator implements CodeGenerator{
	//private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.codegenerator.AbstractCodeGenerator#getCodeId(cn.redflagsoft.base.bean.RFSEntityObject)
	 */
	@Override
	protected String getCodeId(RFSEntityObject object) {
		return "task";
	}
}
