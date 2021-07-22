/*
 * $Id: CodeServiceImpl.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service.impl;

import cn.redflagsoft.base.bean.Code;
import cn.redflagsoft.base.dao.CodeDao;
import cn.redflagsoft.base.service.CodeService;

/**
 * @author mwx
 *
 */
public class CodeServiceImpl implements CodeService{
	private CodeDao codeDao;

	public CodeDao getCodeDao() {
		return codeDao;
	}

	public void setCodeDao(CodeDao codeDao) {
		this.codeDao = codeDao;
	}

	public int deleteCode(Code code) {
		return codeDao.delete(code);
	}

	public Code saveCode(Code code) {
		return codeDao.save(code);
	}

	public Code updateCode(Code code) {
		return codeDao.update(code);
	}

	public Code getCode(String id) {
		return codeDao.get(id);
	}

}
