package org.isf.oers.web.app;

import java.io.File;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.isf.oers.conf.AppConfiguration;
import org.isf.oers.conf.AppConfigurationReader;
import org.isf.oers.web.CONSTANTS;
import org.isf.oers.web.util.JSFUtil;
import org.jboss.logging.Logger;

@Named("oersapp")
@ApplicationScoped
public class Application implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4353479702504017181L;
	
	@Inject
	private Logger log;
	
	private AppConfiguration config;
	
	public Application() {}
	
	@PostConstruct
	public void initial() {
		try {
			File cfg = new File(JSFUtil.getInitParameter(CONSTANTS.APP_CONFIG)+File.separator+CONSTANTS.APP_CONFIG_FILE);
			log.info("config-fie: "+cfg.getAbsolutePath());
			AppConfigurationReader reader = new AppConfigurationReader(cfg);
			config = reader.read();
		} catch(Exception e) {
			log.error("ERROR while initial \"Application\".", e);
		}
	}
	
	public AppConfiguration getConfiguration() { return config; }
}
