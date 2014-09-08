package org.isf.oers.test.ejb;

import java.security.Principal;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.isf.oers.test.ITestHandlerRemote;
import org.jboss.ejb3.annotation.SecurityDomain;

@Stateless
@Local(ITestHandlerLocal.class)
@Remote(ITestHandlerRemote.class)
@SecurityDomain("oers")
@RolesAllowed("oers-user")
public class TestHandlerBean implements ITestHandlerLocal,
		ITestHandlerRemote {

	@Resource
	private SessionContext ctx;
	
	protected String getUsername() {
		Principal p = ctx.getCallerPrincipal();
		return p != null ? p.getName() : "(none)";
	}
	
	@Override
	public void hello() {
		System.out.println("Hello "+getUsername()+"!");
	}

}
