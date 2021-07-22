package org.opoo.apps;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.util.EqualsHelper;
import org.opoo.apps.util.StringUtils;



/**
 * ��ѯ������
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class QueryParameter implements Serializable {
	
	//=,>,<,>=,<=,like,ilike,<>,in,notin,ne,gt,ge,lt,le
	/**
	 * ��ѯ��������
	 */
	public static enum Operator{
		EQ("=", "eq"),
		GT("gt", ">"),
		LT("lt", "<"),
		GE("ge", ">="),
		LE("le", "<="),
		LIKE("like"),
		ILIKE("ilike"),
		NE("ne", "<>", "!="),
		IN("in"),
		NOTIN("notin"),
		NULL("null"),
		NOTNULL("notnull");
		
//		/**
//		 * ��ȡ���еĲ������ķ��ű�ʽ��ʽ��
//		 * 
//		 * @return
//		 */
//		public static List<String> getAllSymbols(){
//			List<String> operators = new ArrayList<String>();
//			Operator[] values = Operator.values();
//			for (Operator operator : values) {
//				for(String op: operator.getSymbols()){
//					operators.add(op);
//				}
//			}
//			return operators;
//		}
		
		public static Operator valueOfSymbol(String symbol){
			Operator[] values = Operator.values();
			for (Operator operator : values) {
				if(operator.isSymbolEquals(symbol)){
					return operator;
				}
			}
			return null;
		}
		
		private String[] symbols;
		private Operator(String... symbols){
			this.symbols = symbols;
		}
		public boolean isSymbolEquals(String symbol){
			for (String op : symbols) {
				if(op.equalsIgnoreCase(symbol)){
					return true;
				}
			}
			return false;
		}
		public String[] getSymbols(){
			return symbols;
		}
	}
	

	private static final Log log = LogFactory.getLog(QueryParameter.class);
	
	private static final long serialVersionUID = 5075569445741853638L;
	
	public static final Operator DEFAULT_OPERATOR = Operator.EQ;
	/**
	 * ����֧�ֵĴ�ǰ̨���������ݵ����͡�
	 */
	private static final Map<String, Class<?>> TYPES;// = new HashMap<String, Class<?>>();
	//private static final List<String> OPERATORS = Operator.getAllSymbols();
	//Arrays.asList("=,>,<,>=,<=,like,ilike,<>,in,notin,ne,gt,ge,lt,le".split(","));
	
	static{
		Map<String, Class<?>> m = new HashMap<String, Class<?>>();
		m.put("string", String.class);
		m.put("long", Long.class);
		m.put("float", Float.class);
		m.put("double", Double.class);
		m.put("int", Integer.class);
		m.put("short", Short.class);
		m.put("byte", Byte.class);
		m.put("bigdecimal", BigDecimal.class);
		m.put("biginteger", BigInteger.class);
		m.put("boolean", Boolean.class);
		m.put("date", Date.class);
		//m.put("onlineStatus", OnlineStatus.class);
		//TYPES.put("date", Date.class);
		//TYPES.put("char", Character.class);
		
		TYPES = Collections.unmodifiableMap(m);
	}
	
	

	
	/**
	 * ��������
	 */
	private String n;
	/**
	 * ������
	 */
	private String o = DEFAULT_OPERATOR.symbols[0];
	/**
	 * ֵ
	 */
	private String v;
	/**
	 * ����
	 * default is null, string
	 */
	private String t;
	
	/**
	 * �������͡�
	 */
	private transient Class<?> type;
	
	private transient Operator operator = DEFAULT_OPERATOR; 
	
	
	public QueryParameter(){
		
	}
	/**
	 * ������ѯ������
	 * 
	 * @param name ��������
	 * @param value ����ֵ
	 */
	public QueryParameter(String name, String value){
		setN(name);
		setV(value);
	}
	
	/**
	 * ������ѯ������
	 * 
	 * @param name ��������
	 * @param value ����ֵ
	 * @param operation ����������
	 */
	public QueryParameter(String name, String value, String operation){
		setN(name);
		setV(value);
		setO(operation);
	}
	
	/**
	 * ������ѯ������
	 * 
	 * @param name ��������
	 * @param value ����ֵ
	 * @param operation ����������
	 * @param type �������͵��ַ�����ʽ
	 */
	public QueryParameter(String name, String value, String operation, String type){
		setN(name);
		setV(value);
		setO(operation);
		setT(type);
	}
	
	/**
	 * ������ѯ������
	 * 
	 * @param name ��������
	 * @param value ����ֵ
	 * @param operation ����������
	 * @param type ��������
	 */
	public QueryParameter(String name, String value, String operation, Class<?> type){
		setN(name);
		setV(value);
		setO(operation);
		this.type = type;
	}
	
	/**
	 * ���ơ�
	 * @return the n
	 */
	public String getN() {
		return n;
	}
	/**
	 * @param n the n to set
	 */
	public void setN(String n) {
		StringUtils.validatePropertyName(n);
		this.n = n;
	}
	/**
	 * ��������
	 * @return the o
	 */
	public String getO() {
		//log.debug(this + " ��ȡ��������" + this.o);
		return o;
	}
	/**
	 * @param o the o to set
	 */
	public void setO(String o) {
		if(StringUtils.isBlank(o)){
			this.o = DEFAULT_OPERATOR.symbols[0];
			this.operator = DEFAULT_OPERATOR;
		}else{
			o = o.trim();
			Operator operator2  = Operator.valueOfSymbol(o);
			if(operator2 != null){
				this.operator = operator2;
				this.o = o;
			}else{
				throw new IllegalArgumentException("��Ч�Ĳ�������" + o);
			}
		}
		log.debug(this + " �����˲�������" + this.o);
	}
	
	/**
	 * ֵ��
	 * @return the v
	 */
	public String getV() {
		return v;
	}
	/**
	 * @param v the v to set
	 */
	public void setV(String v) {
		this.v = v;
	}
	/**
	 * ���͡�
	 * @return the t
	 */
	public String getT() {
		return t;
	}
	/**
	 * @param t the t to set
	 */
	public void setT(String t) {
		this.t = t;
		if(t == null){
			this.type = null;
		}else{
			type = TYPES.get(t.toLowerCase().trim());
		}
	}
	
	
	/**
	 * �������͡�
	 * @return
	 */
	public Class<?> getTypeClass(){
//		if(type == null){
//			if(t != null){
//				type = TYPES.get(t.toLowerCase().trim());
//			}
//		}
		return type;
//		if(type != null){
//			return type;
//		}
//		if(getT() != null){
//			return TYPES.get(getT().toLowerCase().trim());
//		}
//		return null;
	}
	
//	public void setTypeClass(Class<?> clazz){
//		this.type = clazz;
//	}
	
	public Operator getOperator(){
		return this.operator;
	}
	
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(this == obj){
			return true;
		}
		if(!(obj instanceof QueryParameter)){
			return false;
		}
		QueryParameter x = (QueryParameter)obj;
		return EqualsHelper.equals(n, x.n)
			&& EqualsHelper.equals(o, x.o)
			&& EqualsHelper.equals(v, x.v)
			&& EqualsHelper.equals(t, x.t);
	}
	
	public int hashCode(){
		return new HashCodeBuilder().append(n).append(o).append(v).append(t).toHashCode();
	}
	
	public String toString(){
		return String.format("{QueryParameter: %s %s %s (%s)}", n, o, v, t);
		//return new ToStringBuilder(this).append(n).append(o).append(v).append(t).toString();
	}
}
