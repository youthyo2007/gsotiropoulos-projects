package com.socialvideo.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.socialvideo.data.dto.NotificationEnum;




@Entity
@Table(name = "notifications")
public class NotificationEntity implements Serializable {


	private static final long serialVersionUID = 1L;
	

	
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, name="userid_fk")
	private Long userid;
	
	@Column(nullable = true, name="videoid_fk")
	private Long videoid;
	
	@Column(nullable = true, name="actionuserid_fk")
	private Long actionuserid;
	
	@Column(nullable = true, name="collectionid_fk")
	private Long collectionid;
	

	@Column(nullable = true, name="ratingid_fk")
	private Long ratingid;
	
	
	@Column(nullable = true, name="socialnetworkid_fk")
	private Long socialnetworkid;
	
	
	@Column
	private String activity;
	
	
	@Column
	private Integer status;
	
	
	@Column(name = "datecreated", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date  datecreated;
	
	
	
	
	public NotificationEntity() {}

	public NotificationEntity(NotificationEnum activity,Long userid, Long actionuserid, Long videoid, Long collectionid, Long ratingid, Long socialnetworkid, Date  datecreated) {
		this.activity = activity.toString();
		this.userid = userid;
		this.actionuserid = actionuserid;
		this.videoid = videoid;
		this.collectionid = collectionid;
		this.ratingid = ratingid;
		this.socialnetworkid = socialnetworkid;
		this.datecreated = datecreated;
		
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
	
	
	
	
	
	

}
