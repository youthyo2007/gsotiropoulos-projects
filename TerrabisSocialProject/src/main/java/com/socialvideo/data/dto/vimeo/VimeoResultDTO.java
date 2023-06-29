package com.socialvideo.data.dto.vimeo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VimeoResultDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("emptyStatus")
	private boolean emptyStatus = true;
	
	
	@JsonProperty("duration")
	private String duration;

	@JsonProperty("thumbnailuri")
	private String thumbnailuri;

	
	@JsonProperty("usetrid")
	private String userid;
	
	@JsonProperty("username")
	private String username;

	@JsonProperty("userlink")
	private String userlink;

	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("description")
	private String description;
	
	
	@JsonProperty("publishedDate")
	private String publishedDate;
	
	
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


	public String getPublishedDate() {
		return publishedDate;
	}


	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}


	public VimeoResultDTO() {}
	
	
	public boolean isEmptyStatus() {
		return emptyStatus;
	}


	public void setEmptyStatus(boolean emptyStatus) {
		this.emptyStatus = emptyStatus;
	}


	public String getDuration() {
		return duration;
	}


	public void setDuration(String duration) {
		this.duration = duration;
	}


	public String getThumbnailuri() {
		return thumbnailuri;
	}


	public void setThumbnailuri(String thumbnailuri) {
		this.thumbnailuri = thumbnailuri;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getUserlink() {
		return userlink;
	}


	public void setUserlink(String userlink) {
		this.userlink = userlink;
	}
	
	
	
	
	
	


	

}
