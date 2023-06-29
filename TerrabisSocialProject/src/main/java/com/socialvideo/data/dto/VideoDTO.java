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
public class VideoDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	

	
	private Long id;
	
	private String UUID;

	@JsonIgnore
	private Long userid;

	@JsonIgnore
	private Long approverid;
	
	@JsonIgnore
	private Long uploaderid;

	private String title;
	
	private String linkedurl;

	private Boolean link;

	private Boolean isfile = false;
	
	@JsonIgnore
	private Boolean terrabiscomment = false;
	
	@JsonIgnore
	private Boolean tstFlag = false;
	
	private String description;

	
	private String distance;
	

	private Integer rating;
	
	@JsonIgnore
	private Integer privacy;

	private Integer duration;
	
	private String durationtxt;

	private BigDecimal latitude;
	
	
	
	@JsonIgnore
	private Integer status;

	@JsonIgnore
	private Integer healthstatus  = 0;

	@JsonIgnore
	private Integer loadstatus  = 0;
	


	@JsonIgnore
	private Integer integrationstatus  = 0;
	
	@JsonIgnore
	private Integer transferstatus = 0;
	
	private Integer promotesiteindex = 0;

	private Integer promotemap = 0;

	@JsonIgnore
	private Integer pathsort = 0;
	
	private String    vimeoid;
	
	@JsonIgnore
	private String    originalfilename;
	
	@JsonIgnore
	private String    filename;

	@JsonIgnore
	private String    extension;
	
	@JsonIgnore
	private Long    videosize;
	
	@JsonIgnore
	private String    contenttype;
	
	@JsonIgnore
	private String weather;
	
	private Date footagedate;
	
	private BigDecimal longitude;

	private String tags;
	
	@JsonIgnore
	private String dynamicuserid;
	
	@JsonIgnore
	private String dynamicvalue;

	@JsonIgnore
	private String dynamiccount;
	
	@JsonIgnore
	private UserDTOSmall dynamicuser;

	private Boolean ownershipverified = false;
	

	private Boolean markasunwanted = false;
	
	public Boolean getMarkasunwanted() {
		return markasunwanted;
	}

	public void setMarkasunwanted(Boolean markasunwanted) {
		this.markasunwanted = markasunwanted;
	}

	private Boolean youtube = false;

	private Boolean vimeo = false;
	
	@JsonIgnore
	private Boolean commentsent = false;


	
	private String thumburl;
	private String videourl;	
	private String youtubeid;

	private String sharethumburl;	
	
	@JsonIgnore
	private MultipartFile filedata;	

	private String rejectReasonText;

	private Integer likescount;
	private Integer playscount;
    private Integer tweetscount;
    private Integer ratingscount;
    private Integer reviewscount;
	private  Double ratingssum = 5.0;
	private Integer collectionscount;
	
	@JsonIgnore
	private String ytvmuserid;
	@JsonIgnore
	private String ytvmusername;
	@JsonIgnore
	private String ytvmuserlink;
	
	
	@JsonIgnore
	private String categoryid;
	
	private Date datecreated;
	@JsonIgnore
	private Date datemodified;
	@JsonIgnore
	private Date datestatusmodified;
	@JsonIgnore
	private Date dateapproved;
	

/*	
	private CategoryDTO category;

	private String selectedCategory;
	*/
	
	
	private List<CollectionDTO> collections;
	
	private String shootingtypeid;

	
	private String markerimgid;
	
	private String videotypeids;
	
	private List<IDDescrDTO> videoTypeList; 
	
	private UserDTOSmall user;
	
	@JsonIgnore
	private UserDTOSmall uploader;	

	@JsonIgnore
	private UserDTOSmall approver;	

	@JsonIgnore
	private UserDTOSmall modifier;	

	@JsonIgnore
	private UserDTOSmall statusmodifier;
	
	@JsonIgnore
	private IDDescrDTO shootingType; 
	
	//FORM STUFF
	private Map<String, Boolean> weatherMap;
	private List<String> weatherList;
	@JsonIgnore
	private Boolean saveandapprove = false;



	
	private String staticMapURI; 
	
	
	private String selectedvideotypelist = "";

	
	
	public Integer getLoadstatus() {
		return loadstatus;
	}

	public void setLoadstatus(Integer loadstatus) {
		this.loadstatus = loadstatus;
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

	public String getDescShort() {
		return description.length()>301 ? description.substring(0, 300)+"...": description;
	}	
	
	
	public String getDateCreatedAsString() {
		return datecreated!=null ? SmartDateTimeUtil.getDateFormatString(datecreated): "";
	}	
	
	public String getDateApprovedAsString() {
		return dateapproved!=null ? SmartDateTimeUtil.getDateFormatString(dateapproved) : "";
	}	
		
	public String getDateModifiedAsString() {
		return datemodified!=null ? SmartDateTimeUtil.getDateFormatString(datemodified) : "";
	}	
	
	
	/****************** WEB PURPOSE SHORT STRINGS ***************************************/
	public String getShortAndSmart_DATECREATED() {
		return SmartDateTimeUtil.getDateString_shortAndSmart(datecreated);
	}
	
/*	
	
	public String getVideoURI() {
		if(link)
			return getShortAndSmart_LinkURI();
		else
			return getShortAndSmart_VideoURI();
		
	}*/

	public String getS3VideoURL() {
			//return AWSConfiguration.S3STORAGEURI +"/"+AWSConfiguration.USERSVIDEOBUCKET + "/"+this.user.getUUID()+"/"+this.UUID+"."+this.extension;
				return AWSConfiguration.S3STORAGEURI +"/"+AWSConfiguration.USERSVIDEOBUCKET + "/"+this.id+"."+this.extension;
	}

/*	
	
	
	public String getShortAndSmart_LinkURI() {
		
		if(linkedurl==null)
			return "";
		
		
		if(linkedurl.contains("youtu.be")) {
			String youtubelink = "https://www.youtube.com/embed/"+linkedurl.substring(linkedurl.lastIndexOf("/")+1,linkedurl.length());
			return youtubelink;
		}
		
		else if(linkedurl.contains("youtube.com/watch")) {
			String youtubelink = "https://www.youtube.com/embed/"+linkedurl.substring(linkedurl.lastIndexOf("=")+1,linkedurl.length());
			return youtubelink;
		}

		else if(linkedurl.contains("vimeo")) {  
			String vimeolink = "https://player.vimeo.com/video/"+linkedurl.substring(linkedurl.lastIndexOf("/")+1,linkedurl.length());
			return vimeolink;
					
		} else
			return "";
}*/
	
	
	/****************** WEB PURPOSE SHORT STRINGS ***************************************/
	

	
	public String getUUID() {
		return UUID;
	}





	public void setUUID(String uUID) {
		UUID = uUID;
	}





/*	public CategoryDTO getCategory() {
		return category;
	}





	public void setCategory(CategoryDTO category) {
		this.category = category;
	}
*/




	public Map<String, Boolean> getWeatherMap() {
		return weatherMap;
	}





	public void setWeatherMap(Map<String, Boolean> weatherMap) {
		this.weatherMap = weatherMap;
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

	
	
	public VideoDTO() {} 
	


	
	

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

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Date getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}
	public Date getDatemodified() {
		return datemodified;
	}
	public void setDatemodified(Date datemodified) {
		this.datemodified = datemodified;
	}





	public BigDecimal getLatitude() {
		return latitude;
	}





	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}


	public String getFilename() {
		return filename;
	}





	public void setFilename(String filename) {
		this.filename = filename;
	}





	public Long getVideosize() {
		return videosize;
	}





	public void setVideosize(Long videosize) {
		this.videosize = videosize;
	}





	public String getContenttype() {
		return contenttype;
	}





	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}





	public Integer getPrivacy() {
		return privacy;
	}





	public void setPrivacy(Integer privacy) {
		this.privacy = privacy;
	}





	public MultipartFile getFiledata() {
		return filedata;
	}





	public void setFiledata(MultipartFile filedata) {
		this.filedata = filedata;
	}





	public String getWeather() {
		return weather;
	}





/*	public void setWeather(String weather) {
		this.weather = weather;
		
		
		Iterable<String> weatheriter = Splitter.on( "," ).omitEmptyStrings().trimResults().split( weather );
		
		//SET WEATHER MAP
		Map weatherMap = WebUtility.weatherMap();
		for (String item : weatheriter) { weatherMap.put(item, true); }
		setWeatherMap(weatherMap);
		
		
		//SET WEATHER LIST
		setWeatherList(Lists.newArrayList( weatheriter ));
		
		
		
		
		
	}*/





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





	public UserDTOSmall getUser() {
		return user;
	}





	public void setUser(UserDTOSmall user) {
		this.user = user;
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


	public List<String> getWeatherList() {
		return weatherList;
	}


	public void setWeatherList(List<String> weatherList) {
		this.weatherList = weatherList;
	}


/*	public void initWeatherString() {
		String weatherString = ""; 
        for (Map.Entry<String, Boolean> entry : this.getWeatherMap().entrySet()) {
   		if(entry!=null) {
   			if(entry.getValue()!=null && entry.getValue() && (!entry.getKey().equalsIgnoreCase("any")))
   				weatherString+=  entry.getKey()+",";
   		}
        }
        
	  if(weatherString.endsWith(",")) 
	   			weatherString = weatherString.substring(0, weatherString.length()-1);
	    
	  this.setWeather(weatherString);
	  
		
	}*/

/*
	public String getSelectedCategory() {
		return selectedCategory;
	}


	public void setSelectedCategory(String selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
*/


	public List<CollectionDTO> getCollections() {
		return collections;
	}



	public void setCollections(List<CollectionDTO> collections) {
		this.collections = collections;
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
		if(!link)
			return getS3VideoURL();
		else
			return thumburl;
	}



	public void setThumburl(String thumburl) {
		this.thumburl = thumburl;
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
		if(!link)
			return getS3VideoURL();
		else
			return videourl;
	}
	
	public String getYoutubeNoCookieVideoUrl() {
			return "https://www.youtube-nocookie.com/embed/"+youtubeid;
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



	public String getCategoryid() {
		return categoryid;
	}



	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
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


	
	@JsonIgnore
	public UserDTOSmall getUploader() {
		return uploader;
	}



	public void setUploader(UserDTOSmall uploader) {
		this.uploader = uploader;
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



	public Boolean getSaveandapprove() {
		return saveandapprove;
	}



	public void setSaveandapprove(Boolean saveandapprove) {
		this.saveandapprove = saveandapprove;
	}






	public IDDescrDTO getShootingType() {
		return shootingType;
	}



	public void setShootingType(IDDescrDTO shootingType) {
		this.shootingType = shootingType;
	}




	public String getShootingtypeid() {
		return shootingtypeid;
	}



	public void setShootingtypeid(String shootingtypeid) {
		this.shootingtypeid = shootingtypeid;
	}



	public String getSelectedvideotypelist() {
		return selectedvideotypelist;
	}



	public void setSelectedvideotypelist(String selectedvideotypelist) {
		this.selectedvideotypelist = selectedvideotypelist;
	}



	public List<IDDescrDTO> getVideoTypeList() {
		return videoTypeList;
	}



	public void setVideoTypeList(List<IDDescrDTO> videoTypeList) {
		this.videoTypeList = videoTypeList;
	}

	public String getStaticMapURI() {
		this.staticMapURI = "https://maps.googleapis.com/maps/api/staticmap?markers=icon:http://www.terrabis.com/images/markers-list/marker-1.png|"+this.getLatitude().toString()+","+this.getLongitude().toString()+"&center="+this.getLatitude().toString()+","+this.getLongitude().toString();
		return staticMapURI;
	}


	public void setStaticMapURI(String staticMapURI) {
		this.staticMapURI = staticMapURI;
	}



	public String getSharethumburl() {
		this.sharethumburl = "http://terrabisvideothumb.s3.amazonaws.com/"+this.UUID+".png";
		return sharethumburl;
	}

	
	
	

	public void setSharethumburl(String sharethumburl) {
		this.sharethumburl = sharethumburl;
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



	public String getDistance() {
		return distance;
	}



	public void setDistance(String distance) {
		this.distance = distance;
	}



	public Date getDateapproved() {
		return dateapproved;
	}



	public void setDateapproved(Date dateapproved) {
		this.dateapproved = dateapproved;
	}



	public UserDTOSmall getApprover() {
		return approver;
	}



	public void setApprover(UserDTOSmall approver) {
		this.approver = approver;
	}



	public Long getApproverid() {
		return approverid;
	}



	public void setApproverid(Long approverid) {
		this.approverid = approverid;
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



	public UserDTOSmall getModifier() {
		return modifier;
	}



	public void setModifier(UserDTOSmall modifier) {
		this.modifier = modifier;
	}



	public UserDTOSmall getStatusmodifier() {
		return statusmodifier;
	}



	public void setStatusmodifier(UserDTOSmall statusmodifier) {
		this.statusmodifier = statusmodifier;
	}



	public Date getDatestatusmodified() {
		return datestatusmodified;
	}



	public void setDatestatusmodified(Date datestatusmodified) {
		this.datestatusmodified = datestatusmodified;
	}

	public String getYtvmuserlink() {
		return ytvmuserlink;
	}

	public void setYtvmuserlink(String ytvmuserlink) {
		this.ytvmuserlink = ytvmuserlink;
	}

	public String getDynamicuserid() {
		return dynamicuserid;
	}

	public void setDynamicuserid(String dynamicuserid) {
		this.dynamicuserid = dynamicuserid;
	}

	public String getDynamicvalue() {
		return dynamicvalue;
	}

	public void setDynamicvalue(String dynamicvalue) {
		this.dynamicvalue = dynamicvalue;
	}

	public UserDTOSmall getDynamicuser() {
		return dynamicuser;
	}

	public void setDynamicuser(UserDTOSmall dynamicuser) {
		this.dynamicuser = dynamicuser;
	}

	public String getDynamiccount() {
		return dynamiccount;
	}

	public void setDynamiccount(String dynamiccount) {
		this.dynamiccount = dynamiccount;
	}

	public String getRejectReasonText() {
		return rejectReasonText;
	}

	public void setRejectReasonText(String rejectReasonText) {
		this.rejectReasonText = rejectReasonText;
	}

	public Boolean getTstFlag() {
		return tstFlag;
	}

	public void setTstFlag(Boolean tstFlag) {
		this.tstFlag = tstFlag;
	}



}
