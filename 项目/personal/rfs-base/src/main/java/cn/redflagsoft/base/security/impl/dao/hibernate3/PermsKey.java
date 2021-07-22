/*
 * $Id: PermsKey.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.security.impl.dao.hibernate3;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class PermsKey implements Serializable {
	private static final long serialVersionUID = 3480786591998219038L;
	private String aceId;
	private long sid;
	private int type;

	public PermsKey(String aceId, long sid, int type) {
		this.aceId = aceId;
		this.sid = sid;
		this.type = type;
	}
	
	public PermsKey(){
	}
	
	public String getAceId() {
		return aceId;
	}
	public void setAceId(String aceId) {
		this.aceId = aceId;
	}
	public long getSid() {
		return sid;
	}
	public void setSid(long sid) {
		this.sid = sid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(aceId).append(sid).append(type).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if(!(obj instanceof PermsKey)){
			return false;
		}
		PermsKey key = (PermsKey)obj;
		
		if(key.aceId == null){
			return false;
		}
		return key.aceId.equals(aceId) && (key.sid == sid) && (key.type == type);
	}
}
