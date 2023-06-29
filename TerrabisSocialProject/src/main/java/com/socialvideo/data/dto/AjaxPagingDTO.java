package com.socialvideo.data.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AjaxPagingDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	
	public AjaxPagingDTO() {}

	
	@JsonProperty("pageid")
	private String pageid;


	@JsonProperty("nextpage")
	private Boolean nextpage;

	

	public String getPageid() {
		return pageid;
	}


	public void setPageid(String pageid) {
		this.pageid = pageid;
	}


	public Boolean getNextpage() {
		return nextpage;
	}


	public void setNextpage(Boolean nextpage) {
		this.nextpage = nextpage;
	}

	
	
}
