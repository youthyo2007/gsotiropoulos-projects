package com.socialvideo.data.dto.maps;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PointDTO implements Serializable {

	
	

	private static final long serialVersionUID = 1L;
	
	
	
	
	
	
	
	@JsonProperty("lat")
    public String lat;
    public BigDecimal latnumber;
    
    
	

	@JsonProperty("lng")
    public String lng;
	public BigDecimal lngnumber;
    
	
	
	
	
	
	

	public PointDTO() {} 
	
	
	
	public PointDTO(String latlng) {
		
		if(latlng!=null) {
			String [] items = latlng.split(" ");
			//latnumber = new BigDecimal(items[0]);
			//lngnumber = new BigDecimal(items[1]);
			this.lat = items[0];
			this.lng = items[1];
		}
	}
	

	public PointDTO(String lat, String lng) {
		latnumber = new BigDecimal(lat);
		lngnumber = new BigDecimal(lng);
		this.lat = lat;
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		latnumber = new BigDecimal(lat);
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		lngnumber = new BigDecimal(lng);
		this.lng = lng;
	}



	public BigDecimal getLatnumber() {
		return latnumber;
	}



	public void setLatnumber(BigDecimal latnumber) {
		this.latnumber = latnumber;
	}



	public BigDecimal getLngnumber() {
		return lngnumber;
	}



	public void setLngnumber(BigDecimal lngnumber) {
		this.lngnumber = lngnumber;
	} 
	
	

}
