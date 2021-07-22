/**
 * 
 */
package cn.redflagsoft.base.vo;

import java.io.Serializable;
import java.util.List;

import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.SmsgReceiver;

/**
 * @author lcj
 *
 */
public class SmsgVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6903730617273345065L;
	
	private Smsg msg;
	private List<SmsgReceiver> receivers;
	/**
	 * 
	 */
	public SmsgVO() {
		super();
	}
	/**
	 * @param msg
	 * @param receivers
	 */
	public SmsgVO(Smsg msg, List<SmsgReceiver> receivers) {
		super();
		this.msg = msg;
		this.receivers = receivers;
	}
	/**
	 * @return the msg
	 */
	public Smsg getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(Smsg msg) {
		this.msg = msg;
	}
	/**
	 * @return the receivers
	 */
	public List<SmsgReceiver> getReceivers() {
		return receivers;
	}
	/**
	 * @param receivers the receivers to set
	 */
	public void setReceivers(List<SmsgReceiver> receivers) {
		this.receivers = receivers;
	}
}
