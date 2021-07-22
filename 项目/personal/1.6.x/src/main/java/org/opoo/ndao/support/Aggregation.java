package org.opoo.ndao.support;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 聚合查询规则。
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
	 * 设置分组字段，可以为空。
	 * @param groupFields
	 */
	public void setGroupFields(String[] groupFields) {
//		if(ArrayUtils.isEmpty(groupFields)){
//			throw new IllegalArgumentException("Empty groupFields");
//		}
		this.groupFields = groupFields;
	}

	/**
	 * 设置聚合字段，不能为空。
	 * @param fields
	 */
	public void setAggregateFields(AggregateField[] fields) {
		if(ArrayUtils.isEmpty(fields)){
			throw new IllegalArgumentException("Empty fields");
		}
		this.aggregateFields = fields;
	}

	/**
	 * 获取分组字段。
	 * @return
	 */
	public String[] getGroupFields() {
		return groupFields;
	}

	/**
	 * 获取聚合字段。
	 * @return
	 */
	public AggregateField[] getAggregateFields() {
		return aggregateFields;
	}
	
	/**
	 * 获取聚合字段组成的查询字符串（带别名）。
	 * @return
	 */
	protected String getFullAggregateFieldsString(){
		return StringUtils.join(aggregateFields, ", ");
	}
	
	/**
	 * 获取分组字段组成的查询字符串（带别名）。
	 * @return 如果分组字段为空或者个数为0，返回 null。
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
	 * 获取分组字段组成的字符串（用与 group by，无别名）。
	 * @return
	 */
	public String getGroupFieldsString(){
		if(ArrayUtils.isEmpty(groupFields)){
			return null;
		}
		return StringUtils.join(groupFields, ", ");
	}
	
	/**
	 * 返回由分组字段和聚合字段组成的查询字符串。
	 * @return 查询字符串，带别名
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
	 * 将字符串解析为聚合字段数组。
	 * 
	 * @param fieldsString 输入字符串，如 count(*) as myCount, sum(a) as sumA
	 * @return 聚合字段数组
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
					throw new IllegalArgumentException("必须设置别名：" +f);
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
//					throw new IllegalArgumentException("必须设置别名：" + strings[0]);
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
	 * 聚合字段。
	 * <p>
	 * name 为聚合名称，如 “count(*)”。
	 * alias 为别名,如“countA”。
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
		 * 字段名称或者聚合函数内容，如count(*)。
		 * 
		 * @return
		 */
		public String getName() {
			return name;
		}
		/**
		 * 别名。
		 * @return
		 */
		public String getAlias() {
			return alias;
		}
		/**
		 * 字段的字符串格式。
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
