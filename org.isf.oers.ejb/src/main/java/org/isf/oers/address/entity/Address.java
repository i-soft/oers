package org.isf.oers.address.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.isf.oers.address.LocationType;

@Entity
@DiscriminatorValue("address")
public class Address extends Location {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7237487298275320400L;

	public Address() {
		super();
		setType(LocationType.ADDRESS);
	}

}
