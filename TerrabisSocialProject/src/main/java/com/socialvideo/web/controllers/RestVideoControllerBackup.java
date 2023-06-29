package com.socialvideo.web.controllers;

import java.util.ArrayList;
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
import com.socialvideo.constant.GoogleConstants;
import com.socialvideo.constant.QueryConstants;
import com.socialvideo.constant.VideoPublishStatus;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.Activity;
import com.socialvideo.data.dto.ActivityIdentity;
import com.socialvideo.data.dto.AdminQueryDTO;
import com.socialvideo.data.dto.AjaxPagingDTO;
import com.socialvideo.data.dto.AjaxResultDTO;
import com.socialvideo.data.dto.AjaxVideoDTO;
import com.socialvideo.data.dto.CollectionDTO;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PersonalizationDTO;
import com.socialvideo.data.dto.QueryDTO;
import com.socialvideo.data.dto.TagDTO;
import com.socialvideo.data.dto.VideoCatalogQueryDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.Activity.ActivityEnum;
import com.socialvideo.data.dto.maps.GeocodingDTO;
import com.socialvideo.data.dto.maps.PointDTO;
import com.socialvideo.data.services.CollectionService;
import com.socialvideo.data.services.ConnectEntityService;
import com.socialvideo.data.services.DatabaseService;
import com.socialvideo.data.services.IGoogleMapsService;
import com.socialvideo.data.services.inter.IVideoService;
import com.socialvideo.utilities.WebUtility;

import scala.Console;;

//@EnableAutoConfiguration
//@RestController
//@RequestMapping(value = "/rest")
//@SessionAttributes({WebConstants.VIDEOQUERYFRM,WebConstants.VIDCATALOGQUERY,WebConstants.ADMINQUERYFRM, WebConstants.GMAPRESULT,WebConstants.PERSONALIZATION, WebConstants.MAPPAGINATOR, WebConstants.CATALOGPAGINATOR,WebConstants.MAPLISTPAGINATOR})
public class RestVideoControllerBackup {
	
	/*

	 private static final Logger logger = Logger.getLogger(RestVideoControllerBackup.class);
	
	 @Autowired
	 private ApplicationEventPublisher eventPublisher;
	 
	 @Autowired
	 protected IVideoService videoService;
	 
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
	
	
	 
    public RestVideoControllerBackup() {
    }


    
	@RequestMapping(value="/video-relations/", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> fetchRelatedVideosOfVideo(Model model, @RequestBody  AjaxVideoDTO ajaxDTO) {
		List<VideoDTO> resultList = null; 
		
		//CREATE AN EMPTY PAGINATOR FOR 20 RESULTS AND INITIALIZE
		PaginatorDTO paginator  = new PaginatorDTO(100, 20);
		paginator.firstPage();
		
		//FETCH VIDEO
	  	Long videoid = Long.parseLong(ajaxDTO.getVideoid());
     	VideoDTO videoDTO = videoService.findVideoById(videoid);
		
     	 //FETCH NEARBY VIDEOS 
     	 resultList = videoService.selectNearyByVideos(videoDTO.getLatitude().toString(), videoDTO.getLongitude().toString(), QueryConstants.NEARBYRANGE, paginator);

     	 //IF EMTPY FETCH RELEATED TAG VIDEOS
     	 if(resultList.isEmpty())
     		resultList = videoService.selectRelatedTagVideos(videoDTO.getTags(),paginator);

     	 
     	 //IF EMPTY AGAIN FETCH LATEST VIDEOS
     	 if(resultList.isEmpty()) {
     	       VideoCatalogQueryDTO queryDTO = new VideoCatalogQueryDTO(null, QueryConstants.FilterLatest, null);	 
         		resultList = videoService.selectCatalogVideos(queryDTO, paginator);
     	 }
     	         

        //BUILD AND RETURN RESULT OBJ 
     	List<List<VideoDTO>> returnList = new ArrayList<List<VideoDTO>>(); returnList.add(resultList);
        AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(returnList,null,HttpStatus.OK);
     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);
        
	}
    

    
    @RequestMapping(value="/collections/watch-later/add", method = RequestMethod.POST)
    	public ResponseEntity<AjaxResultDTO> addtoWatchLaterCollection(Model model,  @AuthenticationPrincipal CurrentUserDTO activeUser, @RequestBody  AjaxVideoDTO ajaxDTO) {
        
    	//
        Long userid = activeUser.getUser().getId();
	    Long videoid = Long.parseLong(ajaxDTO.getVideoid());


        //GET WATCH LATER COLLECTION OF THIS USER
        Long collectionid = collService.selectWatchLaterCollection(userid, true).getId();
        
	    //ADD VIDEO TO COLLECTION
	    if(cntService.videoExistsCollection(videoid, collectionid)==null)
	    	cntService.videoAddCollection(videoid, collectionid);


        ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK,"VIDEO ADDED TO WATCHLATER COLLECTION"), HttpStatus.OK);
        return response;
    }

    
    
    
    @RequestMapping(value="/video/rate", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> rateVideo(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@AuthenticationPrincipal CurrentUserDTO activeUser, Model model, @RequestBody  AjaxVideoDTO ajaxDTO) throws Exception {
    
    	
    	boolean duplicateRating = false;
    	
    	Long userid = (activeUser!=null && activeUser.getUser()!=null) ? activeUser.getUser().getId() : null;
		Long videoid = Long.parseLong(ajaxDTO.getVideoid());
		Integer rating = Integer.parseInt(ajaxDTO.getRating());
		
		if(userid!=null) {
			  int previewsRatingThisVideo = cntService.isUserRatedVideo(userid, videoid);
			  if(previewsRatingThisVideo>0)
				  duplicateRating =true;
		}

		if(!duplicateRating) {

			//INSERT RATING
		    cntService.videoRate(userid, videoid,rating);

		    //CALCULATE RATING
		    videoService.computeRating(videoid);
		    
		}
		
	    //GET VIDEO
	    VideoDTO videoDTO = videoService.findVideoById(videoid);
	    
	    
   
	     
	    	//LOG
	    	Activity activity = new Activity(ActivityEnum.RATE, identity,null,videoid,null,null,rating+"");
	        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
    	
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK,videoDTO), HttpStatus.OK);
	    return response;
    }


  
    
    @RequestMapping(value="/video", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> videoDetails(Model model, @RequestBody  AjaxVideoDTO ajaxDTO) {
      	
    	Long videoid = Long.parseLong(ajaxDTO.getVideoid());
    	VideoDTO video = videoService.findVideoById(videoid);

        	
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK, video), HttpStatus.OK);
	    return response;
    }

    
    
    
    
    
    
    
    
    @RequestMapping(value="/collection", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> collectionDetails(Model model, @RequestBody  AjaxVideoDTO ajaxDTO) {
      	
    	Long collectionid = Long.parseLong(ajaxDTO.getCollectionid());
    	CollectionDTO collection = collService.selectCollection(collectionid,false);
    	

        	
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK, collection), HttpStatus.OK);
	    return response;
    }
    
    
    
    
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
		    videoService.countLikeVideo(userid, videoid);
		    
		    //KEEP TRACK OF WHICH USER LIKE WHICH VIDEO
		    cntService.videoLike(userid, videoid);
		}
	    
	    //GET VIDEO
	    VideoDTO video = videoService.findVideoById(videoid);
	    

    	//LOG
    	Activity activity = new Activity(ActivityEnum.LIKE, identity,null,videoid,null,null,null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
    	
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK, video.getLikescount().toString()), HttpStatus.OK);
	    return response;
    }



    @RequestMapping(value="/video/collection", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> playCollection(Model model,  @AuthenticationPrincipal CurrentUserDTO activeUser, @RequestBody  AjaxVideoDTO ajaxDTO) {
    
		Long userid = (activeUser!=null && activeUser.getUser()!=null) ? activeUser.getUser().getId() : null;
		System.out.println(userid);
		
	    Long collectionid = Long.parseLong(ajaxDTO.getCollectionid());

	    //COLLECTION PLAY COUNT
	    collService.countPlayCollection(userid, collectionid);
	    

	    //KEEP TRACK OF WHICH USER PLAYED WHICH VIDEO
	    cntService.collectionPlay(userid, collectionid);
	    
	    //GET VIDEO
	    CollectionDTO collection = collService.findCollectionById(collectionid);


    	
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK,collection.getPlayscount().toString()), HttpStatus.OK);
	    return response;
    }
    
    
   
    @RequestMapping(value="/video/play", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> playVideo(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,Model model,  @AuthenticationPrincipal CurrentUserDTO activeUser, @RequestBody  AjaxVideoDTO ajaxDTO) throws Exception {
    
		Long userid = (activeUser!=null && activeUser.getUser()!=null) ? activeUser.getUser().getId() : null;
		System.out.println(userid);
		
	    Long videoid = Long.parseLong(ajaxDTO.getVideoid());

	    //PLAY COUNT
	    videoService.countPlayVideo(userid, videoid);
	    

	    //KEEP TRACK OF WHICH USER PLAYED WHICH VIDEO
	    cntService.videoPlay(userid, videoid);
	    
	    //GET VIDEO
	    VideoDTO video = videoService.findVideoById(videoid);

    	//LOG
    	Activity activity = new Activity(ActivityEnum.PLAY, identity,null,videoid,null,null,null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
    	
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK,video.getPlayscount().toString()), HttpStatus.OK);
	    return response;
    }
    

    
    @RequestMapping(value="/video/share", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> shareVideo(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity, Model model,  @AuthenticationPrincipal CurrentUserDTO activeUser, @RequestBody  AjaxVideoDTO ajaxDTO) throws Exception {
    
		Long userid = (activeUser!=null && activeUser.getUser()!=null) ? activeUser.getUser().getId() : null;
		System.out.println(userid);
		
	    Long videoid = Long.parseLong(ajaxDTO.getVideoid());
	    Long socialnetworkid = Long.parseLong(ajaxDTO.getSocialnetworkid());

	    
	    ResponseEntity<AjaxResultDTO> response = null;
	    
	    if(videoid==1) {
			//KEEP TRACK OF WHICH USER SHARED WHICH VIDEO
			cntService.videoShare(userid, videoid,socialnetworkid);

			response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK,""), HttpStatus.OK);
		}
	    
	    else { 
	    
	    //SHARE COUNT
	    videoService.countShareVideo(userid, videoid);

		//KEEP TRACK OF WHICH USER SHARED WHICH VIDEO
		cntService.videoShare(userid, videoid,socialnetworkid);
	    
	    //GET VIDEO
	    VideoDTO video = videoService.findVideoById(videoid);

	    response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK,video.getTweetscount().toString()), HttpStatus.OK);
	    
	    }
	    

    	//LOG
    	Activity activity = new Activity(ActivityEnum.SHARE, identity,null,videoid,null,null,null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
    	
    	
	    
	    return response;
    }
    
    
    
    @RequestMapping(value="/collections/favorites/add", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> addtoFavoritesCollection(Model model,  @AuthenticationPrincipal CurrentUserDTO activeUser, @RequestBody  AjaxVideoDTO ajaxDTO) {
    
		Long userid = activeUser.getUser().getId();
	    Long videoid = Long.parseLong(ajaxDTO.getVideoid());

	
	    //GET WATCH LATER COLLECTION OF THIS USER
	    Long collectionid = collService.selectFavoritesCollection(userid, true).getId();
	    
	    //ADD VIDEO TO COLLECTION
	    if(cntService.videoExistsCollection(videoid, collectionid)==null)
	    	cntService.videoAddCollection(videoid, collectionid);
	
	    ResponseEntity<AjaxResultDTO> response = new ResponseEntity<AjaxResultDTO>(new AjaxResultDTO( HttpStatus.OK,"VIDEO ADDED TO FAVORITES COLLECTION"), HttpStatus.OK);
	    return response;
    }

    
    
    @RequestMapping(value="/paginator/video-catalog/", method = RequestMethod.POST)
    public ResponseEntity<AjaxResultDTO> paginateVideos_VideoCatalog(Device device,Model model, @ModelAttribute(WebConstants.VIDCATALOGQUERY) VideoCatalogQueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.CATALOGPAGINATOR) PaginatorDTO paginator, @RequestBody  AjaxPagingDTO ajaxDTO) {
    	RowBounds rowbounds;
    	
    	if(ajaxDTO.getNextpage())
    		rowbounds = paginator.nextPage();
    	
    	model.addAttribute(WebConstants.CATALOGPAGINATOR, paginator);
        
    	//FETCH VIDEOS
        List<VideoDTO> resultList = videoService.selectCatalogVideos(queryDTO, paginator);
        

        //SINCE ITS AJAX CALL DO YOUR SELF A FAVOR AND PARTITION RESULT INTO CHUNKS TO MAKE IT EASY TO JAVASCRIPT TO LOOP
        List chunksOfVideoLists = WebUtility.partitionVideoList(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"video-catalog-view").toString());
    
        
        //CONSTRUCT TAGS
        //List<TagDTO> tagList = WebUtility.extractUniqueTagListFromVideos(resultList);
        
        
        //BUILD AND RETURN RESULT OBJ
        AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(chunksOfVideoLists,null,paginator,HttpStatus.OK);
        
        
        
     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);
    }
    
    
    
    @RequestMapping(value="/paginator/map-list/", method = RequestMethod.POST)
    public ResponseEntity<AjaxResultDTO> paginateVideos_MapList(Device device,Model model, @ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, @ModelAttribute(WebConstants.MAPLISTPAGINATOR) PaginatorDTO paginator, @RequestBody  AjaxPagingDTO ajaxDTO) {
    	RowBounds rowbounds;
    	
    	if(ajaxDTO.getNextpage())
    		rowbounds = paginator.nextPage();
    	

        model.addAttribute(WebConstants.MAPLISTPAGINATOR, paginator);

        
    	//FETCH VIDEOS
        List<VideoDTO> resultList = videoService.selectVideos(null, queryDTO,paginator.currentPage());
        //SINCE ITS AJAX CALL DO YOUR SELF A FAVOR AND PARTITION RESULT INTO CHUNKS TO MAKE IT EASY TO JAVASCRIPT TO LOOP
        List chunksOfVideoLists = WebUtility.partitionVideoList(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"video-map-list-view").toString());
        
        
        //BUILD AND RETURN RESULT OBJ
        AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(chunksOfVideoLists,null,paginator,HttpStatus.OK);
        

     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);
    }

    
    
    
    
    
    
    @RequestMapping(value="/paginator/user/video-catalog/", method = RequestMethod.POST)
    public ResponseEntity<AjaxResultDTO> paginateVideos_UserVideoCatalog(Device device,Model model, @ModelAttribute(WebConstants.VIDCATALOGQUERY) VideoCatalogQueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.CATALOGPAGINATOR) PaginatorDTO paginator, @RequestBody  AjaxPagingDTO ajaxDTO) {
    	RowBounds rowbounds;
    	
    	if(ajaxDTO.getNextpage())
    		rowbounds = paginator.nextPage();
    	
    	model.addAttribute(WebConstants.CATALOGPAGINATOR, paginator);
        
    	//FETCH VIDEOS
        List<VideoDTO> resultList = videoService.selectCatalogVideos(queryDTO, paginator);
        

        //SINCE ITS AJAX CALL DO YOUR SELF A FAVOR AND PARTITION RESULT INTO CHUNKS TO MAKE IT EASY TO JAVASCRIPT TO LOOP
        List chunksOfVideoLists = WebUtility.partitionVideoList(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"video-catalog-view").toString());
    
        
        //CONSTRUCT TAGS
        //List<TagDTO> tagList = WebUtility.extractUniqueTagListFromVideos(resultList);
        
        
        //BUILD AND RETURN RESULT OBJ
        AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(chunksOfVideoLists,null,paginator,HttpStatus.OK);
        
        
        
     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);
    }
    
    
    

    @RequestMapping(value="/paginator/map/", method = RequestMethod.POST)
    public ResponseEntity<AjaxResultDTO> paginateVideos_Map(Device device,Model model, @ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, @ModelAttribute(WebConstants.MAPPAGINATOR) PaginatorDTO paginator, @RequestBody  AjaxPagingDTO ajaxDTO) {
    	RowBounds rowbounds;
    	
    	if(ajaxDTO.getNextpage())
    		rowbounds = paginator.nextPage();
    	

        model.addAttribute(WebConstants.MAPPAGINATOR, paginator);
        
    	//FETCH VIDEOS
        List<VideoDTO> resultList = videoService.selectVideos(gmapresult, queryDTO,paginator.currentPage());
        //SINCE ITS AJAX CALL DO YOUR SELF A FAVOR AND PARTITION RESULT INTO CHUNKS TO MAKE IT EASY TO JAVASCRIPT TO LOOP
        List chunksOfVideoLists = WebUtility.partitionVideoList(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"video-map-normal-view").toString());
    
        
        //CONSTRUCT TAGS
        List<TagDTO> tagList = WebUtility.extractUniqueTagListFromVideos(resultList);
        
        
        //BUILD AND RETURN RESULT OBJ
        AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(chunksOfVideoLists,null,tagList,paginator,HttpStatus.OK);
        

     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);
    }

    

	
	
	@RequestMapping(value="/map/viewport-markers/strict", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> fetchVideoMarkersStrict_Viewport(Model model,  @RequestBody QueryDTO ajaxQueryDTO) {
		 
	
		GeocodingDTO gmapresult = new GeocodingDTO();
		gmapresult.setEmptyStatus(false);
    	gmapresult.setBounds(ajaxQueryDTO.getBounds());
    	gmapresult.setZoomlevel(ajaxQueryDTO.getZoomlevel());
    	gmapresult.setLocation(ajaxQueryDTO.getLocation());
    	gmapresult.setViewport(ajaxQueryDTO.getBounds());
    	gmapresult.setBounds(ajaxQueryDTO.getBounds());
    	gmapresult.setFitBounds(ajaxQueryDTO.getFitBounds());

    	List<VideoDTO> markerslist = new ArrayList<>();
    	QueryDTO queryDTO = new QueryDTO();
    	
    	Integer status = null;
    	Integer limit = 100;
    	
    	
    	if(ajaxQueryDTO.getStatus()!=null)
    		status = Integer.parseInt(ajaxQueryDTO.getStatus());
    		
    	if(ajaxQueryDTO.getLimit()!=null)
        	limit = Integer.parseInt(ajaxQueryDTO.getLimit());
    	
    	
    	markerslist  = videoService.selectVideos(gmapresult, queryDTO, new RowBounds(0,limit));
    	

   	     //BUILD AND RETURN RESULT OBJ
	    AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(null,markerslist,HttpStatus.OK);
	    
	 	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);

	}
	
    
    
    
    
   
	@RequestMapping(value="/map/viewport-markers/", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> fetchVideoMarkers_OfMainMapViewport(Model model, @ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, @ModelAttribute(WebConstants.MAPPAGINATOR) PaginatorDTO paginator, @RequestBody QueryDTO ajaxQueryDTO) {
		 
    	//UPDATE FORM
		gmapresult.setEmptyStatus(false);
    	gmapresult.setBounds(ajaxQueryDTO.getBounds());
    	gmapresult.setZoomlevel(ajaxQueryDTO.getZoomlevel());
    	gmapresult.setLocation(ajaxQueryDTO.getLocation());
    	//gmapresult.setMarkerlocation(ajaxQueryDTO.getMarkerlocation());
    	gmapresult.setViewport(ajaxQueryDTO.getBounds());
    	gmapresult.setBounds(ajaxQueryDTO.getBounds());
    	gmapresult.setFitBounds(ajaxQueryDTO.getFitBounds());
    	model.addAttribute(WebConstants.GMAPRESULT, gmapresult);


    	List<VideoDTO> markerslist = new ArrayList<>();
    	markerslist  = videoService.selectVideos(null, queryDTO,new RowBounds(0,5000));
    	
    	
    	
    	
    	//IF CRITERIA IS EMPTY THEN LOAD ALL MARKERS AGAIN  
        if(ajaxQueryDTO.getAdvfilter().isEmptyCriteria()) {
        	markerslist = videoService.selectAllVideoMarkers(new RowBounds(0,5000));	
        }
        else
        //SELECT  MARKERS BASED ON FILTERS (IGNORE VIEWPORT)
        	markerslist  = videoService.selectMapVideos(null, queryDTO,new RowBounds(0,5000));
        	
        
    	
    	
    	 * OLD CODE
    	 * if(ajaxQueryDTO.getAdvfilter().isEmptyCriteria()) {
        	markerslist = videoService.selectAllVideoMarkers(new RowBounds(0,5000));	
        } 

        else {
            markerslist  = videoService.selectMapVideos(gmapresult, queryDTO,new RowBounds(0,24));
        }


    	
    	
	 	//UPDATE THE PAGINATOR
        Integer videosCount = videoService.selectCountVideos(gmapresult,queryDTO);
    	//UPDATE SESSION WITH NEW PAGINATOR
        paginator = new PaginatorDTO(videosCount); 
        model.addAttribute(WebConstants.MAPPAGINATOR, paginator);
    	

	     //BUILD AND RETURN RESULT OBJ
	    AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(null,markerslist,paginator,HttpStatus.OK);
	    
	 	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);

	}

	
	@RequestMapping(value="/map/viewport-datagrid/", method = RequestMethod.POST)
	public ResponseEntity<AjaxResultDTO> fetchVideoGrid_Viewport(Device device,Model model, @ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, @ModelAttribute(WebConstants.MAPPAGINATOR) PaginatorDTO paginator, @RequestBody QueryDTO ajaxQueryDTO) {
		 
    	//UPDATE FORM
		gmapresult.setEmptyStatus(false);
    	gmapresult.setBounds(ajaxQueryDTO.getBounds());
    	gmapresult.setZoomlevel(ajaxQueryDTO.getZoomlevel());
    	gmapresult.setLocation(ajaxQueryDTO.getLocation());
    	//gmapresult.setMarkerlocation(ajaxQueryDTO.getMarkerlocation());
    	gmapresult.setViewport(ajaxQueryDTO.getBounds());
    	gmapresult.setBounds(ajaxQueryDTO.getBounds());
    	gmapresult.setFitBounds(ajaxQueryDTO.getFitBounds());
    	model.addAttribute(WebConstants.GMAPRESULT, gmapresult);
		
    	
    	
        //FETCH ALL VIDEOS VIDEOS BASED ON QUERY CRITERIA AND INITIALISE PAGINATOR AND TAG LIST
        Integer videosCount = videoService.selectCountVideos(gmapresult,queryDTO);
    	//UPDATE SESSION WITH NEW PAGINATOR
        paginator = new PaginatorDTO(videosCount); 
        model.addAttribute(WebConstants.MAPPAGINATOR, paginator);
    	
        
    	//FETCH VIDEOS OF FIRST PAGE
        List<VideoDTO> resultList = videoService.selectVideos(gmapresult, queryDTO,paginator.firstPage());
		
        
        //CREATE CHUNKS OF VIDEOS
        List chunksOfVideoLists = WebUtility.partitionVideoList(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+ajaxQueryDTO.getPageview()).toString());
        
        
        //CONSTRUCT TAGS
        //List<TagDTO> tagList =  WebUtility.extractUniqueTagListFromVideos(resultList);
        String tagListstring =  WebUtility.extractUniqueTagListAsStringFromVideos(resultList);
        
        
        
        
        //BUILD AND RETURN RESULT OBJ
        AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(chunksOfVideoLists,null,tagListstring,paginator,HttpStatus.OK);
        
     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);

        
	}
        
	
	
	
	
    @RequestMapping(value="/map-advanced/search", method = RequestMethod.POST)
    public  ResponseEntity<AjaxResultDTO> search_advancedCriteriaMap(Device device,Model model, @ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, @ModelAttribute(WebConstants.MAPPAGINATOR) PaginatorDTO paginator, @RequestBody QueryDTO ajaxQueryDTO) {
    	
    	//UPDATE ADVANCED FILTERS
    	queryDTO.setAdvfilter(ajaxQueryDTO.getAdvfilter());
    	
    	//UPDATE SESSION WITH QUERY DTO
        model.addAttribute(WebConstants.VIDEOQUERYFRM, queryDTO);

        
        //BUILD AND RETURN RESULT OBJ
        List<VideoDTO> markerslist   = videoService.selectVideos(gmapresult, queryDTO, new RowBounds(0,100));
        AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(null,markerslist,HttpStatus.OK);
        
        
        //CONSTRUCT TAGS
        //List<TagDTO> tagList = WebUtility.extractUniqueTagListFromVideos(resultList);
       // String tagListstring =  WebUtility.extractUniqueTagListAsStringFromVideos(resultList);
        
	    
       
        //FETCH FIRSR 100 MARKERS OF VIEWPORT
    	//List<VideoDTO> markerslist   = videoService.selectMapVideoMarkersOfViewport(gmapresult, queryDTO, VideoPublishStatus.PUBLISHED,new RowBounds(0,100));
    	//List<VideoDTO> markerslist  = videoService.selectMapVideos(null, queryDTO,new RowBounds(0,100));
    	
    	

        //IF CRITERIA IS EMPTY THEN LOAD ALL MARKERS AGAIN  
        if(ajaxQueryDTO.getAdvfilter().isEmptyCriteria()) {
        	markerslist = videoService.selectAllVideoMarkers(new RowBounds(0,5000));	
        }
        else
        //SELECT  MARKERS BASED ON FILTERS (IGNORE VIEWPORT)
        markerslist  = videoService.selectMapVideos(null, queryDTO,new RowBounds(0,5000));
        	
        
        
        
        
        
        //FETCH ALL VIDEOS VIDEOS BASED ON QUERY CRITERIA AND INITIALISE PAGINATOR AND TAG LIST
        //Integer videosCount = videoService.selectCountVideosOfMapArea(gmapresult,queryDTO);
    	//UPDATE SESSION WITH NEW PAGINATOR
        //paginator = new PaginatorDTO(videosCount); 
        //model.addAttribute(WebConstants.MAPPAGINATOR, paginator);
        
        //FETCH VIDEOS OF VIEWPORT
        //List<VideoDTO> resultList = videoService.selectMapVideos(gmapresult, queryDTO,paginator.firstPage());
 
        
        //SINCE ITS AJAX CALL DO YOUR SELF A FAVOR AND PARTITION RESULT INTO CHUNKS TO MAKE IT EASY TO JAVASCRIPT TO LOOP
        //List chunksOfVideoLists = WebUtility.partitionVideoList(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+ajaxQueryDTO.getPageview()).toString());
    
        
        //CONSTRUCT TAGS
        //List<TagDTO> tagList = WebUtility.extractUniqueTagListFromVideos(resultList);
       // String tagListstring =  WebUtility.extractUniqueTagListAsStringFromVideos(resultList);
        

        
        
     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);
  
    }
    
    
    
	
    @RequestMapping(value="/map-advanced/search", method = RequestMethod.POST)
    public  ResponseEntity<AjaxResultDTO> search_advancedCriteriaMap(Model model, @ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, @ModelAttribute(WebConstants.MAPPAGINATOR) PaginatorDTO paginator, @RequestBody QueryDTO ajaxQueryDTO) {
    	
    	//UPDATE ADVANCED FILTERS
    	queryDTO.setAdvfilter(ajaxQueryDTO.getAdvfilter());
    	
    	//UPDATE SESSION WITH QUERY DTO
        model.addAttribute(WebConstants.VIDEOQUERYFRM, queryDTO);
        
        //FETCH ALL VIDEOS VIDEOS BASED ON QUERY CRITERIA AND INITIALISE PAGINATOR AND TAG LIST
        Integer videosCount = videoService.selectCountVideosOfMapArea(gmapresult,queryDTO);
    	//UPDATE SESSION WITH NEW PAGINATOR
        paginator = new PaginatorDTO(videosCount); 
        model.addAttribute(WebConstants.MAPPAGINATOR, paginator);
        
        
    	//FETCH VIDEOS
        List<VideoDTO> resultList = videoService.selectMapVideos(gmapresult, queryDTO,paginator.firstPage());
        //SINCE ITS AJAX CALL DO YOUR SELF A FAVOR AND PARTITION RESULT INTO CHUNKS TO MAKE IT EASY TO JAVASCRIPT TO LOOP
        List chunksOfVideoLists = WebUtility.partitionVideoList(resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+ajaxQueryDTO.getPageview()).toString());
    
        
        //CONSTRUCT TAGS
        List<TagDTO> tagList = WebUtility.extractUniqueTagListFromVideos(resultList);
        
        
        //BUILD AND RETURN RESULT OBJ
        AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(chunksOfVideoLists,tagList,paginator,HttpStatus.OK);
        
        
        
     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);
  
    }
    
	
	
        
        
   

    
    
    
    @RequestMapping(value="/mapevent/on-initialize/", method = RequestMethod.POST)
    public ResponseEntity<AjaxResultDTO> fetchVideos_OnMapInitialize(Model model, @ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, @ModelAttribute(WebConstants.MAPPAGINATOR) PaginatorDTO paginator, @RequestBody QueryDTO ajaxQueryDTO) {
    	 
    	//FETCH FIRST VIDEO MARKERS OF SELECTED VIEWPORT
         List<VideoDTO> resultList = videoService.selectMapVideoMarkersOfViewport(gmapresult, queryDTO,new RowBounds(0,300));
         
         
    	 //FETCH ALL VIDEOS VIDEOS BASED ON QUERY CRITERIA AND INITIALISE PAGINATOR AND TAG LIST
         Integer videosCount = videoService.selectCountVideosOfMapArea(gmapresult,queryDTO);
     	//UPDATE SESSION WITH NEW PAGINATOR
         paginator = new PaginatorDTO(videosCount); 
         model.addAttribute(WebConstants.MAPPAGINATOR, paginator);
         
    	//FETCH VIDEOS OF FIRST PAGE
        //List<VideoDTO> resultList = videoService.selectMapVideos(gmapresult, queryDTO,paginator.firstPage());
        
        
        //SINCE ITS AJAX CALL DO YOUR SELF A FAVOR AND PARTITION RESULT INTO CHUNKS TO MAKE IT EASY TO JAVASCRIPT TO LOOP
        List chunksOfVideoLists = WebUtility.partitionVideoList(resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+ajaxQueryDTO.getPageview()).toString());
    
        
        //CONSTRUCT TAGS
        List<TagDTO> tagList = WebUtility.extractUniqueTagListFromVideos(resultList);
        
        
        //BUILD AND RETURN RESULT OBJ
        AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(chunksOfVideoLists,tagList,paginator,HttpStatus.OK);
        
     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);
    
    }
    
    
    
    
    @RequestMapping(value="/mapevent/on-viewportchange/", method = RequestMethod.POST)
    public ResponseEntity<AjaxResultDTO> fetchVideos_OnMapViewPortChange(Model model, @ModelAttribute(WebConstants.VIDEOQUERYFRM) QueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.GMAPRESULT) GeocodingDTO gmapresult, @ModelAttribute(WebConstants.MAPPAGINATOR) PaginatorDTO paginator, @RequestBody QueryDTO ajaxQueryDTO) {
    	 System.out.println("/mapevent/on-viewportchange/");
    	
    	//REFRESH BOUNDS AND VIEW PORT ON NEW DRAG AND UPDATE SESSION
    	gmapresult.setBounds(ajaxQueryDTO.getBounds());
    	gmapresult.setZoomlevel(ajaxQueryDTO.getZoomlevel());
    	gmapresult.setLocation(ajaxQueryDTO.getLocation());
    	//gmapresult.setMarkerlocation(ajaxQueryDTO.getMarkerlocation());
    	gmapresult.setViewport(ajaxQueryDTO.getBounds());
    	gmapresult.setFitBounds(ajaxQueryDTO.getFitBounds());
    	model.addAttribute(WebConstants.GMAPRESULT, gmapresult);
    	
        System.out.println("/mapevent/on-viewportchange/ zoom level:"+gmapresult.getZoomlevel());
    	
        
        //FETCH ALL VIDEOS VIDEOS BASED ON QUERY CRITERIA AND INITIALISE PAGINATOR AND TAG LIST
        Integer videosCount = videoService.selectCountVideosOfMapArea(gmapresult,queryDTO);
    	//UPDATE SESSION WITH NEW PAGINATOR
        paginator = new PaginatorDTO(videosCount); 
        model.addAttribute(WebConstants.MAPPAGINATOR, paginator);
    	
    	//FETCH VIDEOS
        List<VideoDTO> resultList = videoService.selectMapVideos(gmapresult, queryDTO,paginator.firstPage());
        //SINCE ITS AJAX CALL DO YOUR SELF A FAVOR AND PARTITION RESULT INTO CHUNKS TO MAKE IT EASY TO JAVASCRIPT TO LOOP
        List chunksOfVideoLists = WebUtility.partitionVideoList(resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+ajaxQueryDTO.getPageview()).toString());
    
        
        //CONSTRUCT TAGS
        List<TagDTO> tagList = WebUtility.extractUniqueTagListFromVideos(resultList);
        
        
        //BUILD AND RETURN RESULT OBJ
        AjaxResultDTO ajaxResultDTO = new AjaxResultDTO(chunksOfVideoLists,tagList,paginator,HttpStatus.OK);
        
        
        
     	return new ResponseEntity<AjaxResultDTO>(ajaxResultDTO, HttpStatus.OK);
    
    }


    
    
    @RequestMapping(value="/admin-advanced/search", method = RequestMethod.POST)
    public ResponseEntity<List<List<VideoDTO>>> search_advancedCriteriaAdmin(Device device,Model model, @ModelAttribute(WebConstants.ADMINQUERYFRM) AdminQueryDTO queryDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @RequestBody QueryDTO ajaxQueryDTO) {
    	//ajaxQueryDTO.getAdvfilter().getFootagedate().forEach( (key, value) -> { queryDTO System.out.println(key+":"+value); } );
    	
    	//UPDATE SESSION QUERY
    	queryDTO.setAdvfilter(ajaxQueryDTO.getAdvfilter());
    	
    	
    	//UPDATE SESSION WITH QUERY DTO
        model.addAttribute(WebConstants.ADMINQUERYFRM, queryDTO);

        List<VideoDTO> resultList;
        resultList  =videoService.selectAdminCatalogVideos(queryDTO, new PaginatorDTO(1000));
      
        List videoListPartions = WebUtility.partitionVideoList(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"admin-video-view").toString());
    	return new ResponseEntity<List<List<VideoDTO>>>(videoListPartions, HttpStatus.OK);
        
    }
    
    
    
    @RequestMapping(value="/map-location/search", method = RequestMethod.POST)
    public ResponseEntity<GeocodingDTO> fetchGeolocation_OnMapQuerySearch(@RequestBody QueryDTO ajaxQueryDTO) {
    	GeocodingDTO gmapresult = gMapService.search(ajaxQueryDTO.getQuerytxt()); 
    	return new ResponseEntity<GeocodingDTO>(gmapresult, HttpStatus.OK);
    }

    
    
    

    
    
    @RequestMapping(value="/user-{userid}/avatar/imagebase64", method=RequestMethod.GET)
    @ResponseBody
    public String fetchAvatarBase64Img(@PathVariable Long userid) {
        //FETCH AVATAR DTO
        List<AvatarDTO> avatarDTOList = avatarService.findByUserid(userid);
        String imagebase64 = "";
        if(!avatarDTOList.isEmpty())
        	imagebase64 = ((AvatarDTO)avatarDTOList.get(0)).getImagebase64(); 
        	
       return imagebase64;
    }
    
    
    

    
    
        
*/
}