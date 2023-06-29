package com.socialvideo.data.dto;

import java.io.Serializable;

public class ContactDTO implements Serializable { 
	
	
	private static final long serialVersionUID = 1L;
	
	private Long userid;
	
	private String message;
	
	
	private Long videoid;
	
	
	
	
	public ContactDTO() {}




	public Long getUserid() {
		return userid;
	}




	public void setUserid(Long userid) {
		this.userid = userid;
	}




	public String getMessage() {
		return message;
	}




	public void setMessage(String message) {
		this.message = message;
	}




	public Long getVideoid() {
		return videoid;
	}




	public void setVideoid(Long videoid) {
		this.videoid = videoid;
	}
	


}
