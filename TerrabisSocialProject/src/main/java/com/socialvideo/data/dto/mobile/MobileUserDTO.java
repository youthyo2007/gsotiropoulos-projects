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
public class MobileUserDTO implements Serializable  {


	
	
	private static final long serialVersionUID = 1L;
	
    @JsonIgnore
	private Integer id;

	private String UUID;
	
	private String username;

	@JsonIgnore
    private String email;

	private Integer albumscount = 0;
	
	private Integer videoscount = 0;
	
	private Integer likescount = 0;

	private Integer playlistscount = 0;
	
	private Integer playscount = 0;
	
	private Integer tweetscount = 0;
	
	private Integer ratingscount = 0;
	
	private AvatarDTO avatar;	
	
	private GameLevelDTO gameLevel;
	
	
	public String getUUID() {
		return UUID;
	}


	public void setUUID(String uUID) {
		UUID = uUID;
	}



	public MobileUserDTO() {} 
	
	
	
	
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


	public Integer getAlbumscount() {
		return albumscount;
	}


	public void setAlbumscount(Integer albumscount) {
		this.albumscount = albumscount;
	}


	public Integer getVideoscount() {
		return videoscount;
	}


	public void setVideoscount(Integer videoscount) {
		this.videoscount = videoscount;
	}


	public AvatarDTO getAvatar() {
		return avatar;
	}


	public void setAvatar(AvatarDTO avatar) {
		this.avatar = avatar;
	}


	public GameLevelDTO getGameLevel() {
		return gameLevel;
	}


	public void setGameLevel(GameLevelDTO gameLevel) {
		this.gameLevel = gameLevel;
	}


	public Integer getLikescount() {
		return likescount;
	}


	public void setLikescount(Integer likescount) {
		this.likescount = likescount;
	}


	public Integer getPlaylistscount() {
		return playlistscount;
	}


	public void setPlaylistscount(Integer playlistscount) {
		this.playlistscount = playlistscount;
	}


	public Integer getPlayscount() {
		return playscount;
	}


	public void setPlayscount(Integer playscount) {
		this.playscount = playscount;
	}


	public Integer getTweetscount() {
		return tweetscount;
	}


	public void setTweetscount(Integer tweetscount) {
		this.tweetscount = tweetscount;
	}


	public Integer getRatingscount() {
		return ratingscount;
	}


	public void setRatingscount(Integer ratingscount) {
		this.ratingscount = ratingscount;
	}

	

}
