package com.socialvideo.data.dto.mobile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.socialvideo.config.AWSConfiguration;
import com.socialvideo.constant.GoogleConstants;
import com.socialvideo.utilities.SmartDateTimeUtil;
import com.socialvideo.utilities.WebUtility;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileVideoDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private Long id;
	
	private String UUID;

	private String title;

	private String description;

	private String distance;
	
	private Integer duration;
	
	private String durationtxt;

	private BigDecimal latitude;
	
	private BigDecimal longitude;

	private String tags;
	
	
	private Boolean youtube = false;
	
	private String youtubeid;

	private Boolean vimeo = false;
	
	private String vimeoid;
	
	private String thumburl;
	
	private String videourl;	

	private String sharethumburl;	
		
	private String shootingtypeid;

	private String videotypeids;
	
	private String markerimgid;
	
	private String staticMapURI; 

	
	private Integer likescount;
	
	private Integer playscount;
	
    private Integer ratingscount;
    
    
	private  Double ratingssum = 5.0;

	private Date datecreated;
	
	private Boolean promotemap = false;
	
	public List<MobileVideoDTO> relatedvideoslist;

	
	public MobileUserDTOSmall user;
	
	public String relatedvideostype;

	public MobileVideoDTO() {}
	
	
	public String getDescShort() {
		return (description!=null && description.length()>301) ? description.substring(0, 300)+"...": description;
	}	
	
	
	public String getDateCreatedAsString() {
		return datecreated!=null ? SmartDateTimeUtil.getDateFormatString(datecreated): "";
	}


	public String getUUID() {
		return UUID;
	}


	public void setUUID(String uUID) {
		UUID = uUID;
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


	public String getDistance() {
		return distance;
	}


	public void setDistance(String distance) {
		this.distance = distance;
	}


	public Integer getDuration() {
		return duration;
	}


	public void setDuration(Integer duration) {
		this.duration = duration;
	}


	public String getDurationtxt() {
		return durationtxt;
	}


	public void setDurationtxt(String durationtxt) {
		this.durationtxt = durationtxt;
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


	public String getTags() {
		return tags;
	}


	public void setTags(String tags) {
		this.tags = tags;
	}


	public String getThumburl() {
		return thumburl;
	}


	public void setThumburl(String thumburl) {
		this.thumburl = thumburl;
	}


	public String getVideourl() {
		return videourl;
	}


	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}


	public String getSharethumburl() {
		return sharethumburl;
	}


	public void setSharethumburl(String sharethumburl) {
		this.sharethumburl = sharethumburl;
	}


	public String getShootingtypeid() {
		return shootingtypeid;
	}


	public void setShootingtypeid(String shootingtypeid) {
		this.shootingtypeid = shootingtypeid;
	}


	public String getVideotypeids() {
		return videotypeids;
	}


	public void setVideotypeids(String videotypeids) {
		this.videotypeids = videotypeids;
	}


	public String getStaticMapURI() {
		return staticMapURI;
	}


	public void setStaticMapURI(String staticMapURI) {
		this.staticMapURI = staticMapURI;
	}


	public Integer getLikescount() {
		return likescount;
	}


	public void setLikescount(Integer likescount) {
		this.likescount = likescount;
	}


	public Integer getPlayscount() {
		return playscount;
	}


	public void setPlayscount(Integer playscount) {
		this.playscount = playscount;
	}


	public Integer getRatingscount() {
		return ratingscount;
	}


	public void setRatingscount(Integer ratingscount) {
		this.ratingscount = ratingscount;
	}


	public Double getRatingssum() {
		return ratingssum;
	}


	public void setRatingssum(Double ratingssum) {
		this.ratingssum = ratingssum;
	}


	public Date getDatecreated() {
		return datecreated;
	}


	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public List<MobileVideoDTO> getRelatedvideoslist() {
		return relatedvideoslist;
	}


	public void setRelatedvideoslist(List<MobileVideoDTO> relatedvideoslist) {
		this.relatedvideoslist = relatedvideoslist;
	}


	public String getRelatedvideostype() {
		return relatedvideostype;
	}


	public void setRelatedvideostype(String relatedvideostype) {
		this.relatedvideostype = relatedvideostype;
	}


	public String getMarkerimgid() {
		return markerimgid;
	}


	public void setMarkerimgid(String markerimgid) {
		this.markerimgid = markerimgid;
	}


	public Boolean getYoutube() {
		return youtube;
	}


	public void setYoutube(Boolean youtube) {
		this.youtube = youtube;
	}


	public String getYoutubeid() {
		return youtubeid;
	}


	public void setYoutubeid(String youtubeid) {
		this.youtubeid = youtubeid;
	}


	public Boolean getVimeo() {
		return vimeo;
	}


	public void setVimeo(Boolean vimeo) {
		this.vimeo = vimeo;
	}


	public String getVimeoid() {
		return vimeoid;
	}


	public void setVimeoid(String vimeoid) {
		this.vimeoid = vimeoid;
	}


	public Boolean getPromotemap() {
		return promotemap;
	}


	public void setPromotemap(Boolean promotemap) {
		this.promotemap = promotemap;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public MobileUserDTOSmall getUser() {
		return user;
	}


	public void setUser(MobileUserDTOSmall user) {
		this.user = user;
	}

	

}
