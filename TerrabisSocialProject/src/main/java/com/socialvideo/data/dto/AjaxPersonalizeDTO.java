package com.socialvideo.data.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AjaxPersonalizeDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	
	public AjaxPersonalizeDTO() {}

	
	public String getGoogleMapType() {
		return googleMapType;
	}


	public void setGoogleMapType(String googleMapType) {
		this.googleMapType = googleMapType;
	}


	@JsonProperty("clustererMode")
	private Boolean clustererMode;

	@JsonProperty("searchMapField")
	private Boolean searchMapField;

	
	
	
	
	
	@JsonProperty("googleMapType")
	private String googleMapType;
		

	@JsonProperty("advertType")
	private String advertType;



	public String getAdvertType() {
		return advertType;
	}


	public void setAdvertType(String advertType) {
		this.advertType = advertType;
	}


	public Boolean getClustererMode() {
		return clustererMode;
	}


	public void setClustererMode(Boolean clustererMode) {
		this.clustererMode = clustererMode;
	}


	public Boolean getSearchMapField() {
		return searchMapField;
	}


	public void setSearchMapField(Boolean searchMapField) {
		this.searchMapField = searchMapField;
	}



	
}
