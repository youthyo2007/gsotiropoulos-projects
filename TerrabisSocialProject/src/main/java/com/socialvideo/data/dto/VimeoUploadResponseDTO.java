package com.socialvideo.data.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VimeoUploadResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("uri")
    private String uri;

    @JsonProperty("ticket_id")
    private String ticketId;

    @JsonProperty("upload_link_secure")
    private String linkSecure;
    
    
    @JsonProperty("complete_uri")
    private String completeUri;
    
    public VimeoUploadResponseDTO(String linkSecure, String completeUri){
    	this.linkSecure = linkSecure;
    	this.completeUri = completeUri;
    };

    
    public VimeoUploadResponseDTO(){};

	public String getUri() {
		return uri;
	}


	public void setUri(String uri) {
		this.uri = uri;
	}


	public String getTicketId() {
		return ticketId;
	}


	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}


	public String getLinkSecure() {
		return linkSecure;
	}


	public void setLinkSecure(String linkSecure) {
		this.linkSecure = linkSecure;
	}


	public String getCompleteUri() {
		return completeUri;
	}


	public void setCompleteUri(String completeUri) {
		this.completeUri = completeUri;
	}
    
    
    
    

}
