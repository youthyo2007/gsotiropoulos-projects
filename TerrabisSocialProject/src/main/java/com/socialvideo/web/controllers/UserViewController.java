package com.socialvideo.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mobile.device.Device;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialvideo.constant.CollectionTypes;
import com.socialvideo.constant.Constants;
import com.socialvideo.constant.QueryConstants;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.Activity;
import com.socialvideo.data.dto.Activity.ActivityEnum;
import com.socialvideo.data.dto.ActivityIdentity;
import com.socialvideo.data.dto.AvatarDTO;
import com.socialvideo.data.dto.ChannelDTO;
import com.socialvideo.data.dto.CollectionDTO;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.GameLevelDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PersonalizationDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.UserDTOSmall;
import com.socialvideo.data.dto.VideoCatalogQueryDTO;
import com.socialvideo.data.services.AvatarService;
import com.socialvideo.data.services.ChannelService;
import com.socialvideo.data.services.CollectionService;
import com.socialvideo.data.services.ConnectEntityService;
import com.socialvideo.data.services.GamificationService;
import com.socialvideo.data.services.PublicVideoService;
import com.socialvideo.data.services.inter.IUserService;
import com.socialvideo.utilities.WebUtility;


@EnableAutoConfiguration
@Controller
@RequestMapping(value = "user-{UUID}")
@SessionAttributes({WebConstants.PERSONALIZATION, WebConstants.VIDCATALOGQUERY, WebConstants.CATALOGPAGINATOR})

public class UserViewController {

	 private static final Logger logger = Logger.getLogger(UserViewController.class);

	 @Autowired
	 protected IUserService userService;
	 
	 @Autowired
	 protected ConnectEntityService cntService;
	 
	 @Autowired
	 protected PublicVideoService videoService;
	
	 @Autowired
	 protected ObjectMapper jacksonMapper;
	 
	 @Autowired
	 protected AvatarService avatarService;
	
	 
	 @Autowired
	 protected ChannelService channelService;
	 
	 
	 @Autowired
	 protected CollectionService collService;
	 
	 
	 @Autowired
	 protected GamificationService gameService;
	 
	 
	 @ModelAttribute
     public void navBarAttributes(Model model) {
		model.addAttribute(WebConstants.NAVBAR_SHOWUPLOADBUTTON, true);
		model.addAttribute(WebConstants.NAVBAR_SHOWSEARCHTXT, true);
		
     }

	 
	 @ModelAttribute(WebConstants.CATALOGPAGINATOR)
     public PaginatorDTO init_CATALOGPAGINATOR() {
		 	PaginatorDTO paginatorDTO = new PaginatorDTO(-1);
	       return  paginatorDTO;
     }
	 
	 
	 @ModelAttribute
     public void init_SELECTEDUSER(@PathVariable String UUID, Model model) {
		  //FETCH USER PROFILE DTO
	        UserDTO userDTO = userService.selectUserByUUID(UUID);
	        GameLevelDTO gameLevelDTO = gameService.findGameLevel(userDTO);
	        userDTO.setGameLevel(gameLevelDTO);
	        model.addAttribute("userDTO",userDTO); 
	        
	        AvatarDTO avatarDTO = avatarService.findActive(userDTO.getId());
	        model.addAttribute("avatarDTO",avatarDTO); 
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
	 
    public UserViewController() {
    }
  
    
    //USER PAGE INDEX
    @RequestMapping(value={"", "/"}, method=RequestMethod.GET)
    public ModelAndView index(Device device, @ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity, @PathVariable String UUID, @AuthenticationPrincipal CurrentUserDTO activeUser) throws Exception {
        ModelAndView mav = new ModelAndView("user-index");
        mav.addObject("viewname", "user-index");
        mav.addObject(WebConstants.NAVBAR_SHOWUPLOADBUTTON, false);
        
        UserDTO userDTO = userService.findUserByUUID(UUID);
        
        //IS FOLLOWING USER
        isFollowingUser(mav,activeUser!=null ? activeUser.getId() : null,userDTO.getId());
        
        
        
        
        //SELECT 5 TOPRATED VIDEOS AND 5 LATEST UPLOAD VIDEOS
        List<PublicVideoDTO> topRatedvidList = videoService.selectCatalogVideos(new VideoCatalogQueryDTO(userDTO.getId().toString(), QueryConstants.FilterTopRated, null,null), new PaginatorDTO(5));
        List<PublicVideoDTO> latestVidList = videoService.selectCatalogVideos(new VideoCatalogQueryDTO(userDTO.getId().toString(), QueryConstants.FilterLatest, null,null), new PaginatorDTO(5));
        mav.addObject("topRatedVidList", topRatedvidList);
        mav.addObject("latestVidList", latestVidList);
        mav.addObject("device", device);
        mav.addObject("device", device);
        
    	//LOG
    	Activity activity = new Activity(ActivityEnum.USERPROFILEVIEW, identity,userDTO.getId(),null,null,null,null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
        
        
        
        
        return mav;
        
        
        
        
    }

    
    
    //USER FOLLOWERS
    @RequestMapping(value={"/followers"}, method=RequestMethod.GET)
    public ModelAndView followersView(@PathVariable String UUID, @AuthenticationPrincipal CurrentUserDTO activeUser) {
        ModelAndView mav = new ModelAndView("user-followers-view");
      
        
        //GET SELECTED USER BY UUID
        UserDTO userDTO = userService.findUserByUUID(UUID);
        
        //CHECKED IF LOGGED IN  USER IS FOLLOWING SELECTED USER
        isFollowingUser(mav,activeUser!=null ? activeUser.getId() : null,userDTO.getId());
        
        
        
        //FETCH FOLLOWERS
        List<UserDTOSmall> userListDTO = userService.selectFollowers(userDTO.getId());
        mav.addObject("userListDTO",userListDTO); 
     
        return mav;
    }


    //USER FOLLOWERS
    @RequestMapping(value={"/following"}, method=RequestMethod.GET)
    public ModelAndView followingView(@PathVariable String UUID, @AuthenticationPrincipal CurrentUserDTO activeUser) {
        ModelAndView mav = new ModelAndView("user-following-view");
      
        
        //GET SELECTED USER BY UUID
        UserDTO userDTO = userService.findUserByUUID(UUID);
        
        //CHECKED IF LOGGED IN  USER IS FOLLOWING SELECTED USER
        isFollowingUser(mav,activeUser!=null ? activeUser.getId() : null,userDTO.getId());
        
        
        
        //FETCH FOLLOWERS
        List<UserDTOSmall> userListDTO = userService.selectFollowing(userDTO.getId());
        mav.addObject("userListDTO",userListDTO); 
     
        return mav;
    }
   
    
    
    
    //USER CHANNELS
    @RequestMapping(value={"/channels"}, method=RequestMethod.GET)
    public ModelAndView channelsView(@PathVariable String UUID, @ModelAttribute UserDTO userDTO) {
        ModelAndView mav = new ModelAndView("user-index-channels");
      
        
        
        
        //FETCH USER CHANNELS DTO
        List<ChannelDTO> channeListDTO = channelService.selectUserChannels(userDTO.getId(), 1, 100);
        mav.addObject("channelListDTO",channeListDTO); 
     
        return mav;
    }
    
    
    //USER VIDEO PATHS
    @RequestMapping(value={"/playlists/filter:{filter}"}, method=RequestMethod.GET)
    public ModelAndView playlistsFilterView(Device device, @ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity, @PathVariable String UUID, @PathVariable String filter, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) throws Exception {
        ModelAndView mav = new ModelAndView("user-index-playlists");

        
        //FETCH USER
        UserDTO userDTO = userService.findUserByUUID(UUID);
        
        	
        //PROPAGATE FILTER TO PAGE
        mav.addObject("filter",filter); 
      
        
    	List<CollectionDTO> resultList = collService.selectCollections(userDTO.getId(),CollectionTypes.PLAYLISTS,null,null,null,filter).stream().filter(x->x.getVideos().isEmpty()==false).collect(Collectors.toList());
    	//List collectionListPartions = WebUtility.partitionCollectionList(resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"usermng-video-organizer-playlists").toString());
    	mav.addObject("collectionListDTO", resultList);	
        mav.addObject("device", device);
    	
    	
    	//LOG
    	Activity activity = new Activity(ActivityEnum.USERPATHSVIEW, identity,userDTO.getId(),null,null,null,null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
    	
        return mav;
    }
    
    
    
    //USER ALBUMS
    @RequestMapping(value={"/albums/filter:{filter}"}, method=RequestMethod.GET)
    public ModelAndView albumsFilterView(Device device, @ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity, @PathVariable String UUID, @PathVariable String filter, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) throws Exception {
        ModelAndView mav = new ModelAndView("user-index-albums");

        
        //FETCH USER
        UserDTO userDTO = userService.findUserByUUID(UUID);
        
        	
        //PROPAGATE FILTER TO PAGE
        mav.addObject("filter",filter); 
      
        //IS FOLLOWING USER
        isFollowingUser(mav,activeUser!=null ? activeUser.getId() : null,userDTO.getId());
        
    	List<CollectionDTO> resultList = collService.selectCollections(userDTO.getId(),CollectionTypes.ALBUMS,null,null,null,filter).stream().filter(x->x.getVideos().isEmpty()==false).collect(Collectors.toList());
    	//List collectionListPartions = WebUtility.partitionCollectionList(resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"usermng-video-organizer-playlists").toString());
    	mav.addObject("collectionListDTO", resultList);	
        mav.addObject("device", device);

    	
    	//LOG
    	Activity activity = new Activity(ActivityEnum.USERALBUMSVIEW, identity,userDTO.getId(),null,null,null,null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
        
        return mav;
    }
    
    
/*    
    //USER FAVORITES
    @RequestMapping(value={"/favorites/filter:{filter}"}, method=RequestMethod.GET)
    public ModelAndView favoritesFilterView(Device device,@PathVariable String UUID, @PathVariable String filter, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) {
        ModelAndView mav = new ModelAndView("user-index-favorites");

        
        //FETCH USER
        UserDTO userDTO = userService.findUserByUUID(UUID);
        
        	
        //PROPAGATE FILTER TO PAGE
        mav.addObject("filter",filter); 
      
        
        //FETCH VIDEOS OF COLLECTION:FAVORITES
        List<PublicVideoDTO> resultList = collService.selectVideosOfCollection(userDTO.getId(),null, CollectionTypes.FAVORITESBUCKET,null,null,filter);
    	//List videoListPartions = WebUtility.partitionVideoListPublic(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"user-index-favorites").toString());
    	mav.addObject("videoListDTO", resultList);
        mav.addObject("device", device);

        return mav;
        
    }
    */
    
    //USER FAVORITES
    @RequestMapping(value={"/favorites/filter:{filter}"}, method=RequestMethod.GET)
    public ModelAndView videosFavoritesView(Device device,@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@RequestParam(value = "page", required=false) Integer page, @RequestParam(value = "grid", required=false) String grid, @PathVariable String UUID, @PathVariable String filter, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO,@ModelAttribute(WebConstants.CATALOGPAGINATOR) PaginatorDTO paginator) throws Exception {
        ModelAndView mav = new ModelAndView("user-index-favorites");
        
        //FETCH USER
        UserDTO userDTO = userService.findUserByUUID(UUID);
        CollectionDTO collectionDTO = collService.selectFavoritesCollection(userDTO.getId(), true);

        //IS FOLLOWING USER
        isFollowingUser(mav,activeUser!=null ? activeUser.getId() : null,userDTO.getId());
        
        //ADD PERSONALIZATION
        if(grid!=null) {
        	persDTO.getPreference().put(PersonalizationDTO.KEY_GRIDSTYLE+"user-index-favorites", grid);
            mav.addObject(WebConstants.PERSONALIZATION, persDTO);

        }
        
        
        
        //CREATE CATALOG FILTER FOR USER
        VideoCatalogQueryDTO queryDTO = new VideoCatalogQueryDTO(userDTO.getId().toString(), filter, null,null);
        queryDTO.setCollectionid(collectionDTO.getId().toString());
        mav.addObject(WebConstants.VIDCATALOGQUERY,queryDTO);
        
        
        //INTIALIZE PAGINATOR
        if(page==null) {
	        Integer videosCount = videoService.selectCountCollectionVideos(queryDTO);
	        paginator = new PaginatorDTO(videosCount);
	        RowBounds rowBounds = paginator.firstPage();
	        mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
        } else {
        	 paginator.goToPage(page);
        	 mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
        }

        
        
        
        List<PublicVideoDTO> resultList;
        resultList = videoService.selectCollectionVideos(queryDTO,paginator);
        //List videoListPartions = WebUtility.partitionVideoListPublic(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"user-index-videos").toString());
        mav.addObject("videoListDTO", resultList);
        mav.addObject("device", device);

        
        
    	//LOG
    	Activity activity = new Activity(ActivityEnum.USERFAVORITESVIEW, identity,userDTO.getId(),null,null,null,null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
        
        return mav;
    }
    
    
    
    
    
    //USER WATCH LATER
    @RequestMapping(value={"/watchlater/filter:{filter}"}, method=RequestMethod.GET)
    public ModelAndView videosWatchLaterView(Device device,@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@RequestParam(value = "page", required=false) Integer page, @RequestParam(value = "grid", required=false) String grid, @PathVariable String UUID, @PathVariable String filter, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO,@ModelAttribute(WebConstants.CATALOGPAGINATOR) PaginatorDTO paginator) throws Exception {
        ModelAndView mav = new ModelAndView("user-index-watchlater");
        
        //FETCH USER
        UserDTO userDTO = userService.findUserByUUID(UUID);
        CollectionDTO collectionDTO = collService.selectWatchLaterCollection(userDTO.getId(), true);

        //IS FOLLOWING USER
        isFollowingUser(mav,activeUser!=null ? activeUser.getId() : null,userDTO.getId());
        
        //ADD PERSONALIZATION
        if(grid!=null) {
        	persDTO.getPreference().put(PersonalizationDTO.KEY_GRIDSTYLE+"user-index-watchlater", grid);
            mav.addObject(WebConstants.PERSONALIZATION, persDTO);

        }
        
        
        
        //CREATE CATALOG FILTER FOR USER
        VideoCatalogQueryDTO queryDTO = new VideoCatalogQueryDTO(userDTO.getId().toString(), filter, null,null);
        queryDTO.setCollectionid(collectionDTO.getId().toString());
        mav.addObject(WebConstants.VIDCATALOGQUERY,queryDTO);
        
        
        //INTIALIZE PAGINATOR
        if(page==null) {
	        Integer videosCount = videoService.selectCountCollectionVideos(queryDTO);
	        paginator = new PaginatorDTO(videosCount);
	        RowBounds rowBounds = paginator.firstPage();
	        mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
        } else {
        	 paginator.goToPage(page);
        	 mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
        }

        
        
        
        List<PublicVideoDTO> resultList;
        resultList = videoService.selectCollectionVideos(queryDTO,paginator);
        //List videoListPartions = WebUtility.partitionVideoListPublic(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"user-index-videos").toString());
        mav.addObject("videoListDTO", resultList);
        mav.addObject("device", device);

        
        
    	//LOG
    	Activity activity = new Activity(ActivityEnum.USERWATCHLATERVIEW, identity,userDTO.getId(),null,null,null,null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
        
        return mav;
    }

    //USER VIDEOS
    @RequestMapping(value={"/videos/filter:{filter}"}, method=RequestMethod.GET)
    public ModelAndView videosFilterView(Device device,@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@RequestParam(value = "page", required=false) Integer page, @RequestParam(value = "grid", required=false) String grid, @PathVariable String UUID, @PathVariable String filter, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO,@ModelAttribute(WebConstants.CATALOGPAGINATOR) PaginatorDTO paginator) throws Exception {
        ModelAndView mav = new ModelAndView("user-index-videos");
        
        //FETCH USER
        UserDTO userDTO = userService.findUserByUUID(UUID);
        
        //IS FOLLOWING USER
        isFollowingUser(mav,activeUser!=null ? activeUser.getId() : null,userDTO.getId());
        
        //ADD PERSONALIZATION
        if(grid!=null) {
        	persDTO.getPreference().put(PersonalizationDTO.KEY_GRIDSTYLE+"user-index-videos", grid);
            mav.addObject(WebConstants.PERSONALIZATION, persDTO);

        }
        
        //CREATE CATALOG FILTER FOR USER
        VideoCatalogQueryDTO queryDTO = new VideoCatalogQueryDTO(userDTO.getId().toString(), filter, null,null);
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

        
        
        
        List<PublicVideoDTO> resultList;
        resultList = videoService.selectCatalogVideos(queryDTO,paginator);
        //List videoListPartions = WebUtility.partitionVideoListPublic(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"user-index-videos").toString());
        mav.addObject("videoListDTO", resultList);
        mav.addObject("device", device);

        
        
    	//LOG
    	Activity activity = new Activity(ActivityEnum.USERVIDSVIEW, identity,userDTO.getId(),null,null,null,null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
        
        return mav;
    }
        
 
    //USER VIDEOS WITH SUBFILTER
    @RequestMapping(value={"/videos/filter:{filter}/subfilter:{subfilter}"}, method=RequestMethod.GET)
    public ModelAndView videosFilterSubfilterView(Device device,@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@RequestParam(value = "grid", required=false) String grid, @PathVariable String UUID, @PathVariable String filter, @PathVariable String subfilter,  @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) throws Exception {
        ModelAndView mav = new ModelAndView("user-index-videos");

        //ADD PERSONALIZATION
        if(grid!=null) {
        	persDTO.getPreference().put(PersonalizationDTO.KEY_GRIDSTYLE+"user-index-videos", grid);
            mav.addObject(WebConstants.PERSONALIZATION, persDTO);

        }

        
        //FETCH USER
        UserDTO userDTO = userService.findUserByUUID(UUID);
      
        //IS FOLLOWING USER
        isFollowingUser(mav,activeUser!=null ? activeUser.getId() : null,userDTO.getId());
        
        //CREATE CATALOG FILTER FOR USER
        VideoCatalogQueryDTO queryDTO = new VideoCatalogQueryDTO(userDTO.getId().toString(), filter, null,null);
        mav.addObject(WebConstants.VIDCATALOGQUERY,queryDTO);
        

        //INTIALIZE PAGINATOR
        Integer videosCount = videoService.selectCountCatalogVideos(queryDTO);
        PaginatorDTO paginator = new PaginatorDTO(videosCount);
        RowBounds rowBounds = paginator.firstPage();
        mav.addObject(WebConstants.CATALOGPAGINATOR,paginator);
        
        
        
        List<PublicVideoDTO> resultList;
        resultList = videoService.selectCatalogVideos(queryDTO,paginator);
        //List videoListPartions = WebUtility.partitionVideoListPublic(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"user-index-videos").toString());
        
        mav.addObject("videoListDTO", resultList);
        mav.addObject("device", device);

        
        
    	//LOG
    	Activity activity = new Activity(ActivityEnum.USERVIDSVIEW, identity,userDTO.getId(),null,null,null,null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
        
        return mav;
    }

    
}