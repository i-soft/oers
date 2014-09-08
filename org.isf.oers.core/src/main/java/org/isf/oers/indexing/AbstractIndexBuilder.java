package org.isf.oers.indexing;

import org.isf.commons.bean.Bean;
import org.isf.commons.bean.BeanFactory;
import org.isf.oers.jpa.EntityData;

public abstract class AbstractIndexBuilder implements IndexBuilder {

	public abstract String getSubject(EntityData data);
	public abstract String getContent(EntityData data);
	
	@Override
	public EntityIndexedData buildIndex(EntityData data) {
		EntityIndexedData ret = new EntityIndexedData();
		Bean b = BeanFactory.createBeanInstance(data);
		
		ret.setBeanClass(b.getName());
		ret.setUuid(data.getUuid());
//		ret.setSubject(b.getDisplayName()+" "+data.getId());
//		ret.setContent("");
		ret.setSubject(getSubject(data));
		ret.setContent(getContent(data));
		
		return ret;
	}

}
