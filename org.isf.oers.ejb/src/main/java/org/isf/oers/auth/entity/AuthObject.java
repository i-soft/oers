package org.isf.oers.auth.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.isf.oers.CONSTANTS;
import org.isf.oers.jpa.EntityData;

@Entity
@Table(name=CONSTANTS.SYSTEM_PREFIX+"AUTH", schema=CONSTANTS.SYSTEM_SCHEMA)
@TableGenerator(name=CONSTANTS.GENERATOR_NAME,pkColumnValue=CONSTANTS.SYSTEM_PREFIX+"AUTH_PK",schema=CONSTANTS.SYSTEM_SCHEMA,table=CONSTANTS.AUTOINC_TABLE_NAME)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE", length=10)
public class AuthObject extends EntityData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4547766410543312987L;

	private String name;
	private List<Privilege> privileges = new ArrayList<Privilege>();
	
	@Column(name="NAME", length=40, nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="USERID")
	public List<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}
	
}
