package org.isf.oers.jpa;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class ExtendableEntityData extends EntityData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7692595081246811399L;
	
	private String extFields;

	@Lob
	@Column(name="EXTFIELDS")
	public String getExtFields() {
		return extFields;
	}
	public void setExtFields(String extFields) {
		this.extFields = extFields;
	}
	
	
	
}
