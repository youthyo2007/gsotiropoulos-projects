package com.socialvideo.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.socialvideo.data.dto.VideoClaimDTO;
import com.socialvideo.data.dto.VideoDTO;


@Entity
@Table(name = "videoclaims")
public class VideoClaimEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, name="videoid_fk")
	private Long videoid;
	

	@Column(nullable = false, name="userid_fk")
	private Long userid;

	@Column(nullable = false, name="claimerid_fk")
	private Long claimerid;

	@Column
	private Integer attemptscount = 0;
	
	@Column
	private Integer status = 0;
	
	
	public VideoClaimEntity() {}

	
	public VideoClaimEntity(Long userid, Long videoid, Long claimerid, Integer status) {
		this.userid = userid;
		this.videoid = videoid;
		this.claimerid = claimerid;
		this.status = status;
	}
	
	
	
	
	
	public VideoClaimDTO DTO() {

		VideoClaimDTO DTO = new VideoClaimDTO();
		
		DTO.setId(this.id);
		DTO.setVideoid(this.videoid);
		DTO.setUserid(this.userid);
		DTO.setClaimerid(this.claimerid);
		DTO.setAttemptscount(this.attemptscount);
		DTO.setStatus(this.status);
		
		return DTO;
	}
	
	public void LOAD(VideoClaimDTO DTO) {
		this.id = DTO.getId();
		this.videoid = DTO.getVideoid();
		this.userid = DTO.getUserid();
		this.claimerid = DTO.getClaimerid();
		this.attemptscount = DTO.getAttemptscount();
		this.status = DTO.getStatus();
		
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
	
	
}


