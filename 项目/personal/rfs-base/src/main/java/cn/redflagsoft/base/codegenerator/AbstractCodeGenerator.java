/*
 * $Id: AbstractCodeGenerator.java 5245 2011-12-22 01:47:09Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
 * Code生成器。
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
	 * 获取实体对应的code对象的ID。
	 * @param object
	 * @return
	 */
	protected abstract String getCodeId(RFSEntityObject object);
	
	/**
	 * 获取实体编号规则中的base部分。
	 * @param object
	 * @return
	 */
	protected abstract long getCodeBase(RFSEntityObject object);
	
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.codegenerator.CodeGenerator#generateCode(cn.redflagsoft.base.bean.RFSEntityObject)
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public final synchronized String generateCode(RFSEntityObject object) {
		Assert.notNull(object, "实体对象不能为空");
		
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
			//已经变化了，注意，base部分不能和之前已经使用过得重复
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
