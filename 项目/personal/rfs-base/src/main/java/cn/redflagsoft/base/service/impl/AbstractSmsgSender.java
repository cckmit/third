/*
 * $Id: AbstractSmsgSender.java 5291 2011-12-28 02:43:49Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.service.impl;

import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.lifecycle.Application;

import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.SmsgReceiver;
import cn.redflagsoft.base.event2.SmsgSendEvent;
import cn.redflagsoft.base.service.SmsgSender;

/**
 * �������Ϣ��������
 * 
 * ��������Ϣ���͸���������¿��Ե��õ��¼���
 * @author Alex Lin(lcql@msn.com)
 *
 */
public abstract class AbstractSmsgSender implements SmsgSender {
	
	/**
	 * ��Ϣ��ʼ����ʱ�¼����á�
	 * @param msg ��Ϣ
	 * @param r ������
	 */
	protected final void fireSmsgSendStartEvent(Smsg msg, SmsgReceiver r){
		fireEvent(msg, r, SmsgSendEvent.Type.START);
	}
	
	/**
	 * ��Ϣ���ͳɹ�ʱ���¼����á�
	 * @param msg ��Ϣ
	 * @param r ������
	 */
	protected final void fireSmsgSendSuccessEvent(Smsg msg, SmsgReceiver r){
		fireEvent(msg, r, SmsgSendEvent.Type.SUCCESS);
	}
	
	/**
	 * ��Ϣ����ʧ��ʱ���¼����á�
	 * @param msg ��Ϣ
	 * @param r ������
	 */
	protected final void fireSmsgSendFailEvent(Smsg msg, SmsgReceiver r){
		fireEvent(msg, r, SmsgSendEvent.Type.FAIL);
	}
	
	private void fireEvent(Smsg msg, SmsgReceiver r, SmsgSendEvent.Type type){
		EventDispatcher dispatcher = getEventDispatcher();
		if(dispatcher != null){
			SmsgSendEvent event = new SmsgSendEvent(type, this, msg, r);
			dispatcher.dispatchEvent(event);
		}
	}
	private EventDispatcher getEventDispatcher(){
		return Application.getContext().get("eventDispatcher", EventDispatcher.class);
	}
}
