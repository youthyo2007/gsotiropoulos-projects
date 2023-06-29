package com.socialvideo.web.controllers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.socialvideo.comparators.VideoSortByPathSortIndexComparator;
import com.socialvideo.constant.CollectionTypes;
import com.socialvideo.constant.VideoHealthStatus;
import com.socialvideo.constant.VideoPublishStatus;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.AvatarDTO;
import com.socialvideo.data.dto.CollectionDTO;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.EventDTO;
import com.socialvideo.data.dto.IDDescrDTO;
import com.socialvideo.data.dto.NotificationEnum;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PersonalizationDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.TagDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VideoCatalogQueryDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.VideoManagerDTO;
import com.socialvideo.data.dto.statistics.StatisticsDTO;
import com.socialvideo.data.services.AvatarService;
import com.socialvideo.data.services.ChannelService;
import com.socialvideo.data.services.CollectionService;
import com.socialvideo.data.services.ConnectEntityService;
import com.socialvideo.data.services.DatabaseService;
import com.socialvideo.data.services.EmailService;
import com.socialvideo.data.services.IGoogleMapsService;
import com.socialvideo.data.services.IntegrationService;
import com.socialvideo.data.services.NotificationsService;
import com.socialvideo.data.services.PublicVideoService;
import com.socialvideo.data.services.inter.IUserService;
import com.socialvideo.data.services.inter.IVideoService;
import com.socialvideo.data.services.statistics.StatisticsSevice;
import com.socialvideo.web.events.GenericEvent;


@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/video-organizer")
@SessionAttributes({WebConstants.PERSONALIZATION,WebConstants.CATALOGPAGINATOR})
public class VideoOrganizerController {

	 private static final Logger logger = Logger.getLogger(VideoOrganizerController.class);

	 
	 
	 @Autowired
	 private ApplicationEventPublisher eventPublisher;
	 
	 @Autowired
	 protected NotificationsService nottsService;
	 
	 @Autowired
	 protected IntegrationService integrationService;
	 
	 @Autowired
	 protected EmailService emailService;
	 
	 @Autowired
	 protected StatisticsSevice statsService;
	 
	 
	 @Autowired
	 protected IUserService userService;
	 
	 @Autowired
	 protected IVideoService privateVideoService;
	 
	 @Autowired
	 protected PublicVideoService videoService;
	 
	
	 
	 @Autowired
	 protected AvatarService avatarService;
	
	 
	 @Autowired
	 protected ChannelService channelService;
	 
	 
	 @Autowired
	 protected IGoogleMapsService gMapService;
	 
	 @Autowired
	 protected ConnectEntityService cntService;
	 
	 
	 @Autowired
	 protected CollectionService collService;
	 
	 @Autowired
	 protected DatabaseService dbService;

	 
	 @ModelAttribute("videoTypeList")
     public List<IDDescrDTO> getVideoTypelist() {
	       return   dbService.getVideoTypelist();
     }
	 
	 
	 @ModelAttribute("videoShootingTypeList")
     public List<IDDescrDTO> getVideoShootingTypelist() {
	       return   dbService.getVideoShootingTypelist();
     }
	 
	 

	 
	 @ModelAttribute
     public void navBarAttributes(Model model) {
		model.addAttribute(WebConstants.NAVBAR_SHOWUPLOADBUTTON, true);
		model.addAttribute(WebConstants.NAVBAR_SHOWSEARCHTXT, false);
		
     }

	 
	 @ModelAttribute(WebConstants.VIDMANAGERFRM)
     public VideoManagerDTO init_VIDMANAGERFRM(@AuthenticationPrincipal CurrentUserDTO activeUser) {
		 	VideoManagerDTO managerDTO = new VideoManagerDTO();
		 	managerDTO.initcollections(collService.selectCollectionsEmpty(activeUser.getUser().getId(), CollectionTypes.ALBUMS, null, null, null, null), collService.selectCollectionsEmpty(activeUser.getUser().getId(), CollectionTypes.PLAYLISTS, null, null, null, null), collService.selectWatchLaterCollection(activeUser.getUser().getId(),true),collService.selectFavoritesCollection(activeUser.getUser().getId(),true));
		 	
		 	return  managerDTO;
     }
	 
	 
	 @ModelAttribute(WebConstants.CATALOGPAGINATOR)
     public PaginatorDTO init_CATALOGPAGINATOR() {
		 	PaginatorDTO paginatorDTO = new PaginatorDTO(-1);
	       return  paginatorDTO;
     }
	 
	 
	 @ModelAttribute
     public void init_SELECTEDUSER(@AuthenticationPrincipal CurrentUserDTO activeUser,Model model) {
		  //FETCH USER PROFILE DTO
	        UserDTO userDTO = userService.selectUser(activeUser.getUser().getId());
	        model.addAttribute("userDTO",userDTO); 
	        
	        AvatarDTO avatarDTO = avatarService.findActive(userDTO.getId());
	        model.addAttribute("avatarDTO",avatarDTO); 
     }
	 
	 
    public VideoOrganizerController() {
    }
  
    //ORGANIZE MY VIDEOS
    @RequestMapping(value={"/view:{view}/filter:{filter}"}, method=RequestMethod.GET)
    public ModelAndView videosView(Device device,@RequestParam(value = "grid", required=false) String grid,@RequestParam(value = "page", required=false) String pageid, @RequestParam(value = "videoid", required=false) Integer videoid, @AuthenticationPrincipal CurrentUserDTO activeUser,  @PathVariable String view, @PathVariable String filter, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.VIDMANAGERFRM) VideoManagerDTO managerDTO) {
        
    	
    	ModelAndView mav = new ModelAndView("video-organizer-view");
        UserDTO userDTO = activeUser.getUser();
        

        
        //ADD VIDEO ID OF PREVIOUS EDIT
        mav.addObject("previousEditVideoID", videoid==null ? -1 : videoid);
        
        
        //GET STATISTICS FOR STATUS OF ALL VIDEOS
        StatisticsDTO statisticsDTO = new StatisticsDTO(statsService.selectUserVideoAllStatus(userDTO.getId()));
        mav.addObject(WebConstants.STATISTICSDTO,statisticsDTO);
        
        
        //UPDATE PERSONALIZATION
        persDTO.updateFilterViewGrid(filter,view,grid,pageid!=null ? pageid : "1" ,"video-organizer-view");
        mav.addObject(WebConstants.PERSONALIZATION, persDTO);
        
        
        //CREATE CATALOG FILTER 
        VideoCatalogQueryDTO queryDTO = new VideoCatalogQueryDTO(userDTO.getId().toString(), filter, null,null);
        if(view.equalsIgnoreCase("rejected")) {queryDTO.setStatus(VideoPublishStatus.REJECTED+""); }
        if(view.equalsIgnoreCase("videos")) {queryDTO.setStatus(VideoPublishStatus.PUBLISHED+""); }
        if(view.equalsIgnoreCase("unpublished")) { queryDTO.setStatus(VideoPublishStatus.UNPUBLISHED+""); }
        if(view.equalsIgnoreCase("broken")) {queryDTO.setStatus(null); queryDTO.setHealthstatus(VideoHealthStatus.BROKEN+"");}
        

        
        
        mav.addObject(WebConstants.VIDCATALOGQUERY,queryDTO);
        
        
        
        //INTIALIZE PAGINATOR
        Integer videosCount = 0;
    	if(view.equalsIgnoreCase("pending")) 
    		videosCount = videoService.selectCountUserPendingCatalogVideos(queryDTO);
    	else
    		videosCount = videoService.selectCountCatalogVideos(queryDTO);
    			
        PaginatorDTO paginator = new PaginatorDTO(videosCount,12);
        RowBounds rowBounds = paginator.firstPage();
        if(pageid!=null)
        	paginator.goToPage(Integer.parseInt(pageid));
        
        
        mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
        
        
        
        
        //SELECT ALL USER VIDEOS WHEN VIEW IS MAP OR VIDEOS
        if(view.equalsIgnoreCase("videos") || view.equalsIgnoreCase("pending") || view.equalsIgnoreCase("rejected") || view.equalsIgnoreCase("map") ) {
        	List<PublicVideoDTO> resultList = null;
        	if(view.equalsIgnoreCase("pending")) {
        		resultList = videoService.selectUserPendingCatalogVideos(queryDTO, paginator);
        	}
        	else
        		resultList = videoService.selectCatalogVideos(queryDTO, paginator);
        	
        	//List videoListPartions = WebUtility.partitionVideoListPublic(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"video-organizer-view").toString());
        	mav.addObject("videoListDTO", resultList);	
        	
        	
        	
        	List<CollectionDTO> resultColList = collService.selectCollectionsEmpty(userDTO.getId(),CollectionTypes.PLAYLISTS,null,null,null,filter);
        	mav.addObject("collectionsListDTO", resultColList);	
        	
        	managerDTO.initvideos(resultList);
        	mav.addObject(WebConstants.VIDMANAGERFRM, managerDTO);
        	
        }

       //SELECT PLAYLISTS
        else if(view.equalsIgnoreCase("playlists")) {
        	List<CollectionDTO> collectionList = collService.selectCollections(userDTO.getId(),CollectionTypes.PLAYLISTS,null,null,null,filter);
        	//List collectionListPartions = WebUtility.partitionCollectionList(resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"usermng-video-organizer-playlists").toString());
        	
        	for (CollectionDTO collectionDTO : collectionList) {
        		collectionDTO.getVideos().sort(new VideoSortByPathSortIndexComparator());
        	}
        	
         	mav.addObject("collectionListDTO", collectionList);	
            
         	
        	List<PublicVideoDTO> videoList = collService.selectVideosOfCollection(userDTO.getId(),null, CollectionTypes.PLAYLISTS,null,null,filter);
        	managerDTO.initvideos(videoList);
        	mav.addObject(WebConstants.VIDMANAGERFRM, managerDTO);        	

        }

        //SELECT ALBUMS
        else if(view.equalsIgnoreCase("albums")) {
        	List<CollectionDTO> collectionList = collService.selectCollections(userDTO.getId(),CollectionTypes.ALBUMS,null,null,null,filter);
        	//List collectionListPartions = WebUtility.partitionCollectionList(resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"usermng-video-organizer-playlists").toString());
        	
         	mav.addObject("collectionListDTO", collectionList);	

         	
        	List<PublicVideoDTO> videoList = collService.selectVideosOfCollection(userDTO.getId(),null, CollectionTypes.ALBUMS,null,null,filter);
        	managerDTO.initvideos(videoList);
        	mav.addObject(WebConstants.VIDMANAGERFRM, managerDTO);        	
        }
        
        

        //SELECT VIDEOS BELONG TO WATCH LATER COLLECTION
        else if(view.equalsIgnoreCase("watchlater")) {
        	List<PublicVideoDTO> resultList = collService.selectVideosOfCollection(userDTO.getId(),managerDTO.getWatchlatercollection().getId(), null,null,null,filter);
        	//List videoListPartions = WebUtility.partitionVideoListPublic(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"video-organizer-view").toString());
        	mav.addObject("videoListDTO", resultList);	

        	managerDTO.initvideos(resultList);
        	mav.addObject(WebConstants.VIDMANAGERFRM, managerDTO);
        	
        }

        //SELECT VIDEOS BELONG TO PLAYLIST BUCKET COLLECTION
        else if(view.equalsIgnoreCase("favoritesbucket")) {
        	List<PublicVideoDTO> resultList = collService.selectVideosOfCollection(userDTO.getId(),managerDTO.getFavoritesbucketcollection().getId(), null,null,null,filter);
        	//List videoListPartions = WebUtility.partitionVideoListPublic(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"video-organizer-view").toString());
        	mav.addObject("videoListDTO", resultList);	

        	managerDTO.initvideos(resultList);
        	mav.addObject(WebConstants.VIDMANAGERFRM, managerDTO);
        }
        
        
        
        return mav;
    }
    
    @RequestMapping(value={"/collections/playlists/videos-add"}, method=RequestMethod.POST)
    public String playlistsAddVideos(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.VIDMANAGERFRM) VideoManagerDTO managerDTO) {
    	for(Entry<Long, Boolean> entry : managerDTO.getSelectedplaylists().entrySet()) {
    	    
    		logger.info("selectedplaylist:"+entry.getKey()+" - value:"+entry.getValue());
    		if(entry.getValue()) {
    	    	Long collectionid = entry.getKey();
    	    	//ADD SELECTED VIDEOS TO THIS COLLECTION
    	    	for(Entry<Long, Boolean> videntry : managerDTO.getSelectedvideos().entrySet()) {
    	    		logger.info("selectedvideo:"+videntry.getKey()+" - value:"+videntry.getValue());
    	    		Long videoid = videntry.getKey();
    	    		if(videntry.getValue()) {
	    	    		if(cntService.videoExistsCollection(videoid, collectionid)==null) {
	        	    	    logger.info("collection:"+collectionid+" - add video:"+videoid);
	    	    			cntService.videoAddCollection(videoid, collectionid);
	    	    			
	    	    			//PUBLISH
	    	    	    	eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),videoid, collectionid, EventDTO.COL_VIDADDED)));

	    	    		}
    	    		}
	    	    	}
    	    }
    	}
    	
      return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter();
    }
    
    
    @RequestMapping(value={"/collections/albums/videos-add"}, method=RequestMethod.POST)
    public String albumsAddVideos(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.VIDMANAGERFRM) VideoManagerDTO managerDTO) {
    	Long userid = activeUser.getUser().getId();
    	
    	for(Entry<Long, Boolean> entry : managerDTO.getSelectedalbums().entrySet()) {
    	    
    		logger.info("selectedalbums:"+entry.getKey()+" - value:"+entry.getValue());
    		if(entry.getValue()) {
    	    	Long collectionid = entry.getKey();
    	    	//ADD SELECTED VIDEOS TO THIS COLLECTION
    	    	for(Entry<Long, Boolean> videntry : managerDTO.getSelectedvideos().entrySet()) {
    	    		logger.info("selectedvideo:"+videntry.getKey()+" - value:"+videntry.getValue());
    	    		Long videoid = videntry.getKey();
    	    		if(videntry.getValue()) {
	    	    		if(cntService.videoExistsCollection(videoid, collectionid)==null) {
	        	    	    logger.info("collection:"+collectionid+" - add video:"+videoid);
	    	    			cntService.videoAddCollection(videoid, collectionid);
	    	    			
	    	    			//NOTIFY VIDEO OWNER
	    	    			Long videoOwnerID = privateVideoService.selectUserIDOfVideo(videoid);
	    	    		    nottsService.notify(NotificationEnum.COLLECTIONADDED, videoOwnerID, userid, videoid, collectionid,null,null);	    	    			
	    	    			
	    	    			
	    	    			//PUBLISH
	    	    	    	eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),videoid, collectionid, EventDTO.COL_VIDADDED)));

	    	    		}
    	    		}
	    	    	}
    	    }
    	}
    	
      return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter();
    }
    
    

    
    
    
    
    @RequestMapping(value={"/collections/playlist-new"}, method=RequestMethod.POST)
    public String createNewPlaylist(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.VIDMANAGERFRM) VideoManagerDTO managerDTO) {
    	managerDTO.getNewcollection().setUserid(activeUser.getUser().getId());
    	managerDTO.getNewcollection().setType(CollectionTypes.PLAYLISTS);
    	CollectionDTO collection = collService.createNewCollection(managerDTO.getNewcollection());
    	
		//PUBLISH
    	eventPublisher.publishEvent(new GenericEvent(new EventDTO(collection, EventDTO.COL_CREATE)));

    	
        return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter();

    }

    
    @RequestMapping(value={"/collections/album-new"}, method=RequestMethod.POST)
    public String createNewAlbum(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.VIDMANAGERFRM) VideoManagerDTO managerDTO) {
    	managerDTO.getNewcollection().setUserid(activeUser.getUser().getId());
    	managerDTO.getNewcollection().setType(CollectionTypes.ALBUMS);
    	CollectionDTO collection = collService.createNewCollection(managerDTO.getNewcollection());
    	
		//PUBLISH
    	eventPublisher.publishEvent(new GenericEvent(new EventDTO(collection, EventDTO.COL_CREATE)));

    	
        return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter();

    }
    

    
    @RequestMapping(value={"/collections/album-edit"}, method=RequestMethod.POST)
    public String editAlbum(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.VIDMANAGERFRM) VideoManagerDTO managerDTO) {
    	collService.editCollection(managerDTO.getNewcollection());
        return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter();

    }
    
    
    @RequestMapping(value={"/collections/playlist-edit"}, method=RequestMethod.POST)
    public String editPlaylist(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.VIDMANAGERFRM) VideoManagerDTO managerDTO) {
    	collService.editCollection(managerDTO.getNewcollection());
        return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter();

    }
    
    
    
    @RequestMapping(value={"/collections/remove"}, method=RequestMethod.POST)
    public String collectionRemove(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.VIDMANAGERFRM) VideoManagerDTO managerDTO) {
    	CollectionDTO collection = collService.selectCollection(managerDTO.getSelectedcollection(), true);
		Long collectionid = collection.getId();
	    
		//PUBLISH
    	eventPublisher.publishEvent(new GenericEvent(new EventDTO(collection, EventDTO.COL_DELETE)));

		//REMOVE ALL VIDEOS
		cntService.removeCollectionVideos(collectionid);

		//REMOVE COLLECTION
		collService.removeCollection(activeUser.getUser().getId(), collectionid);
		


		
        return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter();
    } 
    
    @RequestMapping(value={"/collections/clear"}, method=RequestMethod.POST)
    public String collectionClearVideos(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.VIDMANAGERFRM) VideoManagerDTO managerDTO) {
    	CollectionDTO collection = collService.selectCollection(managerDTO.getSelectedcollection(), true);
		Long collectionid = collection.getId();
	    
		//PUBLISH
    	eventPublisher.publishEvent(new GenericEvent(new EventDTO(collection, EventDTO.COL_CLEARVIDS)));

		//REMOVE ALL VIDEOS
		cntService.removeCollectionVideos(collectionid);
		
        return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter();

    } 
    
    

    @RequestMapping(value={"/collections/videos-add"}, method=RequestMethod.POST)
    public String collectionsAddVideos(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.VIDMANAGERFRM) VideoManagerDTO managerDTO) {
    	CollectionDTO collection = collService.selectCollection(managerDTO.getSelectedcollection(), true);
		Long collectionid = collection.getId();
		
		Long userid = activeUser.getUser().getId();
	    
		for(Entry<Long, Boolean> videntry : managerDTO.getSelectedvideos().entrySet()) {
    		if(videntry.getValue()) {
    		Long videoid = videntry.getKey();
    		if(cntService.videoExistsCollection(videoid, collectionid)==null) {
        		logger.info("collection:"+collection.getTitle()+" - add video:"+videoid);
    			cntService.videoAddCollection(videoid, collectionid);
    			
    			//NOTIFY VIDEO OWNER
    			Long videoOwnerID = privateVideoService.selectUserIDOfVideo(videoid);
    		    nottsService.notify(NotificationEnum.COLLECTIONADDED, videoOwnerID, userid, videoid, collectionid,null,null);	    
    		    
    			//PUBLISH
    	    	eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),videoid, collectionid, EventDTO.COL_VIDADDED)));
    			
    		}
    		}
    	}
        return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter();

    } 
    
    
    @RequestMapping(value={"/collections/videos-remove"}, method=RequestMethod.POST)
    public String collectionsRemoveVideos(@AuthenticationPrincipal CurrentUserDTO activeUser,@ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.VIDMANAGERFRM) VideoManagerDTO managerDTO) {
    	CollectionDTO collection = collService.selectCollection(managerDTO.getSelectedcollection(), true);
    	System.out.println("collection remove video:"+managerDTO.getSelectedcollection());
		Long collectionid = collection.getId();
		for(Entry<Long, Boolean> videntry : managerDTO.getSelectedvideos().entrySet()) {
			System.out.println(videntry.getKey()+":"+videntry.getValue());
			
    		if(videntry.getValue()) {
    		Long videoid = videntry.getKey();
    		if(cntService.videoExistsCollection(videoid, collectionid)!=null) {
        		System.out.println("collection:"+collection.getTitle()+" - remove video:"+videoid);
    			cntService.videoRemoveCollection(videoid, collectionid);
    			
    			//PUBLISH
    	    	eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),videoid, collectionid, EventDTO.COL_VIDREMOVED)));
    		}
    		}
    	}
        return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter();

    } 

    
    @RequestMapping(value={"/collections/path-move-up"}, method=RequestMethod.POST)
    public String collectionsVideosPathMoveUp(@AuthenticationPrincipal CurrentUserDTO activeUser,@ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.VIDMANAGERFRM) VideoManagerDTO managerDTO) {
    	
    	Long videoID =managerDTO.getSelectedvideo();


    	CollectionDTO collectionDTO = collService.selectCollection(managerDTO.getSelectedcollection(), false);
    	Long collectionID = collectionDTO.getId();


    	List<PublicVideoDTO> videoList = collectionDTO.getVideos();
    	videoList.sort(new VideoSortByPathSortIndexComparator());
  
    	//FIND INDEX OF SELECTED VIDEO
    	int currentIndex = -1;

    	for(int i=0; i<videoList.size(); i++) {


    		if(videoList.get(i).getId().equals(videoID)) {
    			currentIndex = i;
    			break;
    		}
    	}
		

    	
    	//MOVE UP IF THERE ARE AT LEAST 2 VIDEOS IN THE COLLECTION
    	if((videoList.size()>=2) && (currentIndex<videoList.size()-1)) {
    		Collections.swap(videoList, currentIndex, currentIndex+1);

    		//UPDATE PATHSORT COLUMN FOR ALL VIDEOS
        	for(int i=0; i<videoList.size(); i++) {
        		collService.updatePathSort(collectionID,videoList.get(i).getId(),i);
        	}
    	}
    	

        return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter();

    } 

    
    @RequestMapping(value={"/collections/path-move-down"}, method=RequestMethod.POST)
    public String collectionsVideosPathMovedown(@AuthenticationPrincipal CurrentUserDTO activeUser,@ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.VIDMANAGERFRM) VideoManagerDTO managerDTO) {
    	
    	Long videoID =managerDTO.getSelectedvideo();
    	CollectionDTO collectionDTO = collService.selectCollection(managerDTO.getSelectedcollection(), false);
    	Long collectionID = collectionDTO.getId();
    	List<PublicVideoDTO> videoList = collectionDTO.getVideos();
       	videoList.sort(new VideoSortByPathSortIndexComparator());
        

       	
       	
       	
    	//FIND INDEX OF SELECTED VIDEO
    	int currentIndex = -1;
    	for(int i=0; i<videoList.size(); i++) {
    		if(videoList.get(i).getId().equals(videoID)) {
    			currentIndex = i;
    			break;
    		}
    	}


    	//MOVE UP IF THERE ARE AT LEAST 2 VIDEOS IN THE COLLECTION
    	if((videoList.size()>=2) && (currentIndex>0)) {
    		Collections.swap(videoList, currentIndex, currentIndex-1);

    		//UPDATE PATHSORT COLUMN FOR ALL VIDEOS
        	for(int i=0; i<videoList.size(); i++) {
        		collService.updatePathSort(collectionID,videoList.get(i).getId(),i);
        	}
    	}
    	

        return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter();

    } 
    
    
    
    
    
    @RequestMapping(value={"/collections/all/videos-remove"}, method=RequestMethod.POST)
    public String collectionsRemoveVideosFromAll(@AuthenticationPrincipal CurrentUserDTO activeUser,@ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.VIDMANAGERFRM) VideoManagerDTO managerDTO) {
		for(Entry<Long, Boolean> videntry : managerDTO.getSelectedvideos().entrySet()) {
    		if(videntry.getValue()) {
    		Long videoid = videntry.getKey();
    		if(cntService.videoExistsCollection(videoid, null)!=null) {
    			cntService.videoRemoveFromAllCollections(videoid);
    			
    			//PUBLISH
    	    	eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),videoid, null,EventDTO.COL_VIDREMOVEFROMALL)));
    			
    		}
    		}
    	}
        return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter();

    } 
    
    
    
    
    
    //VIDEO SETTINGS
    //@PreAuthorize("@videoService.canEditVideo(principal, #videoid)")
    @RequestMapping(value={"/video-settings/{videoid}"}, method=RequestMethod.GET)
    public ModelAndView videoSettings(@AuthenticationPrincipal CurrentUserDTO activeUser,  @PathVariable Long videoid) throws Exception {
        
    	
    	if(!privateVideoService.canEditVideo(activeUser, videoid))
    		throw new AccessDeniedException("Access Denied, Authorized Requests Only.");
    		
    		
    	ModelAndView mav = new ModelAndView("video-organizer-video-settings");
        
        UserDTO userDTO = activeUser.getUser();
        
        //SELECT SELECTED VIDEO
        VideoDTO privatevideoDTO = privateVideoService.findVideoById(videoid);
        
        //FIND VIDEO TYPES
        List<IDDescrDTO> videoTypeList = dbService.lookupVideoTypeListOfVideo(videoid);
        privatevideoDTO.setVideoTypeList(videoTypeList);
        
        //SET SELECTED  VIDEO TYPES LIST
        //String selectedVideoTypesCommaString = videoTypeList!=null ? privatevideoDTO.getVideoTypeList().stream().map(i -> i.getId().toString()).collect(Collectors.joining(", ")) : "";
        String selectedVideoTypesCommaString ="";
        if(videoTypeList!=null) {
        	for (IDDescrDTO current : videoTypeList) {
        		selectedVideoTypesCommaString+=current.getId()+",";
        	}
        	selectedVideoTypesCommaString = selectedVideoTypesCommaString.substring(0, selectedVideoTypesCommaString.length()-1);
        	
        }
        privatevideoDTO.setSelectedvideotypelist(selectedVideoTypesCommaString);

        
        mav.addObject(WebConstants.VIDEOSETTINGSFRM, privatevideoDTO);
        
        
        //ADD TAG LIST
        List<TagDTO> tagList = dbService.getAllTagsList(20);
        mav.addObject("tagDTOList",tagList);
        
        
        
        return mav;
    }
    
    

    @RequestMapping(value={"/video/{videoid}/unwanted"}, method=RequestMethod.GET)
    public String videoUnwanted(@AuthenticationPrincipal CurrentUserDTO activeUser,  @PathVariable Long videoid, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) throws Exception {
        
    	
    	if(!privateVideoService.canEditVideo(activeUser, videoid))
    		throw new AccessDeniedException("Access Denied, Authorized Requests Only.");
    		
    	
    	//MARK AS UNWANTED
    	privateVideoService.setMarkAsUnwanted(videoid,true);
    	

        return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter()+"?page="+persDTO.getPageid();
    }

    
    
    @RequestMapping(value={"/video/{videoid}/wanted"}, method=RequestMethod.GET)
    public String videoMarkAsWwanted(@AuthenticationPrincipal CurrentUserDTO activeUser,  @PathVariable Long videoid, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) throws Exception {
        
    	
    	if(!privateVideoService.canEditVideo(activeUser, videoid))
    		throw new AccessDeniedException("Access Denied, Authorized Requests Only.");
    		
    	
    	//MARK AS UNWANTED
    	privateVideoService.setMarkAsUnwanted(videoid,false);
    	

        return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter();
    }

    
    
    
    //VIDEO SETTINGS
    //@PreAuthorize("@videoService.canEditVideo(principal, #videoid)")
    @RequestMapping(value={"/video-settings/{videoid}/edit-submit"}, method=RequestMethod.POST)
    public String videoEdit(@AuthenticationPrincipal CurrentUserDTO activeUser,@ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @PathVariable Long videoid, @ModelAttribute(WebConstants.VIDEOSETTINGSFRM) VideoDTO videoDTOForm) throws Exception  {

    	if(!privateVideoService.canEditVideo(activeUser, videoid))
    		throw new AccessDeniedException("Access Denied, Authorized Requests Only");
    	
    	ModelAndView mav = new ModelAndView("video-organizer-video-settings");
    	String selectedVideoTypesCommaString = videoDTOForm.getSelectedvideotypelist();
    	
        //EXTRACT WEATHER STRING FROM BOOLEAN MAP
	    //videoDTO.initWeatherString();
    	privateVideoService.updateAdminVideo(videoDTOForm,activeUser.getId(),false);
        
	    /***************UPDATE VIDEO TYPES*****************************************/
	    if(selectedVideoTypesCommaString.trim().length()>0) {
	    	privateVideoService.clearVideoTypesListOfVideo(videoDTOForm.getId());
		    
	    	 String[] items = selectedVideoTypesCommaString.split(",");
	         List<String> videoTypesIDList = Arrays.asList(items);

		    for (String videotypeid : videoTypesIDList) {
		    	try {
		    		privateVideoService.insertVideoTypesListOfVideo(videoDTOForm.getId(), Long.parseLong(videotypeid));
		    	} catch (Exception e) {e.printStackTrace();}
		    }
	    }

	    /***************UPDATE VIDEO TYPES*****************************************/

	    
	    //SAVE AND APPROVE
	    if(videoDTOForm.getSaveandapprove()) {
	    	
	    	//GET VIDEO
        	VideoDTO video = privateVideoService.findVideoById(videoDTOForm.getId());
   			UserDTO owner = userService.findUserById(video.getUser().getId());
	   		
   			//UPDATE VIDEO
   			privateVideoService.approveVideo(videoid,activeUser.getId());

   			//SENT EMAIL AND POST A COMMENT
			try {
			if(!video.getIsfile()) {
			    //IF ITS IS A YOUTUBE VIDEO THEN POST A COMMENT TO CHANNEL
			    if(video.getYoutube()) {
			    	integrationService.postInitialTerrabisCommentToYoutube(null, video);
			    }
			    
			}
			} catch (Exception e) {e.printStackTrace();}	    	

	    }


        return "redirect:/video-organizer/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter()+"?videoid="+videoid+"&page="+persDTO.getPageid();

    }
    
    
 
    
    
}