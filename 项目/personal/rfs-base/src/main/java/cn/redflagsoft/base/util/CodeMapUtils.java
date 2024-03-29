/*
 * $Id: CodeMapUtils.java 6432 2014-10-27 09:02:24Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.Labelable;
import org.opoo.apps.lifecycle.Application;
import org.springframework.util.ClassUtils;

import cn.redflagsoft.base.annotation.DisplayName;
import cn.redflagsoft.base.bean.Glossary;
import cn.redflagsoft.base.service.GlossaryService;


/**
 * 代码名称映射处理类。
 * 
 * <p>该类可以从静态字段中提取代码-名称映射信息，也可以从
 * glossary中读取代码-名称对应关系。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class CodeMapUtils {
	private static final Log log = LogFactory.getLog(CodeMapUtils.class);
	
	private static GlossaryService glossaryService;
	
	
	/**
	 * 缓存。
	 */
	private static Map<String, Map<String, String>> codeMaps = new HashMap<String, Map<String,String>>();
	private static Map<String, Map<String, Code>> codeValueMaps = new HashMap<String, Map<String,Code>>();
	

	/**
	 * 从指定类指定前缀的静态字段定义中或者指定code（代码）的名称。
	 * 
	 * <p>通常是通过{@link CodeMapUtils#getCodeMap(Class, String)}获取集合，再从集合中查找指定code的名称。
	 * 
	 * @param <T> 代码的类型
	 * @param clazz 指定的类型
	 * @param staticFieldPrefix 指定的静态字段前缀，通常是最后一个“_”之前的部分
	 * @param code 代码值
	 * @return 代码对应的名称
	 * @see CodeMapUtils#getCodeMap(Class, String)
	 */
	public static <T> String getCodeName(Class<?> clazz, String staticFieldPrefix, T code){
		if(code == null){
			return null;
		}

		Map<String, Code> map = CodeMapUtils.getAllCodeMap(clazz, staticFieldPrefix);
		if(map != null){
			Code c = map.get(code.toString());
			if(c != null){
				return c.getName();
			}
		}
		return code.toString();
	} 
	
	/**
	 * 
	 * @param clazz
	 * @param staticFieldPrefix
	 * @return
	 */
	public static Map<String, Code> getAllCodeMap(Class<?> clazz, String staticFieldPrefix){
		String key = clazz.getName() + "." + staticFieldPrefix;
		Map<String, Code> map = codeValueMaps.get(key);
		if(map == null){
			map = new LinkedHashMap<String, Code>();
			Field[] fields = clazz.getFields();
			String s = staticFieldPrefix + "_";
			int len = s.length();
			String name = null;
			for(Field f: fields){
				name = f.getName();
				if(name.startsWith(s)){
			
					GlossaryCategory gc = f.getAnnotation(GlossaryCategory.class);
					//System.out.println(">>>" + f + " :: " + gc);
					if(gc != null && gc.value() > 0){
						if(log.isDebugEnabled()){
							log.debug(String.format("属性 '%s' 含有注释 '%s'，不处理静态属性定义，查询Glossary中定义以替换。", f, gc));
						}
						
						Map<String, Code> map2 = getAllCodeMap(gc.value());
						if(map2 != null && !map2.isEmpty()){
							map = map2;
							break;//Jump out for
						}
					}
					
					//根据 DisplayName 注解来处理静态字段。
					DisplayName displayName = f.getAnnotation(DisplayName.class);
					if(displayName != null && StringUtils.isNotBlank(displayName.value())){
						if(log.isDebugEnabled()){
							log.debug(String.format("属性 '%s' 含有注释 '%s'，以静态字段的DisplayName注解替换值。", f, displayName));
						}
						
						try {
							Code codeValue = new Code(displayName.value(), f.get(null).toString(), Code.SOURCE_STATIC_FIELDS);
							map.put(codeValue.getCode(), codeValue);
						} catch (Exception e) {
							throw new IllegalArgumentException(e);
						}
						continue;
					}
					
					
					try {
						Code codeValue = new Code(name.substring(len), f.get(null).toString(), Code.SOURCE_STATIC_FIELDS);
						if(f.getAnnotation(Ignore.class) != null){
							if(log.isDebugEnabled()){
								log.debug(String.format("属性 '%s' 含有注释 'Ignore'，不将该属性读取到最终的CodeMap中。", f));
							}
							codeValue.setStatus(Code.STATUS_IGNORE);
						}
						
						map.put(codeValue.getCode(), codeValue);
					} catch (Exception e) {
						throw new IllegalArgumentException(e);
					} 
				}
			}
			codeValueMaps.put(key, map);
		}
		return map;
	}
	
	



	/**
	 * 从指定类的特定前缀的静态字段中获取CodeMap（代码名称映射）集合。
	 * <p>通常在类中以public static final <TYPE>  staticFieldPrefix_<XXX>的形式来定义
	 * 静态字段。
	 * 
	 * <ol><b>注意两点：</b>
	 * <li>{@link Ignore}标注：当静态字段标记了这个注释时，该字段将不出现在最终的CodeMap
	 * 		集合中。
	 * <li>{@link GlossaryCategory}标注：当静态字段标记了这个注释时，CodeMap体系将首先根据
	 * 		标注提供的<code>category</code>在{@link Glossary}中查询CodeMap集合，当数据库的
	 * 		Glossary中没	有该category的配置时，才取静态字段的定义作为CodeMap集合。此时，静态
	 * 		字段的定义是作为一种缺省配置使用的。
	 * </ol>
	 * 
	 * @param clazz 指定的类
	 * @param staticFieldPrefix 指定的前缀字符串
	 * @return CodeMap（代码名称映射）集合
	 */
	public static Map<String, String> getCodeMap(Class<?> clazz, String staticFieldPrefix){
//		String key = clazz.getName() + "." + staticFieldPrefix;
//		Map<String, String> map = codeMaps.get(key);
//		if(map == null){
//			map = new LinkedHashMap<String, String>();
//			Field[] fields = clazz.getFields();
//			String s = staticFieldPrefix + "_";
//			int len = s.length();
//			String name = null;
//			for(Field f: fields){
//				name = f.getName();
//				if(name.startsWith(s)){
//					
//					if(f.getAnnotation(Ignore.class) != null){
//						if(log.isDebugEnabled()){
//							log.debug(String.format("属性 '%s' 含有注释 'Ignore'，不将该属性读取到CodeMap中。", f));
//						}
//						continue;
//					}
//					
//					GlossaryCategory gc = f.getAnnotation(GlossaryCategory.class);
//					//System.out.println(">>>" + f + " :: " + gc);
//					if(gc != null && gc.value() > 0){
//						if(log.isDebugEnabled()){
//							log.debug(String.format("属性 '%s' 含有注释 '%s'，不处理静态属性定义，查询Glossary中定义以替换。", f, gc));
//						}
//						
//						Map<String, String> map2 = getCodeMap(gc.value());
//						if(map2 != null && !map2.isEmpty()){
//							map = map2;
//							break;//Jump out for
//						}
//					}
//					
//					try {
//						map.put(f.get(null).toString(), name.substring(len));
//					} catch (Exception e) {
//						throw new IllegalArgumentException(e);
//					} 
//				}
//			}
//			codeMaps.put(key, map);
//		}
//		return map;
		
		
		return buildFilteredStringCodeMap(CodeMapUtils.getAllCodeMap(clazz, staticFieldPrefix));
	}
	
	/**
	 * 获取所有的CodeMap集合，包括被忽略的。
	 * @param value
	 * @return
	 */
	public static Map<String, Code> getAllCodeMap(short glossaryCategory) {
		log.debug("从Glossary中查询数据：" + glossaryCategory);
		GlossaryService service = getGlossaryService();
		if(service == null){
			return null;
		}
		
		List<Glossary> list = service.findByCategory2(glossaryCategory);
		Map<String, Code> map = new LinkedHashMap<String, Code>();
		if(list != null && !list.isEmpty()){
			for (Glossary g : list) {
				Code code = new Code(g.getTerm(), g.getCode() + "", Code.SOURCE_GLOSSARY);
//				map.put(g.getCode() + "", g.getTerm());
				map.put(code.getCode(), code);
			}
		}
		return map;
	}
	
	/**
	 * 根据Glossary的category从Glossary查询CodeMap集合，Map类型。
	 * 集合有缓存，有顺序。
	 * 
	 * @see Glossary
	 * @param gc Glossary的category
	 * @return
	 */
	public static Map<String, String> getCodeMap(short glossaryCategory) {
//		log.debug("从Glossary中查询数据：" + glossaryCategory);
//		GlossaryService service = getGlossaryService();
//		if(service == null){
//			return null;
//		}
//		
//		List<Glossary> list = service.findByCategory2(glossaryCategory);
//		Map<String, String> map = new LinkedHashMap<String, String>();
//		if(list != null && !list.isEmpty()){
//			for (Glossary g : list) {
//				map.put(g.getCode() + "", g.getTerm());
//			}
//		}
//		return map;
		
		return buildFilteredStringCodeMap(CodeMapUtils.getAllCodeMap(glossaryCategory));
	}
	
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	private static Map<String, Code> buildFilteredCodeMap(Map<String, Code> map){
		if(map == null){
			return null;
		}
		
		Map<String, Code> result = new LinkedHashMap<String, Code>();
		for(Code code: map.values()){
			if(code.getStatus() == Code.STATUS_NORMAL){
				result.put(code.getCode(), code);
			}
		}
		return result;
	}
	
	private static Map<String, String> buildStringCodeMap(Map<String, Code> map){
		if(map == null){
			return null;
		}
		
		Map<String, String> result = new LinkedHashMap<String, String>();
		for(Code code: map.values()){
			result.put(code.getCode(), code.getName());
		}
		return result;
	}
	
	
	private static Map<String, String> buildFilteredStringCodeMap(Map<String, Code> map){
		map = buildFilteredCodeMap(map);
		return buildStringCodeMap(map);
	}

	/**
	 * 从CodeMap体系中获取CodeMap集合，List类型。
	 * 
	 * <p>
	 * 如果key是数字，则直接从Glossary获取集合，见{@link CodeMapUtils#getCodeMapList(short)}。<br>
	 * 如果key是字串，则用“.”来分割字段串，第一部分作为类名，第二部分作为静态字段的前缀，
	 * 用此类调用{@link CodeMapUtils#getCodeMap(Class, String)}。
	 * 
	 * @param key
	 * @return
	 */
	public static List<Labelable> getCodeMapList(String key){
		if(NumberUtils.isDigits(key)){
			return getCodeMapList(Short.parseShort(key));
		}
		
		//List<Labelable> list = new ArrayList<Labelable>();
		int pos = key.lastIndexOf('.');
		String className = key.substring(0, pos);
		String prefix = key.substring(pos + 1);
		//System.out.println(className + "---" + prefix);
		try {
			Class<?> clazz = ClassUtils.forName(className);
			
			Map<String, Code> map = CodeMapUtils.getAllCodeMap(clazz, prefix);
			map = buildFilteredCodeMap(map);
			if(map != null){
				return new ArrayList<Labelable>(map.values());
			}
			
//			Map<String, String> map = CodeMapUtils.getCodeMap(clazz, prefix);
//			//System.out.println(map);
//			if(map != null){
//				for(Map.Entry<String, String> en : map.entrySet()){
//					list.add(new DefaultLabelDataBean(en.getValue(), en.getKey()));
//				}
//			}
			
		} catch (Exception e) {
			log.error("CodeMap处理错误", e);
		} 
		
		return Collections.emptyList();
	}
	/**
	 * 根据Glossary的category从Glossary查询CodeMap集合，List类型。
	 * 集合有缓存，有顺序。
	 * 
	 * @param category
	 * @return
	 */
	public static List<Labelable> getCodeMapList(short category){
//		List<Labelable> list = new ArrayList<Labelable>();
//		getGlossaryService();
//		if(glossaryService != null){
//			List<Glossary> list2 = glossaryService.findByCategory2(category);
//			for (Glossary g : list2) {
//				list.add(new DefaultLabelDataBean(g.getTerm(), g.getCode()));
//			}
//		}
//		return list;
		
		
		Map<String, Code> map = CodeMapUtils.getAllCodeMap(category);
		map = buildFilteredCodeMap(map);
		return new ArrayList<Labelable>(map.values());
	}
	
	/**
	 * 获取应用程序上下文中GlossaryService的实例。
	 * @return
	 */
	private static GlossaryService getGlossaryService(){
		if(Application.isInitialized() && Application.isContextInitialized()){
			if(glossaryService == null){
				glossaryService = Application.getContext().get("glossaryServiceWrapper", GlossaryService.class);
			}
			
			if(glossaryService == null){
				log.warn("容器中找不到 glossaryServiceWrapper 的定义。");
				return null;
			}
			return glossaryService;
		}else{
			log.warn("应用程序或者容器没有初始化，无法获取glossaryService。");
			return null;
		}
	}
	
	/**
	 * 从 Glossary 中查询 指定代码对应的名称。
	 * 
	 * @param category Glossary的category
	 * @param code 指定的代码
	 * @return 代码对应的名称
	 * @see GlossaryService#getByCategoryAndCode(short, int)
	 */
	public static <T> String getCodeName(short category, T code){
		if(code == null){
			return null;
		}
		int c = Integer.MIN_VALUE;
		if(code instanceof Number){
			c = ((Number)code).intValue();
		}else{
			try {
				c = Integer.parseInt(code.toString());
			} catch (NumberFormatException e) {
				log.warn("无法将code转成int类型：" + code);
//				c = Integer.MIN_VALUE;
				return code.toString();
			}
		}
		
		if(c == Integer.MIN_VALUE){
			return null;
		}
		
		getGlossaryService();
		if(glossaryService != null){
			return glossaryService.getByCategoryAndCode(category, c);
		}else{
			log.warn("应用程序或者容器没有初始化，无法从Glossary中获取编码对照数据。");
			return null;
		}
	}
	
	/**
	 * 清理CodeMap体系中的缓存。
	 * 
	 */
	public static void clearCache(){
		codeMaps.clear();
	}
	
	
	public static void main(String[] args) throws Exception{
//		String name = CodeMapUtils.getCodeName(Issue.class, "GRADE", 2);
//		System.out.println(name);
//		
//		name = CodeMapUtils.getCodeName(Issue.class, "GRADE", "1");
//		System.out.println(name);
		
//		List<Labelable> list = getCodeMapList(Issue.class.getName() + ".GRADE");
//		System.out.println(list);
//		for(Labelable l:list){
//			System.out.println(l.getLabel() + ":" + l.getData());
//		}
//		Map<String, String> map = CodeMapUtils.getCodeMap(Issue.class, "GRADE");
//		System.out.println(map);
//		
//		
//		//key-value倒置
//		BiMap<String, String> map2 = HashBiMap.create(map).inverse();
//		System.out.println(map2);
//		System.out.println(map2.inverse());
		
		
		
//		Field field = Project.class.getDeclaredField("STATUS_结束");
//		field.setAccessible(true);
//		GlossaryCategory gc = field.getAnnotation(GlossaryCategory.class);
//		System.out.println(gc);
//		System.out.println(gc.value());
//		System.out.println(field.getAnnotation(GlossaryCategory.class));
		
		
		
//		Issue is = new Issue();
//		is.setGrade(Issue.GRADE_一般);
//		BeanInfo beanInfo = Introspector.getBeanInfo(is.getClass());
//		PropertyDescriptor descriptors[] = beanInfo.getPropertyDescriptors();
//		for(PropertyDescriptor desc: descriptors){
//			//System.out.println(desc.getReadMethod() + " >> " + desc.getName());
//			System.out.println(desc.getName());
//			System.out.println(desc.getReadMethod().invoke(is, (Object[])null));
//		}
		
//		Map<String, Object> context = new HashMap<String, Object>();
//		context.put("user", "Alex Lin");
//		
//		Task task = new Task();
//		task.setName("萨拉科技大啦");
//		context.put("task", task);
//		
//		String string = StringUtils.processExpression("Hello ${user}! \n你好，${task.name}！", context);
//		System.out.println(string);
		
		Object code = new Long(212);
		int c = 0;
		if(code instanceof Number){
			System.out.println("Number");
			c = ((Number)code).intValue();
		}else{
			try {
				c = Integer.parseInt(code.toString());
			} catch (NumberFormatException e) {
				log.warn("无法将code转成int类型：" + code);
				c = Integer.MIN_VALUE;
			}
		}
		System.out.println(c);
	}
	
	
	public static class Code implements Serializable, Labelable{
		private static final long serialVersionUID = 1481143585434417322L;
		
		public static final int SOURCE_UNKNOWN = 0;
		public static final int SOURCE_STATIC_FIELDS = 1;
		public static final int SOURCE_GLOSSARY = 2;
		
		public static final int STATUS_NORMAL = 0;
		public static final int STATUS_IGNORE = 1;
		
		private String name;
		private String code;
		private int source = SOURCE_UNKNOWN;
		private int status = STATUS_NORMAL;
		
		public Code(String name, String code, int source){
			super();
			this.name = name;
			this.code = code;
			this.source = source;
		}
		/**
		 * @param name
		 * @param code
		 * @param source
		 * @param status
		 */
		public Code(String name, String code, int source, int status) {
			super();
			this.name = name;
			this.code = code;
			this.source = source;
			this.status = status;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the code
		 */
		public String getCode() {
			return code;
		}
		/**
		 * @param code the code to set
		 */
		public void setCode(String code) {
			this.code = code;
		}
		/**
		 * @return the source
		 */
		public int getSource() {
			return source;
		}
		/**
		 * @param source the source to set
		 */
		public void setSource(int source) {
			this.source = source;
		}
		/**
		 * @return the status
		 */
		public int getStatus() {
			return status;
		}
		/**
		 * @param status the status to set
		 */
		public void setStatus(int status) {
			this.status = status;
		}
		/* (non-Javadoc)
		 * @see org.opoo.apps.Labelable#getData()
		 */
		public Serializable getData() {
			return code;
		}
		/* (non-Javadoc)
		 * @see org.opoo.apps.Labelable#getLabel()
		 */
		public String getLabel() {
			return name;
		}
		
		public String toString(){
			return name + ":" + code;
		}
	}

}
