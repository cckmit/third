package org.opoo.ndao.hibernate3.usertype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;


/**
 * 当数据存储的是枚举的name时，使用这个自定义类型。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 * @param <E>
 */
public class EnumUserType<E extends Enum<E>> extends AbstractEnumUserType<E> {
	public static final int[] SQL_TYPES = {Types.VARCHAR};
	
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		 String value = rs.getString(names[0]);      
		 Object result = null;        
		 if(!rs.wasNull())        
			 result = Enum.valueOf(returnedClass(), value);
		 return result;
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		if(null == value)         
			st.setNull(index, Types.VARCHAR);  
		else          
			st.setString(index, ((Enum<?>)value).name());
	}

	public int[] sqlTypes() {
		return SQL_TYPES;
	}

}
