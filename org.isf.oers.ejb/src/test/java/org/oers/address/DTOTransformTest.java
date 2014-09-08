package org.oers.address;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.hibernate.validator.util.privilegedactions.GetConstructor;
import org.isf.commons.bean.SegmentEncoder;
import org.isf.commons.dto.DTOFactory;
import org.isf.oers.address.CommunicationType;
import org.isf.oers.address.GenderType;
import org.isf.oers.address.LocationType;
import org.isf.oers.address.entity.Address;
import org.isf.oers.address.entity.Communication;
import org.isf.oers.address.entity.Contact;
import org.isf.oers.address.entity.Location;
import org.isf.oers.address.entity.Person;
import org.junit.Test;

public class DTOTransformTest {

	public static Person createPerson1() {
		Person p = new Person();
		p.setName1("Pape");
		p.setName2("Andreas");
		p.setDateOfBirth(new GregorianCalendar(1979, 4, 16).getTime());
		p.setGender(GenderType.MALE);
		
		Address adr = new Address();
		adr.setMain(true);
		adr.setLocation("Alte Dorfstr. 15");
		adr.setZip("27404");
		adr.setCity("Zeven-Badenstedt");
		
		adr.getCommunications().add(new Communication(CommunicationType.PHONE, "04281/959272", true));
		adr.getCommunications().add(new Communication(CommunicationType.MAIL, "andreas@i-soft.org", true));
		adr.getCommunications().add(new Communication(CommunicationType.WEBSITE, "http://www.i-soft.org", true));
		
		p.getLocations().add(adr);
		
		return p;
	}
	
	public static void printCommunication(Communication c) {
		System.out.println("    communicationType: "+c.getType());
		System.out.println("    value: "+c.getValue());
		System.out.println("    main: "+c.getMain());
	}
	
	public static void printLocation(Location l) {
		System.out.println("  locationType: "+l.getType());
		System.out.println("  location: "+l.getLocation());
		System.out.println("  zip: "+l.getZip());
		System.out.println("  city: "+l.getCity());
		System.out.println("  main: "+l.getMain());
		for (Communication c : l.getCommunications())
			printCommunication(c);
	}
	
	public static void printContact(Contact c) {
		System.out.println("\nprint "+c.getClass().getName());
		System.out.println("contactType: "+c.getType());
		System.out.println("name1: "+c.getName1());
		System.out.println("name2: "+c.getName2());
		System.out.println("name3: "+c.getName3());
		if (c instanceof Person) {
			Person p = (Person)c;
			System.out.println("title: "+p.getTitle());
			System.out.println("birthdate: "+p.getDateOfBirth());
			System.out.println("gender: "+p.getGender());
		}
		for (Location l : c.getLocations())
			printLocation(l);
	}
	
	public static void printDTOAddress(org.isf.oers.address.Address dtoadr) {
		System.out.println("\nprint "+dtoadr.getClass().getName());
		System.out.println("contactType: "+dtoadr.getContactType());
		System.out.println("companyName1: "+dtoadr.getCompanyName1());
		System.out.println("companyName2: "+dtoadr.getCompanyName2());
		System.out.println("companyName3: "+dtoadr.getCompanyName3());
		System.out.println("vatRegNo: "+dtoadr.getVatRegistrationNo());
		System.out.println("taxRegNo: "+dtoadr.getTaxRegistrationNo());
		System.out.println("companyType: "+dtoadr.getCompanyType());
		System.out.println("name: "+dtoadr.getName());
		System.out.println("firstname: "+dtoadr.getFirstname());
		System.out.println("dateOfBirth: "+dtoadr.getDateOfBirth());
		System.out.println("title: "+dtoadr.getTitle());
		System.out.println("gender: "+dtoadr.getGender());
		System.out.println("locationType: "+dtoadr.getLocationType());
		System.out.println("location: "+dtoadr.getLocation());
		System.out.println("zip: "+dtoadr.getZip());
		System.out.println("city: "+dtoadr.getCity());
		System.out.println("phone: "+dtoadr.getPhone());
		System.out.println("fax: "+dtoadr.getFax());
		System.out.println("mobile: "+dtoadr.getMobile());
		System.out.println("mail: "+dtoadr.getMail());
		System.out.println("website: "+dtoadr.getWebsite());
	}
	
	@Test
	public void marshallDTO() throws Exception {
		Contact c = createPerson1();
		printContact(c);
 		org.isf.oers.address.Address dtoadr = DTOFactory.marshallDTO(org.isf.oers.address.Address.class, c);
 		dtoadr.setMobile("keine");
 		printDTOAddress(dtoadr);
// 		Contact nc = DTOFactory.unmarshallDTO(dtoadr, Person.class);
 		Contact nc = DTOFactory.unmarshallDTO(dtoadr, c);
 		printContact(nc);
	}

}
