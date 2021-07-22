package org.opoo.apps.id;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用时间戳为基础的的ID生成器提供者。
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class TimestampIdGeneratorProvider implements IdGeneratorProvider<Long> {
	private Map<Serializable, IdGenerator<Long>> gens = new HashMap<Serializable, IdGenerator<Long>>();
	public <K extends Serializable> IdGenerator<Long> getIdGenerator(K key) {
		IdGenerator<Long> gen = gens.get(key);
		if(gen == null){
			gen = new TimestampIdGenerator();
			gens.put(key, gen);
		}
		return gen;
	}
}
