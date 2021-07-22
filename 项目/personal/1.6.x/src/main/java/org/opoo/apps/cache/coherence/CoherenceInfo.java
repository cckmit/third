package org.opoo.apps.cache.coherence;


import com.tangosol.coherence.component.util.SafeCluster;
import com.tangosol.coherence.component.util.daemon.queueProcessor.packetProcessor.PacketPublisher;
import com.tangosol.coherence.component.util.daemon.queueProcessor.packetProcessor.PacketReceiver;
import com.tangosol.net.AbstractInvocable;
import com.tangosol.net.InvocationService;

import java.io.Serializable;
import java.util.Map;

/**
 * Coherence ª∫¥Ê–≈œ¢°£
 * A utility class which helps to gather Coherence stats and information.
 */
public class CoherenceInfo {

    /**
     * CPU time taken in milliseconds.
     */
    public static final int STATS_CPU_TIME = 0;

    /**
     * Packets sent by publisher.
     */
    public static final int STATS_SENT = 1;

    /**
     * Packets resent by publisher.
     */
    public static final int STATS_RESENT = 2;

    /**
     * Packets sent to the reciever.
     */
    public static final int STATS_RECEIVED = 1;

    /**
     * Repeated packets sent to the receiver.
     */
    public static final int STATS_REPEATED = 2;

    /**
     * Returns a Map of CoherenceInfo.NodeInfo objects keyed by Coherence Member objects.
     * A NodeInfo object is a collection of various Node stats.
     *
     * @return a Map of NodeInfo objects.
     */
    @SuppressWarnings("unchecked")
	public static Map getNodeInfo() {

        InvocationService service = (InvocationService) com.tangosol.net.CacheFactory.getService("Apps Cluster Service");

        // Run cluster-wide stats query
        Map results = service.query(new AbstractInvocable() {
			private static final long serialVersionUID = -5817646840833750922L;

				public void run() {
                    // Get runtime stats - mem and time:
                    Runtime runtime = Runtime.getRuntime();
                    long free = runtime.freeMemory();
                    long total = runtime.totalMemory();
                    long max = runtime.maxMemory();
                    long time = System.currentTimeMillis();
                    // Get cluster stats. Get the cluster then get its listeners. From there,
                    // get more interestig node stats.
                    com.tangosol.coherence.component.net.Cluster cluster =
                            (com.tangosol.coherence.component.net.Cluster)
                            ((SafeCluster)com.tangosol.net.CacheFactory.ensureCluster()).getCluster();

                    PacketPublisher publisher = cluster.getPublisher();
                    long [] publisherStats = new long[3];
                    publisherStats[STATS_CPU_TIME] = publisher.getStatsCpu();
                    publisherStats[STATS_SENT] = publisher.getStatsSent();
                    publisherStats[STATS_RESENT] = publisher.getStatsResent();

                    PacketReceiver receiver = cluster.getReceiver();
                    long [] receiverStats = new long[3];
                    receiverStats[STATS_CPU_TIME] = -1L;//receiver.getStatsCpu();
                    receiverStats[STATS_SENT] = receiver.getStatsReceived();
                    receiverStats[STATS_RESENT] = receiver.getStatsRepeated();

                    NodeInfo nodeInfo = new NodeInfo(free, total, max, time, publisherStats,
                            receiverStats);
                    setResult(nodeInfo);
                }
            }, null);

        return results;
    }

    /**
     * Clears the cache stats.
     */
    public static void clearCacheStats() {

        InvocationService service = (InvocationService) com.tangosol.net.CacheFactory.getService("Apps Cluster Service");

        service.execute(new AbstractInvocable() {
			private static final long serialVersionUID = -3278246558296719948L;

			public void run() {
                com.tangosol.coherence.component.net.Cluster cluster =
                        (com.tangosol.coherence.component.net.Cluster)
                        ((SafeCluster)com.tangosol.net.CacheFactory.ensureCluster()).getCluster();

                PacketPublisher publisher = cluster.getPublisher();
                publisher.resetStats();
                PacketReceiver receiver = cluster.getReceiver();
                receiver.resetStats();
            }
        }, null, null);
    }

    /**
     * Encapsulates statistics and information about a cluster node.
     */
    public static class NodeInfo implements Serializable {
		private static final long serialVersionUID = 6386654935818051186L;
		private long freeMem;
        private long totalMem;
        private long maxMem;
        private long time;
        private long [] publisherStats;
        private long [] receiverStats;

        NodeInfo(long freeMem, long totalMem, long maxMem, long time, long [] publisherStats,
                long [] receiverStats)
        {
            this.freeMem = freeMem;
            this.totalMem = totalMem;
            this.maxMem = maxMem;
            this.time = time;
            this.publisherStats = publisherStats;
            this.receiverStats = receiverStats;
        }

        /**
         * Returns the amount of free memory in the cluster node's VM (in bytes).
         *
         * @return the amount of free memory on the cluster node.
         */
        public long getFreeMem() {
            return freeMem;
        }

        /**
         * Returns the total amount of memory in the cluster node's VM (in bytes).
         *
         * @return the total amount of memory on the cluster node.
         */
        public long getTotalMem() {
            return totalMem;
        }

        /**
         * Returns the max amount of memory in the cluster node's VM (in bytes).
         *
         * @return the max amount of memory on the cluster node.
         */
        public long getMaxMem() {
            return maxMem;
        }

        /**
         * Returns the current time on the cluster node in long format. This is useful
         * monitoring information for applications that require the local times of each
         * cluster member be to roughly in-synch (on top of the standard Coherence
         * cluster time).<p>
         *
         * This value will always be somewhat inaccurate due to network delays, etc, so
         * should only be taken as an approximate value.
         *
         * @return the local time of the cluster node.
         */
        public long getTime() {
            return time;
        }

        /**
         * Returns statistics about the packet publisher on the node. The following
         * stat values are valid array indexes:<ul>
         *
         *      <li> CoherenceInfo.STATS_CPU_TIME
         *      <li> CoherenceInfo.STATS_SENT
         *      <li> CoherenceInfo.STATS_RESENT
         * </ul>
         *
         * @return packet publisher stats.
         */
        public long [] getPublisherStats() {
            return publisherStats;
        }

        /**
         * Returns statistics about the packet receiver on the node. The following
         * stat values are valid array indexes:<ul>
         *
         *      <li> Coherence.STATS_CPU_TIME
         *      <li> CoherenceInfo.STATS_RECEIVED
         *      <li> CoherenceInfo.STATS_REPEATED</ul>
         *
         * @return packet reciever stats.
         */
        public long [] getReceiverStats() {
            return receiverStats;
        }
    }
}