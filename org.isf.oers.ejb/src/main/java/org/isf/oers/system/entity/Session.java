package org.isf.oers.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;

import org.isf.oers.CONSTANTS;
import org.isf.oers.auth.entity.User;
import org.isf.oers.jpa.EntityData;

@Entity
@Table(name=CONSTANTS.SYSTEM_PREFIX+"SESSION",schema=CONSTANTS.SYSTEM_SCHEMA)
@TableGenerator(name=CONSTANTS.GENERATOR_NAME,pkColumnValue=CONSTANTS.SYSTEM_PREFIX+"SESSION_PK",schema=CONSTANTS.SYSTEM_SCHEMA,table=CONSTANTS.AUTOINC_TABLE_NAME)
public class Session extends EntityData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8976832897557895635L;

	private String sessionId;
	private User user;
	private Date startedAt = new Date();
	private Date endedAt;
	private SessionEndState endState =SessionEndState.UNKNOWN;
	
	public Session() { super(); }
	public Session(String sessionId) {
		this();
		setSessionId(sessionId);
	}
	
	@Column(name="SESSIONID", length=40, nullable=false)
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="USERID", nullable=false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="STARTEDAT",nullable=false)
	public Date getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ENDEDAT",nullable=true)
	public Date getEndedAt() {
		return endedAt;
	}
	public void setEndedAt(Date endedAt) {
		this.endedAt = endedAt;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="ENDSTATE", length=20, nullable=false)
	public SessionEndState getEndState() {
		return endState;
	}
	public void setEndState(SessionEndState endState) {
		this.endState = endState;
	}
	
}
