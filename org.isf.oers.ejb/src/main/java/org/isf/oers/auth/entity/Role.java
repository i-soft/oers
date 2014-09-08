package org.isf.oers.auth.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
@DiscriminatorValue("role")
public class Role extends AuthObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1096321819215960754L;

	private List<User> users = new ArrayList<User>();

	public Role() { super(); }
	public Role(String name) { this(); setName(name); }
	
	@ManyToMany(mappedBy="roles")
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}	
	
}
