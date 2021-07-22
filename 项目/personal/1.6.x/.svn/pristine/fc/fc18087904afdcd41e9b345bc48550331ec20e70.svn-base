package org.opoo.apps.security.dao.hibernate3;

import org.hibernate.usertype.UserType;
import org.opoo.apps.security.User.OnlineStatus;
import org.opoo.ndao.hibernate3.usertype.EnumOrdinalUserType;

public class UserOnlineStatusType extends EnumOrdinalUserType<OnlineStatus> implements UserType {

	public UserOnlineStatusType() {
		super(true);
	}
	
	
	
//	
//	private static int[] sqlTypes = {Types.INTEGER};
//	
//	public Object assemble(Serializable cached, Object owner) throws HibernateException {
//		return cached;
//	}
//
//	public Object deepCopy(Object value) throws HibernateException {
//		return value;
//	}
//
//	public Serializable disassemble(Object value) throws HibernateException {
//		return (Serializable) value;
//	}
//
//	public boolean equals(Object x, Object y) throws HibernateException {
//		if(x == y){
//			return true;
//		}
//		if(x == null || y == null){
//			return false;
//		}
//		return x.equals(y);
//	}
//
//	public int hashCode(Object x) throws HibernateException {
//		return x.hashCode();
//	}
//
//	public boolean isMutable() {
//		return false;
//	}
//
//	public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
//			throws HibernateException, SQLException {
//		int value = rs.getInt(names[0]);  
//		try
//		{
//			return OnlineStatus.valueOf(value);
//		}catch(Exception e){
//			
//		}
//		return OnlineStatus.OFFLINE;
//	}
//
//	public void nullSafeSet(PreparedStatement st, Object value, int index)
//			throws HibernateException, SQLException {
//		if(null == value){
//			value = OnlineStatus.OFFLINE;
//		}
//		st.setInt(index, ((OnlineStatus)value).ordinal());
//	}
//
//	public Object replace(Object original, Object target, Object owner)
//			throws HibernateException {
//		return original;
//	}
//
//	public Class<OnlineStatus> returnedClass() {
//		return OnlineStatus.class;
//	}
//
//	public int[] sqlTypes() {
//		return sqlTypes;
//	}
}
