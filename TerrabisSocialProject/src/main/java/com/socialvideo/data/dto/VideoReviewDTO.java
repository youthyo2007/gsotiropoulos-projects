package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.Date;

import com.socialvideo.utilities.SmartDateTimeUtil;



public class VideoReviewDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	private Long id;

	private Long videoid;

	private Long userid;

	private String review;
	
	private Integer status;
	
	private UserDTO user;
	
	
	private Date datecreated;
	
	
	
	public VideoReviewDTO() {}



	
	
	public String getSmartTime() {
		return datecreated!=null ? SmartDateTimeUtil.getDifferenceBtwTime(datecreated): "";
	}	
	
	
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



	public String getReview() {
		return review;
	}



	public void setReview(String review) {
		this.review = review;
	}



	public UserDTO getUser() {
		return user;
	}



	public void setUser(UserDTO user) {
		this.user = user;
	}


	
}


