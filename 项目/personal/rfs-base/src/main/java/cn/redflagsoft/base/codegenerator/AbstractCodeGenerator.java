/*
 * $Id: AbstractCodeGenerator.java 5245 2011-12-22 01:47:09Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.codegenerator;

import org.opoo.apps.util.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Code;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.service.CodeService;

/**
 * Code��������
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public abstract class AbstractCodeGenerator implements CodeGenerator,InitializingBean {
	private CodeService codeService;
	
	/**
	 * @return the codeService
	 */
	public CodeService getCodeService() {
		return codeService;
	}

	/**
	 * @param codeService the codeService to set
	 */
	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	/**
	 * ��ȡʵ���Ӧ��code�����ID��
	 * @param object
	 * @return
	 */
	protected abstract String getCodeId(RFSEntityObject object);
	
	/**
	 * ��ȡʵ���Ź����е�base���֡�
	 * @param object
	 * @return
	 */
	protected abstract long getCodeBase(RFSEntityObject object);
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.codegenerator.CodeGenerator#generateCode(cn.redflagsoft.base.bean.RFSEntityObject)
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public final synchronized String generateCode(RFSEntityObject object) {
		Assert.notNull(object, "ʵ�������Ϊ��");
		
		long base = getCodeBase(object);
		String codeId = getCodeId(object);
		
		Code code = codeService.getCode(codeId);
		long current = 1;
		if(code == null){
			code = new Code();
			code.setId(codeId);
			code.setCurrent(current);
			code.setBase(base);
			codeService.saveCode(code);
		}else{
			//�Ѿ��仯�ˣ�ע�⣬base���ֲ��ܺ�֮ǰ�Ѿ�ʹ�ù����ظ�
			if(code.getBase() != base){
				code.setBase(base);
				code.setCurrent(1);
				codeService.updateCode(code);
			}else{
				current = code.getCurrent() + 1; 
				code.setCurrent(current);
				codeService.updateCode(code);
			}
		}
		
		StringBuffer buffer = new StringBuffer().append(base).append(StringUtils.zeroPadString(current + "", 4));
		return buffer.toString();
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(codeService, "codeService is required.");
	}
	
	
}
