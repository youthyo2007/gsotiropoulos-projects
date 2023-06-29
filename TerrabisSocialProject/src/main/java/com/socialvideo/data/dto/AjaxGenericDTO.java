package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.socialvideo.data.dto.maps.BoundsDTO;
import com.socialvideo.data.dto.maps.PointDTO;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AjaxGenericDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	

	@JsonProperty("tagtext")
	private String tagtext;
	
	@JsonProperty("autocompletetext")
	private String autocompletetext;
	
	
	
	public String getAutocompletetext() {
		return autocompletetext;
	}


	public void setAutocompletetext(String autocompletetext) {
		this.autocompletetext = autocompletetext;
	}


	public AjaxGenericDTO() {}


	public String getTagtext() {
		return tagtext;
	}


	public void setTagtext(String tagtext) {
		this.tagtext = tagtext;
	}

	
}
