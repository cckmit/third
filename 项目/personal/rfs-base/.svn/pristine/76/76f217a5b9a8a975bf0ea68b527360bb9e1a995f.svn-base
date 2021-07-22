package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.cache.Cache;
import org.opoo.cache.NullCache;

import cn.redflagsoft.base.bean.MatterAffair;
import cn.redflagsoft.base.dao.MatterAffairDao;
import cn.redflagsoft.base.service.MatterAffairService;

import com.google.common.collect.Lists;

public class MatterAffairServiceImpl implements MatterAffairService {
	private static final Log log = LogFactory.getLog(MatterAffairServiceImpl.class);
	private MatterAffairDao matterAffairDao;
	private Cache<String,List<Long>> matterAffairListCache = new NullCache<String, List<Long>>();
	private Cache<Long, MatterAffair> matterAffairCache = new NullCache<Long, MatterAffair>();

	
	/**
	 * @return the matterAffairCache
	 */
	public Cache<Long, MatterAffair> getMatterAffairCache() {
		return matterAffairCache;
	}

	/**
	 * @param matterAffairCache the matterAffairCache to set
	 */
	public void setMatterAffairCache(Cache<Long, MatterAffair> matterAffairCache) {
		this.matterAffairCache = matterAffairCache;
	}

	/**
	 * @return the matterAffairListCache
	 */
	public Cache<String, List<Long>> getMatterAffairListCache() {
		return matterAffairListCache;
	}

	/**
	 * @param matterAffairListCache the matterAffairListCache to set
	 */
	public void setMatterAffairListCache(
			Cache<String, List<Long>> matterAffairListCache) {
		this.matterAffairListCache = matterAffairListCache;
	}

	public MatterAffairDao getMatterAffairDao() {
		return matterAffairDao;
	}

	public void setMatterAffairDao(MatterAffairDao matterAffairDao) {
		this.matterAffairDao = matterAffairDao;
	}

//	public List<MatterAffair> findAffairs(byte category, Long bizId,
//			byte bizAction) {
//		return findAffairs(category,bizId,bizAction,(short)0); 
//	}
	
	public List<MatterAffair> findAffairs(byte category, Long bizId, byte bizAction,short tag) {
		//return matterAffairDao.findAffairs(category, bizId, bizAction,tag);
		
		String key = buildCacheKey(category, bizId, -1, bizAction, tag);
		List<MatterAffair> list = getMatterAffairsFromCache(key);
		if(list == null){
			list = matterAffairDao.findAffairs(category, bizId, bizAction,tag);
			if(list != null){
				putMatterAffairsIntoCache(key, list);
			}
		}else{
			log.debug("Load matteraffairs from cache: " + key);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see cn.redflagsoft.base.service.MatterAffairService#findAffairs(byte, java.lang.Long, short, java.lang.Byte, short)
	 */
	public List<MatterAffair> findAffairs(byte bizCategory, Long bizId,	int bizType, Byte bizAction, short tag) {
		//return matterAffairDao.findAffairs(bizCategory, bizId, bizType, bizAction, tag);
		
		String key = buildCacheKey(bizCategory, bizId, bizType, bizAction, tag);
		List<MatterAffair> list = getMatterAffairsFromCache(key);
		if(list == null){
			list = matterAffairDao.findAffairs(bizCategory, bizId, bizType, bizAction, tag);
			if(list != null){
				putMatterAffairsIntoCache(key, list);
			}
		}else{
			log.debug("Load matteraffairs from cache: " + key);
		}
		return list;
	}
	
	private String buildCacheKey(byte bizCategory, Long bizId,	int bizType, Byte bizAction, short tag){
		return String.format("cat%s_bizId%s_bizType%s_bizAction%s_tag%s", bizCategory, bizId, bizType, bizAction, tag);
	}
	
	private List<MatterAffair> getMatterAffairsFromCache(String key){
		List<Long> list = matterAffairListCache.get(key);
		if(list == null){
			return null;
		}
		List<MatterAffair> mas = Lists.newArrayList();
		for(Long sn: list){
			mas.add(getMatterAffair(sn));
		}
		return mas;
	}
	
	private void putMatterAffairsIntoCache(String key, List<MatterAffair> list){
		List<Long> ids = Lists.newArrayList();
		for(MatterAffair ma:list){
			ids.add(ma.getSn());
			matterAffairCache.put(ma.getSn(), ma);
		}
		matterAffairListCache.put(key, ids);
	}
	
	public MatterAffair getMatterAffair(long sn){
		MatterAffair matterAffair = matterAffairCache.get(sn);
		if(matterAffair == null){
			matterAffair = matterAffairDao.get(sn);
			if(matterAffair != null){
				matterAffairCache.put(sn, matterAffair);
			}
		}
		return matterAffair;
	}
}
