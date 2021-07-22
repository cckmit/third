package org.opoo.apps.event.v2;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tangosol.dev.compiler.java.ThisExpression;


/**
 * �¼������ࡣ
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
@SuppressWarnings("unchecked")
public class EventUtils {

	
	/**
	 * ��ȡ�¼����͡�
	 * @param <E> 
	 * @param listener �¼�������
	 * @return �¼���java����
	 */
	public static <E extends Event> Class<E> getParameterizedEventClass(EventListener<E> listener){
        Set<Type> interfaces = new HashSet<Type>();
        Class<?> clazz = listener.getClass();
        do
        {
        	//System.out.println("�����ࣺ" + clazz);
            interfaces.addAll(Arrays.asList(clazz.getGenericInterfaces()));
            clazz = clazz.getSuperclass();
        } while(clazz != null);
        
        //System.out.println("�ӿڣ�" + interfaces);
        
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
