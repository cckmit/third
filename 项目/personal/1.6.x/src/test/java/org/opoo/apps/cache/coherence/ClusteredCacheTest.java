package org.opoo.apps.cache.coherence;

import java.security.cert.CertificateFactory;

import org.junit.Test;

import com.tangosol.net.BackingMapManager;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheService;
import com.tangosol.net.NamedCache;


public class ClusteredCacheTest {
	
	public void testCache() throws Exception{
		NamedCache cache = CacheFactory.getCache("abcd");
		System.out.println(cache);
		
		CacheService service = cache.getCacheService();
		System.out.println(service);
		
		BackingMapManager manager = service.getBackingMapManager();
		System.out.println(manager);
		

	}
	
	@Test
	public void testCert() throws Exception{
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
        //Certificate cert = factory.generateCertificate(streamCert);
		System.out.println(factory);
	}
}
