package org.isf.oers;

import java.util.List;
import java.util.Map;

import org.isf.oers.auth.User;
import org.isf.oers.module.Module;

public interface IOERSSession {

	// Module-Section
	public Map<String, Module> modules();
	public List<Module> listModules();
	public Module getModule(String name);
	
	// User-Section
	public User getUser();
	public boolean changePassword(String oldPassword, String newPassword);
	
	
	
	public void close();
}
