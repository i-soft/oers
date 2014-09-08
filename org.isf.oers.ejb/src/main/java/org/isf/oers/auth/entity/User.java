package org.isf.oers.auth.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.isf.oers.CONSTANTS;


@Entity
@DiscriminatorValue("user")
public class User extends AuthObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 658496756670224924L;

	private String password;
	private List<Role> roles = new ArrayList<Role>();
	
	public User() { super(); }
	public User(String name) { this(); setName(name); }
	public User(String name, String password) { this(name); setPassword(password); }
	
	@Column(name="PASSWORD",length=20)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@ManyToMany
	@JoinTable(name=CONSTANTS.SYSTEM_PREFIX+"USERROLES", schema=CONSTANTS.SYSTEM_SCHEMA, 
			   joinColumns={@JoinColumn(name="USERID")},
			   inverseJoinColumns={@JoinColumn(name="ROLEID")})
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
