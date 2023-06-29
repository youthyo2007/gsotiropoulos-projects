package com.socialvideo.web.controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.socialvideo.config.AWSConfiguration;
import com.socialvideo.constant.QueryConstants;
import com.socialvideo.constant.VideoHealthStatus;
import com.socialvideo.constant.VideoIntegrationStatus;
import com.socialvideo.constant.VideoLoadStatus;
import com.socialvideo.constant.VideoPublishStatus;
import com.socialvideo.constant.VideoTransferStatus;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.EventDTO;
import com.socialvideo.data.dto.FileUploadResultDTO;
import com.socialvideo.data.dto.IDDescrDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.PublicVideoPointDTO;
import com.socialvideo.data.dto.TagDTO;
import com.socialvideo.data.dto.VideoCatalogQueryDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.maps.GeocodingDTO;
import com.socialvideo.data.services.AWSService;
import com.socialvideo.data.services.DatabaseService;
import com.socialvideo.data.services.IntegrationService;
import com.socialvideo.data.services.PublicVideoService;
import com.socialvideo.data.services.YouTubeService;
import com.socialvideo.data.services.inter.IUserService;
import com.socialvideo.data.services.inter.IVideoService;
import com.socialvideo.utilities.TimeUtility;
import com.socialvideo.utilities.WebUtility;
import com.socialvideo.web.events.GenericEvent;


@EnableAutoConfiguration
@Controller
@RequestMapping(value = "upload/")
@SessionAttributes({WebConstants.GMAPRESULT,WebConstants.PERSONALIZATION})
public class UploadController {

	
	 private static final Logger logger = Logger.getLogger(UploadController.class);


	 @Autowired
	 protected IVideoService privateVideoService;

	 @Autowired
	 protected PublicVideoService videoService;

	 @Autowired
	 protected IntegrationService integrationService;
	 
	 @Autowired
	 protected IUserService userService;
	 
	 
	 @Autowired
	 protected DatabaseService dbService;
	 
	 @Autowired
	 protected AWSService awsService;
	 
	 @Autowired
	 YouTubeService youtubeService;
	 
	   
	 @Autowired
	 private ApplicationEventPublisher eventPublisher;
	 
	 
	 @ModelAttribute
     public void navBarAttributes(Model model) {
		model.addAttribute(WebConstants.NAVBAR_SHOWUPLOADBUTTON, false);
		model.addAttribute(WebConstants.NAVBAR_SHOWSEARCHTXT, true);
		
     }
	 

	 @ModelAttribute(WebConstants.VIDEOUPLOADFRM)
     public VideoDTO init_VIDEOUPLOADFRM(@ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult) {
		 
		 
		 	VideoDTO videoDTO = new VideoDTO();
	        videoDTO.setLongitude(gmapresult.getLocation().getLngnumber());
	        videoDTO.setLatitude(gmapresult.getLocation().getLatnumber());
	        videoDTO.setWeatherMap(WebUtility.weatherMap());
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
	 
	 
	 @ModelAttribute("tagDTOList")
     public List<TagDTO> init_TAGLIST() {
	       return   dbService.getAllTagsList(20);
     }
	 
	 
	 
    public UploadController() {
    }

    //UPLOAD INDEX
    @RequestMapping(value={"/video"}, method=RequestMethod.GET) 
    public ModelAndView index( @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, 
    		@RequestParam(value = "lat", required=false) String latitude,
    		@RequestParam(value = "lng", required=false) String longitude) throws Exception  {
        ModelAndView mav = new ModelAndView("video-upload-index");
        
        String currentLatitude = latitude!=null ? latitude : gmapresult.getLocation().getLat().toString();
        String currentLongitude = longitude!=null ? longitude : gmapresult.getLocation().getLng().toString();
        
        
        mav.addObject("uploadLatitude", currentLatitude);
        mav.addObject("uploadLongitude",currentLongitude);
        
        
    	//FETCH NEARBY VIDEO POINTS AND IF NOT FOUND FETCH TAG MATCHED VIDEOS
        PaginatorDTO paginator = new PaginatorDTO(100, 10);
        paginator.firstPage();
        List<PublicVideoPointDTO> relatedPointsList = videoService.selectNearyByVideoPoints(null,currentLatitude, currentLongitude, QueryConstants.NEARBYRANGE,paginator);
	   	mav.addObject("relatedPointListDTO", relatedPointsList);
	   	
        
        return mav;
    }

    //UPLOAD VIDEO LANDING PAGE
    @RequestMapping(value={"/upload-success"}, method=RequestMethod.GET) 
    public ModelAndView videoUploadLandingPage(Device device,@AuthenticationPrincipal CurrentUserDTO activeUser) throws Exception  {
        
    	//LOAD LATEST VIDEO FROM DATABASE IE THE VIDEO JUST UPLOADED OF THE CURRENT USER
    	PublicVideoDTO video = videoService.findLatestUploadedVideoOfUser(activeUser.getUser().getId());
    	ModelAndView mav = new ModelAndView("video-upload-success");
    	
    	//CAUSE WE NEED LAT AND LNG TO RE-UPLOAD AT THE SAME LOCATION WE ADD A PRIVATE VIDEODTO TO RESPONSE. ITS OK CAUSE 
    	//THE USER KNOWS ALREADY THE LAT AND LNG OF THE VIDEO CAUSE HE IS THE ONE WHO UPLOADED IT AT THE FIRST PLACE
        mav.addObject("videoDTO", privateVideoService.findVideoById(video.getId()));
        mav.addObject("device", device);

        
        //FIND 10 LATEST VIDEOS
        List<PublicVideoDTO> resultList = videoService.selectCatalogVideos(new VideoCatalogQueryDTO(null, QueryConstants.FilterLatest, null,null), new PaginatorDTO(10));
        //List videoListPartions = WebUtility.publicPartitionVideoList(device,resultList, WebConstants.GRIDSTYLE_BOX);
        mav.addObject("videoListDTO", resultList);
        
        return mav;
    }
    
    
    //UPLOAD SUBMIT
    @RequestMapping(value="/video-submit/file", method=RequestMethod.POST,  produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public FileUploadResultDTO videoUploadFile(@ModelAttribute(WebConstants.VIDEOUPLOADFRM) VideoDTO videoDTO, @RequestParam("file") MultipartFile filerequest, @AuthenticationPrincipal CurrentUserDTO activeUser){
    	Long userID = activeUser.getUser().getId();
    	FileUploadResultDTO response = new FileUploadResultDTO();
    	
    	Boolean videoUploadError = false;
    	
    	
	 	String selectedVideoTypesCommaString = videoDTO.getSelectedvideotypelist();

	 	videoDTO.setUserid(userID);
	    videoDTO.setUUID(UUID.randomUUID().toString().replace("-", ""));
	    videoDTO.setLink(false);
	    videoDTO.setIsfile(true);
	    
	    

	    /**DEPRECATED *********/
        //EXTRACT WEATHER STRING FROM BOOLEAN MAP
	    //videoDTO.initWeatherString();
	    //System.out.println(videoDTO.getCategoryid());
	    /***DEPRECATED******************/
	    

	    try {
	    
	    //FILE UPLOAD
	    	MultipartFile file = filerequest;
	    	videoDTO.setFiledata(file);

	    	if (!file.isEmpty()) {
		
		 	       videoDTO.setOriginalfilename(file.getOriginalFilename().toLowerCase());
		           videoDTO.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length()).toLowerCase());
			       videoDTO.setContenttype(file.getContentType());
			       videoDTO.setVideosize(file.getSize());
			       
			       videoDTO.setLoadstatus(VideoLoadStatus.MANUAL);
			       videoDTO.setTransferstatus(VideoTransferStatus.NONE);
			       videoDTO.setIntegrationstatus(VideoIntegrationStatus.S3);
				   videoDTO.setHealthstatus(VideoHealthStatus.HEALTHY);
				   videoDTO.setStatus(VideoPublishStatus.JUSTUPLOADED);
				   if(selectedVideoTypesCommaString.trim().length()>0) {
				    	 String[] items = selectedVideoTypesCommaString.split(",");
				    	 videoDTO.setVideotypeids(selectedVideoTypesCommaString);
				    	 videoDTO.setMarkerimgid(items[0]);
				   }
				   
				   //CREATE NEW VIDEO
				   videoDTO = privateVideoService.createNewVideo(videoDTO,new Date());

				   
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

				   
				   

				   
				   //SET RESPONSE WITH THE NEW VIDEO OBJECT
				   response.successVideo(videoDTO);
			       
			       
			       
			     //CHECK IF USER FOLDER EXISTS AND IF NOT THEN CREATE IT
/*			     boolean userfolderExists = awsService.folderObjectExists(AWSConfiguration.USERSVIDEOBUCKET, UUIDUser);
			     if(!userfolderExists)
			    	 awsService.createFolder(AWSConfiguration.USERSVIDEOBUCKET, UUIDUser);*/
			     
			     //UPLOAD FILE TO S3 
		 	     PutObjectResult resultS3 = awsService.addFiletoBucket(AWSConfiguration.USERSVIDEOBUCKET, videoDTO.getId()+"."+videoDTO.getExtension(), file);
		 	     
	
		 	   	
			    //PUBLISH EVENT
			    eventPublisher.publishEvent(new GenericEvent(new EventDTO(videoDTO, EventDTO.VID_JUSTUPLOADED)));
			    	
			 
		 	     
	    	} else {
	    		videoUploadError = true;
	    		response.error(videoDTO.getFilename(), "You failed to upload " +"video" + " =>  because the file was empty");	    		
	    	}
	    
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	videoUploadError = true;
            response.error("User:"+videoDTO.getUserid(), "You failed to upload " +"video" + " => " + e.getMessage());
	    }
	    
	    
	    
	    
	    
	   //DELETE VIDEO RECORD ON ERROR
	    if(videoUploadError) {
		    privateVideoService.deleteVideo(userID, videoDTO.getId());
	    }
	   
        return response;
    }
    
    
    //UPLOAD SUBMIT
    @RequestMapping(value="/video-submit/link", method=RequestMethod.POST)
    public String videoUploadLink(@ModelAttribute(WebConstants.VIDEOUPLOADFRM) VideoDTO videoDTO, @AuthenticationPrincipal CurrentUserDTO activeUser){
    	Long userID = activeUser.getUser().getId();
	 	String selectedVideoTypesCommaString = videoDTO.getSelectedvideotypelist();
	 	
	 	
	 	videoDTO.setUserid(userID);
	    videoDTO.setUUID(UUID.randomUUID().toString().replace("-", ""));
	    videoDTO.setLink(true);
	    videoDTO.setIsfile(false);
	    
	    
	    /**DEPRECATED *********/
        //EXTRACT WEATHER STRING FROM BOOLEAN MAP
	    //videoDTO.initWeatherString();
	    //System.out.println(videoDTO.getCategoryid());
	    /***DEPRECATED******************/
	    
	    
	    
	    
	    //SET STATUSES
	    videoDTO.setLoadstatus(VideoLoadStatus.MANUAL);
	    videoDTO.setTransferstatus(VideoTransferStatus.NONE);
    	videoDTO.setIntegrationstatus(VideoIntegrationStatus.NONE);
	    videoDTO.setHealthstatus(VideoHealthStatus.HEALTHY);
	    videoDTO.setStatus(VideoPublishStatus.JUSTUPLOADED);
		if(selectedVideoTypesCommaString.trim().length()>0) {
		    	 String[] items = selectedVideoTypesCommaString.split(",");
		    	 videoDTO.setVideotypeids(selectedVideoTypesCommaString);
		    	 videoDTO.setMarkerimgid(items[0]);
		}
	    
	    //CREATE NEW VIDEO
	    videoDTO = privateVideoService.createNewVideo(videoDTO,new Date());

		   
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
	    
	    
	    
	    //PUBLISH
    	eventPublisher.publishEvent(new GenericEvent(new EventDTO(videoDTO, EventDTO.VID_JUSTUPLOADED)));

        
	    
	    return "redirect:/upload/upload-success";

    }
    
    
    
    
    
    
}