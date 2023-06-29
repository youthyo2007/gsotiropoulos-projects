package com.socialvideo.data.dto.youtube;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YouTubeResultDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("emptyStatus")
	private boolean emptyStatus = true;
	
	
	@JsonProperty("duration")
	private String duration;

	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("description")
	private String description;
	
	
	@JsonProperty("publishedDate")
	private String publishedDate;
	


	public String getPublishedDate() {
		return publishedDate;
	}


	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
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


	@JsonProperty("thumbnailuri")
	private String thumbnailuri;

	@JsonProperty("tags")
	private String tags;

	
	@JsonProperty("channelId")
	private String channelId;

	@JsonProperty("channelTitle")
	private String channelTitle;

	
	public YouTubeResultDTO() {}
	
	
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


	public String getChannelId() {
		return channelId;
	}


	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}


	public String getTags() {
		return tags;
	}


	public void setTags(String tags) {
		this.tags = tags;
	}


	public String getChannelTitle() {
		return channelTitle;
	}


	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}
	
	
	
	
	
	


	

}
