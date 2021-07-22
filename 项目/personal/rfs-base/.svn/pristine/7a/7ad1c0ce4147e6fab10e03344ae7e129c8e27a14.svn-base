package cn.redflagsoft.base.query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opoo.apps.annotation.Queryable;
import org.opoo.ndao.criterion.Order;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.AuditLog;
import cn.redflagsoft.base.dao.AuditLogDao;

public class AuditMessageQuery {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss"); 
	
	private AuditLogDao auditLogDao;
	
	public AuditLogDao getAuditLogDao() {
		return auditLogDao;
	}

	public void setAuditLogDao(AuditLogDao auditLogDao) {
		this.auditLogDao = auditLogDao;
	}

	@Queryable(name="findAuditMessage")
	public List<Map<String, String>> findAuditMessage(ResultFilter filter){
		if(filter == null){
			filter = new ResultFilter();
		}
		
		SimpleExpression eq = Restrictions.eq("operation","login");
		SimpleExpression eq2 = Restrictions.eq("operation","logout");
		filter = filter.append(Restrictions.logic(eq).or(eq2));
		filter.setOrder(Order.desc("timestamp"));
		List<AuditLog> list = auditLogDao.find(filter);
		
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		for (AuditLog audit : list) {
			Map <String,String> temp = new HashMap<String, String>();
			temp.put("date", dateFormat.format(audit.getTimestamp()));
			temp.put("time", timeFormat.format(audit.getTimestamp()));
			String operation = audit.getOperation();
			
			if("login".equals(operation)){
				temp.put("operation","µÇÂ¼");	
			}else if ("logout".equals(operation)){
				temp.put("operation","ÍË³ö");
			}else{
				temp.put("operation", operation);
			}
			
			temp.put("description", audit.getDescription());
			result.add(temp);
		}

		return result;
	}
		
}
