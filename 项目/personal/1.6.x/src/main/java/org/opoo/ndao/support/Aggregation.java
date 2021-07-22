package org.opoo.ndao.support;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * �ۺϲ�ѯ����
 * 
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class Aggregation {
	private String[] groupFields;
	private AggregateField[] aggregateFields;
	
	public static Aggregation newInstance(String aggregateFieldsString, String... groupFields){
		return new Aggregation(aggregateFieldsString, groupFields);
	}
	
	public Aggregation(AggregateField[] fields, String... groupFields){
		setAggregateFields(fields);
		setGroupFields(groupFields);
	}
	
	protected Aggregation(String aggregateFieldsString, String... groupFields){
		this(aggregateFieldsStringToArray(aggregateFieldsString), groupFields);
	}
	

	/**
	 * ���÷����ֶΣ�����Ϊ�ա�
	 * @param groupFields
	 */
	public void setGroupFields(String[] groupFields) {
//		if(ArrayUtils.isEmpty(groupFields)){
//			throw new IllegalArgumentException("Empty groupFields");
//		}
		this.groupFields = groupFields;
	}

	/**
	 * ���þۺ��ֶΣ�����Ϊ�ա�
	 * @param fields
	 */
	public void setAggregateFields(AggregateField[] fields) {
		if(ArrayUtils.isEmpty(fields)){
			throw new IllegalArgumentException("Empty fields");
		}
		this.aggregateFields = fields;
	}

	/**
	 * ��ȡ�����ֶΡ�
	 * @return
	 */
	public String[] getGroupFields() {
		return groupFields;
	}

	/**
	 * ��ȡ�ۺ��ֶΡ�
	 * @return
	 */
	public AggregateField[] getAggregateFields() {
		return aggregateFields;
	}
	
	/**
	 * ��ȡ�ۺ��ֶ���ɵĲ�ѯ�ַ���������������
	 * @return
	 */
	protected String getFullAggregateFieldsString(){
		return StringUtils.join(aggregateFields, ", ");
	}
	
	/**
	 * ��ȡ�����ֶ���ɵĲ�ѯ�ַ���������������
	 * @return ��������ֶ�Ϊ�ջ��߸���Ϊ0������ null��
	 */
	protected String getFullGroupFieldsString(){
		if(ArrayUtils.isEmpty(groupFields)){
			return null;
		}
		String s = StringUtils.join(groupFields, ", ");
		AggregateField[] fs = Aggregation.aggregateFieldsStringToArray(s);
		return StringUtils.join(fs, ", ");
	}
	
	/**
	 * ��ȡ�����ֶ���ɵ��ַ��������� group by���ޱ�������
	 * @return
	 */
	public String getGroupFieldsString(){
		if(ArrayUtils.isEmpty(groupFields)){
			return null;
		}
		return StringUtils.join(groupFields, ", ");
	}
	
	/**
	 * �����ɷ����ֶκ;ۺ��ֶ���ɵĲ�ѯ�ַ�����
	 * @return ��ѯ�ַ�����������
	 */
	public String toString(){
		String s = getFullGroupFieldsString();
		if(s == null){
			s = "";
		}else{
			s += ", ";
		}
		
		return s + getFullAggregateFieldsString();
	}
	

	/**
	 * ���ַ�������Ϊ�ۺ��ֶ����顣
	 * 
	 * @param fieldsString �����ַ������� count(*) as myCount, sum(a) as sumA
	 * @return �ۺ��ֶ�����
	 */
	protected static AggregateField[] aggregateFieldsStringToArray(String fieldsString){
		if(fieldsString == null || "".equals(fieldsString.trim())){
			throw new IllegalArgumentException("empty fieldsString");
		}
		String[] ff = fieldsString.split(",");
		AggregateField[] fields = new AggregateField[ff.length];
		int index = 0;
		for(String f: ff){
			f = f.trim();
			String lower = f.toLowerCase();
			int i = lower.indexOf(" as ");
			if(i == -1){
				if(f.indexOf('(') != -1){
					throw new IllegalArgumentException("�������ñ�����" +f);
				}
				fields[index++] = new AggregateField(f, f);
			}else{
				String s0 = f.substring(0, i).trim();
				String s1 = f.substring(i + 4).trim();
				fields[index++] = new AggregateField(s0, s1);
			}
			
//			String[] strings = f.trim().split(" ");
//			if(strings.length == 1){
//				if(strings[0].indexOf('(') != -1){
//					throw new IllegalArgumentException("�������ñ�����" + strings[0]);
//				}
//				fields[index++] = new AggregateField(strings[0], strings[0]);
//			}else if(strings.length > 1){
//				fields[index++] = new AggregateField(strings[0], strings[strings.length - 1]);
//			}else{
//				throw new IllegalArgumentException("Wrong string: " + fieldsString);
//			}
		}
		return fields;
	}
	

	/**
	 * �ۺ��ֶΡ�
	 * <p>
	 * name Ϊ�ۺ����ƣ��� ��count(*)����
	 * alias Ϊ����,�硰countA����
	 */
	public static class AggregateField{
		private final String name;
		private final String alias;
		public AggregateField(String name, String alias) {
			if(name == null || "".equals(name.trim())){
				throw new IllegalArgumentException("empty name");
			}
			if(alias == null || "".equals(alias.trim())){
				throw new IllegalArgumentException("empty alias");
			}
			
			this.name = name;
			this.alias = alias;
		}
		/**
		 * �ֶ����ƻ��߾ۺϺ������ݣ���count(*)��
		 * 
		 * @return
		 */
		public String getName() {
			return name;
		}
		/**
		 * ������
		 * @return
		 */
		public String getAlias() {
			return alias;
		}
		/**
		 * �ֶε��ַ�����ʽ��
		 */
		public String toString(){
			return name + " as " + alias;
		}
	}
	
	public static void main(String[] args){
		AggregateField[] fields2 = Aggregation.aggregateFieldsStringToArray("(sum(reportTime) + sum(b)) as   tt, a as bb, c as cc");
		for (AggregateField field : fields2) {
			System.out.println(field.name + " --> " + field.alias);
		}
		//Aggregation info = new Aggregation("count(*) as cnt, sum(a) as bb, sum(c) as cc", "entityId", "entityName");
		//System.out.println(info);
		//System.out.println(info.getGroupFieldsString());
		
		String f = "count(*) AS cnt";
		String lower = f.toLowerCase();
		int i = lower.indexOf(" as ");
		String s = f.substring(0, i);
		System.out.println(s);
		System.out.println(f.substring(i+4));
	}
}
