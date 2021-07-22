package org.opoo.apps.id.sequence;

import java.io.Serializable;

import org.opoo.apps.id.IdGenerator;
import org.opoo.util.Assert;


/**
 * Sequence 提供者抽象类。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class AbstractSequenceProvider implements SequenceProvider {
	public <K extends Serializable> IdGenerator<Long> getIdGenerator(K key) {
		Assert.isInstanceOf(String.class, key);
		return getSequence((String) key);
	}
}
