package com.socialvideo.web.controllers.mobile;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialvideo.constant.MobileConstants;
import com.socialvideo.constant.QueryConstants;
import com.socialvideo.data.dto.mobile.MobileQueryDTO;
import com.socialvideo.data.dto.AvatarDTO;
import com.socialvideo.data.dto.GameLevelDTO;
import com.socialvideo.data.dto.maps.PointDTO;
import com.socialvideo.data.dto.mobile.MobileResultDTO;
import com.socialvideo.data.dto.mobile.MobileUserDTO;
import com.socialvideo.data.dto.mobile.MobileVideoDTO;
import com.socialvideo.data.services.AvatarService;
import com.socialvideo.data.services.ChannelService;
import com.socialvideo.data.services.CollectionService;
import com.socialvideo.data.services.GamificationService;
import com.socialvideo.data.services.VideoService;
import com.socialvideo.data.services.mobile.MobileVideoService;
import com.socialvideo.web.controllers.VideoController.RelatedVideosListType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;



@EnableAutoConfiguration
@RestController
@RequestMapping(value = "/mobile/video")
public class MobileVideoController {

	 private static final Logger logger = Logger.getLogger(MobileVideoController.class);
	
	 @Autowired
	 protected MobileVideoService mobileService;

	 @Autowired
	 protected VideoService videoService;
	 
	 @Autowired
	 protected ObjectMapper jacksonMapper;
	 
	 @Autowired
	 protected AvatarService avatarService;
	
	 
	 @Autowired
	 protected GamificationService gameService;
	 
	 
	 

    public MobileVideoController() {
    }
    
    

    //VALIDATE JWTS TOKEN
    private void validateJWTToken(String tokenString) throws Exception {
   
    	//UNMARSHALL TOKEN
   	 Jws<Claims> claims = Jwts.parser().setSigningKey(MobileConstants.JwsSignedKey).parseClaimsJws(tokenString);
   	 
   	 //validate issuer
   	 String issuer = claims.getBody().getIssuer();
   	 if(!(issuer.equals(MobileConstants.androidIssuerID) || (issuer.equals(MobileConstants.iosIssuerID))))
   			 throw new Exception("incorrect issuer");
   		
    }
    
    
    
    //PARSE JWTS TOKEN
    private MobileQueryDTO parseJWTToken(String tokenString) throws Exception {
   
    	//UNMARSHALL TOKEN
   	 Jws<Claims> claims = Jwts.parser().setSigningKey(MobileConstants.JwsSignedKey).parseClaimsJws(tokenString);
   	 
   	 //validate issuer
   	 String issuer = claims.getBody().getIssuer();
   	 if(!(issuer.equals(MobileConstants.androidIssuerID) || (issuer.equals(MobileConstants.iosIssuerID))))
   			 throw new Exception("incorrect issuer");
   	 
   	 
   	 if(!(claims.getBody().containsKey("jis")))
			 throw new Exception("jis missing");
	
   	 
   	 String jisString = claims.getBody().get("jis").toString();
 	
   	 ObjectMapper jacksonMapper = new ObjectMapper();
   	MobileQueryDTO queryDTO = jacksonMapper.readValue(jisString, MobileQueryDTO.class);
    return 	queryDTO;
    	
    }
    
    
    
    //TIME
    @RequestMapping(value="/time", method = RequestMethod.GET)
	public ResponseEntity<String> time(Model model) {
      	
    	ResponseEntity<String> response = null;

    	try {

        response = new ResponseEntity<String>(System.currentTimeMillis()+"", HttpStatus.OK);
    	
    	} catch (Exception e) {  response = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);}
	    return response;
    }
    
    
    
    
    
    //SIGNLE VIDEO INFO
    @RequestMapping(value="/{uuid}", method = RequestMethod.GET)
	public ResponseEntity<MobileResultDTO> videoDetails(Model model, @PathVariable String uuid,	@RequestParam(value = "token", required=true) String token) {
      	
    	ResponseEntity<MobileResultDTO> response = null;

    	try {
    		
    	validateJWTToken(token);
    		
    	MobileVideoDTO videoDTO = mobileService.selectVideoByUUID(uuid);
    	
    	
    	
        
        /*************************************FETCH RELATED VIDEOS *********************************************/
    	//SET RELATED LIST TYPE TO BE RETURNED
        String relatedvideostype = "";
        
    	List<MobileVideoDTO> relatedvideoslist = null; 
		
     	 //FETCH NEARBY VIDEOS AND IF NOT FOUND FETCH TAG MATCHED VIDEOS
		MobileQueryDTO instantQueryDTO = new MobileQueryDTO(new PointDTO(videoDTO.getLatitude().toString(), videoDTO.getLongitude().toString()),QueryConstants.NEARBYRANGE,0,10);
		relatedvideoslist = mobileService.selectNearyByVideos(instantQueryDTO);
     	 if(!(relatedvideoslist.size()<=1))
     		relatedvideostype = RelatedVideosListType.NEARBY.toString();
     	 else {//FETCH TAG MATCHED VIDEOS
     		relatedvideoslist = mobileService.selectRelatedTagVideos(videoDTO.getTags(),0,10);
     	
         	if(!(relatedvideoslist.size()<=1))
         		relatedvideostype =  RelatedVideosListType.TAGMATCHED.toString();
         	else {  //IF STILL NO TAGS MATCHED FETCH LATEST VIDEOS
         		instantQueryDTO = new MobileQueryDTO(null,null,QueryConstants.FilterLatest,0,10);
         		relatedvideoslist = mobileService.selectVideos(instantQueryDTO);
         		relatedvideostype = RelatedVideosListType.LATEST.toString();
     	 	}      	 
     	 }
    
     	 videoDTO.setRelatedvideoslist(relatedvideoslist);
     	videoDTO.setRelatedvideostype(relatedvideostype);
    	 
     	 /*************************************FETCH RELATED VIDEOS *********************************************/
         
    	
    	
    	response = new ResponseEntity<MobileResultDTO>(new MobileResultDTO( HttpStatus.OK, videoDTO), HttpStatus.OK);
    	} catch (Exception e) {  response = new ResponseEntity<MobileResultDTO>(new MobileResultDTO( HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);}
	    return response;
    }

    
    //NEARBY VIDEOS
    @RequestMapping(value="/map/nearby", method = RequestMethod.GET)
	public ResponseEntity<MobileResultDTO> nearbyVideoList(Model model, @RequestParam(value = "token", required=true) String token) {  
    	ResponseEntity<MobileResultDTO> response = null;
    	Integer total = null;
    	//TODO DECODE TOKEN AND ECTRACT MOBILE QUERY DTO
    	try {
    
    		MobileQueryDTO queryDTO = parseJWTToken(token);
    		
    		//IF COUNT FLAG
    		if(queryDTO.getCount())
    			total = mobileService.selectCountNearyByVideos(queryDTO);
    		
    		
    		//FETCH VIDEOS
    		List<MobileVideoDTO> resultList = mobileService.selectNearyByVideos(queryDTO);

    		
    		response = new ResponseEntity<MobileResultDTO>(new MobileResultDTO(resultList,total,HttpStatus.OK), HttpStatus.OK);
    	} catch (Exception e) {  response = new ResponseEntity<MobileResultDTO>(new MobileResultDTO( HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);}
	    return response;
    }

    
    
    
    //MAP VIDEOS BASED ON BOUNDS
    @RequestMapping(value="/map/viewport", method = RequestMethod.GET)
	public ResponseEntity<MobileResultDTO> viewportVideoList(Model model, @RequestParam(value = "token", required=true) String token) {  
    	ResponseEntity<MobileResultDTO> response = null;
    	Integer total = null;
    	//TODO DECODE TOKEN AND ECTRACT MOBILE QUERY DTO
    	try {
    
    		MobileQueryDTO queryDTO = parseJWTToken(token);
    		
    		queryDTO.setQuerytype(QueryConstants.QUERYTYPE_MAP);
    		
    		//IF COUNT FLAG
    		if(queryDTO.getCount())
    			total = mobileService.selectCountVideos(queryDTO);
    		
    		
    		//FETCH VIDEOS
    		List<MobileVideoDTO> resultList = mobileService.selectVideos(queryDTO);

    		
    		response = new ResponseEntity<MobileResultDTO>(new MobileResultDTO(resultList,total,HttpStatus.OK), HttpStatus.OK);
    	} catch (Exception e) {  response = new ResponseEntity<MobileResultDTO>(new MobileResultDTO( HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);}
	    return response;
    }
    
    //CATALOG VIDEOS
    @RequestMapping(value="/catalog", method = RequestMethod.GET)
	public ResponseEntity<MobileResultDTO> catalogVideoList(Model model, @RequestParam(value = "token", required=true) String token) {  
    	ResponseEntity<MobileResultDTO> response = null;
    	Integer total = null;
    	//TODO DECODE TOKEN AND ECTRACT MOBILE QUERY DTO
    	try {
    
    		MobileQueryDTO queryDTO = parseJWTToken(token);
    		
    		//IF COUNT FLAG
    		if(queryDTO.getCount())
    			total = mobileService.selectCountVideos(queryDTO);
    		
    		
    		//FETCH VIDEOS
    		List<MobileVideoDTO> resultList = mobileService.selectVideos(queryDTO);

    		
    		response = new ResponseEntity<MobileResultDTO>(new MobileResultDTO(resultList,total,HttpStatus.OK), HttpStatus.OK);
    	} catch (Exception e) {  response = new ResponseEntity<MobileResultDTO>(new MobileResultDTO( HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);}
	    return response;
    }
    
    
    //USER VIDEOS
    @RequestMapping(value="/user/{uuid}/list", method = RequestMethod.GET)
	public ResponseEntity<MobileResultDTO> userVideoList(Model model,   @PathVariable String uuid,@RequestParam(value = "token", required=true) String token) {  
    	ResponseEntity<MobileResultDTO> response = null;
    	Integer total = null;
    	//TODO DECODE TOKEN AND ECTRACT MOBILE QUERY DTO
    	try {
    
    		MobileUserDTO mobileUserDTO = mobileService.selectUserByUUID(uuid);
    		MobileQueryDTO queryDTO = parseJWTToken(token);
    		queryDTO.setUserid(mobileUserDTO.getId());
    		
    		//IF COUNT FLAG
    		if(queryDTO.getCount())
    			total = mobileService.selectCountVideos(queryDTO);
    		
    		
    		//FETCH VIDEOS
    		List<MobileVideoDTO> resultList = mobileService.selectVideos(queryDTO);

    		
    		response = new ResponseEntity<MobileResultDTO>(new MobileResultDTO(resultList,total,HttpStatus.OK), HttpStatus.OK);
    	} catch (Exception e) {  response = new ResponseEntity<MobileResultDTO>(new MobileResultDTO( HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);}
	    return response;
    }    
    
    
    //USER PROFILE INFO
    @RequestMapping(value="/user/{uuid}", method = RequestMethod.GET)
	public ResponseEntity<MobileResultDTO> catalogVideoList(Model model,   @PathVariable String uuid, @RequestParam(value = "token", required=true) String token) {  
    	ResponseEntity<MobileResultDTO> response = null;
    	Integer total = null;
    	//TODO DECODE TOKEN AND ECTRACT MOBILE QUERY DTO
    	try {
    		
    		validateJWTToken(token);
    		
    		MobileUserDTO mobileUserDTO = mobileService.selectUserByUUID(uuid);
    		MobileQueryDTO queryDTO = new MobileQueryDTO();
    		queryDTO.setUserid(mobileUserDTO.getId());

     	   GameLevelDTO gameLevelDTO = gameService.findGameLevel(mobileUserDTO);
     	   mobileUserDTO.setGameLevel(gameLevelDTO);
     	  
 	       AvatarDTO avatarDTO = avatarService.findActive(Long.parseLong(mobileUserDTO.getId()+""));
 	       mobileUserDTO.setAvatar(avatarDTO);
     	
    		
    		//FETCH VIDEOS
    		//List<MobileVideoDTO> resultList = mobileService.selectVideos(queryDTO);

    		
    		response = new ResponseEntity<MobileResultDTO>(new MobileResultDTO(HttpStatus.OK,mobileUserDTO), HttpStatus.OK);
    	} catch (Exception e) {  response = new ResponseEntity<MobileResultDTO>(new MobileResultDTO( HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);}
	    return response;
    } 



        
}