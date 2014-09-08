package org.isf.oers.address.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.isf.oers.CONSTANTS;
import org.isf.oers.jpa.EntityData;

@Entity
@Table(name=CONSTANTS.ADDRESS_PREFIX+"COMPANYTYPE",schema=CONSTANTS.ADDRESS_SCHEMA)
@TableGenerator(name=CONSTANTS.GENERATOR_NAME,pkColumnValue=CONSTANTS.ADDRESS_PREFIX+"COMPANYTYPE_PK",schema=CONSTANTS.ADDRESS_SCHEMA,table=CONSTANTS.AUTOINC_TABLE_NAME)
public class CompanyType extends EntityData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1183666302350989861L;

	private String name;
	
	@Column(name="NAME",length=20)
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
}
