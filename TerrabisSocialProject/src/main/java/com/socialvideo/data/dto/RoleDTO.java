package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class RoleDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
	
	
	
	public RoleDTO() {}
	
	
	
	public RoleDTO(String name) {
		this.name = name;
		
		
	}
	
	

}
