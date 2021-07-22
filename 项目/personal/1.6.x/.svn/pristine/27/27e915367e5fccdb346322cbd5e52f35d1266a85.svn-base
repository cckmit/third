package org.opoo.apps.security.impl;

import java.util.Collections;
import java.util.List;

import org.opoo.apps.security.Presence;
import org.opoo.apps.security.OnlineUser;
import org.opoo.apps.security.User;
import org.opoo.apps.security.UserWrapper;
import org.opoo.apps.security.Presence.LastUpdateTimeComparator;
import org.springframework.util.Assert;

public class OnlineUserImpl extends UserWrapper implements OnlineUser {
	private static final long serialVersionUID = 162377525828666727L;
	private static final LastUpdateTimeComparator COMPARATOR = new LastUpdateTimeComparator(false);
	private List<Presence> presences;
	
	public OnlineUserImpl(User user,List<Presence> presences) {
		super(user);
		
		Assert.notEmpty(presences, "presences must not be empty: it must contain at least 1 element.");
		Collections.sort(presences, COMPARATOR);
		this.presences = Collections.unmodifiableList(presences);
	}
	
	public List<Presence> getPresences() {
		return presences;
	}
	
	public void setPresences(List<Presence> presences) {
		this.presences = presences;
	}

	public Presence getLastPresence(){
		return presences.get(0);
	}
}
