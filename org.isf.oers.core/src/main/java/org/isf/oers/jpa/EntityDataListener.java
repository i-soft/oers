package org.isf.oers.jpa;

import java.util.UUID;

import javax.enterprise.util.AnnotationLiteral;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;

import org.isf.commons.JPAUtil;

public class EntityDataListener {
	
//	TODO wait for jpa2.1
//	@Inject
//	@EntityDataEvent
//	private Event<EntityData> event;
	
	@PrePersist
	public void prePesist(Object entity) {
		if (entity instanceof EntityData) {
			EntityData data = (EntityData)entity;
			if (data.getUuid() == null || data.getUuid().length() == 0) 
				data.setUuid(UUID.randomUUID().toString());
		}
	}
	
	@SuppressWarnings("serial")
	@PostPersist
	public void postPersist(Object entity) {
		if (entity instanceof EntityData) {
			EntityData data = (EntityData)entity;
// TODO jpa2.1			event.select(new AnnotationLiteral<Inserted>() {}).fire(data);
			JPAUtil.getBeanManager().fireEvent(data, new AnnotationLiteral<EntityDataEvent>() {}, new AnnotationLiteral<Inserted>() {});
		}
	}
	
	@SuppressWarnings("serial")
	@PostUpdate
	public void postUpdate(Object entity) {
		if (entity instanceof EntityData) {
			EntityData data = (EntityData)entity;
// TODO jpa2.1			event.select(new AnnotationLiteral<Updated>() {}).fire(data);
			JPAUtil.getBeanManager().fireEvent(data, new AnnotationLiteral<EntityDataEvent>() {}, new AnnotationLiteral<Updated>() {});
		}
	}
	
	@SuppressWarnings("serial")
	@PreRemove
	public void preRemove(Object entity) {
		if (entity instanceof EntityData) {
			EntityData data = (EntityData)entity;
// TODO jpa2.1			event.select(new AnnotationLiteral<Removed>() {}).fire(data);
			JPAUtil.getBeanManager().fireEvent(data, new AnnotationLiteral<EntityDataEvent>() {}, new AnnotationLiteral<Removed>() {});
		}
	}
	
}
