/*
 * $Id: AddressService.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service;

import java.util.List;

import org.opoo.ndao.support.PageableList;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Address;

/**
 * @author Alex Lin
 *
 */
public interface AddressService {

	Address getAddress(Long id);
	Address saveAddress(Address address);
	void updateAddress(Address address);
	
	List<Address> findAddresses(ResultFilter rf);
	PageableList<Address> findPageableAddresses(ResultFilter rf);
}
