package cn.redflagsoft.base.audit;


import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;

import cn.redflagsoft.base.security.UserClerk;

/**
 * Models the permanent record of a given action in the appication.
 */
public interface AuditMessage extends Serializable {

	/**
	 * Return the message id.
	 * @return
	 */
	long getId();
	
    /**
     * Returns the user who performed the action which resulted in this message.
     *
     * @return the user who performed the action which resulted in this message.
     */
    public UserClerk getUser();

    /**
     * Returns the time when the action occurred.
     *
     * @return the time when the action occurred.
     */
    public Date getTimestamp();

    /**
     * Returns the address of the node where the action occurred.
     *
     * @return the address of the node where the action occurred.
     */
    public InetAddress getNode();

    /**
     * Returns a short description or logical name of the action.
     *
     * @return a short description or logical name of the action.
     */
    public String getDescription();

    /**
     * Returns the long description of the action and any relevant details such as configuration value changes.
     *
     * @return the long description of the action and any relevant details such as configuration value changes.
     */
    public String getDetails();

}
