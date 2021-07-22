package org.opoo.apps.transaction;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.google.common.collect.Lists;

public abstract class TransactionUtils {
	protected static final Logger log = LogManager.getLogger(TransactionUtils.class);
    private static AtomicLong transactionID = new AtomicLong(1);

    private TransactionUtils() { }

    /**
     * Returns true is there is an active transaction, false otherwise.
     *
     * @return true is there is an active transaction, false otherwise.
     */
    public static boolean isTransactionActive() {
        return TransactionSynchronizationManager.isActualTransactionActive();
    }

    /**
     * If inside of a transactions, get the current transaction's name. If none currently exists, create a new
     * unique name, set it, and return it.
     *
     * @return the current transaction's name
     */
    public static String getCurrentTransactionName() {
        if (!isTransactionActive()) {
            return null;
        }

        String name = TransactionSynchronizationManager.getCurrentTransactionName();
        if (name == null) {
            name = "" + transactionID.incrementAndGet();
            TransactionSynchronizationManager.setCurrentTransactionName(name);
        }

        return name;
    }

    /**
     * Clear the name of the current transaction
     */
    protected static void clearCurrentTransactionName() {
        TransactionSynchronizationManager.setCurrentTransactionName(null);
    }

    /**
     * Executes the provided runnable if:
     * <ul>
     * <li>No transaction is active</li>
     * <li>When the active transaction commits</li>
     * </ul>
     *
     * <p>It is excecuted with the lowest priority.
     *
     * <p><b>NOTE:</b> When the runnable is executed the transaction will have been committed already,
	 * but the transactional resources might still be active and accessible. As a
	 * consequence, any data access code triggered at this point will still "participate"
	 * in the original transaction, allowing to perform some cleanup (with no commit
	 * following anymore!), unless it explicitly declares that it needs to run in a
	 * separate transaction. Hence: <b>Use <code>PROPAGATION_REQUIRES_NEW</code>
	 * for any transactional operation that is called from here.</b></p>
     *
     * @param r the runnable
     */
    public static void onTransactionCommit(Runnable r) {
        if (!isTransactionActive()) {
            r.run();
            return;
        }

        TransactionSynchronizationManager.registerSynchronization(
                new RunnableTransactionSynchronization(Ordered.LOWEST_PRECEDENCE, r, TransactionSynchronization.STATUS_COMMITTED));
    }

    /**
     * Executes the provided runnable if:
     * <ul>
     * <li>No transaction is active</li>
     * <li>When the active transaction commits</li>
     * </ul>
     *
     * <p> The order of execution is defined by <code>order</code>. The order starts with 0. If
     * two runnables have the same number, they will be executed in any order.
     *
     * <p><b>NOTE:</b> When the runnable is executed the transaction will have been committed already,
	 * but the transactional resources might still be active and accessible. As a
	 * consequence, any data access code triggered at this point will still "participate"
	 * in the original transaction, allowing to perform some cleanup (with no commit
	 * following anymore!), unless it explicitly declares that it needs to run in a
	 * separate transaction. Hence: <b>Use <code>PROPAGATION_REQUIRES_NEW</code>
	 * for any transactional operation that is called from here.</b></p>
     *
     * @param r the runnable
     * @param order the order to execute the runnable
     */
    public static void onTransactionCommit(Runnable r, int order) {
        if (!isTransactionActive()) {
            r.run();
            return;
        }

        TransactionSynchronizationManager.registerSynchronization(
                new RunnableTransactionSynchronization(order, r, TransactionSynchronization.STATUS_COMMITTED));
    }

    /**
     * Executes the provided runnable iff there is an active transaction and the transaction rolls back.
     *
     * <p><b>NOTE:</b> When the runnable is executed the transaction will have been rolled back already,
	 * but the transactional resources might still be active and accessible. As a
	 * consequence, any data access code triggered at this point will still "participate"
	 * in the original transaction, allowing to perform some cleanup (with no commit
	 * following anymore!), unless it explicitly declares that it needs to run in a
	 * separate transaction. Hence: <b>Use <code>PROPAGATION_REQUIRES_NEW</code>
	 * for any transactional operation that is called from here.</b></p>
     *
     * @param r the runnable
     */
    public static void onTransactionRollback(Runnable r) {
        if (!isTransactionActive()) {
            return;
        }

        TransactionSynchronizationManager.registerSynchronization(
                new RunnableTransactionSynchronization(Ordered.LOWEST_PRECEDENCE, r, TransactionSynchronization.STATUS_ROLLED_BACK,
                        TransactionSynchronization.STATUS_UNKNOWN));
    }

    /**
     * Executes the provided runnable iff there is an active transaction and the transaction rolls back.
     *
     * <p><b>NOTE:</b> When the runnable is executed the transaction will have been rolled back already,
	 * but the transactional resources might still be active and accessible. As a
	 * consequence, any data access code triggered at this point will still "participate"
	 * in the original transaction, allowing to perform some cleanup (with no commit
	 * following anymore!), unless it explicitly declares that it needs to run in a
	 * separate transaction. Hence: <b>Use <code>PROPAGATION_REQUIRES_NEW</code>
	 * for any transactional operation that is called from here.</b></p>
     *
     * @param r the runnable
     * @param order the order to execute the runnable
     */
    public static void onTransactionRollback(Runnable r, int order) {
        if (!isTransactionActive()) {
            return;
        }

        TransactionSynchronizationManager.registerSynchronization(
                new RunnableTransactionSynchronization(order, r, TransactionSynchronization.STATUS_ROLLED_BACK,
                        TransactionSynchronization.STATUS_UNKNOWN));
    }
    
    public static List<Integer> asList(int[] array) {
        List<Integer> list = Lists.newArrayListWithExpectedSize(array.length);
        for (int anArray : array) {
            list.add(anArray);
        }
        return list;
    }
    
    static class RunnableTransactionSynchronization implements TransactionSynchronization, Ordered {
        private Runnable runnable;
        private List<Integer> statuses;
        private int order;

        RunnableTransactionSynchronization(int order, Runnable runnable, int... statuses) {
            this.runnable = runnable;
            this.statuses = /*PrimitiveArrays.*/asList(statuses);
            this.order = order;
        }

        public void suspend() {
            // not used here
        }

        public void resume() {
            // not used here
        }

        public void beforeCommit(boolean readOnly) {
            // not used here
        }

        public void beforeCompletion() {
            // not used here
        }

        public void afterCommit() {
            if (!statuses.contains(TransactionSynchronization.STATUS_COMMITTED)) {
                // don't run rollback synchronizations for commits
                return;
            }

            try {
                runnable.run();
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        public void afterCompletion(int status) {
            if (status == TransactionSynchronization.STATUS_COMMITTED && order == /*CacheTransactionManager.RUN_ORDER*/0) {
                try {
                    // for cache transactions fire them twice - once above and again here after all other runnables have run.
                    // We need to do this because events (which are fired after transactions) can do cache requests
                    // (and puts) and since the txn cache still thinks it's in a txn (which it technically is)
                    // it'll register a runnable to run on transaction commit and queue up the cache put/remove/delete
                    // actions. The thing is, spring won't run the newly registered runnables but rather will just clear
                    // it's synchronizations leaving the txn cache hanging (on this thread) with actions queued up on
                    // a thread local. The next call through to that cache could be outside of the transaction which
                    // will call through to the underlying cache and most likely get old data.
                    runnable.run();
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            else if (status != TransactionSynchronization.STATUS_COMMITTED && statuses.contains(status)) {
                try {
                    // run rollbacks here
                    runnable.run();
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        public int getOrder() {
            return order;
        }
    }
}
