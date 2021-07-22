package org.opoo.apps.bean.core;

import org.opoo.apps.bean.StringKeyBean;


/**
 * 系统 ID 类。
 * 
 * 供 ID 生成器调用。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SysId extends StringKeyBean {

	private static final long serialVersionUID = -5756705177056599438L;
	private String description;
	private long current;
	private int step = 1;
	
	public SysId(String id){
		this(id, 1L);
	}
	public SysId(){
	}
	
	public SysId(String id, long current){
		super.setId(id);
		this.current = current;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getCurrent() {
		return current;
	}
	public void setCurrent(long current) {
		this.current = current;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
}
