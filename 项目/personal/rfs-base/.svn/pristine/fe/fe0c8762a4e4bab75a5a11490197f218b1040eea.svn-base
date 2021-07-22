package cn.redflagsoft.base.dao;

import java.util.List;
import java.util.Map;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.RiskMessage;

public interface RiskMessageDao extends Dao<RiskMessage, Long> {
	
	/**
	 * Map中的格式为{clerkId,name}.
	 * 
	 * <pre>
	 * select new map(a.clerkId as clerkId,b.name as name) from RiskMessage a,Clerk b where a.clerkId=b.id and a.type=?
	 * </pre>
	 * @param grade
	 * @return
	 */
	List<Map<String, Object>> findAcceptSMSPersons(Byte grade);
}
