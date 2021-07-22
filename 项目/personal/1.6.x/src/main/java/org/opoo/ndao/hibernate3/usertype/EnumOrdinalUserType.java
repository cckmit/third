package org.opoo.ndao.hibernate3.usertype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;


/**
 * 当存储的数据是枚举的Ordinal时，使用这个自定义类型。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 * @param <E>
 */
public class EnumOrdinalUserType<E extends Enum<E>> extends AbstractEnumUserType<E> {
	
	private boolean userFirstAsDefault = false;
	public static final int[] SQL_TYPES = {Types.INTEGER};
	
	public EnumOrdinalUserType(){
		super();
	}
	
	public EnumOrdinalUserType(boolean userFirstAsDefault){
		super();
		this.userFirstAsDefault = userFirstAsDefault;
	}
	
	private E first(){
		return returnedClass().getEnumConstants()[0];
	}
	
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		int value = rs.getInt(names[0]);  
		try{
			return EnumOrdinalUserType.valueOf(returnedClass(), value);
		}catch(Exception e){
			if(userFirstAsDefault){
				return first();
			}
			throw new HibernateException(e);
		}
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		if(null == value){
			if(userFirstAsDefault){
				st.setInt(index, first().ordinal());
			}else{
				st.setNull(index, Types.INTEGER);
			}
		}else{
			st.setInt(index, ((Enum<?>)value).ordinal());
		}
	}

	public int[] sqlTypes() {
		return SQL_TYPES;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param <T>
	 * @param enumType
	 * @param ordinal
	 * @return
	 */
	public static <T extends Enum<T>> T valueOf(Class<T> enumType, int ordinal) {
		T[] values = enumType.getEnumConstants();
		for(T t: values){
			if(t.ordinal() == ordinal){
				return t;
			}
		}
		throw new IllegalArgumentException("Specified ordinal does not relate to a valid enum in " + enumType + "[" + ordinal + "]");
	}

}
