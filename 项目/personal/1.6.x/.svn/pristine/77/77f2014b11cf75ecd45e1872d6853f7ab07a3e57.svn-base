package org.opoo.apps.security;

import java.util.Date;
import java.util.UUID;

/**
 * ÔÚÏß¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface OldPresence{

    int STATUS_ONLINE = 0;
    int STATUS_IDLE = 1;
    int STATUS_INVISIBLE = 2;
    int STATUS_AWAY = 3;

    String getId();

    String getUsername();

    User getUser();

    String getIPAddress();

    Date getLoginTime();

    Date getLastUpdateTime();

    void setLastUpdateTime(Date date);

    void setLastUpdateTime(long time);

    int getStatus();

    void setStatus(int status);
    
    UUID getNodeId();
}
