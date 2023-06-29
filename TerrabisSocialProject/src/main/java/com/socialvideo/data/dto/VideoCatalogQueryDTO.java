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
public class VideoCatalogQueryDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("filter")
	private String filter = "";

	@JsonProperty("subfilter")
	private String subfilter = "";

	@JsonProperty("datefilter")
	private String datefilter = "";
	
	
	@JsonProperty("status")
	private String status = null;
	
	@JsonProperty("healthstatus")
	private String healthstatus = null;
	
	@JsonProperty("userid")
	private String userid = "";

	@JsonProperty("text")
	private String text = "";
	
	@JsonProperty("collectionid")
	private String collectionid = "";
	
	
	public VideoCatalogQueryDTO(String userid, String filter, String subfilter, String datefilter) {
		this.userid = userid;
		this.filter = filter;
		this.subfilter = subfilter;
		this.datefilter = datefilter;
	}

	public VideoCatalogQueryDTO(String text) {
		this.text = text;
	}

	public String getFilter() {
		return filter;
	}


	public void setFilter(String filter) {
		this.filter = filter;
	}


	public String getSubfilter() {
		return subfilter;
	}


	public void setSubfilter(String subfilter) {
		this.subfilter = subfilter;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}

	public String getHealthstatus() {
		return healthstatus;
	}

	public void setHealthstatus(String healthstatus) {
		this.healthstatus = healthstatus;
	}

	public String getCollectionid() {
		return collectionid;
	}

	public void setCollectionid(String collectionid) {
		this.collectionid = collectionid;
	}

	public String getDatefilter() {
		return datefilter;
	}

	public void setDatefilter(String datefilter) {
		this.datefilter = datefilter;
	}


	
	
	
}
