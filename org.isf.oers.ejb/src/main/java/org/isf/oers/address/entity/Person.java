package org.isf.oers.address.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.isf.oers.address.ContactType;
import org.isf.oers.address.GenderType;

@Entity
@DiscriminatorValue("person")
public class Person extends Contact {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2790280188089879513L;

	private Date dateOfBirth;
	private String title;
	private GenderType gender = GenderType.MALE;
	
	public Person() {
		super();
		setType(ContactType.PERSON);
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATEOFBIRTH")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	@Column(name="TITLE", length=40)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="GENDER", length=20)
	public GenderType getGender() {
		return gender;
	}
	public void setGender(GenderType gender) {
		this.gender = gender;
	}
	
}
