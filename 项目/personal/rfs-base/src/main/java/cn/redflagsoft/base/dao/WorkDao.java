package cn.redflagsoft.base.dao;

import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Work;

public interface WorkDao extends org.opoo.ndao.Dao<Work, Long>{
	void workInvalid(RFSObject rfsObject);
}
