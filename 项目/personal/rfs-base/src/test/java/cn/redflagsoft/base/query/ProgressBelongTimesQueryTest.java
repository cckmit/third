/*
 * $Id: ProgressBelongTimesQueryTest.java 6183 2013-01-30 02:05:47Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.query;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.opoo.apps.Labelable;
import org.opoo.ndao.support.ResultFilter;

import com.google.common.collect.Maps;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class ProgressBelongTimesQueryTest {

	@Test
	public void test() {
		ProgressBelongTimesQuery query = new ProgressBelongTimesQuery();
		List<Labelable> list = query.findProgressBelongTimes(null);
		for (Labelable labelable : list) {
			System.out.println(labelable.getLabel() + " : " + labelable.getData());
		}
		
		assertEquals(13, list.size());
	}
	
	@Test
	public void testStartTime(){
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		Map<String,String> map = Maps.newHashMap();
		map.put("startTime", "2009-01-01");
		filter.setRawParameters(map);
		
		ProgressBelongTimesQuery query = new ProgressBelongTimesQuery();
		List<Labelable> list = query.findProgressBelongTimes(filter);
		for (Labelable labelable : list) {
			System.out.println(labelable.getLabel() + " : " + labelable.getData());
		}
		
		Labelable labelable = list.get(list.size() - 1);
		assertEquals("2009年01月", labelable.getLabel());
	}
	
	@Test
	public void testEndTime(){
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		Map<String,String> map = Maps.newHashMap();
		map.put("endTime", "2009-01-01");
		filter.setRawParameters(map);
		
		ProgressBelongTimesQuery query = new ProgressBelongTimesQuery();
		List<Labelable> list = query.findProgressBelongTimes(filter);
		for (Labelable labelable : list) {
			System.out.println(labelable.getLabel() + " : " + labelable.getData());
		}
		
		Labelable labelable = list.get(0);
		assertEquals("2009年01月", labelable.getLabel());
	}
	
	@Test
	public void testStartTimeAndEndTime(){
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		Map<String,String> map = Maps.newHashMap();
		map.put("endTime", "2009-01-01");
		map.put("startTime", "2001-01-02");
		filter.setRawParameters(map);
		
		ProgressBelongTimesQuery query = new ProgressBelongTimesQuery();
		List<Labelable> list = query.findProgressBelongTimes(filter);
		for (Labelable labelable : list) {
			System.out.println(labelable.getLabel() + " : " + labelable.getData());
		}
		
		Labelable labelable = list.get(0);
		assertEquals("2009年01月", labelable.getLabel());
		Labelable l2 = list.get(list.size() - 1);
		assertEquals("2001年02月", l2.getLabel());
	}
	
	@Test
	public void testCount(){
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		Map<String,String> map = Maps.newHashMap();
		map.put("count", "23");
		filter.setRawParameters(map);
		
		ProgressBelongTimesQuery query = new ProgressBelongTimesQuery();
		List<Labelable> list = query.findProgressBelongTimes(filter);
		for (Labelable labelable : list) {
			System.out.println(labelable.getLabel() + " : " + labelable.getData());
		}
		
		assertEquals(23, list.size());
	}
	
	@Test
	public void testStartTimeAndEndTimeAndCount(){
		ResultFilter filter = ResultFilter.createEmptyResultFilter();
		Map<String,String> map = Maps.newHashMap();
		map.put("endTime", "2013-01-01");
		map.put("startTime", "2012-10-02");
		filter.setRawParameters(map);
		
		ProgressBelongTimesQuery query = new ProgressBelongTimesQuery();
		List<Labelable> list = query.findProgressBelongTimes(filter);
		for (Labelable labelable : list) {
			System.out.println(labelable.getLabel() + " : " + labelable.getData());
		}
		
		Labelable labelable = list.get(0);
		assertEquals("2013年01月", labelable.getLabel());
		assertEquals(13, list.size());
	}
}
