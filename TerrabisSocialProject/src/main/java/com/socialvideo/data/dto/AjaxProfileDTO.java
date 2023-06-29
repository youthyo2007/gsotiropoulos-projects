package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.socialvideo.data.dto.maps.BoundsDTO;
import com.socialvideo.data.dto.maps.PointDTO;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AjaxProfileDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	public AjaxProfileDTO() {}

	
	
	@JsonProperty("_csrf")
	private String _csrf;
	
	
	@JsonProperty("username")
	private String username;

	@JsonProperty("oldpassword")
	private String oldpassword;
	
	
	@JsonProperty("password")
	private String password;
	
	
	@JsonProperty("email")
	private String email;
	
	
	@JsonProperty("passwordconfirm")
	private String passwordconfirm;

	
	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPasswordconfirm() {
		return passwordconfirm;
	}


	public void setPasswordconfirm(String passwordconfirm) {
		this.passwordconfirm = passwordconfirm;
	}


	public String get_csrf() {
		return _csrf;
	}


	public void set_csrf(String _csrf) {
		this._csrf = _csrf;
	}


	public String getOldpassword() {
		return oldpassword;
	}


	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	
	
}
