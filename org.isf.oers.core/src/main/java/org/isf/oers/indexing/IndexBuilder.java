package org.isf.oers.indexing;

import org.isf.oers.jpa.EntityData;

public interface IndexBuilder {

	public EntityIndexedData buildIndex(EntityData data);
	
}
