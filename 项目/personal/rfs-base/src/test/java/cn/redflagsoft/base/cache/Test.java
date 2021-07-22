/*
 * $Id$
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.cache;

import java.awt.Color;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class Test {

	///////////////////////////////////////////////
	// 以下是业务层 x.service.*，在应用中主要使用的是这3个接口
	////////////////////////////////////////////
	
	interface Vehicle{
		long getId();
		Color getColor();
		String getName();
		
		List<Wheel> getWheels();
		void addWheel(Wheel w);
	}
	
	interface Wheel{
		long getId();
		Vehicle getOwner();
	}
	
	interface VehicleManager{
		
		List<Vehicle> findVehicles();
		
		Vehicle getVehicle(long id);
		
		Vehicle createVehicle(long id, String name, Color color);
		
		Wheel getWheel(long id);
	}
	
	
	///////////////////////////////////////////////
	// 全局上下文静态类
	////////////////////////////////////////////
	static class Context{
		static Map<Long, Wheel> getWheelCache(){return null;};
		static Map<Long, Vehicle> getVehicleCache(){return null;};
		static VehicleManagerImpl getVehicleManagerImpl(){return null;};
	}
	
	
	///////////////////////////////////////////////
	// 以下是业务层实现类 x.service.impl.* 封装在下层，对上层透明
	////////////////////////////////////////////
	
	class VehicleManagerImpl implements VehicleManager{
		public List<Vehicle> findVehicles() {
			final List<Long> ids = null;// vehicleDao.findIds();
			return new AbstractList<Test.Vehicle>() {
				public Vehicle get(int index) {
					return getVehicle(ids.get(index));
				}
				public int size() {
					return ids.size();
				}
			};
		}

		public Vehicle getVehicle(long id) {
			Vehicle v = Context.getVehicleCache().get(id);
			if(v == null){
				VehicleBean bean = getVehicleBean(id);
				if(bean != null){
					if(bean.type == 1/*car*/){
						v = new Car(bean);
					}else{
						//...
					}
				}
				if(v != null){
					Context.getVehicleCache().put(v.getId(), v);
				}
			}
			return v;
		}

		public Vehicle createVehicle(long id, String name, Color color) {
			VehicleBean bean = new VehicleBean();
			//..设置值，调用dao保存，然后更新缓存 save(bean);
			Vehicle v = getVehicle(id);//
			return v;
		}

		public Wheel getWheel(long id) {
			Wheel w = Context.getWheelCache().get(id);
			if(w == null){
				WheelBean bean = getWheelBean(id);
				if(bean != null){
					w = new WheelImpl(bean);
					Context.getWheelCache().put(w.getId(), w);
				}
			}
			return w;
		}

		public void addWheel(Vehicle v, Wheel w) {
			//转换w -> WheelBean,保存WheelBean到数据库
			//更新缓存
			Context.getWheelCache().put(w.getId(), w);
		}
		
		List<Long> findWheelIdsByVehicleId(long vehicleId){
			return new ArrayList<Long>();
		}
		
		VehicleBean getVehicleBean(Long id){
			//dao.get(id);
			return null;
		}
		WheelBean getWheelBean(long id) {
			//dao.get(id);
			return null;
		}
	}
	
	//数据库存储结构：例如hibernate的映射类或者通过sql直接查询组装的bean
	class VehicleBean{
		long id;
		String name;
		int rgbColor = -1;
		int type;
	}
	class WheelBean{
		long id;
		long ownerId;
	}
	
	//实现类 小车
	class Car implements Vehicle, Serializable{
		//以下全是可以序列化的
		private long id;
		private String name;
		private int rgbColor = -1;
		private List<Long> wheelIds;
		
		public Car(VehicleBean bean){
			this.id = bean.id;
			this.name = bean.name;
			this.rgbColor = bean.rgbColor;
		}
		
		public long getId() {
			return id;
		}

		public Color getColor() {
			if(rgbColor == -1){
				return null;
			}
			return new Color(rgbColor);
		}

		public String getName() {
			return name;
		}

		public List<Wheel> getWheels() {
			if(wheelIds == null){
				wheelIds = Context.getVehicleManagerImpl().findWheelIdsByVehicleId(id);
			}
			return new AbstractList<Wheel>(){
				@Override
				public Wheel get(int index) {
					Long wheelId = wheelIds.get(index);
					return Context.getVehicleManagerImpl().getWheel(wheelId);
				}
				@Override
				public int size() {
					return wheelIds.size();
				}
			};
		}

		public void addWheel(Wheel w) {
			if(wheelIds == null){
				wheelIds = Context.getVehicleManagerImpl().findWheelIdsByVehicleId(id);
			}
			Context.getVehicleManagerImpl().addWheel(this, w);
			wheelIds.add(w.getId());
		}
	}
	
//	//实现类 卡车
//	class Truck implements Vehicle, Serializable{
//		
//	}
	
	//车轮 实现类
	class WheelImpl implements Wheel,Serializable{
		//以下全是可以序列化的
		private long id;
		private long ownerId = -1;
		
		public WheelImpl(WheelBean bean){
			this.id = bean.id;
			this.ownerId = bean.ownerId;
		}
		
		public long getId() {
			return id;
		}

		public Vehicle getOwner() {
			if(ownerId == -1){
				return null;
			}else{
				return Context.getVehicleManagerImpl().getVehicle(ownerId);
			}
		}
	}
}
