package com.socialvideo.data.dto.mobile;

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
public class MobileQueryDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("location")
	private PointDTO location = new PointDTO("0", "0");


	@JsonProperty("bounds")
	protected BoundsDTO bounds = new BoundsDTO(new PointDTO("0", "0"),new PointDTO("0","0"));

	
	@JsonProperty("count")
	protected Boolean count = false;
	
	@JsonProperty("userid")
	private Integer userid;
	
	@JsonProperty("querytxt")
	protected String querytxt;
	
	@JsonProperty("limit")
	protected Integer limit;

	
	@JsonProperty("offset")
	protected Integer offset;
	
	@JsonProperty("range")
	protected Integer range;

	@JsonProperty("querytype")
	protected String querytype;

	
	@JsonProperty("sort")
	private String sort ;

	@JsonProperty("when")
	private String when;
	
	@JsonProperty("shootingtype")
	private Integer shootingtype;
	
	@JsonProperty("videotype")
	private Integer videotype;
	

	
	public MobileQueryDTO() {}

	
	public MobileQueryDTO(PointDTO location, Integer range,Integer offset, Integer limit) {
		this.location = location;
		this.range = range;
		this.offset = offset;
		this.limit = limit;
	}


	public MobileQueryDTO(String querytxt, String querytype,String sort, Integer offset, Integer limit) {
		this.querytxt = querytxt;
		this.querytype = querytype;
		this.sort = sort;
		this.offset = offset;
		this.limit = limit;
	}

	
	public PointDTO getLocation() {
		return location;
	}



	public void setLocation(PointDTO location) {
		this.location = location;
	}



	public BoundsDTO getBounds() {
		return bounds;
	}



	public void setBounds(BoundsDTO bounds) {
		this.bounds = bounds;
	}


	public String getQuerytxtlike() {
		return "%"+querytxt+"%";
	}

	public String getQuerytxt() {
		return querytxt;
	}



	public void setQuerytxt(String querytxt) {
		this.querytxt = querytxt;
	}



	public Integer getLimit() {
		return limit;
	}



	public void setLimit(Integer limit) {
		this.limit = limit;
	}



	public Integer getOffset() {
		return offset;
	}



	public void setOffset(Integer offset) {
		this.offset = offset;
	}



	public Integer getRange() {
		return range;
	}



	public void setRange(Integer range) {
		this.range = range;
	}



	public String getQuerytype() {
		return querytype;
	}



	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}



	public String getSort() {
		return sort;
	}



	public void setSort(String sort) {
		this.sort = sort;
	}



	public String getWhen() {
		return when;
	}



	public void setWhen(String when) {
		this.when = when;
	}



	public Integer getShootingtype() {
		return shootingtype;
	}



	public void setShootingtype(Integer shootingtype) {
		this.shootingtype = shootingtype;
	}



	public Integer getVideotype() {
		return videotype;
	}



	public void setVideotype(Integer videotype) {
		this.videotype = videotype;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public Boolean getCount() {
		return count;
	}



	public void setCount(Boolean count) {
		this.count = count;
	}


	public Integer getUserid() {
		return userid;
	}


	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	

}
