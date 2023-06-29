package com.socialvideo.web.controllers;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mobile.device.Device;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.ActivityIdentity;
import com.socialvideo.data.dto.CaptchaDTO;
import com.socialvideo.data.dto.CaptchaSecurityDTO;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.PublicVideoDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.services.IntegrationService;
import com.socialvideo.data.services.PublicVideoService;
import com.socialvideo.data.services.VideoService;



@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/robot-check")
@SessionAttributes({WebConstants.SECDTO, WebConstants.CAPTHASECFRM, WebConstants.SECURITY_VIDEOCOUNTS, WebConstants.SECURITY_LOCKVIDEO})
public class RobotCheckController {

	 private static final Logger logger = Logger.getLogger(RobotCheckController.class);
	
	 @Autowired
	 protected PublicVideoService videoService;

	 
	 @Autowired
	 protected VideoService privateVideoService;
	 
	
	 @Autowired
	 protected IntegrationService integrationService;

	 
	/******************************SECURITY INIT****************************************************************************************************/
 	 @ModelAttribute(WebConstants.SECDTO)
     public CaptchaSecurityDTO init_CAPTHASECDTO() {
		   return  new CaptchaSecurityDTO();
     }

 	 @ModelAttribute(WebConstants.CAPTHASECFRM)
     public CaptchaDTO init_CAPTHASECFRM() {
		   return  new CaptchaDTO();
     }
	 
	 
	 
    public RobotCheckController() {
    }
    
    
    //ROBOT CHECK REQUEST
    @RequestMapping(value={"/index"}, method=RequestMethod.GET)
    public ModelAndView createCaptchaImage(Device device, @RequestParam(value = "uuid", required=false) String uuid, @RequestParam(value = "error", required=false) String error, @AuthenticationPrincipal CurrentUserDTO activeUser,  @ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity,@ModelAttribute(WebConstants.SECDTO) CaptchaSecurityDTO securityDTO, @ModelAttribute(WebConstants.CAPTHASECFRM) CaptchaDTO captchaForm) throws Exception {
        ModelAndView mav = null;


        //CREATE AUTOMATIC TOKEN
        String token = UUID.randomUUID().toString().replaceAll("-", "").substring(0,5);
        mav = new ModelAndView("robot-check-view");
        PublicVideoDTO videoDTO = videoService.findPublicVideoByUUID(uuid);
        String image = integrationService.createRobotCheckImgBase64(videoDTO, token);
        mav.addObject("image", image);
        mav.addObject("device", device);
        mav.addObject("uuid", uuid);
        mav.addObject("error", error);
        
        
        //CREATE SECURITY CAPTHCA AND PUT IT INTO SESSION 
        securityDTO = new CaptchaSecurityDTO();
        securityDTO.setUuid(uuid);
        securityDTO.setSecuritytoken(token);
        securityDTO.setExpires(String.valueOf(System.currentTimeMillis()+(30000)));
        securityDTO.setIp(identity.getIp());
	    mav.addObject(WebConstants.SECDTO, securityDTO);

        
        
        //CREATE CAPTHA FORM
	    captchaForm = new CaptchaDTO();
        mav.addObject(WebConstants.CAPTHASECFRM, captchaForm);
        return mav;
    }

    
    @RequestMapping(value={"/submit"}, method=RequestMethod.POST)
    public String validateCaptchaImage(Model model, Device device, @AuthenticationPrincipal CurrentUserDTO activeUser,  @ModelAttribute(WebConstants.IDENTITY) ActivityIdentity identity, @ModelAttribute(WebConstants.SECDTO) CaptchaSecurityDTO securityDTO, @ModelAttribute(WebConstants.CAPTHASECFRM) CaptchaDTO captchaFrm) throws Exception {
        String redirect = "";
        Long currentTime = System.currentTimeMillis();
        
        
        
        //CHECK TOKEN
        if(currentTime<Long.parseLong(securityDTO.getExpires())) {
        	  	System.out.println("captchaFrm.getToken():"+captchaFrm.getCaptchatoken());
        	  	System.out.println("securityDTO.getToken():"+securityDTO.getSecuritytoken());

        	  	if(captchaFrm.getCaptchatoken().equalsIgnoreCase(securityDTO.getSecuritytoken())) {
        			  redirect = "redirect:/video/"+securityDTO.getUuid();
        			  model.addAttribute(WebConstants.SECURITY_LOCKVIDEO, new Boolean(false));
        			  model.addAttribute(WebConstants.SECURITY_VIDEOCOUNTS,0);
        	  } else {
     			  redirect = "redirect:/robot-check/index?uuid="+securityDTO.getUuid()+"&error=invalidtoken";
        	  } 
        } else {
			  redirect = "redirect:/robot-check/index?uuid="+securityDTO.getUuid()+"&error=expired";
        }
        
   
        
        return redirect;
    }
 
    
   
    
        
}