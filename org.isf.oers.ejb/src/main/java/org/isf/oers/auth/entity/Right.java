package org.isf.oers.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.isf.oers.CONSTANTS;
import org.isf.oers.jpa.EntityData;

@Entity
@Table(name=CONSTANTS.SYSTEM_PREFIX+"RIGHT",schema=CONSTANTS.SYSTEM_SCHEMA)
@TableGenerator(name=CONSTANTS.GENERATOR_NAME,pkColumnValue=CONSTANTS.SYSTEM_PREFIX+"RIGHT_PK",schema=CONSTANTS.SYSTEM_SCHEMA,table=CONSTANTS.AUTOINC_TABLE_NAME)
public class Right extends EntityData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7921342999775114023L;
	
	private String name;
	private String description;
	
	@Column(name="NAME", length=200, nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Lob
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
