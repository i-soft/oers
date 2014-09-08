package org.isf.oers.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.isf.oers.CONSTANTS;
import org.isf.oers.jpa.EntityData;

@Entity
@Table(name=CONSTANTS.SYSTEM_PREFIX+"PRIVILEGE", schema=CONSTANTS.SYSTEM_SCHEMA)
@TableGenerator(name=CONSTANTS.GENERATOR_NAME,pkColumnValue=CONSTANTS.SYSTEM_PREFIX+"PRIVILEGE_PK",schema=CONSTANTS.SYSTEM_SCHEMA,table=CONSTANTS.AUTOINC_TABLE_NAME)
public class Privilege extends EntityData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3504640753683754916L;

	public static final int NONE = 0;
	public static final int WRITE = 1;
	public static final int READ = 2;
	public static final int EXECUTE = 4;
	public static final int ALL = EXECUTE | WRITE | READ;
	
	private int mode = NONE;
	private Right right;
	
	@Column(name="MODE",nullable=false)
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="RIGHTID", nullable=false)
	public Right getRight() {
		return right;
	}
	public void setRight(Right right) {
		this.right = right;
	}
		
}
