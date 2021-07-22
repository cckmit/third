/*
 * $Id: BaseTaskBean.java 6390 2014-04-17 02:13:46Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.bean;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Alex Lin(lcql@msn.com)
 *
 */
public class BaseTaskBean extends VersionableBean{
	private static final long serialVersionUID = -1951581805415568562L;
	public static final byte STATUS_READY = 0;
	public static final byte STATUS_RUNNING = 1;
	public static final byte STATUS_EXECUTED = 9;
	public static final byte STATUS_FAILED = 12;
	private String baseTask;
	private String paramType;
	private String paramValue;
	private Date executeTime;
	private int executeNumber = 0;
	private Date scheduleExecuteTime;
	private String result;
	private byte[] taskInstance;
	
	/**
	 * @return the executeNumber
	 */
	public int getExecuteNumber() {
		return executeNumber;
	}

	/**
	 * @param executeNumber the executeNumber to set
	 */
	public void setExecuteNumber(int executeNumber) {
		this.executeNumber = executeNumber;
	}

	/**
	 * @return the executeTime
	 */
	public Date getExecuteTime() {
		return executeTime;
	}

	/**
	 * @param executeTime the executeTime to set
	 */
	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	/**
	 * @return the baseTask
	 */
	public String getBaseTask() {
		return baseTask;
	}

	/**
	 * @param baseTask the baseTask to set
	 */
	public void setBaseTask(String baseTask) {
		this.baseTask = baseTask;
	}

	/**
	 * @return the paramType
	 */
	public String getParamType() {
		return paramType;
	}

	/**
	 * @param paramType the paramType to set
	 */
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	/**
	 * @return the paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * @param paramValue the paramValue to set
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}


	
	/**
	 * @return the scheduleExecuteTime
	 */
	public Date getScheduleExecuteTime() {
		return scheduleExecuteTime;
	}

	/**
	 * @param scheduleExecuteTime the scheduleExecuteTime to set
	 */
	public void setScheduleExecuteTime(Date scheduleExecuteTime) {
		this.scheduleExecuteTime = scheduleExecuteTime;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the taskInstance
	 */
	public byte[] getTaskInstance() {
		return taskInstance;
	}

	/**
	 * @param taskInstance the taskInstance to set
	 */
	public void setTaskInstance(byte[] taskInstance) {
		this.taskInstance = taskInstance;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException{
		
		ObjectMapper objectMapper = new ObjectMapper();
		String string = objectMapper.writeValueAsString(new Date());
		System.out.println(string);
		
		
		Bean bean = new Bean();
		bean.setAge(29);
		bean.setDate(new Date());
		bean.setName("Nameeeeeeee中文");
		
		String beanStr = objectMapper.writeValueAsString(bean);
		System.out.println(beanStr);
		
		Bean bean2 = objectMapper.readValue(beanStr, Bean.class);
		System.out.println(bean2.getAge());
		System.out.println(bean2.getName());
		System.out.println(bean2.getDate());

		
		String str2 = objectMapper.writeValueAsString(new Date());
		System.out.println(str2);
		Date value2 = objectMapper.readValue(str2, Date.class);
		System.out.println(value2);
		
		String str3 = objectMapper.writeValueAsString(98339L);
		System.out.println(str3);
		System.out.println(objectMapper.readValue(str3, Long.class));
		
		System.out.println(Bean.class.getName());
		
		
//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		ObjectOutputStream os = new ObjectOutputStream(stream);
//		os.writeObject(new Date());
//		
//		byte[] array = stream.toByteArray();
//		
//		
//		ByteArrayInputStream is2 = new ByteArrayInputStream(array);
//		ObjectInputStream objectInputStream = new ObjectInputStream(is2);
//	
//		
//		Object object = SerializationUtils.deserialize(array);
//		System.out.println(object);
//		
//		String json = org.opoo.apps.util.SerializableUtils.toJSON(new Date());
//		System.out.println(json);
	}

	public static class Bean{
		private String name;
		private int age;
		private Date date;
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the age
		 */
		public int getAge() {
			return age;
		}
		/**
		 * @param age the age to set
		 */
		public void setAge(int age) {
			this.age = age;
		}
		/**
		 * @return the date
		 */
		public Date getDate() {
			return date;
		}
		/**
		 * @param date the date to set
		 */
		public void setDate(Date date) {
			this.date = date;
		}
	}
}
