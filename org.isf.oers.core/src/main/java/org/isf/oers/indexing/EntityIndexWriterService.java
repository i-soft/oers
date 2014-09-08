package org.isf.oers.indexing;

import org.isf.oers.jpa.EntityData;

public interface EntityIndexWriterService {

	public void indexData(EntityData data);
	public void reindexData(EntityData data);
	public void removeData(EntityData data);
	
}
