package org.isf.oers.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.isf.oers.auth.entity.Role;
import org.isf.oers.auth.entity.User;
import org.isf.oers.module.Module;
import org.isf.oers.module.ModuleDeclaration;
import org.isf.oers.module.ModuleHandler;
import org.isf.oers.module.ModuleHandlerDef;
import org.jboss.logging.Logger;

@Singleton
@Startup
public class OERSBackgroudService {

	@Inject
	private Logger log;
	
	@Inject @ModuleHandlerDef
	private Instance<ModuleHandler> moduleHandler;
	
	@PersistenceContext(unitName="OERSUnit")
	private EntityManager em;
	
	private Map<String, Module> modules;
	
	private void checkAdminAccount() {
		List<Role> lr = em.createQuery("from Role a where a.name = 'oers-user'", Role.class).getResultList();
		Role rusers = lr.size() > 0 ? lr.get(0) : em.merge(new Role("oers-user"));
		lr = em.createQuery("from Role a where a.name = 'oers-admin'", Role.class).getResultList();
		Role radmin = lr.size() > 0 ? lr.get(0) : em.merge(new Role("oers-admin"));
		List<User> lu = em.createQuery("from User a where a.name = 'admin'", User.class).getResultList();
		User u = null;
		if (lu.size() == 0) {
			u = em.merge(new User("admin", "admin123"));
		} else u = lu.get(0);
		boolean umod = false;
		if (!u.getRoles().contains(rusers)) {
			umod = true;
			u.getRoles().add(rusers);
		}
		if (!u.getRoles().contains(radmin)) {
			umod = true;
			u.getRoles().add(radmin);
		}
		if (umod) u = em.merge(u);
	}
	
	private void initialModules() {
//		modules = Collections.synchronizedMap(new HashMap<String, ModuleDeclaration>());
		modules = new HashMap<String, Module>();
		int i = 0;
		for (ModuleHandler handler : moduleHandler.select()) {
			i++;
			Class<?> cls = handler.getClass();
			Module mo = new Module(cls);
			log.info("found handler: "+cls.getName());
			ModuleDeclaration md = handler.moduleDeclaration();
			mo.setDeclaration(md);
			if (md != null) {
				log.info("initialize module '"+md.getName()+"' handler: "+cls.getName());
			} else log.warn("ModuleDeclaration is null for handler: "+cls.getName());
			modules.put(md.getName(), mo);
		}
		log.info(i+" handler found...");
	}
	
	@PostConstruct
	public void create() {
		checkAdminAccount();
	}
	
	@Asynchronous
	public Future<Map<String, Module>> getModulesMap() {
		if (modules == null) initialModules();
		return new AsyncResult<Map<String,Module>>(modules); 
	}
	
}
