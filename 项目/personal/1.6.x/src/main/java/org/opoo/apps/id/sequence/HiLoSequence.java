package org.opoo.apps.id.sequence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>高低位 Sequence。
 * <p>使用一个已有的 Sequence 作为当前 Sequence 的高位产生器，根据指定的
 * 最大低位值 maxLo，一次可以产生指定数量的 id，效率高。
 * 
 * <p>这个类在运行期改变 maxLo 大小时会影响已经产生的 id，为了避免生成重复的
 * id，通常 maxLo 设置后不再修改，或者只能改大，不能改小。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class HiLoSequence implements Sequence {
	private static final Log log = LogFactory.getLog(HiLoSequence.class);
	private Sequence sequence;
	private long hi;
	private int lo;
	private int maxLo = Byte.MAX_VALUE;
	
	public HiLoSequence(Sequence sequence){
		this(sequence, Short.MAX_VALUE);
	}
	public HiLoSequence(Sequence sequence, int maxLo){
		this.sequence = sequence;
		this.maxLo = maxLo;
		this.lo = maxLo + 1;
	}

	public Long getCurrent() {
		throw new UnsupportedOperationException();
	}

	public Long getNext() {
		if (lo > maxLo) {
			long val = sequence.getNext();
			lo = (val == 1 ? 1 : 0);
			hi = (val - 1) * (maxLo + 1);
			if(log.isDebugEnabled()){
				log.debug("new hi value: " + (val - 1));
			}
		}
		return hi + (lo ++);
	}
}
