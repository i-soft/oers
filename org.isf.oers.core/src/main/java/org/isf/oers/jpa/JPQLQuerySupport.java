package org.isf.oers.jpa;

import java.io.Serializable;
import java.util.List;

public interface JPQLQuerySupport extends Serializable {

	public List<JPQLColumn> columns();
	public List<JPQLOrder> orders();
	
}
