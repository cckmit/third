package cn.redflagsoft.base.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opoo.apps.annotation.Queryable;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import com.google.common.collect.Lists;

import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.SmsgReceiver;
import cn.redflagsoft.base.dao.SmsgDao;
import cn.redflagsoft.base.dao.SmsgReceiverDao;

public class SmsgQuery {
	private SmsgDao smsgDao;
	private SmsgReceiverDao smsgReceiverDao;
	
	public SmsgDao getSmsgDao() {
		return smsgDao;
	}

	public void setSmsgDao(SmsgDao smsgDao) {
		this.smsgDao = smsgDao;
	}

	public SmsgReceiverDao getSmsgReceiverDao() {
		return smsgReceiverDao;
	}

	public void setSmsgReceiverDao(SmsgReceiverDao smsgReceiverDao) {
		this.smsgReceiverDao = smsgReceiverDao;
	}

	@Queryable(name="findSmsgListByCaution")
	public List<Object> findSmsgListByCaution(ResultFilter filter){
		List<Object> result = Lists.newArrayList();
		List<Smsg> list = smsgDao.find(filter);
		for (Smsg smsg : list) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", smsg.getId());
			map.put("code", smsg.getCode());
			map.put("kindName", smsg.getKindName());
			map.put("content", smsg.getContent());
			map.put("sendTime", smsg.getSendTime());
			
			ResultFilter filter2 = ResultFilter.createEmptyResultFilter();
			filter2.setCriterion(Restrictions.eq("smsgId", smsg.getId()));
			List<SmsgReceiver> list2 = smsgReceiverDao.find(filter2);
			
			String addrTemp = "";
			for (SmsgReceiver smsgReceiver : list2) {
				if(smsgReceiver != null){
					if(smsgReceiver.getToAddr() != null){
						if("".equals(addrTemp)){
							addrTemp = smsgReceiver.getToAddr();
						}else{
							addrTemp += " ," + smsgReceiver.getToAddr();
						}
					}
				}
			}
			map.put("toAddr",addrTemp);	
			result.add(map);
		}
		return result;
	}
}
