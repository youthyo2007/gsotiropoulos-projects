package com.socialvideo.data.dto.mobile;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.socialvideo.data.dto.VideoDTO;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileResultDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	
	public MobileResultDTO() {
	}
	

	
	
	public MobileResultDTO(HttpStatus status) { this.status = status.toString();}
	public MobileResultDTO(HttpStatus status, MobileVideoDTO video) { this.status = status.toString(); this.video = video;}
	public MobileResultDTO(HttpStatus status, MobileUserDTO user) { this.status = status.toString(); this.user = user;}
	public MobileResultDTO(HttpStatus status, String message) { this.status = status.toString(); this.message = message;}
	

	public MobileResultDTO(List<MobileVideoDTO> videolist,  Integer total, HttpStatus status) { 
		this.status = status.toString();
		this.videolist = videolist;
		this.total = total;


	}

	

	@JsonProperty("videolist")
	List<MobileVideoDTO> videolist;

	@JsonProperty("status")
	private String status;

	@JsonProperty("total")
	private Integer total;

	@JsonProperty("video")
	private MobileVideoDTO video;

	@JsonProperty("user")
	private MobileUserDTO user;
	
	@JsonProperty("message")
	private String message;



	public List<MobileVideoDTO> getVideolist() {
		return videolist;
	}




	public void setVideolist(List<MobileVideoDTO> videolist) {
		this.videolist = videolist;
	}




	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}




	public MobileVideoDTO getVideo() {
		return video;
	}




	public void setVideo(MobileVideoDTO video) {
		this.video = video;
	}




	public String getMessage() {
		return message;
	}




	public void setMessage(String message) {
		this.message = message;
	}




	public MobileUserDTO getUser() {
		return user;
	}




	public void setUser(MobileUserDTO user) {
		this.user = user;
	}




	public Integer getTotal() {
		return total;
	}




	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
