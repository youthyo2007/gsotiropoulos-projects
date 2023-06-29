package com.socialvideo.web.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialvideo.constant.Constants;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.Activity;
import com.socialvideo.data.dto.Activity.ActivityEnum;
import com.socialvideo.data.dto.ActivityIdentity;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VideoClaimDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.services.EmailService;
import com.socialvideo.data.services.PublicVideoService;
import com.socialvideo.data.services.UserService;
import com.socialvideo.data.services.VideoClaimService;
import com.socialvideo.data.services.inter.IVideoService;



@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/claim")
public class ClaimController {

	 private static final Logger logger = Logger.getLogger(ClaimController.class);
	
	 @Autowired
	 protected PublicVideoService videoService;

	 
	 @Autowired
	 protected IVideoService privateVideoService;
	 
	 @Autowired
	 protected ObjectMapper jacksonMapper;
	 
	 @Autowired
	 protected UserService userService;

	 @Autowired
	 protected VideoClaimService claimService;

	 @Autowired
	 protected EmailService emailService;

	 
	
	 
	 
    public ClaimController() {
    }
    
    
    //CLAIM REQUEST
    @RequestMapping(value={"/request/{videoid}"}, method=RequestMethod.GET)
    public ModelAndView claimRequest(Device device, @AuthenticationPrincipal CurrentUserDTO activeUser, @PathVariable Long videoid, @ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity) throws Exception {
        ModelAndView mav = null;
        
        if(activeUser==null || activeUser.getUser()==null)
        	throw new AccessDeniedException("You need to have a valid account with TERRABIS before claiming the ownership of the video. Please sign in or register.");
        
        mav = new ModelAndView("claim-request-view");
        
        //CLAIMER ID

        Long claimerid = activeUser.getId();
         
       //CHECK TO SEE IF CLAIM EXIST FOR THIS VIDEO
        //List<VideoClaimDTO> claimDTOList = claimService.findClaimsOfVideo(videoid);
        //VideoClaimDTO claimDTO = claimService.findClaimByVideoID(videoid);
        //mav.addObject(WebConstants.VIDEOCLAIMDTO, claimDTO);

        
        //SELECT SELECTED VIDEO
        PublicVideoDTO video = videoService.findPublicVideoById(videoid); 
        mav.addObject(WebConstants.VIDEODTO, video);

        VideoClaimDTO claimFormDTO = new VideoClaimDTO();
        mav.addObject(WebConstants.VIDEOCLAIMFRM,claimFormDTO);
        mav.addObject("device", device);

        return mav;
    }
    
    
    
    
    //CLAIM REQUEST SUBMIT
    @RequestMapping(value={"/request/submit"}, method=RequestMethod.POST)
    public ModelAndView claimSubmit(Device device, @AuthenticationPrincipal CurrentUserDTO activeUser, @ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity, @ModelAttribute(WebConstants.VIDEOCLAIMFRM) VideoClaimDTO claim) throws Exception {
    	ModelAndView mav = new ModelAndView("claim-success-view");

    	 //SELECT SELECTED VIDEO
        VideoDTO privateVideoDTO = privateVideoService.findVideoById(claim.getVideoid()); 
        UserDTO owner = userService.findUserById(privateVideoDTO.getUser().getId());
        UserDTO claimer = userService.findUserById(activeUser.getId());
         	
        
        
        
        
     
        mav.addObject("device", device);
        Long claimID = claimService.createClaim(claimer.getId(), claim.getVideoid());
        mav.addObject("claimID", claimID);
        
      //LOG
    	Activity activity = new Activity(ActivityEnum.CLAIM, identity,owner.getId(), claim.getVideoid(),null,null,null);
        logger.info(Constants.LOGGERPREFIX+jacksonMapper.writeValueAsString(activity));
        
        //SEND EMAIL TO THE OWNER OF THE VIDEO FOR THE CLAIM OF OWNERSHIP
		try {
		if(!privateVideoDTO.getIsfile())	 {
			emailService.videoClaimRequestEmail(owner.getEmail(), privateVideoDTO,claimer,owner);
		}
		} catch (Exception e) {e.printStackTrace();}
		

		
		//GET PUBLIC VIDEO AND ADD IT TO RESPONSE
		PublicVideoDTO videoDTO = videoService.findPublicVideoById(claim.getVideoid()); 
		 mav.addObject(WebConstants.VIDEODTO, videoDTO);
		
		return mav;
    }
    
   
    
        
}