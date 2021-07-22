/*
 * $Id$
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
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
