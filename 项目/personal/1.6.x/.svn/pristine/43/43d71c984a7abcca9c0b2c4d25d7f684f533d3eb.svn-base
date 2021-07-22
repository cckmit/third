package org.opoo.apps.security;

import java.util.Iterator;
import java.util.List;


public interface PresenceManager {

	/**
     * Sort by username.
     */
    public static final int SORT_USERNAME = 0;

    /**
     * Sort by online time.
     */
    public static final int SORT_ONLINE_TIME = 1;

    /**
     * Returns whether presences are enabled on a global basis or not.
     *
     * @return whether presences are enabled on a global basis or not.
     */
    boolean isPresencesEnabled();

    /**
     * Sets whether presences are enabled on a global basis or not.
     *
     * @param enabled true if presences should be enabled, false otherwise.
     * @throws UnauthorizedException if not a system administrator.
     */
    void setPresencesEnabled(boolean enabled);

    /**
     * Returns the number of users who are currently online. Online users with a
     * presence status set to invisible will not be included.
     *
     * @return the number of online users.
     */
    int getOnlineUserCount();

   
    /**
     * Returns whether the user is currently online or not. If the user's status is set to
     * Presence.STATUS_INVISIBLE, the user will never show up as online.
     *
     * @param user the user to check to see if they're online.
     * @return whether the user is currently online or not.
     */
    boolean isOnline(User user);

    /**
     * Returns an iterator of users who are currently online. Online users with a
     * presence status other that online or idle will not be included.
     *
     * @return an iterator of online users.
     */
    Iterator<OnlineUser> getOnlineUsers();

   

    /**
     * Returns an iterator of users sorted in the manner requested who are currently online.
     * Online users with a presence status other that online or idle will not be included.
     *
     * @param ascending sort ascending if true, descending if false.
     * @param sortField a valid sort field from the PresenceManager interface.
     * @return an iterator of online users.
     */
    Iterator<OnlineUser> getOnlineUsers(boolean ascending, int sortField);

   
    /**
     * Returns an iterator of users who are currently online matching the criteria given.
     * Online users with a presence status other than online or idle will not be included.
     *
     * @param ascending sort ascending if true, descending if false.
     * @param sortField a valid sort field from the PresenceManager interface.
     * @param numResults - the number of results to return.
     * @return an iterator of online users matching the given criteria.
     */
    Iterator<OnlineUser> getOnlineUsers(boolean ascending, int sortField, int numResults);

    /**
     * Returns an iterator of users who are currently online matching the criteria given.
     * Online users with a presence status other than online or idle will not be included.
     *
     * @param ascending sort ascending if true, descending if false.
     * @param sortField a valid sort field from the PresenceManager interface.
     * @param start the index into the sorted set of users to start iterating from.
     * @param numResults - the number of results to return.
     * @return an iterator of online users matching the given criteria.
     */
    Iterator<OnlineUser> getOnlineUsers(boolean ascending, int sortField, int start, int numResults);

   
  
    /**
     * Create a presence for a user. Creating a presence will automatically set the user to be
     * online.<p>
     *
     * The uid should be unique within the application instance. A good source of a uid is the
     * servlet session id.
     *
     * @param user the user to create a presence for.
     * @param uid a unique string.
     * @param IPAddress the IP address of the user.
     * @return the presence for the user.
     * @throws UnauthorizedException if not the user.
     */
    Presence createPresence(User user, String sessionId, String IPAddress);
    
    
    /**
     * 
     * @param sessionId
     * @return
     */
    Presence getPresence(String sessionId);
    
    

//    /**
//     * Remove the given user's presence and roster entry.
//     *
//     * @param user having their presence removed
//     * @throws UnauthorizedException If currently authenticated user is not a sys-admin.
//     */
//    void deletePresence(User user);

    /**
     * Sets a presence to be offline which causes the presence to be removed from the system.
     *
     * @param presence to presence to set to be offline.
     * @throws UnauthorizedException if not the user.
     */
    void setOffline(Presence presence);
    
    
    //OnlineUser getOnlineUser(User user);
    
    
    List<Presence> getPresences(User user);
}
