/*
 * $Id: CacheTest.java 5299 2011-12-29 08:17:38Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.cache;

import java.util.Date;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.junit.Before;
import org.junit.Test;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.Invocable;
import com.tangosol.net.InvocationService;
import com.tangosol.net.Member;
import com.tangosol.net.NamedCache;
import com.tangosol.util.MapEvent;
import com.tangosol.util.MapListener;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class CacheTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		NamedCache cache = CacheFactory.getCache("clerkCache");
		Object object = cache.get("a");
		System.out.println(object);
		cache.addMapListener(new MapListener() {
			public void entryUpdated(MapEvent arg0) {
				System.out.println("entryUpdated" + arg0);
			}
			
			public void entryInserted(MapEvent arg0) {
				System.out.println("entryInserted" + arg0);
			}
			
			public void entryDeleted(MapEvent arg0) {
				System.out.println("entryDeleted" + arg0);
			}
		});
		
		cache.put("a", "aaaaaaaaaaaa");
		System.out.println(cache);
		object = cache.get("a");
		System.out.println(object);
		
		InvocationService service = (InvocationService) CacheFactory.getService("Apps Cluster Service");
		System.out.println(service);
		Member member = service.getInfo().getOldestMember();
		System.out.println(member);
		
		Set<?> members = service.getInfo().getServiceMembers();
		
		service.execute(new Invocable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void run() {
				System.out.println(new Date());
			}
			
			public void init(InvocationService arg0) {
				// TODO Auto-generated method stub
			}
			
			public Object getResult() {
				// TODO Auto-generated method stub
				return null;
			}
		}, members, null);
	}
	
	@Test
	public void testEHCache(){
		Cache cache = CacheManager.getInstance().getCache("clerkCache");
		System.out.println(cache);
	}
	
}
