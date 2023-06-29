package com.socialvideo.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import com.socialvideo.data.dto.VideoClaimDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.statistics.StatisticsDayActivityDTO;


@Entity
@Table(name = "statisticsactivityday")
public class StatisticsDayActivityEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	private Long dateid;


	@Column
	private Integer videocount = 0;
	

	@Column
	private Integer usercount = 0;
	

	@Column
	private Integer sharecount = 0;
	

	@Column
	private Integer reviewcount = 0;
	
	@Column
	private Integer ratingcount = 0;

	
	@Column
	private Integer likecount = 0;
	
	
	@Column
	private Integer playcount = 0;
	
	
	@Column(name = "activitydate", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date activitydate;
	
	
	public StatisticsDayActivityEntity() {}

	
	public StatisticsDayActivityEntity(Long dateid) {
		this.dateid = dateid;
	}
	
	
	
	
	
	public StatisticsDayActivityDTO DTO() {

		StatisticsDayActivityDTO DTO = new StatisticsDayActivityDTO();
		
		DTO.setDateid(this.dateid);
		DTO.setLikecount(this.likecount);
		DTO.setPlaycount(this.playcount);
		DTO.setReviewcount(this.reviewcount);
		DTO.setSharecount(this.sharecount);
		DTO.setUsercount(this.usercount);
		DTO.setVideocount(this.videocount);
		DTO.setRatingcount(this.ratingcount);
		return DTO;
	}
	
	public void LOAD(StatisticsDayActivityDTO DTO) {
		this.dateid = DTO.getDateid();
		this.likecount = DTO.getLikecount();
		this.playcount = DTO.getPlaycount();
		this.reviewcount = DTO.getReviewcount();
		this.sharecount = DTO.getSharecount();
		this.usercount = DTO.getUsercount();
		this.videocount = DTO.getVideocount();
		this.ratingcount = DTO.getRatingcount();
	}


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

	

	
}


