package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.Date;

public class ChannelDTO implements Serializable  {
	
	
	private static final long serialVersionUID = 1L;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Integer privacy) {
		this.privacy = privacy;
	}
	private Long userid;

	private String title;

	private String description;
	
	private Integer privacy;
	
	private Date datecreated;
	
	private Date datemodified;
	
	
	
	public ChannelDTO() {} 
	
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
