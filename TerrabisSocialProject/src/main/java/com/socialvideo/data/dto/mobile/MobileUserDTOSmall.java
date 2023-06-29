package com.socialvideo.data.dto.mobile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.Joiner;
import com.socialvideo.data.dto.AvatarDTO;
import com.socialvideo.data.dto.GameLevelDTO;
import com.socialvideo.utilities.SmartDateTimeUtil;
import com.socialvideo.validation.PasswordMatches;
import com.socialvideo.validation.ValidEmail;
import com.socialvideo.validation.ValidPassword;



@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileUserDTOSmall implements Serializable  {
	
	
	private static final long serialVersionUID = 1L;
	
    @JsonIgnore
	private Integer id;

	private String UUID;
	
	private String username;
	
    @JsonIgnore
    private String email;
 
	
	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public MobileUserDTOSmall() {} 
	
	
	
	
	public String getShortdesc() {
		String shortDesc = "";
		if(username!=null && username.trim().length()>0) {
			if (username.length()>20)
				shortDesc = username.substring(0, 19)+"...";
			else
				shortDesc = username;
		} 
		
		else {
			if (email!=null && email.length()>20)
				shortDesc = email.substring(0, 19)+"...";
			else 
				shortDesc = email;
		}
		
		
		return shortDesc;
		
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
