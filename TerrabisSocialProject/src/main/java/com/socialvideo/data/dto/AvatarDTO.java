package com.socialvideo.data.dto;

import java.io.Serializable;
import java.util.Date;

public class AvatarDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	

private Long id;
private Long userid;
private byte[] datablob;
private String imagebase64;
private String extension;
private String contenttype;
private Date datecreated;



public AvatarDTO(Long userid) {
	this.userid = userid;
}


public AvatarDTO() {
}


public String getExtension() {
	return extension;
}







public void setExtension(String extension) {
	this.extension = extension;
}




private Long  size;




public Long getSize() {
	return size;
}




public void setSize(Long size) {
	this.size = size;
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




public byte[] getDatablob() {
	return datablob;
}




public void setDatablob(byte[] datablob) {
	this.datablob = datablob;
}




public String getImagebase64() {
	return imagebase64;
}




public void setImagebase64(String imagebase64) {
	this.imagebase64 = imagebase64;
}




public void setContenttype(String contentType) {
	this.contenttype = contentType;
	
}




public String getContenttype() {
	return contenttype;
}


public Date getDatecreated() {
	return datecreated;
}


public void setDatecreated(Date datecreated) {
	this.datecreated = datecreated;
}


}