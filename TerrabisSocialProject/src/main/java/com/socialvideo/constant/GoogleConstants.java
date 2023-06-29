package com.socialvideo.constant;

import com.socialvideo.data.dto.maps.BoundsDTO;
import com.socialvideo.data.dto.maps.PointDTO;

public class GoogleConstants {
	
	//TERRABIS YOUTUBE CHANNEL
	// terrabis@outlook.com
	// x0u^EDQsTn
	
	
	 //GOOGLE MAP SEARCH INITIALIZATION ATHENS GREECE
	 public static String initAddress = "London, United Kingdom"; 
	 public static PointDTO initLocation = new PointDTO("51.52648","-0.24565"); //ADDRESS LONDON UK 
	 public  static BoundsDTO initBound = new BoundsDTO(new PointDTO("68.56770639618274","55.01864711977532"),new PointDTO("16.06506881678898","-121.28994663022468")); 
	
	 public static final String GEOCODE_URI = "https://maps.googleapis.com/maps/api/geocode/json";
	 //public static final String API_KEY = "AIzaSyAcTB5EdpPANBHw2z9tHpZeYgQb56w5wZQ"; //GEORGE
	 public static final String API_KEY = "AIzaSyBrqn9Rl-MX3yJPDKGXL_Ly7ej6DSC0sRw"; //TERRABIS
	 public static final String YOUTUBE_URI = "https://www.googleapis.com/youtube/v3";
	 public static final String SERVICE_ACCOUNT_EMAIL = "account-1@terrabis-1078.iam.gserviceaccount.com";
	 public static final String SERVICE_ACCOUNT_OWNER= "gsotiropoulos2007@gmail.com";
	 public static final String OAUTH_YOUTUBE_CLIENT_ID= "976629344421-l2kob5j6l32tbp86nrdvqtg82vkdkqm3.apps.googleusercontent.com";
	 public static final String OAUTH_YOUTUBE_CLIENT_SECRET= "dvtDhRJcvzsGouo3Us7Ym9kv";
	 public static final String OAUTH_FUSIONTABLE_CLIENT_ID= "976629344421-kenb71v5m83an9p974fg0201mdmim1cg.apps.googleusercontent.com";
	 public static final String OAUTH_FUSIONTABLE_CLIENT_SECRET= "WXmUI6xpefXIGFqCHA578mSn";
	 
	 
	 public static final String STATICGOOGLE_URI= "https://maps.googleapis.com/maps/api/staticmap?center=$latitude$+$longitude$&zoom=$zoom$&scale=false&size=600x300&maptype=hybrid&format=png&visual_refresh=true&$markers$&key="+API_KEY;
	 public static final String TERRABIS_SHAREMAP_MAP_URI = "http://www.terrabis.com/video/map/normal/_?center=$center$&zoom=$zoom$&northeast=$northeast$&southwest=$southwest$";
/*	 public static final String TERRABIS_SHAREMAP_IMAGE_URI = "http://www.terrabis.com/video/map/google-static-image/?center=$center$&zoom=$zoom$&northeast=$northeast$&southwest=$southwest$";*/
	 public static final String TERRABIS_SHAREMAP_IMAGE_URI = "http://www.terrabis.com/video/map/static/snapshot";
	 public static final String TERRABIS_PRIVATE_FUSION_TABLE_MASTER = "1kvOiNnge2P84qI26hkzUlujdoVs4wYtHSc0BJuq5";
	 public static final String TERRABIS_PUBLIC_FUSION_TABLE = "11nY5lrBT8Wa4WNeq18Xvf6O-cO_mrLCERIKguLpY";	
	 
	 
}


