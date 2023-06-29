package com.socialvideo.data.services;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialvideo.constant.GoogleConstants;
import com.socialvideo.data.dto.maps.GeocodingDTO;
import com.socialvideo.data.dto.maps.PointDTO;
import com.socialvideo.utilities.WebUtility;
import com.socialvideo.utilities.WebUtility.SmartTextResultDTO;

@Service
public class GoogleMapsService implements IGoogleMapsService {
	 
	

	 
	  @Autowired
	  private RestTemplate restTemplate;
	  
		 
	 @Autowired
	 protected WebUtility webUtility;
		 

	  
	  
	  @Override
	  public GeocodingDTO geocodeRequest(String address) throws Exception  {
		  
		  GeocodingDTO resultDTO = new GeocodingDTO();
		  
		  Map<String, String> vars = new HashMap<String, String>();
		  vars.put("address", address);
		  vars.put("key", GoogleConstants.API_KEY);
		  String jsonResponseString = restTemplate.getForObject(GoogleConstants.GEOCODE_URI+"?address={address}&key={key}",String.class, vars);
		  //System.out.println(jsonResponseString);
		  JSONObject jsonObject = new JSONObject(jsonResponseString);
		  
		 /********************************************GEOMETRY NODE*******************************************/
		  //System.out.println(jsonObject.getJSONArray("results").getJSONObject(0).get("geometry"));
		 
		  
		  String status = jsonObject.getString("status");
		  
		  if(status.equalsIgnoreCase("ZERO_RESULTS")) {
			  resultDTO.setEmptyStatus(true);
		  }
		  
		  else {
			  ObjectMapper mapper = new ObjectMapper();
			  resultDTO = mapper.readValue(jsonObject.getJSONArray("results").getJSONObject(0).get("geometry").toString(), GeocodingDTO.class);
			  resultDTO.setAddress(jsonObject.getJSONArray("results").getJSONObject(0).get("formatted_address").toString());
			  if (jsonObject.getJSONArray("results").getJSONObject(0).get("types").toString().equalsIgnoreCase("[\"country\",\"political\"]")) {
				  resultDTO.setCountry(true);
			  }
				  
				  
				  
				  
				  
			  resultDTO.setEmptyStatus(false);
		  }
		   
		
		  
		 return resultDTO;
		 
	  } 
	  
	  @Override
	  public GeocodingDTO reverseGeocodeRequest(String latitude, String longitude) throws Exception  {
		  
		
		  GeocodingDTO resultDTO = new GeocodingDTO();
		  Map<String, String> vars = new HashMap<String, String>();
		  vars.put("latlng", latitude.trim()+","+longitude.trim());
		  vars.put("key", GoogleConstants.API_KEY);
		  String jsonResponseString = restTemplate.getForObject(GoogleConstants.GEOCODE_URI+"?latlng={latlng}&key={key}",String.class, vars);
		  //System.out.println(jsonResponseString);
		  JSONObject jsonObject = new JSONObject(jsonResponseString);
		  
		 /********************************************GEOMETRY NODE*******************************************/
		  //System.out.println(jsonObject.getJSONArray("results").getJSONObject(0).get("geometry"));
		  
		  
		  String status = jsonObject.getString("status");
		  
		  if(status.equalsIgnoreCase("ZERO_RESULTS")) {
			  resultDTO.setEmptyStatus(true);
		  }
		  
		  else {
			  ObjectMapper mapper = new ObjectMapper();
			  resultDTO = mapper.readValue(jsonObject.getJSONArray("results").getJSONObject(0).get("geometry").toString(), GeocodingDTO.class);
			  resultDTO.setAddress(jsonObject.getJSONArray("results").getJSONObject(0).get("formatted_address").toString());
			  resultDTO.setFormalAddress(jsonObject.getJSONArray("results").getJSONObject(0).get("formatted_address").toString());
			  
			  try {
			  resultDTO.setGenericAddress(jsonObject.getJSONArray("results").getJSONObject(1).get("formatted_address").toString());
			  } catch (Exception e) {e.printStackTrace();}
			  resultDTO.setEmptyStatus(false);
				
		  }
		   
		
		  
		 return resultDTO;
		 
	  }

	@Override
	public GeocodingDTO search(String querytxt) {
	
		GeocodingDTO gmapresult = new GeocodingDTO();
		
    	try {
    		
    		
    		
    		
	    	SmartTextResultDTO smartText = webUtility.processSmartText(querytxt);
	    	
	   
	    	if(smartText.reverseGeocode) {
	    		//System.out.println("attempting reverse geocode on lat:"+smartText.latitude+" and long:"+smartText.longitude);
	    		gmapresult = reverseGeocodeRequest(smartText.latitude, smartText.longitude);
	    		gmapresult.setLocation(new PointDTO(smartText.latitude,smartText.longitude));
	    	  	gmapresult.setAddress("lat:"+smartText.latitude+" - lng:"+smartText.longitude);
		    	gmapresult.setReverseGeocode(true);
		    	
	    	}

	    	else {
	    		//System.out.println("attempting normal geocode on address");
	    		gmapresult = geocodeRequest(smartText.addressPart);
		    	gmapresult.setReverseGeocode(false);
	    	}
	    	
	    	
	    	
	    	//SET DEFAULT MARKER LOCATION CENTER OF MAP 
    		gmapresult.setMarkerlocation(gmapresult.getLocation());
    		
	    	
	    	
    	} catch (Exception e) {
    		gmapresult = new GeocodingDTO();
    		gmapresult.setEmptyStatus(true);
    	}
		
		
    	
    	
    	
		return gmapresult;
		
		
	} 


			  
	  
}
