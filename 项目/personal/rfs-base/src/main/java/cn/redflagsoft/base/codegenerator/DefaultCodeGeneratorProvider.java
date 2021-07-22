/*
 * $Id: DefaultCodeGeneratorProvider.java 5245 2011-12-22 01:47:09Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.codegenerator;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;

import cn.redflagsoft.base.bean.RFSEntityObject;

/**
 * @author Alex Lin
 *
 */
public class DefaultCodeGeneratorProvider implements CodeGeneratorProvider, InitializingBean {
	private static final Log log = LogFactory.getLog(DefaultCodeGeneratorProvider.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	//private static final String ID_KEY = "CGPKEY";
	private Map<String, CodeGenerator> codeGenerators;
	private CodeGenerator defaultCodeGenerator;// = new DateBasedCodeGenerator();//new SimpleCodeGenerator();
	
	//private IdGeneratorProvider<Long> idGeneratorProvider;
	//private IdGenerator<String> idGenerator;
	//private int autoGeneratorLength = 8;
	
	public DefaultCodeGeneratorProvider(){
		
	}
	
//	
//	/* (non-Javadoc)
//	 * @see cn.redflagsoft.base.codegenerator.CodeGeneratorProvider#generateCode(java.lang.Class, java.lang.Byte, java.lang.Short, java.lang.Long, java.lang.Long)
//	 */
//	public String generateCode(Class<?> clazz, Byte bizCategory, Integer bizType, Long clerkID, Long orgID) {
//		String name = clazz.getSimpleName();
//		CodeGenerator gen = codeGenerators.get(name);
//		if(gen != null){
//			return gen.generateCode(bizCategory, bizType, clerkID, orgID);
//		}
//		if(log.isDebugEnabled()){
//			log.warn("没有为类" + clazz.getName() + "找到指定的CodeGenerator，将使用简单Code生成原则。");
//		}
//		String code = defaultCodeGenerator.generateCode(bizCategory, bizType, clerkID, orgID);
//		if(idGenerator != null){
//			code += idGenerator.getNext();
//		}
//		return code;
//	}
//
//	/* (non-Javadoc)
//	 * @see cn.redflagsoft.base.codegenerator.CodeGeneratorProvider#generateCode(java.lang.Class, java.lang.Byte, java.lang.Short)
//	 */
//	public String generateCode(Class<?> clazz, Byte bizCategory, Integer bizType) {
//		//取得当前用户的相关信息
//		Clerk clerk = null;
//		try{
//			clerk=UserClerkHolder.getClerk();
//		}catch(Exception e){
//			
//		}
//		Long clerkId = -1L;
//		Long orgId = 0L;
//		if(clerk!=null){
//		clerkId = clerk.getId();
//		orgId = clerk.getEntityID();
//		}
//		return generateCode(clazz, bizCategory, bizType, clerkId, orgId);
//	}

	/**
	 * @return the codeGenerators
	 */
	public Map<String, CodeGenerator> getCodeGenerators() {
		return codeGenerators;
	}

	/**
	 * @param codeGenerators the codeGenerators to set
	 */
	public void setCodeGenerators(Map<String, CodeGenerator> codeGenerators) {
		this.codeGenerators = codeGenerators;
	}
	
	
//	public void setIdGeneratorProvider(IdGeneratorProvider<Long> idGeneratorProvider){
//		//IdGenerator<Long> idg = idGeneratorProvider.getIdGenerator("ID_KEY");
//		//idGenerator = new StringIdGeneratorWrapper(idg, 8);
//		this.idGeneratorProvider = idGeneratorProvider;
//	}
//
//	
//
//
//
//	/**
//	 * @return the autoGeneratorLength
//	 */
//	public int getAutoGeneratorLength() {
//		return autoGeneratorLength;
//	}
//
//
//	/**
//	 * @param autoGeneratorLength the autoGeneratorLength to set
//	 */
//	public void setAutoGeneratorLength(int autoGeneratorLength) {
//		this.autoGeneratorLength = autoGeneratorLength;
//	}	
//	
//
//
//
//	/**
//	 * @return the idGenerator
//	 */
//	public IdGenerator<String> getIdGenerator() {
//		return idGenerator;
//	}
//
//
//	/**
//	 * @param idGenerator the idGenerator to set
//	 */
//	public void setIdGenerator(IdGenerator<String> idGenerator) {
//		this.idGenerator = idGenerator;
//	}
//
//
//	/**
//	 * @return the idGeneratorProvider
//	 */
//	public IdGeneratorProvider<Long> getIdGeneratorProvider() {
//		return idGeneratorProvider;
//	}

	/**
	 * @return the defaultCodeGenerator
	 */
	public CodeGenerator getDefaultCodeGenerator() {
		return defaultCodeGenerator;
	}

	/**
	 * @param defaultCodeGenerator the defaultCodeGenerator to set
	 */
	public void setDefaultCodeGenerator(CodeGenerator defaultCodeGenerator) {
		this.defaultCodeGenerator = defaultCodeGenerator;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
//		if(idGenerator == null && idGeneratorProvider != null){
//			idGenerator = new StringIdGeneratorWrapper(idGeneratorProvider.getIdGenerator(ID_KEY), autoGeneratorLength);
//		}
		Assert.notNull(defaultCodeGenerator, "默认的编号生成器不能为空，请配置");
		if(codeGenerators == null){
			codeGenerators = Maps.newHashMap();
		}
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.codegenerator.CodeGeneratorProvider#generateCode(cn.redflagsoft.base.bean.RFSEntityObject)
	 */
	public String generateCode(RFSEntityObject object) {
		Class<?> clazz = object.getClass();
		String name = clazz.getSimpleName();
		CodeGenerator gen = codeGenerators.get(name);
		if(gen != null){
			return gen.generateCode(object);
		}
		
		if(IS_DEBUG_ENABLED){
			log.debug("没有为对象 " + object + " 找到指定的 CodeGenerator，将使用默认 Code 生成原则。");
		}
		
		String code = defaultCodeGenerator.generateCode(object);
		if(IS_DEBUG_ENABLED){
			log.debug("对象 " + object + " 生成了新编号：" + code);
		}
		return code;
	}
	
	
	public static void main(String[] args){
		DefaultCodeGeneratorProvider dcgp = new DefaultCodeGeneratorProvider();
		System.out.println(dcgp);
	}
}
