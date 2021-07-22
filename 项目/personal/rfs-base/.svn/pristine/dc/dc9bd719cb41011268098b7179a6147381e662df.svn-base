package cn.redflagsoft.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.test.BaseTests;

public class LifeStageServiceTest extends BaseTests{
	
	protected LifeStageService lifeStageService;
	
	public void testFindSummaryLifeStage(){
		
		ResultFilter rf = ResultFilter.createEmptyResultFilter();
		
		rf.setCriterion((Criterion) Restrictions.eq("objectType", (short)1002));
		rf.setOrder(Order.desc("creationTime"));
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("compareStatusValue", "9");
		param.put("groupFieldNames", "managerId,managerName");
		rf.setRawParameters(param);
		
		List<Map<String,Object>> findSummaryLifeStage = lifeStageService.findSummaryLifeStage(rf);
		
		for (Map<String, Object> map : findSummaryLifeStage) {
			System.out.println(map.toString());
		}
	}
}
