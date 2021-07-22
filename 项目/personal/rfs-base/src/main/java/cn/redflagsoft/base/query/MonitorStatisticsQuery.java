package cn.redflagsoft.base.query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.lifecycle.Application;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.hibernate3.HibernateDaoSupport;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.RiskRule;
import cn.redflagsoft.base.bean.Task;
import cn.redflagsoft.base.bean.TaskDef;
import cn.redflagsoft.base.dao.RiskDao;
import cn.redflagsoft.base.dao.RiskRuleDao;
import cn.redflagsoft.base.dao.TaskDao;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.TaskDefProvider;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MonitorStatisticsQuery extends HibernateDaoSupport{
	private static final Log log = LogFactory.getLog(MonitorStatisticsQuery.class);
	private TaskDao taskDao;
	private RiskDao riskDao;
	private RiskRuleDao riskRuleDao;
	private TaskDefProvider taskDefProvider;// = Application.getContext().get("taskDefProvider", TaskDefProvider.class);
	
	public RiskRuleDao getRiskRuleDao() {
		return riskRuleDao;
	}

	public void setRiskRuleDao(RiskRuleDao riskRuleDao) {
		this.riskRuleDao = riskRuleDao;
	}

	public RiskDao getRiskDao() {
		return riskDao;
	}

	public void setRiskDao(RiskDao riskDao) {
		this.riskDao = riskDao;
	}

	public TaskDao getTaskDao() {
		return taskDao;
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public List<MonitorItem> findMonitorStatisticsGroup() {
		return findMonitorStatisticsGroupListInternal(null, null);
	}
	
	/**
	 * @return the taskDefProvider
	 */
	public TaskDefProvider getTaskDefProvider() {
		if(taskDefProvider == null){
			taskDefProvider = Application.getContext().get("taskDefProvider", TaskDefProvider.class);
		}
		return taskDefProvider;
	}

	/**
	 * @param taskDefProvider the taskDefProvider to set
	 */
	public void setTaskDefProvider(TaskDefProvider taskDefProvider) {
		this.taskDefProvider = taskDefProvider;
	}

	/***
	 * �������ͳ�ƣ�ȫ���� 2012-05-08����
	 * @return
	 */
	public List<MonitorItem> findMonitorStatisticsGroupNew(){
		return findMonitorStatisticsGroupListInternal_New2(null,null);
	}
	
	/***
	 * ҵ������ͳ�� - ͳ��ȫ��
	 * @return
	 */
	public List<MonitorItem> findBusinessStatisticsGroupNew(){
		return findBusinessStatisticsGroupListInternal_New2(null,null);
	}
	
	/****
	 * 	ҵ������ͳ�� - ͳ�Ʊ���λ
	 * @return
	 */
	public List<MonitorItem> findBusinessStatisticsGroupNewForDutyEntity(){
		// ��õ�ǰ��¼ϵͳ�û���Ϣ
		Clerk clerk = UserClerkHolder.getClerk();
		return findBusinessStatisticsGroupListInternal_New2(clerk.getEntityID(),null);
	}
	
	/****
	 * ҵ������ͳ�� - ͳ�Ʊ���
	 * @return
	 */
	public List<MonitorItem> findBusinessStatisticsGroupNewForClerk(){
		// ��õ�ǰ��¼ϵͳ�û���Ϣ
		Clerk clerk = UserClerkHolder.getClerk();
		return findBusinessStatisticsGroupListInternal_New2(null,clerk.getId());
	}
	
	public List<MonitorItem> findMonitorStatisticsGroupNewForDutyEntity() {
		// ��õ�ǰ��¼ϵͳ�û���Ϣ
		Clerk clerk = UserClerkHolder.getClerk();
		return findMonitorStatisticsGroupListInternal_New2(clerk.getEntityID(),null);
	}

	public List<MonitorItem> findMonitorStatisticsGroupNewForClerk() {
		// ��õ�ǰ��¼ϵͳ�û���Ϣ
		Clerk clerk = UserClerkHolder.getClerk();
		return findMonitorStatisticsGroupListInternal_New2(null,clerk.getId());
	}
	
	public List<Map<String,Object>> findBusinessStatisticsGroup() {
//		Map<String, Code> allCodeMap = CodeMapUtils.getAllCodeMap(Task.class,"STATUS");
//		// PSS2����ʱ û�� "Ԥ��","�ϲ�"��2��״̬,���� allCodeMap ��ȥ�� '3' ,'16' ��2��key
//		allCodeMap.remove("3");
//		allCodeMap.remove("16");
		long okSum = 0;
		long workSum = 0;
		long hangSum = 0;
		long terminateSum = 0;
		long cancelSum = 0;
		long stopSum = 0;
		long avoidSum = 0;
		long withdrawSum = 0;
		long rejectSum = 0;
		long transferSum = 0;
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		String sql = "select t.name as name,t.tp as tp,t.status as status,count(t.sn) as cnt from task t" +
				" where t.name is not null and t.tp <> 8010 group by t.name,t.tp,t.status order by t.tp";
		/*Session session = null;
		List list = null;
		List list2 = null;
		try {
			session= this.getHibernateTemplate().getSessionFactory()
					.openSession();
			SQLQuery query = session.createSQLQuery(sql);
			list = query.list();
			String sql2 = "select distinct t.tp as tp from task t where t.name is not null and t.tp <> 8010";
			SQLQuery query2 = session.createSQLQuery(sql2);
			 list2 = query2.list();
		} catch (Exception e) {
			log.debug("" + e.getMessage());
		}finally{
			session.close();
		}*/
		
		List<?> list = getQuerySupport().findBySQL(sql, new ResultFilter());//getHibernateTemplate().find(sql);
		String sql2 = "select distinct t.tp as tp from task t where t.name is not null and t.tp <> 8010";
		List<?> list2 = getQuerySupport().findBySQL(sql2, new ResultFilter());//getHibernateTemplate().find(sql2);

		
		Map<String,Object> taskTypeList = new HashMap<String, Object>();
		if(list2 != null && list2.size() > 0){
			for (Object object : list2) {
				BigDecimal o = (BigDecimal) object;
				taskTypeList.put(o.toString(),o);
			}
		}
		if(list != null && list.size() > 0){
			for (String ky : taskTypeList.keySet()) {
				Map<String,Object> resultMap = new HashMap<String, Object>();
				int itemHJSum = 0;  // �ϼ� 
				resultMap.put("taskType", ky);
				resultMap.put("businessOK", 0);			// ����
				resultMap.put("businessWORK", 0);		// �ڰ�
				resultMap.put("businessHANG", 0);		// ��ͣ
				resultMap.put("businessTERMINATE", 0);	// ����
				resultMap.put("businessCANCEL", 0);		// ȡ��
				resultMap.put("businessSTOP", 0);		// ��ֹ
				resultMap.put("businessAVOID", 0);		// ���
				resultMap.put("businessWITHDRAW", 0);	// ����
				resultMap.put("businessREJECT", 0);		// ����
				resultMap.put("businessTRANSFER", 0);	// ת��
				resultMap.put("itemHJSum",0);
				for (Object object : list) {
					Object[] tp = (Object[]) object;
					String taskTp = tp[1].toString();
					if(ky.equals(taskTp)){
						resultMap.put("businessName", tp[0].toString());
						byte status = Byte.parseByte(tp[2].toString());
						long statusCnt = Long.parseLong(tp[3].toString());
						switch (status) {
						case Task.STATUS_����:
							resultMap.put("businessOK", statusCnt);
							okSum+=statusCnt;
							break;
						case Task.STATUS_�ڰ�:
							resultMap.put("businessWORK", statusCnt);
							workSum+=statusCnt;
							break;
						case Task.STATUS_��ͣ:
							resultMap.put("businessHANG", statusCnt);
							hangSum+=statusCnt;
							break;
						case Task.STATUS_����:
							resultMap.put("businessTERMINATE", statusCnt);
							terminateSum+=statusCnt;
							break;
						case Task.STATUS_ȡ��:
							resultMap.put("businessCANCEL", statusCnt);
							cancelSum+=statusCnt;
							break;
						case Task.STATUS_��ֹ:
							resultMap.put("businessSTOP", statusCnt);
							stopSum+=statusCnt;
							break;
						case Task.STATUS_���:
							resultMap.put("businessAVOID", statusCnt);
							avoidSum+=statusCnt;
							break;
						case Task.STATUS_����:
							resultMap.put("businessWITHDRAW", statusCnt);
							withdrawSum+=statusCnt;
							break;
						case Task.STATUS_����:
							resultMap.put("businessREJECT", statusCnt);
							rejectSum+=statusCnt;
							break;
						case Task.STATUS_ת��:
							resultMap.put("businessTRANSFER", statusCnt);
							transferSum+=statusCnt;
							break;
						}
						itemHJSum+=statusCnt;
						continue;
					}
				}
				resultMap.put("itemHJSum",itemHJSum);
				resultList.add(resultMap);
			}
		}
		Map<String,Object> sumMap = new HashMap<String, Object>();
		sumMap.put("taskType", "-123");
		sumMap.put("businessName", "�ܼ�");
		sumMap.put("businessOK", okSum);		
		sumMap.put("businessWORK",workSum);		// �ڰ�
		sumMap.put("businessHANG",hangSum);		// ��ͣ
		sumMap.put("businessTERMINATE",terminateSum);	// ����
		sumMap.put("businessCANCEL",cancelSum);	// ȡ��
		sumMap.put("businessSTOP",stopSum);		// ��ֹ
		sumMap.put("businessAVOID", avoidSum);		// ���
		sumMap.put("businessWITHDRAW",withdrawSum);	// ����
		sumMap.put("businessREJECT",rejectSum);	// ����
		sumMap.put("businessTRANSFER",transferSum);	// ת��
		long sum = (okSum + workSum + hangSum + terminateSum + cancelSum + stopSum + avoidSum + withdrawSum + rejectSum + transferSum);
		sumMap.put("itemHJSum",sum);
		resultList.add(sumMap);
		return resultList;
	}
	
	/***
	 * �������ͳ�Ʒ���(����λ)
	 * @return
	 */
	public List<MonitorItem> findMonitorStatisticsGroupList(final Long dutyerOrgId, final Long dutyerId) {
		return findMonitorStatisticsGroupListInternal(dutyerOrgId, dutyerId);
	}
	
	@SuppressWarnings("unchecked")
	List<MonitorItem> findMonitorStatisticsGroupListInternal(final Long dutyerOrgId, final Long dutyerId) {
		String qs = "select r.ruleID, r.ruleName, r.grade, count(*) from Risk r where r.status=1 and r.ruleType="+RiskRule.RULE_TYPE_���;
		List<Object> values = Lists.newArrayList();
		if(dutyerOrgId != null){
			qs += " and r.dutyerOrgId=?";
			values.add(dutyerOrgId);
		}
		if(dutyerId != null){
			qs += " and r.dutyerID=?";
			values.add(dutyerId);
		}
		qs += " group by r.ruleID, r.ruleName, r.grade order by r.ruleID";
		
		List<Object[]> list;
		if(values.isEmpty()){
			list = getHibernateTemplate().find(qs);
		}else{
			list = getHibernateTemplate().find(qs, values.toArray());
		}
		
		Map<Long,MonitorItem> map = Maps.newLinkedHashMap();// new LinkedHashMap<Long,MonitorItem>();
		
		MonitorItem summary = new MonitorItem(-123L, "�ܼ�");
		for(Object[] objs:list){
			Long ruleID = (Long) objs[0];
			String ruleName = (String) objs[1];
			int grade = ((Number)objs[2]).byteValue();
			int num = ((Number)objs[3]).intValue();
			
			MonitorItem item = map.get(ruleID);
			if(item == null){
				item = new MonitorItem(ruleID, ruleName);
				item.setDutyerId(dutyerId);
				item.setDutyerOrgId(dutyerOrgId);
				map.put(ruleID, item);
			}
			
			switch (grade) {
			case Risk.GRADE_NORMAL:
				item.monitorZC += num;
				summary.monitorZC += num;
				break;
			case Risk.GRADE_BLUE:
				item.monitorYJ += num;
				summary.monitorYJ += num;
				break;
			case Risk.GRADE_YELLOW:
				item.monitorHP += num;
				summary.monitorHP += num;
				break;
			case Risk.GRADE_RED:
				item.monitorREDP += num;
				summary.monitorREDP += num;
				break;
			}
		}

		ArrayList<MonitorItem> result = new ArrayList<MonitorItem>(map.values());
		
		summary.setDutyerId(dutyerId);
		summary.setDutyerOrgId(dutyerOrgId);
		result.add(summary);
		
		return result;
	}
	
	private List<MonitorItem> findMonitorStatisticsGroupListInternal_New2(final Long dutyerOrgId, final Long dutyerId){
		//������Ԥ�������ơ�����
		//select r.ruleID, r.grade, count(*) from Risk r where r.ruleType=��� and r.status=δ����
		
		//��ѯ������
		//select r.ruleID, count(*) from Risk r where r.ruleType=��� and r.status=����
		
		//�ϼ� = ������Ԥ�������ơ����� + ����
		
		//��ѯ����ı�ע
		//��������
		//select r.ruleID, count(*) from Risk r where r.ruleType=��� and r.beginTime>=�������
		//��������
		//select r.ruleID, count(*) from Risk r where r.ruleType=��� and r.beginTiem>=����1�����
		//��������
		//select r.ruleID, count(*) from Risk r where r.ruleType=��� and r.beginTime>=����1��1�����
		
		
		//Ȼ����RiskRuleΪ��׼��װ����
		
		
		// ������Ԥ�������ơ����ơ�����δ�����ģ�״̬0,1
		String qs = "select r.ruleID, r.grade, count(*) from Risk r where r.ruleType=? and r.status<?";
		// ���� ״̬2,3,��grade�޹�
		String endqs = "select r.ruleID, count(*) from Risk r where r.ruleType=? and r.status>=?";
		
		//��������
		String brqs = "select r.ruleID, count(*) from Risk r where r.ruleType=? and r.status<? and r.beginTime>=?";
		//��������
		String byqs = "select r.ruleID, count(*) from Risk r where r.ruleType=? and r.status<? and r.beginTime>=?";
		//��������
		String bnqs = "select r.ruleID, count(*) from Risk r where r.ruleType=? and r.status<? and r.beginTime>=?";
		
		
		List<Object> qsValues = Lists.newArrayList();
		qsValues.add(RiskRule.RULE_TYPE_���);
		qsValues.add(Risk.STATUS_FINISH);
		
		List<Object> endqsValues = Lists.newArrayList();
		endqsValues.add(RiskRule.RULE_TYPE_���);
		endqsValues.add(Risk.STATUS_FINISH);
		
		//��ѯ����ı�ע
		Calendar br = Calendar.getInstance();
		br.set(Calendar.HOUR, 0);
		br.set(Calendar.SECOND, 0);
		br.set(Calendar.MINUTE, 0);
		br.set(Calendar.MILLISECOND, 0);
		
		Calendar by = Calendar.getInstance();
		by.set(Calendar.DAY_OF_MONTH,1);
		by.set(Calendar.HOUR, 0);
		by.set(Calendar.SECOND, 0);
		by.set(Calendar.MINUTE, 0);
		by.set(Calendar.MILLISECOND, 0);
		
		Calendar bn = Calendar.getInstance();
		bn.set(Calendar.MONTH,0);
		bn.set(Calendar.DAY_OF_MONTH,1);
		bn.set(Calendar.HOUR, 0);
		bn.set(Calendar.SECOND, 0);
		bn.set(Calendar.MINUTE, 0);
		bn.set(Calendar.MILLISECOND, 0);
		
		List<Object> brqsValues = Lists.newArrayList();
		brqsValues.add(RiskRule.RULE_TYPE_���);
		brqsValues.add(Risk.STATUS_FINISH);
		brqsValues.add(br.getTime());
		
		List<Object> byqsValues = Lists.newArrayList();
		byqsValues.add(RiskRule.RULE_TYPE_���);
		byqsValues.add(Risk.STATUS_FINISH);
		byqsValues.add(by.getTime());
		
		List<Object> bnqsValues = Lists.newArrayList();
		bnqsValues.add(RiskRule.RULE_TYPE_���);
		bnqsValues.add(Risk.STATUS_FINISH);
		bnqsValues.add(bn.getTime());
		

		if (dutyerOrgId != null) {
			qs += " and r.dutyerOrgId=?";
			endqs += " and r.dutyerOrgId=?";
			brqs += " and r.dutyerOrgId=?";
			byqs += " and r.dutyerOrgId=?";
			bnqs += " and r.dutyerOrgId=?";
			qsValues.add(dutyerOrgId);
			endqsValues.add(dutyerOrgId);
			brqsValues.add(dutyerOrgId);
			byqsValues.add(dutyerOrgId);
			bnqsValues.add(dutyerOrgId);
		}
		
		if (dutyerId != null) {
			qs += " and r.dutyerID=?";
			endqs += " and r.dutyerID=?";
			brqs += " and r.dutyerID=?";
			byqs += " and r.dutyerID=?";
			bnqs += " and r.dutyerID=?";
			qsValues.add(dutyerId);
			endqsValues.add(dutyerId);
			brqsValues.add(dutyerId);
			byqsValues.add(dutyerId);
			bnqsValues.add(dutyerId);
		}
		qs += " group by r.ruleID, r.grade order by r.ruleID";
		endqs += " group by r.ruleID order by r.ruleID";
		brqs += " group by r.ruleID order by r.ruleID";
		byqs += " group by r.ruleID order by r.ruleID";
		bnqs += " group by r.ruleID order by r.ruleID";

		@SuppressWarnings("unchecked")
		List<Object[]> riskList = getHibernateTemplate().find(qs, qsValues.toArray());
		@SuppressWarnings("unchecked")
		List<Object[]> endRiskList = getHibernateTemplate().find(endqs, endqsValues.toArray());
		@SuppressWarnings("unchecked")
		List<Object[]> brRiskList = getHibernateTemplate().find(brqs, brqsValues.toArray());
		@SuppressWarnings("unchecked")
		List<Object[]> byRiskList = getHibernateTemplate().find(byqs, byqsValues.toArray());
		@SuppressWarnings("unchecked")
		List<Object[]> bnRiskList = getHibernateTemplate().find(bnqs, bnqsValues.toArray());
		
//		final String tempSql = "select r.ruleID, count(*) from risk r where r.rule_type=? and r.status <>? and r.beginTime>? group by r.ruleID, r.grade,r.status order by r.ruleID";
//		
//		bnRiskList = (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
//			public Object doInHibernate(Session session) throws HibernateException,
//					SQLException {
//				return session.createSQLQuery(tempSql).setInteger(0, 1).setByte(1,(byte)2).setTimestamp(2,bn.getTime()).list();
//			}
//		});
//		
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		filter.setCriterion(Restrictions.logic(Restrictions.eq("ruleType", RiskRule.RULE_TYPE_���))
				.and(Restrictions.eq("status", RiskRule.STATUS_ON_USE)));
		filter.setOrder(Order.asc("id"));
		List<RiskRule> riskRuleList = riskRuleDao.find(filter);

		//�ܼ�
		ArrayList<MonitorItem> result = new ArrayList<MonitorItem>();
		MonitorItem summary = new MonitorItem(-123L, "�ܼ�");
		
		for (RiskRule riskRule : riskRuleList) {
			MonitorItem item = new MonitorItem(riskRule.getId(), riskRule.getName());
			for (Object[] objs : riskList) {
				Long ruleID = (Long) objs[0];
				int grade = ((Number) objs[1]).byteValue();
				int num = ((Number) objs[2]).intValue();
				if (riskRule.getId().equals(ruleID)){
					switch (grade) {
					case Risk.GRADE_NORMAL:
							item.setMonitorZC(item.getMonitorZC() + num);
							summary.setMonitorZC(summary.getMonitorZC() + num);
						break;
					case Risk.GRADE_BLUE:
							item.setMonitorYJ(item.getMonitorYJ()+num);
							summary.setMonitorYJ(summary.getMonitorYJ()+num);
						break;
					case Risk.GRADE_YELLOW:
							item.setMonitorHP(item.getMonitorHP() + num);
							summary.setMonitorHP(summary.getMonitorHP() + num);
						break;
					case Risk.GRADE_RED:
							item.setMonitorREDP(item.getMonitorREDP() + num);
							summary.setMonitorREDP(summary.getMonitorREDP() + num);
						break;
					}
				}
			}

			for (Object[] objs : endRiskList) {
				Long ruleID = (Long) objs[0];
				int num = ((Number) objs[1]).intValue();
				if (riskRule.getId().equals(ruleID)) {
					item.setMonitorEnd(item.getMonitorEnd()+num);
					summary.setMonitorEnd(summary.getMonitorEnd()+num);
				}
			}
			
			for (Object[] objs : brRiskList) {
				Long ruleID = (Long) objs[0];
				int num = ((Number) objs[1]).intValue();
				if (riskRule.getId().equals(ruleID)) {
					item.setDayNum(item.getDayNum()+num);
					summary.setDayNum(summary.getDayNum()+num);
				}
			}
			
			for (Object[] objs : byRiskList) {
				Long ruleID = (Long) objs[0];
				int num = ((Number) objs[1]).intValue();
				if (riskRule.getId().equals(ruleID)) {
					item.setMonthNum(item.getMonthNum()+ num);
					summary.setMonthNum(summary.getMonthNum()+num);
				}
			}
			
			for (Object[] objs : bnRiskList) {	
				Long ruleID = Long.parseLong(objs[0].toString());
				int num = Integer.parseInt(objs[1].toString()); //((Number) objs[1]).intValue();
				if (riskRule.getId().equals(ruleID)) {
					item.setYearNum(item.getYearNum()+ num);
					summary.setYearNum(summary.getYearNum()+ num);
				}
			}
			item.setDutyerId(dutyerId);
			item.setDutyerOrgId(dutyerOrgId);
//			item.setMonitorRemark("��������" + item.getDayNum() + " ��������� " + 
//					item.getMonthNum() + " ��������� " + item.getYearNum() + " �");
			result.add(item);
		}
		summary.setDutyerId(dutyerId);
		summary.setDutyerOrgId(dutyerOrgId);
//		summary.setMonitorRemark("��������" + summary.getDayNum() + " ��������� " + 
//				summary.getMonthNum() + " ��������� " + summary.getYearNum() + " �");
		result.add(summary);
		return result;
	}
	
	public List<MonitorItem> findBusinessStatisticsGroupListInternal_New2(final Long dutyerOrgId, final Long dutyerId){
		//��ѯ ��켶��ʱ������Ԥ�������ơ����Ƶ� task
		String qs = "select t.type, t.timeLimitRiskGrade, count(*) from Task t where (t.status=? or t.status=?) and t.type <> 8010 and (t.visibility = 0 or t.visibility = 100)";
		//��������
		String brqs = "select t.type, count(*) from Task t where (t.status=? or t.status=?) and t.type <> 8010 and (t.visibility = 0 or t.visibility = 100) and t.beginTime>=?";
		//����
		String byqs = brqs;
		//��������
		String bnqs = brqs;
		
		List<Object> qsValues = Lists.newArrayList();
		qsValues.add(Task.STATUS_�ڰ�);
		qsValues.add(Task.STATUS_��ͣ);
		
		
		//��ѯ����ı�ע
		Calendar br = Calendar.getInstance();
		br.set(Calendar.HOUR, 0);
		br.set(Calendar.SECOND, 0);
		br.set(Calendar.MINUTE, 0);
		br.set(Calendar.MILLISECOND, 0);
		
		Calendar by = Calendar.getInstance();
		by.set(Calendar.DAY_OF_MONTH,1);
		by.set(Calendar.HOUR, 0);
		by.set(Calendar.SECOND, 0);
		by.set(Calendar.MINUTE, 0);
		by.set(Calendar.MILLISECOND, 0);
		
		Calendar bn = Calendar.getInstance();
		bn.set(Calendar.MONTH,0);
		bn.set(Calendar.DAY_OF_MONTH,1);
		bn.set(Calendar.HOUR, 0);
		bn.set(Calendar.SECOND, 0);
		bn.set(Calendar.MINUTE, 0);
		bn.set(Calendar.MILLISECOND, 0);
		
		List<Object> brqsValues = Lists.newArrayList();
		brqsValues.add(Task.STATUS_�ڰ�);
		brqsValues.add(Task.STATUS_��ͣ);
		brqsValues.add(br.getTime());
		
		List<Object> byqsValues = Lists.newArrayList();
		byqsValues.add(Task.STATUS_�ڰ�);
		byqsValues.add(Task.STATUS_��ͣ);
		byqsValues.add(by.getTime());
		
		List<Object> bnqsValues = Lists.newArrayList();
		bnqsValues.add(Task.STATUS_�ڰ�);
		bnqsValues.add(Task.STATUS_��ͣ);
		bnqsValues.add(bn.getTime());
		
		
		if (dutyerOrgId != null) {
			qs += " and t.entityID=?";
			brqs += " and t.entityID=?";
			byqs += " and t.entityID=?";
			bnqs += " and t.entityID=?";
			qsValues.add(dutyerOrgId);
			brqsValues.add(dutyerOrgId);
			byqsValues.add(dutyerOrgId);
			bnqsValues.add(dutyerOrgId);
		}
		
		if (dutyerId != null) {
			qs += " and t.clerkID=?";
			brqs += " and t.clerkID=?";
			byqs += " and t.clerkID=?";
			bnqs += " and t.entityID=?";
			qsValues.add(dutyerId);
			brqsValues.add(dutyerId);
			byqsValues.add(dutyerId);
			bnqsValues.add(dutyerId);
		}
		qs += " group by t.type, t.timeLimitRiskGrade order by t.type";
		brqs += " group by t.type order by t.type";
		byqs += " group by t.type order by t.type";
		bnqs += " group by t.type order by t.type";
		
		@SuppressWarnings("unchecked")
		List<Object[]> taskList = getHibernateTemplate().find(qs, qsValues.toArray());
		@SuppressWarnings("unchecked")
		List<Object[]> brTaskList = getHibernateTemplate().find(brqs, brqsValues.toArray());
		@SuppressWarnings("unchecked")
		List<Object[]> byTaskList = getHibernateTemplate().find(byqs, byqsValues.toArray());
		@SuppressWarnings("unchecked")
		List<Object[]> bnTaskList = getHibernateTemplate().find(bnqs, bnqsValues.toArray());
		
		Map<Integer, MonitorItem> map = Maps.newLinkedHashMap();
		MonitorItem summary = new MonitorItem(null, -123, "�ܼ�");
		
		//�����Ƶ�
		for(Object[] objs: taskList){
			int taskType = ((Number)objs[0]).intValue();
			int grade = ((Number)objs[1]).byteValue();
			int num = ((Number)objs[2]).intValue(); 
			
			MonitorItem item = lookupItem(map, taskType);
			switch (grade) {
				case Risk.GRADE_NORMAL:
					item.monitorZC += num;
					summary.monitorZC += num;
					break;
				case Risk.GRADE_BLUE:
					item.monitorYJ += num;
					summary.monitorYJ += num;
					break;
				case Risk.GRADE_YELLOW:
					item.monitorHP += num;
					summary.monitorHP += num;
					break;
				case Risk.GRADE_RED:
					item.monitorREDP += num;
					summary.monitorREDP += num;
					break;
			}
		}
		
		for(Object[] objs: brTaskList){
			int taskType = ((Number)objs[0]).intValue();
			int num = ((Number)objs[1]).intValue(); 
			MonitorItem item = lookupItem(map, taskType);
			item.dayNum += num;
			summary.dayNum += num;
		}
		for(Object[] objs: byTaskList){
			int taskType = ((Number)objs[0]).intValue();
			int num = ((Number)objs[1]).intValue(); 
			MonitorItem item = lookupItem(map, taskType);
			item.monthNum += num;
			summary.monthNum += num;
		}
		for(Object[] objs: bnTaskList){
			int taskType = ((Number)objs[0]).intValue();
			int num = ((Number)objs[1]).intValue(); 
			MonitorItem item = lookupItem(map, taskType);
			item.yearNum += num;
			summary.yearNum += num;
		}
		
		map.put(-123, summary);
		
		List<MonitorItem> items = Lists.newArrayList();
		for(MonitorItem item: map.values()){
			if(item.getMonitorItemSum() == 0){
				log.debug("��ǰtask����ҵ��ʵ������Ϊ0��������ͳ�ƽ����" + item.taskType);
				continue;
			}
			items.add(item);
		}
		return items;
	}
	
	private MonitorItem lookupItem(Map<Integer, MonitorItem> map, int taskType){
		MonitorItem item = map.get(taskType);
		if(item == null){
			TaskDef taskDef = getTaskDefProvider().getTaskDef(taskType);
			item = new MonitorItem(null, taskType, taskDef.getName());
			map.put(taskType, item);
		}
		return item;
	}
	
	
	
	public static class MonitorItem{
		private Long ruleID;
		private String monitorName;
		private Long dutyerOrgId;
		private Long dutyerId;
		
		private int monitorZC = 0;//����
		private int monitorYJ = 0;//Ԥ��
		private int monitorHP = 0;//����
		private int monitorREDP = 0;//����
		private int monitorEnd = 0; //����
		//private int monitorItemSum = 0; //����
		//private String monitorRemark;
		
		private int taskType;
		
		private int yearNum = 0;
		private int monthNum = 0;
		private int dayNum = 0;
		
		/**
		 * @param ruleID
		 * @param monitorName
		 */
		public MonitorItem(Long ruleID, String monitorName) {
			super();
			this.ruleID = ruleID;
			this.monitorName = monitorName;
		}
		
		public MonitorItem(Long ruleID, int taskType, String monitorName) {
			super();
			this.taskType = taskType;
			this.monitorName = monitorName;
		}
		
		/**
		 * 
		 */
		public MonitorItem() {
			super();
		}
		
		/**
		 * @return the ruleID
		 */
		public Long getRuleID() {
			return ruleID;
		}
		/**
		 * @param ruleID the ruleID to set
		 */
		public void setRuleID(Long ruleID) {
			this.ruleID = ruleID;
		}
		/**
		 * @return the monitorName
		 */
		public String getMonitorName() {
			return monitorName;
		}
		/**
		 * @param monitorName the monitorName to set
		 */
		public void setMonitorName(String monitorName) {
			this.monitorName = monitorName;
		}
		/**
		 * @return the dutyerOrgId
		 */
		public Long getDutyerOrgId() {
			return dutyerOrgId;
		}
		/**
		 * @param dutyerOrgId the dutyerOrgId to set
		 */
		public void setDutyerOrgId(Long dutyerOrgId) {
			this.dutyerOrgId = dutyerOrgId;
		}
		/**
		 * @return the dutyerId
		 */
		public Long getDutyerId() {
			return dutyerId;
		}
		/**
		 * @param dutyerId the dutyerId to set
		 */
		public void setDutyerId(Long dutyerId) {
			this.dutyerId = dutyerId;
		}
		/**
		 * @return the monitorZC
		 */
		public int getMonitorZC() {
			return monitorZC;
		}
		/**
		 * @param monitorZC the monitorZC to set
		 */
		public void setMonitorZC(int monitorZC) {
			this.monitorZC = monitorZC;
		}
		/**
		 * @return the monitorYJ
		 */
		public int getMonitorYJ() {
			return monitorYJ;
		}
		/**
		 * @param monitorYJ the monitorYJ to set
		 */
		public void setMonitorYJ(int monitorYJ) {
			this.monitorYJ = monitorYJ;
		}
		/**
		 * @return the monitorHP
		 */
		public int getMonitorHP() {
			return monitorHP;
		}
		/**
		 * @param monitorHP the monitorHP to set
		 */
		public void setMonitorHP(int monitorHP) {
			this.monitorHP = monitorHP;
		}
		/**
		 * @return the monitorREDP
		 */
		public int getMonitorREDP() {
			return monitorREDP;
		}
		/**
		 * @param monitorREDP the monitorREDP to set
		 */
		public void setMonitorREDP(int monitorREDP) {
			this.monitorREDP = monitorREDP;
		}
		
		public int getMonitorItemSum(){
			return this.monitorHP + this.monitorREDP + this.monitorYJ + this.monitorZC + this.monitorEnd;
		}
		public int getMonitorEnd() {
			return monitorEnd;
		}
		public void setMonitorEnd(int monitorEnd) {
			this.monitorEnd = monitorEnd;
		}

		public int getTaskType() {
			return taskType;
		}

		public void setTaskType(int taskType) {
			this.taskType = taskType;
		}

		public String getMonitorRemark() {
			//return monitorRemark;
			return  String.format("�������� %s ��������� %s ��������� %s �", dayNum, monthNum, yearNum);
		}

//		public void setMonitorRemark(String monitorRemark) {
//			//this.monitorRemark = monitorRemark;
//		}

		public int getYearNum() {
			return yearNum;
		}

		public void setYearNum(int yearNum) {
			this.yearNum = yearNum;
		}

		public int getMonthNum() {
			return monthNum;
		}

		public void setMonthNum(int monthNum) {
			this.monthNum = monthNum;
		}

		public int getDayNum() {
			return dayNum;
		}

		public void setDayNum(int dayNum) {
			this.dayNum = dayNum;
		}
		
//		public void setMonitorItemSum(int monitorItemSum){
//			//ignore
//		}
	}
}
