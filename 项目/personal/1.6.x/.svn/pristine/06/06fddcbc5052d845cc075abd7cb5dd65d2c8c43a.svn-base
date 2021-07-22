package org.opoo.apps.cache.coherence;

import org.junit.Test;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.Cluster;
import com.tangosol.net.Member;
import com.tangosol.net.NamedCache;
import com.tangosol.net.Service;


public class CoherenceCacheTest {

	@Test
	public void testCache0(){
		NamedCache cache = CacheFactory.getCache("abcd");
		Object object = cache.get("a");
		System.out.println(object);
		cache.put("a", "aaa");
		object = cache.get("a");
		System.out.println(object);
		Service service = CacheFactory.getService("Apps Cluster Service");
		System.out.println(service);
		
		Cluster cluster = CacheFactory.ensureCluster();
		Member localMember = cluster.getLocalMember();
		Member member = service.getInfo().getOldestMember();
		
		System.out.println(localMember == member);
		
	}
}
