package com.socialvideo.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.socialvideo.constant.GoogleConstants;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.CollectionDTO;
import com.socialvideo.data.dto.GameLevelDTO;
import com.socialvideo.data.dto.TagDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.maps.GeocodingDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.PublicVideoPointDTO;
import com.socialvideo.data.dto.QueryDTO;




@Component
public class WebUtility {
	
	
	
	
	
	public static RandomDataGenerator randomData = new RandomDataGenerator(); 
	
	
	
	public static Integer nextRandomInt(Integer lowerlimit, Integer upperlimit) {
		return randomData.nextInt(lowerlimit, upperlimit);
		
	}

	
	
	
	
	
	
	public static HashMap<Integer, String> videoTypeColorsStringToHashMap(String videoTypesString) {
		HashMap<Integer, String> result = new HashMap<>();
		
		
		
		
		try {
	
		String[] tokens = videoTypesString.split(",");
		int count=0;
		for (String token : tokens)
			
		{
			String color = token.substring(token.lastIndexOf(":")+2,token.lastIndexOf("\""));
			
			result.put(count,color);
			
			count++;
		}
		
		
		} catch (Exception e) {e.printStackTrace();}
		
		
		return result;
		
		
	}
	
	
	
    public static List<List<PublicVideoDTO>> publicPartitionVideoList(Device device, List<PublicVideoDTO> videoList, String gridstyle) {
    	List<List<PublicVideoDTO>> resultList = new ArrayList<List<PublicVideoDTO>>();
    	
    	int partitionSize = 0;
    	
    	if(device.isNormal() && gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_BOX))
    		partitionSize = 3;
    	
    	if(device.isNormal() && gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_THUMB))
    		partitionSize = 4;
    	
    	if(device.isTablet() && gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_BOX))
    		partitionSize = 2;
    	
    	if(device.isTablet() && gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_THUMB))
    		partitionSize = 3;
    	

    	
    	if(device.isMobile())
    		resultList.add(videoList);
    	else if(gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_DETAIL))
    		resultList.add(videoList);
    	else if(gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_ANALYTIC))
    		resultList.add(videoList);
    	else if(gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_BOX))
    		resultList = Lists.partition(videoList, partitionSize);
    	else if(gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_THUMB))
    		resultList = Lists.partition(videoList, partitionSize);
    	else if(gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_FULLSCREENMAP))
    		resultList.add(videoList);
    	
    	return resultList;
    	
    };
	
	
    public static List<List<VideoDTO>> privatePartitionVideoList(Device device, List<VideoDTO> videoList, String gridstyle) {
    	List<List<VideoDTO>> resultList = new ArrayList<List<VideoDTO>>();
    	
    	int partitionSize = 0;
    	
    	if(device.isNormal() && gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_BOX))
    		partitionSize = 3;
    	
    	if(device.isNormal() && gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_THUMB))
    		partitionSize = 4;
    	
    	if(device.isTablet() && gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_BOX))
    		partitionSize = 2;
    	
    	if(device.isTablet() && gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_THUMB))
    		partitionSize = 3;
    	

    	
    	if(device.isMobile())
    		resultList.add(videoList);
    	else if(gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_DETAIL))
    		resultList.add(videoList);
    	else if(gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_ANALYTIC))
    		resultList.add(videoList);
    	else if(gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_BOX))
    		resultList = Lists.partition(videoList, partitionSize);
    	else if(gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_THUMB))
    		resultList = Lists.partition(videoList, partitionSize);
    	else if(gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_FULLSCREENMAP))
    		resultList.add(videoList);
    	
    	return resultList;
    	
    };

    
    
    public static List<List<CollectionDTO>> partitionCollectionList(List<CollectionDTO> list, String gridstyle) {
    	List<List<CollectionDTO>> resultList = new ArrayList<List<CollectionDTO>>();
    	
    	
    	if(gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_DETAIL))
    		resultList.add(list);
    	else if(gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_BOX))
    		resultList = Lists.partition(list, 3);
    	else if(gridstyle.equalsIgnoreCase(WebConstants.GRIDSTYLE_THUMB))
    		resultList = Lists.partition(list, 4);
    	
    	return resultList;
    	
    };

    
	
	 public  static Map<String, Boolean> weatherMap() {
		 Map<String, Boolean> weatherMap = new LinkedHashMap<String, Boolean>();
			weatherMap.put("any", true);
	    	weatherMap.put("sunny", false);
	    	weatherMap.put("cloudy", false);
	    	weatherMap.put("rain", false);
	    	weatherMap.put("snow", false);
	    	weatherMap.put("windy", false);
	    	weatherMap.put("storm", false);
	    
	    	return weatherMap;
	 }
	 
	
	 
	 public  static Map<String, Boolean> seasonMap() {
		 Map<String, Boolean> seasonMap = new LinkedHashMap<String, Boolean>();
	    	seasonMap.put("any", true);
	    	seasonMap.put("winter", false);
	    	seasonMap.put("spring", false);
	    	seasonMap.put("summer", false);
	    	seasonMap.put("autumn", false);
	    	return seasonMap;
	 }
	
	 
	 
	 
	 
	
	 public  static List<GameLevelDTO> gameLevels() {
		 List<GameLevelDTO> gameLevels = new ArrayList<GameLevelDTO>();
		 	gameLevels.add(new GameLevelDTO(1,"GLASS", 0, 4));
		 	gameLevels.add(new GameLevelDTO(2,"ALUMINUM", 5, 29));
		 	gameLevels.add(new GameLevelDTO(3,"STEEL", 30, 249));
		 	gameLevels.add(new GameLevelDTO(4,"BRONZE", 250, 499));
		 	gameLevels.add(new GameLevelDTO(5,"SILVER", 500, 1499));
		 	gameLevels.add(new GameLevelDTO(6,"GOLD", 1500, 2499));
		 	gameLevels.add(new GameLevelDTO(7,"PLATINUM", 2500, 4999));
		 	gameLevels.add(new GameLevelDTO(8,"TOPAZ", 5000, 9999));
		 	gameLevels.add(new GameLevelDTO(9,"EMERALD", 10000, 14999));
		 	gameLevels.add(new GameLevelDTO(10,"DIAMOND", 15000, 9999999));
	    	return gameLevels;
	 }
	 
	 public  static Map<String, Boolean> datesMap() {
		 Map<String, Boolean> datesMap = new LinkedHashMap<String, Boolean>();
	    	datesMap.put("any", true);
	    	datesMap.put("today", false);
	    	datesMap.put("yesterday", false);
	    	datesMap.put("10 days", false);
	    	datesMap.put("last month", false);
	    	datesMap.put("last year", false);
	    	return datesMap;
	 }
	
	 public  static Map<String, Boolean> categoryMap() {
		 Map<String, Boolean> categoryMap = new LinkedHashMap<String, Boolean>();
	    	categoryMap.put("any", true);
	    	categoryMap.put("aerial", false);
	    	categoryMap.put("time lapse", false);
	    	categoryMap.put("still", false);
	    	categoryMap.put("moving", false);
	    	categoryMap.put("underwater", false);
	    	categoryMap.put("route", false);
	    	categoryMap.put("multiple locations", false);
	    	return categoryMap;
	 }
	

	 
	 public  static Map<String, Integer> categoryIDS() {
		 Map<String, Integer> categoryMap = new LinkedHashMap<String, Integer>();
	    	categoryMap.put("any", 0);
	    	categoryMap.put("aerial", 1);
	    	categoryMap.put("time lapse", 2);
	    	categoryMap.put("still", 3);
	    	categoryMap.put("moving", 4);
	    	categoryMap.put("underwater", 5);
	    	categoryMap.put("route", 6);
	    	categoryMap.put("multiple locations", 7);
	    	return categoryMap;
	 }

	 
	
	//private static final String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$&@?<>~!%#";
	private static final String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public static String genRandomPassword(Random r) {
		    while(true) {
		        char[] result = new char[r.nextBoolean()?12:13];
		        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
		        for(int i=0; i<result.length; i++) {
		            char ch = symbols.charAt(r.nextInt(symbols.length()));
		            if(Character.isUpperCase(ch))
		                hasUpper = true;
		            else if(Character.isLowerCase(ch))
		                hasLower = true;
		            else if(Character.isDigit(ch))
		                hasDigit = true;
		            
		            result[i] = ch;
		        }
		        if(hasUpper && hasLower && hasDigit) {
		            return new String(result);
		        }
		    }
		}
	
	
	
	
	
	
	
	
	public   SmartTextResultDTO processSmartText(String querytxt) throws Exception {
		
		SmartTextResultDTO smartDTO = new SmartTextResultDTO();
		
		if(querytxt.contains("#")) {
			smartDTO.addressPart = querytxt.substring(0, querytxt.indexOf("#")-1);
			smartDTO.tagsPart = querytxt.substring(querytxt.indexOf(","),querytxt.length());
			smartDTO.containsTags = true;
    	}
		
		else {
			smartDTO.addressPart = querytxt;
			smartDTO.tagsPart = "";
			smartDTO.containsTags = false;
			
		}
    	

    	//TOKENIZE ADDRESSPART
    	String[] tokens = smartDTO.addressPart.split(" ");
    	if(tokens.length==1) {
    		if(Pattern.matches("[0-9,-.]+", tokens[0])) {
    			smartDTO.reverseGeocode = true;
    			smartDTO.latitude = tokens[0];
    			smartDTO.longitude = "0";
    		}
    	}

    	else if(tokens.length==2) {
    		if(Pattern.matches("[0-9,-.]+", tokens[0]) && Pattern.matches("[0-9,-.]+", tokens[1])) {
    			smartDTO.reverseGeocode = true;
    			smartDTO.latitude = tokens[0];
    			smartDTO.longitude = tokens[1];
    		}
    	}
    	
    	else {
			smartDTO.reverseGeocode = false;
    	}
    	
    	
    	return smartDTO;
    	
		
	}
	
	
	
	public  class SmartTextResultDTO {
		
		
		public boolean containsTags = false;
    	public boolean reverseGeocode = false;
    	public String addressPart = null;
    	public String tagsPart, longitude, latitude = "";
    	
		
	}



	public static List<TagDTO> extractUniqueTagListFromVideos(List<VideoDTO> resultList) {
		List<TagDTO> tagList = new ArrayList<>();
		HashMap<String,Integer> tagmap = new HashMap<>();
		
		for (VideoDTO video : resultList) {
			List<String> videoTagsList = video.getTagslist();
			for (String videotag : videoTagsList) {
				if(tagmap.containsKey(videotag.trim())) {
					tagmap.put(videotag.trim(), tagmap.get(videotag.trim())+1);
				}
				else 
					tagmap.put(videotag.trim(), 1);
							
			}
			
		}
		
		
		for(Map.Entry<String, Integer> entry : tagmap.entrySet()){ 
			tagList.add(new TagDTO(entry.getKey()));
		}


		
		
		return tagList;
		
		
	}

	
	
	

	public static String extractUniqueTagListAsStringFromVideos(List<PublicVideoDTO> resultList) {
		String tagListstring = "";
		HashMap<String,Integer> tagmap = new HashMap<>();
		
		for (PublicVideoDTO video : resultList) {
			List<String> videoTagsList = video.getTagslist();
			for (String videotag : videoTagsList) {
				if(tagmap.containsKey(videotag.trim())) {
					tagmap.put(videotag.trim(), tagmap.get(videotag.trim())+1);
				}
				else { 
					tagmap.put(videotag.trim(), 1);
					tagListstring = tagListstring + "<a href=\"#\" class=\"text-sm text-info-dark\" onclick=\"onTagAdvCriteriaClick(\'"+videotag+"\');\">"+videotag+"</a>&nbsp;&nbsp;"; 
				}
							
			}
			
		}
		
		
		if (tagListstring.endsWith(","))
			tagListstring = tagListstring.substring(0, tagListstring.length()-1);
				
		
		return tagListstring;
	}


	public static String buildGoogleStaticImageURI(GeocodingDTO gmapresult, List<PublicVideoPointDTO> pointsList) {
		String resultURI = GoogleConstants.STATICGOOGLE_URI;
		//String markerURI = "&markers=size:mid%7Ccolor:0x449bb5%7Clabel:%7C$latitude$+$longitude$";
		String markersString = "";
		

		
		
		for (PublicVideoPointDTO point : pointsList) {
			markersString = markersString+"&markers=size:mid%7Ccolor:0x449bb5%7Clabel:%7C"+point.getLatitude()+"+"+point.getLongitude(); 
		}
		
		resultURI = resultURI.replace("$longitude$", gmapresult.getLocation().getLng());		
		resultURI = resultURI.replace("$latitude$", gmapresult.getLocation().getLat());
    	resultURI = resultURI.replace("$zoom$",gmapresult.getZoomlevel().toString());
    	resultURI = resultURI.replace("$markers$",markersString);

    	
    	return resultURI;
    	
		
		
	}
	
	


	
public static String buildTerrabisShareDesc(Integer videosCount, GeocodingDTO gmapresult, QueryDTO queryDTO) {

   String shareDescOffset = "";
   if(queryDTO.getQuerytxt().trim().length()>0)
	   shareDescOffset = "'"+queryDTO.getQuerytxt()+"'";
   else
	   shareDescOffset =  "'"+gmapresult.getGenericAddress()+"'";
   
      
   String shareDescription;
   
   if(videosCount>0)
	   if (videosCount>1)
		   shareDescription = ""+ videosCount+" videos found at "+shareDescOffset;
	   else 
		   shareDescription = ""+ videosCount+" video found at "+shareDescOffset;
	
   else 
	   shareDescription = "Explore "+shareDescOffset;
   
   
   
   
   return shareDescription;
   
}




	
	public static String buildTerrabisShareMapURI(GeocodingDTO gmapresult) {
		String resultURI = GoogleConstants.TERRABIS_SHAREMAP_MAP_URI;
    	resultURI = resultURI.replace("$center$", gmapresult.getLocation().getLat()+' '+gmapresult.getLocation().getLng());
    	resultURI = resultURI.replace("$northeast$", gmapresult.getBounds().getNortheast().getLat()+' '+gmapresult.getBounds().getNortheast().getLng());
    	resultURI = resultURI.replace("$southwest$", gmapresult.getBounds().getSouthwest().getLat()+' '+gmapresult.getBounds().getSouthwest().getLng());
    	resultURI = resultURI.replace("$zoom$",gmapresult.getZoomlevel().toString());
    	return resultURI;
	}

	
	public static String buildTerrabisShareMapImageURI(GeocodingDTO gmapresult) {
		String resultURI = GoogleConstants.TERRABIS_SHAREMAP_IMAGE_URI;
/*    	resultURI = resultURI.replace("$center$", gmapresult.getLocation().getLat()+' '+gmapresult.getLocation().getLng());
    	resultURI = resultURI.replace("$northeast$", gmapresult.getBounds().getNortheast().getLat()+' '+gmapresult.getBounds().getNortheast().getLng());
    	resultURI = resultURI.replace("$southwest$", gmapresult.getBounds().getSouthwest().getLat()+' '+gmapresult.getBounds().getSouthwest().getLng());
    	resultURI = resultURI.replace("$zoom$",gmapresult.getZoomlevel().toString());*/
    	return resultURI;
	}
	

}
