package org.opoo.apps.dv.connector;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
* Represents a Host that a service is attempting to use.
*
* @since Jive SBS Double Mountain
*/
public class ServiceHost {

    private final Logger log = Logger.getLogger(ServiceHost.class);

    private final String host;
    private boolean checkConnection = true;
    private int connectionTimeout = 60*1000;

    public ServiceHost(String host) {
        this.host = host;
    }

    public ServiceHost(String host, boolean checkConnection, int connectionTimeout) {
        this.host = host;
        this.checkConnection = checkConnection;
        this.connectionTimeout = connectionTimeout;
    }

    public void setCheckConnection(boolean checkConnection) {
        this.checkConnection = checkConnection;
    }

    public String getHost() {
        return host;
    }

    public boolean isValid() {
        if (checkConnection) {
            try {
                InetAddress addr = InetAddress.getByName(host);
                boolean test = addr.isReachable(connectionTimeout);
                log.debug("Is host " + addr + " reachable? " + test);
                return test;
            }
            catch (UnknownHostException e) {
                log.error("Can't find host with address " + host + ": " + e.getMessage());
                return false;
            }
            catch (IOException e) {
                log.error("Can't reach host with address " + host + ": " + e.getMessage());
                return false;
            }
        }
        else {
            log.debug("No connection check on host  " + host);
            return true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceHost serviceHost = (ServiceHost) o;

        if (!host.equals(serviceHost.host)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return host.hashCode();
    }

    @Override
    public String toString() {
        return host;
    }
}