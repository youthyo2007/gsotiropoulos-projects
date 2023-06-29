package com.socialvideo.data.dto.statistics;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VideoDTO;

public class StatisticsDayActivityDTO {
	
	
	

	private Long dateid;



	private Integer videocount = 0;
	


	private Integer usercount = 0;
	


	private Integer sharecount = 0;
	


	private Integer reviewcount = 0;
	
	

	private Integer likecount = 0;

	
	private Integer playcount = 0;
	
	
	private Integer ratingcount = 0;
	
	
	
	private Date activitydate;
	
	
	private String datestring;
	
	
	private List<VideoDTO> uploadList;
	private List<UserDTO> registrationList;
	private List<VideoDTO> shareList;
	private List<VideoDTO> likeList;
	private List<VideoDTO> playList;
	private List<VideoDTO> ratingList;
	
	
	

	
	public StatisticsDayActivityDTO() {}





	public Long getDateid() {
		return dateid;
	}





	public void setDateid(Long dateid) {
		this.dateid = dateid;
	}





	public Integer getVideocount() {
		return videocount;
	}





	public void setVideocount(Integer videocount) {
		this.videocount = videocount;
	}





	public Integer getUsercount() {
		return usercount;
	}





	public void setUsercount(Integer usercount) {
		this.usercount = usercount;
	}





	public Integer getSharecount() {
		return sharecount;
	}





	public void setSharecount(Integer sharecount) {
		this.sharecount = sharecount;
	}





	public Integer getReviewcount() {
		return reviewcount;
	}





	public void setReviewcount(Integer reviewcount) {
		this.reviewcount = reviewcount;
	}





	public Integer getLikecount() {
		return likecount;
	}





	public void setLikecount(Integer likecount) {
		this.likecount = likecount;
	}





	public Integer getPlaycount() {
		return playcount;
	}





	public void setPlaycount(Integer playcount) {
		this.playcount = playcount;
	}





	public Integer getRatingcount() {
		return ratingcount;
	}





	public void setRatingcount(Integer ratingcount) {
		this.ratingcount = ratingcount;
	}





	public Date getActivitydate() {
		return activitydate;
	}





	public void setActivitydate(Date activitydate) {
		this.activitydate = activitydate;
	}





	public String getDatestring() {
		return datestring;
	}





	public void setDatestring(String datestring) {
		this.datestring = datestring;
	}





	public List<VideoDTO> getUploadList() {
		return uploadList;
	}





	public void setUploadList(List<VideoDTO> uploadList) {
		this.uploadList = uploadList;
	}





	public List<UserDTO> getRegistrationList() {
		return registrationList;
	}





	public void setRegistrationList(List<UserDTO> registrationList) {
		this.registrationList = registrationList;
	}





	public List<VideoDTO> getShareList() {
		return shareList;
	}





	public void setShareList(List<VideoDTO> shareList) {
		this.shareList = shareList;
	}





	public List<VideoDTO> getPlayList() {
		return playList;
	}





	public void setPlayList(List<VideoDTO> playList) {
		this.playList = playList;
	}





	public List<VideoDTO> getRatingList() {
		return ratingList;
	}





	public void setRatingList(List<VideoDTO> ratingList) {
		this.ratingList = ratingList;
	}





	public List<VideoDTO> getLikeList() {
		return likeList;
	}





	public void setLikeList(List<VideoDTO> likeList) {
		this.likeList = likeList;
	}

}
