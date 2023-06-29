package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AjaxResultDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	
	
	
	
	
	public AjaxResultDTO() {
	}
	

	
	
	
	
	
	public AjaxResultDTO(HttpStatus status) { this.status = status.toString();}
	public AjaxResultDTO(HttpStatus status, String message) { this.status = status.toString(); this.message = message;}
	public AjaxResultDTO(HttpStatus status, PublicVideoDTO video) { this.status = status.toString(); this.video = video;}
	public AjaxResultDTO(HttpStatus status, CollectionDTO collection) { this.status = status.toString(); this.collection = collection;}
	

	public AjaxResultDTO(List<List<PublicVideoDTO>> videolist,  HttpStatus status) { 
		this.status = status.toString();
		this.videolist = videolist;
	}



	
	
	public AjaxResultDTO(NotificationDTO signature,List<NotificationDTO> notificationlist,  Integer total, HttpStatus status) { 
		this.status = status.toString();
		this.notificationlist = notificationlist;
		this.total = total;
	}

	
	public AjaxResultDTO(PublicVideoPointDTO signature, List<PublicVideoPointDTO> pointslist,  Integer total, HttpStatus status) { 
		this.status = status.toString();
		this.pointslist = pointslist;
		this.total = total;
	}
	
	
	
	
	
	public AjaxResultDTO(List<List<PublicVideoDTO>> videolist, List<TagDTO> taglist, PaginatorDTO paginator, HttpStatus status) { 
		this.status = status.toString();
		this.videolist = videolist;
		this.taglist = taglist;
		this.paginator = paginator;
	}
	
	
	public AjaxResultDTO(List<List<PublicVideoDTO>> videolist, String tagliststring, PaginatorDTO paginator, HttpStatus status) { 
		this.status = status.toString();
		this.videolist = videolist;
		this.tagliststring = tagliststring;
		this.paginator = paginator;
	}
	
	
	public AjaxResultDTO(List<List<PublicVideoDTO>> videolist,  PaginatorDTO paginator, HttpStatus status) { 
		this.status = status.toString();
		this.videolist = videolist;
		this.paginator = paginator;
	}

	


	@JsonProperty("pointslist")
	List<PublicVideoPointDTO> pointslist;
	

	@JsonProperty("notificationlist")
	List<NotificationDTO>  notificationlist;
	
	@JsonProperty("videolist")
	List<List<PublicVideoDTO>> videolist;

	
	@JsonProperty("taglist")
	List<TagDTO> taglist;
	
	@JsonProperty("tagliststring")
	private String  tagliststring;
	

	@JsonProperty("status")
	private String status;
	

	@JsonProperty("message")
	private String message;

	
	@JsonProperty("total")
	private Integer total;
	
	@JsonProperty("paginator")
	private PaginatorDTO paginator;
	
	
	@JsonProperty("video")
	private PublicVideoDTO video;

	
	public CollectionDTO getCollection() {
		return collection;
	}




	public void setCollection(CollectionDTO collection) {
		this.collection = collection;
	}


	@JsonProperty("collection")
	private CollectionDTO collection;
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public List<List<PublicVideoDTO>> getVideolist() {
		return videolist;
	}


	public void setVideolist(List<List<PublicVideoDTO>> videolist) {
		this.videolist = videolist;
	}


	public List<TagDTO> getTaglist() {
		return taglist;
	}


	public void setTaglist(List<TagDTO> taglist) {
		this.taglist = taglist;
	}


	public PaginatorDTO getPaginator() {
		return paginator;
	}


	public void setPaginator(PaginatorDTO paginator) {
		this.paginator = paginator;
	}



	public String getTagliststring() {
		return tagliststring;
	}


	public void setTagliststring(String tagliststring) {
		this.tagliststring = tagliststring;
	}




	public PublicVideoDTO getVideo() {
		return video;
	}




	public void setVideo(PublicVideoDTO video) {
		this.video = video;
	}




	public List<PublicVideoPointDTO> getPointslist() {
		return pointslist;
	}




	public void setPointslist(List<PublicVideoPointDTO> pointslist) {
		this.pointslist = pointslist;
	}




	public Integer getTotal() {
		return total;
	}




	public void setTotal(Integer total) {
		this.total = total;
	}




	public List<NotificationDTO> getNotificationlist() {
		return notificationlist;
	}




	public void setNotificationlist(List<NotificationDTO> notificationlist) {
		this.notificationlist = notificationlist;
	}



	
	
}
