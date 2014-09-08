package org.isf.oers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.InjectionException;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.isf.commons.dto.DTOFactory;
import org.isf.oers.IOERSSession;
import org.isf.oers.auth.entity.User;
import org.isf.oers.module.Module;
import org.isf.oers.system.ejb.ISystemHandlerLocal;
import org.isf.oers.system.entity.Session;
import org.isf.oers.system.entity.SessionEndState;
import org.isf.oers.system.service.OERSBackgroudService;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;

@Stateful(name="OERSSession")
@SessionScoped
@Local(IOERSSessionLocal.class)
@Remote(IOERSSession.class)
@SecurityDomain("oers")
@RolesAllowed("oers-user")
public class OERSSessionBean implements IOERSSession, IOERSSessionLocal {

	@Inject
	private Logger log;
	
//	@Resource
//	private SessionContext ctx;
	
	@Resource
	private BeanManager beanManager;
	
	@PersistenceContext(unitName="OERSUnit")
	private EntityManager em;
	
	@EJB
	private ISystemHandlerLocal shl;
	
	@EJB
	private OERSBackgroudService backgroundService;
	
	private Session localSession;
	
	protected Session getSession() {
		if (localSession == null) localSession = shl.createSession();
		return localSession;
	}
	
	@SuppressWarnings("unchecked")  // TODO vielleicht als JPAUtil-Class?
	protected <T> T dynamicInject(Class<T> clazz) throws InjectionException {
		try {		
			Bean<T> bean = (Bean<T>)beanManager.getBeans(clazz).iterator().next();
			CreationalContext<T> ctx = beanManager.createCreationalContext(bean);
			return clazz.cast(beanManager.getReference(bean, clazz, ctx));
		} catch(Throwable t) {
			throw new InjectionException("Could not inject CDI BeanManager for Type '"+clazz.getName()+"'", t);
		}
	}

	@Override
	public Map<String, Module> modules() {
		Future<Map<String, Module>> res = backgroundService.getModulesMap();
		try {
			return res.get();
		} catch(Exception e) {
			return null;  // TODO handle and throw Exception
		}
	}
	
	@Override
	public List<Module> listModules() {
		List<Module> l = new ArrayList<Module>();
		for (Module m : modules().values())
			l.add(m);
		return l;
	}
	
	@Override
	public Module getModule(String name) {
		for (Module m : modules().values())
			if (m.getName().equals(name)) 
				return m;
		return null;
	}
	
//	@Override
//	public String getUsername() {
//		if (ctx.getCallerPrincipal() == null) return "anonymous";
//		else return ctx.getCallerPrincipal().getName();
//	}
	
	
	@Override
	public org.isf.oers.auth.User getUser() {
		try {
//			User u = shl.getUser();
			User u = getSession().getUser();
			return DTOFactory.marshallDTO(org.isf.oers.auth.User.class, u);
		} catch(Exception e) {
			return new org.isf.oers.auth.User();
		}
	}
	
	@Override
	public boolean changePassword(String oldPassword, String newPassword) {
		try {
			User u = em.createQuery("from User a where a.name = :name and password = :password", User.class).setParameter("name", getUser().getUsername()).setParameter("password", oldPassword).getSingleResult();
			u.setPassword(newPassword);
			em.merge(u);
			return true;
		} catch(Exception e) {
			return false;
		}
	}	

	@PostConstruct
	public void create() {
//		log.info("Session create for user: "+getUsername());
//		log.info("Session create for user: "+getSession().getUser().getName());
	}
	
	@PostActivate
	public void activate() {
//		log.info("Session activated for user: "+getUsername());
//		log.info("Session activated for user: "+getSession().getUser().getName());
	}
	
	@PrePassivate
	public void passivate() {
//		log.info("Session passivated for user: "+getUsername());
//		log.info("Session passivated for user: "+getSession().getUser().getName());
	}
	
	@PreDestroy
	public void destroy() {
//		log.info("Session destroyed for user: "+getUsername());
		if (getSession().getEndState() == SessionEndState.UNKNOWN) {
			getSession().setEndState(SessionEndState.SUCCESSFUL);
			getSession().setEndedAt(new Date());
			shl.saveSession(getSession());
//			log.info("Session destroyed for user: "+getSession().getUser().getName());		
		}		
	}
	
	@Override
	@Remove
	public void close() {
		if (getSession().getEndState() == SessionEndState.UNKNOWN) {
			getSession().setEndState(SessionEndState.SUCCESSFUL);
			getSession().setEndedAt(new Date());
			shl.saveSession(getSession());
//			log.info("Session closed for user: "+getSession().getUser().getName());
		}
	}
	
}
