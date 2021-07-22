/*
 * $Id: AbstractWorkProcessManager.java 4624 2011-08-29 09:24:19Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 * 
 */
package cn.redflagsoft.base.process;

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.aop.Advice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.util.Assert;
import org.springframework.aop.framework.ProxyFactory;


/**
 * @author Alex Lin
 *
 */
public abstract class AbstractWorkProcessManager implements WorkProcessManager {
	protected final Log log = LogFactory.getLog(getClass());
	
	//private List<Advice> workProcessAdvices = new ArrayList<Advice>();
	private Map<Integer, String> names = new HashMap<Integer, String>(); 
	private Advice[] workProcessAdvices;
	private boolean optimize = false;
	private boolean proxied = false;
	
	private WorkProcess buildProxy(WorkProcess workProcess){
		if(workProcessAdvices != null && workProcessAdvices.length > 0){
			ProxyFactory factory = new ProxyFactory(workProcess);
			factory.addInterface(WorkProcess.class);
			factory.setOptimize(optimize);
			//factory.setExposeProxy(true);
			for(Advice advice: workProcessAdvices){
				factory.addAdvice(advice);
			}
			log.debug("Build proxied WorkProcess for " + workProcess);
			return (WorkProcess) factory.getProxy();
		}
		return workProcess;
	}
	
	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.process.WorkProcessManager#execute(short, java.util.Map, boolean)
	 */
	@SuppressWarnings("unchecked")
	public final Object execute(int processType, Map parameters, boolean needLog) {
		return getProcess(processType).execute(parameters, needLog);
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.process.WorkProcessManager#getProcess(int)
	 */
	public final WorkProcess getProcess(int processType) {
		String name = names.get(processType);
		Assert.notNull(name, "找不到注册的WorkProcess: processType = " + processType);
		return getProcess(name);
	}
	
	
	public final WorkProcess getProcess(String name){
		WorkProcess wp = buildWorkProcess(name);
		if(isProxied()){
			return wp;
		}else{
			return buildProxy(wp);
		}
	}

	/**
	 * 根据名称创建WorkProcess。
	 * 子类中根据proxied参数指定该类是不是在子类中已经代理过了。
	 * 
	 * @param name
	 * @return
	 */
	protected abstract WorkProcess buildWorkProcess(String name);



	/**
	 * @return the names
	 */
	public Map<Integer, String> getNames() {
		return names;
	}

	/**
	 * @param names the names to set
	 */
	public void setNames(Map<Integer, String> names) {
		this.names = names;
	}

	/**
	 * @return the workProcessAdvices
	 */
	public Advice[] getWorkProcessAdvices() {
		return workProcessAdvices;
	}

	/**
	 * @param workProcessAdvices the workProcessAdvices to set
	 */
	public void setWorkProcessAdvices(Advice[] workProcessAdvices) {
		this.workProcessAdvices = workProcessAdvices;
	}

	/**
	 * @return the optimize
	 */
	public boolean isOptimize() {
		return optimize;
	}

	/**
	 * @param optimize the optimize to set
	 */
	public void setOptimize(boolean optimize) {
		this.optimize = optimize;
	}

	/**
	 * 该类是不是已经代理过了。
	 * 
	 * @return the proxied
	 */
	public boolean isProxied() {
		return proxied;
	}

	/**
	 * @param proxied the proxied to set
	 */
	public void setProxied(boolean proxied) {
		this.proxied = proxied;
	}
}
