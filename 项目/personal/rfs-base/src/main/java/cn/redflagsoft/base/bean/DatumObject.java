/*
 * $Id: DatumObject.java 5240 2011-12-21 09:27:55Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.bean;

import cn.redflagsoft.base.ObjectTypes;
import cn.redflagsoft.base.annotation.ObjectType;

/**
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
@ObjectType(ObjectTypes.DATUM)
public class DatumObject implements RFSEntityObject, RFSObjectable {
	public static final int OBJECT_TYPE = ObjectTypes.DATUM;
	private Datum datum;
	
	/**
	 * @param datum
	 */
	public DatumObject(Datum datum) {
		super();
		this.datum = datum;
	}

	/**
	 * 
	 */
	public DatumObject() {
		super();
	}
	

	/**
	 * @return the datum
	 */
	public Datum getDatum() {
		return datum;
	}

	/**
	 * @param datum the datum to set
	 */
	public void setDatum(Datum datum) {
		this.datum = datum;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.RFSObjectable#getName()
	 */
	public String getName() {
		return datum != null ? datum.getName() : null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.RFSEntityObject#getId()
	 */
	public Long getId() {
		return datum != null ? datum.getId() : null;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.bean.RFSEntityObject#getObjectType()
	 */
	public int getObjectType() {
		return OBJECT_TYPE;
	}

}
