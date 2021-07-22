/*
 * $Id: AddressDao.java 3996 2010-10-18 06:56:46Z lcj $
 * AddressDao.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.dao;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.Address;

/**
 * @author Administrator
 *
 */
public interface AddressDao extends Dao<Address,Long>{
	Address getAddress(String name);
}
