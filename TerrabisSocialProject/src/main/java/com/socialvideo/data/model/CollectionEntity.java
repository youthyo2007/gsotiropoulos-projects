package com.socialvideo.data.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.socialvideo.data.dto.CollectionDTO;
import com.socialvideo.data.dto.VideoDTO;


@Entity
@Table(name = "collections")
public class CollectionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	
	
	
	
	public CollectionEntity() {}
	
	public CollectionEntity(Long userid, String title, String description, Integer type, Integer privacy) {
		this.userid = userid;
		this.title = title;
		this.description = description;
		this.type = type;
		this.privacy = privacy;
	}
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name="userid_fk")
	private Long userid;

	@Column(nullable = false)
	private String title;

	@Column
	private String description;
	
	@Column(nullable = false)
	private Integer privacy;

	@Column(nullable = false)
	private Integer type;
	
	@Column
	private Integer videoscount;

	@Column
	private Integer playscount = 0;
	
	public Integer getPlayscount() {
		return playscount;
	}

	public void setPlayscount(Integer playscount) {
		this.playscount = playscount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public void play() {
		if(playscount==null)
			playscount = 0;
		
		this.playscount = playscount+1;
	}

	public CollectionDTO DTO() {

		CollectionDTO DTO = new CollectionDTO();
		
		DTO.setId(this.id);
		DTO.setUserid(this.userid);
		DTO.setTitle(this.title);
		DTO.setDescription(this.description);
		DTO.setType(this.type);
		DTO.setPrivacy(this.privacy);
		DTO.setVideoscount(this.videoscount);
		DTO.setPlayscount(this.playscount);
		

		return DTO;
	}

	
	public void LOAD(CollectionDTO DTO) {
		this.id = DTO.getId();
		this.userid = DTO.getUserid();
		this.title = DTO.getTitle();
		this.description = DTO.getDescription();
		this.type = DTO.getType();
		this.privacy = DTO.getPrivacy();
		this.videoscount = DTO.getVideoscount();
		this.playscount = DTO.getPlayscount();
	}


	public void LOADCALC(CollectionDTO DTO) {
		this.videoscount = DTO.getVideoscount();
	}

	
	public Integer getVideoscount() {
		return videoscount;
	}

	public void setVideoscount(Integer videoscount) {
		this.videoscount = videoscount;
	}
	
	
	
}


