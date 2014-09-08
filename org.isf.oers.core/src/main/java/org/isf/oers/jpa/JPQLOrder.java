package org.isf.oers.jpa;

import org.isf.commons.data.OrderDirection;

public interface JPQLOrder extends JPQLColumn {

	public OrderDirection getOrderDirection();
	
}
