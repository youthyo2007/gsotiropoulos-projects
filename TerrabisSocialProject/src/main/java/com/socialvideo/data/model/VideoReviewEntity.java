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
import com.socialvideo.data.dto.VideoReviewDTO;


@Entity
@Table(name = "reviews")
public class VideoReviewEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, name="videoid_fk")
	private Long videoid;
	

	@Column(nullable = false, name="userid_fk")
	private Long userid;

	
	
	@Column
	private Integer status;
	
	

	@Column
	private String review;
	
	@Column(name = "datecreated", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datecreated;
	
	public VideoReviewEntity() {}

	
	public VideoReviewEntity(Long userid, Long videoid, String review) {
		this.userid = userid;
		this.videoid = videoid;
		this.review = review;
	
	}
	
	
	
	
	
	public VideoReviewDTO DTO() {

		VideoReviewDTO DTO = new VideoReviewDTO();
		
		DTO.setId(this.id);
		DTO.setVideoid(this.videoid);
		DTO.setUserid(this.userid);
		DTO.setStatus(this.status);
		DTO.setReview(this.review);
	
		return DTO;
	}
	
	public void LOAD(VideoReviewDTO DTO) {
		this.id = DTO.getId();
		this.videoid = DTO.getVideoid();
		this.userid = DTO.getUserid();
		this.status = DTO.getStatus();
		this.review = DTO.getReview();
	
		
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


	public String getReview() {
		return review;
	}


	public void setReview(String review) {
		this.review = review;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Date getDatecreated() {
		return datecreated;
	}


	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}


	
	
	
}


