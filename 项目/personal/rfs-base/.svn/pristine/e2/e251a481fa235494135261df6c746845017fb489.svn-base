package cn.redflagsoft.base.vo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.SendingMsg;


/**
 * @author 
 *
 */
public class SendingMsgVO extends SendingMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Clerk> receiverList;

	public List<Clerk> getReceiverList() {
		return receiverList;
	}

	public void setReceiverList(List<Clerk> receiverList) {
		this.receiverList = receiverList;
	}
	public SendingMsgVO (SendingMsg s,List<Clerk> rl){    
	    try {
			PropertyUtils.copyProperties(this, s);
			this.receiverList=rl;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
    } 
	
}
