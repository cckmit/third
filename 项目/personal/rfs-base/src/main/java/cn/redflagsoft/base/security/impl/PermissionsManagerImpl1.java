/*
 * $Id: PermissionsManagerImpl1.java 4342 2011-04-22 02:17:01Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.security.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventDispatcherAware;
import org.opoo.apps.security.Group;
import org.opoo.apps.security.GroupManager;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserManager;
import org.opoo.cache.Cache;
import org.springframework.dao.DataAccessException;

import cn.redflagsoft.base.security.PermissionType;
import cn.redflagsoft.base.security.Permissions;
import cn.redflagsoft.base.security.PermissionsManager;
import cn.redflagsoft.base.security.impl.dao.Perms;
import cn.redflagsoft.base.security.impl.dao.PermsDao;
import cn.redflagsoft.base.util.LongList;
import cn.redflagsoft.base.util.StringList;


/**
 * 权限管理器。
 * 
 * <p>该版实现以用户组ID和用户ID为主键进行缓存。需要两个缓存器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class PermissionsManagerImpl1 implements PermissionsManager, EventDispatcherAware{
	private static final Log log = LogFactory.getLog(PermissionsManagerImpl1.class);
	private PermsDao permsDao;
	private UserManager userManager;
	private GroupManager groupManager;
	private EventDispatcher eventDispatcher;
	private Cache<Long,PermissionsCacheEntry> groupPermsCache;
	private Cache<Long,PermissionsCacheEntry> userPermsCache;
	

	public PermsDao getPermsDao() {
		return permsDao;
	}
	public void setPermsDao(PermsDao permsDao) {
		this.permsDao = permsDao;
	}
	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	public GroupManager getGroupManager() {
		return groupManager;
	}
	public void setGroupManager(GroupManager groupManager) {
		this.groupManager = groupManager;
	}
	public Cache<Long, PermissionsCacheEntry> getGroupPermsCache() {
		return groupPermsCache;
	}
	public void setGroupPermsCache(Cache<Long, PermissionsCacheEntry> groupPermsCache) {
		this.groupPermsCache = groupPermsCache;
	}
	public Cache<Long, PermissionsCacheEntry> getUserPermsCache() {
		return userPermsCache;
	}
	public void setUserPermsCache(Cache<Long, PermissionsCacheEntry> userPermsCache) {
		this.userPermsCache = userPermsCache;
	}
	public EventDispatcher getEventDispatcher() {
		return eventDispatcher;
	}

	
	public Permissions getFinalGroupPerms(long groupId, String aceId, PermissionType permissionType) 
	{
		//get the permissions bundle
		PermissionsCacheEntry bundle = getGroupPermissionsCacheEntry(groupId);
		
		long permissions = bundle.getPerms(aceId, permissionType);
        return new Permissions(permissions);
	}

	private PermissionsCacheEntry getGroupPermissionsCacheEntry(long groupId) {
		PermissionsCacheEntry bundle = groupPermsCache.get(groupId);
		if(bundle == null){
			synchronized(String.valueOf(groupId)){
				try {
					if(log.isDebugEnabled()){
						log.debug("loading groupperms for groupId: " + groupId);
					}
					List<Perms> list = permsDao.findGroupPerms(groupId);
					bundle = buildPermissionsCacheEntry(list);
					groupPermsCache.put(groupId, bundle);
				} catch (DataAccessException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return bundle;
	}
	
	private PermissionsCacheEntry getUserPermissionsCacheEntry(long userId){
		PermissionsCacheEntry bundle = userPermsCache.get(userId);
		if(bundle == null){
			synchronized(String.valueOf(userId)){
				try {
					if(log.isDebugEnabled()){
						log.debug("loading userperms for userId: " + userId);
					}
					List<Perms> list = permsDao.findUserPerms(userId);
					bundle = buildPermissionsCacheEntry(list);
//					System.out.println("caching bundle " + bundle);
					userPermsCache.put(userId, bundle);
				} catch (DataAccessException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return bundle;
	}
	private PermissionsCacheEntry buildPermissionsCacheEntry(List<Perms> list){
		Collections.sort(list, new PermsComparator());
		StringList additiveAceIds = new StringList();
		StringList negativeAceIds = new StringList();
		LongList additivePerms = new LongList();
		LongList negativePerms = new LongList();
		for (Perms perms : list) {
			int type = perms.getType();
			String aceId = perms.getAceId();
			long permission = perms.getPermissions();
			
			if(type == PermissionType.NEGATIVE.getId()){
				int index = negativeAceIds.indexOf(aceId);
				if(index != -1){
					permission |= negativePerms.get(index);
					negativeAceIds.remove(index);
					negativePerms.remove(index);
				}
				negativeAceIds.add(aceId);
				negativePerms.add(permission);
			}
			//
			else{
				int index = additiveAceIds.indexOf(aceId);
				if(index != -1){
					permission |= additivePerms.get(index);
					additiveAceIds.remove(index);
					additivePerms.remove(index);
				}
				additiveAceIds.add(aceId);
				additivePerms.add(permission);
			}
			
//			if(perms.getType() == PermissionType.NEGATIVE.getId()){
//				negativeAceIds.add(perms.getAceId());
//				negativePerms.add(perms.getPermissions());
//			}else{
//				additiveAceIds.add(perms.getAceId());
//				additivePerms.add(perms.getPermissions());
//			}
		}
		
		return new PermissionsCacheEntry(additiveAceIds.toStringArray(), additivePerms.toArray(),
				negativeAceIds.toStringArray(), negativePerms.toArray());
	}
	
	public Permissions getFinalUserPerms(long userId, String aceId, PermissionType permissionType) {
		PermissionsCacheEntry bundle = getUserPermissionsCacheEntry(userId);
		long perms = bundle.getPerms(aceId, permissionType);
		Permissions permissions = getGroupPerms(userId, aceId, permissionType);
		perms |= permissions.value();
		return new Permissions(perms);
	}
	
	private Permissions getGroupPerms(long userId, String aceId, PermissionType permissionType) {
        long permissions = 0;
        try {
            User user = (User) userManager.loadUserByUserId(userId);
            List<Group> groups = groupManager.findGroupsOfUser(user.getUsername());
            for (Group group : groups) {
            	PermissionsCacheEntry bundle = getGroupPermissionsCacheEntry(group.getId());
            	long perms = bundle.getPerms(aceId, permissionType);
            	permissions |= perms;
			}
        }
        catch (Exception unfe) {
            log.error(unfe.getMessage(), unfe);
        }
        return new Permissions( permissions );
    }


	
	/**
     * Returns the normal permissions value from the db format. So, converting from x to y in:
     * 2^x=y.
     *
     * @param dbPermissions the db format of the permissions value.
     * @return the normal permissions value.
     */
    public static long fromDbFormat(int dbPermissions) {
        return (long) Math.pow(2, dbPermissions);
    }

    /**
     * Converts a normal permissions value into the db format. So, converting from y to x in:
     * 2^x=y.
     *
     * @param permission the normal permissions value.
     * @return the db format of the permissions value.
     */
	public static int toDbFormat(long permission) {
        if (permission < 1) {
            throw new IllegalArgumentException("Permission value negative: " + permission);
        }
        long value = permission;
        int count = 0;
        while ((value & 1) != 1) {
            value >>= 1;
            count++;
        }
        if (value != 1) {
            throw new IllegalArgumentException("Invalid permission value: " + permission);
        }
        return count;
    }
	
	
	public static class PermissionsCacheEntry implements Serializable {
		private static final long serialVersionUID = -1604210753774612621L;
		private String[] additiveAceIds;
		private String[] negativeAceIds;
		private long[] additivePerms;
		private long[] negativePerms;
		
		private PermissionsCacheEntry(String[] additiveAceIds, long[] additivePerms, 
				String[] negativeAceIds, long[] negativePerms){
			this.additiveAceIds = additiveAceIds;
			this.negativeAceIds = negativeAceIds;
			this.additivePerms = additivePerms;
			this.negativePerms = negativePerms;
			
			sort(this.additiveAceIds, this.additivePerms);
			sort(this.negativeAceIds, this.negativePerms);
		}
		
		public long getPerms(String aceId, PermissionType permissionType){
			if(permissionType == PermissionType.NEGATIVE){
				int index = Arrays.binarySearch(negativeAceIds, aceId);
				if(index >= 0){
					return negativePerms[index];
				}else{
					return Permissions.NONE;
				}
			}else{
				int index = Arrays.binarySearch(additiveAceIds, aceId);
				if(index >= 0){
					return additivePerms[index];
				}else{
					return Permissions.NONE;
				}
			}
		}
	}
	
	public static class PermsComparator implements Comparator<Perms>{
		public int compare(Perms o1, Perms o2) {
			return o1.getAceId().compareTo(o2.getAceId());
		}
	}
	
    /**
     * Special version of selection sort. Instead of sorting a single array, it sorts two arrays
     * based on the values in the first. This implies the two arrays are correlated. We could
     * accomplish the same thing more elegantly using custom objects and a Comparator, but this
     * method is used for maximum speed and minimal memory usage.<p>
     * <p/>
     * Both arrays must be the same size.
     *
     * @param array1 the array to sort values on.
     * @param array2 the array to sort based on the values in <tt>array1</tt>.
     */
    private static void sort(String[] array1, long[] array2) {
        int left = 0;
        int right = array1.length - 1;
        int max, j;
        String temp;
        long temp2;
        for (int i = right; i > left; i--) {
            max = 0;
            // Find largest non-sorted item in array.
            for (j = left + 1; j <= i; j++) {
            	if(array1[max].compareTo(array1[j]) < 0){
//            	if (array1[max] < array1[j]) {
                    max = j;
                }
            }
            // Swap elements in array1
            temp = array1[i];
            array1[i] = array1[max];
            array1[max] = temp;
            // Also swap elements in array2
            temp2 = array2[i];
            array2[i] = array2[max];
            array2[max] = temp2;
        }
    }
    
    
    


	public void setEventDispatcher(EventDispatcher arg0) {
		this.eventDispatcher = arg0;
	}

	public void addUserPermission(User user, String aceId, PermissionType permissionType, long permission) {
		checkPermission(permission);
		permsDao.addUserPermission(user.getUserId(), aceId, permissionType.getId(), permission);
		userPermsCache.remove(user.getUserId());
	}

	public void removeUserPermission(User user, String aceId, PermissionType permissionType, long permission) {
		checkPermission(permission);
		permsDao.removeUserPermission(user.getUserId(), aceId, permissionType.getId(), permission);
		userPermsCache.remove(user.getUserId());
	}

	public void removeAllUserPermissions(String aceId, PermissionType permissionType) {
		permsDao.removeAllUserPermissions(aceId, permissionType.getId());
		userPermsCache.clear();
		
	}

	public void addGroupPermission(Group group, String aceId, PermissionType permissionType, long permission) {
		checkPermission(permission);
		permsDao.addGroupPermission(group.getId(), aceId, permissionType.getId(), permission);
		groupPermsCache.remove(group.getId());
	}

	public void removeGroupPermission(Group group, String aceId, PermissionType permissionType, long permission) {
		checkPermission(permission);
		permsDao.removeGroupPermission(group.getId(), aceId, permissionType.getId(), permission);
		groupPermsCache.remove(group.getId());
	}

	public void removeAllGroupPermissions(String aceId, PermissionType permissionType) {
		permsDao.removeAllGroupPermissions(aceId, permissionType.getId());
		groupPermsCache.clear();
	}

	
	private void checkPermission(long permission){
		if (permission < 1) {
            throw new IllegalArgumentException("Permission value negative: " + permission);
        }
	}
	
	
	/*
	public Permissions getPermissions(User user, String aceId) {
        Permissions permissionUnion = new Permissions(Permissions.NONE);
        Permissions grantedPermissions = getFinalUserPerms(user.getUserId(), aceId, PermissionType.ADDITIVE);
		Permissions revokedPermissions = getFinalUserPerms(user.getUserId(), aceId, PermissionType.NEGATIVE);
        permissionUnion.set(grantedPermissions.value(), true);
        permissionUnion.set(revokedPermissions.value(), false);
        
        List<Group> groups = groupManager.findGroupsOfUser(user.getUsername());
        
        for(Group g: groups){
        	Permissions a = getFinalGroupPerms(g.getId(), aceId, PermissionType.ADDITIVE);
        	Permissions b = getFinalGroupPerms(g.getId(), aceId, PermissionType.NEGATIVE);
        	permissionUnion.set(a.value(), true);
            permissionUnion.set(b.value(), false);
        }
        return permissionUnion;
	}*/
	
	
	public Permissions getPermissions(long userId, String aceId) {
        Permissions permissionUnion = new Permissions(Permissions.NONE);

        Permissions grantedPermissions = getFinalUserPerms(userId, aceId, PermissionType.ADDITIVE);

        Permissions revokedPermissions = getFinalUserPerms(userId, aceId, PermissionType.NEGATIVE);

        permissionUnion.set(grantedPermissions.value(), true);
        permissionUnion.set(revokedPermissions.value(), false);

        return permissionUnion;
	}
	
	public Map<String,Permissions> getPermissions2(User user){
		List<Group> groups = groupManager.findGroupsOfUser(user.getUsername());
		PermissionsCacheEntry userBundle = getUserPermissionsCacheEntry(user.getUserId());
		
		//处理正向
		Map<String,Permissions> additivePermsMap = new HashMap<String,Permissions>();
		for(int i = 0 ; i < userBundle.additiveAceIds.length ; i++){
			addToMap(additivePermsMap, userBundle.additiveAceIds[i], userBundle.additivePerms[i], true);
		}
		for(Group g : groups){
			PermissionsCacheEntry groupBundle = getGroupPermissionsCacheEntry(g.getId());
			for(int i = 0 ; i < groupBundle.additiveAceIds.length ; i++){
				addToMap(additivePermsMap, groupBundle.additiveAceIds[i], groupBundle.additivePerms[i], true);
			}
		}
		
		
		//处理逆向
		Map<String,Permissions> negativePermsMap = new HashMap<String,Permissions>();
		for(int i = 0 ; i < userBundle.negativeAceIds.length ; i++){
			addToMap(negativePermsMap, userBundle.negativeAceIds[i], userBundle.negativePerms[i], true);
		}
		for(Group g : groups){
			PermissionsCacheEntry groupBundle = getGroupPermissionsCacheEntry(g.getId());
			for(int i = 0 ; i < groupBundle.negativeAceIds.length ; i++){
				addToMap(negativePermsMap, groupBundle.negativeAceIds[i], groupBundle.negativePerms[i], true);
			}
		}
		
		//合并
		for(Map.Entry<String,Permissions> en: negativePermsMap.entrySet()){
			addToMap(additivePermsMap, en.getKey(), en.getValue().value(), false);
		}
		return additivePermsMap;
	}
	
	public Map<String, Permissions> getPermissions(User user) {
		List<Perms> userPerms = permsDao.findUserPerms(user.getUserId());
		List<Perms> groupPerms = permsDao.findGroupPermsByUsername(user.getUsername());
		
		//additive permissions
		Map<String,Permissions> additivePermsMap = new HashMap<String,Permissions>();
		for (Perms perms : userPerms) {
			if(perms.getType() == PermissionType.ADDITIVE.getId()){
				addToMap(additivePermsMap, perms.getAceId(), perms.getPermissions(), true);
			}
		}
		for (Perms perms : groupPerms) {
			if(perms.getType() == PermissionType.ADDITIVE.getId()){
				addToMap(additivePermsMap, perms.getAceId(), perms.getPermissions(), true);
			}
		}
		
		//negative permissions
		Map<String,Permissions> negativePermsMap = new HashMap<String,Permissions>();
		for (Perms perms : userPerms) {
			if(perms.getType() == PermissionType.NEGATIVE.getId()){
				addToMap(negativePermsMap, perms.getAceId(), perms.getPermissions(), false);
			}
		}
		for (Perms perms : groupPerms) {
			if(perms.getType() == PermissionType.NEGATIVE.getId()){
				addToMap(negativePermsMap, perms.getAceId(), perms.getPermissions(), false);
			}
		}
		
		//union
		for(Map.Entry<String,Permissions> en: negativePermsMap.entrySet()){
			addToMap(additivePermsMap, en.getKey(), en.getValue().value(), false);
		}
		return additivePermsMap;
	}
	
	protected static void addToMap(Map<String,Permissions> map, String aceId, long permissions, boolean additive){
		Permissions perms = map.get(aceId);
		if(perms == null){
			perms = new Permissions(Permissions.NONE);
		}
		perms.set(permissions, additive);
		if(perms.value() > 0){
			map.put(aceId, perms);
		}else{
			map.remove(aceId);
		}
	}
	
	
	public void removeAllUserPermissions(long userId, PermissionType permissionType) {
		permsDao.removeAllUserPermissions(userId, permissionType.getId());
		userPermsCache.remove(userId);
	}
	public void removeAllGroupPermissions(long groupId, PermissionType permissionType) {
		permsDao.removeAllGroupPermissions(groupId, permissionType.getId());
		groupPermsCache.remove(groupId);
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args){
		System.out.println((long)Math.pow(2, 63) + "");
		long perms = 16;
		
		int format = toDbFormat(perms);
		System.out.println(format);
		
		String[] arr = new String[]{"da", "bb", "ccc"};
		Arrays.sort(arr);
		System.out.println(Arrays.toString(arr));
		int index = Arrays.binarySearch(arr, "bb");
		System.out.println(index);
		
		
		Permissions p = new Permissions(0);
		p.set(16, false);
		System.out.println(p.value());
	}
}
