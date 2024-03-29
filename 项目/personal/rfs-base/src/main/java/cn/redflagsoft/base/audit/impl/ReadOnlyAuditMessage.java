/*
 * $Id: ReadOnlyAuditMessage.java 4341 2011-04-22 02:12:39Z lcj $
 * 
 * Copyright 2007-2010 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.audit.impl;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import cn.redflagsoft.base.audit.AuditMessage;
import cn.redflagsoft.base.security.UserClerk;

/**
 * Default read-only  implementation of AuditMessage.
 */
public class ReadOnlyAuditMessage implements AuditMessage {
	private static final long serialVersionUID = -3313653549124277281L;
	private final long id;
    private final long userID;
    private final long timestamp;
    private final String nodeAddress;
    private final String details;
    private final String description;


    public ReadOnlyAuditMessage(long objID, long userID, long timestamp, String nodeAddress, String details, String description) {
        this.id = objID;
        this.userID = userID;
        this.timestamp = timestamp;
        this.nodeAddress = nodeAddress;
        this.details = details;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public UserClerk getUser() {
		return null;
    }

    public Date getTimestamp() {
        return new Date(timestamp);
    }

    public InetAddress getNode() {
        if (nodeAddress == null) {
            return null;
        }
        try {
            return InetAddress.getByName(nodeAddress);
        }
        catch (UnknownHostException e) {
            return null;
        }
    }

    public String getDetails() {
        return details;
    }

    public String getDescription() {
        return description;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReadOnlyAuditMessage that = (ReadOnlyAuditMessage) o;

        if (timestamp != that.timestamp) {
            return false;
        }
        if (userID != that.userID) {
            return false;
        }
        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (!details.equals(that.details)) {
            return false;
        }
        if (!nodeAddress.equals(that.nodeAddress)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = (int) (userID ^ (userID >>> 32));
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + nodeAddress.hashCode();
        result = 31 * result + details.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ReadOnlyAuditMessage{" + "id=" + id + ", userID=" + userID + ", timestamp=" + timestamp
                + ", nodeAddress='" + nodeAddress + '\'' + ", details='" + details + '\'' + ", description='"
                + description + '\'' + '}';
    }
}
