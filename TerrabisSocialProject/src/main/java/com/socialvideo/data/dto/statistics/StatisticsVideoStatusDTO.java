package com.socialvideo.data.dto.statistics;

public class StatisticsVideoStatusDTO {
	
	public  Integer total = 0;
	public  Integer status_YOUTUBETOOLUPLOADED = 0;
	public  Integer status_EXCELUPLOADED = 0;
	public  Integer status_JUSTUPLOADED = 0;
	public  Integer status_PUBLISHED = 0;
	public  Integer status_APPROVED = 0;
	public  Integer status_STAGING = 0;
	public  Integer status_UNPUBLISHED = 0;
	public  Integer status_REJECTED = 0;
	public  Integer promote_status_SITEINDEX = 0;
	public  Integer promote_status_MAP = 0;
	public Integer mark_UNWANTED = 0;
	
	
	
	
	public static Integer health_NONE = -1;
	public static Integer health_HEALTHY = 1;
	public Integer getStatus_EXCELUPLOADED() {
		return status_EXCELUPLOADED;
	}


	public void setStatus_EXCELUPLOADED(Integer status_EXCELUPLOADED) {
		this.status_EXCELUPLOADED = status_EXCELUPLOADED;
	}


	public static Integer health_BROKEN = 0;
	
	public  Integer inter_NONE = -1;
	public  Integer inter_S3 = 1;
	public  Integer inter_S3_YOUTUBE = 2;
	public  Integer inter_S3_YOUTUBE_GLACIER = 4;
	public  Integer inter_YOUTUBE_GLACIER = 5;

	
	
	
	public StatisticsVideoStatusDTO() {}
	
	
	public Integer getStatus_JUSTUPLOADED() {
		return status_JUSTUPLOADED;
	}
	public void setStatus_JUSTUPLOADED(Integer status_JUSTUPLOADED) {
		this.status_JUSTUPLOADED = status_JUSTUPLOADED;
	}
	public Integer getStatus_PUBLISHED() {
		return status_PUBLISHED;
	}
	public void setStatus_PUBLISHED(Integer status_PUBLISHED) {
		this.status_PUBLISHED = status_PUBLISHED;
	}
	public Integer getStatus_APPROVED() {
		return status_APPROVED;
	}
	public void setStatus_APPROVED(Integer status_APPROVED) {
		this.status_APPROVED = status_APPROVED;
	}
	public Integer getStatus_STAGING() {
		return status_STAGING;
	}
	public void setStatus_STAGING(Integer status_STAGING) {
		this.status_STAGING = status_STAGING;
	}
	public Integer getStatus_UNPUBLISHED() {
		return status_UNPUBLISHED;
	}
	public void setStatus_UNPUBLISHED(Integer status_UNPUBLISHED) {
		this.status_UNPUBLISHED = status_UNPUBLISHED;
	}
	public Integer getStatus_REJECTED() {
		return status_REJECTED;
	}
	public void setStatus_REJECTED(Integer status_REJECTED) {
		this.status_REJECTED = status_REJECTED;
	}
	public static Integer getHealth_NONE() {
		return health_NONE;
	}
	public static void setHealth_NONE(Integer health_NONE) {
		StatisticsVideoStatusDTO.health_NONE = health_NONE;
	}
	public static Integer getHealth_HEALTHY() {
		return health_HEALTHY;
	}
	public static void setHealth_HEALTHY(Integer health_HEALTHY) {
		StatisticsVideoStatusDTO.health_HEALTHY = health_HEALTHY;
	}
	public static Integer getHealth_BROKEN() {
		return health_BROKEN;
	}
	public static void setHealth_BROKEN(Integer health_BROKEN) {
		StatisticsVideoStatusDTO.health_BROKEN = health_BROKEN;
	}
	public Integer getInter_NONE() {
		return inter_NONE;
	}
	public void setInter_NONE(Integer inter_NONE) {
		this.inter_NONE = inter_NONE;
	}
	public Integer getInter_S3() {
		return inter_S3;
	}
	public void setInter_S3(Integer inter_S3) {
		this.inter_S3 = inter_S3;
	}
	public Integer getInter_S3_YOUTUBE() {
		return inter_S3_YOUTUBE;
	}
	public void setInter_S3_YOUTUBE(Integer inter_S3_YOUTUBE) {
		this.inter_S3_YOUTUBE = inter_S3_YOUTUBE;
	}
	public Integer getInter_S3_YOUTUBE_GLACIER() {
		return inter_S3_YOUTUBE_GLACIER;
	}
	public void setInter_S3_YOUTUBE_GLACIER(Integer inter_S3_YOUTUBE_GLACIER) {
		this.inter_S3_YOUTUBE_GLACIER = inter_S3_YOUTUBE_GLACIER;
	}
	public Integer getInter_YOUTUBE_GLACIER() {
		return inter_YOUTUBE_GLACIER;
	}
	public void setInter_YOUTUBE_GLACIER(Integer inter_YOUTUBE_GLACIER) {
		this.inter_YOUTUBE_GLACIER = inter_YOUTUBE_GLACIER;
	}


	public Integer getTotal() {
		return total;
	}


	public void setTotal(Integer total) {
		this.total = total;
	}


	public Integer getPromote_status_SITEINDEX() {
		return promote_status_SITEINDEX;
	}


	public void setPromote_status_SITEINDEX(Integer promote_status_SITEINDEX) {
		this.promote_status_SITEINDEX = promote_status_SITEINDEX;
	}


	public Integer getPromote_status_MAP() {
		return promote_status_MAP;
	}


	public void setPromote_status_MAP(Integer promote_status_MAP) {
		this.promote_status_MAP = promote_status_MAP;
	}


	public Integer getStatus_YOUTUBETOOLUPLOADED() {
		return status_YOUTUBETOOLUPLOADED;
	}


	public void setStatus_YOUTUBETOOLUPLOADED(Integer status_YOUTUBETOOLUPLOADED) {
		this.status_YOUTUBETOOLUPLOADED = status_YOUTUBETOOLUPLOADED;
	}


	public Integer getMark_UNWANTED() {
		return mark_UNWANTED;
	}


	public void setMark_UNWANTED(Integer mark_UNWANTED) {
		this.mark_UNWANTED = mark_UNWANTED;
	}
	

	
	
	
	
	
}
