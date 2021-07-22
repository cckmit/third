package org.opoo.ndao.support;

import java.io.Serializable;

import org.opoo.ndao.criterion.Criterion;
import org.opoo.ndao.criterion.Restrictions;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class CriterionBuffer implements Serializable{
	private static final long serialVersionUID = 3556800380103806696L;
	
	private Criterion criterion;
	public CriterionBuffer(){
	}
	
	public CriterionBuffer(Criterion c){
		criterion = c;
	}

	public CriterionBuffer and(Criterion c){
		if(c != null){
			if(criterion != null){
				criterion = Restrictions.logic(criterion).and(c);
			}else{
				criterion = c;
			}
		}
		return this;
	}
	public CriterionBuffer or(Criterion c){
		if(c != null){
			if(criterion != null){
				criterion = Restrictions.logic(criterion).or(c);
			}else{
				criterion = c;
			}
		}
		return this;
	}
	
	public CriterionBuffer and(CriterionBuffer c){
		if(c != null && c.criterion != null){
			if(criterion != null){
				criterion = Restrictions.logic(criterion).and(c.criterion);
			}else{
				criterion = c.criterion;
			}
		}
		return this;
	}
	
	public CriterionBuffer or(CriterionBuffer c){
		if(c != null && c.criterion != null){
			if(criterion != null){
				criterion = Restrictions.logic(criterion).or(c.criterion);
			}else{
				criterion = c.criterion;
			}
		}
		return this;
	}
	
	public Criterion toCriterion(){
		return criterion;
	}
}
