package com.socialvideo.web.controllers;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
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

import com.socialvideo.config.AWSConfiguration;
import com.socialvideo.constant.QueryConstants;
import com.socialvideo.constant.VideoHealthStatus;
import com.socialvideo.constant.VideoIntegrationStatus;
import com.socialvideo.constant.VideoPublishStatus;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.AdminManagerDTO;
import com.socialvideo.data.dto.AdminQueryDTO;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.EventDTO;
import com.socialvideo.data.dto.IDDescrDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PersonalizationDTO;
import com.socialvideo.data.dto.PublicVideoPointDTO;
import com.socialvideo.data.dto.SystemPropertyDTO;
import com.socialvideo.data.dto.TagDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VideoClaimDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.dto.statistics.StatisticsDTO;
import com.socialvideo.data.dto.youtube.YouTubeResultDTO;
import com.socialvideo.data.services.AWSService;
import com.socialvideo.data.services.AvatarService;
import com.socialvideo.data.services.ChannelService;
import com.socialvideo.data.services.CollectionService;
import com.socialvideo.data.services.ConnectEntityService;
import com.socialvideo.data.services.DatabaseService;
import com.socialvideo.data.services.EmailService;
import com.socialvideo.data.services.IGoogleMapsService;
import com.socialvideo.data.services.IntegrationService;
import com.socialvideo.data.services.PublicVideoService;
import com.socialvideo.data.services.SystemPropertiesService;
import com.socialvideo.data.services.VideoClaimService;
import com.socialvideo.data.services.YouTubeService;
import com.socialvideo.data.services.inter.IUserService;
import com.socialvideo.data.services.inter.IVideoService;
import com.socialvideo.data.services.statistics.StatisticsSevice;
import com.socialvideo.utilities.WebUtility;
import com.socialvideo.web.events.GenericEvent;


@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/admin")

@SessionAttributes({WebConstants.ADMINQUERYFRM, WebConstants.PERSONALIZATION,WebConstants.ADMINPAGINATOR, WebConstants.STATISTICSDTO})
public class AdministrationController {

	 private static final Logger logger = Logger.getLogger(AdministrationController.class);


	 
	 @Autowired
	 protected VideoClaimService claimService;
	 
	 
	 @Autowired
	 protected YouTubeService youtubeService;
	 
	 @Autowired
	 protected AWSService awsService;
	 
	 @Autowired
	 protected IntegrationService integrationService;
	 
	 @Autowired
	 protected EmailService emailService;
	 
	 @Autowired
	 private ApplicationEventPublisher eventPublisher;
	 

	 
	 @Autowired
	 protected IUserService userService;
	 
	 @Autowired
	 protected IVideoService videoService;

	 @Autowired
	 protected PublicVideoService publicVideoService;
	 
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

	 
	 @Autowired
	 protected StatisticsSevice statsService;
	 
	 
	 @Autowired
	 protected SystemPropertiesService systemPropertiesService;
	 
	 @ModelAttribute("rejectReasonDTOList")
     public List<IDDescrDTO> init_REJECTREASONLIST() {
	       return   dbService.getRejectReasonsList();
     }
	 
	 
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
		model.addAttribute(WebConstants.NAVBAR_SHOWSEARCHTXT, true);
		
     }

	 
	 
	 @ModelAttribute(WebConstants.ADMINMANAGERFRM)
     public AdminManagerDTO ADMINMANAGERFRM() {
		 AdminManagerDTO managerDTO = new AdminManagerDTO();
		 	return  managerDTO;
     }

	 @ModelAttribute(WebConstants.ADMINQUERYFRM)
     public AdminQueryDTO ADMINQUERYFRM() {
		 AdminQueryDTO queryDTO = new AdminQueryDTO();
		 	return  queryDTO;
     }
	 
	 @ModelAttribute(WebConstants.ADMINPAGINATOR)
     public PaginatorDTO init_ADMINPAGINATOR() {
		 	PaginatorDTO paginatorDTO = new PaginatorDTO(-1);
	       return  paginatorDTO;
     }
	 	 
	 
	 
	 
	 
    public AdministrationController() {
    }
    
   
    
    //ADMIN VIDEOS
    @RequestMapping(value={"/email-test"}, method=RequestMethod.GET)
    public ModelAndView testEmail() {
        ModelAndView mav = new ModelAndView("admin-video-view");

        emailService.sentTestEmail();
        
        //ADD FILTER TO PAGE
        
        return mav;

    }
    

    
    
    


    
    //ADMIN SYSTEM PROPS
    @RequestMapping(value={"/system/properties"}, method=RequestMethod.GET)
    public ModelAndView systemPropertiesView(@ModelAttribute(WebConstants.ADMINMANAGERFRM) AdminManagerDTO managerDTO) {
        ModelAndView mav = new ModelAndView("admin-systemproperties-view");
             
        //FETCH PROPERTIES
        List<SystemPropertyDTO> resultList  = systemPropertiesService.selectSysPropertiesList();
        mav.addObject("propertiesListDTO", resultList);

        return mav;

    }

    
    
    
    //ADMIN SYSTEM PROPERTIES
    @RequestMapping(value={"/system/properties/submit"}, method=RequestMethod.POST)
    public String systemPropertiesUpdate(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.ADMINMANAGERFRM) AdminManagerDTO managerDTO) {
    	systemPropertiesService.updateSystemPropertyById(managerDTO.getSelecteditem(), activeUser.getId(),managerDTO.getSelecteditemvalue());
    	return "redirect:/admin/system/properties";
    }
    
    
    
    //ADMIN USERS
    @RequestMapping(value={"/users/view:{view}/filter:{filter}"}, method=RequestMethod.GET)
    public ModelAndView usersAdminCatalog(@RequestParam(value = "recalc", required=false) Integer recalc, @RequestParam(value = "grid", required=false) String grid, @RequestParam(value = "page", required=false) String pageid, @PathVariable String view, @PathVariable String filter, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.ADMINMANAGERFRM) AdminManagerDTO managerDTO, @ModelAttribute(WebConstants.ADMINPAGINATOR) PaginatorDTO paginator, @ModelAttribute(WebConstants.ADMINQUERYFRM) AdminQueryDTO queryDTO) {
        ModelAndView mav = new ModelAndView("admin-user-view");

        boolean recalculate = true;

        
        
        //PERSONALIZATION UPDATE VIEW FILTER GRID
        persDTO.updateFilterViewGrid(filter,view,grid,pageid,"admin-user-view");
        mav.addObject(WebConstants.PERSONALIZATION, persDTO);

        //UPDATE CATALOG FILTER 
        queryDTO.setFilter(filter);
        queryDTO.setView(view);

        
        //INTIALIZE PAGINATOR
        if(recalculate) {
        	Integer usersCount = userService.selectCountAdminCatalogUsers(queryDTO);
        	paginator = new PaginatorDTO(usersCount);
        	RowBounds rowBounds = paginator.firstPage();
        }
        
        if(pageid!=null)
        	paginator.goToPage(Integer.parseInt(pageid));
        
        mav.addObject(WebConstants.ADMINPAGINATOR,paginator);
        
             
        //FETCH USERS AND PARTITION PER DATE
        List<UserDTO> resultList  = userService.selectAdminCatalogUsers(queryDTO, paginator);
        
        
        if(view.equalsIgnoreCase("registered")) {
        	
        	
        	
	        Map<Integer, List<UserDTO>> resultListPerDayMap = resultList.parallelStream().collect(Collectors.groupingBy(x -> x.getDatenumber()));
	        
	        Map<Integer, List<UserDTO>> sortedResultListPerDayMap =  resultListPerDayMap.entrySet().parallelStream()
	        		.sorted((x1,x2) -> (-1)*x1.getKey().compareTo(x2.getKey()))
	        		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new));
	    
	        mav.addObject("userListDTO", sortedResultListPerDayMap);
        }
        
        else {
            mav.addObject("userListDTO", resultList);
        	
        }

     
        
        return mav;

    }

    
    
    //FILTER AND REDIRECT TO ADMIN VIDEOS PAGE
    @RequestMapping(value={"/videos/filter/get"}, method=RequestMethod.GET)
    public String videosCatalogFilterGet(Model model, @ModelAttribute(WebConstants.ADMINQUERYFRM) AdminQueryDTO queryDTO,@RequestParam(value = "videoid", required=false) String videoid) {
       //UPDATE SESSION WITH QUERY DTO
    	queryDTO.getAdvfilter().setVideoid(videoid);
        model.addAttribute(WebConstants.ADMINQUERYFRM, queryDTO);
    	return "redirect:/admin/videos/view:"+queryDTO.getView()+"/filter:"+queryDTO.getFilter()+"?recalc=1";
    }

    
    
    //REGENERATE INTEGRATION DATA AND THUMBNAIL AND REDIRECT TO VIDEO CATALOG PAGE
    @RequestMapping(value={"/videos/integration/regenerate"}, method=RequestMethod.GET)
    public String regenerateIntegrationData(Model model, @ModelAttribute(WebConstants.ADMINQUERYFRM) AdminQueryDTO queryDTO,@RequestParam(value = "videoid", required=false) String videoid) {

    	Exception  intException = null;
    	
    	try {
    		
    		intException = integrationService.integrateDataFromVimeoYoutube(Long.parseLong(videoid),false);
    		model.addAttribute("integrationException",intException);
    		VideoDTO videoDTO = videoService.findVideoById(Long.parseLong(videoid));
    		integrationService.createVideoThumbImg(videoDTO);
  		  
    		
    		
    	} catch (Exception e) {
    		  e.printStackTrace();	
    		  model.addAttribute("shareThumbException", e);
    		  
    		
    	}
    	
    	return "redirect:/admin/videos/view:"+queryDTO.getView()+"/filter:"+queryDTO.getFilter()+"?videoid="+videoid+"&recalc=0";
    }

    
    
    
    //FILTER AND REDIRECT TO ADMIN VIDEOS PAGE
    @RequestMapping(value={"/videos/filter"}, method=RequestMethod.POST)
    public String videosCatalogFilter(Model model, @ModelAttribute(WebConstants.ADMINQUERYFRM) AdminQueryDTO queryDTO) {
       //UPDATE SESSION WITH QUERY DTO
        model.addAttribute(WebConstants.ADMINQUERYFRM, queryDTO);
    	return "redirect:/admin/videos/view:"+queryDTO.getView()+"/filter:"+queryDTO.getFilter()+"?recalc=1";
    }

    
    //ADMIN VIDEOS
    @RequestMapping(value={"/videos/view:{view}/filter:{filter}"}, method=RequestMethod.GET)
    public ModelAndView videosAdminCatalog(Device device,@RequestParam(value = "recalc", required=false) Integer recalc,  @RequestParam(value = "videoid", required=false) Integer videoid, @RequestParam(value = "grid", required=false) String grid, @RequestParam(value = "page", required=false) String pageid, @PathVariable String view, @PathVariable String filter, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.ADMINMANAGERFRM) AdminManagerDTO managerDTO, @ModelAttribute(WebConstants.ADMINPAGINATOR) PaginatorDTO paginator, @ModelAttribute(WebConstants.ADMINQUERYFRM) AdminQueryDTO queryDTO) {
        ModelAndView mav = new ModelAndView("admin-video-view");


        //ADD VIDEO ID OF PREVIOUS EDIT
        mav.addObject("previousEditVideoID", videoid==null ? -1 : videoid);

        Long startTime = System.currentTimeMillis();

        boolean recalculate = true;
/*        if (recalc!=null && recalc==1)
        	recalculate = true;
        */
        
        //GET STATISTICS FOR STATUS OF ALL VIDEOS
        if(recalculate) {
        StatisticsDTO statisticsDTO = new StatisticsDTO(); 
        //StatisticsDTO(statsService.selectAdminVideoAllStatus(queryDTO));
        mav.addObject(WebConstants.STATISTICSDTO,statisticsDTO);
        }
        
        Long stopA = System.currentTimeMillis();
        System.out.println("Statistics Calculation in Secs:"+(startTime-stopA)/1000);
        
        

        
        //PERSONALIZATION UPDATE VIEW FILTER GRID
        persDTO.updateFilterViewGrid(filter,view,grid,pageid!=null ? pageid : "1","admin-video-view");
        mav.addObject(WebConstants.PERSONALIZATION, persDTO);
        
        
        
        
        
        //TURN ON FLAG IF VIEW IS DIFFERENT FROM THE PREVIOUS VIEW
        boolean viewChanged = false;
        if(queryDTO.getView()==null || queryDTO.getView().equalsIgnoreCase(view))
        	viewChanged = true;
        
        
        //UPDATE CATALOG FILTER 
        queryDTO.setFilter(filter);
        queryDTO.setView(view);
        
        
        //RESET FILTERS
        queryDTO.getAdvfilter().setMarkasunwanted(false);
        queryDTO.getAdvfilter().setPromotesiteindex(false); 
        queryDTO.getAdvfilter().setPromotemap(false);
        queryDTO.setFilter("all");
        queryDTO.getAdvfilter().setHealthstatus(null);
        queryDTO.getAdvfilter().setStatus(null);
        
        
        if(view.equalsIgnoreCase("published")) {queryDTO.setFilter("lastapproved"); queryDTO.getAdvfilter().setStatus(VideoPublishStatus.PUBLISHED);  queryDTO.getAdvfilter().setHealthstatus(VideoHealthStatus.HEALTHY);}
        else if(view.equalsIgnoreCase("unpublished")) { queryDTO.getAdvfilter().setStatus(VideoPublishStatus.UNPUBLISHED);  queryDTO.getAdvfilter().setHealthstatus(VideoHealthStatus.HEALTHY);}
        else if(view.equalsIgnoreCase("uploaded")) {queryDTO.getAdvfilter().setStatus(VideoPublishStatus.JUSTUPLOADED);}
        else if(view.equalsIgnoreCase("youtubetooluploaded")) {queryDTO.getAdvfilter().setStatus(VideoPublishStatus.YOUTUBETOOLUPLOADED);}
        else if(view.equalsIgnoreCase("exceluploaded")) {queryDTO.getAdvfilter().setStatus(VideoPublishStatus.EXCELUPLOADED); }
        else if(view.equalsIgnoreCase("approved")) {queryDTO.getAdvfilter().setStatus(VideoPublishStatus.APPROVED);}
        else if(view.equalsIgnoreCase("staging")) { queryDTO.getAdvfilter().setStatus(VideoPublishStatus.STAGING);}
        else if(view.equalsIgnoreCase("rejected")) {queryDTO.getAdvfilter().setStatus(VideoPublishStatus.REJECTED); queryDTO.getAdvfilter().setHealthstatus(VideoHealthStatus.HEALTHY);}
        else if(view.equalsIgnoreCase("broken")) {queryDTO.getAdvfilter().setHealthstatus(VideoHealthStatus.BROKEN);}
        else if(view.equalsIgnoreCase("unwanted")) {queryDTO.getAdvfilter().setMarkasunwanted(true);}
        else if(view.equalsIgnoreCase("promotesiteindex")) {queryDTO.getAdvfilter().setPromotesiteindex(true); queryDTO.getAdvfilter().setPromotemap(false); queryDTO.getAdvfilter().setStatus(VideoPublishStatus.PUBLISHED); queryDTO.getAdvfilter().setHealthstatus(VideoHealthStatus.HEALTHY);}
        else if(view.equalsIgnoreCase("promotemap")) {queryDTO.getAdvfilter().setPromotemap(true); queryDTO.getAdvfilter().setPromotesiteindex(false); queryDTO.getAdvfilter().setStatus(VideoPublishStatus.PUBLISHED); queryDTO.getAdvfilter().setHealthstatus(VideoHealthStatus.HEALTHY);}
        else {
        	queryDTO.getAdvfilter().setHealthstatus(null);
        	queryDTO.getAdvfilter().setStatus(null);
        }
        mav.addObject(WebConstants.ADMINQUERYFRM, queryDTO);

        


        if(viewChanged) {
        //INTIALIZE PAGINATOR
        Integer videosCount = videoService.selectCountAdminCatalogVideos(queryDTO);
        paginator = new PaginatorDTO(videosCount,10);
        RowBounds rowBounds = paginator.firstPage();

        }
    
        Long stopB = System.currentTimeMillis();
        System.out.println("Paginator Init in Secs:"+(stopB-stopA)/1000);
        
    
        if(pageid!=null)
        	paginator.goToPage(Integer.parseInt(pageid));
        
        mav.addObject(WebConstants.ADMINPAGINATOR,paginator);
        
    
        
        
        
        //FETCH VIDEOS AND PARTITION
        List<VideoDTO> resultList;
        resultList  =videoService.selectAdminCatalogVideos(queryDTO,paginator);
        
        
        Long stopC = System.currentTimeMillis();
        System.out.println("Fetch Result in Secs:"+(stopC-stopB)/1000);

        
        
        List videoListPartions = WebUtility.privatePartitionVideoList(device,resultList,persDTO.getPreference().get(PersonalizationDTO.KEY_GRIDSTYLE+"admin-video-view").toString());
        mav.addObject("videoListDTO", videoListPartions);
        
        Long stopD = System.currentTimeMillis();
        System.out.println("Partition Result in Secs:"+(stopD-stopC)/1000);
        
        //INITIALIZE MANAGER
    	//managerDTO.initvideos(resultList);
    	mav.addObject(WebConstants.ADMINMANAGERFRM, managerDTO);
        
        Long stopE = System.currentTimeMillis();
        System.out.println("Manager Init in Secs:"+(stopE-stopD)/1000);
    	
    	mav.addObject(WebConstants.PERFOMANCETIME, stopD-stopA);
        
        return mav;

    }
    
    
    //VIDEO SETTINGS
    @RequestMapping(value={"/video-settings/{videoid}"}, method=RequestMethod.GET)
    public ModelAndView videosFilterView(@AuthenticationPrincipal CurrentUserDTO activeUser, @PathVariable Long videoid) {
        ModelAndView mav = new ModelAndView("admin-video-settings");
        
        UserDTO userDTO = activeUser.getUser();
        
        //SELECT SELECTED VIDEO
        VideoDTO video = videoService.findVideoById(videoid);
        List<IDDescrDTO> videoTypeList = dbService.lookupVideoTypeListOfVideo(videoid);
        video.setVideoTypeList(videoTypeList);
        
        //SET SELECTED  VIDEO TYPES LIST
        //String selectedVideoTypesCommaString = videoTypeList!=null ? video.getVideoTypeList().stream().map(i -> i.getId().toString()).collect(Collectors.joining(", ")) : "";
        String selectedVideoTypesCommaString ="";
        if(videoTypeList!=null) {
        	for (IDDescrDTO current : videoTypeList) {
        		selectedVideoTypesCommaString+=current.getId()+",";
        	}
        	
        	try {
        	selectedVideoTypesCommaString = selectedVideoTypesCommaString.substring(0, selectedVideoTypesCommaString.length()-1);
        	} catch (Exception e) {}
        	
        }
        
        video.setSelectedvideotypelist(selectedVideoTypesCommaString);

        
        
    	//FETCH NEARBY VIDEO POINTS AND IF NOT FOUND FETCH TAG MATCHED VIDEOS
        PaginatorDTO paginator = new PaginatorDTO(100, 10);
        paginator.firstPage();
        List<PublicVideoPointDTO> relatedPointsList = publicVideoService.selectNearyByVideoPoints(video.getId(),video.getLatitude().toString(),video.getLongitude().toString(), QueryConstants.NEARBYRANGE,paginator);
	   	mav.addObject("relatedPointListDTO", relatedPointsList);
        
        
        mav.addObject(WebConstants.VIDEOSETTINGSFRM, video);
        
        
        //ADD TAG LIST
        List<TagDTO> tagList = dbService.getAllTagsList(20);
        mav.addObject("tagDTOList",tagList);
        
        
        
        
        
        
        return mav;
    }
    
    
    //VIDEO SETTINGS SUBMIT
    @RequestMapping(value={"/video-settings/{videoid}/edit-submit"}, method=RequestMethod.POST)
    public String videoEdit(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.ADMINQUERYFRM) AdminQueryDTO queryDTO, @RequestParam(value = "redirect", required=false) String redirectParam,@PathVariable Long videoid, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.VIDEOSETTINGSFRM) VideoDTO videoDTOForm) {
        ModelAndView mav = new ModelAndView("admin-video-settings");
	 	String selectedVideoTypesCommaString = videoDTOForm.getSelectedvideotypelist();

	 	
	    //FETCH VIDEO AND CHECK LINKEDURL
        String  oldLinkedURL = videoService.findVideoById(videoDTOForm.getId()).getLinkedurl();
	 	
        //EXTRACT WEATHER STRING FROM BOOLEAN MAP
	    //videoDTOForm.initWeatherString();
	    
	 	
	 	//SET MARKER IMG ID
		if(selectedVideoTypesCommaString.trim().length()>0) {
	    	 String[] items = selectedVideoTypesCommaString.split(",");
	    	 videoDTOForm.setVideotypeids(selectedVideoTypesCommaString);
	    	 videoDTOForm.setMarkerimgid(items[0]);
		}
	 	
	 	
	 	
	    videoService.updateAdminVideo(videoDTOForm,activeUser.getId(),true);
        
	    
	    
	    //IF OLD LINKED URL DIFFERENT FROM NEW LINKED URL THEN UPDATE INTEGRATION DATA
	    if(!videoDTOForm.getLinkedurl().equalsIgnoreCase(oldLinkedURL)) {
	    	integrationService.integrateDataFromVimeoYoutube(videoDTOForm.getId(),false);
	    	try {
	    		integrationService.createVideoThumbImg(videoService.findVideoById(videoDTOForm.getId()));
	    	} catch (Exception e) {e.printStackTrace();}
	    }
	    
	    
	    
	    
	    /***************UPDATE VIDEO TYPES*****************************************/
	    if(selectedVideoTypesCommaString.trim().length()>0) {
		    videoService.clearVideoTypesListOfVideo(videoDTOForm.getId());
		    
		    
	    	 String[] items = selectedVideoTypesCommaString.split(",");
	         List<String> videoTypesIDList = Arrays.asList(items);

		    for (String videotypeid : videoTypesIDList) {
		    	try {
		    	videoService.insertVideoTypesListOfVideo(videoDTOForm.getId(), Long.parseLong(videotypeid));
		    	} catch (Exception e) {e.printStackTrace();}
		    }
	    }

	    /***************UPDATE VIDEO TYPES*****************************************/
	    
	    
	    String redirectURI = redirectParam==null ? "redirect:/admin/videos/view:"+queryDTO.getView()+"/filter:"+queryDTO.getFilter()+"?videoid="+videoDTOForm.getId() : "redirect:"+redirectParam;
	    
	     
	    
	    //SAVE AND APPROVE
	    if(videoDTOForm.getSaveandapprove()) {
	    	
	    	 redirectURI =  redirectParam==null ? "redirect:/admin/videos/view:"+queryDTO.getView()+"/filter:"+queryDTO.getFilter()+"?videoid="+videoDTOForm.getId()  : "redirect:"+redirectParam;
    		
	    	//GET VIDEO
        	VideoDTO video = videoService.findVideoById(videoDTOForm.getId());
   			UserDTO owner = userService.findUserById(video.getUser().getId());
	   		
   			//UPDATE VIDEO
   			videoService.approveVideo(video.getId(),activeUser.getId());

   			//SENT EMAIL AND POST A COMMENT
			try {
			if(!video.getIsfile()) {
				emailService.videoApprovedEmail(video.getUser().getEmail(), video,owner);
			    //IF ITS IS A YOUTUBE VIDEO THEN POST A COMMENT TO CHANNEL
			    if(video.getYoutube()) {
			    	integrationService.postInitialTerrabisCommentToYoutube(null, video);
			    }
			    
			}
			} catch (Exception e) {e.printStackTrace();}	    	

	    }
        
        return redirectURI;

    }
    
    
    
    @RequestMapping(value={"/video/reject"}, method=RequestMethod.POST)
    public String videoReject(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.ADMINMANAGERFRM) AdminManagerDTO managerDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) {

    
    	
   
    	
    	
    	if(managerDTO.singleselection) {
    		//GET VIDEO
        	VideoDTO video = videoService.findVideoById(managerDTO.getSelectedvideo());
        	UserDTO owner = userService.findUserById(video.getUser().getId());
			videoService.rejectVideo(managerDTO.getSelectedvideo(),activeUser.getId(),managerDTO.getRejectionreason(), managerDTO.getRejectionreasontxt());
			System.out.println("REJECTION REASON:"+managerDTO.getRejectionreasontxt());
			
			
			//SENT EMAIL
			try {
			String rejectionReasonText = "";
			if ((managerDTO.getRejectionreasontxt()!=null) &&  (managerDTO.getRejectionreasontxt().trim().length()>0))	 {
				rejectionReasonText =  managerDTO.getRejectionreasontxt();
				System.out.println("REJECTION REASON:"+rejectionReasonText);
				//GET REJECTION REASON DESCRIPTION
						
			}
			else {
				rejectionReasonText = dbService.lookupRejectionReason(managerDTO.getRejectionreason()).getDescr();		

			}
			
			
			
			
			
			emailService.videoRejectEmail(video.getUser().getEmail(), video, owner,rejectionReasonText);
			} catch (Exception e) {e.printStackTrace();}

			
			//PUBLISH EVENT
    	    //eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),managerDTO.getSelectedvideo(), null,EventDTO.VID_REJECTED)));
    	}
    		
    	else {
    	    	for(Entry<Long, Boolean> videntry : managerDTO.getSelectedvideos().entrySet()) {
    	    		logger.info("reject selectedvideo:"+videntry.getKey()+" - value:"+videntry.getValue());
    	    		Long videoid = videntry.getKey();
    	    		if(videntry.getValue()) {
    	    			
    	    			//GET VIDEO
    	    	    	VideoDTO video = videoService.findVideoById(videoid);
    	    	    	UserDTO owner = userService.findUserById(video.getUser().getId());
    	    	    	
    	    			videoService.rejectVideo(videoid,activeUser.getId(), managerDTO.getRejectionreason(),managerDTO.getRejectionreasontxt());
    	    			
    	    			
    	    			//SENT EMAIL
    	    			try {
    	    				
    	    				String rejectionReason = "";
    	    				if(managerDTO.getRejectionreason()!=-1)	 {
    	    				 	//GET REJECTION REASON DESCRIPTION
    	    					rejectionReason = dbService.lookupRejectionReason(managerDTO.getRejectionreason()).getDescr();				
    	    				}
    	    				else {
    	    					rejectionReason =  managerDTO.getRejectionreasontxt();
    	    				}
    	    				
    	    			emailService.videoRejectEmail(video.getUser().getEmail(), video, owner,rejectionReason);
    	    			} catch (Exception e) {e.printStackTrace();}
    	    			}
    	    			
    	    			
    	    			//PUBLISH EVENT
	    	    	    //eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),videoid, null,EventDTO.VID_REJECTED)));

    	    		}
    	}
	    	    	
    	
      return "redirect:/admin/videos/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter()+"?recalc=0";
    }    
    
    
    @RequestMapping(value={"/video/promote:{promote}/type:{promotetype}"}, method=RequestMethod.POST)
    public String videoPromoteAndUnpromote(@PathVariable String promote, @PathVariable String promotetype, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.ADMINMANAGERFRM) AdminManagerDTO managerDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) {
    	Boolean promoteFlag =false;
    	
    	if(promote.equalsIgnoreCase("promote"))
    		promoteFlag = true;
    	
    	
    	
    	if(managerDTO.singleselection) {
    		if(promotetype.equalsIgnoreCase("siteindex"))
    			videoService.promoteVideoToSiteIndex(managerDTO.getSelectedvideo(), promoteFlag);
    		else if(promotetype.equalsIgnoreCase("map"))
    			videoService.promoteVideoToMap(managerDTO.getSelectedvideo(), promoteFlag);
			
			//PUBLISH EVENT
    	    //eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),managerDTO.getSelectedvideo(), null,EventDTO.VID_UNPUBLISHED)));
    	}
    		
    	else {

    	
    	    	for(Entry<Long, Boolean> videntry : managerDTO.getSelectedvideos().entrySet()) {
    	    		logger.info((promoteFlag ? "promote to " : "unpromote from ") + promotetype + " selectedvideo:"+videntry.getKey()+" - value:"+videntry.getValue());
    	    		Long videoid = videntry.getKey();
    	    		if(videntry.getValue()) {
    	        		if(promotetype.equalsIgnoreCase("siteindex"))
    	        			videoService.promoteVideoToSiteIndex(videoid, promoteFlag);
    	        		else if(promotetype.equalsIgnoreCase("map"))
    	        			videoService.promoteVideoToMap(videoid, promoteFlag);
    	    			
    	    			//PUBLISH EVENT
	    	    	    //eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),videoid, null,EventDTO.VID_UNPUBLISHED)));

	    	    		}
    	    		}
    	}
	    	    	
    	
    	return "redirect:/admin/videos/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter()+"?recalc=0";

    }
    
    
    
    @RequestMapping(value={"/video/unpublish"}, method=RequestMethod.POST)
    public String videoUnpublish(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.ADMINMANAGERFRM) AdminManagerDTO managerDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) {
    	if(managerDTO.singleselection) {
			videoService.updateVideoStatus(managerDTO.getSelectedvideo(),activeUser.getId(),VideoPublishStatus.UNPUBLISHED);
			
			//PUBLISH EVENT
    	    eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),managerDTO.getSelectedvideo(), null,EventDTO.VID_UNPUBLISHED)));
    	}
    		
    	else {

    	
    	    	for(Entry<Long, Boolean> videntry : managerDTO.getSelectedvideos().entrySet()) {
    	    		logger.info("unpublish selectedvideo:"+videntry.getKey()+" - value:"+videntry.getValue());
    	    		Long videoid = videntry.getKey();
    	    		if(videntry.getValue()) {
    	    			videoService.updateVideoStatus(videoid,activeUser.getId(),VideoPublishStatus.UNPUBLISHED);
    	    			
    	    			//PUBLISH EVENT
	    	    	    eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),videoid, null,EventDTO.VID_UNPUBLISHED)));

	    	    		}
    	    		}
    	}
	    	    	
    	
    	return "redirect:/admin/videos/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter()+"?recalc=0";

    }
    
    
    @RequestMapping(value={"/video/publish"}, method=RequestMethod.POST)
    public String videoPublish(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.ADMINMANAGERFRM) AdminManagerDTO managerDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) {

    	if(managerDTO.singleselection) {
			videoService.updateVideoStatus(managerDTO.getSelectedvideo(),activeUser.getId(),VideoPublishStatus.PUBLISHED);
			
			//PUBLISH EVENT
    	    eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),managerDTO.getSelectedvideo(), null,EventDTO.VID_PUBLISHED)));
    	}
    		
    	else {

    	
    	    	for(Entry<Long, Boolean> videntry : managerDTO.getSelectedvideos().entrySet()) {
    	    		logger.info("publish selectedvideo:"+videntry.getKey()+" - value:"+videntry.getValue());
    	    		Long videoid = videntry.getKey();
    	    		if(videntry.getValue()) {
    	    			videoService.updateVideoStatus(videoid,activeUser.getId(),VideoPublishStatus.PUBLISHED);
    	    			
    	    			//PUBLISH EVENT
	    	    	    eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),videoid, null,EventDTO.VID_PUBLISHED)));

	    	    		}
    	    		}
    	}
	    	    	
    	
    	return "redirect:/admin/videos/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter()+"?recalc=0";
    }
    
    
   @RequestMapping(value={"/video/approve"}, method=RequestMethod.POST)
    public String videoApprove(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.ADMINMANAGERFRM) AdminManagerDTO managerDTO,@ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) {

	   	if(managerDTO.singleselection) {
	   		
	   			//GET VIDEO
	   			VideoDTO video = videoService.findVideoById(managerDTO.getSelectedvideo());
	   			UserDTO owner = userService.findUserById(video.getUser().getId());
	   		
	   			//UPDATE VIDEO
				videoService.approveVideo(managerDTO.getSelectedvideo(),activeUser.getId());
				
				//SENT EMAIL AND POST A COMMENT
				try {
				if(!video.getIsfile()) {	
						emailService.videoApprovedEmail(video.getUser().getEmail(), video,owner);


				    //IF ITS IS A YOUTUBE VIDEO THEN POST A COMMENT TO CHANNEL
				    if(video.getYoutube()) {
				    	integrationService.postInitialTerrabisCommentToYoutube(null, video);

				    }
				    
				}
				} catch (Exception e) {e.printStackTrace();}
				
			    
				   

				
				
				//PUBLISH EVENT
				//eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),managerDTO.getSelectedvideo(), null,EventDTO.VID_APPROVED)));
	   	}
	   		
	   	else {

    	    	for(Entry<Long, Boolean> videntry : managerDTO.getSelectedvideos().entrySet()) {
    	    		logger.info("approve selectedvideo:"+videntry.getKey()+" - value:"+videntry.getValue());
    	    		Long videoid = videntry.getKey();
    	    		if(videntry.getValue()) {
    	    			
    		   			//GET VIDEO
    		   			VideoDTO video = videoService.findVideoById(videoid);
    		   			UserDTO owner = userService.findUserById(video.getUser().getId());
    	    			
    	    			videoService.approveVideo(videoid,activeUser.getId());
    	    			
    	    			//SENT EMAIL AND POST A COMMENT
    					try {
    						if(!video.getIsfile()) { 
    								emailService.videoApprovedEmail(video.getUser().getEmail(), video,owner);
    
    							//IF ITS IS A YOUTUBE VIDEO THEN POST A COMMENT TO CHANNEL
    	    				    if(video.getYoutube()) {
    	    				    	integrationService.postInitialTerrabisCommentToYoutube(null, video);

    	    				    }
    						}
    						
    					} catch (Exception e) {e.printStackTrace();}

    	    			
    
    					
    					
    	    			//PUBLISH EVENT
	    	    	    eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),videoid, null,EventDTO.VID_APPROVED)));

	    	    		}
    	    		}
	   	}
	    	    	
    	
	   	return "redirect:/admin/videos/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter()+"?recalc=0";

    }
   
   
   
   @RequestMapping(value={"/video/delete"}, method=RequestMethod.POST)
   public String videoDelete(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.ADMINMANAGERFRM) AdminManagerDTO managerDTO,@ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) throws Exception  {
	   
	   
   	if(managerDTO.singleselection) {
   		VideoDTO videoDTO =   videoService.findVideoById(managerDTO.getSelectedvideo());
   		if(videoDTO.getIsfile()) {
   			//IF IS FILE AND S3 HAVES IT THEN DELETE IT FROM S3 STORAGE
   			if(videoDTO.getIntegrationstatus()==VideoIntegrationStatus.S3 || videoDTO.getIntegrationstatus()==VideoIntegrationStatus.S3_YOUTUBE)
   				try {
   				awsService.deleteFile(AWSConfiguration.USERSVIDEOBUCKET, videoDTO.getId()+"."+videoDTO.getExtension());
   				} catch (Exception e) {
   					e.printStackTrace();
   					throw e;
   				}
   				
   			//DELETE IT FROM YOUTUBE
   			if(videoDTO.getIntegrationstatus()==VideoIntegrationStatus.S3_YOUTUBE || videoDTO.getIntegrationstatus()==VideoIntegrationStatus.YOUTUBE_GLACIER)
   				try {
   					youtubeService.deleteVideoFromTerrabisChannel(videoDTO);
   				} catch (Exception e) {
   					e.printStackTrace();
   					throw e;
   				}
   		}
   			
   			
   			
   		//DELETE IT FROM THE DATABASE	
   		videoService.deleteVideo(null, videoDTO.getId());
   			
		//PUBLISH EVENT
   	    //eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),managerDTO.getSelectedvideo(), null,EventDTO.VID_DELETED)));
   	}
   		
   	else {

	   
   	    	for(Entry<Long, Boolean> videntry : managerDTO.getSelectedvideos().entrySet()) {
   	    		logger.info("delete selectedvideo:"+videntry.getKey()+" - value:"+videntry.getValue());
   	    		Long videoid = videntry.getKey();
   	    		if(videntry.getValue()) {
   	    			VideoDTO videoDTO =   videoService.findVideoById(videoid);
   	    	   		if(videoDTO.getIsfile()) {
   	    	   			//IF IS FILE AND S3 HAVES IT THEN DELETE IT FROM S3 STORAGE
   	    	   			if(videoDTO.getIntegrationstatus()==VideoIntegrationStatus.S3 || videoDTO.getIntegrationstatus()==VideoIntegrationStatus.S3_YOUTUBE)
   	    	   				try {
   	    	   				awsService.deleteFile(AWSConfiguration.USERSVIDEOBUCKET, videoDTO.getId()+"."+videoDTO.getExtension());
   	    	   				} catch (Exception e) {
   	    	   					throw e;
   	    	   				}
   	    	   				
   	    	   			//DELETE IT FROM YOUTUBE
   	    	   			if(videoDTO.getIntegrationstatus()==VideoIntegrationStatus.S3_YOUTUBE || videoDTO.getIntegrationstatus()==VideoIntegrationStatus.YOUTUBE_GLACIER)
   	    	   				try {
   	    	   					youtubeService.deleteVideoFromTerrabisChannel(videoDTO);
   	    	   				} catch (Exception e) {
   	    	   					throw e;
   	    	   				}
   	    	   		}
   	    			
   	    			
   	    			
   	    			
   	    			//DELETE VIDEO FROM DATABASE
   	    			videoService.deleteVideo(null, videoid);
   	    			
   	    			//REMOVE IT FROM ALL COLLECTIONS
   	    			cntService.videoRemoveFromAllCollections(videoid);
   	    			
	    	    	//PUBLISH EVENT
	    	    	//eventPublisher.publishEvent(new GenericEvent(new EventDTO(activeUser.getId(),videoid, null,EventDTO.VID_DELETED)));

	    	    }
   	    }
   	}
	    	    	
   	
   	return "redirect:/admin/videos/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter()+"?recalc=0";

   }

   
   
   
   
   @RequestMapping(value={"/user/{action}"}, method=RequestMethod.POST)
   public String userAdminDynamicAction(@PathVariable String action, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.ADMINMANAGERFRM) AdminManagerDTO managerDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO) {
   	if(managerDTO.singleselection) { 
   				   		switch(action) {
				   			case "enable": userService.enableUser(managerDTO.getSelecteditem()); break;
				 			case "disable": userService.disableUser(managerDTO.getSelecteditem()); break;
							  
				   		}
				   			
   			
   	}

   	else {
   	List<String> selectedUsersList =  Arrays.asList(managerDTO.getSelecteditemslist());	
   	selectedUsersList.stream().forEach( x -> System.out.println("selected item:"+x));
   	
   	
   	for(String userID : selectedUsersList) {
	   		switch(action) {
				case "enable": userService.enableUser(new Long(userID)); break;
				case "disable": userService.disableUser(new Long(userID)); break;
			}
   	}
   	
   	
   	}
   		
   	
   	return "redirect:/admin/users/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter()+"?recalc=0";

   }
   
   
   
   
   
   //ADMIN USERS
   @RequestMapping(value={"/claims/view:{view}/filter:{filter}"}, method=RequestMethod.GET)
   public ModelAndView claimsAdminCatalog(@RequestParam(value = "recalc", required=false) Integer recalc, @RequestParam(value = "grid", required=false) String grid, @RequestParam(value = "page", required=false) String pageid, @PathVariable String view, @PathVariable String filter, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO, @ModelAttribute(WebConstants.ADMINMANAGERFRM) AdminManagerDTO managerDTO, @ModelAttribute(WebConstants.ADMINPAGINATOR) PaginatorDTO paginator, @ModelAttribute(WebConstants.ADMINQUERYFRM) AdminQueryDTO queryDTO) {
       ModelAndView mav = new ModelAndView("admin-claim-view");

       boolean recalculate = true;
    
       
       
       //PERSONALIZATION UPDATE VIEW FILTER GRID
       persDTO.updateFilterViewGrid(filter,view,grid,pageid,"admin-claim-view");
       mav.addObject(WebConstants.PERSONALIZATION, persDTO);

       //UPDATE CATALOG FILTER 
       queryDTO.setFilter(filter);
       queryDTO.setView(view);

       
       //INTIALIZE PAGINATOR
       if(recalculate) {
       	Integer usersCount = claimService.selectCountAdminClaims(queryDTO);
       	paginator = new PaginatorDTO(usersCount);
       	RowBounds rowBounds = paginator.firstPage();
       }
       
       if(pageid!=null)
       	paginator.goToPage(Integer.parseInt(pageid));
       
       mav.addObject(WebConstants.ADMINPAGINATOR,paginator);
       
            
       //FETCH USERS AND PARTITION PER DATE
       List<VideoClaimDTO> resultList  = claimService.selectAdminClaims(queryDTO, paginator);
      mav.addObject("claimsListDTO", resultList);
       	
       
       return mav;

   }


   
   
   //FILTER AND REDIRECT TO CLAIM VIDEOS PAGE
   @RequestMapping(value={"/claims/filter"}, method=RequestMethod.POST)
   public String claimsCatalogFilter(Model model, @ModelAttribute(WebConstants.ADMINQUERYFRM) AdminQueryDTO queryDTO) {
      //UPDATE SESSION WITH QUERY DTO
       model.addAttribute(WebConstants.ADMINQUERYFRM, queryDTO);
   	return "redirect:/admin/claims/view:"+queryDTO.getView()+"/filter:"+queryDTO.getFilter()+"?recalc=1";
   }
   

   
   
   @RequestMapping(value={"/claims/force-claim"}, method=RequestMethod.POST)
   public String claimForceClaim(@AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.ADMINMANAGERFRM) AdminManagerDTO managerDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO,@ModelAttribute(WebConstants.ADMINPAGINATOR) PaginatorDTO paginator) {

   	if(managerDTO.singleselection) {
   		
   			
   		//FIND CLAIM
   		VideoClaimDTO claimDTO = claimService.findClaimByID(managerDTO.getSelecteditem());
   		VideoDTO video = videoService.findVideoById(claimDTO.getVideoid());
		UserDTO claimer = userService.findUserById(claimDTO.getClaimerid());
		UserDTO uploader = userService.findUserById(claimDTO.getUserid());
		

		claimService.verifyOwnership(claimDTO.getClaimerid(), uploader.getId(),claimDTO.getVideoid());
		claimService.claimVerifiedSuccess(claimDTO.getId());
		try {
			emailService.videoClaimSucessEmailToClaimer(claimer.getEmail(), video, claimer, uploader);
			emailService.videoClaimSucessEmailToOriginalUploader(uploader.getEmail(), video, claimer, uploader);
			} catch (Exception e) {e.printStackTrace();}


	}
	    	
   
   	
   	return "redirect:/admin/claims/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter()+"?page="+(paginator.getCurrentPage()+1);
   }
   

   
   @RequestMapping(value={"/claims/delete"}, method=RequestMethod.GET)
   public String claimDelete(@AuthenticationPrincipal CurrentUserDTO activeUser, @RequestParam(value = "claimid", required=false) Long claimid, @ModelAttribute(WebConstants.ADMINMANAGERFRM) AdminManagerDTO managerDTO, @ModelAttribute(WebConstants.PERSONALIZATION) PersonalizationDTO persDTO,@ModelAttribute(WebConstants.ADMINPAGINATOR) PaginatorDTO paginator) {

   		

		claimService.deleteClaim(claimid);
	
   	
   	return "redirect:/admin/claims/view:"+persDTO.getView()+"/filter:"+persDTO.getFilter()+"?page="+(paginator.getCurrentPage()+1);
   }
   
   
   
   
   
   

    
}