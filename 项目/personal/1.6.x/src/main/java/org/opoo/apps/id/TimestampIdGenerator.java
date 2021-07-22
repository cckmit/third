package org.opoo.apps.id;


/**
 * 使用时间戳为基础的的ID生成器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class TimestampIdGenerator implements LongIdGenerator {
	private long start = System.currentTimeMillis();
	private int i = 0;
	public TimestampIdGenerator(){
	}
	public synchronized Long getNext(){
		i++;
		return start + i;
	}
	public String toString(){
		return String.valueOf(start + i);
	}
	
	public Long getCurrent(){
		return start + i;
	}
	
	public static void main(String[] args){
		TimestampIdGenerator gen = new TimestampIdGenerator();
		System.out.println(gen.getNext());
		System.out.println(gen.getNext());
		System.out.println(gen.getNext());
	}
}
