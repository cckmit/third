package cn.redflagsoft.base.sequence;

import org.opoo.apps.id.sequence.DefaultSequenceProvider;
import org.opoo.apps.id.sequence.Sequence;
import org.opoo.apps.id.sequence.SequenceType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 系统 ID 生成器提供者。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class BaseSequenceProvider extends DefaultSequenceProvider {

	public BaseSequenceProvider(){
		super();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	protected Sequence createNewSequence(SequenceType sequenceType, String key) {
		return super.createNewSequence(sequenceType, key);
	}
}
