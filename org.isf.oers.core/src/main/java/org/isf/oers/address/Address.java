package org.isf.oers.address;

import java.io.Serializable;
import java.util.Date;

import org.isf.commons.dto.DTO;
import org.isf.commons.dto.DTOAttribute;
import org.isf.oers.dto.DTOId;

@DTO
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6787046212752748780L;

	private Long id;
	private ContactType contactType = ContactType.COMPANY;
	
	private String companyName1;
	private String companyName2;
	private String companyName3;
	private String vatRegistrationNo;
	private String taxRegistrationNo;
	private String companyType;
	
	private String name;
	private String firstname;
	private Date dateOfBirth;
	private String title;
	private GenderType gender = GenderType.NONE;
	
	private LocationType locationType;
	
	private String location;
	private String zip;
	private String city;
	
	private String phone;
	private String fax;
	private String mobile;
	private String mail;
	private String website;
	
	@DTOId
	@DTOAttribute("id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@DTOAttribute("type")
	public ContactType getContactType() {
		return contactType;
	}
	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}
	
	@DTOAttribute("name1")
	public String getCompanyName1() {
		return companyName1;
	}
	public void setCompanyName1(String companyName1) {
		this.companyName1 = companyName1;
	}
	
	@DTOAttribute("name2")
	public String getCompanyName2() {
		return companyName2;
	}
	public void setCompanyName2(String companyName2) {
		this.companyName2 = companyName2;
	}
	
	@DTOAttribute("name3")
	public String getCompanyName3() {
		return companyName3;
	}
	public void setCompanyName3(String companyName3) {
		this.companyName3 = companyName3;
	}
	
	@DTOAttribute("vatRegistrationNo")
	public String getVatRegistrationNo() {
		return vatRegistrationNo;
	}
	public void setVatRegistrationNo(String vatRegistrationNo) {
		this.vatRegistrationNo = vatRegistrationNo;
	}
	
	@DTOAttribute("taxRegistrationNo")
	public String getTaxRegistrationNo() {
		return taxRegistrationNo;
	}
	public void setTaxRegistrationNo(String taxRegistrationNo) {
		this.taxRegistrationNo = taxRegistrationNo;
	}
	
	@DTOAttribute("companyType.name")
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	
	@DTOAttribute("name1")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@DTOAttribute("name2")
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	@DTOAttribute("dateOfBirth")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	@DTOAttribute("title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@DTOAttribute("gender")
	public GenderType getGender() {
		return gender;
	}
	public void setGender(GenderType gender) {
		this.gender = gender;
	}
	
	@DTOAttribute("locations[${main == true}].type")
	public LocationType getLocationType() {
		return locationType;
	}
	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}
	
	@DTOAttribute("locations[${main == true}].location")
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@DTOAttribute("locations[${main == true}].zip")
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@DTOAttribute("locations[${main == true}].city")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@DTOAttribute("locations[${main == true}].communications[${main == true && type == 'PHONE'}].value")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@DTOAttribute("locations[${main == true}].communications[${main == true && type == 'FAX'}].value")
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@DTOAttribute("locations[${main == true}].communications[${main == true && type == 'MOBILE'}].value")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@DTOAttribute("locations[${main == true}].communications[${main == true && type == 'MAIL'}].value")
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	@DTOAttribute("locations[${main == true}].communications[${main == true && type == 'WEBSITE'}].value")
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	
}
