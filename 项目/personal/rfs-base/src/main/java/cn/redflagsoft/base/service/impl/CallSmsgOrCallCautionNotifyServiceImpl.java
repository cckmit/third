package cn.redflagsoft.base.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Risk;
import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.SmsgReceiver;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.CallSmsgOrCallCautionNotifyService;
import cn.redflagsoft.base.service.SmsgService;

import com.google.common.collect.Lists;

public class CallSmsgOrCallCautionNotifyServiceImpl implements CallSmsgOrCallCautionNotifyService{
	
	private static final Log log = LogFactory.getLog(CallSmsgOrCallCautionNotifyServiceImpl.class);
	
	private SmsgService smsgService;
	private SchemeManager schemeManager;
	
	public SchemeManager getSchemeManager() {
		return schemeManager;
	}

	public void setSchemeManager(SchemeManager schemeManager) {
		this.schemeManager = schemeManager;
	}

	public SmsgService getSmsgService() {
		return smsgService;
	}

	public void setSmsgService(SmsgService smsgService) {
		this.smsgService = smsgService;
	}

	public void callSmsg(Caution caution) throws Exception {
		if (caution != null) {
			ResultFilter filter = ResultFilter.createEmptyResultFilter();
			Logic and = Restrictions.logic(
					Restrictions.eq("refObjectId", caution.getId())).and(
					Restrictions.eq("refObjectType",Caution.OBJECT_TYPE));
			filter.setCriterion(and);
			List<Smsg> smsgList = smsgService.findObjects(filter);
			if(smsgList != null && !smsgList.isEmpty()){
				for (Smsg smsg : smsgList) {
					if(smsg.getBizStatus() != Smsg.BIZ_STATUS_已拟定 && smsg.getBizStatus() != Smsg.BIZ_STATUS_已批准){
						log.debug("消息不能发布,发生异常....请核对消息状态!");
						continue;
					}
					Scheme scheme = getSchemeManager().getScheme("publishSmsg");
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("objectId", smsg.getId());
					((AbstractWorkScheme)scheme).setParameters(map);
					SchemeInvoker.invoke(scheme);
					
				}
			}
		}
	}

	public void callCautionNotify(Caution caution, Risk risk) throws Exception {
		if(caution == null){
			log.debug("Caution为空，不执行callCautionNotify");
			return;
		}
		
		Clerk clerk = UserClerkHolder.getClerk();
		List<Smsg> smsgList = Lists.newArrayList();
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		Logic and = Restrictions.logic(
				Restrictions.eq("refObjectId", caution.getId())).and(
				Restrictions.eq("refObjectType", (int)Caution.OBJECT_TYPE));
		filter.setCriterion(and);
		smsgList = smsgService.findObjects(filter);
		
		boolean isCall = false;
		
		
		if(caution.getGrade() == Risk.GRADE_WHITE){
			if(smsgList != null && !smsgList.isEmpty()){
				isCall = true;
			}
			else{
				isCall = false;
			}
		}else{
			isCall = true;
		}
		
		
		if(isCall){
			for (Smsg smsg : smsgList) {
				List<SmsgReceiver> listReceivers = smsgService.findReceivers(smsg.getId());
				SmsgReceiver sr = null;
				if(listReceivers != null && !listReceivers.isEmpty()){
					sr = listReceivers.iterator().next();
				}
				if(sr == null){
					log.error("消息无接收人：" + smsg.getId() + " : " + smsg.getTitle());
					continue;
				}
				
				Scheme scheme2 = getSchemeManager().getScheme("cautionSmsNotify");
				Map<String,Object> map2 = new HashMap<String,Object>();
				map2.put("objectId", caution.getId());
				map2.put("cautionNotify.cautionId", caution.getId());
				map2.put("cautionNotify.cautionName", caution.getName());
				map2.put("cautionNotify.cautionCode", caution.getCode());
				map2.put("cautionNotify.notifyMsgCode", smsg.getCode());
				
				//System.out.println("");
				map2.put("cautionNotify.notifyTargetId", sr.getToId());
				map2.put("cautionNotify.notifyTargetName",sr.getToName());
				map2.put("cautionNotify.notifyContent", smsg.getContent());
				map2.put("cautionNotify.notifyClerkId", clerk.getId());
				map2.put("cautionNotify.notifyClerkName", clerk.getName());
				map2.put("cautionNotify.notifyTime",new Date());
				((AbstractWorkScheme)scheme2).setParameters(map2);
				SchemeInvoker.invoke(scheme2);
			}
		}else{
			Scheme scheme2 = getSchemeManager().getScheme("cautionSmsNotify");
			Map<String,Object> map2 = new HashMap<String,Object>();
			map2.put("objectId", caution.getId());
			/*
			map2.put("cautionNotify.cautionId", caution.getId());
			map2.put("cautionNotify.cautionName", caution.getName());
			map2.put("cautionNotify.cautionCode", caution.getCode());
			map2.put("cautionNotify.notifyMsgCode", "");
			
			List<SmsgReceiver> listReceivers = smsgService.findReceivers(smsg.getId());
			SmsgReceiver sr = null;
			if(listReceivers != null && !listReceivers.isEmpty()){
				sr = listReceivers.iterator().next();
			}
			map2.put("cautionNotify.notifyTargetId",null);
			map2.put("cautionNotify.notifyTargetName",null);
			map2.put("cautionNotify.notifyContent", null);
			map2.put("cautionNotify.notifyClerkId", clerk.getId());
			map2.put("cautionNotify.notifyClerkName", clerk.getName());
			map2.put("cautionNotify.notifyTime",new Date());
			*/
			((AbstractWorkScheme)scheme2).setParameters(map2);
			SchemeInvoker.invoke(scheme2,"avoid");
		}
	}
	
}
