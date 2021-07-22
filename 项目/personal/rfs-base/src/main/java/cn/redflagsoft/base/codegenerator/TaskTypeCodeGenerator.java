/*
 * $Id: TaskTypeCodeGenerator.java 5240 2011-12-21 09:27:55Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.codegenerator;

import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.Task;

/**
 * Task的编号生成器，每种task（taskType）归为一类。
 * @author mwx
 */
public class TaskTypeCodeGenerator extends DateBasedCodeGenerator implements CodeGenerator{

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.codegenerator.DateBasedCodeGenerator#getCodeId(cn.redflagsoft.base.bean.RFSEntityObject)
	 */
	@Override
	protected String getCodeId(RFSEntityObject object) {
		Assert.isInstanceOf(Task.class, object, "必须是Task对象");
		Task task = (Task) object;
		int taskType = task.getType();
		String codeId = "task" + taskType;
		return codeId;
	}
}