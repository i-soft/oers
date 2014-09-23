package org.isf.oers.system.service;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.jboss.logging.Logger;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class OERSConcurrentTinyCache {

	private ConcurrentHashMap<String, Object> tinyCache;
	
	@Inject
	private Logger log;
	
	@PostConstruct
	public void initial() {
		tinyCache = new ConcurrentHashMap<String, Object>();
	}
	
	public Object getCachedData(String key) {
		log.info("call key: "+key);
		return tinyCache.get(key);
	}
	
	public void putCachedData(String key, Object data) {
		log.info("put data for key: "+key);
		tinyCache.put(key, data);
	}
	
	public boolean hasCachedData(String key) {
		log.info("check key: "+key+" ["+tinyCache.containsKey(key)+"]");
		return tinyCache.containsKey(key);
	}
	
}
