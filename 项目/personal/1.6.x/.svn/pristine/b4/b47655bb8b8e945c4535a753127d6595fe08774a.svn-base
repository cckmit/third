package org.opoo.apps.dv.connector;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.ConnectionPoolTimeoutException;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.log4j.Logger;
import org.opoo.apps.AppsGlobals;

/**
 * <p>Customized extension to Commons HttpClient 3.1's <code>MultiThreadedHttpConnectionManager</code> that adds
 * additional management capabilities.</p>
 */
public class AppsHttpConnectionManager extends MultiThreadedHttpConnectionManager {

    private static final Logger log = Logger.getLogger(AppsHttpConnectionManager.class);

    // Apps property names (and default values) for the configuration manager properties that can be dynamically changed
    public static final String DEFAULT_ALLOCATE_TIMEOUT_PROPERTY = "apps.connects.http.defaultAllocateTimeout";
    public static final int DEFAULT_ALLOCATE_TIMEOUT_DEFAULT = 5000; // milliseconds
    public static final String DEFAULT_CONNECT_TIMEOUT_PROPERTY = "apps.connects.http.defaultConnectTimeout";
    public static final int DEFAULT_CONNECT_TIMEOUT_DEFAULT = 13000; // milliseconds
    public static final String DEFAULT_READ_TIMEOUT_PROPERTY = "apps.connects.http.defaultReadTimeout";
    public static final int DEFAULT_READ_TIMEOUT_DEFAULT = 13000; // milliseconds
    public static final String MAX_CONNECT_TIMEOUT_PROPERTY = "apps.connects.http.maxConnectTimeout";
    public static final int MAX_CONNECT_TIMEOUT_DEFAULT = 30000; // milliseconds
    public static final String MAX_HOST_CONNECTIONS_PROPERTY = "apps.connects.http.maxHostConnections";
    public static final int MAX_HOST_CONNECTIONS_DEFAULT = 4;
    public static final String MAX_READ_TIMEOUT_PROPERTY = "apps.connects.http.maxReadTimeout";
    public static final int MAX_READ_TIMEOUT_DEFAULT = 30000; // milliseconds
    public static final String MAX_TOTAL_CONNECTIONS_PROPERTY = "apps.connects.http.maxTotalConnections";
    public static final int MAX_TOTAL_CONNECTIONS_DEFAULT = 128;

    protected int outstandingConnections = 0;
    protected Map<String,Integer> outstandingConnectionsByHost = new HashMap<String,Integer>();
    protected long totalAllocations = 0L;
    protected long totalReleases = 0L;

    public AppsHttpConnectionManager() {
        HttpConnectionManagerParams params = new AuditedHttpConnectionManagerParams();
        int connectTimeout =
                AppsGlobals.getProperty(DEFAULT_CONNECT_TIMEOUT_PROPERTY, DEFAULT_CONNECT_TIMEOUT_DEFAULT);
        params.setConnectionTimeout(connectTimeout);
        int maxHostConnections =
        		AppsGlobals.getProperty(MAX_HOST_CONNECTIONS_PROPERTY, MAX_HOST_CONNECTIONS_DEFAULT);
        params.setDefaultMaxConnectionsPerHost(maxHostConnections);
        int maxTotalConnections =
        		AppsGlobals.getProperty(MAX_TOTAL_CONNECTIONS_PROPERTY, MAX_TOTAL_CONNECTIONS_DEFAULT);
        params.setMaxTotalConnections(maxTotalConnections);
        int readTimeout =
        		AppsGlobals.getProperty(DEFAULT_READ_TIMEOUT_PROPERTY, DEFAULT_READ_TIMEOUT_DEFAULT);
        params.setSoTimeout(readTimeout);
        this.setParams(params);
    }

    @Override
    public HttpConnection getConnectionWithTimeout(HostConfiguration hostConfiguration, long timeout)
            throws ConnectionPoolTimeoutException
    {
        if (log.isDebugEnabled()) {
            log.debug("getConnectionWithTimeout(" + hostConfiguration + "," + timeout + ")");
        }
        HttpConnection connection = super.getConnectionWithTimeout(hostConfiguration, timeout);
        if (log.isDebugEnabled()) {
            log.debug("  returns " + toString(connection));
        }
        totalAllocations++;
        outstandingConnections++;
        String key = toConnectionKey(connection);
        synchronized(outstandingConnectionsByHost) {
            Integer count = outstandingConnectionsByHost.get(key);
            if (count == null) {
                count = new Integer(1);
            }
            else {
                count = new Integer(count.intValue() + 1);
            }
            outstandingConnectionsByHost.put(key, count);
        }
        return connection;
    }

    /**
     * <p>Return the total number of outstanding connections (that is, the number that have been allocated but not yet returned.</p>
     */
    public int getOutstandingConnections() {
        return this.outstandingConnections;
    }

    /**
     * <p>Return a map of the number of outstanding connections per host URL, keyed by host URL.</p>
     */
    public Map<String,Integer> getOutstandingConnectionsByHost() {
        return this.outstandingConnectionsByHost;
    }

    /**
     * <p>Return the total number of connection allocations since the last reset.</p>
     */
    public long getTotalAllocations() {
        return this.totalAllocations;
    }

    /**
     * <p>Return the total number of connection releases since the last reset.</p>
     */
    public long getTotalReleases() {
        return this.totalReleases;
    }

    @Override
    public void releaseConnection(HttpConnection connection) {
        if (log.isDebugEnabled()) {
            log.debug("releaseConnection(" + toString(connection) + ")");
        }
        outstandingConnections--;
        totalReleases++;
        String key = toConnectionKey(connection);
        synchronized(outstandingConnectionsByHost) {
            Integer count = outstandingConnectionsByHost.get(key);
            if (count == null) {
                count = new Integer(1);
            }
            if (1 == count.intValue()) {
                outstandingConnectionsByHost.remove(key);
            }
            else {
                count = new Integer(count.intValue() - 1);
                outstandingConnectionsByHost.put(key, count);
            }
            outstandingConnectionsByHost.put(key, count);
        }
        super.releaseConnection(connection);
    }

    /**
     * <p>Reset the allocation and release accumulators.</p>
     */
    public void resetAccumulators() {
        this.totalAllocations = 0L;
        this.totalReleases = 0L;
    }

    private String toConnectionKey(HttpConnection conn) {
        String scheme = conn.getProtocol().getScheme();
        StringBuilder sb = new StringBuilder(scheme).append("://").append(conn.getHost());
        int port = conn.getPort();
        if ((scheme.equals("http") && (port != 80)) ||
            (scheme.equals("https") && (port != 443)) ||
            (scheme.equals("lenient") && (port != 443))) {
            sb.append(":").append(port);
        }
        return sb.toString();
    }

    private String toString(HttpConnection conn) {
        StringBuilder sb = new StringBuilder("HttpConnection")
                .append("{host='").append(conn.getHost()).append("'")
                .append(",port=").append(conn.getPort())
                .append(",scheme=").append(conn.getProtocol().getScheme())
                .append("}");
        return sb.toString();
    }

    /**
     * <p>Wrapper around <code>HttpConnection</code> that can log state changes, and provides high quality
     * <code>equals()</code>, <code>hashCode()</code>, and <code>toString()</code> implementations.</p>
     */
    static class AuditedHttpConnection extends HttpConnection {

        private AuditedHttpConnection(String host, int port) {
            this(null, -1, host, null, port, Protocol.getProtocol("http"));
        }

        private AuditedHttpConnection(String host, int port, Protocol protocol) {
            this(null, -1, host, null, port, protocol);
        }

        private AuditedHttpConnection(String host, String virtualHost, int port, Protocol protocol) {
            this(null, -1, host, virtualHost, port, protocol);
        }

        private AuditedHttpConnection(String proxyHost, int proxyPort, String host, int port) {
            this(proxyHost, proxyPort, host, null, port, Protocol.getProtocol("http"));
        }

        private AuditedHttpConnection(HostConfiguration hostConfiguration) {
            super(hostConfiguration);
            this.proxyHost = hostConfiguration.getProxyHost();
            this.proxyPort = hostConfiguration.getProxyPort();
            this.host = hostConfiguration.getHost();
            this.port = hostConfiguration.getPort();
            this.protocol = hostConfiguration.getProtocol();
        }

        private AuditedHttpConnection(String proxyHost, int proxyPort, String host, String virtualHost, int port,
                Protocol protocol) {
            this(proxyHost, proxyPort, host, port, protocol);
        }

        private AuditedHttpConnection(String proxyHost, int proxyPort, String host, int port, Protocol protocol) {
            super(proxyHost, proxyPort, host, port, protocol);
            this.proxyHost = proxyHost;
            this.proxyPort = proxyPort;
            this.host = host;
            this.port = port;
            this.protocol = protocol;
        }

        private final String host;
        private final int port;
        private final Protocol protocol;
        private final String proxyHost;
        private final int proxyPort;

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AuditedHttpConnection)) {
                return false;
            }
            AuditedHttpConnection that = (AuditedHttpConnection) other;
            if (this.host == null ? that.host != null : !this.host.equals(that.host)) {
                return false;
            }
            if (this.port != that.port) {
                return false;
            }
            if (this.protocol == null ? that.protocol != null : !this.protocol.equals(that.protocol)) {
                return false;
            }
            if (this.proxyHost == null ? that.proxyHost != null : !this.proxyHost.equals(that.proxyHost)) {
                return false;
            }
            if (this.proxyPort != that.proxyPort) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int result = 0;
            result = 31 * result + (host != null ? host.hashCode() : 0);
            result = 31 * result + (port ^ (port >>> 32));
            result = 31 * result + (protocol != null ? protocol.hashCode() : 0);
            result = 31 * result + (proxyHost != null ? proxyHost.hashCode() : 0);
            result = 31 * result + (proxyPort ^ (proxyPort >>> 32));
            return result;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("AuditedHttpConnection")
                    .append("{host='").append(host).append("'")
                    .append(",port=").append(port)
                    .append(",protocol=").append(protocol)
                    .append(",proxyHost='").append(proxyHost).append("'")
                    .append(",proxyPort=").append(proxyPort)
                    .append("}");
            return sb.toString();
        }

    }

    /**
     * <p>Wrapper around <code>HttpConnectionManagerParams</code> that logs settings changes.</p>
     */
    static class AuditedHttpConnectionManagerParams extends HttpConnectionManagerParams {
		private static final long serialVersionUID = -4842039645999599881L;

		// Connection timeout (milliseconds)
        @Override
        public void setConnectionTimeout(int timeout) {
            log.info("setConnectionTimeout(" + timeout + ")");
            super.setConnectionTimeout(timeout);
        }

        @Override
        public void setDefaultMaxConnectionsPerHost(int defaultMaxConnectionsPerHost) {
            log.info("setDefaultMaxConnectionsPerHost(" + defaultMaxConnectionsPerHost + ")");
            super.setDefaultMaxConnectionsPerHost(defaultMaxConnectionsPerHost);
        }

        @Override
        public void setLinger(int value) {
            log.info("setLinger(" + value + ")");
            super.setLinger(value);
        }

        @Override
        public void setMaxConnectionsPerHost(HostConfiguration hostConfiguration, int maxHostConnections) {
            log.info("setMaxConnectionsPerHost(" + hostConfiguration + "," + maxHostConnections + ")");
            super.setMaxConnectionsPerHost(hostConfiguration, maxHostConnections);
        }

        @Override
        public void setMaxTotalConnections(int maxTotalConnections) {
            log.info("setMaxTotalConnections(" + maxTotalConnections + ")");
            super.setMaxTotalConnections(maxTotalConnections);
        }

        @Override
        public void setReceiveBufferSize(int size) {
            log.info("setReceiveBufferSize(" + size + ")");
            super.setReceiveBufferSize(size);
        }

        @Override
        public void setSendBufferSize(int size) {
            log.info("setSendBufferSize(" + size + ")");
            super.setSendBufferSize(size);
        }

        // Read timeout (milliseconds)
        @Override
        public void setSoTimeout(int timeout) {
            log.info("setSoTimeout(" + timeout + ")");
            super.setSoTimeout(timeout);
        }

        @Override
        public void setStaleCheckingEnabled(boolean value) {
            log.info("setStaleCheckingEnabled(" + value + ")");
            super.setStaleCheckingEnabled(value);
        }

        @Override
        public void setTcpNoDelay(boolean value) {
            log.info("setTcpNoDelay(" + value + ")");
            super.setTcpNoDelay(value);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder()
                    .append("{connectionTimeout=").append(getConnectionTimeout())
                    .append(",defaultMaxConnectionsPerHost=").append(getDefaultMaxConnectionsPerHost())
                    .append(",linger=").append(getLinger())
                    .append(",maxTotalConnections=").append(getMaxTotalConnections())
                    .append(",receiveBufferSize=").append(getReceiveBufferSize())
                    .append(",sendBufferSize=").append(getSendBufferSize())
                    .append(",staleCheckingEnabled=").append(isStaleCheckingEnabled())
                    .append(",tcpNoDelay=").append(getTcpNoDelay())
                    .append("}");
            return sb.toString();
        }

    }

}
