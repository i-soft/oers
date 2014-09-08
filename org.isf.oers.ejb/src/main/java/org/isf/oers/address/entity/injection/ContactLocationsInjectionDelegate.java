package org.isf.oers.address.entity.injection;

import java.util.List;

import org.isf.commons.bean.AttributeInjectionData;
import org.isf.commons.bean.IAttributeInjectionDelegate;
import org.isf.oers.address.entity.Address;
import org.isf.oers.address.entity.Location;

public class ContactLocationsInjectionDelegate implements
		IAttributeInjectionDelegate {

	@SuppressWarnings("unchecked")
	@Override
	public Object injectAttribute(AttributeInjectionData injection)
			throws Exception {
		List<Location> l = (List<Location>)injection.getData();
		if (l.size() == 0) {
			Location lo = new Address();
			lo.setMain(true);
			l.add(lo);
		}
		return l;
	}

}
