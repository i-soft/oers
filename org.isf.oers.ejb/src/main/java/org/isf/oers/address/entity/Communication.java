package org.isf.oers.address.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.isf.oers.CONSTANTS;
import org.isf.oers.address.CommunicationType;
import org.isf.oers.jpa.EntityData;

@Entity
@Table(name=CONSTANTS.ADDRESS_PREFIX+"COMMUNICATION",schema=CONSTANTS.ADDRESS_SCHEMA)
@TableGenerator(name=CONSTANTS.GENERATOR_NAME,pkColumnValue=CONSTANTS.ADDRESS_PREFIX+"COMMUNICATION_PK",schema=CONSTANTS.ADDRESS_SCHEMA,table=CONSTANTS.AUTOINC_TABLE_NAME)
public class Communication extends EntityData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5927407725521163843L;

	private Boolean main = false;
	private CommunicationType type;
	private String value;
	
	public Communication() { super(); }
	public Communication(CommunicationType type, String value) {
		this();
		setType(type);
		setValue(value);
	}
	public Communication(CommunicationType type, String value, Boolean main) {
		this(type,value);
		setMain(main);
	}
	
	@Column(name="MAIN",nullable=false)
	public Boolean getMain() {
		return main;
	}
	public void setMain(Boolean main) {
		this.main = main;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="COMTYPE", length=20)
	public CommunicationType getType() {
		return type;
	}
	public void setType(CommunicationType type) {
		this.type = type;
	}
	
	@Column(name="COMVALUE", length=80)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
