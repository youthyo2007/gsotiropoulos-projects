package com.socialvideo.data.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTOSmall implements Serializable  {
	
	
	private static final long serialVersionUID = 1L;
	
	
	@JsonIgnore
	private Long id;

	
	private String UUID;
	
	private String username;
	
	@JsonIgnore
    private String email;
 
    
	private AvatarDTO avatar;	
	
	private Integer followerscount;
	
	private Integer followingcount;
	
	

	
	public Integer getFollowingcount() {
		return followingcount;
	}



	public void setFollowingcount(Integer followingcount) {
		this.followingcount = followingcount;
	}



	public String getShortdesc() {
		String shortDesc = "";
		if(username!=null && username.trim().length()>0) {
			if (username.length()>20)
				shortDesc = username.substring(0, 19)+"...";
			else
				shortDesc = username;
		} 
		
		else {
			if (email.length()>20)
				shortDesc = email.substring(0, 19)+"...";
			else 
				shortDesc = email;
		}
		
		
		return shortDesc;
		
	}

    
    
	public UserDTOSmall() {}




	
	@JsonIgnore
	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getUUID() {
		return UUID;
	}




	public void setUUID(String uUID) {
		UUID = uUID;
	}




	public String getUsername() {
		return username;
	}




	public void setUsername(String username) {
		this.username = username;
	}




	
	@JsonIgnore
	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}



	public AvatarDTO getAvatar() {
		return avatar;
	}



	public void setAvatar(AvatarDTO avatar) {
		this.avatar = avatar;
	} 
	
	
	
}
