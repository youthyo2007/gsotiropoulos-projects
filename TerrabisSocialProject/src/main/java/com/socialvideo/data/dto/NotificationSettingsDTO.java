package com.socialvideo.data.dto;

import java.io.Serializable;

public class NotificationSettingsDTO implements Serializable {


	private static final long serialVersionUID = 1L;
	

	private Long id;

	private Long userid;
	
	private  Integer videoreview;
	 
	private  Integer videolike;
	
	private  Integer videoshare;

	private  Integer videorate;
	
	private  Integer newfollower;
	
	private  Integer videocollectionadded;

	private  Integer performanceweekly;
	
	private  Integer performancemonthly;
	
	private  Integer newsletter;
	

	
	
	
	public NotificationSettingsDTO() {}
	

	public NotificationSettingsDTO(Integer setting) {
		this.videoreview = setting;
		this.videolike = setting;
		this.videoshare = setting;
		this.newfollower = setting;
		this.videorate = setting;
		this.videocollectionadded = setting;
		this.performanceweekly = setting;
		this.performancemonthly = setting;
		this.newsletter = setting;

	}


	public Integer getVideorate() {
		return videorate;
	}


	public void setVideorate(Integer videorate) {
		this.videorate = videorate;
	}


	public Long getUserid() {
		return userid;
	}


	public void setUserid(Long userid) {
		this.userid = userid;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}



	public Integer getVideolike() {
		return videolike;
	}


	public void setVideolike(Integer videolike) {
		this.videolike = videolike;
	}


	public Integer getVideoshare() {
		return videoshare;
	}


	public void setVideoshare(Integer videoshare) {
		this.videoshare = videoshare;
	}


	public Integer getNewfollower() {
		return newfollower;
	}


	public void setNewfollower(Integer newfollower) {
		this.newfollower = newfollower;
	}


	public Integer getVideocollectionadded() {
		return videocollectionadded;
	}


	public void setVideocollectionadded(Integer videocollectionadded) {
		this.videocollectionadded = videocollectionadded;
	}


	public Integer getPerformanceweekly() {
		return performanceweekly;
	}


	public void setPerformanceweekly(Integer performanceweekly) {
		this.performanceweekly = performanceweekly;
	}


	public Integer getPerformancemonthly() {
		return performancemonthly;
	}


	public void setPerformancemonthly(Integer performancemonthly) {
		this.performancemonthly = performancemonthly;
	}


	public Integer getNewsletter() {
		return newsletter;
	}


	public void setNewsletter(Integer newsletter) {
		this.newsletter = newsletter;
	}


	public Integer getVideoreview() {
		return videoreview;
	}


	public void setVideoreview(Integer videoreview) {
		this.videoreview = videoreview;
	}



}
