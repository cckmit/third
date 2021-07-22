package org.opoo.apps.security;

import java.util.Comparator;
import java.util.Date;
import java.util.UUID;


public interface Presence {
	
    int STATUS_ONLINE = 0;
    int STATUS_IDLE = 1;
    int STATUS_INVISIBLE = 2;
    int STATUS_AWAY = 3;
	
    
	String getSessionId();
	
	String getUsername();
	
	User getUser();
	
	String getRemoteAddr();
	
	Date getLoginTime();

    Date getLastUpdateTime();

    void setLastUpdateTime(Date date);

    void setLastUpdateTime(long time);

    int getStatus();

    void setStatus(int status);
    
    UUID getNodeId();
    
    int getLoginStatus();

    void setLoginStatus(int loginStatus);
    
    
    
    public static class LastUpdateTimeComparator implements Comparator<Presence> {
		boolean ascending = true;

		public LastUpdateTimeComparator(boolean asc) {
			ascending = asc;
		}

		public int compare(Presence presence1, Presence presence2) {
			if (ascending)
				return presence1.getLastUpdateTime().compareTo(presence2.getLastUpdateTime());
			else
				return presence2.getLastUpdateTime().compareTo(presence1.getLastUpdateTime());
		}
	}
}
