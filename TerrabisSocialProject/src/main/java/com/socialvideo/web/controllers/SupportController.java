package com.socialvideo.web.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.socialvideo.constant.QueryConstants;
import com.socialvideo.constant.VideoPrivacyStatus;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.PaginatorDTO;
import com.socialvideo.data.dto.PasswordResetTokenDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VerificationTokenDTO;
import com.socialvideo.data.dto.VideoCatalogQueryDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.services.CollectionService;
import com.socialvideo.data.services.EmailService;
import com.socialvideo.data.services.inter.IUserService;
import com.socialvideo.data.services.inter.IVideoService;
import com.socialvideo.utilities.WebUtility;

@EnableAutoConfiguration
@Controller
@RequestMapping(value = "/support")
public class SupportController {
	private static final Logger LOGGER = Logger.getLogger(SupportController.class);


    @Autowired
    private IUserService userService;


	 @Autowired
	 protected IVideoService videoService;
    
    
    @Autowired
    private CollectionService collectionService;

    @Autowired
    private EmailService emailService;
    

    public SupportController() {
        super();
    }

    
    
    
    
  //DYNAMIC ACTION REQUEST PAGE
    @RequestMapping(value = "/password-reset/success", method = RequestMethod.GET) 
    public ModelAndView passwordResetSucess() throws Exception {
    	  ModelAndView mav = new ModelAndView("support-password-reset-success");
    	  return mav;
         
    }
    
    
    @RequestMapping(value = "/password-reset", method = RequestMethod.GET)
    public String passwordReset(@RequestParam("token")  String token,  RedirectAttributes redirectAttributes) throws Exception {
         PasswordResetTokenDTO verificationToken = userService.getPasswordResetToken(token);
        if (verificationToken == null) {
             //String message = messages.getMessage("auth.message.regverification.tokeninvalid", null, locale);
        	String message = "something missing from the equation...";
        	 redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/error/message";
        }
        
         UserDTO user = verificationToken.getUser();
         Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
        	String message = "this link has expired.";
        	redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/error/message";          
        }

        
        //RESET PASSWORD
        String newPassword =RandomStringUtils.randomAlphanumeric(8);
        userService.changeUserPassword(user.getId(), newPassword);
        
        //SEND EMAIL OF NEW PASSWORD
        emailService.newPasswordEmail(user, newPassword);
        
        Map<String, Object> parameters = new HashMap<>();
        return "redirect:/support/password-reset/success";  
    }
    
    
    
    
    
    /**************************DYNAMIC ACCOUNT FORM REQUEST SUBMIT SUCCESS WEB FLOW **********************************/ 
    
    //DYNAMIC ACTION REQUEST PAGE
    @RequestMapping(value = "/{ACTION}/request", method = RequestMethod.GET) 
    public ModelAndView dynamicActionRequest(@PathVariable String ACTION) throws Exception {
    	  ModelAndView mav = new ModelAndView("support-"+ACTION+"-request");
    	  return mav;
         
    }
    
    
    
    //DYNAMIC ACTION AFTER SUBMIT SUCCESS PAGE
    @RequestMapping(value = "/{ACTION}/request/sucess", method = RequestMethod.GET) 
    public ModelAndView dynamicActionRequestSubmitSucess(@PathVariable String ACTION) throws Exception {
    	  ModelAndView mav = new ModelAndView("support-"+ACTION+"-request-success");
    	  return mav;
         
    }

  
    
    
    //DYNAMIC ACTION REQUEST SUBMIT
    @RequestMapping(value = "/{ACTION}/request/submit", method = RequestMethod.POST) 
    public String dynamicActionRequestSubmit(@ModelAttribute(WebConstants.REGISTERFRM) UserDTO accountDTO, @PathVariable String ACTION) throws Exception {
     
    	 UserDTO userDTO =  userService.findUserByEmail(accountDTO.getEmail());
    	 
    	 if(userDTO==null)
    		 throw new Exception("We can't find any account under '"+accountDTO.getEmail()+"'. Are you sure you've entered the correct email?");
    	 
    	 //CREATE NEW  TOKEN UUID
	     String uuidToken = UUID.randomUUID().toString();
	 
    	
    	switch (ACTION) {
		case "reset-activation-token":
		     //CREATE TOKEN
		     userService.createVerificationTokenForUser(userDTO, uuidToken);

		     //SEND NEW TOKEN
		     emailService.resetActivationTokenEmail(userDTO, uuidToken);
			
			break;

		case "reset-password-token":

			//CREATE TOKEN
		     userService.createPasswordResetTokenForUser(userDTO, uuidToken);

		     //SEND NEW TOKEN
		     emailService.resetPasswordTokenEmail(userDTO, uuidToken);
			
			break;

		default:
			break;
		}
   
      //FIND USER
        	
     return "redirect:/support/"+ACTION+"/request/sucess"; 
         
    }

    
    
    
    
    
    
    
    
    

}
