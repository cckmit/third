package org.opoo.apps.id;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 可缓存的 ID 生成器提供者。
 * @author Alex Lin(alex@opoo.org)
 * @deprecated 不适合集群，标记为过期
 */
public abstract class AbstractCachedIdGeneratorProvider implements IdGeneratorProvider<Long> {
	private Map<Serializable, IdGenerator<Long>> generators = new HashMap<Serializable, IdGenerator<Long>>();
	
	protected abstract <K extends Serializable> IdGenerator<Long> createIdGenerator(K key);
	protected abstract <K extends Serializable> boolean supportsKey(K key);
	
	public final <K extends Serializable> IdGenerator<Long> getIdGenerator(K key) {
		IdGenerator<Long> gen = generators.get(key);
		if(gen == null){
			if(supportsKey(key)){
				gen = createIdGenerator(key);
				if(gen != null){
					generators.put(key, gen);
				}
			}else{
				throw new IllegalArgumentException("unsupport key type: " + key.getClass());
			}
		}
		return gen;
	}
}

