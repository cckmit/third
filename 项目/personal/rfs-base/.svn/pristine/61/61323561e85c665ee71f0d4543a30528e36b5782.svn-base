/*
 * $Id$
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.codegenerator;

import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.test.BaseTests;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class CodeGeneratorProviderTest extends BaseTests{
	protected CodeGeneratorProvider codeGeneratorProvider;
	
	public void testGenerateCodes(){
		RFSEntityObject entityObject = new RFSEntityObject() {
			public int getObjectType() {
				return -1000;
			}
			
			public Long getId() {
				return -1000L;
			}
		};
		
		for(int i = 0 ; i < 100 ; i++){
			String code = codeGeneratorProvider.generateCode(entityObject);
			System.out.println(code);
		}
	}

}
