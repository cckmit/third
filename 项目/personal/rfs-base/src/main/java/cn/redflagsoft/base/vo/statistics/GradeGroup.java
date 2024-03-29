/*
 * $Id: GradeGroup.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.vo.statistics;

/**
 * @author Alex Lin
 *
 */
public class GradeGroup implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4892225580226495352L;
	private Grade normal = new Grade();
	private Grade blue = new Grade();
	private Grade yellow = new Grade();
	private Grade red = new Grade();
	private Grade white = new Grade();
	private Grade orange = new Grade();
	private Grade black = new Grade();
		
	
	public GradeGroup(){
		
	}
	
	public GradeGroup(Grade normal, Grade blue, Grade yellow, Grade red, Grade white, Grade orange, Grade black){
		this.normal = normal;
		this.blue = blue;
		this.yellow = yellow;
		this.red = red;
		this.white = white;
		this.orange = orange;
		this.black = black;
	}
	/**
	 * @return the normal
	 */
	public Grade getNormal() {
		return normal;
	}
	/**
	 * @param normal the normal to set
	 */
	public void setNormal(Grade normal) {
		this.normal = normal;
	}
	/**
	 * @return the warning
	 */
	public Grade getBlue() {
		return blue;
	}
	/**
	 * @param warning the warning to set
	 */
	public void setBlue(Grade blue) {
		this.blue = blue;
	}
	/**
	 * @return the yellow
	 */
	public Grade getYellow() {
		return yellow;
	}
	/**
	 * @param yellow the yellow to set
	 */
	public void setYellow(Grade yellow) {
		this.yellow = yellow;
	}
	/**
	 * @return the red
	 */
	public Grade getRed() {
		return red;
	}
	/**
	 * @param red the red to set
	 */
	public void setRed(Grade red) {
		this.red = red;
	}
	
	public Grade getWhite() {
		return white;
	}

	public void setWhite(Grade white) {
		this.white = white;
	}

	public Grade getOrange() {
		return orange;
	}

	public void setOrange(Grade orange) {
		this.orange = orange;
	}

	public Grade getBlack() {
		return black;
	}

	public void setBlack(Grade black) {
		this.black = black;
	}

	public String toString(){
		return hashCode() + "@GradeGroup(nornal=" + normal + ", "
			+ "warning=" + blue + ", " 
			+ "yellow=" + yellow + ", " 
			+ "white=" + white + ", "
			+ "orange=" + orange + ", "
			+ "black=" + black + ", "
			+ "red=" + red + ")";
	}
}
