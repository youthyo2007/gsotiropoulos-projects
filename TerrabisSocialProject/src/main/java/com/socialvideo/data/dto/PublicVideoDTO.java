package com.socialvideo.data.dto;

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
public class PublicVideoDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;


	private Long id;
	
	private String UUID;

	private String title;

	private String description;

	private String distance;
	
	private Integer duration;
	
	private String durationtxt;

	private String tags;
	
	private List<IDDescrDTO> videoTypeList; 
	
	
	private List<VideoReviewDTO> reviews; 
	
	private Boolean youtube = false;
	
	private String youtubeid;

	private Boolean vimeo = false;
	
	@JsonIgnore
	private Integer status;
	

	private Boolean ownershipverified = false;
	
	private Boolean markasunwanted = false;
	
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
    
    private Integer tweetscount;
    
    private Integer reviewscount;
    
	@JsonIgnore
	private Integer pathsort = 0;
    
	private  Double ratingssum = 5.0;

	private Date datecreated;
	
	private Boolean promotemap = false;

	private Boolean link = true;
	
	
	public List<PublicVideoDTO> relatedvideoslist;

	
	public UserDTOSmall user;
	
	public UserDTOSmall uploader;
	
	public String relatedvideostype;

	
	
	//WHEN USED AS A FORM
	@JsonIgnore
	private String selectedvideotypelist = "";
	
	
	
	public PublicVideoDTO() {}
	
	
	public String getDescShort() {
		return (description!=null && description.length()>301) ? description.substring(0, 300)+"...": description;
	}	

	
	
	
	public String getShortAndSmart_DATECREATED() {
		return SmartDateTimeUtil.getDateString_shortAndSmart(datecreated);
	}
	
	
	public String getDateCreatedAsString() {
		return datecreated!=null ? SmartDateTimeUtil.getDateFormatString(datecreated): "";
	}

	public Boolean getLink() {
		return link;
	}





	public void setLink(Boolean link) {
		this.link = link;
	}


	//CONVERT TAG STRING TO TAG LIST
	public List<String> getTagslist() {
		if ((tags!=null) && (tags.trim().length()>0)) {
		  Iterable<String> split = Splitter.on( "," ).omitEmptyStrings().trimResults().split( tags );
		  return Lists.newArrayList( split );
		}
		else 
			return new ArrayList<>();
		
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
		this.sharethumburl = "http://terrabisvideothumb.s3.amazonaws.com/"+this.UUID+".png";
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


	public List<PublicVideoDTO> getRelatedvideoslist() {
		return relatedvideoslist;
	}


	public void setRelatedvideoslist(List<PublicVideoDTO> relatedvideoslist) {
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


	public UserDTOSmall getUser() {
		return user;
	}


	public void setUser(UserDTOSmall user) {
		this.user = user;
	}


	public Integer getTweetscount() {
		return tweetscount;
	}


	public void setTweetscount(Integer tweetscount) {
		this.tweetscount = tweetscount;
	}


	public List<IDDescrDTO> getVideoTypeList() {
		return videoTypeList;
	}


	public void setVideoTypeList(List<IDDescrDTO> videoTypeList) {
		this.videoTypeList = videoTypeList;
	}


	public String getSelectedvideotypelist() {
		return selectedvideotypelist;
	}


	public void setSelectedvideotypelist(String selectedvideotypelist) {
		this.selectedvideotypelist = selectedvideotypelist;
	}


	public Integer getPathsort() {
		return pathsort;
	}


	public void setPathsort(Integer pathsort) {
		this.pathsort = pathsort;
	}


	public Boolean getOwnershipverified() {
		return ownershipverified;
	}


	public void setOwnershipverified(Boolean ownershipverified) {
		this.ownershipverified = ownershipverified;
	}


	public UserDTOSmall getUploader() {
		return uploader;
	}


	public void setUploader(UserDTOSmall uploader) {
		this.uploader = uploader;
	}


	public Boolean getMarkasunwanted() {
		return markasunwanted;
	}


	public void setMarkasunwanted(Boolean markasunwanted) {
		this.markasunwanted = markasunwanted;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public List<VideoReviewDTO> getReviews() {
		return reviews;
	}


	public void setReviews(List<VideoReviewDTO> reviews) {
		this.reviews = reviews;
	}


	public Integer getReviewscount() {
		return reviewscount;
	}


	public void setReviewscount(Integer reviewscount) {
		this.reviewscount = reviewscount;
	}

	

}
