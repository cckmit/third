package cn.redflagsoft.base.listener;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.event.v2.EventListener;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.BizStatistics;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.commons.ObjectInvalid;
import cn.redflagsoft.base.dao.BizStatisticsDao;
import cn.redflagsoft.base.event2.ObjectAdminEvent;
import cn.redflagsoft.base.service.BizStatisticsService;
import cn.redflagsoft.base.util.BigDecimalUtils;


public class ObjectInvalidForBizStatisticsEventListener implements EventListener<ObjectAdminEvent>{
	
	private static final Log log = LogFactory.getLog(ObjectInvalidForBizStatisticsEventListener.class);
	
	private BizStatisticsService bizStatisticsService;

	private BizStatisticsDao bizStatisticsDao;
	
	public BizStatisticsDao getBizStatisticsDao() {
		return bizStatisticsDao;
	}

	public void setBizStatisticsDao(BizStatisticsDao bizStatisticsDao) {
		this.bizStatisticsDao = bizStatisticsDao;
	}

	public BizStatisticsService getBizStatisticsService() {
		return bizStatisticsService;
	}

	public void setBizStatisticsService(BizStatisticsService bizStatisticsService) {
		this.bizStatisticsService = bizStatisticsService;
	}

	public void handle(ObjectAdminEvent event) {
		if(event.getType() == ObjectAdminEvent.Type.INVALID){
			RFSObject object = event.getSource();
			if(object instanceof RFSObject){
				handle(object,(ObjectInvalid) event.getObjectAdmin());
			}
		}
	}
	
	
	private void handle(RFSObject rfsObject,ObjectInvalid objectInvalid){
		handle(rfsObject);
	}
	
	public void handle(RFSObject rfsObject){
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		SimpleExpression eq = Restrictions.eq("refObjectId", rfsObject.getId());
		SimpleExpression eq2 = Restrictions.eq("refObjectType", rfsObject.getObjectType());
		filter.setCriterion(Restrictions.logic(eq).and(eq2));
		List<BizStatistics> list = bizStatisticsService.findBizStatistics(filter);
		if(list != null && !list.isEmpty()){
			for (BizStatistics b : list) {
				String format = String.format("@@@@@@@@@@@@@@@@@@@@@@@@=================>>>>>>>%s ,year:%s,month:%s,week:%s,day:%s", b.getDefinition(), b.getTheYear(), b.getTheMonth(), b.getTheWeek(),b.getTheDay());
				log.info(format);
				BizStatistics bz = bizStatisticsService.getBizStatistics(b.getDefinition(), 0L,0,
						b.getTheYear(), b.getTheMonth(), b.getTheWeek(),b.getTheDay());
				if(bz != null){
					BigDecimal dataSumTmep = bz.getDataSum();
					bz.setDataSum(BigDecimalUtils.subtract(bz.getDataSum(), b.getDataSum()));
					bizStatisticsDao.update(bz);
					log.info("统计数据修改成功，数据ID ： " + bz.getId() + ",修改的前值：" +  dataSumTmep + ",修改后的值：" + bz.getDataSum());
				}
			}
			
			// 修改完总数以后，删除详细的数据。
			for (BizStatistics bs : list) {
				bizStatisticsDao.delete(bs);
				log.info("数据删除成功，数据ID ： " + bs.getId());
			}
		}
	}
}
