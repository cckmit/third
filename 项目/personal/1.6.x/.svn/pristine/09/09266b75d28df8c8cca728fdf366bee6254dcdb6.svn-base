package org.opoo.apps.id.sequence;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;

/**
 * 通过一个 Sequence 实例来封装的可缓存一定数量 id 的新 Sequence。
 * 一次可产生 blockSize 指定的或者根据 blockSize 计算而得的数量的 id，
 * 效率高。
 * 
 * 这个类在运行期改变 blockSize 属性时不影响数据库中已经生成的 id。
 * 且 flexibleBlockSizeEnabled 属性决定了使用不使用原始的 blockSize
 * 的值。
 * 
 * 
 * 该类不需要事务处理。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class BlockableSequenceWrapper implements Sequence {
	private static final Log log = LogFactory.getLog(BlockableSequenceWrapper.class);
    
    private final RetrievalHelper blockRetrieval = new RetrievalHelper();

    private final Blockable blockable;
    private boolean flexibleBlockSizeEnabled = true;
    private String name;
    private final int blockSize;
    
    
    //runtime
    private final AtomicLong currentID = new AtomicLong(-1L);
    private long maxID = -2L;

    /**
     * 
     * @param blockable
     * @param name
     * @param blockSize
     * @param flexibleBlockSizeEnabled
     */
    BlockableSequenceWrapper(Blockable blockable, String name, int blockSize, boolean flexibleBlockSizeEnabled) {
        this.blockSize = AppsGlobals.getProperty("apps.sequence." + name + ".blocksize", blockSize);
        this.flexibleBlockSizeEnabled = flexibleBlockSizeEnabled;
        this.blockable = blockable;
        this.name = name;
    }




    /**
     * We're shooting for a maximum of one block retrieval per second so as to not overwhelm the db and have threads
     * start to backup on retrieving id blocks
     *
     * @return the blockSize
     */
    private int determineBlockSize() {
        if (!flexibleBlockSizeEnabled) {
            return blockSize;
        }

        blockRetrieval.updateTimeStamp();
        int retrievalsPerSecond = (int) (1000 / blockRetrieval.getDifference());
        int maxBlockSize = 500;

        if (retrievalsPerSecond > 1 && !(blockSize * blockRetrieval.getMultiplier() > maxBlockSize)) {
            blockRetrieval.incrementMultiplier();

            // double up if we're retrieving a ton every second
            if (retrievalsPerSecond > 100) {
                blockRetrieval.incrementMultiplier();
            }
        }
        else if (retrievalsPerSecond < 1) {
            blockRetrieval.decrementMultiplier();
        }

        // no more then 500 at a time
        return Math.min(blockSize * blockRetrieval.getMultiplier(), maxBlockSize);
    }

   



    static class RetrievalHelper {
        long previousStamp = System.currentTimeMillis();
        long currentStamp = previousStamp;
        private int multiplier = 1;

        void updateTimeStamp() {
            previousStamp = currentStamp;
            currentStamp = System.currentTimeMillis();
        }

        long getDifference() {
            long diff = currentStamp - previousStamp;
            return diff > 0 ? diff : 1;
        }

        int getMultiplier() {
            return multiplier;
        }

        void incrementMultiplier() {
            multiplier++;
        }

        void decrementMultiplier() {
            if (multiplier > 1) {
                multiplier--;
            }
        }
    }

	public Long getCurrent() {
		long currentIDVal = currentID.longValue();
		if(currentIDVal <= 0L){
			throw new IllegalStateException("请先调用 getNext()");
		}
		return currentIDVal;
	}

//	public Long getNext2() {
//        long currentIdVal = currentID.longValue();
//        if (!(currentIdVal <= maxID) || currentIdVal == 0) {
//        	int rateDeterminedBlockSize = determineBlockSize();
//        	log.debug("BlockSise: " + rateDeterminedBlockSize);
//        	
//        	maxID = blockable.getNext(rateDeterminedBlockSize);
//            currentID.set(maxID - rateDeterminedBlockSize + 1);
//        }
//        
//        
//        long id =  currentID.getAndIncrement();
//        if(log.isDebugEnabled()){
//        	String s = String.format("获取 Sequence '%s' 的值: %s", name, id);
//        	log.debug(s);
//        }
//        
//        return id;
//	}
	
	public Long getNext() {
		long currentIDVal = currentID.longValue();
		if (currentIDVal > maxID) {
			int rateDeterminedBlockSize = determineBlockSize();
			if(log.isDebugEnabled()){
				log.debug("Sequence '" + name + "' maxID id " + maxID + " was consumed. acquiring new block, size is " + rateDeterminedBlockSize);
			}
			
        	maxID = blockable.getNext(rateDeterminedBlockSize);
            currentID.set(maxID - rateDeterminedBlockSize + 1);
		}
		long id = currentID.getAndIncrement();
//		if (log.isDebugEnabled()) {
//			String s = String.format("获取 Sequence '%s' 的值: %s", name, id);
//			log.debug(s);
//		}
		return id;
	}
}
