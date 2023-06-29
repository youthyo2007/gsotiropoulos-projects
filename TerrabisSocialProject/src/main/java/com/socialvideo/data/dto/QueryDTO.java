package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.socialvideo.constant.QueryConstants;
import com.socialvideo.data.dto.maps.BoundsDTO;
import com.socialvideo.data.dto.maps.PointDTO;


@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	@JsonProperty("fitBounds")
	protected Boolean fitBounds = true;
	
	@JsonProperty("location")
	protected PointDTO location = new PointDTO("0","0");
	
	@JsonProperty("markerlocation")
	protected PointDTO markerlocation = new PointDTO("0","0");
	
	@JsonProperty("zoomlevel")
	protected Integer zoomlevel;
	
	@JsonProperty("querytxt")
	protected String querytxt = "";

	
	@JsonProperty("querytxtlike")
	protected String querytxtlike = "";
	
	@JsonProperty("pageview")
	protected String pageview;

	@JsonProperty("limit")
	protected String limit;

	@JsonProperty("latitude")
	protected String latitude;
	
	@JsonProperty("longitude")
	protected String longitude;
	
	@JsonProperty("range")
	protected Integer range;
	
	
	@JsonProperty("querytype")
	protected String querytype = QueryConstants.QUERYTYPE_MAP;


	@JsonProperty("queryastextflag")
	protected Boolean queryastextflag = false;

	
	@JsonProperty("status")
	protected String status;
	
	
	@JsonProperty("limitviewport")
	protected String limitviewport;
	
	
	@JsonProperty("bounds")
	protected BoundsDTO bounds = new BoundsDTO(new PointDTO("0", "0"),new PointDTO("0","0"));
	
	
	@JsonProperty("advfilter")
	protected AdvancedFiltersDTO advfilter = new AdvancedFiltersDTO(); 
	
	@JsonProperty("filter")
	private String filter = "";
	
	
	public QueryDTO() {}
	
	
	
	public QueryDTO(Boolean queryastextflag) {
		this.queryastextflag = queryastextflag;
	}
	
	
	public QueryDTO(String querytxt, Boolean queryastextflag) {
		this.querytxt = querytxt;
		this.queryastextflag = queryastextflag;
	}
	
		
	
	
	
	public String getQuerytxt() {
		return querytxt;
	}


	public void setQuerytxt(String querytxt) {
		this.querytxt = querytxt;
	}


	public BoundsDTO getBounds() {
		return bounds;
	}


	public void setBounds(BoundsDTO bounds) {
		this.bounds = bounds;
	}


	public PointDTO getLocation() {
		return location;
	}


	public void setLocation(PointDTO location) {
		this.location = location;
	}


	public PointDTO getMarkerlocation() {
		return markerlocation;
	}


	public void setMarkerlocation(PointDTO markerlocation) {
		this.markerlocation = markerlocation;
	}


	public Integer getZoomlevel() {
		return zoomlevel;
	}


	public void setZoomlevel(Integer zoomlevel) {
		this.zoomlevel = zoomlevel;
	}


	public Boolean getFitBounds() {
		return fitBounds;
	}


	public void setFitBounds(Boolean fitBounds) {
		this.fitBounds = fitBounds;
	}


	public AdvancedFiltersDTO getAdvfilter() {
		return advfilter;
	}


	public void setAdvfilter(AdvancedFiltersDTO advfilter) {
		this.advfilter = advfilter;
	}


	public String getPageview() {
		return pageview;
	}


	public void setPageview(String pageview) {
		this.pageview = pageview;
	}

	

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getLimitviewport() {
		return limitviewport;
	}

	public void setLimitviewport(String limitviewport) {
		this.limitviewport = limitviewport;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Boolean getQueryastextflag() {
		return queryastextflag;
	}




	public void setQueryastextflag(Boolean queryastextflag) {
		this.queryastextflag = queryastextflag;
	}


	public String getQuerytxtlike() {
		return "%"+querytxt+"%";
	}



	public void setQuerytxtlike(String querytxtlike) {
		this.querytxtlike = querytxtlike;
	}



	public String getQuerytype() {
		return querytype;
	}



	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}



	public String getFilter() {
		return filter;
	}



	public void setFilter(String filter) {
		this.filter = filter;
	}



	public String getLatitude() {
		return latitude;
	}



	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}



	public String getLongitude() {
		return longitude;
	}



	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}



	public Integer getRange() {
		return range;
	}



	public void setRange(Integer range) {
		this.range = range;
	}

}
