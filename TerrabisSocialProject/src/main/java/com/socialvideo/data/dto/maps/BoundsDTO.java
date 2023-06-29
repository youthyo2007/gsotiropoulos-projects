package com.socialvideo.data.dto.maps;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BoundsDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	public BoundsDTO() {}
	
	public BoundsDTO( PointDTO northeast, PointDTO southwest) {
		this.northeast = northeast;
		this.setSouthwest(southwest);
	}
	
	
	@JsonProperty("northeast")
	private PointDTO northeast;
	
	
	@JsonProperty("southwest")
	private PointDTO southwest;


	public PointDTO getNortheast() {
		return northeast;
	}


	public void setNortheast(PointDTO northeast) {
		this.northeast = northeast;
	}

	public PointDTO getSouthwest() {
		return southwest;
	}

	public void setSouthwest(PointDTO southwest) {
		this.southwest = southwest;
	}

}
