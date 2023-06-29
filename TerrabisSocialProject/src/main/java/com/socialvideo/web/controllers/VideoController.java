package com.socialvideo.web.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.Device;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.socialvideo.constant.CollectionTypes;
import com.socialvideo.constant.Constants;
import com.socialvideo.constant.GoogleConstants;
import com.socialvideo.constant.QueryConstants;
import com.socialvideo.constant.SysPropsConstants;
import com.socialvideo.constant.VideoPrivacyStatus;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.Activity;
import com.socialvideo.data.dto.Activity.ActivityEnum;
import com.socialvideo.data.dto.ActivityIdentity;
import com.socialvideo.data.dto.AdvancedFiltersDTO;
import com.socialvideo.data.dto.CollectionDTO;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.IDDescrDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PersonalizationDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.PublicVideoPointDTO;
import com.socialvideo.data.dto.QueryDTO;
import com.socialvideo.data.dto.SystemPropertyDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VideoCatalogQueryDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.maps.BoundsDTO;
import com.socialvideo.data.dto.maps.GeocodingDTO;
import com.socialvideo.data.dto.maps.PointDTO;
import com.socialvideo.data.services.CollectionService;
import com.socialvideo.data.services.ConnectEntityService;
import com.socialvideo.data.services.DatabaseService;
import com.socialvideo.data.services.EmailService;
import com.socialvideo.data.services.GoogleAuthService;
import com.socialvideo.data.services.IGoogleMapsService;
import com.socialvideo.data.services.IntegrationService;
import com.socialvideo.data.services.PublicVideoService;
import com.socialvideo.data.services.SystemPropertiesService;
import com.socialvideo.data.services.UserService;
import com.socialvideo.data.services.VideoClaimService;
import com.socialvideo.data.services.inter.IVideoService;
import com.socialvideo.utilities.WebUtility;
import com.socialvideo.web.exceptions.SecurityException;
import com.socialvideo.web.exceptions.VideoSecurityException;

import antlr.StringUtils;



@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/video")
@SessionAttributes({WebConstants.VIDEOQUERYFRM, WebConstants.GMAPRESULT, WebConstants.SECURITY_VIDEOCOUNTS, WebConstants.SECURITY_LOCKVIDEO, WebConstants.VIDCATALOGQUERY, WebConstants.PERSONALIZATION, WebConstants.MAPPAGINATOR,WebConstants.CATALOGPAGINATOR,WebConstants.MAPLISTPAGINATOR})
public class VideoController {

	
	
	
	
	 private static final Logger logger = Logger.getLogger(VideoController.class);

	 @Autowired
	 protected IVideoService privateVideoService;
	 

	 
	 @Autowired
	 protected ConnectEntityService cntService;
	 
	 @Autowired
	 protected PublicVideoService videoService;

	 @Autowired
	 protected ObjectMapper jacksonMapper;
	 
	 @Autowired
	 protected UserService userService;
	 
	 @Autowired
	 protected IGoogleMapsService gMapService;
	 
	 @Autowired
	 protected DatabaseService dbService;
	
	 @Autowired
	 protected CollectionService collService;

	 @Autowired
	 protected IntegrationService integrationService;

	 @Autowired
	 protected VideoClaimService claimService;

	 @Autowired
	 protected EmailService emailService;
	 
	 
	 @Autowired
	 protected SystemPropertiesService systemPropertiesService;
	 
	 
	 @Autowired
	 private ApplicationEventPublisher eventPublisher;
	 
	 @ModelAttribute
     public void navBarAttributes(Model model) {
		model.addAttribute(WebConstants.NAVBAR_SHOWUPLOADBUTTON, true);
		
     }
	 

	 
	 @ModelAttribute(WebConstants.VIDEOUPLOADFRM)
     public VideoDTO init_QUICKVIDEOUPLOADFRM() {
		 	VideoDTO videoDTO = new VideoDTO();
	        videoDTO.setWeatherMap(WebUtility.weatherMap());
	        videoDTO.setFootagedate(new Date());
	        videoDTO.setCategoryid(WebUtility.categoryIDS().get("aerial").toString());
	       return  videoDTO;
     }
	 	 

	 
 	
 	/******************************SECURITY CODE****************************************************************************************************/
 	 @ModelAttribute(WebConstants.SECURITY_VIDEOCOUNTS)
     public Integer init_SECURITYCOUNTS() {
		   return  0;
     }
 	 
 	
 	 @ModelAttribute(WebConstants.SECURITY_LOCKVIDEO)
     public Boolean init_SECURITYLOCKVIDEO() {
		   return  false;
     }
 	
 	
	 
	 @ModelAttribute(WebConstants.MAPPAGINATOR)
     public PaginatorDTO init_MAPPAGINATOR() {
		 	PaginatorDTO paginatorDTO = new PaginatorDTO(-1);
	       return  paginatorDTO;
     }

	 @ModelAttribute(WebConstants.MAPLISTPAGINATOR)
     public PaginatorDTO init_MAPLISTPAGINATOR() {
		 	PaginatorDTO paginatorDTO = new PaginatorDTO(-1);
	       return  paginatorDTO;
     }
	 
	 @ModelAttribute(WebConstants.CATALOGPAGINATOR)
     public PaginatorDTO init_CATALOGPAGINATOR() {
		 	PaginatorDTO paginatorDTO = new PaginatorDTO(-1);
	       return  paginatorDTO;
     }
	
	 
	 @ModelAttribute("videoTypeList")
     public List<IDDescrDTO> getVideoTypelist() {
		
		 
		 List<IDDescrDTO> resultList = dbService.getVideoTypelist();
		 resultList.add(0,new IDDescrDTO(new Integer(0),"any"));  
		 return resultList;
		 
	 
	 }
	 
	 
	 
	 
	 
	 
	 @ModelAttribute("videoShootingTypeList")
     public List<IDDescrDTO> getVideoShootingTypelist() {

		 
		 List<IDDescrDTO> resultList = dbService.getVideoShootingTypelist();
		 resultList.add(0,new IDDescrDTO(new Integer(0),"any"));  
		 return resultList;
     }
	 

		private void isFollowingUser(ModelAndView mav,Long activeUser, Long targetUser) {
			 //CHECK IF VIDEO USER IS A REGISTERED FOLLOWER  
		     if(activeUser!=null && targetUser!=null) {
		  	  Boolean isFollowing = cntService.isUserFollowingUser(activeUser,  targetUser) > 0 ? true : false;
		  	  mav.addObject(WebConstants.ISFOLLOWINGUSER, isFollowing);
		     } else {
		    	 mav.addObject(WebConstants.ISFOLLOWINGUSER, false);
		     }
			
			
		} 
	 
	 
	 
    public VideoController() {
    }
    

    
    
    
    //SEARCH
    @RequestMapping(value={"/search"}, method=RequestMethod.GET)
    public String search(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity, 
    		Model model, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, @ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO,
    		RedirectAttributes redirectAttrs) throws Exception {
        
    	
    	String queryText = queryDTO.getQuerytxt();
    	
    	//ONLY USED FOR GOOGLE ANALYTICS TRACKING AND NOTHING ELSE
    	String analyticsQueryTxt = queryText.replaceAll(" ", "+");
    	if(queryText.trim().length()==0)
    		analyticsQueryTxt = "_";
    	
    	Integer videosCountOntitleOrDescriptionOrTags=0;
    	Integer videosCountOnMapArea = 0;
    	String redirectTemplate = queryDTO.getQuerytype().equalsIgnoreCase(QueryConstants.QUERYTYPE_MAP) ?  "redirect:/video/map/normal/"+analyticsQueryTxt : "redirect:/video/map/list/"+analyticsQueryTxt;
    	
    	
    	//RESET ADVANCED CRITERIA EVERY TIME SEARCHING FROM TEXTFIELD
    	queryDTO.setAdvfilter(new AdvancedFiltersDTO());
    	
    	
    	
    	
    	if(queryText.trim().length()>0) {

    		
    			//IF SEARCH ON MAP SELECTED
    			if(queryDTO.getQuerytype().equalsIgnoreCase(QueryConstants.QUERYTYPE_MAP)) {
    		
    				
	    			//CHECK LOCATION IF MATCHES FOUND
			    	GeocodingDTO newgmapresult = gMapService.search(queryText);
			    	
			    	//IF LOCATION NOT FOUND DONT UPDATE SESSION WITH THE NEW BOUNDS, ONLY CHANGE GMAPRESULT STATUS TO EMPTY. THUS STILL SHOWING OLD LOCATION WITH MARKERS AND EVERYTHING
			    	if(!newgmapresult.isEmptyStatus()) {

		

			    		//videosCountOnMapArea = videoService.selectCountVideos(newgmapresult, new QueryDTO(false));
			    		model.addAttribute(WebConstants.GMAPRESULT, newgmapresult);
			    	} else {
		    			gmapresult.setEmptyStatus(true);
			    	}
			    	
		    		redirectTemplate = "redirect:/video/map/normal/"+analyticsQueryTxt;
    			} 
    			
    		if(queryDTO.getQuerytype().equalsIgnoreCase(QueryConstants.QUERYTYPE_titledescription)) {
    				//videosCountOntitleOrDescriptionOrTags =  videoService.selectCountVideos(null, new QueryDTO(queryText,true));
    				redirectTemplate = "redirect:/video/map/list/"+analyticsQueryTxt;
    			    		
    		}
    		
    		if(queryDTO.getQuerytype().equalsIgnoreCase(QueryConstants.QUERYTYPE_TAGS)) {
    				//videosCountOntitleOrDescriptionOrTags =  videoService.selectCountVideos(null, new QueryDTO(queryText,true));
    				redirectTemplate = "redirect:/video/map/list/"+analyticsQueryTxt;
    		}
 
		    		
		    		
		    	//ADD REDIRECT ATTRIBUTES
		    	//redirectAttrs.addFlashAttribute("videosCountOntitleOrDescriptionOrTags", videosCountOntitleOrDescriptionOrTags);
				//redirectAttrs.addFlashAttribute("videosCountOnMapArea", videosCountOnMapArea);
		    	
		}

    	
    	//UPDATE QUERY
    	model.addAttribute(WebConstants.VIDEOQUERYFRM, queryDTO);
    	

    	//LOG
    	Activity activity = new Activity(ActivityEnum.SEARCH, identity,null,null,queryText,gmapresult.getLocation(),null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
        return redirectTemplate;
    }
    
    

    /*@RequestMapping(value={"/map/google-static-image"}, method=RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[]  mapStaticImage(@ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO,  @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, @RequestParam(value = "zoom", required=true) String zoom,@RequestParam(value = "center", required=true) String center, @RequestParam(value = "northeast", required=true) String northeast, @RequestParam(value = "southwest", required=true) String southwest) throws Exception {    
    */

    
    
    
    
    
    
    @RequestMapping(value={"/map/static/snapshot"}, method=RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[]  mapStaticsnaphsot(@ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO,  @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult) throws Exception {    
    


            Integer videosCount = videoService.selectCountCatalogVideosViewPort(gmapresult,queryDTO);
            List<PublicVideoPointDTO> pointsList = videoService.selectCatalogVideoPointsOfViewport(gmapresult,queryDTO,0,20);
            
            //FETCH ALL VIDEOS VIDEOS BASED ON QUERY CRITERIA AND INITIALISE PAGINATOR AND TAG LIST
           String imageURI = WebUtility.buildGoogleStaticImageURI(gmapresult,pointsList);
           
           
           String shareDescription = WebUtility.buildTerrabisShareDesc(videosCount, gmapresult, queryDTO);
        	
           
           byte[] image = integrationService.createGoogleMapStaticImg(imageURI,  shareDescription, "");
           return image;
    };
    

    
    
    
    
  
    //MAP VIEW
    @RequestMapping(value={"/map/normal","/map/normal/{query}"}, method=RequestMethod.GET)
    public ModelAndView baseMapView(Device device,@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity, Model model, 
    		@ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult,
    		@RequestParam(value = "grid", required=false) String grid,
    		@RequestParam(value = "videouuid", required=false) String videouuid,
    		@RequestParam(value = "zoom", required=false) String zoom,
    		@RequestParam(value = "center", required=false) String center, 
    		@RequestParam(value = "northeast", required=false) String northeast, 
    		@RequestParam(value = "southwest", required=false) String southwest,
    		@RequestParam(value = "useruuid", required=false) String useruuid,
    		@RequestParam(value = "mode", required=false) String mode,
    		@PathVariable String query,
    		@ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) throws Exception {    
    	
	       
    		//SET MODEL VIEW
	    	ModelAndView mav = null;
	    	if(mode!=null)
	    		mav = new ModelAndView("video-map-normal-view-"+mode);
	    	else
	    		mav = new ModelAndView("video-map-normal-view-"+ (device.isMobile() || device.isTablet()  ? "mobile" : "desktop"));
    	

            
        	
  
        	//ADD VIDEO TYPE COLORS FOR PINS AND CATEGORY FILTERS AND COLORS FOR CLUSTERS
        	SystemPropertyDTO videoTypeColors =  systemPropertiesService.selectSystemPropertyByKey(SysPropsConstants.VIDEOTYPE_COLORS);
        
        	 mav.addObject(WebConstants.VIDEOTYPECOLORSLIST, videoTypeColors);
        	 mav.addObject(WebConstants.VIDEOTYPECOLORSMAP, WebUtility.videoTypeColorsStringToHashMap(videoTypeColors.getValue()));
        	 mav.addObject(WebConstants.PINSCLUSTERCOLORSLIST, systemPropertiesService.selectSystemPropertyByKey(SysPropsConstants.MAPCLUSTER_COLORS));	

        	 
        	//*******************************MANAGE REQUEST PARAMETERS*******************************************//*
        	//GRID LAYOUT CHANGES
            if(grid!=null) {
            	persDTO.getPreference().put(PersonalizationDTO.KEY_GRIDSTYLE+"video-map-normal-view-"+ (device.isMobile() || device.isTablet()  ? "mobile" : "desktop"), grid);
            	//persDTO.getPreference().put(PersonalizationDTO.KEY_GRIDSTYLE+"video-map-normal-view-mobile", grid);
            	
                //UPDATE PERSONALIZATION
                mav.addObject(WebConstants.PERSONALIZATION, persDTO);	
            	
            }
            
             //SINGLE VIDEO SUBMITTED
            if(videouuid!=null) {
            	//FETCH VIDEO
            	VideoDTO privateVideoDTO = privateVideoService.findVideoByUUID(videouuid);
            	model.addAttribute(WebConstants.RADIUSDTO, new PointDTO(privateVideoDTO.getLatitude().toString(), privateVideoDTO.getLongitude().toString()));
        		
        		
            	
            	
            	if(privateVideoDTO!=null) 
            		model.addAttribute(WebConstants.VIDEODTO, videoService.findPublicVideoById(privateVideoDTO.getId()));   

            	//RESET QUERY
            	queryDTO = new QueryDTO();
            	
            	//SEARCH BASED ON VIDEO LATITUDE AND LONGITUDE
            	String locationquerytxt = privateVideoDTO.getLatitude().toString()+" "+privateVideoDTO.getLongitude().toString();
            	
            	gmapresult = gMapService.search(locationquerytxt);
            	gmapresult.setEmptyStatus(false);
            	gmapresult.setZoomlevel(10);
            	model.addAttribute(WebConstants.GMAPRESULT, gmapresult);
            	model.addAttribute(WebConstants.VIDEOQUERYFRM, queryDTO);
            }
            
            
            //CENTER LOCATION SUBMITTED WITH BOUNDS AND ZOOM LEVEL FOR FACEBOOK SHARE PUPROSES
            if(center!=null) {
            	//RESET QUERY
            	queryDTO = new QueryDTO();
         		BoundsDTO boundsDTO = new BoundsDTO(new PointDTO(northeast), new PointDTO(southwest));
         		PointDTO centerDTO = new PointDTO(center);
            	gmapresult = new GeocodingDTO("", centerDTO, boundsDTO, boundsDTO);

            	
/*            	
            	GeocodingDTO reverseGeoDTO = gMapService.reverseGeocodeRequest(centerDTO.getLat(), centerDTO.getLng());
        		gmapresult.setGenericAddress(reverseGeoDTO.getGenericAddress());
            	gmapresult.setZoomlevel(zoom!=null ? Integer.parseInt(zoom) : 10);
            	*/
            	
            	gmapresult = gMapService.search(center);
            	gmapresult.setZoomlevel(zoom!=null ? Integer.parseInt(zoom) : 10);
            	
            	model.addAttribute(WebConstants.GMAPRESULT, gmapresult);
            	model.addAttribute(WebConstants.VIDEOQUERYFRM, queryDTO);
            	
            	
            	
            	model.addAttribute("terrabisMapURI",WebUtility.buildTerrabisShareMapURI(gmapresult));
            	model.addAttribute("terrabisMapImageURI",WebUtility.buildTerrabisShareMapImageURI(gmapresult));

            	String locationDesc = "Explore "+(queryDTO.getQuerytxt().trim().length()>0 ? queryDTO.getQuerytxt() : gmapresult.getGenericAddress());
            	model.addAttribute("terrabisMapDesc",locationDesc);
                
            	
            }
            
        	
            
            
            
            //SINGLE USER SUBMITTED
           if(useruuid!=null) {
           	//FETCH USER
        	UserDTO userDTO = userService.findUserByUUID(useruuid);
           	
        	if(userDTO!=null)
           		model.addAttribute(WebConstants.USERDTO, userDTO);   

           		//RESET QUERY
           		queryDTO = new QueryDTO();
           		queryDTO.getAdvfilter().setUserid(userDTO.getId().toString());
           		model.addAttribute(WebConstants.VIDEOQUERYFRM, queryDTO);
           }
           
            
            

		  
            //SECURITY OF VIEWPORT
    		mav.addObject("minzoomlevel",  systemPropertiesService.selectSystemPropertyByKey(SysPropsConstants.SECURITY_MAP_MIN_ZOOMOUT_LEVEL).getValue());
    		mav.addObject("viewportpinssize",  systemPropertiesService.selectSystemPropertyByKey(SysPropsConstants.SECURITY_VIEWPORT_FETCHPINS_SIZE).getValue());
    		
        	//ADD DEVICE
        	mav.addObject("device", device);
        	
        	
        	//ADD SYSTEM PROPERTIES FOR MAP CLUSTERER
        	mav.addObject("clusterSize",systemPropertiesService.selectSystemPropertyByKey(SysPropsConstants.MAPCLUSTER_CLUSTERSIZE).getValue());
        	mav.addObject("clusterZoomLevel",systemPropertiesService.selectSystemPropertyByKey(SysPropsConstants.MAPCLUSTER_ZOOMLEVEL).getValue());
        	
        	
      return mav;
    }
    
    
    
/*
    //MAP VIEW
    @RequestMapping(value={"/oldmapview"}, method=RequestMethod.GET)
    public ModelAndView baseMapViewBackup(Device device,@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity, Model model, 
    		@ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult,
    		@RequestParam(value = "grid", required=false) String grid,
    		@RequestParam(value = "videoid", required=false) Long videoid,
    		@RequestParam(value = "useruuid", required=false) String useruuid,
    		@PathVariable String query,
    		@ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) throws Exception {    

    	
        //TURN OFF QUERY AS titledescription OR TAGS
        queryDTO.setQuerytype(QueryConstants.QUERYTYPE_MAP);

    	//SET MODEL VIEW
        ModelAndView mav = new ModelAndView("video-map-normal-view");
        
        //TURN OFF SEARCH HEADER
        mav.addObject(WebConstants.NAVBAR_SHOWSEARCHTXT, false);
        
        
        
        //LOG
        Activity activity = new Activity(ActivityEnum.MAPVIEW, identity);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
    			
    	
    	
    	*//*******************************MANAGE REQUEST PARAMETERS*******************************************//*
    	//GRID LAYOUT CHANGES
        if(grid!=null) {
        	persDTO.getPreference().put(PersonalizationDTO.KEY_GRIDSTYLE+"video-map-normal-view", grid);
        	
            //UPDATE PERSONALIZATION
            mav.addObject(WebConstants.PERSONALIZATION, persDTO);	
        	
        }
        
         //SINGLE VIDEO SUBMITTED
        if(videoid!=null) {
        	//FETCH VIDEO
        	VideoDTO video = null;//videoService.findVideoById(videoid);
        	if(video!=null)
        		model.addAttribute(WebConstants.VIDEODTO, video);   

        	//RESET QUERY
        	queryDTO = new QueryDTO();
        	
        	//SEARCH BASED ON VIDEO LATITUDE AND LONGITUDE
        	String locationquerytxt = video.getLatitude().toString()+" "+video.getLongitude().toString();
        	gmapresult = gMapService.search(locationquerytxt);
        	gmapresult.setEmptyStatus(false);
        	//gmapresult.setZoomlevel(10);
        	model.addAttribute(WebConstants.GMAPRESULT, gmapresult);
        	model.addAttribute(WebConstants.VIDEOQUERYFRM, queryDTO);
        }
        
    	*//*******************************MANGE REQUEST PARAMETERS*******************************************//*
        
        //FILTER BY USER
       if(useruuid!=null) {
       	//FETCH USER
    	UserDTO userDTO = userService.findUserByUUID(useruuid);
       	
    	if(userDTO!=null)
       		model.addAttribute(WebConstants.USERDTO, userDTO);   

       	//RESET QUERY
       	queryDTO = new QueryDTO();
    	queryDTO.getAdvfilter().setUserid(userDTO.getId().toString());
    	model.addAttribute(WebConstants.VIDEOQUERYFRM, queryDTO);
       	
       }
       

        
        //FIND FIRST 100 VIDEO VIDEOS
        List<PublicVideoDTO> videoList   = videoService.select(gmapresult, queryDTO, new RowBounds(0,100));
		model.addAttribute(WebConstants.VIEWPORTMARKERS, videoList); 
      //  mav.addObject("device", device);


		
        
      return mav;
    }*/
    
    
    //SEARCH RESULTS LIST VIEW
    @RequestMapping(value={"/map/list/","/map/list/{query}"}, method=RequestMethod.GET)
    public ModelAndView baseListView(Device device,@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity, Model model,  
    		@RequestParam(value = "page", required=false) Integer page,
    		@RequestParam(value = "grid", required=false) String grid,
    		@PathVariable String query,
    		@ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, 
    		@ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO,@ModelAttribute(WebConstants.MAPLISTPAGINATOR) PaginatorDTO paginator) throws Exception {    

    	//SET MODEL VIEW
        ModelAndView mav = new ModelAndView("video-map-list-view");
        
        
    	//GRID LAYOUT CHANGES
        if(grid!=null) {
        	persDTO.getPreference().put(PersonalizationDTO.KEY_GRIDSTYLE+"video-map-list-view", grid);
            //UPDATE PERSONALIZATION
            mav.addObject(WebConstants.PERSONALIZATION, persDTO);	
        	
        }
        
        
        
        //TURN on SEARCH HEADER
        mav.addObject(WebConstants.NAVBAR_SHOWSEARCHTXT, true);
        
        
        Integer videosCount = 0;

        
        videosCount =  videoService.selectCountCatalogVideosViewPort(null,queryDTO);

        
        /*******************INITIALISE PAGINATOR*****************************************************/
        if(page==null) {
        	paginator = new PaginatorDTO(videosCount);
        	RowBounds rowBounds = paginator.firstPage();
        	mav.addObject(WebConstants.MAPLISTPAGINATOR,paginator);
        } else {
        	paginator.goToPage(page);
        	mav.addObject(WebConstants.MAPLISTPAGINATOR,paginator);
        	
        }
        /*******************INITIALISE PAGINATOR*****************************************************/
        
        
        
        /*******************FETCH VIDEOS *****************************************************/
        List<PublicVideoDTO> videoList   = videoService.selectCatalogVideosViewPort(null, queryDTO, paginator);
        //List videoListPartions = WebUtility.partitionVideoListPublic(device,videoList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"video-map-list-view").toString());
        
        mav.addObject("videoListDTO", videoList);
        mav.addObject("videosCount", videosCount);
      //  mav.addObject("device", device);

        /*******************FETCH VIDEOS *****************************************************/
        

        
        
        //LOG
        Activity activity = new Activity(ActivityEnum.MAPLISTVIEW, identity);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
        
        
        
        
      return mav;
    }
    
    
    //BASE SEARCH CHOOSE TO GO WITH MAP OR TITLE SEARCH
    @RequestMapping(value={"/map/choose"}, method=RequestMethod.GET)
    public ModelAndView baseChooseView(@ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO) {
    	ModelAndView mav = new ModelAndView("video-map-choose-view");
    	return mav;
    }
    
    
    
    public enum RelatedVideosListType {
        NEARBY, TAGMATCHED, LATEST 
    }
    
    
    
    
    
    
    
    //SINGLE-VIDEO-PAGE
    @RequestMapping(value={"/{uuid}"}, method=RequestMethod.GET)
    public ModelAndView videoOneView(@AuthenticationPrincipal CurrentUserDTO activeUser, Device device, @PathVariable String uuid, 	@RequestParam(value = "mode", required=false) String mode, @ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity, @ModelAttribute(WebConstants.SECURITY_VIDEOCOUNTS) Integer securityVideoCounts, @ModelAttribute(WebConstants.SECURITY_LOCKVIDEO) Boolean securityLockVideo) throws Exception {
        
		
    	
    	ModelAndView mav = null;
    	if(mode!=null)
    		mav = new ModelAndView("video-one-view-"+mode);
    	else
    		mav = new ModelAndView("video-one-view-"+ (device.isMobile() || device.isTablet()  ? "mobile" : "desktop"));
    	
    	if(activeUser!=null) {
    		mav.addObject(WebConstants.USERDTO,userService.selectUserWithAvatar(activeUser.getUser().getId()));
    		
    		
    		
    		
    		
    	}
    		
        
    	

    	//IF VIDEO LOCKED REDIRECT TO ROBOT-CHECK
    	if(securityLockVideo) {
    		//throw new VideoSecurityException("redirect:/robot-check/index?uuid="+uuid);
    		mav = new ModelAndView("redirect:/robot-check/index?uuid="+uuid);
    		return mav;
    	}

    	
    	//INCREMENT SECURITY VIDEO COUNTS 
    	securityVideoCounts =  securityVideoCounts +1;
    	mav.addObject(WebConstants.SECURITY_VIDEOCOUNTS,securityVideoCounts);
    	
    	
    	//IF PLAY LIMIT EXCEED LOCK VIDEO AND REDIRECT TO ROBOT CHECK
    	Integer triggerSecurityPlayLimit = Integer.parseInt(systemPropertiesService.selectSystemPropertyByKey(SysPropsConstants.SECURITY_MAX_VIDEOPLAY).getValue());
    	//Integer triggerSecurityPlayLimit = 2;
    	if(securityVideoCounts>triggerSecurityPlayLimit) {
    		//throw new VideoSecurityException("redirect:/robot-check/index?uuid="+uuid);
    		mav = new ModelAndView("redirect:/robot-check/index?uuid="+uuid);
    		mav.addObject(WebConstants.SECURITY_LOCKVIDEO, new Boolean(true));
    		return mav;
    	}
     	/******************************SECURITY CODE****************************************************************************************************/
    	   
    	
    		
    	
    	//SELECT SELECTED VIDEO
        VideoDTO videoDTO = privateVideoService.findVideoByUUID(uuid);
        
        
        
        
       if (videoDTO==null) {
    	   	try {
    	   		
    	   		videoDTO = privateVideoService.findVideoById(Long.parseLong(uuid));
    	   	} catch (Exception e){}
        }
          	
       
       
       //IS FOLLOWING USER
       isFollowingUser(mav, activeUser!=null && activeUser.getUser()!=null ? activeUser.getId() : null,videoDTO.getUser().getId());
       
       

        //TURN ON SEARCH HEADER
        mav.addObject(WebConstants.NAVBAR_SHOWSEARCHTXT, true);
        
        
        //SET RELATED LIST TYPE TO BE RETURNED
        String relatedListType = "";
        
        
        /*************************************FETCH RELATED VIDEOS (TO BE REPLACE BY AJAX)*********************************************/
        List<PublicVideoDTO> relatedVideoList = null; 
		
		//CREATE AN EMPTY PAGINATOR FOR 20 RESULTS AND INITIALIZE
		PaginatorDTO paginator  = new PaginatorDTO(100, 10);
		paginator.firstPage();
		
     	 //FETCH NEARBY VIDEOS AND IF NOT FOUND FETCH TAG MATCHED VIDEOS
		relatedVideoList = videoService.selectNearyByVideos(videoDTO.getId(),videoDTO.getLatitude().toString(), videoDTO.getLongitude().toString(), QueryConstants.NEARBYRANGE, paginator);
     	 if((relatedVideoList!=null) && (relatedVideoList.size()>1))
     		relatedListType = RelatedVideosListType.NEARBY.toString();
     	 else {//FETCH TAG MATCHED VIDEOS
     		relatedVideoList = videoService.selectRelatedTagVideos(videoDTO.getTags(),paginator);
     	
     	 	 if((relatedVideoList!=null) && (relatedVideoList.size()>1))
         		relatedListType =  RelatedVideosListType.TAGMATCHED.toString();
         	else {  //IF STILL NO TAGS MATCHED FETCH LATEST VIDEOS
                VideoCatalogQueryDTO queryDTO = new VideoCatalogQueryDTO(null, QueryConstants.FilterLatest, null,null);	 
                relatedVideoList = videoService.selectCatalogVideos(queryDTO, paginator);
         		relatedListType = RelatedVideosListType.LATEST.toString();
     	 	}      	 
     	 
     	 
     	 }
     	final Long videoIdParam = videoDTO.getId();
     	List<String> relatedVideoIdsList = relatedVideoList.stream().filter(x ->(!x.getId().equals(videoIdParam))).map(x-> x.getId().toString()).collect(Collectors.toList());
     	List<PublicVideoPointDTO> relatedPointsList = videoService.findVideoPointsOfVideoList(relatedVideoIdsList);

     	
	    //ADD RELATED VIDEOS LIST
	   	mav.addObject("relatedVideoListDTO", relatedVideoList);
	   	mav.addObject("relatedPointListDTO", relatedPointsList);
	   	mav.addObject("relatedListType", relatedListType);
     	
 

	   	//ADD PUBLIC VIDEO DTO INFO AND LOCATION
     	 mav.addObject(WebConstants.VIDEODTO, videoService.findPublicVideoWithReviewsById(videoDTO.getId()));
     	 //SECURITY WHOLE
     	 mav.addObject("videoDTOLocation",  new PointDTO(videoDTO.getLatitude().toString(),videoDTO.getLongitude().toString()));
     
     	 
     	 //ADD DEVICE
     	 mav.addObject("device", device);
        
     	
     	  
     	  
     	  
          
    	//LOG
    	Activity activity = new Activity(ActivityEnum.VIDEOSINGLEPAGEVIEW, identity,null,videoDTO.getId(),null,null,null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
        

        
        return mav;
    }
    
    
    
    
    
    //VID PLAYER
    @RequestMapping(value={"/play"}, method=RequestMethod.GET)
    public ModelAndView player(@RequestParam("uri") String videoURI, @RequestParam("style") String style, @RequestParam(value="start",required=false) String start) {
    	ModelAndView mav = new ModelAndView("video-player-view");
    	mav.addObject("videoURI",videoURI);
    	mav.addObject("style",style);
    	
    	if(start==null)
    		start = "false";
    	
    	mav.addObject("start",start);
    	
    	return mav;
    }
    
    
    //TRENDING VIDEOS WITH FILTER
    @RequestMapping(value={"/trending-catalog/filter:{filter}/datefilter:{datefilter}"}, method=RequestMethod.GET)
    public ModelAndView trendingVideosFilterView(Device device, @ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@RequestParam(value = "page", required=false) Integer page,@RequestParam(value = "grid", required=false) String grid, @PathVariable String filter, @PathVariable String datefilter, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO,@ModelAttribute(WebConstants.CATALOGPAGINATOR) PaginatorDTO paginator) throws Exception  {
        ModelAndView mav = new ModelAndView("trending-catalog-view");
        mav.addObject(WebConstants.NAVBAR_SHOWSEARCHTXT, true);

        
        if(grid!=null) {
        	persDTO.getPreference().put(PersonalizationDTO.KEY_GRIDSTYLE+"trending-catalog-view", grid);
            mav.addObject(WebConstants.PERSONALIZATION, persDTO);

        }
        	
        
        

        //CREATE CATALOG FILTER 
        VideoCatalogQueryDTO queryDTO = new VideoCatalogQueryDTO(null, filter, null,datefilter);
        mav.addObject(WebConstants.VIDCATALOGQUERY,queryDTO);

        //INTIALIZE PAGINATOR
        if(page==null) {
	        Integer videosCount = videoService.selectCountTrendingCatalogVideos(queryDTO);
	        paginator = new PaginatorDTO(videosCount);
	        RowBounds rowBounds = paginator.firstPage();
	        mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
        } else {
        	  
        	 paginator.goToPage(page);
        	 mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
        }


        List<PublicVideoDTO> resultList = videoService.selectTrendingCatalogVideos(queryDTO,paginator);
        //List videoListPartions = WebUtility.partitionVideoListPublic(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"video-catalog-view").toString());
        
        mav.addObject("videoListDTO", resultList);
      ////  mav.addObject("device", device);


        
        //LOG
  		Activity activity = new Activity(ActivityEnum.VIDEOCATALOGVIEW, identity);
        logger.info("[TERRABIS]-"+jacksonMapper.writeValueAsString(activity));
        
        return mav;
    }    
    
    
    
    
    //CATALOG VIDEOS WITH FILTER
    @RequestMapping(value={"/video-catalog/filter:{filter}"}, method=RequestMethod.GET)
    public ModelAndView videosCatalogFilterView(Device device, @ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@RequestParam(value = "page", required=false) Integer page,@RequestParam(value = "grid", required=false) String grid, @PathVariable String filter, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO,@ModelAttribute(WebConstants.CATALOGPAGINATOR) PaginatorDTO paginator) throws Exception  {
        ModelAndView mav = new ModelAndView("video-catalog-view");
        mav.addObject(WebConstants.NAVBAR_SHOWSEARCHTXT, true);

        
        if(grid!=null) {
        	persDTO.getPreference().put(PersonalizationDTO.KEY_GRIDSTYLE+"video-catalog-view", grid);
            mav.addObject(WebConstants.PERSONALIZATION, persDTO);

        }
        	
        
        

        //CREATE CATALOG FILTER 
        VideoCatalogQueryDTO queryDTO = new VideoCatalogQueryDTO(null, filter, null,null);
        mav.addObject(WebConstants.VIDCATALOGQUERY,queryDTO);
        
        
        
        
        //INTIALIZE PAGINATOR
        if(page==null) {
	        Integer videosCount = videoService.selectCountCatalogVideos(queryDTO);
	        paginator = new PaginatorDTO(videosCount);
	        RowBounds rowBounds = paginator.firstPage();
	        mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
        } else {
        	  
        	 paginator.goToPage(page);
        	 mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
        }


        List<PublicVideoDTO> resultList = videoService.selectCatalogVideos(queryDTO,paginator);
        //List videoListPartions = WebUtility.partitionVideoListPublic(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"video-catalog-view").toString());
        
        mav.addObject("videoListDTO", resultList);
      ////  mav.addObject("device", device);


        
        //LOG
  		Activity activity = new Activity(ActivityEnum.VIDEOCATALOGVIEW, identity);
        logger.info("[TERRABIS]-"+jacksonMapper.writeValueAsString(activity));
        
        return mav;
    }    
    
    
    //CATALOG VIDEOS WITH SUBFILTER
    @RequestMapping(value={"/video-catalog/filter:{filter}/subfilter:{subfilter}"}, method=RequestMethod.GET)
    public ModelAndView videosCatalogFilterSubfilterView(Device device,@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@RequestParam(value = "page", required=false) Integer page,@RequestParam(value = "grid", required=false) String grid, @PathVariable String filter, @PathVariable String subfilter,  @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO,@ModelAttribute(WebConstants.CATALOGPAGINATOR) PaginatorDTO paginator) throws Exception {
        ModelAndView mav = new ModelAndView("video-catalog-view");
        mav.addObject(WebConstants.NAVBAR_SHOWSEARCHTXT, true);


        //CREATE CATALOG FILTER 
        VideoCatalogQueryDTO queryDTO = new VideoCatalogQueryDTO(null, filter, subfilter,null);
        mav.addObject(WebConstants.VIDCATALOGQUERY,queryDTO);
        

        //INTIALIZE PAGINATOR
        if(page==null) {
	        Integer videosCount = videoService.selectCountCatalogVideos(queryDTO);
	        paginator = new PaginatorDTO(videosCount);
	        RowBounds rowBounds = paginator.firstPage();
	        mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
        } else {
        	paginator.goToPage(page);
        	 mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
        }

        List<PublicVideoDTO> resultList = videoService.selectCatalogVideos(queryDTO,paginator);
        
        if(grid!=null) {
        	persDTO.getPreference().put(PersonalizationDTO.KEY_GRIDSTYLE+"video-catalog-view", grid);
            mav.addObject(WebConstants.PERSONALIZATION, persDTO);

        }
        	
        //List videoListPartions = WebUtility.partitionVideoListPublic(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"video-catalog-view").toString());
        
        mav.addObject("videoListDTO", resultList);
      //  mav.addObject("device", device);


        //LOG
  		Activity activity = new Activity(ActivityEnum.VIDEOCATALOGVIEW, identity);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
        
        return mav;
    }
    
    
    //VIDEO PATHS
    @RequestMapping(value={"/playlist-catalog/filter:{filter}"}, method=RequestMethod.GET)
    public ModelAndView playlistCatalogFilterView(Device device, @ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@RequestParam(value = "page", required=false) Integer page, @RequestParam(value = "grid", required=false) String grid, @PathVariable String filter, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO,@ModelAttribute(WebConstants.CATALOGPAGINATOR) PaginatorDTO paginator) throws Exception {
        ModelAndView mav = new ModelAndView("playlist-catalog-view");
        mav.addObject(WebConstants.NAVBAR_SHOWSEARCHTXT, true);


        //ADD FILTER TO PAGE
        mav.addObject("filter",filter); 
      
        //SET PRIVACY
        Integer privacy = VideoPrivacyStatus.PUBLIC;

        //INTIALIZE PAGINATOR
        if(page==null) {
        	Integer collectionsCount = collService.selectCountCollections(CollectionTypes.PLAYLISTS);
        	paginator = new PaginatorDTO(collectionsCount);
        	RowBounds rowBounds = paginator.firstPage();
        	mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
        } else {
        	paginator.goToPage(page);
       	 	mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
       }
        
        
        List<CollectionDTO> resultList = collService.selectPaginatedCollections(null,CollectionTypes.PLAYLISTS,paginator).stream().filter(x->x.getVideos().isEmpty()==false).collect(Collectors.toList());
    	//List collectionListPartions = WebUtility.partitionCollectionList(resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"usermng-video-organizer-playlists").toString());
    	mav.addObject("collectionListDTO", resultList);	
      //  mav.addObject("device", device);


    	//LOG
  		Activity activity = new Activity(ActivityEnum.PATHCATALOGVIEW, identity);
        logger.info("[TERRABIS]-"+jacksonMapper.writeValueAsString(activity));
    	
    	return mav;
    }
    
    
    //ALBUMS
    @RequestMapping(value={"/album-catalog/filter:{filter}"}, method=RequestMethod.GET)
    public ModelAndView albumsCatalogFilterView(Device device, @ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@RequestParam(value = "page", required=false) Integer page, @RequestParam(value = "grid", required=false) String grid, @PathVariable String filter, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO,@ModelAttribute(WebConstants.CATALOGPAGINATOR) PaginatorDTO paginator) throws Exception {
        ModelAndView mav = new ModelAndView("album-catalog-view");
        mav.addObject(WebConstants.NAVBAR_SHOWSEARCHTXT, true);


        //ADD FILTER TO PAGE
        mav.addObject("filter",filter); 
      
        //SET PRIVACY
        Integer privacy = VideoPrivacyStatus.PUBLIC;

        //INTIALIZE PAGINATOR
        if(page==null) {
        	Integer collectionsCount = collService.selectCountCollections(CollectionTypes.ALBUMS);
        	paginator = new PaginatorDTO(collectionsCount);
        	RowBounds rowBounds = paginator.firstPage();
        	mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
        } else {
        	paginator.goToPage(page);
       	 	mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
       }
        
        
        List<CollectionDTO> resultList = collService.selectPaginatedCollections(null,CollectionTypes.ALBUMS,paginator).stream().filter(x->x.getVideos().isEmpty()==false).collect(Collectors.toList());
    	//List collectionListPartions = WebUtility.partitionCollectionList(resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"usermng-video-organizer-playlists").toString());
    	mav.addObject("collectionListDTO", resultList);	
      //  mav.addObject("device", device);


    	//LOG
  		Activity activity = new Activity(ActivityEnum.ALBUMCATALOGVIEW, identity);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
    	
    	return mav;
    }

    

    
    
    

    
   
    
    
    
    
    
        
}