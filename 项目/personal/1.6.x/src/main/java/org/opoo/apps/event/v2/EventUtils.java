package org.opoo.apps.event.v2;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tangosol.dev.compiler.java.ThisExpression;


/**
 * 事件工具类。
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public class EventUtils {

	
	/**
	 * 获取事件类型。
	 * @param <E> 
	 * @param listener 事件监听器
	 * @return 事件的java类型
	 */
	public static <E extends Event> Class<E> getParameterizedEventClass(EventListener<E> listener){
        Set<Type> interfaces = new HashSet<Type>();
        Class<?> clazz = listener.getClass();
        do
        {
        	//System.out.println("处理类：" + clazz);
            interfaces.addAll(Arrays.asList(clazz.getGenericInterfaces()));
            clazz = clazz.getSuperclass();
        } while(clazz != null);
        
        //System.out.println("接口：" + interfaces);
        
        for(Type iface: interfaces){
        	//System.out.println(iface + "==" + (iface instanceof ParameterizedType));
        	if((iface instanceof ParameterizedType) && ((ParameterizedType)iface).getRawType().equals(EventListener.class)){
        		Type type = ((ParameterizedType)iface).getActualTypeArguments()[0];
        		//System.out.println(">>> " + type);
        		//System.out.println(((ParameterizedType)iface).getRawType());
        		return (Class<E>) type;
        	}
        }
        return null;
	}
}
