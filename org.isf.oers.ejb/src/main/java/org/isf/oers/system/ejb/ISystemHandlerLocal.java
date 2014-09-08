package org.isf.oers.system.ejb;

import org.isf.oers.auth.entity.User;
import org.isf.oers.system.entity.Session;

public interface ISystemHandlerLocal {
	
	public User getUser();

	public Session createSession();
	public void saveSession(Session session);
	
}
