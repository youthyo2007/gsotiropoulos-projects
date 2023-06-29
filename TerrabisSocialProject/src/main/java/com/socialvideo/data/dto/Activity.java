package com.socialvideo.data.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.socialvideo.data.dto.maps.PointDTO;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	public enum ActivityEnum {
		TERMSOFSERVICEVIEW, CONTACTUSVIEW, PRIVACYPOLICYVIEW, ABOUTUSVIEW, INDEXVIEW, MAPVIEW, MAPLISTVIEW, VIDEOCATALOGVIEW, ALBUMCATALOGVIEW, PATHCATALOGVIEW, VIDEOSINGLEPAGEVIEW, FULLMAPVIEW, USERVIDSVIEW, USERFAVORITESVIEW, USERWATCHLATERVIEW, USERALBUMSVIEW, USERPATHSVIEW, USERPROFILEVIEW, SEARCH, PLAY, CLAIM,RATE, LIKE, SHARE, REVIEW, ADDFAV, ADDWATCHL, FILTER
	}


	@JsonProperty("targetuserid")
	private Long targetuserid = new Long(-1);
	
	@JsonProperty("searchtext")
	private String searchtext = "-1";

	@JsonProperty("targetlatlng")
	private PointDTO targetlatlng = null;
	
	@JsonProperty("movieid")
	private Long movieid =  new Long(-1);

	@JsonProperty("rating")
	private String rating = "-1";

	
	@JsonProperty("activity")
	private String activity;
	
	@JsonProperty("identity")
	private ActivityIdentity identity;
	

	public Activity(ActivityEnum activity, ActivityIdentity identity) {
		this.identity =  identity;
		this.activity = activity.toString();		
	}
	
	
	public Activity(ActivityEnum activity, ActivityIdentity identity, Long targetuserid, Long movieid, String searchtxt, PointDTO targetlatlng, String rating) {
		this.identity =  identity;
		this.targetuserid = targetuserid==null ? -1 : targetuserid;
		this.movieid = movieid == null ? -1 : movieid;
		this.searchtext = searchtxt ==null ? "-1" : searchtxt;
		this.targetlatlng = targetlatlng== null ? null : targetlatlng;
		this.rating = rating==null ? "-1" : rating;
		this.activity = activity.toString();
	}


	public Activity(String activity, ActivityIdentity identity, Long targetuserid, Long movieid, String searchtxt, PointDTO targetlatlng, String rating) {
		this.identity =  identity;
		this.targetuserid = targetuserid==null ? -1 : targetuserid;
		this.movieid = movieid == null ? -1 : movieid;
		this.searchtext = searchtxt ==null ? "-1" : searchtxt;
		this.targetlatlng = targetlatlng== null ? null : targetlatlng;
		this.rating = rating==null ? "-1" : rating;
		this.activity = activity.toString();
	}
	


	public Long getMovieid() {
		return movieid;
	}


	public void setMovieid(Long movieid) {
		this.movieid = movieid;
	}


	public String getRating() {
		return rating;
	}


	public void setRating(String rating) {
		this.rating = rating;
	}


	public String getActivity() {
		return activity;
	}


	public void setActivity(String activity) {
		this.activity = activity;
	}

	

	public String getSearchtext() {
		return searchtext;
	}


	public void setSearchtext(String searchtext) {
		this.searchtext = searchtext;
	}


	public Long getTargetuserid() {
		return targetuserid;
	}




	public void setTargetuserid(Long targetuserid) {
		this.targetuserid = targetuserid;
	}



}
