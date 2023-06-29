package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.Date;

import com.socialvideo.utilities.SmartDateTimeUtil;



public class NotificationDTO implements Serializable {


	private static final long serialVersionUID = 1L;
	

	
	private Long id;

	private Long userid;
	
	private Long videoid;
	
	private Long actionuserid;
	
	private Long collectionid;
	

	private Long ratingid;
	

	private Long socialnetworkid;
	
	private String activity;
	
	private Integer status;

	private Integer total;
	
	private Date  datecreated;
	
	
	private UserDTO actionuser;

	private IDDescrDTO socialnetwork;
	
	private IDDescrDTO rating;
	
	private CollectionDTO collection;
	
	private PublicVideoDTO video;	 
	
	
	public NotificationDTO() {}

	public NotificationDTO(String activity,Long videoid, Long collectionid) {
		this.activity = activity;
		this.videoid = videoid;
		this.collectionid = collectionid;
		
	}

	
	public String getSmartTime() {
		return datecreated!=null ? SmartDateTimeUtil.getDifferenceBtwTime(datecreated): "";
	}	
	
	
	public String getDateCreatedAsString() {
		return datecreated!=null ? SmartDateTimeUtil.getDateFormatString(datecreated): "";
	}		
	

	
	public Long getRatingid() {
		return ratingid;
	}

	public void setRatingid(Long ratingid) {
		this.ratingid = ratingid;
	}

	public Long getSocialnetworkid() {
		return socialnetworkid;
	}

	public void setSocialnetworkid(Long socialnetworkid) {
		this.socialnetworkid = socialnetworkid;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getUserid() {
		return userid;
	}



	public void setUserid(Long userid) {
		this.userid = userid;
	}



	public Long getVideoid() {
		return videoid;
	}



	public void setVideoid(Long videoid) {
		this.videoid = videoid;
	}




	public Long getCollectionid() {
		return collectionid;
	}



	public void setCollectionid(Long collectionid) {
		this.collectionid = collectionid;
	}



	public String getActivity() {
		return activity;
	}



	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Long getActionuserid() {
		return actionuserid;
	}

	public void setActionuserid(Long actionuserid) {
		this.actionuserid = actionuserid;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public UserDTO getActionuser() {
		return actionuser;
	}

	public void setActionuser(UserDTO actionuser) {
		this.actionuser = actionuser;
	}

	public IDDescrDTO getSocialnetwork() {
		return socialnetwork;
	}

	public void setSocialnetwork(IDDescrDTO socialnetwork) {
		this.socialnetwork = socialnetwork;
	}

	public IDDescrDTO getRating() {
		return rating;
	}

	public void setRating(IDDescrDTO rating) {
		this.rating = rating;
	}

	public CollectionDTO getCollection() {
		return collection;
	}

	public void setCollection(CollectionDTO collection) {
		this.collection = collection;
	}

	public PublicVideoDTO getVideo() {
		return video;
	}

	public void setVideo(PublicVideoDTO video) {
		this.video = video;
	}



}
