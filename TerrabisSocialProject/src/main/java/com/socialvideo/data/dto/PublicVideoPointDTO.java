package com.socialvideo.data.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.client.util.Key;
import com.socialvideo.data.dto.maps.PointDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicVideoPointDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	

	private String title;
	
	private String description;
	
	private String latitude;
	

	private String longitude;
	

	private String markerimgid;
	

	private String ID;

	private String UUID;
	
	@Key
	private String sharethumburl;
	
	

	public PublicVideoPointDTO() {}





	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}








	public String getMarkerimgid() {
		return markerimgid;
	}





	public void setMarkerimgid(String markerimgid) {
		this.markerimgid = markerimgid;
	}





	public String getUUID() {
		return UUID;
	}





	public void setUUID(String uUID) {
		UUID = uUID;
	}





	
	
	public String getSharethumburl() {
		this.sharethumburl = "http://terrabisvideothumb.s3.amazonaws.com/"+this.UUID+".png";
		return sharethumburl;
	}




	public void setSharethumburl(String sharethumburl) {
		this.sharethumburl = sharethumburl;
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





	public String getID() {
		return ID;
	}





	public void setID(String iD) {
		ID = iD;
	}





	public String getDescription() {
		return description;
	}





	public void setDescription(String description) {
		this.description = description;
	} 
	
	

}
