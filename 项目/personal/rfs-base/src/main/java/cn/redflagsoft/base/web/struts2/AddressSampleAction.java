/*
 * $Id: AddressSampleAction.java 3996 2010-10-18 06:56:46Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.web.struts2;

import org.opoo.apps.web.struts2.AbstractAppsAction;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Address;
import cn.redflagsoft.base.service.AddressService;

/**
 * @author Alex Lin
 *
 * @deprecated 仅仅是示例，勿用于实际产品
 * @since 1.0
 */
public class AddressSampleAction extends AbstractAppsAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4319523351213097641L;
	private Address address;
	private AddressService addressService;

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	/**
	 * 查询时参数为 address.id=8888
	 * @return
	 */
	public String get(){
		Long id = address.getId();
		System.out.println(id);
		Address addr = addressService.getAddress(id);
		if(addr == null){
			model.setMessage(false, "找不到查询对象", null);
		}else{
			model.setData(addr);
		}
		
		return SUCCESS;
	}
	
	public String list(){
		model.setRows(addressService.findAddresses(ResultFilter.createEmptyResultFilter()));
		
		return SUCCESS;
	}
	

	/**
	 * @return the addressService
	 */
	public AddressService getAddressService() {
		return addressService;
	}

	/**
	 * @param addressService the addressService to set
	 */
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}
	
}
