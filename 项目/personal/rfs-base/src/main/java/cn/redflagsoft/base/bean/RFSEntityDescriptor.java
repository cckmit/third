/*
 * $Id: RFSEntityDescriptor.java 4615 2011-08-21 07:10:37Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.io.Serializable;

/**
 * 实体描述。
 * @author Alex Lin(alex@opoo.org)
 * @since 2.0.1
 */
public class RFSEntityDescriptor implements Serializable, RFSEntityObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 693208213101169109L;
	protected long id;
    protected int objectType;

    /**
     * Constructor for serialization use only.
     */
    public RFSEntityDescriptor() {}
    
    public RFSEntityDescriptor(int objectType, long id) {
		super();
		this.objectType = objectType;
		this.id = id;
	}
    
    public RFSEntityDescriptor(RFSEntityObject bean) {
        objectType = bean.getObjectType();
        id = bean.getId();
    }
	public Long getId() {
		return id;
	}

	public int getObjectType() {
		return objectType;
	}

	public void setId(long objectId) {
		this.id = objectId;
	}

	public void setEntityType(int objectType) {
		this.objectType = objectType;
	}
    public boolean isValid() {
        return id > 0L;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof RFSEntityDescriptor)) {
            return false;
        }

        RFSEntityDescriptor that = (RFSEntityDescriptor) o;

        if (id != that.id) {
            return false;
        }
        //noinspection RedundantIfStatement
        if (objectType != that.objectType) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + objectType;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("RFSEntityDescriptor");
        sb.append("{objectId=").append(id);
        sb.append(", objectType=").append(objectType);
        sb.append('}');
        return sb.toString();
    }
}
