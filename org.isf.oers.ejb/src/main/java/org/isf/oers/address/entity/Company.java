package org.isf.oers.address.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.isf.oers.address.ContactType;

@Entity
@DiscriminatorValue("company")
public class Company extends Contact {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4788957330017059421L;

	private String vatRegistrationNo;
	private String taxRegistrationNo;
	private CompanyType companyType;
	
	public Company() {
		super();
		setType(ContactType.COMPANY);
	}
	
	@Column(name="VATREGNO", length=40)
	public String getVatRegistrationNo() {
		return vatRegistrationNo;
	}
	public void setVatRegistrationNo(String vatRegistrationNo) {
		this.vatRegistrationNo = vatRegistrationNo;
	}
	
	@Column(name="TAXREGNO", length=40)
	public String getTaxRegistrationNo() {
		return taxRegistrationNo;
	}
	public void setTaxRegistrationNo(String taxRegistrationNo) {
		this.taxRegistrationNo = taxRegistrationNo;
	}
	
	@ManyToOne
	@JoinColumn(name="COMPANYTYPEID")
	public CompanyType getCompanyType() {
		return companyType;
	}
	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}
	
}
