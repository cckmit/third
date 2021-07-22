package org.opoo.apps.dv.office.dao.impl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.opoo.apps.dv.office.OfficeConversionArtifactType;

/**
 * 
 * @author lcj
 *
 */
public class OfficeConversionArtifactTypeUserType implements UserType{
	public static final int[] SQL_TYPES = {Types.VARCHAR};
	
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

	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	public Class<?> returnedClass() {
		return OfficeConversionArtifactType.class;
	}
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
			throws HibernateException, SQLException {
		String value = rs.getString(names[0]);      
		 Object result = null;        
		 if(!rs.wasNull())        
			 result = OfficeConversionArtifactType.valueOf(value);
		 return result;
	}
	public void nullSafeSet(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException {
		if(null == value)         
			st.setNull(index, Types.VARCHAR);  
		else          
			st.setString(index, ((OfficeConversionArtifactType)value).name());
	}
}