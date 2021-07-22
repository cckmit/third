package cn.redflagsoft.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.redflagsoft.base.bean.DatumAttachment;
import cn.redflagsoft.base.bean.DatumCategory;
import cn.redflagsoft.base.bean.DatumCategoryWithData;
import cn.redflagsoft.base.bean.MatterDatum;
import cn.redflagsoft.base.dao.MatterDatumDao;
import cn.redflagsoft.base.service.DatumAttachmentService;
import cn.redflagsoft.base.service.DatumCategoryWithDataService;

public class DatumCategoryWithDataServiceImpl implements DatumCategoryWithDataService {
	private MatterDatumDao matterDatumDao;
	private DatumAttachmentService datumAttachmentService;

	public void setDatumAttachmentService(DatumAttachmentService datumAttachmentService) {
		this.datumAttachmentService = datumAttachmentService;
	}

	public void setMatterDatumDao(MatterDatumDao matterDatumDao) {
		this.matterDatumDao = matterDatumDao;
	}

	public List<DatumCategoryWithData> findDatumCategoryWithData(
			int taskType, int workType, int processType, Long matterId, Long objectId) {
		List<DatumCategory> datumCategories = matterDatumDao.findDatumCategory(taskType, workType, processType, matterId, MatterDatum.BIZ_ACTION_DEFAULT);
		List<DatumAttachment> datumAttachments = datumAttachmentService.findDatumAttachment(objectId);
		List<DatumCategoryWithData> result = new ArrayList<DatumCategoryWithData>();
		for (DatumCategory dc : datumCategories) {
			DatumAttachment da = lookupDatumAttachment(datumAttachments, dc.getId());
			DatumCategoryWithData dcwd = new DatumCategoryWithData(dc, da);
			result.add(dcwd);
		}
		return result;
	}
	
	private DatumAttachment lookupDatumAttachment(List<DatumAttachment> datumAttachments, Long catID){
		for(DatumAttachment da: datumAttachments){
			if(da.getCategoryID() == catID){
				return da;
			}
		}
		return null;
	}	
}
