package com.socialvideo.data.dto;

public class EventDTO {
	
	
	
	
	
	private Long userID;
	private Long videoID;
	private Long collectionID;
	
	public Integer eventType;
	

	
	public static final Integer VID_JUSTUPLOADED = 10;
	public static final Integer VID_PUBLISHED = 11;
	public static final Integer VID_APPROVED = 12;
	public static final Integer VID_STAGING = 13;
	public static final Integer VID_UNPUBLISHED = 14;
	public static final Integer VID_REJECTED = 15;
	public static final Integer VID_LIKE = 16;
	public static final Integer VID_SHARE = 17;
	public static final Integer VID_PLAY = 18;
	public static final Integer VID_DELETED = 19;	
	public static final Integer VID_FORCECLAIMED = 20;	
	
		
	public static final Integer COL_CREATE = 30;
	public static final Integer COL_DELETE = 31;
	public static final Integer COL_VIDADDED = 32;
	public static final Integer COL_VIDREMOVED = 33;
	public static final Integer COL_CLEARVIDS = 34;
	public static final Integer COL_VIDREMOVEFROMALL = 35;
	
	
	
	
	
	
	public EventDTO() {
		
	}
	

	public EventDTO(Long userID, Long videoID, Long collectionID, Integer eventType) {
		this.userID = userID;
		this.videoID = videoID;
		this.collectionID = collectionID;
		this.eventType = eventType;
		
	}

	
	public EventDTO(VideoDTO videoDTO, Integer eventType) {
		this.userID = videoDTO.getUserid();
		this.videoID = videoDTO.getId();
		this.eventType = eventType;
		
	}

	public EventDTO(CollectionDTO collectionDTO, Integer eventType) {
		this.userID = collectionDTO.getUserid();
		this.collectionID = collectionDTO.getId();
		this.eventType = eventType;
		
	}

	public EventDTO(VideoDTO videoDTO, CollectionDTO collectionDTO, Integer eventType) {
		this.userID = videoDTO.getUserid();
		this.videoID = videoDTO.getId();
		this.collectionID = collectionDTO.getId();
		this.eventType = eventType;
		
	}

	
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public Long getVideoID() {
		return videoID;
	}
	public void setVideoID(Long videoID) {
		this.videoID = videoID;
	}
	public Long getCollectionID() {
		return collectionID;
	}
	public void setCollectionID(Long collectionID) {
		this.collectionID = collectionID;
	}
	
}
