package org.opoo.apps.security;

import java.util.List;


public interface OnlineUser extends User {
	
	/**
	 * 
	 * @return
	 */
	List<Presence> getPresences();
	
	
	/**
	 * 
	 * @return
	 */
	Presence getLastPresence();
}
