/*
 * $Id: CodeMapUtils.java 6432 2014-10-27 09:02:24Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package springold.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import springold.anotation.GlossaryCategory;
import springold.anotation.Ignore;
import springold.bean.Glossary;
import springold.config.Application;
import springold.service.GlossaryService;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * ��������ӳ�䴦���ࡣ
 * 
 * <p>������ԴӾ�̬�ֶ�����ȡ����-����ӳ����Ϣ��Ҳ���Դ�
 * glossary�ж�ȡ����-���ƶ�Ӧ��ϵ��
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class CodeMapUtils {
	private static final Log log = LogFactory.getLog(CodeMapUtils.class);
	
	private static GlossaryService glossaryService;
	
	
	/**
	 * ���档
	 */
	private static Map<String, Map<String, String>> codeMaps = new HashMap<String, Map<String,String>>();
	private static Map<String, Map<String, Code>> codeValueMaps = new HashMap<String, Map<String,Code>>();
	

	/**
	 * ��ָ����ָ��ǰ׺�ľ�̬�ֶζ����л���ָ��code�����룩�����ơ�
	 * 
	 * <p>ͨ����ͨ��{@link CodeMapUtils#getCodeMap(Class, String)}��ȡ���ϣ��ٴӼ����в���ָ��code�����ơ�
	 * 
	 * @param <T> ���������
	 * @param clazz ָ��������
	 * @param staticFieldPrefix ָ���ľ�̬�ֶ�ǰ׺��ͨ�������һ����_��֮ǰ�Ĳ���
	 * @param code ����ֵ
	 * @return �����Ӧ������
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
							log.debug(String.format("���� '%s' ����ע�� '%s'��������̬���Զ��壬��ѯGlossary�ж������滻��", f, gc));
						}
						
						Map<String, Code> map2 = getAllCodeMap(gc.value());
						if(map2 != null && !map2.isEmpty()){
							map = map2;
							break;//Jump out for
						}
					}
					

					
					
					try {
						Code codeValue = new Code(name.substring(len), f.get(null).toString(), Code.SOURCE_STATIC_FIELDS);
						if(f.getAnnotation(Ignore.class) != null){
							if(log.isDebugEnabled()){
								log.debug(String.format("���� '%s' ����ע�� 'Ignore'�����������Զ�ȡ�����յ�CodeMap�С�", f));
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
	 * ��ָ������ض�ǰ׺�ľ�̬�ֶ��л�ȡCodeMap����������ӳ�䣩���ϡ�
	 * <p>ͨ����������public static final <TYPE>  staticFieldPrefix_<XXX>����ʽ������
	 * ��̬�ֶΡ�
	 * 
	 * <ol><b>ע�����㣺</b>
	 * <li>{@link Ignore}��ע������̬�ֶα�������ע��ʱ�����ֶν������������յ�CodeMap
	 * 		�����С�
	 * <li>{@link GlossaryCategory}��ע������̬�ֶα�������ע��ʱ��CodeMap��ϵ�����ȸ���
	 * 		��ע�ṩ��<code>category</code>��{@link Glossary}�в�ѯCodeMap���ϣ������ݿ��
	 * 		Glossary��û	�и�category������ʱ����ȡ��̬�ֶεĶ�����ΪCodeMap���ϡ���ʱ����̬
	 * 		�ֶεĶ�������Ϊһ��ȱʡ����ʹ�õġ�
	 * </ol>
	 * 
	 * @param clazz ָ������
	 * @param staticFieldPrefix ָ����ǰ׺�ַ���
	 * @return CodeMap����������ӳ�䣩����
	 */
	public static Map<String, String> getCodeMap(Class<?> clazz, String staticFieldPrefix){
		return buildFilteredStringCodeMap(CodeMapUtils.getAllCodeMap(clazz, staticFieldPrefix));
	}
	
	/**
	 * @Description: ��ȡ���е�CodeMap���ϣ����������Եġ�
	 * @Author: Weitj
	 * @Date: 2022/01/24 9:10
	  * @param glossaryCategory 
	 * @return: java.util.Map<java.lang.String,springold.utils.CodeMapUtils.Code>
	 */
	public static Map<String, Code> getAllCodeMap(short glossaryCategory) {
		log.debug("��Glossary�в�ѯ���ݣ�" + glossaryCategory);
		GlossaryService service = getGlossaryService();
		if(service == null){
			return null;
		}
		
		List<Glossary> list = service.findByCategory(glossaryCategory);
		Map<String, Code> map = new LinkedHashMap<String, Code>();
		if(list != null && !list.isEmpty()){
			for (Glossary g : list) {
				Code code = new Code(g.getTerm(), g.getCode() + "", Code.SOURCE_GLOSSARY);
				map.put(code.getCode(), code);
			}
		}
		return map;
	}
	
	/**
	 * @Description: ����Glossary��category��Glossary��ѯCodeMap���ϣ�Map���͡�
	 * 	 �����л��棬��˳��
	 * @Author: Weitj
	 * @Date: 2022/01/24 9:31
	  * @param glossaryCategory 
	 * @return: java.util.Map<java.lang.String,java.lang.String>
	 */
	public static Map<String, String> getCodeMap(short glossaryCategory) {
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
	 * ��ȡӦ�ó�����������GlossaryService��ʵ����
	 * @return
	 */
	private static GlossaryService getGlossaryService(){
		if(Application.isInitialized){
			if(glossaryService == null){
				glossaryService = Application.getBean("glossaryService", GlossaryService.class);
			}
			
			if(glossaryService == null){
				log.warn("�������Ҳ��� glossaryServiceWrapper �Ķ��塣");
				return null;
			}
			return glossaryService;
		}else{
			log.warn("Ӧ�ó����������û�г�ʼ�����޷���ȡglossaryService��");
			return null;
		}
	}
	
	/**
	 * �� Glossary �в�ѯ ָ�������Ӧ�����ơ�
	 * 
	 * @param category Glossary��category
	 * @param code ָ���Ĵ���
	 * @return �����Ӧ������
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
				log.warn("�޷���codeת��int���ͣ�" + code);
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
			log.warn("Ӧ�ó����������û�г�ʼ�����޷���Glossary�л�ȡ����������ݡ�");
			return null;
		}
	}
	
	/**
	 * ����CodeMap��ϵ�еĻ��档
	 * 
	 */
	public static void clearCache(){
		codeMaps.clear();
	}

	
	public static class Code implements Serializable{
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
