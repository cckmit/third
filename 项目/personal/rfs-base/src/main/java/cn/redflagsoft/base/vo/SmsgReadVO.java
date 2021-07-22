package cn.redflagsoft.base.vo;

import cn.redflagsoft.base.bean.Smsg;
import cn.redflagsoft.base.bean.SmsgReceiver;


/**
 * ��Ե��������˵���Ϣ�ͽ����˶���
 * 
 * @author lcj
 *
 */
public class SmsgReadVO {
	private Smsg smsg;
	private SmsgReceiver smsgReceiver;

	
	/**
	 * @param smsg
	 * @param smsgReceiver
	 */
	public SmsgReadVO(Smsg smsg, SmsgReceiver smsgReceiver) {
		super();
		this.smsg = smsg;
		this.smsgReceiver = smsgReceiver;
	}

	/**
	 * 
	 */
	public SmsgReadVO() {
		super();
	}

	public Smsg getSmsg() {
		return smsg;
	}

	public void setSmsg(Smsg smsg) {
		this.smsg = smsg;
	}

	public SmsgReceiver getSmsgReceiver() {
		return smsgReceiver;
	}

	public void setSmsgReceiver(SmsgReceiver smsgReceiver) {
		this.smsgReceiver = smsgReceiver;
	}
}
