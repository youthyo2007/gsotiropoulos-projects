package com.socialvideo.data.dto;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;


public class VimeoResponseWrapper  {

	HttpHeaders headers;
	HttpStatus statusCode;
	VimeoUploadResponseDTO body;
	
	
	
	
	public HttpHeaders getHeaders() {
		return headers;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public VimeoUploadResponseDTO getBody() {
		return body;
	}

	
    
    
    public VimeoResponseWrapper(){}

	

	public  void setHeaders(HttpHeaders headers) {
		this.headers = headers;
		
	}

	public  void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
		
	}

	public  void setBody(VimeoUploadResponseDTO body) {
		this.body = body;
		
	}
    
    
    
    

}
