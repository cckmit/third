package org.opoo.apps.id.sequence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>�ߵ�λ Sequence��
 * <p>ʹ��һ�����е� Sequence ��Ϊ��ǰ Sequence �ĸ�λ������������ָ����
 * ����λֵ maxLo��һ�ο��Բ���ָ�������� id��Ч�ʸߡ�
 * 
 * <p>������������ڸı� maxLo ��Сʱ��Ӱ���Ѿ������� id��Ϊ�˱��������ظ���
 * id��ͨ�� maxLo ���ú����޸ģ�����ֻ�ܸĴ󣬲��ܸ�С��
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
