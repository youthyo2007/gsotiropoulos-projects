package com.socialvideo.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.socialvideo.constant.NotificationSettingStatus;
import com.socialvideo.data.dto.NotificationSettingsDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VideoClaimDTO;





@Entity
@Table(name = "notificationsettings")
public class NotificationSettingsEntity implements Serializable {


	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, name="userid_fk")
	private Long userid;
	
	
	@Column
	private  Integer videoreview;
	 
	@Column
	private  Integer videolike;
	
	@Column
	private  Integer videoshare;
	
	@Column
	private  Integer videorate;
	
	@Column
	private  Integer newfollower;
	
	@Column
	private  Integer videocollectionadded;

	@Column
	private  Integer performanceweekly;
	
	@Column
	private  Integer performancemonthly;
	
	
	@Column
	private  Integer newsletter;
	
	
	
	public NotificationSettingsEntity() {}


	public NotificationSettingsEntity(Long userid, Integer status) {
		this.userid = userid;
		this.videoreview = status;
		this.videolike = status;
		this.videoshare = status;
		this.videorate = status;
		this.newfollower = status;
		this.videocollectionadded = status;
		this.performanceweekly = status;
		this.performancemonthly = status;
		this.newsletter = status;
	}

	
	public NotificationSettingsDTO DTO() {

		NotificationSettingsDTO DTO = new NotificationSettingsDTO();
		DTO.setId(this.id);
		DTO.setUserid(this.userid);
		DTO.setNewfollower(newfollower);
		DTO.setNewsletter(newsletter);
		DTO.setPerformancemonthly(performancemonthly);
		DTO.setPerformanceweekly(performanceweekly);
		DTO.setVideocollectionadded(videocollectionadded);
		DTO.setVideoshare(videoshare);
		DTO.setVideolike(videolike);
		DTO.setVideoreview(videoreview);
		DTO.setVideorate(videorate);
		return DTO;
	}
	

	
	
	
	public Integer getVideorate() {
		return videorate;
	}



	public void setVideorate(Integer videorate) {
		this.videorate = videorate;
	}



	public void LOADEDIT(NotificationSettingsDTO DTO) {
		this.newfollower = DTO.getNewfollower();
		this.newsletter = DTO.getNewsletter();
		this.performancemonthly = DTO.getPerformancemonthly();
		this.performanceweekly = DTO.getPerformanceweekly();
		this.videocollectionadded = DTO.getVideocollectionadded();
		this.videoshare = DTO.getVideoshare();
		this.videolike = DTO.getVideolike();
		this.videoreview = DTO.getVideoreview();
		this.videorate = DTO.getVideorate();
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
