/**
 * 
 */
package org.opoo.apps.ws.types;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

/**
 * @author lcj
 *
 */
@XmlType(name="ResultFilter")
public class WSResultFilter {
	private int firstResult = -1;
	private int maxResults = 0;
	private WSCriterion criterion;
	private List<WSOrder> orders;
	
	public WSResultFilter(WSCriterion criterion, List<WSOrder> orders) {
		super();
		this.criterion = criterion;
		this.orders = orders;
	}
	public WSResultFilter(int firstResult, int maxResults) {
		super();
		this.firstResult = firstResult;
		this.maxResults = maxResults;
	}
	public WSResultFilter(int firstResult, int maxResults,
			WSCriterion criterion, List<WSOrder> orders) {
		super();
		this.firstResult = firstResult;
		this.maxResults = maxResults;
		this.criterion = criterion;
		this.orders = orders;
	}
	public WSResultFilter() {
		super();
	}
	public int getFirstResult() {
		return firstResult;
	}
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}
	public int getMaxResults() {
		return maxResults;
	}
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
	public WSCriterion getCriterion() {
		return criterion;
	}
	public void setCriterion(WSCriterion criterion) {
		this.criterion = criterion;
	}
	public List<WSOrder> getOrders() {
		return orders;
	}
	public void setOrders(List<WSOrder> orders) {
		this.orders = orders;
	}
}
