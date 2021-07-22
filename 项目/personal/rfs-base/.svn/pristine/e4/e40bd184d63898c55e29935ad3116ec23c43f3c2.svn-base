package cn.redflagsoft.base.service.order;

import java.util.Map;

import cn.redflagsoft.base.service.OrderFinder;
import cn.redflagsoft.base.util.CodeMapUtils;


/**
 * 从 CodeMap 中查找根据特定对象的特定属性查找该对象的排序号。
 * 
 * <p>该类还定义了对某类CodeMap集合的缓存。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @see CodeMapUtils
 */
public abstract class AbstractCodeMapOrderFinder implements OrderFinder<String>{
	//缓存
	private final Map<String, String> codeMap;
	
	/**
	 * 根据指定类别的Glossary构建实例。
	 * 
	 * <p>CodeMap是从Glossary中读取了，参数指定了Glossary的类型。
	 * 
	 * @param glossaryCategory glossary 的类别
	 */
	public AbstractCodeMapOrderFinder(short glossaryCategory){
		codeMap = CodeMapUtils.getCodeMap(glossaryCategory);
	}
	
	/**
	 * 根据指定类静态字段定义构建实例。
	 * 
	 * <p>CodeMap是从指定类的静态字段中生成的，例如类Issue
	 * <pre>
	 * public static final int STATUS_一 = 1;
	 * public static final int STATUS_二 = 2;
	 * ...
	 * </pre>
	 * 其中 clazz为Issue，静态字段前缀为“STATUS”，生成的CodeMap集合
	 * 类似于{1:"一",2:"二",...}
	 * 
	 * 
	 * 
	 * @param clazz 指定的类，静态字段定义在其中
	 * @param staticFieldPrefix 静态字段的前缀
	 */
	public AbstractCodeMapOrderFinder(Class<?> clazz, String staticFieldPrefix){
		codeMap = CodeMapUtils.getCodeMap(clazz, staticFieldPrefix);
	}
	
	/**
	 * 根据CodeMap中的key来查找排序号。
	 * 
	 * <p>由于CodeMap集合中的数据已经是排序过的，所以Map中元素的顺序号
	 * 就是对象的排序号。
	 * @param key CodeMap的键
	 * @return 排序号
	 */
	protected int getOrderByKey(String key){
		if(key == null){
			return 0;
		}
		int index = 0;
		for(Map.Entry<String, String> en: codeMap.entrySet()){
			index++;
			if(key.equals(en.getKey())){
				return index;
			}
		}
		return 0;
	}
	
	/**
	 * 根据CodeMap中的值来查找排序号。
	 * 
	 * <p>由于CodeMap集合中的数据已经是排序过的，所以Map中元素的顺序号
	 * 就是对象的排序号。
	 * @param value CodeMap的值
	 * @return 排序号
	 */
	protected int getOrderByValue(String value){
		if(value == null){
			return 0;
		}
		int index = 0;
		for(Map.Entry<String, String> en: codeMap.entrySet()){
			index++;
			if(value.equals(en.getValue())){
				return index;
			}
		}
		return 0;
	}
}
