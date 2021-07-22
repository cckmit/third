package org.opoo.apps.dao;

import java.util.List;

import org.opoo.apps.Labelable;
import org.opoo.ndao.support.ResultFilter;


public interface LabelableDao {
	/**
	 * 
	 * @param rf
	 * @return
	 */
	List<Labelable> findLabelables(ResultFilter rf);
	/**
	 * 
	 * @return
	 */
	//List<Labelable> findLabelables();
}
