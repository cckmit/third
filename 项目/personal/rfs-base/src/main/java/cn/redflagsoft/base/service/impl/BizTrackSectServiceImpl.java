/*
 * $Id: BizTrackSectServiceImpl.java 3996 2010-10-18 06:56:46Z lcj $
 * JobServiceImpl.java
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 */
package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.BizRouteSect;
import cn.redflagsoft.base.bean.BizTrackSect;
import cn.redflagsoft.base.dao.BizRouteSectDao;
import cn.redflagsoft.base.dao.BizTrackSectDao;
import cn.redflagsoft.base.service.BizTrackSectService;

/**
 * 
 * @author ymq
 *
 */
public class BizTrackSectServiceImpl implements BizTrackSectService {
	private BizRouteSectDao bizRouteSectDao;
	private BizTrackSectDao bizTrackSectDao;
	
	public void setBizRouteSectDao(BizRouteSectDao bizRouteSectDao) {
		this.bizRouteSectDao = bizRouteSectDao;
	}
	
	public void setBizTrackSectDao(BizTrackSectDao bizTrackSectDao) {
		this.bizTrackSectDao = bizTrackSectDao;
	}

	public boolean createBizTrackSect(Long bizTrackSN, Long routeId) {
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		rf.setCriterion(Restrictions.logic(
				Restrictions.eq("status", Byte.parseByte("1"))).and(
				Restrictions.eq("routeID", routeId)));
		List<BizRouteSect> bizRouteSectList = bizRouteSectDao.find(rf);
		for(BizRouteSect bizRouteSect : bizRouteSectList){
			BizTrackSect bizTrackSect = new BizTrackSect();
			bizTrackSect.setTrackSN(bizTrackSN);
			bizTrackSect.setSectNo(bizRouteSect.getSectNo());
			bizTrackSect.setSectName(bizRouteSect.getSectName());
			bizTrackSect.setStatus(bizRouteSect.getStatus());
			bizTrackSectDao.save(bizTrackSect);
		}
		return true;
	}
}
