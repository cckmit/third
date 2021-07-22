package cn.redflagsoft.base.event;

import java.math.BigDecimal;

import org.opoo.apps.event.Event;

import cn.redflagsoft.base.bean.RiskEntry;

/**
 * 
 * @author Alex Lin
 *
 */
public class RiskEntryEvent extends Event<RiskEntry> {

	public static final int VALUE_CHANGE = 1;
	
	
	private BigDecimal value;
	/**
	 * @param eventType
	 * @param source
	 */
	public RiskEntryEvent(int eventType, RiskEntry source, BigDecimal value) {
		super(eventType, source);
		this.value = value;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7336798812558229831L;

	
	public BigDecimal getValue(){
		return value;
	}
}
