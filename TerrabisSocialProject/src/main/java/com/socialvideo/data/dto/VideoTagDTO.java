package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class VideoTagDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;
	
	private Date datecreated;
	
	private Date datemodified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public Date getDatemodified() {
		return datemodified;
	}

	public void setDatemodified(Date datemodified) {
		this.datemodified = datemodified;
	}
	
	

}
