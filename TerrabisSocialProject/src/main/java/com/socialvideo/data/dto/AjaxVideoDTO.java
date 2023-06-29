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
public class AjaxVideoDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	
	public AjaxVideoDTO() {}

	
	@JsonProperty("userid")
	private String userid;
	
	@JsonProperty("videoid")
	private String videoid;

	@JsonProperty("reviewid")
	private String reviewid;
	
	
	@JsonProperty("socialnetworkid")
	private String socialnetworkid;
	
	
	@JsonProperty("collectionid")
	private String collectionid;
	
	
	public String getSocialnetworkid() {
		return socialnetworkid;
	}


	public void setSocialnetworkid(String socialnetworkid) {
		this.socialnetworkid = socialnetworkid;
	}


	public String getCollectionid() {
		return collectionid;
	}


	public void setCollectionid(String collectionid) {
		this.collectionid = collectionid;
	}


	@JsonProperty("rating")
	private String rating;

	@JsonProperty("time")
	private String time;
	
	@JsonProperty("reviewtext")
	private String reviewtext;
	
	@JsonProperty("videourl")
	private String videourl;

	
	
	public String getVideoid() {
		return videoid;
	}


	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getRating() {
		return rating;
	}


	public void setRating(String rating) {
		this.rating = rating;
	}


	public String getVideourl() {
		return videourl;
	}


	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}


	public String getReviewtext() {
		return reviewtext;
	}


	public void setReviewtext(String reviewtext) {
		this.reviewtext = reviewtext;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getReviewid() {
		return reviewid;
	}


	public void setReviewid(String reviewid) {
		this.reviewid = reviewid;
	}


	
	
	
}
