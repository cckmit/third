/*
 * $Id: OldTaskTypeCodeGenerator.java 5240 2011-12-21 09:27:55Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.codegenerator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.opoo.apps.util.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Code;
import cn.redflagsoft.base.bean.RFSEntityObject;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.service.CodeService;

/**
 * Task的编号生成器，每种task（taskType）归为一类。
 * @author mwx
 * @deprecated using {@link TaskTypeCodeGenerator}
 */
public class OldTaskTypeCodeGenerator implements CodeGenerator{
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
	private CodeService codeService;
//	public TaskCodeGenerator(CodeService codeService){
//		codeService = new CodeServiceImpl();
//	}
//	private IdGenerator<String> idGenerator;
//	private IdGeneratorProvider<Long> idGeneratorProvider ;
//	private int autoGeneratorLength = 4;
//	private static final String ID_KEY = "TASKKEY";
	
//	/**
//	 * @return the idGenerator
//	 */
//	public IdGenerator<String> getIdGenerator() {
//		return idGenerator;
//	}
//	public IdGeneratorProvider<Long> getIdGeneratorProvider() {
//		return idGeneratorProvider;
//	}
//	public void setIdGeneratorProvider(IdGeneratorProvider<Long> idGeneratorProvider) {
//		this.idGeneratorProvider = idGeneratorProvider;
//	}
//	/**
//	 * @param idGenerator the idGenerator to set
//	 */
//	public void setIdGenerator(IdGenerator<String> idGenerator) {
//		this.idGenerator = idGenerator;
//	}
	
	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public synchronized String generateCode(RFSEntityObject object) {
		Assert.isInstanceOf(Task.class, object, "必须是Task对象");
		Task task = (Task) object;
		int taskType = task.getType();
//		String b = bizCategory != null ? bizCategory + "" : "";
		String a = taskType + "";//bizType != null ? bizType + "" : "";
		String date = dateFormat.format(new Date());
		
		long base = Long.parseLong(date);
		String codeId = "task" + taskType;
		
		String codeResult ="";
		Code code = codeService.getCode(codeId);
		if(code == null){
			code = new Code();
			code.setId(codeId);
			code.setCurrent(1);
			code.setBase(base);
			codeService.saveCode(code);
			codeResult = date+a+StringUtils.zeroPadString(code.getCurrent()+"", 4);
			
		}else{
			if(code.getBase() != base){
				code.setBase(base);
				code.setCurrent(1);
				codeService.updateCode(code);
				codeResult = date+a+StringUtils.zeroPadString(code.getCurrent()+"", 4);
			}else{
				long current = code.getCurrent()+1; 
				code.setCurrent(current);
				codeService.updateCode(code);
				codeResult = date+a+StringUtils.zeroPadString(code.getCurrent()+"", 4);
				
			}
		}
		
//		idGenerator = new StringIdGeneratorWrapper(idGeneratorProvider.getIdGenerator(ID_KEY), autoGeneratorLength);
//		if(idGenerator != null){
//			code += idGenerator.getNext();
//		}
		return codeResult;
	}
}