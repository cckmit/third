/*
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.process.impl;

import java.util.Date;
import java.util.Map;
import org.opoo.apps.Model;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Msg;
import cn.redflagsoft.base.dao.MsgDao;
import cn.redflagsoft.base.process.AbstractWorkProcess;
import cn.redflagsoft.base.process.WorkProcessException;
import cn.redflagsoft.base.process.annotation.ProcessType;
import cn.redflagsoft.base.security.UserClerkHolder;


/**
 *
 * 
 * @author 
 *
 */
@ProcessType(AddOrUpdateMsgWorkProcess.TYPE)
public class AddOrUpdateMsgWorkProcess extends AbstractWorkProcess {
	public static final int TYPE = 6180;
	
	private Msg msg;
	private MsgDao msgDao;
	
	public Msg getMsg() {
		return msg;
	}

	public void setMsg(Msg msg) {
		this.msg = msg;
	}

	public MsgDao getMsgDao() {
		return msgDao;
	}

	public void setMsgDao(MsgDao msgDao) {
		this.msgDao = msgDao;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.process.WorkProcess#execute(java.util.Map, boolean)
	 */
	public Object execute(Map parameters, boolean needLog) throws WorkProcessException {	
		if(msg == null){
			return new Model(false, "操作失败，提交数据为空。", "");
		}
		Clerk clerk = UserClerkHolder.getClerk();
		msg.setFromId(clerk.getId());
		msg.setFromName(clerk.getName());
		msg.setAttached(false);
		
		if(msg.getId() != null){//以前的草稿
			Msg old = msgDao.get(msg.getId());
			if(old.getSendStatus() == Msg.SEND_STATUS_SENDING){
				return new Model(false, "信息正在发送，不能修改。", "");
			}	
			old.setTitle(msg.getTitle());
			old.setAttached(msg.isAttached());
			old.setContent(msg.getContent());
			if(msg.getSendingMsgId()!=null){
				old.setSendingMsgId(msg.getSendingMsgId());
			}
			old.setToId(msg.getToId());
			old.setToType(msg.getToType());
			old.setToAddr(msg.getToAddr());
			old.setToName(msg.getToName());
			old.setSendStatus(msg.getSendStatus());	
			if(msg.getSendTime()!=null){
				old.setSendTime(msg.getSendTime());
			}
			if(msg.getSendTimeStr()!=null){
				old.setSendTimeStr(msg.getSendTimeStr());
			}
			if(msg.getReadTimeStr()!=null){
				old.setReadTimeStr(msg.getReadTimeStr());
			}
			if(msg.getReader()!=null){
				old.setReader(msg.getReader());
			}	
			old.setReadStatus(msg.getReadStatus());
			if(msg.getPublishTime()!=null){
				old.setPublishTime(msg.getPublishTime());
			}
			if(msg.getExpirationTime()!=null){
				old.setExpirationTime(msg.getExpirationTime());
			}
			old.setTrySendCount(msg.getTrySendCount());
			old.setFromId(msg.getFromId());
			old.setFromName(msg.getFromName());
			old.setModificationTime(new Date());		
			old.setStatus(msg.getStatus());
			old.setType(msg.getType());
			
			msgDao.update(old);
			return "更新消息成功！";
		}else{//新消息
			msg.setReadStatus((byte)0);
			msgDao.save(msg);
			return "新增消息成功！";
		}
		
	
		
		
	}

	
	
}
