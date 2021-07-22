package cn.redflagsoft.base.scheme.schemes;

import java.util.HashMap;
import java.util.Map;

import org.opoo.util.Assert;
import cn.redflagsoft.base.bean.District;
import cn.redflagsoft.base.bean.DistrictBean;
import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.service.DistrictService;

public class DistrictScheme extends AbstractScheme {

	private DistrictService districtService;
	private DistrictBean districtBean;
	
	private String code;
	private String name;
	private String parentCode;
	private int displayOrder;
	private byte status;
	private int type;
	private String remark;
		
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setDistrictBean(DistrictBean districtBean) {
		this.districtBean = districtBean;
	}
	public DistrictBean getDistrictBean() {
		return this.districtBean;
	}	
	public DistrictService getDistrictService() {
		return this.districtService;
	}
	public void setDistrictService (DistrictService districtService) {
		this.districtService = districtService;
	}
	
//	/**
//	 * ���²���-1
//	 * @return
//	 */
//	public Object doUpdateOne() {
//		Assert.notNull(district, "������������Ϊ��");
//		
//		districtService.updateDistrict(district.getCode(), district.getName(), district.getParent(),
//				district.getRemark(), district.getDisplayOrder(), district.getStatus(), district.getType());
//		return "�����ɹ�";
//	}
	
	/**
	 * ���²���-2
	 * @return
	 */
	public Object doUpdateTwo() {
		Assert.notNull(getCode(), "�����������벻��Ϊ��");
		Assert.notNull(getName(), "�����������Ʋ���Ϊ��");
		
		districtService.updateDistrict(getCode(), getName(), getDistrict(getParentCode()), getRemark(), getDisplayOrder(), getStatus(), getType());
		return "�����ɹ�";
	}
	
	/**
	 * ��������-1
	 * @return
	 */
	public Object doSaveOne() {
		Assert.notNull(districtBean, "������������Ϊ��");
		Assert.notNull(districtBean.getCode(), "�����������벻��Ϊ��");
		Assert.notNull(districtBean.getName(), "�����������Ʋ���Ϊ��");
		
		districtService.saveDistrict(districtBean.getCode(), districtBean.getName(), getDistrict(districtBean.getParentCode()),
				districtBean.getRemark(), districtBean.getDisplayOrder(), districtBean.getStatus(), districtBean.getType());
		return "�����ɹ�";
	}
	
	/**
	 * ��������-2
	 * @return
	 */
	public Object doSaveTwo() {
		Assert.notNull(getCode(), "�����������벻��Ϊ��");
		Assert.notNull(getName(), "�����������Ʋ���Ϊ��");
		
		districtService.saveDistrict(getCode(), getName(), getDistrict(getParentCode()),
				getRemark(), getDisplayOrder(), getStatus(), getType());
		return "�����ɹ�";
	}
	
	/**
	 * ɾ������
	 * @return
	 */
	public Object doRemove() {
		Assert.notNull(getCode(), "�����������벻��Ϊ��");
		districtService.removeDistrict(getCode());
		return "�����ɹ�";
	}
	
	/*
	 * ���ݸ�ID�������ϴ���������
	 */
	private District getDistrict(String code) {
		if("null".equalsIgnoreCase(code)){
			code = null;
		}
		return districtService.getDistrict(code);
	}
	
	public Object viewGetDistrict(){
		Assert.notNull(getCode(), "�����������벻��Ϊ��");
		District district = getDistrict(getCode());
		if(district != null){
			Map<String,Object> map = new HashMap<String,Object>();
			String parentCode = district.getParent() != null ? district.getParent().getCode() : null;
			if(parentCode == null){
				parentCode = "null";
			}
			map.put("code", district.getCode());
			map.put("name", district.getName());
			map.put("parentCode", parentCode);
			map.put("displayOrder", district.getDisplayOrder());
			map.put("status", district.getStatus());
			map.put("type", district.getType());
			map.put("remark", district.getRemark());
			return map;
		}
		return null;
	}
}
