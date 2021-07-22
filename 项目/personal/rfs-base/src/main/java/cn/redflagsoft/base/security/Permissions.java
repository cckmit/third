/*
 * $Id: Permissions.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security;

import java.io.Serializable;

import org.springframework.security.GrantedAuthority;

public class Permissions implements Serializable {

	private static final long serialVersionUID = -5207732689860844044L;
	
	 /**
     * No permissions.
     */
    public static final long NONE = 0x0L;

    /**
     * A long holding permission values. We use the bits in the number to extract up to 63
     * different permissions.
     */
    private long permissions;

    /**
     * Constructor for serialization use only.
     */
    public Permissions() { }

    /**
     * Create a new permissions object with the specified permissions.
     *
     * @param permissions integer bitmask values to for the new Permissions.
     */
    public Permissions(long permissions) {
        this.permissions = permissions;
    }

    /**
     * Creates a permissions object with the given granted authorities from
     * the acegi infrastructure.
     * @param authorities
     */
    public Permissions(GrantedAuthority[] authorities) {
        if(null == authorities) {
            this.permissions = 0;
        }
        else {
            for(GrantedAuthority auth: authorities) {
                this.permissions |= Integer.parseInt(auth.getAuthority());
            }
        }
    }

    /**
     * Creates a new Permission object by combining two permissions
     * objects. The higher permission of each permission type will be used.
     *
     * @param permissions1 the first permissions to use when creating the new Permissions.
     * @param permissions2 the second permissions to use when creating the new Permissions.
     */
    public Permissions(Permissions permissions1, Permissions permissions2) {
        permissions = permissions1.permissions | permissions2.permissions;
    }

    /**
     * Returns true if one or more of the permission types is set to true.
     *
     * @param perm
     * @return true if one or more of the permission types is set to true, false otherwise.
     */
    public boolean hasPermission(long perm) {
        return (permissions & perm) != 0;
    }

    /**
     * Sets the permissions given by a bit mask to true or false. 
     *
     * @param permissionTypes the permission types to set.
     * @param value true to enable the permission, false to disable.
     */
    public Permissions set(long permissionTypes, boolean value) {
        if (value) {
            permissions = permissions | permissionTypes;
        }
        else {
            permissionTypes = ~permissionTypes;
            permissions = permissions & permissionTypes;
        }
        return this;
    }

    /**
     * Returns the long value (bitmask) of the permissions that are set.
     *
     * @return the long value of the object.
     */
    public long value() {
        return permissions;
    }
    
    public static void main(String[] args){
    	Permissions perms = new Permissions();
    	perms.set(2, true).set(8, false).set(2, false);
    	System.out.println(perms.value());
    }
}

