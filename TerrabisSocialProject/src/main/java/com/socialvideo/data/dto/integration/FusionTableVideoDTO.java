package com.socialvideo.data.dto.integration;

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
public class FusionTableVideoDTO implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	
	
	    private String id;
	    private String UUID;
	    private String userid_fk;
	    private String categoryid_fk;
	    private String soundtrack_fk;
	    private String linkedurl;
	    private String link;
	    private String langid_fk;
	    private String title;
	    private String description;
	    private String privacy;
	    private String latitude;
	    private String longitude;
	    private String tags;
	    private String weather;
	    private String thumbnail;
	    private String status;
	    private String vimeostatus;
	    private String vimeoid;
	    private String filename;
	    private String originalfilename;
	    private String extension;
	    private String contenttype;
	    private String videosize;
	    private String reviewscount;
	    private String ratingscount;
	    private String playscount;
	    private String likescount;
	    private String tweetscount;
	    private String duration;
	    private String footagedate;
	    private String datecreated;
	    private String datemodifed;
	    private String ratingssum;
	    private String collectionscount;
	    private String youtube;
	    private String vimeo;
	    private String thumburl;
	    private String youtubeid;
	    private String videourl;
	    private String durationtxt;
	    private String healthstatus;
	    private String integrationstatus;
	    private String isfile;
	    private String rejectreason_fk;
	    private String transferstatus;
	    private String promotestatus;
	    private String promotesiteindex;
	    private String promotemap;
	    private String youtubechannelid;
	    private String uploaderid_fk;
	    private String ownershipverified;
	    private String ownershipverificationdate;
	    private String commentsent;
	    private String shootingtypeid;
	    private String terrabiscomment;
	    private String videotypeids;
	    private String markerimgid;
	    private String dateapproved;
	    private String approverid_fk;
	    private String ytvmuserid;
	    private String ytvmusername;
	    private String modifierid_fk;
	    private String modifierstatusid_fk;
	    private String datestatusmodified;
	    private String ytvmuserlink;
	
	    
	    
	    
	    public FusionTableVideoDTO() {}




		public String getId() {
			return id;
		}




		public void setId(String id) {
			this.id = id;
		}




		public String getUUID() {
			return UUID;
		}




		public void setUUID(String uUID) {
			UUID = uUID;
		}




		public String getUserid_fk() {
			return userid_fk;
		}




		public void setUserid_fk(String userid_fk) {
			this.userid_fk = userid_fk;
		}




		public String getCategoryid_fk() {
			return categoryid_fk;
		}




		public void setCategoryid_fk(String categoryid_fk) {
			this.categoryid_fk = categoryid_fk;
		}




		public String getSoundtrack_fk() {
			return soundtrack_fk;
		}




		public void setSoundtrack_fk(String soundtrack_fk) {
			this.soundtrack_fk = soundtrack_fk;
		}




		public String getLinkedurl() {
			return linkedurl;
		}




		public void setLinkedurl(String linkedurl) {
			this.linkedurl = linkedurl;
		}




		public String getLink() {
			return link;
		}




		public void setLink(String link) {
			this.link = link;
		}




		public String getLangid_fk() {
			return langid_fk;
		}




		public void setLangid_fk(String langid_fk) {
			this.langid_fk = langid_fk;
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




		public String getPrivacy() {
			return privacy;
		}




		public void setPrivacy(String privacy) {
			this.privacy = privacy;
		}




		public String getLatitude() {
			return latitude;
		}




		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}




		public String getLongitude() {
			return longitude;
		}




		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}




		public String getTags() {
			return tags;
		}




		public void setTags(String tags) {
			this.tags = tags;
		}




		public String getWeather() {
			return weather;
		}




		public void setWeather(String weather) {
			this.weather = weather;
		}




		public String getThumbnail() {
			return thumbnail;
		}




		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}




		public String getStatus() {
			return status;
		}




		public void setStatus(String status) {
			this.status = status;
		}




		public String getVimeostatus() {
			return vimeostatus;
		}




		public void setVimeostatus(String vimeostatus) {
			this.vimeostatus = vimeostatus;
		}




		public String getVimeoid() {
			return vimeoid;
		}




		public void setVimeoid(String vimeoid) {
			this.vimeoid = vimeoid;
		}




		public String getFilename() {
			return filename;
		}




		public void setFilename(String filename) {
			this.filename = filename;
		}




		public String getOriginalfilename() {
			return originalfilename;
		}




		public void setOriginalfilename(String originalfilename) {
			this.originalfilename = originalfilename;
		}




		public String getExtension() {
			return extension;
		}




		public void setExtension(String extension) {
			this.extension = extension;
		}




		public String getContenttype() {
			return contenttype;
		}




		public void setContenttype(String contenttype) {
			this.contenttype = contenttype;
		}




		public String getVideosize() {
			return videosize;
		}




		public void setVideosize(String videosize) {
			this.videosize = videosize;
		}




		public String getReviewscount() {
			return reviewscount;
		}




		public void setReviewscount(String reviewscount) {
			this.reviewscount = reviewscount;
		}




		public String getRatingscount() {
			return ratingscount;
		}




		public void setRatingscount(String ratingscount) {
			this.ratingscount = ratingscount;
		}




		public String getPlayscount() {
			return playscount;
		}




		public void setPlayscount(String playscount) {
			this.playscount = playscount;
		}




		public String getLikescount() {
			return likescount;
		}




		public void setLikescount(String likescount) {
			this.likescount = likescount;
		}




		public String getTweetscount() {
			return tweetscount;
		}




		public void setTweetscount(String tweetscount) {
			this.tweetscount = tweetscount;
		}




		public String getDuration() {
			return duration;
		}




		public void setDuration(String duration) {
			this.duration = duration;
		}




		public String getFootagedate() {
			return footagedate;
		}




		public void setFootagedate(String footagedate) {
			this.footagedate = footagedate;
		}




		public String getDatecreated() {
			return datecreated;
		}




		public void setDatecreated(String datecreated) {
			this.datecreated = datecreated;
		}




		public String getDatemodifed() {
			return datemodifed;
		}




		public void setDatemodifed(String datemodifed) {
			this.datemodifed = datemodifed;
		}




		public String getRatingssum() {
			return ratingssum;
		}




		public void setRatingssum(String ratingssum) {
			this.ratingssum = ratingssum;
		}




		public String getCollectionscount() {
			return collectionscount;
		}




		public void setCollectionscount(String collectionscount) {
			this.collectionscount = collectionscount;
		}




		public String getYoutube() {
			return youtube;
		}




		public void setYoutube(String youtube) {
			this.youtube = youtube;
		}




		public String getVimeo() {
			return vimeo;
		}




		public void setVimeo(String vimeo) {
			this.vimeo = vimeo;
		}




		public String getThumburl() {
			return thumburl;
		}




		public void setThumburl(String thumburl) {
			this.thumburl = thumburl;
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




		public String getHealthstatus() {
			return healthstatus;
		}




		public void setHealthstatus(String healthstatus) {
			this.healthstatus = healthstatus;
		}




		public String getIntegrationstatus() {
			return integrationstatus;
		}




		public void setIntegrationstatus(String integrationstatus) {
			this.integrationstatus = integrationstatus;
		}




		public String getIsfile() {
			return isfile;
		}




		public void setIsfile(String isfile) {
			this.isfile = isfile;
		}




		public String getRejectreason_fk() {
			return rejectreason_fk;
		}




		public void setRejectreason_fk(String rejectreason_fk) {
			this.rejectreason_fk = rejectreason_fk;
		}




		public String getTransferstatus() {
			return transferstatus;
		}




		public void setTransferstatus(String transferstatus) {
			this.transferstatus = transferstatus;
		}




		public String getPromotestatus() {
			return promotestatus;
		}




		public void setPromotestatus(String promotestatus) {
			this.promotestatus = promotestatus;
		}




		public String getPromotesiteindex() {
			return promotesiteindex;
		}




		public void setPromotesiteindex(String promotesiteindex) {
			this.promotesiteindex = promotesiteindex;
		}




		public String getPromotemap() {
			return promotemap;
		}




		public void setPromotemap(String promotemap) {
			this.promotemap = promotemap;
		}




		public String getYoutubechannelid() {
			return youtubechannelid;
		}




		public void setYoutubechannelid(String youtubechannelid) {
			this.youtubechannelid = youtubechannelid;
		}




		public String getUploaderid_fk() {
			return uploaderid_fk;
		}




		public void setUploaderid_fk(String uploaderid_fk) {
			this.uploaderid_fk = uploaderid_fk;
		}




		public String getOwnershipverified() {
			return ownershipverified;
		}




		public void setOwnershipverified(String ownershipverified) {
			this.ownershipverified = ownershipverified;
		}




		public String getOwnershipverificationdate() {
			return ownershipverificationdate;
		}




		public void setOwnershipverificationdate(String ownershipverificationdate) {
			this.ownershipverificationdate = ownershipverificationdate;
		}




		public String getCommentsent() {
			return commentsent;
		}




		public void setCommentsent(String commentsent) {
			this.commentsent = commentsent;
		}




		public String getShootingtypeid() {
			return shootingtypeid;
		}




		public void setShootingtypeid(String shootingtypeid) {
			this.shootingtypeid = shootingtypeid;
		}




		public String getTerrabiscomment() {
			return terrabiscomment;
		}




		public void setTerrabiscomment(String terrabiscomment) {
			this.terrabiscomment = terrabiscomment;
		}




		public String getVideotypeids() {
			return videotypeids;
		}




		public void setVideotypeids(String videotypeids) {
			this.videotypeids = videotypeids;
		}




		public String getMarkerimgid() {
			return markerimgid;
		}




		public void setMarkerimgid(String markerimgid) {
			this.markerimgid = markerimgid;
		}




		public String getDateapproved() {
			return dateapproved;
		}




		public void setDateapproved(String dateapproved) {
			this.dateapproved = dateapproved;
		}




		public String getApproverid_fk() {
			return approverid_fk;
		}




		public void setApproverid_fk(String approverid_fk) {
			this.approverid_fk = approverid_fk;
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




		public String getModifierid_fk() {
			return modifierid_fk;
		}




		public void setModifierid_fk(String modifierid_fk) {
			this.modifierid_fk = modifierid_fk;
		}




		public String getModifierstatusid_fk() {
			return modifierstatusid_fk;
		}




		public void setModifierstatusid_fk(String modifierstatusid_fk) {
			this.modifierstatusid_fk = modifierstatusid_fk;
		}




		public String getDatestatusmodified() {
			return datestatusmodified;
		}




		public void setDatestatusmodified(String datestatusmodified) {
			this.datestatusmodified = datestatusmodified;
		}




		public String getYtvmuserlink() {
			return ytvmuserlink;
		}




		public void setYtvmuserlink(String ytvmuserlink) {
			this.ytvmuserlink = ytvmuserlink;
		}




		public static long getSerialversionuid() {
			return serialVersionUID;
		}

}
