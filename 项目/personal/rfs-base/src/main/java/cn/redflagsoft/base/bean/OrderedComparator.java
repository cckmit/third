package cn.redflagsoft.base.bean;

import java.util.Comparator;

import org.springframework.core.Ordered;

/**
 * 排序对象的比较器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class OrderedComparator implements Comparator<Ordered> {
	
	private boolean reverse = false;
	public OrderedComparator(){
	}
	public OrderedComparator(boolean reverse){
		this.reverse = reverse;
	}
	public int compare(Ordered o1, Ordered o2) {
		return reverse ? (o2.getOrder() - o1.getOrder()) : (o1.getOrder() - o2.getOrder());
	}
	
	
	public static OrderedComparator getInstance(){
		return Holder.instance;
	}
	public static OrderedComparator getReverseInstance(){
		return Holder.reverseInstance;
	}
	
	private static class Holder{
		private static OrderedComparator instance = new OrderedComparator();
		private static OrderedComparator reverseInstance = new OrderedComparator(true);
	}
}
