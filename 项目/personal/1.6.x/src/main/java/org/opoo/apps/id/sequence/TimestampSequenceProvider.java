package org.opoo.apps.id.sequence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.opoo.apps.id.TimestampIdGenerator;

public class TimestampSequenceProvider extends AbstractSequenceProvider implements SequenceProvider {
	private Map<String, Sequence> gens = new ConcurrentHashMap<String, Sequence>();
	public Sequence getSequence(String key) {
		Sequence gen = gens.get(key);
		if(gen == null){
			gen = new TimestampSequence();
			gens.put(key, gen);
		}
		return gen;
	}

	public static class TimestampSequence extends TimestampIdGenerator implements Sequence{
	}
	
	public static void main(String[] args){
		TimestampSequence gen = new TimestampSequence();
		System.out.println(gen.getNext());
		System.out.println(gen.getNext());
		System.out.println(gen.getNext());
	}
}
