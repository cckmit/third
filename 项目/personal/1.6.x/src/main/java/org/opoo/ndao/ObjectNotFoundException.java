package org.opoo.ndao;

import java.io.Serializable;

public class ObjectNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5959948418970631513L;
	
	/**
	 * �����쳣ʵ����
	 * @param id �����ʶ
	 * @param clazz ������
	 */
	public ObjectNotFoundException(Serializable id, String clazz){
		this("���󲻴��� " + clazz + "��" + id);
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
