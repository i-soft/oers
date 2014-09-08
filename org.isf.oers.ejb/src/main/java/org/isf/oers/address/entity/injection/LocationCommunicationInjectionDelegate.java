package org.isf.oers.address.entity.injection;

import java.util.List;

import org.isf.commons.bean.AttributeInjectionData;
import org.isf.commons.bean.IAttributeInjectionDelegate;
import org.isf.oers.address.CommunicationType;
import org.isf.oers.address.entity.Communication;

public class LocationCommunicationInjectionDelegate implements
		IAttributeInjectionDelegate {

	protected static final CommunicationType[] TYPES = {CommunicationType.PHONE,CommunicationType.FAX, CommunicationType.MOBILE, CommunicationType.MAIL, CommunicationType.WEBSITE};
	
	protected boolean containsType(CommunicationType type, List<Communication> list) {
		for (Communication c : list)
			if (c.getType() == type) 
				return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object injectAttribute(AttributeInjectionData injection)
			throws Exception {
		List<Communication> l = (List<Communication>)injection.getData();
		if (l.size() < 4) {
			for (CommunicationType type : TYPES)
				if (!containsType(type, l)) {
					Communication c = new Communication();
					c.setType(type);
					c.setMain(true);
					l.add(c);
				}
		}
		return l;
	}

}
