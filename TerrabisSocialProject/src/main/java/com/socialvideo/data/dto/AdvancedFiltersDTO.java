package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.socialvideo.utilities.WebUtility;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AdvancedFiltersDTO implements Serializable {
	
	
	

	private static final long serialVersionUID = 1L;
	
	
	
	public AdvancedFiltersDTO() {}
	
	
	
/*	
	public Boolean isEmptyCriteria() {
		

		
		Boolean isEmpty = false;
		
		if(((this.getUserid()==null) || (this.getUserid().trim().equalsIgnoreCase(""))) && ((this.getPathofvideoids()==null) || (this.getPathofvideoids().trim().equalsIgnoreCase(""))) && (this.getUploaddatetxt()==null) && (this.getCategoryid()==0) && ((this.getTags()==null) || (this.getTags().trim().length()==0)))   {
			isEmpty = true;
		}
		
        System.out.println("isEmpty:"+this.getUserid()+"-"+this.getPathofvideoids()+"-"+this.getUploaddatetxt()+"-"+this.getCategoryid()+"-"+this.getTags()+"-"+isEmpty.toString());

		
		
		return isEmpty; 
		
		
		
	}*/
	

	
	@JsonProperty("userid")
	private String userid = null;
	
	@JsonProperty("collectionvideos")
	private String collectionvideos = "";

	@JsonProperty("collectionid")
	private String collectionid = "";

	@JsonProperty("terrabiscomment")
	private Boolean terrabiscomment = null;
	
	public  List<String> getCollectionvideoslist() {
		  Iterable<String> split = Splitter.on( "," ).omitEmptyStrings().trimResults().split( this.getCollectionvideos());
		  return Lists.newArrayList( split );
	}
	
	
	
	
	@JsonProperty("weather")
	private Map<String, Boolean> weather = WebUtility.weatherMap();
	private String weathertxt;
	
	
	@JsonProperty("footagedate")
	private Map<String, Boolean> footagedate = WebUtility.datesMap();
	private String footagedatetxt;
	
	
	
	@JsonProperty("uploaddate")
	private Map<String, Boolean> uploaddate = WebUtility.datesMap();
	private String uploaddatetxt;

	
	@JsonProperty("season")
	private Map<String, Boolean> season = WebUtility.seasonMap();	
	private String seasontxt;

	
	private Long categoryid = new Long(0);

	private Long shootingtypeid = new Long(0);
	
	private Long videotypeid = new Long(0);
	
	
	@JsonProperty("tags")
	private String tags;

	@JsonProperty("desc")
	private String desc;
	
	@JsonProperty("mapradious")
	private Integer mapradious;
	
	
	@JsonProperty("vidduration")
	private Integer vidduration;

	
	@JsonProperty("status")
	private Integer status;

	@JsonProperty("healthstatus")
	private Integer healthstatus;
	
	@JsonProperty("integrationstatus")
	private Integer integrationstatus;
		
	@JsonProperty("enabled")
	private Integer enabled;

	
	@JsonProperty("privacy")
	private Integer privacy;

	




	@JsonProperty("videotitle")
	private String videotitle;

	@JsonProperty("username")
	private String username;

	@JsonProperty("ytvmusername")
	private String ytvmusername;
	
	
	@JsonProperty("videoid")
	private String videoid;
	
	
	@JsonProperty("promotesiteindex")
	private Boolean promotesiteindex;
	
	
	@JsonProperty("promotemap")
	private Boolean promotemap;
	
	@JsonProperty("markasunwanted")
	private Boolean markasunwanted = false;
	
	
	
	public String getYtvmusername() {
		return ytvmusername;
	}




	public void setYtvmusername(String ytvmusername) {
		this.ytvmusername = ytvmusername;
	}
	
	public Boolean getMarkasunwanted() {
		return markasunwanted;
	}




	public void setMarkasunwanted(Boolean markasunwanted) {
		this.markasunwanted = markasunwanted;
	}




	public String getWeathertxt() {
		for(Map.Entry<String, Boolean> entry : weather.entrySet()){  if(entry.getValue() && (!entry.getKey().equals("any"))) {weathertxt+=entry.getKey()+",";} }; 
		return weathertxt;
	}




	public void setWeathertxt(String weathertxt) {
		this.weathertxt = weathertxt;
	}




	public String getFootagedatetxt() {
		for(Map.Entry<String, Boolean> entry : footagedate.entrySet()){  if(entry.getValue() && (!entry.getKey().equals("any"))) {footagedatetxt=entry.getKey();}}; 
		return footagedatetxt;
	}




	public void setFootagedatetxt(String footagedatetxt) {
		this.footagedatetxt = footagedatetxt;
	}




	public String getUploaddatetxt() {
		for(Map.Entry<String, Boolean> entry : uploaddate.entrySet()){  if(entry.getValue() && (!entry.getKey().equals("any"))) {uploaddatetxt=entry.getKey();}}; 
		return uploaddatetxt;
	}




	public void setUploaddatetxt(String uploaddatetxt) {
		this.uploaddatetxt = uploaddatetxt;
	}




	public String getSeasontxt() {
		return seasontxt;
	}




	public void setSeasontxt(String seasontxt) {
		this.seasontxt = seasontxt;
	}

	
	
	public List<String> getTagslist() {
		if ((tags!=null) && (tags.trim().length()>0)) {
		  Iterable<String> split = Splitter.on( "," ).omitEmptyStrings().trimResults().split( tags );
		  return Lists.newArrayList( split );
		}
		else 
			return new ArrayList<>();
		
	}
	
	
	

	
	
	public Long getCategoryid() {
		return categoryid;
		
	}



	public Map<String, Boolean> getWeather() {
		return weather;
	}




	public void setWeather(Map<String, Boolean> weather) {
		this.weather = weather;
	}




	public Map<String, Boolean> getFootagedate() {
		return footagedate;
	}




	public void setFootagedate(Map<String, Boolean> footagedate) {
		this.footagedate = footagedate;
	}




	public Map<String, Boolean> getUploaddate() {
		return uploaddate;
	}




	public void setUploaddate(Map<String, Boolean> uploaddate) {
		this.uploaddate = uploaddate;
	}




	public Map<String, Boolean> getSeason() {
		return season;
	}




	public void setSeason(Map<String, Boolean> season) {
		this.season = season;
	}






	public Integer getMapradious() {
		return mapradious;
	}




	public void setMapradious(Integer mapradious) {
		this.mapradious = mapradious;
	}




	public Integer getVidduration() {
		return vidduration;
	}




	public void setVidduration(Integer vidduration) {
		this.vidduration = vidduration;
	}




	public String getTags() {
		return tags;
	}




	public void setTags(String tags) {
		this.tags = tags;
	}




	public String getDesc() {
		return desc;
	}




	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getHealthstatus() {
		return healthstatus;
	}

	public void setHealthstatus(Integer healthstatus) {
		this.healthstatus = healthstatus;
	}

	public Integer getIntegrationstatus() {
		return integrationstatus;
	}

	public void setIntegrationstatus(Integer integrationstatus) {
		this.integrationstatus = integrationstatus;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Integer privacy) {
		this.privacy = privacy;
	}


	public String getVideotitle() {
		return videotitle;
	}

	public void setVideotitle(String videotitle) {
		this.videotitle = videotitle;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}




	public Boolean getPromotesiteindex() {
		return promotesiteindex;
	}




	public void setPromotesiteindex(Boolean promotesiteindex) {
		this.promotesiteindex = promotesiteindex;
	}




	public Boolean getPromotemap() {
		return promotemap;
	}




	public void setPromotemap(Boolean promotemap) {
		this.promotemap = promotemap;
	}




	public String getUserid() {
		return userid;
	}




	public void setUserid(String userid) {
		this.userid = userid;
	}




	public String getCollectionvideos() {
		return collectionvideos;
	}




	public void setCollectionvideos(String collectionvideos) {
		this.collectionvideos = collectionvideos;
	}




	public String getCollectionid() {
		return collectionid;
	}




	public void setCollectionid(String collectionid) {
		this.collectionid = collectionid;
	}




	public String getUsername() {
		return username;
	}




	public void setUsername(String username) {
		this.username = username;
	}




	public String getVideoid() {
		return videoid;
	}




	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}




	public Boolean getTerrabiscomment() {
		return terrabiscomment;
	}




	public void setTerrabiscomment(Boolean terrabiscomment) {
		this.terrabiscomment = terrabiscomment;
	}




	public Long getShootingtypeid() {
		return shootingtypeid;
	}




	public void setShootingtypeid(Long shootingtypeid) {
		this.shootingtypeid = shootingtypeid;
	}




	public Long getVideotypeid() {
		return videotypeid;
	}




	public void setVideotypeid(Long videotypeid) {
		this.videotypeid = videotypeid;
	}




}
