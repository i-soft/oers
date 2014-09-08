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
import org.isf.oers.address.LocationType;
import org.isf.oers.address.entity.injection.LocationCommunicationInjectionDelegate;
import org.isf.oers.jpa.ExtendableEntityData;

@Entity
@Table(name=CONSTANTS.ADDRESS_PREFIX+"LOCATION",schema=CONSTANTS.ADDRESS_SCHEMA)
@TableGenerator(name=CONSTANTS.GENERATOR_NAME,pkColumnValue=CONSTANTS.ADDRESS_PREFIX+"LOCATION_PK",schema=CONSTANTS.ADDRESS_SCHEMA,table=CONSTANTS.AUTOINC_TABLE_NAME)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE",length=10)
public abstract class Location extends ExtendableEntityData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5258134112532987698L;

	private String location;
	private String zip;
	private String city;
	private LocationType type;
	private Boolean main;
	private List<Communication> communications = new ArrayList<Communication>();
	
	public Location() { super(); }
	
	@Column(name="LOCATION",length=80)
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Column(name="ZIP", length=10)
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@Column(name="CITY", length=80)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="LOCATIONTYPE", length=20)
	public LocationType getType() {
		return type;
	}
	public void setType(LocationType type) {
		this.type = type;
	}
	
	@Column(name="MAIN",nullable=false)
	public Boolean getMain() {
		return main;
	}
	public void setMain(Boolean main) {
		this.main = main;
	}
	
	@OneToMany
	@JoinColumn(name="ADDRESSID")
	@AttributeInjection(LocationCommunicationInjectionDelegate.class)
	public List<Communication> getCommunications() {
		return communications;
	}
	public void setCommunications(List<Communication> communications) {
		this.communications = communications;
	}
	
}
