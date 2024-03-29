/*
 * VersionLogable.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.bean;

import org.opoo.ndao.domain.Versionable;

/**
 * @author Alex Lin
 *
 */
public interface VersionLogable extends Versionable {
	public Long getCreator();
	public void setCreator(Long creator);
	public Long getModifier();
	public void setModifier(Long modifier);
}
