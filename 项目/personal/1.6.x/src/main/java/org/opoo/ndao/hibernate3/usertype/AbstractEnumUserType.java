package org.opoo.ndao.hibernate3.usertype;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;


/**
 * 抽象的枚举类自定义Hibernate类型。
 * @author Alex Lin(alex@opoo.org)
 *
 * @param <E>
 */
public abstract class AbstractEnumUserType<E extends Enum<E>> implements UserType {

	private Class<E> clazz = null;
	
//	protected void setRturnedClass(Class<E> cls){
//		clazz = cls;
//	}
	
	/**
	 * 构建实例。
	 * 
	 * 从泛型的实现类中找到枚举类的具体类型。
	 */
	@SuppressWarnings("unchecked")
	public AbstractEnumUserType(){
        Type genType = getClass().getGenericSuperclass();
        if(genType != null && genType instanceof ParameterizedType){
        	Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        	if(params.length >= 1){
        		clazz = params[0] instanceof Class ? (Class<E>) params[0] : null;
        		return;
        	}
        }
        
        throw new IllegalArgumentException("无法创建枚举映射类型，找不到枚举类。");
	}
	
	
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		if(x == y){
			return true;
		}
		if(x == null || y == null){
			return false;
		}
		return x.equals(y);
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	public boolean isMutable() {
		return false;
	}

	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

	public Class<E> returnedClass() {
		return clazz;
	}
}
