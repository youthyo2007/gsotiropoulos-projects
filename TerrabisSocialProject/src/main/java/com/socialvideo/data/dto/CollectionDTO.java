package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Long id;

	private Long userid;

	private String title;

	private String description;
	
	private Integer privacy;
	
	private Integer type;
	
	private Integer playscount;
	
	

	public Integer getPlayscount() {
		return playscount;
	}

	public void setPlayscount(Integer playscount) {
		this.playscount = playscount;
	}

	private UserDTOSmall user;
	
	private List<PublicVideoDTO> videos = new ArrayList<PublicVideoDTO>();
	

	private Integer videoscount;
	
	
	private String videoids;

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

	public Integer getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Integer privacy) {
		this.privacy = privacy;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	public Integer getVideoscount() {
		return videoscount;
	}

	public void setVideoscount(Integer videoscount) {
		this.videoscount = videoscount;
	}

	private Long pathstartvideoid;
	private Long pathendvideoid;
	
	

	public String getVideoids() {
		videoids = "";
		
		for (Iterator<PublicVideoDTO> i=videos.iterator(); i.hasNext(); ) {
			videoids+=i.next().getId()+",";
		}
		
		videoids = videoids.substring(0, videoids.length()-1);
				
		
		return videoids;
		
		
		
		
		
	}

	public void setVideoids(String videoids) {
		this.videoids = videoids;
	}

	public Long getPathstartvideoid() {
		return pathstartvideoid;
	}

	public void setPathstartvideoid(Long pathstartvideoid) {
		this.pathstartvideoid = pathstartvideoid;
	}

	public Long getPathendvideoid() {
		return pathendvideoid;
	}

	public void setPathendvideoid(Long pathendvideoid) {
		this.pathendvideoid = pathendvideoid;
	}

	public UserDTOSmall getUser() {
		return user;
	}

	public void setUser(UserDTOSmall user) {
		this.user = user;
	}

	public List<PublicVideoDTO> getVideos() {
		return videos;
	}

	public void setVideos(List<PublicVideoDTO> videos) {
		this.videos = videos;
	}

	
	
}


