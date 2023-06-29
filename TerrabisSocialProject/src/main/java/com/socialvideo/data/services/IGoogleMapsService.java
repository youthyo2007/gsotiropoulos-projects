package com.socialvideo.data.services;


import com.socialvideo.data.dto.maps.GeocodingDTO;


public interface IGoogleMapsService {

	public GeocodingDTO geocodeRequest(String address) throws Exception;
	public GeocodingDTO reverseGeocodeRequest(String latitude, String longitude) throws Exception;
	public GeocodingDTO search(String querytxt);
	

}
