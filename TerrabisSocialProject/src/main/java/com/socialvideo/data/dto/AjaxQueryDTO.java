package com.socialvideo.data.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AjaxQueryDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	
	public AjaxQueryDTO() {}

	
	@JsonProperty("userid")
	private String userid;



	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	
	
	
	
}
