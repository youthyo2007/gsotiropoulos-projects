package com.socialvideo.web.controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.services.fusiontables.Fusiontables;
import com.google.api.services.fusiontables.Fusiontables.Query.Sql;
import com.socialvideo.constant.Constants;
import com.socialvideo.constant.GoogleConstants;
import com.socialvideo.constant.VideoHealthStatus;
import com.socialvideo.constant.VideoIntegrationStatus;
import com.socialvideo.constant.VideoPublishStatus;
import com.socialvideo.constant.VideoTransferStatus;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.Activity;
import com.socialvideo.data.dto.Activity.ActivityEnum;
import com.socialvideo.data.dto.integration.FusionTableVideoDTO;
import com.socialvideo.data.dto.maps.GeocodingDTO;
import com.socialvideo.data.dto.ActivityIdentity;
import com.socialvideo.data.dto.AjaxResultDTO;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.EventDTO;
import com.socialvideo.data.dto.IDDescrDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PersonalizationDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.QueryDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VideoClaimDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.services.AWSService;
import com.socialvideo.data.services.DatabaseService;
import com.socialvideo.data.services.EmailService;
import com.socialvideo.data.services.GoogleAuthService;
import com.socialvideo.data.services.IntegrationService;
import com.socialvideo.data.services.PublicVideoService;
import com.socialvideo.data.services.UserService;
import com.socialvideo.data.services.VideoClaimService;
import com.socialvideo.data.services.YouTubeService;
import com.socialvideo.data.services.inter.IUserService;
import com.socialvideo.data.services.inter.IVideoService;
import com.socialvideo.utilities.TimeUtility;
import com.socialvideo.utilities.WebUtility;
import com.socialvideo.web.events.GenericEvent;



@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/tool")
public class ToolController {

	 private static final Logger logger = Logger.getLogger(ToolController.class);

	 protected ObjectMapper jacksonMapper;
	 
	 @Autowired
	 protected UserService userService;

	 @Autowired
	 protected IVideoService privateVideoService;

	 @Autowired
	 protected PublicVideoService videoService;
	 
	 @Autowired
	 protected IntegrationService integrationService;
	
	 @Autowired
	 protected DatabaseService dbService;
	 
	 @Autowired
	 protected AWSService awsService;
	 
	 @Autowired
	 YouTubeService youtubeService;
	 

	 
	 
	 @ModelAttribute(WebConstants.VIDEOUPLOADFRM)
     public VideoDTO init_VIDEOUPLOADFRM(@ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult) {
		 
		 
		 	VideoDTO videoDTO = new VideoDTO();
	        videoDTO.setFootagedate(new Date());
	        videoDTO.setCategoryid(WebUtility.categoryIDS().get("aerial").toString());
	       return  videoDTO;
     }
	 

	 @ModelAttribute("videoTypeList")
     public List<IDDescrDTO> getVideoTypelist() {
	       return   dbService.getVideoTypelist();
     }
	 
	 
	 @ModelAttribute("videoShootingTypeList")
     public List<IDDescrDTO> getVideoShootingTypelist() {
	       return   dbService.getVideoShootingTypelist();
     }
	
	 
	 
    public ToolController() {
    }
    
    
    //YOUTUBE GEO SEARCH TOOL REALLY HANDY
    @RequestMapping(value={"/youtube/index"}, method=RequestMethod.GET)
    public ModelAndView youtubeIndex(Device device, @AuthenticationPrincipal CurrentUserDTO activeUser) throws Exception {
    	ModelAndView mav = new ModelAndView("tool-youtube-view");
        mav.addObject("device", device);
        return mav;
    }
    
    
    
    //UPLOAD SUBMIT
    @RequestMapping(value="/youtube/submit", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> submitYoutubeVideo( @AuthenticationPrincipal CurrentUserDTO activeUser, @RequestBody VideoDTO videoDTO) {
    	Long userID = activeUser.getUser().getId();
	 	String selectedVideoTypesCommaString = videoDTO.getSelectedvideotypelist();	
		
	 	videoDTO.setUserid(userID);
	    videoDTO.setUUID(UUID.randomUUID().toString().replace("-", ""));
	    videoDTO.setLink(true);
	    videoDTO.setIsfile(false);
    	
	 	
	    //SET STATUSES
	    videoDTO.setTransferstatus(VideoTransferStatus.NONE);
    	videoDTO.setIntegrationstatus(VideoIntegrationStatus.NONE);
	    videoDTO.setHealthstatus(VideoHealthStatus.HEALTHY);
	    videoDTO.setStatus(VideoPublishStatus.YOUTUBETOOLUPLOADED);
		if(selectedVideoTypesCommaString.trim().length()>0) {
		    	 String[] items = selectedVideoTypesCommaString.split(",");
		    	 videoDTO.setVideotypeids(selectedVideoTypesCommaString);
		    	 videoDTO.setMarkerimgid(items[0]);
		}
	    
	    //CREATE NEW VIDEO
	    videoDTO = privateVideoService.createNewVideo(videoDTO, new Date());

		   
	    /***************UPDATE VIDEO TYPES*****************************************/
	    if(selectedVideoTypesCommaString.trim().length()>0) {
	    	 String[] items = selectedVideoTypesCommaString.split(",");
	         List<String> videoTypesIDList = Arrays.asList(items);

		    for (String videotypeid : videoTypesIDList) {
		    	try {
		    		privateVideoService.insertVideoTypesListOfVideo(videoDTO.getId(), Long.parseLong(videotypeid));
		    	} catch (Exception e) {e.printStackTrace();}
		    }
	    }	 
	    /***************UPDATE VIDEO TYPES*****************************************/
	    
	    
	    
	    //INTEGRATE WITH VIMEO AND YOUTUBE TO LOAD DATA
	    integrationService.integrateDataFromVimeoYoutube(videoDTO.getId(),false);	    
	    
	    
	    //CREATE THUMBNAIL
	    try {
	    
	   //REFRESH OBJECT WITH THE LATEST CHANGES FROM INTEGRATION DATA 	
	    videoDTO = privateVideoService.findVideoById(videoDTO.getId());	
	    integrationService.createVideoThumbImg(videoDTO);
	    
	    } catch (Exception e) {e.printStackTrace();}
	    
	    
	    //UPDATE DURATION
		try {
    		Long videoid = 	videoDTO.getId();
    		Integer duration =  TimeUtility.convertDurationStringToSeconds(videoDTO.getDurationtxt());
    		privateVideoService.updateDuration(videoid, duration);
    		
	    } catch (Exception e) {e.printStackTrace();}
	    
	    
		
		
        //BUILD AND RETURN RESULT OBJ
	    PublicVideoDTO publicVideoDTO = videoService.findPublicVideoById(videoDTO.getId());
        AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(HttpStatus.OK,publicVideoDTO);
        
     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);
	    
    	
    	
    }
    
    
    
    
    
    
    

    //YOUTUBE GEO SEARCH TOOL REALLY HANDY
    @RequestMapping(value={"/fusion/index"}, method=RequestMethod.GET)
    public ModelAndView fusionIndex(Device device, @AuthenticationPrincipal CurrentUserDTO activeUser) throws Exception {
    	ModelAndView mav = new ModelAndView("fusion-tables-index");

    	
    	GoogleAuthorizationCodeFlow flow = GoogleAuthService.initFusionTablesReadOnlyFlow();
		Credential credential = flow.loadCredential(GoogleAuthService.TERRABIS_FUSION_AUTH_READONLY_USER);
		
		  if(credential!=null) {
	   		//REFRESH ACCESS TOKEN IN CASE OF EXPIRATION
  	    	credential.refreshToken();
		  }
		  
		  mav.addObject("crd", credential);
		  mav.addObject("device", device);
	
		  
        return mav;
    }
    
  //YOUTUBE GEO SEARCH TOOL REALLY HANDY
    @RequestMapping(value={"/fusion/index/markers"}, method=RequestMethod.GET)
    public ModelAndView fusionIndexMarkers(Device device, @AuthenticationPrincipal CurrentUserDTO activeUser) throws Exception {
    	ModelAndView mav = new ModelAndView("fusion-tables-index-custom-markers");
    	
    	GoogleAuthorizationCodeFlow flow = GoogleAuthService.initFusionTablesReadOnlyFlow();
		Credential credential = flow.loadCredential(GoogleAuthService.TERRABIS_FUSION_AUTH_READONLY_USER);
		
		  if(credential!=null) {
	   		//REFRESH ACCESS TOKEN IN CASE OF EXPIRATION
  	    	credential.refreshToken();
		  }
		  
		  mav.addObject("crd", credential);
		  mav.addObject("device", device);
		  
		  return mav;
		
	}
    	
    	
    
    
    //YOUTUBE GEO SEARCH TOOL REALLY HANDY
/*    @RequestMapping(value={"/fusion/test/insert"}, method=RequestMethod.GET)
    public ModelAndView fusiontestInsert(Device device, @AuthenticationPrincipal CurrentUserDTO activeUser) throws Exception {
    	ModelAndView mav = new ModelAndView("fusion-tables-test-insert");
    	
    	//GoogleAuthorizationCodeFlow flow = GoogleAuthService.initFusionTablesAdminFlow();
		Credential credential = null;//flow.loadCredential(GoogleAuthService.TERRABIS_FUSION_AUTH_ADMIN_USER);
		
		  if(credential!=null) {
	   		//REFRESH ACCESS TOKEN IN CASE OF EXPIRATION
  	    	credential.refreshToken();
		  }
		  
		  System.out.println(credential.getAccessToken());
		  
		  
		  mav.addObject("crd", credential);
		  mav.addObject("device", device);
		  
		  
		    Fusiontables fusiontables =GoogleAuthService.initFusionTableAPI(credential);
		    FusionTableVideoDTO fusionTableVideoDTO = privateVideoService.findFusionVideoById(Long.parseLong(2948893+""));

		    
		    
		    
		    //String sqlStatement = "INSERT INTO "+GoogleConstants.TERRABISPOINTS_TABLEID +" (id,UUID) VALUES (57909090,'9898989898989898')"; 
	
		    
		    String sqlStatement = "INSERT INTO "+GoogleConstants.TERRABIS_PRIVATE_FUSION_TABLE_MASTER
		    +" (id,UUID,title,description,latitude,longitude,markerimgid,shootingtypeid,status,healthstatus) VALUES " 
		    +" ("+fusionTableVideoDTO.getId()+",'"+fusionTableVideoDTO.getUUID()+"','" 
		    +fusionTableVideoDTO.getTitle()+"','"	    		
		    +fusionTableVideoDTO.getDescription()+"',"	    		
		    +fusionTableVideoDTO.getLatitude()+","	    		
		    +fusionTableVideoDTO.getLongitude()+","	    		
		    +fusionTableVideoDTO.getMarkerimgid()+","	    		
		    +fusionTableVideoDTO.getShootingtypeid()+","	    		
		    +fusionTableVideoDTO.getStatus()+","	    		
		    +fusionTableVideoDTO.getHealthstatus()+")";	 
		 		
		    		
		    Sql fusionTablesSql = fusiontables.query().sql(sqlStatement);
		    
		    try {
		    	fusionTablesSql.execute();
		      } catch (IllegalArgumentException e) {
		    	  e.printStackTrace();
		        // For google-api-services-fusiontables-v1-rev1-1.7.2-beta this exception will always
		        // been thrown.
		        // Please see issue 545: JSON response could not be deserialized to Sqlresponse.class
		        // http://code.google.com/p/google-api-java-client/issues/detail?id=545
		      }
		    	
		  
		  
		  
		  
		  
		  
		  return mav;
		  
		  
		  
		  
		  
		  
		
	}
    	*/
    
    
    

        
}