package org.opoo.apps;

import java.io.Serializable;


/**
 *  
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class LabeledBean implements Labelable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2491131768404448503L;
	private Serializable data;
	private String label;
	
	public LabeledBean(String label, Serializable data) {
		super();
		this.data = data;
		this.label = label;
	}
	public LabeledBean() {
		super();
	}
	
	/**
	 * @return the data
	 */
	public Serializable getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Serializable data) {
		this.data = data;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	public String toString(){
		return super.toString() + "(" + label + ": " + data + ")";
	}
}
