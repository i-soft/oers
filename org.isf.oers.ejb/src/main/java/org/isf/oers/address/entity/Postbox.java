package org.isf.oers.address.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.isf.oers.address.LocationType;

@Entity
@DiscriminatorValue("postbox")
public class Postbox extends Location {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3166348005960572812L;

	public Postbox() {
		super();
		setType(LocationType.POSTBOX);
	}

}
