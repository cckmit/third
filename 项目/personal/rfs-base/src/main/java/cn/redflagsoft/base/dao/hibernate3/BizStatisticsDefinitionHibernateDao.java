package cn.redflagsoft.base.dao.hibernate3;


import cn.redflagsoft.base.bean.BizStatisticsDefinition;
import cn.redflagsoft.base.dao.BizStatisticsDefinitionDao;

public class BizStatisticsDefinitionHibernateDao extends AbstractBaseHibernateDao<BizStatisticsDefinition, String> implements BizStatisticsDefinitionDao {
	
//	public List<BizStatistics> findWithDef(ResultFilter filter){
//		String qs = "select a, b from BizStatistics a, BizStatisticsDefinition b where a.dataId=b.dataId";
//		@SuppressWarnings("unchecked")
//		List<Object[]> list = getQuerySupport().find(qs, filter);
//		List<BizStatistics> result = new AsrrayList<BizStatistics>();
//		for(Object[] arr:list){
//			BizStatistics a = (BizStatistics) arr[0];
//			BizStatisticsDefinition b = (BizStatisticsDefinition) arr[1];
//			a.setBizStatisticsDefinition(b);
//			result.add(a);
//		}
//		return result;
//	}
}
