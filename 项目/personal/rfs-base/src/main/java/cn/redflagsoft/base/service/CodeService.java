/*
 * $Id: CodeService.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service;

import cn.redflagsoft.base.bean.Code;

/**
 * @author mwx
 *
 */
public interface CodeService {
	Code saveCode(Code code);
	Code updateCode(Code code);
	int deleteCode(Code code);
	Code getCode(String id);
}
