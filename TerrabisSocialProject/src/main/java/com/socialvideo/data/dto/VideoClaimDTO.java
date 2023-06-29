package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.Date;

import com.socialvideo.utilities.SmartDateTimeUtil;



public class VideoClaimDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	private Long id;

	private Long videoid;
	

	private Long userid;

	private Long claimerid;

	private Integer attemptscount = 0;
	
	private Integer status = 0;
	
	
	private Date datecreated;
	
	
	
	private UserDTO user;
	
	
	private UserDTO claimer;
	
	
	private VideoDTO video;
	
	
	
	
	
	public VideoClaimDTO() {}


	
	public String getDateCreatedAsString() {
		return datecreated!=null ? SmartDateTimeUtil.getDateFormatString(datecreated): "";
	}	
	
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getVideoid() {
		return videoid;
	}


	public void setVideoid(Long videoid) {
		this.videoid = videoid;
	}


	public Long getUserid() {
		return userid;
	}


	public void setUserid(Long userid) {
		this.userid = userid;
	}


	public Long getClaimerid() {
		return claimerid;
	}


	public void setClaimerid(Long claimerid) {
		this.claimerid = claimerid;
	}


	public Integer getAttemptscount() {
		return attemptscount;
	}


	public void setAttemptscount(Integer attemptscount) {
		this.attemptscount = attemptscount;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	};
	
	
	public Date getDatecreated() {
		return datecreated;
	}


	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}


	
	public VideoDTO getVideo() {
		return video;
	}


	public void setVideo(VideoDTO video) {
		this.video = video;
	}



	public UserDTO getUser() {
		return user;
	}



	public void setUser(UserDTO user) {
		this.user = user;
	}



	public UserDTO getClaimer() {
		return claimer;
	}



	public void setClaimer(UserDTO claimer) {
		this.claimer = claimer;
	}

	
	
}


