package org.isf.oers.address.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.isf.commons.bean.AttributeInjection;
import org.isf.oers.CONSTANTS;
import org.isf.oers.address.ContactType;
import org.isf.oers.address.entity.injection.ContactLocationsInjectionDelegate;
import org.isf.oers.jpa.ExtendableEntityData;

@Entity
@Table(name=CONSTANTS.ADDRESS_PREFIX+"CONTACT",schema=CONSTANTS.ADDRESS_SCHEMA)
@TableGenerator(name=CONSTANTS.GENERATOR_NAME,pkColumnValue=CONSTANTS.ADDRESS_PREFIX+"CONTACT_PK",schema=CONSTANTS.ADDRESS_SCHEMA,table=CONSTANTS.AUTOINC_TABLE_NAME)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE",length=10)
public class Contact extends ExtendableEntityData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7958392356683220086L;

	private String name1;
	private String name2;
	private String name3;
	private ContactType type;
	private List<Location> locations = new ArrayList<Location>();
	
	public Contact() { super(); }
	
	@Column(name="NAME1", length=80)
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	
	@Column(name="NAME2", length=80)
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	
	@Column(name="NAME3", length=80)
	public String getName3() {
		return name3;
	}
	public void setName3(String name3) {
		this.name3 = name3;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="CONTACTTYPE", length=20)
	public ContactType getType() {
		return type;
	}
	public void setType(ContactType type) {
		this.type = type;
	}
	
	@OneToMany
	@JoinColumn(name="CONTACTID")
	@AttributeInjection(ContactLocationsInjectionDelegate.class)
	public List<Location> getLocations() {
		return locations;
	}
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
		
}
