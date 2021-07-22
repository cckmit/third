package org.opoo.apps.dv.office;

import java.lang.reflect.Field;

import org.opoo.apps.AppsObject;
import org.opoo.apps.AppsObjectLoader;
import org.opoo.apps.NotFoundException;
import org.opoo.apps.dv.provider.TestConvertibleObject;


public class TestAppsObjectLoader implements AppsObjectLoader {

	@SuppressWarnings("unchecked")
	public <T extends AppsObject> T getAppsObject(int objectType, long objectId) throws NotFoundException {
		if(objectType != TestConvertibleObject.OBJECT_TYPE){
			throw new IllegalArgumentException("不支持类型：" + objectType);
		}
		MockConvertableObject co = new MockConvertableObject();
		try {
			Field field = TestConvertibleObject.class.getDeclaredField("id");
			field.set(co, objectId);
		} catch(RuntimeException e){
			throw e;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return (T) co;
	}

}
