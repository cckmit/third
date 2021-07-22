package org.opoo.apps.json;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;

@Deprecated
public class DefaultJSONSerializer implements JSONSerializer {

	public String toJSON(Object object) {
		try {
			return JSONUtil.serialize(object);
		} catch (JSONException e) {
			throw new IllegalStateException("Json serialize error", e);
		}
	}

	public Object fromJSON(String json) {
		try {
			return JSONUtil.deserialize(json);
		} catch (JSONException e) {
			throw new IllegalStateException("Json deserialize error", e);
		}
	}

}
