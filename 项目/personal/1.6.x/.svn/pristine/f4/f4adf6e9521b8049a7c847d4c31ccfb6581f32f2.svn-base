package org.opoo.apps.id.sequence;

import org.opoo.apps.id.IdGeneratorProvider;


/**
 * Sequence 提供者接口。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface SequenceProvider extends IdGeneratorProvider<Long> {
	/*
	 public <K extends Serializable> IdGenerator<Long> getIdGenerator(K key) {
		Assert.isInstanceOf(String.class, key);
		return getSequence((String) key);
	}*/
	Sequence getSequence(String key);
}
