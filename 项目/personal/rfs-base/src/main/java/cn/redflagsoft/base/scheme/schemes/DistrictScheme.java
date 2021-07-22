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
//	 * 更新操作-1
//	 * @return
//	 */
//	public Object doUpdateOne() {
//		Assert.notNull(district, "行政区划不能为空");
//		
//		districtService.updateDistrict(district.getCode(), district.getName(), district.getParent(),
//				district.getRemark(), district.getDisplayOrder(), district.getStatus(), district.getType());
//		return "操作成功";
//	}
	
	/**
	 * 更新操作-2
	 * @return
	 */
	public Object doUpdateTwo() {
		Assert.notNull(getCode(), "行政区划编码不能为空");
		Assert.notNull(getName(), "行政区划名称不能为空");
		
		districtService.updateDistrict(getCode(), getName(), getDistrict(getParentCode()), getRemark(), getDisplayOrder(), getStatus(), getType());
		return "操作成功";
	}
	
	/**
	 * 新增操作-1
	 * @return
	 */
	public Object doSaveOne() {
		Assert.notNull(districtBean, "行政区划不能为空");
		Assert.notNull(districtBean.getCode(), "行政区划编码不能为空");
		Assert.notNull(districtBean.getName(), "行政区划名称不能为空");
		
		districtService.saveDistrict(districtBean.getCode(), districtBean.getName(), getDistrict(districtBean.getParentCode()),
				districtBean.getRemark(), districtBean.getDisplayOrder(), districtBean.getStatus(), districtBean.getType());
		return "操作成功";
	}
	
	/**
	 * 新增操作-2
	 * @return
	 */
	public Object doSaveTwo() {
		Assert.notNull(getCode(), "行政区划编码不能为空");
		Assert.notNull(getName(), "行政区划名称不能为空");
		
		districtService.saveDistrict(getCode(), getName(), getDistrict(getParentCode()),
				getRemark(), getDisplayOrder(), getStatus(), getType());
		return "操作成功";
	}
	
	/**
	 * 删除操作
	 * @return
	 */
	public Object doRemove() {
		Assert.notNull(getCode(), "行政区划编码不能为空");
		districtService.removeDistrict(getCode());
		return "操作成功";
	}
	
	/*
	 * 根据父ID，查找上次行政区划
	 */
	private District getDistrict(String code) {
		if("null".equalsIgnoreCase(code)){
			code = null;
		}
		return districtService.getDistrict(code);
	}
	
	public Object viewGetDistrict(){
		Assert.notNull(getCode(), "行政区划编码不能为空");
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
