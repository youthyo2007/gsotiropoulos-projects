package com.socialvideo.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.socialvideo.constant.VideoPublishStatus;
import com.socialvideo.constant.WebConstants;
import com.socialvideo.data.dto.AjaxFormValidationIOResultDTO;
import com.socialvideo.data.dto.AjaxProfileDTO;
import com.socialvideo.data.dto.AjaxResultDTO;
import com.socialvideo.data.dto.AjaxVideoDTO;
import com.socialvideo.data.dto.CurrentUserDTO;
import com.socialvideo.data.dto.UserDTO;
import com.socialvideo.data.dto.VideoDTO;
import com.socialvideo.data.services.ConnectEntityService;
import com.socialvideo.data.services.IntegrationService;
import com.socialvideo.data.services.inter.IUserService;
import com.socialvideo.data.services.inter.IVideoService;;

@EnableAutoConfiguration
@RestController
@RequestMapping(value = "/rest-validation")
@SessionAttributes({WebConstants.PERSONALIZATION})
public class RestValidationController {

	 private static final Logger logger = Logger.getLogger(RestValidationController.class);

	 @Autowired
	 private IUserService userService;
	 
	 @Autowired
	 protected IntegrationService integrationService;
	 
	 
	 @Autowired
	 protected ConnectEntityService cntService;
	 
	  
	 
	 
	@Autowired
	private BCryptPasswordEncoder encoder;
	 
	 
	 
    public RestValidationController() {
    }

    
    
    
    //CHECK IF SOMEONE UPLOADED THE SAME LINK
    @RequestMapping(value="/upload/duplicate/video-url", method = RequestMethod.POST)
	public ResponseEntity<AjaxFormValidationIOResultDTO> duplicateUpload(Model model, @RequestBody  AjaxVideoDTO ajaxDTO) {
    
    	ResponseEntity<AjaxFormValidationIOResultDTO> response = null;
    	
    	Boolean valid = true;
    	
    	//CHECK IF VIDEO EXISTS
    	VideoDTO duplicateVideoDTO = integrationService.getVideoFromVimeoYoutubeURL(ajaxDTO.getVideourl());
    	
    	if(duplicateVideoDTO!=null)
    		valid = false;
    	
    	
    	response = new ResponseEntity<AjaxFormValidationIOResultDTO>(new AjaxFormValidationIOResultDTO(valid.toString()),  HttpStatus.OK);
    	
    	return response;
    }

    //CHECK FOR DUPLICATE VIDEO
    @RequestMapping(value="/video/duplicate/youtube", method = RequestMethod.POST)
	public ResponseEntity<AjaxFormValidationIOResultDTO> duplicateVideo(Model model, @RequestBody  AjaxVideoDTO ajaxDTO) {
    
    	ResponseEntity<AjaxFormValidationIOResultDTO> response = null;
    	
    	Boolean valid = true;
    	
    	//CHECK IF VIDEO EXISTS
    	VideoDTO duplicateVideoDTO = integrationService.getVideoFromYoutubeID(ajaxDTO.getVideoid());
    	
    	if(duplicateVideoDTO!=null)
    		valid = false;
    	
    	
    	response = new ResponseEntity<AjaxFormValidationIOResultDTO>(new AjaxFormValidationIOResultDTO(valid.toString(),duplicateVideoDTO!=null ? duplicateVideoDTO.getUUID() : null),  HttpStatus.OK);
    	
    	return response;
    }
    
    
    //CHECK IF SOMEONE UPLOADED THE SAME LINK AND IS REJECTED
    @RequestMapping(value="/upload/rejected/video-url", method = RequestMethod.POST)
	public ResponseEntity<AjaxFormValidationIOResultDTO> rejectedBeforeSameUpload(Model model, @RequestBody  AjaxVideoDTO ajaxDTO) {
    
    	ResponseEntity<AjaxFormValidationIOResultDTO> response = null;
    	
    	Boolean valid = true;
    	
    	//CHECK IF VIDEO EXISTS
    	VideoDTO duplicateVideoDTO = integrationService.getVideoFromVimeoYoutubeURL(ajaxDTO.getVideourl());
    	
    	
    	//IF VIDEO EXISTS AND HAS BEEN REJECTED
    	if((duplicateVideoDTO!=null) && (duplicateVideoDTO.getStatus()==VideoPublishStatus.REJECTED))
    		valid = false;
    	
    	
    	response = new ResponseEntity<AjaxFormValidationIOResultDTO>(new AjaxFormValidationIOResultDTO(valid.toString()),  HttpStatus.OK);
    	
    	return response;
    }

    
    //CHECK IF THE NEW USERNAME EXISTS EXCLUDING THE CURRENT USERNAME
    @RequestMapping(value="/user-settings/duplicate/username", method = RequestMethod.POST)
	public ResponseEntity<AjaxFormValidationIOResultDTO> duplicateUserName(Model model, @AuthenticationPrincipal CurrentUserDTO activeUser, @RequestBody  AjaxProfileDTO ajaxDTO) {
    
    	ResponseEntity<AjaxFormValidationIOResultDTO> response = null;
    	Boolean valid = true;
    	UserDTO duplicateUserDTO = userService.findUserByUsername(ajaxDTO.getUsername());
    	if((duplicateUserDTO!=null) && (activeUser.getUsername()!=ajaxDTO.getUsername()))
    		valid = false;
    	
    	response = new ResponseEntity<AjaxFormValidationIOResultDTO>(new AjaxFormValidationIOResultDTO(valid.toString()),  HttpStatus.OK);
    	
    	return response;
    }
    
    
    //CHECK IF THH NEW USERNAME EXISTS EXCLUDING THE CURRENT USERNAME
    @RequestMapping(value="/user-settings/valid/old-password", method = RequestMethod.POST)
	public ResponseEntity<AjaxFormValidationIOResultDTO> validPassword(Model model, @AuthenticationPrincipal CurrentUserDTO activeUser, @RequestBody  AjaxProfileDTO ajaxDTO) {

    	ResponseEntity<AjaxFormValidationIOResultDTO> response = null;
    	Boolean valid = false;
    	
    
    	if(encoder.matches(ajaxDTO.getOldpassword().trim(), activeUser.getUser().getPassword()))
    			valid = true;
    			
    			
    	response = new ResponseEntity<AjaxFormValidationIOResultDTO>(new AjaxFormValidationIOResultDTO(valid.toString()),  HttpStatus.OK);
    	
    	return response;
    }
    

    
    //DUPLICATE RATING
    @RequestMapping(value="/video/duplicate/rating", method = RequestMethod.POST)
	public ResponseEntity<AjaxFormValidationIOResultDTO> duplicateRating( @AuthenticationPrincipal CurrentUserDTO activeUser, Model model, @RequestBody AjaxVideoDTO ajaxDTO) {
    
    	ResponseEntity<AjaxFormValidationIOResultDTO> response = null;
    	
    	Boolean valid = true;
    	
    	Long userid = (activeUser!=null && activeUser.getUser()!=null) ? activeUser.getUser().getId() : null;
		Long videoid = Long.parseLong(ajaxDTO.getVideoid());

		
		if(userid!=null) {
			  int previewsRatingThisVideo = cntService.isUserRatedVideo(userid, videoid);
			  if(previewsRatingThisVideo>0)
				  valid =false;
		}


		response = new ResponseEntity<AjaxFormValidationIOResultDTO>(new AjaxFormValidationIOResultDTO(valid.toString()),  HttpStatus.OK);
    	
    	return response;
    }
    
    
    
    //CHECK IF USER NAME EXISTS
    @RequestMapping(value="/registration/duplicate/username", method = RequestMethod.POST)
	public ResponseEntity<AjaxFormValidationIOResultDTO> duplicateUserName(Model model, @RequestBody  AjaxProfileDTO ajaxDTO) {
    
    	ResponseEntity<AjaxFormValidationIOResultDTO> response = null;
    	Boolean valid = true;
    	UserDTO duplicateUserDTO = userService.findUserByUsername(ajaxDTO.getUsername());
    	if(duplicateUserDTO!=null)
    		valid = false;
    	
    	response = new ResponseEntity<AjaxFormValidationIOResultDTO>(new AjaxFormValidationIOResultDTO(valid.toString()),  HttpStatus.OK);
    	
    	return response;
    }


    
    //CHECK IF EMAIL EXISTS
    @RequestMapping(value="/registration/duplicate/email", method = RequestMethod.POST)
	public ResponseEntity<AjaxFormValidationIOResultDTO> duplicateEmail(Model model, @RequestBody  AjaxProfileDTO ajaxDTO) {
    
    	ResponseEntity<AjaxFormValidationIOResultDTO> response = null;
    	Boolean valid = true;
    	UserDTO duplicateUserDTO = userService.fetchUserUponAuthentication(ajaxDTO.getEmail());
    	if(duplicateUserDTO!=null)
    		valid = false;
    	
    	response = new ResponseEntity<AjaxFormValidationIOResultDTO>(new AjaxFormValidationIOResultDTO(valid.toString()),  HttpStatus.OK);
    	
    	return response;
    }
    
    
    
    
    //CHECK IF EMAIL EXISTS
    @RequestMapping(value="/email-exists", method = RequestMethod.POST)
	public ResponseEntity<AjaxFormValidationIOResultDTO> existsEmail(Model model, @RequestBody  AjaxProfileDTO ajaxDTO) {
    
    	ResponseEntity<AjaxFormValidationIOResultDTO> response = null;
    	Boolean valid = false;
    	UserDTO userDTO = userService.findUserByEmail(ajaxDTO.getEmail());
    	if(userDTO!=null)
    		valid = true;
    	
    	response = new ResponseEntity<AjaxFormValidationIOResultDTO>(new AjaxFormValidationIOResultDTO(valid.toString()),  HttpStatus.OK);
    	
    	return response;
    }
    
    
}
    
    	
    	