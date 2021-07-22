package cn.redflagsoft.base.vo;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import cn.redflagsoft.base.bean.Task;

/**
 * 
 * @author ZF
 *
 */
public class TaskBaseinfoVO extends Task {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private String timeLimitLabel;
	@SuppressWarnings("unused")
	private String timeHangLabel;
	@SuppressWarnings("unused")
	private String timeDelayLabel;
	@SuppressWarnings("unused")
	private String surplusTimeLabel;
	@SuppressWarnings("unused")
	private String timeusedLabel;
	
	@SuppressWarnings("unused")
	private String totalTimeHangLabel;
	
	public String getTimeLimitLabel() {
		return super.getTimeLimit()+super.getTimeUnitName();
	}
	
	public void setTimeLimitLabel(String timeLimitLabel) {
		this.timeLimitLabel = timeLimitLabel;
	}
	
	public String getTimeHangLabel() {
		return super.getTimeHang()+super.getTimeUnitName();
	}
	
	public void setTimeHangLabel(String timeHangLabel) {
		this.timeHangLabel = timeHangLabel;
	}
	
	public String getTimeDelayLabel() {
		return super.getTimeDelay()+super.getTimeUnitName();
	}
	
	public void setTimeDelayLabel(String timeDelayLabel) {
		this.timeDelayLabel = timeDelayLabel;
	}
	
	/**
	 * 如果Task无监察信息（即timeLimit值为0），则直接返回 "-"。
	 * 该问题描述在 <a href="http://192.168.18.6/sf/go/artf1343">artf1343</a> 。
	 * @return
	 */
	public String getSurplusTimeLabel() {
		if(getTimeLimit() == 0){
			return "-";
		}
		return super.getSurplus()+super.getTimeUnitName();
	}
	
	public void setSurplusTimeLabel(String surplusTimeLabel) {
		this.surplusTimeLabel = surplusTimeLabel;
	}
	
	public String getTimeusedLabel() {
		return super.getTimeused() + super.getTimeUnitName();
	}

	public void setTimeusedLabel(String timeusedLabel) {
		this.timeusedLabel = timeusedLabel;
	}
	
	public String getTotalTimeHangLabel() {
		return super.getTotalTimeHang()+ super.getTimeUnitName();
	}

	public void setTotalTimeHangLabel(String totalTimeHangLabel) {
		this.totalTimeHangLabel = totalTimeHangLabel;
	}

	public TaskBaseinfoVO(Task t){
		 try {
				PropertyUtils.copyProperties(this, t);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
	    
	}

}
