package org.isf.oers.auth;

import java.io.Serializable;

import org.isf.commons.dto.DTO;
import org.isf.commons.dto.DTOAttribute;
import org.isf.oers.dto.DTOId;

@DTO
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8974992658267949657L;
	
	private Long id;
	private String username = "anonymous";
	
	@DTOId
	@DTOAttribute("id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}   
	
	@DTOAttribute("name")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
