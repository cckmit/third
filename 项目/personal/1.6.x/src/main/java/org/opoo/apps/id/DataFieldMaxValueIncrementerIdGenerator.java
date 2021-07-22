package org.opoo.apps.id;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;


/**
 * ʹ�� Spring �� DataFieldMaxValueIncrementer ʵ�ֵ� ID ��������
 * @author Alex
 *
 * @param <T>
 */
public class DataFieldMaxValueIncrementerIdGenerator<T extends Serializable> implements	IdGenerator<T> {
	
	private final DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer;
	private boolean isStringId = false;
	private boolean isIntId = false;
	private boolean isNumberId = false;
	
	
	@SuppressWarnings("unchecked")
	public DataFieldMaxValueIncrementerIdGenerator(DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer){
		this.dataFieldMaxValueIncrementer = dataFieldMaxValueIncrementer;
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
		Class<T> clazz = (Class<T>) params[0];
		
		//�����⼸�����ԣ����Ч�ʡ�
        isStringId = String.class.isAssignableFrom(clazz);
        if(!isStringId) isIntId = Integer.class.isAssignableFrom(clazz);
        if(!isIntId) isNumberId = Number.class.isAssignableFrom(clazz);
	}
	public T getCurrent() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	public T getNext() {
		if(isStringId) return (T) dataFieldMaxValueIncrementer.nextStringValue();
		if(isIntId) return (T) (Integer) dataFieldMaxValueIncrementer.nextIntValue();
		if(isNumberId) return (T) (Number) dataFieldMaxValueIncrementer.nextLongValue();
		return null;
	}
}
