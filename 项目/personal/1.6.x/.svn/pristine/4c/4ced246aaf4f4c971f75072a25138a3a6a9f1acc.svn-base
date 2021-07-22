package org.opoo.apps.id.sequence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HibernateSequenceProvider extends AbstractSequenceProvider implements SequenceProvider{
	public static final String HIBERNATE_SEQUENCE_BLOCK_SIZE = "SequenceProvider.hibernate.blockSize";
	public static final String HIBERNATE_SEQUENCE_FLEXIBLE_BLOCK_SIZE_ENABLED = "SequenceProvider.hibernate.flexibleBlockSizeEnabled";
	public static final String HIBERNATE_SEQUENCE_MAX_ATTEMPTS = "SequenceProvider.hibernate.maxAttempts";
	
	private Map<String, Sequence> sequences = new ConcurrentHashMap<String, Sequence>();
	
	public Sequence getSequence(String key) {
		Sequence sequence = sequences.get(key);
		if(sequence == null){
			sequence = createSequence(key);
			sequences.put(key, sequence);
		}
		return sequence;
	}

	private Sequence createSequence(String key) {
		HibernateTemplate hibernateTemplate = Application.getContext().get("hibernateTemplate", HibernateTemplate.class);
		int blockSize = AppsGlobals.getProperty(HIBERNATE_SEQUENCE_BLOCK_SIZE, 5);
		boolean flexibleBlockSizeEnabled = AppsGlobals.getProperty(HIBERNATE_SEQUENCE_FLEXIBLE_BLOCK_SIZE_ENABLED, true);
		int maxAttempts = AppsGlobals.getProperty(HIBERNATE_SEQUENCE_MAX_ATTEMPTS, 3);
		if(flexibleBlockSizeEnabled){
			HibernateBlockable blockable = new HibernateBlockable(hibernateTemplate, key);
			return new BlockableSequenceWrapper(blockable,key, blockSize, true);
		}else{
			return new HibernateSequence(hibernateTemplate, key, blockSize, maxAttempts);
		}
	}
}
