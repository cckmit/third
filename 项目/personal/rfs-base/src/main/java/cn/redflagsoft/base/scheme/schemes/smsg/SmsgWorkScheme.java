package cn.redflagsoft.base.scheme.schemes.smsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.MatterAffair;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.SmsgReceiver;
import cn.redflagsoft.base.scheme.AbstractWorkScheme;
import cn.redflagsoft.base.scheme.SchemeParametersBuilder;
import cn.redflagsoft.base.scheme.WorkScheme;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.SmsgRuntimeException;
import cn.redflagsoft.base.service.SmsgService;
import cn.redflagsoft.base.util.DateUtil;
import cn.redflagsoft.base.vo.SmsgReadVO;

/**
 * 消息管理。
 * 
 * @author lf
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class SmsgWorkScheme extends AbstractWorkScheme implements SchemeParametersBuilder{
	private static final Log log = LogFactory.getLog(SmsgWorkScheme.class);
	
	private Smsg smsg = new Smsg();
//	private List<SmsgReceiver> smsgReceiverList;
	private List<Long> receiverIds;
	private Long id;
	private byte autosend = 0;
	
	
	public byte getAutosend() {
		return autosend;
	}

	public void setAutosend(byte autosend) {
		this.autosend = autosend;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the receiverIds
	 */
	public List<Long> getReceiverIds() {
		return receiverIds;
	}


	/**
	 * @param receiverIds the receiverIds to set
	 */
	public void setReceiverIds(List<Long> receiverIds) {
		this.receiverIds = receiverIds;
	}


	public Smsg getSmsg() {
		return smsg;
	}


	public void setSmsg(Smsg smsg) {
		this.smsg = smsg;
	}

//	public List<SmsgReceiver> getSmsgReceiverList() {
//		return smsgReceiverList;
//	}
//
//
//	public void setSmsgReceiverList(List<SmsgReceiver> smsgReceiverList) {
//		this.smsgReceiverList = smsgReceiverList;
//	}

	

	public SmsgService getSmsgService() {
		return (SmsgService)getObjectService();
	}
	
	/***
	 * 消息发布
	 * @return
	 */
	public Object doPublish(){
		getSmsgService().publishSmsg((Smsg)getObject());
		finishMatters();
		return "信息发布成功!";
	}
	
	/***
	 * 	消息撤销
	 * @return
	 */
	public Object doCancel(){
		String message = "消息撤销成功!";
		try{
			getSmsgService().cancelSmsg((Smsg)getObject());
		}catch(SmsgRuntimeException e){
			log.debug("消息撤销失败", e);
			message = "消息撤销失败!";
		}
		finishMatters();
		return message;
	}
	
	public Object doDelete(){
		getSmsgService().deleteSmsg((Smsg)getObject());
		finishMatters();
		return "消息删除成功!";
	}
	
	public Object doUpdate(){
		Smsg oldMsg = (Smsg) getObject();
		Smsg newMsg = getSmsg();
//		List<SmsgReceiver> smsgReceiverList = parseReceivers(receiverIds);
//		checkFr(newMsg);
//		
//		oldMsg.setTitle(newMsg.getTitle());
//		oldMsg.setContent(newMsg.getContent());
//		oldMsg.setKeyword(newMsg.getKeyword());
//		oldMsg.setKind(newMsg.getKind());
//		oldMsg.setPublishTime(newMsg.getPublishTime());
//		oldMsg.setExpirationTime(newMsg.getExpirationTime());
		getSmsgService().updateMsg(oldMsg, newMsg, receiverIds, getFileIds());
		finishMatters();
		return "消息已保存";
	}

	public Object doCheck(){
		getSmsgService().checkSmsg((Smsg)getObject());
		finishMatters();
		return "消息审核成功!";
	}
	
	public Object doApprove(){
		getSmsgService().approveSmsg((Smsg)getObject());
		finishMatters();
		return "消息已批准!";
	}
	
	public Object doCreate(){
		finishMatters();
		// 如果tag > 0 表示不需要手动确认消息，并且自动发送消息。
		//（自动发送消息则需要自动调用消息的发布功能。）
		if(autosend == 1){
			log.debug("消息已经创建，执行tag为1的MatterAffair配置。");
			finishMatter((short)1);
		}
		return "消息已保存";
	}
	
	@Override
	public RFSObject createObject() {
//		List<SmsgReceiver> smsgReceiverList = parseReceivers(receiverIds);
//		checkFr(smsg);
//		Smsg createSmsg = getSmsgService().createSmsg(smsg, smsgReceiverList);
		Assert.notNull(smsg, "消息对象不能为空");
		return getSmsgService().createMsg(smsg, receiverIds, getFileIds());
	}
	
	
//	private List<SmsgReceiver> parseReceivers(List<Long> receiverIds){
//		if(receiverIds == null){
//			receiverIds = new ArrayList<Long>();
//		}
//		List<SmsgReceiver> smsgReceiverList = new ArrayList<SmsgReceiver>();
//		for(Long id: receiverIds){
//			Clerk ccc = getClerkService().getClerk(id);
//			SmsgReceiver sr = buildSmsgReceiver(smsg, ccc);
//			if(sr != null){
//				smsgReceiverList.add(sr);
//			}
//		}
//		return smsgReceiverList;
//	}
//	
//	private void checkFr(Smsg smsg){
//		if(smsg.getFrId() == null){
//			Clerk c = getClerk();//UserClerkHolder.getClerk();
//			smsg.setFrId(c.getId());
//			smsg.setFrAddr(getAddressOfClerk(c, smsg.getKind()));
//			smsg.setFrName(c.getName());
//			smsg.setFrOrgId(c.getEntityID());
//			smsg.setFrOrgName(c.getEntityName());
//		}
//	}
//	
//	private String getAddressOfClerk(Clerk c, byte smsgKind){
//		if(smsgKind == Smsg.KIND_电子邮件){
//			return c.getEmailAddr();
//		}else if(smsgKind == Smsg.KIND_内部消息){
//			return "无";
//		}else if(smsgKind == Smsg.KIND_手机短信){
//			return c.getMobNo();
//		}
//		return null;
//	}
//	
//	private SmsgReceiver buildSmsgReceiver(Smsg msg, Clerk c){
//		if(c == null){
//			return null;
//		}
//		SmsgReceiver sr = new SmsgReceiver();
//		sr.setToAddr(getAddressOfClerk(c, msg.getKind()));
//		
//		if(StringUtils.isBlank(sr.getToAddr())){
//			//log.warn("无有效接收地址：" + c.getName());
//			return null;
//		}
//		sr.setToId(c.getId());
//		sr.setToName(c.getName());
//		return sr;
//	}
	
	
	/**
	 * 	消息阅读(接收人)
	 * @return
	 */
	public Object viewSmsgReadByClerk(){
		Clerk clerk = UserClerkHolder.getClerk();
		Smsg getSmsg = getSmsgService().getObject(getObjectId());
		SmsgReadVO vo= getSmsgService().readSmsg(getSmsg, clerk);
		Map<String,String> map = new HashMap<String,String>();
		if(vo != null){
			map.put("frName", vo.getSmsg().getFrName());
			map.put("frAddrName",vo.getSmsg().getFrAddrName());
			map.put("toName",vo.getSmsgReceiver().getToNameLabel());
			//map.put("toAddrName",vo.getSmsgReceiver().getToAddrName());
			map.put("title",vo.getSmsg().getTitle());
			map.put("content",vo.getSmsg().getContent());
			map.put("keyword",vo.getSmsg().getKeyword());
			map.put("smsgId",vo.getSmsg().getId().toString());
			map.put("toNameLabel", vo.getSmsgReceiver().getToNameLabel());
			map.put("frNameLabel", vo.getSmsg().getFrNameLabel());
			if(vo.getSmsgReceiver().getSendTime() != null){
				map.put("sendTime", DateUtil.formatShortDateTime(vo.getSmsgReceiver().getSendTime()));
			}
		}
		return map;
	}
	
//	/**
//	 * 加载编辑消息时的消息。
//	 * @return
//	 */
//	public Object viewLoadSmsg(){
//		Assert.notNull(id, "必须指定要查询的消息的ID");
//		Smsg msg = getSmsgService().getSmsg(id);
//		Map<String, Object> map = BeanUtils.getPropertiesMap(msg);
//		map.put("receiverIds", msg.getId() + "");
//		return map;
//	}
	
	/**
	 * 加载指定消息的所有接收人对应的Clerk对象集合。
	 * @return
	 */
	public Object viewLoadReceivers(){
		Assert.notNull(id, "必须指定要查询的消息的ID");
		List<SmsgReceiver> list = getSmsgService().findReceivers(id);
		List<Clerk> clerks = new ArrayList<Clerk>();
		for(SmsgReceiver r: list){
			Clerk c = getClerkService().getClerk(r.getToId());
			if(c != null){
				clerks.add(c);
			}
		}
		return clerks;
	}

	@Override
	public void setParameters(@SuppressWarnings("rawtypes") Map parameters) {
		super.setParameters(parameters);
		if(log.isDebugEnabled()){
			log.debug("=====================>>>>>> 1: " + parameters.toString());
			log.debug("=====================>>>>>> 2: " + this);
		}
	}

	public Map<String, Object> buildParameters(MatterAffair ma,
			WorkScheme refWorkSchme, RFSObject refObject,
			RFSObject refAffairObject) {
		String schemeInfo = ma.getSchemeInfo();
		if("publishSmsg".equalsIgnoreCase(schemeInfo)){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("objectId", refObject.getId());
				return params;
		}
		return null;
	}
}
