package org.isf.oers.address.ejb;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.isf.oers.address.IAddressHandler;
import org.isf.oers.module.AbstractModuleHandler;
import org.isf.oers.module.ColumnDef;
import org.isf.oers.module.DeviceDef;
import org.isf.oers.module.ModuleHandlerDef;

@Stateless
@Local(IAddressHandlerLocal.class)
@Remote(IAddressHandler.class)
@ModuleHandlerDef(
		name="Addresses",
		description="-> some Description about the Addresses <-",
		entityClassName="org.isf.oers.address.entity.Contact",
		devices={
				@DeviceDef(
						name="Person",
						entityClassName="org.isf.oers.address.entity.Person",
						dtoClassName="org.isf.oers.address.Address",
						columns={
								@ColumnDef(name="name1",displayName="Name",displayOrder=1),								
								@ColumnDef(name="name2",displayName="Firstname",displayOrder=2),
								@ColumnDef(name="dateOfBirth",displayName="Birthdate",displayOrder=100),
								@ColumnDef(name="title",displayName="Title",displayOrder=100),
								@ColumnDef(name="gender",displayName="Gender",displayOrder=100)
						}
				),
				@DeviceDef(
						name="Company",
						entityClassName="org.isf.oers.address.entity.Company",
						dtoClassName="org.isf.oers.address.Address",
						columns={
								@ColumnDef(name="name1",displayName="Company (1)"),
								@ColumnDef(name="name2",displayName="Company (2)"),
								@ColumnDef(name="name3",displayName="Company (3)"),
								@ColumnDef(name="vatRegistrationNo",displayName="VAT Reg.No.",displayOrder=99),
								@ColumnDef(name="taxRegistrationNo",displayName="TAX Reg.No.",displayOrder=99),
								@ColumnDef(name="companyType.name",displayName="Companytype",displayOrder=99)
						}
				)
		},
		columns={
				@ColumnDef(name="locations.location", displayName="Street / Postbox", displayOrder=3),
				@ColumnDef(name="locations.zip", displayName="Zip", displayOrder=4),
				@ColumnDef(name="locations.city", displayName="City", displayOrder=5),
		}
)
public class AddressHandlerBean extends AbstractModuleHandler implements IAddressHandler, IAddressHandlerLocal {

	
	
}
