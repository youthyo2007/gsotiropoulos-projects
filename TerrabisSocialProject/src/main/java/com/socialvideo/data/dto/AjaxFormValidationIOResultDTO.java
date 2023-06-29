package com.socialvideo.data.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AjaxFormValidationIOResultDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	
	public AjaxFormValidationIOResultDTO() {
	}
	
	
	public AjaxFormValidationIOResultDTO(String valid) {
		this.valid =valid;
	}

	public AjaxFormValidationIOResultDTO(String valid, String videoid) {
		this.valid =valid;
		this.videoid = videoid;
	}

	
	@JsonProperty("valid")
	private String valid;


	@JsonProperty("videoid")
	private String videoid;
	
	
	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}


	public String getVideoid() {
		return videoid;
	}


	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}

	
}
