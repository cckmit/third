package org.opoo.ndao;

import java.io.Serializable;

public class ObjectNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5959948418970631513L;
	
	/**
	 * 构建异常实例。
	 * @param id 对象标识
	 * @param clazz 对象类
	 */
	public ObjectNotFoundException(Serializable id, String clazz){
		this("对象不存在 " + clazz + "：" + id);
	}

	public ObjectNotFoundException() {
	}

	public ObjectNotFoundException(String message) {
		super(message);
	}

	public ObjectNotFoundException(Throwable cause) {
		super(cause);
	}

	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
