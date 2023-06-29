/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.socialvideo.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.VideoDTO;


@Entity
@Table(name = "videos")
public class VideoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String UUID;
	
	@Column
	private String linkedurl;

	@Column
	private Boolean link = false;

	@Column
	private Boolean ownershipverified = false;

	@Column
	private Boolean markasunwanted = false;
	
	
	public Boolean getMarkasunwanted() {
		return markasunwanted;
	}
	public void setMarkasunwanted(Boolean markasunwanted) {
		this.markasunwanted = markasunwanted;
	}
	@Column
	private Boolean terrabiscomment = false;
	
	@Column
	private Boolean vimeo = false;
	
	@Column
	private Boolean isfile = false;
	
	@Column
	private Boolean youtube = false;
	

	@Column
	private Boolean commentsent = false;
	
	@Column
	private String thumburl;

	
	private String ytvmuserid;
	private String ytvmusername;
	private String ytvmuserlink;
	
	@Column
	private String markerimgid;
	
	@Column
	private String videotypeids;
	
	
	@Column(nullable = false, name="shootingtypeid")
	private String shootingtypeid;
	
	@Column
	private String videourl;
	
	@Column(nullable = false, name="userid_fk")
	private Long userid;

	@Column(nullable = true, name="uploaderid_fk")
	private Long uploaderid;
	
	@Column(nullable = true, name="approverid_fk")
	private Long approverid;	
	
	@Column(nullable = true, name="modifierid_fk")
	private Long modifierid;	

	@Column(nullable = true, name="modifierstatusid_fk")
	private Long modifierstatusid;	
	
	@Column(nullable = true, name="rejectreason_fk")
	private Long rejectreasonid;

	
	@Column(nullable = true, name="rejectreasontext")
	private String rejectReasonText;
	
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String description;

	@Column
	private Integer privacy;

	
	@Column(nullable = true, name="categoryid_fk")
	private Long categoryid;
	
	@Column
	private String durationtxt;

	@Column
	private Integer duration;

	@Column
	private  Double ratingssum = 5.0;
	
	@Column
	private BigDecimal latitude;
	
	@Column
	private BigDecimal longitude;
	
	@Column
	private String tags;
	
	@Column
	private String weather;
	
	
	
	@Column(name = "footagedate", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date footagedate;

	@Column(name = "datemodifed", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datemodified;

	@Column(name = "datecreated", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datecreated;
	
	@Column(name = "dateapproved", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateapproved;

	
	@Column(name = "datestatusmodified", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datestatusmodified;
	
	
	
	@Column
	private String    extension;
	
	@Column
	private Integer playscount = 0;
	
	@Column
	private Integer likescount  = 0;

	@Column
	private Integer tweetscount  = 0;
	
	@Column
	private Integer status  = 0;
	
	@Column
	private Integer healthstatus  = 0;

	
	@Column
	private Integer loadstatus  = 0;
	
	
	@Column
	private Integer integrationstatus  = 0;
	

	@Column
	private Integer transferstatus  = 0;
	
	@Column
	private Integer promotesiteindex = 0;

	@Column
	private Integer promotemap = 0;
	
	
	@Column
	private String vimeoid; 
	
	@Column
	private String youtubeid;
	
	@Column
	private String    originalfilename;

	@Column
	private String    filename;
	
	@Column
	private Long    videosize;
	
	@Column
	private String    contenttype;
	
	
	@Column
    private Integer ratingscount  = 0;
	
	@Column
	private Integer collectionscount  = 0;

	@Column
    private Integer reviewscount  = 0;
	
	
	@Column
	private Boolean tstFlag = false;
	
	
	
/*	@ManyToMany
	@JoinTable(name = "categoriesvideos", joinColumns = @JoinColumn(name = "videoid_fk", referencedColumnName = "id") , inverseJoinColumns = @JoinColumn(name = "categoryid_fk", referencedColumnName = "id") )
	private Collection<CategoryEntity> categories;	*/

	
	public Integer getLoadstatus() {
		return loadstatus;
	}
	public void setLoadstatus(Integer loadstatus) {
		this.loadstatus = loadstatus;
	}
	
	
	public Boolean getTstFlag() {
		return tstFlag;
	}
	public void setTstFlag(Boolean tstFlag) {
		this.tstFlag = tstFlag;
	}
	public String getContenttype() {
		return contenttype;
	}
	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}
	public Long getVideosize() {
		return videosize;
	}
	public void setVideosize(Long videosize) {
		this.videosize = videosize;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getVimeoid() {
		return vimeoid;
	}
	public void setVimeoid(String vimeoid) {
		this.vimeoid = vimeoid;
	}

	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Integer getPlayscount() {
		return playscount;
	}
	public void setPlayscount(Integer playscount) {
		this.playscount = playscount;
	}
	public Integer getLikescount() {
		return likescount;
	}
	public void setLikescount(Integer likescount) {
		this.likescount = likescount;
	}
	public Integer getTweetscount() {
		return tweetscount;
	}
	public void setTweetscount(Integer tweetscount) {
		this.tweetscount = tweetscount;
	}
	
	
	
	

	public VideoEntity() {}
	
	public VideoEntity(Long userid, String title, String description, Integer duration, BigDecimal longitude, BigDecimal latitude, String tags, Integer playscount, Integer likescount ) {
			this.userid = userid;
			this.title = title;
			this.description = description;
			this.duration = duration;
			this.longitude = longitude;
			this.latitude = latitude;
			this.tags = tags;
			this.playscount = playscount;
			this.likescount = likescount;

	}
	
	
	
	public VideoDTO DTO() {

		VideoDTO DTO = new VideoDTO();
		DTO.setId(this.id);
		DTO.setUUID(this.UUID);
		DTO.setUserid(this.userid);
		DTO.setUploaderid(this.uploaderid);
		DTO.setOwnershipverified(this.ownershipverified);
		DTO.setTitle(this.title);
		DTO.setDescription(this.description);
		DTO.setLinkedurl(this.linkedurl);
		DTO.setLink(this.link);
		DTO.setIsfile(this.isfile);
		DTO.setDuration(this.duration);
		DTO.setDurationtxt(this.durationtxt);
		DTO.setPrivacy(this.privacy);
		DTO.setLongitude(this.longitude);
		DTO.setLatitude(this.latitude);
		DTO.setTags(this.tags);
		DTO.setPlayscount(this.playscount);
		DTO.setLikescount(this.likescount);
		DTO.setRatingscount(this.ratingscount);
		DTO.setRatingssum(this.ratingssum);
		DTO.setReviewscount(this.reviewscount);
		DTO.setLikescount(this.likescount);
		DTO.setStatus(this.status);
		DTO.setLoadstatus(this.loadstatus);
		DTO.setHealthstatus(this.healthstatus);
		DTO.setIntegrationstatus(this.integrationstatus);
		DTO.setTransferstatus(this.transferstatus);
		DTO.setVimeoid(this.vimeoid);
		DTO.setFilename(this.filename);
		DTO.setOriginalfilename(this.originalfilename);
		DTO.setExtension(this.extension);
		DTO.setContenttype(this.contenttype);
		DTO.setVideosize(this.videosize);
		DTO.setFootagedate(this.footagedate);
		//DTO.setWeather(this.weather);
		DTO.setYoutube(this.youtube);
		DTO.setYoutubeid(this.youtubeid);
		DTO.setVimeo(this.vimeo);
		DTO.setVimeoid(this.vimeoid);
		DTO.setThumburl(this.thumburl);
		DTO.setVideourl(this.videourl);
		DTO.setCollectionscount(this.collectionscount);
		DTO.setPromotemap(this.promotemap);
		DTO.setPromotesiteindex(this.promotesiteindex);
		DTO.setCommentsent(this.commentsent);
		//DTO.setDatecreated(this.datecreated);
		//DTO.setCategoryid(this.categoryid.toString());
		DTO.setShootingtypeid(this.shootingtypeid);
		DTO.setTstFlag(this.tstFlag);
		DTO.setMarkasunwanted(this.markasunwanted);
		
		return DTO;
	}

	public void LOAD(VideoDTO DTO) {
		this.id = DTO.getId();
		this.UUID = DTO.getUUID();
		this.userid = DTO.getUserid();
		this.uploaderid = DTO.getUploaderid();
		this.ownershipverified = DTO.getOwnershipverified();
		//this.categoryid = Long.parseLong(DTO.getCategoryid());
		this.title = DTO.getTitle();
		this.description = DTO.getDescription();
		this.linkedurl = DTO.getLinkedurl();
		this.link = DTO.getLink();
		this.isfile = DTO.getIsfile();
		this.duration =  DTO.getDuration();
		this.durationtxt =  DTO.getDurationtxt();
		this.privacy = DTO.getPrivacy();
		this.longitude =  DTO.getLongitude();
		this.latitude =  DTO.getLatitude();
		this.tags =  DTO.getTags();
		//this.weather = DTO.getWeather();
		this.footagedate = DTO.getFootagedate();
		this.playscount =  DTO.getPlayscount();
		this.likescount =  DTO.getLikescount();
		this.tweetscount = DTO.getTweetscount();
		this.reviewscount =  DTO.getReviewscount();
		this.ratingscount =  DTO.getRatingscount();
		this.ratingssum =  DTO.getRatingssum();
		this.youtube = DTO.getYoutube();
		this.youtubeid = DTO.getYoutubeid();
		this.vimeo = DTO.getVimeo();
		this.vimeoid = DTO.getVimeoid();
		this.thumburl = DTO.getThumburl();
		this.videourl = DTO.getVideourl();
		this.collectionscount = DTO.getCollectionscount();
		this.status = DTO.getStatus();
		this.loadstatus = DTO.getLoadstatus();
		this.healthstatus = DTO.getHealthstatus();
		this.integrationstatus = DTO.getIntegrationstatus();
		this.transferstatus = DTO.getTransferstatus();
		this.vimeoid = DTO.getVimeoid();
		this.filename = DTO.getFilename();
		this.originalfilename = DTO.getOriginalfilename();
		this.extension = DTO.getExtension();
		this.contenttype = DTO.getContenttype();
		this.videosize = DTO.getVideosize();
		this.promotemap = DTO.getPromotemap();
		this.promotesiteindex = DTO.getPromotesiteindex();
		this.commentsent = DTO.getCommentsent();
		this.videotypeids = DTO.getVideotypeids();
		this.markerimgid = DTO.getMarkerimgid();
		this.shootingtypeid = DTO.getShootingtypeid();
		this.tstFlag = DTO.getTstFlag();
		this.markasunwanted = DTO.getMarkasunwanted();
		
	}
	
	
	
	public void LOADCALC(VideoDTO DTO) {
		this.playscount =  DTO.getPlayscount();
		this.likescount =  DTO.getLikescount();
		this.reviewscount =  DTO.getReviewscount();
		this.ratingscount =  DTO.getRatingscount();
		this.ratingssum =  DTO.getRatingssum();
		this.tweetscount = DTO.getTweetscount();
		this.collectionscount = DTO.getCollectionscount();

		
	}

	
	public void LOADVIDEOEDIT(PublicVideoDTO DTO,Boolean updateLink) {
		this.title = DTO.getTitle();
		this.description = DTO.getDescription();
		this.tags =  DTO.getTags();
		this.videotypeids = DTO.getVideotypeids();
		this.markerimgid = DTO.getMarkerimgid();
		this.shootingtypeid = DTO.getShootingtypeid();
		this.markasunwanted = DTO.getMarkasunwanted();
	}

	
	
	public void LOADVIDEOEDIT(VideoDTO DTO,Boolean updateLink) {
		this.title = DTO.getTitle();
		this.description = DTO.getDescription();
		this.privacy = DTO.getPrivacy();
		this.longitude =  DTO.getLongitude();
		this.latitude =  DTO.getLatitude();
		this.tags =  DTO.getTags();
		if(updateLink)
			this.linkedurl =  DTO.getLinkedurl();
		//this.weather = DTO.getWeather();
		this.footagedate = DTO.getFootagedate();
		//this.categoryid = Long.parseLong(DTO.getCategoryid());
		//this.categories = Arrays.asList(category);
		this.videotypeids = DTO.getVideotypeids();
		this.markerimgid = DTO.getMarkerimgid();
		this.shootingtypeid = DTO.getShootingtypeid();
		this.markasunwanted = DTO.getMarkasunwanted();
		
		
	}
	
	
	public void like() {
		if(likescount==null)
			likescount = 0;		
		
		this.likescount = likescount+1;
	}
	
	public void unlike() {
		this.likescount = likescount-1;
	}
	
	
	public void play() {
		if(playscount==null)
			playscount = 0;
		
		this.playscount = playscount+1;
	}
	
	public void rate() {
		if(ratingscount==null)
			ratingscount = 0;
		
		this.ratingscount = ratingscount+1;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
	
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Integer getPrivacy() {
		return privacy;
	}
	public void setPrivacy(Integer privacy) {
		this.privacy = privacy;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public Date getFootagedate() {
		return footagedate;
	}
	public void setFootagedate(Date footagedate) {
		this.footagedate = footagedate;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getOriginalfilename() {
		return originalfilename;
	}
	public void setOriginalfilename(String originalfilename) {
		this.originalfilename = originalfilename;
	}
	public Integer getRatingscount() {
		return ratingscount;
	}
	public void setRatingscount(Integer ratingscount) {
		this.ratingscount = ratingscount;
	}
	public Integer getReviewscount() {
		return reviewscount;
	}
	public void setReviewscount(Integer reviewscount) {
		this.reviewscount = reviewscount;
	}

	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public String getLinkedurl() {
		return linkedurl;
	}
	public void setLinkedurl(String linkedurl) {
		this.linkedurl = linkedurl;
	}
	public Boolean getLink() {
		return link;
	}
	public void setLink(Boolean link) {
		this.link = link;
	}
	
	public Boolean getYoutube() {
		return youtube;
	}
	public void setYoutube(Boolean youtube) {
		this.youtube = youtube;
	}
	public Boolean getVimeo() {
		return vimeo;
	}
	public void setVimeo(Boolean vimeo) {
		this.vimeo = vimeo;
	}
	public String getThumburl() {
		return thumburl;
	}
	public void setThumburl(String thumburl) {
		this.thumburl = thumburl;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public Integer getCollectionscount() {
		return collectionscount;
	}
	public void setCollectionscount(Integer collectionscount) {
		this.collectionscount = collectionscount;
	}
	
	public String getYoutubeid() {
		return youtubeid;
	}
	public void setYoutubeid(String youtubeid) {
		this.youtubeid = youtubeid;
	}
	public String getVideourl() {
		return videourl;
	}
	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}
	public String getDurationtxt() {
		return durationtxt;
	}
	public void setDurationtxt(String durationtxt) {
		this.durationtxt = durationtxt;
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
	public Boolean getIsfile() {
		return isfile;
	}
	public void setIsfile(Boolean isfile) {
		this.isfile = isfile;
	}
	public Long getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}
	public Long getRejectreasonid() {
		return rejectreasonid;
	}
	public void setRejectreasonid(Long rejectreasonid) {
		this.rejectreasonid = rejectreasonid;
	}
	public Double getRatingssum() {
		return ratingssum;
	}
	public void setRatingssum(Double ratingssum) {
		this.ratingssum = ratingssum;
	}
	public Integer getTransferstatus() {
		return transferstatus;
	}
	public void setTransferstatus(Integer transferstatus) {
		this.transferstatus = transferstatus;
	}
	public Integer getPromotesiteindex() {
		return promotesiteindex;
	}
	public void setPromotesiteindex(Integer promotesiteindex) {
		this.promotesiteindex = promotesiteindex;
	}
	public Integer getPromotemap() {
		return promotemap;
	}
	public void setPromotemap(Integer promotemap) {
		this.promotemap = promotemap;
	}
	
	public void share() {
		if(tweetscount==null)
			tweetscount = 0;
		
		this.tweetscount = tweetscount+1;
	}

	public Boolean getOwnershipverified() {
		return ownershipverified;
	}
	public void setOwnershipverified(Boolean ownershipverified) {
		this.ownershipverified = ownershipverified;
	}
	public Long getUploaderid() {
		return uploaderid;
	}
	public void setUploaderid(Long uploaderid) {
		this.uploaderid = uploaderid;
	}
	public Boolean getCommentsent() {
		return commentsent;
	}
	public void setCommentsent(Boolean commentsent) {
		this.commentsent = commentsent;
	}
	public String getShootingtypeid() {
		return shootingtypeid;
	}
	public void setShootingtypeid(String shootingtypeid) {
		this.shootingtypeid = shootingtypeid;
	}
	public Boolean getTerrabiscomment() {
		return terrabiscomment;
	}
	public void setTerrabiscomment(Boolean terrabiscomment) {
		this.terrabiscomment = terrabiscomment;
	}
	public String getMarkerimgid() {
		return markerimgid;
	}
	public void setMarkerimgid(String markerimgid) {
		this.markerimgid = markerimgid;
	}
	public String getVideotypeids() {
		return videotypeids;
	}
	public void setVideotypeids(String videotypeids) {
		this.videotypeids = videotypeids;
	}
	public Long getApproverid() {
		return approverid;
	}
	public void setApproverid(Long approverid) {
		this.approverid = approverid;
	}
	public Long getModifierid() {
		return modifierid;
	}
	public void setModifierid(Long modifierid) {
		this.modifierid = modifierid;
	}
	public Date getDatemodified() {
		return datemodified;
	}
	public void setDatemodified(Date datemodified) {
		this.datemodified = datemodified;
	}
	public Date getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}
	public Date getDateapproved() {
		return dateapproved;
	}
	public void setDateapproved(Date dateapproved) {
		this.dateapproved = dateapproved;
	}
	public Long getModifierstatusid() {
		return modifierstatusid;
	}
	public void setModifierstatusid(Long modifierstatusid) {
		this.modifierstatusid = modifierstatusid;
	}
	public Date getDatestatusmodified() {
		return datestatusmodified;
	}
	public void setDatestatusmodified(Date datestatusmodified) {
		this.datestatusmodified = datestatusmodified;
	}
	public String getYtvmuserid() {
		return ytvmuserid;
	}
	public void setYtvmuserid(String ytvmuserid) {
		this.ytvmuserid = ytvmuserid;
	}
	public String getYtvmusername() {
		return ytvmusername;
	}
	public void setYtvmusername(String ytvmusername) {
		this.ytvmusername = ytvmusername;
	}
	public String getYtvmuserlink() {
		return ytvmuserlink;
	}
	public void setYtvmuserlink(String ytvmuserlink) {
		this.ytvmuserlink = ytvmuserlink;
	}
	public String getRejectReasonText() {
		return rejectReasonText;
	}
	public void setRejectReasonText(String rejectReasonText) {
		this.rejectReasonText = rejectReasonText;
	}
	
	
}