package com.socialvideo.data.dto.maps;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodingDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("emptyStatus")
	private boolean emptyStatus = true;

	@JsonProperty("isCountry")
	private boolean isCountry = false;


	@JsonProperty("reverseGeocode")
	private boolean reverseGeocode;
	
	@JsonProperty("fitBounds")
	private Boolean fitBounds = true;


	
	@JsonProperty("zoomlevel")
	private Integer zoomlevel = 15;
	
	@JsonProperty("location")
	private PointDTO location = new PointDTO("0","0");
	
	@JsonProperty("markerlocation")
	private PointDTO markerlocation = new PointDTO("0","0");

	private String formalAddress = "";

	@JsonProperty("sharetitle")
	private String sharetitle = "";
	
	private String address = "Somewhere at the Equator ....";

	private String genericAddress = "Somewhere at the Equator ....";
	
	
	@JsonProperty("bounds")
	private BoundsDTO bounds = new BoundsDTO(new PointDTO("0","0"), new PointDTO("0","0"));
	
	
	@JsonProperty("location_type")
	private String locationType;

	public String getGenericAddress() {
		return genericAddress;
	}


	public void setGenericAddress(String genericAddress) {
		this.genericAddress = genericAddress;
	}


	@JsonProperty("viewport")
	private BoundsDTO viewport = new BoundsDTO(new PointDTO("0","0"), new PointDTO("0","0"));

	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public GeocodingDTO() {
	}

	
	public GeocodingDTO(String address, PointDTO location) {
		this.location = location;
		this.address = address;
	}

	public GeocodingDTO(String address, PointDTO location, BoundsDTO bounds, BoundsDTO viewport) {
		this.address = address;
		this.location = location;
		this.bounds = bounds;
		this.viewport = viewport;
	}
	
	
	public BoundsDTO getBounds() {
		return bounds;
	}

	public void setBounds(BoundsDTO bounds) {
		this.bounds = bounds;
	}

	public PointDTO getLocation() {
		return location;
	}

	public void setLocation(PointDTO location) {
		this.location = location;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public BoundsDTO getViewport() {
		return viewport;
	}

	
	public boolean isCountry() {
		return isCountry;
	}


	public void setCountry(boolean isCountry) {
		this.isCountry = isCountry;
	}

	
	public void setViewport(BoundsDTO viewport) {
		this.viewport = viewport;
	}


	public boolean isEmptyStatus() {
		return emptyStatus;
	}


	public void setEmptyStatus(boolean emptyStatus) {
		this.emptyStatus = emptyStatus;
	}


	public boolean isReverseGeocode() {
		return reverseGeocode;
	}


	public void setReverseGeocode(boolean reverseGeocode) {
		this.reverseGeocode = reverseGeocode;
	}


	public Boolean getFitBounds() {
		return fitBounds;
	}


	public void setFitBounds(Boolean fitBounds) {
		this.fitBounds = fitBounds;
	}


	public Integer getZoomlevel() {
		return zoomlevel;
	}


	public void setZoomlevel(Integer zoomlevel) {
		this.zoomlevel = zoomlevel;
	}


	public PointDTO getMarkerlocation() {
		return markerlocation;
	}


	public void setMarkerlocation(PointDTO markerlocation) {
		this.markerlocation = markerlocation;
	}


	public String getFormalAddress() {
		return formalAddress;
	}


	public void setFormalAddress(String formalAddress) {
		this.formalAddress = formalAddress;
	}


	public String getSharetitle() {
		return sharetitle;
	}


	public void setSharetitle(String sharetitle) {
		this.sharetitle = sharetitle;
	}



}
