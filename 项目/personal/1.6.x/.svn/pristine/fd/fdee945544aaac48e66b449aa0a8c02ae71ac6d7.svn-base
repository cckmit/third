package org.opoo.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GuidKey {

	private String mId;
	private String mPrefix;
	private static final transient Log smLogger = LogFactory.getLog(GuidKey.class);

	public GuidKey() {
		mPrefix = null;
		mId = null;
	}

	public GuidKey(String id) {
		this(null, id);
	}

	protected GuidKey(String prefix, String id) {
		if (prefix != null && !id.startsWith(prefix)) {
			smLogger.error((new StringBuilder()).append("Id: ").append(id).append(" does not start with prefix: ")
					.append(prefix).append(" for key class type: ").append(getClass().getName()).toString());
			throw new RuntimeException();
		} else {
			mPrefix = prefix;
			mId = id;
			return;
		}
	}

	public String getGuid() {
		return mId;
	}

	public String getPrefix() {
		return mPrefix;
	}

	public static GuidKey newKey(String prefix) {
		if (prefix.length() != 4)
			throw new RuntimeException();
		else
			return new GuidKey(prefix, GuidGenerator.newGuid(prefix));
	}

	public static GuidKey newKey() {
		return new GuidKey(null, GuidGenerator.newGuid());
	}

	public Object convertFromString(String value) {
		return new GuidKey(value);
	}

	public final String convertToString(Object object) {
		if (object instanceof GuidKey)
			return ((GuidKey) object).getGuid();
		else
			throw new RuntimeException();
	}

	public boolean equals(Object rhs) {
		if (this == rhs)
			return true;
		if (!(rhs instanceof GuidKey))
			return false;
		GuidKey rhsKey = (GuidKey) rhs;
		if (mPrefix == null) {
			if (rhsKey.mPrefix != null)
				return false;
		} else if (!mPrefix.equals(rhsKey.mPrefix))
			return false;
		return mId == null ? rhsKey.mId == null : mId.equals(rhsKey.mId);
	}

	public int hashCode() {
		return mId == null ? 0 : mId.hashCode();
	}

	public int compareTo(Object o) {
		GuidKey key2 = (GuidKey) o;
		String guid2 = key2.getGuid();
		if (getGuid().length() == guid2.length())
			return getGuid().compareTo(guid2);
		String prefix1 = getPrefix();
		String prefix2 = key2.getPrefix();
		int result = prefix1.compareTo(prefix2);
		if (result == 0) {
			Integer suffix1 = new Integer(getGuid().substring(prefix1.length()));
			Integer suffix2 = new Integer(guid2.substring(prefix2.length()));
			return suffix1.compareTo(suffix2);
		} else {
			return result;
		}
	}
}
