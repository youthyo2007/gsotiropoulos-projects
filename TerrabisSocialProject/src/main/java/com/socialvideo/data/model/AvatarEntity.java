package com.socialvideo.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.socialvideo.data.dto.AvatarDTO;
import com.socialvideo.data.dto.UserDTO;




@Entity
@Table(name = "avatars")
public class AvatarEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
@Id
@GeneratedValue
private Long id;

@Column(nullable = false, name="userid_fk")
private Long userid;

@Column
private Boolean enabled;

@Column
private byte[] datablob;

@Column(name="imagebase64", columnDefinition="TEXT")
private String imagebase64;

@Column
private String extension;


@Column
private Long  size;


@Column
private String contenttype;



public AvatarEntity() {};

public AvatarEntity(Long userid, byte[] datablob) {
	this.userid = userid;
	this.datablob = datablob;
	
};

public AvatarEntity(AvatarDTO DTO) {
	this.LOAD(DTO);
};


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
public byte[] getDatablob() {
	return datablob;
}
public void setDatablob(byte[] datablob) {
	this.datablob = datablob;
}


public AvatarDTO DTO() {

	AvatarDTO DTO = new AvatarDTO();
	
	DTO.setId(this.id);
	DTO.setUserid(this.userid);
	DTO.setDatablob(datablob);
	DTO.setExtension(this.extension);
	DTO.setImagebase64(this.imagebase64);
	DTO.setSize(this.size);
	DTO.setContenttype(this.contenttype);
	return DTO;
}

public void LOAD(AvatarDTO DTO) {
	this.id = DTO.getId();
	this.userid = DTO.getUserid();
	this.datablob = DTO.getDatablob();
	this.size = DTO.getSize();
	this.extension = DTO.getExtension();
	this.imagebase64 = DTO.getImagebase64();
	this.contenttype = DTO.getContenttype();
	
}

public String getImagebase64() {
	return imagebase64;
}

public void setImagebase64(String imagebase64) {
	this.imagebase64 = imagebase64;
}

public String getContenttype() {
	return contenttype;
}

public void setContenttype(String contenttype) {
	this.contenttype = contenttype;
}

public Boolean getEnabled() {
	return enabled;
}

public void setEnabled(Boolean enabled) {
	this.enabled = enabled;
}





}