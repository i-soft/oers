package org.isf.oers;

import java.util.Iterator;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingException;

import org.isf.commons.dto.DTO;
import org.isf.commons.dto.DTOFactory;

public abstract class OERSClient {

	private Context ctx;
	private IOERSSession session;
	
	@Inject @DTO
	private Instance<Object> dtoList;
	
	protected abstract Context createContext() throws NamingException;
	
	public Context getContext() { return ctx; }
	
	protected void initialize() throws NamingException {
		ctx = createContext();
		
		if (dtoList != null) {
			Iterator<Object> it = dtoList.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				try {
					DTOFactory.createDTOSupportClass(obj.getClass());
				} catch(Exception e) {
					e.printStackTrace();
					throw new NamingException("Could not create DTOSupport for Class '"+obj.getClass()+"' [root cause: "+e+"]");
				}
			}
		}
	}
	
	public <T> T lookup(Class<T> clazz, String name) throws NamingException {
		return clazz.cast(getContext().lookup(name));
	}
	
	public IOERSSession getOERSSession() throws NamingException {
		if (session == null) session = lookup(IOERSSession.class, "org.isf.oers-ear/oers-ejb/OERSSession!"+IOERSSession.class.getName()); // TODO separate in module and so on
		return session;
	}
	
	public Context connect() throws NamingException {
		initialize();
		return getContext();
	}
	
	public void close() throws NamingException {
		if (session != null) session.close();
		try { getContext().close(); } catch(Exception e) {}
	}
	
}
