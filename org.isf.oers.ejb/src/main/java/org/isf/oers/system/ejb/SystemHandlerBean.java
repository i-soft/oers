package org.isf.oers.system.ejb;

import java.util.UUID;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.isf.oers.auth.entity.User;
import org.isf.oers.system.entity.Session;
import org.jboss.ejb3.annotation.SecurityDomain;

@Stateless
@Local(ISystemHandlerLocal.class)
@SecurityDomain("oers")
public class SystemHandlerBean implements ISystemHandlerLocal {

	@PersistenceContext(unitName="OERSUnit")
	private EntityManager em;
	
	@Resource
	private SessionContext ctx;
	
	public String getUsername() {
		return (ctx.getCallerPrincipal() != null) ? ctx.getCallerPrincipal().getName() : "anonymous"; 
	}
	
	@Override
	@RolesAllowed("oers-user")
	public User getUser() {
		return em.createQuery("from User a where a.name = :name", User.class).setParameter("name", getUsername()).getSingleResult();
	}
	
	@Override
	@RolesAllowed("oers-user")
//	@PermitAll
	public Session createSession() {
		Session session = new Session(UUID.randomUUID().toString());
		try {
			session.setUser(getUser());
			return em.merge(session);
		} finally {
			em.flush();
		}
	}

	@Override
//	@RolesAllowed("oers-user")
	@PermitAll
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveSession(Session session) {
		em.merge(session);
		em.flush();
	}

	

}
