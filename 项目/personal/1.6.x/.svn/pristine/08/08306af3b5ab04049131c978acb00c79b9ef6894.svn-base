package org.opoo.apps.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.opoo.apps.json.JSONSerializable;
import org.opoo.apps.util.SerializableUtils;
import org.opoo.apps.xml.XMLSerializable;
import org.opoo.ndao.domain.Entity;



/**
 * 可序列化的域对象基类。
 * 
 * @author Alex Lin
 * @since 1.0
 */
public abstract class SerializableEntity<K extends Serializable> extends Entity<K>
		implements XMLSerializable, JSONSerializable {
	private static final long serialVersionUID = -6475190248645176713L;

//	protected static final JavaXML JAVA_XML = JavaXMLFactory.getInstance().getJavaXML();
	

	public String toXMLString() {
		//return JavaXmlFactory.getInstance().getJavaXml().toXml(this);
//		return JAVA_XML.toXMLString(this);
		return SerializableUtils.toXML(this);
	}
	
	public String toJSONString(){
//		try {
//			return JSONUtil.serialize(this);
//		} catch (JSONException e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
		return SerializableUtils.toJSON(this);
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
	
	public int hashCode(){
		return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
	}
}
