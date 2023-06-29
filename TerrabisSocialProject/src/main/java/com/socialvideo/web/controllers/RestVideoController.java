package com.socialvideo.web.controllers;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialvideo.constant.Constants;
import com.socialvideo.constant.NotificationStatus;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.Activity;
import com.socialvideo.data.dto.Activity.ActivityEnum;
import com.socialvideo.data.dto.ActivityIdentity;
import com.socialvideo.data.dto.AdvancedFiltersDTO;
import com.socialvideo.data.dto.AjaxPagingDTO;
import com.socialvideo.data.dto.AjaxQueryDTO;
import com.socialvideo.data.dto.AjaxResultDTO;
import com.socialvideo.data.dto.AjaxUserDTO;
import com.socialvideo.data.dto.AjaxVideoDTO;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.FusionTableResultDTO;
import com.socialvideo.data.dto.NotificationDTO;
import com.socialvideo.data.dto.NotificationEnum;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PersonalizationDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.PublicVideoPointDTO;
import com.socialvideo.data.dto.QueryDTO;
import com.socialvideo.data.dto.VideoCatalogQueryDTO;
import com.socialvideo.data.dto.maps.BoundsDTO;
import com.socialvideo.data.dto.maps.GeocodingDTO;
import com.socialvideo.data.dto.maps.PointDTO;
import com.socialvideo.data.services.CollectionService;
import com.socialvideo.data.services.ConnectEntityService;
import com.socialvideo.data.services.DatabaseService;
import com.socialvideo.data.services.FusionTablesService;
import com.socialvideo.data.services.IGoogleMapsService;
import com.socialvideo.data.services.NotificationsService;
import com.socialvideo.data.services.PublicVideoService;
import com.socialvideo.data.services.UserService;
import com.socialvideo.data.services.VideoReviewService;
import com.socialvideo.data.services.inter.IVideoService;
import com.socialvideo.utilities.WebUtility;;

@EnableAutoConfiguration
@RestController
@RequestMapping(value = "/rest")
@SessionAttributes({WebConstants.VIDEOQUERYFRM,WebConstants.VIDCATALOGQUERY,WebConstants.ADMINQUERYFRM, WebConstants.GMAPRESULT,WebConstants.PERSONALIZATION, WebConstants.MAPPAGINATOR, WebConstants.CATALOGPAGINATOR,WebConstants.MAPLISTPAGINATOR})
public class RestVideoController {

	 private static final Logger logger = Logger.getLogger(RestVideoController.class);
	
	 @Autowired
	 private ApplicationEventPublisher eventPublisher;
	 
	 @Autowired
	 protected IVideoService privateVideoService;
	 
	 
	 @Autowired
	 protected NotificationsService nottsService;
	 
	 @Autowired
	 protected FusionTablesService fusionService;
	 
	 
	 @Autowired
	 protected PublicVideoService videoService;
	 

	 @Autowired
	 protected VideoReviewService reviewService;
	 
	 @Autowired
	 protected WebUtility webUtility;
	 
	 @Autowired
	 protected ConnectEntityService cntService;
	 
	 @Autowired
	 protected ObjectMapper jacksonMapper;
	 
	 @Autowired
	 protected CollectionService collService;
	 
	 @Autowired
	 protected DatabaseService dbService;
	 
	 @Autowired
	 protected IGoogleMapsService gMapService;
	
	
	 
    public RestVideoController() {
    }


    
    //FOLLOW USER
    @RequestMapping(value="/user/follow", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> followUser(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@AuthenticationPrincipal CurrentUserDTO activeUser, Model model, @RequestBody  AjaxUserDTO ajaxDTO) throws Exception {

    	Long userid = (activeUser!=null && activeUser.getUser()!=null) ? activeUser.getUser().getId() : null;

    	if(userid!=null) {

    		
			Long userfollowedid = Long.parseLong(ajaxDTO.getUserid());
			if(cntService.isUserFollowingUser(userid, userfollowedid)==0) {
				cntService.userFollowUser(userid, userfollowedid);
		    	nottsService.notify(NotificationEnum.NEWFOLLOWER, userfollowedid, userid, null, null,null,null);
				
				
			}

			
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK), HttpStatus.OK);
	    return response;
		}
		else { 
			
			return new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
		}
    }
    
    
    //UNFOLLOW USER
    @RequestMapping(value="/user/unfollow", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> unfollowUser(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@AuthenticationPrincipal CurrentUserDTO activeUser, Model model, @RequestBody  AjaxUserDTO ajaxDTO) throws Exception {

    	Long userid = (activeUser!=null && activeUser.getUser()!=null) ? activeUser.getUser().getId() : null;
    	Long userfollowedid = Long.parseLong(ajaxDTO.getUserid());
    	
    	if(userid!=null) {

    		if(cntService.isUserFollowingUser(userid, userfollowedid)>0) {
    			cntService.userUnFollowUser(userid, userfollowedid);
    		}
			
	    
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK), HttpStatus.OK);
	    return response;
		}
		else { 
			
			return new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
		}
    }
    
    /************************************************************* USER NOTIFICATIONS  *********************************************************/ 
   	@RequestMapping(value="/notifications/list", method = RequestMethod.POST)
   	public ResponseEntity<AjaxResultDTO> fetchNotifications_ofUser(@AuthenticationPrincipal CurrentUserDTO activeUser,Device device,Model model,  @RequestBody AjaxQueryDTO ajaxQueryDTO) {
   		
   		Long userid = (activeUser!=null && activeUser.getUser()!=null) ? activeUser.getUser().getId() : null;
   		
   		if(userid!=null) {
   			Integer total = nottsService.selectCountNotificationsOfUser(userid, NotificationStatus.UNREADED);
   			List<NotificationDTO> notList =nottsService.selectNotificationsOfUser(userid, NotificationStatus.UNREADED, 10);
   			
   			
   		  ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO(new NotificationDTO(), notList,total, HttpStatus.OK), HttpStatus.OK);
  	    return response;
  		

   		}
   		
   		else { 
			
   			return new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
   		}
   			
   		
   		
   		
   		
   		
   	}
   		 

   	
   	

    //REVIEW VIDEO
    @RequestMapping(value="/video/review", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> reviewVideo(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@AuthenticationPrincipal CurrentUserDTO activeUser, Model model, @RequestBody  AjaxVideoDTO ajaxDTO) throws Exception {
    
    	Long userid = (activeUser!=null && activeUser.getUser()!=null) ? activeUser.getUser().getId() : null;
		Long videoid = Long.parseLong(ajaxDTO.getVideoid());
		String reviewtext = ajaxDTO.getReviewtext();
		
		
	
		
		if(userid!=null) {
		//CREATE THE REVIEW
		Long reviewid = reviewService.createReview(userid, videoid, reviewtext,ajaxDTO.getTime());
		
	    //CALCULATE REVIEW COUNT
	    privateVideoService.computeReviewCount(videoid);
	
	    //LOG
	    Activity activity = new Activity(ActivityEnum.REVIEW, identity,null,videoid,null,null,null);
	    logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
		
	    
		//NOTIFY VIDEO OWNER
		Long videoOwnerID = privateVideoService.selectUserIDOfVideo(videoid);
    	nottsService.notify(NotificationEnum.VIDEOREVIEW, videoOwnerID, userid, videoid, null,null,null);
	    
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK,reviewid.toString()), HttpStatus.OK);
	    return response;
		} 
		
		else { 
			
		return new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
		}
		
		
		
    }
    
    
    //REVIEW VIDEO
    @RequestMapping(value="/video/review/delete", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> deleteVideoReview(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@AuthenticationPrincipal CurrentUserDTO activeUser, Model model, @RequestBody  AjaxVideoDTO ajaxDTO) throws Exception {
    
    	Long userid = (activeUser!=null && activeUser.getUser()!=null) ? activeUser.getUser().getId() : null;
		Long reviewid = Long.parseLong(ajaxDTO.getReviewid());
		Long videoid = Long.parseLong(ajaxDTO.getVideoid());
		

		if(userid!=null) {
		//REMOVE THE REVIEW
		reviewService.removeReview(userid,reviewid);

	    //CALCULATE REVIEW COUNT
	    privateVideoService.computeReviewCount(videoid);
	    
	    
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK), HttpStatus.OK);
	    return response;
		}
		else { 
			
			return new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
		}
    }
    
    
    
    
    /*************************************************************METHOD TO RATE, LIKE, SHARE AND COUNT PLAY OF VIDEO*********************************************************/ 
        
    //RATE VIDEO
    @RequestMapping(value="/video/rate", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> rateVideo(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@AuthenticationPrincipal CurrentUserDTO activeUser, Model model, @RequestBody  AjaxVideoDTO ajaxDTO) throws Exception {
    
    	
    	boolean duplicateRating = false;
    	
    	Long userid = (activeUser!=null && activeUser.getUser()!=null) ? activeUser.getUser().getId() : null;
		Long videoid = Long.parseLong(ajaxDTO.getVideoid());
		Long ratingid = Long.parseLong(ajaxDTO.getRating());
		
		if(userid!=null) {
			  int previewsRatingThisVideo = cntService.isUserRatedVideo(userid, videoid);
			  if(previewsRatingThisVideo>0)
				  duplicateRating =true;
		}

		if(!duplicateRating) {
			//INSERT RATING
		    cntService.videoRate(userid, videoid,ratingid);
		}
		
		else {
			//UPDATE RATING
		    cntService.updateVideoRate(userid, videoid,ratingid);
			
		}

		
	    //CALCULATE RATING
	    privateVideoService.computeRatingCount(videoid);
	    
		//NOTIFY VIDEO OWNER
		Long videoOwnerID = privateVideoService.selectUserIDOfVideo(videoid);
	    nottsService.notify(NotificationEnum.VIDEORATE, videoOwnerID, userid, videoid, null,ratingid,null);

		
		//GET VIDEO
	    PublicVideoDTO video = videoService.findPublicVideoById(videoid);
		
		
	    //LOG
	    Activity activity = new Activity(ActivityEnum.RATE, identity,null,videoid,null,null,ratingid+"");
	    logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
	        

	        
    	
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK,video.getRatingssum().toString()), HttpStatus.OK);
	    return response;
    }

    
    
    
    //VIDEO LIKE
    @RequestMapping(value="/video/like", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> likeVideo(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity, @AuthenticationPrincipal CurrentUserDTO activeUser, Model model, @RequestBody  AjaxVideoDTO ajaxDTO) throws Exception {
    
    	boolean duplicateLike = false;
    	
    	Long userid = (activeUser!=null && activeUser.getUser()!=null) ? activeUser.getUser().getId() : null;
		System.out.println(userid);
		Long videoid = Long.parseLong(ajaxDTO.getVideoid());

		
		
		if(userid!=null) {
			  int previewsLikeOfThisVideo = cntService.isUserLikesVideo(userid, videoid);
			  if(previewsLikeOfThisVideo>0)
				  duplicateLike =true;
		}

		if(!duplicateLike) {
			//COUNT LIKE
		    privateVideoService.countLikeVideo(userid, videoid);
		    
		    //KEEP TRACK OF WHICH USER LIKE WHICH VIDEO
		    cntService.videoLike(userid, videoid);
		    
			//NOTIFY VIDEO OWNER
			Long videoOwnerID = privateVideoService.selectUserIDOfVideo(videoid);
		    nottsService.notify(NotificationEnum.VIDEOLIKE, videoOwnerID, userid, videoid, null,null,null);
		    
		}
	    
	    //GET VIDEO
	    PublicVideoDTO video = videoService.findPublicVideoById(videoid);
	    

    	//LOG
    	Activity activity = new Activity(ActivityEnum.LIKE, identity,null,videoid,null,null,null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
    	
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK, video.getLikescount().toString()), HttpStatus.OK);
	    return response;
    }


    @RequestMapping(value="/video/play", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> playVideo(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,Model model,  @AuthenticationPrincipal CurrentUserDTO activeUser, @RequestBody  AjaxVideoDTO ajaxDTO) throws Exception {
    
		Long userid = (activeUser!=null && activeUser.getUser()!=null) ? activeUser.getUser().getId() : null;
		
	    Long videoid = Long.parseLong(ajaxDTO.getVideoid());

	    //PLAY COUNT
	    privateVideoService.countPlayVideo(userid, videoid);
	    

	    //KEEP TRACK OF WHICH USER PLAYED WHICH VIDEO
	    cntService.videoPlay(userid, videoid);
	    
	    //GET VIDEO
	    PublicVideoDTO video = videoService.findPublicVideoById(videoid);

    	//LOG
    	Activity activity = new Activity(ActivityEnum.PLAY, identity,null,videoid,null,null,null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
    	
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK,video.getPlayscount().toString()), HttpStatus.OK);
	    return response;
    }
    

    
    @RequestMapping(value="/video/share", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> shareVideo(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity, Model model,  @AuthenticationPrincipal CurrentUserDTO activeUser, @RequestBody  AjaxVideoDTO ajaxDTO) throws Exception {
    
    	
    	
		Long userid = (activeUser!=null && activeUser.getUser()!=null) ? activeUser.getUser().getId() : null;

	    Long videoid = Long.parseLong(ajaxDTO.getVideoid());
	    Long socialnetworkid = Long.parseLong(ajaxDTO.getSocialnetworkid());

	    
	    
	    
	    ResponseEntity<AjaxResultDTO> response = null;
	    
	    
	    
	    System.out.println("user id:"+userid);
	    System.out.println("video id:"+videoid);
	    
	    
	    if(userid==null) {
	    	
	    	
			//KEEP TRACK OF WHICH USER SHARED WHICH VIDEO
			cntService.videoShare(null, videoid,socialnetworkid);

			response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK,""), HttpStatus.OK);
		}
	    
	    else { 
	    
	    //SHARE COUNT
	    privateVideoService.countShareVideo(userid, videoid);

		//KEEP TRACK OF WHICH USER SHARED WHICH VIDEO
		cntService.videoShare(userid, videoid,socialnetworkid);
	    
	    //GET VIDEO
		PublicVideoDTO video = videoService.findPublicVideoById(videoid);

		
		//NOTIFY VIDEO OWNER
		Long videoOwnerID = privateVideoService.selectUserIDOfVideo(videoid);
	    nottsService.notify(NotificationEnum.VIDEOSHARE, videoOwnerID, userid, videoid, null,null,socialnetworkid);

		
		
	    response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK,video.getTweetscount().toString()), HttpStatus.OK);
	    
	    }
	    

    	//LOG
    	Activity activity = new Activity(ActivityEnum.SHARE, identity,null,videoid,null,null,null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
    	
    	
	    
	    return response;
    }
    
    
    
    
    /*************************************************************METHODS FOR VIDEO TO ADD TO WATCH LATER AND FAVORITES *********************************************************/ 
    
    //ADD TO WATCH LATER
    @RequestMapping(value="/collections/watch-later/add", method = RequestMethod.POST)
    	public ResponseEntity<AjaxResultDTO> addtoWatchLaterCollection(Model model,  @AuthenticationPrincipal CurrentUserDTO activeUser, @RequestBody  AjaxVideoDTO ajaxDTO) {
        
    	//
        Long userid = activeUser.getUser().getId();
	    Long videoid = Long.parseLong(ajaxDTO.getVideoid());


        //GET WATCH LATER COLLECTION OF THIS USER
        Long collectionid = collService.selectWatchLaterCollection(userid, true).getId();
        
	    //ADD VIDEO TO COLLECTION
	    if(cntService.videoExistsCollection(videoid, collectionid)==null) {
	    	cntService.videoAddCollection(videoid, collectionid);

			//NOTIFY VIDEO OWNER
			Long videoOwnerID = privateVideoService.selectUserIDOfVideo(videoid);
		    nottsService.notify(NotificationEnum.COLLECTIONADDED, videoOwnerID, userid, videoid, collectionid,null,null);

	    }


	    
        ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK,"VIDEO ADDED TO WATCHLATER COLLECTION"), HttpStatus.OK);
        return response;
    }
    
    
    
    //ADDTO FAVORITES
    @RequestMapping(value="/collections/favorites/add", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> addtoFavoritesCollection(Model model,  @AuthenticationPrincipal CurrentUserDTO activeUser, @RequestBody  AjaxVideoDTO ajaxDTO) {
    
		Long userid = activeUser.getUser().getId();
	    Long videoid = Long.parseLong(ajaxDTO.getVideoid());

	
	    //GET WATCH LATER COLLECTION OF THIS USER
	    Long collectionid = collService.selectFavoritesCollection(userid, true).getId();
	    
	    //ADD VIDEO TO COLLECTION
	    if(cntService.videoExistsCollection(videoid, collectionid)==null) {
	    	cntService.videoAddCollection(videoid, collectionid);

	    	//NOTIFY VIDEO OWNER
			Long videoOwnerID = privateVideoService.selectUserIDOfVideo(videoid);
		    nottsService.notify(NotificationEnum.COLLECTIONADDED, videoOwnerID, userid, videoid, collectionid,null,null);	    	
	    }
	    	
	
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK,"VIDEO ADDED TO FAVORITES COLLECTION"), HttpStatus.OK);
	    return response;
    }


    
    /*************************************************************PAGINATOR METHODS OF VIDEO GLOBAL CATALOG AND USER VIDEO CATALOG  *********************************************************/ 
    //GLOBAL CATALOG VIDEOS
    @RequestMapping(value="/paginator/video-catalog/", method = RequestMethod.POST)
    public ResponseEntity<AjaxResultDTO> paginateVideos_VideoCatalog(Device device,Model model, @ModelAttribute(WebConstants.VIDCATALOGQUERY) VideoCatalogQueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.CATALOGPAGINATOR) PaginatorDTO paginator, @RequestBody  AjaxPagingDTO ajaxDTO) {
    	RowBounds rowbounds;
    	
    	if(ajaxDTO.getNextpage())
    		rowbounds = paginator.nextPage();
    	
    	model.addAttribute(WebConstants.CATALOGPAGINATOR, paginator);
        
    	//FETCH VIDEOS
        List<PublicVideoDTO> resultList = videoService.selectCatalogVideos(queryDTO, paginator);
        

        //SINCE ITS AJAX CALL DO YOUR SELF A FAVOR AND PARTITION RESULT INTO CHUNKS TO MAKE IT EASY TO JAVASCRIPT TO LOOP
        List chunksOfVideoLists = WebUtility.publicPartitionVideoList(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"video-catalog-view").toString());
    
        
        //CONSTRUCT TAGS
        //List<TagDTO> tagList = WebUtility.extractUniqueTagListFromVideos(resultList);
        
        
        //BUILD AND RETURN RESULT OBJ
        AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(chunksOfVideoLists,paginator,HttpStatus.OK);
        
        
        
     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);
    }
    
    
    //USER CATALOG VIDEOS
    @RequestMapping(value="/paginator/user/video-catalog/", method = RequestMethod.POST)
    public ResponseEntity<AjaxResultDTO> paginateVideos_UserVideoCatalog(Device device,Model model, @ModelAttribute(WebConstants.VIDCATALOGQUERY) VideoCatalogQueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.CATALOGPAGINATOR) PaginatorDTO paginator, @RequestBody  AjaxPagingDTO ajaxDTO) {
    	RowBounds rowbounds;
    	
    	if(ajaxDTO.getNextpage())
    		rowbounds = paginator.nextPage();
    	
    	model.addAttribute(WebConstants.CATALOGPAGINATOR, paginator);
        
    	//FETCH VIDEOS
        List<PublicVideoDTO> resultList = videoService.selectCatalogVideos(queryDTO, paginator);
        

        //SINCE ITS AJAX CALL DO YOUR SELF A FAVOR AND PARTITION RESULT INTO CHUNKS TO MAKE IT EASY TO JAVASCRIPT TO LOOP
        List chunksOfVideoLists = WebUtility.publicPartitionVideoList(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"video-catalog-view").toString());
    
        
        //CONSTRUCT TAGS
        //List<TagDTO> tagList = WebUtility.extractUniqueTagListFromVideos(resultList);
        
        
        //BUILD AND RETURN RESULT OBJ
        AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(chunksOfVideoLists,paginator,HttpStatus.OK);
        
        
        
     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);
    }
    
    
    
    @RequestMapping(value="/paginator/viewport-catalog/", method = RequestMethod.POST)
    public ResponseEntity<AjaxResultDTO> paginateVideos_Viewport(Device device,Model model, @ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, @ModelAttribute(WebConstants.MAPPAGINATOR) PaginatorDTO paginator,@RequestBody  AjaxPagingDTO ajaxDTO) {
    	RowBounds rowbounds;
           model.addAttribute(WebConstants.MAPPAGINATOR, paginator);
        
        
        
    	//FETCH VIDEOS OF FIRST PAGE
        List<PublicVideoDTO> resultList = videoService.selectCatalogVideosViewPort(gmapresult, queryDTO,paginator);
		
        
        //CREATE CHUNKS OF VIDEOS
        String viewName = "video-map-normal-view-"+ (device.isMobile() || device.isTablet()  ? "mobile" : "desktop");
        List chunksOfVideoLists = WebUtility.publicPartitionVideoList(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+viewName).toString());
    	
        
        //CONSTRUCT TAGS
        String tagListstring =  WebUtility.extractUniqueTagListAsStringFromVideos(resultList);
        

        //BUILD AND RETURN RESULT OBJ
        AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(chunksOfVideoLists,tagListstring,paginator,HttpStatus.OK);
     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);

     
    }
    
    
    
    /************************************************************* VIEWPORT VIDEOS FUSION LAYER *********************************************************/ 
	@RequestMapping(value="/viewport-pins/fusion", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> fetchVideoPinsFusion_Viewport(Device device,Model model, @ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, @ModelAttribute(WebConstants.MAPPAGINATOR) PaginatorDTO paginator, @RequestBody QueryDTO ajaxQueryDTO) {
		 

		//GET BOUNDS AND LOCATION
		BoundsDTO boundsDTO = ajaxQueryDTO.getBounds();
		PointDTO location = ajaxQueryDTO.getLocation();
		
		//GET FILTERS
		AdvancedFiltersDTO filtersDTO = ajaxQueryDTO.getAdvfilter();
		
		
		AjaxResultDTO ajaxResultDTO = null;
		try {
		FusionTableResultDTO resultDTO = fusionService.findPointsOfViewPort(boundsDTO, location,filtersDTO);
		//FusionTableResultDTO resultDTO = fusionService.findPointsOfRadious(ajaxQueryDTO.getLocation(), filtersDTO);

		
		ajaxResultDTO = new AjaxResultDTO(new PublicVideoPointDTO(),resultDTO.getPoints(),resultDTO.getTotal(),HttpStatus.OK);
		} catch (Exception e) { ajaxResultDTO =  new AjaxResultDTO(HttpStatus.BAD_REQUEST,e.getMessage());};
		
		
		
     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);

        
	}
    
	
    /************************************************************* VIEWPORT VIDEOS FUSION LAYER *********************************************************/ 
	@RequestMapping(value="/viewport-pins/", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> fetchVideoPinsDatabase_Viewport(Device device,Model model, @ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, @ModelAttribute(WebConstants.MAPPAGINATOR) PaginatorDTO paginator, @RequestBody QueryDTO ajaxQueryDTO) {
		 

		//GET BOUNDS AND LOCATION
    	gmapresult.setBounds(ajaxQueryDTO.getBounds());
    	gmapresult.setViewport(ajaxQueryDTO.getBounds());
    	gmapresult.setLocation(ajaxQueryDTO.getLocation());
    	//UPDATE ADVANCED FILTERS
    	queryDTO.setAdvfilter(ajaxQueryDTO.getAdvfilter());

    	
		
		
		//GET FILTERS
		AdvancedFiltersDTO filtersDTO = ajaxQueryDTO.getAdvfilter();
		
		
		AjaxResultDTO ajaxResultDTO = null;
		try {
	    //FETCH VIDEOS OF FIRST PAGE
	     List<PublicVideoPointDTO> pointsList = videoService.selectCatalogVideoPointsOfViewport(gmapresult, queryDTO,0,550);
	     System.out.println(pointsList.size());
		
		ajaxResultDTO = new AjaxResultDTO(null,pointsList,pointsList.size(),HttpStatus.OK);
		} catch (Exception e) { 
			
			e.printStackTrace();
			ajaxResultDTO =  new AjaxResultDTO(HttpStatus.BAD_REQUEST,e.getMessage());};
		
		
		
     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);

        
	}
    
	
	
	
    
    
    
    /************************************************************* VIEWPORT VIDEOS  *********************************************************/ 
	@RequestMapping(value="/viewport-catalog/", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> fetchVideoGrid_Viewport(Device device,Model model, @ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, @ModelAttribute(WebConstants.MAPPAGINATOR) PaginatorDTO paginator, @RequestBody QueryDTO ajaxQueryDTO) {
		 
    	//UPDATE FORM
		gmapresult.setEmptyStatus(false);
    	gmapresult.setBounds(ajaxQueryDTO.getBounds());
    	gmapresult.setZoomlevel(ajaxQueryDTO.getZoomlevel());
    	gmapresult.setLocation(ajaxQueryDTO.getLocation());
    	gmapresult.setViewport(ajaxQueryDTO.getBounds());
    	gmapresult.setBounds(ajaxQueryDTO.getBounds());
    	gmapresult.setFitBounds(ajaxQueryDTO.getFitBounds());
    	model.addAttribute(WebConstants.GMAPRESULT, gmapresult);
		
    	
    	//UPDATE ADVANCED FILTERS
    	queryDTO.setAdvfilter(ajaxQueryDTO.getAdvfilter());
    	
    	//UPDATE SESSION WITH QUERY DTO
        model.addAttribute(WebConstants.VIDEOQUERYFRM, queryDTO);
    	
    	
        //FETCH ALL VIDEOS VIDEOS BASED ON QUERY CRITERIA AND INITIALISE PAGINATOR AND TAG LIST
        Integer videosCount = videoService.selectCountCatalogVideosViewPort(gmapresult,queryDTO);
        
    	//UPDATE SESSION WITH NEW PAGINATOR
        paginator = new PaginatorDTO(videosCount,15); 
        paginator.firstPage();
        model.addAttribute(WebConstants.MAPPAGINATOR, paginator);
    	
        
        
        
        
    	//FETCH VIDEOS OF FIRST PAGE
        List<PublicVideoDTO> resultList = videoService.selectCatalogVideosViewPort(gmapresult, queryDTO,paginator);
		
        
        //CREATE CHUNKS OF VIDEOS
        List chunksOfVideoLists = WebUtility.publicPartitionVideoList(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+ajaxQueryDTO.getPageview()).toString());
        
        
        //CONSTRUCT TAGS
        String tagListstring =  WebUtility.extractUniqueTagListAsStringFromVideos(resultList);
        

        //BUILD AND RETURN RESULT OBJ
        AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(chunksOfVideoLists,tagListstring,paginator,HttpStatus.OK);
     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);

        
	}
    

    
 

    
    
        
}