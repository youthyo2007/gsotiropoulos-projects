package com.socialvideo.web.controllers;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialvideo.constant.Constants;
import com.socialvideo.constant.QueryConstants;
import com.socialvideo.constant.SysPropsConstants;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.Activity;
import com.socialvideo.data.dto.Activity.ActivityEnum;
import com.socialvideo.data.dto.statistics.BIQueryDTO;
import com.socialvideo.data.dto.ActivityIdentity;
import com.socialvideo.data.dto.AvatarDTO;
import com.socialvideo.data.dto.ContactDTO;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.SettingsDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VideoCatalogQueryDTO;
import com.socialvideo.data.dto.VideoClaimDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.services.EmailService;
import com.socialvideo.data.services.PublicVideoService;
import com.socialvideo.data.services.SystemPropertiesService;
import com.socialvideo.data.services.UserService;
import com.socialvideo.data.services.VideoClaimService;
import com.socialvideo.data.services.inter.IVideoService;



@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/static")
public class StaticPagesController {

	 private static final Logger logger = Logger.getLogger(StaticPagesController.class);
	


	 @Autowired
	 protected PublicVideoService videoService;

	 @Autowired
	 protected ObjectMapper jacksonMapper;
	 
	 @Autowired
	 protected UserService userService;

	 @Autowired
	 protected SystemPropertiesService sysPropsService;

	 @Autowired
	 protected EmailService emailService;

	 
	
	//CONTACT US , ABOUT US, TERMS OF USE 
	 
    public StaticPagesController() {
    }

    
    
    
    
	 @ModelAttribute(WebConstants.CONTACTUSFRM)
    public ContactDTO init_CONTACTUSFRM() {
		 ContactDTO contactDTO = new ContactDTO();
	     return  contactDTO;
    }
	 	 
    
    
    //TERMS-OF-USE
    @RequestMapping(value={"/terms/index"}, method=RequestMethod.GET)
    public ModelAndView termsOfUse(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity) throws Exception {
        ModelAndView mav = null;
        
        mav = new ModelAndView("static-terms-view");

        //LOG
    	Activity activity = new Activity(ActivityEnum.TERMSOFSERVICEVIEW, identity);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));

     
        return mav;
    }
    

    
    //TERMS-OF-USE
    @RequestMapping(value={"/privacy/index"}, method=RequestMethod.GET)
    public ModelAndView privacyPolicy(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity) throws Exception {
        ModelAndView mav = null;
        
        mav = new ModelAndView("static-privacy-view");

        //LOG
    	Activity activity = new Activity(ActivityEnum.PRIVACYPOLICYVIEW, identity);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));

     
        return mav;
    }
    
    
    @RequestMapping(value={"/contact/index"}, method=RequestMethod.GET)
    public ModelAndView contactUs(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity) throws Exception {
        ModelAndView mav = null;
        
        mav = new ModelAndView("static-contact-view");

        //LOG
    	Activity activity = new Activity(ActivityEnum.CONTACTUSVIEW, identity);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));

     
        return mav;
    }

    
    //CLAIM REQUEST SUBMIT
    @RequestMapping(value={"/contact/submit"}, method=RequestMethod.POST)
    public ModelAndView contactSubmit(Device device, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity, @ModelAttribute(WebConstants.CONTACTUSFRM) ContactDTO contactDTO) throws Exception {
    	ModelAndView mav = new ModelAndView("static-contact-success-view");

    	UserDTO user = null;
    	if(activeUser!=null) {
    		 user = userService.getUserByID(activeUser.getId());

    	}

    	//SEND EMAIL
    	List<String> emailList =  Arrays.asList(sysPropsService.selectSystemPropertyByKey(SysPropsConstants.EMAILLIST_CONTACTUS).getValue().split(","));
    	for (String email : emailList) {
    		emailService.contactUsEmail(email, user, contactDTO.getMessage());
    		
    		
    	}
    	  
    	 //FIND 10 LATEST VIDEOS
        List<PublicVideoDTO> resultList = videoService.selectCatalogVideos(new VideoCatalogQueryDTO(null, QueryConstants.FilterLatest, null,null), new PaginatorDTO(10));
        //List videoListPartions = WebUtility.publicPartitionVideoList(device,resultList, WebConstants.GRIDSTYLE_BOX);
        mav.addObject("videoListDTO", resultList);
        
        return mav;       

    }
    
    
    
    
    
    @RequestMapping(value={"/about/index"}, method=RequestMethod.GET)
    public ModelAndView aboutUs(@ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity) throws Exception {
        ModelAndView mav = null;
        
        mav = new ModelAndView("static-about-view");

        //LOG
    	Activity activity = new Activity(ActivityEnum.ABOUTUSVIEW, identity);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));

     
        return mav;
    }
    
    
    
        
}