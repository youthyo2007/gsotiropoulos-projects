package com.socialvideo.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.socialvideo.data.dto.RoleDTO;
import com.socialvideo.data.dto.UserDTO;


@Entity
@Table(name = "roles")
public class RoleEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	
	public RoleEntity() {}
	
	public RoleEntity(String name) {
		this.name = name;
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	private String name;

	
	
	public RoleDTO DTO() {

		RoleDTO DTO = new RoleDTO();
		DTO.setName(this.name);
		DTO.setName(this.name);
		
		
		
		return DTO;
	}
	
	
}


